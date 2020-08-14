package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import java.lang.String;
import java.io.Serializable;

/** A class representing a mathematical concept in the theory. It consists of a datatable
 * for the concept and a set of specifications for the concept.
 * @author Simon Colton, started 11th December 1999
 * @version 1.0
 */

public class Concept extends TheoryConstituent implements Serializable
{
    /** The skolemised representation of this concept.
     */

    public SkolemisedRepresentation skolemised_representation = new SkolemisedRepresentation();

    /** The specification strings obtained by using Otter's Knuth Bendix completion.
     */

    public Vector specification_strings_from_knuth_bendix = new Vector();

    /** A vector of user-given markers for this concept, so that the user (or the react
     * mechanism) can find all those concepts with a particular marker.
     */

    public Vector markers = new Vector();

    /** The set of analogy puzzles which have been generated for this concept.
     */

    public Vector analogy_puzzles = new Vector();

    /** The set of next-in-sequence puzzles which have been generated for this concept.
     */

    public Vector nis_puzzles = new Vector();

    /** The set of odd-one-out puzzles which have been generated for this concept.
     */

    public Vector ooo_puzzles = new Vector();

    /** The set of implication conjectures involving this concept.
     */

    public Vector implications = new Vector();

    /** The set of specifications implied in addition when the properties of this
     * concept are satisfied.
     */

    public Vector implicates = new Vector();

    /** Whether or not to use the top substitutable concept instead of this concept.
     */

    public boolean use_top_substitutable = true;

    /** The set of concepts which can be substituted for this concept,
     * for various reasons.
     */

    public Vector substitutable_concepts = new Vector();

    /** The equivalence conjecture which maps this (conjecture to be equivalent)
     * concept to the concept it is conjectured to be equivalent with.
     */

    public Conjecture equivalence_conjecture = new Conjecture();

    /** Whether or not this is a (possibly single) disjunction or conjunction
     * [or combo] of single_entity concepts.
     */

    public boolean is_entity_instantiations = false;

    /** Whether or not this is a single entity concept (e.g., [a] : a=4).
     */

    public boolean is_single_entity = false;

    /** The definition_writer object for this concept.
     */

    public DefinitionWriter definition_writer = new DefinitionWriter();

    /** The step number when this concept was constructed
     */

    public int step_number = 0;

    /** The vector of concepts which are nearly equivalent to this concept
     * (statistically so).
     */

    public SortableVector near_equivalences = new SortableVector();

    /** The set of near_implication conjectures involving this
     *  concept. alisonp
     */

    public SortableVector near_implications = new SortableVector();

    /** The set of relevant implicates for this concept.
     * This is the set of implicate conjectures for which this concept
     * implies the left hand side of the implicate, hence the right
     * hand side is also true. This remains empty until the method:
     * getRelevantImplicates is called.
     */

    public SortableVector relevant_implicates = new SortableVector();

    /** The set of relevant equivalences for this concept. This is the
     * set of equivalence conjectures involving this concept.
     */

    public SortableVector relevant_equivalences = new SortableVector();

    /** The concept which supplies the objects of interest for this concept.
     */

    public Concept entity_concept = null;

    /** Whether or not the user has supplied java code for calculations with this
     * concept.
     */

    public boolean is_java_enabled = false;

    /** The type of objects in the datatable (i.e. the first column of the types).
     */

    public String object_type;

    /** Whether or not this concept contains a list of objects of interest (with no
     * additional properties).
     */

    public boolean is_object_of_interest_concept = false;


    /** A copy of the user functions object from the theory (which calculates all the
     * user given java functions.
     */

    public UserFunctions user_functions = new UserFunctions();

    /** The time (in milliseconds) taken to construct the datatable for this concept.
     */

    public long datatable_construction_time = 0;

    /** Whether or not this concept has the categorisation specified by the user.
     */

    public boolean has_required_categorisation = false;

    /** The set of functions for this concept, i.e. the columns which can be considered
     * input columns and those which can be considered output columns.
     * @see Function
     */

    public Vector functions = new Vector();

    /** A flag to say that this concept should not be used in any theory formation steps.
     */

    public boolean dont_develop = false;

    /** The alternative concepts (from equivalence conjectures)
     */

    public Vector conjectured_equivalent_concepts = new Vector();

    /** A vector of alternative ways to construct this concept.
     */

    public Vector conjectured_equivalent_constructions = new Vector();

    /** The other concepts which have been constructed on the
     * way to this concept.
     */

    public Vector ancestors = new Vector();

    /** The ids of the other concepts which have been constructed on the
     * way to this concept.
     */

    public Vector ancestor_ids = new Vector();

    /** The parent concepts of this concept.
     */

    public Vector parents = new Vector();

    /** The children concepts of this concept.
     */

    public Vector children = new Vector();

    /** The arity (number of columns in datatable) for this concept.
     */

    public int arity = 0;

    /** The domain from which this concept is taken.
     */

    public String domain = "";

    /** Whether or not this concept is built from concepts from two or
     * more domains, i.e. whether it is cross domain or not.
     */

    public boolean is_cross_domain = false;

    /** Scores 1 if this is cross domain, 0 otherwise.
     *
     */

    public double cross_domain_score = 0;

    /** Whether or not this is a core concept supplied by the user.
     */

    public boolean is_user_given;

    /** The associated relation for this concept. Note that if the concept has been constructed
     * using the compose production rule, there will be no associated relation.
     */

    public Relation associated_relation;

    /** An additional datatable required to store the calculations performed on entities
     * outside of the set used in the theory (ie. when using the compose production rules,
     * other entities may need to be considered.
     */

    public Datatable additional_datatable = new Datatable();

    /** The complexity of this concept.
     */

    public int complexity = 0;

    /** The applicability of this concept (the proportion of entities which are #not# empty in
     * the datatable).
     */

    public double applicability = 0;

    /** The positive applicability of this concept (the proportion of entities in the
     * "positives" category of the gold standard categorisation
     * which are #not# empty in the datatable).
     */

    public double positive_applicability = 0;

    /** The negative applicability of this concept (the proportion of entities in the
     * "positives" category of the gold standard categorisation
     * which are #not# empty in the datatable).
     */

    public double negative_applicability = 0;

    /** The normalised positive applicability of this concept (the proportion of entities in the
     * "positives" category of the gold standard categorisation
     * which are #not# empty in the datatable).
     */

    public double normalised_positive_applicability = 0;

    /** The normalised negative applicability of this concept (the proportion of entities in the
     * "positives" category of the gold standard categorisation
     * which are #not# empty in the datatable).
     */

    public double normalised_negative_applicability = 0;

    /** The predictive power of this concept
     * (positives.size() * positive_applicability + negatives.size() * negative_applicability
     */

    public double predictive_power = 0;

    /** The normalised predictive power of this concept.
     */

    public double normalised_predictive_power = 0;

    /** The normalised applicability of this concept.
     */

    public double normalised_applicability = 0;

    /** The coverage of this concept (the proportion of entities which are #not# empty in
     * the datatable).
     */

    public double coverage = 0;

    /** The normalised coverage of this concept.
     */

    public double normalised_coverage = 0;

    /** The sum of the scores from the equivalences conjectures involving this concept.
     */

    public double equiv_conj_sum = 0;

    /** The sum of the scores from the non-existence conjectures involving this concept.
     */

    public double ne_conj_sum = 0;

    /** The sum of the scores from the implicate conjectures involving this concept.
     */

    public double imp_conj_sum = 0;

    /** The sum of the scores from the prime implicate conjectures involving this concept.
     */

    public double pi_conj_sum = 0;

    /** The set of equivalence conjectures involving this concept.
     */

    public Vector equiv_conjectures = new Vector();

    /** The set of non-existence conjectures involving this concept.
     */

    public Vector ne_conjectures = new Vector();

    /** The set of implicate conjectures involving this concept.
     */

    public Vector imp_conjectures = new Vector();

    /** The set of prime implicate conjectures involving this concept.
     */

    public Vector pi_conjectures = new Vector();

    /** The score from the equivalence conjectures of this concept.
     */

    public double equiv_conj_score = 0;

    /** The normalised equivalence conjecture score of this concept.
     */

    public double normalised_equiv_conj_score = 0;

    /** The score from the prime implicate conjectures of this concept.
     */

    public double pi_conj_score = 0;

    /** The normalised equivalence conjecture score of this concept.
     */

    public double normalised_pi_conj_score = 0;

    /** The score from the non-existence conjectures of this concept.
     */

    public double ne_conj_score = 0;

    /** The normalised non-existence conjecture score of this concept.
     */

    public double normalised_ne_conj_score = 0;

    /** The score from the implicate conjectures of this concept.
     */

    public double imp_conj_score = 0;

    /** The normalised implicate conjecture score of this concept.
     */

    public double normalised_imp_conj_score = 0;

    /** The normalised equivalence conjecture number score of this concept.
     */

    public double normalised_equiv_conj_num = 0;

    /** The normalised equivalence conjecture number score of this concept.
     */

    public double normalised_pi_conj_num = 0;

    /** The normalised non-existence conjecture number score of this concept.
     */

    public double normalised_ne_conj_num = 0;

    /** The normalised implicate conjecture number score of this concept.
     */

    public double normalised_imp_conj_num = 0;

    /** The comprehensibility of this concept (1 over the complexity);
     */

    public double comprehensibility = 0;

    /** The normalised comprehensibility of this concept.
     */

    public double normalised_comprehensibility = 0;

    /** The invariance of the concept's categorisation with respect to a user-given
     * gold standard categorisation.
     */

    public double invariance = 0;

    /** The normalised invariance of the concept's categorisation with respect to a user-given
     * gold standard categorisation.
     */

    public double normalised_invariance_score = 0;

    /** The discrimination of the concept's categorisation with respect to a user-given
     * gold standard categorisation.
     */

    public double discrimination = 0;

    /** The normalised disrimination of the concept's categorisation with respect to a user-given
     * gold standard categorisation.
     */

    public double normalised_discrimination_score = 0;

    /** The novelty of this concept (inversely proportional to the number of other
     * concepts which share the categorisation this concept has).
     */

    public double novelty = 0;

    /** The normalised novelty of this concept.
     */

    public double normalised_novelty = 0;

    /** The parsimony of this concept (1 over the size of the datatable);
     */

    public double parsimony = 0;

    /** The normalised parsimony of this concept.
     */

    public double normalised_parsimony = 0;

    /** The score inherited from the parents of this concept.
     */

    public double parent_score = 0;

    /** The score from the children of this concept.
     */

    public double children_score = 0;

    /** The productivity of this concept proportion of steps leading to a concept.
     */

    public double productivity = 0;

    /** The normalised parsimony of this concept.
     */

    public double normalised_productivity = 0;

    /** The variety of this concept (1 over the size of the datatable);
     */

    public double variety = 0;

    /** The normalised_variety of this concept.
     */

    public double normalised_variety = 0;

    /** The tuple of old_concepts, production rule and parameters which were used to construct
     * this concept.
     */

    public Step construction = new Step();

    /** The categorisation this concept produces.
     */

    public Categorisation categorisation = new Categorisation();

    /** The datatable for this concept.
     */

    public Datatable datatable = new Datatable();

    /** Whether or not the concept is cumulative. If it is cumulative, the datatable row
     * for the nth entity requires information from the first n-1 rows.
     */

    public boolean is_cumulative = false;

    /** The name of the concept.
     */

    public String name = "";

    /** If this was the nth concept introduced (including core concepts), then the position in
     * the theory is n.
     */

    public int position_in_theory = 0;

    /** The specification of the relations applicable to this concept. This is a vector of
     * specifications.
     * @see Specification
     */

    public Vector specifications = new Vector();

    /** The types of elements in the columns for the concept's datatable
     */

    public Vector types = new Vector();

    /** The highlight value of this concept (1 if highlighted, 0 otherwise)
     */

    public double highlight_score = 0;

    /** The set of concepts which contain a subset of the specifications of this
     * concept.
     */

    public Vector generalisations = new Vector();

    /** The weighted sum of all the interestingness measures for this concept. Designed
     * to give some overall indication of the worth of the concept.
     */

    public double interestingness = 0;

    /** The number of theory formation steps in which this concept has been involved.
     */
    public double development_steps_num = 0;

    /** The normalised value for the number_of_steps_involved.
     */

    public double normalised_development_steps_num = 0;

    /** The number of theory formation steps in which this concept has been involved which
     * led to a new concept being introduced.
     */

    public double number_of_children = 0;

    /** The set of construction steps required to reproduce this concept.
     */

    public Vector construction_history = new Vector();

    /** The set of alternative id numbers for this concept. A concept might get an alternative
     * id number if it is repeated in another theory (from another agent perhaps).
     */

    public Vector alternative_ids = new Vector();

    /** Whether the concept has been invented just to cover specific
     entities (in which case no other definition is available). The
     entities (if they exist) are stored in entity_strings
     below. This flag is written for counterexample-barring -
     alisonp */

    public boolean concept_to_cover_entities = false;

    /** The vector of entity strings which this concept is to cover
     (used for counterexample-barring) - alisonp */

    public Vector entity_strings = new Vector();


    /** Whether the concept has been produced using Lakatos methods, and
     * if so which one.
     */

    public String lakatos_method = "no";


    /** This writes the concept definition in the given language, using the
     * letters supplied.
     */

    public String writeDefinition(String language, Vector letters)
    {
        return definition_writer.writeDefinition(this, language, letters);
    }

    /** This writes the concept definition in the given language, using the
     * letters supplied, putting @ signs around the letters.
     */

    public String writeDefinitionWithAtSigns(String language)
    {
        boolean old_val = definition_writer.surround_by_at_sign;
        definition_writer.surround_by_at_sign = true;
        String output = definition_writer.writeDefinition(this, language);
        definition_writer.surround_by_at_sign = old_val;
        return output;
    }

    /** This writes the concept definition in the given language.
     */

    public String writeDefinition(String language)
    {
        if(!concept_to_cover_entities)
            return definition_writer.writeDefinition(this, language);
        else
            return definition_writer.writeDefinitionForGivenEntities(this, language, entity_strings);
    }

