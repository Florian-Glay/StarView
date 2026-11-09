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

    public Color colorOf(char symbol) {
        if (symbol == '.') return new Color(0, 0, 0, 0);
        Color color = colors.get(symbol);
        if (color == null) throw new IllegalArgumentException("Unknown color code: " + symbol);
        return color;
    }

    public boolean contains(char symbol) {
        return colors.containsKey(symbol);
    }

    public static Palette fromResource(String resource) throws IOException {
        try (InputStream stream = Palette.class.getResourceAsStream(resource)) {
            if (stream == null) throw new IOException("Missing palette: " + resource);
            Map<Character, Color> colors = new LinkedHashMap<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                String line;
                int number = 0;
                while ((line = reader.readLine()) != null) {
                    number++;
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;
                    if (line.length() != 9 || line.charAt(1) != '=' || line.charAt(2) != '#') {
                        throw new IOException("Invalid palette line " + number + ": " + line);
                    }
                    try {
                        char code = line.charAt(0);
                        if (code == '.' || colors.containsKey(code))
                            throw new IllegalArgumentException("Duplicate/reserved code");
                        colors.put(code, Color.decode(line.substring(2)));
                    } catch (IllegalArgumentException ex) {
                        throw new IOException("Invalid palette entry line " + number, ex);
                    }
                }
            }
            return new Palette(colors);
        }
    }
}
