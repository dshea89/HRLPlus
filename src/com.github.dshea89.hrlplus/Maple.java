package com.github.dshea89.hrlplus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Vector;

public class Maple implements Serializable {
    public Maple() {
    }

    public String callFunction(String[] var1, String var2, String[] var3, String[] var4) {
        String var5 = "";
        String var6 = "";

        int var7;
        for(var7 = 0; var7 < var3.length; ++var7) {
            var6 = var6 + "with(" + var3[var7] + "):\n";
        }

        for(var7 = 0; var7 < var4.length; ++var7) {
            var6 = var6 + "read `" + var4[var7] + "`:\n";
        }

        var6 = var6 + "writeto(\"delcalc.out\"):\n";
        var6 = var6 + "lprint(" + var2 + "(";

        for(var7 = 0; var7 < var1.length; ++var7) {
            var6 = var6 + var1[var7];
            if (var7 < var1.length - 1) {
                var6 = var6 + ", ";
            }
        }

        var6 = var6 + ")):\n";
        var6 = var6 + "quit:";

        try {
            PrintWriter var13 = new PrintWriter(new BufferedWriter(new FileWriter("delcalc.in")));
            var13.write(var6);
            var13.close();
            Process var8 = Runtime.getRuntime().exec("maple2.bat");
            int var9 = var8.waitFor();
            var8.destroy();
            BufferedReader var10 = new BufferedReader(new FileReader("delcalc.out"));

            for(String var11 = var10.readLine(); var11 != null; var11 = var10.readLine()) {
                if (var11 != null && var11.indexOf(">") < 0 && var11.indexOf("bytes") < 0) {
                    var5 = var11;
                }
            }

            var10.close();
        } catch (Exception var12) {
            System.out.println(var12);
            System.out.println("here in Maple");
        }

        return var5;
    }

    public String callFunction(String var1, String var2, String[] var3, String[] var4) {
        String[] var5 = new String[]{var1};
        return this.callFunction(var5, var2, var3, var4);
    }

    public Tuples getOutput(int var1, String var2, String var3, String[] var4, String[] var5) {
        Tuples var6 = new Tuples();
        String var7 = this.callFunction(var2, var3, var4, var5);
        Vector var8;
        if (var1 == 1 && var7.equals("true")) {
            var8 = new Vector();
            var6.addElement(var8);
        }

        if (var1 == 2) {
            if (var7.indexOf("{") < 0) {
                var8 = new Vector();
                var8.addElement(var7);
                var6.addElement(var8);
            } else {
                for(var7 = var7.substring(1, var7.length() - 1); var7.indexOf(",") >= 0; var7 = var7.substring(var7.indexOf(",") + 2, var7.length())) {
                    var8 = new Vector();
                    String var9 = var7.substring(0, var7.indexOf(","));
                    var8.addElement(var9);
                    var6.addElement(var8);
                }

                var8 = new Vector();
                var8.addElement(var7);
                var6.addElement(var8);
            }
        }

        var6.sort();
        return var6;
    }

    public Tuples getOutput(int var1, String var2, String var3, String[] var4) {
        String[] var5 = new String[0];
        return this.getOutput(var1, var2, var3, var4, var5);
    }

    public String writeGraphForMaple(String var1) {
        String var2 = "abcdefghijklmnopqrstuvwxzy";
        String var3 = "graph({";

        int var4;
        for(var4 = 0; var4 < var1.indexOf("["); ++var4) {
            var3 = var3 + Integer.toString(var2.indexOf(var1.charAt(var4)));
            if (var4 < var1.indexOf("[") - 1) {
                var3 = var3 + ",";
            }
        }

        var3 = var3 + "},{";
        ++var4;

        while(var4 < var1.indexOf("]")) {
            var3 = var3 + "{" + Integer.toString(var2.indexOf(var1.charAt(var4))) + "," + Integer.toString(var2.indexOf(var1.charAt(var4 + 1))) + "}";
            var4 += 3;
            if (var4 < var1.indexOf("]") - 1) {
                var3 = var3 + ",";
            }
        }

        return var3 + "})";
    }
}
