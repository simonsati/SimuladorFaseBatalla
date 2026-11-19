package co.edu.uao.yugioh.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Monstruos de ejemplo para probar el simulador rápidamente. Los textos de
 * descripción y efecto son resúmenes propios.
 *
 * @author simonsati
 */
public final class CatalogoMonstruos {

    private CatalogoMonstruos() {
        // Clase utilitaria: solo tiene métodos estáticos.
    }

    /**
     * @return monstruos para el campo del primer duelista del duelo de ejemplo
     */
    public static List<Monstruo> crearMazoDeYugi() {
        List<Monstruo> mazo = new ArrayList<>();
        mazo.add(new MonstruoNormal("Mago Oscuro", 2500, 2100, Posicion.ATAQUE,
                "Hechicero de túnica oscura, maestro de la magia de ataque."));
        mazo.add(new MonstruoNormal("Guardián Celta", 1400, 1200, Posicion.ATAQUE,
                "Espadachín élfico de reflejos muy rápidos."));
        mazo.add(new MonstruoNormal("Elfa Mística", 800, 2000, Posicion.DEFENSA,
                "Protege a sus aliados con barreras de luz."));
        mazo.add(new MonstruoEfecto("Kuriboh", 300, 200, Posicion.DEFENSA,
                "Puede descartarse para evitar daño de batalla (no se simula)."));
        return mazo;
    }

    /**
     * @return monstruos para el campo del segundo duelista del duelo de ejemplo
     */
    public static List<Monstruo> crearMazoDeKaiba() {
        List<Monstruo> mazo = new ArrayList<>();
        mazo.add(new MonstruoNormal("Dragón Blanco de Ojos Azules", 3000, 2500, Posicion.ATAQUE,
                "Dragón legendario de enorme poder destructivo."));
        mazo.add(new MonstruoNormal("Soldado Gigante de Piedra", 1300, 2000, Posicion.DEFENSA,
                "Guerrero de roca capaz de resistir golpes muy fuertes."));
        mazo.add(new MonstruoEfecto("Cyber Dragón", 2100, 1600, Posicion.ATAQUE,
                "Puede invocarse de forma especial si solo el rival tiene monstruos (no se simula)."));
        return mazo;
    }

    /**
     * @return catálogo completo de monstruos de ejemplo (para reportes)
     */
    public static List<Monstruo> crearCatalogo() {
        List<Monstruo> catalogo = new ArrayList<>();
        catalogo.addAll(crearMazoDeYugi());
        catalogo.addAll(crearMazoDeKaiba());
        catalogo.add(new MonstruoNormal("Dragón Negro de Ojos Rojos", 2400, 2000, Posicion.ATAQUE,
                "Dragón feroz con un potencial que crece en cada batalla."));
        catalogo.add(new MonstruoNormal("Calavera Convocada", 2500, 1200, Posicion.ATAQUE,
                "Demonio que ataca con descargas de energía."));
        catalogo.add(new MonstruoNormal("Gaia el Caballero Feroz", 2300, 2100, Posicion.ATAQUE,
                "Caballero que avanza a toda velocidad sobre su corcel."));
        catalogo.add(new MonstruoEfecto("Jinzo", 2400, 1500, Posicion.ATAQUE,
                "Mientras está boca arriba anula las cartas trampa (no se simula)."));
        return catalogo;
    }
}
