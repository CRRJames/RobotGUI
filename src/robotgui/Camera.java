/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robotgui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author laptop
 */
public class Camera extends javax.swing.JPanel {

    Thread connectionThread = null;

    /**
     * Creates new form Camera
     */
    public Camera() {
        initComponents();
    }

    private BufferedImage getImage() {
        BufferedImage img = null;
        try {
            Authenticator.setDefault(new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return (new PasswordAuthentication("root", "password".toCharArray()));
                }
            });
            img = ImageIO.read(new URL("http://"+jTextField1.getText()+"/jpg/1/image.jpg"));
            //img = ImageIO.read(new URL("http://entertainment.blogs.foxnews.com/files/2011/04/aflac-duck.jpg"));
            //img = ImageIO.read(new URL("https://lh3.googleusercontent.com/-SMgeINXOuyg/UO-oUtmtvbI/AAAAAAAAAY4/_kX1bM2i5IA/s0-d/infrared%2Bimage%2B1-9-13.bmp"));
        } catch (IOException ex) {
        }
        return img;
    }

    public synchronized void Disconnect() {
        jTextField1.setEditable(true);
        if (connectionThread != null) {
            connectionThread.interrupt();
            connectionThread = null;
            cameraImagePanel1.setText("Camera Not Connected");
        }
    }

    public synchronized void Connect() {
        Disconnect();
        jTextField1.setEditable(false);
        new Thread((new Runnable() {
            Camera camera;

            @Override
            public void run() {
                connectionThread = Thread.currentThread();
                cameraImagePanel1.setText("Connecting...");
                BufferedImage img = getImage();
                while (img != null && !Thread.interrupted()) {
                    cameraImagePanel1.paintImage(img);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Camera.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    img = getImage();
                }
                Disconnect();
            }

            public Runnable setCamera(Camera camera) {
                this.camera = camera;
                return this;
            }
        }).setCamera(this)).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        disconnectButton = new javax.swing.JButton();
        connectButton = new javax.swing.JButton();
        cameraImagePanel1 = new robotgui.CameraImagePanel();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jLabel2.setText("IP:");

        jTextField1.setText("192.168.0.90");

        disconnectButton.setText("Disconnect");
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cameraImagePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(connectButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(disconnectButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cameraImagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(disconnectButton)
                            .addComponent(connectButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(0, 18, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        Connect();
    }//GEN-LAST:event_connectButtonActionPerformed

    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectButtonActionPerformed
        Disconnect();
    }//GEN-LAST:event_disconnectButtonActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        //int h = cameraImagePanel1.getHeight();
        //int w = (int) (((double)h)*1.333333);
        //cameraImagePanel1.setSize(new Dimension(w, h));
    }//GEN-LAST:event_formComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private robotgui.CameraImagePanel cameraImagePanel1;
    private javax.swing.JButton connectButton;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
