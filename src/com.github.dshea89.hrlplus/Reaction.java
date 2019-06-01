package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Hashtable;

public class Reaction extends PseudoCodeUser implements Serializable {
    String situation = "all_purpose";

    public Reaction(String var1, String var2, Hashtable var3) {
        this.getCodeLines(var2 + "\n");
        this.original_alias_hashtable = var3;
        this.original_alias_hashtable.put("this", this);
        this.id = var1;
    }

    public void react(String var1, Object var2) {
        System.out.println("situation is " + var1);
        System.out.println("object_to_react_to is " + var2);
        this.original_alias_hashtable.put("object_to_react_to", var2);
        this.original_alias_hashtable.put("situation", var1);
        this.pseudo_code_interpreter.local_alias_hashtable = (Hashtable)this.original_alias_hashtable.clone();
        System.out.println("pseudo_code_lines are " + this.pseudo_code_lines);
        this.pseudo_code_interpreter.runPseudoCode(this.pseudo_code_lines);
    }

    public void setSituation(String var1) {
        this.situation = var1;
    }
}
