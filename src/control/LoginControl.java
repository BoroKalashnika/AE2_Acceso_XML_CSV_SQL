package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import clases.ConexioBD;
import clases.Utils;
import gui.Login;

/**
 * Classe de control per a gestionar els esdeveniments i la funcionalitat de la
 * finestra "Login".
 */
public class LoginControl {
	private Login login;
	private ActionListener actionListener_login;
	private Principal principal;

	/**
	 * Constructor que inicialitza el control de la finestra "Login".
	 *
	 * @param login     instància de la classe Login que representa la interfície
	 *                  gràfica.
	 * @param principal instància de la classe Principal per a la navegació després
	 *                  d'iniciar sessió.
	 */
	public LoginControl(Login login, Principal principal) {
		this.login = login;
		this.principal = principal;
		control();
	}

	/**
	 * Configura l'ActionListener per a gestionar l'esdeveniment d'inici de sessió.
	 */
	private void control() {
		actionListener_login = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String usuari = login.getTxtUsuari().getText();
				char[] passwordChars = login.getPwdContrasenya().getPassword();

				if (!usuari.isEmpty() && passwordChars.length > 0) {
					try {
						String password = Utils.getMd5Hash(new String(passwordChars));
						new ConexioBD(usuari, password);

						login.dispose();
						principal.loginSucces();
					} catch (Exception ex) {
						if (ex.getMessage().contains("Access denied for user")) {
							JOptionPane.showMessageDialog(null,
									"L'usuari no existix o les credencials són incorrectes!", "Error de Connexió", 0);
						} else {
							JOptionPane.showMessageDialog(null,
									"No s'ha pogut connectar a la base de dades, prova més tard!", "Error de Connexió",
									0);
						}
						login.setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Completa els camps!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		login.getBtnLogin().addActionListener(actionListener_login);
	}

	/**
	 * Mostra la finestra "Login".
	 */
	public void mostrarLogin() {
		login.setVisible(true);
	}
}
