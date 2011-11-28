package Merge;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Rafael Barros
 *
 */

public class XMLMerge
{
    public Document document;
    private Element raiz;
    private Date ultimaModificacao;

    /** Creates a new instance of DOMManager */
    public XMLMerge()
    {
    }

    public XMLMerge(String caminho)
    {
        this.loadXML(caminho);
    }

    /*public String toString()
    {
        return raiz.toString();
    }*/

    /*public void carregaXML(String caminho)
    {
        try
        {

        	//fazer o parse do arquivo e criar o documento XML
    		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    		DocumentBuilder db = dbf.newDocumentBuilder();
    		Document doc = db.parse(caminho);
    		//System.out.println(doc);
    		//pegar o elemento raiz
    		Element raiz = doc.getDocumentElement();
    		System.out.println("O elemento raiz é: " + raiz.getNodeName());


    	}
        catch (ParserConfigurationException parserException)
        {
            //parserException.printStackTrace();
            System.out.println(parserException.getLocalizedMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getLocalizedMessage());
        }
    } */

    /***** retorna ou um Document ou uma String se acontecer algum erro *****/
    private Object loadXML( String src ) {
      File xmlFile = new File( src ) ;
      System.out.println("src");
      System.out.println(src);
      String err = null ;
      try {
       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //dbf.setValidating( validate );
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse( xmlFile );
        //pegar o elemento raiz
		@SuppressWarnings("unused")
		Element raiz = doc.getDocumentElement();
        return doc ;
      }catch(ParserConfigurationException pce){
          err = pce.toString();
      }catch(SAXParseException spe ){
          StringBuffer sb = new StringBuffer( spe.toString() );
          sb.append("\n  Line number: " + spe.getLineNumber());
          sb.append("\nColumn number: " + spe.getColumnNumber() );
          sb.append("\n Public ID: " + spe.getPublicId() );
          sb.append("\n System ID: " + spe.getSystemId() + "\n");
          err = sb.toString();
      }catch( SAXException se ){
         err = se.toString();
         if( se.getException() != null ){
            err += " caused by: " + se.getException().toString() ;
         }
      }catch( IOException ie ){
         err = ie.toString();
      }
      return err ;
    } // end loadXML


    /***** Retorna o nodo filho (de 'pai') de indice passado *****/
    public Node getChild(Node pai, int posicao)
    {
        NodeList nodos = pai.getChildNodes();
        return nodos.item(posicao);
    }

    /***** Recebe um caminho no formato 0:0:3:1 e retorna um array com estes números ******/
    private static int[] convertPath(String path)
    {
        String[] split = path.split(":");
        int[] result = new int[split.length];

        for (int i = 0; i < result.length; i++)
            result[i] = Integer.parseInt(split[i]);

        return result;
    }

    /***** Recebe um caminho (e.g. 0:0:1:3) e retorna o nodo que corresponde a ele *****/
    public Node getNode(String caminho)
    {
        // Transforma o caminho em inteiros
        @SuppressWarnings("static-access")
		int[] intCaminho = this.convertPath(caminho);
        Node nodoAux = raiz;

        try
        {
            for (int i=2; i<intCaminho.length; i++)
            {
                nodoAux = this.getChild(nodoAux, intCaminho[i]);
            }
        }
        catch (Exception e)
        {
            System.out.println("EXCESSAO" + e.getMessage());
        }
        return nodoAux;
    }


    /***** Insere atributos em todos os nodos do xml do arquivo passado. O atributo inserido é a data passada *****/
    public void insereAtributos(Date data)
    {
        @SuppressWarnings("unused")
		Node nodo;
        this.ultimaModificacao = data;
        try
        {
            // Insere os atributos em cada nodo da árvore
            Element e = document.getDocumentElement();
            System.out.println(e);
            this.insereAtributos(e, data);
        }
        catch (Exception e)
        {
            System.out.println("M�todo insereAtributos - ERRO: " + e.getMessage());
        }
    }

    /***** Insere atributos de data inicial e final no nodo passado e em todos os seus filhos *****/
    private void insereAtributos(Element nodo, Date data)
    {
        // Adiciona os atributos no nodo
        Attr inicio = document.createAttribute("data_inicio");
        Attr fim = document.createAttribute("data_fim");
        inicio.setValue(this.getData(data));
        fim.setValue("");
        nodo.setAttributeNode(inicio);
        nodo.setAttributeNode(fim);

        NodeList filhos = nodo.getChildNodes();
        Node nodoAux;

        // Adiciona os atributos nos filhos do nodo
        for (int i=0; i < filhos.getLength(); i++)
        {
            nodoAux = filhos.item(i);
            if (nodoAux.getNodeType() == Node.ELEMENT_NODE)
            {
                Element filho = (Element)nodoAux;
                insereAtributos(filho, data);
            }
        }
    }

