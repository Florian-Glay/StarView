package fr.starview.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SpriteLoaderTest {

    private final SpriteLoader loader;

    SpriteLoaderTest() {
        loader = new SpriteLoader();
    }

    @Test
    void loadsAllRequiredSceneSpritesFromTextFiles() throws IOException {
        PixelSprite background = loader.fromResource("/sprites/nebula.txt");
        PixelSprite texture = loader.fromResource("/sprites/planet_texture.txt");
        PixelSprite moon = loader.fromResource("/sprites/moon.txt");
        assertEquals(128, background.width());
        assertEquals(128, background.height());
        assertEquals(64, texture.width());
        assertEquals(32, texture.height());
        assertEquals(9, moon.width());
        assertEquals(9, moon.height());
    }

    @Test
    void supportsTransparentPixelsAndCopiesDataDefensively() throws IOException {
        PixelSprite sprite = loader.fromResource("/sprites/valid-mini.txt");
        assertEquals('.', sprite.get(0, 1));
        char[][] pixels = sprite.copyPixels();
        pixels[0][0] = 'z';
        assertEquals('a', sprite.get(0, 0));
    }

    @Test
    void refusesMissingMalformedAndUnmappedSpriteFiles() {
        assertThrows(IOException.class, () -> loader.fromResource("/sprites/missing.txt"));
        assertThrows(IOException.class, () -> loader.fromResource("/sprites/invalid-row.txt"));
        assertThrows(IOException.class, () -> loader.fromResource("/sprites/unknown-color.txt"));
    }
}
