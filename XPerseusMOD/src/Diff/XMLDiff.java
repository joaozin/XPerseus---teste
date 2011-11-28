package Diff;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

import fr.loria.ecoo.so6.xml.node.Document;
import fr.loria.ecoo.so6.xml.node.TreeNode;
import fr.loria.ecoo.so6.xml.util.XmlUtil;
import fr.loria.ecoo.so6.xml.xydiff.DeltaConstructor;
import fr.loria.ecoo.so6.xml.xydiff.XyDiff;

/**
 *
 * @author Rafael Barros
 *
 */
public class XMLDiff
{
	@SuppressWarnings("unused")
	private static Document document; // O DOM que carregará o XML
    private TreeNode raiz; // raiz do arquivo carregado

    /***** Cria uma nova instancia do XMLDiff *****/
    public XMLDiff(){}

    public String toString()
    {
        return raiz.toString();
    }

    /***** Preenche a propriedade 'document' com o arquivo passado *****/
    public void carregaXML(String nome)
    {
        @SuppressWarnings("unused")
		File docFile = new File(nome);

        @SuppressWarnings("unused")
		Document doc = null;
        try
        {
            raiz = XmlUtil.load(nome);
            //System.out.print(raiz.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        	//System.out.print("Problem parsing the file.");
        }
    }

    /***** Esse metodo retorna as diferencas entre os arquivos xml. Eh retornado para o textArea3 *****/
    public String diferencas(String arq1, String arq2)
    {
        try
        {
            XyDiff xydiff = new XyDiff(arq1, arq2);
            DeltaConstructor c = xydiff.diff();
            Document delta = c.getDeltaDocument();
            delta.save("delta.tmp", false);
        }
        catch (Exception e)
        {
        	getErroSemArquivosAbertos();
        }

        StringBuffer fileBuffer;
        String fileString=null;
        String line;
        File file = new File("delta.tmp");
        try
        {
            FileReader in = new FileReader(file);
            BufferedReader dis = new BufferedReader(in);
            fileBuffer = new StringBuffer() ;

            while ((line = dis.readLine()) != null)
            {
                fileBuffer.append("\n" + line + "\n");
            }

            in.close();
            fileString = fileBuffer.toString();
        }
        catch  (IOException e )
        {
            return null;
        }
        return fileString;
    }


    public static void getErroSemArquivosAbertos(){

    	//Limpa o arquivo delta.tmp
    	FileOutputStream fileOut;
        try{
            fileOut= new FileOutputStream("delta.tmp");
            fileOut.flush();
			fileOut.close();
        }catch(FileNotFoundException e1){
            e1.printStackTrace();
        }catch(IOException e2){
            e2.printStackTrace();
        }

    	//System.out.print(e.getMessage());
    	JOptionPane.showMessageDialog(null,"Você se esqueceu de escolher os arquivos antes" +
    										"\nde tentar mostrar suas diferenças. Por favor," +
    										"\ntente novamente.","Erro",JOptionPane.ERROR_MESSAGE);


    }
}