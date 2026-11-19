package co.edu.uao.yugioh.modelo;

import java.util.List;

/**
 * Contrato para guardar y recuperar el registro de cada ataque. El duelo
 * depende de esta interfaz y no de un archivo concreto, por eso en las pruebas
 * se puede reemplazar por una implementación en memoria.
 *
 * @author simonsati
 */
public interface HistorialCombate {

    /**
     * Agrega un registro al historial.
     */
    void guardar(RegistroCombate registro) throws PersistenciaException;

    /**
     * @return todos los registros guardados, en el orden en que ocurrieron
     */
    List<RegistroCombate> cargar() throws PersistenciaException;

    /**
     * Elimina todos los registros.
     */
    void limpiar() throws PersistenciaException;

    /**
     * @return descripción de dónde se guarda el historial (por ejemplo, la ruta del archivo)
     */
    String getUbicacion();
}
