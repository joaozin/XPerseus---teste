package GUI;

import GUI.JTablePersonalizada;
import GUI.JTablePersonalizada;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestaJTable extends JFrame {
	private static final long serialVersionUID = 1L;
	private Object[] titulos;
	private Object[][] conteudo;
	private int[] larguraColunas = {45,450};
	private JPanel painel;

	public TestaJTable(){
		super("Exemplo de uma tabela simples");

		titulos = new Object[2];
		titulos[0] = "Linha";
		titulos[1]="Versão base";

		conteudo = new Object[5][2];
		for(int i=0; i<=4; i++){
			conteudo[i][0] = i + ".";

                }

		conteudo[0][1] = "Goiânia";
		conteudo[1][1] = "São Paulo";
		conteudo[2][1] = "Rio de Janeiro";
		conteudo[3][1] = "Jussara";
		conteudo[4][1] = "Barra do Garças";

		@SuppressWarnings("unused")
		final JTablePersonalizada tabela = new JTablePersonalizada(titulos,conteudo,larguraColunas,false,false);
		painel = new JPanel(new FlowLayout());
		painel.setPreferredSize(new Dimension(getWidth(),100));

		Container c= getContentPane();
		c.setLayout(new FlowLayout());
		c.add(painel);

		/*
		JButton btn= new JButton("Número de Linhas");
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"Quantidade de Linhas: " + tabela.getModel().getRowCount(),"JTable",JOptionPane.INFORMATION_MESSAGE);
			}
		});

		c.add(btn);
		*/

		c.add(JTablePersonalizada.getScrollPane());
		setSize(500,500);
		setVisible(true);

	}

	public static void main(String args[]){
		@SuppressWarnings("unused")
		TestaJTable t = new TestaJTable();
	}
}
