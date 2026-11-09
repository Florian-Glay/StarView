package fr.starview.ui;
// Import des classes
import fr.starview.animation.SpaceScene;
import fr.starview.io.SpriteLoader;
// Import de Swing et awt (Bibliothèque graphique)
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

// Une fenêtre s'ouvre pour afficher l'animation Pixel Art
public final class StarViewApp {
    private static final int LOGICAL_SIZE = 256; // Taille du Pixel Art
    private static final int INITIAL_SCALE = 3; // Scale appliquer sur la fenêtre du Pixel Art (Initialement 768x768)
    private static final int FRAME_DELAY_MS = 10; // Environ 100 FPS
    // Buffer de la fenêtre le type ARGB qui permet de gérer les transparences des pixels
    private final BufferedImage frameBuffer = new BufferedImage(LOGICAL_SIZE, LOGICAL_SIZE, BufferedImage.TYPE_INT_ARGB);

    private SpaceScene scene; // Scène de rendu
    private PixelRenderer renderer; // Outils d'encrage des pixels
    private long frameNumber; // Permet de définir le num de l'image actuelle

    public void start() throws IOException {
        SpriteLoader spriteLoader = new SpriteLoader(); // Charge tous les sprites du dossier
        scene = new SpaceScene(
                spriteLoader.fromResource("/sprites/nebula.txt"), // Arrière-plan
                spriteLoader.fromResource("/sprites/planet_texture.txt"), // Planète
                spriteLoader.fromResource("/sprites/moon.txt")); // Lune
        renderer = new PixelRenderer(); // Défini une nouvelle image

        JFrame window = new JFrame("StarView - Animated Pixel Art"); // Nom de la fenêtre
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Action fermeture de la fenêtre
        JPanel canvas = new SpaceCanvas(); // Pixel Art
        canvas.setPreferredSize(new Dimension(LOGICAL_SIZE * INITIAL_SCALE, LOGICAL_SIZE * INITIAL_SCALE));
        window.setContentPane(canvas); // On applique à la fenêtre la taille du canvas
        window.pack(); // Lance le redimensionnement
        window.setMinimumSize(new Dimension(256, 288));
        // Attention, la fenêtre ne peut pas être plus petite que le pixel art
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        // Mise en route des frames
        Timer animation = new Timer(FRAME_DELAY_MS, event -> {
            frameNumber++;
            canvas.repaint();
        });
        animation.setCoalesce(true); // Permet une animation non interrompue
        animation.start();
    }

    private final class SpaceCanvas extends JPanel {
        private SpaceCanvas() {
            setBackground(new Color(12, 10, 25));
            setDoubleBuffered(true); // Utilise le buffer de l'image précédente
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            Graphics2D pixels = frameBuffer.createGraphics();
            try {
                pixels.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF); // Pas de flou de pixel
                scene.draw(pixels, renderer, frameNumber); // Draw
            } finally {
                pixels.dispose(); // Relâche la mémoire pour le prochain pixel
            }

            int size = Math.max(1, Math.min(getWidth(), getHeight())); // Taille du pixel à afficher
            int x = (getWidth() - size) / 2; // Position X
            int y = (getHeight() - size) / 2; // Position Y

            Graphics2D display = (Graphics2D) graphics.create(); // Créer l'élément 2D
            try {
                // Pixel avec bonne position
                display.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                display.drawImage(frameBuffer, x, y, size, size, null); // Affiche le pixel
            } finally {
                display.dispose(); // Relâche la mémoire
            }
        }
    }
}
