package co.edu.uao.yugioh.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Registro de un ataque: quién atacó a quién, el daño infligido y el resultado.
 * Cada registro se guarda como una línea del historial de combate.
 *
 * @author simonsati
 */
public class RegistroCombate {

    /** Formato de fecha y hora que se usa en el historial. */
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** Texto que se guarda como defensor cuando el ataque es directo. */
    public static final String SIN_DEFENSOR = "(ataque directo)";

    /** Texto que se guarda como afectado cuando nadie recibe daño. */
    public static final String NADIE = "Nadie";

    private final LocalDateTime fechaHora;
    private final int turno;
    private final String duelistaAtacante;
    private final String atacante;
    private final String duelistaDefensor;
    private final String defensor;
    private final TipoEnfrentamiento tipo;
    private final int danio;
    private final String afectado;
    private final String resultado;

    public RegistroCombate(LocalDateTime fechaHora, int turno, String duelistaAtacante, String atacante,
            String duelistaDefensor, String defensor, TipoEnfrentamiento tipo, int danio,
            String afectado, String resultado) {
        if (turno < 1) {
            throw new IllegalArgumentException("El turno debe ser mayor o igual a 1.");
        }
        if (danio < 0) {
            throw new IllegalArgumentException("El daño no puede ser negativo.");
        }
        this.fechaHora = Objects.requireNonNull(fechaHora, "La fecha es obligatoria.").truncatedTo(ChronoUnit.SECONDS);
        this.turno = turno;
        this.duelistaAtacante = obligatorio(duelistaAtacante, "duelista atacante");
        this.atacante = obligatorio(atacante, "monstruo atacante");
        this.duelistaDefensor = obligatorio(duelistaDefensor, "duelista defensor");
        this.defensor = obligatorio(defensor, "defensor");
        this.tipo = Objects.requireNonNull(tipo, "El tipo de enfrentamiento es obligatorio.");
        this.danio = danio;
        this.afectado = obligatorio(afectado, "afectado");
        this.resultado = obligatorio(resultado, "resultado");
    }

    private static String obligatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo '" + campo + "' del registro es obligatorio.");
        }
        return valor.trim();
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getFechaHoraTexto() {
        return FORMATO_FECHA.format(fechaHora);
    }

    public int getTurno() {
        return turno;
    }

    public String getDuelistaAtacante() {
        return duelistaAtacante;
    }

    public String getAtacante() {
        return atacante;
    }

    public String getDuelistaDefensor() {
        return duelistaDefensor;
    }

    public String getDefensor() {
        return defensor;
    }

    public TipoEnfrentamiento getTipo() {
        return tipo;
    }

    public int getDanio() {
        return danio;
    }

    public String getAfectado() {
        return afectado;
    }

    public String getResultado() {
        return resultado;
    }

    public boolean esAtaqueDirecto() {
        return tipo == TipoEnfrentamiento.ATAQUE_DIRECTO;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof RegistroCombate)) {
            return false;
        }
        RegistroCombate r = (RegistroCombate) otro;
        return turno == r.turno && danio == r.danio && fechaHora.equals(r.fechaHora)
                && duelistaAtacante.equals(r.duelistaAtacante) && atacante.equals(r.atacante)
                && duelistaDefensor.equals(r.duelistaDefensor) && defensor.equals(r.defensor)
                && tipo == r.tipo && afectado.equals(r.afectado) && resultado.equals(r.resultado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaHora, turno, duelistaAtacante, atacante, duelistaDefensor, defensor, tipo, danio,
                afectado, resultado);
    }

    @Override
    public String toString() {
        return "[" + getFechaHoraTexto() + "] Turno " + turno + " · " + atacante + " (" + duelistaAtacante + ") → "
                + defensor + " (" + duelistaDefensor + ") · " + tipo + " · daño " + danio + " a " + afectado
                + " · " + resultado;
    }
}