    /** This writes the definition in ascii*/
    public String writeDefinition()
    {
        return this.writeDefinition("ascii");
    }

    /** Another method for writing the concept in ascii.
     */

    public String toString()
    {
        return writeDefinition();
    }



    /** This writes the concept definition in the given language and includes a vector
     * of letters at the start.
     */

    public String writeDefinitionWithStartLetters(String language)
    {
        return definition_writer.writeDefinitionWithStartLetters(this, language);
    }

    /** This calculates the row for a given entity. It also needs to be supplied the rest of the
     * concepts in the theory, so that it can backtrack and calculate values for the ancestors
     * of the concept.
     * @see Row
     */

    public Row calculateRow(Vector all_concepts, String entity)
    {
        Row output = new Row();
        output.entity = entity;
        int i=0;
        Row row = new Row();

        // First check to see if it's already in the datatable //

        while (i<datatable.size())
        {
            row = (Row)datatable.elementAt(i);
            if (row.entity.equals(entity)) break;
            i++;
        }
        if (i<datatable.size()) return row;

        // Next check the additional datatable //

        i=0;
        while (i<additional_datatable.size())
        {
            row = (Row)additional_datatable.elementAt(i);
            if (row.entity.equals(entity)) break;
            i++;
        }
        if (i<additional_datatable.size()) return row;

        // If the concept is user given and an object of interest, then return //

        if (is_object_of_interest_concept)
        {
            Tuples tuples = new Tuples();
            tuples.addElement(new Vector());
            Row new_row = new Row();
            new_row.entity = entity;
            new_row.tuples = tuples;
            additional_datatable.addElement(new_row);
            return (new_row);
        }

        // If the entity is of the wrong type, then return //
        // THIS STILL NEEDS DOING //

        // If it's user given, then we need to use the user-supplied code to
        // calculate a new row.

        if (is_user_given)
        {
            Tuples tuples = user_functions.calculateTuples(id, entity);
            Row new_row = new Row();
            new_row.entity = entity;
            new_row.tuples = tuples;
            additional_datatable.addElement(new_row);
            return new_row;
        }

        // For the non-user given concepts, calculate the row using the pr //

        boolean void_calc = false;
        Vector old_concepts = (Vector)construction.elementAt(0);
        ProductionRule pr = (ProductionRule)construction.elementAt(1);
        Vector parameters = (Vector)construction.elementAt(2);

        Vector old_datatables = new Vector();
        for(i=0;i<old_concepts.size();i++)
        {
            if (!void_calc)
            {
                Concept old_concept = (Concept)old_concepts.elementAt(i);
                if (!old_concept.domain.equals(domain))
                    old_datatables.addElement(old_concept.datatable);
                if (old_concept.domain.equals(domain))
                {
                    Datatable old_datatable = new Datatable();
                    if (is_cumulative)
                    {
                        int j=1;
                        int top = (new Integer(entity)).intValue();
                        while (j<top && !void_calc)
                        {
                            String ent_string = Integer.toString(j);
                            Row old_row = old_concept.calculateRow(all_concepts, ent_string);
                            if (old_row.is_void) void_calc = true;
                            old_datatable.addElement(old_row);
                            j++;
                        }
                    }
                    Row old_row = old_concept.calculateRow(all_concepts, entity);
                    if (old_row.is_void) void_calc = true;
                    old_datatable.addElement(old_row);
                    old_datatables.addElement(old_datatable);
                }
            }
        }
        if (!void_calc)
        {

            Datatable row_table =
                    pr.transformTable(old_datatables, old_concepts, parameters, all_concepts);
            output = (Row)row_table.rowWithEntity(entity);
        }
        if (void_calc) return (new Row(entity,"void"));
        additional_datatable.addElement(output);
        return output;
    }

    /** Returns the complexity of a new concept formed by composing this concept with the
     * other concept specified.
     */

    public int complexityWith(Concept other_concept)
    {
        int i=0;
        int output = ancestor_ids.size();
        while (i<other_concept.ancestor_ids.size())
        {
            if (!ancestor_ids.contains((String)other_concept.ancestor_ids.elementAt(i)))
                output++;
            i++;
        }
        return output;
    }

    /** This returns a string which details the id, name, definition, domain, types,
     * ancestors, construction history and datatable of the concept.
     */
    public String fullDetails(String[] attributes_in, int dp)
    {
        return fullDetails("ascii", attributes_in, dp);
    }

    public String fullDetails(String markup_type, String[] attributes_in, int dp)
    {
        Vector attributes = new Vector();
        for (int i=0; i<attributes_in.length; i++)
            attributes.addElement(attributes_in[i]);
        if (markup_type.equals("html"))
            return fullHTMLDetails(attributes, dp);
        if (markup_type.equals("ascii"))
            return fullASCIIDetails(attributes, dp);
        return "";
    }

    public String fullHTMLDetails(Vector attributes, int dp)
    {
        String output = "";

        // Identifier and definitions //

        if (attributes.contains("id"))
            output = output + "<b>" + id + "</b>";
        if (attributes.contains("name") && !name.equals(""))
        {
            output = output + " &nbsp;&nbsp;<b>\"" + name + "\"</b>";
        }

        if (attributes.contains("id") || attributes.contains("name"))
            output = output + "\n";

        int num_definitions = 0;
        String main_language = "";

        if (attributes.contains("simplified def"))
            num_definitions++;
        if (attributes.contains("ascii def"))
        {
            num_definitions++;
            main_language = "ascii";
        }
        if (attributes.contains("otter def"))
        {
            num_definitions++;
            main_language = "otter";
        }
        if (attributes.contains("prolog def"))
        {
            num_definitions++;
            main_language = "prolog";
        }
        if (attributes.contains("tptp def"))
        {
            num_definitions++;
            main_language = "tptp";
        }
        if (attributes.contains("skolemised def"))
            num_definitions++;
        String list_add = "<li>";
        if (num_definitions<=1)
            list_add = "";
        else
            main_language = "ascii";

        String def_string = "";

        if (attributes.contains("simplified def"))
        {
            boolean tmp_bool = definition_writer.remove_existence_variables;
            definition_writer.remove_existence_variables = true;
            def_string =
                    def_string + list_add + "<b>" + replaceLTForHTML(writeDefinitionWithStartLetters("otter")) + "</b>\n";
            definition_writer.remove_existence_variables = tmp_bool;
        }
        if (attributes.contains("ascii def"))
            def_string =
                    def_string + list_add + "<b>" + replaceLTForHTML(writeDefinitionWithStartLetters("ascii")) + "</b>\n";
        if (attributes.contains("otter def"))
            def_string =
                    def_string + list_add + "<b>" + replaceLTForHTML(writeDefinitionWithStartLetters("otter")) + "</b>\n";
        if (attributes.contains("prolog def"))
            def_string =
                    def_string + list_add + "<b>" + replaceLTForHTML(writeDefinitionWithStartLetters("prolog")) + "</b>\n";
        if (attributes.contains("tptp def"))
            def_string =
                    def_string + list_add + "<b>" + replaceLTForHTML(writeDefinitionWithStartLetters("tptp")) + "</b>\n";
        if (attributes.contains("skolemised def"))
            def_string =
                    def_string + list_add + "<b>" + replaceLTForHTML(writeSkolemisedRepresentation()) + "</b>\n";

        if (num_definitions>1)
            output = output + "<ul>" + def_string + "</ul>\n";
        else
        {
            if (num_definitions==1)
            {
                output = output +
                        "<br><table border=1 cellpadding=3 fgcolor=white bgcolor=\"#ffff99\"><tr><td><font color=blue>"+
                        def_string+"</font></td></tr></table><br>\n";
            }
        }

        if (!(output.equals("")))
            output = output + "<hr>\n";
        String snapshot = output;

        // Measures //

        output = output + "\n<table border=0><tr><td align=center><font size=4 color=red>Measures</font></td>";
        output = output + "<td align=center><font size=4 color=green>Examples</font></td></tr>\n";
        output = output + "<tr valign=top><td><table border=1><tr><td>Measure</td><td>Value</td><td>Normalised</td></tr>\n";

        if (attributes.contains("total score"))
            output = output + "<tr><td>Total score</td><td>" + decimalPlaces(interestingness, dp) + "</td><td></td></tr>\n";

        if (attributes.contains("arity"))
            output = output + "<tr><td>Arity</td><td>" + decimalPlaces(arity, dp) + "</td><td></td></tr>\n";
        if (attributes.contains("applicability"))
        {
            output=output+"<tr><td>Applicability</td><td>" + decimalPlaces(applicability, dp) +
                    "</td><td>" + decimalPlaces(normalised_applicability, dp) + "</td></tr>\n";
        }
        if (attributes.contains("child num"))
            output=output+"<tr><td>Children number</td><td>" + decimalPlaces(number_of_children, dp)
                    + "</td><td></td></tr>\n";
        if (attributes.contains("child score"))
        {
            output=output+"<tr><td>Children score</td><td>" + decimalPlaces(children_score, dp) +
                    "</td><td>" + decimalPlaces(children_score, dp) + "</td></tr>\n";
        }
        if (attributes.contains("comprehens"))
        {
            output=output+"<tr><td>Comprehensibility</td><td>" + decimalPlaces(comprehensibility, dp) +
                    "</td><td>" + decimalPlaces(normalised_comprehensibility, dp) + "</td></tr>\n";
        }
        if (attributes.contains("complexity"))
            output=output + "<tr><td>Complexity</td><td>" + decimalPlaces(complexity, dp) + "</td><td></td></tr>\n";
        if (attributes.contains("coverage"))
        {
            output=output+"<tr><td>Coverage</td><td>" + decimalPlaces(coverage, dp) +
                    "</td><td>" + decimalPlaces(normalised_coverage, dp) + "</td></tr>\n";
        }
        if (attributes.contains("devel steps"))
        {
            output=output+"<tr><td>Development steps</td><td>" + decimalPlaces(development_steps_num, dp) +
                    "</td><td>" + decimalPlaces(normalised_development_steps_num, dp) + "</td></tr>\n";
        }
        if (attributes.contains("discrimination"))
        {
            output=output+"<tr><td>Discrimination</td><td>" + decimalPlaces(discrimination, dp) +
                    "</td><td>" + decimalPlaces(normalised_discrimination_score, dp) + "</td></tr>\n";
        }
        if (attributes.contains("equiv conj score"))
        {
            output=output+"<tr><td>Equiv Conjecture score</td><td>" + decimalPlaces(equiv_conj_score, dp) +
                    "</td><td>" + decimalPlaces(normalised_equiv_conj_score, dp) + "</td></tr>\n";
        }
        if (attributes.contains("equiv conj num"))
        {
            output=output+"<tr><td>Equiv Conjecture num</td><td>" + decimalPlaces(equiv_conjectures.size(), dp) +
                    "</td><td>" + decimalPlaces(normalised_equiv_conj_num, dp) + "</td></tr>\n";
        }
        if (attributes.contains("highlight"))
            output=output+"<tr><td>Highlight</td><td>" + decimalPlaces(highlight_score, dp) + "</td><td></td></tr>\n";
        if (attributes.contains("ne conj score"))
        {
            output=output+"<tr><td>Non-exists Conjecture score</td><td>" + decimalPlaces(ne_conj_score, dp) +
                    "</td><td>" + decimalPlaces(normalised_ne_conj_score, dp) + "</td></tr>\n";
        }
        if (attributes.contains("ne conj num"))
        {
            output = output + "<tr><td>Non-exists Conjecture num</td><td>" +
                    decimalPlaces(ne_conjectures.size(), dp) +
                    "</td><td>" + decimalPlaces(normalised_ne_conj_num, dp) + "</td></tr>\n";
        }
        if (attributes.contains("neg applic"))
        {
            output=output+"<tr><td>Negative Applicability</td><td>" + decimalPlaces(negative_applicability, dp) +
                    "</td><td>" + decimalPlaces(normalised_negative_applicability, dp) + "</td></tr>\n";
        }
        if (attributes.contains("imp conj score"))
        {
            output=output+"<tr><td>Implicate Conjecture score</td><td>" + decimalPlaces(imp_conj_score, dp) +
                    "</td><td>" + decimalPlaces(normalised_imp_conj_score, dp) + "</td></tr>\n";
        }
        if (attributes.contains("imp conj num"))
        {
            output=output+"<tr><td>Implicate Conjecture num</td><td>" + decimalPlaces(imp_conjectures.size(), dp) +
                    "</td><td>" + decimalPlaces(normalised_imp_conj_num, dp) + "</td></tr>\n";
        }
        if (attributes.contains("invariance"))
        {
            output=output+"<tr><td>Invariance</td><td>" + decimalPlaces(invariance, dp) +
                    "</td><td>" + decimalPlaces(normalised_invariance_score, dp) + "</td></tr>\n";
        }
        if (attributes.contains("novelty"))
        {
            output=output+"<tr><td>Novelty</td><td>" + decimalPlaces(novelty, dp) +
                    "</td><td>" + decimalPlaces(normalised_novelty, dp) + "</td></tr>\n";
        }
        if (attributes.contains("parent score"))
        {
            output=output+"<tr><td>Parent score</td><td>" + decimalPlaces(parent_score, dp) +
                    "<td></td></td></tr>\n";
        }
        if (attributes.contains("parsimony"))
        {
            output=output+"<tr><td>Parsimony</td><td>" + decimalPlaces(parsimony, dp) +
                    "</td><td>" + decimalPlaces(normalised_parsimony, dp) + "</td></tr>\n";
        }
        if (attributes.contains("pi conj score"))
        {
            output=output+"<tr><td>PI Conjecture score</td><td>" + decimalPlaces(pi_conj_score, dp) +
                    "</td><td>" + decimalPlaces(normalised_pi_conj_score, dp) + "</td></tr>\n";
        }
        if (attributes.contains("pi conj num"))
        {
            output=output+"<tr><td>PI Conjecture num</td><td>" + decimalPlaces(pi_conjectures.size(), dp) +
                    "</td><td>" + decimalPlaces(normalised_pi_conj_num, dp) + "</td></tr>\n";
        }
        if (attributes.contains("pos applic"))
        {
            output=output+"<tr><td>Positive Applicability</td><td>" + decimalPlaces(positive_applicability, dp) +
                    "</td><td>" + decimalPlaces(normalised_positive_applicability, dp) + "</td></tr>\n";
        }
        if (attributes.contains("pred power"))
        {
            output=output+"<tr><td>Predictive Power</td><td>" +
                    decimalPlaces(predictive_power, dp) + "</td><td></td></tr>\n";
        }
        if (attributes.contains("productivity"))
        {
            output=output+"<tr><td>Productivity</td><td>" + decimalPlaces(productivity, dp) +
                    "</td><td>" + decimalPlaces(normalised_productivity, dp) + "</td></tr>\n";
        }
        if (attributes.contains("variety"))
        {
            output=output+"<tr><td>Variety</td><td>" + decimalPlaces(variety, dp) +
                    "</td><td>" + decimalPlaces(normalised_variety, dp) + "</td></tr>\n";
        }

        output = output + "</table></td><td>";

        if (attributes.contains("examples"))
            output = output + prettyPrintExamplesHTML(datatable)+"\n";

        if (attributes.contains("datatable") && attributes.contains("examples"))
            output = output + "\n<hr>\n";
        if (attributes.contains("datatable"))
        {
            output = output + "The datatable is:<pre>\n" +
                    datatable.toTable() + "</pre>\n";
        }

        if (attributes.contains("add examples"))
        {
            if (additional_datatable.isEmpty())
                output = output + "No additional entities for this concept.<br>\n";
            else
            {
                output = output + "The additional table is:\n";
                output = output + "<pre>" + additional_datatable.toTable() + "</pre>\n";
            }
        }

        output = output + "</td></tr></table><p>";

        if (!(snapshot.equals(output)))
            output=output+"<hr>\n";
        snapshot = output;

        // Construction details //

        output = output + "<b><u>Construction details</b></u><br>\n";

        if (attributes.contains("const step"))
        {
            if (is_user_given)
                output = output + "This concept was supplied by the user.<br>\n";
            else
                output = output + "Constructed with this step: " + construction.asString()+".<br>\n";
        }

        if (attributes.contains("ancestors"))
            output = output + "The ancestors of this concept are:" +  ancestor_ids.toString() + "<br>\n";

        if (attributes.contains("const time"))
        {
            String s_or_not = "s";
            String seconds = Long.toString(when_constructed).trim();
            if (seconds.equals("1"))
                s_or_not = "";
            output=output+"Constructed after "+Long.toString(when_constructed) +
                    " second"+s_or_not+", "+Integer.toString(step_number)+ " steps.<br>\n";
        }

        if (attributes.contains("cross domain"))
        {
            String yn = "";
            if (!is_cross_domain)
                yn = "not ";
            output=output+"This is "+yn+"a cross domain concept.<br>\n";
        }

        if (attributes.contains("entity instant"))
        {
            String yn = "";
            if (!is_entity_instantiations)
                yn = "not ";
            output=output+"This is "+yn+"an instantiation of entities.<br>\n";
        }

        if (attributes.contains("types"))
            output = output + "The object types in the datatable are: " + types.toString()+"<br>\n";

        if (attributes.contains("categorisation"))
        {
            output = output + "The categorisation this concept achieves is:<br>\n";
            output=output+categorisation.toString()+"<br>\n";
        }

        if (!(snapshot.equals(output)))
            output=output+"<hr>\n";
        snapshot = output;

        // Conjectures //

        if (!(snapshot.equals(output)))
            output=output+"<hr>\n";
        snapshot = output;

        output = output + "<b><u>Conjectures about this concept:</u></b><br>\n";

        if (attributes.contains("implications"))
        {
            if (implications.size()>0)
            {
                output = output + "Concepts implied by this concept:<br>\n";
                for (int i=0; i<implications.size(); i++)
                {
                    Implication imp = (Implication)implications.elementAt(i);
                    if (imp.lh_concept==this)
                        output = output + imp.rh_concept.id + " "
                                + imp.rh_concept.writeDefinitionWithStartLetters(main_language) + " [" + imp.id + "]<br>\n";
                }
                output = output + "\nConcepts which imply this concept:<br>\n";
                for (int i=0; i<implications.size(); i++)
                {
                    Implication imp = (Implication)implications.elementAt(i);
                    if (imp.rh_concept==this)
                        output = output + imp.lh_concept.id + " "
                                + imp.lh_concept.writeDefinitionWithStartLetters(main_language) + " [" + imp.id + "]<br>\n";
                }
                output = output + "\n\n";
            }
            else
                output = output + "There are no concept implications involving this concept.<br>\n\n";
        }

        if (attributes.contains("generalisations"))
        {
            output=output+"The generalisations of this concept are:<br>\n"+
                    writeGeneralisationsHTML(main_language) + "\n";
        }

        if (attributes.contains("alt defs"))
        {
            if (conjectured_equivalent_concepts.isEmpty())
                output=output+"There are no alternative definitions for this concept.<br>\n";
            else
                output=output+"The alternative definitions of this concept are:<br>\n"+allDefinitionsHTML(main_language);
        }

        if (attributes.contains("rel equivs"))
        {
            if (relevant_equivalences.isEmpty())
                output=output+"There are no relevant equivalences for this concept.<br>\n";
            else
            {
                output=output+"The relevant equivalences for this concept are:<br>\n";
                for (int i=0; i<relevant_equivalences.size(); i++)
                {
                    Equivalence equiv = (Equivalence)relevant_equivalences.elementAt(i);
                    output = output + replaceLTForHTML(equiv.writeConjecture(main_language)) + "<br>\n";
                }
            }
        }

        if (attributes.contains("rel imps"))
        {
            if (relevant_implicates.isEmpty())
                output=output+"There are no relevant implicates for this concept.<br>\n";
            else
            {
                output=output+"The relevant implicates for this concept are:<br>\n";
                for (int i=0; i<relevant_implicates.size(); i++)
                {
                    Implicate imp = (Implicate)relevant_implicates.elementAt(i);
                    output = output + replaceLTForHTML(imp.writeConjecture(main_language) + "\n");
                }
            }
        }

        if (attributes.contains("near equivs"))
        {
            if (near_equivalences.isEmpty())
                output=output+"\nThere are no near-equivalent concepts.<br>\n";
            else
            {
                output=output+"\nThe near-equivalence concepts are:<br>\n";
                for (int i=0; i<near_equivalences.size(); i++)
                {
                    NearEquivalence near_equiv = (NearEquivalence)near_equivalences.elementAt(i);
                    output = output + decimalPlaces(near_equiv.score, 2) + ":" + near_equiv.rh_concept.id+". " +
                            replaceLTForHTML(near_equiv.rh_concept.writeDefinitionWithStartLetters(main_language))+
                            near_equiv.counterexamples.toString() + "<br>\n";
                }
            }
        }

        if (attributes.contains("implied specs"))
        {
            if (implicates.size()>0)
            {
                output = output + "These specifications are implied in addition:\n";
                for (int i=0; i<implicates.size(); i++)
                {
                    Implicate implicate = (Implicate)implicates.elementAt(i);
                    output = output + replaceLTForHTML(implicate.writeGoal("otter")) + "<br>\n";
                }
            }
            else
                output = output + "There are no specifications implied in addition<br>\n";
            String generalisation_implicates = "";
            Vector gen_imp_strings_seen = new Vector();
            for (int i=0; i<generalisations.size(); i++)
            {
                Concept generalisation = (Concept)generalisations.elementAt(i);
                for (int j=0; j<generalisation.implicates.size(); j++)
                {
                    Implicate gen_implicate = (Implicate)generalisation.implicates.elementAt(j);
                    String gen_imp_string = gen_implicate.writeGoal("otter");
                    if (!gen_imp_strings_seen.contains(gen_imp_string))
                    {
                        generalisation_implicates = generalisation_implicates +
                                gen_implicate.writeGoal("otter") + "<br>\n";
                        gen_imp_strings_seen.addElement(gen_imp_string);
                    }
                }
            }
            if (!generalisation_implicates.equals(""))
                output = output + "The specifications implied by the generalisations are:\n" + generalisation_implicates;
        }

        if (!(snapshot.equals(output)))
            output=output+"<hr>\n";
        snapshot = output;

        // Specifications //

        output = output + "<b><u>Internal details</u></b><br>\n";

        if (attributes.contains("functions"))
        {
            if (functions.isEmpty())
                output = output + "There are no function specifications for this concept.<br>\n";
            else
            {
                output = output + "The function specifications for this concept are:<br>\n";
                for (int i=0; i<functions.size(); i++)
                {
                    Function function = (Function)functions.elementAt(i);
                    output = output + function.writeFunction();
                    if (i<functions.size()-1)
                        output = output + " & ";
                }
                output = output + "<br>";
            }
        }

        if (attributes.contains("specifications"))
        {
            output=output+"These are the specifications inside the concept definition:<br>\n";
            output=output+"\n"+writeSpecDetailsHTML();
        }
        return output;
    }


