package co.edu.uao.yugioh.modelo;

import java.util.Objects;

/**
 * Monstruo con Puntos de Ataque (ATQ), Puntos de Defensa (DEF) y una posición
 * de batalla. Es abstracta porque cada subclase define su tipo y su detalle.
 *
 * @author simonsati
 */
public abstract class Monstruo extends Carta {

    /** Valor mínimo permitido para ATQ y DEF. */
    public static final int PUNTOS_MINIMOS = 0;

    /** Valor máximo permitido para ATQ y DEF. */
    public static final int PUNTOS_MAXIMOS = 5000;

    private int ataque;
    private int defensa;
    private Posicion posicion;

    protected Monstruo(String nombre, int ataque, int defensa, Posicion posicion) {
        super(nombre);
        this.ataque = validarPuntos(ataque, "ATQ");
        this.defensa = validarPuntos(defensa, "DEF");
        this.posicion = Objects.requireNonNull(posicion, "La posición del monstruo es obligatoria.");
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = validarPuntos(ataque, "ATQ");
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = validarPuntos(defensa, "DEF");
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = Objects.requireNonNull(posicion, "La posición del monstruo es obligatoria.");
    }

    public boolean estaEnAtaque() {
        return posicion == Posicion.ATAQUE;
    }

    /**
     * @return el ATQ si está en ataque o la DEF si está en defensa
     */
    public int getPuntosDePosicion() {
        return estaEnAtaque() ? ataque : defensa;
    }

    /**
     * @return la descripción (monstruo Normal) o el efecto (monstruo de Efecto)
     */
    public abstract String describir();

    @Override
    public String toString() {
        return getNombre() + " (ATQ " + ataque + " / DEF " + defensa + " · " + posicion + ")";
    }

    private static int validarPuntos(int puntos, String atributo) {
        if (puntos < PUNTOS_MINIMOS || puntos > PUNTOS_MAXIMOS) {
            throw new IllegalArgumentException(
                    "El " + atributo + " debe estar entre " + PUNTOS_MINIMOS + " y " + PUNTOS_MAXIMOS + ".");
        }
        return puntos;
    }
}
