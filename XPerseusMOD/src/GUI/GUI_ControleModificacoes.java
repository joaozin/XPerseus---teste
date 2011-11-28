package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI_ControleModificacoes extends JFrame implements ActionListener{

	private static final long serialVersionUID= 1L;
	private JButton btnSetaParaCima, btnSetaParaBaixo, btnSetaParaEsquerda, btnSetaParaDireita, btnDesfazer, btnConcluir, btnAjuda;
	private JTextArea txtAreaLinha;
	private JPanel pnlCentral,pnlSuperior,pnlInferior,pnlTodo;
	private JPanel pnlCentralEsquerdo, pnlCentralMeio, pnlCentralDireito;
	private JLabel lblIrParaConflito;
	private final String imagemAjuda = "/imagens/iconeAjuda.png";
	private static GUI_ControleModificacoes singletonControle;
	protected static GUI_AjudaModificacoes singletonAjuda;

	/**
	 * Construtor default da GUI_ControleModificacoes.
	 * Inicializa toda a parte gráfica da janela de modificações.
	 */
	private GUI_ControleModificacoes(){
		// parâmetros: redimensionável, botao de fechar, maximizar, minimizar para bandeja
		super("Controle de mesclagem");
		pnlTodo = new JPanel(new BorderLayout());
		Font fonte = new Font("Arial",1,16);

		lblIrParaConflito = new JLabel("Ir para conflito:");

		txtAreaLinha = new JTextArea("1");
		txtAreaLinha.setFont(fonte);
		txtAreaLinha.setPreferredSize(new Dimension(100,30));

		btnSetaParaCima = new JButton("/\\");
		btnSetaParaBaixo = new JButton("\\/");
		btnSetaParaEsquerda = new JButton("<");
		btnSetaParaDireita = new JButton(">");
		btnDesfazer = new JButton("Desfazer");
		btnConcluir = new JButton("Concluir Mesclagem");

		btnAjuda = new JButton();
		btnAjuda.addActionListener(this);
		btnAjuda.setIcon(new ImageIcon(getClass().getResource(imagemAjuda)));
		btnAjuda.setPreferredSize(new Dimension(28,28));

		/***** Painel Superior *****/
		pnlSuperior = new JPanel(new FlowLayout());
		pnlSuperior.add(lblIrParaConflito);
		pnlSuperior.add(txtAreaLinha);
		pnlSuperior.add(new JLabel("            "));
		pnlSuperior.add(btnAjuda);

		/***** Painel Central *****/
		pnlCentralMeio = new JPanel(new GridLayout(3,3));

		pnlCentralMeio.add(new JLabel(""));
		pnlCentralMeio.add(btnSetaParaCima);
		pnlCentralMeio.add(new JLabel(""));

		pnlCentralMeio.add(btnSetaParaEsquerda);
		pnlCentralMeio.add(new JLabel(""));
		pnlCentralMeio.add(btnSetaParaDireita);

		pnlCentralMeio.add(new JLabel(""));
		pnlCentralMeio.add(btnSetaParaBaixo);
		pnlCentralMeio.add(new JLabel(""));

		pnlCentralEsquerdo= new JPanel(new FlowLayout());
		pnlCentralEsquerdo.add(new JLabel("           "));

		pnlCentralDireito= new JPanel(new FlowLayout());
		pnlCentralDireito.add(new JLabel("           "));

		pnlCentral = new JPanel(new BorderLayout());
		pnlCentral.add(pnlCentralEsquerdo,BorderLayout.WEST);
		pnlCentral.add(pnlCentralMeio,BorderLayout.CENTER);
		pnlCentral.add(pnlCentralDireito,BorderLayout.EAST);

		/***** Painel Inferior *****/
		pnlInferior = new JPanel(new FlowLayout());
		pnlInferior.add(btnDesfazer);
		pnlInferior.add(btnConcluir);

		pnlTodo.add(pnlSuperior,BorderLayout.NORTH);
		pnlTodo.add(pnlCentral,BorderLayout.CENTER);
		pnlTodo.add(pnlInferior,BorderLayout.SOUTH);

		getContentPane().add(pnlTodo,"Center");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setAlwaysOnTop(true);
		pack();
		setLocation((InterfacePrincipal.getLarguraMonitor() - getContentPane().getWidth())/2 ,100);
		setVisible(true);

		/**
		 * Método alternativo criado para destruir o singleton antes de fechar a janela de controle de modificações
		 */
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent evt) {
		        singletonControle = null;
		        dispose(); //pede para a janela fechar
		    }
		});
	}

	/**
	 * Método que escuta o botão de ajuda da janela de modificações
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAjuda){
			getInstanciaAjuda();
		}
	}

	/**
	 * Singleton da GUI_AjudaModificacoes
	 * @return singletonAjuda
	 */
	private static GUI_AjudaModificacoes getInstanciaAjuda() {
		if(singletonAjuda == null){
			singletonAjuda = new GUI_AjudaModificacoes();
		}
		return singletonAjuda;
	}

	/**
	 * Singleton do GUI_ControleModificacoes
	 * @return singletonControle
	 */
	public static GUI_ControleModificacoes getInstancia() {
		if(singletonControle == null){
			singletonControle = new GUI_ControleModificacoes();
		}
		return singletonControle;
	}

	/**
	public static void main(String args[]) {
		GUI_ControleModificacoes.getInstancia();
	}
	**/
}