package com.github.dshea89.hrlplus;

import java.lang.String;
import java.io.Serializable;

/** A class representing something in the theory - this could be a
 * concept, conjecture, entity or proof.
 *
 * @author Simon Colton, started 18th November 2001
 * @version 1.0 */

public class TheoryConstituent implements Serializable
{
    /** The time which had elapsed in the session before this conjecture was made.
     */

    public long when_constructed = 0;

    /** The id of this theory constituent.
     */

    public String id = "";

    public String decimalPlaces(int d, int dp)
    {
        return decimalPlaces((new Double(d)).doubleValue(), dp);
    }

    public String decimalPlaces(double d, int dp)
    {
        String d_string = Double.toString(d);
        if (d_string.equals("Inf") || d_string.equals("Infinity"))
            return "Infinity";
        try
        {
            int places_already = d_string.length() - d_string.indexOf(".") - 1;
            if (places_already==dp) return d_string;
            if (places_already<dp)
            {
                for (int i=0; i<dp-places_already; i++)
                    d_string = d_string + "0";
            }
            if (places_already>dp)
            {
                d_string = d_string.substring(0,d_string.indexOf(".")+dp+2);
                String last_digit = d_string.substring(d_string.length()-2, d_string.length()-1);
                int last_digit_int = (new Integer(last_digit)).intValue();
                if (last_digit_int < 5)
                    d_string = d_string.substring(0,d_string.length()-1);
                else
                {
                    String dint = d_string.substring(d_string.indexOf(".")+1,d_string.length());
                    String add_on = Integer.toString(10-last_digit_int);
                    String new_dint = APlusB(dint, add_on);
                    if (new_dint.length()==dint.length())
                    {
                        d_string = d_string.substring(0,d_string.indexOf(".")+1)+
                                new_dint.substring(0,new_dint.length()-1);
                    }
                    else
                    {
                        String int_part = d_string.substring(0,d_string.indexOf("."));
                        int new_int_part_int = (new Integer(int_part)).intValue()+1;
                        d_string = Integer.toString(new_int_part_int)+".";
                        for (int i=0; i<dp; i++)
                            d_string = d_string + "0";
                    }
                }
            }
        }
        catch(Exception e){}
        return d_string;
    }

    public String APlusB(String biggest, String smallest)
    {
        if (compareIntegers(biggest,smallest)<0)
            return APlusB(smallest, biggest);

        String output = "";
        int carry=0;
        for (int i=biggest.length()-smallest.length()-1; i>=0; i--)
            smallest = "0" + smallest;

        for (int i=smallest.length(); i>0; i--)
        {
            String digit1 = smallest.substring(i-1,i);
            String digit2 = biggest.substring(i-1,i);
            int dig1 = (new Integer(digit1)).intValue();
            int dig2 = (new Integer(digit2)).intValue();
            String added_digit = Integer.toString((new Integer(dig1+dig2+carry)).intValue());
            carry=0;
            if (added_digit.length()==2)
                carry=1;
            added_digit = added_digit.substring(added_digit.length()-1,added_digit.length());
            output=added_digit+output;
        }
        if (carry==1)
            output = "1"+output;

        return output;
    }


    /** Returns 0 if the strings are the same, a number greater than 1 if the
     * first is bigger than the second, a number greater than 0 otherwise.
     */

    public int compareIntegers(String int_1, String int_2)
    {
        if (int_1.equals(int_2))
            return 0;
        boolean first_negative = false;
        boolean second_negative = false;
        if (int_1.substring(0,1).equals("-"))
            first_negative = true;
        if (int_2.substring(0,1).equals("-"))
            second_negative = true;
        if (first_negative && !second_negative)
            return -1;
        if (!first_negative && second_negative)
            return 1;
        if (int_1.length() < int_2.length() && !first_negative)
            return -1;
        if (int_1.length() < int_2.length() && first_negative)
            return 1;
        if (int_1.length() > int_2.length() && !first_negative)
            return 1;
        if (int_1.length() > int_2.length() && first_negative)
            return -1;
        if (first_negative)
            int_1 = int_1.substring(1,int_1.length());
        if (second_negative)
            int_2 = int_2.substring(1,int_2.length());
        int i=0;
        int output = 0;
        while (output==0 && i<int_1.length())
        {
            output = int_1.substring(i,i+1).compareTo(int_2.substring(i,i+1));
            i++;
        }
        if (first_negative && second_negative)
        {
            if (output > 1)
                output = -1;
            else
                output = 1;
        }
        return output;
    }

    public String replaceLTForHTML(String s)
    {
        String output = "";
        for (int i=0; i<s.length(); i++)
        {
            if (s.substring(i,i+1).equals("<"))
                output = output + "&lt;";
            else
                output = output + s.substring(i,i+1);
        }
        return output;
    }


    /** writes the theory constituent - alisonp*/
    public String writeTheoryConstituent()
    {
        if (this instanceof Conjecture)
            return ((Conjecture)this).writeConjecture();

        if (this instanceof Concept)
            return ((Concept)this).writeDefinition();

        if(this instanceof Entity)
            return ((Entity)this).toString();

        if(this instanceof ProofScheme)
            return ((ProofScheme)this).writeProofScheme();

        return "";
    }

    /** return true if the string expression of the theory constituent
     is blank, false otherwise dec 2006 -- needs testing*/

    public boolean equals(String string)
    {
        if((this.writeTheoryConstituent()).equals(string))
            return true;
        else
            return false;
    }

    public boolean equals(Object object)
    {
        if(object instanceof String)
            return equals((String)object);
        else
        {
            System.out.println("Warning: TheoryConstituent equals method - not a String");
            return false;
        }
    }
}
