package co.edu.uao.yugioh.modelo;

/**
 * Posiciones de batalla en las que puede estar un monstruo dentro del campo.
 *
 * @author simonsati
 */
public enum Posicion {

    ATAQUE("Ataque"),
    DEFENSA("Defensa");

    private final String etiqueta;

    Posicion(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * @return la posición contraria (Ataque pasa a Defensa y viceversa)
     */
    public Posicion opuesta() {
        return this == ATAQUE ? DEFENSA : ATAQUE;
    }

    /**
     * Convierte un texto como "Ataque" o "defensa" en la posición correspondiente.
     *
     * @param texto etiqueta o nombre de la posición
     * @return la posición encontrada
     * @throws IllegalArgumentException si el texto no corresponde a ninguna posición
     */
    public static Posicion desdeTexto(String texto) {
        if (texto != null) {
            String limpio = texto.trim();
            for (Posicion posicion : values()) {
                if (posicion.etiqueta.equalsIgnoreCase(limpio) || posicion.name().equalsIgnoreCase(limpio)) {
                    return posicion;
                }
            }
        }
        throw new IllegalArgumentException("Posición desconocida: " + texto);
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
