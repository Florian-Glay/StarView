package fr.starview.animation;

import fr.starview.io.PixelSprite;
import fr.starview.ui.PixelRenderer;
import java.awt.Color;
import java.awt.Graphics2D;

/** Animated spherical projection of an ASCII texture map, with pixelated lighting. */
public final class PlanetBody extends SpaceBody {
    private final PixelSprite texture;
    private final int cx, cy, radius;
    public PlanetBody(PixelSprite texture, int cx, int cy, int radius) {
        this.texture = texture;
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
    }
    @Override public double depth(double time) { return 0.0; }

    @Override
    public void draw(Graphics2D g, PixelRenderer renderer, double time) {
        // Soft blocky glow and black-purple contour, fully inside the logical framebuffer.
        g.setColor(new Color(142, 238, 217, 23));
        g.fillOval(cx - radius - 7, cy - radius - 7, radius * 2 + 14, radius * 2 + 14);
        g.setColor(new Color(20, 25, 63, 170));
        g.fillOval(cx - radius - 2, cy - radius - 2, radius * 2 + 4, radius * 2 + 4);
        for (int dy = -radius; dy <= radius; dy += 2) {
            for (int dx = -radius; dx <= radius; dx += 2) {
                double nx = (dx + 0.5) / radius;
                double ny = (dy + 0.5) / radius;
                double r2 = nx * nx + ny * ny;
                if (r2 > 1.0) continue;
                double longitude = Math.atan2(nx, Math.sqrt(Math.max(0, 1 - r2)));
                double latitude = Math.asin(Math.max(-1, Math.min(1, ny)));
                int tx = Math.floorMod((int) ((longitude / (2 * Math.PI) + 0.5 + time * 0.011) * texture.width()), texture.width());
                int ty = Math.max(0, Math.min(texture.height() - 1, (int) ((latitude / Math.PI + .5) * texture.height())));
                Color base = renderer.color(texture.get(tx, ty));
                double sunlight = .48 + .52 * Math.max(0, Math.sqrt(Math.max(0, 1 - r2)) * .85 - nx * .42);
                int rr = (int)(base.getRed() * sunlight);
                int gg = (int)(base.getGreen() * sunlight);
                int bb = (int)(base.getBlue() * sunlight);
                g.setColor(new Color(rr, gg, bb));
                g.fillRect(cx + dx, cy + dy, 2, 2);
            }
        }
        g.setColor(new Color(232, 255, 239, 92));
        g.fillRect(cx - radius / 3, cy - radius / 2, 4, 2);
        g.fillRect(cx - radius / 3 + 4, cy - radius / 2 + 2, 2, 2);
    }
}
