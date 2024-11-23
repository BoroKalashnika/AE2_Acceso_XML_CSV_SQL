package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La classe ConexioBD s'encarrega de gestionar la connexió a una base de dades MySQL 
 * utilitzant el patró de disseny Singleton. 
 * Assegura que només existeixi una instància de la connexió a la base de dades durant 
 * tot el cicle de vida de l'aplicació.
 */
public class ConexioBD {
    private static ConexioBD instance;

    private Connection connection;

    private final String url = "jdbc:mysql://localhost:3306/population";
    private static String login = null;
    private static String password = null;

    /**
     * Constructor privat que inicialitza la connexió.
     * Només s'invoca una vegada durant la creació de la instància Singleton.
     */
    private ConexioBD() {
        initializeConnection();
    }

    /**
     * Constructor públic per inicialitzar el login i la contrasenya utilitzats per a la connexió.
     * Aquest constructor s'hauria d'invocar abans de la primera crida al mètode getInstance().
     * 
     * @param login el nom d'usuari per a la connexió a la base de dades
     * @param password la contrasenya per a la connexió a la base de dades
     */
    public ConexioBD(String login, String password) {
        ConexioBD.login = login;
        ConexioBD.password = password;
    }

    /**
     * Retorna la instància única de la classe ConexioBD.
     * Si no existeix, es crea.
     * 
     * @return la instància única de ConexioBD
     */
    public static synchronized ConexioBD getInstance() {
        if (instance == null) {
            instance = new ConexioBD();
        }
        return instance;
    }

    /**
     * Inicialitza la connexió a la base de dades utilitzant les credencials definides.
     * Si la connexió falla, es llança una excepció.
     */
    private void initializeConnection() {
        try {
            this.connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            throw new RuntimeException("La connexió a la base de dades ha fallat: " + e.getMessage());
        }
    }

    /**
     * Retorna la connexió activa a la base de dades.
     * Si la connexió no està disponible o està tancada, intenta reinicialitzar-la.
     * 
     * @return la connexió activa
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                initializeConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException("No s'ha pogut reiniciar la connexió a la base de dades: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Tanca la connexió a la base de dades.
     * Si la connexió existeix, es tanca correctament.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                instance = null;
            }
        }
    }
}
