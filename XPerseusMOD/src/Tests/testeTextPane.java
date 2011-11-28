package Tests;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

/**
 *
 * @autor Pedro Gazzola
 */
public class testeTextPane extends JFrame {

    private Container conteiner;
    private static int alturaMonitor;
    private static int larguraMonitor;
    private JTextPane txtPane;
    private JTextArea txtArea;

    public testeTextPane() {
        super("XPerseus - Micenas: Controle de Versoes de Dados Semi - Estruturados");

        conteiner = getContentPane();
        conteiner.setLayout(new BorderLayout());

        /***************************** Pegando a resolucao do monitor ******************************/
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        alturaMonitor = (int) dim.getHeight();
        larguraMonitor = (int) dim.getWidth();

        txtPane = new JTextPane();
        txtPane.setText("");
        txtPane.setFont(new Font("Arial", Font.PLAIN, 16));
        txtPane.setPreferredSize(new Dimension(700, 500));
        JScrollPane sPane1= new JScrollPane(txtPane);

        txtArea = new JTextArea();
        txtArea.setPreferredSize(new Dimension(200,200));

        //conteiner.add(txtArea);
        conteiner.add(txtPane);

        setSize(larguraMonitor/2, alturaMonitor/2 - 40);
        setLocation(0, 0);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String args[]) {
        testeTextPane XPerseus = new testeTextPane();
    }
}
