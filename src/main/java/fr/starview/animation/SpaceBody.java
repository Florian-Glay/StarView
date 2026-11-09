package fr.starview.animation;

import fr.starview.ui.PixelRenderer;
import java.awt.Graphics2D;

/** Common abstraction for different bodies in the animated background. */
public abstract class SpaceBody {
    public abstract double depth(double time);
    public abstract void draw(Graphics2D graphics, PixelRenderer renderer, double time);
}