    public String fullASCIIDetails(Vector attributes, int dp)
    {
        String output = "";

        // Identifier and definitions //

        if (attributes.contains("id"))
            output = output + id;
        if (attributes.contains("name"))
            output = output + " " + name + " " + this.toString();

        if (attributes.contains("id") || attributes.contains("name"))
            output = output + "\n";

        if (attributes.contains("simplified def"))
        {
            boolean tmp_bool = definition_writer.remove_existence_variables;
            definition_writer.remove_existence_variables = true;
            output = output + writeDefinitionWithStartLetters("otter") + "\n";
            definition_writer.remove_existence_variables = tmp_bool;
        }
        if (attributes.contains("ascii def"))
            output = output + writeDefinitionWithStartLetters("ascii") + "\n";
        if (attributes.contains("otter def"))
            output = output + writeDefinitionWithStartLetters("otter") + "\n";
        if (attributes.contains("prolog def"))
            output = output + writeDefinitionWithStartLetters("prolog") + "\n";
        if (attributes.contains("tptp def"))
            output = output + writeDefinitionWithStartLetters("tptp") + "\n";
        if (attributes.contains("skolemised def"))
            output = output + writeSkolemisedRepresentation() + "\n";
        if (!(output.equals("")))
            output = output + "-----------------\n";
        String snapshot = output;

        // Measures and total score //

        if (attributes.contains("total score"))
            output = output + "Total score = " + decimalPlaces(interestingness, dp) + "\n";
        if (attributes.contains("arity"))
            output = output + "Arity = " + decimalPlaces(arity, dp) + "\n";
        if (attributes.contains("applicability"))
        {
            output=output+"Applicability = " + decimalPlaces(applicability, dp) +
                    " " + decimalPlaces(normalised_applicability, dp) + ")\n";
        }
        if (attributes.contains("child num"))
            output=output+"Children number = " + decimalPlaces(number_of_children, dp)+ "\n";
        if (attributes.contains("child score"))
        {
            output=output+"Children score = " + decimalPlaces(children_score, dp) +
                    " (" + decimalPlaces(children_score, dp) + ")\n";
        }
        if (attributes.contains("comprehens"))
        {
            output=output+"Comprehensibility = " + decimalPlaces(comprehensibility, dp) +
                    " (" + decimalPlaces(normalised_comprehensibility, dp) + ")\n";
        }
        if (attributes.contains("complexity"))
            output=output + "Complexity = " + decimalPlaces(complexity, dp) + "\n";
        if (attributes.contains("coverage"))
        {
            output=output+"Coverage = " + decimalPlaces(coverage, dp) +
                    " (" + decimalPlaces(normalised_coverage, dp) + ")\n";
        }
        if (attributes.contains("devel steps"))
        {
            output=output+"Development steps = " + decimalPlaces(development_steps_num, dp) +
                    " (" + decimalPlaces(normalised_development_steps_num, dp) + ")\n";
        }
        if (attributes.contains("discrimination"))
        {
            output=output+"Discrimination = " + decimalPlaces(discrimination, dp) +
                    " (" + decimalPlaces(normalised_discrimination_score, dp) + ")\n";
        }
        if (attributes.contains("equiv conj score"))
        {
            output=output+"Equiv Conjecture score = " + decimalPlaces(equiv_conj_score, dp) +
                    " (" + decimalPlaces(normalised_equiv_conj_score, dp) + ")\n";
        }
        if (attributes.contains("equiv conj num"))
        {
            output=output+"Equiv Conjecture num = " + decimalPlaces(equiv_conjectures.size(), dp) +
                    " (" + decimalPlaces(normalised_equiv_conj_num, dp) + ")\n";
        }
        if (attributes.contains("highlight"))
            output=output + "Highlight = " + decimalPlaces(highlight_score, dp) + "\n";
        if (attributes.contains("ne conj score"))
        {
            output=output+"Non-exists Conjecture score = " + decimalPlaces(ne_conj_score, dp) +
                    " (" + decimalPlaces(normalised_ne_conj_score, dp) + ")\n";
        }
        if (attributes.contains("ne conj num"))
        {
            output=output+"Non-exists Conjecture num = " + decimalPlaces(ne_conjectures.size(), dp) +
                    " (" + decimalPlaces(normalised_ne_conj_num, dp) + ")\n";
        }
        if (attributes.contains("neg applic"))
        {
            output=output+"Negative Applicability = " + decimalPlaces(negative_applicability, dp) +
                    " (" + decimalPlaces(normalised_negative_applicability, dp) + ")\n";
        }
        if (attributes.contains("imp conj score"))
        {
            output=output+"Implicate Conjecture score = " + decimalPlaces(imp_conj_score, dp) +
                    " (" + decimalPlaces(normalised_imp_conj_score, dp) + ")\n";
        }
        if (attributes.contains("imp conj num"))
        {
            output=output+"Implicate Conjecture num = " + decimalPlaces(imp_conjectures.size(), dp) +
                    " (" + decimalPlaces(normalised_imp_conj_num, dp) + ")\n";
        }
        if (attributes.contains("invariance"))
        {
            output=output+"Invariance = " + decimalPlaces(invariance, dp) +
                    " (" + decimalPlaces(normalised_invariance_score, dp) + ")\n";
        }
        if (attributes.contains("novelty"))
        {
            output=output+"Novelty = " + decimalPlaces(novelty, dp) +
                    " (" + decimalPlaces(normalised_novelty, dp) + ")\n";
        }
        if (attributes.contains("parent score"))
        {
            output=output+"Parent score = " + decimalPlaces(parent_score, dp) +
                    " (" + decimalPlaces(parent_score, dp) + ")\n";
        }
        if (attributes.contains("parsimony"))
        {
            output=output+"Parsimony = " + decimalPlaces(parsimony, dp) +
                    " (" + decimalPlaces(normalised_parsimony, dp) + ")\n";
        }
        if (attributes.contains("pi conj score"))
        {
            output=output+"PI Conjecture score = " + decimalPlaces(pi_conj_score, dp) +
                    " (" + decimalPlaces(normalised_pi_conj_score, dp) + ")\n";
        }
        if (attributes.contains("pi conj num"))
        {
            output=output+"PI Conjecture num = " + decimalPlaces(pi_conjectures.size(), dp) +
                    " (" + decimalPlaces(normalised_pi_conj_num, dp) + ")\n";
        }
        if (attributes.contains("pos applic"))
        {
            output=output+"Positive Applicability = " + decimalPlaces(positive_applicability, dp) +
                    " (" + decimalPlaces(normalised_positive_applicability, dp) + ")\n";
        }
        if (attributes.contains("pred power"))
        {
            output=output+"Predictive Power = " + decimalPlaces(predictive_power, dp) + "\n";
        }
        if (attributes.contains("productivity"))
        {
            output=output+"Productivity = " + decimalPlaces(productivity, dp) +
                    " (" + decimalPlaces(normalised_productivity, dp) + ")\n";
        }
        if (attributes.contains("variety"))
        {
            output=output+"Variety = " + decimalPlaces(variety, dp) +
                    " (" + decimalPlaces(normalised_variety, dp) + ")\n";
        }

        // Construction //

        if (!(snapshot.equals(output)))
            output=output+"-----------------\n";
        snapshot = output;

        if (attributes.contains("const step"))
        {
            if (is_user_given)
                output = output + "This concept was supplied by the user.\n";
            else
                output = output + "Constructed with this step: " + construction.asString()+".\n";
        }

        if (attributes.contains("ancestors"))
            output = output + "The ancestors of this concept are:" +  ancestor_ids.toString() + "\n";

        if (attributes.contains("const time"))
        {
            String s_or_not = "s";
            String seconds = Long.toString(when_constructed);
            if (seconds.equals("1"))
                s_or_not = "";
            output=output+"Constructed after "+Long.toString(when_constructed) +
                    " second"+s_or_not+", "+Integer.toString(step_number)+ " steps.\n";
        }

        if (attributes.contains("cross domain"))
        {
            String yn = "";
            if (!is_cross_domain)
                yn = "not ";
            output=output+"This is "+yn+"a cross domain concept.\n";
        }

        if (attributes.contains("entity instant"))
        {
            String yn = "";
            if (!is_entity_instantiations)
                yn = "not ";
            output=output+"This is "+yn+"an instantiation of entities.\n";
        }

        // Examples //

        if (!(snapshot.equals(output)))
            output=output+"-----------------\n";
        snapshot = output;

        if (attributes.contains("examples"))
            output = output + prettyPrintExamples(datatable)+"\n";

        if (attributes.contains("datatable"))
        {
            output = output + "The datatable is:\n" +
                    datatable.toTable() + "\n";
        }

        if (attributes.contains("types"))
            output = output + "The object types in the datatable are: " + types.toString()+"\n";

        if (attributes.contains("functions"))
        {
            if (functions.isEmpty())
                output = output + "There are no function specifications for this concept.\n";
            else
            {
                output = output + "The function specifications for this concept are:\n";
                for (int i=0; i<functions.size(); i++)
                {
                    Function function = (Function)functions.elementAt(i);
                    output = output + function.writeFunction();
                    if (i<functions.size()-1)
                        output = output + " & ";
                }
                output = output + "\n\n";
            }
        }

        if (attributes.contains("categorisation"))
        {
            output = output + "The categorisation the examples of this concept achieves is:\n";
            output=output+categorisation.toString()+"\n\n";
        }

        if (attributes.contains("add examples"))
        {
            if (additional_datatable.isEmpty())
                output = output + "There are no additional entities for this concept.\n";
            else
            {
                output = output + "From the additional table:\n";
                output = output + prettyPrintExamples(additional_datatable) + "\n";
            }
        }

        // Conjectures //

        if (!(snapshot.equals(output)))
            output=output+"-----------------\n";
        snapshot = output;

        if (attributes.contains("implications"))
        {
            if (implications.size()>0)
            {
                output = output + "Concepts implied by this concept:\n";
                for (int i=0; i<implications.size(); i++)
                {
                    Implication imp = (Implication)implications.elementAt(i);
                    if (imp.lh_concept==this)
                        output = output + imp.rh_concept.id + " "
                                + imp.rh_concept.writeDefinitionWithStartLetters("otter") + " [" + imp.id + "]\n";
                }
                output = output + "\nConcepts which imply this concept:\n";
                for (int i=0; i<implications.size(); i++)
                {
                    Implication imp = (Implication)implications.elementAt(i);
                    if (imp.rh_concept==this)
                        output = output + imp.lh_concept.id + " "
                                + imp.lh_concept.writeDefinitionWithStartLetters("otter") + " [" + imp.id + "]\n";
                }
                output = output + "\n\n";
            }
            else
                output = output + "There are no concept implications involving this concept\n\n";
        }

        if (attributes.contains("generalisations"))
        {
            output=output+"The generalisations of this concept are:\n"+
                    writeGeneralisations("ascii") + "\n";
        }

        if (attributes.contains("alt defs ascii"))
        {
            if (conjectured_equivalent_concepts.isEmpty())
                output=output+"There are no alternative definitions for this concept.\n";
            else
                output=output+"The alternative definitions of this concept are:\n"+allDefinitions("ascii");
        }

        if (attributes.contains("alt defs prolog"))
        {
            if (conjectured_equivalent_concepts.isEmpty())
                output=output+"There are no alternative definitions for this concept.\n";
            else
                output=output+"The alternative definitions of this concept are:\n"+allDefinitions("prolog");
        }

        if (attributes.contains("rel equivs prolog"))
        {
            if (relevant_equivalences.isEmpty())
                output=output+"There are no relevant equivalences for this concept.\n";
            else
            {
                output=output+"The relevant equivalences for this concept are:\n";
                for (int i=0; i<relevant_equivalences.size(); i++)
                {
                    Equivalence equiv = (Equivalence)relevant_equivalences.elementAt(i);
                    output = output + equiv.writeConjecture("prolog") + "\n";
                }
            }
        }

        if (attributes.contains("rel equivs ascii"))
        {
            if (relevant_equivalences.isEmpty())
                output=output+"There are no relevant equivalences for this concept.\n";
            else
            {
                output=output+"The relevant equivalences for this concept are:\n";
                for (int i=0; i<relevant_equivalences.size(); i++)
                {
                    Equivalence equiv = (Equivalence)relevant_equivalences.elementAt(i);
                    output = output + equiv.writeConjecture("ascii") + "\n";
                }
            }
        }

        if (attributes.contains("rel imps prolog"))
        {
            if (relevant_implicates.isEmpty())
                output=output+"There are no relevant implicates for this concept.\n";
            else
            {
                output=output+"The relevant implicates for this concept are:\n";
                for (int i=0; i<relevant_implicates.size(); i++)
                {
                    Implicate imp = (Implicate)relevant_implicates.elementAt(i);
                    output = output + imp.writeConjecture("prolog") + "\n";
                }
            }
        }

        if (attributes.contains("rel imps ascii"))
        {
            if (relevant_implicates.isEmpty())
                output=output+"There are no relevant implicates for this concept.\n";
            else
            {
                output=output+"The relevant implicates for this concept are:\n";
                for (int i=0; i<relevant_implicates.size(); i++)
                {
                    Implicate imp = (Implicate)relevant_implicates.elementAt(i);
                    output = output + imp.writeConjecture("ascii") + "\n";
                }
            }
        }

        if (attributes.contains("near equivs prolog"))
        {
            if (near_equivalences.isEmpty())
                output=output+"\nThere are no near-equivalent concepts.\n";
            else
            {
                output=output+"\nThe near-equivalence concepts are:\n";
                for (int i=0; i<near_equivalences.size(); i++)
                {
                    NearEquivalence near_equiv = (NearEquivalence)near_equivalences.elementAt(i);
                    output = output + decimalPlaces(near_equiv.score, 2) + ":" + near_equiv.rh_concept.id+". " +
                            near_equiv.rh_concept.writeDefinitionWithStartLetters("prolog")+
                            near_equiv.counterexamples.toString() + "\n";
                }
            }
        }

        if (attributes.contains("near equivs ascii"))
        {
            if (near_equivalences.isEmpty())
                output=output+"\nThere are no near-equivalent concepts.\n";
            else
            {
                output=output+"\nThe near-equivalence concepts are:\n";
                for (int i=0; i<near_equivalences.size(); i++)
                {
                    NearEquivalence near_equiv = (NearEquivalence)near_equivalences.elementAt(i);
                    output = output + decimalPlaces(near_equiv.score, 2) + ":" + near_equiv.rh_concept.id+". " +
                            near_equiv.rh_concept.writeDefinitionWithStartLetters("ascii")+
                            near_equiv.counterexamples.toString() + "\n";
                }
            }
        }

        if (!(snapshot.equals(output)))
            output=output+"-----------------\n";

        snapshot = output;

        if (attributes.contains("implied specs"))
        {
            if (implicates.size()>0)
            {
                output = output + "These specifications are implied in addition:\n";
                for (int i=0; i<implicates.size(); i++)
                {
                    Implicate implicate = (Implicate)implicates.elementAt(i);
                    output = output + implicate.writeGoal("otter") + "\n";
                }
            }
            else
                output = output + "There are no specifications implied in addition\n";
            String generalisation_implicates = "";
            Vector gen_imp_strings_seen = new Vector();
            for (int i=0; i<generalisations.size(); i++)
            {
                Concept generalisation = (Concept)generalisations.elementAt(i);
                for (int j=0; j<generalisation.implicates.size(); j++)
                {
                    Implicate gen_implicate = (Implicate)generalisation.implicates.elementAt(j);
                    String gen_imp_string = gen_implicate.writeGoal("otter");
                    if (!gen_imp_strings_seen.contains(gen_imp_string))
                    {
                        generalisation_implicates = generalisation_implicates +
                                gen_implicate.writeGoal("otter") + "\n";
                        gen_imp_strings_seen.addElement(gen_imp_string);
                    }
                }
            }
            if (!generalisation_implicates.equals(""))
                output = output + "The specifications implied by the generalisations are:\n" + generalisation_implicates;
        }

        if (!(snapshot.equals(output)))
            output=output+"-----------------\n";

        // Specifications //

        if (attributes.contains("specifications"))
        {
            output=output+"These are the specifications inside the concept definition:\n";
            output=output+"\n"+writeSpecDetails();
        }

        // Ancestor definitions //

        if (attributes.contains("anc defs prolog"))
        {
            output = output + "The ancestors of this concept are:\n";
            output = output + writeAncestorsAndEquivalents("prolog");
        }
        return output;
    }

