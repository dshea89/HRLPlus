package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class Datatable extends Vector implements Serializable {
    public int number_of_tuples = 0;

    public Datatable() {
    }

    public void setNumberOfTuples() {
        for(int var1 = 0; var1 < this.size(); ++var1) {
            Row var2 = (Row)this.elementAt(var1);
            this.number_of_tuples += var2.tuples.size();
        }

    }

    public String toTable() {
        String var1 = new String();

        for(int var2 = 0; var2 < this.size(); ++var2) {
            var1 = var1 + ((Row)this.elementAt(var2)).entity;
            var1 = var1 + "=";
            var1 = var1 + ((Row)this.elementAt(var2)).tuples.toString();
            var1 = var1 + "\n";
        }

        return var1;
    }

    public void sort() {
        this.quickSort(0, this.size() - 1);
    }

    private void quickSort(int var1, int var2) {
        if (var2 > var1) {
            String var3 = ((Row)this.elementAt(var2)).entity;
            int var4 = var1 - 1;
            int var5 = var2;

            while(true) {
                while(true) {
                    ++var4;
                    if (this.entityCompare(((Row)this.elementAt(var4)).entity, var3) >= 0) {
                        while(var5 > 0) {
                            --var5;
                            if (this.entityCompare(((Row)this.elementAt(var5)).entity, var3) <= 0) {
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

    private int entityCompare(String var1, String var2) {
        int var3 = 0;

        try {
            int var4 = new Integer(var1);
            int var5 = new Integer(var2);
            if (var4 < var5) {
                var3 = -1;
            }

            if (var4 == var5) {
                var3 = 0;
            }

            if (var4 > var5) {
                var3 = 1;
            }
        } catch (Exception var6) {
            var3 = var1.compareTo(var2);
        }

        return var3;
    }

    private void swap(int var1, int var2) {
        Object var3 = this.elementAt(var1);
        this.setElementAt(this.elementAt(var2), var1);
        this.setElementAt(var3, var2);
    }

    public Datatable copy() {
        Datatable var1 = new Datatable();

        for(int var2 = 0; var2 < this.size(); ++var2) {
            Row var3 = (Row)this.elementAt(var2);
            Tuples var4 = new Tuples();

            for(int var5 = 0; var5 < var3.tuples.size(); ++var5) {
                Vector var6 = (Vector)((Vector)var3.tuples.elementAt(var5)).clone();
                var4.addElement(var6);
            }

            Row var7 = new Row(var3.entity, var4);
            var1.addElement(var7);
        }

        return var1;
    }

    public Row rowWithEntity(String var1) {
        int var2;
        for(var2 = 0; var2 < this.size() && !((Row)this.elementAt(var2)).entity.equals(var1); ++var2) {
            ;
        }

        return var2 < this.size() ? (Row)this.elementAt(var2) : new Row(var1, new Tuples());
    }

    private Row rowWithEntityNoTuples(String var1) {
        int var2;
        for(var2 = 0; var2 < this.size() && !((Row)this.elementAt(var2)).entity.equals(var1); ++var2) {
            ;
        }

        return var2 < this.size() ? (Row)this.elementAt(var2) : new Row("", new Tuples());
    }

    public boolean hasEntity(String var1) {
        int var2;
        for(var2 = 0; var2 < this.size() && !((Row)this.elementAt(var2)).entity.equals(var1); ++var2) {
            ;
        }

        return var2 < this.size();
    }

    public Vector toFlatTable() {
        Vector var1 = new Vector();

        for(int var2 = 0; var2 < this.size(); ++var2) {
            Row var3 = (Row)this.elementAt(var2);
            if (var3.tuples.size() > 0) {
                for(int var4 = 0; var4 < var3.tuples.size(); ++var4) {
                    Vector var5 = (Vector)((Vector)var3.tuples.elementAt(var4)).clone();
                    if (var5.size() > 0 && ((String)var5.elementAt(0)).equals("")) {
                        var5.removeElementAt(0);
                    }

                    var5.insertElementAt(var3.entity, 0);
                    var1.addElement(var5);
                }
            }
        }

        return var1;
    }

    public Datatable fromFlatTable() {
        Datatable var1 = new Datatable();

        for(int var2 = 0; var2 < this.size(); ++var2) {
            Vector var3 = (Vector)this.elementAt(var2);
            String var4 = (String)var3.elementAt(0);
            var3.removeElementAt(0);
            boolean var5 = false;
            Row var6 = var1.rowWithEntity(var4);
            var6.tuples.addElement(var3);
            if (var6.tuples.size() == 1) {
                var1.addElement(var6);
            }
        }

        return var1;
    }

    public boolean isIdenticalTo(Theory var1, Datatable var2) {
        if (!var1.pseudo_entities.isEmpty()) {
            ;
        }

        boolean var3 = true;
        int var4 = 0;
        if (var1.pseudo_entities.isEmpty() && var2.size() != this.size()) {
            var3 = false;
        }

        if (!var1.pseudo_entities.isEmpty() && var2.size() != this.size() && (var2.size() > this.size() + var1.pseudo_entities.size() || this.size() > var2.size() + var1.pseudo_entities.size())) {
            var3 = false;
        }

        for(; var4 < this.size() && var3; ++var4) {
            Row var5 = (Row)this.elementAt(var4);
            Row var6 = (Row)var2.elementAt(var4);
            if (!var1.pseudo_entities.contains(var5.entity.toString()) && !var1.pseudo_entities.contains(var6.entity.toString())) {
                if (!var5.entity.equals(var6.entity)) {
                    var3 = false;
                    break;
                }

                if (!var5.tuples.toString().equals(var6.tuples.toString())) {
                    var3 = false;
                }
            } else {
                var3 = true;
            }
        }

        return var3;
    }

    public String firstTuples() {
        String var1 = "";

        for(int var2 = 0; var2 < this.size(); ++var2) {
            Row var3 = (Row)this.elementAt(var2);
            if (var3.tuples.isEmpty()) {
                var1 = var1 + "empty";
            } else {
                var1 = var1 + ((Vector)var3.tuples.elementAt(0)).toString();
            }
        }

        return var1;
    }

    public void addRow(String var1) {
        Tuples var2 = new Tuples();
        var2.addElement(new Vector());
        Row var3 = new Row(var1, var2);
        this.addElement(var3);
    }

    public void addEmptyRow(String var1) {
        Tuples var2 = new Tuples();
        Row var3 = new Row(var1, var2);
        this.addElement(var3);
    }

    public void addRow(String var1, String var2) {
        String var3 = "";
        Tuples var4 = new Tuples();
        if (!var2.equals("")) {
            for(int var5 = 0; var5 < var2.length(); ++var5) {
                if (!var2.substring(var5, var5 + 1).equals(",") && var5 != var2.length() - 1) {
                    var3 = var3 + var2.substring(var5, var5 + 1);
                } else {
                    if (var5 == var2.length() - 1) {
                        var3 = var3 + var2.substring(var5, var5 + 1);
                    }

                    Vector var6 = new Vector();
                    var6.addElement(var3);
                    var4.addElement(var6);
                    var3 = "";
                }
            }
        }

        Row var7 = new Row(var1, var4);
        this.addElement(var7);
    }

    public void addTwoRow(String var1, String var2, String var3) {
        new Tuples();
        Vector var5 = new Vector();
        var5.addElement(var2);
        var5.addElement(var3);
        Row var6 = this.rowWithEntity(var1);
        var6.tuples.addElement(var5);
        if (var6.tuples.size() == 1) {
            this.addElement(var6);
        }

    }

    public void addTuple(Vector var1) {
        String var2 = (String)var1.elementAt(0);
        Vector var3 = (Vector)var1.clone();
        var3.removeElementAt(0);
        Row var4 = this.rowWithEntity(var2);
        var4.tuples.addElement(var3);
        var4.tuples.removeDuplicates();
        var4.tuples.sort();
    }

    public boolean allEmptyTuples(Theory var1) {
        int var2 = 0;

        boolean var3;
        for(var3 = true; var3 && var2 < this.size(); ++var2) {
            Row var4 = (Row)this.elementAt(var2);
            if (!var4.tuples.isEmpty() && !var1.pseudo_entities.contains(var4.entity.toString())) {
                var3 = false;
            }
        }

        return var3;
    }

    public double fullSize() {
        double var1 = 0.0D;

        for(int var3 = 0; var3 < this.size(); ++var3) {
            Row var4 = (Row)this.elementAt(var3);

            for(int var5 = 0; var5 < var4.tuples.size(); ++var5) {
                var1 += (double)((Vector)var4.tuples.elementAt(var5)).size();
            }
        }

        return var1;
    }

    public double applicability() {
        if (this.size() == 0) {
            return 0.0D;
        } else {
            double var1 = 0.0D;

            for(int var3 = 0; var3 < this.size(); ++var3) {
                Row var4 = (Row)this.elementAt(var3);
                if (!var4.tuples.isEmpty()) {
                    ++var1;
                }
            }

            return var1 / (double)this.size();
        }
    }

    public double applicability(Vector var1) {
        if (this.size() == 0) {
            return 0.0D;
        } else {
            double var2 = 0.0D;

            for(int var4 = 0; var4 < this.size(); ++var4) {
                Row var5 = (Row)this.elementAt(var4);
                if (!var5.tuples.isEmpty() && var1.contains(var5.entity)) {
                    ++var2;
                }
            }

            return var2 / (double)var1.size();
        }
    }

    public double plausibility() {
        if (this.size() == 0) {
            return 0.0D;
        } else {
            double var1 = 0.0D;

            for(int var3 = 0; var3 < this.size(); ++var3) {
                Row var4 = (Row)this.elementAt(var3);
                if (!var4.tuples.isEmpty()) {
                    ++var1;
                }
            }

            return var1;
        }
    }

    public double coverage(Categorisation var1) {
        double var2 = 0.0D;

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            Vector var5 = (Vector)var1.elementAt(var4);
            boolean var6 = false;

            for(int var7 = 0; !var6 && var7 < var5.size(); ++var7) {
                String var8 = (String)var5.elementAt(var7);
                Row var9 = this.rowWithEntityNoTuples(var8);
                if (var9.entity.equals(var8) && !var9.tuples.isEmpty()) {
                    ++var2;
                    var6 = true;
                }
            }
        }

        return var2 / (double)var1.size();
    }

    public Vector getEntities() {
        Vector var1 = new Vector();

        for(int var2 = 0; var2 < this.size(); ++var2) {
            var1.addElement(((Row)this.elementAt(var2)).entity);
        }

        return var1;
    }

    public double percentageMatchWith(Datatable var1) {
        double var2 = 0.0D;
        double var4 = 0.0D;

        for(int var6 = 0; var6 < this.size(); ++var6) {
            Row var7 = (Row)this.elementAt(var6);
            Row var8 = (Row)var1.elementAt(var6);
            String var9 = var8.tuples.toString();
            if (var9.equals("[]")) {
                ++var4;
                if (var7.tuples.isEmpty()) {
                    ++var2;
                }
            }

            if (var9.equals("[[]]")) {
                ++var4;
                if (var7.tuples.size() == 1) {
                    ++var2;
                }
            }

            if (!var9.equals("[]") && !var9.equals("[[]]")) {
                for(int var10 = 0; var10 < var7.tuples.size(); ++var10) {
                    ++var4;
                    Vector var11 = (Vector)var7.tuples.elementAt(var10);
                    String var12 = var11.toString();
                    if (var9.indexOf(var12) >= 0) {
                        ++var2;
                    }
                }
            }
        }

        double var13 = var2 / var4;
        return var13;
    }

    public double quickPercentageMatchWith(Theory var1, Datatable var2, Vector var3) {
        double var4 = (double)this.size();

        for(int var6 = 0; var6 < this.size(); ++var6) {
            Row var7 = (Row)this.elementAt(var6);
            Row var8 = (Row)var2.elementAt(var6);
            if (!var1.pseudo_entities.contains(var7.entity)) {
                if (var8.tuples.isEmpty() && !var7.tuples.isEmpty()) {
                    --var4;
                    var3.addElement(new Entity(var7.entity));
                }

                if (!var8.tuples.isEmpty() && var7.tuples.isEmpty()) {
                    --var4;
                    var3.addElement(new Entity(var7.entity));
                }

                if (!var8.tuples.isEmpty() && !var7.tuples.isEmpty()) {
                    String var9 = var7.tuples.toString();
                    String var10 = var8.tuples.toString();
                    if (!var9.equals(var10)) {
                        --var4;
                        var3.addElement(new Entity(var7.entity));
                    }
                }
            }
        }

        double var11 = var4 / (double)this.size();
        return var11;
    }

    public void removeRow(String var1) {
        for(int var2 = 0; var2 < this.size(); ++var2) {
            Row var3 = (Row)this.elementAt(var2);
            if (var3.entity.equals(var1)) {
                this.removeElementAt(var2);
                --var2;
            }
        }

    }

    public void trimRowsToSize() {
        this.trimToSize();

        for(int var1 = 0; var1 < this.size(); ++var1) {
            Row var2 = (Row)this.elementAt(var1);
            var2.tuples.trimToSize();
        }

    }

    public boolean sameExampleForEveryEntity(Theory var1, String var2, AgentWindow var3) {
        var3.writeToFrontEnd("started sameExampleForEveryEntity - testing to see if this entity: " + var2);
        var3.writeToFrontEnd("is a culprit entity for this datatable");
        var3.writeToFrontEnd(this.toTable());
        int var4 = 0;

        boolean var5;
        for(var5 = true; var5 && var4 < this.size(); ++var4) {
            var3.writeToFrontEnd("i is " + var4);
            Row var6 = (Row)this.elementAt(var4);
            if (var6.tuples.size() != 1 && !var1.pseudo_entities.contains(var6.entity.toString())) {
                var5 = false;
            }

            if (var6.tuples.isEmpty()) {
                var5 = false;
            }

            if (var6.tuples.size() <= 1) {
                Vector var7 = (Vector)var6.tuples.elementAt(0);
                String var8 = (String)var7.elementAt(0);
                var3.writeToFrontEnd("vector_in_tuple is " + var7);
                var3.writeToFrontEnd("entity_on_row is " + var8);
                if (!var8.equals(var2) && !var1.pseudo_entities.contains(var6.entity.toString())) {
                    var5 = false;
                }
            }
        }

        return var5;
    }

    public void removeEntity(String var1) {
        for(int var2 = 0; var2 < this.size(); ++var2) {
            if (((Row)this.elementAt(var2)).entity.equals(var1)) {
                this.removeElementAt(var2);
                --var2;
            }
        }

    }

    public Vector getDifference(Datatable var1) {
        Vector var2 = new Vector();
        int var3;
        Row var4;
        Row var5;
        if (this.size() == var1.size()) {
            for(var3 = 0; var3 < this.size(); ++var3) {
                var4 = (Row)this.elementAt(var3);
                var5 = (Row)var1.elementAt(var3);
                if (!var4.tuples.toString().equals(var5.tuples.toString())) {
                    var2.addElement(new Entity(var4.entity));
                }
            }
        }

        if (this.size() < var1.size()) {
            for(var3 = 0; var3 < this.size(); ++var3) {
                var4 = (Row)this.elementAt(var3);
                var5 = (Row)var1.elementAt(var3);
                if (!var4.tuples.toString().equals(var5.tuples.toString())) {
                    var2.addElement(new Entity(var4.entity));
                }
            }

            for(var3 = this.size(); var3 < var1.size(); ++var3) {
                var4 = (Row)var1.elementAt(var3);
                var2.addElement(new Entity(var4.entity));
            }
        }

        if (this.size() > var1.size()) {
            for(var3 = 0; var3 < var1.size(); ++var3) {
                var4 = (Row)this.elementAt(var3);
                var5 = (Row)var1.elementAt(var3);
                if (!var4.tuples.toString().equals(var5.tuples.toString())) {
                    var2.addElement(new Entity(var4.entity));
                }
            }

            for(var3 = var1.size(); var3 < this.size(); ++var3) {
                var4 = (Row)this.elementAt(var3);
                var2.addElement(new Entity(var4.entity));
            }
        }

        return var2;
    }
}
