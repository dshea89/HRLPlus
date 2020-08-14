package com.github.dshea89.hrlplus;

import java.lang.String;
import java.io.Serializable;
import javax.swing.*;

/** A class for debugging with.
 *
 * @author Simon Colton, started 17th December 2001
 * @version 1.0 */

public class Debugger implements Serializable
{
    public void runFunction(Theory theory, String param)
    {
        JFrame f = new JFrame();
        f.setSize(500,500);
        JEditorPane jt = new JEditorPane("text/html",param);
        JScrollPane jsp = new JScrollPane(jt);
        f.getContentPane().add(jsp);
        f.setVisible(true);
        try
        {
            jt.setPage(param);
        }
        catch(Exception ignored){}
    }
}
