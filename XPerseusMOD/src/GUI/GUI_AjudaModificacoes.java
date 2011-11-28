package GUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Brian Mazini Siervi
 */
public class GUI_AjudaModificacoes extends JFrame{
	private static final long serialVersionUID = 1L;
	private JFrame janelaAjuda;
	private JLabel label;

	/**
	 * Construtor default da GUI_AjudaModificacoes.
	 * Gera a janela de ajuda do controle de modificações.
	 */
	public GUI_AjudaModificacoes(){
		janelaAjuda = new JFrame();
		janelaAjuda.setTitle("Ajuda");

		JPanel painel = new JPanel();
		//painel.add(new JLabel(""));

		// Adicionando o fundo ao painel
		label = new JLabel("");
		label.setIcon(new ImageIcon(getClass().getResource("/imagens/imagemAjuda.png")));
		painel.add(label);

		janelaAjuda.add(painel);
		janelaAjuda.setSize(980,500);
		janelaAjuda.setLocation((InterfacePrincipal.getLarguraMonitor() - janelaAjuda.getWidth())/2 ,100);
		janelaAjuda.setResizable(false);
		janelaAjuda.setDefaultCloseOperation(1);
		//janelaAjuda.setEnabled(false);
		//janelaAjuda.setFocusable(true);
		janelaAjuda.setAlwaysOnTop(true);
		janelaAjuda.setVisible(true);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		/**
		 * Método alternativo criado para destruir o singleton antes de fechar a janela de controle de modificações
		 */
		janelaAjuda.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent evt) {
		        GUI_ControleModificacoes.singletonAjuda = null;
		        dispose(); //pede para a janela fechar
		    }
		});
	}
}
