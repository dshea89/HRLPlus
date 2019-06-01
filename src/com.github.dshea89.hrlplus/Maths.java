package com.github.dshea89.hrlplus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.Vector;

public class Maths implements Serializable {
    public Maths() {
    }

    public Vector nonIsomorphicFromFile(String var1) {
        Vector var2 = new Vector();

        try {
            BufferedReader var3 = new BufferedReader(new FileReader(var1));

            for(String var4 = var3.readLine(); var4 != null; var4 = var3.readLine()) {
                var2.addElement(var4);
            }

            var3.close();
        } catch (Exception var5) {
            System.out.println("Couldn't open file");
        }

        return this.nonIsomorphic(var2);
    }

    public Vector nonIsomorphic(Vector var1) {
        Vector var2 = (Vector)var1.clone();
        int var3 = 0;
        String var4 = (String)var2.elementAt(0);
        int var5 = (new Double(Math.sqrt((double)var4.length()))).intValue();
        String var6 = "0123456789";
        if (var4.indexOf("a") >= 0) {
            var6 = "abcdefghijklmnopqrst";
        }

        for(Vector var7 = this.getPossibleIsomorphicMaps(var5, var6); var3 < var2.size(); ++var3) {
            var4 = (String)var2.elementAt(var3);
            boolean var8 = false;
            int var9 = var3 + 1;

            while(var9 < var2.size()) {
                String var10 = (String)var2.elementAt(var9);
                if (this.isIsomorphic(var5, var7, var4, var10)) {
                    var2.removeElementAt(var9);
                } else {
                    ++var9;
                }
            }
        }

        return var2;
    }

    public boolean isIsomorphic(int var1, Vector var2, String var3, String var4) {
        if (var3.equals(var4)) {
            return true;
        } else if (var3.length() != var4.length()) {
            return false;
        } else {
            int var5 = 0;

            boolean var6;
            for(var6 = false; !var6 && var5 < var2.size(); ++var5) {
                var6 = this.isIsomorphism((Vector)var2.elementAt(var5), var3, var4, var1);
            }

            return var6;
        }
    }

    public boolean isIsomorphism(Vector var1, String var2, String var3, int var4) {
        String var5 = "";

        for(int var6 = 0; var6 < var1.size(); ++var6) {
            var5 = var5 + (String)var1.elementAt(var6);
        }

        String var20 = "0123456789";
        if (var2.indexOf("a") >= 0) {
            var20 = "abcdefghijklmnopqrst";
        }

        Vector var7 = new Vector();

        int var8;
        for(var8 = 0; var8 < var4; ++var8) {
            var7.addElement(var20.substring(var8, var8 + 1));
        }

        var8 = 0;

        for(int var9 = 0; var9 < var7.size(); ++var9) {
            for(int var10 = 0; var10 < var7.size(); ++var10) {
                String var11 = var2.substring(var8, var8 + 1);
                int var12 = var7.indexOf(var11);
                String var13 = var5.substring(var12, var12 + 1);
                String var14 = var5.substring(var9, var9 + 1);
                String var15 = var5.substring(var10, var10 + 1);
                int var16 = var7.indexOf(var14);
                int var17 = var7.indexOf(var15);
                int var18 = var16 * var4 + var17;
                String var19 = var3.substring(var18, var18 + 1);
                if (!var19.equals(var13)) {
                    return false;
                }

                ++var8;
            }
        }

        return true;
    }

    private Vector getPossibleIsomorphicMaps(int var1, String var2) {
        Vector var3 = new Vector();
        Vector var4 = new Vector();

        int var5;
        for(var5 = 0; var5 < var1; ++var5) {
            var3.addElement(var2.substring(var5, var5 + 1));
            Vector var6 = new Vector();
            var6.addElement(var2.substring(var5, var5 + 1));
            var4.addElement(var6);
        }

        for(var5 = 1; var5 < var1; ++var5) {
            int var12 = var4.size();

            for(int var7 = 0; var7 < var12; ++var7) {
                Vector var8 = (Vector)var4.elementAt(0);

                for(int var9 = 0; var9 < var3.size(); ++var9) {
                    String var10 = (String)var3.elementAt(var9);
                    if (!var8.contains(var10)) {
                        Vector var11 = (Vector)var8.clone();
                        var11.addElement(var10);
                        var4.addElement(var11);
                    }
                }

                var4.removeElementAt(0);
            }
        }

        return var4;
    }

    public Tuples baseRepresentation(int var1, String var2) {
        Tuples var3 = new Tuples();
        int var4 = new Integer(var2);
        int var5 = 1;
        int var6 = 0;

        String var7;
        for(var7 = ""; var5 * var1 <= var4; var5 *= var1) {
            ++var6;
        }

        while(var6 >= 0) {
            Vector var8 = new Vector();
            if (var5 <= var4) {
                var7 = "1";
            } else {
                var7 = "0";
            }

            var8.addElement(Integer.toString(var6));
            var8.addElement(var7);
            if (!var7.equals("0")) {
                var4 -= var5;
            }

            var5 /= var1;
            --var6;
            var3.insertElementAt(var8, 0);
        }

        return var3;
    }

    public Vector bellPartitions(int var1) {
        Vector var2 = new Vector();
        Vector var3 = new Vector();
        var3.addElement(Integer.toString(var1));
        Vector var4 = new Vector();
        var4.addElement(var3);
        var2.addElement(var4);
        if (var1 == 1) {
            return var2;
        } else {
            Vector var5 = (Vector)this.bellPartitions(var1 - 1).clone();
            var2 = new Vector();

            int var6;
            Vector var7;
            for(var6 = 0; var6 < var5.size(); ++var6) {
                var7 = (Vector)((Vector)var5.elementAt(var6)).clone();
                var7.addElement(var3);
                var2.addElement(var7);
            }

            for(var6 = 0; var6 < var5.size(); ++var6) {
                var7 = (Vector)var5.elementAt(var6);

                for(int var8 = 0; var8 < var7.size(); ++var8) {
                    Vector var9 = new Vector();

                    for(int var10 = 0; var10 < var7.size(); ++var10) {
                        Vector var11 = (Vector)((Vector)var7.elementAt(var10)).clone();
                        if (var10 == var8) {
                            var11.addElement(Integer.toString(var1));
                        }

                        var9.addElement(var11);
                    }

                    var2.addElement(var9);
                }
            }

            return var2;
        }
    }

    public boolean isSquare(String var1) {
        double var2 = new Double(var1);
        return Math.floor(Math.sqrt(var2)) == Math.sqrt(var2);
    }

    public boolean isOdd(String var1) {
        double var2 = new Double(var1);
        return Math.floor(var2 / 2.0D) != var2 / 2.0D;
    }

    public boolean isEven(String var1) {
        double var2 = new Double(var1);
        return Math.floor(var2 / 2.0D) == var2 / 2.0D;
    }
}
