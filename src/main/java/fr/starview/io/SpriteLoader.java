package fr.starview.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/** Reads sprites of the form: "width height" followed by height ASCII rows. */
public final class SpriteLoader {

    public PixelSprite fromResource(String resource) throws IOException {
        try (InputStream stream = getClass().getResourceAsStream(resource)) {
            if (stream == null) throw new IOException("Missing sprite: " + resource);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                String first = reader.readLine();
                if (first == null || !first.matches("[0-9]+ [0-9]+")) throw new IOException("Invalid sprite header: " + resource);
                String[] dimensions = first.split(" ");
                int w, h;
                try {
                    w = Integer.parseInt(dimensions[0]);
                    h = Integer.parseInt(dimensions[1]);
                } catch (NumberFormatException ex) { throw new IOException("Invalid sprite dimensions", ex); }
                if (w < 1 || h < 1 || w > 512 || h > 512) throw new IOException("Sprite dimensions out of range");
                char[][] pixels = new char[h][w];
                for (int y = 0; y < h; y++) {
                    String row = reader.readLine();
                    if (row == null || row.length() != w) throw new IOException("Invalid sprite row " + y + " in " + resource);
                    for (int x = 0; x < w; x++) {
                        char c = row.charAt(x);
                        if (c != '.' && !Colors.contains(c)) {
                            throw new IOException(
                                    "Unknown color '" + c + "' in " + resource
                            );
                        }
                        pixels[y][x] = c;
                    }
                }
                return new PixelSprite(pixels);
            }
        }
    }
}
