package principal;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JScrollBar;

public class Listado {

	public JFrame frame;
	private JTextField txtTexto;
	private JTable table;


	/**
	 * Create the application.
	 */
	public Listado(List<Book> libros) {
		initialize(libros);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(List<Book> libros) {
		frame = new JFrame();
		frame.setBounds(30, 30, 800, 650);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		frame.getContentPane().add(desktopPane);
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable();
		 
		String[] columnNames = {"Sitio",
                "Autor",
                "Descuento",
                "Precio Antiguo",
                "Nuevo Precio","Titulo"};
		
		 model.setColumnIdentifiers(columnNames);
		

		for (Book s : libros) {
			  Object[] o = new Object[6];
			  o[0] = s.getSitio();
			  o[1] = s.getAutor();
			  o[2] = s.getDescuento();
			  o[3] = s.getOldPrice();
			  o[4] = s.getNewPrice();
			  o[5] = s.getTitulo();
			  model.addRow(o);
			}
		
		table.setModel(model);
		table.setVisible(true);
		table.setBounds(10, 11, 764, 589);
		desktopPane.add(table);
	}
}
