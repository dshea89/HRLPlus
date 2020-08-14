package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Enumeration;
import java.lang.String;
import java.io.*;

/** A class for performing all the calculations associated with known (core) mathematical
 * concepts. For example, it can calculated the divisors of an integer and so on. Any concept
 * supplied here with java code will be fully functional in HR, and it will be possible to
 * fullly explore it. I hope users will want to add more and more functions to this package.
 * @author Simon Colton, started 11th December 1999.
 * @version 1.0
 */

public class Maths implements Serializable
{
    /** As nonIsomorphic below, but reading the algebras from the given file.
     */

    public Vector nonIsomorphicFromFile(String file_name)
    {
        Vector algebras = new Vector();
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(file_name));
            String s = in.readLine();
            while (!(s==null))
            {
                algebras.addElement(s);
                s = in.readLine();
            }
            in.close();
        }
        catch(Exception e)
        {
            System.out.println("Couldn't open file");
        }
        return nonIsomorphic(algebras);
    }

    /** This reduces a set of possibly isomorphic algebras to those which are not
     * isomorphic.
     */

    public Vector nonIsomorphic(Vector algebras)
    {
        Vector output = (Vector)algebras.clone();
        int i=0;
        String algebra1 = (String)output.elementAt(0);
        int size = (new Double(Math.sqrt(algebra1.length()))).intValue();
        String all_letters = "0123456789";
        if (algebra1.indexOf("a")>=0)
            all_letters = "abcdefghijklmnopqrst";
        Vector isomorphisms = getPossibleIsomorphicMaps(size, all_letters);
        while (i<output.size())
        {
            algebra1 = (String)output.elementAt(i);
            boolean is_isomorphic = false;
            int j=i+1;
            while (j<output.size())
            {
                String algebra2 = (String)output.elementAt(j);
                if (isIsomorphic(size, isomorphisms, algebra1, algebra2))
                    output.removeElementAt(j);
                else
                    j++;
            }
            i++;
        }
        return output;
    }

    /** Checks whether two flattened Cayley tables (e.g. abba, abcbcaabc)
     * are isomorphic. Must be the letters a,b,c, etc., not numbers,
     * and they must be of size 9 or less.
     */

    public boolean isIsomorphic(int size, Vector isomorphisms, String algebra1, String algebra2)
    {
        if (algebra1.equals(algebra2))
            return true;
        if (algebra1.length()!=algebra2.length())
            return false;

        int i=0;
        boolean output = false;
        while (!output && i<isomorphisms.size())
        {
            output = isIsomorphism((Vector)isomorphisms.elementAt(i),algebra1,algebra2,size);
            i++;
        }
        return output;
    }

    public boolean isIsomorphism(Vector phi_map, String algebra1, String algebra2, int size)
    {
        String map = "";
        for (int i=0; i<phi_map.size(); i++)
            map=map + (String)phi_map.elementAt(i);

        String all_letters = "0123456789";
        if (algebra1.indexOf("a")>=0)
            all_letters = "abcdefghijklmnopqrst";

        Vector letters = new Vector();
        for (int i=0; i<size; i++)
            letters.addElement(all_letters.substring(i,i+1));

        int pos=0;
        for (int i=0; i<letters.size(); i++)
        {
            for (int j=0; j<letters.size(); j++)
            {
                String a_times_b = algebra1.substring(pos,pos+1);
                int a_times_b_pos = letters.indexOf(a_times_b);
                String phi_a_times_b = map.substring(a_times_b_pos, a_times_b_pos+1);

                String phi_a = map.substring(i,i+1);
                String phi_b = map.substring(j,j+1);
                int phi_a_pos = letters.indexOf(phi_a);
                int phi_b_pos = letters.indexOf(phi_b);
                int phi_a_times_phi_b_pos = (phi_a_pos*size)+phi_b_pos;
                String phi_a_times_phi_b = algebra2.substring(phi_a_times_phi_b_pos, phi_a_times_phi_b_pos+1);

                if (!phi_a_times_phi_b.equals(phi_a_times_b))
                    return false;

                pos++;
            }
        }
        return true;
    }

    private Vector getPossibleIsomorphicMaps(int size, String all_letters)
    {
        Vector letters = new Vector();
        Vector output = new Vector();
        for (int i=0; i<size; i++)
        {
            letters.addElement(all_letters.substring(i,i+1));
            Vector perm = new Vector();
            perm.addElement(all_letters.substring(i,i+1));
            output.addElement(perm);
        }
        int present_size = 1;
        while (present_size < size)
        {
            int fixed_size = output.size();
            for (int i=0; i<fixed_size; i++)
            {
                Vector perm = (Vector)output.elementAt(0);
                for (int j=0; j<letters.size(); j++)
                {
                    String letter = (String)letters.elementAt(j);
                    if (!perm.contains(letter))
                    {
                        Vector new_perm = (Vector)perm.clone();
                        new_perm.addElement(letter);
                        output.addElement(new_perm);
                    }
                }
                output.removeElementAt(0);
            }
            present_size++;
        }
        return output;
    }

    public Tuples baseRepresentation(int base, String entity)
    {
        Tuples output = new Tuples();
        int number = (new Integer(entity)).intValue();
        int power_of_base = 1;
        int power_raised_to = 0;
        String add_string = "";
        while (power_of_base * base <= number)
        {
            power_raised_to++;
            power_of_base = power_of_base * base;
        }
        while (power_raised_to >= 0)
        {
            Vector tuple = new Vector();
            if (power_of_base <= number) add_string = "1";
            else add_string = "0";
            tuple.addElement(Integer.toString(power_raised_to));
            tuple.addElement(add_string);
            if (!add_string.equals("0")) number = number - power_of_base;
            power_of_base = power_of_base/base;
            power_raised_to--;
            output.insertElementAt(tuple,0);
        }
        return output;
    }

    /** This generates the set of Bell partitions of a set of integers. eg. given
     * input 3 it returns a vector of vectors: [[[1,2,3]],[[1,2],3],[[1,3],2],[[1],[2,3]],[[1],[2],[3]]]
     * The number of partitions is equals to the bell number.
     */

    public Vector bellPartitions(int num)
    {
        // Base case //

        Vector output = new Vector();
        Vector a = new Vector();
        a.addElement(Integer.toString(num));
        Vector b = new Vector();
        b.addElement(a);
        output.addElement(b);
        if (num==1)
            return output;
        Vector old_output = (Vector)bellPartitions(num - 1).clone();

        // Take each old categorisation and add on the new class to
        // each.

        output = new Vector();

        for (int i=0; i<old_output.size(); i++)
        {
            Vector new_cat2 = (Vector)((Vector)old_output.elementAt(i)).clone();
            new_cat2.addElement(a);
            output.addElement(new_cat2);
        }

        // For each old categorisation, go through each class in it and
        // add on the new number.

        for (int i=0; i<old_output.size(); i++)
        {
            Vector new_cat2 = (Vector)old_output.elementAt(i);
            for (int j=0; j<new_cat2.size(); j++)
            {
                Vector new_addition = new Vector();
                for (int k=0;k<new_cat2.size();k++)
                {
                    Vector add = (Vector)((Vector)new_cat2.elementAt(k)).clone();
                    if (k==j)
                        add.addElement(Integer.toString(num));
                    new_addition.addElement(add);
                }
                output.addElement(new_addition);
            }
        }
        return output;
    }

    public boolean isSquare(String int_string)
    {
        double d = (new Double(int_string)).doubleValue();
        if (Math.floor(Math.sqrt(d))==Math.sqrt(d))
            return true;
        return false;
    }

    public boolean isOdd(String int_string)
    {
        double d = (new Double(int_string)).doubleValue();
        if (Math.floor(d/2)==d/2)
            return false;
        return true;
    }

    public boolean isEven(String int_string)
    {
        double d = (new Double(int_string)).doubleValue();
        if (Math.floor(d/2)==d/2)
            return true;
        return false;
    }
}
