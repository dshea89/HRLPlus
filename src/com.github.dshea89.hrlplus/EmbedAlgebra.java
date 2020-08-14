package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.lang.String;
import java.io.*;

/** A class representing the embed algebra production rule.
 *
 * @author Simon Colton, started 19th April 2002
 * @version 1.0 */

public class EmbedAlgebra extends ProductionRule implements Serializable
{
    /** Where the batch files to call Mace are.
     */

    public String input_files_directory = "";

    /** The hashtables of isomorphism which to test for isomorphism.
     */

    public Hashtable isomorphisms = new Hashtable();

    /** The maths object for this production rule. This is used for checking whether
     * two algebras are isomorphic or not.
     */

    public Maths maths = new Maths();

    /** The Mace object for this production rule. (Mace is used to model check
     * whether the sub-algebras satisfy the axioms of particular algebras).
     */

    public Mace mace = new Mace();

    /** The Hashtable of <algebra_name, Vector of axiom strings> this will use.
     */

    public Hashtable algebra_hashtable = new Hashtable();

    /** Which algebras to try and embed.
     */

    public Vector algebras_to_check_for = new Vector();

    public boolean isBinary()
    {
        return false;
    }

    /** Whether or not this produces cumulative concepts.
     * @see Concept
     */

    public boolean is_cumulative = false;

    /** Returns "exists" as that is the name of this production rule.
     */

    public String getName()
    {
        return "embed_algebra";
    }

    /** Given a vector of one concept, this will return all the parameterisations for this
     * concept. It will return all tuples of columns which do not contain the first column.
     */

    public Vector allParameters(Vector old_concepts, Theory theory)
    {
        Concept old_concept = (Concept)old_concepts.elementAt(0);
        Vector output = new Vector();
        if (old_concept.arity==4)
        {
            for (int i=0; i<algebras_to_check_for.size(); i++)
            {
                Vector param = new Vector();
                param.addElement((String)algebras_to_check_for.elementAt(i));
                output.addElement(param);
            }
        }
        return output;
    }

