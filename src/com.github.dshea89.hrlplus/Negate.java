package com.github.dshea89.hrlplus;

import java.io.Serializable;
import java.util.Vector;

import java.util.Vector;
import java.util.Enumeration;
import java.lang.String;
import java.io.Serializable;

/** A class representing the negate production rule. This production
 * rule takes an two old datatables as input. There is no
 * parameterization for this production rule. This production rule
 * extracts rows from the first (generalised) datatable which do not appear in
 * the second datatable.
 *
 * @author Simon Colton, started 3rd January 1999
 * @version 1.0 */

public class Negate extends ProductionRule implements Serializable
{

    /** Returns false as this is a unary production rule.
     */

    public boolean isBinary()
    {
        return true;
    }

    /** Returns "negate" as that is the name of this production rule.
     */

    public String getName()
    {
        return "negate";
    }

    /** Given a vector of two concepts, this will return all the parameterisations for this
     * concept. It returns an empty vector if the secondary concept is not a generalisation
     * of the first or if the types are different. Otherwise it returns a vector containing
     * a single empty vector - indicating that the negation can go ahead.
     *
     * There is one forbidden path - double negation.
     */

    public Vector allParameters(Vector old_concepts, Theory theory)
    {
        return allParameters(old_concepts);
    }

    public Vector allParameters(Vector old_concepts)
    {
        Vector output = new Vector();
        Concept old_concept =(Concept)old_concepts.elementAt(0);
        if (old_concept.arity > arity_limit)
            return new Vector();
        Concept supposed_generalisation = (Concept)old_concepts.elementAt(1);
        if (old_concept==supposed_generalisation)
            return new Vector();
        if (old_concept.generalisations.contains(supposed_generalisation) &&
                old_concept.arity == supposed_generalisation.arity)
            output.addElement(new Vector());

        // Forbidden path //

        if (old_concept.construction.productionRule().getName().equals("negate") &&
                ((Concept)old_concept.construction.conceptList().elementAt(1)).id.equals(supposed_generalisation.id))
            return (new Vector());
        return output;
    }