    public String prettyPrintExamplesHTML(Datatable datatable_to_pretty_print)
    {
        String output = "";
        if (arity==1)
        {
            output = output + "<table border=0><tr><td>The positives are:\n<ol>\n";
            for (int i=0; i<datatable_to_pretty_print.size(); i++)
            {
                Row row = (Row)datatable_to_pretty_print.elementAt(i);
                if (!row.tuples.isEmpty())
                    output = output + "<li>" + row.entity + "\n";
            }
            output = output + "\n</ol></td><td>&nbsp;</td><td>";
            output = output + "The negatives are:\n<ol>\n";
            for (int i=0; i<datatable_to_pretty_print.size(); i++)
            {
                Row row = (Row)datatable_to_pretty_print.elementAt(i);
                if (row.tuples.isEmpty())
                {
                    output = output + "<li>" + row.entity + "\n";
                }
            }
            output = output + "</td></tr></table>\n";
        }
        else
        {
            for (int i=0; i<datatable_to_pretty_print.size(); i++)
            {
                Row row = (Row)datatable_to_pretty_print.elementAt(i);
                output = output + "f("+row.entity+")=";
                output = output + row.tuples.toString();
                output = output + "<br>";
            }
        }
        return output;
    }

    public String prettyPrintExamples(Datatable datatable_to_pretty_print)
    {
        String output = "";
        if (arity==1)
        {
            output = output + "The " + (String)types.elementAt(0) + "s with this property are:\n";
            boolean got_one = false;
            for (int i=0; i<datatable_to_pretty_print.size(); i++)
            {
                Row row = (Row)datatable_to_pretty_print.elementAt(i);
                if (!row.tuples.isEmpty())
                {
                    if (got_one) output = output + ", ";
                    output = output + row.entity;
                    got_one = true;
                }
            }
            output = output + "\n";
            output = output + "The " + (String)types.elementAt(0) + "s without this property are:\n";
            got_one = false;
            for (int i=0; i<datatable_to_pretty_print.size(); i++)
            {
                Row row = (Row)datatable_to_pretty_print.elementAt(i);
                if (row.tuples.isEmpty())
                {
                    if (got_one) output = output + ", ";
                    output = output + row.entity;
                    got_one = true;
                }
            }
            output = output + "\n";
        }
        else
        {
            output = output + "As a function, this concept has these values:\n";
            for (int i=0; i<datatable_to_pretty_print.size(); i++)
            {
                Row row = (Row)datatable_to_pretty_print.elementAt(i);
                output = output + "f("+row.entity+")=";
                output = output + row.tuples.toString();
                output = output + "\n";
            }
        }
        return output;
    }

