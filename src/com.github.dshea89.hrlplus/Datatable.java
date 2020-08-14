package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class representing the data for a given concept.
 * It is a vector of rows.
 * @see Row
 * @author Simon Colton, started 11th December 1999
 * @version 1.0
 */

public class Datatable extends Vector implements Serializable
{

    /** The number of tuples in the datatable.
     */

    public int number_of_tuples = 0;

    /** Calculates the number of tuples
     */

    public void setNumberOfTuples()
    {
        for (int i=0; i<size(); i++)
        {
            Row row = (Row)elementAt(i);
            number_of_tuples = number_of_tuples + row.tuples.size();
        }
    }

    /** Writes the table neatly.
     */
    public String toTable()
    {
        String output = new String();
        for (int i=0;i<size();i++)
        {
            output=output+(((Row)elementAt(i)).entity);
            output=output+"=";
            output=output+(((Row)elementAt(i)).tuples.toString());
            output=output+"\n";
        }
        return output;
    }

    /** Sorts the datatable using a lexicographic ordering of the entities which each row
     * represents.
     */

    public void sort()
    {
        quickSort(0, size() - 1);
    }

    private void quickSort(int left, int right)
    {
        if(right > left) {
            String s1 = ((Row)elementAt(right)).entity;
            int i = left - 1;
            int j = right;
            while(true)
            {
                while(entityCompare(((Row)elementAt(++i)).entity,s1) < 0);
                while(j > 0)
                    if(entityCompare(((Row)elementAt(--j)).entity,s1) <= 0) break;
                if(i >= j) break;
                swap(i, j);
            }
            swap(i , right);
            quickSort(left, i-1);
            quickSort(i+1, right);
        }
    }

    private int entityCompare(String e1, String e2)
    {
        int output = 0;
        try
        {
            int n1 = new Integer(e1).intValue();
            int n2 = new Integer(e2).intValue();
            if (n1 < n2) output = -1;
            if (n1 == n2) output = 0;
            if (n1 > n2) output = 1;
        }
        catch(Exception e)
        {
            output = e1.compareTo(e2);
        }
        return output;
    }

    private void swap(int loc1, int loc2) {
        Object tmp = elementAt(loc1);
        setElementAt(elementAt(loc2), loc1);
        setElementAt(tmp, loc2);
    }

    /** Returns a clone of the datatable.
     */

    public Datatable copy()
    {
        Datatable output = new Datatable();
        for (int i=0;i<size();i++)
        {
            Row row = (Row)elementAt(i);
            Tuples new_tuples = new Tuples();
            for (int j=0;j<row.tuples.size();j++)
            {
                Vector new_tuple = (Vector)((Vector)row.tuples.elementAt(j)).clone();
                new_tuples.addElement(new_tuple);
            }
            Row new_row = new Row(row.entity,new_tuples);
            output.addElement(new_row);
        }
        return(output);
    }

    /** Finds the row which corresponds to the given entity.
     * @see Row
     */

    public Row rowWithEntity(String entity)
    {
        int i=0;
        while(i<size() && !((Row)elementAt(i)).entity.equals(entity)) i++;
        if (i<size()) return((Row)elementAt(i));
        else return (new Row(entity,new Tuples()));
    }

    private Row rowWithEntityNoTuples(String entity)
    {
        int i=0;
        while(i<size() && !((Row)elementAt(i)).entity.equals(entity)) i++;
        if (i<size()) return((Row)elementAt(i));
        else return (new Row("",new Tuples()));
    }

    /** Checks to see if the given entity is in the datatable.
     */

    public boolean hasEntity(String entity)
    {
        int i=0;
        while(i<size() && !((Row)elementAt(i)).entity.equals(entity)) i++;
        if (i<size()) return true;
        else return false;
    }

    /** Writes the datatable not as a vector of rows, but as a vector of tuples each starting
     * with the entity of the row the tuple was taken from.
     */

    public Vector toFlatTable()
    {
        Vector output = new Vector();
        for(int i=0;i<size();i++)
        {
            Row row = (Row)elementAt(i);
            if(row.tuples.size() > 0)
            {
                for(int j=0;j<row.tuples.size();j++)
                {
                    Vector new_tuple = (Vector)((Vector)row.tuples.elementAt(j)).clone();
                    if (new_tuple.size()>0 && ((String)new_tuple.elementAt(0)).equals(""))
                        new_tuple.removeElementAt(0);
                    new_tuple.insertElementAt(row.entity,0);
                    output.addElement(new_tuple);
                }
            }
        }
        return output;
    }

    /** Writes a flat table back as a vector of rows.
     */

