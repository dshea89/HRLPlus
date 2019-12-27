package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

/**
 * A class representing a written definition for a relation in the theory. Each relation may have more than one definition.
 * For example, multiplication can be written as n=axb or as a=n/b or as b=n/a. Each definition has a set of variables and
 * a method by which they can be written with those variables set to given letters. If the definition contains an equals sign,
 * this additional information can be recorded. Finally, the language of the definition (human or programming language) can
 * be specified.
 */
public class Definition implements Serializable {
    /**
     * The language of the definition. This is currently allowed to be either "ascii", "java" "latex", "maple", "mathematica",
     * "prolog", but not all of these languages are supported yet. It defaults to "ascii".
     */
    public String language = "ascii";

    /**
     * The text of a definition is the ASCII string that will be output using the write command. Any variable letters must be
     * replaced by @k@ where k is the position in the vector of letters that the letter appears.
     * For example,
     *
     * "@1@ is an element of @0@"
     *
     * is the text for the definition of elements of groups. When given letters G and a, this will be written as "a is an element of G".
     */
    public String text = "";

    /**
     * The simple constructor
     */
    public Definition() {
    }

    /**
     * Constructs a Definition object with the specified text field. For example:
     *  Definition definition = new Definition("@1@ is an element of @0@").
     */
    public Definition(String input_text, String lang) {
        this.text = input_text;
        this.language = lang;
    }

    /**
     * Constructs a Definition object. The letters String should be a string containing all the letters which appear in the
     * input_text as variables, given in the correct order. This is a simpler way of constructing a definition object. For example,
     *  Definition definition = new Definition("Ga","@a@ is an element of @G@");
     * will do the necessary conversion to the internal format. ie. it is equivalent to:
     *  Definition definition = new Definition("@1@ is an element of @0@").
     */
    public Definition(String letters, String input_text, String lang) {
        for(int var4 = 0; var4 < letters.length(); ++var4) {
            String var5 = letters.substring(var4, var4 + 1);

            for(int var6 = input_text.indexOf("@" + var5 + "@", 0); var6 > -1; var6 = input_text.indexOf("@" + var5 + "@", var6 + 1)) {
                String var7 = Integer.toString(var4);
                input_text = input_text.substring(0, var6 + 1) + var7 + input_text.substring(var6 + 2, input_text.length());
            }
        }

        this.text = input_text;
        this.language = lang;
    }

    /**
     * Produces the string representation of this definition by setting the variables to the given letters. For example
     *
     *  Definition definition = new Definition("@1@ divides @0@");
     *  Vector letters = new Vector(); letters.addElement("n"); letters.addElement("d");
     *  definition.write(letters);
     *
     * produces this:
     *  "d divides n"
     *
     * Alternatively, any letters other than n and d could have been used. Letters can actually be strings of any length.
     */
    public String write(Vector letters) {
        String var2 = this.text;

        String var6;
        for(int var3 = var2.indexOf("@", 0); var3 > -1; var3 = var2.indexOf("@", var3 + var6.length() + 1)) {
            int var4 = var2.indexOf("@", var3 + 1);
            int var5 = new Integer(var2.substring(var3 + 1, var4));
            var6 = (String)letters.elementAt(var5);
            var2 = var2.substring(0, var3) + var6 + var2.substring(var4 + 1, var2.length());
        }

        return var2;
    }

    /**
     * Produces the string representation of this definition, but setting the variables using the letters in the given string,
     * rather than using a vector of letters.
     */
    public String write(String letters) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < letters.length(); ++var3) {
            var2.addElement(letters.substring(var3, var3 + 1));
        }

        return this.write(var2);
    }
}