    /** This writes the details of the specifications.
     */

    public String writeSpecDetails()
    {
        String output = "";
        for (int i=0; i<specifications.size(); i++)
        {
            Specification specification = (Specification)specifications.elementAt(i);
            output = output + specification.fullDetails()+"\n---------------------\n";
        }
        return output;
    }

    /** This writes the details of the specifications.
     */

    public String writeSpecDetailsHTML()
    {
        String output = "";
        for (int i=0; i<specifications.size(); i++)
        {
            Specification specification = (Specification)specifications.elementAt(i);
            output = output + "<pre>" + specification.fullDetails() + "</pre><table width=100><tr><td><hr></td></tr></table>\n";
        }
        return output;
    }

    /** This returns a string with all the alternative (equivalent) definitions.
     */

    public String allDefinitions(String language)
    {
        String output = "";
        String addon = "";
        for (int i=0;i<conjectured_equivalent_concepts.size();i++)
        {
            Concept conjectured_equivalent_concept = (Concept)conjectured_equivalent_concepts.elementAt(i);
            Conjecture alt_conjecture = conjectured_equivalent_concept.equivalence_conjecture;
            if (this.isGeneralisationOf(conjectured_equivalent_concept)<0)
            {
                output = output + "(" + alt_conjecture.id + ") " + conjectured_equivalent_concept.writeDefinition(language);
                output = output + " [" + alt_conjecture.proof_status + "]\n";
            }
            else
            {
                addon = addon + "(" + alt_conjecture.id + ") " + conjectured_equivalent_concept.writeDefinition(language);
                addon = addon + " [" + alt_conjecture.proof_status + "]\n";
            }
        }
        if (!addon.equals(""))
            output = output + "--\n" + addon;
        return output;
    }

    /** This returns a string with all the alternative (equivalent) definitions.
     */

    public String allDefinitionsHTML(String language)
    {
        String output = "";
        String addon = "";
        for (int i=0;i<conjectured_equivalent_concepts.size();i++)
        {
            Concept conjectured_equivalent_concept = (Concept)conjectured_equivalent_concepts.elementAt(i);
            Conjecture alt_conjecture = conjectured_equivalent_concept.equivalence_conjecture;
            if (this.isGeneralisationOf(conjectured_equivalent_concept)<0)
            {
                output = output + "(" + alt_conjecture.id + ") " + conjectured_equivalent_concept.writeDefinition(language);
                output = output + " [" + alt_conjecture.proof_status + "]<br>\n";
            }
            else
            {
                addon = addon + "(" + alt_conjecture.id + ") " + conjectured_equivalent_concept.writeDefinition(language);
                addon = addon + " [" + alt_conjecture.proof_status + "]<br>\n";
            }
        }
        if (!addon.equals(""))
            output = output + "--<br>\n" + addon;
        return output;
    }

    /** This returns a string with the definitions of all the generalisations of this
     * concept.
     */

    public String writeGeneralisations(String language)
    {
        String output = "";
        for (int i=0;i<generalisations.size();i++)
        {
            Concept generalisation = (Concept)generalisations.elementAt(i);
            output = output + generalisation.id +". " +
                    generalisation.writeDefinition(language) + "\n";
        }
        return output;
    }

    /** This returns a string with the definitions of all the generalisations of this
     * concept.
     */

    public String writeGeneralisationsHTML(String language)
    {
        String output = "";
        for (int i=0;i<generalisations.size();i++)
        {
            Concept generalisation = (Concept)generalisations.elementAt(i);
            output = output + generalisation.id +". " +
                    generalisation.writeDefinition(language) + "<br>\n";
        }
        return output;
    }

    /** This will write the definition for all the ancestors of the concept.
     * The definitions will be written in the given language.
     */

    public String writeAncestors(String language, Theory theory)
    {
        String output = "";
        for (int i=0; i<ancestor_ids.size(); i++)
        {
            String ancestor_id = (String)ancestor_ids.elementAt(i);
            Concept ancestor = theory.getConcept(ancestor_id);
            output = output + ancestor.id + ". " + ancestor.writeDefinition(language) + "\n";
        }
        return output;
    }

    /** This will transform a number theory concept into a sequence
     *  object. The sequence produced will depend on the arity of the concept.
     */

    public Sequence asSequence()
    {
        Sequence output = new Sequence();
        if (arity==1)
        {
            for (int i=0;i<datatable.size();i++)
            {
                Row row = (Row)datatable.elementAt(i);
                Tuples tuples = row.tuples;
                if (tuples.size()>0) output.addElement(row.entity);
            }
        }
        if (arity==2)
        {
            for(int i=0;i<datatable.size();i++)
            {
                Row row = (Row)datatable.elementAt(i);
                Tuples tuples = row.tuples;
                for(int j=0;j<tuples.size();j++)
                    output.addElement((String)((Vector)tuples.elementAt(j)).elementAt(0));
            }
        }
        return output;
    }

    /** Checks whether this concept is a generalisation of the given concept.
     * It returns -1 if it is not a generalisation, 0 if it is a proper generalisation,
     * and 1 if it has exactly the same specifications.
     */

    public int isGeneralisationOf(Concept other_concept)
    {
        int output = 0;
        Vector other_specs = other_concept.specifications;
        if (specifications.size() > other_specs.size())
            return -1;
        int i=0;
        while (output>-1 && i<specifications.size())
        {
            if (!other_specs.contains(specifications.elementAt(i)))
                output=-1;
            i++;
        }
        if (specifications.size()==other_specs.size() && output==0)
            output = 1;
        return output;
    }

    /** This updates the datatable of a concept given a new entity for the theory.
     */

    public void updateDatatable(Vector all_concepts, Entity new_entity)
    {
        Tuples tuples = (Tuples)new_entity.concept_data.get(name);
        if (tuples!=null)
        {
            Row new_row = new Row(new_entity.name, tuples);
            datatable.addElement(new_row);
        }
        else
            calculateRow(all_concepts, new_entity.name);
        boolean found = false;
        for (int i=0; i<additional_datatable.size() && !found; i++)
        {
            Row row = (Row)additional_datatable.elementAt(i);
            if (row.entity.equals(new_entity.name))
            {
                found = true;
                datatable.addElement(row);
                additional_datatable.removeElementAt(i);
            }
        }
    }

    /** This finds the most specific concept for the columns given.
     * For example, if this concept is integer multiplication and the columns
     * are 0 and 1, then the most specific concept over those columns
     * (as specified in this concept's specifications) will be the divisor
     * concept.
     */

    public Concept mostSpecificGeneralisation(Vector all_concepts, Vector parameters)
    {
        Vector input_specifications = new Vector();
        Vector input_specifications_before_permutation_change = new Vector();

        // First work out which specifications are only valid of the input to the function.

        Enumeration e = specifications.elements();
        while (e.hasMoreElements())
        {
            Specification old_specification = (Specification)e.nextElement();
            if (!old_specification.involvesColumns(parameters))
                input_specifications_before_permutation_change.addElement(old_specification);
        }

        // Next change the permutations of the input specifications to reflect the removal
        // of the columns.

        e = input_specifications_before_permutation_change.elements();
        while (e.hasMoreElements())
        {
            Specification old_specification = (Specification)e.nextElement();
            Specification new_specification = old_specification.copy();
            new_specification.permutation = new Vector();
            for (int j=0;j<old_specification.permutation.size();j++)
            {
                int number_of_columns_to_remove = 0;
                int add_column =
                        (new Integer((String)old_specification.permutation.elementAt(j))).intValue();
                for (int k=0;k<parameters.size();k++)
                {
                    int param =
                            (new Integer((String)parameters.elementAt(k))).intValue();
                    if (param<add_column) number_of_columns_to_remove++;
                }
                new_specification.permutation.
                        addElement(Integer.toString(add_column-number_of_columns_to_remove));
            }
            input_specifications.addElement(new_specification);
        }

        // Next find the concept with these specifications.

        e = all_concepts.elements();
        boolean found = false;
        Concept output_concept = new Concept();
        while (e.hasMoreElements() && !found)
        {
            output_concept = (Concept)e.nextElement();
            if (output_concept.specifications.size()==input_specifications.size())
            {
                int i=0;
                while (i<input_specifications.size())
                {
                    Specification spec = (Specification)input_specifications.elementAt(i);
                    int j=0;
                    while (j<output_concept.specifications.size())
                    {
                        Specification check_spec =
                                (Specification)output_concept.specifications.elementAt(j);
                        if (check_spec.equals(spec)) break;
                        j++;
                    }
                    if (j==output_concept.specifications.size()) break;
                    i++;
                }
                if (i==input_specifications.size()) found=true;
            }
        }
        if (!found)
            output_concept = new Concept();

        // Discard the answer if it isn't of the correct arity. This is because sometimes
        // the concept has the right specifications, but is not of the correct arity.

        if (!(output_concept.arity==arity-parameters.size()))
            output_concept = new Concept();

        return output_concept;
    }

    /** This returns a first order representation of the tuples in the datatable
     * as a string.
     */

    public String firstOrderRepresentation()
    {
        int num=0;
        String output = "";
        String main_head = Integer.toString(arity)+"tuple("+id+",";
        for (int i=0; i<datatable.size(); i++)
        {
            Row row = (Row)datatable.elementAt(i);
            for (int j=0; j<row.tuples.size(); j++)
            {
                String predicate = main_head+row.entity;
                Vector tuple = (Vector)row.tuples.elementAt(j);
                for (int k=0; k<tuple.size(); k++)
                    predicate = predicate + "," + (String)tuple.elementAt(k);
                predicate = predicate + ").";
                output = output + predicate;
                num++;
                if (num==5)
                {
                    output = output + "\n";
                    num=0;
                }
                else
                    output = output + " ";
            }
        }
        return output;
    }

    /** This adds a row to the datatable. The second argument is a string representation
     * of a set of tuples.
     */

    public void addAdditionalRow(String entity, String tuples_string)
    {
        additional_datatable.addEmptyRow(entity);
        if (tuples_string.equals("[]"))
            return;
        if (tuples_string.equals("[[]]"))
        {
            Row row = additional_datatable.rowWithEntity(entity);
            row.tuples.addElement(new Vector());
            return;
        }
        if (tuples_string.substring(0,2).equals("[["))
            tuples_string = tuples_string.substring(1,tuples_string.length());

        int ind = tuples_string.indexOf("]");
        while (ind>=0)
        {
            String tuple_string = tuples_string.substring(0,ind+1);
            Vector tuple = getTuple(tuple_string);
            tuple.insertElementAt(entity, 0);
            additional_datatable.addTuple(tuple);
            if (tuples_string.indexOf("[",ind)<0)
                break;
            tuples_string =
                    tuples_string.substring(tuples_string.indexOf("[",ind), tuples_string.length());
            ind = tuples_string.indexOf("]");
        }
    }

    private Vector getTuple(String tuple)
    {
        Vector output = new Vector();
        tuple = tuple.substring(1,tuple.length()-1);
        int ind = tuple.indexOf(",");
        while (ind>0)
        {
            output.addElement(tuple.substring(0,ind).trim());
            tuple = tuple.substring(ind+1,tuple.length());
            if (tuple.substring(0,1).equals(" "))
                tuple = tuple.substring(1,tuple.length());
            ind = tuple.indexOf(",");
        }
        if (!tuple.equals("") ||
                !tuple.equals(" "))
            output.addElement(tuple);
        return output;
    }

    /** This goes through the concepts and finds any which have datatables
     * which are close to this one's datatable (in terms of the percentage of
     * tuples which match).
     */