    public Datatable fromFlatTable()
    {
        Datatable output = new Datatable();
        for(int i=0;i<size();i++)
        {
            Vector tuple = (Vector)elementAt(i);
            String entity = (String)tuple.elementAt(0);
            tuple.removeElementAt(0);
            int j=0;
            Row row = output.rowWithEntity(entity);
            row.tuples.addElement(tuple);
            if (row.tuples.size()==1) output.addElement(row);
        }
        return output;
    }

    /** Checks whether this datatable is identical to the other given
     datatable. Ignores anything which is in psuedo entities list -
     alisonp. needs testing*/

    public boolean isIdenticalTo(Theory theory, Datatable other_table)
    {
        if(!theory.pseudo_entities.isEmpty())
        {
            //System.out.println("started isIdenticalTo() on " + this.toTable() + " and " + other_table.toTable());
            //System.out.println("pseudo_entities is " + theory.pseudo_entities);
        }
        boolean output = true;
        int i=0;
        if(theory.pseudo_entities.isEmpty() && other_table.size()!=size()) output=false;

        if(!theory.pseudo_entities.isEmpty() && other_table.size()!=size())
        {
            if(other_table.size()>size()+theory.pseudo_entities.size() || size() > other_table.size()+theory.pseudo_entities.size())
                output=false;
        }
        while(i<size() && output)
        {
            Row row = (Row)elementAt(i);
            Row other_row = (Row)other_table.elementAt(i);

            if(theory.pseudo_entities.contains(row.entity.toString()) || theory.pseudo_entities.contains(other_row.entity.toString()))
                output = true;

            else
            {
                if (!row.entity.equals(other_row.entity))
                {
                    output = false;
                    break;
                }
                if (!row.tuples.toString().equals(other_row.tuples.toString())) output = false;
            }
            i++;
        }
        //if(!theory.pseudo_entities.isEmpty())
        //System.out.println("finished isIdenticalTo() - returning output=" + output);
        return output;
    }

    /** Returns a string representation of the first tuple for each row
     */

    public String firstTuples()
    {
        String output = "";
        for (int i=0; i<size(); i++)
        {
            Row row = (Row)elementAt(i);
            if (row.tuples.isEmpty())
                output = output + "empty";
            else
                output = output + ((Vector)row.tuples.elementAt(0)).toString();
        }
        return output;
    }

    /** Adds a row to the datatable
     */

    public void addRow(String entity)
    {
        Tuples tuples = new Tuples();
        tuples.addElement(new Vector());
        Row row = new Row(entity,tuples);
        addElement(row);
    }

    /** Adds an empty row to the datatable
     */

    public void addEmptyRow(String entity)
    {
        Tuples tuples = new Tuples();
        Row row = new Row(entity,tuples);
        addElement(row);
    }

    /** Adds a row to the datatable
     */

    public void addRow(String entity, String elements)
    {
        String element = "";
        Tuples tuples = new Tuples();
        if (!elements.equals(""))
        {
            for (int i=0;i<elements.length();i++)
            {
                if (elements.substring(i,i+1).equals(",") || i==elements.length()-1)
                {
                    if (i==elements.length()-1)
                        element = element + elements.substring(i,i+1);
                    Vector tuple = new Vector();
                    tuple.addElement(element);
                    tuples.addElement(tuple);
                    element = "";
                }
                else
                    element = element + elements.substring(i,i+1);
            }
        }
        Row row = new Row(entity,tuples);
        addElement(row);
    }

    /** Adds a row to the datatable
     */

    public void addTwoRow(String entity, String element1, String element2)
    {
        Tuples tuples = new Tuples();
        Vector tuple = new Vector();
        tuple.addElement(element1);
        tuple.addElement(element2);
        Row row = rowWithEntity(entity);
        row.tuples.addElement(tuple);
        if (row.tuples.size()==1)
            addElement(row);
    }

    /** Adds a row to the datatable
     */

    public void addTuple(Vector tuple)
    {
        String entity = (String)tuple.elementAt(0);
        Vector new_tuple = (Vector)tuple.clone();
        new_tuple.removeElementAt(0);
        Row row = rowWithEntity(entity);
        row.tuples.addElement(new_tuple);
        row.tuples.removeDuplicates();
        row.tuples.sort();
    }

    /** Checks whether the datatable has all empty rows.
     * i.e. no tuples for any entity
     */

    public boolean allEmptyTuples(Theory theory)
    {
        int i=0;
        boolean output = true;
        while(output && i<size())
        {
            Row row = (Row)elementAt(i);
            if (!row.tuples.isEmpty())
            {
                if(!(theory.pseudo_entities.contains(row.entity.toString())))
                    output = false;
            }
            i++;
        }
        return output;
    }

