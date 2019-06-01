package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Measure implements Serializable {
    public double interestingness_zero_min = 0.0D;
    public boolean normalise_values = true;
    public boolean measures_need_updating = false;
    public boolean old_measures_have_been_updated = false;

    public Measure() {
    }

    public double normalisedValue(double var1, Vector var3) {
        this.measures_need_updating = false;
        if (!this.normalise_values) {
            return var1;
        } else {
            String var4 = Double.toString(var1);
            if (var3.isEmpty()) {
                var3.addElement(var4);
                var3.trimToSize();
                return 1.0D;
            } else {
                int var5 = var3.indexOf(var4);
                if (var3.size() == 1 && var5 == 0) {
                    return 1.0D;
                } else {
                    double var6 = new Double((double)var3.size());
                    if (var3.indexOf(var4) > -1) {
                        double var12 = new Double((double)var5);
                        return var12 / (var6 - 1.0D);
                    } else {
                        this.measures_need_updating = true;
                        int var8 = -1;
                        boolean var9 = false;

                        while(!var9 && var8 < var3.size() - 1) {
                            ++var8;
                            double var10 = new Double((String)var3.elementAt(var8));
                            if (var10 > var1) {
                                var3.insertElementAt(var4, var8);
                                var3.trimToSize();
                                var9 = true;
                            }
                        }

                        if (!var9) {
                            var3.addElement(var4);
                            var3.trimToSize();
                            ++var8;
                        }

                        return new Double((double)var8) / var6;
                    }
                }
            }
        }
    }
}
