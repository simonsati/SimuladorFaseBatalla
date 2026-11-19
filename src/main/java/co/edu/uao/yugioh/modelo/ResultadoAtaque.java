package co.edu.uao.yugioh.modelo;

import java.util.Objects;

/**
 * Resultado inmutable del cálculo de daño de un ataque: quién recibe daño y
 * qué monstruos se destruyen.
 *
 * @author simonsati
 */
public final class ResultadoAtaque {

    private final TipoEnfrentamiento tipo;
    private final int danioAlAtacante;
    private final int danioAlDefensor;
    private final boolean atacanteDestruido;
    private final boolean defensorDestruido;
    private final String descripcion;

    ResultadoAtaque(TipoEnfrentamiento tipo, int danioAlAtacante, int danioAlDefensor,
            boolean atacanteDestruido, boolean defensorDestruido, String descripcion) {
        this.tipo = Objects.requireNonNull(tipo);
        this.danioAlAtacante = danioAlAtacante;
        this.danioAlDefensor = danioAlDefensor;
        this.atacanteDestruido = atacanteDestruido;
        this.defensorDestruido = defensorDestruido;
        this.descripcion = Objects.requireNonNull(descripcion);
    }

    public TipoEnfrentamiento getTipo() {
        return tipo;
    }

    /**
     * @return daño que recibe el dueño del monstruo atacante
     */
    public int getDanioAlAtacante() {
        return danioAlAtacante;
    }

    /**
     * @return daño que recibe el duelista que defiende
     */
    public int getDanioAlDefensor() {
        return danioAlDefensor;
    }

    public boolean isAtacanteDestruido() {
        return atacanteDestruido;
    }

    public boolean isDefensorDestruido() {
        return defensorDestruido;
    }

    /**
     * @return explicación de la regla aplicada
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return el daño infligido en el ataque (en una batalla solo un duelista recibe daño)
     */
    public int getDanioInfligido() {
        return Math.max(danioAlAtacante, danioAlDefensor);
    }

    public boolean huboDanio() {
        return getDanioInfligido() > 0;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
