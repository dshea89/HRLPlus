package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class representing a integer sequence. It is basically a vector
 * of integer sequences which can also calculate some values for the
 * sequence.
 * @author Simon Colton, started 3rd January 2000
 * @version 1.0
 */

public class Sequence extends Vector implements Serializable
{
    /** If this is a cyclic sequence, then this is the length of the cycle.
     */

    public int cycle_length = 0;

    /** If this is a hill sequence, this is the offset of the top of the hill.
     */

    public int hill_offset = 0;

    /** If this is an arithmetic progression, then this is the common difference between terms.
     */

    public int common_difference = 0;

    /** If this is an geometric progression, then this is the common ratio between terms.
     */

    public double common_ratio = 0;

    /** The largest integer in this sequence.
     */

    public int top_integer = 0;

    /** The smallest integer in this sequence.
     */

    public int bottom_integer = 0;

    /** The name of the sequence (in conjunction with the Encyclopedia of Integer Sequences).
     */

    public String name = "";

    /** The definition of the sequence (in conjunction with the Encyclopedia of Integer Sequences).
     */

    public String definition = "";

    /** Constructs an empty sequence.
     */

    public Sequence()
    {
    }

    /** Constructs a sequence out of a string like this: "1,5,8,9"
     */

    public Sequence(String st)
    {
        try
        {
            int ind = st.indexOf(",");
            String comma_text = ",";
            if (ind < 0)
                comma_text = " ";
            String int_text = "";
            int pos = 0;
            while (pos < st.length())
            {
                String sub = st.substring(pos,pos+1);
                if (!sub.equals(comma_text))
                    int_text = int_text + sub;
                if (sub.equals(comma_text))
                {
                    addElement(int_text);
                    int the_int = (new Integer(int_text)).intValue();
                    if (the_int > top_integer)
                        top_integer = the_int;
                    int_text = "";
                }
                pos++;
            }
            if (!int_text.equals(""))
            {
                int the_int = (new Integer(int_text)).intValue();
                if (the_int > top_integer)
                    top_integer = the_int;
                addElement(int_text);
            }
            bottom_integer = (new Integer((String)elementAt(0))).intValue();
        }
        catch(Exception e)
        {
            System.out.println("problem with: "+st);
        }
    }

    public Vector asCategorisation(boolean start_at_one)
    {
        Vector output = new Vector();
        Vector positives = new Vector();
        Vector negatives = new Vector();
        int bot = bottom_integer;
        if (start_at_one) bot = 1;
        for (int i=bot; i<=top_integer; i++)
        {
            String int_text = Integer.toString(i);
            int index = indexOf(int_text);
            if (index > -1)
                positives.addElement(int_text);
            else
                negatives.addElement(int_text);
        }
        output.addElement(positives);
        int first_pos = (new Integer((String)positives.elementAt(0))).intValue();
        if (!negatives.isEmpty())
        {
            int first_neg = (new Integer((String)negatives.elementAt(0))).intValue();
            if (first_pos < first_neg)
                output.addElement(negatives);
            else
                output.insertElementAt(negatives,0);
        }
        return output;
    }

    /** Returns a concept in number theory representing this sequence. This returns a number type concept.
     */

    public Concept asNumberTypeConcept()
    {
        Concept output = new Concept();
        for (int i=bottom_integer; i<=top_integer; i++)
        {
            String int_text = Integer.toString(i);
            Row row = new Row();
            row.entity = int_text;
            int index = indexOf(int_text);
            if (index > -1)
                row.tuples.addElement(new Vector());
            output.datatable.addElement(row);
            output.types.addElement("integer");
            output.arity = 1;
        }
        return output;
    }

    /** Reverses the sequence.
     */

    public Sequence reversed()
    {
        Sequence output = new Sequence();
        for (int i=size()-1; i>=0; i--)
        {
            String n = (String)elementAt(i);
            int nint = (new Integer(n)).intValue();
            if (nint > output.top_integer)
                output.top_integer = nint;
            output.addElement(n);
        }
        return output;
    }


    /** Returns the sequence as a string.
     */

    public String asString(boolean with_commas)
    {
        String output = "";
        String seperator = "";
        if (with_commas) seperator = ",";
        else seperator = " ";
        for (int i=0;i<size();i++)
        {
            output=output+(String)elementAt(i);
            if (i<size()-1)
                output = output + seperator;
        }
        return output;
    }

    /** The range of the sequence is the difference between its smallest
     * and largest entries.
     */

    public int range()
    {
        Vector copy = (Vector)this.clone();
        int output = 0;
        while (copy.size()>0)
        {
            String entry = (String)copy.elementAt(0);
            copy.removeElementAt(0);
            if(!copy.contains(entry)) output++;
        }
        return output;
    }

    /** This checks whether the given sequence is subsequence of this sequence.
     */

    public boolean isSubsequenceOf(Sequence s)
    {
        for (int i=0;i<size();i++)
        {
            if (!s.contains((String)elementAt(i))) return false;
        }
        return true;
    }

    /** The term overlap of two sequences is the number of distinct
     * terms which are found in both sequences.
     */

    public int termOverlap(Sequence s)
    {
        int output = 0;
        Vector temp_vector = new Vector();
        for (int i=0;i<size();i++)
        {
            String element = (String)elementAt(i);
            if(s.contains(element) && !temp_vector.contains(element))
            {
                output++;
                temp_vector.addElement(element);
            }
        }
        return output;
    }

    /** This checks whether the sequence is increasing or not.
     */

    public boolean isIncreasing()
    {
        if (size()<2) return false;
        int top = new Integer((String)elementAt(0)).intValue();
        for (int i=1;i<size();i++)
        {
            int tryer = (new Integer((String)elementAt(i))).intValue();
            if (tryer <= top) return false;
            if (tryer > top) top = tryer;
        }
        return true;
    }

