package juegoComputadora;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public Scanner entrada = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {

		Bbdd.iniciar();
		Menu.menuInicial();

	}

}
