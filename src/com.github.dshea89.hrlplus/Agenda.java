package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.io.Serializable;

/** A class representing an agenda of theory formation tasks to do. At present the only
 * tasks are theory formation steps.
 * @author Simon Colton, started 13th December 1999
 * @version 1.0
 */

public class Agenda implements Serializable
{
    /** The current step number.
     */

    public int step_number = 0;

    /** Whether or not to use a segregated search.
     */

    public boolean use_segregated_search = false;

    /** The segregation categorisation for this agenda.
     */

    public Categorisation segregation_categorisation = new Categorisation();

    /** Whether to keep a record of the steps which weren't allowed for
     * limit reasons.
     */

    public boolean keep_not_allowed_steps = false;

    /** The hashtable of concepts in the theory, with the key being the id of the
     * concept.
     */

    public Hashtable concept_hashtable = new Hashtable();

    /** A vector of forced ids which resulted in a non-existence conjecture.
     */

    public Vector non_existent_concept_ids = new Vector();

    /** A hashtable to stop repeated steps being carried out (when forced).
     */

    public Hashtable repeated_forced_steps = new Hashtable();

    /** The list of steps which have been forced (conjecture driven concept formation).
     */

    public Vector forced_steps = new Vector();

    /** A hashtable for looking up a concept which was given an id by the theory
     * forcing that step.
     */

    public Hashtable concept_forced_names = new Hashtable();

    /** The set of steps to force the theory to produce instead of
     * the normal theory formation step (conjecture-driven concept formation).
     * These are not steps, but strings of the form:
     * "NewConcept = PR OldConcept (OldConcept2) Parameterisation"
     * and the agenda records the id of the new concept which is produced, for
     * use in later forced steps (or the id of a re-invented concept).
     */

    public Vector steps_to_force = new Vector();

    /** A flag to tell the addConcept function that the concept is being added for a second
     * time because the agenda is not being expanded automatically.
     */

    public boolean second_addition = false;

    /** Whether to expand the agenda or not. If running in a breadth first search, then there
     * is no need to put all pairs of concepts on the agenda, rather this can be done when
     * the concept reaches the top. This reduces the memory allocation required for the agenda.
     */

    public boolean expand_agenda = true;

    /** Which tier of the agenda is currently being used
     */

    public int current_tier = 0;

    /** Whether or not the search is tiered (in terms of the production rules)
     */

    public boolean is_tiered = false;

    /** The list of tiers in the agenda.
     */

    public Vector agenda_tiers = new Vector();

    /** The limit for the complexity of any concept which will be produced.
     */

    public int complexity_limit = 1000;

    /** The limit for the number of items which can be stored on the agenda.
     */

    public int agenda_size_limit = 10000;

    /** A search strategy - randomly permute the concepts.
     */

    public boolean random = false;

    /** The type of objects of interest in the categorisation to be learned.
     */

    public String object_types_to_learn = "";

    /** The list of entities which must be learned, eg. 2,3,5,7,...
     */

    public Vector positives_for_forward_lookahead = new Vector();

    /** The list of entities which are not to be learned.
     */

    public Vector negatives_for_forward_lookahead = new Vector();

    /** Whether or not to use the patterns heuristic (for identifyint a particular
     * concept).
     */

    public boolean use_forward_lookahead = false;

    /** Whether or not to use the scores for placing pattern-suggested items
     * in the agenda.
     */

    public boolean use_scores = false;

    /** A set of concepts, such as integer, which it is not worth using in
     * a compose step with any other concept. Usually the entity concepts.
     */

    public Vector dont_compose_with = new Vector();

    /** The unary steps still to do in the agenda.
     */

    public Vector unary_steps = new Vector();

    /** The binary steps still to do in the agenda.
     */

    public Vector binary_steps = new Vector();

    /** The exhaustive list of steps in the agenda.
     */

    public Vector exhaustive_steps = new Vector();

    /** The urgent unary steps still to do in the agenda.
     */

    public Vector urgent_unary_steps = new Vector();

    /** The urgent binary steps still to do in the agenda.
     */

    public Vector urgent_binary_steps = new Vector();

    /** The list of steps not allowed because of it breaking some limit.
     */

    public Vector not_allowed_steps = new Vector();

