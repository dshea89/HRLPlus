package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.io.Serializable;

public class SortableVector extends Vector implements Serializable
{
    double prune_less_than = 0;
    Reflection reflect = new Reflection();
    boolean sort_items = true;
    Vector values = new Vector();

    public SortableVector()
    {
    }

    public void addElement(Object obj, String sort_field)
    {
        if (sort_field==null || sort_field.equals(""))
        {
            super.addElement(obj);
            return;
        }
        try
        {
            String value = reflect.getValue(obj, sort_field).toString();
            Double new_value = new Double(value);
            double d = new_value.doubleValue();
            if (d<prune_less_than)
                return;
            for (int i=0; i<values.size(); i++)
            {
                Double old_value = (Double)values.elementAt(i);
                if (old_value.doubleValue() < d)
                {
                    super.insertElementAt(obj, i);
                    values.insertElementAt(new_value, i);
                    return;
                }
            }
            super.addElement(obj);
            values.addElement(new_value);
        }
        catch(Exception e)
        {
            super.addElement(obj);
        }
    }

    public void addElement(Object obj, int value)
    {
        Double new_value = new Double(value);
        double d = new_value.doubleValue();
        if (d<prune_less_than)
            return;
        for (int i=0; i<values.size(); i++)
        {
            Double old_value = (Double)values.elementAt(i);
            if (old_value.doubleValue() < d)
            {
                super.insertElementAt(obj, i);
                values.insertElementAt(new_value, i);
                return;
            }
        }
        super.addElement(obj);
        values.addElement(new_value);
        return;
    }

    public void addElement(Object obj, double d)
    {
        Double new_value = new Double(d);
        if (d<prune_less_than)
            return;
        for (int i=0; i<values.size(); i++)
        {
            Double old_value = (Double)values.elementAt(i);
            if (old_value.doubleValue() < d)
            {
                super.insertElementAt(obj, i);
                values.insertElementAt(new_value, i);
                return;
            }
        }
        super.addElement(obj);
        values.addElement(new_value);
        return;
    }

    public void addElement(Object obj, Long l)
    {
        Double new_value = new Double(l.longValue());
        double d = new_value.doubleValue();
        if (d<prune_less_than)
            return;
        for (int i=0; i<values.size(); i++)
        {
            Double old_value = (Double)values.elementAt(i);
            if (old_value.doubleValue() < d)
            {
                super.insertElementAt(obj, i);
                values.insertElementAt(new_value, i);
                return;
            }
        }
        super.addElement(obj);
        values.addElement(new_value);
        return;
    }

    public void reSortBy(String sort_field)
    {
        Vector copy = (Vector)clone();
        removeAllElements();
        for (int i=0; i<copy.size(); i++)
            addElement(copy.elementAt(i), sort_field);
    }

    public void reverse()
    {
        for (int i=0; i<size(); i++)
        {
            insertElementAt(lastElement(), i);
            removeElementAt(size()-1);
        }
    }
}