    public void getNearEquivalences(Theory theory, Vector concepts, double percent_min)
    {
        near_equivalences.removeAllElements();
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if (concept.types.toString().equals(types.toString()) && concept!=this)
            {
                Vector tuple_score_vector = new Vector();
                Vector counterexamples = new Vector();
                double score = datatable.quickPercentageMatchWith(theory, concept.datatable, counterexamples);
                if (score >= percent_min/100)
                {
                    NearEquivalence near_equiv = new NearEquivalence(this, concept, counterexamples, score);
                    NearEquivalence other_near_equiv = new NearEquivalence(concept, this, counterexamples, score);
                    near_equivalences.addElement(near_equiv, "score");
                    boolean already_there = false;
                    for (int j=0; j<concept.near_equivalences.size() && !already_there; j++)
                    {
                        NearEquivalence other_neq = (NearEquivalence)concept.near_equivalences.elementAt(j);
                        if (other_neq.lh_concept==concept && other_neq.rh_concept==this)
                            already_there = true;
                    }
                    if (!already_there)
                        concept.near_equivalences.addElement(near_equiv, "score");
                }
            }
        }
    }

    /** This goes through the concepts and finds any which have
     * datatables which (a) are almost subsumed by this one's datatable,
     * or (b) almost subsume this one's datatable (in terms of the
     * percentage of tuples which match). alisonp
     */

    public void getNearImplications(Theory theory, Vector concepts, double percent_min)
    {
        near_implications.removeAllElements();
        for (int i=0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if (concept.types.toString().equals(types.toString()) && concept!=this)
            {

                Vector counterexamples = new Vector();
                double score = datatable.quickPercentageMatchWith(theory, concept.datatable, counterexamples);
                if (score >= percent_min/100)
                {
                    NearEquivalence near_equiv = new NearEquivalence(this, concept, counterexamples, score);
                    NearEquivalence other_near_equiv = new NearEquivalence(concept, this, counterexamples, score);
                    near_equivalences.addElement(near_equiv, "score");
                    boolean already_there = false;
                    for (int j=0; j<concept.near_equivalences.size() && !already_there; j++)
                    {
                        NearEquivalence other_neq = (NearEquivalence)concept.near_equivalences.elementAt(j);
                        if (other_neq.lh_concept==concept && other_neq.rh_concept==this)
                            already_there = true;
                    }
                    if (!already_there)
                        concept.near_equivalences.addElement(near_equiv, "score");
                }
            }
        }
    }


    /** This goes through all the concepts and finds any which have
     * datatables which (a) are almost subsumed by this one's datatable,
     * or (b) almost subsume this one's datatable (in terms of the
     * percentage of tuples which match). alisonp
     */

    public void getSubsumptionNearImplications(Vector concepts, double percent_min)
    {
        //System.out.println("Starting getSubsumptionNearImplications on " + this.writeDefinition("ascii"));
        near_implications.removeAllElements();
        double score =0.0; //[need to change]
        for (int i=0; i<concepts.size(); i++)
        {
            //System.out.println("in for loop");
            //System.out.println("concepts.size() is " + concepts.size());
            Concept other_concept = (Concept)concepts.elementAt(i);
            if(other_concept.arity==arity)
            {
                //System.out.println("other_concept is " + other_concept.writeDefinition("ascii"));
                //System.out.println("other_concept.arity: " + other_concept.id + other_concept.arity + ", and arity: " + id + arity);
                if (!other_concept.is_entity_instantiations)
                {
                    //System.out.println("here");
                    if (other_concept.isGeneralisationOf(this)==-1)
                    {
                        //System.out.println("1 ");
                        Vector counterexamples = other_concept.empiricallySubsumes(this, percent_min);
                        //System.out.println("2 - counterexamples is " + counterexamples);
                        if (counterexamples != null && !(counterexamples.size()==0))
                        {
                            int total = 0;
                            if(arity==0||arity==1)
                                total = getPositives().size();
                            else
                                total = datatable.size();
                            score = total - counterexamples.size();
                            ////System.out.println(" score is " + score);
                            score = score/total;
                            ////System.out.println(" score is " + score);
                            score = score*100;
                            //System.out.println(" total is " + total);
                            //System.out.println(" counterexamples.size() is " + counterexamples.size());
                            //System.out.println(" score is " + score);
                            if (score>= percent_min)
                            {
                                //System.out.println("score>= percent_min");
                                NearImplication near_imp = new NearImplication(this, other_concept, counterexamples, score);
                                //System.out.println("4 near_imp is " +near_imp.writeConjecture("ascii"));
                                near_implications.addElement(near_imp, "score");
                            }
                        }
                    }
                    if (isGeneralisationOf(other_concept)==-1)
                    {
                        //System.out.println("5 ");
                        Vector counterexamples = empiricallySubsumes(other_concept, percent_min);
                        //System.out.println("6 - counterexamples is " + counterexamples);
                        if (counterexamples != null && !(counterexamples.size()==0))
                        {
                            int total = 0;
                            if(other_concept.arity==0||other_concept.arity==1)
                                total = other_concept.getPositives().size();
                            else
                                total = other_concept.datatable.size();
                            score = total - counterexamples.size();
                            ////System.out.println(" score is " + score);
                            score = score/total;
                            ////System.out.println(" score is " + score);
                            score = score*100;
                            //System.out.println(" total is " + total);
                            //System.out.println(" counterexamples.size() is " + counterexamples.size());
                            //System.out.println(" score is " + score);
                            if (score>= percent_min)
                            {
                                //System.out.println("score>= percent_min");
                                NearImplication near_imp = new NearImplication(other_concept, this, counterexamples, score);//[should be imp?]
                                near_implications.addElement(near_imp, "score");
                                //System.out.println("8 near_imp is " +near_imp.writeConjecture("ascii"));
                            }
                        }
                    }
                    //check this
                    boolean already_there = false;
                    for (int j=0; j<other_concept.near_implications.size() && !already_there; j++)
                    {
                        //System.out.println("9 ");
                        NearImplication other_nimp = (NearImplication)other_concept.near_implications.elementAt(j);
                        if (other_nimp.lh_concept==other_concept && other_nimp.rh_concept==this)
                            already_there = true;
                    }
                    //check this
                    // if (!already_there)
                    //concept.near_implications.addElement(near_imp, "score");
                    //	concept.near_implications.addElement(near_imp, "score");
                    //System.out.println(" ");
                }
            }
            //System.out.println("10 ");
        }
        //System.out.println("11 ");
    }


    /** This checks whether all the examples of the given concept are examples
     * of this concept too (up to a given exception percentage number, i.e., there can be
     * a certain number of counterexamples). This returns null if this concept
     * doesn't empirically subsume the other concept, and a (possibly empty)
     * vector of counterexamples otherwise.
     */

    public Vector empiricallySubsumes(Concept other_concept, double max_percentage_of_counters)
    {
        if (arity!=other_concept.arity)
            return null;

        if (!(types.toString().equals(other_concept.types.toString())))
            return null;

        Vector bad_entities = new Vector();
        int size_of_datatable = 0;
        int size_of_other_datatable = 0;

        for (int i=0; i<datatable.size(); i++)
        {
            Row row = (Row)datatable.elementAt(i);
            Row other_row = (Row)other_concept.datatable.elementAt(i);
            if (!row.tuples.subsumes(other_row.tuples))
                bad_entities.addElement(new Entity(row.entity));
            if(!row.tuples.isEmpty())
                size_of_datatable ++;
            if(!other_row.tuples.isEmpty())//alisonp
                size_of_other_datatable ++;
        }

        //alisonp here
        double percentage_counters = 0.0;

        if(!(size_of_datatable==0))
            percentage_counters = ((size_of_datatable - bad_entities.size())/size_of_datatable)*100;
        else
        {
            if(size_of_other_datatable==0)
                return bad_entities;
            if(!(size_of_other_datatable==0))
                return null;
        }


        if (percentage_counters > max_percentage_of_counters)
            return null;
        return bad_entities;
    }


    /** This re-evaluates a near-equivalence in the light of a new
     * entity being introduced. It removes any where the match is now
     * lower than the required percentage value.
     */

    public void reEvaluateNearEquivalences(Theory theory, double percent_min)
    {
        SortableVector new_near_equivalences = new SortableVector();
        for (int i=0; i<near_equivalences.size(); i++)
        {
            NearEquivalence near_equiv = (NearEquivalence)near_equivalences.elementAt(i);
            Vector new_counterexamples = new Vector();
            double new_score = datatable.quickPercentageMatchWith(theory, near_equiv.rh_concept.datatable, new_counterexamples);
            if (new_score >= percent_min)
            {
                NearEquivalence new_near_equiv = new NearEquivalence(this, near_equiv.rh_concept, new_counterexamples, new_score);
                new_near_equivalences.addElement(new_near_equiv, "score");
            }
        }
        near_equivalences = new_near_equivalences;
    }

    /** This re-evaluates a near-implication in the light of a new
     * entity being introduced. It removes any where the match is now
     * lower than the required percentage value. - alisonp [change]
     */

    public void reEvaluateNearImplications(Theory theory, double percent_min)
    {
        SortableVector new_near_implications = new SortableVector();
        for (int i=0; i<near_implications.size(); i++)
        {
            NearImplication near_imp = (NearImplication)near_implications.elementAt(i);
            Vector new_counterexamples = new Vector();
            double new_score = datatable.quickPercentageMatchWith(theory, near_imp.rh_concept.datatable, new_counterexamples);
            if (new_score >= percent_min)
            {
                NearImplication new_near_imp = new NearImplication(this, near_imp.rh_concept, new_counterexamples, new_score);
                new_near_implications.addElement(new_near_imp, "score");
            }
        }
        near_implications = new_near_implications;
    }


    /** This checks whether all the examples of the given concept are examples
     * of this concept too (up to a given exception number, i.e., there can be
     * a certain number of counterexamples). This returns null if this concept
     * doesn't empirically subsume the other concept, and a (possibly empty)
     * vector of counterexamples otherwise.
     */

    public Vector empiricallySubsumes(Concept other_concept, int max_number_of_counters)
    {
        if (arity!=other_concept.arity)
            return null;

        if (!(types.toString().equals(other_concept.types.toString())))
            return null;

        Vector bad_entities = new Vector();
        for (int i=0; i<datatable.size() && bad_entities.size() <= max_number_of_counters; i++)
        {
            Row row = (Row)datatable.elementAt(i);
            Row other_row = (Row)other_concept.datatable.elementAt(i);
            if (!row.tuples.subsumes(other_row.tuples))
                bad_entities.addElement(row.entity);
        }
        if (bad_entities.size() > max_number_of_counters)
            return null;
        return bad_entities;
    }

    /** Which production rule was used to produce this concept.
     */

    public String productionRuleUsedName()
    {
        if (is_user_given)
            return ("user given");
        ProductionRule pr = (ProductionRule)construction.elementAt(1);
        return pr.getName();
    }

    /** This goes through all the concepts and finds any which are subsumed
     * by this concept, similarly finding any which subsume this concept.
     */

    public Vector getSubsumptionImplications(Vector concepts)
    {
        Vector output = new Vector();
        for (int i=0; i<concepts.size(); i++)
        {
            Concept other_concept = (Concept)concepts.elementAt(i);
            if (!other_concept.is_entity_instantiations)
            {
                if (other_concept.isGeneralisationOf(this)==-1)
                {
                    Vector counterexamples = other_concept.empiricallySubsumes(this, 0);
                    if (counterexamples != null && counterexamples.size()==0)
                    {
                        Implication implication = new Implication();
                        implication.lh_concept = this;
                        implication.rh_concept = other_concept;
                        output.addElement(implication);
                    }
                }
                if (isGeneralisationOf(other_concept)==-1)
                {
                    Vector counterexamples = empiricallySubsumes(other_concept, 0);
                    if (counterexamples != null && counterexamples.size()==0)
                    {
                        Implication implication = new Implication();
                        implication.lh_concept = other_concept;
                        implication.rh_concept = this;
                        output.addElement(implication);
                    }
                }
            }
        }
        return output;
    }

    /** If the parents of a concept have changed their specifications, then the
     * change must also occur in the child (this concept). This recalculates the
     * specifications.
     */

    public void recalculateSpecifications(Theory theory, boolean recurse)
    {
        ProductionRule pr = construction.productionRule();
        Vector params = construction.parameters();
        specifications = pr.newSpecifications(parents, params, theory, new Vector());
        if (recurse)
        {
            for (int i=0; i<children.size(); i++)
            {
                Concept child = (Concept)children.elementAt(i);
                child.recalculateSpecifications(theory, true);
            }
        }
    }

    /** This returns the same concept if no substitutable ones are available,
     * otherwise, it returns the top of the substutatable list.
     */

    public Concept getSubstitutableConcept()
    {
        if (!use_top_substitutable || substitutable_concepts.isEmpty())
            return this;
        else
            return ((Concept)substitutable_concepts.elementAt(0));
    }

    /** This uses the getConcept method in the theory to get a vector
     * of ancestor concepts (from their ids stored in the ancestors vector).
     */

    public Vector getAncestors(Theory theory)
    {
        Vector output = new Vector();
        for (int i=0; i<ancestor_ids.size(); i++)
            output.addElement(theory.getConcept((String)ancestor_ids.elementAt(i)));
        return output;
    }

    /** This writes the definition of the ancestors, along with their equivalent
     * definitions for this concept.
     */

    public String writeAncestorsAndEquivalents(String language)
    {
        String output = "";
        for (int i=0; i<ancestors.size(); i++)
        {
            Concept ancestor = (Concept)ancestors.elementAt(i);
            output = output + "--\n";
            output = output + ancestor.id + ". " + ancestor.writeDefinitionWithStartLetters(language) + "\n";
            if (ancestor.conjectured_equivalent_concepts.size()>0)
                output = output + "Alternative definitions:\n";
            for (int j=0; j<ancestor.conjectured_equivalent_concepts.size(); j++)
            {
                Concept equiv_concept = (Concept)ancestor.conjectured_equivalent_concepts.elementAt(j);
                output = output + equiv_concept.id + ". " + equiv_concept.writeDefinitionWithStartLetters(language) + "\n";
            }
        }
        return output;
    }

    /** This gets the equivalent conjectures of this concept and
     * its ancestors and the ancestors of the equivalent concepts.
     */

    public void getRelevantEquivalences(Theory theory)
    {
        relevant_equivalences = new SortableVector();
        Vector equiv_strings = new Vector();
        Concept conc = this;
        for (int i=0; i<theory.concepts.size(); i++)
        {
            Concept first_conc_to_try = (Concept)theory.concepts.elementAt(i);
            Vector concs_to_try = (Vector)first_conc_to_try.conjectured_equivalent_concepts.clone();
            concs_to_try.insertElementAt(first_conc_to_try, 0);
            boolean add_all_equivs = false;
            Vector equivs_to_add = new Vector();
            for (int j=0; j<concs_to_try.size(); j++)
            {
                Concept conc_to_try = (Concept)concs_to_try.elementAt(j);
                if (theory.specification_handler.leftSkolemisedImpliesRight(conc, conc_to_try, true) ||
                        theory.specification_handler.leftSkolemisedImpliesRight(conc_to_try, conc, true))
                {
                    add_all_equivs = true;
                    break;
                }
            }
            if (add_all_equivs)
            {
                equivs_to_add = new Vector();
                for (int j=1; j<concs_to_try.size(); j++)
                {
                    Concept conc_to_try = (Concept)concs_to_try.elementAt(j);
                    Equivalence new_equiv = new Equivalence(first_conc_to_try, conc_to_try, "");
                    equivs_to_add.addElement(new_equiv);
                    String def_string = new_equiv.writeConjecture("prolog");
                    if (!equiv_strings.contains(def_string))
                    {
                        equiv_strings.addElement(def_string);
                        relevant_equivalences.addElement(new_equiv);
                    }
                }
            }
        }
        for (int i=0; i<conjectured_equivalent_concepts.size(); i++)
        {
            Concept cec = (Concept)conjectured_equivalent_concepts.elementAt(i);
            cec.getRelevantEquivalences(theory);
            for (int j=0; j<cec.relevant_equivalences.size(); j++)
            {
                Equivalence new_equiv = (Equivalence)cec.relevant_equivalences.elementAt(j);
                String def_string = new_equiv.writeConjecture("prolog");
                if (!equiv_strings.contains(def_string))
                {
                    equiv_strings.addElement(def_string);
                    relevant_equivalences.addElement(new_equiv);
                }
            }
        }
    }

    /** This gets the implicate conjectures for which the left hand
     * concept is implied by this concept.
     */

    public void getRelevantImplicates(Theory theory)
    {
        relevant_implicates = new SortableVector();
        Vector all_imps = theory.implicates;
        Vector output = new Vector();
        SortableVector relevant_imps = new SortableVector();
        Vector all_concepts = (Vector)conjectured_equivalent_concepts.clone();
        all_concepts.insertElementAt(this, 0);
        int num_equivs = all_concepts.size();
        for (int i=0; i<num_equivs; i++)
        {
            Concept equiv = (Concept)all_concepts.elementAt(i);
            for (int j=0; j<equiv.ancestors.size(); j++)
                all_concepts.addElement(equiv.ancestors.elementAt(j));
        }
        for (int i=0; i<all_concepts.size(); i++)
        {
            Concept conc = (Concept)all_concepts.elementAt(i);
            for (int j=0; j<all_imps.size(); j++)
            {
                Implicate imp = (Implicate)all_imps.elementAt(j);
                if (theory.specification_handler.leftSkolemisedImpliesRight(conc, imp.premise_concept, true))
                {
                    String conj_string = imp.writeConjecture("prolog");
                    boolean dont_add = false;
                    for (int k=0; k<relevant_imps.size(); k++)
                    {
                        Implicate old_rel_imp = (Implicate)relevant_imps.elementAt(k);
                        if (old_rel_imp.subsumes(imp))
                            dont_add = true;
                        if (imp.subsumes(old_rel_imp))
                        {
                            relevant_imps.removeElementAt(k);
                            k--;
                        }
                    }
                    if (!dont_add)
                        relevant_imps.addElement(imp, "writeConjecture(prolog)");
                    all_imps.removeElementAt(j);
                    j--;
                }
            }
        }
        for (int i=0; i<relevant_imps.size(); i++)
        {
            Implicate rel_imp = (Implicate)relevant_imps.elementAt(i);
            relevant_implicates.addElement(rel_imp);
        }
    }

    /** Returns the vector of specifications that this concept shares with the
     * given concept.
     */

    public Vector sharedSpecifications(Concept other_concept)
    {
        Vector output = new Vector();
        for (int i=0; i<specifications.size(); i++)
        {
            Specification spec = (Specification)specifications.elementAt(i);
            boolean found = false;
            for (int j=0; j<other_concept.specifications.size() && !found; j++)
            {
                Specification other_spec = (Specification)other_concept.specifications.elementAt(j);
                if (other_spec==spec)
                {
                    output.addElement(spec);
                    found =  true;
                }
            }
        }
        return output;
    }

    /** This returns the positives for this concept (those entities
     * which have non-empty output for this concept).  */

    public Vector positives()
    {
        Vector output = new Vector();
        for (int i=0; i<datatable.size(); i++)
        {
            Row row = (Row)datatable.elementAt(i);
            if (!row.tuples.isEmpty())
                output.addElement(row.entity);
        }
        return output;
    }

    /** Same as positives() except return a vector of entities as opposed
     to a vector of strings. This returns the positives for this
     concept (those entities which have non-empty output for this
     concept).  */
    public Vector positiveEntities()
    {
        Vector output = new Vector();
        for (int i=0; i<datatable.size(); i++)
        {
            Row row = (Row)datatable.elementAt(i);
            if (!row.tuples.isEmpty())
            {
                Entity entity_to_add = new Entity(row.entity);
                output.addElement(entity_to_add);
            }
        }
        return output;
    }


    /** This returns the negatives for this concept (those entities
     * which have empty output for this concept).  */

    public Vector negatives()
    {
        Vector output = new Vector();
        for (int i=0; i<datatable.size(); i++)
        {
            Row row = (Row)datatable.elementAt(i);
            if (row.tuples.isEmpty())
                output.addElement(row.entity);
        }
        return output;
    }

    public Vector getPositives()
    {
        Vector output = new Vector();
        for (int i=0; i<datatable.size(); i++)
        {
            Row row = (Row)datatable.elementAt(i);
            if (!row.tuples.isEmpty())
                output.addElement(row.entity);
        }
        return output;
    }

    /** Given a vector of entity strings, returns a vector which contains the
     subset of those from original vector which are also in the
     concept */
    public Vector getSubset(Vector vector)
    {
        Vector output = new Vector();
        Vector concept_entities = getPositives();

        for(int i=0; i<vector.size(); i++)
        {
            String entity = (String)vector.elementAt(i);
            if(concept_entities.contains(entity))
                output.addElement(entity);

        }
        return output;
    }

    /** Returns true if the concept has the same value for the given
     entity, false otherwise */

    public boolean sameValueOfEntity(Row given_row)
    {
        boolean output = false;
        int i = 0;
        Row row = new Row();

        while (i<datatable.size())
        {
            row = (Row)datatable.elementAt(i);
            if (row.equals(given_row))
            {
                output = true;
                break;
            }
            i++;
        }
        return output;
    }


    /** This checks whether the positives (those entities which have
     * non-empty output for this concept) contain all the given entities
     * (given as strings).  */

    public boolean positivesContains(Vector positives)
    {
        for(int i=0; i<positives.size(); i++)
        {
            String positive = (String)positives.elementAt(i);
            Row row = datatable.rowWithEntity(positive);
            if(row.tuples.isEmpty())
                return false;
        }
        return true;
    }

    /** This checks whether the positives (those entities which have
     * non-empty output for this concept) contain the given entity
     * string.  */

    public boolean positivesContains(String entity_string)
    {
        Row row = datatable.rowWithEntity(entity_string);
        if(row.tuples.isEmpty())
            return false;
        return true;
    }

    /** This checks whether the positives (those entities which have non-empty
     * output for this concept) contain one of the given entities (given as strings).
     */

    public boolean positivesContainOneOf(Vector counterexamples)
    {
        for(int i=0; i<counterexamples.size(); i++)
        {
            String counterexample = (String)counterexamples.elementAt(i);
            //System.out.println("in positivesContainOneOf - counterexample is " + counterexample);
            Row row = datatable.rowWithEntity(counterexample);
            //System.out.println("row.entity is " + row.entity);
            if(!row.tuples.isEmpty())
            {
                //System.out.println("in positivesContainOneOf -- in here");
                return true;
            }
        }
        return false;
    }

    /** Given two concepts, returns the vector of entities they have in
     * common [needs testing]*/

    public Vector sharedEntities(Concept other_concept)
    {
        Vector concept1_entities = this.positives();
        Vector concept2_entities = other_concept.positives();

        Vector shared_entities = new Vector();

        for(int i=0; i<concept1_entities.size(); i++)
        {
            String entity = (String)concept1_entities.elementAt(i);
            if(concept2_entities.contains(entity))
                shared_entities.addElement(entity);
        }

        return shared_entities;
    }

    /** This writes out the skolemised representation of this concept.
     */

    public String writeSkolemisedRepresentation()
    {
        String output = skolemised_representation.relation_columns.toString();
        return output;
    }

    /** This collects all the skolemised representations of the specifications
     * in this concept's definition and puts them into the output vector.
     */

    public void setSkolemisedRepresentation()
    {
        for (int i=0; i<specifications.size(); i++)
        {
            Specification spec = (Specification)specifications.elementAt(i);
            Vector skol_reps = spec.skolemisedRepresentation();
            for (int j=0; j<skol_reps.size(); j++)
            {
                Vector skol_rep = (Vector)skol_reps.elementAt(j);
                skolemised_representation.relation_columns.addElement(skol_rep);
                String rel_name = (String)skol_rep.elementAt(0);
                Vector cols = (Vector)skol_rep.elementAt(1);
                for (int k=0; k<cols.size(); k++)
                {
                    String variable = (String)cols.elementAt(k);
                    if (variable.substring(0,1).equals(":"))
                    {
                        if (!skolemised_representation.ground_variables.contains(variable))
                            skolemised_representation.ground_variables.addElement(variable, "toString()");
                    }
                    else
                    if (!skolemised_representation.variables.contains(variable))
                        skolemised_representation.variables.addElement(variable);
                    String rel_pos_string = rel_name + "(" + Integer.toString(k) + ")";
                    Vector rel_positions =
                            (Vector)skolemised_representation.relation_position_hashtable.get(rel_pos_string);
                    if (rel_positions==null)
                    {
                        rel_positions = new Vector();
                        skolemised_representation.relation_position_hashtable.put(rel_pos_string, rel_positions);
                    }
                    if (!rel_positions.contains(variable))
                        rel_positions.addElement(variable);

                    Vector var_rel_positions =
                            (Vector)skolemised_representation.variable_relation_position_hashtable.get(variable);
                    if (var_rel_positions==null)
                    {
                        var_rel_positions = new Vector();
                        skolemised_representation.variable_relation_position_hashtable.put(variable, var_rel_positions);
                    }
                    if (!var_rel_positions.contains(rel_pos_string))
                        var_rel_positions.addElement(rel_pos_string);
                }
                Vector col_vector = (Vector)skolemised_representation.relation_hashtable.get(rel_name);
                if (col_vector == null)
                {
                    col_vector = new Vector();
                    skolemised_representation.relation_hashtable.put(rel_name, col_vector);
                }
                if (!col_vector.contains(cols))
                    col_vector.addElement(cols);
            }
        }
    }

    /** Returns a vector of all the conjectures in the theory involving
     * this concept */
    public Vector getConjectures(Theory theory, Concept other_concept)
    {
        Vector output = new Vector();
        //Vector concept_vector = new Vector();
        //concept_vector.addElement(concept);
        //double min = 0.0;
        //get NearEquivalences (don't use method above as that clears the
        //near_equivalences vector for the concept)
        //getSubsumptionNearImplications();

        //make NearEquivalences
        //System.out.println("starting getConjectures");
        if (other_concept.types.toString().equals(types.toString()) && other_concept!=this)
        {
            //System.out.println("got same types");
            Vector tuple_score_vector = new Vector();
            Vector counterexamples = new Vector();
            double score = datatable.quickPercentageMatchWith(theory, other_concept.datatable, counterexamples);


            NearEquivalence near_equiv = new NearEquivalence(this, other_concept, counterexamples, score);
            NearEquivalence other_near_equiv = new NearEquivalence(other_concept, this, counterexamples, score);
            //System.out.println("near_equiv is " + near_equiv.writeConjecture("ascii"));
            output.addElement(near_equiv);//, "score");
        }

        //make Near Implications
        if(other_concept.arity==arity)
        {
            //System.out.println("got same arity");
            double score =0.0;
            double percent_min = 0.0;
            //System.out.println("other_concept is " + other_concept.writeDefinition("ascii"));
            //System.out.println("other_concept.arity: " + other_concept.id + " " + other_concept.arity
            //			   + ", and arity: " + id  + " " + arity);
            if (!other_concept.is_entity_instantiations)
            {
                //System.out.println("here");
                if (other_concept.isGeneralisationOf(this)==-1)
                {
                    //System.out.println("1 ");
                    Vector counterexamples = other_concept.empiricallySubsumes(this, percent_min);
                    //System.out.println("2 - counterexamples is " + counterexamples);
                    if (counterexamples != null && !(counterexamples.size()==0))
                    {
                        int total = 0;
                        if(arity==0||arity==1)
                            total = getPositives().size();
                        else
                            total = datatable.size();
                        score = total - counterexamples.size();
                        ////System.out.println(" score is " + score);
                        score = score/total;
                        ////System.out.println(" score is " + score);
                        score = score*100;
                        //System.out.println(" total is " + total);
                        //System.out.println(" counterexamples.size() is " + counterexamples.size());
                        //System.out.println(" score is " + score);
                        if (score>= percent_min)
                        {
                            //System.out.println("got lh_concept: " + this.writeDefinition("ascii"));
                            //System.out.println("got rh_concept: " + other_concept.writeDefinition("ascii"));
                            //System.out.println("got counterexamples " + counterexamples);
                            //System.out.println("score>= percent_min");
                            NearImplication near_imp = new NearImplication(this, other_concept, counterexamples, score);
                            //System.out.println("near_imp is " +near_imp.writeConjecture("ascii"));
                            output.addElement(near_imp);//, "score");
                        }
                    }
                }
                if (isGeneralisationOf(other_concept)==-1)
                {
                    //System.out.println("5 ");
                    Vector counterexamples = empiricallySubsumes(other_concept, percent_min);
                    //System.out.println("6 - counterexamples is " + counterexamples);
                    if (counterexamples != null && !(counterexamples.size()==0))
                    {
                        int total = 0;
                        if(other_concept.arity==0||other_concept.arity==1)
                            total = other_concept.getPositives().size();
                        else
                            total = other_concept.datatable.size();
                        score = total - counterexamples.size();
                        ////System.out.println(" score is " + score);
                        score = score/total;
                        ////System.out.println(" score is " + score);
                        score = score*100;
                        //System.out.println(" total is " + total);
                        //System.out.println(" counterexamples.size() is " + counterexamples.size());
                        //System.out.println(" score is " + score);
                        if (score>= percent_min)
                        {
                            //System.out.println("got lh_concept: " + other_concept.writeDefinition("ascii"));
                            //System.out.println("got rh_concept: " + this.writeDefinition("ascii"));
                            //System.out.println("got counterexamples " + counterexamples);
                            //System.out.println("score>= percent_min");
                            NearImplication near_imp = new NearImplication(other_concept, this, counterexamples, score);//[should be imp?]
                            output.addElement(near_imp);//, "score");
                            //System.out.println("near_imp is " +near_imp.writeConjecture("ascii"));
                        }
                    }
                }

                //make implications
                if (other_concept.isGeneralisationOf(this)==0)
                {
                    //this -> other
                    Implication imp = new Implication(this, other_concept, "dummy_id");
                    //System.out.println("imp is " +imp.writeConjecture("ascii"));
                    output.addElement(imp);
                }

                if (isGeneralisationOf(other_concept)==0)
                {
                    //other -> this
                    Implication imp = new Implication(other_concept, this,"dummy_id");
                    //System.out.println("imp is " +imp.writeConjecture("ascii"));
                    output.addElement(imp);
                }

                if (other_concept.isGeneralisationOf(this)==1)
                {
                    //other -> this
                    Equivalence equiv = new Equivalence(other_concept, this,"dummy_id");
                    //System.out.println("equiv is " +equiv.writeConjecture("ascii"));
                    output.addElement(equiv);
                }
            }
        }
        return output;
    }



    /** Reconstructs a concept and returns it
     */

    public TheoryConstituent reconstructConcept_old(Theory theory, AgentWindow window)
    {
        window.writeToFrontEnd("started reconstructConcept(" + this.writeDefinition() + ")");
        Vector steps_to_force = this.construction_history;
        window.writeToFrontEnd("steps_to_force are " + steps_to_force);
        Vector step_strings = new Vector();
        String flat_step_string = "";

        if ((this.construction_history).isEmpty())
            return (Concept)this;


        for(int i=0; i<steps_to_force.size(); i++)
        {
            Vector flat_step = (Vector)steps_to_force.elementAt(i);
            String new_id = (String)flat_step.elementAt(0);
            String prod_rule = (String)flat_step.elementAt(1);
            Vector flat_old_concepts = (Vector)flat_step.elementAt(2);
            Vector parameters = (Vector)flat_step.elementAt(3);

            String concept_list_string = "";
            for(int j=0; j<flat_old_concepts.size(); j++)
                concept_list_string =  concept_list_string + (String)flat_old_concepts.elementAt(j) +  " ";

            String parameters_string = parameters.toString();
            String trimmed_parameters_string = new String();
            int l = parameters_string.length();
            int n = 0;
            try
            {
                while(0<=n && n<l)
                {
                    n = parameters_string.indexOf("], [");
                    trimmed_parameters_string = trimmed_parameters_string + parameters_string.substring(0,n) + "],[";
                    parameters_string = parameters_string.substring(n+4,l);
                    l = parameters_string.length();
                }
                trimmed_parameters_string = trimmed_parameters_string + parameters_string;
            }

            catch(Exception e)
            {
                trimmed_parameters_string = trimmed_parameters_string + parameters_string;
            }

            flat_step_string = new_id+" = "+concept_list_string+prod_rule+" "+trimmed_parameters_string.trim();


            //String flat_step_string = (String)flat_step.toString();
            step_strings.addElement(flat_step_string.trim());
        }
        //go through the string.whenever find ], [, replace it with ],[
        // agenda.addForcedStep(agenda_line, false);
        //Step forcedstep = guider.forceStep(agenda_line, theory);
        //output = forcedstep.result_of_step;

        window.writeToFrontEnd("step_strings are " + step_strings);
        TheoryConstituent tc = theory.guider.forceSteps(step_strings, theory);
        window.writeToFrontEnd("finished reconstructConcept(" + this.writeDefinition() + ")");

        if(!(tc instanceof Concept))
        {
            window.writeToFrontEnd("returning " + tc);
        }

        if(tc instanceof Concept)
        {
            window.writeToFrontEnd("returning " + ((Concept)tc).writeDefinition());
        }
        //Concept output = (Concept)tc;
        //System.out.println("finished reconstructConcept()");
        //return output;
        return  tc;
    }


    /** Reconstructs a concept and returns it
     */

    public TheoryConstituent reconstructConcept(Theory theory, AgentWindow window)
    {
        window.writeToFrontEnd("started reconstructConcept(" + this.writeDefinition() + ")");
        Vector steps_to_force = this.construction_history;
        Vector step_strings = new Vector();
        String flat_step_string = "";

        if ((this.construction_history).isEmpty())
            return (Concept)this;

        for(int i=0; i<steps_to_force.size(); i++)
        {
            Vector flat_step = (Vector)steps_to_force.elementAt(i);
            String new_id = (String)flat_step.elementAt(0);
            String prod_rule = (String)flat_step.elementAt(1);
            Vector flat_old_concepts = (Vector)flat_step.elementAt(2);
            Vector parameters = (Vector)flat_step.elementAt(3);

            String concept_list_string = "";
            for(int j=0; j<flat_old_concepts.size(); j++)
                concept_list_string =  concept_list_string + (String)flat_old_concepts.elementAt(j) +  " ";

            String parameters_string = parameters.toString();
            String trimmed_parameters_string = new String();
            int l = parameters_string.length();
            int n = 0;
            try
            {
                while(0<=n && n<l)
                {
                    n = parameters_string.indexOf("], [");
                    trimmed_parameters_string = trimmed_parameters_string + parameters_string.substring(0,n) + "],[";
                    parameters_string = parameters_string.substring(n+4,l);
                    l = parameters_string.length();
                }
                trimmed_parameters_string = trimmed_parameters_string + parameters_string;
            }

            catch(Exception e)
            {
                trimmed_parameters_string = trimmed_parameters_string + parameters_string;
            }

            flat_step_string = new_id+" = "+concept_list_string+prod_rule+" "+trimmed_parameters_string.trim();
            step_strings.addElement(flat_step_string.trim());
        }

        // agenda.addForcedStep(agenda_line, false);
        //Step forcedstep = guider.forceStep(agenda_line, theory);
        //output = forcedstep.result_of_step;

        window.writeToFrontEnd("step_strings are " + step_strings);
        TheoryConstituent tc = theory.guider.forceSteps(step_strings, theory);
        window.writeToFrontEnd("here -- so far reconstructConcept gives " + tc.writeTheoryConstituent());




        if(tc instanceof Concept)
        {
            if(((Concept)tc).writeDefinition().equals((this).writeDefinition()))
                return (Concept)tc;
        }
        else
        {
            for(int i=0;i<theory.conjectures.size();i++)
            {
                Conjecture conj = (Conjecture)theory.conjectures.elementAt(i);
                if(conj instanceof Equivalence)
                {
                    Equivalence equiv = (Equivalence)conj;
                    if (equiv.lh_concept.writeDefinition().equals((this).writeDefinition()))
                        return equiv.lh_concept;
                    if (equiv.rh_concept.writeDefinition().equals((this).writeDefinition()))
                        return equiv.rh_concept;
                }
                if(conj instanceof Implication)
                {
                    Implication imp = (Implication)conj;
                    if (imp.lh_concept.writeDefinition().equals((this).writeDefinition()))
                        return imp.lh_concept;
                    if (imp.rh_concept.writeDefinition().equals((this).writeDefinition()))
                        return imp.rh_concept;
                }
                if(conj instanceof NonExists)
                {
                    NonExists nexists = (NonExists)conj;
                    if (nexists.concept.writeDefinition().equals((this).writeDefinition()))
                        return nexists.concept;
                }
            }
        }

        return  tc;
    }







