package juegoComputadora;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public Scanner entrada = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {

		Bbdd.iniciar();
		//Bbdd.muestraRanking();
		//Bbdd.muestraHistorico();
		//Bbdd.muestraJugadores();
		// Bbdd.crearJugador();
		//Bbdd.eliminarJugador();
		// System.out.println(Bbdd.isConnected());
		// Menu.menuInicial();
		
		ArrayList<Jugador>a1=new ArrayList();
		a1.add(new PJ("Carlitos",5));
		a1.add(new PJ("Joselito",3));
		
		Bbdd.partidaAlHistorico(a1);
		Bbdd.muestraHistorico();

	}

}
