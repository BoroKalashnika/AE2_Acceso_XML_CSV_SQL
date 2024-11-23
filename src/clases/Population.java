package clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.csvreader.CsvReader;

/**
 * Classe Population Aquesta classe s'encarrega de gestionar una taula de la
 * base de dades basada en un fitxer CSV i de generar fitxers XML a partir de
 * les dades. Les principals funcionalitats inclouen: - Crear una taula SQL si
 * no existeix. - Importar dades d'un fitxer CSV a la taula SQL. - Generar
 * fitxers XML a partir de les dades del CSV. - Llegir dades dels fitxers XML.
 */
public class Population {

	/**
	 * Constructor de la classe Population. Si la taula de la base de dades no
	 * existeix, es crea, es carreguen les dades des del fitxer CSV i es generen
	 * fitxers XML.
	 */
	public Population() {
		if (!taulaExisteix()) {
			crearTaulaPopulation();
			crearXml();
			carregarDades();
		}
	}

	/**
	 * Crea la taula "population" a la base de dades utilitzant les capçaleres del
	 * fitxer CSV com a noms de les columnes. Elimina la taula existent si ja
	 * existix.
	 */
	void crearTaulaPopulation() {
		String nomTaula = "population";
		String nomFixer = "AE02_population.csv";
		char separador = ';';

		Connection con = ConexioBD.getInstance().getConnection();
		CsvReader reader;

		esborrarTaula(con, nomTaula);

		String query = "CREATE TABLE IF NOT EXISTS `" + nomTaula + "`(";
		try {
			reader = new CsvReader(new FileReader(nomFixer), separador);
			try {
				reader.readHeaders();
				for (int i = 0; i < reader.getHeaderCount(); i++) {
					query += "\n`" + reader.getHeader(i).replaceAll("[^a-zA-Z]", "") + "` VARCHAR(30),";
				}
				query += "\nPRIMARY KEY (`" + reader.getHeader(0).replaceAll("[^a-zA-Z]", "") + "`));";
				reader.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No es va trobar l'arxiu!", "Error", JOptionPane.ERROR_MESSAGE);
		}

		try {
			con.createStatement().executeUpdate(query);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Comprova si la taula "population" existeix a la base de dades.
	 * 
	 * @return true si la taula existeix, false en cas contrari.
	 */
	boolean taulaExisteix() {
		String nomTaula = "population";
		try {
			var metaData = ConexioBD.getInstance().getConnection().getMetaData();
			var resultSet = metaData.getTables(null, null, nomTaula, new String[] { "TABLE" });

			return resultSet.next();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	/**
	 * Esborra una taula específica de la base de dades.
	 * 
	 * @param con      Connexió a la base de dades.
	 * @param nomTaula Nom de la taula a esborrar.
	 */
	void esborrarTaula(Connection con, String nomTaula) {
		try {
			con.createStatement().executeUpdate("DROP TABLE IF EXISTS `" + nomTaula + "`;");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Genera fitxers XML basats en les dades del fitxer CSV. Si els fitxers XML ja
	 * existeixen, no es regeneren.
	 */
	void crearXml() {
		try {
			CsvReader reader = new CsvReader(new FileReader("AE02_population.csv"), ';');
			reader.readHeaders();

			File xmlDir = new File("./xml");
			xmlDir.mkdir();

			boolean existixenFitxers = true;
			while (reader.readRecord()) {
				String nomFitxer = "./xml/" + reader.get(0) + ".xml";
				File xmlFile = new File(nomFitxer);
				if (!xmlFile.exists()) {
					existixenFitxers = false;
					break;
				}
			}

			if (existixenFitxers) {
				return;
			}

			reader.close();
			reader = new CsvReader(new FileReader("AE02_population.csv"), ';');
			reader.readHeaders();

			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = dFact.newDocumentBuilder();

			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();
			aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

			while (reader.readRecord()) {
				String[] line = reader.getValues();
				Document doc = build.newDocument();

				Element arrell = doc.createElement(reader.getHeader(0).replaceAll("[^a-zA-Z]", ""));
				String id = String.valueOf(line[0]);
				arrell.setAttribute("id", id);
				doc.appendChild(arrell);

				for (int i = 1; i < reader.getHeaderCount(); i++) {
					Element camp = doc.createElement(reader.getHeader(i).replaceAll("[^a-zA-Z]", ""));
					camp.appendChild(doc.createTextNode(line[i]));
					arrell.appendChild(camp);
				}

				try (FileWriter fw = new FileWriter("./xml/" + line[0] + ".xml")) {
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(fw);
					aTransformer.transform(source, result);
				}
			}
			reader.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Obté els noms dels camps del fitxer XML.
	 * 
	 * @return Un array amb els noms dels camps o nul si no es troben arxius XML.
	 */
	public String[] llegirNomCamps() {
		String arrell = "country";
		String[] camps = null;
		File directory = new File("xml");
		File[] files = directory.listFiles((dir, name) -> name.endsWith(".xml"));

		if (files != null && files.length > 0) {
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document document = dBuilder.parse(files[0]);

				NodeList nodeList = document.getElementsByTagName(arrell);
				if (nodeList.getLength() > 0) {
					Element firstCountryElement = (Element) nodeList.item(0);

					NodeList childNodes = firstCountryElement.getChildNodes();
					int fieldCount = 1;

					for (int i = 0; i < childNodes.getLength(); i++) {
						if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
							fieldCount++;
						}
					}

					camps = new String[fieldCount];
					camps[0] = arrell;

					int index = 1;
					for (int i = 0; i < childNodes.getLength(); i++) {
						Node node = childNodes.item(i);
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							camps[index++] = node.getNodeName();
						}
					}
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return camps;
	}

	/**
	 * Llig les dades dels fitxers XML i les retorna com una llista.
	 * 
	 * @return Una llista on cada entrada representa una fila de dades d'un arxiu
	 *         XML.
	 */
	public List<String[]> llegirXml() {
		String arrell = "country";
		List<String[]> data = new ArrayList<>();

		File directory = new File("xml");
		File[] files = directory.listFiles((dir, name) -> name.endsWith(".xml"));

		if (files != null) {
			String[] nomCamps = llegirNomCamps();
			for (File file : files) {
				try {
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(file);
					NodeList nodeList = doc.getElementsByTagName(arrell);

					for (int i = 0; i < nodeList.getLength(); i++) {
						Node node = nodeList.item(i);
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) node;

							String[] rowDada = new String[nomCamps.length];

							rowDada[0] = eElement.getAttribute("id");

							for (int j = 1; j < nomCamps.length; j++) {
								rowDada[j] = getFillElementText(eElement, nomCamps[j]);
							}

							data.add(rowDada);
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		return data;
	}

	/**
	 * Recupera el contingut de text d'un element secundari pel seu nom d'etiqueta.
	 * 
	 * @param pare   L'element principal.
	 * @param tagNom El nom de l'element secundari.
	 * @return El contingut de text de l'element secundari, o "NO CONSTA" si no
	 *         existix.
	 */
	static String getFillElementText(Element pare, String tagNom) {
		NodeList nodeList = pare.getElementsByTagName(tagNom);
		if (nodeList.getLength() > 0) {
			return nodeList.item(0).getTextContent();
		}
		return "NO CONSTA";
	}

	/**
	 * Carrega les dades del fitxer CSV a la base de dades.
	 * 
	 * @return veritable si les dades es van carregar correctament, fals en cas
	 *         contrari.
	 */
	boolean carregarDades() {
		String[] nomCamps = llegirNomCamps();
		int[] res = null;
		try {
			String camps = "`" + nomCamps[0] + "`";
			String interrogacios = "?";

			for (int i = 1; i < nomCamps.length; i++) {
				camps += ", `" + nomCamps[i] + "`";
				interrogacios += ", ?";
			}

			String query = "INSERT IGNORE INTO population (" + camps + ") VALUES (" + interrogacios + ");";

			PreparedStatement pstmt = ConexioBD.getInstance().getConnection().prepareStatement(query);

			List<String[]> dades = llegirXml();
			for (String[] dada : dades) {
				for (int i = 0; i < dada.length; i++) {
					pstmt.setString(i + 1, dada[i]);
				}
				pstmt.addBatch();
			}

			res = pstmt.executeBatch();
			pstmt.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return res != null && res.length > 0;
	}

}
