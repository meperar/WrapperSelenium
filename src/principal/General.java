package principal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class General {

	private JFrame frame;
	private JTextField txtTexto;
	private JTextField txtTexto_1;
	private JTextPane txtpnAutor;
	private JTextField txtTexto_2;
	private JCheckBox chckbxNewCheckBox_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					General window = new General();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public General() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.WHITE);
		frame.getContentPane().add(desktopPane);

		txtTexto_1 = new JTextField();
		txtTexto_1.setBounds(171, 39, 86, 20);
		desktopPane.add(txtTexto_1);
		txtTexto_1.setColumns(10);

		JTextPane txtpnLibros = new JTextPane();
		txtpnLibros.setText("Titulo del libro:");
		txtpnLibros.setBounds(75, 39, 86, 20);
		desktopPane.add(txtpnLibros);

		txtpnAutor = new JTextPane();
		txtpnAutor.setText("Autor:");
		txtpnAutor.setBounds(111, 70, 50, 20);
		desktopPane.add(txtpnAutor);

		txtTexto_2 = new JTextField();
		txtTexto_2.setBounds(171, 70, 86, 20);
		desktopPane.add(txtTexto_2);
		txtTexto_2.setColumns(10);

		JCheckBox chckbxAmazon = new JCheckBox("Amazon");
		chckbxAmazon.setBackground(Color.WHITE);
		chckbxAmazon.setBounds(28, 170, 97, 23);
		desktopPane.add(chckbxAmazon);

		JCheckBox chckbxNewCheckBox = new JCheckBox("Fnac");
		chckbxNewCheckBox.setBackground(Color.WHITE);
		chckbxNewCheckBox.setBounds(28, 151, 97, 23);
		desktopPane.add(chckbxNewCheckBox);

		chckbxNewCheckBox_1 = new JCheckBox("El Corte Inglés");
		chckbxNewCheckBox_1.setBackground(Color.WHITE);
		chckbxNewCheckBox_1.setForeground(Color.BLACK);
		chckbxNewCheckBox_1.setBounds(28, 131, 97, 23);
		desktopPane.add(chckbxNewCheckBox_1);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBuscar.setBounds(28, 214, 89, 23);
		desktopPane.add(btnBuscar);
		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String texto1=txtTexto_1.getText();
				String texto2=txtTexto_2.getText();
				Boolean box0 =chckbxNewCheckBox.isSelected() ;
				Boolean box1 =chckbxNewCheckBox_1.isSelected() ;
				if ((!texto1.isEmpty() || !texto2.isEmpty()) && box0 || box1) {
					String titulo = txtTexto_1.getText();
					String autor = txtTexto_2.getText();
					List<Book> misLibros = new ArrayList();
					if (chckbxNewCheckBox_1.isSelected()) {
						Driver.Chrome();
						CorteIngles corteIngles = new CorteIngles(Driver.driver);
						misLibros.addAll(corteIngles.buscarLibro(titulo, autor));
					}
					if (chckbxNewCheckBox.isSelected()) {
						Driver.Chrome();
						Fnac fnac = new Fnac(Driver.driver);
						misLibros.addAll(fnac.buscarLibro(titulo, autor));
					}
					
					Listado window2 = new Listado(misLibros);
					window2.frame.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(null,
							"Debe introducir al menos uno de los 2 campos para realizar la busqueda. Y seleccional al menos una pagina de busqueda(Fnac o Corte Ingles)");
				}
			}
		});
	}
}