    /** This produces the new specifications for concepts output using the embed
     * algebra production rule.
     */

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters,
                                    Theory theory, Vector new_functions)
    {
        Concept old_concept = (Concept)input_concepts.elementAt(0);
        String algebra = (String)input_parameters.elementAt(0);
        Relation relation = new Relation();
        relation.addDefinition("ab", old_concept.id + " forms " + algebra + " @b@ for @a@", "ascii");
        relation.name = old_concept.id + algebra;
        Specification new_specification = new Specification();
        Vector permutation = new Vector();
        permutation.addElement("0");
        permutation.addElement("1");
        new_specification.addRelation(permutation, relation);
        new_specification.permutation.addElement("0");
        new_specification.permutation.addElement("1");

        Vector output = new Vector();
        output.addElement(new_specification);
        return output;
    }

    /** This produces the new datatable from the given datatable, using the parameters
     * specified. This production rule removes columns from the input datatable, then
     * removes any duplicated rows. The parameters details which columns are to be #removed#.
     */

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector parameters, Vector all_concepts)
    {
        Datatable output = new Datatable();
        String algebra = (String)parameters.elementAt(0);
        Datatable old_datatable = (Datatable)input_datatables.elementAt(0);
        for (int i=0; i<old_datatable.size(); i++)
        {
            Hashtable element_names = new Hashtable();
            int new_element_number = 0;
            Vector all_numbered_elements = new Vector();
            Row row = (Row)old_datatable.elementAt(i);
            if (!isomorphisms.containsKey(row.entity))
                isomorphisms.put(row.entity, row.entity);
            for (int j=0; j<row.tuples.size(); j++)
            {
                Vector tuple = (Vector)row.tuples.elementAt(j);
                for (int k=0; k<tuple.size(); k++)
                {
                    String element = (String)tuple.elementAt(k);
                    if (!element_names.containsKey(element))
                    {
                        element_names.put(element, Integer.toString(new_element_number));
                        all_numbered_elements.addElement(Integer.toString(new_element_number));
                        new_element_number++;
                    }
                }
            }

            int algebra_size = all_numbered_elements.size();
            Vector axioms_for_algebra = (Vector)algebra_hashtable.get(algebra);
            String mult_string = "";
            Vector all_mult_rows = new Vector();
            boolean repeated_entry = false;
            String string_rep = "";
            Vector mult_in_place = new Vector();
            for (int j=0; j<row.tuples.size(); j++)
            {
                Vector tuple = (Vector)row.tuples.elementAt(j);
                String element0 = (String)element_names.get((String)tuple.elementAt(0));
                String element1 = (String)element_names.get((String)tuple.elementAt(1));
                String element2 = (String)element_names.get((String)tuple.elementAt(2));
                String mult_row = element0 + " * " + element1 + " = " + element2 + ".\n";
                mult_string = mult_string + mult_row;
                String multiplicands = element0 + "," + element1;
                if (all_mult_rows.contains(multiplicands))
                    repeated_entry = true;
                else
                {
                    mult_in_place.addElement(element2);
                    all_mult_rows.addElement(multiplicands);
                }
            }
            boolean closed = true;
            for (int j=0; j<all_numbered_elements.size() && closed; j++)
            {
                for (int k=0; k<all_numbered_elements.size() && closed; k++)
                {
                    String multiplicand = (String)all_numbered_elements.elementAt(j) + "," +
                            (String)all_numbered_elements.elementAt(k);
                    if (!all_mult_rows.contains(multiplicand))
                        closed=false;
                    else
                    {
                        int pos = all_mult_rows.indexOf(multiplicand);
                        string_rep = string_rep + (String)mult_in_place.elementAt(pos);
                    }
                }
            }
            if (closed && !repeated_entry && !string_rep.trim().equals(""))
            {
                Vector axiom_strings = (Vector)algebra_hashtable.get(algebra);
                String mace_string = "set(auto).\nformula_list(usable).\n" + mult_string;
                for (int j=0; j<axiom_strings.size(); j++)
                {
                    String axiom = (String)axiom_strings.elementAt(j);
                    mace_string = mace_string + axiom + "\n";
                }
                mace_string = mace_string + "end_of_list.\n";
                try
                {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("delembedalgebra.in")));
                    out.write(mace_string);
                    out.close();
                }
                catch(Exception e)
                {
                    System.out.println("Having trouble opening file delembedalgebra.in");
                }
                if (mace.operating_system.equals("windows"))
                {
                    try
                    {
                        String mace_process_string = input_files_directory + "ea.bat " + Integer.toString(algebra_size);
                        Process mace_process = Runtime.getRuntime().exec(mace_process_string);
                        int exit_value = mace_process.waitFor();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Having trouble running mace");
                    }
                }
                Hashtable concept_data = mace.readModelFromMace("delembedalgebra.out");

                Row new_row = new Row(row.entity, new Tuples());
                if (!concept_data.isEmpty())
                {
                    Vector tuple = new Vector();
                    tuple.addElement(getIsomorphism(string_rep));
                    new_row.tuples.addElement(tuple);
                }
                output.addElement(new_row);
            }
            else
            {
                Row new_row = new Row(row.entity, new Tuples());
                output.addElement(new_row);
            }
        }
        return output;
    }

    private String getIsomorphism(String string_rep)
    {
        String output = (String)isomorphisms.get(string_rep);
        if (output!=null)
            return output;
        Enumeration isos = isomorphisms.keys();
        while (isos.hasMoreElements())
        {
            String iso = (String)isos.nextElement();
            Vector check_vector = new Vector();
            check_vector.addElement(string_rep);
            check_vector.addElement(iso);

            if (iso.length()==string_rep.length() &&
                    maths.nonIsomorphic(check_vector).size()==1)
            {
                isomorphisms.put(string_rep, iso);
                return iso;
            }
        }
        isomorphisms.put(string_rep, string_rep);
        return string_rep;
    }

    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        Vector old_types = (Vector)((Concept)old_concepts.elementAt(0)).types.clone();
        Vector new_types = new Vector();
        String algebra = (String)parameters.elementAt(0);
        new_types.addElement((String)old_types.elementAt(0));
        new_types.addElement(algebra);
        return new_types;
    }

    /** This assigns a score to  a concept depending on whether the
     * production rule can see any likelihood of a pattern. The pattern for the exists
     * production rule is that each of the entities has a non-empty row of tuples. The
     * fewer the non-entities that have a non-empty row, the better. Alternatively, if
     * none of the entities to learn has a non-empty row of tuples, this is also a
     * pattern.
     */

    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list)
    {
        int score = 0;
        return score;
    }
}
