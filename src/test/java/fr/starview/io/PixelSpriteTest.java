package fr.starview.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PixelSpriteTest {

    @Test
    void refusesInvalidOrNonRectangularPixels() {
        assertThrows(IllegalArgumentException.class, () -> new PixelSprite(new char[0][]));
        assertThrows(IllegalArgumentException.class, () -> new PixelSprite(new char[][]{{'a', 'b'}, {'c'}}));
        assertThrows(IllegalArgumentException.class, () -> new PixelSprite(new char[][]{{'a'}, null}));
    }

    @Test
    void constructorCopiesInputArray() {
        char[][] pixels = {{'a', 'b'}, {'c', '.'}};
        PixelSprite sprite = new PixelSprite(pixels);
        pixels[0][0] = 'z';
        assertEquals('a', sprite.get(0, 0));
        assertEquals(2, sprite.width());
        assertEquals(2, sprite.height());
    }
}
