package juegoComputadora;

public class Cpu extends Jugador {
	private int puntos;
	private String nombre;

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Cpu(int puntos, String nombre) {
		super();
		this.puntos = puntos;
		this.nombre = nombre;
	}
}
