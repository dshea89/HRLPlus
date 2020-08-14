package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;

/** A class representing the Arithmetic production rule. This takes two concepts which
 * are functions returning a numerical output and performs arithmetic on their values, for
 * instance, addition, subtraction, multiplication, etc.
 *
 * @author Simon Colton, started 31st January 2003
 * @version 1.0 */

public class Arithmeticb extends ProductionRule implements Serializable
{
    /** Whether or not the rule can use a concept with itself. The default is no.
     */

    public boolean use_same_concept_twice = false;

    /** An exists production rule which will help produce the specifications
     * for the concepts produced by this rule.
     */

    public Exists exists = new Exists();

    /** A compose production rule which will help produce the specifications
     * for the concepts produced by this rule.
     */

    public Compose compose = new Compose();

    /** Which operators are avialable to this production rule.
     * Note that d is the Dirichlet operator (number theory).
     */

    public String operators_allowed = "+*d";

    /** Returns true as this is a binary production rule.
     */

    public boolean isBinary()
    {
        return true;
    }

    /** Whether or not this produces cumulative concepts.
     * @see Concept
     */

    public boolean is_cumulative = false;

    /** Returns "arithmeticb" as that is the name of this production rule.
     */

    public String getName()
    {
        return "arithmeticb";
    }

    /** Given a vector of two concepts this will return default parameters
     * if they are both function concepts producing an integer for every row in
     * the datatable. Otherwise it will return an empty list.
     */

    public Vector allParameters(Vector old_concepts, Theory theory)
    {

        Concept old_concept1 = (Concept)old_concepts.elementAt(0);
        Concept old_concept2 = (Concept)old_concepts.elementAt(1);

        if (old_concept1==old_concept2 && !use_same_concept_twice)
            return new Vector();

        if (old_concept1.arity!=2 || old_concept2.arity!=2)
            return new Vector();
        if (!old_concept1.types.elementAt(1).equals("integer") ||
                !old_concept2.types.elementAt(1).equals("integer"))
            return new Vector();
        // if (!sharedHistoryIsOK(old_concept1, old_concept2)) //friday
        // return new Vector();

        for (int i=0; i<old_concept1.datatable.size(); i++)
        {
            if (((Row)(old_concept1.datatable.elementAt(i))).tuples.isEmpty())
                return new Vector();
        }
        for (int i=0; i<old_concept2.datatable.size(); i++)
        {
            if (((Row)(old_concept2.datatable.elementAt(i))).tuples.isEmpty())
                return new Vector();
        }
        boolean no_params = true;
        boolean c1_ok = false;
        boolean c2_ok = false;
        Vector output = new Vector();
        for (int i=0; i<old_concept1.functions.size(); i++)
        {
            Function f = (Function)old_concept1.functions.elementAt(i);
            if (f.output_columns.toString().equals("[1]"))
                c1_ok = true;
        }
        for (int i=0; i<old_concept2.functions.size(); i++)
        {
            Function f = (Function)old_concept2.functions.elementAt(i);
            if (f.output_columns.toString().equals("[1]"))
                c2_ok = true;
        }
        if (c1_ok && c2_ok)
            no_params = false;

        if (old_concept1.arity!=2 || !old_concept1.types.elementAt(1).equals("integer")
                || old_concept2.arity!=2 || !old_concept2.types.elementAt(1).equals("integer"))
            no_params = true;
        if (!no_params)
        {
            for (int i=0; i<operators_allowed.length(); i++)
            {
                String op = operators_allowed.substring(i,i+1);
                //	    if (!(op.equals("+") || op.equals("*")) || old_concept1.position_in_theory < old_concept2.position_in_theory)
                //	      {
                Vector param = new Vector();
                param.addElement(op);
                output.addElement(param);
                //	      }
            }
        }
        return output;
    }

    /** This produces the new specifications for concepts output using the arithmeticb production
     * rule.
     */

    public Vector newSpecifications(Vector input_concepts, Vector input_parameters,
                                    Theory theory, Vector new_functions)
    {
        String operator = (String)input_parameters.elementAt(0);
        Concept old_concept1 = (Concept)input_concepts.elementAt(0);
        Concept old_concept2 = (Concept)input_concepts.elementAt(1);
        Vector v1 = new Vector();
        Vector v2 = new Vector();
        v1.addElement(old_concept1);
        v2.addElement(old_concept2);

        Vector p = new Vector();
        p.addElement("1");
        p.addElement("0");
        p.addElement("2");
        Vector composed_specs = compose.newSpecifications(input_concepts, p, theory, new Vector());

        Relation relation = new Relation();
        Vector permutation = new Vector();
        permutation.addElement("1");
        permutation.addElement("2");
        permutation.addElement("3");
        if (!operator.equals("d"))
            relation.addDefinition("abc", "@c@=@a@"+operator+"@b@", "ascii");
        else
            relation.addDefinition("abc", "@c@=(f"+old_concept1.id+",f"+old_concept2.id+")(@a@,@b@)", "ascii");
        relation.name = operator + input_parameters.toString();
        Specification op_specification = new Specification();
        Vector diff_perm = new Vector();
        diff_perm.addElement("0");
        diff_perm.addElement("1");
        diff_perm.addElement("2");

        op_specification.addRelation(diff_perm, relation);
        op_specification.permutation = (Vector)permutation.clone();

        composed_specs.addElement(op_specification);
        Concept dummy_concept = new Concept();
        dummy_concept.types.addElement(old_concept1.types.elementAt(0));
        dummy_concept.types.addElement("integer");
        dummy_concept.types.addElement("integer");
        dummy_concept.types.addElement("integer");
        dummy_concept.arity = 4;
        dummy_concept.specifications = composed_specs;
        Vector ec = new Vector();
        ec.addElement(dummy_concept);
        Vector ev = new Vector();
        ev.addElement("1");
        ev.addElement("2");
        Vector exists_specs = exists.newSpecifications(ec, ev, theory, new Vector());

        Specification multiple_specification = (Specification)exists_specs.elementAt(0);

        Vector f_in_cols = new Vector();
        f_in_cols.addElement("0");
        Vector f_out_cols = new Vector();
        f_out_cols.addElement("1");
        Function size_function = new Function("fa"+Integer.toString(number_of_new_functions++),
                f_in_cols, f_out_cols);
        new_functions.addElement(size_function);
        multiple_specification.functions.addElement(size_function);
        return exists_specs;
    }

