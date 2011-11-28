package GUI;
import java.io.*;

public class FiltroJFileChooser extends javax.swing.filechooser.FileFilter
{
  public boolean accept (File f) {
    return f.getName ().toLowerCase ().endsWith (".xml")
          || f.isDirectory ();
  }

  public String getDescription () {
    return "Arquivos XML(*.xml)";
  }
}
