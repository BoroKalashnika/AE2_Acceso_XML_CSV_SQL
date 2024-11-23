package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import clases.ConexioBD;
import clases.Population;
import clases.Utils;
import gui.Main;

/**
 * Classe de control per a gestionar els esdeveniments i la funcionalitat de la
 * finestra "Main".
 */
public class MainControl {
	private Main main;
	private ActionListener actionListener_logout, actionListener_administrar, actionListener_filtrar;
	private Principal principal;
	private Population population;

	/**
	 * Constructor que inicialitza el control de la finestra "Main".
	 *
	 * @param main      instància de la classe Main que representa la interfície
	 *                  gràfica.
	 * @param principal instància de la classe Principal per a la navegació entre
	 *                  pantalles.
	 */
	public MainControl(Main main, Principal principal) {
		this.main = main;
		this.principal = principal;
		population = new Population();
		control();
	}

	/**
	 * Configura els ActionListeners per a gestionar els esdeveniments de la
	 * finestra "Main".
	 */
	private void control() {
		actionListener_logout = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConexioBD.getInstance().closeConnection();
				main.dispose();
				principal.logout();
			}
		};

		actionListener_administrar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.dispose();
				principal.administrar();
			}
		};

		actionListener_filtrar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.dispose();
				principal.filtrar();
			}
		};

		main.getTable().setModel(Utils.omplirTaula(population.llegirNomCamps(), population.llegirXml()));
		main.getBtnLogout().addActionListener(actionListener_logout);
		main.getBtnAdministrar().addActionListener(actionListener_administrar);
		main.getBtnFiltrar().addActionListener(actionListener_filtrar);
	}

	/**
	 * Mostra la finestra "Main".
	 */
	public void mostrarMain() {
		main.setVisible(true);
	}
}