    /** This returns the number of ground objects (inside all the tuples
     * inside the rows) in the datatable.
     */

    public double fullSize()
    {
        double output = 0;
        for (int i=0; i<size(); i++)
        {
            Row row = (Row)elementAt(i);
            for (int j=0; j<row.tuples.size(); j++)
                output = output + ((Vector)row.tuples.elementAt(j)).size();
        }
        return output;
    }

    /** This returns the proportion of entities in the datatable which
     * have something in their row.
     */

    public double applicability()
    {
        if (size()==0)
            return 0;
        double num_filled = 0;
        for (int i=0; i<size(); i++)
        {
            Row row = (Row)elementAt(i);
            if (!row.tuples.isEmpty())
                num_filled++;
        }
        return num_filled/size();
    }

    /** This returns the proportion of entities in the datatable which
     * have something in their row.
     */

    public double applicability(Vector entities)
    {
        if (size()==0)
            return 0;
        double num_filled = 0;
        for (int i=0; i<size(); i++)
        {
            Row row = (Row)elementAt(i);
            if (!row.tuples.isEmpty() && entities.contains(row.entity))
                num_filled++;
        }
        return num_filled/entities.size();
    }


    /** This returns the number of entities in the datatable which
     * have something in their row.
     */

    public double plausibility()
    {
        if (size()==0)
            return 0;
        double num_filled = 0;
        for (int i=0; i<size(); i++)
        {
            Row row = (Row)elementAt(i);
            if (!row.tuples.isEmpty())
                num_filled++;
        }
        return num_filled;
    }


    /** This returns the proportion of categories containing an entity which
     * has a non-empty row.
     */

    public double coverage(Categorisation cat)
    {
        double output = 0;
        for (int i=0; i<cat.size(); i++)
        {
            Vector category = (Vector)cat.elementAt(i);
            boolean found_non_empty = false;
            int j=0;
            while (!found_non_empty && j<category.size())
            {
                String entity = (String)category.elementAt(j);
                Row row = rowWithEntityNoTuples(entity);
                if (row.entity.equals(entity) && !row.tuples.isEmpty())
                {
                    output++;
                    found_non_empty = true;
                }
                j++;
            }
        }
        return output/(cat.size());
    }

    /** Returns the entities in the rows, as they appear in the table.
     */

    public Vector getEntities()
    {
        Vector output = new Vector();
        for (int i=0; i<size(); i++)
            output.addElement(((Row)elementAt(i)).entity);
        return output;
    }

    /** Returns the percentage number of tuples of this table which are
     * also tuples of the given table.
     */

    public double percentageMatchWith(Datatable other_table)
    {
        double num_matches = 0;
        double tries = 0;
        for (int i=0; i<size(); i++)
        {
            Row row = (Row)elementAt(i);
            Row other_row = (Row)other_table.elementAt(i);
            String other_row_string = other_row.tuples.toString();
            if (other_row_string.equals("[]"))
            {
                tries++;
                if (row.tuples.isEmpty())
                    num_matches++;
            }
            if (other_row_string.equals("[[]]"))
            {
                tries++;
                if (row.tuples.size()==1)
                    num_matches++;
            }

            if (!other_row_string.equals("[]") && !other_row_string.equals("[[]]"))
            {
                for (int j=0; j<row.tuples.size(); j++)
                {
                    tries++;
                    Vector tuple = (Vector)row.tuples.elementAt(j);
                    String tuple_string = tuple.toString();
                    if (other_row_string.indexOf(tuple_string)>=0)
                        num_matches++;
                }
            }
        }
        double output = num_matches/tries;
        return output;
    }

    /** Does a quick comparison (based on entities, rather than tuples)
     * of the number of matches this table has with the given one.
     * Altered by alisonp on 12/4 to record counter-examples.  Altered
     * by alisonp on 8/7 to ignore entities in pseudo_entities.  */

    public double quickPercentageMatchWith(Theory theory, Datatable other_table, Vector counterexamples)
    {

        double num_matches = size();
        for (int i=0; i<size(); i++)
        {
            Row row = (Row)elementAt(i);
            Row other_row = (Row)other_table.elementAt(i);
            if (!theory.pseudo_entities.contains(row.entity))
            {

                if (other_row.tuples.isEmpty() && !row.tuples.isEmpty())
                {
                    num_matches--;
                    counterexamples.addElement(new Entity(row.entity));
                }
                if (!other_row.tuples.isEmpty() && row.tuples.isEmpty())
                {
                    num_matches--;
                    counterexamples.addElement(new Entity(row.entity));
                }
                if (!other_row.tuples.isEmpty() && !row.tuples.isEmpty())
                {
                    String row_string = row.tuples.toString();
                    String other_row_string = other_row.tuples.toString();
                    if (!row_string.equals(other_row_string))
                    {
                        num_matches--;
                        counterexamples.addElement(new Entity(row.entity));
                    }
                }
            }
        }
        double output = num_matches/size();
        return output;
    }

