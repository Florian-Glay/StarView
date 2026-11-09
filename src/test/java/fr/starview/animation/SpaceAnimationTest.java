package fr.starview.animation;

import fr.starview.io.PixelSprite;
import fr.starview.io.SpriteLoader;
import fr.starview.ui.PixelRenderer;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SpaceAnimationTest {

    @Test
    void moonDepthChangesFromFrontToBackWhilePlanetStaysAtDepthZero() {
        PixelSprite moonImage = new PixelSprite(new char[][]{{'a'}});
        MoonBody moon = new MoonBody(moonImage, 128, 128, 80, 30, 10, 0, 1);
        PlanetBody planet = new PlanetBody(moonImage, 128, 128, 30);
        assertEquals(0.0, planet.depth(20), 1e-12);
        assertTrue(moon.depth(Math.PI / 2) > planet.depth(0));
        assertTrue(moon.depth(3 * Math.PI / 2) < planet.depth(0));
    }

    @Test
    void textureRotationActuallyChangesPlanetSurface() throws IOException {
        SpriteLoader loader = new SpriteLoader();
        PlanetBody planet = new PlanetBody(
                loader.fromResource("/sprites/planet_texture.txt"), 128, 128, 47);
        PixelRenderer renderer = new PixelRenderer();

        BufferedImage first = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        BufferedImage later = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        drawBody(first, planet, renderer, 0);
        drawBody(later, planet, renderer, 45);
        assertNotEquals(first.getRGB(128, 128), later.getRGB(128, 128));
    }

    @Test
    void loadedSceneRendersAFullBackgroundAndChangesOverTime() throws IOException {
        SpriteLoader loader = new SpriteLoader();
        SpaceScene scene = new SpaceScene(
                loader.fromResource("/sprites/nebula.txt"),
                loader.fromResource("/sprites/planet_texture.txt"),
                loader.fromResource("/sprites/moon.txt"));
        PixelRenderer renderer = new PixelRenderer();
        BufferedImage first = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        BufferedImage later = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);

        drawScene(first, scene, renderer, 0);
        drawScene(later, scene, renderer, 400);

        assertEquals(255, (first.getRGB(0, 0) >>> 24)); // opaque sky
        int changedPixels = 0;
        for (int y = 0; y < 256; y++) {
            for (int x = 0; x < 256; x++) {
                if (first.getRGB(x, y) != later.getRGB(x, y)) {
                    changedPixels++;
                }
            }
        }
        assertTrue(changedPixels > 100, "Scene must animate over time");
    }

    private static void drawBody(BufferedImage image, SpaceBody body,
                                 PixelRenderer renderer, double time) {
        Graphics2D g = image.createGraphics();
        try { body.draw(g, renderer, time); }
        finally { g.dispose(); }
    }

    private static void drawScene(BufferedImage image, SpaceScene scene,
                                  PixelRenderer renderer, long frame) {
        Graphics2D g = image.createGraphics();
        try { scene.draw(g, renderer, frame); }
        finally { g.dispose(); }
    }
}
