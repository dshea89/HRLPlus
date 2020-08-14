package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.io.Serializable;

/** A class representing the compose production rule. This production
 * rule takes two concepts as input and produces a third concept which
 * has all the specifications from both concepts.  This production
 * rule takes two concepts as input and a set of parameters such as
 * [0,1,0,3,2] which state that the output concept will be of
 * arity 5 and will have the specifications of the first concept
 * acting on the original variables, along with the specifications of
 * the second concept acting on the variables in columns 2, 5 and 4.
 *
 * @author Simon Colton, started 12th December 1999
 * @version 1.0 */

public class Compose extends ProductionRule implements Serializable
{
    /** Whether or not to swap concepts when producing new specifications.
     */

    public boolean dont_swap = false;

    /** Whether or not to allow repeated specifications in the specifications for
     * a new concept.
     */

    public boolean allow_repeated_specifications = false;

    /** Subobject overlap flag. If this is set to true, then the parameterisations must
     * produce a concept with some non-trivial overlapping of the subobject columns of the
     * concept. eg. parameters [1,0,2] would not be allowed for two arity-2 concepts, as
     * the subobjects do not overlap, but [1,2] is allowed.
     */

    boolean subobject_overlap = false;

    /** The limit in seconds for the time allowed to construct a new datatable.
     */

    int time_limit = 10;

    /** The limit for the product of the tuple sizes for two concepts.
     * If two concepts with big tables are composed, this can seriously
     * slow things down. The default is 10000.
     */

    int tuple_product_limit = 150000;

    /** Returns true as this is a binary production rule.
     */

    public boolean isBinary()
    {
        return true;
    }

    /** Returns "compose" as that is the name of this production rule.
     */

    public String getName()
    {
        return "compose";
    }

    /** Whether or not this produces cumulative concepts.
     * @see Concept
     */

    public boolean isCumulative()
    {
        return false;
    }


    /** Given a vector of two concepts, this will return all the parameterisations for these
     * concepts. Note that it does not check whether the resulting concept will be syntactically
     * different to the rest in the theory (ie. has a different set of specifications).
     * Syntactic checking is carried out just before the parameterisation is used. If it were
     * used here, some concepts may be introduced before these parameters were used.
     */

    public Vector allParameters(Vector concepts, Theory theory)
    {
        parameter_failure_reason = "";
        Vector output = new Vector();
        Concept primary_concept = (Concept)concepts.elementAt(0);
        Concept secondary_concept = (Concept)concepts.elementAt(1);

        int t_prod = primary_concept.datatable.number_of_tuples*secondary_concept.datatable.number_of_tuples;
        if (t_prod > tuple_product_limit)
        {
            parameter_failure_reason = "t_prod ("+Integer.toString(t_prod)+">"+Integer.toString(tuple_product_limit)+")";
            return output;
        }
        int primary_arity = primary_concept.arity;
        int secondary_arity = secondary_concept.arity;
        int top_width = primary_arity + secondary_arity - 1;
        if (top_width > arity_limit)
            top_width = arity_limit;
        if (subobject_overlap &&
                primary_arity > 1 && secondary_arity > 1 &&
                top_width > primary_arity + secondary_arity - 2)
            top_width = primary_arity + secondary_arity - 2;
        int bottom_width = secondary_arity;
        if (bottom_width < primary_arity)
            bottom_width = primary_arity;

        Vector primary_types = primary_concept.types;
        Vector secondary_types = secondary_concept.types;

        for (int i=bottom_width; i<=top_width; i++)
        {
            Vector additional_parameterisations =
                    getParameterisations(i, primary_types, secondary_types);
            for (int j=0; j<additional_parameterisations.size(); j++)
                output.addElement(additional_parameterisations.elementAt(j));
        }
        return output;
    }

