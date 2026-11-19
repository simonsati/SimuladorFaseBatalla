package co.edu.uao.yugioh.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Duelista que participa en la Fase de Batalla. Administra sus Puntos de Vida
 * (LP) y los monstruos que controla.
 *
 * @author simonsati
 */
public class Jugador {

    /** LP con los que empieza cada duelista en un duelo oficial. */
    public static final int LP_OFICIALES = 8000;

    /** LP iniciales mínimos que acepta el simulador. */
    public static final int LP_MINIMOS = 100;

    /** LP iniciales máximos que acepta el simulador. */
    public static final int LP_MAXIMOS = 99999;

    /** Longitud máxima del nombre de un duelista. */
    public static final int LONGITUD_MAXIMA_NOMBRE = 20;

    /** Zonas de monstruo disponibles en el campo de cada duelista. */
    public static final int MAX_MONSTRUOS_EN_CAMPO = 5;

    private final String nombre;
    private final int lpIniciales;
    private int puntosVida;
    private final List<Monstruo> campo = new ArrayList<>();
    private final List<Monstruo> cementerio = new ArrayList<>();

    /**
     * Crea un duelista con los LP oficiales (8000).
     */
    public Jugador(String nombre) {
        this(nombre, LP_OFICIALES);
    }

    /**
     * Crea un duelista con un contador inicial de LP personalizado.
     *
     * @throws IllegalArgumentException si el nombre está vacío o los LP están fuera de rango
     */
    public Jugador(String nombre, int lpIniciales) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del duelista no puede estar vacío.");
        }
        if (nombre.trim().length() > LONGITUD_MAXIMA_NOMBRE) {
            throw new IllegalArgumentException(
                    "El nombre del duelista admite máximo " + LONGITUD_MAXIMA_NOMBRE + " caracteres.");
        }
        if (lpIniciales < LP_MINIMOS || lpIniciales > LP_MAXIMOS) {
            throw new IllegalArgumentException(
                    "Los LP iniciales deben estar entre " + LP_MINIMOS + " y " + LP_MAXIMOS + ".");
        }
        this.nombre = nombre.trim();
        this.lpIniciales = lpIniciales;
        this.puntosVida = lpIniciales;
    }

    /**
     * Resta daño a los Puntos de Vida. Los LP nunca quedan por debajo de cero.
     *
     * @param danio daño recibido (no puede ser negativo)
     * @return los LP que le quedan al duelista
     */
    public int recibirDanio(int danio) {
        if (danio < 0) {
            throw new IllegalArgumentException("El daño no puede ser negativo.");
        }
        puntosVida = Math.max(0, puntosVida - danio);
        return puntosVida;
    }

    /**
     * @return {@code true} si los LP llegaron a cero
     */
    public boolean estaDerrotado() {
        return puntosVida == 0;
    }

    /**
     * Pone un monstruo en el campo del duelista.
     *
     * @throws ReglaDeBatallaException si el campo está lleno o el monstruo ya está en él
     */
    public void invocar(Monstruo monstruo) throws ReglaDeBatallaException {
        Objects.requireNonNull(monstruo, "El monstruo es obligatorio.");
        if (campo.contains(monstruo)) {
            throw new ReglaDeBatallaException(monstruo.getNombre() + " ya está en el campo de " + nombre + ".");
        }
        if (campo.size() >= MAX_MONSTRUOS_EN_CAMPO) {
            throw new ReglaDeBatallaException(nombre + " ya tiene " + MAX_MONSTRUOS_EN_CAMPO
                    + " monstruos en el campo, que es el máximo permitido.");
        }
        campo.add(monstruo);
    }

    /**
     * @return {@code true} si el monstruo está en el campo de este duelista
     */
    public boolean controla(Monstruo monstruo) {
        return campo.contains(monstruo);
    }

    public boolean tieneMonstruosEnCampo() {
        return !campo.isEmpty();
    }

    /**
     * @return los monstruos del campo (lista de solo lectura)
     */
    public List<Monstruo> getCampo() {
        return Collections.unmodifiableList(campo);
    }

    /**
     * @return los monstruos destruidos (lista de solo lectura)
     */
    public List<Monstruo> getCementerio() {
        return Collections.unmodifiableList(cementerio);
    }

    void enviarAlCementerio(Monstruo monstruo) {
        if (campo.remove(monstruo)) {
            cementerio.add(monstruo);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getLpIniciales() {
        return lpIniciales;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    @Override
    public String toString() {
        return nombre + " (" + puntosVida + " LP)";
    }
}
