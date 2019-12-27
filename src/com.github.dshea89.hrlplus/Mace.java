package com.github.dshea89.hrlplus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class Mace extends DataGenerator implements Serializable {
    public Mace() {
    }

    public Hashtable readModelFromMace(String var1) {
        Hashtable var2 = new Hashtable();
        System.out.println("Thurs - 1");

        try {
            System.out.println("Thurs - 2");
            BufferedReader var3 = new BufferedReader(new FileReader(var1));
            System.out.println("Thurs - 3");
            String var4 = "";
            boolean var5 = true;
            boolean var6 = false;
            boolean var7 = false;
            boolean var8 = false;
            boolean var9 = false;
            boolean var10 = false;
            boolean var11 = false;
            int var12 = 0;
            String var13 = "";
            Tuples var14 = new Tuples();
            Vector var15 = new Vector();
            Vector var16 = new Vector();
            System.out.println("Thurs - 4");
            var4 = var3.readLine().trim();
            System.out.println("Thurs - 5");

            String var19;
            while(var4 != null && !var7 && (var5 || !var4.equals(")"))) {
                if (var8) {
                    if (var5 && var4.indexOf("((") >= 0 && var12 > 1) {
                        var10 = true;
                        var5 = false;
                    }

                    if (var11) {
                        var9 = true;
                        var11 = false;
                    }

                    if (var10) {
                        var13 = var4.substring(2, var4.indexOf(" "));
                        var10 = false;
                        var11 = true;
                    }

                    String var17;
                    if (var4.indexOf("))") >= 0) {
                        if (!var13.equals("") && !var13.equals("equal") && !var14.isEmpty() && var13.indexOf("$") < 0) {
                            var2.put(var13, var14);
                        }

                        if (var4.indexOf("((") >= 0) {
                            var13 = var4.substring(2, var4.indexOf(" "));
                            if (var13.indexOf("$") < 0) {
                                var15 = new Vector();
                                var14 = new Tuples();
                                var17 = var4.substring(var4.lastIndexOf(" ") + 1, var4.indexOf("))"));
                                var15.addElement(var17);
                                var14.addElement(var15);
                                if (!var16.contains(var17)) {
                                    var16.addElement(var17);
                                }

                                var2.put(var13, var14);
                            }
                        }

                        var9 = false;
                        var10 = true;
                        var15 = new Vector();
                        var14 = new Tuples();
                    }

                    if (var9 && !var10) {
                        var17 = var4.substring(2, var4.indexOf(")"));
                        String var18 = "";

                        while(var17.indexOf(" ") >= 0) {
                            var18 = var17.substring(0, var17.indexOf(" "));
                            var15.addElement(var18);
                            var17 = var17.substring(var17.indexOf(" ") + 1, var17.length());
                            if (!var16.contains(var18) && !var18.equals(var13) && !var18.equals(".") && !var18.equals("")) {
                                var16.addElement(var18);
                            }
                        }

                        var15.addElement(var17);
                        var19 = var4.substring(var4.lastIndexOf(" ") + 1, var4.length() - 1);
                        var15.addElement(var19);
                        if (!var15.contains(var13)) {
                            var14.addElement((Vector)var15.clone());
                        }

                        var15 = new Vector();
                        if (!var16.contains(var19) && !var19.equals(var13) && !var19.equals(".") && !var19.equals("")) {
                            var16.addElement(var19);
                        }
                    }

                    ++var12;
                }

                if (var4.equals(";; BEGINNING OF IVY MODEL")) {
                    var8 = true;
                }

                var4 = var3.readLine();
                if (var4 != null) {
                    var4 = var4.trim();
                }
            }

            if (!var16.isEmpty()) {
                Tuples var22 = new Tuples();

                for(int var23 = 0; var23 < var16.size(); ++var23) {
                    var19 = (String)var16.elementAt(var23);
                    if (!var19.equals("t") && !var19.equals("nil")) {
                        Vector var20 = new Vector();
                        var20.addElement(var19);
                        var22.addElement(var20);
                    }
                }

                var22.sort();
                var2.put("element", var22);
            }
        } catch (Exception var21) {
            System.out.println("Problem reading Mace model");
            System.out.println(var21);
        }

        return var2;
    }

    public String conjectureStatement(Conjecture var1) {
        String var2 = var1.writeConjecture("otter");
        if (!var2.equals("")) {
            if (var1 instanceof NonExists) {
                var2 = var2.substring(1, var2.length());
            } else {
                var2 = "-(" + var2 + ").";
            }
        }

        if (var1 instanceof NonExists) {
            NonExists var3 = (NonExists)var1;
            if (var3.concept.is_object_of_interest_concept) {
                var2 = "";
            }
        }

        String var6 = "set(auto).\n";
        var6 = var6 + "formula_list(usable).\n";
        var6 = var6 + this.writeAxioms();

        for(int var4 = 0; var4 < this.axiom_conjectures.size(); ++var4) {
            Conjecture var5 = (Conjecture)this.axiom_conjectures.elementAt(var4);
            var6 = var6 + var5.writeConjecture("otter") + ".\n";
        }

        if (this.use_ground_instances) {
            var6 = var6 + this.ground_instances_as_axioms + "\n";
        }

        var6 = var6 + var2;
        var6 = var6 + "\nend_of_list.\n";
        return var6;
    }

    public Vector counterexamplesFor(Conjecture conj, Theory theory, int num_required) {
        String var4 = this.executionParameter("size_limit");
        String var5 = this.executionParameter("time_limit");
        Vector var6 = new Vector();
        Entity var7 = new Entity();
        String var8 = "";
        boolean var9 = false;
        boolean var10 = false;
        this.operating_system = "york_unix";
        if (conj instanceof NonExists) {
            NonExists var11 = (NonExists) conj;
            if (var11.concept.is_object_of_interest_concept) {
                var10 = true;
            }
        }

        String var22 = this.conjectureStatement(conj);

        try {
            PrintWriter var12 = new PrintWriter(new BufferedWriter(new FileWriter("delmodel.in")));
            var12.write(var22);
            var12.close();
        } catch (Exception var20) {
            System.out.println("Having trouble opening file delmodel.in");
        }

        try {
            if (this.operating_system.equals("jcu_unix")) {
                String[] var23 = new String[]{"/bin/sh", "-c", "/home/guest/hr/devel/mace1.bat"};
                Process var13 = Runtime.getRuntime().exec(var23);
                var13.waitFor();
            }

            if (this.operating_system.equals("york_unix")) {
                Process var24 = Runtime.getRuntime().exec("mace1.bat");
                int var26 = var24.waitFor();
            }

            if (this.operating_system.equals("windows")) {
                boolean var25 = false;
                Process var14;
                int var15;
                String var27;
                if (var10) {
                    var27 = this.input_files_directory + "mace2.bat 1 " + var5;
                    var14 = Runtime.getRuntime().exec(var27);
                    var15 = var14.waitFor();
                    Hashtable var16 = this.readModelFromMace("delmodel.out");
                    if (var16.isEmpty()) {
                        var25 = true;
                    }
                }

                if (!var10 || var25) {
                    var27 = this.input_files_directory + "mace1.bat " + var4 + " " + var5;
                    var14 = Runtime.getRuntime().exec(var27);
                    var15 = var14.waitFor();
                }
            }

            Hashtable var28 = this.readModelFromMace("delmodel.out");
            if (!var28.isEmpty()) {
                var7.concept_data = var28;
                var7.conjecture = conj;
                var6.addElement(var7);
                conj.counterexamples.addElement(var7);
                conj.proof_status = "disproved";
            }

            Enumeration var29 = var28.keys();
            String var30 = "";
            boolean var31 = false;

            while(var29.hasMoreElements()) {
                String var32 = (String)var29.nextElement();
                if (var32.equals("*")) {
                    Tuples var17 = (Tuples)var28.get(var32);

                    for(int var18 = 0; var18 < var17.size(); ++var18) {
                        Vector var19 = (Vector)var17.elementAt(var18);
                        var30 = var30 + (String)var19.lastElement();
                    }
                }

                if (var32.equals("+")) {
                    var31 = true;
                }
            }

            if (!var31) {
                var7.name = var30;
            } else {
                var7.name = "new_" + Integer.toString(theory.entities.size());
            }
        } catch (Exception var21) {
            System.out.println("Having trouble running MACE");
        }

        return var6;
    }
}
