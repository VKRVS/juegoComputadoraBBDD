package juegoComputadora;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Ingles extends Pregunta {

	static String ingles = "src/juegoComputadora/ingles.txt";
	String letraBuena;
	boolean acertada;

	public Ingles() {
		ejecucionCompleta();
	}

	public Ingles(int cpu) {
		enunciado();
		System.out.println();
		acertada = comprueba(Extra.letraAleatoria(), letraBuena);
	}

	// Este método devuelve la cantidad de líneas del fichero
	public int cantidadLineas() {
		File archivoLector = new File(ingles);
		Scanner archivo = null;
		try {
			archivo = new Scanner(archivoLector);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int lineas = 0;
		while (archivo.hasNext()) {
			archivo.nextLine();
			lineas++;
		}
		archivo.close();
		// System.out.println("cantidad de líneas: " + lineas);
		return lineas;
	}

	// Este método devuelve una línea aleatoria que se corresponde con un enunciado
	public int lineaAleatoria() {
		int num = 1;
		while ((num % 5 != 0) || (num == 5000)) {
			num = Extra.aleatorio(0, cantidadLineas());
		}
		// System.out.println("línea número: " + (num + 1));
		return num;
	}

	// Este método pinta por pantalla el enunciado con las opciones desordenadas y
	// devuelve la letra correcta
	public void enunciado() {
		int enunciado = lineaAleatoria();
		// String letraBuena = null;
		try {
			System.out.println(Files.readAllLines(Paths.get(ingles)).get(enunciado));
			String buena = Files.readAllLines(Paths.get(ingles)).get(enunciado + 1);
			String mala1 = Files.readAllLines(Paths.get(ingles)).get(enunciado + 2);
			String mala2 = Files.readAllLines(Paths.get(ingles)).get(enunciado + 3);
			String mala3 = Files.readAllLines(Paths.get(ingles)).get(enunciado + 4);
			ArrayList<String> opciones = new ArrayList<String>();
			opciones.add(buena);
			opciones.add(mala1);
			opciones.add(mala2);
			opciones.add(mala3);
			int opcion;
			while (!opciones.isEmpty()) {
				opcion = Extra.aleatorio(0, (opciones.size() - 1));
				switch (opciones.size()) {
				case 4: {
					System.out.println("	A. - " + opciones.get(opcion));
					if (opciones.get(opcion).equals(buena)) {
						letraBuena = "A";
					}
					break;
				}
				case 3: {
					System.out.println("	B. - " + opciones.get(opcion));
					if (opciones.get(opcion).equals(buena)) {
						letraBuena = "B";
					}
					break;
				}
				case 2: {
					System.out.println("	C. - " + opciones.get(opcion));
					if (opciones.get(opcion).equals(buena)) {
						letraBuena = "C";
					}
					break;
				}
				case 1: {
					System.out.println("	D. - " + opciones.get(opcion));
					if (opciones.get(opcion).equals(buena)) {
						letraBuena = "D";
					}
					break;
				}
				}
				opciones.remove(opcion);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return letraBuena;

	}

	public boolean comprueba(String introducido, String correcto) {
		if (introducido.equalsIgnoreCase(correcto)) {
			System.out.println("¡Has acertado!\nLa solución correcta es la " + letraBuena);
			System.out.println();
			return true;
		} else {
			System.out.println("¡Has fallado!\nLa solución correcta era la " + letraBuena);
			System.out.println();
			return false;
		}
	}

	public void ejecucionCompleta() {
		enunciado();
		System.out.println();
		System.out.println("Introduce la respuesta correcta:");
		// Acierto=true, fallo=false
		acertada = comprueba(Menu.entrada.next(), letraBuena);
	}

}
