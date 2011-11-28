package GUI;

import Inference.Inferencia;
import Inference.Regras;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

/**
 *
 * @autor Pedro Gazzola
 */
public class GUI_Regras extends JDialog implements ActionListener {

    private Inferencia moduloInferencia;
    private JButton btnAbrirRegras, btnConcluir, btnIdentificarRegras, btnMarcarTodos;
    private JPanel pnlTodo, pnlSuperior, pnlCentral, pnlInferior;
    private JTextPane txtPane1;
    public boolean fechadoCorretamente = false;
    private ArrayList<JCheckBox> regrasSelecionadas;

    public GUI_Regras(Inferencia moduloInferencia) {
        //super("Regras Prolog para a inferência");
        setModal(true);

        this.moduloInferencia = moduloInferencia;

        pnlTodo = new JPanel(new BorderLayout());
        Font fonte = new Font("Arial", 1, 16);

        btnAbrirRegras = new JButton("Carregar regras");
        btnAbrirRegras.addActionListener(this);
        btnConcluir = new JButton("Concluir");
        btnConcluir.addActionListener(this);
        btnConcluir.setVisible(false);
        btnIdentificarRegras = new JButton("Identificar regras");
        btnIdentificarRegras.addActionListener(this);

        txtPane1 = new JTextPane();
        txtPane1.setText("");
        txtPane1.setToolTipText("Crie ou selecione um arquivo que contenha as regras Prolog do contexto desejado.");
        txtPane1.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPane1.setPreferredSize(new Dimension(350, 250));

        pnlInferior = new JPanel(new FlowLayout());
        pnlSuperior = new JPanel(new FlowLayout());
        pnlCentral = new JPanel(new FlowLayout());

        pnlSuperior.add(btnAbrirRegras);

        pnlCentral.add(txtPane1);

        pnlInferior.add(btnConcluir);
        pnlInferior.add(btnIdentificarRegras);

        pnlTodo.add(pnlSuperior, BorderLayout.NORTH);
        pnlTodo.add(pnlCentral, BorderLayout.CENTER);
        pnlTodo.add(pnlInferior, BorderLayout.SOUTH);

        getContentPane().add(pnlTodo, "Center");
        setResizable(false);
        setAlwaysOnTop(true);
        pack();
        setLocation((InterfacePrincipal.getLarguraMonitor() - getContentPane().getWidth()) / 2, 100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnConcluir) {
            if (!txtPane1.getText().isEmpty()) {
                ArrayList<String> regrasFiltradas = new ArrayList<String>();
                int contador = 0;
                for (JCheckBox item : regrasSelecionadas) {
                    if (item.isSelected()) {
                        contador++;
                        regrasFiltradas.add(item.getName());
                    }
                }
                if (contador > 0) {
                    this.moduloInferencia.setRegras(txtPane1.getText());
                    this.moduloInferencia.setRegrasSelecionadas(regrasFiltradas);
                    this.fechadoCorretamente = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "É preciso definir as regras para "
                            + "realizar a inferência de informações.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "É preciso definir as regras para "
                        + "realizar a inferência de informações.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == btnAbrirRegras) {
            txtPane1.setText("");
            AbrirAction abrir = new AbrirAction(txtPane1);
            txtPane1.setText(abrir.getTexto());
        } else if (ae.getSource() == btnIdentificarRegras) {
            if (!txtPane1.getText().isEmpty()) {
                String regras = txtPane1.getText();
                btnIdentificarRegras.setEnabled(false);
                btnAbrirRegras.setEnabled(false);
                pnlCentral.remove(txtPane1);
                Regras moduloRegras = new Regras();
                String[] regrasSeparadas = moduloRegras.getRegrasSeparadas(regras);
                setIdentificaRegras(moduloRegras.getNomeEArgumentosRegras(regrasSeparadas));
            } else {
                JOptionPane.showMessageDialog(this, "É preciso definir as regras para "
                        + "realizar a inferência de informações.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == btnMarcarTodos) {
            if(btnMarcarTodos.getText().equalsIgnoreCase("Marcar todos")){
                btnMarcarTodos.setText("Desmarcar todos");
                for (JCheckBox item : regrasSelecionadas) {
                    item.setSelected(true);
                }
            }else{
                btnMarcarTodos.setText("Marcar todos");
                for (JCheckBox item : regrasSelecionadas) {
                    item.setSelected(false);
                }
            }
        }
    }

    /**
     * Cria as caixas para a exibição das regras (nomes + argumentos)
     * @param regras
     * Recebe um vetor de String onde cada índice contém o nome da regra e os argumentos dela.
     * Ex: mesmo_salario(Nome)
     */
    private void setIdentificaRegras(String[] regras) {
        regrasSelecionadas = new ArrayList<JCheckBox>();

        pnlCentral.setLayout(new GridLayout(0, 1));

        for (String regra : regras) {
            JCheckBox chkItem = new JCheckBox(regra);
            chkItem.setName(regra);
            regrasSelecionadas.add(chkItem);
            pnlCentral.add(chkItem);
        }

        //JScrollBar barraRolagem = new JScrollBar();
        //pnlCentral.add(barraRolagem);

        btnMarcarTodos = new JButton("Marcar todos");
        btnMarcarTodos.addActionListener(this);

        pnlInferior.add(btnMarcarTodos);
        btnConcluir.setVisible(true);
    }
}
