package com.github.dshea89.hrlplus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.Vector;

/**
 * A class for performing all the calcualations associated with known (core) mathematical concepts. For example,
 * it can calculated the divisors of an integer and so on. Any concept supplied here with java code will be fully functional
 * in HR, and it will be possible to fullly explore it. I hope users will want to add more and more functions to this package.
 */
public class Maths implements Serializable {
    public Maths() {
    }

    /**
     * As nonIsomorphic below, but reading the algebras from the given file.
     */
    public Vector nonIsomorphicFromFile(String file_name) {
        Vector var2 = new Vector();

        try {
            BufferedReader var3 = new BufferedReader(new FileReader(file_name));

            for(String var4 = var3.readLine(); var4 != null; var4 = var3.readLine()) {
                var2.addElement(var4);
            }

            var3.close();
        } catch (Exception var5) {
            System.out.println("Couldn't open file");
        }

        return this.nonIsomorphic(var2);
    }

    /**
     * This reduces a set of possibly isomorphic algebras to those which are not isomorphic.
     */
    public Vector nonIsomorphic(Vector algebras) {
        Vector var2 = (Vector)algebras.clone();
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

    /**
     * Checks whether two flattened Cayley tables (e.g. abba, abcbcaabc) are isomorphic.
     * Must be the letters a,b,c, etc., not numbers, and they must be of size 9 or less.
     */
    public boolean isIsomorphic(int size, Vector isomorphisms, String algebra1, String algebra2) {
        if (algebra1.equals(algebra2)) {
            return true;
        } else if (algebra1.length() != algebra2.length()) {
            return false;
        } else {
            int var5 = 0;

            boolean var6;
            for(var6 = false; !var6 && var5 < isomorphisms.size(); ++var5) {
                var6 = this.isIsomorphism((Vector)isomorphisms.elementAt(var5), algebra1, algebra2, size);
            }

            return var6;
        }
    }

    public boolean isIsomorphism(Vector phi_map, String algebra1, String algebra2, int size) {
        String var5 = "";

        for(int var6 = 0; var6 < phi_map.size(); ++var6) {
            var5 = var5 + (String)phi_map.elementAt(var6);
        }

        String var20 = "0123456789";
        if (algebra1.indexOf("a") >= 0) {
            var20 = "abcdefghijklmnopqrst";
        }

        Vector var7 = new Vector();

        int var8;
        for(var8 = 0; var8 < size; ++var8) {
            var7.addElement(var20.substring(var8, var8 + 1));
        }

        var8 = 0;

        for(int var9 = 0; var9 < var7.size(); ++var9) {
            for(int var10 = 0; var10 < var7.size(); ++var10) {
                String var11 = algebra1.substring(var8, var8 + 1);
                int var12 = var7.indexOf(var11);
                String var13 = var5.substring(var12, var12 + 1);
                String var14 = var5.substring(var9, var9 + 1);
                String var15 = var5.substring(var10, var10 + 1);
                int var16 = var7.indexOf(var14);
                int var17 = var7.indexOf(var15);
                int var18 = var16 * size + var17;
                String var19 = algebra2.substring(var18, var18 + 1);
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

    public Tuples baseRepresentation(int base, String entity) {
        Tuples var3 = new Tuples();
        int var4 = new Integer(entity);
        int var5 = 1;
        int var6 = 0;

        String var7;
        for(var7 = ""; var5 * base <= var4; var5 *= base) {
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

            var5 /= base;
            --var6;
            var3.insertElementAt(var8, 0);
        }

        return var3;
    }

    /**
     * This generates the set of Bell partitions of a set of integers. eg. given input 3 it returns a vector of vectors:
     *  [[[1,2,3]],[[1,2],3],[[1,3],2],[[1],[2,3]],[[1],[2],[3]]]
     * The number of partitions is equals to the bell number.
     */
    public Vector bellPartitions(int num) {
        Vector var2 = new Vector();
        Vector var3 = new Vector();
        var3.addElement(Integer.toString(num));
        Vector var4 = new Vector();
        var4.addElement(var3);
        var2.addElement(var4);
        if (num == 1) {
            return var2;
        } else {
            Vector var5 = (Vector)this.bellPartitions(num - 1).clone();
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
                            var11.addElement(Integer.toString(num));
                        }

                        var9.addElement(var11);
                    }

                    var2.addElement(var9);
                }
            }

            return var2;
        }
    }

    public boolean isSquare(String int_string) {
        double var2 = new Double(int_string);
        return Math.floor(Math.sqrt(var2)) == Math.sqrt(var2);
    }

    public boolean isOdd(String int_string) {
        double var2 = new Double(int_string);
        return Math.floor(var2 / 2.0D) != var2 / 2.0D;
    }

    public boolean isEven(String int_string) {
        double var2 = new Double(int_string);
        return Math.floor(var2 / 2.0D) == var2 / 2.0D;
    }
}
