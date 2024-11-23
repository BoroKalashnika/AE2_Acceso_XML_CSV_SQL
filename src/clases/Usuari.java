package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * La classe Usuari representa un usuari que es pot registrar a la base de
 * dades. Proporciona mètodes per comprovar si un usuari existeix, afegir-ne un
 * de nou i gestionar la creació de l'usuari a la base de dades i la concessió
 * de permisos.
 */
public class Usuari {
	String login;
	String password;
	String type;

	/**
	 * Constructor de la classe Usuari.
	 * 
	 * @param login    el nom d'usuari
	 * @param password la contrasenya de l'usuari
	 * @param type     el tipus d'usuari (ex. "client", "admin")
	 */
	public Usuari(String login, String password, String type) {
		this.login = login;
		this.password = password;
		this.type = type;
	}

	/**
	 * Comprova si l'usuari ja existeix a la base de dades.
	 * 
	 * @return true si l'usuari existeix, false si no
	 */
	boolean existeixUsuari() {
		boolean res = false;
		try {
			String query = "SELECT `login` FROM users WHERE login = ?";
			PreparedStatement pstmt = ConexioBD.getInstance().getConnection().prepareStatement(query);
			pstmt.setString(1, this.login);
			ResultSet rs = pstmt.executeQuery();

			res = rs.next();

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return res;
	}

	/**
	 * Afegeix un nou usuari a la base de dades. Si l'usuari no existeix, l'usuari
	 * es crea a la base de dades i se li concedeixen els permisos adequats.
	 * 
	 * @return true si l'usuari s'ha afegit correctament, false si no
	 * @throws SQLException si es produeix un error en la inserció de l'usuari
	 */
	public boolean agregarUsuari() throws SQLException {
		int resInsertar = 0;

		if (!existeixUsuari()) {
			Connection con = ConexioBD.getInstance().getConnection();
			try {
				String query = "INSERT INTO users (login,`password`,`type`) VALUES (?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setString(1, this.login);
				pstmt.setString(2, this.password);
				pstmt.setString(3, this.type);
				resInsertar = pstmt.executeUpdate();

				String crearUsuari = "CREATE USER IF NOT EXISTS '" + this.login + "'@'localhost' IDENTIFIED BY '"
						+ this.password + "';";

				try (PreparedStatement crearStmt = con.prepareStatement(crearUsuari)) {
					crearStmt.executeUpdate();
				}

				String darPermisos = "GRANT SELECT ON population.population TO '" + this.login + "'@'localhost';";
				try (PreparedStatement darStmt = con.prepareStatement(darPermisos)) {
					darStmt.executeUpdate();
				}

				pstmt.close();
			} catch (SQLException e) {
				if (e.getMessage().contains("INSERT command denied to user")) {
					JOptionPane.showMessageDialog(null, "¡Vosté no té permisos per a agregar a un usuari!",
							"Permís Denegat", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			throw new SQLException("L'usuari ja existix!");
		}
		return resInsertar == 1 ? true : false;
	}

}
