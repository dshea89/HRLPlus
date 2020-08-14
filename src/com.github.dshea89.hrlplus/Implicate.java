package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;

/** A class representing a conjecture, where a set of specifications implies another
 * specification. ie. conjectures of the form: P(a,b) & Q(a,b,c) -> R(a,c).
 *
 * @author Simon Colton, started 11th December 1999
 * @version 1.0
 */

public class Implicate extends Conjecture implements Serializable
{
    /** If the implicate is made from a non-existence conjecture, then the left hand
     * side concept may not have the correct arity to be able to state the goal specification.
     * Hence we record the types of the non-existent concept this implicate comes from.
     */

    public Vector overriding_types = new Vector();

    /** The premise concept.
     */

    public Concept premise_concept = new Concept();

    /** The right hand side specification
     * @see Specification
     */

    public Specification goal = new Specification();

    /** The simple constructor
     */

    public Implicate()
    {
    }

    /** Constructs an Implicate object with the two given specifications. The set of
     * premises is taken to be the single specification input.
     */

    public Implicate(Concept concept, Specification rhs, Step construction_step)
    {
        premise_concept = concept;
        goal = rhs;
        type = "implicate";
        step = construction_step;
        applicability = concept.applicability;
        arity = concept.arity;
        if (goal.is_entity_instantiations)
            involves_instantiation = true;
        if (premise_concept.is_entity_instantiations)
            involves_instantiation = true;
        object_type = concept.object_type;
    }

    /** This constructs an implicate conjecture with the first set of specifications
     * as the premises and the second one as the goal.
     */

    public Implicate(Vector types, Vector premise_specifications, Specification goal_spec, String domain)
    {
        premise_concept = new Concept();
        premise_concept.specifications = premise_specifications;
        premise_concept.setSkolemisedRepresentation();
        premise_concept.types = types;
        premise_concept.domain = domain;
        goal = goal_spec;
        arity = types.size();
        if (goal.is_entity_instantiations)
            involves_instantiation = true;
        if (premise_concept.is_entity_instantiations)
            involves_instantiation = true;
        object_type = domain;
    }

    /** Returns the domain of the left hand concept.
     */

    public String getDomain()
    {
        return premise_concept.domain;
    }

    /** This checks whether this implicate is the same as the given one.
     * It must have the same premises and goal.
     */

    public boolean equals(Implicate other_implicate)
    {
        if (!(premise_concept==other_implicate.premise_concept))
            return false;
        if (!(goal==other_implicate.goal))
            return false;
        return true;
    }

    /** This checks whether this implicate subsumes the given one.
     * i.e. the this one says something stronger (from which this one follows).
     */

    public boolean subsumes(Implicate other_implicate, SpecificationHandler spec_handler)
    {
        if (this.writeConjecture("otter").equals(other_implicate.writeConjecture("otter")))
            return true;
        if (goal==other_implicate.goal &&
                premise_concept.isGeneralisationOf(other_implicate.premise_concept)>=0)
            return true;

        if (spec_handler.leftSkolemisedSubsumesRight(this, other_implicate, false))
        {
            System.out.println(this.writeConjecture("otter"));
            System.out.println("subsumes");
            System.out.println(other_implicate.writeConjecture("otter"));
            System.out.println("-----------------");
            return true;
        }
        return false;
    }

    /** This writes the goal specification.
     */

    public String writeGoal(String language)
    {
        Concept dummy_concept = new Concept();
        dummy_concept.specifications.addElement(goal);
        dummy_concept.types = premise_concept.types;
        if (!overriding_types.isEmpty())
            dummy_concept.types = overriding_types;
        Vector letters =
                premise_concept.definition_writer.lettersForTypes(premise_concept.types,language,new Vector());
        return dummy_concept.writeDefinition(language);
    }

    /** This writes the implicate but without the initial universal quantification.
     */

