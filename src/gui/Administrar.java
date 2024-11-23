package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class Administrar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuari;
	private JPasswordField pwdContrasenya;
	private JPasswordField pwdConfirmacio;
	private JButton btnTornar;
	private JButton btnNetejarDades;
	private JButton btnAgergarUsuari;

	public JTextField getTxtUsuari() {
		return txtUsuari;
	}

	public JPasswordField getPwdContrasenya() {
		return pwdContrasenya;
	}

	public JPasswordField getPwdConfirmacio() {
		return pwdConfirmacio;
	}

	public JButton getBtnTornar() {
		return btnTornar;
	}

	public JButton getBtnNetejarDades() {
		return btnNetejarDades;
	}

	public JButton getBtnAgergarUsuari() {
		return btnAgergarUsuari;
	}

	/**
	 * Create the frame.
	 */
	public Administrar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnTornar = new JButton("Tornar");
		btnTornar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTornar.setBounds(272, 265, 93, 37);
		contentPane.add(btnTornar);

		btnNetejarDades = new JButton("Netejar dades");
		btnNetejarDades.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNetejarDades.setBounds(125, 265, 145, 37);
		contentPane.add(btnNetejarDades);

		btnAgergarUsuari = new JButton("Agergar Usuari");
		btnAgergarUsuari.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnAgergarUsuari.setBounds(125, 217, 240, 37);
		contentPane.add(btnAgergarUsuari);

		txtUsuari = new JTextField();
		txtUsuari.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtUsuari.setBounds(125, 52, 240, 30);
		contentPane.add(txtUsuari);
		txtUsuari.setColumns(10);

		JLabel lblNewLabel = new JLabel("Usuari:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(60, 52, 55, 30);
		contentPane.add(lblNewLabel);

		JLabel lblContrasenya = new JLabel("Contrasenya:");
		lblContrasenya.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblContrasenya.setBounds(10, 104, 105, 30);
		contentPane.add(lblContrasenya);

		JLabel lblConfirmaci = new JLabel("Confirmació:");
		lblConfirmaci.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblConfirmaci.setBounds(10, 157, 105, 30);
		contentPane.add(lblConfirmaci);

		JLabel lblAgregarUsuari = new JLabel("Agregar Usuari");
		lblAgregarUsuari.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAgregarUsuari.setBounds(147, 11, 166, 30);
		contentPane.add(lblAgregarUsuari);

		pwdContrasenya = new JPasswordField();
		pwdContrasenya.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pwdContrasenya.setBounds(125, 104, 240, 30);
		contentPane.add(pwdContrasenya);

		pwdConfirmacio = new JPasswordField();
		pwdConfirmacio.setFont(new Font("Tahoma", Font.PLAIN, 18));
		pwdConfirmacio.setBounds(125, 157, 240, 30);
		contentPane.add(pwdConfirmacio);
	}
}
