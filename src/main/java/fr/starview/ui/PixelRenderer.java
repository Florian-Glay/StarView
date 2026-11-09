package fr.starview.ui;

import fr.starview.io.Colors;
import fr.starview.io.PixelSprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

// Converti un TXT sprite symbolique vers une couleur et les dessinent avec des pixels sans anticrénelage
public final class PixelRenderer {

    // On Map le sprite avec un buffer
    private final Map<PixelSprite, BufferedImage> images = new IdentityHashMap<>();

    // Permet de pick la couleur grace à l'enum Colors dans /io
    public Color color(char symbol) {
        return Colors.colorOf(symbol);
    }

    //
    public void drawSprite(Graphics2D graphics, PixelSprite sprite, int x, int y, int width, int height) {
        Objects.requireNonNull(graphics, "graphics");
        Objects.requireNonNull(sprite, "sprite");
        if (width <= 0 || height <= 0) { // trow Exception si les tailles sont négatives
            throw new IllegalArgumentException("Sprite display dimensions must be positive");
        }
        BufferedImage image = images.computeIfAbsent(sprite, this::rasterize);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        graphics.drawImage(image, x, y, width, height, null);
    }

    private BufferedImage rasterize(PixelSprite sprite) {
        BufferedImage image = new BufferedImage(sprite.width(), sprite.height(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < sprite.height(); y++) {
            for (int x = 0; x < sprite.width(); x++) {
                image.setRGB(x, y, color(sprite.get(x, y)).getRGB());
            }
        }
        return image;
    }
}
