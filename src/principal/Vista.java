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
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.JCheckBox;

public class Vista {

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
					Vista window = new Vista();
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
	public Vista() {
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
		txtTexto_1.setText("texto");
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
		txtTexto_2.setText("texto");
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
		btnBuscar.setBounds(28, 214, 89, 23);
		desktopPane.add(btnBuscar);
	}
}
