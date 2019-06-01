package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Definition implements Serializable {
    public String language = "ascii";
    public String text = "";

    public Definition() {
    }

    public Definition(String var1, String var2) {
        this.text = var1;
        this.language = var2;
    }

    public Definition(String var1, String var2, String var3) {
        for(int var4 = 0; var4 < var1.length(); ++var4) {
            String var5 = var1.substring(var4, var4 + 1);

            for(int var6 = var2.indexOf("@" + var5 + "@", 0); var6 > -1; var6 = var2.indexOf("@" + var5 + "@", var6 + 1)) {
                String var7 = Integer.toString(var4);
                var2 = var2.substring(0, var6 + 1) + var7 + var2.substring(var6 + 2, var2.length());
            }
        }

        this.text = var2;
        this.language = var3;
    }

    public String write(Vector var1) {
        String var2 = this.text;

        String var6;
        for(int var3 = var2.indexOf("@", 0); var3 > -1; var3 = var2.indexOf("@", var3 + var6.length() + 1)) {
            int var4 = var2.indexOf("@", var3 + 1);
            int var5 = new Integer(var2.substring(var3 + 1, var4));
            var6 = (String)var1.elementAt(var5);
            var2 = var2.substring(0, var3) + var6 + var2.substring(var4 + 1, var2.length());
        }

        return var2;
    }

    public String write(String var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < var1.length(); ++var3) {
            var2.addElement(var1.substring(var3, var3 + 1));
        }

        return this.write(var2);
    }
}
