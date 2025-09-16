package br.com.mariojp.figureeditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

class DrawingPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final List<DrawableShape> shapes = new ArrayList<>();
    private Point startDrag = null;
    private Shape currentShape = null;
    private Color currentColor = new Color(30,144,255);

    class DrawableShape {
        Shape shape;
        Color color;
        
        public DrawableShape(Shape s, Color c) {
            this.shape = s;
            this.color = c;
        }
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }
    
    DrawingPanel() {
        
        setBackground(Color.WHITE);
        setOpaque(true);
        setDoubleBuffered(true);

        var mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startDrag = e.getPoint();
                currentShape = null;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (startDrag != null) {
                    Point endDrag = e.getPoint();
                    double width = Math.abs(endDrag.x - startDrag.x);
                    double height = Math.abs(endDrag.y - startDrag.y);
                    double x = Math.min(startDrag.x, endDrag.x);
                    double y = Math.min(startDrag.y, endDrag.y);

                    Shape newShape = new Ellipse2D.Double(x, y, width, height);
                    
                    shapes.add(new DrawableShape(newShape, currentColor));
                    startDrag = null;
                    currentShape = null;
                    repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (startDrag != null) {
                    Point endDrag = e.getPoint();
                    double width = Math.abs(endDrag.x - startDrag.x);
                    double height = Math.abs(endDrag.y - startDrag.y);
                    double x = Math.min(startDrag.x, endDrag.x);
                    double y = Math.min(startDrag.y, endDrag.y);
                    
                    currentShape = new Ellipse2D.Double(x, y, width, height);
                    repaint();
                }
            }
        };

        addMouseListener(mouse);        
        addMouseMotionListener(mouse);

    }

    void clear() {
        shapes.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (DrawableShape ds : shapes) {
            g2.setColor(ds.color);
            g2.fill(ds.shape);
            g2.setColor(new Color(0,0,0,70));
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(ds.shape);
        }
        
        if (currentShape != null) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.setStroke(new BasicStroke(1.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f));
            g2.draw(currentShape);
        }

        g2.dispose();
    }
}