    /** Whether or not to use a delayed best first search.
     */

    public boolean best_first_delayed = false;

    /** The number of steps to delay the best first search for.
     */

    public int best_first_delay_steps = 0;

    /** Whether or not to use the interestingness of concepts
     * to order the agenda line in the lists to complete (i.e., a best first search).
     */

    public boolean best_first = false;

    /** Whether to do a depth first search - each new concept is explored fully
     * before moving on to the next one. If this is set to false, HR assumes a
     * depth first (or best first) search.
     */

    public boolean depth_first = false;

    /** The list of concepts ordered in terms of their
     * overall interestingness measure (those which have agenda items left).
     */

    public Vector live_ordered_concepts = new Vector();

    /** The list of concepts ordered in terms of their
     * overall interestingness measure (those which have agenda items left).
     */

    public Vector all_ordered_concepts = new Vector();

    /** The construction step to be forced next.
     */

    public Step forced_step = new Step();

    /** Whether to allow the use of unary rules. The unary rules may be turned off
     * in an agency environment to stop the repetition of steps.
     */

    public boolean use_unary_rules = true;

    /** Default constructor - adds 20 tiers to the agenda
     */

    public Agenda()
    {
        for (int i=0; i<20; i++)
            agenda_tiers.addElement(new Vector());
    }

    /** Adds all possible steps involving the given concept to the agenda.
     */

