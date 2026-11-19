package co.edu.uao.yugioh.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Persistencia del historial en un archivo de texto plano
 * ({@value #NOMBRE_ARCHIVO}). Cada ataque ocupa una línea con sus campos
 * separados por " | ". Las líneas que empiezan por '#' son comentarios.
 *
 * @author simonsati
 */
public class ArchivoHistorial implements HistorialCombate {

    /** Nombre del archivo que pide el enunciado. */
    public static final String NOMBRE_ARCHIVO = "historial_combate.txt";

    /** Separador de campos dentro de cada línea. */
    public static final String SEPARADOR = " | ";

    private static final int NUMERO_CAMPOS = 10;
    private static final String MARCA_BOM = "﻿";
    private static final String[] ENCABEZADO = {
        "# Historial de combate - Simulador de la Fase de Batalla Yu-Gi-Oh!",
        "# fecha y hora | turno | duelista atacante | monstruo atacante | duelista defensor | monstruo defensor"
        + " | enfrentamiento | daño | duelista afectado | resultado"
    };

    private final Path ruta;
    private int lineasDescartadas;

    /**
     * Usa el archivo {@value #NOMBRE_ARCHIVO} en la carpeta del proyecto.
     */
    public ArchivoHistorial() {
        this(Paths.get(NOMBRE_ARCHIVO));
    }

    /**
     * Usa un archivo en la ruta indicada (útil para las pruebas).
     */
    public ArchivoHistorial(Path ruta) {
        this.ruta = Objects.requireNonNull(ruta, "La ruta del archivo es obligatoria.");
    }

    @Override
    public void guardar(RegistroCombate registro) throws PersistenciaException {
        Objects.requireNonNull(registro, "El registro es obligatorio.");
        try {
            boolean archivoNuevo = Files.notExists(ruta) || Files.size(ruta) == 0;
            crearCarpeta();
            try (BufferedWriter escritor = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                if (archivoNuevo) {
                    escribirEncabezado(escritor);
                }
                escritor.write(aLinea(registro));
                escritor.newLine();
            }
        } catch (IOException ex) {
            throw new PersistenciaException("No se pudo escribir en " + getUbicacion() + ".", ex);
        }
    }

    @Override
    public List<RegistroCombate> cargar() throws PersistenciaException {
        List<RegistroCombate> registros = new ArrayList<>();
        lineasDescartadas = 0;
        if (Files.notExists(ruta)) {
            return registros;
        }
        try (BufferedReader lector = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.startsWith(MARCA_BOM)) {
                    linea = linea.substring(1);
                }
                if (linea.isBlank() || linea.startsWith("#")) {
                    continue;
                }
                try {
                    registros.add(desdeLinea(linea));
                } catch (IllegalArgumentException | DateTimeException ex) {
                    lineasDescartadas++;
                }
            }
        } catch (IOException ex) {
            throw new PersistenciaException("No se pudo leer " + getUbicacion() + ".", ex);
        }
        return registros;
    }

    @Override
    public void limpiar() throws PersistenciaException {
        try {
            crearCarpeta();
            try (BufferedWriter escritor = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {
                escribirEncabezado(escritor);
            }
        } catch (IOException ex) {
            throw new PersistenciaException("No se pudo vaciar " + getUbicacion() + ".", ex);
        }
    }

    @Override
    public String getUbicacion() {
        return ruta.toAbsolutePath().normalize().toString();
    }

    public Path getRuta() {
        return ruta;
    }

    /**
     * @return cuántas líneas dañadas se ignoraron en la última lectura
     */
    public int getLineasDescartadas() {
        return lineasDescartadas;
    }

    /**
     * Convierte un registro en una línea del archivo.
     */
    String aLinea(RegistroCombate registro) {
        return String.join(SEPARADOR,
                registro.getFechaHoraTexto(),
                String.valueOf(registro.getTurno()),
                sanear(registro.getDuelistaAtacante()),
                sanear(registro.getAtacante()),
                sanear(registro.getDuelistaDefensor()),
                sanear(registro.getDefensor()),
                registro.getTipo().getEtiqueta(),
                String.valueOf(registro.getDanio()),
                sanear(registro.getAfectado()),
                sanear(registro.getResultado()));
    }

    /**
     * Reconstruye un registro a partir de una línea del archivo.
     *
     * @throws IllegalArgumentException si la línea no tiene el formato esperado
     */
    RegistroCombate desdeLinea(String linea) {
        String[] campos = linea.split(Pattern.quote(SEPARADOR), -1);
        if (campos.length != NUMERO_CAMPOS) {
            throw new IllegalArgumentException("Se esperaban " + NUMERO_CAMPOS + " campos y hay " + campos.length);
        }
        LocalDateTime fecha = LocalDateTime.parse(campos[0].trim(), RegistroCombate.FORMATO_FECHA);
        int turno = Integer.parseInt(campos[1].trim());
        TipoEnfrentamiento tipo = TipoEnfrentamiento.desdeEtiqueta(campos[6]);
        int danio = Integer.parseInt(campos[7].trim());
        return new RegistroCombate(fecha, turno, campos[2], campos[3], campos[4], campos[5], tipo, danio,
                campos[8], campos[9]);
    }

    private void escribirEncabezado(BufferedWriter escritor) throws IOException {
        for (String linea : ENCABEZADO) {
            escritor.write(linea);
            escritor.newLine();
        }
    }

    private void crearCarpeta() throws IOException {
        Path carpeta = ruta.toAbsolutePath().getParent();
        if (carpeta != null) {
            Files.createDirectories(carpeta);
        }
    }

    /**
     * Evita que un nombre rompa el formato: quita el separador y los saltos de línea.
     */
    private static String sanear(String texto) {
        return texto.replace('|', '/').replace('\r', ' ').replace('\n', ' ').trim();
    }
}