    /***** junta esta instância com a instância passada usando como base o delta passado retorna uma string que é o arquivo resultante *****/
    public String mesclarXML(XMLMerge novaVersao, XMLMerge diferencas)
    {
        // Obtem uma lista das alterações a serem feitas
        Element raizDiferencas = diferencas.document.getDocumentElement();
        System.out.println(raizDiferencas);
        Node aux;
        String mesclados; // o resultado da função
        NodeList filhos; // filhos de algum nodo sendo analisado

        // Obtem uma lista de todas as operações de deleção de nodos
        filhos = raizDiferencas.getChildNodes();
        for (int i=0; i<filhos.getLength(); i++)
        {
            aux = filhos.item(i);
            // Se for um elemento com conteúdo dentro (o xdiff gera elementos vazios para operações de move)
            if ((aux.getNodeType() == Node.ELEMENT_NODE) && (this.temNodosFilhos(aux)))
                if (aux.getNodeName().indexOf("Deleted") > -1) // � "Deleted" ou "AttributeDeleted"
                {
                    // Para cada nodo de deleção coloca no xml original a data de termino
                    this.insereDataFim((Element)aux, this.getData(novaVersao.ultimaModificacao));
                }
                else if (aux.getNodeName().indexOf("Inserted") > -1) // é "Inserted" ou "AttributeInserted"
                {
                    // Para cada nodo de inserçãao, coloca no xml original o nodo e data
                    // inicial nova.
                    this.insereDataInicio(this.raiz, (Element)aux, this.getData(novaVersao.ultimaModificacao));
                }
        }
        aux = this.document.getDocumentElement();
        System.out.println(aux);
        mesclados = aux.toString();
        System.out.println(mesclados);

        // Retorna o xml criado
        this.writeFile("XML_mesclado.xml", mesclados);
        return mesclados;
    }

    /***** Insere no nodo passado e nos seus filhos a data final ******/
    private void insereDataFim(Element delecao, String data)
    {
        Node deletado;
        Element nodoAux;
        // cada nodo da lista de diferenças eh um elemento
        if (delecao.getNodeType() == Node.ELEMENT_NODE)
        {
            nodoAux = (Element)delecao;
            deletado = this.getNode(nodoAux.getAttribute("pos"));
            // testa se é uma deleção de nodo ou de atributo
            if (delecao.getNodeName() == "Deleted")
            {
                // O nodo deletado é um texto - muda o atributo do seu pai
                if (deletado.getNodeType() == Node.TEXT_NODE)
                {
                    nodoAux = (Element)deletado.getParentNode();
                    nodoAux.setAttribute("data_fim", data);
                }
                // se foi deletado um nodo, muda a data dele e dos seus filhos
                else if (deletado.getNodeType() == Node.ELEMENT_NODE)
                {
                    nodoAux = (Element)deletado;
                    this.setAtributosFilhos(nodoAux, data);
                }
            }
            else if (delecao.getNodeName() == "AttributeDeleted")
            {
                nodoAux = (Element)deletado.getParentNode();
                nodoAux.setAttribute("data_fim", data);
            }
        }
    }

    /***** Recebe um elemento e insere o atributo passado nele e em todos os seus filhos *****/
    private void setAtributo(Element elem, String nomeAtt, String valorAtt)
    {
        elem.setAttribute(nomeAtt, valorAtt);
        NodeList filhos = elem.getChildNodes();
        for (int i=0; i < filhos.getLength(); i++)
        {
            if (filhos.item(i).getNodeType() == Node.ELEMENT_NODE)
            {
                this.setAtributo((Element)filhos.item(i), nomeAtt, valorAtt);
            }
        }
    }

