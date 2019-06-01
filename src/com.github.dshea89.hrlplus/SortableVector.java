package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class SortableVector extends Vector implements Serializable {
    double prune_less_than = 0.0D;
    Reflection reflect = new Reflection();
    boolean sort_items = true;
    Vector values = new Vector();

    public SortableVector() {
    }

    public void addElement(Object var1, String var2) {
        if (var2 != null && !var2.equals("")) {
            try {
                String var3 = this.reflect.getValue(var1, var2).toString();
                Double var4 = new Double(var3);
                double var5 = var4;
                if (var5 < this.prune_less_than) {
                    return;
                }

                for(int var7 = 0; var7 < this.values.size(); ++var7) {
                    Double var8 = (Double)this.values.elementAt(var7);
                    if (var8 < var5) {
                        super.insertElementAt(var1, var7);
                        this.values.insertElementAt(var4, var7);
                        return;
                    }
                }

                super.addElement(var1);
                this.values.addElement(var4);
            } catch (Exception var9) {
                super.addElement(var1);
            }

        } else {
            super.addElement(var1);
        }
    }

    public void addElement(Object var1, int var2) {
        Double var3 = new Double((double)var2);
        double var4 = var3;
        if (var4 >= this.prune_less_than) {
            for(int var6 = 0; var6 < this.values.size(); ++var6) {
                Double var7 = (Double)this.values.elementAt(var6);
                if (var7 < var4) {
                    super.insertElementAt(var1, var6);
                    this.values.insertElementAt(var3, var6);
                    return;
                }
            }

            super.addElement(var1);
            this.values.addElement(var3);
        }
    }

    public void addElement(Object var1, double var2) {
        Double var4 = new Double(var2);
        if (var2 >= this.prune_less_than) {
            for(int var5 = 0; var5 < this.values.size(); ++var5) {
                Double var6 = (Double)this.values.elementAt(var5);
                if (var6 < var2) {
                    super.insertElementAt(var1, var5);
                    this.values.insertElementAt(var4, var5);
                    return;
                }
            }

            super.addElement(var1);
            this.values.addElement(var4);
        }
    }

    public void addElement(Object var1, Long var2) {
        Double var3 = new Double((double)var2);
        double var4 = var3;
        if (var4 >= this.prune_less_than) {
            for(int var6 = 0; var6 < this.values.size(); ++var6) {
                Double var7 = (Double)this.values.elementAt(var6);
                if (var7 < var4) {
                    super.insertElementAt(var1, var6);
                    this.values.insertElementAt(var3, var6);
                    return;
                }
            }

            super.addElement(var1);
            this.values.addElement(var3);
        }
    }

    public void reSortBy(String var1) {
        Vector var2 = (Vector)this.clone();
        this.removeAllElements();

        for(int var3 = 0; var3 < var2.size(); ++var3) {
            this.addElement(var2.elementAt(var3), var1);
        }

    }

    public void reverse() {
        for(int var1 = 0; var1 < this.size(); ++var1) {
            this.insertElementAt(this.lastElement(), var1);
            this.removeElementAt(this.size() - 1);
        }

    }
}