    /** This checks whether the sequence is decreasing or not.
     */

    public boolean isDecreasing()
    {
        if (size()<2) return false;
        int top = new Integer((String)elementAt(0)).intValue();
        for (int i=1;i<size();i++)
        {
            int tryer = (new Integer((String)elementAt(i))).intValue();
            if (tryer >= top) return false;
            if (tryer < top) top = tryer;
        }
        return true;
    }

    /** This checks whether the sequence is non-decreasing.
     */

    public boolean isNonDecreasing()
    {
        int top = new Integer((String)elementAt(0)).intValue();
        for (int i=1;i<size();i++)
        {
            int tryer = (new Integer((String)elementAt(i))).intValue();
            if (tryer < top) return false;
            if (tryer > top) top = tryer;
        }
        return true;
    }

    /** This checks whether the sequence is a golomb ruler (ie. all the
     * differences between entries are different.
     */

    public boolean isGolomb()
    {
        Vector differences = new Vector();
        for (int i=0;i<size()-1;i++)
        {
            int first = (new Integer((String)elementAt(i))).intValue();
            for (int j=i+1;j<size();j++)
            {
                int second = (new Integer((String)elementAt(j))).intValue();
                if (differences.contains(Integer.toString(second - first))) return false;
                else differences.addElement(Integer.toString(second - first));
            }
        }
        return true;
    }

    /** If the sequence is an arithmetic progression, this returns true and sets the value
     * common_difference correctly.
     */

    public boolean isAP()
    {
        if (size()<2)
            return false;
        int a = (new Integer((String)elementAt(0))).intValue();
        int b = (new Integer((String)elementAt(1))).intValue();
        int difference = b-a;
        int i = 1;
        boolean is_ap = true;
        while (is_ap && i<size()-1)
        {
            a = (new Integer((String)elementAt(i))).intValue();
            b = (new Integer((String)elementAt(i+1))).intValue();
            if (b-a!=difference)
                is_ap = false;
            i++;
        }
        if (is_ap)
            common_difference = difference;
        return is_ap;
    }

    /** If the sequence is an arithmetic progression, this returns true and sets the value of
     * common_ratio correctly.
     */

    public boolean isGP()
    {
        if (size()<2)
            return false;
        double a = (new Double((String)elementAt(0))).doubleValue();
        double b = (new Double((String)elementAt(1))).doubleValue();
        double ratio = b/a;
        int i = 1;
        boolean is_gp = true;
        while (is_gp && i<size()-1)
        {
            a = (new Double((String)elementAt(i))).doubleValue();
            b = (new Double((String)elementAt(i+1))).doubleValue();
            if (b/a!=ratio)
                is_gp = false;
            i++;
        }
        if (is_gp)
            common_ratio = ratio;
        return is_gp;
    }

    /** Checks to see if the sequence follows a cycle, eg. 1,4,6,1,4,6.
     * It can also check to see whether the cycle goes up and then down,
     * eg. 1,4,6,4,1,4,6
     */

    public boolean isCyclic()
    {
        int clength = 0;
        boolean cycle_holds = false;
        while (clength<size() && !cycle_holds)
        {
            clength++;
            cycle_holds = true;
            int pos = 0;
            while (cycle_holds && pos < size() - clength)
            {
                String first_element = (String)elementAt(pos);
                String match_element = (String)elementAt(pos+clength);
                if (!first_element.equals(match_element))
                    cycle_holds = false;
                pos++;
            }
        }
        if (cycle_holds && clength < size())
        {
            cycle_length = clength;
            return true;
        }
        else
            cycle_holds = false;
        double mid = size()/2;
        int cpos = (new Double(mid)).intValue() - 1;
        while (cpos < size() && !cycle_holds)
        {
            cpos++;
            int diff = 1;
            cycle_holds = true;
            while (cycle_holds && cpos - diff >=0 && cpos + diff < size())
            {
                String left = (String)elementAt(cpos-diff);
                String right = (String)elementAt(cpos+diff);
                if (!left.equals(right))
                    cycle_holds = false;
                diff++;
            }
        }
        if (cycle_holds && cpos < size() - 1)
        {
            hill_offset = cpos;
            cycle_length = (cpos+1) * 2;
            return true;
        }
        return false;
    }

    /** Construct a new sequence by taking successive differences between terms.
     */

    public Sequence differenceSequence()
    {
        Sequence output = new Sequence();
        for (int i=1;i<size();i++)
        {
            int term = (new Integer((String)elementAt(i))).intValue();
            int previous_term = (new Integer((String)elementAt(i-1))).intValue();
            int new_term = term-previous_term;
            if (new_term > output.top_integer)
                output.top_integer = new_term;
            if (new_term<0)
                new_term = 0-new_term;
            output.addElement(Integer.toString(new_term));
        }
        return output;
    }

    /** Construct a new sequence by taking those integers set the
     * record for a function concept.
     */

    public Sequence recordSequence(Concept c)
    {
        Sequence output = new Sequence();
        int top_number = -1000000;
        for (int i=0; i<c.datatable.size(); i++)
        {
            Row row = (Row)c.datatable.elementAt(i);
            if (!row.tuples.isEmpty())
            {
                Vector tuple = (Vector)row.tuples.elementAt(0);
                int try_number = (new Integer((String)tuple.elementAt(0))).intValue();
                if (try_number > top_number)
                {
                    top_number = try_number;
                    output.addElement(row.entity);
                    if (output.size()==1)
                        output.bottom_integer = (new Integer(row.entity)).intValue();
                    output.top_integer = (new Integer(row.entity)).intValue();
                }
            }
        }
        return output;
    }
}
