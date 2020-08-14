package com.github.dshea89.hrlplus;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.Serializable;

public class ImageViewer extends Frame implements ImageObserver, Serializable
{
    Image image;

    String files_dir = "";
    int height = 0;
    int width = 0;

    /** The simple constructor
     */

    public ImageViewer()
    {
    }

    public ImageViewer(String files_directory, String filename, String title)
    {
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                dispose();
            }
        });
        if (files_directory!=null)
        {
            setResizable(false);
            files_dir = files_directory;
            if (!files_dir.substring(files_dir.length()-1, files_dir.length()).equals("/"))
                files_dir = files_dir + "/";
            image = getToolkit().getImage(files_dir+filename);
            int pause_loop = 0;
            while (width<=0 && pause_loop < 10000)
            {
                width = image.getWidth(this);
                height = image.getHeight(this);
            }
            setVisible(true);
            setSize(width, height+22);
            setTitle(title);
            Graphics g = getGraphics();
            paint(g);
        }
    }

    public void repaint()
    {
        paint(getGraphics());
    }

    public void update(Graphics g)
    {
        paint(getGraphics());
    }

    public void paint(Graphics g)
    {
        g = getGraphics();
        if (image!=null)
            g.drawImage(image,0,22,width,height,this);
    }
}
