package com.coderedrobotics.dashboard.api.gui;

import com.coderedrobotics.dashboard.api.resources.RemoteDouble;
import com.coderedrobotics.dashboard.api.resources.listeners.RemoteDoubleListener;
import com.coderedrobotics.dashboard.communications.Connection;
import com.coderedrobotics.dashboard.communications.Subsocket;
import com.coderedrobotics.dashboard.communications.exceptions.InvalidRouteException;
import com.coderedrobotics.dashboard.communications.exceptions.NotMultiplexedException;
import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Austin
 */
public class Compass extends javax.swing.JPanel implements RemoteDoubleListener {

    private double angle = 0;
    RemoteDouble value;

    /**
     * Creates new form Compass
     */
    public Compass() {
        initComponents();
    }

    public void setAngle(double angle) {
        this.angle = angle % 360;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        int size = Math.min(getWidth(), getHeight() - 20);
        g.drawOval(0, 0, size, size);
        for (int i = 0; i < 32; i++) {
//            switch (i % 2) {
//                case 0:
//                    g.setColor(Color.RED);
//                    break;
//                case 1:
//                    g.setColor(Color.BLACK);
//                    break;
//            }
            g.drawLine(((int) (Math.cos(((double) i) * 0.19635) * size * 0.5) + (size / 2)),
                    ((int) (Math.sin(((double) i) * 0.19635) * size * 0.5) + (size / 2)),
                    ((int) (Math.cos(((double) i) * 0.19635) * size * 0.47) + (size / 2)),
                    ((int) (Math.sin(((double) i) * 0.19635) * size * 0.47) + (size / 2)));
        }
        g.setColor(Color.BLACK);
        g.drawLine(((int) (Math.cos((angle - 90) * (Math.PI / 180)) * size * 0.5) + (size / 2)),
                ((int) (Math.sin((angle - 90) * (Math.PI / 180)) * size * 0.5) + (size / 2)),
                size / 2,
                size / 2);
        g.drawString("angle: " + angle, 16, getHeight() - 4);
    }
    
    public void setup(String subsocketPath) {
        try {
            Subsocket root = Connection.getInstance().getRootSubsocket().enableMultiplexing();
            root.createNewRoute(subsocketPath).enableMultiplexing();
            value = new RemoteDouble(subsocketPath, RemoteDouble.MODE.LOCAL);
            value.addListener(this);
        } catch (InvalidRouteException | NotMultiplexedException ex) {
            Logger.getLogger(StatusLabel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void update(double angle, RemoteDouble sender) {
        setAngle(angle);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
