package fr.starview;

import fr.starview.ui.StarViewApp;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public final class Main {
    private Main() { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { // Commence un appel asynchrone pour charger tout
            try {
                new StarViewApp().start(); // Lance l'appli si pas d'erreur
            } catch (Exception exception) {
                exception.printStackTrace(); // Print les erreurs
                JOptionPane.showMessageDialog(null,
                        "Unable to start StarView:\n" + exception.getMessage(),
                        "StarView - Error", JOptionPane.ERROR_MESSAGE);
            }
            //  Pop up d'une boite de dialogue d'erreur
        });
    }
}
