package juegoComputadora;

import java.util.ArrayList;

public class Mates extends Pregunta {

	ArrayList<Integer> nums;
	ArrayList<String> operaciones;
	ArrayList<String> completo;
	int resultadoTotal = 0;
	boolean acertada;
	// QUITA LOS STATIC CUANDO TERMINES DE HACER PRUEBAS EN MAIN

	public Mates() {
		nums = new ArrayList<Integer>();
		operaciones = new ArrayList<String>();
		completo = new ArrayList<String>();
		ejecucionCompleta();
	}

	public Mates(int cpu) {
		nums = new ArrayList<Integer>();
		operaciones = new ArrayList<String>();
		completo = new ArrayList<String>();
		generaNum();
		generaOperac(nums);
		preguntaCompleta(nums, operaciones);
		preguntaCompletaNum(nums, operaciones);
		System.out.println();
		acertada = comprueba(String.valueOf(resultadoTotal), resultadoTotal);
	}

	// Método que genera los números posibles
	public void generaNum() {
		// estas variables representan los posibles valores de la cantidad de numeros a
		// calcular (rango) asi como sus valores (valor)
		int rangoMin = 4;
		int rangoMax = 8;
		int valorMin = 2;
		int valorMax = 12;
		// ArrayList<Integer> nums = new ArrayList<Integer>();
		for (int i = 0; i < Extra.aleatorio(rangoMin, rangoMax); i++) {
			nums.add(Extra.aleatorio(valorMin, valorMax));
		}

	}

	// Método que genera las operaciones
	public void generaOperac(ArrayList<Integer> nums) {
		// ArrayList<String> operaciones = new ArrayList<String>();
		for (int i = 0; i < (nums.size() - 1); i++) {
			switch (Extra.aleatorio(1, 3)) {
			case 1: {
				operaciones.add("+");
				break;
			}
			case 2: {
				operaciones.add("-");
				break;
			}
			case 3: {
				operaciones.add("*");
				break;
			}
			}
		}
	}

	// Este método genera el enunciado de la pregunta pasandole el array de numeros
	// y operaciones
	public void preguntaCompleta(ArrayList<Integer> nums, ArrayList<String> operac) {
		// System.out.println("Tamaño nums: " + nums.size() + "\nTamaño operac: " +
		// operac.size());
		// System.out.println("Nums: " + nums + "\nOperac: " + operac);
		// El resultado será volcado en el array "completo"
		// ArrayList<String> completo = new ArrayList<String>();
		// llevo una cuenta individual de cada tipo para ir alternando entre los arrays
		// los valores asi como usando una puerta lógica que los alterna
		boolean puerta = false;
		int contadornums = 0;
		int contadoroperac = 0;
		for (int i = 0; i < (nums.size() + operac.size()); i++) {

			if (!puerta) {
				completo.add(nums.get(contadornums).toString());
				puerta = true;
				contadornums++;
			} else {
				completo.add(operac.get(contadoroperac));
				puerta = false;
				contadoroperac++;
			}
		}
		// "Limpio" el String para quitarle las comas del tostring

		// ESTO ESTA AQUI PORQUE SINO JODE EL ARRAY FINAL!!!
		ArrayList<Integer> completoNum = (ArrayList<Integer>) nums.clone();
		ArrayList<String> completoOper = (ArrayList<String>) operac.clone();
		// preguntaCompletaNum(completoNum, completoOper);
		// return completo.toString().replaceAll("[\\[\\],]", "");
		System.out.println((completo.toString().replaceAll("[\\[\\],]", "") + " = ¿?"));
	}

	// Este método calcula el resultado correcto en base al enunciado
	public void preguntaCompletaNum(ArrayList<Integer> nums, ArrayList<String> operac) {
		int resultado = 0;
		while (operac.contains("*")) {
			int posicion = operac.indexOf("*");
			resultado = ((nums.get(posicion)) * (nums.get(posicion + 1)));
			operac.remove(posicion);
			nums.set(posicion, resultado);
			nums.remove((posicion + 1));
			// System.out.println("resultado Multi: " + resultado);
			// System.out.println("Array Multi: " + nums);
		}
		while (operac.iterator().hasNext()) {
			switch (operac.get(0)) {
			case "+": {
				resultado = ((nums.get(0)) + (nums.get(1)));
				operac.remove(0);
				nums.set(0, resultado);
				nums.remove((0 + 1));
				break;
			}
			case "-": {
				resultado = ((nums.get(0)) - (nums.get(1)));
				operac.remove(0);
				nums.set(0, resultado);
				nums.remove((0 + 1));
				break;
			}

			}
		}
		// Muestra la respuesta:
		// System.out.println("Respuesta: " + nums);
		// System.out.println("array oper: " + operac);
		resultadoTotal = nums.get(0);
	}

	// Método que comprueba si has acertado
	public boolean comprueba(String introducido, int correcto) {
		if (introducido.matches("-?\\d+(\\.\\d+)?")) {

			if (Integer.parseInt(introducido) == correcto) {
				System.out.println("¡Has acertado!\nLa solución es: " + correcto);
				System.out.println();
				return true;
			} else {
				System.out.println("¡Has fallado!\nLa solución era: " + correcto);
				System.out.println();
				return false;
			}
		} else {
			System.out.println("¡Has fallado!\nLa solución era: " + correcto);
			System.out.println();
			return false;
		}
	}

	// Ejecuta toda la secuencia
	public void ejecucionCompleta() {
		generaNum();
		generaOperac(nums);
		preguntaCompleta(nums, operaciones);
		preguntaCompletaNum(nums, operaciones);
		System.out.println();
		System.out.println("Introduce el resultado de la operación:");
		// Acierto=true, fallo=false
		acertada = comprueba((Menu.entrada.next()), resultadoTotal);
	}
}