/**
 public Concept reconstructConcept(Theory theory)
 {
 //System.out.println("OI YOU - started reconstructConcept(theory)");

 //System.out.println("trying to reconstruct this: " + this.toString());
 Vector steps_to_force = this.construction_history;
 Vector step_strings = new Vector();
 //System.out.println("steps_to_force.size() is " + steps_to_force.size());

 for(int i=0; i<steps_to_force.size(); i++)
 {
 Vector flat_step = (Vector)steps_to_force.elementAt(i);
 String new_id = (String)flat_step.elementAt(0);
 String prod_rule = (String)flat_step.elementAt(1);
 Vector flat_old_concepts = (Vector)flat_step.elementAt(2);
 Vector parameters = (Vector)flat_step.elementAt(3);
 //Vector concept_list = flat_step.conceptList();
 String concept_list_string = "";
 String parameters_string = "";
 //System.out.println("1) \n");

 for(int j=0; j<flat_old_concepts.size(); j++)
 concept_list_string =  concept_list_string + (String)flat_old_concepts.elementAt(j) +  " ";

 //System.out.println("2) \n");
 //System.out.println("parameters.size() is " + parameters.size());
 //System.out.println("parameters is " + parameters);

 //for(int j=0; j<parameters.size(); j++)
 // {
 //   if(parameters.elementAt(j) instanceof String)
 //    {
 //	String parameter = (String)parameters.elementAt(j);
 //	parameters_string = parameters_string + parameter +  " ";
 //     }
 //   if(parameters.elementAt(j) instanceof Vector)
 //     {
 //	Vector parameter = (Vector)parameters.elementAt(j);
 //	parameters_string = parameters_string + parameter.toString() +  " ";
 //     }
 // }
 //HERE - comment out
 for(int j=0; j<parameters.size()-1; j++)
 {
 //System.out.println("3) j is " + j + "\n");
 if(parameters.elementAt(j) instanceof String)
 {
 //System.out.println(" got a string\n");
 String parameter = (String)parameters.elementAt(j);
 parameters_string = parameters_string + parameter +  ", ";
 //System.out.println("parameters_string so far is " + parameters_string);
 }
 if(parameters.elementAt(j) instanceof Vector)
 {
 //System.out.println(" got a vector\n");
 Vector parameter = (Vector)parameters.elementAt(j);
 parameters_string = parameters_string + parameter.toString() +  ",";
 //System.out.println("parameters_string so far is " + parameters_string);
 }
 }
 //System.out.println("4) up to parameters.size(), i.e " + parameters.size() + "\n");

 if(parameters.elementAt(parameters.size()) instanceof String)
 {
 //System.out.println(" got a string\n");
 String parameter = (String)parameters.elementAt(parameters.size());
 parameters_string = parameters_string + parameter;
 //System.out.println("parameters_string so far is " + parameters_string);
 }

 if(parameters.elementAt(parameters.size()) instanceof Vector)
 {
 //System.out.println(" got a vector\n");
 Vector parameter = (Vector)parameters.elementAt(parameters.size());
 parameters_string = parameters_string + parameter.toString();
 //System.out.println("parameters_string so far is " + parameters_string);
 }
 //HERE - end comment out
 parameters_string = parameters.toString();


 //System.out.println("5) parameters_string NOW is " + parameters_string);
 //System.out.println("6) parameters_string.trim() is " + parameters_string.trim());
 String step_string = new_id+" = "+prod_rule+" "+ concept_list_string+parameters_string;
 String step_string = new_id+" = "+prod_rule+" "+ concept_list_string+"["+parameters_string+"]";
 //System.out.println("the " + i + "th step_string is ");
 //System.out.println(step_string);
 step_strings.addElement(step_string);
 }


 //System.out.println("starting to forcesteps");
 String ss = (String)step_strings.elementAt(0);//need to put in another for loop
 Step step = theory.guider.forceStep(ss, theory);
 //TheoryConstituent tc = theory.guider.forceSteps(step_strings, theory);
 //System.out.println("done forceSteps");
 //Concept output = (Concept)tc;
 Concept output = new Concept();
 //System.out.println("returning output");
 return output;



 //return (Concept)theory.guider.forceSteps(step_strings, theory);
 }
 */

    /** return true if the ascii string expression of the two concepts
     are the same, false otherwise. This isn't really good enough
     */
    // public boolean equals(String string)
