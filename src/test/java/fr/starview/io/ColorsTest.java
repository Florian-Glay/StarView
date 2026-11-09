
package fr.starview.io;

import org.junit.jupiter.api.Test;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class ColorsTest {

    @Test
    void resolvesExistingSpriteColorCodes() {
        assertEquals(
                new Color(0x190E37),
                Colors.colorOf('a')
        );

        assertEquals(
                new Color(0xFF1717),
                Colors.colorOf('R')
        );

        assertEquals(
                new Color(0xD5F3CA),
                Colors.colorOf('9')
        );

        assertEquals(
                Colors.NEBULA_A,
                Colors.fromSymbol('a')
        );
    }

    @Test
    void treatsDotAsTransparent() {
        assertEquals(0, Colors.colorOf('.').getAlpha());
    }

    @Test
    void rejectsUnknownCodes() {
        assertFalse(Colors.contains('~'));

        assertThrows(
                IllegalArgumentException.class,
                () -> Colors.colorOf('~')
        );
    }
}

