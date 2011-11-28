package Inference;

/**
 *
 * @author pedrogazzola
 */
import alice.tuprolog.*;
import br.ufrj.ppgi.parser.XMLParser;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Inferencia {

    private String regrasNaoAlteradas;
    private ArrayList<String> regrasSelecionadas;

    public void setRegras(String regras) {
        this.regrasNaoAlteradas = regras;
    }

    public String getRegras() {
        return this.regrasNaoAlteradas;
    }

    public void setRegrasSelecionadas(ArrayList<String> regrasSelecionadas) {
        this.regrasSelecionadas = regrasSelecionadas;
    }

    public ArrayList<String> getRegrasSelecionadas() {
        return this.regrasSelecionadas;
    }

    /**
     * Esta função não será mais necessária se utilizarmos a nova biblioteca do Diego M. Lima
     * @param fatosBase Fatos proveniente da tradução do documento XML base.
     * @param fatosModificados Fatos proveniente da tradução do documento XML modificado.
     * @return Retorna a união destes dois conjuntos de fatos com os identificadores normalizados.
     */
    public String setNormalizaFatos(String fatosBase, String fatosModificados) {
        //divide os fatos traduzidos provinientes dos documentos XML
        String[] fatosBaseDivididos = fatosBase.split("\n");
        String fatosBaseReconstruidos = "";

        String segundaLinha2 = fatosBaseDivididos[1];
        int inicioId2 = segundaLinha2.indexOf("(");
        int terminoId2 = segundaLinha2.indexOf(",");

        String trecho2 = segundaLinha2.substring(inicioId2 + 1, terminoId2);
        int idRaizModificada2 = Integer.parseInt(trecho2.substring(2));

        for (String linha : fatosBaseDivididos) {
            int fim = linha.indexOf(",");
            //alterar o id1 para qualquer que seja o id vindo do tradutor para o primeiro elemento
            if (fim == -1) {
                linha = linha.replaceAll("\\(id1\\)", "\\(base\\)");
                linha = linha.replaceAll("\\(id" + idRaizModificada2 + "\\),", "\\(base,\\)");
            } else {
                String primeiraParte = linha.substring(0, fim + 1);
                String segundaParte = linha.substring(fim + 1);
                primeiraParte = primeiraParte.replaceAll("id" + idRaizModificada2 + ",", "base,");
                linha = primeiraParte + segundaParte;
            }
            fatosBaseReconstruidos += linha + "\n";
        }

        String[] fatosModificadosDivididos = fatosModificados.split("\n");

        String segundaLinha = fatosModificadosDivididos[1];
        int inicioId = segundaLinha.indexOf("(");
        int terminoId = segundaLinha.indexOf(",");

        String trecho = segundaLinha.substring(inicioId + 1, terminoId);
        int idRaizModificada = Integer.parseInt(trecho.substring(2));

        String fatosModificadosReconstruidos = "";

        for (String linha : fatosModificadosDivididos) {
            int fim = linha.indexOf(",");

            if (fim == -1) {
                linha = linha.replaceAll("\\(id1\\)", "\\(modificado\\)");

            } else {
                String primeiraParte = linha.substring(0, fim + 1);
                String segundaParte = linha.substring(fim + 1);

                primeiraParte = primeiraParte.replaceAll("id" + idRaizModificada + ",", "modificado,");
                linha = primeiraParte + segundaParte;
            }
            fatosModificadosReconstruidos += linha + "\n";
        }
        String fatosUniao = fatosBaseReconstruidos + "\n" + fatosModificadosReconstruidos;
        return fatosUniao;
    }

    /**
     * Esta função utiliza uma biblioteca de tradução de XML para fatos Prolog,
     * disponibilizada por Diego M. Lima (2011).
     *
     * @param arqBase
     * A primeira  versão, ou versão base, do arquivo XML a ser analisado.
     * @param arqModificado
     * A versão modificada do arquivo XML a ser analisado.
     * @return
     * Retorna dois objeto do tipo String contendo os fatos provenientes da
     * tradução dos dois documentos XML passados por parâmetro.
     */
    public String[] setTraduzFatos(File arqBase, File arqModificado) {
        XMLParser parserBase = new XMLParser(arqBase);
        String fatosBaseTraduzidos = parserBase.executeParse();

        XMLParser parserModificado = new XMLParser(arqModificado);

        String fatosModificadosTraduzidos = parserModificado.executeParse();

        String[] fatos = new String[2];

        fatos[0] = fatosBaseTraduzidos;
        fatos[1] = fatosModificadosTraduzidos;

        return fatos;
    }

    /**
     * Retorna o resultado da aplicação das regras Prolog nos fatos traduzidos e normalizados.
     * @param fatosNormalizados
     * Os fatos
     * @return
     */
    public String setExecutaProlog(String fatosNormalizados) {
        String resultados = "";

        Prolog engine = new Prolog();
        Theory teoria;

        try {
            teoria = new Theory(fatosNormalizados + this.regrasNaoAlteradas);
            engine.setTheory(teoria);
        } catch (InvalidTheoryException ex) {
            Logger.getLogger(Inferencia.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro na formulação da teoria.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        Regras moduloRegras = new Regras();

        for (String regra : getRegrasSelecionadas()) {
            String nomeDaRegra = moduloRegras.getNomeRegra(regra);
            //se a regra possui mais de 1 argumento
            //se sim, precisamos construir um struct especial
            if (moduloRegras.getArgumentosRegra(regra).length > 1) {
                String[] argumentos = moduloRegras.getArgumentosRegra(regra);
                Var[] listaVar = new Var[argumentos.length];
                int identificadorVar = 0;

                for (String argumento : argumentos) {
                    Var variavel = new Var("Xid" + argumento);
                    listaVar[identificadorVar] = variavel;
                    identificadorVar++;
                }

                Struct novoStruct = new Struct(nomeDaRegra, listaVar);
                resultados += setExecuta(engine, novoStruct, regra) + "\n";

            } else {
                Var X = new Var("X");
                Struct novoStruct = new Struct(nomeDaRegra, X);
                resultados += setExecuta(engine, novoStruct, regra) + "\n";
            }
        }

        return resultados;
    }

    private String setExecuta(Prolog motorProlog, Struct struct, String regra) {
        // @TODO repensar as regras que nem todos os argumentos sao (F)atos ou nem todos sao (v)ariaveis.
        // talvez se imprimir o nome do argumento + a solução.
        String saida = "";

        SolveInfo info = motorProlog.solve(struct);

        //System.out.println(info.toString());


        saida += struct.getName().toUpperCase() + ":\n";
        try {
            while (info.isSuccess()) {
                Regras moduloRegras = new Regras();

                String[] argumentos = moduloRegras.getArgumentosRegra(regra);
                int numeroArgumento = 0;
                for(String argumento : argumentos){
                    saida += info.getVarValue(struct.getArg(numeroArgumento).toString()) + "\n";
                    numeroArgumento++;
                }

                if (motorProlog.hasOpenAlternatives()) {
                    try {
                        info = motorProlog.solveNext();
                    } catch (NoMoreSolutionException ex) {
                        System.out.println("erro na Teoria");
                    }
                } else {
                    motorProlog.solveEnd();
                    break;
                }
            }
        } catch (NoSolutionException ex) {
            System.out.println("erro na Teoria");
        }
        return (saida);
    }
}
