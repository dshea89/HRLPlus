package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JTable;

public class ExplanationHandler implements Serializable {
    public boolean store_conjectures = false;
    public StorageHandler storage_handler = new StorageHandler();
    public SortableVector explainers = new SortableVector();
    public Hashtable previous_implicates = new Hashtable();
    public Hashtable previous_implications = new Hashtable();
    public Hashtable previous_equivalences = new Hashtable();
    public Hashtable previous_non_exists = new Hashtable();
    public String operating_system = "";
    public String input_files_directory = "";
    public boolean use_entity_letter = false;
    public boolean use_ground_instances = false;
    public boolean use_all_explainers = false;
    public HoldBackChecker hold_back_checker = new HoldBackChecker();
    public Reflection reflect = new Reflection();

    public ExplanationHandler() {
    }

    public void explainConjecture(Conjecture var1, Theory var2, String var3) {
        this.logThis(var1.writeConjecture("otter"), var2);

        for(int var4 = 0; var4 < this.explainers.size(); ++var4) {
            Explainer var5 = (Explainer)this.explainers.elementAt(var4);
            var5.use_entity_letter = this.use_entity_letter;
            var2.addToTimer(var3 + "." + Integer.toString(var4) + " Trying " + var5.name + " to solve conjecture");
            if (!var5.condition_string.trim().equals("") && !this.reflect.checkCondition(var1, var5.condition_string)) {
                this.logThis("Failed conditions for " + var5.name + "(" + var5.setup_name + ")", var2);
            } else {
                boolean var7;
                if (var5 instanceof FileProver) {
                    FileProver var6 = (FileProver)var5;
                    var7 = var6.prove(var1, var2);
                    this.logThis(var5.name + "(" + var5.setup_name + ") " + var1.proof_status, var2);
                    if (var7) {
                        var1.proof_status = "proved";
                        if (var1.is_trivially_true) {
                            var1.explained_by = "being trivial";
                        } else {
                            var1.explained_by = var5.name + "(" + var5.setup_name + ")";
                        }

                        if (!this.use_all_explainers) {
                            this.logThis("-----------------", var2);
                            if (this.store_conjectures) {
                                this.storage_handler.handleStorageOf(var1);
                            }

                            return;
                        }
                    }

                    if (!var1.counterexamples.isEmpty()) {
                        var1.proof_status = "disproved";
                        this.logThis(var5.name + "(" + var5.setup_name + ") " + var1.proof_status, var2);
                        var1.explained_by = var5.name + "(" + var5.setup_name + ")";
                        if (!this.use_all_explainers) {
                            this.logThis("-----------------", var2);
                            if (this.store_conjectures) {
                                this.storage_handler.handleStorageOf(var1);
                            }

                            return;
                        }
                    }

                    if (var1.proof_status.equals("sos") && !this.use_all_explainers) {
                        this.logThis("-----------------", var2);
                        if (this.store_conjectures) {
                            this.storage_handler.handleStorageOf(var1);
                        }

                        return;
                    }
                }

                if (var5 instanceof DataGenerator) {
                    DataGenerator var8 = (DataGenerator)var5;
                    Vector var10 = var8.counterexamplesFor(var1, var2, 1);
                    if (!var10.isEmpty()) {
                        var1.proof_status = "disproved";
                        this.logThis(var5.name + "(" + var5.setup_name + ") " + var1.proof_status, var2);
                        var1.counterexamples = var10;
                        var1.explained_by = var5.name + "(" + var5.setup_name + ")";
                        if (!this.use_all_explainers) {
                            this.logThis("-----------------", var2);
                            if (this.store_conjectures) {
                                this.storage_handler.handleStorageOf(var1);
                            }

                            return;
                        }
                    }
                }

                if (var5 instanceof Prover && !(var5 instanceof FileProver)) {
                    Prover var9 = (Prover)this.explainers.elementAt(var4);
                    var7 = var9.prove(var1, var2);
                    this.logThis(var5.name + "(" + var5.setup_name + ") " + var1.proof_status, var2);
                    if (var7) {
                        var1.proof_status = "proved";
                        if (var1.is_trivially_true) {
                            var1.explained_by = "being trivial";
                        } else {
                            var1.explained_by = var5.name + "(" + var5.setup_name + ")";
                        }

                        if (!this.use_all_explainers) {
                            this.logThis("-----------------", var2);
                            if (this.store_conjectures) {
                                this.storage_handler.handleStorageOf(var1);
                            }

                            return;
                        }
                    }
                }
            }
        }

        if (this.store_conjectures) {
            this.storage_handler.handleStorageOf(var1);
        }

        this.logThis("-----------------", var2);
    }