    public String writeConjectureWithoutUniversalQuantification(String language)
    {
        boolean old_simplify = simplify_definitions;
        simplify_definitions = true;
        String output = writeConjectureMain(language, false, true);
        simplify_definitions = old_simplify;
        Hashtable letters_hashtable = new Hashtable();
        Vector letters_already = new Vector();
        while (output.indexOf("@")>=0)
        {
            int next_pos = output.indexOf("@");
            String letter_string = output.substring(next_pos, next_pos+3);
            String letter_to_use = (String)letters_hashtable.get(letter_string);
            if (letter_to_use==null)
            {
                String[] all_letters = {"","a","b","c","d","e","f","g","h",
                        "i","j","k","l","m","n","o","p","q",
                        "r","s","t","u","v","w","x","y","z"};

                String[] all_upper_letters = {"","A","B","C","D","E","F","G","H",
                        "I","J","K","L","M","N","O","P","Q",
                        "R","S","T","U","V","W","X","Y","Z"};

                int pos = 0;
                boolean found = false;
                for (int j=0; j<all_letters.length && !found; j++)
                {
                    for (int k=1; k<all_letters.length && !found; k++)
                    {
                        letter_to_use = all_letters[j]+all_letters[k];
                        if (language.equals("tptp"))
                            letter_to_use = all_upper_letters[j]+all_upper_letters[k];
                        if (!letters_already.contains(letter_to_use))
                            found=true;
                    }
                }
                letters_hashtable.put(letter_string, letter_to_use);
                letters_already.addElement(letter_to_use);
            }
            output = output.substring(0,next_pos)+letter_to_use+output.substring(next_pos+3, output.length());
        }
        return output;
    }

    /** This writes the implicate.
     */

    public String writeConjecture(String language)
    {
        return writeConjectureMain(language, true, false);
    }

    private String writeConjectureMain(String language, boolean include_universal, boolean with_at_signs)
    {
        Concept dummy_concept = new Concept();
        dummy_concept.specifications.addElement(goal);
        dummy_concept.types = premise_concept.types;
        if (!overriding_types.isEmpty())
            dummy_concept.types = overriding_types;
        Vector letters =
                premise_concept.definition_writer.lettersForTypes(premise_concept.types,language,new Vector());

        boolean old_b1 = dummy_concept.definition_writer.remove_existence_variables;
        boolean old_b2 = premise_concept.definition_writer.remove_existence_variables;

        if (simplify_definitions)
        {
            dummy_concept.definition_writer.remove_existence_variables = true;
            premise_concept.definition_writer.remove_existence_variables = true;
        }
        String goal_string = "";
        String premises_string = "";
        if (with_at_signs)
        {
            goal_string = dummy_concept.writeDefinitionWithAtSigns(language);
            premises_string = premise_concept.writeDefinitionWithAtSigns(language);
        }
        else
        {
            goal_string = dummy_concept.writeDefinition(language);
            premises_string = premise_concept.writeDefinition(language);
        }

        if (simplify_definitions)
        {
            dummy_concept.definition_writer.remove_existence_variables = old_b1;
            premise_concept.definition_writer.remove_existence_variables = old_b2;
        }

        if (language.equals("prolog") && premises_string.equals(""))
        {
            String type = (String)premise_concept.types.elementAt(0);
            premises_string = type + "(A)";
        }
        String output = "";
        if (language.equals("ascii"))
            output = writeAsciiConjecture(letters, premises_string, goal_string, include_universal);
        if (language.equals("otter"))
            output = writeOtterConjecture(letters, premises_string, goal_string, include_universal);
        if (language.equals("tptp"))
            output = writeTPTPConjecture(letters, premises_string, goal_string, include_universal);
        if (language.equals("prolog"))
            output = writePrologConjecture(letters, premises_string, goal_string, include_universal);

        dummy_concept.definition_writer.remove_existence_variables = old_b1;
        premise_concept.definition_writer.remove_existence_variables = old_b2;

        return output;
    }

    private String writeAsciiConjecture(Vector letters, String premises_string,
                                        String goal_string, boolean include_universal)
    {
        String  output = " for all ";

        for (int i=0; i<letters.size(); i++)
            output = output + (String)letters.elementAt(i) + " ";
        return output + ": " + premises_string + " -> " + goal_string;
    }

    private String writeOtterConjecture(Vector letters, String lh_string,
                                        String rh_string, boolean include_universal)
    {
        String output = "";
        if (lh_string.equals("") && rh_string.equals(""))
            return "";
        if (rh_string.equals(""))
            return writeOtterConjecture(letters, rh_string, lh_string, include_universal);
        if ((letters.size()>1 || use_entity_letter) && include_universal)
            output = output + "all ";
        int start_pos = 1;
        if (use_entity_letter)
            start_pos = 0;
        for (int i=start_pos; i<letters.size() && include_universal; i++)
            output = output + (String)letters.elementAt(i) + " ";
        if ((letters.size()>1 || use_entity_letter) && include_universal)
            output = output + "(";
        if (!lh_string.equals("") && !rh_string.equals(""))
            output = output + "((" + lh_string + ") -> (" + rh_string + "))";
        if (lh_string.equals(""))
            output = output + "(" + rh_string + ")";
        if ((letters.size()>1 || use_entity_letter) && include_universal)
            output = output + ")";
        return output;
    }

