package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class representing a vector of tuples from a row.
 * It is an extension of a vector, where each element of the vector is a tuple.
 * @author Simon Colton, started 11th December 1999
 * @version 1.0
 */

public class Tuples extends Vector implements Serializable
{
    /** Checks whether this vector subsumes (i.e., is a super-vector of)
     * the other vector.
     */

    public boolean subsumes(Tuples other_tuples)
    {
        if (size()<other_tuples.size())
            return false;
        String this_string = toString();
        for (int i=0; i<other_tuples.size(); i++)
        {
            Vector tuple = (Vector)other_tuples.elementAt(i);
            String tuple_string = tuple.toString();
            if (this_string.indexOf(tuple_string)<0)
                return false;
        }
        return true;
    }

    /** Removes any duplicate tuples.
     */

    public void removeDuplicates()
    {
        sort();
        int i=0;
        while (i<size()-1)
        {
            if (((Vector)elementAt(i)).toString().equals(((Vector)elementAt(i+1)).toString()))
                removeElementAt(i);
            else i++;
        }
    }

    /** Sorts the tuple. Note that it sorts integers correctly.
     */

    public void sort()
    {
        quickSort(0, size() - 1);
    }

    private void quickSort(int left, int right)
    {
        if(right > left)
        {
            Vector s1 = ((Vector)elementAt(right));
            int i = left - 1;
            int j = right;
            while(true)
            {
                while(tupleCompare(((Vector)elementAt(++i)),s1) < 0);
                while(j > 0)
                    if(tupleCompare(((Vector)elementAt(--j)),s1) <= 0) break;
                if(i >= j) break;
                swap(i, j);
            }
            swap(i , right);
            quickSort(left, i-1);
            quickSort(i+1, right);
        }
    }

    private int tupleCompare(Vector t1, Vector t2)
    {
        return ((t1.toString()).compareTo(t2.toString()));
    }

    private void swap(int loc1, int loc2) {
        Object tmp = elementAt(loc1);
        setElementAt(elementAt(loc2), loc1);
        setElementAt(tmp, loc2);
    }

    /** Reverses the vector
     */

    public void reverse()
    {
        Tuples new_output = new Tuples();
        for (int i=0; i<size(); i++)
            new_output.addElement(elementAt(size()-i-1));
        for (int i=0; i<size(); i++)
            setElementAt(new_output.elementAt(i),i);
    }
}
