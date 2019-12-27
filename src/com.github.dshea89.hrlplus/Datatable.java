package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

/**
 * A class representing the data for a given concept. It is a vector of rows.
 */
public class Datatable extends Vector implements Serializable {
    /**
     * The number of tuples in the datatable.
     */
    public int number_of_tuples = 0;

    public Datatable() {
    }

    /**
     * Calculates the number of tuples
     */
    public void setNumberOfTuples() {
        for(int var1 = 0; var1 < this.size(); ++var1) {
            Row var2 = (Row)this.elementAt(var1);
            this.number_of_tuples += var2.tuples.size();
        }

    }

    /**
     * Writes the table neatly.
     */
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

    /**
     * Sorts the datatable using a lexicographic ordering of the entities which each row represents.
     */
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

    /**
     * Returns a clone of the datatable.
     */
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

    /**
     * Finds the row which corresponds to the given entity.
     */
    public Row rowWithEntity(String entity) {
        int var2;
        for(var2 = 0; var2 < this.size() && !((Row)this.elementAt(var2)).entity.equals(entity); ++var2) {
            ;
        }

        return var2 < this.size() ? (Row)this.elementAt(var2) : new Row(entity, new Tuples());
    }

    /**
     * Finds the row which corresponds to the given entity. Does not return a row with the entity if the count is less
     * than the size of the data table.
     */
    private Row rowWithEntityNoTuples(String entity) {
        int var2;
        for(var2 = 0; var2 < this.size() && !((Row)this.elementAt(var2)).entity.equals(entity); ++var2) {
            ;
        }

        return var2 < this.size() ? (Row)this.elementAt(var2) : new Row("", new Tuples());
    }

    /**
     * Checks to see if the given entity is in the datatable.
     */
    public boolean hasEntity(String entity) {
        int var2;
        for(var2 = 0; var2 < this.size() && !((Row)this.elementAt(var2)).entity.equals(entity); ++var2) {
            ;
        }

        return var2 < this.size();
    }

    /**
     * Writes the datatable not as a vector of rows, but as a vector of tuples each starting with the entity of the row
     * the tuple was taken from.
     */
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

    /**
     * Writes a flat table back as a vector of rows.
     */
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

