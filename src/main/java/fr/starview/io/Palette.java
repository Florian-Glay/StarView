package fr.starview.io;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Lookup table between one printable ASCII character and one RGB color.
 */
public record Palette(Map<Character, Color> colors) {
    public Palette(Map<Character, Color> colors) {
        if (colors == null || colors.isEmpty()) throw new IllegalArgumentException("Empty palette");
        this.colors = Collections.unmodifiableMap(new LinkedHashMap<>(colors));
    }

}
