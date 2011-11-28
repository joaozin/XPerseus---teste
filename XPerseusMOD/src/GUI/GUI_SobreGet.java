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
public class GUI_SobreGet {

	public GUI_SobreGet(){
		JFrame frameSobre = new JFrame("Informações do GETComp");

		JPanel painelCima = new JPanel(new GridLayout(2,1));
		JPanel painelMeio = new JPanel(new FlowLayout());
		painelMeio.setBackground(Color.BLACK);
		JPanel painelBaixo = new JPanel(new FlowLayout());
		painelMeio.setBackground(Color.BLACK);

		/*Texto do titulo do GETComp*/
		JTextField textAreaTitulo;
		textAreaTitulo = new JTextField(5);
		textAreaTitulo.setEditable(false);
		textAreaTitulo.setBackground(Color.DARK_GRAY);
		textAreaTitulo.setForeground(Color.WHITE);
		textAreaTitulo.setFont(new Font("Courier", Font.PLAIN, 16));
		textAreaTitulo.setText(" :: GETComp - Grupo de Educação Tutorial da Computação ::");

		/*Texto da descricao do GETComp*/
		JTextArea textAreaGetComp;
		textAreaGetComp = new JTextArea(2, 20);
		textAreaGetComp.setEditable(false);
		textAreaGetComp.setBackground(Color.DARK_GRAY);
		textAreaGetComp.setForeground(Color.WHITE);
		textAreaGetComp.setFont(new Font("Courier", Font.PLAIN, 12));
		textAreaGetComp.setText("  ::: O GETComp (Grupo de Educação Tutorial da Computação) é composto por grupos \n" +
				" tutoriais interdisciplinares de aprendizagem. O grupo tem por objetivo primário  \n" +
				" propiciar  aos alunos, sob  orientação de um  professor tutor, condições para a  \n" +
				" realização de atividades de ensino, pesquisa e extensão nas diversas  vertentes  \n" +
				" da ciência da computação. \n" +
				"      Estas atividades visam ampliar e/ou aprofundar os objetivos e os conteúdos  \n" +
				" programáticos  que  integram  a grade curricular na qual o grupo está inserido.  \n" +
				"      Através da ação efetiva de seu tutor, professores colaboradores, bolsistas  \n" +
				" e  voluntários, o  grupo  pretende  melhorar  o ensino  e as práticas do curso,  \n" +
				" trazendo palestras, discussões e seminários de temas atuais voltados tanto para  \n" +
				" o meio acadêmico quanto para o mercado. \n\n");

		/*Texto da descricao do GETComp Gerencia de Conf de Software*/
		JTextArea textAreaGetGCS;
		textAreaGetGCS = new JTextArea(2, 20);
		textAreaGetGCS.setEditable(false);
		textAreaGetGCS.setBackground(Color.DARK_GRAY);
		textAreaGetGCS.setForeground(Color.WHITE);
		textAreaGetGCS.setFont(new Font("Courier", Font.PLAIN, 12));
		textAreaGetGCS.setText(" ::: GETComp - Gerência de Configuração de Software \n\n" +
				"     O objetivo do grupo é explorar o tema Gerência de Configuração que é a  \n" +
				" parte da engenharia de software responsável pelo controle da evolução de  \n" +
				" software. A Gerência de Configuração de Software (GCS) é uma disciplina que aplica  \n" +
				" procedimentos técnicos e administrativos para identificar  e  documentar as  \n" +
				" características físicas e funcionais  de um  item de configuração, controlar as  \n" +
				" alterações  nessas  características, armazenar  e  relatar  o processamento das  \n" +
				" modificações e o estágio da implementação e verificar a compatibilidade  com os  \n" +
				" requisitos especificados. \n");

		/*Texto dos participantes do GETComp GCS*/
		JTextArea textAreaParticipantes;
		textAreaParticipantes = new JTextArea(2, 20);
		textAreaParticipantes.setEditable(false);
		textAreaParticipantes.setBackground(Color.DARK_GRAY);
		textAreaParticipantes.setForeground(Color.WHITE);
		textAreaParticipantes.setFont(new Font("Courier", Font.PLAIN, 12));
	    textAreaParticipantes.setText("     Participantes: \n" +
	    		" ::: Carolina de Oliveira Cunha \n" +
	    		" ::: Daniel Tannure Menandro de Freitas \n" +
	    		" ::: Guilherme Gomes Martins \n" +
	    		" ::: Leonardo Chinelate Costa \n" +
	    		" ::: Pedro Otavio Lima Gazzola \n" +
	    		" ::: Plinio Antunes Garcia \n" +
	    		" ::: Rafael Barros Silva                                                          ");
	    JLabel label5 = new JLabel();
		JLabel label6 = new JLabel();
		JLabel label7 = new JLabel();

		label5.setIcon(new ImageIcon(getClass().getResource("/imagens/computacao.PNG")));
		label6.setIcon(new ImageIcon(getClass().getResource("/imagens/get.png")));
		label7.setIcon(new ImageIcon(getClass().getResource("/imagens/ufjf.PNG")));

		painelCima.add(textAreaTitulo);
		painelMeio.add(textAreaGetComp);
		painelMeio.add(textAreaGetGCS);
		painelMeio.add(textAreaParticipantes);
		painelBaixo.add(label5);
		painelBaixo.add(label6);
		painelBaixo.add(label7);

		frameSobre.add(painelCima, BorderLayout.NORTH);
		frameSobre.add(painelMeio, BorderLayout.CENTER);
		frameSobre.add(painelBaixo, BorderLayout.SOUTH);
		frameSobre.setSize(585,720);

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