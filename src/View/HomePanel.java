package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomePanel extends JPanel {
    public static BufferedImage image;

    public HomePanel() {
        super();
        try {
            image = ImageIO.read(new File("png/racing.png")); // Ensure this path is correct
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background Gradient
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, new Color(245, 245, 245), 0, getHeight(), Color.LIGHT_GRAY, true);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Fonts and Colors
        Font titleFont = new Font("Tahoma", Font.BOLD, 36);
        Font subtitleFont = new Font("Tahoma", Font.PLAIN, 20);
        Font footerFont = new Font("Tahoma", Font.PLAIN, 10);

        Color titleColor = new Color(66, 134, 244); // Light Blue
        Color subtitleColor = new Color(193, 73, 13); // Orange
        Color footerColor = Color.DARK_GRAY;

        // Draw Title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("🚗 Cars Rental System", getWidth() / 3 - 50, getHeight() / 2 - 50);

        // Draw Subtitle
        g.setColor(subtitleColor);
        g.setFont(subtitleFont);
        g.drawString("Car Rental Office Application", getWidth() / 3 - 40, getHeight() / 2);

        // Draw Image
        if (image != null) {
            g.drawImage(image, getWidth() / 3 - 150, getHeight() / 2 - 120, 100, 100, null);
        }

        // Footer Text
        g.setColor(footerColor);
        g.setFont(footerFont);
        g.drawString("2024 Car Rental Office Application.", getWidth() / 2 - 100, getHeight() - 20);
    }
}