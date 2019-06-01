package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

public class NonExists extends Conjecture implements Serializable {
    public Concept concept = new Concept();

    public NonExists() {
    }

    public NonExists(Concept var1, String var2) {
        this.concept = var1;
        if (this.concept.is_entity_instantiations) {
            this.involves_instantiation = true;
        }

        this.id = var2;
        this.type = "non-exists";
        this.object_type = this.concept.object_type;
    }

    public String getDomain() {
        return this.concept.domain;
    }

    public String writeConjecture(String var1) {
        Vector var2 = this.concept.definition_writer.lettersForTypes(this.concept.types, "ascii", new Vector());
        String var3 = "";
        if (var1.equals("ascii")) {
            var3 = "not exists " + var2.toString().substring(1, var2.toString().length() - 1) + " s.t. ( ";
            var3 = var3 + this.concept.writeDefinition(var1) + " ) ";
        }

        if (var1.equals("prolog")) {
            var3 = "not exists " + var2.toString().substring(1, var2.toString().length() - 1).toUpperCase() + " s.t. ( ";
            var3 = var3 + this.concept.writeDefinition(var1) + " ) ";
        }

        if (var1.equals("otter")) {
            if (var2.size() <= 1 && !this.use_entity_letter) {
                if (!this.concept.writeDefinition(var1).equals("")) {
                    var3 = "-(" + this.concept.writeDefinition(var1) + ")";
                }
            } else {
                var3 = "-(exists ";
                byte var4 = 1;
                if (this.use_entity_letter) {
                    var4 = 0;
                }

                for(int var5 = var4; var5 < var2.size(); ++var5) {
                    var3 = var3 + (String)var2.elementAt(var5) + " ";
                }

                var3 = var3 + "(" + this.concept.writeDefinition(var1) + "))";
            }
        }

        return var3;
    }

    public boolean subsumes(NonExists var1) {
        boolean var2 = true;
        if (var1.type.equals("non-exists")) {
            for(int var3 = 0; var2 && var3 < this.concept.specifications.size(); ++var3) {
                Specification var4 = (Specification)this.concept.specifications.elementAt(var3);
                if (!var1.concept.specifications.contains(var4)) {
                    var2 = false;
                }
            }
        }

        return var2;
    }

    public Vector implicates(Vector var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < this.concept.specifications.size(); ++var3) {
            Vector var4 = (Vector)this.concept.specifications.clone();
            var4.removeElementAt(var3);
            boolean var5 = false;
            Concept var6 = new Concept();

            for(int var7 = 0; var7 < var1.size() && !var5; ++var7) {
                var6 = (Concept)var1.elementAt(var7);
                var5 = true;
                if (var6.specifications.size() == var4.size()) {
                    for(int var8 = 0; var8 < var6.specifications.size() && var5; ++var8) {
                        Specification var9 = (Specification)var6.specifications.elementAt(var8);
                        if (!var9.equals((Specification)var4.elementAt(var8))) {
                            var5 = false;
                        }
                    }
                } else {
                    var5 = false;
                }
            }

            if (var5) {
                Specification var14 = ((Specification)this.concept.specifications.elementAt(var3)).copy();
                Specification var15 = new Specification();
                if (!var14.type.equals("negate")) {
                    var15.type = "negate";
                    var15.previous_specifications.addElement(var14);
                    var15.permutation = (Vector)var14.permutation.clone();
                    Vector var17 = new Vector();

                    for(int var18 = 0; var18 < var14.permutation.size(); ++var18) {
                        var17.addElement(Integer.toString(var15.permutation.indexOf((String)var14.permutation.elementAt(var18))));
                    }

                    var14.permutation = var17;
                    Implicate var19 = new Implicate(var6, var15, new Step());
                    var19.overriding_types = (Vector)this.concept.types.clone();
                    var2.addElement(var19);
                } else {
                    for(int var16 = 0; var16 < var14.previous_specifications.size(); ++var16) {
                        Vector var10 = new Vector();
                        Specification var11 = ((Specification)var14.previous_specifications.elementAt(var16)).copy();

                        for(int var12 = 0; var12 < var11.permutation.size(); ++var12) {
                            int var13 = new Integer((String)var11.permutation.elementAt(var12));
                            var10.addElement(var14.permutation.elementAt(var13));
                        }

                        var11.permutation = var10;
                        Implicate var20 = new Implicate(var6, var11, new Step());
                        var20.overriding_types = (Vector)this.concept.types.clone();
                        var2.addElement(var20);
                    }
                }
            }
        }

        return var2;
    }
}