    public void addConcept(Concept concept, Vector all_concepts, Theory theory)
    {
        // Firstly, add this to the concept hashtable

        concept_hashtable.put(concept.id, concept);

        // Firstly, dont develop this concept if the dont develop flag is set.

        if (concept.dont_develop)
            return;

        // Firstly see if it fails the complexity limit. If so, no items are
        // added to the agenda.

        if (!second_addition)
            addConceptToOrderedList(all_ordered_concepts, concept);

        if (concept.complexity>=complexity_limit)
        {
            if (keep_not_allowed_steps)
            {
                Step unary_agenda_line = new Step();
                Vector concept_list = new Vector();
                concept_list.addElement(concept);
                unary_agenda_line.addElement(concept_list);
                not_allowed_steps.addElement(unary_agenda_line);
                unary_agenda_line.trimToSize();
                not_allowed_steps.trimToSize();
            }
            return;
        }

        // Add the concept to the ordered list //

        if (!second_addition)
            addConceptToOrderedList(live_ordered_concepts, concept);

        // Next set up some vectors and some values.

        if (!use_forward_lookahead)
        {
            if (use_unary_rules && !second_addition)
            {
                Step unary_agenda_line = new Step();
                Vector unary_concept_list = new Vector();
                unary_concept_list.addElement(concept);
                unary_agenda_line.addElement(unary_concept_list);
                unary_concept_list.trimToSize();
                unary_agenda_line.trimToSize();

                // Now add the single concept to the agenda. When it gets to the top,
                // all the unary production rules and parameters will be added.
                // If the depth first flag is true, then the step is added to the
                // top of the agenda, otherwise it is added to the bottom of the agenda.

                if (depth_first)
                    exhaustive_steps.insertElementAt(unary_agenda_line,0);
                else
                    exhaustive_steps.addElement(unary_agenda_line);
            }

            // Now add the pairs of concepts to the agenda. When they get to the top,
            // all the binary production rules and parameters will be added. Note that
            // only pairs (a,a-something) are added to the agenda.

            // Note that this is limited by the size of the agenda. Pairs will be
            // added until the agenda contains the limit (or less). Again, if the depth
            // first flag is true, the step is added to the top of the agenda, otherwise
            // to the bottom.

            if (!expand_agenda)
                return;

            int items = itemsInAgenda();
            int limit = all_ordered_concepts.size();
            if (items + all_ordered_concepts.size() > agenda_size_limit)
                limit = agenda_size_limit - items;

            int counter=0;
            for(int i=0;i<limit;i++)
            {
                Step binary_agenda_line = new Step();
                Vector binary_concept_list = new Vector();
                Concept other_concept = (Concept)all_ordered_concepts.elementAt(i);
                if (!dont_compose_with.contains(other_concept.id)
                        && !other_concept.dont_develop && !conceptsAreSegregated(concept, other_concept))
                {
                    binary_concept_list.addElement(concept);
                    binary_concept_list.addElement(other_concept);
                    binary_concept_list.trimToSize();

                    // Add the secondary concept to the live list, unless it is already there //

                    if (!live_ordered_concepts.contains(other_concept))
                        addConceptToOrderedList(live_ordered_concepts, other_concept);

                    binary_agenda_line.addElement(binary_concept_list);
                    binary_agenda_line.trimToSize();

                    // If the proposed binary step will produce a concept which is too
                    // complex, then add it to the not_allowed_steps

                    if (concept.complexityWith(other_concept) >= complexity_limit &&
                            keep_not_allowed_steps)
                        not_allowed_steps.addElement(binary_agenda_line);

                    if (concept.complexityWith(other_concept) < complexity_limit &&
                            concept.position_in_theory >= other_concept.position_in_theory)
                    {
                        if (second_addition)
                        {
                            exhaustive_steps.insertElementAt(binary_agenda_line,1+counter);
                            counter++;
                        }
                        else
                        {
                            if (depth_first)
                                exhaustive_steps.insertElementAt(binary_agenda_line,0);
                            else
                                exhaustive_steps.addElement(binary_agenda_line);
                        }
                    }
                }
            }
        }

        if (use_forward_lookahead)
        {
            Vector unary_concept_list = new Vector();
            unary_concept_list.addElement(concept);
            Vector u_prs = theory.unary_rules;
            Vector b_prs = theory.binary_rules;

            // Now add the single concept to the agenda. When it gets to
            // the top, all the unary production rules and parameters will
            // be added.  Each production rule will return a score for the
            // likelihood of a pattern. This will enable the positioning
            // of the item <concept,production rule> in the
            // list_to_complete. If the score is zero, the agenda item
            // gets put in the second agenda list.

            for (int i=0;i<u_prs.size();i++)
            {
                Step unary_agenda_line = new Step();
                unary_agenda_line.addElement(unary_concept_list);
                ProductionRule pr = (ProductionRule)u_prs.elementAt(i);
                int score = pr.patternScore(unary_concept_list,all_concepts,
                        positives_for_forward_lookahead,
                        negatives_for_forward_lookahead,
                        object_types_to_learn);
                if (score < negatives_for_forward_lookahead.size() && !use_scores)
                    score = 0;
                Long l = new Long(negatives_for_forward_lookahead.size());
                Vector pr_vector = new Vector();
                pr_vector.addElement(pr);
                unary_agenda_line.addElement(pr_vector);

                if (score==0)
                    exhaustive_steps.addElement(unary_agenda_line);
                else
                    urgent_unary_steps.addElement(unary_agenda_line);
            }

            // Now add the pairs of concepts to the agenda. When they get to the top,
            // all the binary production rules and parameters will be added. Note that
            // only pairs (a,a-something) are added to the agenda.

            // Note that this is limited by the size of the agenda. Pairs will be
            // added until the agenda contains the limit (or less). If the score is
            // zero, the agenda item gets put in the third list.

            int items = itemsInAgenda();
            int limit = all_concepts.size();
            if (items + all_concepts.size() > agenda_size_limit)
                limit = agenda_size_limit - items;

            for(int i=0;i<limit;i++)
            {
                Vector binary_concept_list = new Vector();
                Concept other_concept = (Concept)all_concepts.elementAt(i);
                if (!dont_compose_with.contains(other_concept.id))
                {
                    binary_concept_list.addElement(concept);
                    binary_concept_list.addElement(other_concept);
                    if (concept.complexityWith(other_concept) < complexity_limit &&
                            concept.position_in_theory >= other_concept.position_in_theory)
                    {
                        for (int j=0;j<b_prs.size();j++)
                        {
                            ProductionRule pr = (ProductionRule)b_prs.elementAt(j);
                            Step binary_agenda_line = new Step();
                            binary_agenda_line.addElement(binary_concept_list);
                            int score = pr.patternScore(binary_concept_list,all_concepts,
                                    positives_for_forward_lookahead,
                                    negatives_for_forward_lookahead,
                                    object_types_to_learn);
                            Vector pr_vector = new Vector();
                            if (score < negatives_for_forward_lookahead.size() && !use_scores)
                                score = 0;
                            pr_vector.addElement(pr);
                            binary_agenda_line.addElement(pr_vector);
                            if (score==0)
                                exhaustive_steps.addElement(binary_agenda_line);
                            else
                                urgent_binary_steps.addElement(binary_agenda_line);
                        }
                    }
                }
            }
        }
    }

