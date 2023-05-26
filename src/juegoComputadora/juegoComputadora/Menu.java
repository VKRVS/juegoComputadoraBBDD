package juegoComputadora;

import java.io.IOException;
import java.util.Scanner;

public abstract class Menu {

	public static Scanner entrada = new Scanner(System.in);

	public static void menuInicial() {
		boolean salir = false;
		String opcion;
		while (!salir) {
			System.out.println("Elige una opcion:\n" + "	1.-Jugar Partida\n" + "	2.-Ranking\n" + "	3.-Histórico\n"
					+ "	4.-Jugadores\n" + "	5.-Salir");
			opcion = entrada.next();
			switch (opcion) {
			case "1": {
				System.out.println("\nJugar Partida");
				menuJugarPartida();
				System.out.println();
				break;
			}
			case "2": {
				System.out.println("\nRanking de Jugadores:");
				Ficheros.verRanking();
				System.out.println();
				break;
			}
			case "3": {
				System.out.println("\nHistórico de Partidas:");
				Ficheros.verHistorico();
				System.out.println();
				break;
			}
			case "4": {
				System.out.println("\nJugadores");
				menuJugadores();
				break;
			}
			case "5": {
				salir = true;
				System.out.println("\nSalir");
				break;
			}
			default:
				System.out.println("Introduce una opción válida");
			}
		}
	}

	public static void menuJugarPartida() {
		int jugadores;
		int jugadoresHumanos;
		int tipo = Extra.comprobador(1, 2,
				"Elige el tipo de partida:\n    1.-Partida de práctica\n    2.-Partida normal", null);
		switch (tipo) {
		case 1: {
			try {
				new Partida(1, 1, menuTipoPartida(), 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		case 2: {
			jugadores = Extra.comprobador(1, 4,
					"Introduce la cantidad de jugadores para la partida (mínimo 1, máximo 4)",
					"Error, introduce un valor válido");
			jugadoresHumanos = Extra.comprobador(0, jugadores,
					"Introduce la cantidad de jugadores Humanos (mínimo 0, máximo " + jugadores + ")",
					"Error, introduce un valor válido");
			try {
				new Partida(jugadores, jugadoresHumanos, menuTipoPartida(), 2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}

	}

	public static void menuJugadores() {
		boolean volver = false;
		String opcion;
		while (!volver) {
			System.out.println("Elige una opcion:\n" + "	1.-Ver Jugadores\n" + "	2.-Añadir Jugador\n"
					+ "	3.-Eliminar Jugador\n" + "	4.-Volver");
			opcion = entrada.next();
			switch (opcion) {
			case "1": {
				System.out.println("\nVer Jugadores");
				Ficheros.verJugadores();
				break;
			}
			case "2": {
				System.out.println("\nAñadir Jugador");
				Ficheros.anadirJugador();
				break;
			}
			case "3": {
				System.out.println("\nEliminar Jugador");
				Ficheros.eliminaJugador();
				break;
			}
			case "4": {
				System.out.println("\nVolver\n");
				volver = true;
				break;
			}
			default:
				System.out.println("\nIntroduce una opción válida");
			}
		}
	}

	public static int menuTipoPartida() {
		boolean valido = false;
		while (!valido) {
			System.out.println("¿Cuántas rondas quieres jugar?");
			System.out.println("	1.-Partida rápida (3 preguntas)");
			System.out.println("	2.-Partida corta (5 preguntas)");
			System.out.println("	3.-Partida normal (10 preguntas)");
			System.out.println("	4.-Partida larga (20 preguntas)");
			String opcion = entrada.next();
			switch (opcion) {
			case "1": {
				valido = true;
				return 3;
			}
			case "2": {
				valido = true;
				return 5;
			}
			case "3": {
				valido = true;
				return 10;
			}
			case "4": {
				valido = true;
				return 20;
			}
			default:
				System.out.println("Error. Por favor, introduce un valor válido");
				valido = false;
			}
		}
		return 0;
	}

}
