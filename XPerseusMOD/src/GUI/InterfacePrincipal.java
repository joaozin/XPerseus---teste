package GUI;

import Inference.Inferencia;
import Diff.XMLDiff;
import Merge.XMLMerge;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * @author versão Original: Rafael Barros
 * @author versão Micenas: Brian Mazini Siervi ~> http:www.briansiervi.com
 * @author módulo inferência: Pedro Gazzola
 */
public class InterfacePrincipal extends JFrame implements ActionListener, ItemListener {

    private static final long serialVersionUID = 1L;
    private boolean moduloInferencia = false;
    private Container conteiner;
    private JMenu mArquivo, mEditar, mVisualizar, mLinguagem, mSelecionar, mSkin, mSobre, mInferencia;
    private JMenuItem miPortugues, miEnglish, miLog; // Linguagens
    private JRadioButtonMenuItem radioSkinMetal, radioSkinNimbus, radioSkinMotif, radioSkinWindows, radioSkinWindowsClassic; // Skins
    private ButtonGroup grupoSkin;
    private JMenuItem miNovo, miAbrir, miSalvar, miSair, miFonte, miSobreAutor, miSobreFerramenta, miSobreGetComp, miAtivarInf, miDesativarInf;
    private JMenuBar barraDeMenu;
    private JPanel pnlSuperior, pnlInferior, desktop;
    private JTextField txtCaminhoArquivo, txtCaminhoArquivo2;
    private JButton btnNovo, btnDiferencas, btnMesclar, btnAbrirCaminho, btnAbrirCaminho2, btnSalvarDiff, btnSalvarMerge;
    private JTextPane txtPane1, txtPane2, txtPane3;
    private AbrirAction abrir;
    private String caminhoArquivo1, caminhoArquivo2;
    private static int alturaMonitor;
    private static int larguraMonitor;
    private static InterfacePrincipal XPerseus;
    FiltroJFileChooser fJavaFilter = new FiltroJFileChooser();
    File fFile = new File("default.xml");
    File[] fFiles;

