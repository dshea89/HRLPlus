package com.github.dshea89.hrlplus;

import java.awt.List;
import java.io.Serializable;
import java.util.Vector;

public class SortableList extends List implements Serializable {
    boolean sort_items = true;
    Vector values = new Vector();

    public SortableList(int var1) {
    }

    public SortableList() {
    }

    public SortableList(int var1, boolean var2) {
        this.setMultipleMode(var2);
    }

    public void addItem(String var1) {
        if (!this.sort_items) {
            super.addItem(var1);
        } else {
            for(int var2 = 0; var2 < this.getItemCount(); ++var2) {
                String var3 = this.getItem(var2);
                if (var3.compareTo(var1) > 0) {
                    super.addItem(var1, var2);
                    return;
                }
            }

            super.addItem(var1);
        }
    }

    public void addItem(String var1, String var2) {
        if (var2 != null && !var2.equals("")) {
            try {
                Double var3 = new Double(var2);
                double var4 = var3;

                for(int var6 = 0; var6 < this.values.size(); ++var6) {
                    Double var7 = (Double)this.values.elementAt(var6);
                    if (var7 < var4) {
                        super.addItem(var1, var6);
                        this.values.insertElementAt(var3, var6);
                        return;
                    }
                }

                super.addItem(var1);
                this.values.addElement(var3);
            } catch (Exception var8) {
                super.addItem(var1);
            }

        } else {
            this.addItem(var1);
        }
    }

    public void clear() {
        super.clear();
        this.values = new Vector();
    }
}
