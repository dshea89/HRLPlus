package com.github.dshea89.hrlplus;

import java.awt.List;
import java.util.Vector;
import java.io.Serializable;

public class SortableList extends List implements Serializable
{
    boolean sort_items = true;

    Vector values = new Vector();

    public SortableList(int i)
    {
    }

    public SortableList()
    {
    }

    public SortableList(int i, boolean multiple)
    {
        setMultipleMode(multiple);
    }

    public void addItem(String s)
    {
        if (!sort_items)
        {
            super.addItem(s);
            return;
        }

        for (int i=0; i<getItemCount(); i++)
        {
            String item = getItem(i);
            if (item.compareTo(s) > 0)
            {
                super.addItem(s, i);
                return;
            }
        }
        super.addItem(s);
    }

    public void addItem(String s, String value)
    {
        if (value==null || value.equals(""))
        {
            addItem(s);
            return;
        }
        try
        {
            Double new_value = new Double(value);
            double d = new_value.doubleValue();
            for (int i=0; i<values.size(); i++)
            {
                Double old_value = (Double)values.elementAt(i);
                if (old_value.doubleValue() < d)
                {
                    super.addItem(s, i);
                    values.insertElementAt(new_value, i);
                    return;
                }
            }
            super.addItem(s);
            values.addElement(new_value);
        }
        catch(Exception e)
        {
            super.addItem(s);
        }
    }

    public void clear()
    {
        super.clear();
        values = new Vector();
    }
}
