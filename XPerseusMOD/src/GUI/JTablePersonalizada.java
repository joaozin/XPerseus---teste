package GUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class JTablePersonalizada extends JTable {

	private static final long serialVersionUID= 1L;
	private static Component scrollPane = null;

	/**
	 * Cria uma nova JTable, com 2 colunas e infinitas linhas. As linhas não são editáveis,
	 * têm seleção única e a primeira coluna, tem o background cinza, como padrão para JFrame.
	 *
	 * @param titulos
	 * @param conteudo
	 * @param larguraColunas
	 */
	public JTablePersonalizada(Object[] titulos, Object[][] conteudo, int[] larguraColunas, boolean mostrarLinhasHorizontais, boolean mostrarLinhasVerticais) {
		// Modelo para desativar colunas
		@SuppressWarnings("serial")
		DefaultTableModel model= new DefaultTableModel(conteudo,titulos){
			public boolean isCellEditable(int row, int column) {
				/*
				if(column == 0){
					return false;
				}
				return super.isCellEditable(row,column);
				*/
				return false;
			}
		};


		//Modelo para colocar a coluna sem cor
		DefaultTableCellRenderer fundo0 = new DefaultTableCellRenderer();
		fundo0.setBackground((new JFrame()).getBackground()); // fundo com a cor default

		DefaultTableCellRenderer fundo1 = new DefaultTableCellRenderer();
		fundo1.setBackground(Color.WHITE); // fundo com a cor default

		final JTable tabela= new JTable(model);
		tabela.getColumnModel().getColumn(0).setCellRenderer(fundo0);
		tabela.getColumnModel().getColumn(1).setCellRenderer(fundo1);

		tabela.getColumnModel().getColumn(0).setMinWidth(larguraColunas[0]);
		tabela.getColumnModel().getColumn(0).setMaxWidth(larguraColunas[0] + 50);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(larguraColunas[1]);

		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.setShowHorizontalLines(mostrarLinhasHorizontais);
		tabela.setShowVerticalLines(mostrarLinhasVerticais);

		scrollPane= new JScrollPane(tabela);
	}

	/**
	 * Retorna o objeto scrollPane que deve ser adicionado a um JFrame ou JPanel.
	 *
	 * @return scrollPane
	 */
	protected static Component getScrollPane(){
		return scrollPane;
	}
}