    /**
     * Checks whether this datatable is identical to the other given datatable.
     */
    public boolean isIdenticalTo(Theory theory, Datatable other_table) {
        if (!theory.pseudo_entities.isEmpty()) {
            ;
        }

        boolean var3 = true;
        int var4 = 0;
        if (theory.pseudo_entities.isEmpty() && other_table.size() != this.size()) {
            var3 = false;
        }

        if (!theory.pseudo_entities.isEmpty() && other_table.size() != this.size() && (other_table.size() > this.size() + theory.pseudo_entities.size() || this.size() > other_table.size() + theory.pseudo_entities.size())) {
            var3 = false;
        }

        for(; var4 < this.size() && var3; ++var4) {
            Row var5 = (Row)this.elementAt(var4);
            Row var6 = (Row)other_table.elementAt(var4);
            if (!theory.pseudo_entities.contains(var5.entity.toString()) && !theory.pseudo_entities.contains(var6.entity.toString())) {
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

    /**
     * Returns a string representation of the first tuple for each row
     */
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

    /**
     * Adds a row to the datatable
     */
    public void addRow(String entity) {
        Tuples var2 = new Tuples();
        var2.addElement(new Vector());
        Row var3 = new Row(entity, var2);
        this.addElement(var3);
    }

    /**
     * Adds an empty row to the datatable
     */
    public void addEmptyRow(String entity) {
        Tuples var2 = new Tuples();
        Row var3 = new Row(entity, var2);
        this.addElement(var3);
    }

    /**
     * Adds a row to the datatable
     */
    public void addRow(String entity, String elements) {
        String var3 = "";
        Tuples var4 = new Tuples();
        if (!elements.equals("")) {
            for(int var5 = 0; var5 < elements.length(); ++var5) {
                if (!elements.substring(var5, var5 + 1).equals(",") && var5 != elements.length() - 1) {
                    var3 = var3 + elements.substring(var5, var5 + 1);
                } else {
                    if (var5 == elements.length() - 1) {
                        var3 = var3 + elements.substring(var5, var5 + 1);
                    }

                    Vector var6 = new Vector();
                    var6.addElement(var3);
                    var4.addElement(var6);
                    var3 = "";
                }
            }
        }

        Row var7 = new Row(entity, var4);
        this.addElement(var7);
    }

    /**
     * Adds a row to the datatable
     */
    public void addTwoRow(String entity, String element1, String element2) {
        new Tuples();
        Vector var5 = new Vector();
        var5.addElement(element1);
        var5.addElement(element2);
        Row var6 = this.rowWithEntity(entity);
        var6.tuples.addElement(var5);
        if (var6.tuples.size() == 1) {
            this.addElement(var6);
        }

    }

    /**
     * Adds a row to the datatable
     */
    public void addTuple(Vector tuple) {
        String var2 = (String)tuple.elementAt(0);
        Vector var3 = (Vector)tuple.clone();
        var3.removeElementAt(0);
        Row var4 = this.rowWithEntity(var2);
        var4.tuples.addElement(var3);
        var4.tuples.removeDuplicates();
        var4.tuples.sort();
    }

    /**
     * Checks whether the datatable has all empty rows.
     */
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

    /**
     * This returns the number of ground objects (inside all the tuples inside the rows) in the datatable.
     */
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

    /**
     * This returns the proportion of entities in the datatable which have something in their row.
     */
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

    /**
     * This returns the proportion of entities in the datatable which have something in their row.
     */
    public double applicability(Vector entities) {
        if (this.size() == 0) {
            return 0.0D;
        } else {
            double var2 = 0.0D;

            for(int var4 = 0; var4 < this.size(); ++var4) {
                Row var5 = (Row)this.elementAt(var4);
                if (!var5.tuples.isEmpty() && entities.contains(var5.entity)) {
                    ++var2;
                }
            }

            return var2 / (double)entities.size();
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

    /**
     * This returns the proportion of categories containing an entity which has a non-empty row.
     */
    public double coverage(Categorisation cat) {
        double var2 = 0.0D;

        for(int var4 = 0; var4 < cat.size(); ++var4) {
            Vector var5 = (Vector)cat.elementAt(var4);
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

        return var2 / (double)cat.size();
    }

    /**
     * Returns the entities in the rows, as they appear in the table.
     */
    public Vector getEntities() {
        Vector var1 = new Vector();

        for(int var2 = 0; var2 < this.size(); ++var2) {
            var1.addElement(((Row)this.elementAt(var2)).entity);
        }

        return var1;
    }

    /**
     * Returns the percentage number of tuples of this table which are also tuples of the given table.
     */
    public double percentageMatchWith(Datatable other_table) {
        double var2 = 0.0D;
        double var4 = 0.0D;

        for(int var6 = 0; var6 < this.size(); ++var6) {
            Row var7 = (Row)this.elementAt(var6);
            Row var8 = (Row)other_table.elementAt(var6);
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

    /**
     * Does a quick comparison (based on entities, rather than tuples) of the number of matches this table has with the given one.
     * Altered by alisonp on 12/4 to record counter-examples
     */
    public double quickPercentageMatchWith(Theory theory, Datatable other_table, Vector counterexamples) {
        double var4 = (double)this.size();

        for(int var6 = 0; var6 < this.size(); ++var6) {
            Row var7 = (Row)this.elementAt(var6);
            Row var8 = (Row)other_table.elementAt(var6);
            if (!theory.pseudo_entities.contains(var7.entity)) {
                if (var8.tuples.isEmpty() && !var7.tuples.isEmpty()) {
                    --var4;
                    counterexamples.addElement(new Entity(var7.entity));
                }

                if (!var8.tuples.isEmpty() && var7.tuples.isEmpty()) {
                    --var4;
                    counterexamples.addElement(new Entity(var7.entity));
                }

                if (!var8.tuples.isEmpty() && !var7.tuples.isEmpty()) {
                    String var9 = var7.tuples.toString();
                    String var10 = var8.tuples.toString();
                    if (!var9.equals(var10)) {
                        --var4;
                        counterexamples.addElement(new Entity(var7.entity));
                    }
                }
            }
        }

        double var11 = var4 / (double)this.size();
        return var11;
    }

    /**
     * Removes the rows with the given entities
     */
    public void removeRow(String entity) {
        for(int var2 = 0; var2 < this.size(); ++var2) {
            Row var3 = (Row)this.elementAt(var2);
            if (var3.entity.equals(entity)) {
                this.removeElementAt(var2);
                --var2;
            }
        }
    }

    /**
     * This function uses the trimToSize() method from Vector to trim both itself and the tuples in the rows.
     * This should reduce the size of the memory required to store the datatable.
     */
    public void trimRowsToSize() {
        this.trimToSize();

        for(int var1 = 0; var1 < this.size(); ++var1) {
            Row var2 = (Row)this.elementAt(var1);
            var2.tuples.trimToSize();
        }

    }

    /**
     * Test to see whether this entity is a culprit entity for this datatable.
     */
    public boolean sameExampleForEveryEntity(Theory theory, String entity, AgentWindow window) {
        window.writeToFrontEnd("started sameExampleForEveryEntity - testing to see if this entity: " + entity);
        window.writeToFrontEnd("is a culprit entity for this datatable");
        window.writeToFrontEnd(this.toTable());
        int var4 = 0;

        boolean var5;
        for(var5 = true; var5 && var4 < this.size(); ++var4) {
            window.writeToFrontEnd("i is " + var4);
            Row var6 = (Row)this.elementAt(var4);
            if (var6.tuples.size() != 1 && !theory.pseudo_entities.contains(var6.entity.toString())) {
                var5 = false;
            }

            if (var6.tuples.isEmpty()) {
                var5 = false;
            }

            if (var6.tuples.size() <= 1) {
                Vector var7 = (Vector)var6.tuples.elementAt(0);
                String var8 = (String)var7.elementAt(0);
                window.writeToFrontEnd("vector_in_tuple is " + var7);
                window.writeToFrontEnd("entity_on_row is " + var8);
                if (!var8.equals(entity) && !theory.pseudo_entities.contains(var6.entity.toString())) {
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
