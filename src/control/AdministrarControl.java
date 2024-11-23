package control;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import clases.Usuari;
import clases.Utils;
import gui.Administrar;

/**
 * Classe de control per a gestionar els esdeveniments i la funcionalitat de la
 * finestra "Administrar".
 */
public class AdministrarControl {
	private Administrar administrar;
	private Principal principal;
	private ActionListener actionListener_tornar, actionListener_netejar, actionListener_agregarUsuari;

	/**
	 * Constructor que inicialitza el control de la finestra "Administrar".
	 *
	 * @param administrar instància de la classe Administrar que representa la
	 *                    interfície gràfica.
	 * @param principal   instància de la classe Principal per a la navegació entre
	 *                    finestres.
	 */
	public AdministrarControl(Administrar administrar, Principal principal) {
		this.administrar = administrar;
		this.principal = principal;
		control();
	}

	/**
	 * Configura els ActionListeners per a gestionar els esdeveniments dels
	 * components de la finestra.
	 */
	private void control() {
		actionListener_tornar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				administrar.setVisible(false);
				administrar.dispose();
				principal.loginSucces();
			}
		};

		actionListener_netejar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				netejarDades();
			}
		};

		actionListener_agregarUsuari = new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				String login = administrar.getTxtUsuari().getText();
				String contrasenya = administrar.getPwdContrasenya().getText();
				String confirmacio = administrar.getPwdConfirmacio().getText();

				if (!login.isBlank() && !contrasenya.isBlank() && !confirmacio.isBlank()) {
					if (contrasenya.equals(confirmacio)) {
						Usuari usu = new Usuari(login, Utils.getMd5Hash(contrasenya), "client");
						try {
							if (usu.agregarUsuari()) {
								JOptionPane.showMessageDialog(null, "Usuari agregat correctament!", "Info", 1);
								netejarDades();
							}
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", 0);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Les contrasenyes no coincidixen!", "Error", 0);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Completa els camps!", "Error", 0);
				}
			}
		};

		administrar.getBtnTornar().addActionListener(actionListener_tornar);
		administrar.getBtnNetejarDades().addActionListener(actionListener_netejar);
		administrar.getBtnAgergarUsuari().addActionListener(actionListener_agregarUsuari);

	}

	/**
	 * Neteja els camps del formulari.
	 */
	void netejarDades() {
		administrar.getTxtUsuari().setText(null);
		administrar.getPwdContrasenya().setText(null);
		administrar.getPwdConfirmacio().setText(null);
	}

	/**
	 * Mostra la finestra "Administrar".
	 */
	public void mostrarAdministrar() {
		administrar.setVisible(true);
	}
}