    /** This checks whether the two given concepts have segregated ancestors.
     */

    public boolean conceptsAreSegregated(Concept c1, Concept c2)
    {
        Vector segregation_positions = new Vector();
        for (int i=0; i<c1.ancestor_ids.size(); i++)
        {
            String ancestor_id = (String)c1.ancestor_ids.elementAt(i);
            String segregation_position = segregationPosition(ancestor_id);
            if (!segregation_positions.contains(segregation_position) && !segregation_position.equals(""))
                segregation_positions.addElement(segregation_position);
        }
        for (int i=0; i<c2.ancestor_ids.size(); i++)
        {
            String ancestor_id = (String)c2.ancestor_ids.elementAt(i);
            String segregation_position = segregationPosition(ancestor_id);
            if (!segregation_positions.contains(segregation_position) && !segregation_position.equals(""))
                segregation_positions.addElement(segregation_position);
            if (segregation_positions.size()>1)
                return true;
        }
        return false;
    }

    private String segregationPosition(String id)
    {
        for (int i=0; i<segregation_categorisation.size(); i++)
        {
            Vector segregation_category = (Vector)segregation_categorisation.elementAt(i);
            if (segregation_category.contains(id))
                return Integer.toString(i);
        }
        return "";
    }

    /** Returns the next valid step at the top of the agenda.
     */

