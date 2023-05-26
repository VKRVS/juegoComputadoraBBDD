package juegoComputadora;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Scanner;


public class Letras extends Pregunta {

	String diccionario ="diccionario.txt";
	String palabra;
	String palabraOculta;
	boolean acertada;

	public Letras() {
		ejecucionCompleta();
	};
	
	public Letras(int cpu) {
		leerPalabra();
		muestraPalabra();
		System.out.println();
		acertada= comprueba("", palabra);
	};

	// Este método lee la cantidad de líneas del archivo
	public int cantidadLineas() {
		File archivoLector = new File("src/juegoComputadora/" + diccionario);
		Scanner archivo = null;
		try {
			archivo = new Scanner(archivoLector);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int lineas = 0;
		while (archivo.hasNext()) {
			archivo.next();
			lineas++;
		}
		archivo.close();
		return lineas;
	}

	// Este método devuelve una palabra aleatoria del fichero
	public void leerPalabra() {
		File archivoLector = new File("src/juegoComputadora/" + diccionario);
		Scanner archivo = null;
		try {
			archivo = new Scanner(archivoLector);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int num = Extra.aleatorio(1, cantidadLineas());
		//System.out.println("línea: " + num);
		//String palabra = null;
		for (int i = 0; i < num; i++) {
			palabra = archivo.next();
		}
		archivo.close();
		//return palabra;
	}

	// Este método da la palabra lista para mostrar y resolver
	public void muestraPalabra() {
		int cantidadOcultas = 3;
		palabraOculta=palabra;
		//System.out.println("La palabra es: " + palabra);
		int ocultas = palabra.length() / cantidadOcultas;
		int cantidad = 0;
		int posicion;
		//System.out.println("cantidad de letras que se ocultarán: " + ocultas);
		char[] array = palabraOculta.toCharArray();
		//System.out.println(array);
		while (cantidad < ocultas) {
			posicion = Extra.aleatorio(0, (array.length - 1));
			if (array[posicion] != '*') {
				array[posicion] = '*';
				cantidad++;
			}
			//System.out.println(array);
		}
		System.out.println(array);
		//return palabra;
	}

	// Este método comprueba que hayas acertado y devuelve true si acertaste
	public boolean comprueba(String respuesta, String correcto) {
		//normalizer elimina los acentos en la nueva variable "normalizado"
		String correctoNormalizado = Normalizer.normalize(correcto, Normalizer.Form.NFD);
        correctoNormalizado = correctoNormalizado.replaceAll("\\p{M}", "");
		if (correctoNormalizado.equalsIgnoreCase(respuesta)) {
			System.out.println("¡Has acertado!\nLa respuesta era: " + correcto);
			System.out.println();
			return true;
		} else {
			System.out.println("¡Has fallado!\nLa respuesta correcta era: " + correcto);
			System.out.println();
			return false;
		}
	}
	
	public void ejecucionCompleta() {
		//cantidadLineas();
		leerPalabra();
		muestraPalabra();
		System.out.println();
		System.out.println("Introduce la palabra misteriosa:");
		// Acierto=true, fallo=false
		acertada= comprueba(Menu.entrada.next(), palabra);
	}
}
