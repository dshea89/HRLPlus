package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class Entity extends TheoryConstituent implements Serializable {
    public boolean required_in_table_format = false;
    public String domain = "";
    public Conjecture conjecture = new Conjecture();
    public String name = "";
    public Hashtable concept_data = new Hashtable();
    public String lakatos_method = "no";

    public Entity() {
    }

    public Entity(String var1) {
        this.name = var1;
    }

    public String fullDetails(Vector var1, String[] var2) {
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.addElement(var2[var4]);
        }

        String var5 = "";
        var5 = this.name + "\n";
        var5 = var5 + this.domain + "\n\n";
        if (var3.contains("representation")) {
            var5 = var5 + this.userConcepts(var1) + "\n\n";
        }

        if (var3.contains("conjecture") && !this.conjecture.writeConjecture("ascii").equals("")) {
            var5 = var5 + "Conjecture:\n";
            var5 = var5 + this.conjecture.writeConjecture("ascii") + "\n";
            var5 = var5 + this.conjecture.writeConjecture("otter");
        }

        if (var3.contains("related")) {
            var5 = var5 + "\n" + this.mostRelated(var1);
        }

        if (var3.contains("distinctive")) {
            var5 = var5 + "\n" + this.distinctiveFor(var1, "ascii");
        }

        return var5;
    }

    public String fullHTMLDetails(Vector var1, String[] var2) {
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < var2.length; ++var4) {
            var3.addElement(var2[var4]);
        }

        String var7 = "<table border=1 bgcolor=yellow><tr><td>" + this.name + "</td></tr></table><br>\n";
        var7 = var7 + this.domain + "<hr>\n";
        if (var3.contains("representation")) {
            var7 = var7 + "<u><b>Values for user-given concepts</b></u><p>\n";
            var7 = var7 + this.userConcepts(var1) + "<p><hr>";
        }

        String var5 = "ascii";
        if (!this.conjecture.writeConjecture("otter").equals("")) {
            int var6 = 0;
            if (var3.contains("ascii conjecture")) {
                ++var6;
                var5 = "ascii";
            }

            if (var3.contains("otter conjecture")) {
                ++var6;
                var5 = "otter";
            }

            if (var3.contains("prolog conjecture")) {
                ++var6;
                var5 = "prolog";
            }

            if (var3.contains("tptp conjecture")) {
                ++var6;
                var5 = "tptp";
            }

            if (var6 == 1) {
                var7 = var7 + "<u><b>Conjecture for which this entity was a counterexample</b></u><p>";
                var7 = var7 + this.conjecture.writeConjectureHTML(var5) + "<p>\n";
            }

            if (var6 > 1) {
                var5 = "ascii";
                var7 = var7 + "<u><b>Conjecture for which this entity was a counterexample</b></u><p><ul>\n";
                if (var3.contains("ascii conjecture")) {
                    var7 = var7 + "<li>" + this.conjecture.writeConjectureHTML("ascii") + "</li>\n";
                }

                if (var3.contains("otter conjecture")) {
                    var7 = var7 + "<li>" + this.conjecture.writeConjectureHTML("otter") + "</li>\n";
                }

                if (var3.contains("prolog conjecture")) {
                    var7 = var7 + "<li>" + this.conjecture.writeConjectureHTML("prolog") + "</li>\n";
                }

                if (var3.contains("tptp conjecture")) {
                    var7 = var7 + "<li>" + this.conjecture.writeConjectureHTML("tptp") + "</li>\n";
                }

                var7 = var7 + "</ul>\n";
            }
        }

        if (var3.contains("related")) {
            var7 = var7 + "<hr><b><u>Most related entities</u></b><br>" + this.mostRelated(var1);
        }

        if (var3.contains("distinctive")) {
            var7 = var7 + "<hr><b><u>Concepts for which this entity is distinct</u></b><p>" + this.distinctiveFor(var1, var5);
        }

        return var7;
    }

    public String userConcepts(Vector var1) {
        String var2 = "<table border=1>\n";

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Concept var4 = (Concept)var1.elementAt(var3);
            if (var4.is_user_given) {
                var2 = var2 + "<tr><td>" + var4.name + "</td><td>" + this.conceptAsTable(var4) + "</td></tr>\n";
            }
        }

        var2 = var2 + "</table>\n";
        return var2;
    }

    private String conceptAsTable(Concept var1) {
        Row var2 = var1.datatable.rowWithEntity(this.name);
        if (var1.arity == 1) {
            return (new Boolean(!var2.tuples.isEmpty())).toString();
        } else if (var1.arity == 2 && var2.tuples.size() == 1) {
            return (String)((Vector)var2.tuples.elementAt(0)).elementAt(0);
        } else {
            boolean var3;
            int var4;
            Function var5;
            String var6;
            String var15;
            String var16;
            String var20;
            if (var1.arity == 3) {
                var3 = false;

                for(var4 = 0; var4 < var1.functions.size(); ++var4) {
                    var5 = (Function)var1.functions.elementAt(var4);
                    if (var5.output_columns.toString().equals("[2]") && var5.input_columns.toString().equals("[0, 1]")) {
                        var3 = true;
                    }
                }

                if (var3) {
                    var15 = "<pre>";
                    var16 = "";
                    var6 = "";

                    for(int var17 = 0; var17 < var2.tuples.size(); ++var17) {
                        Vector var18 = (Vector)var2.tuples.elementAt(var17);
                        String var19 = (String)var18.elementAt(0);
                        var20 = (String)var18.elementAt(1);
                        var15 = var15 + "|" + var19;
                        var16 = var16 + "+";
                        var16 = var16 + this.repeat("-", var19.length());
                        var6 = var6 + "|" + var20;
                        var16 = var16 + this.repeat("-", var20.length() - var19.length());
                    }

                    var15 = var15 + "|\n";
                    var6 = var6 + "|";
                    var16 = var16 + "+\n";
                    return var15 + var16 + var6 + "</pre>";
                }
            }

            if (var1.arity == 4) {
                var3 = false;

                for(var4 = 0; var4 < var1.functions.size(); ++var4) {
                    var5 = (Function)var1.functions.elementAt(var4);
                    if (var5.output_columns.toString().equals("[3]") && var5.input_columns.toString().equals("[0, 1, 2]")) {
                        var3 = true;
                    }
                }

                if (var3) {
                    var15 = "<pre> |";
                    var16 = "";
                    var6 = "";
                    String var7 = "";
                    boolean var8 = true;
                    int var9 = 0;

                    for(int var10 = 0; var10 < var2.tuples.size(); ++var10) {
                        Vector var11 = (Vector)var2.tuples.elementAt(var10);
                        String var12 = (String)var11.elementAt(0);
                        String var13 = (String)var11.elementAt(1);
                        String var14 = (String)var11.elementAt(2);
                        if (!var12.equals(var7)) {
                            if (!var16.equals("")) {
                                var6 = var6 + var16 + "\n";
                                var8 = false;
                            }

                            if (var7.length() > var9) {
                                var9 = var7.length();
                            }

                            var7 = var12;
                            var16 = var12 + "|";
                        }

                        var16 = var16 + var14;
                        if (var8) {
                            var15 = var15 + var13;
                        }
                    }

                    var6 = var6 + var16;
                    var20 = this.repeat("-", var15.trim().length() - 5);
                    var15 = var15 + "\n" + var20;
                    return var15 + "\n" + var6 + "</pre>";
                }
            }

            return var2.tuples.toString();
        }
    }

    private String repeat(String var1, int var2) {
        if (var2 <= 0) {
            return "";
        } else {
            String var3 = "";

            for(int var4 = 0; var4 < var2; ++var4) {
                var3 = var3 + var1;
            }

            return var3;
        }
    }

    public String mostRelated(Vector var1) {
        String var2 = this.name;
        Vector var3 = new Vector();
        Vector var4 = new Vector();

        for(int var5 = 0; var5 < var1.size(); ++var5) {
            Concept var6 = (Concept)var1.elementAt(var5);
            Categorisation var7 = var6.categorisation;

            for(int var8 = 0; var8 < var7.size(); ++var8) {
                Vector var9 = (Vector)var7.elementAt(var8);
                if (var9.contains(var2)) {
                    for(int var10 = 0; var10 < var9.size(); ++var10) {
                        String var11 = (String)var9.elementAt(var10);
                        if (!var11.equals(var2)) {
                            int var12 = var3.indexOf(var11);
                            if (var12 >= 0) {
                                int var13 = (Integer)var4.elementAt(var12);
                                var4.setElementAt(new Integer(var13 + 1), var12);
                            } else {
                                var3.addElement(var11);
                                var4.addElement(new Integer(1));
                            }
                        }
                    }
                }
            }
        }

        SortableVector var14 = new SortableVector();

        for(int var15 = 0; var15 < var3.size(); ++var15) {
            String var17 = (String)var3.elementAt(var15);
            Integer var19 = (Integer)var4.elementAt(var15);
            int var21 = var19;
            var14.addElement(var17, var21);
        }

        String var16 = "<table border=0><tr><td><u>Entity</u></td><td><u>Number of relating concepts</u></td></tr>\n";

        for(int var18 = 0; var18 < var14.size(); ++var18) {
            String var20 = (String)var14.elementAt(var18);
            Double var22 = (Double)var14.values.elementAt(var18);
            var16 = var16 + "<tr><td>" + var20 + "</td><td align=center>" + var22.toString() + "</td></tr>\n";
        }

        var16 = var16 + "</table>\n";
        return var16;
    }

    public String distinctiveFor(Vector var1, String var2) {
        String var3 = "";

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            Concept var5 = (Concept)var1.elementAt(var4);
            Categorisation var6 = var5.categorisation;
            boolean var7 = false;

            for(int var8 = 0; var8 < var6.size() && !var7; ++var8) {
                Vector var9 = (Vector)var6.elementAt(var8);
                if (var9.contains(this.name)) {
                    var7 = true;
                    if (var9.size() == 1) {
                        var3 = var3 + var5.id + ". " + this.replaceLTForHTML(var5.writeDefinitionWithStartLetters(var2)) + "<br>\n";
                    }
                }
            }
        }

        return var3;
    }

    public String toString() {
        return this.name;
    }

    public boolean entitiesContains(Vector var1) {
        for(int var2 = 0; var2 < var1.size(); ++var2) {
            Entity var3 = (Entity)var1.elementAt(var2);
            if (this.name.equals(var3.name)) {
                return true;
            }
        }

        return false;
    }

    public Vector removeEntityStringFromVector(Vector var1, AgentWindow var2) {
        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Object var4 = var1.elementAt(var3);
            if (var4 instanceof String) {
                String var5 = (String)var1.elementAt(var3);
                if (this.toString().equals(var5)) {
                    var1.removeElementAt(var3);
                }
            }

            if (var4 instanceof Entity) {
                Entity var6 = (Entity)var1.elementAt(var3);
                if (this.toString().equals(var6.toString())) {
                    var1.removeElementAt(var3);
                }
            }
        }

        return var1;
    }

    public boolean equals(Object var1) {
        if (!(var1 instanceof Entity)) {
            System.out.println("Warning: Entity equals method - not an entity");
            if (var1 instanceof Conjecture) {
                System.out.println("it's a conj: " + ((Conjecture)var1).writeConjecture());
            }

            if (var1 instanceof Concept) {
                System.out.println("it's a concept: " + ((Concept)var1).writeDefinition());
            }
        } else if (this.toString().equals(((Entity)var1).toString())) {
            return true;
        }

        return false;
    }
}