    public InterfacePrincipal() {
        super("XPerseus - Controle de Versões de Dados SemiEstruturados");
        //setListarLookAndFeel();

        // Tentando setar o Nimbus look and feel
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        conteiner = getContentPane();
        conteiner.setLayout(new BorderLayout());

        /***************************** Pegando a resolucao do monitor ******************************/
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        alturaMonitor = (int) dim.getHeight();
        larguraMonitor = (int) dim.getWidth();
        /******************************************************************************************/
        /**************************** Criando a barra de menus ************************************/
        barraDeMenu = new JMenuBar();

        /***** Menu Arquivo *****/
        mArquivo = new JMenu("Arquivo");
        mArquivo.setMnemonic('A');
        miNovo = new JMenuItem("Novo");
        miNovo.setMnemonic('N');
        miAbrir = new JMenuItem("Abrir");
        miAbrir.setMnemonic('b');
        miSalvar = new JMenuItem("Salvar");
        miSalvar.setMnemonic('S');
        miSair = new JMenuItem("Sair");
        miSair.setMnemonic('r');

        mArquivo.add(miNovo);
        mArquivo.add(miAbrir);
        mArquivo.add(miSair);

        /***** Menu Editar *****/
        mEditar = new JMenu("Editar");
        mEditar.setMnemonic('E');

        miFonte = new JMenuItem("Fonte");
        miFonte.setMnemonic('F');

        mEditar.add(miFonte);

        /***** Menu Visualizar *****/
        mVisualizar = new JMenu("Visualizar");
        mVisualizar.setMnemonic('V');

        miLog = new JMenuItem("Log's do programa");
        miLog.setMnemonic('L');

        mLinguagem = new JMenu("Linguagem");
        mLinguagem.setMnemonic('g');

        miPortugues = new JMenuItem("Português");
        miPortugues.setMnemonic('P');
        miEnglish = new JMenuItem("English");
        miEnglish.setMnemonic('E');

        mLinguagem.add(miPortugues);
        mLinguagem.add(miEnglish);

        mVisualizar.add(miLog);
        mVisualizar.add(mLinguagem);

        /***** Menu Selecionar *****/
        mSelecionar = new JMenu("Selecionar");
        mSelecionar.setMnemonic('S');

        mSkin = new JMenu("Skin");
        mSkin.setMnemonic('k');

        mInferencia = new JMenu("Módulo de inferência");
        mInferencia.setMnemonic('i');

        /* Opção de ativar ou desativar módulo de inferência de informações do XPerseus */
        miDesativarInf = new JMenuItem("Desativar");
        miDesativarInf.setMnemonic('d');
        miDesativarInf.addActionListener(this);

        miAtivarInf = new JMenuItem("Ativar");
        miAtivarInf.setMnemonic('a');
        miAtivarInf.addActionListener(this);

        mInferencia.add(miAtivarInf);
        mInferencia.add(miDesativarInf);

        /* */


        radioSkinMetal = new JRadioButtonMenuItem("Metal");
        radioSkinMetal.setMnemonic('M');
        radioSkinMetal.addItemListener(this);

        radioSkinNimbus = new JRadioButtonMenuItem("Nimbus");
        radioSkinNimbus.setMnemonic('N');
        radioSkinNimbus.setSelected(true);
        radioSkinNimbus.addItemListener(this);

        radioSkinMotif = new JRadioButtonMenuItem("Motif");
        radioSkinMotif.setMnemonic('M');
        radioSkinMotif.addItemListener(this);

        radioSkinWindows = new JRadioButtonMenuItem("Windows");
        radioSkinWindows.setMnemonic('W');
        radioSkinWindows.addItemListener(this);

        radioSkinWindowsClassic = new JRadioButtonMenuItem("Windows Classic");
        radioSkinWindowsClassic.setMnemonic('C');
        radioSkinWindowsClassic.addItemListener(this);

        grupoSkin = new ButtonGroup();
        grupoSkin.add(radioSkinMetal);
        grupoSkin.add(radioSkinNimbus);
        grupoSkin.add(radioSkinMotif);
        grupoSkin.add(radioSkinWindows);
        grupoSkin.add(radioSkinWindowsClassic);

        mSkin.add(radioSkinMetal);
        mSkin.add(radioSkinNimbus);
        mSkin.add(radioSkinMotif);
        mSkin.add(radioSkinWindows);
        mSkin.add(radioSkinWindowsClassic);
        mSelecionar.add(mSkin);

        /***** Adicionando Acoes aos menuItens *****/
        miNovo.addActionListener(this);
        miAbrir.addActionListener(this);
        miSalvar.addActionListener(this);
        miSair.addActionListener(this);

        /***** Menu Sobre *****/
        mSobre = new JMenu("Sobre");
        mSobre.setMnemonic('S');
        miSobreAutor = new JMenuItem("Autor");
        miSobreAutor.setMnemonic('A');
        miSobreAutor.addActionListener(this);
        miSobreFerramenta = new JMenuItem("XPerseus");
        miSobreFerramenta.setMnemonic('X');
        miSobreFerramenta.addActionListener(this);
        miSobreGetComp = new JMenuItem("GET");
        miSobreGetComp.setMnemonic('G');
        miSobreGetComp.addActionListener(this);
        mSobre.add(miSobreAutor);
        mSobre.add(miSobreFerramenta);
        mSobre.add(miSobreGetComp);

        /***** Adicionando Menus a barra de menus *****/
        barraDeMenu.add(mArquivo);
        barraDeMenu.add(mEditar);
        barraDeMenu.add(mVisualizar);
        barraDeMenu.add(mSelecionar);
        barraDeMenu.add(mInferencia);
        barraDeMenu.add(mSobre);
        setJMenuBar(barraDeMenu);
        /******************************************************************************************/
        /********************************** Painel Superior ***************************************/
        pnlSuperior = new JPanel();
        pnlSuperior.setBackground(Color.DARK_GRAY);

        btnDiferencas = new JButton("Diferenças");
        btnDiferencas.addActionListener(this);
        btnMesclar = new JButton("Mesclar");
        btnMesclar.addActionListener(this);

        txtCaminhoArquivo = new JTextField(35);
        txtCaminhoArquivo.setEditable(false);
        txtCaminhoArquivo2 = new JTextField(35);
        txtCaminhoArquivo2.setEditable(false);

        btnAbrirCaminho = new JButton();
        btnAbrirCaminho.addActionListener(this);
        btnAbrirCaminho.setIcon(new ImageIcon(getClass().getResource("/imagens/iconePasta2.PNG")));
        btnAbrirCaminho.setPreferredSize(new Dimension(23, 23));
        btnAbrirCaminho2 = new JButton();
        btnAbrirCaminho2.addActionListener(this);
        btnAbrirCaminho2.setIcon(new ImageIcon(getClass().getResource("/imagens/iconePasta2.PNG")));
        btnAbrirCaminho2.setPreferredSize(new Dimension(23, 23));

        pnlSuperior.add(txtCaminhoArquivo);
        pnlSuperior.add(btnAbrirCaminho);
        pnlSuperior.add(btnDiferencas);
        pnlSuperior.add(btnMesclar);
        pnlSuperior.add(btnAbrirCaminho2);
        pnlSuperior.add(txtCaminhoArquivo2);
        /******************************************************************************************/
        /********************************* Painel Central *****************************************/
        desktop = new JPanel(new BorderLayout());
        desktop.setBackground(Color.DARK_GRAY);

        /***** primeira area de texto *****/
        txtPane1 = new JTextPane();
        txtPane1.setText("");
        txtPane1.setFont(new Font("Arial", Font.PLAIN, 20));
        JScrollPane sPane1 = new JScrollPane(txtPane1);

        /***** segunda area de texto *****/
        txtPane2 = new JTextPane();
        txtPane2.setText("");
        txtPane2.setFont(new Font("Arial", Font.PLAIN, 20));
        JScrollPane sPane2 = new JScrollPane(txtPane2);

        /***** terceira area de texto *****/
        txtPane3 = new JTextPane();
        txtPane3.setText("");
        txtPane3.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane sPane3 = new JScrollPane(txtPane3);

        /***** Cria o JSplitPane *****/
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sPane1, sPane2);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation((getLarguraMonitor() - 10) / 2);
        splitPane.setPreferredSize(new Dimension(getLarguraMonitor() - 50, getAlturaMonitor() - 50));

        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, sPane3);
        splitPane2.setOneTouchExpandable(true);
        splitPane2.setDividerLocation((getAlturaMonitor() - 50) / 2);
        splitPane2.setPreferredSize(new Dimension(getLarguraMonitor() - 50, getAlturaMonitor() - 50));

        desktop.add(splitPane2, BorderLayout.CENTER);
        setVisible(true);
        /******************************************************************************************/
        /********************************* Painel Inferior ****************************************/
        pnlInferior = new JPanel();
        pnlInferior.setSize(getWidth(), 50);
        pnlInferior.setBackground(Color.DARK_GRAY);

        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(this);
        btnNovo.setIcon(new ImageIcon(getClass().getResource("/imagens/iconePasta.PNG")));
        btnNovo.setPreferredSize(new Dimension(180, 27));

        btnSalvarDiff = new JButton("Salvar Diferenças");
        btnSalvarDiff.addActionListener(this);
        btnSalvarDiff.setIcon(new ImageIcon(getClass().getResource("/imagens/iconeSave.PNG")));
        btnSalvarDiff.setPreferredSize(new Dimension(180, 27));

        btnSalvarMerge = new JButton("Salvar Mesclagem");
        btnSalvarMerge.addActionListener(this);
        btnSalvarMerge.setIcon(new ImageIcon(getClass().getResource("/imagens/iconeSave.PNG")));
        btnSalvarMerge.setPreferredSize(new Dimension(180, 27));
        btnSalvarMerge.setEnabled(false);

        pnlInferior.add(btnNovo);
        pnlInferior.add(btnSalvarDiff);
        pnlInferior.add(btnSalvarMerge);

        /******************************************************************************************/
        /*************************************** Container ****************************************/
        conteiner.add(pnlSuperior, BorderLayout.NORTH);
        conteiner.add(desktop, BorderLayout.CENTER);
        conteiner.add(pnlInferior, BorderLayout.SOUTH);

        setSize(larguraMonitor, alturaMonitor - 40);
        setLocation(0, 0);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent action) {
        if (action.getSource() == miNovo) {
            txtPane1.setText("");
            txtPane2.setText("");
            txtPane3.setText("");
            txtCaminhoArquivo.setText("");
            txtCaminhoArquivo2.setText("");
        } else if (action.getSource() == miAbrir) {
            if (txtPane1.getText().equals("")) {
                setEscolhetextPane(txtPane1);
            } else if (txtPane2.getText().equals("")) {
                setEscolhetextPane(txtPane2);
            } else {// Se os dois textPane's estiverem com texto, então abrimos no textPane1
                setEscolhetextPane(txtPane1);
            }
        } else if (action.getSource() == miSalvar) {
        } else if (action.getSource() == miSair) {
            System.exit(0);
        } else if (action.getSource() == miSobreAutor) {
            setSobre();
        } else if (action.getSource() == miSobreFerramenta) {
            setSobreFerramenta();
        } else if (action.getSource() == miSobreGetComp) {
            setSobreGetComp();
        } else if (action.getSource() == btnNovo) {
            if (moduloInferencia) {
                btnMesclar.setEnabled(false);
            }

            if (!moduloInferencia && !txtPane3.getText().equals("")) {// Se alterações foram realizadas no diff dos 2 arquivos, então perguntamos ao usuário se ele quer salvÃ¡-las

                int resposta = JOptionPane.showConfirmDialog(this, "Você deseja salvar as alterações realizadas?", "Pergunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (resposta == JOptionPane.YES_OPTION) {
                    setSalvarArquivoMerge();
                }
            }
            txtPane1.setText("");
            txtPane2.setText("");
            txtPane3.setText("");
            txtCaminhoArquivo.setText("");
            txtCaminhoArquivo2.setText("");
        } else if (action.getSource() == btnDiferencas) {

            if (txtCaminhoArquivo.getText().trim().equals("") && txtCaminhoArquivo2.getText().trim().equals("")) {
                XMLDiff.getErroSemArquivosAbertos();
            } else {
                if (moduloInferencia) {
                    setTraduzir();
                } else {
                    setDiferencas();
                }
            }
        } else if (action.getSource() == btnAbrirCaminho) {
            setEscolhetextPane(txtPane1);
        } else if (action.getSource() == btnAbrirCaminho2) {
            setEscolhetextPane(txtPane2);
        } else if (action.getSource() == btnSalvarDiff) {
            setSalvarArquivoDiff();
        } else if (action.getSource() == btnMesclar) {
            if (moduloInferencia) {
                setExecutaInferencia();
            } else {
                GUI_ControleModificacoes.getInstancia();
                // setMesclar();
            }
        } else if (action.getSource() == btnSalvarMerge) {
            setSalvarArquivoMerge();
        } else if (action.getSource() == miAtivarInf) {
            setAtivarAmbienteInf();
        } else if (action.getSource() == miDesativarInf) {
            setDesativarAmbienteInf();
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String lookAndFeel = null;

            if (e.getSource() == radioSkinMetal) {
                lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
                radioSkinMetal.setSelected(true);
            } else if (e.getSource() == radioSkinNimbus) {
                lookAndFeel = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
                radioSkinNimbus.setSelected(true);
            } else if (e.getSource() == radioSkinMotif) {
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                radioSkinMotif.setSelected(true);
            } else if (e.getSource() == radioSkinWindows) {
                lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                radioSkinWindows.setSelected(true);
            } else if (e.getSource() == radioSkinWindowsClassic) {
                lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";
                radioSkinWindowsClassic.setSelected(true);
            }

            try {
                UIManager.setLookAndFeel(lookAndFeel);
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception e2) {
                //e2.printStackTrace();
                JOptionPane.showMessageDialog(null, "ERRO: A versão do JDK do Java instalada na máquina\n"
                        + "não dá acesso às bibliotecas de customização Look And Feel.\n"
                        + "Por favor, atualize a versão do Java instalado na máquina.");

            }
        }
    }

    private void setDiferencas() {
        try {
            XMLDiff xml = new XMLDiff();
            txtPane3.setText(xml.diferencas(caminhoArquivo1, caminhoArquivo2));
            setIdentar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: " + e.getLocalizedMessage());
        }
    }

    private void setMesclar() {
        try {
            XMLDiff xml = new XMLDiff();
            xml.diferencas(caminhoArquivo1, caminhoArquivo2);

            // XMLs que guardam os arquivos a serem mesclados e o delta
            XMLMerge xmlOriginal = new XMLMerge(caminhoArquivo1);
            XMLMerge xmlAlterado = new XMLMerge(caminhoArquivo2);
            XMLMerge diferencas = new XMLMerge("delta.tmp");
            // System.out.println(caminhoArquivo1);
            // System.out.println(caminhoArquivo2);

            // Cria 2 arquivos iguais aos comparados, mas com as datas iniciais de atributo
            // Arquivos criados: "arq1temp.tmp" e "arq2temp.tmp"
            Date ultimaAlteracao = new Date();
            File xml_temp1 = new File("xml1_temp.tmp");
            File xml_temp2 = new File("xml2_temp.tmp");
            // System.out.println(xml_temp1);
            // System.out.println(xml_temp2);
            boolean success1 = xml_temp1.createNewFile();
            boolean success2 = xml_temp2.createNewFile();
            if (!(success1 && success2)) {
                // se o arquivo ja existir no diretorio, sera excluido e criado um novo
                xml_temp1.delete();
                xml_temp2.delete();
                xml_temp1.createNewFile();
                xml_temp2.createNewFile();
            }
            ultimaAlteracao.setTime(abrir.lastModified());
            // xmlOriginal.insereAtributos(arquivos[0].getAbsolutePath(), ultimaAlteracao);
            xmlOriginal.insereAtributos(ultimaAlteracao);
            txtPane1.setText(xmlOriginal.toString());
            this.writeFile(xml_temp1, xmlOriginal.toString());

            ultimaAlteracao.setTime(abrir.lastModified());
            xmlAlterado.insereAtributos(ultimaAlteracao);
            // xmlOriginal.insereAtributos(arquivos[1].getAbsolutePath(), ultimaAlteracao);
            this.writeFile(xml_temp2, xmlAlterado.toString());
            txtPane2.setText(xmlAlterado.toString());

            // passa o arquivo com as diferencas e os arquivos xml para o metodo mesclar
            txtPane3.setText(xmlOriginal.mesclarXML(xmlAlterado, diferencas));
        } catch (Exception e) {
            // System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            // JOptionPane.showMessageDialog(null, "ERRO: " + e.getLocalizedMessage());
        }
    }

    /***** Grava os dados nos arquivos temporarios. *****/
    public static boolean writeFile(File file, String dataString) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.print(dataString);
            out.flush();
            out.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**************************************************************/
    private void setEscolhetextPane(JTextPane textPane) {
        if (textPane == txtPane1) {
            txtPane1.setText("");
            abrir = new AbrirAction(txtPane1);
            txtPane1.setText(abrir.getTexto());
            caminhoArquivo1 = abrir.getCaminhoArquivo();
            txtCaminhoArquivo.setText(caminhoArquivo1); // passa o caminho pro jtextfield
        } else if (textPane == txtPane2) {
            txtPane2.setText("");
            abrir = new AbrirAction(txtPane2);
            txtPane2.setText(abrir.getTexto());
            caminhoArquivo2 = abrir.getCaminhoArquivo();
            txtCaminhoArquivo2.setText(caminhoArquivo2); // passa o caminho pro jtextfield
        }
    }

    private void setIdentar() {
        try {
            String texto = getTextoArquivo("delta.tmp");
            String bufferResultado = "";
            String tabulacao = "      ";
            // String tabulacao = "\t";
            char caracter;
            int contadorTabulacao = 0;

            for (int i = 0; i < texto.length(); i++) {
                caracter = texto.charAt(i);

                if (caracter == '<') {
                    if (texto.charAt(i + 1) != '/') { // exemplo de código: <xml>
                        bufferResultado = bufferResultado + caracter;
                        contadorTabulacao++;
                    } else {// exemplo de código: </xml>
                        contadorTabulacao--;
                        bufferResultado = bufferResultado + "\n";
                        for (int j = 0; j < contadorTabulacao; j++) {
                            bufferResultado = bufferResultado + tabulacao;
                        }
                        bufferResultado = bufferResultado + caracter;
                    }
                } else if (caracter == '>') { // pula de linha
                    bufferResultado = bufferResultado + caracter;

                    if ((i < texto.length() - 2) && (texto.charAt(i + 2) != '/')) {
                        bufferResultado = bufferResultado + "\n";
                        for (int j = 0; j < contadorTabulacao; j++) {
                            bufferResultado = bufferResultado + tabulacao;
                        }
                    }
                } else {
                    bufferResultado = bufferResultado + caracter;
                }
            }

            txtPane3.setText(bufferResultado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTextoArquivo(String arquivo) {
        String strRetorno = "";

        try {
            BufferedReader in = new BufferedReader(new FileReader(arquivo));
            String str;

            while ((str = in.readLine()) != null) {
                strRetorno += str;
            }
            in.close();
        } catch (IOException e) {
            return null;
        }
        return strRetorno;
    }

    /***** Salva o arquivo gerado com as diferenças entre os outros dois XMLs *****/
    private void setSalvarArquivoDiff() {
        if (!txtPane3.getText().equals("")) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(fJavaFilter);
            int resp = fileChooser.showSaveDialog(txtPane1);
            if (resp != JFileChooser.APPROVE_OPTION) {
                return;
            }
            try {
                fileChooser.setDialogTitle("Salvar");
                String arquivo1 = fileChooser.getSelectedFile().getAbsolutePath();
                PrintWriter in = new PrintWriter(arquivo1 + ".xml");
                in.println(this.txtPane3.getText());
                in.close();
                JOptionPane.showMessageDialog(null, "Arquivo foi salvo com sucesso!", "Arquivo salvo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, "ERRO!" + erro.toString());
            }
        } else { // Se não tiver texto no textPane 3, então um aviso serÃ¡ mostrado
            JOptionPane.showMessageDialog(this, "Nenhuma alteração foi realizada.");
        }
    }

    /***** Salva o arquivo mesclado *****/
    private void setSalvarArquivoMerge() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fJavaFilter);
        int resp = fileChooser.showSaveDialog(txtPane1);

        if (resp != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            fileChooser.setDialogTitle("Salvar");
            String arquivo1 = fileChooser.getSelectedFile().getAbsolutePath();
            PrintWriter in = new PrintWriter(arquivo1 + ".xml");
            in.println(this.txtPane3.getText());
            in.close();
            JOptionPane.showMessageDialog(null, "Arquivo foi salvo com sucesso!", "Arquivo salvo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "ERRO!" + erro.toString());
        }
    }

    private void setSobre() {
        try {
            @SuppressWarnings("unused")
            GUI_Sobre janelaSobre = new GUI_Sobre();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        // getContentPane();
    }

    private void setSobreFerramenta() {
        try {
            @SuppressWarnings("unused")
            GUI_SobreXPerseus janelaSobre = new GUI_SobreXPerseus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        // getContentPane();
    }

    private void setSobreGetComp() {
        try {
            @SuppressWarnings("unused")
            GUI_SobreGet janelaSobre = new GUI_SobreGet();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        // getContentPane();
    }

    protected static int getAlturaMonitor() {
        return alturaMonitor;
    }

    protected static int getLarguraMonitor() {
        return larguraMonitor;
    }

    private void setListarLookAndFeel() {
        UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo look : looks) {
            System.out.println(look.getClassName());
        }
    }

    /**
     * Configura o ambiente do XPerseus para realizar inferência de informações utilizando Prolog
     * através de duas versões de um documento XML. Os documentos são carregados nas duas áreas superiores e o resultado
     * da tradução para fatos Prolog é exibida na área inferior.
     */
    // @TODO rever botao de salvar fatos ou resultados
    private void setAtivarAmbienteInf() {
        moduloInferencia = true;
        btnDiferencas.setText("Traduzir");
        btnMesclar.setText("Selecionar regras");
        btnMesclar.setEnabled(false);
        btnSalvarDiff.setText("Salvar resultados");
        //btnNovo.setVisible(false);
        btnSalvarMerge.setVisible(false);
        txtPane1.setText("");
        txtPane1.setToolTipText("Selecione a versão base do documento XML visado para realizar a inferência de informações.");
        txtPane2.setText("");
        txtPane2.setToolTipText("Selecione a versão modificada do documento XML visado para realizar a inferência de informações.");
        txtPane3.setText("");
        txtCaminhoArquivo.setText("");
        txtCaminhoArquivo2.setText("");
        txtPane1.setEditable(false);
        txtPane2.setEditable(false);
        txtPane3.setEditable(false);
    }

    /**
     * Configura o ambiente do XPerseus de volta ao seu estado normal, desabilitando as opções de inferência de informações.
     */
    private void setDesativarAmbienteInf() {
        moduloInferencia = false;
        btnDiferencas.setText("Diferenças");
        btnMesclar.setText("Mesclar");
        btnMesclar.setEnabled(true);
        btnSalvarDiff.setText("Salvar Diferenças");
        //btnSalvarDiff.setVisible(true);
        //btnNovo.setVisible(true);
        btnSalvarMerge.setVisible(true);
        txtPane1.setText("");
        txtPane1.setToolTipText("");
        txtPane2.setText("");
        txtPane2.setToolTipText("");
        txtPane3.setText("");
        txtCaminhoArquivo.setText("");
        txtCaminhoArquivo2.setText("");
        txtPane1.setEditable(true);
        txtPane2.setEditable(true);
        txtPane3.setEditable(true);
    }

    /**
     * Função para testar se os dois arquivos foram carregados corretamente e se sim, executa a tradução dos documentos XML em fatos Prolog utilizando
     * a biblioteca de Diego Mury Lima.
     */
    private void setTraduzir() {
        //Se os dois arquivos foram carregados corretamente, pode continuar
        if (!txtPane1.getText().isEmpty() && !txtPane2.getText().isEmpty()) {
            //Se os documentos são diferentes, pode continuar
            if (caminhoArquivo1.equalsIgnoreCase(caminhoArquivo2)) {
                JOptionPane.showMessageDialog(this, "Você definiu mesmo documento para ser a versão base e a modificada, "
                        + "a inferência de informações pode não ser bem-sucedida.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            Inferencia inferencia = new Inferencia();
            //realiza a tradução em fatos
            String fatos[] = inferencia.setTraduzFatos(new File(caminhoArquivo1), new File(caminhoArquivo2));
            //Corrige os fatos traduzidos
            String fatosNormalizados = inferencia.setNormalizaFatos(fatos[0], fatos[1]);
            //habilita a opção de realizar inferência
            btnMesclar.setEnabled(true);
            //exibe na área inferior os fatos traduzidos e já normalizados
            txtPane3.setText(fatosNormalizados);
        } else {
            JOptionPane.showMessageDialog(this, "É preciso definir as versões do documento XML para "
                    + "realizar a inferência de informações.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Função para executar a inferência de informações utilizando Prolog, em base nos fatos disponibilizados pela etapa anterior.
     */
    private void setExecutaInferencia() {
        Inferencia inferencia = new Inferencia();
        GUI_Regras GUIregras = new GUI_Regras(inferencia);
        //se as regras foram selecionadas corretamente, pode realizar a inferência
        if (GUIregras.fechadoCorretamente) {
            String resultadoInferencia = inferencia.setExecutaProlog(txtPane3.getText());
            txtPane3.setText(resultadoInferencia);
        }
    }

    public static void main(String args[]) {
        XPerseus = new InterfacePrincipal();
    }
}
