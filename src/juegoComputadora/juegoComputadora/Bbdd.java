package juegoComputadora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author lionel
 */
public class Bbdd {

	// Conexión a la base de datos
	private static Connection conn = null;

	// Configuración de la conexión a la base de datos
	private static final String DB_HOST = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_NAME = "juegoDamGGM";
	private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME
			+ "?serverTimezone=UTC";
	private static final String DB_USER = "dam2223";
	private static final String DB_PASS = "dam2223";
	private static final String DB_MSQ_CONN_OK = "CONEXIÓN CORRECTA";
	private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXIÓN";

	// Configuración de la tabla HISTORICO
	private static final String DB_HIS = "historico";
	private static final String DB_HIS_ID = "id_partida";
	private static final String DB_HIS_NIC = "nickname";
	private static final String DB_HIS_PUN = "puntuacion";
	private static final String DB_HIS_SELECT = "SELECT * FROM " + DB_HIS;
	private static final String DB_HIS_MAXID = "SELECT MAX(" + DB_HIS_ID + ") FROM " + DB_HIS;

	// Configuración de la tabla RANKING
	private static final String DB_RAN = "ranking";
	private static final String DB_RAN_NIC = "nickname";
	private static final String DB_RAN_PUN = "puntuacion";
	private static final String DB_RAN_SELECT = "SELECT * FROM " + "ranking ORDER BY " + DB_RAN_PUN + " DESC";

	//////////////////////////////////////////////////
	// MÉTODOS DE CONEXIÓN A LA BASE DE DATOS
	//////////////////////////////////////////////////

	/**
	 * Intenta cargar el JDBC driver.
	 * 
	 * @return true si pudo cargar el driver, false en caso contrario
	 */
	public static boolean loadDriver() {
		try {
			System.out.print("Cargando Driver...");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("OK!");
			return true;
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Intenta conectar con la base de datos.
	 *
	 * @return true si pudo conectarse, false en caso contrario
	 */
	public static boolean connect() {
		try {
			System.out.print("Conectando a la base de datos...");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			System.out.println("OK!");
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Comprueba la conexión y muestra su estado por pantalla
	 *
	 * @return true si la conexión existe y es válida, false en caso contrario
	 */
	public static boolean isConnected() {
		// Comprobamos estado de la conexión
		try {
			if (conn != null && conn.isValid(0)) {
				System.out.println(DB_MSQ_CONN_OK);
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			System.out.println(DB_MSQ_CONN_NO);
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public static void close() {
		try {
			System.out.print("Cerrando la conexión...");
			conn.close();
			System.out.println("OK!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void iniciar() {
		loadDriver();
		connect();
	}

	public static void muestraRanking() {
		Statement statement;
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(DB_RAN_SELECT);
			System.out.println();
			System.out.println("Ranking:");
			while (resultSet.next()) {
				int puntos = resultSet.getInt(DB_RAN_PUN);
				String nick = resultSet.getString(DB_RAN_NIC);
				System.out.println(nick.toUpperCase() + " " + puntos);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void muestraHistorico() {
		Statement statement;
		try {
			statement = conn.createStatement();
			ResultSet maximo = statement.executeQuery(DB_HIS_MAXID);
			int maximoID = 0;
			if (maximo.next()) {
				maximoID = maximo.getInt(1);
			}
			System.out.println();
			System.out.println("Histórico:");
			// System.out.println("partidas: " + maximoID);
			for (int i = 0; i < maximoID; i++) {
				ResultSet resultSet = statement
						.executeQuery("SELECT * FROM " + "historico" + " WHERE " + DB_HIS_ID + "=" + (i + 1));
				System.out.print((i + 1) + " ");
				while (resultSet.next()) {
					String nick = resultSet.getString(DB_HIS_NIC);
					int puntos = resultSet.getInt(DB_HIS_PUN);
					System.out.print(nick.toUpperCase() + " " + puntos);
					System.out.print(" ");
				}
				System.out.print("\n");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static <PreparedStatement> void crearJugador() {
		System.out.println("Introduce el nombre del jugador");
		Statement statement;
		try {
			statement = conn.createStatement();
			String nombre = Extra.entrada.next();
			ResultSet resultSet = statement.executeQuery(
					"SELECT * FROM " + DB_RAN + " WHERE LOWER(" + DB_RAN_NIC + ") = LOWER('" + nombre + "')");
			if (resultSet.next()) {
				System.out.println("El jugador ya existe en el sistema");
			} else {
				statement.execute("INSERT INTO " + DB_RAN + " VALUES ('" + nombre + "',0)");
				System.out.println("El jugador " + nombre.toUpperCase() + " ha sido añadido al sistema");
				statement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static <PreparedStatement> void eliminarJugador() {
		System.out.println("Introduce el nombre del jugador");
		Statement statement;
		try {
			statement = conn.createStatement();
			String nombre = Extra.entrada.next();
			ResultSet resultSet = statement.executeQuery(
					"SELECT * FROM " + DB_RAN + " WHERE LOWER(" + DB_RAN_NIC + ") = LOWER('" + nombre + "')");
			if (resultSet.next()) {
				statement.execute(
						"DELETE FROM " + DB_RAN + " WHERE LOWER(" + DB_RAN_NIC + ") = LOWER('" + nombre + "')");
				System.out.println("El jugador " + nombre.toUpperCase() + " ha sido eliminado del sistema");
				statement.close();
			} else {
				System.out.println("El jugador que ha introducido no existe en el sistema");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void muestraJugadores() {
		Statement statement;
		try {
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(DB_RAN_SELECT);
			System.out.println();
			System.out.println("Jugadores:");
			while (resultSet.next()) {
				String nick = resultSet.getString(DB_RAN_NIC);
				System.out.println(nick.toUpperCase());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void partidaAlHistorico(ArrayList<Jugador> participantes) throws SQLException {
		Statement statement = conn.createStatement();
		ResultSet maximo = statement.executeQuery(DB_HIS_MAXID);
		int maximoID = 0;
		if (maximo.next()) {
			maximoID = maximo.getInt(1);
		}
		for (int i = 0; i < participantes.size(); i++) {
			statement.execute("INSERT INTO " + DB_HIS + " (" + DB_HIS_ID + "," + DB_HIS_NIC + "," + DB_HIS_PUN
					+ ") VALUES (" + (maximoID + 1) + ",'" + participantes.get(i).getNombre() + "',"
					+ participantes.get(i).getPuntos() + ")");
		}
	}
}