    public void initialiseExplainers(Theory var1) {
        Hashtable var2 = new Hashtable();
        Hashtable var3 = new Hashtable();
        JTable var4 = var1.front_end.axiom_table;

        String var7;
        for(int var5 = 0; var5 < var4.getRowCount(); ++var5) {
            String var6 = (String)var4.getValueAt(var5, 0);
            if (var6 instanceof String && var6.trim().equals("")) {
                break;
            }

            if (var6 instanceof String) {
                var7 = (String)var4.getValueAt(var5, 1);
                var2.put(var6.trim(), var7.trim());
            }
        }

        JTable var22 = var1.front_end.algebra_table;

        String var8;
        String var13;
        String var14;
        String var28;
        for(int var23 = 0; var23 < var22.getRowCount(); ++var23) {
            var7 = (String)var22.getValueAt(var23, 0);
            if (var7 instanceof String && !var7.trim().equals("")) {
                var8 = (String)var22.getValueAt(var23, 1);
                Vector var10;
                if (var8 instanceof String) {
                    Categorisation var9 = new Categorisation("[" + var8 + "]");
                    var10 = (Vector)var9.elementAt(0);
                    var3.put(var7, var10);
                }

                var28 = (String)var22.getValueAt(var23, 2);
                if (var28 instanceof String && (var28.equals("true") || var28.equals("yes"))) {
                    var10 = (Vector)var3.get(var7);
                    Vector var11 = new Vector();

                    for(int var12 = 0; var12 < var10.size(); ++var12) {
                        var13 = (String)var10.elementAt(var12);
                        var14 = (String)var2.get(var13);
                        var11.addElement(var14);
                    }

                    var1.embed_algebra.algebra_hashtable.put(var7, var11);
                }
            }
        }

        JTable var24 = var1.front_end.file_prover_table;

        String var15;
        String var16;
        String var17;
        String var32;
        for(int var25 = 0; var25 < var24.getRowCount(); ++var25) {
            var8 = (String)var24.getValueAt(var25, 0);
            if (var8 == null || var8 instanceof String && var8.trim().equals("")) {
                break;
            }

            if (var8 instanceof String) {
                var28 = (String)var24.getValueAt(var25, 1);
                FileProver var29 = new FileProver(var28, var1.storage_handler);
                var29.try_pos = new Integer(var8);
                var29.name = "file:" + var28;
                var32 = (String)var24.getValueAt(var25, 3);
                if (!var32.equals("")) {
                    Categorisation var33 = new Categorisation("[" + var32 + "]");
                    Vector var35 = (Vector)var33.elementAt(0);

                    for(int var37 = 0; var37 < var35.size(); ++var37) {
                        var15 = (String)var35.elementAt(var37);
                        var16 = var15.substring(0, var15.indexOf(":"));
                        var17 = var15.substring(var15.indexOf(":") + 1, var15.length());
                        Vector var18 = new Vector();
                        var18.addElement(var16);
                        var18.addElement(var17);
                        var29.operation_substitutions.addElement(var18);
                    }
                }

                this.explainers.addElement(var29, "try_pos");
            }
        }

        JTable var26 = var1.front_end.other_prover_table;

        int var27;
        for(var27 = 0; var27 < var26.getRowCount(); ++var27) {
            var28 = (String)var26.getValueAt(var27, 0);
            if (var28 == null) {
                break;
            }

            var28 = var28.trim();
            if (var28 instanceof String && var28.trim().equals("")) {
                break;
            }

            if (var28 instanceof String) {
                Object var31 = new Explainer();
                var32 = (String)var26.getValueAt(var27, 1);
                if (var32 instanceof String) {
                    if (var32.equals("HoldBackChecker")) {
                        var31 = this.hold_back_checker;
                    } else {
                        try {
                            Object var34 = Class.forName(var32).newInstance();
                            var31 = (Explainer)var34;
                        } catch (Exception var21) {
                            ;
                        }
                    }

                    ((Explainer)var31).try_pos = new Integer(var28);
                    ((Explainer)var31).name = var32;
                    this.explainers.addElement(var31, "try_pos");
                }

                String var36 = (String)var26.getValueAt(var27, 2);
                if (var36 instanceof String) {
                    ((Explainer)var31).condition_string = var36;
                }

                var13 = (String)var26.getValueAt(var27, 3);
                if (var13 instanceof String && !var13.trim().equals("")) {
                    Vector var38 = (Vector)var3.get(var13);

                    for(int var39 = 0; var39 < var38.size(); ++var39) {
                        var16 = (String)var38.elementAt(var39);
                        var17 = (String)var2.get(var16);
                        ((Explainer)var31).axiom_strings.addElement(var17);
                    }
                }

                var14 = (String)var26.getValueAt(var27, 4);
                if (var14 instanceof String && !var14.trim().equals("")) {
                    Categorisation var40 = new Categorisation("[" + var14 + "]");
                    Vector var41 = (Vector)var40.elementAt(0);

                    for(int var42 = 0; var42 < var41.size(); ++var42) {
                        String var43 = (String)var41.elementAt(var42);
                        String var19 = var43.substring(0, var43.indexOf("="));
                        String var20 = var43.substring(var43.indexOf("=") + 1, var43.length());
                        ((Explainer)var31).execution_parameters.put(var19, var20);
                    }
                }

                var15 = (String)var26.getValueAt(var27, 5);
                if (var15 instanceof String && !var15.equals("")) {
                    ((Explainer)var31).setup_name = var15;
                }
            }
        }

        this.explainers.reverse();

        for(var27 = 0; var27 < this.explainers.size(); ++var27) {
            Explainer var30 = (Explainer)this.explainers.elementAt(var27);
            var30.operating_system = this.operating_system;
            var30.input_files_directory = this.input_files_directory;
            if (this.use_ground_instances) {
                this.setGroundInstancesAsAxioms(var30, var1.concepts);
            }
        }

    }

