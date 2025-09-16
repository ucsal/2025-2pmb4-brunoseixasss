package br.com.mariojp.figureeditor;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            JFrame frame = new JFrame("Figure Editor — Clique + Arraste para desenhar");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            DrawingPanel panel = new DrawingPanel();

            frame.setLayout(new BorderLayout());
            frame.add(panel, BorderLayout.CENTER);

            JButton colorButton = new JButton("Cor...");
            colorButton.addActionListener(e -> {
                Color newColor = JColorChooser.showDialog(frame, "Escolha a Cor", panel.getCurrentColor());
                if (newColor != null) {
                    panel.setCurrentColor(newColor);
                }
            });
            frame.add(colorButton, BorderLayout.NORTH);

            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}