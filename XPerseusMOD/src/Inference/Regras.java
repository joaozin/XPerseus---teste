package Inference;

/**
 * Módulo para aplicar diferentes operações em um conjunto de regras disponibilizado pelo usuário.
 * @autor Pedro Gazzola
 */
public class Regras {

    /**
     * Operação para dividir as regras concentradas em uma String, vindo diretamente de um arquivo ou digitada, em um vetor de Strings.
     * @param regrasSemTratamento
     * As regras exatamente como foram lidas do arquivo ou digitadas.
     * @return
     * Retorna um vetor de String, onde cada índice corresponde a uma regra.
     */
    public String[] getRegrasSeparadas(String regrasSemTratamento) {
        //remove quebras de linha
        regrasSemTratamento = regrasSemTratamento.replaceAll("(\n|\r)", "");
        //separa as regras através do ponto final dos axiomas.
        String[] regrasSeparadas = regrasSemTratamento.split("\\.");

        return regrasSeparadas;
    }

    /**
     *
     * @param regras
     * Conjunto de regras separadas em cada índice do vetor
     * @return
     * Cada índice no vetor de retorno contém apenas o nome da regra anteriormente representada no mesmo índice.
     */
    public String[] getNomesRegras(String[] regras) {
        String[] nomeRegras = new String[regras.length];
        for (int i = 0; i < regras.length; i++) {
            int indiceParenteses = regras[i].indexOf("(");
            nomeRegras[i] = regras[i].substring(0, indiceParenteses);
        }

        return nomeRegras;
    }

    /**
     * Retorna o nome de uma única regra.
     * @param regra
     * @return
     */
    public String getNomeRegra(String regra) {
        String nomeRegra = "";

        int indiceParenteses = regra.indexOf("(");
        nomeRegra = regra.substring(0, indiceParenteses);

        return nomeRegra;
    }

    /**
     * Separa as regras do predicado
     * @param regras
     * Um vetor de Strings com uma regra completa, nome + argumentos + predicado, em cada índice
     * @return
     * Retorna um outro vetor de String com uma regra em cada índce, contendo nome + argumentos
     */
    public String[] getNomeEArgumentosRegras(String[] regras) {
        String[] nomeArgumentosRegras = new String[regras.length];
        for (int i = 0; i < regras.length; i++) {
            int idxAbreParenteses = regras[i].indexOf(")");
            nomeArgumentosRegras[i] = regras[i].substring(0, idxAbreParenteses + 1).trim();
        }

        return nomeArgumentosRegras;
    }

    /**
     * Separa uma única regra de seu predicado
     * @param regra
     * Uma única regra completa, nome + argumentos + predicado.
     * @return
     * Retorna somente o nome + argumentos.
     */
    public String getNomeEArgumentosRegra(String regra) {
        String nomeArgumentosRegra = "";

        int idxAbreParenteses = regra.indexOf(")");
        nomeArgumentosRegra = regra.substring(0, idxAbreParenteses + 1).trim();

        return nomeArgumentosRegra;
    }

    /**
     * Retorna os argumentos de uma regra passada para a função
     * @param regra
     * A regra completa, com o nome e os argumentos.
     * @return
     * Um argumento da regra recebida é representado em cada índice do vetor.
     */
    public String[] getArgumentosRegra(String regra) {
        int idxAbreParenteses = regra.indexOf("(");
        int idxFechaParenteses = regra.indexOf(")");

        String[] argumentos = regra.substring(idxAbreParenteses + 1, idxFechaParenteses).split(",");

        return argumentos;
    }
}