    public void updateGroundInstances(Theory var1) {
        for(int var2 = 0; var2 < this.explainers.size(); ++var2) {
            Explainer var3 = (Explainer)this.explainers.elementAt(var2);
            if (this.use_ground_instances) {
                this.setGroundInstancesAsAxioms(var3, var1.concepts);
            }
        }

    }

    public void setGroundInstancesAsAxioms(Explainer var1, Vector var2) {
        if (this.use_ground_instances) {
            var1.use_ground_instances = true;
            var1.ground_instances_as_axioms = "";

            for(int var3 = 0; var3 < var2.size(); ++var3) {
                Concept var4 = (Concept)var2.elementAt(var3);
                if (var4.is_user_given) {
                    for(int var5 = 0; var5 < var4.datatable.size(); ++var5) {
                        Row var6 = (Row)var4.datatable.elementAt(var5);

                        for(int var7 = 0; var7 < var6.tuples.size(); ++var7) {
                            Vector var8 = (Vector)var6.tuples.elementAt(var7);
                            Vector var9 = (Vector)var8.clone();
                            var9.insertElementAt(var6.entity, 0);
                            String var10 = var4.writeDefinition("otter", var9);
                            if (!var10.trim().equals("")) {
                                var1.ground_instances_as_axioms = var1.ground_instances_as_axioms + var10.trim() + ".\n";
                            }
                        }

                        if (var4.arity == 1 && var6.tuples.size() == 0) {
                            Vector var11 = new Vector();
                            var11.addElement(var6.entity);
                            String var12 = var4.writeDefinition("otter", var11);
                            if (!var12.trim().equals("")) {
                                var1.ground_instances_as_axioms = var1.ground_instances_as_axioms + "-(" + var12.trim() + ").\n";
                            }
                        }
                    }
                }
            }

        }
    }

    public void addConjectureAsAxiom(Conjecture var1) {
        var1.proof_status = "axiom";

        for(int var2 = 0; var2 < this.explainers.size(); ++var2) {
            Explainer var3 = (Explainer)this.explainers.elementAt(var2);
            var3.axiom_strings.addElement(var1.writeConjecture("otter"));
        }

    }

    public boolean conjectureSeenAlready(Conjecture var1) {
        String var2 = var1.writeConjecture("otter");
        if (var1 instanceof Implication) {
            return this.previous_implications.containsKey(var2);
        } else if (var1 instanceof Equivalence) {
            return this.previous_equivalences.containsKey(var2);
        } else if (var1 instanceof NonExists) {
            return this.previous_non_exists.containsKey(var2);
        } else if (var1 instanceof Implicate) {
            return this.previous_implicates.containsKey(var2);
        } else {
            return false;
        }
    }

    public String previousResult(Conjecture var1) {
        String var2 = var1.writeConjecture("otter");
        Conjecture var3 = new Conjecture();
        if (var1 instanceof Implication) {
            var3 = (Conjecture)this.previous_implications.get(var2);
        }

        if (var1 instanceof Equivalence) {
            var3 = (Conjecture)this.previous_equivalences.get(var2);
        }

        if (var1 instanceof NonExists) {
            var3 = (Conjecture)this.previous_non_exists.get(var2);
        }

        if (var1 instanceof Implicate) {
            var3 = (Conjecture)this.previous_implicates.get(var2);
        }

        return var3 == null ? null : var3.proof_status;
    }

    public Implicate getSubsumingImplicate(Implicate var1, Theory var2, boolean var3) {
        for(int var4 = 0; var4 < var2.implicates.size(); ++var4) {
            Implicate var5 = (Implicate)var2.implicates.elementAt(var4);
            if ((var5.proof_status.equals("proved") || !var3) && var5.subsumes(var1, var2.specification_handler)) {
                return var5;
            }
        }

        return null;
    }

    public void addConjecture(Conjecture var1) {
        String var2 = var1.writeConjecture("otter");
        if (var1 instanceof Implicate) {
            this.previous_implicates.put(var2, var1);
        }

        if (var1 instanceof Implication) {
            this.previous_implications.put(var2, var1);
        }

        if (var1 instanceof Equivalence) {
            this.previous_equivalences.put(var2, var1);
        }

        if (var1 instanceof NonExists) {
            this.previous_non_exists.put(var2, var1);
        }

    }

    public void logThis(String var1, Theory var2) {
    }
}