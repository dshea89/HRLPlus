package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.io.Serializable;

public class Categorisation extends Vector implements Serializable
{
    public double novelty = 0;
    public double discrimination = -1;
    public double invariance = -1;
    public int appearances = 0;
    public boolean is_user_given = false;
    public String type_of_objects_in_categorisation = "blank";
    public Vector concepts = new Vector();
    public Vector entities = new Vector();
    public int cat_pos[] = null;

    /** The constructor from a string such as [[a,b,c],[d,e,f]]
     * It shouldn't have any spaces in it and the entities being
     * categorised cannot have commas in their string representations.
     */

    public Categorisation(String cat_string)
    {
        int pos = 2;
        Vector category = new Vector();
        String element = "";
        while (pos < cat_string.length()-1)
        {
            String s = cat_string.substring(pos, pos+1);
            if (s.equals(","))
            {
                category.addElement(element);
                element = "";
            }
            if (s.equals("]"))
            {
                category.addElement(element);
                sortAndAdd((Vector)category.clone());
                pos = pos + 2;
                element = "";
                category = new Vector();
            }
            if (!s.equals(",") && !s.equals("]"))
                element = element + s;
            pos++;
        }
    }

    public Categorisation()
    {
    }

    private void sortAndAdd(Vector v)
    {
        Vector add_cat = new Vector();
        for (int i=0; i<v.size(); i++)
        {
            String new_entity = (String)v.elementAt(i);
            boolean placed = false;
            for (int j=0; j<add_cat.size() && !placed; j++)
            {
                String old_entity = (String)add_cat.elementAt(j);
                if (old_entity.compareTo(new_entity)>0)
                {
                    add_cat.insertElementAt(new_entity, j);
                    placed = true;
                }
            }
            if (!placed)
                add_cat.addElement(new_entity);
        }
        addElement(add_cat);
    }

    /** This returns the set of entities being categorised.
     */

    public Vector getEntities()
    {
        if (entities.isEmpty())
        {
            for (int i=0; i<size(); i++)
            {
                Vector category = (Vector)elementAt(i);
                for (int j=0; j<category.size(); j++)
                    entities.addElement((String)category.elementAt(j));
            }
        }
        return entities;
    }

    /** This checks whether this categorisation categorises the entities
     * in a way which complies with the given categorisation, i.e. that
     * it doesn't put two entities together which should be apart and
     * doesn't put two entities apart which should be together.
     */

    public boolean compliesWith(Categorisation other_cat)
    {
        for (int i=0; i<size(); i++)
        {
            Vector category = (Vector)elementAt(i);
            if (!(categoryCompliesWith(getEntities(), category, other_cat)))
                return false;
        }
        return true;
    }

    private boolean categoryCompliesWith(Vector all_entities, Vector category, Categorisation other_cat)
    {
        for (int i=0; i<category.size(); i++)
        {
            String entity = (String)category.elementAt(i);

            // First check that no two elements are split up which should be together.

            Vector other_category = other_cat.getCategoryContaining(entity);
            for (int j=0; j<other_category.size(); j++)
            {
                String other_entity = (String)other_category.elementAt(j);
                if (all_entities.contains(other_entity) && !category.contains(other_entity))
                    return false;
            }

            // Next check that no two elements are put together which shouldn't be.

            for (int j=i; j<category.size(); j++)
            {
                String second_entity = (String)category.elementAt(j);
                if (other_category!=other_cat.getCategoryContaining(second_entity))
                    return false;
            }
        }
        return true;
    }

    public Vector getCategoryContaining(String entity)
    {
        for (int i=0; i<size(); i++)
        {
            if (((Vector)elementAt(i)).contains(entity))
                return ((Vector)elementAt(i));
        }
        return new Vector();
    }

    /** This calculates the invariance of this categorisation with respect to the
     * the given gold standard categorisation over the given set of entities.
     */

    public double invarianceWith(Vector entity_names, Vector same_pairs)
    {
        if (invariance >= 0)
            return invariance;
        double top = 0;
        double bottom = 0;
        getCatPoses(entity_names);
        for (int i=0; i<same_pairs.size(); i++)
        {
            int[] same_pair = (int[])same_pairs.elementAt(i);
            if (cat_pos[same_pair[0]]==cat_pos[same_pair[1]])
                top++;
            bottom++;
        }
        invariance = top/bottom;
        return invariance;
    }

    /** This calculates the invariance of this categorisation with respect to the
     * the given gold standard categorisation over the given set of entities.
     */

    public double discriminationWith(Vector entity_names, Vector diff_pairs)
    {
        if (discrimination >= 0)
            return discrimination;
        double top = 0;
        double bottom = 0;
        getCatPoses(entity_names);
        for (int i=0; i<diff_pairs.size(); i++)
        {
            int[] diff_pair = (int[])diff_pairs.elementAt(i);
            if (cat_pos[diff_pair[0]]!=cat_pos[diff_pair[1]])
                top++;
            bottom++;
        }
        discrimination = top/bottom;
        return discrimination;
    }

    /** This puts the integers into the cat_pos array, where the ith integer
     * is the position of the category in this categorisation that the
     * ith entity belongs to.
     */

    public void getCatPoses(Vector entity_names)
    {
        if (cat_pos==null)
        {
            cat_pos = new int[entity_names.size()];
            for (int i=0; i<size(); i++)
            {
                Vector category = (Vector)elementAt(i);
                for (int j=0; j<category.size(); j++)
                {
                    String entity = (String)category.elementAt(j);
                    if (entity_names.indexOf(entity)>=0)
                        cat_pos[entity_names.indexOf(entity)] = i;
                }
            }
        }
    }

}