//   {

//       String concept_string = this.writeDefinition("ascii");
//       if(string.equals("")&& concept_string.equals(""))
// 	return true;

//       if(string.equals(concept_string))
// 	return true;

//       else
// 	return false;
//   }



    public boolean equals(Object object)
    {
        String concept_string = this.writeDefinition("ascii");

        if(object instanceof String)
        {
            if(((String)object).equals("")&& concept_string.equals(""))
                return true;
            if(((String)object).equals(concept_string))
                return true;
        }
        if(object instanceof Concept)
        {
            Concept concept1 = (Concept)object;
            String concept1_string = concept1.writeDefinition("ascii");

            if(concept1_string.equals("")&& concept_string.equals(""))
                return true;
            if(concept1_string.equals(concept_string))
                return true;
        }

        else
            System.out.println("Warning: Concept equals method - not a string or concept");

        return false;

    }





    //  /** This updates the datatable of a concept given an entity to
//     * delete from the theory.
//     */

//   public void updateDatatableToDeleteEntity(Vector all_concepts, Entity entity)
//   {
//     datatable.removeEntity(entity.name);
//   }


    /** returns a vector of the entities which have empty rows [], in the
     * datatable for this concept, where other rows have entries, eg there
     * are rows with numbers such as [[1,2],[2,2]], rather than
     * just [[]]*/
    public Vector nonLemConcept()
    {
        Vector output = new Vector();

        Vector negatives = new Vector();
        Vector positives = new Vector();
        Vector entities_with_entries = new Vector();

        for (int i=0; i<datatable.size(); i++)
        {
            Row row = (Row)datatable.elementAt(i);
            String string_row = row.tuples.toString();
            //System.out.println("string_row is " + string_row);

            if (row.tuples.isEmpty())
                negatives.addElement(row.entity);

            if (!(row.tuples.isEmpty()))
            {
                if(string_row.equals("[[]]"))
                    positives.addElement(row.entity);

                if(!(string_row.equals("[[]]")))
                    entities_with_entries.addElement(row.entity);
            }
        }

        //System.out.println("negatives is " + negatives);
        //System.out.println("positives is " + positives);
        //System.out.println("entities_with_entries is " + entities_with_entries);

        output.addElement(negatives);
        output.addElement(positives);
        output.addElement(entities_with_entries);

        return output;
    }



    /** Given a concept, returns the first concept in the list of
     * concepts which is an object of interest concept and has the same
     * domain as the given concept.
     */

    public Concept findObjectOfInterestConcept(Vector concepts)
    {
        for(int i=0; i<concepts.size();i++)
        {
            Concept poss_concept = (Concept)concepts.elementAt(i);
            if(poss_concept.is_object_of_interest_concept && poss_concept.domain.equals(this.domain))
                return poss_concept;
            break;
        }
        Concept output = new Concept();
        return output;
    }


    /** returns true if the concept is already in the theory, false
     * otherwise */

    public boolean theoryContainsConcept(Vector concepts)
    {
        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if(this.writeDefinition().equals(concept.writeDefinition()))
                return true;
        }
        return false;
    }


}