    public Step nextStep(Vector unary_rules, Vector binary_rules, Theory theory)
    {
        if (!steps_to_force.isEmpty())
        {
            String step_to_force = (String)steps_to_force.elementAt(0);
            steps_to_force.removeElementAt(0);
            boolean step_is_ok = true;
            Step step = getStepFromString(step_to_force, theory);
            forced_steps.addElement(step_to_force);
            if (step==null)
            {
                String step_arising_name = step_to_force.substring(0,step_to_force.indexOf(" ="));
                non_existent_concept_ids.addElement(step_arising_name);
                return nextStep(unary_rules, binary_rules, theory);
            }
            Concept concept_produced =
                    (Concept)repeated_forced_steps.get(step.asString());
            if (concept_produced != null)
            {
                if (!step.concept_arising_name.equals(""))
                    concept_forced_names.put(step.concept_arising_name, concept_produced);
                return nextStep(unary_rules, binary_rules, theory);
            }
            step.forced = true;
            step.trimToSize();
            return step;
        }

        Step output = new Step();
        Vector list_to_complete = new Vector();
        int i=0;

        // First determine which list to complete the next step will be taken from //

        if (!urgent_unary_steps.isEmpty())
            list_to_complete = urgent_unary_steps;
        else
        {
            if (!urgent_binary_steps.isEmpty())
                list_to_complete = urgent_binary_steps;
            else
                list_to_complete = exhaustive_steps;
        }
        Vector all_concepts = theory.concepts;

        // Check the other tiers if there is nothing left in tier 0.

        if (list_to_complete.isEmpty())
        {
            boolean found = false;
            i=0;
            while (!found && i<agenda_tiers.size())
            {
                Vector agenda_tier = (Vector)agenda_tiers.elementAt(i);
                if (!agenda_tier.isEmpty())
                {
                    found = true;
                    list_to_complete = agenda_tier;
                }
                i++;
            }
            if (found && (best_first || best_first_delayed) && i!=current_tier)
                live_ordered_concepts = (Vector)all_ordered_concepts.clone();
            current_tier = i;
        }
        else
            current_tier = 0;

        // Return empty step if the list to complete is still empty

        if (list_to_complete.isEmpty())
            return output;

        // Next find the first step with the most interesting concept in the list //

        Step agenda_line = new Step();
        Vector concept_list = new Vector();
        int agenda_pos = 0;
        if (best_first || (best_first_delayed && step_number >= best_first_delay_steps))
        {
            i=0;
            Concept interesting_concept = (Concept)live_ordered_concepts.elementAt(0);
            boolean found = false;
            while (!found && i<list_to_complete.size())
            {
                agenda_line = (Step)list_to_complete.elementAt(i);
                concept_list = (Vector)agenda_line.elementAt(0);
                Concept concept_in_agenda = (Concept)concept_list.elementAt(0);
                if (concept_in_agenda==interesting_concept)
                {
                    list_to_complete.removeElementAt(i);
                    list_to_complete.insertElementAt(agenda_line, 0);
                    found = true;
                    agenda_pos = 0;
                }
                if (concept_list.size()==2 && !found)
                {
                    Concept concept_in_agenda2 = (Concept)concept_list.elementAt(1);
                    if (concept_in_agenda2==interesting_concept)
                    {
                        list_to_complete.removeElementAt(i);
                        list_to_complete.insertElementAt(agenda_line, 0);
                        found = true;
                        agenda_pos = 0;
                    }
                }
                i++;
            }
            if (!found)
            {
                live_ordered_concepts.removeElementAt(0);
                return nextStep(unary_rules, binary_rules, theory);
            }
        }
        else
        {
            if (random)
            {
                agenda_pos = (new Double(Math.random()*list_to_complete.size())).intValue();
                agenda_line = (Step)list_to_complete.elementAt(agenda_pos);
                concept_list = (Vector)agenda_line.elementAt(0);
                list_to_complete.removeElementAt(agenda_pos);
                list_to_complete.insertElementAt(agenda_line, 0);
                agenda_pos = 0;
            }
            else
            {
                agenda_line = (Step)list_to_complete.elementAt(0);
                concept_list = (Vector)agenda_line.elementAt(0);
                agenda_pos = 0;
            }
        }

        // If the line in the agenda contains just a concept, then expand it with
        // the production rules, put these into tiers (if necessary) and start again

        if (agenda_line.size()==1)
        {
            if (concept_list.size()==1)
            {
                Concept concept = (Concept)concept_list.elementAt(0);
                if (!expand_agenda)
                {
                    expand_agenda=true;
                    second_addition = true;
                    addConcept(concept, all_concepts, theory);
                    second_addition = false;
                    expand_agenda=false;
                }

                if (unary_rules.size()==0)
                    list_to_complete.removeElementAt(agenda_pos);
                else
                {
                    putIntoTiers(agenda_line, concept_list, unary_rules);
                    if (agenda_line.size()==1)
                        list_to_complete.removeElementAt(agenda_pos);
                }
            }
            if (concept_list.size()==2)
            {
                if (binary_rules.size()==0)
                    list_to_complete.removeElementAt(agenda_pos);
                else
                {
                    putIntoTiers(agenda_line, concept_list, binary_rules);
                    if (agenda_line.size()==1)
                        list_to_complete.removeElementAt(agenda_pos);
                }
            }
            return nextStep(unary_rules, binary_rules, theory);
        }

        // If the line in the agenda contains just a concept and a production rule,
        // then expand it with the set of parameterisations for the step and start over

        if (agenda_line.size()==2)
        {
            Vector prodrules_left = (Vector)agenda_line.elementAt(1);
            ProductionRule pr = (ProductionRule)prodrules_left.elementAt(0);

            Vector parameter_list = pr.allParameters(concept_list, theory );
            if(parameter_list.size()>0)
                agenda_line.addElement(parameter_list);
            else
                prodrules_left.removeElementAt(0);
            if(prodrules_left.size()==0)
                list_to_complete.removeElementAt(agenda_pos);
            return nextStep(unary_rules, binary_rules, theory);
        }

        // If the line is complete, then return this as the next step

        if (agenda_line.size()==3)
        {
            Vector prodrules_left = (Vector)agenda_line.elementAt(1);
            ProductionRule pr = (ProductionRule)prodrules_left.elementAt(0);
            Vector parameter_list = (Vector)agenda_line.elementAt(2);
            Vector parameters = (Vector)parameter_list.elementAt(0);
            parameter_list.removeElementAt(0);
            if (parameter_list.size()==0)
            {
                agenda_line.removeElementAt(2);
                prodrules_left.removeElementAt(0);
            }
            if (prodrules_left.size()==0)
                list_to_complete.removeElementAt(agenda_pos);
            output.addElement(concept_list);
            output.addElement(pr);
            output.addElement(parameters);
        }
        output.trimToSize();
        return output;
    }

