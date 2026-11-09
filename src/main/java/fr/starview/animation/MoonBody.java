package fr.starview.animation;

import fr.starview.io.PixelSprite;
import fr.starview.ui.PixelRenderer;
import java.awt.Graphics2D;

/** Orbiting moon; depth determines if it is drawn in front of or behind the planet. */
public final class MoonBody extends SpaceBody {
    private final PixelSprite sprite;
    private final int cx, cy, orbitX, orbitY, size;
    private final double phase, speed;
    public MoonBody(PixelSprite sprite, int cx, int cy, int orbitX, int orbitY, int size, double phase, double speed) {
        this.sprite = sprite;
        this.cx = cx;
        this.cy = cy;
        this.orbitX = orbitX;
        this.orbitY = orbitY;
        this.size = size;
        this.phase = phase;
        this.speed = speed;
    }
    private double angle(double time) { return phase + time * speed; }
    @Override public double depth(double time) { return Math.sin(angle(time)); }
    @Override public void draw(Graphics2D g, PixelRenderer renderer, double time) {
        double a = angle(time);
        int px = cx + (int) Math.round(orbitX * Math.cos(a));
        int py = cy + (int) Math.round(orbitY * Math.sin(a));
        renderer.drawSprite(g, sprite, px - size/2, py - size/2, size, size);
    }
}
