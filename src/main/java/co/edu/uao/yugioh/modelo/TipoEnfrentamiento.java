package co.edu.uao.yugioh.modelo;

/**
 * Casos de la tabla oficial de cálculo de daño de la Fase de Batalla.
 *
 * @author simonsati
 */
public enum TipoEnfrentamiento {

    ATAQUE_VS_ATAQUE("Ataque vs Ataque"),
    ATAQUE_VS_DEFENSA("Ataque vs Defensa"),
    ATAQUE_DIRECTO("Ataque directo");

    private final String etiqueta;

    TipoEnfrentamiento(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Busca el tipo de enfrentamiento a partir de su etiqueta (se usa al leer el historial).
     *
     * @param etiqueta texto como "Ataque vs Defensa"
     * @return el tipo correspondiente
     * @throws IllegalArgumentException si la etiqueta no existe
     */
    public static TipoEnfrentamiento desdeEtiqueta(String etiqueta) {
        if (etiqueta != null) {
            for (TipoEnfrentamiento tipo : values()) {
                if (tipo.etiqueta.equalsIgnoreCase(etiqueta.trim())) {
                    return tipo;
                }
            }
        }
        throw new IllegalArgumentException("Tipo de enfrentamiento desconocido: " + etiqueta);
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