    /** This splits the agenda items into tiers.
     */

    public void putIntoTiers(Vector agenda_line, Vector concept_list, Vector pr_list)
    {
        int i=0;
        Vector new_pr_list = (Vector)pr_list.clone();
        while (i<new_pr_list.size())
        {
            ProductionRule pr = (ProductionRule)new_pr_list.elementAt(i);
            if (pr.tier>0)
            {
                Vector agenda_tier = (Vector)agenda_tiers.elementAt(pr.tier);
                Vector temp_v = new Vector();
                temp_v.addElement(pr);
                Step new_agenda_line = new Step();
                new_agenda_line.addElement(concept_list);
                new_agenda_line.addElement(temp_v);
                agenda_tier.addElement(new_agenda_line);
                new_pr_list.removeElementAt(i);
            }
            else
                i++;
        }
        if (!new_pr_list.isEmpty())
            agenda_line.addElement((Vector)new_pr_list);
    }

    /** Counts how many agenda items there are in the set of lists
     * to complete.
     */

    public int itemsInAgenda()
    {
        int output = unary_steps.size();
        output = output + binary_steps.size();
        output = output + exhaustive_steps.size();
        output = output + urgent_binary_steps.size();
        output = output + urgent_unary_steps.size();
        for (int i=0; i<agenda_tiers.size(); i++)
            output = output + ((Vector)agenda_tiers.elementAt(i)).size();
        return output;
    }

    // Note that this not only adds the higher scoring items to the top
    // of the list, but also prefers certain production rules first.
    // exists > split > negate > size > match > compose

    private void addConceptToOrderedList(Vector list, Concept concept)
    {
        if (best_first || best_first_delayed)
        {
            boolean placed_the_step = false;
            int i=0;
            while (i<list.size() && !placed_the_step)
            {
                Concept other = (Concept)list.elementAt(i);
                if (concept.interestingness > other.interestingness)
                {
                    list.insertElementAt(concept,i);
                    placed_the_step = true;
                }
                i++;
            }
            if (!placed_the_step)
                list.addElement(concept);
        }
        else
            list.addElement(concept);
        list.trimToSize();
    }

    /** To force the agenda to order its concepts - after a new concept has been
     * added.
     */

    public void orderConcepts()
    {
        if (best_first || best_first_delayed)
        {
            Vector copy_all_concepts = new Vector();
            Vector copy_live_concepts = new Vector();
            for (int i=0;i<all_ordered_concepts.size();i++)
            {
                Concept c = (Concept)all_ordered_concepts.elementAt(i);
                addConceptToOrderedList(copy_all_concepts, c);
                if (live_ordered_concepts.contains(c))
                    addConceptToOrderedList(copy_live_concepts, c);
            }
            all_ordered_concepts = copy_all_concepts;
            live_ordered_concepts = copy_live_concepts;
        }
        all_ordered_concepts.trimToSize();
        live_ordered_concepts.trimToSize();
    }

    /** This collates the top n agenda items into one vector.
     */

    public Vector listToComplete(boolean look_at_not_allowed, int vector_size)
    {
        Vector output = new Vector();
        int i=0;
        if (look_at_not_allowed)
        {
            while (output.size() < vector_size && i<not_allowed_steps.size())
            {
                addSteps(output, (Step)not_allowed_steps.elementAt(i), vector_size);
                i++;
            }
        }
        if (!look_at_not_allowed)
        {
            while (output.size() < vector_size && i<exhaustive_steps.size())
            {
                addSteps(output, (Step)exhaustive_steps.elementAt(i), vector_size);
                i++;
            }
        }

        if (!look_at_not_allowed)
        {
            for (int j=0; j<agenda_tiers.size(); j++)
            {
                Vector agenda_tier = (Vector) agenda_tiers.elementAt(j);
                while(output.size() < vector_size && i< agenda_tier.size())
                {
                    addSteps(output, (Step)agenda_tier.elementAt(i), vector_size);
                    i++;
                }
            }
        }
        return output;
    }

