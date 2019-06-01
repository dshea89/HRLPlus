package com.github.dshea89.hrlplus;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.io.Serializable;

public class ImageViewer extends Frame implements ImageObserver, Serializable {
    Image image;
    String files_dir = "";
    int height = 0;
    int width = 0;

    public ImageViewer() {
    }

    public ImageViewer(String var1, String var2, String var3) {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent var1) {
                ImageViewer.this.dispose();
            }
        });
        if (var1 != null) {
            this.setResizable(false);
            this.files_dir = var1;
            if (!this.files_dir.substring(this.files_dir.length() - 1, this.files_dir.length()).equals("/")) {
                this.files_dir = this.files_dir + "/";
            }

            this.image = this.getToolkit().getImage(this.files_dir + var2);

            for(byte var4 = 0; this.width <= 0 && var4 < 10000; this.height = this.image.getHeight(this)) {
                this.width = this.image.getWidth(this);
            }

            this.setVisible(true);
            this.setSize((int) (this.width / 1.5), (int) (this.height / 1.5) + 22);
            this.setTitle(var3);
            Graphics var5 = this.getGraphics();
            this.paint(var5);
        }

    }

    public void repaint() {
        this.paint(this.getGraphics());
    }

    public void update(Graphics var1) {
        this.paint(this.getGraphics());
    }

    public void paint(Graphics var1) {
        var1 = this.getGraphics();
        if (this.image != null) {
            var1.drawImage(this.image, 0, 22, (int) (this.width / 1.5), (int) (this.height / 1.5), this);
        }

    }
}
