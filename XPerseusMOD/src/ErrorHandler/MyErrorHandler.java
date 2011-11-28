package ErrorHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class MyErrorHandler implements ErrorHandler {
  public void warning(SAXParseException e) throws SAXException {
    show("Perigo", e);
    throw (e);
  }

  public void error(SAXParseException e) throws SAXException {
    show("Erro", e);
    throw (e);
  }

  public void fatalError(SAXParseException e) throws SAXException {
    show("Erro Fatal", e);
    throw (e);
  }

  private void show(String type, SAXParseException e) {
    System.out.println(type + ": " + e.getMessage());
    System.out.println("Linha " + e.getLineNumber() + " Coluna " + e.getColumnNumber());
    System.out.println("ID: " + e.getSystemId());
  }
}