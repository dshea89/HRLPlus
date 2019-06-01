package com.github.dshea89.hrlplus;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class StorageHandler implements Serializable {
    public Vector object_streams = new Vector();
    public PseudoCodeInterpreter pseudo_code_interpreter = new PseudoCodeInterpreter();
    public Guide guider = new Guide();
    public String storage_directory = "";
    public Reflection reflect = new Reflection();

    public StorageHandler() {
    }

    public void storeObject(Object var1, String var2) {
        try {
            ObjectOutputStream var3 = new ObjectOutputStream(new FileOutputStream(this.storage_directory + var2));
            var3.writeObject(var1);
        } catch (Exception var4) {
            ;
        }

    }

    public Object retrieveObject(String var1) {
        try {
            ObjectInputStream var2 = new ObjectInputStream(new FileInputStream(this.storage_directory + var1));
            Object var3 = var2.readObject();
            return var3;
        } catch (Exception var4) {
            return null;
        }
    }

    public void retrieveObject(String var1, Vector var2) {
        var2.addElement(this.retrieveObject(var1));
    }

    public void retrieveObject(String var1, Vector var2, List var3) {
        Object var4 = this.retrieveObject(var1);
        var2.addElement(var4);
        if (var4 instanceof TheoryConstituent) {
            var3.addItem(((TheoryConstituent)var4).id);
        }

    }

    public void buildConstructionFromFile(String var1, Theory var2) {
        Vector var3 = new Vector();

        try {
            BufferedReader var4 = new BufferedReader(new FileReader(this.storage_directory + var1));
            String var5 = var4.readLine();
            var3.addElement(var5);

            while(var5 != null) {
                var5 = var4.readLine();
                if (var5 != null) {
                    var3.addElement(var5);
                }
            }

            var4.close();
        } catch (Exception var6) {
            System.out.println("problem with file: " + this.storage_directory + var1);
        }

        this.pseudo_code_interpreter.local_alias_hashtable = new Hashtable();
        this.pseudo_code_interpreter.local_alias_hashtable.put("theory", var2);
        this.pseudo_code_interpreter.local_alias_hashtable.put("this", var2.guider);
        this.pseudo_code_interpreter.input_text_box = var2.front_end.pseudo_code_input_text;
        this.pseudo_code_interpreter.output_text_box = var2.front_end.pseudo_code_output_text;
        this.pseudo_code_interpreter.runPseudoCode(var3);
    }

    public void writeConstructionToFile(String var1, Object var2) {
        if (var2 instanceof Concept) {
            this.writeConstructionToFile(var1, (Concept)var2);
        }

        if (var2 instanceof Equivalence) {
            this.writeConstructionToFile(var1, (Equivalence)var2);
        }

        if (var2 instanceof NearEquivalence) {
            this.writeConstructionToFile(var1, (NearEquivalence)var2);
        }

    }

    public void writeStringToFile(String var1, Object var2) {
        this.writeStringToFile(var1, var2, false);
    }

    public void writeStringToFile(String var1, Object var2, boolean var3) {
        try {
            PrintWriter var4 = new PrintWriter(new BufferedWriter(new FileWriter(this.storage_directory + var1, var3)));
            if (var2 instanceof Vector) {
                Vector var5 = (Vector)var2;

                for(int var6 = 0; var6 < var5.size(); ++var6) {
                    var4.write(var5.elementAt(var6).toString() + "\n");
                }
            } else {
                var4.write(var2.toString());
            }

            var4.close();
        } catch (Exception var7) {
            ;
        }

    }

    public void writeConstructionToFile(String var1, Concept var2) {
        this.writeConstructionToFile(var1, var2, false);
    }

    public void writeConstructionToFile(String var1, Concept var2, boolean var3) {
        try {
            PrintWriter var4 = new PrintWriter(new BufferedWriter(new FileWriter(this.storage_directory + var1, var3)));

            for(int var5 = 0; var5 < var2.ancestors.size(); ++var5) {
                Concept var6 = (Concept)var2.ancestors.elementAt(var5);
                if (!var6.is_user_given) {
                    String var7 = this.getConstructionString(var6);
                    var4.write("forceStep(\"" + var7 + "\", theory);\n");
                }
            }

            var4.write("forceStep(\"" + this.getConstructionString(var2) + "\", theory);\n");
            var4.close();
        } catch (Exception var8) {
            System.out.println(var8);
        }

    }

    public String getConstructionString(Concept var1) {
        String var2 = var1.id;
        if (!var1.is_user_given) {
            var2 = var2 + "*";
        }

        var2 = var2 + " = ";

        for(int var3 = 0; var3 < var1.parents.size(); ++var3) {
            Concept var4 = (Concept)var1.parents.elementAt(var3);
            String var5 = var4.id;
            if (!var4.is_user_given) {
                var5 = var5 + "*";
            }

            var2 = var2 + var5 + " ";
        }

        var2 = var2 + var1.construction.productionRule().getName() + " ";
        var2 = var2 + this.removeSpaces(var1.construction.parameters().toString());
        return var2;
    }

    public void writeConstructionToFile(String var1, Equivalence var2) {
        this.writeConstructionToFile(var1, var2.lh_concept, false);
        this.writeConstructionToFile(var1, var2.rh_concept, true);
        String var3 = var2.lh_concept.id;
        if (!var2.lh_concept.is_user_given) {
            var3 = var3 + "*";
        }

        String var4 = var2.rh_concept.id;
        if (!var2.rh_concept.is_user_given) {
            var4 = var4 + "*";
        }

        this.writeCommandToFile(var1, "action(construct_equivalence," + var3 + "," + var4 + "," + var2.id + "*)", true);
    }

    public void writeConstructionToFile(String var1, NearEquivalence var2) {
        this.writeConstructionToFile(var1, var2.lh_concept, false);
        this.writeConstructionToFile(var1, var2.rh_concept, true);
        String var3 = var2.lh_concept.id;
        if (!var2.lh_concept.is_user_given) {
            var3 = var3 + "*";
        }

        String var4 = var2.rh_concept.id;
        if (!var2.rh_concept.is_user_given) {
            var4 = var4 + "*";
        }

        this.writeCommandToFile(var1, "action(construct_near_equivalence," + var3 + "," + var4 + "," + var2.id + "*)", true);
    }

    public void writeCommandToFile(String var1, String var2, boolean var3) {
        try {
            PrintWriter var4 = new PrintWriter(new BufferedWriter(new FileWriter(this.storage_directory + var1, var3)));
            var4.write(var2 + "\n");
            var4.close();
        } catch (Exception var5) {
            ;
        }

    }

    private String removeSpaces(String var1) {
        String var2 = "";

        for(int var3 = 0; var3 < var1.length(); ++var3) {
            String var4 = var1.substring(var3, var3 + 1);
            if (!var4.equals(" ")) {
                var2 = var2 + var4;
            }
        }

        return var2;
    }

    public Object slimRepresentation(Object var1) {
        if (var1 instanceof Implicate) {
            Implicate var2 = (Implicate)var1;
            Concept var3 = new Concept();
            var3.specifications = var2.premise_concept.specifications;
            var3.types = var2.premise_concept.types;
            Implicate var4 = new Implicate();
            var4.premise_concept = var3;
            var4.goal = var2.goal;
            var4.proof_status = var2.proof_status;
            var4.counterexamples = var2.counterexamples;
            var4.proof = var2.proof;
            return var4;
        } else {
            return var1;
        }
    }

    public Object expandedObject(Object var1) {
        return var1;
    }

    public Vector retrieveAllSlim(String var1) {
        Vector var2 = new Vector();

        try {
            ObjectInputStream var3 = new ObjectInputStream(new FileInputStream(this.storage_directory + var1));
            boolean var4 = false;

            while(!var4) {
                try {
                    Object var5 = var3.readObject();
                    var2.addElement(this.expandedObject(var5));
                } catch (Exception var6) {
                    var4 = true;
                    var3.close();
                }
            }

            return var2;
        } catch (Exception var7) {
            System.out.println(var7);
            return null;
        }
    }

    public Object retrieveSlim(String var1) {
        Object var2 = this.retrieveObject(var1);
        return this.expandedObject(var2);
    }

    public boolean openStorageStream(String var1, String var2) {
        if (var2.equals("")) {
            return true;
        } else {
            Vector var4;
            for(int var3 = 0; var3 < this.object_streams.size(); ++var3) {
                var4 = (Vector)this.object_streams.elementAt(var3);
                String var5 = (String)var4.elementAt(2);
                if (var5.equals(var2)) {
                    ObjectOutputStream var6 = (ObjectOutputStream)var4.elementAt(1);
                    Vector var7 = new Vector();
                    var7.addElement(var1);
                    var7.addElement(var6);
                    var7.addElement(var2);
                    this.object_streams.addElement(var7);
                    return true;
                }
            }

            try {
                ObjectOutputStream var9 = new ObjectOutputStream(new FileOutputStream(this.storage_directory + var2));
                var4 = new Vector();
                var4.addElement(var1);
                var4.addElement(var9);
                var4.addElement(var2);
                this.object_streams.addElement(var4);
                return true;
            } catch (Exception var8) {
                return false;
            }
        }
    }

    public void closeObjectStreams() {
        for(int var1 = 0; var1 < this.object_streams.size(); ++var1) {
            Vector var2 = (Vector)this.object_streams.elementAt(var1);
            ObjectOutputStream var3 = (ObjectOutputStream)var2.elementAt(1);

            try {
                var3.close();
            } catch (Exception var6) {
                ;
            }
        }

        Enumeration var7 = this.object_streams.elements();

        while(var7.hasMoreElements()) {
            ObjectOutputStream var8 = (ObjectOutputStream)var7.nextElement();

            try {
                var8.close();
            } catch (Exception var5) {
                ;
            }
        }

    }

    public void handleStorageOf(Object var1) {
        for(int var2 = 0; var2 < this.object_streams.size(); ++var2) {
            Vector var3 = (Vector)this.object_streams.elementAt(var2);
            String var4 = (String)var3.elementAt(0);
            if (this.reflect.checkCondition(var1, var4)) {
                try {
                    ObjectOutputStream var5 = (ObjectOutputStream)var3.elementAt(1);
                    Object var6 = this.slimRepresentation(var1);
                    var5.writeObject(var6);
                } catch (Exception var7) {
                    ;
                }
            }
        }

    }
}
