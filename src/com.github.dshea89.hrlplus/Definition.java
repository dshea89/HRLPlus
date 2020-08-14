package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/**
 * A class representing a written definition for a relation in the theory. Each relation may have
 * more than one definition. For example, multiplication can be written as n=axb or as
 * a=n/b or as b=n/a. Each definition has a set of variables and a method by which they
 * can be written with those variables set to given letters.
 *
 * If the definition contains an equals sign, this additional information
 * can be recorded. Finally, the language of the definition (human or programming language)
 * can be specified.
 *
 * @author Simon Colton, started 11th December 1999.
 * @version 1.0
 */

public class Definition implements Serializable
{
    /**
     * The language of the definition. This is currently allowed to be either
     * "ascii", "java" "latex", "maple", "mathematica", "prolog", but not all of
     * these languages are supported yet. It defaults to "ascii".
     */

    public String language = "ascii";

    /**
     * The text of a definition is the ASCII string that will be output using the write command.
     * Any variable letters must be replaced by @k@ where k is the
     * position in the vector of letters that the letter appears.
     * <p>
     * For example, <pre>"@1@ is an element of @0@"</pre> is the text for the
     * definition of elements of groups. When given letters G and a, this will
     * be written as "a is an element of G".
     */

    public String text = "";

    /** The simple constructor
     */

    public Definition()
    {
    }

    /**
     * Constructs a Definition object with the specified text field. For example:
     * <pre> Definition definition = new Definition("@1@ is an element of @0@"). </pre>
     */

    public Definition(String input_text, String lang)
    {
        text = input_text;
        language = lang;
    }

    /**
     * Constructs a Definition object. The letters String should be a string containing
     * all the letters which appear in the input_text as variables, given in the correct
     * order. This is a simpler way of constructing a definition object. For example,
     * <pre> Definition definition = new Definition("Ga","@a@ is an element of @G@");</pre>
     * will do the necessary conversion to the internal format. ie. it is equivalent to:
     * <pre> Definition definition = new Definition("@1@ is an element of @0@"). </pre>
     */

    public Definition(String letters, String input_text, String lang)
    {
        for (int i=0;i<letters.length();i++)
        {
            String letter = letters.substring(i,i+1);
            int start_pos = input_text.indexOf("@"+letter+"@",0);
            while (start_pos > -1)
            {
                String number = Integer.toString(i);
                input_text = input_text.substring(0,start_pos+1) + number +
                        input_text.substring(start_pos+2,input_text.length());
                start_pos = input_text.indexOf("@"+letter+"@",start_pos+1);
            }
        }
        text = input_text;
        language = lang;
    }

    /**
     * Produces the string representation of this definition by setting the variables to
     * the given letters. For example
     * <pre>
     * Definition definition = new Definition("@1@ divides @0@");
     * Vector letters = new Vector(); letters.addElement("n"); letters.addElement("d");
     * definition.write(letters);
     * </pre>
     * produces this:
     * <pre>
     * "d divides n"
     * </pre>
     * Alternatively, any letters other than n and d could have been used.
     * Letters can actually be strings of any length.
     */

    public String write(Vector letters)
    {
        String output = text;
        int start_pos = output.indexOf("@",0);
        while (start_pos > -1)
        {
            int stop_pos = output.indexOf("@",start_pos + 1);
            int letter_number =
                    (new Integer(output.substring(start_pos+1,stop_pos))).intValue();
            String letter = (String)letters.elementAt(letter_number);
            output = output.substring(0,start_pos) + letter +
                    output.substring(stop_pos+1,output.length());
            start_pos = output.indexOf("@",start_pos + letter.length() + 1);
        }
        return output;
    }

    /**
     * Produces the string representation of this definition, but setting the variables
     * using the letters in the given string, rather than using a vector of letters.
     */

    public String write(String letters)
    {
        Vector letters_vector = new Vector();
        for (int i=0;i<letters.length();i++)
            letters_vector.addElement(letters.substring(i,i+1));
        return write(letters_vector);
    }
}
