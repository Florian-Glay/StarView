
package fr.starview.io;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Catalogue des couleurs des sprites StarView.
 * Chaque constante associe un caractère à une couleur RGB.
 */
public enum Colors {

    // Couleurs du fond spatial
    NEBULA_A('a', 0x190E37),
    NEBULA_B('b', 0x21133F),
    NEBULA_C('c', 0x2A164C),
    NEBULA_D('d', 0x341A5B),
    NEBULA_L('l', 0x421D69),
    NEBULA_M('m', 0x542472),
    NEBULA_N('n', 0x66307B),
    NEBULA_O('o', 0x723D87),
    NEBULA_P('p', 0x704C91),
    NEBULA_Q('q', 0x62609B),
    NEBULA_R('r', 0x5173A0),
    NEBULA_T('t', 0x408798),
    NEBULA_U('u', 0x31948D),
    NEBULA_V('v', 0x2FA486),
    NEBULA_W('w', 0x45B58B),
    NEBULA_X('x', 0x5DC696),
    NEBULA_Z('z', 0x7BD0A0),

    // Couleurs des lunes
    MOON_R('R',0xFF1717),
    MOON_D('D',0x950004),
    MOON_W('W',0xCF0404),

    // Couleurs de la planète
    PLANET_0('0', 0x162E57),
    PLANET_1('1', 0x243966),
    PLANET_2('2', 0x344D75),
    PLANET_3('3', 0x3A6583),
    PLANET_4('4', 0x377C87),
    PLANET_5('5', 0x2D978D),
    PLANET_6('6', 0x49AE97),
    PLANET_7('7', 0x8EC6AB),
    PLANET_8('8', 0xABDEBA),
    PLANET_9('9', 0xD5F3CA);

    private final char symbol;
    private final Color color;

    private static final Map<Character, Colors> BY_SYMBOL;

    // Initialisation de la table de correspondance
    static {
        Map<Character, Colors> values = new HashMap<>();

        for (Colors entry : Colors.values()) {
            if (values.put(entry.symbol, entry) != null) {
                throw new IllegalStateException(
                        "Duplicate color code: " + entry.symbol
                );
            }
        }

        BY_SYMBOL = Map.copyOf(values);
    }

    Colors(char symbol, int rgb) {
        this.symbol = symbol;
        this.color = new Color(rgb);
    }

    public Color color() {
        return color;
    }

    public static boolean contains(char symbol) {
        return BY_SYMBOL.containsKey(symbol);
    }

    public static Colors fromSymbol(char symbol) {
        Colors value = BY_SYMBOL.get(symbol);

        if (value == null) {
            throw new IllegalArgumentException(
                    "Unknown color code: " + symbol
            );
        }

        return value;
    }

    public static Color colorOf(char symbol) {

        // Le point dans les sprites signifie transparent
        if (symbol == '.') {
            return new Color(0, 0, 0, 0);
        }

        return fromSymbol(symbol).color();
    }
}