    private void addSteps(Vector output, Step step, int output_limit)
    {
        Vector concepts = (Vector)step.elementAt(0);
        Vector concept_ids = new Vector();
        for (int i=0; i<concepts.size(); i++)
            concept_ids.addElement(((Concept)concepts.elementAt(i)).id);
        if (step.size()==1)
            output.addElement(concept_ids.toString());
        if (step.size()==2)
        {
            Vector prs = (Vector)step.elementAt(1);
            Vector pr_names = new Vector();
            for (int i=0; i<prs.size(); i++)
            {
                String pr_name = ((ProductionRule)prs.elementAt(i)).getName();
                output.addElement(concept_ids + " " + pr_name);
            }
        }
        if (step.size()==3)
        {
            Vector prs = (Vector)step.elementAt(1);
            Vector pr_names = new Vector();
            Vector params = (Vector)step.elementAt(2);
            String first_pr_name = ((ProductionRule)prs.elementAt(0)).getName();
            int i=0;
            while (i<params.size() && output.size() < output_limit)
            {
                output.addElement(concept_ids.toString() + " " + first_pr_name + " " +
                        ((Vector)params.elementAt(i)).toString());
                i++;
            }
            i=1;
            while (i<prs.size() && output.size() < output_limit)
            {
                String pr_name = ((ProductionRule)prs.elementAt(i)).getName();
                output.addElement(concept_ids.toString() + " " + pr_name);
                i++;
            }
        }
    }

    /** When a change has been made to the search settings, some not-allowed
     * steps may be allowed now, so they are added to the agenda. Other changes
     * also need to be made.
     */

    public void makeChanges()
    {
        Vector new_not_allowed = new Vector();
        for (int i=0; i<not_allowed_steps.size(); i++)
        {
            Step not_allowed_step = (Step)not_allowed_steps.elementAt(i);
            Vector concept_list = (Vector)not_allowed_step.elementAt(0);
            Concept concept = (Concept)concept_list.elementAt(0);
            boolean is_allowed = true;
            if (concept.complexity >= complexity_limit)
            {
                is_allowed = false;
                new_not_allowed.addElement(not_allowed_step);
            }
            if (concept_list.size()==2)
            {
                Concept second_concept = (Concept)concept_list.elementAt(1);
                if (concept.complexityWith(second_concept)>=complexity_limit)
                {
                    is_allowed = false;
                    new_not_allowed.addElement(not_allowed_step);
                }
            }
            if (is_allowed)
            {
                exhaustive_steps.addElement(not_allowed_step);
                if (concept_list.size()==1)
                {
                    if (!live_ordered_concepts.contains(concept))
                        addConceptToOrderedList(live_ordered_concepts, concept);
                }
            }
        }
        not_allowed_steps = new_not_allowed;
    }

    /** Given a string of the form "Newconcept = PR oldconcept (oldconcept2) parameters",
     * this function turns it into a step.
     */

    public Step getStepFromString(String step, Theory theory)
    {
        Step output = new Step();
        String concept_name = step.substring(0,step.indexOf(" ="));
        step = step.substring(step.indexOf("=")+2,step.length());
        String extra_conditions = "";
        if (step.indexOf(":")>=0)
        {
            extra_conditions = step.substring(step.indexOf(":"),step.length());
            step = step.substring(0,step.indexOf(":")-1);
        }
        String parameters = step.substring(step.indexOf("["), step.length());
        step = step.substring(0, step.indexOf("["));
        String first_part = step.substring(0, step.indexOf(" "));
        step = step.substring(step.indexOf(" ") + 1, step.length());
        String second_part = step.substring(0, step.indexOf(" "));
        step = step.substring(step.indexOf(" "), step.length());
        Vector concept_list = new Vector();
        ProductionRule pr = new ProductionRule();

        if (!step.equals(" "))
        {
            //System.out.println("~~~~ first_part is " + first_part);


            String third_part = step.substring(1,step.length()-1);
            Concept first_concept = getConcept(first_part, theory);
            //System.out.println("first_concept is " +first_concept.writeDefinition()); //problem here

            if (first_concept==null)
            {
                //System.out.println("~~~~ third_part is " +third_part);

                //System.out.println("the first_concept is null");
                return null;
            }

            //System.out.println("1");
            concept_list.addElement(first_concept);
            Concept second_concept = getConcept(second_part, theory);
            //System.out.println("second_concept is " + second_concept.writeDefinition());


            if (second_concept==null)
            {
                //System.out.println("getStepFromString - 2");
                return null;
            }
            //System.out.println("2");
            concept_list.addElement(second_concept);
            //System.out.println("3");
            pr = theory.productionRuleFromName(third_part);
            //System.out.println("4");
        }
        else
        {
            Concept first_concept = getConcept(first_part, theory);
            if (first_concept==null)
            {
                System.out.println("getStepFromString - 3");
                return null;
            }
            concept_list.addElement(first_concept);
            pr = theory.productionRuleFromName(second_part);
        }

        output.addElement(concept_list);
        output.addElement(pr);
        output.addElement(getVector(parameters, pr.getName(), theory));

        if (extra_conditions.indexOf("dont_develop")>=0)
            output.dont_develop = true;

        output.concept_arising_name = concept_name;
        output.trimToSize();


//  if(!(output==null))
//       System.out.println("step_to_try is " + output.asString());
//     else
//       System.out.println("output is null");
        return output;
    }

