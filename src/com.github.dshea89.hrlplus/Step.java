package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Step extends Vector implements Serializable {
    public String id = "";
    public boolean dont_develop = false;
    public String concept_arising_name = "";
    public double score = 0.0D;
    public boolean forced = false;
    public String result_of_step_explanation = "";
    public TheoryConstituent result_of_step = new TheoryConstituent();

    public Step() {
    }

    public String asString() {
        if (this.size() == 0) {
            return "";
        } else {
            String var1 = "[";
            Vector var2;
            if (this.size() == 1) {
                var2 = (Vector)this.elementAt(0);

                for(int var3 = 0; var3 < var2.size(); ++var3) {
                    var1 = var1 + ((Concept)var2.elementAt(var3)).id + " ";
                }

                var1 = var1.trim();
            }

            ProductionRule var6;
            if (this.size() == 2) {
                var2 = (Vector)this.elementAt(0);
                var6 = (ProductionRule)this.elementAt(1);

                for(int var4 = 0; var4 < var2.size(); ++var4) {
                    var1 = var1 + ((Concept)var2.elementAt(var4)).id + " ";
                }

                var1 = var1.trim() + "," + var6.getName();
            }

            if (this.size() == 3) {
                var2 = (Vector)this.elementAt(0);
                var6 = (ProductionRule)this.elementAt(1);
                Vector var7 = (Vector)this.elementAt(2);

                for(int var5 = 0; var5 < var2.size(); ++var5) {
                    var1 = var1 + ((Concept)var2.elementAt(var5)).id + " ";
                }

                var1 = var1.trim() + "," + var6.getName() + "," + this.writeParameters(var7);
            }

            var1 = var1 + "]";
            return var1;
        }
    }

    public String writeParameters(Vector var1) {
        String var2 = "[";

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Object var4 = var1.elementAt(var3);
            if (var4 instanceof Concept) {
                var2 = var2 + ((Concept)var4).id;
            } else {
                var2 = var2 + var4.toString();
            }

            if (var3 < var1.size() - 1) {
                var2 = var2 + ", ";
            }
        }

        return var2 + "]";
    }

    public Vector conceptList() {
        return (Vector)this.elementAt(0);
    }

    public ProductionRule productionRule() {
        return this.size() < 1 ? new ProductionRule() : (ProductionRule)this.elementAt(1);
    }

    public Vector parameters() {
        return this.size() < 2 ? new Vector() : (Vector)this.elementAt(2);
    }
}