    /***** Insere o nodo passado 'inserção' e seus filhos na árvore 'raiz', e já coloca nele a data inicial 'data' *****/
    private void insereDataInicio(Node raiz, Element insercao, String data)
    {

            Element elemInserir;
            Node nodoAux;
            String caminho;

            // testa se é uma inserção de (nodo ou texto) ou de atributo
            if (insercao.getNodeName() == "Inserted")
            {
                // obtém o nodo a ser inserido e seus filhos
                nodoAux = insercao.getFirstChild();
                if (nodoAux.getNodeType() != Node.TEXT_NODE)
                    elemInserir = (Element)nodoAux.cloneNode(true);
                else // cria uma cópia do pai do nodo texto
                {
                    nodoAux = nodoAux.getParentNode();
                    Element elemAux = (Element)nodoAux;
                    caminho = elemAux.getAttribute("pos");
                    caminho = caminho.substring(0, caminho.length()-2);
                    nodoAux = this.getNode(caminho);
                    elemInserir = (Element)nodoAux.cloneNode(false);
                    elemInserir.appendChild(this.copiaNodo(insercao.getFirstChild()));
                }
                elemInserir.setAttribute("data_inicio", data);
                this.setAtributo(elemInserir, "data_inicio", data);
                this.setAtributo(elemInserir, "data_fim", "");

                // obtém a posição onde o nodo deve ser inserido
                caminho = insercao.getAttribute("pos");

                caminho = caminho.substring(0, caminho.length()-2);

                // verifica se esta inserindo no primeiro nível ou não
                if (caminho.compareTo("0:0") != 0) // não está
                {
                    // verifica se o caminho é válido
                    if (caminho.length() > 2)
                    {
                        nodoAux = this.getNode(caminho).getParentNode();
                        nodoAux.appendChild((Node)elemInserir);
                    }
                }
                else
                {
                    nodoAux = this.copiaNodo(elemInserir);
                    raiz.appendChild(nodoAux);
                }
            }
    }

    /***** Cria uma cópia do nodo e de todos os seus filhos *****/
    private Node copiaNodo(Node original)
    {
        Node copiaText, nodoAux;
        Element copiaElem;
        Node copiaFeita;

        if (original.getNodeType() == Node.TEXT_NODE)
        {
            copiaText = document.createTextNode(original.getNodeValue());
            copiaFeita = copiaText;
        }
        else // extender para comentário depois
        {
            copiaElem = document.createElement(original.getNodeName());
            // copia os atributos
            if (original.hasAttributes())
            {
                NamedNodeMap atributos = original.getAttributes();
                for (int i =0; i < atributos.getLength(); i++)
                {
                    nodoAux = atributos.item(i);
                    copiaElem.setAttribute(nodoAux.getNodeName(), nodoAux.getNodeValue());
                }
            }
            // copia os filhos
            if (original.hasChildNodes())
            {
                NodeList filhos = original.getChildNodes();
                for (int i=0; i<filhos.getLength(); i++)
                {
                    nodoAux = this.copiaNodo(filhos.item(i));
                    copiaElem.appendChild(nodoAux);
                }
            }
            copiaFeita = copiaElem;
        }
        return copiaFeita;
    }

    /***** Recebe um objeto Date e retorna ele por escrito ******/
    private String getData(Date data)
    {
        Calendar cal = new GregorianCalendar();
        cal.setTime(data);

        String dia, mes, ano;
        ano = String.valueOf(cal.get(Calendar.YEAR));
        mes = String.valueOf(cal.get(Calendar.MONTH));
        dia = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

        // Faz o mes e o dia terem 2 casas
        if (mes.length() == 1)
            mes = "0" + mes;
        if (dia.length() == 1)
            dia = "0" + dia;

        return ano + "/" + mes + "/" + dia;
    }

    /** Grava os dados de 'dataString' no arquivo 'nome'. *****/
    public boolean writeFile(String nome, String dataString)
    {
        File file = new File(nome);
        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.print(dataString);
            out.flush();
            out.close();
        }
        catch (IOException e)
        {
            return false;
        }
        return true;
    } // writeFile

    /***** Muda os atributos de data final do nodo passado e de todos os seus filhos *****/
    private void setAtributosFilhos(Element nodo, String data)
    {
        nodo.setAttribute("data_fim", data);
        NodeList filhos = nodo.getChildNodes();
        Node aux;
        for (int i=0; i < filhos.getLength(); i++)
        {
            aux = filhos.item(i);
            if (aux.getNodeType() == Node.ELEMENT_NODE)
                this.setAtributosFilhos((Element)aux, data);
        }
    }

    /***** Verifica se o nodo passado tem algum filho element ou text *****/
    private boolean temNodosFilhos(Node nodo)
    {
        boolean ok = false;
        NodeList filhos = nodo.getChildNodes();
        for (int i=0; i<filhos.getLength(); i++)
        {
            if ((filhos.item(i).getNodeType() == Node.ELEMENT_NODE) ||
                (filhos.item(i).getNodeType() == Node.TEXT_NODE))
            {
                ok = true;
                break;
            }
        }
        return ok;
    }
}
