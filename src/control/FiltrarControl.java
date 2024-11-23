package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import clases.Utils;
import gui.Filtrar;

/**
 * Classe de control per a gestionar els esdeveniments i la funcionalitat de la
 * finestra "Filtrar".
 */
public class FiltrarControl {
	private Filtrar filtrar;
	private Principal principal;
	private ActionListener actionListener_tornar, actionListener_radioButtons, actionListener_mostrarTaula,
			actionListener_desmarcar, actionListener_marcar, actionListener_exportarCSV, actionListener_filtreAvancat;
	private String nomCamps;
	private List<String[]> dades;

	/**
	 * Constructor que inicialitza el control de la finestra "Filtrar".
	 *
	 * @param filtrar   instància de la classe Filtrar que representa la interfície
	 *                  gràfica.
	 * @param principal instància de la classe Principal per a la navegació entre
	 *                  finestres.
	 */
	public FiltrarControl(Filtrar filtrar, Principal principal) {
		this.filtrar = filtrar;
		this.principal = principal;
		control();
	}

	/**
	 * Configura els diferents ActionListeners per a gestionar els esdeveniments
	 * dels components de la finestra.
	 */
	private void control() {
		filtrar.getPnlUsuari().setVisible(false);
		filtrar.getBtnExportarCsv().setEnabled(false);

		actionListener_tornar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filtrar.dispose();
				principal.loginSucces();
			}
		};

		actionListener_radioButtons = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campsMostrar();
			}
		};

		actionListener_mostrarTaula = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					filtrar.getBtnExportarCsv().setEnabled(true);

					String taula = (filtrar.getRdbtnPopulation().isSelected()) ? "population" : "users";
					nomCamps = campsSeleccionats();

					dades = Utils.selectFromDB(taula, nomCamps);

					filtrar.getTable().setModel(Utils.omplirTaula(nomCamps.split(","), dades));
				} catch (SQLException e1) {
					filtrar.getBtnExportarCsv().setEnabled(false);

					if (e1.getMessage().contains("SELECT command denied to user")) {
						JOptionPane.showMessageDialog(null, "¡Vosté no té permisos per a accedir a esta taula!",
								"Permís Denegat", JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};

		actionListener_desmarcar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				desmarcarCamps();
			}
		};

		actionListener_marcar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				marcarTots();
			}
		};

		actionListener_exportarCSV = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nomFitxer = (filtrar.getRdbtnPopulation().isSelected()) ? "selectPopulation.csv"
						: "selectUsers.csv";
				try {
					Utils.exportarFitxer(nomCamps.replace(',', ';'), dades, nomFitxer, ';');
					JOptionPane.showMessageDialog(null, "Fitxer creat amb èxit", "Info", 1);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "S'ha produït algun error: " + e1.getMessage(), "Error", 0);
				}
			}
		};

		actionListener_filtreAvancat = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String query = JOptionPane.showInputDialog(filtrar, "Introduïx la consulta que vol realitzar:", null);

				if (query != null) {
					try {
						dades = Utils.selectFromDBLliure(query);
						nomCamps = Utils.campsSelecionats(query);

						if (query.contains("population")) {
							filtrar.getRdbtnPopulation().setSelected(true);
						} else {
							filtrar.getRdbtnUsers().setSelected(true);
						}
						campsMostrar();

						filtrar.getTable().setModel(Utils.omplirTaula(nomCamps.split(","), dades));
						filtrar.getBtnExportarCsv().setEnabled(true);
					} catch (SQLException e1) {
						filtrar.getBtnExportarCsv().setEnabled(false);
						if (e1.getMessage().contains("SELECT command denied to user")) {
							JOptionPane.showMessageDialog(null, "¡Vosté no té permisos per a accedir a esta taula!",
									"Permís Denegat", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		};

		filtrar.getBtnTornar().addActionListener(actionListener_tornar);
		filtrar.getRdbtnPopulation().addActionListener(actionListener_radioButtons);
		filtrar.getRdbtnUsers().addActionListener(actionListener_radioButtons);
		filtrar.getBtnMostrarTaula().addActionListener(actionListener_mostrarTaula);
		filtrar.getBtnDesmarcar().addActionListener(actionListener_desmarcar);
		filtrar.getBtnMarcarTot().addActionListener(actionListener_marcar);
		filtrar.getBtnExportarCsv().addActionListener(actionListener_exportarCSV);
		filtrar.getBtnFiltreAvancat().addActionListener(actionListener_filtreAvancat);
	}

	/**
	 * Mostra els camps disponibles en funció de la taula seleccionada.
	 */
	void campsMostrar() {
		if (filtrar.getRdbtnPopulation().isSelected()) {
			filtrar.getPnlUsuari().setVisible(false);
			filtrar.getPnlPopulation().setVisible(true);
		} else {
			filtrar.getPnlPopulation().setVisible(false);
			filtrar.getPnlUsuari().setVisible(true);
		}
	}

	/**
	 * Retorna els camps seleccionats com a una cadena separada per comes.
	 *
	 * @return una cadena amb els camps seleccionats.
	 */
	String campsSeleccionats() {
		String camps = "";
		if (filtrar.getRdbtnPopulation().isSelected()) {
			if (filtrar.getChkCountry().isSelected()) {
				camps += ", country";
			}
			if (filtrar.getChkPopulation().isSelected()) {
				camps += ", population";
			}
			if (filtrar.getChkDensity().isSelected()) {
				camps += ", density";
			}
			if (filtrar.getChkArea().isSelected()) {
				camps += ", area";
			}
			if (filtrar.getChkFertility().isSelected()) {
				camps += ", fertility";
			}
			if (filtrar.getChkAge().isSelected()) {
				camps += ", age";
			}
			if (filtrar.getChkUrban().isSelected()) {
				camps += ", urban";
			}
			if (filtrar.getChkShare().isSelected()) {
				camps += ", share";
			}
		} else {
			if (filtrar.getChkId().isSelected()) {
				camps += ", id";
			}
			if (filtrar.getChkLogin().isSelected()) {
				camps += ", login";
			}
			if (filtrar.getChkPassword().isSelected()) {
				camps += ", password";
			}
			if (filtrar.getChkType().isSelected()) {
				camps += ", type";
			}
		}
		return camps.replaceFirst(",", "");
	}

	/**
	 * Desmarca tots els camps disponibles.
	 */
	void desmarcarCamps() {
		if (filtrar.getRdbtnPopulation().isSelected()) {
			filtrar.getChkPopulation().setSelected(false);
			filtrar.getChkDensity().setSelected(false);
			filtrar.getChkArea().setSelected(false);
			filtrar.getChkFertility().setSelected(false);
			filtrar.getChkAge().setSelected(false);
			filtrar.getChkUrban().setSelected(false);
			filtrar.getChkShare().setSelected(false);
		} else {
			filtrar.getChkLogin().setSelected(false);
			filtrar.getChkPassword().setSelected(false);
			filtrar.getChkType().setSelected(false);
		}
	}

	/**
	 * Marca tots els camps disponibles.
	 */
	void marcarTots() {
		if (filtrar.getRdbtnPopulation().isSelected()) {
			filtrar.getChkPopulation().setSelected(true);
			filtrar.getChkDensity().setSelected(true);
			filtrar.getChkArea().setSelected(true);
			filtrar.getChkFertility().setSelected(true);
			filtrar.getChkAge().setSelected(true);
			filtrar.getChkUrban().setSelected(true);
			filtrar.getChkShare().setSelected(true);
		} else {
			filtrar.getChkLogin().setSelected(true);
			filtrar.getChkPassword().setSelected(true);
			filtrar.getChkType().setSelected(true);
		}
	}

	/**
	 * Metode per a poder mostrar el formulari filtrar des d'un altre formulari
	 */
	public void mostrarFiltrar() {
		filtrar.setVisible(true);
	}
}