    private String writePrologConjecture(Vector letters, String lh_string,
                                         String rh_string, boolean include_universal)
    {
        String output = "";
        if (lh_string.equals("") && rh_string.equals(""))
            return "";
        if (rh_string.equals(""))
            return writeOtterConjecture(letters, rh_string, lh_string, include_universal);
        output = rh_string + " :- " + lh_string + ".";
        if (lh_string.equals("") && !rh_string.equals(""))
            output = rh_string + ".";
        return output;
    }

    private String writeTPTPConjecture(Vector letters, String lh_string,
                                       String rh_string, boolean include_universal)
    {
        if (lh_string.equals("") && rh_string.equals(""))
            return "";
        String letters_string = "[";
        for (int i=1; i<letters.size()-1; i++)
            letters_string=letters_string + (String)letters.elementAt(i)+",";
        if (letters.size()>1)
            letters_string=letters_string+(String)letters.elementAt(letters.size()-1);
        letters_string=letters_string + "]";
        String output = "";
        boolean printed_with_an_empty_side = false;
        if (lh_string.trim().equals("") || lh_string.trim().equals("()") || lh_string.trim().equals("(())"))
        {
            if (letters_string.equals("[]"))
                output = "input_formula(conjecture"+id+",conjecture,(("+rh_string+"))).";
            else
                output = "input_formula(conjecture"+id+",conjecture,(! "+letters_string+
                        " : ("+rh_string+"))).";
            printed_with_an_empty_side = true;
        }

        if (rh_string.trim().equals("") || rh_string.trim().equals("()") || rh_string.trim().equals("(())"))
        {
            if (letters_string.equals("[]"))
                output = "input_formula(conjecture"+id+",conjecture,(("+lh_string+"))).";
            else
                output = "input_formula(conjecture"+id+",conjecture,(! "+letters_string+
                        " : ("+lh_string+"))).";
            printed_with_an_empty_side = true;
        }

        if (!printed_with_an_empty_side)
        {
            if (letters_string.equals("[]"))
                output = "input_formula(conjecture"+id+",conjecture,((("+lh_string+")) => ("+rh_string+"))).";
            else
                output = "input_formula(conjecture"+id+",conjecture,(! "+letters_string+
                        " : (("+lh_string+" ) => ("+rh_string+")))).";
        }
        return output;
    }

    /** This returns possible prime implicates of this implicate conjecture.
     */

    public Vector possiblePrimeImplicates()
    {
        Vector output = new Vector();
        Vector present = new Vector();
        present.addElement(new Vector());
        Vector strings = new Vector();
        strings.addElement(writeConjectureWithoutUniversalQuantification("otter"));
        for (int pos = 0; pos<premise_concept.specifications.size()-1; pos++)
        {
            int size=present.size();
            for (int i=0; i<size; i++)
            {
                Vector specs = (Vector)present.elementAt(0);
                present.removeElementAt(0);
                int last_pos = 0;
                if (!specs.isEmpty())
                {
                    Specification last_spec = (Specification)specs.lastElement();
                    last_pos = premise_concept.specifications.indexOf(last_spec);
                }
                for (int j=last_pos+1; j<premise_concept.specifications.size(); j++)
                {
                    Specification spec = (Specification)premise_concept.specifications.elementAt(j);
                    Vector new_specs = (Vector)specs.clone();
                    new_specs.addElement(spec);
                    Implicate prime_implicate =
                            new Implicate(premise_concept.types, new_specs, goal, premise_concept.domain);
                    String pi_string = prime_implicate.writeConjectureWithoutUniversalQuantification("otter");
                    if (!pi_string.equals("") && !strings.contains(pi_string))
                    {
                        output.addElement(prime_implicate);
                        strings.addElement(pi_string);
                    }
                    present.addElement(new_specs);
                }
            }
        }
        return output;
    }
}
