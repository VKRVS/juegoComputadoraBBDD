package juegoComputadora;

import java.io.IOException;
import java.util.ArrayList;

public class Partida {

	int jugadores;
	ArrayList<Jugador> participantes;
	int jugadoresHumanos;
	int rondas;
	// 1=practica, 2=normal
	int tipo;

	public Partida(int jugadores, int jugadoresHumanos, int rondas, int tipo) throws IOException {
		participantes = new ArrayList<Jugador>();
		this.jugadores = jugadores;
		this.jugadoresHumanos = jugadoresHumanos;
		this.rondas = rondas;
		this.tipo = tipo;
		if ((jugadores - jugadoresHumanos) > 0) {
			anadirJugadorCPU();
		}
		JugarPartida();
	}

	public void JugarPartida() {
		// Partida práctica
		if (tipo == 1) {
			for (int i = 0; i < rondas; i++) {
				System.out.println("Pregunta #" + (i + 1));
				Ejecucion(true);
			}
		}
		// Partida normal
		if (tipo == 2) {
			for (int i = 0; i < jugadoresHumanos; i++) {
				String nombre;
				System.out.println("Introduce el nombre del jugador #" + (i + 1));
				nombre = Extra.entrada.next();
				if (Ficheros.buscarCoincidencia(nombre) != 0) {
					System.out.println("Jugador encontrado en el sistema");
					participantes.add(new PJ(nombre, 0));
				} else {
					System.out.println("El jugador no existe en el sistema. Creando jugador...");
					Ficheros.anadirJugadorNombre(nombre);
					participantes.add(new PJ(nombre, 0));
				}
			}
			System.out.println("Comienza el juego:");

			for (int i = 0; i < (rondas); i++) {
				System.out.println();
				System.out.println("Ronda #" + (i + 1));
				for (int j = 0; j < participantes.size(); j++) {
					System.out.println();
					System.out.println();
					System.out.println("Pregunta para " + participantes.get(j).getNombre());
					if (participantes.get(j) instanceof PJ) {
						if (Ejecucion(true)) {
							participantes.get(j).setPuntos((participantes.get(j).getPuntos()) + 1);
						}
					} else {
						if (Ejecucion(false)) {
							participantes.get(j).setPuntos((participantes.get(j).getPuntos()) + 1);
						}
					}
				}
				for (int k = 0; k < participantes.size(); k++) {
					System.out.println("El jugador " + participantes.get(k).getNombre() + " tiene acumulados "
							+ participantes.get(k).getPuntos() + " puntos");
				}
			}

		}
		for (int i = 0; i < participantes.size(); i++) {
			if (participantes.get(i) instanceof PJ) {
				Ficheros.anadirPuntos(participantes.get(i).getNombre(), participantes.get(i).getPuntos());
			}
		}
		Ficheros.partidaAlHistorico(participantes);
		System.out.println();
		System.out.println("¡FIN DE LA PARTIDA!");
		System.out.println();
	}

	public boolean Ejecucion(boolean tipo) {
		// false indica que participa cpu
		switch (Extra.aleatorio(1, 3)) {
		case 1: {
			System.out.println("Categoria: Mates");
			System.out.println();
			if (tipo) {
				return (new Mates().acertada);
			} else {
				return (new Mates(1).acertada);
			}

		}
		case 2: {
			System.out.println("Categoria: Letras");
			System.out.println();
			if (tipo) {
				return (new Letras().acertada);
			} else {
				return (new Letras(1).acertada);
			}
		}
		case 3: {
			System.out.println("Categoria: Ingles");
			System.out.println();
			if (tipo) {
				return (new Ingles().acertada);
			} else {
				return (new Ingles(1).acertada);
			}
		}
		}
		return false;
	}

	//
	public void anadirJugadorCPU() {
		for (int i = 0; i < (jugadores - jugadoresHumanos); i++) {
			participantes.add(new Cpu(0, ("Cpu" + (i + 1))));
		}
	}
}
