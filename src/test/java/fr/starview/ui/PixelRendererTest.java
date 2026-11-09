
package fr.starview.ui;

import fr.starview.io.Colors;
import fr.starview.io.PixelSprite;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class PixelRendererTest {

    @Test
    void appliesEnumColorsAndTransparency() {

        PixelRenderer renderer = new PixelRenderer();

        PixelSprite sprite = new PixelSprite(
                new char[][]{
                        {'a', 'b'},
                        {'.', 'a'}
                }
        );

        BufferedImage target = new BufferedImage(
                4, 4, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = target.createGraphics();

        try {
            renderer.drawSprite(g, sprite, 0, 0, 4, 4);
        } finally {
            g.dispose();
        }

        assertEquals(
                Colors.colorOf('a').getRGB(),
                target.getRGB(0, 0)
        );

        assertEquals(
                Colors.colorOf('b').getRGB(),
                target.getRGB(3, 0)
        );

        assertEquals(0, target.getRGB(0, 3));

        assertEquals(
                Colors.colorOf('a').getRGB(),
                target.getRGB(3, 3)
        );
    }

    @Test
    void refusesNonPositiveDrawSize() {

        PixelRenderer renderer = new PixelRenderer();

        PixelSprite sprite =
                new PixelSprite(new char[][]{{'a'}});

        BufferedImage image = new BufferedImage(
                4, 4, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        try {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> renderer.drawSprite(
                            g, sprite, 0, 0, 0, 4
                    )
            );
        } finally {
            g.dispose();
        }
    }
}
