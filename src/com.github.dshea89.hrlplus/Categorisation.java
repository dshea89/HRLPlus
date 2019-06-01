package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Categorisation extends Vector implements Serializable {
    public double novelty = 0.0D;
    public double discrimination = -1.0D;
    public double invariance = -1.0D;
    public int appearances = 0;
    public boolean is_user_given = false;
    public String type_of_objects_in_categorisation = "blank";
    public Vector concepts = new Vector();
    public Vector entities = new Vector();
    public int[] cat_pos = null;

    public Categorisation(String var1) {
        int var2 = 2;
        Vector var3 = new Vector();

        for(String var4 = ""; var2 < var1.length() - 1; ++var2) {
            String var5 = var1.substring(var2, var2 + 1);
            if (var5.equals(",")) {
                var3.addElement(var4);
                var4 = "";
            }

            if (var5.equals("]")) {
                var3.addElement(var4);
                this.sortAndAdd((Vector)var3.clone());
                var2 += 2;
                var4 = "";
                var3 = new Vector();
            }

            if (!var5.equals(",") && !var5.equals("]")) {
                var4 = var4 + var5;
            }
        }

    }

    public Categorisation() {
    }

    private void sortAndAdd(Vector var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            String var4 = (String)var1.elementAt(var3);
            boolean var5 = false;

            for(int var6 = 0; var6 < var2.size() && !var5; ++var6) {
                String var7 = (String)var2.elementAt(var6);
                if (var7.compareTo(var4) > 0) {
                    var2.insertElementAt(var4, var6);
                    var5 = true;
                }
            }

            if (!var5) {
                var2.addElement(var4);
            }
        }

        this.addElement(var2);
    }

    public Vector getEntities() {
        if (this.entities.isEmpty()) {
            for(int var1 = 0; var1 < this.size(); ++var1) {
                Vector var2 = (Vector)this.elementAt(var1);

                for(int var3 = 0; var3 < var2.size(); ++var3) {
                    this.entities.addElement((String)var2.elementAt(var3));
                }
            }
        }

        return this.entities;
    }

    public boolean compliesWith(Categorisation var1) {
        for(int var2 = 0; var2 < this.size(); ++var2) {
            Vector var3 = (Vector)this.elementAt(var2);
            if (!this.categoryCompliesWith(this.getEntities(), var3, var1)) {
                return false;
            }
        }

        return true;
    }

    private boolean categoryCompliesWith(Vector var1, Vector var2, Categorisation var3) {
        for(int var4 = 0; var4 < var2.size(); ++var4) {
            String var5 = (String)var2.elementAt(var4);
            Vector var6 = var3.getCategoryContaining(var5);

            int var7;
            String var8;
            for(var7 = 0; var7 < var6.size(); ++var7) {
                var8 = (String)var6.elementAt(var7);
                if (var1.contains(var8) && !var2.contains(var8)) {
                    return false;
                }
            }

            for(var7 = var4; var7 < var2.size(); ++var7) {
                var8 = (String)var2.elementAt(var7);
                if (var6 != var3.getCategoryContaining(var8)) {
                    return false;
                }
            }
        }

        return true;
    }

    public Vector getCategoryContaining(String var1) {
        for(int var2 = 0; var2 < this.size(); ++var2) {
            if (((Vector)this.elementAt(var2)).contains(var1)) {
                return (Vector)this.elementAt(var2);
            }
        }

        return new Vector();
    }

    public double invarianceWith(Vector var1, Vector var2) {
        if (this.invariance >= 0.0D) {
            return this.invariance;
        } else {
            double var3 = 0.0D;
            double var5 = 0.0D;
            this.getCatPoses(var1);

            for(int var7 = 0; var7 < var2.size(); ++var7) {
                int[] var8 = (int[])var2.elementAt(var7);
                if (this.cat_pos[var8[0]] == this.cat_pos[var8[1]]) {
                    ++var3;
                }

                ++var5;
            }

            this.invariance = var3 / var5;
            return this.invariance;
        }
    }

    public double discriminationWith(Vector var1, Vector var2) {
        if (this.discrimination >= 0.0D) {
            return this.discrimination;
        } else {
            double var3 = 0.0D;
            double var5 = 0.0D;
            this.getCatPoses(var1);

            for(int var7 = 0; var7 < var2.size(); ++var7) {
                int[] var8 = (int[])var2.elementAt(var7);
                if (this.cat_pos[var8[0]] != this.cat_pos[var8[1]]) {
                    ++var3;
                }

                ++var5;
            }

            this.discrimination = var3 / var5;
            return this.discrimination;
        }
    }

    public void getCatPoses(Vector var1) {
        if (this.cat_pos == null) {
            this.cat_pos = new int[var1.size()];

            for(int var2 = 0; var2 < this.size(); ++var2) {
                Vector var3 = (Vector)this.elementAt(var2);

                for(int var4 = 0; var4 < var3.size(); ++var4) {
                    String var5 = (String)var3.elementAt(var4);
                    if (var1.indexOf(var5) >= 0) {
                        this.cat_pos[var1.indexOf(var5)] = var2;
                    }
                }
            }
        }

    }
}
