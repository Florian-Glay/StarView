package fr.starview.io;

/** Immutable sprite read from a text resource. '.' indicates transparency. */
public final class PixelSprite {
    private final int width;
    private final int height;
    private final char[][] pixels;

    public PixelSprite(char[][] source) {
        if (source == null || source.length == 0 || source[0].length == 0) {
            throw new IllegalArgumentException("Invalid sprite dimensions");
        }
        height = source.length;
        width = source[0].length;
        pixels = new char[height][width];
        for (int y = 0; y < height; y++) {
            if (source[y] == null || source[y].length != width) {
                throw new IllegalArgumentException("Non-rectangular sprite");
            }
            pixels[y] = source[y].clone();
        }
    }

    public int width() { return width; }
    public int height() { return height; }
    public char get(int x, int y) { return pixels[y][x]; }
    public char[][] copyPixels() {
        char[][] copy = new char[height][];
        for (int y = 0; y < height; y++) copy[y] = pixels[y].clone();
        return copy;
    }
}
