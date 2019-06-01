package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class ReactionHandler implements Serializable {
    public Hashtable reaction_vector_hashtable = new Hashtable();

    public ReactionHandler() {
    }

    public void reactTo(Object var1, String var2) {
        Vector var3 = (Vector)this.reaction_vector_hashtable.get(var2);
        if (var3 != null) {
            for(int var4 = 0; var4 < var3.size(); ++var4) {
                Reaction var5 = (Reaction)var3.elementAt(var4);
                System.out.println("reaction is " + var5);
                var5.react(var2, var1);
                System.out.println("here - 1");
            }
        }

    }

    public void addReaction(String var1, String var2, Theory var3) {
        System.out.println("in addReaction");
        System.out.println("pseudo_code is " + var2);
        Hashtable var4 = new Hashtable();
        var4.put("theory", var3);
        Reaction var5 = new Reaction(var1, var2, var4);
        var5.pseudo_code_interpreter.output_text_box = var3.front_end.pseudo_code_output_text;
        var5.pseudo_code_interpreter.input_text_box = var3.front_end.pseudo_code_input_text;
        var5.pseudo_code_interpreter.input_reporting_style = "print";
        String var6 = "all_purpose";
        int var7 = var2.indexOf("setSituation(");
        System.out.println("ind is " + var7);
        if (var7 > 0) {
            var6 = var2.substring(var7 + 14, var2.indexOf(")", var7 + 13) - 1);
            var5.setSituation(var6);
        }

        Vector var8 = (Vector)this.reaction_vector_hashtable.get(var6);
        System.out.println("in addReaction - reactions is " + var8);
        if (var8 == null) {
            var8 = new Vector();
        }

        var8.addElement(var5);
        this.reaction_vector_hashtable.put(var6, var8);
        System.out.println("reaction_vector_hashtable is " + this.reaction_vector_hashtable.toString());
        System.out.println("reactions is now " + var8);
    }

    public void removeReaction(String var1) {
        Enumeration var2 = this.reaction_vector_hashtable.elements();

        while(true) {
            while(var2.hasMoreElements()) {
                Vector var3 = (Vector)var2.nextElement();

                for(int var4 = 0; var4 < var3.size(); ++var4) {
                    Reaction var5 = (Reaction)var3.elementAt(var4);
                    if (var5.id.equals(var1)) {
                        var3.removeElementAt(var4);
                        break;
                    }
                }
            }

            return;
        }
    }

    public void testForConjecture(TheoryConstituent var1) {
        System.out.println("-------- hurray!!!!!!");
    }
}