    /** This produces the new specifications for concepts output using
     * the size production rule. To do this, it works out which generalisation
     * of the starting concept was used. Then it determines which specifications
     * were not in the generalisation, and negates them.
     * It also determines which columns are used in the negated part of the specification.
     */

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters,
                                    Theory theory, Vector new_functions)
    {
        Vector output = new Vector();
        Concept old_concept = (Concept)input_concepts.elementAt(0);
        Vector all_specs = old_concept.specifications;
        Concept generalisation = (Concept)input_concepts.elementAt(1);
        Vector true_specs = generalisation.specifications;
        Specification multiple_negate_spec = new Specification();
        multiple_negate_spec.type = "negate";

        // Special case: the concept being negated is a single entity (e.g., [a] : a=4).
        if (old_concept.is_single_entity)
        {
            output = (Vector)true_specs.clone();
            Specification spec_to_negate = (Specification)all_specs.lastElement();
            multiple_negate_spec.previous_specifications.addElement(spec_to_negate);
            multiple_negate_spec.permutation.addElement("0");
            output.addElement(multiple_negate_spec);
            return output;
        }

        Vector used_columns = new Vector();
        for (int i=0; i<all_specs.size(); i++)
        {
            Specification spec = (Specification)all_specs.elementAt(i);
            if (true_specs.contains(spec))
                output.addElement(spec);
            else
            {
                for (int j=0;j<spec.permutation.size();j++)
                {
                    String column = (String)spec.permutation.elementAt(j);
                    if (!used_columns.contains(column))
                        used_columns.addElement(column);
                }
                multiple_negate_spec.previous_specifications.addElement(spec.copy());
            }
        }
        for (int i=0; i<old_concept.arity; i++)
        {
            if (used_columns.contains(Integer.toString(i)))
                multiple_negate_spec.permutation.addElement(Integer.toString(i));
        }

        // Change the permutation of the previous specifications //

        for (int i=0; i<multiple_negate_spec.previous_specifications.size(); i++)
        {
            Vector new_perm = new Vector();
            Specification prev_spec =
                    (Specification)multiple_negate_spec.previous_specifications.elementAt(i);
            for (int j=0; j<prev_spec.permutation.size(); j++)
                new_perm.addElement(Integer.toString(multiple_negate_spec.permutation.
                        indexOf((String)prev_spec.permutation.elementAt(j))));
            prev_spec.permutation=new_perm;
        }

        // Add in any functions not involved in the multiple specifications //

        for (int i=0; i<output.size(); i++)
        {
            Specification spec = (Specification)output.elementAt(i);
            for (int j=0; j<spec.functions.size(); j++)
                new_functions.addElement(spec.functions.elementAt(j));
        }

        // If a single specification has been negated, then negate the functions
        // from this and add them in
        // THIS STILL NEEDS DOING: TAKEN OUT BECAUSE IT IS BROKEN //

    /*
    if (multiple_negate_spec.previous_specifications.size()==1)
      {
	Specification previous_spec =
	  (Specification)multiple_negate_spec.previous_specifications.elementAt(0);
	for (int i=0; i<previous_spec.functions.size(); i++)
	  {
	    Function function = (Function)previous_spec.functions.elementAt(i);
	    Function negated_function = (Function)function.copy();
	    if (negated_function.is_negated)
	      negated_function.is_negated = false;
	    else
	      negated_function.is_negated = true;

	    // Change the input and output columns of the new function //

	    for (int j=0; j<negated_function.input_columns.size(); j++)
	      {
		String ic = (String)negated_function.input_columns.elementAt(j);
		if (!(ic.substring(0,1).equals(":")))
		  {
		    int pos = (new Integer(ic)).intValue();
		    String new_ic = (String)previous_spec.permutation.elementAt(pos);
		    negated_function.input_columns.setElementAt(new_ic, j);
		  }
	      }
	    for (int j=0; j<negated_function.output_columns.size(); j++)
	      {
		String oc = (String)negated_function.output_columns.elementAt(j);
		if (!(oc.substring(0,1).equals(":")))
		  {
		    int pos = (new Integer(oc)).intValue();
		    String new_oc = (String)previous_spec.permutation.elementAt(pos);
		    negated_function.output_columns.setElementAt(new_oc, j);
		  }
	      }

	    new_functions.addElement(negated_function);
	    multiple_negate_spec.functions.addElement(negated_function);
	  }
      }
      */

        output.addElement(multiple_negate_spec);
        return output;
    }

    /** This produces the new datatable from the given datatable, using the parameters
     * specified.
     */

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector input_parameters, Vector all_concepts)
    {
        // First get the required old datatables. //

        //System.out.println("1 - transformTable()");
        Concept old_concept = (Concept)input_concepts.elementAt(0);
        Datatable output = new Datatable();
        Concept generalisation = (Concept)input_concepts.elementAt(1);

        Datatable old_datatable = (Datatable)input_datatables.elementAt(0);
        //System.out.println("2 - transformTable()");
        for (int i=0;i<old_datatable.size();i++)
        {
            Row new_row = new Row();
            Row old_row = (Row)old_datatable.elementAt(i);
            new_row.entity = old_row.entity;
            Row general_row = generalisation.calculateRow(all_concepts,old_row.entity);
            for (int j=0;j<general_row.tuples.size();j++)
            {
                Vector general_tuple = (Vector)general_row.tuples.elementAt(j);
                String general_tuple_string = general_tuple.toString();
                Vector check_vector = new Vector();
                for (int k=0; k<old_row.tuples.size();k++)
                    check_vector.addElement(((Vector)old_row.tuples.elementAt(k)).toString());
                if (!check_vector.contains(general_tuple_string))
                    new_row.tuples.addElement(general_tuple);
            }
            //System.out.println("3 - old_concept.prettyPrintExamples(old_datatable) is\n" + old_concept.prettyPrintExamples(old_datatable));
            //System.out.println("4 - generalisation.prettyPrintExamples(generalisation.datatable) is\n" + generalisation.prettyPrintExamples(generalisation.datatable));
            output.addElement(new_row);
        }

        return output;
    }

    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        return ((Concept)old_concepts.elementAt(0)).types;
    }

    /** This assigns a score to a concept depending on whether the
     * production rule can see any likelihood of a pattern. The pattern for the negate
     * production rule is that each of the entities has a empty row of tuples. The
     * fewer the non-entities that have an empty row, the better.
     */

    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list)
    {
        Concept c = (Concept)concept_list.elementAt(0);
        if (allParameters(concept_list).isEmpty())
            return 0;
        int i=0;
        boolean empty_pattern = true;
        while (empty_pattern && i<entity_list.size())
        {
            String entity = (String)entity_list.elementAt(i);
            Row row = c.calculateRow(all_concepts,entity);
            if (row.tuples.size()>0)
                empty_pattern = false;
            i++;
        }
        if (!empty_pattern) return 0;
        int score = non_entity_list.size();
        for (i=0;i<non_entity_list.size();i++)
        {
            String entity = (String)non_entity_list.elementAt(i);
            Row row = c.calculateRow(all_concepts,entity);
            if (row.tuples.size()==0)
                score--;
        }
        return score;
    }
}
