package fr.starview.animation;

import fr.starview.io.PixelSprite;
import fr.starview.ui.PixelRenderer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

// planet animation
public final class SpaceScene {
    private final PixelSprite sky;
    private final List<SpaceBody> bodies = new ArrayList<>();
    private final int[][] stars = new int[39][3];

    public SpaceScene(PixelSprite sky, PixelSprite texture, PixelSprite moon) {
        this.sky = sky;
        bodies.add(new PlanetBody(texture, 133, 136, 47));
        bodies.add(new MoonBody(moon, 133, 136, 85, 25, 9, 5.9, 0.07));
        bodies.add(new MoonBody(moon, 133, 138, 85, 25, 9, 0.9, 0.07));
        bodies.add(new MoonBody(moon, 133, 140, 85, 25, 9, 1.9, 0.06));
        bodies.add(new MoonBody(moon, 133, 141, 85, 25, 9, 2.9, 0.07));
        bodies.add(new MoonBody(moon, 133, 142, 85, 25, 9, 4.9, 0.08));
        bodies.add(new MoonBody(moon, 133, 143, 85, 25, 9, 3.9, 0.075));
        bodies.add(new MoonBody(moon, 133, 144, 85, 25, 9, 6.9, 0.077));
        bodies.add(new MoonBody(moon, 133, 136, 85, 25, 9, 45.9, 0.07));
        bodies.add(new MoonBody(moon, 133, 138, 85, 25, 9, 40.9, 0.07));
        bodies.add(new MoonBody(moon, 133, 140, 85, 25, 9, 41.9, 0.06));
        bodies.add(new MoonBody(moon, 133, 141, 85, 25, 9, 42.9, 0.07));
        bodies.add(new MoonBody(moon, 133, 142, 85, 25, 9, 44.9, 0.08));
        bodies.add(new MoonBody(moon, 133, 143, 85, 25, 9, 43.9, 0.075));
        bodies.add(new MoonBody(moon, 133, 144, 85, 25, 9, 46.9, 0.077));
        Random random = new Random(306);
        for (int[] star : stars) {
            star[0] = 4 + random.nextInt(248);
            star[1] = 8 + random.nextInt(242);
            star[2] = random.nextInt(90);
        }
    }
    public void draw(Graphics2D g, PixelRenderer r, long frame) {
        r.drawSprite(g, sky, 0, 0, 256, 256);
        double time = frame / 4.0;
        // Two broken pixel orbital arcs.
        g.setColor(new Color(206, 252, 227, 42));
        for (int a = 0; a < 360; a += 4) {
            if ((a / 4) % 9 < 3) continue;
            double rad = Math.toRadians(a);
            int x = 133 + (int)(86 * Math.cos(rad));
            int y = 136 + (int)(29 * Math.sin(rad));
            g.fillRect(x, y, 2, 1);
        }
        List<SpaceBody> sorted = new ArrayList<>(bodies);
        sorted.sort(Comparator.comparingDouble(b -> b.depth(time)));
        for (SpaceBody body : sorted) body.draw(g, r, time);
        for (int[] star : stars) {
            double twinkle = Math.sin(frame * .038 + star[2]);
            int alpha = 65 + (int)(60 * (1 + twinkle) / 2);
            g.setColor(new Color(229, 255, 249, alpha));
            int size = star[2] % 9 == 0 ? 2 : 1;
            g.fillRect(star[0], star[1], size, size);
            if (size == 2 && twinkle > .55) {
                g.fillRect(star[0] - 2, star[1], 2, 1);
                g.fillRect(star[0] + 2, star[1], 2, 1);
                g.fillRect(star[0], star[1] - 2, 1, 2);
            }
        }
    }
}