    /** Removes the rows with the given entities
     */

    public void removeRow(String entity)
    {
        for (int i=0; i<size(); i++)
        {
            Row row = (Row)elementAt(i);
            if (row.entity.equals(entity))
            {
                removeElementAt(i);
                i--;
            }
        }
    }

    /** This function uses the trimToSize() method from Vector to trim both itself
     * and the tuples in the rows. This should reduce the size of the memory required
     * to store the datatable.
     */

    public void trimRowsToSize()
    {
        trimToSize();
        for (int i=0; i<size(); i++)
        {
            Row row = (Row)elementAt(i);
            row.tuples.trimToSize();
        }
    }

    /** Checks whether the datatable has the same, single entity in
     * every row.  i.e. the same example for every entity (ignoring
     * anything in pseudo_entities) - alisonp */

    public boolean sameExampleForEveryEntity(Theory theory, String entity_example, AgentWindow window)
    {
        window.writeToFrontEnd("started sameExampleForEveryEntity - testing to see if this entity: " + entity_example);
        window.writeToFrontEnd("is a culprit entity for this datatable");
        window.writeToFrontEnd(this.toTable());

        int i=0;
        boolean output = true;
        while(output && i<size())
        {
            window.writeToFrontEnd("i is " + i);
            Row row = (Row)elementAt(i);
            if (!(row.tuples.size()==1))
            {
                if(!(theory.pseudo_entities.contains(row.entity.toString())))
                    output = false;
            }

            if (row.tuples.isEmpty())//check
                output = false;

            if (!(row.tuples.size()>1))
            {
                Vector vector_in_tuple = (Vector)row.tuples.elementAt(0);
                String entity_on_row = (String)vector_in_tuple.elementAt(0);
                window.writeToFrontEnd("vector_in_tuple is " + vector_in_tuple);
                window.writeToFrontEnd("entity_on_row is " + entity_on_row);

                if(!entity_on_row.equals(entity_example))
                {
                    if(!(theory.pseudo_entities.contains(row.entity.toString())))
                        output = false;
                }
            }
            i++;
        }
        return output;
    }


    /** Checks to see if the given entity is in the datatable, and if
     * so, deletes that row. */

    public void removeEntity(String entity)
    {
        for(int i=0; i<size(); i++)
        {
            if(((Row)elementAt(i)).entity.equals(entity))
            {
                removeElementAt(i);
                i--;
            }
        }
    }


    /** Returns a vector of all the entities which appear in one
     * datatable but not the other */

    public Vector getDifference(Datatable datatable)
    {
        Vector counters = new Vector();

        if(this.size() == datatable.size())
        {
            for (int i=0; i<this.size(); i++)
            {
                Row lh_row = (Row)this.elementAt(i);
                Row rh_row = (Row)datatable.elementAt(i);
                if (!lh_row.tuples.toString().equals(rh_row.tuples.toString()))
                    counters.addElement(new Entity(lh_row.entity));
            }
        }

        if(this.size() < datatable.size())
        {
            for(int i=0; i<this.size(); i++)
            {
                Row lh_row = (Row)this.elementAt(i);
                Row rh_row = (Row)datatable.elementAt(i);
                if (!lh_row.tuples.toString().equals(rh_row.tuples.toString()))
                    counters.addElement(new Entity(lh_row.entity));
            }

            for(int i = this.size(); i<datatable.size(); i++)
            {
                Row rh_row = (Row)datatable.elementAt(i);
                counters.addElement(new Entity(rh_row.entity));
            }
        }

        if(this.size() > datatable.size())
        {
            for(int i=0; i<datatable.size(); i++)
            {
                Row lh_row = (Row)this.elementAt(i);
                Row rh_row = (Row)datatable.elementAt(i);
                if (!lh_row.tuples.toString().equals(rh_row.tuples.toString()))
                    counters.addElement(new Entity(lh_row.entity));
            }

            for(int i = datatable.size(); i<this.size(); i++)
            {
                Row rh_row = (Row)this.elementAt(i);
                counters.addElement(new Entity(rh_row.entity));
            }
        }
        return counters;
    }
}