    private Vector getParameterisations(int param_size, Vector primary_types, Vector secondary_types)
    {
        // Base Parameters will be something like this: [0,1,2,-1]

        Vector output = new Vector();
        boolean add_s = false;
        Vector types1 = (Vector)primary_types.clone();
        Vector types2 = (Vector)secondary_types.clone();
        if (primary_types.size() < secondary_types.size())
        {
            add_s = true;
            types1 = (Vector)secondary_types.clone();
            types2 = (Vector)primary_types.clone();
        }

        int[] base_param = new int[param_size];
        for (int i=0; i<param_size; i++)
        {
            if (i<types1.size())
                base_param[i]=i;
            else
                base_param[i]=-1;
        }

        if (add_s)
        {
            Vector first_param = new Vector();
            first_param.addElement("s");
            output.addElement(first_param);
        }
        else
            output.addElement(new Vector());

        for (int i=0; i<base_param.length; i++)
        {
            Vector new_output = new Vector();
            for (int j=0; j<output.size(); j++)
            {
                Vector param = (Vector)output.elementAt(j);
                if (base_param[i]>=0)
                {
                    Vector blank_param = (Vector)param.clone();
                    blank_param.addElement("0");
                    new_output.addElement(blank_param);
                }
                for (int k=0; k<types2.size(); k++)
                {
                    if (!param.contains(Integer.toString(k+1)) &&
                            (base_param[i]<0 || types1.elementAt(base_param[i]).equals(types2.elementAt(k))) &&
                            !(k==0 && base_param[i]<0))
                    {
                        Vector new_param = (Vector)param.clone();
                        new_param.addElement(Integer.toString(k+1));
                        new_output.addElement(new_param);
                    }
                }
            }
            output = new_output;
        }

        Vector final_output = new Vector();
        for (int i=0; i<output.size(); i++)
        {
            Vector param = (Vector)output.elementAt(i);
            boolean keep = true;
            for (int j=1; j<=types2.size() && keep; j++)
                if (!param.contains(Integer.toString(j)))
                    keep=false;
            if (keep)
                final_output.addElement(param);
        }
        return final_output;
    }

