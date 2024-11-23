package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField pwdContrasenya;
	private JTextField txtUsuari;
	private JButton btnLogin;

	public JPanel getContentPane() {
		return contentPane;
	}

	public JPasswordField getPwdContrasenya() {
		return pwdContrasenya;
	}

	public JTextField getTxtUsuari() {
		return txtUsuari;
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 495, 311);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		pwdContrasenya = new JPasswordField();
		pwdContrasenya.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pwdContrasenya.setBounds(120, 114, 248, 39);
		contentPane.add(pwdContrasenya);

		txtUsuari = new JTextField();
		txtUsuari.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtUsuari.setBounds(120, 49, 248, 39);
		contentPane.add(txtUsuari);
		txtUsuari.setColumns(10);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLogin.setBounds(120, 184, 248, 39);
		contentPane.add(btnLogin);

		JLabel lblUsuari = new JLabel("Usuari:");
		lblUsuari.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUsuari.setBounds(53, 49, 57, 39);
		contentPane.add(lblUsuari);

		JLabel lblContrasenya = new JLabel("Contrasenya:");
		lblContrasenya.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblContrasenya.setBounds(7, 114, 103, 39);
		contentPane.add(lblContrasenya);
	}
}
