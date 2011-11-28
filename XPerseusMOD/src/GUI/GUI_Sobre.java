package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Rafael Barros
 *
 */
public class GUI_Sobre {

	public GUI_Sobre(){
		JFrame frameSobre = new JFrame("Informações do Aluno");

		JPanel painelCima = new JPanel(new GridLayout(2,1));
		JPanel painelMeio = new JPanel(new FlowLayout());
		painelMeio.setBackground(Color.BLACK);
		JPanel painelBaixo = new JPanel(new FlowLayout());
		painelMeio.setBackground(Color.BLACK);

		/*Texto do titulo*/
		JTextField textAreaTitulo;
		textAreaTitulo = new JTextField(5);
		textAreaTitulo.setEditable(false);
		textAreaTitulo.setBackground(Color.DARK_GRAY);
		textAreaTitulo.setForeground(Color.WHITE);
		textAreaTitulo.setFont(new Font("Courier", Font.PLAIN, 18));
		textAreaTitulo.setText("               :: Dados do Aluno ::");

		/*Texto dos dados do aluno*/
		JTextArea textAreaFuncionalidades;
		textAreaFuncionalidades = new JTextArea(2, 20);
		textAreaFuncionalidades.setEditable(false);
		textAreaFuncionalidades.setBackground(Color.DARK_GRAY);
		textAreaFuncionalidades.setForeground(Color.WHITE);
		textAreaFuncionalidades.setFont(new Font("Courier", Font.PLAIN, 14));
		textAreaFuncionalidades.setText("                                                                   \n" +
				" ::: Nome:      Rafael Barros Silva \n" +
				" ::: Matrícula: 200622021 \n" +
				" ::: Email:     rafaelbarros84ufjf@gmail.com \n" );

		JLabel label5 = new JLabel();
		JLabel label6 = new JLabel();
		JLabel label7 = new JLabel();

		label5.setIcon(new ImageIcon(getClass().getResource("/imagens/computacao.PNG")));
		label6.setIcon(new ImageIcon(getClass().getResource("/imagens/get.png")));
		label7.setIcon(new ImageIcon(getClass().getResource("/imagens/ufjf.PNG")));

		painelCima.add(textAreaTitulo);
		painelMeio.add(textAreaFuncionalidades);
		painelBaixo.add(label5);
		painelBaixo.add(label6);
		painelBaixo.add(label7);

		frameSobre.add(painelCima, BorderLayout.NORTH);
		frameSobre.add(painelMeio, BorderLayout.CENTER);
		frameSobre.add(painelBaixo, BorderLayout.SOUTH);
		frameSobre.setSize(550,275);

		/*Pegando a resolucao do monitor*/
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension dim = tk.getScreenSize();
	    int alturaMonitor= (int)dim.getHeight();
	    int larguraMonitor= (int) dim.getWidth();

	    frameSobre.setLocation((larguraMonitor - 500) / 2, (alturaMonitor - 700) / 2);
		frameSobre.setVisible(true);
		frameSobre.setResizable(false);
	}
}