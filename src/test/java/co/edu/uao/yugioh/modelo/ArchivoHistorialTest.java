package co.edu.uao.yugioh.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Pruebas de la persistencia del historial en el archivo de texto (RF4).
 * Cada prueba usa una carpeta temporal para no tocar el archivo real.
 *
 * @author simonsati
 */
class ArchivoHistorialTest {

    @TempDir
    Path carpeta;

    private Path ruta;
    private ArchivoHistorial archivo;

    @BeforeEach
    void preparar() {
        ruta = carpeta.resolve(ArchivoHistorial.NOMBRE_ARCHIVO);
        archivo = new ArchivoHistorial(ruta);
    }

    private static RegistroCombate registro(int turno, String atacante, String defensor, int danio) {
        return new RegistroCombate(LocalDateTime.of(2026, 11, 19, 10, 30, 15), turno, "Yugi", atacante, "Kaiba",
                defensor, TipoEnfrentamiento.ATAQUE_VS_ATAQUE, danio, danio > 0 ? "Kaiba" : RegistroCombate.NADIE,
                "Destruido: " + defensor + "; Kaiba recibe " + danio + " de daño");
    }

    @Test
    @DisplayName("RF4: si el archivo no existe, el historial está vacío")
    void archivoInexistente() throws PersistenciaException {
        assertTrue(archivo.cargar().isEmpty());
        assertTrue(Files.notExists(ruta));
    }

    @Test
    @DisplayName("RF4: guardar crea el archivo con encabezado y una línea por ataque")
    void guardarEscribeLinea() throws Exception {
        archivo.guardar(registro(1, "Mago Oscuro", "Cyber Dragón", 400));

        List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
        assertEquals(3, lineas.size());
        assertTrue(lineas.get(0).startsWith("#"));
        assertTrue(lineas.get(1).startsWith("#"));
        assertEquals("2026-11-19 10:30:15 | 1 | Yugi | Mago Oscuro | Kaiba | Cyber Dragón | Ataque vs Ataque | 400"
                + " | Kaiba | Destruido: Cyber Dragón; Kaiba recibe 400 de daño", lineas.get(2));
    }

    @Test
    @DisplayName("RF4: lo que se escribe se puede leer igual (tildes y eñes incluidas)")
    void escribirYLeer() throws PersistenciaException {
        RegistroCombate primero = registro(1, "Mago Oscuro", "Cyber Dragón", 400);
        RegistroCombate segundo = new RegistroCombate(LocalDateTime.of(2026, 11, 19, 10, 31, 0), 2, "Kaiba",
                "Dragón Blanco de Ojos Azules", "Yugi", RegistroCombate.SIN_DEFENSOR, TipoEnfrentamiento.ATAQUE_DIRECTO,
                3000, "Yugi", "Yugi recibe 3000 de daño directo");
        archivo.guardar(primero);
        archivo.guardar(segundo);

        List<RegistroCombate> leidos = new ArchivoHistorial(ruta).cargar();

        assertEquals(List.of(primero, segundo), leidos);
        assertTrue(leidos.get(1).esAtaqueDirecto());
    }

    @Test
    @DisplayName("RF4: vaciar el historial deja solo el encabezado")
    void limpiar() throws Exception {
        archivo.guardar(registro(1, "Mago Oscuro", "Cyber Dragón", 400));

        archivo.limpiar();

        assertTrue(archivo.cargar().isEmpty());
        assertEquals(2, Files.readAllLines(ruta, StandardCharsets.UTF_8).size());
    }

    @Test
    @DisplayName("RF4: las líneas dañadas se ignoran y se cuentan")
    void lineasDanadas() throws Exception {
        archivo.guardar(registro(1, "Mago Oscuro", "Cyber Dragón", 400));
        Files.writeString(ruta, "texto sin formato\n2026-99-99 | x | y\n", StandardCharsets.UTF_8,
                java.nio.file.StandardOpenOption.APPEND);

        List<RegistroCombate> leidos = archivo.cargar();

        assertEquals(1, leidos.size());
        assertEquals(2, archivo.getLineasDescartadas());
    }

    @Test
    @DisplayName("RF4: un '|' dentro de un nombre no rompe el formato del archivo")
    void separadorEnNombres() throws PersistenciaException {
        archivo.guardar(registro(3, "Mago|Oscuro", "Elfa Mística", 0));

        RegistroCombate leido = archivo.cargar().get(0);

        assertEquals("Mago/Oscuro", leido.getAtacante());
        assertEquals(RegistroCombate.NADIE, leido.getAfectado());
    }

    @Test
    @DisplayName("RF4: se crea la carpeta si no existe y se ignora la marca BOM de Windows")
    void carpetaNuevaYMarcaBom() throws IOException, PersistenciaException {
        Path enSubcarpeta = carpeta.resolve("datos").resolve("historial.txt");
        new ArchivoHistorial(enSubcarpeta).guardar(registro(1, "Mago Oscuro", "Cyber Dragón", 400));
        String contenido = Files.readString(enSubcarpeta, StandardCharsets.UTF_8);
        Files.writeString(enSubcarpeta, "﻿" + contenido, StandardCharsets.UTF_8);

        ArchivoHistorial conBom = new ArchivoHistorial(enSubcarpeta);

        assertEquals(1, conBom.cargar().size());
        assertEquals(0, conBom.getLineasDescartadas());
    }

    @Test
    @DisplayName("RF4: por defecto se usa historial_combate.txt")
    void nombrePorDefecto() {
        ArchivoHistorial porDefecto = new ArchivoHistorial();

        assertEquals("historial_combate.txt", porDefecto.getRuta().getFileName().toString());
        assertTrue(porDefecto.getUbicacion().endsWith("historial_combate.txt"));
    }
}
