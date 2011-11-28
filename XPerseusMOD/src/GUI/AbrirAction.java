package GUI;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

/**
 * @author versão Original: Rafael Barros
 * @author versão Micenas: Brian Mazini Siervi ~> http:www.briansiervi.com
 */

public class AbrirAction{

	private String textoInserido;
	private String nomeCaminho;

	public AbrirAction(JTextPane textPane){
		JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File("."));
		jfc.setFileFilter(new FiltroJFileChooser());

		int resposta = jfc.showOpenDialog(textPane);
		if(resposta == JFileChooser.APPROVE_OPTION){
			setCaminhoArquivo(jfc.getSelectedFile().getAbsolutePath().toString());
			openFile(jfc.getSelectedFile());
		}
	}

	private void openFile(File f){
		try{
			FileReader rd = new FileReader(f);
			int i = rd.read();
			String ret="";
			while(i!=-1){
				ret = ret+(char)i;
				i = rd.read();
			}
			setTexto(ret);

		}catch(IOException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void setTexto(String textoInserido){
		this.textoInserido = textoInserido;
	}

	public String getTexto(){
		return textoInserido;
	}

	protected void setCaminhoArquivo(String nomeCaminho){
		this.nomeCaminho = nomeCaminho;
	}

	protected String getCaminhoArquivo(){
		return nomeCaminho;
	}

	public long lastModified() {
		// TODO Auto-generated method stub
		return 0;
	}
}
