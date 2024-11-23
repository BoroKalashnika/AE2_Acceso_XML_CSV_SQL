package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton btnAdministrar;
	JButton btnLogout;
	private JTable table;
	private JButton btnFiltrar;

	public JButton getBtnAdministrar() {
		return btnAdministrar;
	}

	public JButton getBtnLogout() {
		return btnLogout;
	}
	
	public JTable getTable() {
		return table;
	}

	public JButton getBtnFiltrar() {
		return btnFiltrar;
	}
	
	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1076, 628);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		toolBar.setBounds(0, 0, 1060, 33);
		contentPane.add(toolBar);
		
		btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 14));
		toolBar.add(btnLogout);
		
		btnAdministrar = new JButton("Administrar");
		btnAdministrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		toolBar.add(btnAdministrar);
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		toolBar.add(btnFiltrar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 49, 929, 472);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
	}
}
