package clases;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

/**
 * La classe Utils proporciona diversos mètodes utilitaris per a la gestió de
 * connexions a bases de dades, generació de hashes MD5, exportació de dades a
 * fitxers i ompliment de taules amb dades per a la visualització en interfícies
 * gràfiques.
 */
public class Utils {

	/**
	 * Genera un hash MD5 a partir d'una cadena d'entrada.
	 * 
	 * @param input la cadena d'entrada per generar el hash
	 * @return el hash MD5 de la cadena d'entrada en format hexadecimal
	 */
	public static String getMd5Hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Realitza una consulta SELECT a la base de dades i retorna els resultats en
	 * una llista de vectors de cadenes.
	 * 
	 * @param taula el nom de la taula a consultar
	 * @param camps els noms dels camps a seleccionar
	 * @return una llista de vectors de cadenes amb els resultats de la consulta
	 * @throws SQLException si hi ha un error en la consulta
	 */
	public static List<String[]> selectFromDB(String taula, String camps) throws SQLException {
		String query = "SELECT " + camps + " FROM " + taula;
		List<String[]> filas = new ArrayList<>();

		try {
			PreparedStatement pstmt = ConexioBD.getInstance().getConnection().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			int numCol = rsmd.getColumnCount();

			while (rs.next()) {
				String[] fila = new String[numCol];
				for (int i = 0; i < numCol; i++) {
					fila[i] = rs.getString(i + 1);
				}
				filas.add(fila);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			throw e;
		}
		return filas;
	}

	/**
	 * Executa una consulta SQL donada i retorna els resultats com una llista de
	 * fileres.
	 *
	 * @param query la consulta SQL a executar.
	 * @return una llista d'arrays de cadenes, on cada array representa una filera
	 *         del resultat.
	 * @throws SQLException si ocorre un error durant l'execució de la consulta.
	 */
	public static List<String[]> selectFromDBLliure(String query) throws SQLException {
		List<String[]> filas = new ArrayList<>();

		try {
			PreparedStatement pstmt = ConexioBD.getInstance().getConnection().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			int numCol = rsmd.getColumnCount();

			while (rs.next()) {
				String[] fila = new String[numCol];
				for (int i = 0; i < numCol; i++) {
					fila[i] = rs.getString(i + 1);
				}
				filas.add(fila);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			throw e;
		}
		return filas;
	}

	/**
	 * Retorna els noms dels camps seleccionats en una consulta SQL.
	 *
	 * @param query la consulta SQL a executar.
	 * @return una cadena amb els noms dels camps separats per comes.
	 * @throws SQLException si ocorre un error durant l'execució de la consulta.
	 */
	public static String campsSelecionats(String query) throws SQLException {
		try {
			PreparedStatement pstmt = ConexioBD.getInstance().getConnection().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			int numCol = rsmd.getColumnCount();

			String nomCamps = rsmd.getColumnName(1);
			for (int i = 2; i <= numCol; i++) {
				nomCamps += ", " + rsmd.getColumnName(i);
			}

			rs.close();
			pstmt.close();

			return nomCamps;
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Exporta les dades a un fitxer, utilitzant un separador personalitzat entre
	 * els valors.
	 * 
	 * @param capcalera la capçalera per al fitxer exportat
	 * @param dada      les dades a exportar
	 * @param nomFixer  el nom del fitxer de sortida
	 * @param separador el caràcter utilitzat per separar els valors en el fitxer
	 * @throws IOException
	 */
	public static void exportarFitxer(String capcalera, List<String[]> dada, String nomFixer, char separador)
			throws IOException {
		try {
			FileWriter fw = new FileWriter(new File(nomFixer));

			fw.write(capcalera + "\n");

			for (String[] fila : dada) {
				fw.write(fila[0]);
				for (int i = 1; i < fila.length; i++) {
					fw.write(separador + fila[i]);
				}
				fw.write("\n");
			}

			fw.close();
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Omple un model de taula (DefaultTableModel) amb les dades proporcionades.
	 * 
	 * @param nomCamps els noms dels camps de la taula
	 * @param dada     les dades a omplir a la taula
	 * @return un model de taula amb les dades proporcionades
	 */
	public static DefaultTableModel omplirTaula(String[] nomCamps, List<String[]> dada) {
		DefaultTableModel tableModel = new DefaultTableModel();

		for (int i = 0; i < nomCamps.length; i++) {
			tableModel.addColumn(nomCamps[i].toUpperCase());
		}

		for (String[] fila : dada) {
			tableModel.addRow(fila);
		}
		return tableModel;
	}
}
