package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Tuples extends Vector implements Serializable {
    public Tuples() {
    }

    public boolean subsumes(Tuples var1) {
        if (this.size() < var1.size()) {
            return false;
        } else {
            String var2 = this.toString();

            for(int var3 = 0; var3 < var1.size(); ++var3) {
                Vector var4 = (Vector)var1.elementAt(var3);
                String var5 = var4.toString();
                if (var2.indexOf(var5) < 0) {
                    return false;
                }
            }

            return true;
        }
    }

    public void removeDuplicates() {
        this.sort();
        int var1 = 0;

        while(var1 < this.size() - 1) {
            if (((Vector)this.elementAt(var1)).toString().equals(((Vector)this.elementAt(var1 + 1)).toString())) {
                this.removeElementAt(var1);
            } else {
                ++var1;
            }
        }

    }

    public void sort() {
        this.quickSort(0, this.size() - 1);
    }

    private void quickSort(int var1, int var2) {
        if (var2 > var1) {
            Vector var3 = (Vector)this.elementAt(var2);
            int var4 = var1 - 1;
            int var5 = var2;

            while(true) {
                while(true) {
                    ++var4;
                    if (this.tupleCompare((Vector)this.elementAt(var4), var3) >= 0) {
                        while(var5 > 0) {
                            --var5;
                            if (this.tupleCompare((Vector)this.elementAt(var5), var3) <= 0) {
                                break;
                            }
                        }

                        if (var4 >= var5) {
                            this.swap(var4, var2);
                            this.quickSort(var1, var4 - 1);
                            this.quickSort(var4 + 1, var2);
                            return;
                        }

                        this.swap(var4, var5);
                    }
                }
            }
        }
    }

    private int tupleCompare(Vector var1, Vector var2) {
        return var1.toString().compareTo(var2.toString());
    }

    private void swap(int var1, int var2) {
        Object var3 = this.elementAt(var1);
        this.setElementAt(this.elementAt(var2), var1);
        this.setElementAt(var3, var2);
    }

    public void reverse() {
        Tuples var1 = new Tuples();

        int var2;
        for(var2 = 0; var2 < this.size(); ++var2) {
            var1.addElement(this.elementAt(this.size() - var2 - 1));
        }

        for(var2 = 0; var2 < this.size(); ++var2) {
            this.setElementAt(var1.elementAt(var2), var2);
        }

    }
}