    /** Composing two concepts together will result in a new concept with all
     * the specifications of both. This calculates all the specifications, removing
     * any duplicates.
     */

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters,
                                    Theory theory, Vector new_functions)
    {
        Vector old_parameters = (Vector)input_parameters.clone();
        Vector concept_list = input_concepts;
        if (((String)input_parameters.elementAt(0)).equals("s"))
        {
            old_parameters.removeElementAt(0);
            concept_list = super.swap(input_concepts);
        }
        Concept primary_concept = (Concept)concept_list.elementAt(0);
        Concept secondary_concept = (Concept)concept_list.elementAt(1);
        Vector primary_specifications = primary_concept.specifications;
        Vector secondary_specifications = secondary_concept.specifications;

        Vector output = (Vector)primary_specifications.clone();
        Vector parameters = new Vector();
        for (int i=0;i<old_parameters.size();i++)
        {
            int val = (new Integer((String)old_parameters.elementAt(i))).intValue();
            parameters.addElement(Integer.toString(val-1));
        }
        for (int i=0;i<secondary_specifications.size();i++)
        {
            Specification secondary_specification =
                    (Specification)secondary_specifications.elementAt(i);
            Vector permutation = secondary_specification.permutation;
            Vector new_permutation = new Vector();
            for (int j=0;j<permutation.size();j++)
            {
                String position =(String)permutation.elementAt(j);
                if (j>=0)
                {
                    int k=0;
                    while (k<parameters.size() && !((String)parameters.elementAt(k)).equals(position))
                        k++;
                    new_permutation.addElement(Integer.toString(k));
                }
            }
            Specification new_specification = secondary_specification.copy();
            new_specification.relations = secondary_specification.relations;
            new_specification.permutation = new_permutation;
            int j=0;
            if (!allow_repeated_specifications)
            {
                while (j<primary_specifications.size())
                {
                    if (new_specification.equals((Specification)primary_specifications.elementAt(j)))
                        break;
                    j++;
                }
                if (j==primary_specifications.size())
                    output.addElement(new_specification);
            }
            else
            {
                if (dont_swap && ((String)input_parameters.elementAt(0)).equals("s"))
                    output.insertElementAt(new_specification, i);
                else
                    output.addElement(new_specification);
            }
        }

        // Finally, make the new functions

        for (int i=0;i<primary_concept.functions.size();i++)
        {
            Function function = (Function)primary_concept.functions.elementAt(i);
            new_functions.addElement(function.copy());
        }

        for (int i=0;i<secondary_concept.functions.size();i++)
        {
            Function function = (Function)secondary_concept.functions.elementAt(i);
            Function new_function = function.copy();
            new_function.permute(parameters);
            new_functions.addElement(new_function);
        }

        return output;
    }

    /** This produces the new datatable from the two given datatables, using the parameters
     * specified.
     */

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector input_s_params, Vector all_concepts)
    {
        long start_time = System.currentTimeMillis()/1000;
        Vector concepts = new Vector();
        Vector datatables = new Vector();
        Vector s_params = new Vector();
        if (((String)input_s_params.elementAt(0)).equals("s"))
        {
            s_params = (Vector)input_s_params.clone();
            s_params.removeElementAt(0);
            concepts = super.swap(input_concepts);
            datatables = super.swap(input_datatables);
        }
        else
        {
            concepts = input_concepts;
            datatables = input_datatables;
            s_params = input_s_params;
        }
        int entity_column = s_params.indexOf("1");
        Datatable primary_table = (Datatable)datatables.elementAt(0);
        Concept primary_concept = (Concept)concepts.elementAt(0);
        int primary_arity = primary_concept.arity;

        Concept secondary_concept = (Concept)concepts.elementAt(1);

        Datatable output = new Datatable();
        for(int primary_row_number=0;primary_row_number<primary_table.size();primary_row_number++)
        {
            Tuples new_tuples = new Tuples();
            Row p_row = (Row)primary_table.elementAt(primary_row_number);
            Datatable p_sub_table = new Datatable();
            p_sub_table.addElement(p_row);
            Vector primary_tuples = p_sub_table.toFlatTable();
            Vector secondary_tuples = new Vector();
            if(entity_column==0)
            {
                Row s_row = secondary_concept.calculateRow(all_concepts,p_row.entity);
                Datatable s_sub_table = new Datatable();
                s_sub_table.addElement(s_row);
                secondary_tuples = s_sub_table.toFlatTable();
            }
            for(int primary_tuple_number=0;primary_tuple_number<primary_tuples.size();
                primary_tuple_number++)
            {
                Vector p_tuple = (Vector)primary_tuples.elementAt(primary_tuple_number);

                if(p_tuple.size()==primary_arity)
                {
                    if(entity_column>0)// && entity_column<p_tuple.size())// && entity_column<p_tuple.size() added dec2006
                    {
                        String entity_now = (String)p_tuple.elementAt(entity_column);
                        Row s_row = secondary_concept.calculateRow(all_concepts,entity_now);
                        Datatable s_sub_table = new Datatable();
                        s_sub_table.addElement(s_row);
                        secondary_tuples = s_sub_table.toFlatTable();
                    }
                    for(int secondary_tuple_number=0;secondary_tuple_number<secondary_tuples.size();
                        secondary_tuple_number++)
                    {

                        long time_taken_so_far = System.currentTimeMillis()/1000 - start_time;
                        if (time_taken_so_far > time_limit)
                            return new Datatable();

                        Vector s_tuple = (Vector)secondary_tuples.elementAt(secondary_tuple_number);
                        int k=0;
                        while(k<s_params.size())
                        {
                            int p_pos;
                            if (k<p_tuple.size()) p_pos = k; else p_pos=-1;
                            int s_pos = new Integer((String)s_params.elementAt(k)).intValue()-1;
                            if (s_pos >= 0 && p_pos >= 0)
                            {
                                try {
                                    String p_element = (String)p_tuple.elementAt(p_pos);
                                    String s_element = (String)s_tuple.elementAt(s_pos);
                                    if(!p_element.equals(s_element)) break;
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    break;
                                }
                            }
                            k++;
                        }
                        if (k==s_params.size())
                        {
                            Vector new_tuple = composeTuples(p_tuple,s_tuple,s_params);
                            new_tuple.removeElementAt(0);
                            new_tuples.addElement(new_tuple);
                        }
                    }
                }
            }
            Row new_row = new Row(p_row.entity,new_tuples);
            output.addElement(new_row);
        }
        return output;
    }

    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector input_s_params)
    {
        Vector concepts = new Vector();
        Vector s_params = new Vector();
        if (((String)input_s_params.elementAt(0)).equals("s"))
        {
            s_params = (Vector)input_s_params.clone();
            s_params.removeElementAt(0);
            concepts = super.swap(old_concepts);
        }
        else
        {
            concepts = old_concepts;
            s_params = input_s_params;
        }
        Vector p_types = (Vector)((Concept)concepts.elementAt(0)).types.clone();
        Vector s_types = (Vector)((Concept)concepts.elementAt(1)).types.clone();
        return(composeTuples(p_types,s_types,s_params));
    }

    /** This assigns a score to  a concept depending on whether the
     * production rule can see any likelihood of a pattern. At present, the pattern
     * spotting is limited. It can notice that all values in one column have
     * the property of another concept.
     */

    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list)
    {
        Concept c1 = (Concept)concept_list.elementAt(0);
        Concept c2 = (Concept)concept_list.elementAt(1);

        if (c1.datatable.number_of_tuples*c2.datatable.number_of_tuples >
                tuple_product_limit)
            return 0;
        int score = non_entity_list.size();

        // Both Entity Types //
        if (c1.arity==1 && c2.arity==1 &&
                c1.object_type.equals(c2.object_type))
        {
            boolean full_pattern_holds = true;
            boolean empty_pattern_holds = true;
            boolean negate_pattern_holds = true;
            int i=0;
            while ((full_pattern_holds || empty_pattern_holds || negate_pattern_holds) &&
                    i < entity_list.size())
            {
                String entity = (String)entity_list.elementAt(i);
                Row row1 = c1.calculateRow(all_concepts, entity);
                Row row2 = c2.calculateRow(all_concepts, entity);
                if (row1.tuples.size()!=row2.tuples.size())
                {
                    full_pattern_holds = false;
                    empty_pattern_holds = false;
                }
                if (row1.tuples.size()==row2.tuples.size() && !row1.tuples.isEmpty())
                {
                    negate_pattern_holds = false;
                    empty_pattern_holds = false;
                }
                if (row1.tuples.size()==row2.tuples.size() && row1.tuples.isEmpty())
                {
                    negate_pattern_holds = false;
                    full_pattern_holds = false;
                }
                i++;
            }
            if (!full_pattern_holds && !empty_pattern_holds && !negate_pattern_holds)
                return 0;
            for (i=0;i<non_entity_list.size();i++)
            {
                String entity = (String)non_entity_list.elementAt(i);
                Row row1 = c1.calculateRow(all_concepts, entity);
                Row row2 = c2.calculateRow(all_concepts, entity);
                if (row1.tuples.size()==row2.tuples.size() && full_pattern_holds && !row1.tuples.isEmpty())
                    score--;
                if (row1.tuples.size()==row2.tuples.size() && empty_pattern_holds && row1.tuples.isEmpty())
                    score--;
                if (row1.tuples.size()!=row2.tuples.size() && negate_pattern_holds)
                    score--;
            }
            return score;
        }

        // Both subobject types //
        if (c1.arity==2 && c2.arity==2 &&
                c1.object_type.equals(c2.object_type))
        {
            if (c1.id.equals(c2.id))
                return 0;
            String p_type = (String)c1.types.elementAt(1);
            String s_type = (String)c2.types.elementAt(1);
            if (!p_type.equals(s_type))
                return 0;
            boolean pattern_holds = true;
            boolean negate_pattern_holds = true;
            int i=0;
            while ((pattern_holds || negate_pattern_holds) &&
                    i < entity_list.size())
            {
                String entity = (String)entity_list.elementAt(i);
                Row row1 = c1.calculateRow(all_concepts, entity);
                Row row2 = c2.calculateRow(all_concepts, entity);

                // A tuple from every primary row must appear in the secondary row or vice versa //

                Vector row1_elements = new Vector();
                Vector row2_elements = new Vector();
                Vector shared_elements = new Vector();
                for (int j=0; j<row1.tuples.size(); j++)
                {
                    Vector tuple = (Vector)row1.tuples.elementAt(j);
                    String element = (String)tuple.elementAt(0);
                    row1_elements.addElement(element);
                }
                for (int j=0; j<row2.tuples.size(); j++)
                {
                    Vector tuple = (Vector)row2.tuples.elementAt(j);
                    String element = (String)tuple.elementAt(0);
                    row2_elements.addElement(element);
                    if (row1_elements.contains(element))
                        shared_elements.addElement(element);
                }
                if (shared_elements.isEmpty())
                    pattern_holds = false;
                else
                    negate_pattern_holds = false;
                i++;
            }
            if (!pattern_holds && !negate_pattern_holds)
                return 0;
            for (i=0;i<non_entity_list.size();i++)
            {
                String entity = (String)non_entity_list.elementAt(i);
                Row row1 = c1.calculateRow(all_concepts, entity);
                Row row2 = c2.calculateRow(all_concepts, entity);
                Vector row1_elements = new Vector();
                Vector row2_elements = new Vector();
                Vector shared_elements = new Vector();
                for (int j=0; j<row1.tuples.size(); j++)
                {
                    Vector tuple = (Vector)row1.tuples.elementAt(j);
                    String element = (String)tuple.elementAt(0);
                    row1_elements.addElement(element);
                }
                for (int j=0; j<row2.tuples.size(); j++)
                {
                    Vector tuple = (Vector)row2.tuples.elementAt(j);
                    String element = (String)tuple.elementAt(0);
                    row2_elements.addElement(element);
                    if (row1_elements.contains(element))
                        shared_elements.addElement(element);
                }
                if (shared_elements.isEmpty() && negate_pattern_holds)
                    score--;
                if (!shared_elements.isEmpty() && pattern_holds)
                    score--;
            }
            return score;
        }

        if (c1.arity==c2.arity || c1.arity>2 || c2.arity>2)
            return 0;

        if (c1.object_type!=c2.object_type)
            return 0;

        if (c1.isGeneralisationOf(c2)==0 || c2.isGeneralisationOf(c1)==0)
            return 0;

        // One is of arity one, the other is of arity two.

        Vector columns_to_check = new Vector();
        Concept object_concept = new Concept();
        Concept subobject_concept = new Concept();

        if (c1.arity<c2.arity)
        {
            object_concept = c1;
            subobject_concept = c2;
        }
        else
        {
            object_concept = c2;
            subobject_concept = c1;
        }

        // Case one - an exists step will occur with the subobject concept //
        // This means that we can re-use code. Simply make the concept which would //
        // occur after the exists step, and pass it back through this function //

        Concept new_object_concept = new Concept();
        for (int i=0; i<subobject_concept.datatable.size(); i++)
        {
            Row row = (Row)subobject_concept.datatable.elementAt(i);
            Tuples tuples = new Tuples();
            if (!row.tuples.isEmpty())
                tuples.addElement(new Vector());
            new_object_concept.datatable.addElement(new Row(row.entity,tuples));
        }
        new_object_concept.arity = 1;
        Vector both_concepts = new Vector();
        both_concepts.addElement(object_concept);
        both_concepts.addElement(new_object_concept);
        score = patternScore(both_concepts, all_concepts, entity_list, non_entity_list);
        if (score == non_entity_list.size())
            return score;

        // Case two - at least one subobjects for each entity to learn is an object from
        // the primary table. Or the same number of subobjects are found for each entity
        // to learn.

        boolean exists_pattern_holds = true;
        boolean negate_pattern_holds = true;
        boolean number_pattern_holds = true;
        boolean split_pattern_holds = true;

        // First set up the set of entities which appear as entities in the object datatable.

        Vector object_entities = new Vector();
        for (int i=0; i<object_concept.datatable.size(); i++)
        {
            Row row = (Row)object_concept.datatable.elementAt(i);
            if (!row.tuples.isEmpty())
                object_entities.addElement("["+row.entity+"]");
        }

        int i=0;
        int same_set_size = 0;
        Vector same_set = new Vector();

        while ((exists_pattern_holds ||
                negate_pattern_holds ||
                number_pattern_holds ||
                split_pattern_holds) && i<entity_list.size())
        {
            String entity = (String)entity_list.elementAt(i);
            Row row = subobject_concept.calculateRow(all_concepts,entity);
            Vector this_set = new Vector();
            for (int j=0; j<row.tuples.size(); j++)
            {
                Vector tuple = (Vector)row.tuples.elementAt(j);
                String tuple_string = tuple.toString();
                if (object_entities.contains(tuple_string))
                    this_set.addElement(tuple_string);
            }
            if (i==0)
            {
                same_set = (Vector)this_set.clone();
                same_set_size = this_set.size();
            }
            if (i>0)
            {
                int k=0;
                while (k<same_set.size())
                {
                    String tuple = (String)same_set.elementAt(k);
                    if (!this_set.contains(tuple))
                        same_set.removeElementAt(k);
                    else
                        k++;
                }
            }
            if(i>0 && same_set_size!=this_set.size())
                number_pattern_holds = false;
            if (this_set.size()==0)
                exists_pattern_holds = false;
            if (this_set.size()>0)
                negate_pattern_holds = false;
            if (same_set.isEmpty())
                split_pattern_holds = false;
            i++;
        }

        if (!exists_pattern_holds && !negate_pattern_holds && !number_pattern_holds)
            return 0;

        int number_score = 0;
        int exists_score = 0;
        int negate_score = 0;
        int split_score = 0;
        if (number_pattern_holds)
            number_score = non_entity_list.size();
        if (exists_pattern_holds)
            exists_score = non_entity_list.size();
        if (negate_pattern_holds)
            negate_score = non_entity_list.size();
        if (split_pattern_holds)
            split_score = non_entity_list.size();

        for (i=0;i<non_entity_list.size();i++)
        {
            Row row = subobject_concept.calculateRow(all_concepts,(String)non_entity_list.elementAt(i));
            Vector this_set = new Vector();
            for (int j=0; j<row.tuples.size(); j++)
            {
                Vector tuple = (Vector)row.tuples.elementAt(j);
                if (object_entities.contains(tuple.toString()))
                    this_set.addElement(tuple.toString());
                if (same_set.contains(tuple.toString()))
                    same_set.removeElement(tuple.toString());
            }

            if (this_set.isEmpty())
                negate_score--;
            else
                exists_score--;
            if (this_set.size()==same_set_size)
                number_score--;
            if (same_set.isEmpty())
                split_score = 0;
        }

        score = negate_score;
        if (exists_score > score)
            score = exists_score;
        if (number_score > score)
            score = number_score;
        if (split_score > score)
            score = split_score;
        return score;
    }

    public Vector composeTuples(Vector p_tuple, Vector s_tuple, Vector s_params)
    {
        Vector output = new Vector();
        for(int i=0;i<s_params.size();i++)
        {
            int p_pos;
            if (i<p_tuple.size()) p_pos = i; else p_pos=-1;

            try {
                int s_pos = new Integer((String)s_params.elementAt(i)).intValue()-1;
                if (p_pos >=0) output.addElement(p_tuple.elementAt(p_pos));
                else output.addElement(s_tuple.elementAt(s_pos));
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        return output;
    }
}