    public Concept getConcept(String id, Theory theory)
    {
        Concept output = theory.getConcept(id);
        if (output.id.equals(""))
        {
            if (non_existent_concept_ids.contains(id))
                return null;

            else
                return (Concept)concept_forced_names.get(id);
        }
        return output;
    }

    public Concept getConcept(String id)
    {
        if (non_existent_concept_ids.contains(id))
            return null;
        if (concept_forced_names.containsKey(id))
            return (Concept)concept_forced_names.get(id);
        if (concept_hashtable.containsKey(id))
            return (Concept)concept_hashtable.get(id);
        return null;
    }

    private Vector getVector(String s, String pr_name, Theory theory)
    {
        Vector output = new Vector();
        s = s.trim();
        if (s.indexOf(", ")<0)
        {
            String temp_s = "";
            for (int i=0; i<s.length(); i++)
                if (s.substring(i,i+1).equals(","))
                    temp_s = temp_s + ", ";
                else
                    temp_s = temp_s + s.substring(i,i+1);
            s = temp_s;
        }
        if (pr_name.equals("forall"))
        {
            Concept concept = getConcept(s.substring(1, s.indexOf(",")), theory);
            output.addElement(concept);
            output.addElement(getVector(s.substring(s.indexOf(",")+1, s.indexOf("]")+1),"",theory));
            output.addElement(getVector(s.substring(s.indexOf("], [")+3,s.length()-1),"",theory));
            return output;
        }
        if (pr_name.equals("split"))
        {
            output.addElement(getVector(s.substring(1,s.indexOf("]")+1),"",theory));
            output.addElement(getVector(s.substring(s.indexOf("], [")+3,s.length()-1),"",theory));
            return output;
        }
        s = s.substring(1,s.length()-1);
        while (s.indexOf(", ")>0)
        {
            output.addElement(s.substring(0,s.indexOf(", ")));
            s = s.substring(s.indexOf(", ")+2,s.length());
        }
        output.addElement(s);
        return output;
    }

    public void recordNonExistentConcept(Step step)
    {
        if (!step.concept_arising_name.equals(""))
            non_existent_concept_ids.addElement(step.concept_arising_name);
    }

    public void recordForcedName(Step step, Concept concept)
    {
        if (!step.concept_arising_name.equals(""))
        {
            concept_forced_names.put(step.concept_arising_name, concept);
            System.out.println("step.concept_arising_name is " + step.concept_arising_name);
            System.out.println("putting " + step.asString() + ", " +  concept.writeDefinition());
            repeated_forced_steps.put(step.asString(), concept);
        }
    }

    /** This adds a forced step to the agenda, unless the step has already
     * been forced, in which case it add to the concepts and names hashtable,
     * so that the result of performing the step can be found.
     */

    public void addForcedStep(String step_string, boolean to_top)
    {
        if (to_top)
            steps_to_force.insertElementAt(step_string, 0);
        steps_to_force.addElement(step_string);
    }
}