    /** This produces the new datatable from the given datatable, using the parameters
     * specified.
     */

    public Datatable transformTable(Vector input_datatables, Vector input_concepts,
                                    Vector parameters, Vector all_concepts)
    {
        Datatable old_datatable1 = (Datatable)input_datatables.elementAt(0);
        Datatable old_datatable2 = (Datatable)input_datatables.elementAt(1);
        Concept old_concept1 = (Concept)input_concepts.elementAt(0);
        Concept old_concept2 = (Concept)input_concepts.elementAt(1);
        Datatable output = new Datatable();
        String operator = (String)parameters.elementAt(0);

        for (int i=0; i<old_datatable1.size(); i++)
        {
            Row row_a;
            Row row_b;
            try {
                row_a = (Row) old_datatable1.elementAt(i);
                row_b = (Row) old_datatable2.elementAt(i);
            } catch (ArrayIndexOutOfBoundsException ignored) {
                continue;
            }
            if (!row_a.tuples.isEmpty() && !row_b.tuples.isEmpty())
            {
                String num_a = (String)((Vector)row_a.tuples.elementAt(0)).elementAt(0);
                String num_b = (String)((Vector)row_b.tuples.elementAt(0)).elementAt(0);
                Tuples new_tuples = new Tuples();
                Vector new_tuple = new Vector();
                if (num_a.equals("infinity") || num_b.equals("infinity"))
                    new_tuple.addElement("infinity");
                else
                {
                    double dec_a = (new Double(num_a)).doubleValue();
                    double dec_b = (new Double(num_b)).doubleValue();
                    double new_dec = 0;
                    if (operator.equals("+"))
                        new_dec = dec_a + dec_b;
                    if (operator.equals("*"))
                        new_dec = dec_a * dec_b;
                    if (operator.equals("-"))
                        new_dec = dec_a - dec_b;
                    if (operator.equals("/"))
                        new_dec = dec_a / dec_b;
                    if (operator.equals("d"))
                        new_dec = dirichletConvolution(old_concept1, old_concept2, i+1, all_concepts);
                    String new_dec_string = Double.toString(new_dec);
                    if (new_dec_string.indexOf(".0")==new_dec_string.length()-2)
                        new_dec_string = new_dec_string.substring(0,new_dec_string.indexOf(".0"));
                    new_tuple.addElement(new_dec_string);
                }
                new_tuples.addElement(new_tuple);
                Row new_row = new Row(row_a.entity, new_tuples);
                output.addElement(new_row);
            }
        }
        return output;
    }

    /** Calculates the dirichlet convultion of two functions.
     */

    public int dirichletConvolution(Concept f, Concept g, int n, Vector all_concepts)
    {
        int sum = 0;
        for (int d=1; d<=n/2; d++)
        {
            double k = n/d;
            if(k==Math.floor(k))
            {
                int n_over_d = (new Double(k)).intValue();
                int addon = calculateFunction(f,d,all_concepts) * calculateFunction(g,n_over_d,all_concepts);
                sum = sum + addon;
            }
        }
        sum = sum + calculateFunction(f, n, all_concepts) * calculateFunction(g, 1, all_concepts);
        return sum;
    }

    int calculateFunction(Concept f, int input, Vector all_concepts)
    {
        try
        {
            Row row = f.calculateRow(all_concepts, (new Integer(input)).toString());
            String val = (String)((Vector)row.tuples.elementAt(0)).elementAt(0);
            Integer i = new Integer(val);
            return i.intValue();
        }
        catch(Exception e){System.out.println(e); System.out.println(f.id); System.out.println(input);
        }
        return 0;
    }

    /** Returns the types of the objects in the columns of the new datatable.
     */

    public Vector transformTypes(Vector old_concepts, Vector parameters)
    {
        Vector old_types = (Vector)((Concept)old_concepts.elementAt(0)).types.clone();
        return old_types;
    }

    /** This assigns a score to a concept depending on whether the
     * production rule can see any likelihood of a pattern.
     */

    public int patternScore(Vector concept_list, Vector all_concepts,
                            Vector entity_list, Vector non_entity_list)
    {
        int score = 0;
        return score;
    }
}
