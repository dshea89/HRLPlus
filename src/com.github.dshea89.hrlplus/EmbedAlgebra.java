package com.github.dshea89.hrlplus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class EmbedAlgebra extends ProductionRule implements Serializable {
    public String input_files_directory = "";
    public Hashtable isomorphisms = new Hashtable();
    public Maths maths = new Maths();
    public Mace mace = new Mace();
    public Hashtable algebra_hashtable = new Hashtable();
    public Vector algebras_to_check_for = new Vector();
    public boolean is_cumulative = false;

    public EmbedAlgebra() {
    }

    public boolean isBinary() {
        return false;
    }

    public String getName() {
        return "embed_algebra";
    }

    public Vector allParameters(Vector concept_list, Theory theory) {
        Concept var3 = (Concept) concept_list.elementAt(0);
        Vector var4 = new Vector();
        if (var3.arity == 4) {
            for(int var5 = 0; var5 < this.algebras_to_check_for.size(); ++var5) {
                Vector var6 = new Vector();
                var6.addElement((String)this.algebras_to_check_for.elementAt(var5));
                var4.addElement(var6);
            }
        }

        return var4;
    }

    public Vector newSpecifications(Vector concept_list, Vector parameters, Theory theory, Vector new_functions) {
        Concept var5 = (Concept) concept_list.elementAt(0);
        String var6 = (String) parameters.elementAt(0);
        Relation var7 = new Relation();
        var7.addDefinition("ab", var5.id + " forms " + var6 + " @b@ for @a@", "ascii");
        var7.name = var5.id + var6;
        Specification var8 = new Specification();
        Vector var9 = new Vector();
        var9.addElement("0");
        var9.addElement("1");
        var8.addRelation(var9, var7);
        var8.permutation.addElement("0");
        var8.permutation.addElement("1");
        Vector var10 = new Vector();
        var10.addElement(var8);
        return var10;
    }

    public Datatable transformTable(Vector old_datatables, Vector old_concepts, Vector parameters, Vector all_concepts) {
        Datatable var5 = new Datatable();
        String var6 = (String) parameters.elementAt(0);
        Datatable var7 = (Datatable) old_datatables.elementAt(0);

        for(int var8 = 0; var8 < var7.size(); ++var8) {
            Hashtable var9 = new Hashtable();
            int var10 = 0;
            Vector var11 = new Vector();
            Row var12 = (Row)var7.elementAt(var8);
            if (!this.isomorphisms.containsKey(var12.entity)) {
                this.isomorphisms.put(var12.entity, var12.entity);
            }

            int var13;
            Vector var14;
            for(var13 = 0; var13 < var12.tuples.size(); ++var13) {
                var14 = (Vector)var12.tuples.elementAt(var13);

                for(int var15 = 0; var15 < var14.size(); ++var15) {
                    String var16 = (String)var14.elementAt(var15);
                    if (!var9.containsKey(var16)) {
                        var9.put(var16, Integer.toString(var10));
                        var11.addElement(Integer.toString(var10));
                        ++var10;
                    }
                }
            }

            var13 = var11.size();
            var14 = (Vector)this.algebra_hashtable.get(var6);
            String var29 = "";
            Vector var30 = new Vector();
            boolean var17 = false;
            String var18 = "";
            Vector var19 = new Vector();

            Vector var21;
            String var22;
            String var23;
            String var24;
            for(int var20 = 0; var20 < var12.tuples.size(); ++var20) {
                var21 = (Vector)var12.tuples.elementAt(var20);
                var22 = (String)var9.get((String)var21.elementAt(0));
                var23 = (String)var9.get((String)var21.elementAt(1));
                var24 = (String)var9.get((String)var21.elementAt(2));
                String var25 = var22 + " * " + var23 + " = " + var24 + ".\n";
                var29 = var29 + var25;
                String var26 = var22 + "," + var23;
                if (var30.contains(var26)) {
                    var17 = true;
                } else {
                    var19.addElement(var24);
                    var30.addElement(var26);
                }
            }

            boolean var31 = true;

            for(int var32 = 0; var32 < var11.size() && var31; ++var32) {
                for(int var34 = 0; var34 < var11.size() && var31; ++var34) {
                    var23 = (String)var11.elementAt(var32) + "," + (String)var11.elementAt(var34);
                    if (!var30.contains(var23)) {
                        var31 = false;
                    } else {
                        int var36 = var30.indexOf(var23);
                        var18 = var18 + (String)var19.elementAt(var36);
                    }
                }
            }

            if (var31 && !var17 && !var18.trim().equals("")) {
                var21 = (Vector)this.algebra_hashtable.get(var6);
                var22 = "set(auto).\nformula_list(usable).\n" + var29;

                for(int var35 = 0; var35 < var21.size(); ++var35) {
                    var24 = (String)var21.elementAt(var35);
                    var22 = var22 + var24 + "\n";
                }

                var22 = var22 + "end_of_list.\n";

                try {
                    PrintWriter var37 = new PrintWriter(new BufferedWriter(new FileWriter("delembedalgebra.in")));
                    var37.write(var22);
                    var37.close();
                } catch (Exception var28) {
                    System.out.println("Having trouble opening file delembedalgebra.in");
                }

                if (this.mace.operating_system.equals("windows")) {
                    try {
                        var23 = this.input_files_directory + "ea.bat " + Integer.toString(var13);
                        Process var39 = Runtime.getRuntime().exec(var23);
                        int var41 = var39.waitFor();
                    } catch (Exception var27) {
                        System.out.println("Having trouble running mace");
                    }
                }

                Hashtable var38 = this.mace.readModelFromMace("delembedalgebra.out");
                Row var40 = new Row(var12.entity, new Tuples());
                if (!var38.isEmpty()) {
                    Vector var42 = new Vector();
                    var42.addElement(this.getIsomorphism(var18));
                    var40.tuples.addElement(var42);
                }

                var5.addElement(var40);
            } else {
                Row var33 = new Row(var12.entity, new Tuples());
                var5.addElement(var33);
            }
        }

        return var5;
    }

    private String getIsomorphism(String var1) {
        String var2 = (String)this.isomorphisms.get(var1);
        if (var2 != null) {
            return var2;
        } else {
            Enumeration var3 = this.isomorphisms.keys();

            String var4;
            Vector var5;
            do {
                if (!var3.hasMoreElements()) {
                    this.isomorphisms.put(var1, var1);
                    return var1;
                }

                var4 = (String)var3.nextElement();
                var5 = new Vector();
                var5.addElement(var1);
                var5.addElement(var4);
            } while(var4.length() != var1.length() || this.maths.nonIsomorphic(var5).size() != 1);

            this.isomorphisms.put(var1, var4);
            return var4;
        }
    }

    public Vector transformTypes(Vector old_concepts, Vector parameters) {
        Vector var3 = (Vector)((Concept) old_concepts.elementAt(0)).types.clone();
        Vector var4 = new Vector();
        String var5 = (String) parameters.elementAt(0);
        var4.addElement((String)var3.elementAt(0));
        var4.addElement(var5);
        return var4;
    }

    public int patternScore(Vector concept_list, Vector all_concepts, Vector entity_list, Vector non_entity_list) {
        byte var5 = 0;
        return var5;
    }
}
