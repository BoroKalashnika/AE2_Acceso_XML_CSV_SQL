package control;

import gui.Administrar;
import gui.Filtrar;
import gui.Login;
import gui.Main;

/**
 * Classe principal que gestiona la navegació entre les diferents interfícies
 * gràfiques de l'aplicació.
 */

public class Principal {

	/**
	 * Mostra la finestra de filtres i inicialitza el seu control.
	 */
	public void filtrar() {
		Filtrar filtrar = new Filtrar();
		FiltrarControl fC = new FiltrarControl(filtrar, this);
		fC.mostrarFiltrar();
	}

	/**
	 * Mostra la finestra d'administració i inicialitza el seu control.
	 */
	public void administrar() {
		Administrar admin = new Administrar();
		AdministrarControl aC = new AdministrarControl(admin, this);
		aC.mostrarAdministrar();
	}

	/**
	 * Mostra la finestra principal després d'un login satisfactori i inicialitza el
	 * seu control.
	 */
	public void loginSucces() {
		Main main = new Main();
		MainControl mC = new MainControl(main, this);
		mC.mostrarMain();
	}

	/**
	 * Tanca la sessió de l'usuari i torna a mostrar la finestra de login.
	 */
	public void logout() {
		Login login = new Login();
		LoginControl lC = new LoginControl(login, this);
		lC.mostrarLogin();
	}

	/**
	 * Punt d'entrada principal de l'aplicació. Inicialitza la finestra de login i
	 * el seu control.
	 *
	 * @param args arguments de línia de comandament (no s'utilitzen).
	 */
	public static void main(String[] args) {
		Login login = new Login();
		LoginControl lC = new LoginControl(login, new Principal());
		lC.mostrarLogin();
	}
}
