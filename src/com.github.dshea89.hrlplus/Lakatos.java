package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
//import java.io.Serializable;
import java.io.*;
import java.util.Enumeration;

/** A class for the Lakatos techniques.
 *
 * @author Alison Pease, started 18th April 2002.
 * @version 1.0 */

public class Lakatos implements Serializable
{

    /** The front end of the theory
     */

    FrontEnd front_end = new FrontEnd();

    /** The guider of the theory
     */

    Guide guider = new Guide();

    /** The agenda of the theory.
     */

    Agenda agenda = new Agenda();

    /** The concepts of the theory.
     */

    Vector concepts = new Vector();

    /** The equivalence conjectures of the theory.
     */

    Vector equivalences = new Vector();

    /** The non-existence conjectures of the theory.
     */

    Vector non_existences = new Vector();

    /** The implicate conjectures of the theory.
     */
    Vector implicates = new Vector();

    /** The implications conjectures of the theory.
     */

    Vector implications = new Vector();

    /** The entities of the theory.
     */

    Vector entities = new Vector();

    /** The production rules of the theory.
     */

    Vector production_rules = new Vector();

    /** The storage handler object for this theory.
     */

    public StorageHandler storage_handler = new StorageHandler();


    /** The variables for the lakatos methods */

    //discussion

    public boolean use_threshold_to_add_conj_to_theory = false;
    public boolean use_threshold_to_add_concept_to_theory = false;
    public double threshold_to_add_conj_to_theory = 0.0;
    public double threshold_to_add_concept_to_theory = 0.0;


    //number of times to work independently
    public boolean test_max_num_independent_work_stages = true;
    public int max_num_independent_work_stages = 10;//max number of independent work stages to take


    // how many steps in each IWP
    public boolean test_num_independent_steps = true;
    public int num_independent_steps = 10;




    //surrender
    public boolean test_number_modifications_surrender = false;
    public boolean test_interestingness_surrender = false;
    public boolean compare_average_interestingness_surrender = false;
    public boolean test_plausibility_surrender = false;
    public boolean test_domain_application_surrender = false;
    public int number_modifications_surrender = 0;
    public double interestingness_th_surrender = 0.0;
    public double plausibility_th_surrender = 0.0;
    public double domain_application_th_surrender = 0.0;

    // public double interestingness_threshold

    //monster-barring
    double monster_barring_min = 0.0;
    String monster_barring_type = "vaguetospecific";
    int monster_barring_culprit_min = 0;


    boolean use_breaks_conj_under_discussion = false;
    boolean accept_strictest = true;
    boolean use_percentage_conj_broken = false;
    boolean use_culprit_breaker = false;
    boolean use_culprit_breaker_on_conj = false;
    boolean use_culprit_breaker_on_all = false;

    //exception-barring
    boolean use_counterexample_barring = false;
    boolean use_piecemeal_exclusion = false;


    /** Finds the variables for each method */

    public void findLakatosVariables()
    {
        //might need to pass the theory.frontend in as an arg
        String variables = front_end.force_parameter_list.getSelectedItem();
        // if (e.getSource()==front_end.force_parameter_list)
//       {
// 	guider.writeForceString(this);
//       }

        //   theory.front_end.lakatos_variables_string_text.setText("step = " + primary + " " + secondary);
        return;

    }



    /** Performs concept barring.  Given a (new)concept, it finds all
     the near equivalence conjectures associated with it. For each
     conjecture it finds the counterexamples */

    public void performConceptBarring(Concept new_concept)//change to piecemealExclusion
    {
        // Altered by Simon and Alison on April 15th to:
        // 1) record the counterexamples = X
        // 2) return a list of concepts for which X is a subset
        // 3) order this list smallest first
        // 4) take the first element in this list (we want to exclude the
        // fewest possible) = concept_to_exclude
        // 5) (supposing near-equivalence is N <~> E) force the step
        // applying negate to N & X, and to E & X
        // (to get the new concepts N & ~X and E & ~X respectively)
        // 6) add these new concepts to the agenda

        if(new_concept.arity==1)
        {

            for(int i=0;i< new_concept.near_equivalences.size();i++)
            {
                NearEquivalence near_equivalence = (NearEquivalence)new_concept.near_equivalences.elementAt(i);
                Concept nearly_equiv_concept = near_equivalence.rh_concept;
                Vector counterexamples = near_equivalence.counterexamples;
                Vector concepts_to_exclude = new Vector();
                Vector counters_in_new_concept = new Vector();
                Vector counters_in_nearly_equiv_concept = new Vector();

                for (int j=0;j<counterexamples.size();j++)
                {
                    String countereg = (String)counterexamples.elementAt(j);
                    Vector counterexample_vector = new Vector();
                    counterexample_vector.addElement(countereg);
                    if (new_concept.positivesContains(counterexample_vector))
                    {
                        counters_in_new_concept.addElement(countereg);
                    }
                    else counters_in_nearly_equiv_concept.addElement(countereg);
                }

                if (counters_in_nearly_equiv_concept.size()==0)//all counters on side of new_concept
                    makeConceptForEquivConj(new_concept, nearly_equiv_concept, counterexamples);
                else
                if (counters_in_new_concept.size()==0)//all counters on side of nearly_equiv_concept
                    makeConceptForEquivConj(nearly_equiv_concept, new_concept, counterexamples);
                else
                {
                    makeConceptForImpConj(new_concept, nearly_equiv_concept, counters_in_new_concept);//counters both sides
                    makeConceptForImpConj(nearly_equiv_concept, new_concept, counters_in_nearly_equiv_concept);
                }
            }
        }
    }


    /** for each concept in theory, if it has arity 1 and contains the counters, add to vector app_con_pair
     */
    public void makeConceptForEquivConj(Concept gen_concept, Concept subset_concept, Vector counterexamples)
    {
        Tuples app_con_pairs = new Tuples();//applicability_conjecture_pairs
        for(int i=0; i < concepts.size(); i++)
        {
            Concept concept_to_exclude = (Concept)concepts.elementAt(i);
            if (concept_to_exclude.arity==1 &&
                    concept_to_exclude.positivesContains(counterexamples))
            {
                Vector app_con_pair = new Vector();
                app_con_pair.addElement(Double.toString(concept_to_exclude.applicability));
                app_con_pair.addElement(concept_to_exclude);
                app_con_pairs.addElement(app_con_pair);
            }
        }

        app_con_pairs.sort();
        Vector app_con_pair_to_exclude = (Vector)app_con_pairs.elementAt(0);//take first

        double counter_size = (new Double(counterexamples.size())).doubleValue();
        double entities_size = (new Double(entities.size())).doubleValue();
        double c_over_e = counter_size/entities_size;
        Concept concept_to_exclude = (Concept)app_con_pair_to_exclude.elementAt(1);
        String app_string = (String)app_con_pair_to_exclude.elementAt(0);
        if (app_string.equals(Double.toString(c_over_e))) //we only want concepts which exactly cover counters
        {
            Concept object_of_interest_concept = new Concept();
            for(int i=0; i< concept_to_exclude.generalisations.size();i++)
            {
                object_of_interest_concept = (Concept)concept_to_exclude.generalisations.elementAt(i);
                if (object_of_interest_concept.is_object_of_interest_concept) break;
            }
            String agenda_line1 = "id_of_dummy1 = " + concept_to_exclude.id + " " +
                    object_of_interest_concept.id
                    + " negate [] ";

            agenda.addForcedStep(agenda_line1, false);
            //System.out.println("\n\n---------");
            //System.out.println("---------");
            //System.out.println("makeEquivConjecture");
            //System.out.println("gen_concept = " + gen_concept.id);
            //System.out.println("gen_concept = " + gen_concept.writeDefinition("otter"));
            //System.out.println("gen_concept = " + gen_concept.datatable.toTable());
            //System.out.println("subset_concept = " + subset_concept.id);
            //System.out.println("subset_concept = " + subset_concept.writeDefinition("otter"));
            //System.out.println("subset_concept = " + subset_concept.datatable.toTable());
            //System.out.println("concept_to_exclude = " + concept_to_exclude.id);
            //System.out.println("concept_to_exclude = " + concept_to_exclude.writeDefinition("otter"));
            //System.out.println("concept_to_exclude = " + concept_to_exclude.datatable.toTable());
            //System.out.println("Counterexamples: "+counterexamples.toString());

            String agenda_line2 = "compose_" + "id_of_dummy2 = " +
                    "id_of_dummy1 " + gen_concept.id + " compose " +
                    "[1]" + " : dont_develop";	//?
            agenda.addForcedStep(agenda_line2, false);
            //	System.out.println(agenda_line1);
            //System.out.println(agenda_line2);
            //now HR will make equivalence conjecture
        }
    }

    public void makeConceptForImpConj(Concept new_concept, Concept nearly_equiv_concept, Vector counters_in_new_concept)
    {
        Tuples app_con_pairs = new Tuples();

        for(int i=0; i < concepts.size(); i++)
        {
            Concept concept_to_exclude = (Concept)concepts.elementAt(i);
            if (concept_to_exclude.arity==1 &&       //[why?]
                    concept_to_exclude.positivesContains(counters_in_new_concept))
            {
                Vector app_con_pair = new Vector();
                app_con_pair.addElement(Double.toString(concept_to_exclude.applicability));
                app_con_pair.addElement(concept_to_exclude);
                app_con_pairs.addElement(app_con_pair);
            }
        }

        app_con_pairs.sort();

        Vector app_con_pair_to_exclude = (Vector)app_con_pairs.elementAt(0);//take first

        double counter_size = (new Double(counters_in_new_concept.size())).doubleValue();
        double entities_size = (new Double(entities.size())).doubleValue();
        double c_over_e = counter_size/entities_size;
        Concept concept_to_exclude = (Concept)app_con_pair_to_exclude.elementAt(1);
        String app_string = (String)app_con_pair_to_exclude.elementAt(0);
        if (app_string.equals(Double.toString(c_over_e)))
        {
            Concept object_of_interest_concept = new Concept();
            for(int i=0; i< concept_to_exclude.generalisations.size();i++)
            {
                object_of_interest_concept = (Concept)concept_to_exclude.generalisations.elementAt(i);
                if (object_of_interest_concept.is_object_of_interest_concept) break;
            }
            String agenda_line1 = "id_of_dummy1 = " + concept_to_exclude.id + " " +
                    object_of_interest_concept.id + " negate [] ";//  problem here?

            agenda.addForcedStep(agenda_line1, false);

            String agenda_line2 = "compose_" + "id_of_dummy2 = " +
                    "id_of_dummy1 " + new_concept.id + " compose " +
                    "[1]" + " : dont_develop";	//?
            agenda.addForcedStep(agenda_line2, false);
            //now HR will make implication conjecture
            //System.out.println(agenda_line1);
            //System.out.println(agenda_line2);

        }
        //    else System.out.println("cannot find concept \n ");
    }




    /** looks through Datatable for a given concept to see whether there
     is a single culprit entity which is the same for all of the
     counterexamples (i.e. all the entities in the vector
     counterexamples). If so returns it.
     public Entity getCulpritBreaker(DataTable datatable, Vector counterexamples)
     {
     Entity culprit_breaker = new Entity();

     if(!(counterexamples.isEmpty()))
     {
     Entity current_entity = (Entity)counterexamples.elementAt(0);
     Row row = (Row)datatable.elementAt(i);
     if (!row.tuples.isEmpty())
     output.addElement(row.entity);

     culprit_breaker
     }

     for(int i=0; i<counterexamples.size(); i++)
     {
     Entity current_entity = (Entity)counterexamples.elementAt(i);

     }


     for (int i=0; i<datatable.size(); i++)
     {
     Row row = (Row)datatable.elementAt(i);
     if (!row.tuples.isEmpty())
     output.addElement(row.entity);
     }



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

     This checks whether the positives (those entities which have
     non-empty output for this concept) contain the given entity
     string.

     public boolean positivesContains(String entity_string)
     {
     Row row = datatable.rowWithEntity(entity_string);
     if(row.tuples.isEmpty())
     return false;
     return true;
     }


     return culprit_breaker;
     }

     */

    /** Given a vector of entities, returns a vector of the n smallest
     * concepts which cover them all - here - for lhs/rhs the teacher
     * needs to specify before sending request, ie. by the time it gets
     * here the counters are all from one side of the conj - can extend
     * this to "limit to" (as well as bar - i.e. send a vector of
     * positives instead of negatives) [needs testing]*/



    public Vector getNConceptsWhichCoverGivenEntities(Vector entities_to_cover, int n)
    {

        //System.out.println("started getNConceptsWhichCoverGivenEntities");
        //System.out.println("entities_to_cover are " + entities_to_cover);
        Vector concepts_which_cover_counters = new Vector();

        //is the vector concepts ordered at all? how? by size? this assumes by size - need to check this
        for (int i=0;i<concepts.size();i++)
        {
            for(int j=0; j<n; j++)
            {
                Concept current_concept = (Concept)concepts.elementAt(i);
                if (current_concept.positivesContains(entities_to_cover))
                    concepts_which_cover_counters.addElement(current_concept);
            }
        }
        //System.out.println("finished getNConceptsWhichCoverGivenEntities");
        //System.out.println("the concepts are :");

        for (int i=0; i<concepts_which_cover_counters.size(); i++)
        {
            Concept concept = (Concept)concepts_which_cover_counters.elementAt(i);
            //System.out.println(concept.writeDefinition("ascii"));
        }

        return concepts_which_cover_counters;
    }

    /**************************************************************************************/
    //                              METHOD 1                                              //
    //                             SURRENDER                                              //
    /**************************************************************************************/

    /** Given a faulty conjecture, simply return the String that
     * <conjecture> is not worth defending
     */

    public Vector surrender(Conjecture conj)
    {
        Vector output = new Vector();
        String response_to_conj = conj.writeConjecture("ascii") + " is not worth modifying";
        output.addElement(response_to_conj);
        return output;
    }



    /**************************************************************************************/
    //                              METHOD 2                                              //
    //                         PIECEMEAL EXCLUSION                                        //
    /**************************************************************************************/

    /** Exclude the counterexamples - by forming a concept which
     * covers all the counters (and none of the positives), and
     * excluding this concept. Given a faulty conjecture P <~> Q, and
     * two vectors of counterexamples - the first containing objects
     * in P but not Q, and the second containing objects in Q but not
     * P, return a vector of possible piecemeal exclusion
     * modifications to the conjecture
     */


    public Vector piecemealExclusion(Conjecture conj_to_modify, Theory theory, AgentWindow window)
    {
        System.out.println("___________________________ \n\n");
        System.out.println("started piecemealExclusion on " + conj_to_modify.writeConjecture("ascii"));
        Vector output = new Vector();

        if (conj_to_modify instanceof NearEquivalence)
        {
            NearEquivalence nequiv = (NearEquivalence)conj_to_modify;
            Concept left_concept = (Concept)nequiv.lh_concept;
            Concept right_concept = (Concept)nequiv.rh_concept;
            window.writeToFrontEnd("The counterexamples are " + nequiv.counterexamples);
            output = piecemealExclusion1("equiv", nequiv.counterexamples, left_concept, right_concept, theory, window);

            //test -- tues 23rd aug 2005 need to change a lot
            System.out.println("MONDAY ~ 5 output is " + output);

            //test to see whether we need to perform counterexample barring,
            //ie if the output from concept-barring is empty and there are 3
            //or fewer counterexamples
            boolean no_output = output.isEmpty();
            boolean small_num_counters = nequiv.counterexamples.size()<3;
            if(no_output && small_num_counters)
            {
                System.out.println("MONDAY ~ 6 -- trying counter-barring");
                Vector new_output = performCounterexampleBarring("equiv",left_concept, right_concept, nequiv.counterexamples, theory, window);
                System.out.println("new_output is "+ new_output);
                // if(!new_output.isEmpty())
// 	      new_output.num_modifications ++;
                return new_output;
            }





            //can't remember why had this -- omit??
            // boolean got_concept = false;
// 	for(int i=0; i<output.size(); i++)
// 	  {
// 	    if(output.elementAt(i) instanceof Concept)
// 	      {
// 		Concept concept = (Concept)output.elementAt(i);
// 		concept.lakatos_method = "piecemeal_exclusion";
// 		got_concept = true;
// 	      }
// 	  }

// 	System.out.println("c is " + got_concept);








            //end of test -- tues 23rd aug 2005

        }

        if (conj_to_modify instanceof NearImplication)
        {
            NearImplication nimp = (NearImplication)conj_to_modify;
            Concept left_concept = (Concept)nimp.lh_concept;
            Concept right_concept = (Concept)nimp.rh_concept;
            //System.out.println("wed night --- got a near impl, and the counterexamples are " + nimp.counterexamples);
            window.writeToFrontEnd("The counterexamples are " + nimp.counterexamples);
            output = piecemealExclusion1("imp", nimp.counterexamples, left_concept, right_concept, theory, window);

            boolean no_output = output.isEmpty();
            boolean small_num_counters = nimp.counterexamples.size()<3;
            if(no_output && small_num_counters)
            {
                System.out.println("MONDAY ~ 6 -- trying counter-barring");
                Vector new_output = performCounterexampleBarring("imp",left_concept, right_concept, nimp.counterexamples, theory, window);
                System.out.println("new_output is "+ new_output);
                return new_output;
            }



        }

        if (conj_to_modify instanceof Equivalence)
        {
            Equivalence equiv = (Equivalence)conj_to_modify;
            String response_to_conj = conj_to_modify.writeConjecture("ascii") + " holds for all my examples";
            System.out.println("conj_to_modify is an Equiv, so returning response_to_conj = " + response_to_conj);
            output.addElement(response_to_conj);
        }

        if(conj_to_modify instanceof Implication)
        {
            Implication imp = (Implication)conj_to_modify;
            String response_to_conj = conj_to_modify.writeConjecture("ascii") + " holds for all my examples";
            System.out.println("conj_to_modify is an Implication, so returning response_to_conj = " + response_to_conj);
            output.addElement(response_to_conj);
        }

        if(conj_to_modify instanceof NonExists)  //needs testing
        {
            window.writeToFrontEnd("Got a NonExists - just going to modify it now... ");

            NonExists nexists = (NonExists)conj_to_modify;
            Concept concept_with_counters = (Concept)nexists.concept;
            window.writeToFrontEnd("the id of the concept_with_counters is " + concept_with_counters.id);
            //Vector entity_strings_to_exclude = conj_to_modify.getCountersToConjecture();//are these entity strings??
            //Vector entity_strings_to_exclude = concept_with_counters.positives();
            Vector entities_to_exclude = concept_with_counters.positiveEntities();
            Vector entity_strings_to_exclude = fromEntitiesToStrings(entities_to_exclude);

            if (entity_strings_to_exclude.isEmpty())
            {
                String response_to_conj = conj_to_modify.writeConjecture("ascii") + " holds for all my examples";
                output.addElement(response_to_conj);
            }
            //
            //Concept concept_to_use =  getNewConceptWhichExactlyCoversGivenEntities(entity_strings_to_exclude, concept_with_counters);
            else
            {
                TheoryConstituent tc =
                        forceStepForPiecemealExclusionAvoidingConcept(concept_with_counters, entity_strings_to_exclude, theory, window);// prob step
                if(tc instanceof Conjecture)
                    output.addElement((Conjecture)tc);
                else
                {
                    Concept null_concept = new Concept();
                    output = performCounterexampleBarring("nexists", concept_with_counters, null_concept, entities_to_exclude, theory, window);

                }

                //test
                if(tc instanceof Conjecture)
                {
                    Conjecture conj_to_write = (Conjecture)tc;
                    theory.measure_conjecture.measureConjecture(conj_to_write,theory);
                    window.writeToFrontEnd("After modifying, the interestingness of the modification is " + conj_to_write.interestingness);
                }
                window.writeToFrontEnd("Finished modifying it - got " + tc.writeTheoryConstituent());
            }
        }



        return output;
    }

    public Vector piecemealExclusion1(String conj_type, Vector counterexamples,
                                      Concept left_concept, Concept right_concept, Theory theory, AgentWindow window)
    {
        Vector output = new Vector();
        Vector entity_strings = fromEntitiesToStrings(counterexamples);
        boolean counters_on_left = left_concept.positivesContainOneOf(entity_strings);
        boolean counters_on_right = right_concept.positivesContainOneOf(entity_strings);
        //System.out.println("wed night --- we'd expect this to be true; counters_on_left is " + counters_on_left);
        //System.out.println("wed night --- we'd expect this to be false; counters_on_right is " + counters_on_right);


        //test - in the case of implications, there can only be counters
        //on one side. so no need to test for counters on both sides
        if(conj_type.equals("imp"))
        {
            //System.out.println("wed night -- 1");
            boolean counters_to_imp = left_concept.positivesContainOneOf(entity_strings);
            if(counters_to_imp)
            {
                //System.out.println("wed night -- 2");
                return getConjectureFromCountersOneSide(conj_type, left_concept, right_concept, counterexamples, theory, window);// should include nearimps
            }
            //put an else in here
        }


        if(counters_on_left && !(counters_on_right))
        {//System.out.println("wed night -- in here");
            output = getConjectureFromCountersOneSide(conj_type, left_concept, right_concept, counterexamples, theory, window);// should include nearimps
        }

        if(!(counters_on_left) && counters_on_right)
            output = getConjectureFromCountersOneSide(conj_type, right_concept, left_concept, counterexamples, theory, window);

        if (counters_on_left && counters_on_right)
            return output;//fixme

        return output;
    }

    public Vector getConjectureFromCountersOneSide(String conj_type, Concept concept_with_counters, Concept other_concept,
                                                   Vector counters, Theory theory, AgentWindow window)
    {

        Vector output = new Vector();
        Vector entity_strings_to_exclude = fromEntitiesToStrings(counters);
        TheoryConstituent tc = forceStepForPiecemealExclusion(concept_with_counters, entity_strings_to_exclude, theory, window);
        String tc_string = (String)tc.writeTheoryConstituent();
        System.out.println("tues ~~~~~~~tc_string is " +  tc_string);
        //if found concept, add tc to output and return [what if got a concept?]
        if(!tc_string.equals(""))
            output.addElement(tc);
            //otherwise perform counterexample-barring
        else
        {
            System.out.println("tues -- ");
            output = performCounterexampleBarring(conj_type, concept_with_counters, other_concept,counters, theory, window);
        }
        //test -24/03/04
        /**
         {

         Concept concept_to_exclude = makeConceptFromVector1(counters, theory);
         window.writeToFrontEnd("couldn't find a concept, so trying counterexample-barring - made concept "
         + concept_to_exclude.writeDefinition("ascii"));
         System.out.println("1 concept_to_exclude is " + concept_to_exclude.writeDefinition("ascii"));
         System.out.println("2 concept_to_exclude==null is " +concept_to_exclude==null);
         System.out.println("3 concept_to_exclude is " + concept_to_exclude);
         System.out.println("4 concept_with_counters is " + concept_with_counters.writeDefinition("ascii"));


         //test start....
         String agenda_line1 = "id_of_dummy1 = " + concept_to_exclude.id + " " + "int001" + " negate [] ";
         System.out.println("agenda_line1 is " + agenda_line1);
         Step forced_step1 = guider.forceStep(agenda_line1, theory);
         TheoryConstituent tc_dummy1 = new TheoryConstituent();
         tc_dummy1 = forced_step1.result_of_step;

         System.out.println("tc_dummy1 is  " + tc_dummy1.writeTheoryConstituent());
         if(tc_dummy1 instanceof Concept)
         {
         Concept new_concept = (Concept)tc_dummy1;//here - was tc_dummy
         String agenda_line2 = "id_of_dummy1 = " + new_concept.id + " " + concept_with_counters.id + " compose [1] ";
         System.out.println("agenda_line2 is " + agenda_line2);
         Step forced_step2 = guider.forceStep(agenda_line2, theory);
         TheoryConstituent tc_dummy2 = new TheoryConstituent();
         tc_dummy2 = forced_step2.result_of_step;

         System.out.println("tc_dummy2 is  " + tc_dummy2.writeTheoryConstituent());

         //...test finish


         // String agenda_line = "id_of_dummy = " + concept_to_exclude.id + " " + concept_with_counters.id + " negate [] ";
         //System.out.println("6 agenda_line is " + agenda_line);
         //TheoryConstituent tc_dummy = new TheoryConstituent();
         //agenda.addForcedStep(agenda_line, false);//omit?
         //System.out.println("done addForcedStep");
         //Step forced_step = guider.forceStep(agenda_line, theory);
         //System.out.println("YO - 3");
         //tc_dummy = forced_step.result_of_step;
         //System.out.println("YO - 4");
         if (tc_dummy2 instanceof Concept && conj_type.equals("equiv"))
         {
         System.out.println("tc_dummy2 is a Concept");
         Equivalence equiv = new Equivalence();
         equiv.lh_concept = (Concept)tc_dummy2;
         equiv.rh_concept = other_concept;
         window.writeToFrontEnd("formed new concept P and -C, i.e. " +  equiv.rh_concept.writeDefinition("ascii"));
         output.addElement(equiv);
         System.out.println("YO  - new equiv is " + equiv.writeConjecture("ascii"));
         }
         if (tc_dummy2 instanceof Concept && conj_type.equals("imp"))
         {
         System.out.println("tc_dummy2 is a Concept");
         Implication imp = new Implication();
         imp.lh_concept = (Concept)tc_dummy2;
         imp.rh_concept = other_concept;
         System.out.println("imp.lh_concept is " + imp.lh_concept.writeDefinition("ascii"));
         System.out.println("imp.rh_concept is " + imp.rh_concept.writeDefinition("ascii"));
         window.writeToFrontEnd("formed new concept P and -C, i.e. " +  imp.lh_concept.writeDefinition("ascii"));
         output.addElement(imp);
         System.out.println("YO  - new imp is " + imp.writeConjecture("ascii"));


         }
         if (tc_dummy2 instanceof Concept && conj_type.equals("nonexists"))//testme
         {
         System.out.println("TESTME - tc_dummy2 is a Concept");
         NonExists nexists = new NonExists();
         nexists.concept = (Concept)tc_dummy2;
         output.addElement(nexists);
         }
         }

         if (tc_dummy1 instanceof Conjecture) //[need to check]
         {
         Conjecture conj = (Conjecture)tc_dummy1;
         output.addElement(conj);
         }

         }
         */
        return output;
    }


    public Vector performCounterexampleBarring(String conj_type, Concept concept_with_counters, Concept other_concept,
                                               Vector counters, Theory theory, AgentWindow window)
    {
        Vector output = new Vector();
        if(!use_counterexample_barring)
            return output;
        else
        {
            System.out.println("I couldn't find a concept, so trying counterexample-barring");
            window.writeToFrontEnd("I couldn't find a concept, so trying counterexample-barring");
            theory.make_equivalences_from_equality = false;

            Concept concept_to_exclude = makeConceptFromVector1(counters, theory);
            concept_to_exclude.lakatos_method = "counterexample-barring";




            boolean found_concept = false;
            for(int i=0;i<theory.concepts.size();i++)
            {
                Concept current_concept = (Concept)theory.concepts.elementAt(i);
                //System.out.println("current_concept.id is " + current_concept.id);
                if(current_concept.id == concept_to_exclude.id)
                {
                    //System.out.println("which is equal to that of concept_to_exclude");
                    found_concept = true;
                    break;
                }

                for(int j=0;j<current_concept.conjectured_equivalent_concepts.size();j++)
                {
                    Concept conj_concept = (Concept)current_concept.conjectured_equivalent_concepts.elementAt(j);
                    //System.out.println("conj_concept.id is " +conj_concept.id);

                    if(conj_concept.id == concept_to_exclude.id)
                    {
                        //System.out.println("conj_concept.id is equal to that of concept_to_exclude");
                        found_concept = true;
                        break;
                    }


                }

            }




            // System.out.println("found_concept is " + found_concept);

            //System.out.println("done the first theory formation step: got concept_to_exclude: " + concept_to_exclude.writeDefinition());
            //System.out.println("concept_to_exclude.id is " + concept_to_exclude.id);
            //System.out.println("concept_to_exclude.datatable is " + concept_to_exclude.datatable.toTable());
            //System.out.println("concept_to_exclude.categorisation is " + concept_to_exclude.categorisation);
            //System.out.println("concept_to_exclude.categorisation.getEntities() is " + concept_to_exclude.categorisation.getEntities());
            //System.out.println("concept_to_exclude.writeSpecDetails() is " + concept_to_exclude.writeSpecDetails());
            //System.out.println("concept_to_exclude.allDefinitions is " + concept_to_exclude.allDefinitions("ascii"));
            //System.out.println("concept_to_exclude.dont_develop is " + concept_to_exclude.dont_develop);

            //System.out.println("concept_with_counters is " + concept_with_counters.writeDefinition("ascii"));
            //System.out.println("concept_with_counters.id is " + concept_with_counters.id);

            window.writeToFrontEnd("done the first theory formation step: got concept_to_exclude: " + concept_to_exclude.writeDefinition("otter"));
            window.writeToFrontEnd("concept_to_exclude.id is " + concept_to_exclude.id);

            //window.writeToFrontEnd("concept_with_counters is " + concept_with_counters.writeDefinition("ascii"));
            //window.writeToFrontEnd("concept_with_counters.id is " + concept_with_counters.id);




            //wednesday 24th -- test
            //  String agenda_line_test = "id_of_dummy1 = " + concept_to_exclude.id + " " + concept_with_counters.id + " negate [] ";
//     System.out.println("agenda_line_test is " + agenda_line_test);
//     Step forced_step_test = guider.forceStep(agenda_line_test, theory);//problem might be here...
//     System.out.println("we've done this");
//     TheoryConstituent tc_dummy_test = new TheoryConstituent();
//     System.out.println("we're here now");
//     tc_dummy_test = forced_step_test.result_of_step;
            //System.out.println("tc_dummy_test is  " + tc_dummy_test.writeTheoryConstituent());
            //end test







            //test start....

            //need a way for it to work out this --  int001, or group001, etc
            //String agenda_line1 = "id_of_dummy1 = " + concept_to_exclude.id + " " + "int001" + " negate [] ";

            Vector entity_strings = new Vector();
            for(int i=0; i<counters.size();i++)
            {
                Entity current_entity = (Entity)counters.elementAt(i);
                entity_strings.addElement(current_entity.toString());
            }

            Concept core_concept = findObjectOfInterestConcept(theory.concepts, entity_strings);



            String agenda_line1 = "id_of_dummy1 = " + concept_to_exclude.id + " " + core_concept.id + " negate [] ";


            System.out.println("doing the second theory formation step: got core_concept " + core_concept.writeDefinition("otter"));
            System.out.println("core_concept.id is " + core_concept.id);
            System.out.println("agenda_line1 is " + agenda_line1);

            window.writeToFrontEnd("doing the second theory formation step: got core_concept " + core_concept.writeDefinition("otter"));
            window.writeToFrontEnd("core_concept.id is " + core_concept.id);
            window.writeToFrontEnd("agenda_line1 is " + agenda_line1);


            //agenda.addForcedStep(agenda_line1, false);
            //Step forcedstep = guider.forceStep(agenda_line1, theory);
            //output = forcedstep.result_of_step;

            agenda.addForcedStep(agenda_line1, false);
            Step forced_step1 = guider.forceStep(agenda_line1, theory);
            TheoryConstituent tc_dummy1 = new TheoryConstituent();
            tc_dummy1 = forced_step1.result_of_step;

            System.out.println("2nd step: result_of_step_explanation is " + forced_step1.result_of_step_explanation);
            window.writeToFrontEnd("2nd step: result_of_step_explanation is " + forced_step1.result_of_step_explanation);
            //System.out.println("tc_dummy1 is  " + tc_dummy1.writeTheoryConstituent());
            if(tc_dummy1 instanceof Concept)
            {
                Concept new_concept = (Concept)tc_dummy1;//here - was tc_dummy
                //new_concept.lakatos_method = "piecemeal_exclusion";
                System.out.println("done the second theory formation step: got new_concept: " + new_concept.writeDefinition());
                System.out.println("new_concept.id is " +new_concept.id);


                String agenda_line2 = "id_of_dummy1 = " + new_concept.id + " " + concept_with_counters.id + " compose [1] ";
                System.out.println("now starting the third theory formation step, with agenda_line: " + agenda_line2);

                window.writeToFrontEnd("done the second theory formation step: got new_concept: " + new_concept.writeDefinition());
                window.writeToFrontEnd("new_concept.id is " +new_concept.id);
                window.writeToFrontEnd("now starting the third theory formation step, with agenda_line: " + agenda_line2);

                Step forced_step2 = guider.forceStep(agenda_line2, theory);//problem might be here...

                System.out.println("3rd step: result_of_step_explanation is " + forced_step2.result_of_step_explanation);
                window.writeToFrontEnd("3rd step: result_of_step_explanation is " + forced_step2.result_of_step_explanation);

                TheoryConstituent tc_dummy2 = new TheoryConstituent();
                tc_dummy2 = forced_step2.result_of_step;

                if(tc_dummy2 instanceof Conjecture)
                {
                    System.out.println("the result of forced step2 was a conjecture");
                    Conjecture new_conj = (Conjecture)tc_dummy2;
                    System.out.println("new_conj is " + new_conj.writeConjecture());
                }

                System.out.println("3rd step: tc_dummy2 is  " + tc_dummy2.writeTheoryConstituent());
                window.writeToFrontEnd("3rd step: tc_dummy2 is  " + tc_dummy2.writeTheoryConstituent());

                //...test finish


                // String agenda_line = "id_of_dummy = " + concept_to_exclude.id + " " + concept_with_counters.id + " negate [] ";
                //System.out.println("6 agenda_line is " + agenda_line);
                //TheoryConstituent tc_dummy = new TheoryConstituent();
                //agenda.addForcedStep(agenda_line, false);//omit?
                //System.out.println("done addForcedStep");
                //Step forced_step = guider.forceStep(agenda_line, theory);
                //System.out.println("YO - 3");
                //tc_dummy = forced_step.result_of_step;
                //System.out.println("YO - 4");
                if (tc_dummy2 instanceof Concept && conj_type.equals("equiv"))
                {
                    System.out.println("tc_dummy2 is a Concept");
                    Equivalence equiv = new Equivalence();
                    equiv.lh_concept = (Concept)tc_dummy2;
                    equiv.rh_concept = other_concept;
                    window.writeToFrontEnd("formed new concept P and -C, i.e. " +  equiv.rh_concept.writeDefinition("ascii"));
                    output.addElement(equiv);
                    System.out.println("YO  - new equiv is " + equiv.writeConjecture("ascii"));
                }
                if (tc_dummy2 instanceof Concept && conj_type.equals("imp"))
                {
                    System.out.println("tc_dummy2 is a Concept");
                    Implication imp = new Implication();
                    imp.lh_concept = (Concept)tc_dummy2;
                    imp.rh_concept = other_concept;
                    System.out.println("imp.lh_concept is " + imp.lh_concept.writeDefinition("ascii"));
                    System.out.println("imp.rh_concept is " + imp.rh_concept.writeDefinition("ascii"));
                    window.writeToFrontEnd("formed new concept P and -C, i.e. " +  imp.lh_concept.writeDefinition("ascii"));
                    output.addElement(imp);
                    System.out.println("YO  - new imp is " + imp.writeConjecture("ascii"));


                }
                if (tc_dummy2 instanceof Concept && conj_type.equals("nonexists"))//testme
                {
                    System.out.println("TESTME - tc_dummy2 is a Concept");
                    NonExists nexists = new NonExists();
                    nexists.concept = (Concept)tc_dummy2;
                    output.addElement(nexists);
                }

                if (tc_dummy2 instanceof NonExists)
                {
                    NonExists nexists = (NonExists)tc_dummy2;
                    output.addElement(nexists);
                }
            }

            if (tc_dummy1 instanceof Conjecture) //[need to check]
            {
                Conjecture conj = (Conjecture)tc_dummy1;
                output.addElement(conj);
            }
            theory.make_equivalences_from_equality = theory.front_end.make_equivalences_from_equality_check.getState();
            if(!output.isEmpty())
            {
                for(int i=0;i<output.size();i++)
                {
                    Conjecture conj = (Conjecture)output.elementAt(i);
                    conj.lakatos_method = "counterexample_barring";
                }
            }

            return output;
        }
    }

    public Vector piecemealExclusion(Conjecture conj_to_modify, Concept left_concept, Concept right_concept, Theory theory, AgentWindow window)
    {
        Vector output = new Vector();
        Vector counterexamples = new Vector();



        if(conj_to_modify instanceof NearEquivalence)
        {
            //System.out.println("HEY - got a nequiv");
            NearEquivalence nequiv = (NearEquivalence)conj_to_modify;
            Concept nequiv_lh_concept = (Concept)nequiv.lh_concept;
            Concept nequiv_rh_concept = (Concept)nequiv.rh_concept;
            //System.out.println("got our nequiv concepts");


            //System.out.println("counters are " + nequiv.counterexamples);
            Vector entity_strings = fromEntitiesToStrings(nequiv.counterexamples);


            boolean counters_on_left = nequiv_lh_concept.positivesContainOneOf(entity_strings);
            //System.out.println("counters to reconstructed conjecture are " + nequiv.counterexamples);
            //System.out.println("counters_on_left is " + counters_on_left);

            boolean counters_on_right = nequiv_rh_concept.positivesContainOneOf(entity_strings);
            System.out.println("counters_on_right is " + counters_on_right);
            //Vector counters_on_rhs = right_concept.getSubset(nequiv.counterexamples);//get counters_to_lhs
            //boolean counters_on_right = !(counters_on_rhs.isEmpty());



            //System.out.println("1) here");

            //Vector entities_to_cover = getUnionOfConcepts(nequiv_lh_concept, nequiv_rh_concept);
            //Vector entities_to_cover = nequiv_lh_concept.getSubset(nequiv_rh_concept.getPositives());

            //if got counters on both sides, make implication conjectures P /\ -C -> Q and Q /\ -C -> P
            Concept lh_concept_for_pm_ex = null;
            Concept rh_concept_for_pm_ex = null;

            if(counters_on_left && counters_on_right) //need to think about this one[]
            {
                //Vector entity_strings = fromEntitiesToStrings(nequiv.counterexamples);

                Vector counters_on_lhs = left_concept.getSubset(entity_strings);//get counters_to_lhs
                window.writeToFrontEnd("counters_on_lhs are " + counters_on_lhs);
                TheoryConstituent tc1 = forceStepForPiecemealExclusion(left_concept, counters_on_lhs, theory, window);
                String tc1_string = (String)tc1.writeTheoryConstituent();

                Vector counters_on_rhs = right_concept.getSubset(entity_strings);//get counters_to_lhs
                window.writeToFrontEnd("counters_on_rhs are " + counters_on_rhs);
                TheoryConstituent tc2 = forceStepForPiecemealExclusion(right_concept, counters_on_lhs, theory, window);
                String tc2_string = (String)tc2.writeTheoryConstituent();

                if(!(tc1_string.equals("")))
                    output.addElement(tc1);

                if(!(tc2_string.equals("")))
                    output.addElement(tc2);

                if(tc1_string.equals("") && tc2_string.equals(""))
                {
                    Concept concept1_to_exclude = makeConceptFromVector(counters_on_lhs, theory);
                    Concept concept2_to_exclude = makeConceptFromVector(counters_on_rhs, theory);
                    window.writeToFrontEnd("looking for a concept to cover the lhs counters - got " + concept1_to_exclude.writeDefinition("ascii"));
                    window.writeToFrontEnd("looking for a concept to cover the rhs counters - got " + concept2_to_exclude.writeDefinition("ascii"));
                    Concept core_concept = new Concept();
                    for (int i = 0; i< theory.concepts.size(); i++)
                    {
                        Concept concept = (Concept)theory.concepts.elementAt(i);
                        if(concept.position_in_theory==0)
                        {
                            core_concept = concept;
                            break;
                        }
                    }
                    String agenda_line1 = "id_of_dummy1 = " + concept1_to_exclude.id + " " + left_concept.id + " negate [] ";
                    String agenda_line2 = "id_of_dummy2 = " + concept2_to_exclude.id + " " + right_concept.id + " negate [] ";

                    TheoryConstituent tc_dummy1 = new TheoryConstituent();
                    TheoryConstituent tc_dummy2 = new TheoryConstituent();

                    agenda.addForcedStep(agenda_line1, false);//omit?
                    Step forced_step1_for_counterbarring = guider.forceStep(agenda_line1, theory);
                    tc_dummy1 = forced_step1_for_counterbarring.result_of_step;

                    agenda.addForcedStep(agenda_line2, false);//omit?
                    Step forced_step2_for_counterbarring  = guider.forceStep(agenda_line2, theory);
                    tc_dummy2 = forced_step2_for_counterbarring.result_of_step;

                    if (tc_dummy1 instanceof Concept && tc_dummy2 instanceof Concept)
                    {
                        Equivalence equiv = new Equivalence();
                        equiv.lh_concept = (Concept)tc_dummy1;
                        equiv.rh_concept = (Concept)tc_dummy2;
                        window.writeToFrontEnd("formed new concept: P and -C1, i.e. " + equiv.lh_concept.writeDefinition("ascii"));
                        window.writeToFrontEnd("formed new concept: Q and -C2, i.e. " + equiv.rh_concept.writeDefinition("ascii"));
                        output.addElement(equiv);
                        //System.out.println("YO  - equiv is " + equiv.writeConjecture("ascii"));
                    }
                }
            }
            //if got counters on one side only(eg P), make equivalence conjecture P/\ -C <-> Q
            if(counters_on_left && !(counters_on_right))
            {
                Vector entity_strings_to_exclude = fromEntitiesToStrings(nequiv.counterexamples);
                window.writeToFrontEnd("just got counters on left: " + entity_strings_to_exclude);
                TheoryConstituent tc = forceStepForPiecemealExclusion(left_concept, entity_strings_to_exclude, theory, window);
                String tc_string = (String)tc.writeTheoryConstituent();

                if(!tc_string.equals(""))
                {
                    output.addElement(tc);
                }
                else
                {
                    Concept concept_to_exclude = makeConceptFromVector1(nequiv.counterexamples, theory);
                    concept_to_exclude.lakatos_method = "piecemeal-exclusion";
                    window.writeToFrontEnd(" MONDAY ---- 1: looking for a concept to cover the counters - got " + concept_to_exclude.writeDefinition("ascii"));
                    String agenda_line = "id_of_dummy = " + concept_to_exclude.id + " " + left_concept.id + " negate [] ";
                    TheoryConstituent tc_dummy = new TheoryConstituent();
                    agenda.addForcedStep(agenda_line, false);//omit?
                    Step forced_step = guider.forceStep(agenda_line, theory);
                    tc_dummy = forced_step.result_of_step;

                    if (tc_dummy instanceof Concept)
                    {
                        Equivalence equiv = new Equivalence();
                        equiv.lh_concept = (Concept)tc_dummy;
                        window.writeToFrontEnd("formed new concept P and -C, i.e. " +  equiv.lh_concept.writeDefinition("ascii"));
                        equiv.rh_concept = right_concept;
                        output.addElement(equiv);
                        //System.out.println("YO  - equiv is " + equiv.writeConjecture("ascii"));
                    }

                }
            }

            if(!(counters_on_left) && counters_on_right)
            {
                //System.out.println("2) here");
                //System.out.println("just got counters_on_right");
                Vector entity_strings_to_exclude = fromEntitiesToStrings(nequiv.counterexamples);
                window.writeToFrontEnd("just got counters on right: " + entity_strings_to_exclude);
                TheoryConstituent tc = forceStepForPiecemealExclusion(right_concept, entity_strings_to_exclude, theory, window);
                String tc_string = (String)tc.writeTheoryConstituent();

                //System.out.println("4) here");
                if(!tc_string.equals(""))
                {
                    //System.out.println("6) tc.writeTheoryConstituent() to add is " + tc.writeTheoryConstituent());
                    output.addElement(tc);
                }
                else
                {
                    Concept concept_to_exclude = makeConceptFromVector1(nequiv.counterexamples, theory);
                    concept_to_exclude.lakatos_method = "piecemeal-exclusion";
                    window.writeToFrontEnd("---------couldn't find a concept, so trying counterexample-barring - made concept " + concept_to_exclude.writeDefinition("ascii"));
                    //System.out.println("1 concept_to_exclude is " + concept_to_exclude.writeDefinition("ascii"));
                    //System.out.println("2 concept_to_exclude==null is " +concept_to_exclude==null);
                    //System.out.println("3 concept_to_exclude is " + concept_to_exclude);
                    //no id number of concept_to_exclude so stopping at this line
                    //need to sort out makeConceptFromVector - not sure if it's working...
                    String agenda_line = "id_of_dummy = " + concept_to_exclude.id + " " + right_concept.id + " negate [] ";
                    //System.out.println("4 concept_to_exclude is " + concept_to_exclude);
                    //System.out.println("5 concept_to_exclude==null is " +concept_to_exclude==null);
                    //System.out.println("6 agenda_line is " + agenda_line);
                    //System.out.println("7 concept_to_exclude is " + concept_to_exclude);
                    //System.out.println("8 concept_to_exclude==null is " +concept_to_exclude==null);
                    TheoryConstituent tc_dummy = new TheoryConstituent();
                    //System.out.println("9 YO - 1");
                    //System.out.println("10 concept_to_exclude is " + concept_to_exclude);
                    //System.out.println("11 concept_to_exclude==null is " +concept_to_exclude==null);
                    agenda.addForcedStep(agenda_line, false);//omit?
                    //System.out.println("12 concept_to_exclude is " + concept_to_exclude);
                    //System.out.println("13 concept_to_exclude==null is " +concept_to_exclude==null);
                    //System.out.println("done addForcedStep");



                    Step forced_step = guider.forceStep(agenda_line, theory);
                    //System.out.println("YO - 3");
                    tc_dummy = forced_step.result_of_step;
                    //System.out.println("YO - 4");
                    if (tc_dummy instanceof Concept)
                    {
                        //System.out.println("tc_dummy is a Concept");
                        Equivalence equiv = new Equivalence();
                        //System.out.println("YO - 6");
                        equiv.lh_concept = left_concept;
                        //System.out.println("YO - 7");
                        equiv.rh_concept = (Concept)tc_dummy;
                        window.writeToFrontEnd("formed new concept P and -C, i.e. " +  equiv.rh_concept.writeDefinition("ascii"));
                        //System.out.println("YO - 8");
                        output.addElement(equiv);
                        //System.out.println("YO  - new equiv is " + equiv.writeConjecture("ascii"));
                    }
                    if (tc_dummy instanceof Conjecture)
                    {
                        Conjecture conj = (Conjecture)tc_dummy;
                        output.addElement(conj);
                    }
                }
            }
            //if no concepts cover all the counters, then no possible modifications (i.e. p/e isn't possible)
            //if(concepts_to_exclude_from_lhs.isEmpty() && concepts_to_exclude_from_rhs.isEmpty())
            //   return possible_modifications;
        }
        window.writeToFrontEnd("formed new conjectures:");
        for(int i=0;i<output.size();i++)
        {
            if(!(output.elementAt(i) instanceof Conjecture))
                window.writeToFrontEnd(output.elementAt(i) + "is not a conj");
            Conjecture conj = (Conjecture)output.elementAt(i);
            window.writeToFrontEnd(conj.writeConjecture("ascii"));
        }
        //System.out.println("finished piecemealExclusion on " + conj_to_modify.writeConjecture("ascii"));
        return output;
    }

    /** Given a concept P and vector of counterexamples which are
     * entity strings, finds a concept C which (exactly) covers the
     * counterexamples and then adds the step "P C negate []" to the
     * agenda and forces it. This forms the concept P /\ -C and any
     * resulting conjectures. The theory constituent(s??) formed
     * during the step is returned. */

    public TheoryConstituent forceStepForPiecemealExclusionAvoidingConcept(Concept concept, Vector string_entities_to_cover,
                                                                           Theory theory, AgentWindow window)
    {
        TheoryConstituent output = new TheoryConstituent();
        //System.out.println("starting forceStepForPiecemealExclusionAvoidingConcept");
        //System.out.println("concept is " + concept.writeDefinition("ascii"));
        //System.out.println("string_entities_to_cover are " + string_entities_to_cover);

        //first find concept C to cover the counters
        Concept concept_to_exclude = getNewConceptWhichExactlyCoversGivenEntities(string_entities_to_cover, concept);
        //System.out.println("concept_to_exclude is " + concept_to_exclude.writeDefinition("ascii"));

        //then (supposing there is such a concept), form the concept P &
        //-C by adding step to agenda and forcing it. Otherwise just
        //return output
        if(concept_to_exclude.writeDefinition("ascii").equals(""))
            return output;
        else
        {
            window.writeToFrontEnd("MONDAY ------------------  2: looking for a concept to cover the counters - got " + concept_to_exclude.writeDefinition("ascii"));
            String agenda_line = "id_of_dummy = " + concept_to_exclude.id + " " + concept.id + " negate [] ";
            //System.out.println("agenda_line is " + agenda_line);
            agenda.addForcedStep(agenda_line, false);
            Step forcedstep = guider.forceStep(agenda_line, theory);
            output = forcedstep.result_of_step;
            return output;
        }
    }

    /** Given a concept P and vector of counterexamples which are
     * entity strings, finds a concept C which (exactly) covers the
     * counterexamples and then adds the step "P C negate []" to the
     * agenda and forces it. This forms the concept P /\ -C and any
     * resulting conjectures. The theory constituent(s??) formed
     * during the step is returned. */

    public TheoryConstituent forceStepForPiecemealExclusion(Concept concept, Vector string_entities_to_cover, Theory theory, AgentWindow window)
    {
        TheoryConstituent output = new TheoryConstituent();
        //System.out.println("starting forceStepForPiecemealExclusion");
        //System.out.println("concept is " + concept.writeDefinition("ascii"));
        //System.out.println("string_entities_to_cover are " + string_entities_to_cover);

        //first find concept C to cover the counters
        Concept concept_to_exclude = getConceptWhichExactlyCoversGivenEntities(string_entities_to_cover);
        //System.out.println("concept_to_exclude is " + concept_to_exclude.writeDefinition("ascii"));

        //then (supposing there is such a concept), form the concept P &
        //-C by adding step to agenda and forcing it. Otherwise just
        //return output
        if(concept_to_exclude.writeDefinition("ascii").equals(""))
            return output;
        else
        {
            window.writeToFrontEnd("MONDAY  --------------  3: looking for a concept to cover the counters - got "
                    + concept_to_exclude.writeDefinition("ascii"));
            window.writeToFrontEnd("want to form the concept " + concept.writeDefinition() + " and not "
                    + concept_to_exclude.writeDefinition());



            if(concept.is_object_of_interest_concept)
            {
                String agenda_line = "id_of_dummy = " + concept_to_exclude.id + " " + concept.id + " negate [] ";
                window.writeToFrontEnd("MONDAY  -------------- agenda_line is " + agenda_line);
                agenda.addForcedStep(agenda_line, false);
                Step forcedstep = guider.forceStep(agenda_line, theory);
                output = forcedstep.result_of_step;
            }
            else
            {

                Concept core_concept = new Concept();
                for(int i=0; i<concepts.size();i++)
                {
                    Concept poss_concept = (Concept)concepts.elementAt(i);
                    boolean b = poss_concept.domain.equals(concept_to_exclude.domain);
                    if(poss_concept.is_object_of_interest_concept && b)
                    {
                        core_concept = poss_concept;
                        break;
                    }
                }


                String agenda_line1 = "id_of_dummy1 = " + concept_to_exclude.id + " " + core_concept.id + " negate [] ";

                agenda.addForcedStep(agenda_line1, false);
                Step forcedstep = guider.forceStep(agenda_line1, theory);
                TheoryConstituent output1 = new TheoryConstituent();
                output1 = forcedstep.result_of_step;

                if(output1 instanceof Concept)
                {

                    Concept concept_to_use = (Concept)output1;
                    concept_to_use.lakatos_method = "piecemeal_exclusion";
                    String agenda_line2 = "id_of_dummy2 = " + concept.id + " " + concept_to_use.id + " compose [1] ";

                    Step forcedstep2 = guider.forceStep(agenda_line2, theory);
                    output = forcedstep2.result_of_step;

                }

            }




            //System.out.println("agenda_line is " + agenda_line);


            if(output instanceof Conjecture)
            {
                Conjecture conj = (Conjecture)output;
                //System.out.println("MONDAY -------------- conjecture output is " + conj.writeConjecture());
            }

            if(output instanceof Concept)
            {
                window.writeToFrontEnd("MONDAY  - we've got a concept:");
                Concept c = (Concept)output;
                c.lakatos_method = "piecemeal_exclusion";
                window.writeToFrontEnd(c.writeDefinition());
                System.out.println("MONDAY(here) -------------- concept output is " + c.writeDefinition());
            }

            //System.out.println("MONDAY -------------- 4 output is " + output);
            window.writeToFrontEnd("MONDAY  -------------- output is " + output);
            if(output instanceof TheoryConstituent)
            {
                window.writeToFrontEnd("MONDAY  -------------- output is " + ((TheoryConstituent)output).writeTheoryConstituent());
            }

// 	//test here
// 	if(output instanceof Conjecture)
// 	 //otherwise perform counterexample-barring
// 	else
// 	  output = performCounterexampleBarring(conj_type, concept_with_counters, other_concept,counters, theory, window);


            return output;
        }
    }



    /** Given a concept P and vector of counterexamples which are
     * entity strings, finds a concept C which covers (just) the
     * counterexamples and then forms the concept P /\ -C and returns
     * it.  */

    public Concept makeConceptForPiecemealExclusion(Concept concept, Vector string_entities_to_cover, Theory theory)
    {
        System.out.println("started makeConceptForPiecemealExclusion");
        Concept concept_for_pm_ex = new Concept();
        System.out.println("concept_for_pm_ex is " + concept_for_pm_ex.writeDefinition("ascii"));

        //first find concept C to cover the counters
        Concept concept_to_exclude = getConceptWhichExactlyCoversGivenEntities(string_entities_to_cover);

        //then (supposing there is such a concept), form the concept  P /\ -C

        String agenda_line1 = "id_of_dummy1 = " + concept_to_exclude.id + " " + concept.id + " negate [] ";
        System.out.println("agenda_line1 is " + agenda_line1);
        agenda.addForcedStep(agenda_line1, false);
        Step forcedstep = guider.forceStep(agenda_line1, theory);

        System.out.println("forcedstep was :" + forcedstep);
        System.out.println("forcedstep.result_of_step_explanation was :" +  forcedstep.result_of_step_explanation);
        TheoryConstituent tc_result_of_step = forcedstep.result_of_step;
        System.out.println("tc_result_of_step was :" + tc_result_of_step);

        System.out.println("finished makeConceptForPiecemealExclusion");

        if(concept_for_pm_ex.writeDefinition("ascii").equals(""))
            System.out.println("concept_for_pm_ex is null (probably haven't done step yet)");
        System.out.println("concept_for_pm_ex is " + concept_for_pm_ex.writeDefinition("ascii"));

        return concept_for_pm_ex;
    }




    /**************************************************************************************/
    //                              METHOD 3                                              //
    //                         STRATEGIC WITHDRAWAL                                       //
    /**************************************************************************************/

    /** Withdraw to a safe domain - by forming a concept which covers
     * all the positives (and none of the negatives) and limiting the
     * conjecture to this concept. Given a faulty conjecture P <~> Q,
     * and two vectors of counterexamples - the first containing
     * objects in P but not Q, and the second containing objects in Q
     * but not P, return a vector of possible strategic withdrawal
     * modifications to the conjecture.
     */

    public Vector strategicWithdrawal(Conjecture conj_to_modify, Theory theory, AgentWindow window)
    {
        System.out.println("___________________________ \n\n");
        System.out.println("started strategicWithdrawal");
        System.out.println("conj_to_modify is " + conj_to_modify.id + ": " + conj_to_modify.writeConjecture("ascii") + ", ");

        Vector output = new Vector();
        if (conj_to_modify instanceof NearEquivalence)
        {
            System.out.println("starting with a NearEquivalence");
            NearEquivalence nequiv = (NearEquivalence)conj_to_modify;
            Concept left_concept = (Concept)nequiv.lh_concept;
            Concept right_concept = (Concept)nequiv.rh_concept;
            output = strategicWithdrawal((NearEquivalence)conj_to_modify, left_concept, right_concept, theory, window);
        }

        if (conj_to_modify instanceof Equivalence)
        {
            System.out.println("starting with a Equivalence");
            Equivalence equiv = (Equivalence)conj_to_modify;
            Concept left_concept = (Concept)equiv.lh_concept;
            Concept right_concept = (Concept)equiv.rh_concept;
            output = strategicWithdrawal(equiv, left_concept, right_concept, theory, window);
        }

        if(conj_to_modify instanceof NearImplication)
        {
            System.out.println("starting with an Implication");
            NearImplication nimp = (NearImplication)conj_to_modify;
            Concept left_concept = (Concept)nimp.lh_concept;
            Concept right_concept = (Concept)nimp.rh_concept;
            output = strategicWithdrawal(nimp, left_concept, right_concept, theory, window);
        }


        if(conj_to_modify instanceof Implication)
        {
            System.out.println("starting with an Implication");
            Implication imp = (Implication)conj_to_modify;
            Concept left_concept = (Concept)imp.lh_concept;
            Concept right_concept = (Concept)imp.rh_concept;
            output = strategicWithdrawal(imp, left_concept, right_concept, theory, window);
        }

        //need to extend
        if(conj_to_modify instanceof NonExists)
        {
            System.out.println("starting with a NonExists");
            NonExists non_exists = (NonExists)conj_to_modify;
            Concept concept = (Concept)non_exists.concept;
            output = strategicWithdrawal(non_exists, concept, theory, window);
        }

        System.out.println("finished strategicWithdrawal");
        System.out.println("___________________________ \n\n");
        return output;
    }

    //needs extending
    public Vector strategicWithdrawal(Conjecture conj_to_modify, Concept concept, Theory theory, AgentWindow window)
    {
        Vector output = new Vector();
        String response_to_conj = new String();

        if((conj_to_modify.getCountersToConjecture()).isEmpty())
            response_to_conj = conj_to_modify.writeConjecture() + " holds for all my examples";
        else
            response_to_conj = "cannot perform strategic withdrawal on a nonexists conjecture, sorry.";

        window.writeToFrontEnd("MONDAY -- my response_to_conj is " + response_to_conj);

        output.addElement(response_to_conj);
        return output;
    }

    //need to add the NearImplication case
    public Vector strategicWithdrawal(Conjecture conj_to_modify, Concept left_concept, Concept right_concept, Theory theory, AgentWindow window)
    {
        Vector output = new Vector();
        Vector counterexamples = new Vector();

        if(conj_to_modify instanceof Equivalence)
        {
            Equivalence equiv = (Equivalence)conj_to_modify;
            String response_to_conj = conj_to_modify.writeConjecture("ascii") + " holds for all my examples";
            System.out.println("OI, YOU ---- conj_to_modify is an Equiv, so returning response_to_conj = " + response_to_conj);
            output.addElement(response_to_conj);
        }

        if(conj_to_modify instanceof NearEquivalence)
        {

            NearEquivalence nequiv = (NearEquivalence)conj_to_modify;
            Concept nequiv_lh_concept = (Concept)nequiv.lh_concept;
            Concept nequiv_rh_concept = (Concept)nequiv.rh_concept;
            //window.writeToFrontEnd("lh_concept has examples: " + nequiv_lh_concept.datatable.toTable());
            //window.writeToFrontEnd("rh_concept has examples: " + nequiv_rh_concept.datatable.toTable());


            System.out.println("counters are: " + nequiv.counterexamples);
            Vector entity_strings = fromEntitiesToStrings(nequiv.counterexamples);

            boolean counters_on_left = nequiv_lh_concept.positivesContainOneOf(entity_strings);

            System.out.println("counters to reconstructed conjecture are " + nequiv.counterexamples);
            System.out.println("counters_on_left is " + counters_on_left);

            boolean counters_on_right = nequiv_rh_concept.positivesContainOneOf(entity_strings);
            System.out.println("counters_on_right is " + counters_on_right);

            Vector counters_on_lhs = left_concept.getSubset(entity_strings);//get counters_to_lhs

            //Vector counters_on_lhs = left_concept.getSubset(nequiv.counterexamples);//get counters_to_lhs
            Vector positives = getPositives(nequiv.counterexamples, left_concept);//check HERE


            //Vector entities_to_cover = getUnionOfConcepts(nequiv_lh_concept, nequiv_rh_concept);
            Vector entities_to_cover = nequiv_lh_concept.getSubset(nequiv_rh_concept.getPositives());

            // put back in ...  Vector concepts_to_limit = getNConceptsWhichCoverGivenEntities(positives, 1);

            //if no concepts cover all the positives, then no possible modifications (i.e. s/w isn't possible)
            //put back in ... if(concepts_to_limit.isEmpty()) return output;
            // put back in ... else
            // put back in ...   {
            // put back in ... Concept concept_to_limit = (Concept)concepts_to_limit.elementAt(0);//is this biggest?[][]

            //Concept concept_to_limit = getConceptWhichExactlyCoversGivenEntities(positives);
            //Concept concept_to_limit = getNewConceptWhichExactlyCoversGivenEntities(positives, nequiv_rh_concept);//change
            //System.out.println("just got concept_to_limit  - it's " +  concept_to_limit.writeDefinition("ascii"));
            //System.out.println("concept_to_limit.writeDefinition(\"ascii\").equals(\"\") is " + concept_to_limit.writeDefinition("ascii").equals("");
            //if got counters on both sides, make conjectures C -> P and C -> Q
            if(counters_on_left && counters_on_right)
            {
                window.writeToFrontEnd("got counters on both sides: " + entity_strings);
                Concept concept_to_limit = getConceptWhichExactlyCoversGivenEntities(entities_to_cover);
                concept_to_limit.lakatos_method = "strategic-withdrawal";
                if (concept_to_limit.writeDefinition("ascii").equals(""))
                {
                    return output;
                }
                window.writeToFrontEnd("looking for a concept to cover the positives, i.e." + entities_to_cover);
                window.writeToFrontEnd("found "+ concept_to_limit.writeDefinition("ascii"));
                Conjecture imp_conj1 = makeImpConj(concept_to_limit, right_concept);
                Conjecture imp_conj2 = makeImpConj(concept_to_limit, left_concept);

                output.addElement(imp_conj1);
                output.addElement(imp_conj2);
            }

            //if got counters on one side only (eg. P), make equivalence C <-> Q, implication C -> Q and implication C -> P
            else
            if(counters_on_left)
            {
                window.writeToFrontEnd("just got counters on left: " + entity_strings);
                Concept concept_to_limit = getNewConceptWhichExactlyCoversGivenEntities(entities_to_cover, nequiv_rh_concept);
                window.writeToFrontEnd("looking for a concept to cover the positives, i.e." + entities_to_cover);
                window.writeToFrontEnd("found "+ concept_to_limit.writeDefinition("ascii"));
                if (concept_to_limit.writeDefinition("ascii").equals(""))
                {
                    return output;
                }


                Conjecture equiv_conj = makeEquivConj(concept_to_limit, right_concept);
                Conjecture imp_conj1 = makeImpConj(concept_to_limit, right_concept);//[doesn't HR automatically make an imp conj when it finds an equiv conj?]
                Conjecture imp_conj2 = makeImpConj(concept_to_limit, left_concept);

                output.addElement(equiv_conj);
                output.addElement(imp_conj1);
                output.addElement(imp_conj2);

            }
            else
            if(counters_on_right)
            {
                window.writeToFrontEnd("just got counters on right: " + entity_strings);
                Concept concept_to_limit = getNewConceptWhichExactlyCoversGivenEntities(entities_to_cover, nequiv_lh_concept);
                window.writeToFrontEnd("looking for a concept to cover the positives, i.e." + entities_to_cover);
                window.writeToFrontEnd("found "+ concept_to_limit.writeDefinition("ascii"));
                if (concept_to_limit.writeDefinition("ascii").equals(""))
                {
                    return output;
                }

                Conjecture equiv_conj = makeEquivConj(concept_to_limit, left_concept);
                Conjecture imp_conj1 = makeImpConj(concept_to_limit, left_concept);
                Conjecture imp_conj2 = makeImpConj(concept_to_limit, right_concept);

                output.addElement(equiv_conj);
                output.addElement(imp_conj1);
                output.addElement(imp_conj2);
            }
        }
        if(output.isEmpty())
            System.out.println("output from SW is EMPTY");
        else
        {
            System.out.println("output from SW is ");
            window.writeToFrontEnd("output from SW is ");
            for(int i=0; i<output.size(); i++)
            {
                if(output.elementAt(i) instanceof Conjecture)
                {
                    Conjecture conj = (Conjecture)output.elementAt(i);
                    System.out.println(conj.id + ": " + conj.writeConjecture("ascii") + ", ");
                    window.writeToFrontEnd(conj.id + ": " + conj.writeConjecture("ascii") + ", ");
                }
                if(output.elementAt(i) instanceof String)
                {
                    String string_element = (String)output.elementAt(i);
                    System.out.println(string_element);
                    window.writeToFrontEnd(string_element);
                }
            }
        }

        return output;
    }

    /**************************************************************************************/
    //                                 METHOD 4                                           //
    //                              MONSTER BARRING                                       //
    /**************************************************************************************/

    public Request monsterBarring(Entity monster_entity, String domain, Theory theory,
                                  Conjecture conjecture_under_discussion, String mb_type, AgentWindow window)
    {
        Request request = new Request();
        window.writeToFrontEnd("started monsterBarring(" + monster_entity.name + ", " + domain + ")");
        //window.writeToFrontEnd("conjecture_under_discussion is " + conjecture_under_discussion.writeConjecture());
        if(mb_type.equals("vaguetovague"))
            request = monsterBarringVagueToVague(monster_entity, domain, theory, conjecture_under_discussion, window);
        if(mb_type.equals("vaguetospecific"))
            request = monsterBarringVagueToSpecific(monster_entity, domain, theory, conjecture_under_discussion, window);
        if(mb_type.equals("specifictospecific"))
            request = monsterBarringSpecificToSpecific(monster_entity, domain, theory, conjecture_under_discussion, window);
        return request;
    }

    /** doesn't really do what you want -- check more in Student -- might be okay
     */
    public Request monsterBarringVagueToVague(Entity monster_entity, String domain, Theory theory,
                                              Conjecture conjecture_under_discussion, AgentWindow window)
    {
        Request request = new Request();
        request.motivation = new Motivation(monster_entity, "monster-barring");
        //window.writeToFrontEnd("\n\n\n\n\n\n *** just setting the conjecture_under_discussion ");
        request.motivation.conjecture_under_discussion = conjecture_under_discussion;
        request.string_object = monster_entity.toString()
                + " is breaking many of my conjectures. It would be neater to bar it from the concept of "
                + domain + " than from all my conjectures";
        window.writeToFrontEnd("request at the end of m-b is " + request.toString());
        return request;
    }


    /** Note: might not be able to use `domain' - is very specific and
     * can't then use this method for nested monster-barring
     */
    public Request monsterBarringVagueToSpecific (Entity monster_entity, String domain, Theory theory,
                                                  Conjecture conjecture_under_discussion, AgentWindow window)
    {
        Request request = new Request();
        Vector v = new Vector();

        Concept mb_concept = (Concept)getSpecificFromVagueDefinition(monster_entity, false, domain, theory, conjecture_under_discussion, window); //why the false??
        v.addElement(mb_concept);
        //Concept mb_concept2 = (Concept)getSpecificFromVagueDefinition(monster_entity, true, domain, theory, conjecture_under_discussion, window); use to react to mb

        request.motivation = new Motivation(monster_entity, "monster-barring");
        window.writeToFrontEnd("\n\n\n\n\n\n *** just setting the conjecture_under_discussion ");
        request.motivation.conjecture_under_discussion = conjecture_under_discussion;
        request.string_object = monster_entity.toString() + " is not a " + domain + ". A " +
                domain +  " is something which satisfies " + mb_concept.writeDefinition();
        request.theory_constituent_vector = v;
        window.writeToFrontEnd("request at the end of m-b is " + request.toString());

        return request;
    }


    /** to do
     */
    public Request monsterBarringSpecificToSpecific (Entity monster_entity, String domain, Theory theory,
                                                     Conjecture conjecture_under_discussion, AgentWindow window)
    {
        return (new Request());
    }



    /** Given a vague definition (eg a core concept), looks for a
     * specific concept definition
     */

    public Concept getSpecificFromVagueDefinition(Entity monster_entity, boolean cover_monster, String domain,
                                                  Theory theory, Conjecture conjecture_under_discussion, AgentWindow window)
    {
        Concept new_concept = new Concept();
        Vector entity_strings = new Vector();
        for(int i=0; i<theory.entities.size();i++)
        {
            Entity current_entity = (Entity)theory.entities.elementAt(i);
            entity_strings.addElement(current_entity.toString());
        }

        Concept concept_to_avoid = (Concept)theory.getObjectOfInterestConcept();
        TheoryConstituent tc = generateDefinitionGivenEntities(entity_strings, concept_to_avoid, monster_entity.toString());//currently don't use this
        Vector conjectured_equivalent_concepts = findConjecturedEquivalenceConcepts(theory.conjectures, concept_to_avoid);
        //Vector conjectured_equivalent_concepts = concept_to_avoid.conjectured_equivalent_concepts;

        //method1 looks at the list of conjectured equivalent concepts and
        //tries to apply the pr compose successively until there's only
        //one remaining concept - then returns this concept. note that
        //compose is a bit funny and needs looking at. It currently
        //doesn't check that the concept is not one in the original
        //conjecture.
        boolean method1 = false;

        //method2 looks at the list of conjectured equivalent concepts and
        //tries to pick the best one - get the most interesting; check
        //whether it covers the monster, and check that it isn't in the
        //conjecture under discussion.
        boolean method2 = true;

        if(method1)
        {
            addConjecturedEquivalentConceptsToTheory(conjectured_equivalent_concepts, theory); //may need to remove them at some point

            window.writeToFrontEnd("conjectured_equivalent_concepts are: ");
            for(int i=0;i<conjectured_equivalent_concepts.size();i++)
            {
                Concept current_concept = (Concept)conjectured_equivalent_concepts.elementAt(i);
                window.writeToFrontEnd(current_concept.writeDefinition("ascii"));
            }

            theory.make_equivalences_from_equality = false;
            new_concept = makeConjunctionFromVector(conjectured_equivalent_concepts, theory);
            theory.make_equivalences_from_equality = theory.front_end.make_equivalences_from_equality_check.getState();

            window.writeToFrontEnd("in method1; new_suggested_concept is " + new_concept.writeDefinition());
        }

        if(method2)
        {
            /**
             boolean entity_covered_by_all_concepts = false;
             window.writeToFrontEnd("sat - starting the wee test");

             for(int i=0; i<theory.entities.size();i++)
             {
             Entity current_entity = (Entity)theory.entities.elementAt(i);
             window.writeToFrontEnd("current_entity is " + current_entity.toString());
             entity_covered_by_all_concepts = entityCoveredByAllConcepts(conjectured_equivalent_concepts, current_entity, window);
             window.writeToFrontEnd("entity_covered_by_all_concepts is " + entity_covered_by_all_concepts);
             }
             window.writeToFrontEnd("monster_entity is " + monster_entity.toString());
             window.writeToFrontEnd("testing the monster now");
             entity_covered_by_all_concepts = entityCoveredByAllConcepts(conjectured_equivalent_concepts, monster_entity, window);
             window.writeToFrontEnd("entity_covered_by_all_concepts is " + entity_covered_by_all_concepts);

             window.writeToFrontEnd("sat - finished the wee test -- hope it worked!");
             */

            //check 1 - at least one concept doesn't cover the monster
            window.writeToFrontEnd("the monster_entity is " + monster_entity);
            window.writeToFrontEnd("conjectured_equivalent_concepts.size() is " + conjectured_equivalent_concepts.size());

            //test
            for(int i=0;i<theory.concepts.size();i++)
            {
                Concept current_concept = (Concept)theory.concepts.elementAt(i);
                window.writeToFrontEnd("the " + i + "th concept in the theory is "
                        + current_concept.writeDefinition());

                Row row = current_concept.calculateRow(theory.concepts, monster_entity.toString());

                window.writeToFrontEnd("row.entity is " + row.entity);
                window.writeToFrontEnd("row.tuples are " + row.tuples);
            }

            //end test



            for(int i=0;i<conjectured_equivalent_concepts.size();i++)
            {
                Concept current_concept = (Concept)conjectured_equivalent_concepts.elementAt(i);
                window.writeToFrontEnd("the " + i + "th conjectured equivalent concept is "
                        + current_concept.writeDefinition());

                Row row = current_concept.calculateRow(theory.concepts, monster_entity.toString());
                window.writeToFrontEnd("row.entity is " + row.entity);
                window.writeToFrontEnd("row.tuples are " + row.tuples);
                boolean b1 = cover_monster;
                boolean b2 = row.tuples.isEmpty();

                window.writeToFrontEnd("cover_monster is " + b1);
                window.writeToFrontEnd("row.tuples.isEmpty() is " + b2);


                //do we want a concept which covers the monster?
                //if we don't want to cover monster then remove the concepts which do cover it.  --> this is too strict.
                if(!cover_monster && !(row.tuples.isEmpty()))
                {
                    window.writeToFrontEnd("1");
                    conjectured_equivalent_concepts.removeElementAt(i);
                    i--;
                }
                //if we do want to cover monster then remove the concepts which do not cover it
                if(cover_monster && row.tuples.isEmpty())
                {
                    window.writeToFrontEnd("2");
                    conjectured_equivalent_concepts.removeElementAt(i);
                    i--;
                }
            }

            window.writeToFrontEnd("after check one, conjectured_equivalent_concepts are " +
                    conjectured_equivalent_concepts);

            window.writeToFrontEnd("now going through the concepts on the conj to check they aren't the same ");

            //check 2 - the suggested concept is not in the conj under discussion
            Vector concepts_in_conjecture = conjecture_under_discussion.conceptsInConjecture();
            for(int i=0;i<conjectured_equivalent_concepts.size();i++)
            {
                Concept current_concept = (Concept)conjectured_equivalent_concepts.elementAt(i);
                window.writeToFrontEnd("our " + i + "th  conjectured_equivalent_concept is "
                        + current_concept.writeDefinition());


                for(int j=0;j<concepts_in_conjecture.size();j++)
                {
                    Concept concept = (Concept)concepts_in_conjecture.elementAt(j);
                    if(current_concept.equals(concept.writeDefinition()))
                    {
                        window.writeToFrontEnd("current_concept is " + current_concept.writeDefinition() +
                                " and concept is " + concept.writeDefinition());
                        window.writeToFrontEnd("current_concept.equals(concept.writeDefinition()) is "
                                + current_concept.equals(concept.writeDefinition()));
                        window.writeToFrontEnd("3: conjectured_equivalent_concepts is "
                                +conjectured_equivalent_concepts);

                        conjectured_equivalent_concepts.removeElementAt(i);
                        window.writeToFrontEnd("4: conjectured_equivalent_concepts is NOW "
                                +conjectured_equivalent_concepts);
                        i--;
                    }
                }
            }

            //write out

            window.writeToFrontEnd("conjectured_equivalent_concepts are now: ");
            for(int i=0;i<conjectured_equivalent_concepts.size();i++)
            {
                Concept current_concept = (Concept)conjectured_equivalent_concepts.elementAt(i);
                window.writeToFrontEnd(current_concept.writeDefinition());
            }

            new_concept = getMostInterestingConcept(conjectured_equivalent_concepts);
            //window.writeToFrontEnd("in method2; new_suggested_concept is " + new_concept.writeDefinition());
        }

        return new_concept;
    }

    /** Add all the concepts in conjectured_equivalent_concepts to the
     * concepts list in theory, if they aren't aready in there.
     */
    public void addConjecturedEquivalentConceptsToTheory(Vector conjectured_equivalent_concepts, Theory theory)
    {
        for(int i=0;i<conjectured_equivalent_concepts.size();i++)
        {
            Concept concept_to_add = (Concept)conjectured_equivalent_concepts.elementAt(i);

            boolean found=false;
            for(int j=0;j<theory.concepts.size();j++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(j);
                if((concept.id).equals(concept_to_add.id))
                {
                    found = true;
                    break;
                }
            }
            if(found==false)
                theory.concepts.addElement(concept_to_add);
        }
    }


    /** Takes in a vector of concepts -- all of arity 1 -- and an
     * entity, and returns true if the entity satisfies all of the
     * concepts, false otherwise. -- SAT
     */

    public boolean entityCoveredByAllConcepts(Vector concepts, Entity entity, AgentWindow window)
    {
        boolean oksofar = false;
        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            //window.writeToFrontEnd("concept.datatable.toTable() is " + concept.datatable.toTable());

            for(int j=0;j<concept.datatable.size();j++)
            {
                Row row = (Row)concept.datatable.elementAt(j);
                //window.writeToFrontEnd("row.entity is " + row.entity);
                //window.writeToFrontEnd("row.tuples is " + row.tuples);

                if((row.entity).equals(entity.toString()) && !(row.tuples.isEmpty()))
                {
                    oksofar = true;
                    break;
                }
            }
            if(!oksofar)
                break;
        }
        return oksofar;
    }


    /** Given a vector of concepts, returns a new Concept which is the
     * conjunction of all of the concepts. Note - there's a weird
     * problem - compose doesn't return what you'd expect it to - eg if
     * we force the same concepts and compose, we get back a different
     * answer. Here it seems to return `a is a group' for the concepts
     * we give it - which is exactly what we don't want. Note that we
     * also need to think about what we want if the result of step is a
     * conjecture rather than a concept - we currently ignore it if this
     * happens.
     */

    public Concept makeConjunctionFromVector(Vector concepts, Theory theory)
    {
        Concept output = new Concept();
        Vector two_concept_vector = new Vector();
        Vector parameters = new Vector();
        //System.out.println("friday afternoon...");

        while(!(concepts.isEmpty()))
        {
            //write out the concepts
            //System.out.println("new loop -- concepts is: ");
            // for(int i=0;i<concepts.size();i++)
// 	  {
// 	    System.out.println(((Concept)concepts.elementAt(i)).writeDefinition());
// 	  }

            if(concepts.size()==1)
            {
                //System.out.println("got 1 concept left");
                output = (Concept)concepts.elementAt(0);
                break;
            }
            if(concepts.size()>1)
            {
                Concept concept1 = (Concept)concepts.elementAt(0);
                Concept concept2 = (Concept)concepts.elementAt(1);
                //System.out.println("concept1 is " + concept1.writeDefinition());
                //System.out.println("concept2 is " + concept2.writeDefinition());
                concepts.removeElementAt(0);
                concepts.removeElementAt(0);

                two_concept_vector.addElement(concept1);
                two_concept_vector.addElement(concept2);
                parameters = theory.compose.allParameters(two_concept_vector, theory);
                //System.out.println("parameters is " + parameters);

                String agenda_line = "id_of_dummy1 = " + concept1.id + " " + concept2.id + " compose [1] ";
                //System.out.println("agenda_line is " + agenda_line);
                Step forced_step = guider.forceStep(agenda_line, theory);
                TheoryConstituent tc_dummy = new TheoryConstituent();
                tc_dummy = forced_step.result_of_step;
                //System.out.println("tc_dummy is " + tc_dummy + ": " + tc_dummy.writeTheoryConstituent());


                if(tc_dummy instanceof Concept)
                {
                    //System.out.println("got a concept");
                    Concept concept_to_add = (Concept)tc_dummy;
                    concept_to_add.lakatos_method = "piecemeal_exclusion";
                    //System.out.println("concept_to_add is " + concept_to_add.writeDefinition());
                    concepts.insertElementAt(concept_to_add, 0);
                }
            }
        }
        //System.out.println("finished makeConjunctionFromVector - returning output = " + output.writeDefinition());
        return output;
    }



    /** Given a vector of concepts, returns a new Concept which is the
     * conjunction of all of the concepts.
     */

    public TheoryConstituent testForce(Vector concepts, Theory theory)
    {
        //System.out.println("starting testForce");

        TheoryConstituent tc = new TheoryConstituent();

        Vector parameters = new Vector();

        //test 1

        /** System.out.println("test 1 starts here");
         for(int i=0; i<concepts.size();i++)
         {
         Concept concept = (Concept)concepts.elementAt(i);
         System.out.println("concept.id is " + concept.id);
         }

         if(concept.id.equals("group005a"))
         {
         System.out.println("Got our concept");
         Vector v = new Vector();
         v.addElement(concept);
         parameters = theory.exists.allParameters(v, theory);
         System.out.println("parameters is " + parameters);



         String agenda_line = "id_of_dummy1 = " + concept.id + " exists [1] ";
         System.out.println("agenda_line is " + agenda_line);
         Step forced_step = guider.forceStep(agenda_line, theory);
         TheoryConstituent tc_dummy = new TheoryConstituent();
         tc_dummy = forced_step.result_of_step;
         System.out.println("tc_dummy is " + tc_dummy);
         tc = tc_dummy;


         if(tc_dummy instanceof Concept)
         {
         System.out.println("got a concept");
         System.out.println(((Concept)tc_dummy).writeDefinition());
         }

         else
         if(tc_dummy instanceof Equivalence)
         {
         System.out.println("got an Equivalence");

         }

         else
         if(tc_dummy instanceof Conjecture)
         {
         System.out.println("got a conjecture");
         System.out.println(((Conjecture)tc_dummy).writeConjecture("ascii"));
         }
         else
         if(tc_dummy instanceof Entity)
         {
         System.out.println("got an entity");
         System.out.println(((Entity)tc_dummy).toString());
         }

         else
         System.out.println("haven't got a theory constituent");
         }
         }
         System.out.println("done test 1");

         */
        //test 2
        //System.out.println("onto test 2");
        Vector two_concept_vector = new Vector();

        //System.out.println("into compose test");
        for(int i=0; i<concepts.size();i++)
        {
            Concept concept1 = (Concept)concepts.elementAt(i);
            //System.out.println("concept1.id is " + concept1.id);

            //if(concept1.id.equals("g37_0"))
            if(concept1.id.equals("group001"))
            {
                //System.out.println("here - i");
                for(int j=0; j<concepts.size();j++)
                {
                    //System.out.println("here - ii");
                    Concept concept2 = (Concept)concepts.elementAt(j);
                    //if(concept2.id.equals("g184_0"))

                    if(concept2.id.equals("g17_0"))
                    {
                        //System.out.println("here -iii");
                        two_concept_vector.addElement(concept1);
                        two_concept_vector.addElement(concept2);
                        parameters = theory.compose.allParameters(two_concept_vector, theory);
                        //System.out.println("parameters  is " + parameters);
                        String agenda_line = "id_of_dummy1 = " + concept1.id + " " + concept2.id + " compose [1] ";
                        //System.out.println("agenda_line is " + agenda_line);
                        Step forced_step = guider.forceStep(agenda_line, theory);
                        //System.out.println("testForce - 1");
                        TheoryConstituent tc_dummy = new TheoryConstituent();
                        //System.out.println("testForce - 2");
                        tc_dummy = forced_step.result_of_step;
                        // System.out.println("testForce - tc_dummy is " + tc_dummy);



                        if(tc_dummy instanceof Concept)
                        {
                            //System.out.println("got a concept");

                            Concept concept_to_add = (Concept)tc_dummy;
                            //System.out.println("concept_to_add is " + concept_to_add.writeDefinition());
                            concepts.insertElementAt(concept_to_add, 0);
                        }

                        else
                        if(tc_dummy instanceof Equivalence)
                        {
                            //System.out.println("got an Equivalence");
                            concepts.insertElementAt(concept1, 0);
                        }

                        else
                        if(tc_dummy instanceof Conjecture)
                        {
                            //System.out.println("got a conjecture");
                            System.out.println(((Conjecture)tc_dummy).writeConjecture("ascii"));
                        }
                        else
                        if(tc_dummy instanceof Entity)
                        {
                            //System.out.println("got an entity");
                            System.out.println(((Entity)tc_dummy).toString());
                        }

                        else
                            System.out.println("haven't got a theory constituent");
                        break;
                    }
                }
            }
        }
        //System.out.println("finished testForce - returning tc = " + tc.writeTheoryConstituent());
        return tc;
    }





    /** Starts a Mace process which tries to satisfy all the axioms
     * given to it. (For counters we negate the conjecture and add it to
     * the list of axioms.) It returns any counters found in a
     * vector. Use this for proof and refutations method. need to input
     * the axioms as conjectures in a lemma - need a test case in group
     * theory.
     */
    public Vector useMaceToGenerateCounters(Vector axioms, String conjecture, AgentWindow window)
    {
        Vector data_hashtables = new Vector();
        String negated_conjecture = "-(" + conjecture + ").";
        Vector entities_contradicting_conjecture_strings = new Vector();
        Vector entities_contradicting_conjecture = new Vector();
        int max_model_size = 8;
        int mace_max_time = 10;
        Mace mace = new Mace();

        //Vector axioms = getAxiomsForMace(conjectured_equivalent_concepts);

        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("delmodel.in")));
            out.println("set(auto).\nformula_list(usable).");
            for (int i=0; i<axioms.size(); i++)
                out.println((String)axioms.elementAt(i));
            out.println(negated_conjecture);
            out.println("end_of_list.");
            out.close();
        }
        catch(Exception e)
        {
        }

        for (int i=0; i<max_model_size; i++)
        {
            try
            {
                Process mace_process =
                        Runtime.getRuntime().exec("./call_mace " + Integer.toString(i) + " " + Integer.toString(mace_max_time));
                int exit_value = mace_process.waitFor();
                mace_process.destroy();
                Hashtable ht = getDataHashtable(mace, window);

                if (ht!=null)
                {
                    String entity = (String)ht.get("repn");
                    entities_contradicting_conjecture_strings.addElement(entity); //for counters
                    data_hashtables.addElement(ht);
                }
            }
            catch(Exception e)
            {
            }
        }

        for(int i=0;i<entities_contradicting_conjecture_strings.size(); i++)
        {
            String entity_string = (String)entities_contradicting_conjecture_strings.elementAt(i);
            entities_contradicting_conjecture.addElement(new Entity(entity_string));
        }

        window.writeToFrontEnd("entities_contradicting_conjecture are " + entities_contradicting_conjecture_strings);
        return entities_contradicting_conjecture;
    }



    /** This method comes from TM - written by Simon
     */
    public void getPositivesAndNegatives(Vector axioms, String conjecture, AgentWindow window)
    {
        Vector data_hashtables = new Vector();
        String negated_conjecture = "-(all b c (b * c = c * b)).";//need to negate here
        Vector entities_supporting_conjecture = new Vector();
        Vector entities_contradicting_conjecture = new Vector();
        int max_model_size = 8;
        int mace_max_time = 10;
        Mace mace = new Mace();

        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("delmodel.in")));
            out.println("set(auto).\nformula_list(usable).");
            for (int i=0; i<axioms.size(); i++)
                out.println((String)axioms.elementAt(i));
            out.println(negated_conjecture);
            out.println("end_of_list.");
            out.close();
        }
        catch(Exception e)
        {
        }

        for (int i=0; i<max_model_size; i++)
        {
            try
            {
                Process mace_process =
                        Runtime.getRuntime().exec("./call_mace " + Integer.toString(i) + " " + Integer.toString(mace_max_time));
                int exit_value = mace_process.waitFor();
                mace_process.destroy();
                Hashtable ht = getDataHashtable(mace, window);

                if (ht!=null)
                {
                    String entity = (String)ht.get("repn");
                    entities_contradicting_conjecture.addElement(entity); //for counters
                    data_hashtables.addElement(ht);
                }
            }
            catch(Exception e)
            {
            }
        }
        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("delmodel.in")));
            out.println("set(auto).\nformula_list(usable).");
            for (int i=0; i<axioms.size(); i++)
                out.println((String)axioms.elementAt(i));
            out.println(conjecture);
            out.println("end_of_list.");
            out.close();
        }
        catch(Exception e)
        {
        }

        for (int i=0; i<max_model_size; i++)
        {
            try
            {
                Process mace_process =
                        Runtime.getRuntime().exec("./call_mace " + Integer.toString(i) + " " + Integer.toString(mace_max_time));
                int exit_value = mace_process.waitFor();
                mace_process.destroy();
                Hashtable ht = getDataHashtable(mace, window);
                if (ht!=null)
                {
                    String entity = (String)ht.get("repn");
                    entities_supporting_conjecture.addElement(entity);//for positives
                    data_hashtables.addElement(ht);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        window.writeToFrontEnd("entities_supporting_conjecture are " + entities_supporting_conjecture);
        window.writeToFrontEnd("entities_contradicting_conjecture are " + entities_contradicting_conjecture);
    }

    /** Written by Simon for TM
     */
    public Hashtable getDataHashtable(Mace mace, AgentWindow window)
    {
        String repn = "";
        Hashtable ht = mace.readModelFromMace("delmodel.out");
        Vector mults = (Vector)ht.get("*");
        Vector adds = (Vector)ht.get("+");

        if (mults!=null)
        {
            for (int i=0; i<mults.size(); i++)
                repn += (String)((Vector)mults.elementAt(i)).elementAt(2);

            if (adds!=null)
            {
                repn += ":";
                for (int i=0; i<adds.size(); i++)
                    repn += (String)((Vector)adds.elementAt(i)).elementAt(2);
            }
            ht.put("repn", repn);
        }
        else
            return null;

        return ht;
    }


    /** Given a vector of concepts, this returns a vector in Mace (and Otter) format.
     */
    public Vector getAxiomsForMace(Vector concepts)
    {
        //Vector axioms_test = new Vector();
        //String first = "all a (a * id = a & id * a = a).";
        //String second = "all a ((a * inv(a) = id) & (inv(a) * a = id)).";
        //String third = "all a b c ((a * b) * c = a * (b * c)).";
        //axioms_test.addElement(first);
        //axioms_test.addElement(second);
        //axioms_test.addElement(third);
        //window.writeToFrontEnd("axioms_test are " + axioms_test);

        //String conj_test = "all b c (b * c = c * b)";
        //new_counters_strings = useMaceToGenerateCounters(axioms_test, conj_test, window);

        Vector axioms = new Vector();
        for(int i=0;i<concepts.size();i++)
        {
            Concept current_concept = (Concept)concepts.elementAt(i);
            String s = current_concept.writeDefinition("otter")+".";
            axioms.addElement(s);
        }
        return axioms;
    }

    /** Given a vector of concepts, this returns the most interesting concept.
     */
    public Concept getMostInterestingConcept(Vector concepts)
    {
        Concept output = new Concept();
        double highest_score_so_far = 0;

        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if(concept.interestingness>=highest_score_so_far)
                output = concept;
        }
        return output;
    }


    public Vector findConjecturedEquivalenceConcepts(Vector conjectures, Concept concept_equiv_to)
    {
        Vector concepts = new Vector();
        for(int i=0;i<conjectures.size();i++)
        {
            Conjecture conj = (Conjecture)conjectures.elementAt(i);
            if(conj instanceof Equivalence)
            {
                Equivalence equiv = (Equivalence)conj;
                if(equiv.lh_concept.equals(concept_equiv_to))
                    concepts.addElement(equiv.rh_concept);
            }
        }
        return concepts;
    }

    // tested - but not extensively - esp conjectureBrokenByEntity
    // methods for NearEquiv and NearImp
    public double percentageConjecturesBrokenByEntity(Theory theory, Entity entity, AgentWindow window)
    {
        //window.writeToFrontEnd("wed -----testing entity" + entity);
        //System.out.println("wed -----");
        double output;
        int num_broken = 0;

        for (int i = 0; i<theory.conjectures.size(); i++)
        {
            Conjecture conjecture = (Conjecture)theory.conjectures.elementAt(i);
            if (conjecture.conjectureBrokenByEntity(entity.toString(), theory.concepts))
            {
                window.writeToFrontEnd(entity.toString() + " is a counter to " +conjecture.writeConjecture());
                //System.out.println(entity.toString() + " is a counter to " +conjecture.writeConjecture());
                num_broken ++;
            }
        }

        output = (num_broken*100)/theory.conjectures.size();
        //window.writeToFrontEnd("wed -----output is " + output);
        return output;
    }

    /** Calculates whether the entity in question forces other entities
     into being counterexamples for a higher number of conjectures than
     the proportion specified. If so, returns yes, otherwise returns
     no. needs testing */

    public String conjectureBrokenByCulpritEntity(Theory theory, AgentWindow window, int monster_barring_culprit_min)
    {
        int num_conjs_in_theory = theory.conjectures.size();
        int min_num_conj_needed = monster_barring_culprit_min/100*num_conjs_in_theory;
        int num_conj_so_far = 0;
        int conj_checked_so_far = 0;

        while(num_conj_so_far<min_num_conj_needed && conj_checked_so_far<num_conjs_in_theory)
        {
            Conjecture conj = (Conjecture)theory.conjectures.elementAt(conj_checked_so_far);
            String culprit_entity = conjectureBrokenByCulpritEntity(theory, conj, window);
            //don't know if it's always the same culprit entity...
            if(culprit_entity.equals("yes"))
                num_conj_so_far++;
            conj_checked_so_far++;
        }
        window.writeToFrontEnd("The culprit entity forces other entities into being counterexamples for "
                + num_conj_so_far + " of my conjectures");

        boolean b1 = num_conj_so_far>min_num_conj_needed;
        boolean b2 = num_conj_so_far<=min_num_conj_needed;
        if(b1)
            window.writeToFrontEnd(num_conj_so_far + " is greater than " + min_num_conj_needed
                    + ", which is the minimum number of conjectures neededed, so I'm going to perform monster-barring");
        if(b2)
            window.writeToFrontEnd(num_conj_so_far + " is less than or equal to " + min_num_conj_needed
                    + ", which is the minimum number of conjectures neededed, so I'm not going to perform monster-barring");
        if(num_conj_so_far>min_num_conj_needed)
            return"yes";
        else
            return "no";
    }

    /** Tests to see whether a given conjecture is broken by a single
     entity or not, i.e. if a single entity is responsible for all of
     the entities in the theory being counterexamples. If so, returns
     the culprit entity as a String. Otherwise returns "no". Currently
     only works for NonExists conjectures. Note - does *not* mean that
     there is only one counterexample to the conjecture. tested (not
     extensively) and working for nonexists conjectures*/

    public String conjectureBrokenByCulpritEntity(Theory theory, Conjecture conjecture, AgentWindow window)
    {
        String output = "no";
        window.writeToFrontEnd("just started conjectureBrokenByCulpritEntity");
        Conjecture conj = conjecture.reconstructConjecture(theory, window);
        if(conj instanceof NonExists)
        {
            NonExists ne_conj = (NonExists)conj;
            window.writeToFrontEnd("got a nonExists conj - " + ne_conj.writeConjecture("ascii"));
            window.writeToFrontEnd("ne_conj.getCountersToConjecture() are " + ne_conj.getCountersToConjecture());
            window.writeToFrontEnd("ne_conj.concept.datatable.toTable() is " + ne_conj.concept.datatable.toTable());
            window.writeToFrontEnd("ne_conj.concept.datatable.isEmpty() is " + ne_conj.concept.datatable.isEmpty());
            if(!ne_conj.concept.datatable.isEmpty())
            {
                window.writeToFrontEnd("its datatable isn't empty");
                Row row = (Row)ne_conj.concept.datatable.elementAt(0);
                Vector vector_in_tuple = (Vector)row.tuples.elementAt(0);
                window.writeToFrontEnd("vector_in_tuple is " + vector_in_tuple);
                if(!vector_in_tuple.isEmpty())
                {
                    String entity_on_row_0 = (String)vector_in_tuple.elementAt(0);
                    window.writeToFrontEnd("entity_on_row_0 is " + entity_on_row_0);
                    ne_conj.concept.datatable.sameExampleForEveryEntity(theory, entity_on_row_0, window);
                    output = entity_on_row_0;
                }
            }
        }
        window.writeToFrontEnd("just finished conjectureBrokenByCulpritEntity... returning " + output);
        return output;
    }



    /** Returns a single entity which means that all entities in the
     theory break the conjecture, i.e. with this entity they are
     all counterexamples, and without it none of them would be
     counterexamples.[not sure about this]

     public Entity getCulpritBreaker(Theory theory, Conjecture conjecture)
     {

     }
     */


    //i.e. if with the entity we can construct a non-exists conjecture
    //with no counters
    // and without it we can reconstruct the original conjecture without
    // any counters [what if it was a non-exists????]

    /** Returns true if the given entity forces all other entities in
     the theory/concept to break the conjecture, false otherwise. That
     is, it tests to see how many counters the conjecture has (n1) -
     before adding the potential culprit - then temporarily adds the
     potential culrit to its theory, reconstructs the conjecture and
     checks to see how many counters there are now. If there are more
     than n1 + 1 then return yes - no otherwise. Note - this returns
     yes if the culprit counter forces *any* other entity to be a
     counter, rather than forcing *every* other entity in the theory
     to be a counter. [Could return a percentage of the number of
     entities which it forces to become counters.] */

    public boolean entityIsCulpritBreaker(Theory theory, Conjecture conjecture, AgentWindow window, Entity entity)
    {
        // 	datatable.sameExampleForEveryEntity(theory, entity, window);
        //ne_conj.concept.datatable.sameExampleForEveryEntity(theory, entity_on_row_0.toString(), window);


        //find out how many counters there are before adding the potential culprit
        Conjecture conj = conjecture.reconstructConjecture(theory, window);
        Vector counters_without_culprit = conj.getCountersToConjecture(); //prob here

        int num_counters_without_culprit = counters_without_culprit.size();

        window.writeToFrontEnd("counters_without_culprit are " + counters_without_culprit);
        window.writeToFrontEnd("counters_without_culprit.size() is " + counters_without_culprit.size());
        window.writeToFrontEnd("theory.entities.size() is " + theory.entities.size());

        Conjecture conj_with_new_entity = conjecture.reconstructConjectureWithNewEntity(theory, window, entity);//PROBLEM hererererer

        Vector counters_with_culprit = conj_with_new_entity.getCountersToConjecture();
        int num_counters_with_culprit = counters_with_culprit.size();
        window.writeToFrontEnd("counters_with_culprit are " + counters_with_culprit);
        window.writeToFrontEnd("counters_with_culprit.size() is " + counters_with_culprit.size());
        boolean b = num_counters_without_culprit+1<num_counters_with_culprit;
        window.writeToFrontEnd("num_counters_without_culprit+1<num_counters_with_culprit is " + b);


        if (num_counters_without_culprit+1<num_counters_with_culprit)
            return true;
        else
            return false;
        //if (counters.size().equals(theory.entities.size()))
        //    {
        //		theory.entities.removeElement(entity);
        //    }

        //return true;
    }


    //generate definition
    public TheoryConstituent generateDefinitionGivenEntities(Vector positive_entity_strings, Concept concept_to_avoid, String counter_string)
    {
        TheoryConstituent output = new TheoryConstituent();

        Concept poss_concept = (Concept)getNewConceptWhichExactlyCoversGivenEntities(positive_entity_strings, concept_to_avoid);
        //Concept poss_concept = (Concept)getConceptWhichExactlyCoversGivenEntities(positive_entity_strings);
        if(poss_concept.positivesContains(counter_string))
            return output;
        else
            return (TheoryConstituent)poss_concept;

    }

    /**************************************************************************************/
    //                                 METHOD 5                                           //
    //                           LEMMA INCORPORATION                                      //
    /**************************************************************************************/


    /** given the reconstructed conjecture and proof object, the student:
     case 1:
     1) looks to see if got counters to the conj;
     2) if so, looks to see whether these counters are counters to any of the lemmas in the proof;
     3) if so, takes the first lemma and forms the concept - `objects which satisfy this lemma'
     or checks to see whether it has any concepts in its theory which exactly fit the positives in
     the lemmas (ie performs strategic withdrawal on the lemma
     4) the student then changes the conj to apply only to objects of this new kind
     5) the student returns the improved conjecture and the same proof
     [note, could check that none of the other cases also apply before sending back in]
     */
    public ProofScheme lemmaIncorporation(Theory theory, ProofScheme proofscheme, AgentWindow window)
    {
        //System.out.println("starting lemma-incorporation");

        //print out the proofscheme

        window.writeToFrontEnd("The initial proofscheme is:");
        window.writeToFrontEnd(proofscheme);

        //System.out.println("proofscheme.conj is " + proofscheme.conj.writeConjecture());
        //System.out.println("proofscheme.conj.counterexamples are: ");
        for(int i=0;i<proofscheme.conj.counterexamples.size();i++)
        {
            Entity entity = (Entity)proofscheme.conj.counterexamples.elementAt(i);
            System.out.println(entity.toString());
        }

        //System.out.println("proofscheme.proof_vector is:");
        for(int i=0;i<proofscheme.proof_vector.size();i++)
        {
            Conjecture current_conj = (Conjecture)proofscheme.proof_vector.elementAt(i);
            //System.out.println("("+i+") " +current_conj.writeConjecture());
        }


        ProofScheme improved_proofscheme = new ProofScheme();
        Vector counters_to_global = proofscheme.conj.counterexamples;
        boolean got_counters_to_global = !counters_to_global.isEmpty();
        //System.out.println("l-inc: counters_to_global are " + counters_to_global);


        //determine which type of lemma-inc to do
        //type 1: counters to the global conj and the same counters to one of the local lemmas
        if(got_counters_to_global)
        {
            //System.out.println("l-inc: got_counters_to_global is " + got_counters_to_global);
            Conjecture faulty_lemma = getFaultyLemma(proofscheme, counters_to_global,theory);
            //System.out.println("l-inc: faulty_lemma is: " + faulty_lemma.writeConjecture());
            boolean same_counters_to_local = !((faulty_lemma.writeConjecture()).equals(""));
            //System.out.println("l-inc: same_counters_to_local is " + same_counters_to_local);

            if(same_counters_to_local)//this needs changing
                improved_proofscheme = globalAndLocalLemmaIncorporation(theory, proofscheme, faulty_lemma, window);

            else
            {

                //  //start test
// 	      System.out.println("Tuesday night ----------------- the counters_to_global are " + counters_to_global);
// 	      Conjecture conj = testForRelatedCounter(proofscheme, counters_to_global);

// 	      System.out.println("Tuesday night ----------------- the conj is " + conj.writeConjecture());
// 	      boolean b2 = (conj==null);
// 	      System.out.println("b2 is " + b2);

// 	      if(!(conj==null))
// 		{
// 		  System.out.println("right then -- going to perform globalAndLocalLemmaIncorporation where conj is "  +
// 				     conj.writeConjecture());

// 		  //stage 2 - fix faulty lemma by adding an explicit condition which
// 		  //the counter does break (or adding a lemma)
// 		  Conjecture explicit_lemma = fixHiddenFaultyLemma(conj, proofscheme, theory);
// 		  System.out.println("explicit_lemma is " + explicit_lemma.writeConjecture());
// 		  ProofScheme intermediate_proofscheme = createNewProofScheme(conj,explicit_lemma,proofscheme);
// 		  System.out.println("\n\nintermediate_proofscheme is: " + intermediate_proofscheme.writeProofScheme());

// 		  //stage 3 - perform explicit lemma-incoroporation on it

// 		  System.out.println("right then again -- i've got to here now. i'm gonna perform globalAndLocal on "
// 				     + explicit_lemma.writeConjecture());


// 		  improved_proofscheme = globalAndLocalLemmaIncorporation(theory, proofscheme, explicit_lemma, window);
// 		  System.out.println("TEST of your new globalAndLocal method ENDS HERE ");
// 		}
// 	      //end test

                //else
                //System.out.println("GOING INTO THE NEW HIDDEN METHOD HERE ");
                improved_proofscheme = hiddenLemmaIncorporation_new(theory, proofscheme, window);
            }
        }

        if(!got_counters_to_global)
        {
            Conjecture faulty_lemma = countersInProofScheme(proofscheme);
            //System.out.println("faulty_lemma is " + faulty_lemma.writeConjecture());

            boolean counters_to_local = !(faulty_lemma.writeConjecture().equals(""));//this doesn't work
            if(!counters_to_local)
                System.out.println("I don't know of any global nor local counters, so I'm going to return the same proof I was given.");
            if(counters_to_local)
                improved_proofscheme = onlyLocalLemmaIncorporation(theory,proofscheme,faulty_lemma,window);
        }

        //if you've done nothing to the proofscheme then return the original proofscheme
        if(improved_proofscheme.isEmpty())
        {
            window.writeToFrontEnd("I can't find anything wrong with the proof scheme, so I'm returning that one");
            return proofscheme;
        }

        //print out the proofscheme
        System.out.println("finishing lemma-incorporation - returning");

        System.out.println("improved_proofscheme.conj is " + improved_proofscheme.conj.writeConjecture());
        System.out.println("improved_proofscheme.conj.counterexamples are: ");
        for(int i=0;i<improved_proofscheme.conj.counterexamples.size();i++)
        {
            Entity entity = (Entity)improved_proofscheme.conj.counterexamples.elementAt(i);
            System.out.println(entity.toString());
        }

        System.out.println("improved_proofscheme.proof_vector is:");
        for(int i=0;i<improved_proofscheme.proof_vector.size();i++)
        {
            Conjecture current_conj = (Conjecture)improved_proofscheme.proof_vector.elementAt(i);
            System.out.println("("+i+") " +current_conj.writeConjecture());
        }

        window.writeToFrontEnd("The improved proofscheme is:");
        window.writeToFrontEnd(improved_proofscheme);

        return improved_proofscheme;
    }

    //eg hollow cube - a counter to both global conj and a local lemma
    public ProofScheme globalAndLocalLemmaIncorporation(Theory theory, ProofScheme proofscheme,
                                                        Conjecture faulty_lemma, AgentWindow window)
    {
        System.out.println("starting globalAndLocalLemmaIncorporation ");
        ProofScheme improved_proofscheme = new ProofScheme();
        improved_proofscheme.proof_vector = (Vector)(proofscheme.proof_vector).clone();
        Conjecture global_conj = proofscheme.conj;
        System.out.println("globalAndLocal l-inc -- global_conj is " + global_conj.writeConjecture());

        System.out.println("globalAndLocal l-inc -- faulty_lemma is " + faulty_lemma.writeConjecture());
        // make a concept - objects which satisfy this lemma


        Vector entity_strings = new Vector();
        for(int i=0; i<global_conj.counterexamples.size();i++)
        {
            Entity current_entity = (Entity)global_conj.counterexamples.elementAt(i);
            entity_strings.addElement(current_entity.toString());
        }

        Concept core_concept = findObjectOfInterestConcept(theory.concepts, entity_strings);
        System.out.println("globalAndLocal l-inc -- core_concept is " + core_concept.writeDefinition());

        //need to write each case separately - fixme
        if(global_conj instanceof NearImplication && faulty_lemma instanceof NearImplication)
        {
            System.out.println("l-inc: got 2 nimps");

            NearImplication global_conj_nimp = (NearImplication)global_conj;
            NearImplication faulty_lemma_nimp = (NearImplication)faulty_lemma;

            //need to test it on concepts of a different arity. the
            //arguments to compose will change (maybe use the
            //getParameters method)

            String production_rule = " compose [1";

            for(int i=1;i<faulty_lemma_nimp.rh_concept.arity;i++)
            {
                production_rule = production_rule + ",0";
            }
            production_rule = production_rule + "] ";

            System.out.println("production_rule is " + production_rule);
            String agenda_line1 = "id_of_dummy1 = " + faulty_lemma_nimp.rh_concept.id + " " + core_concept.id + production_rule;

            //String agenda_line1 = "id_of_dummy1 = " + faulty_lemma_nimp.rh_concept.id + " " + core_concept.id + " compose [1] ";
            System.out.println("agenda_line1 is " + agenda_line1);
            Step forced_step1 = guider.forceStep(agenda_line1, theory);
            TheoryConstituent tc_dummy1 = new TheoryConstituent();
            tc_dummy1 = forced_step1.result_of_step;
            System.out.println("globalAndLocal l-inc: tc_dummy1 is " + tc_dummy1.writeTheoryConstituent());

            if(tc_dummy1 instanceof Concept)
            {
                System.out.println("globalAndLocal l-inc: tc_dummy1 is a concept");
                Concept tc_dummy1_concept = (Concept)tc_dummy1;
                tc_dummy1_concept.lakatos_method = "lemma-incorporation";
                Implication imp = new Implication();
                imp.lh_concept = (Concept)tc_dummy1_concept;
                imp.rh_concept = global_conj_nimp.rh_concept;
                System.out.println("globalAndLocal l-inc: just made a new conjecture - imp=" + imp.writeConjecture());
                improved_proofscheme.conj = imp;
                System.out.println("globalAndLocal l-inc: setting the new proofscheme to have our new imp ");

                //change the faulty lemma as well.

            }
        }




        //need to write each case separately - fixme
        if(global_conj instanceof NearImplication && faulty_lemma instanceof NearEquivalence)
        {
            System.out.println("l-inc: got a nimp and a neq");

            NearImplication global_conj_nimp = (NearImplication)global_conj;
            NearEquivalence faulty_lemma_neq = (NearEquivalence)faulty_lemma;

            //need to test it on concepts of a different arity. the
            //arguments to compose will change (maybe use the
            //getParameters method)

            String production_rule = " compose [1";

            for(int i=1;i<faulty_lemma_neq.rh_concept.arity;i++)
            {
                production_rule = production_rule + ",0";
            }
            production_rule = production_rule + "] ";

            System.out.println("production_rule is " + production_rule);
            String agenda_line1 = "id_of_dummy1 = " + faulty_lemma_neq.rh_concept.id + " " + core_concept.id + production_rule;

            //String agenda_line1 = "id_of_dummy1 = " + faulty_lemma_neq.rh_concept.id + " " + core_concept.id + " compose [1] ";
            System.out.println("agenda_line1 is " + agenda_line1);
            Step forced_step1 = guider.forceStep(agenda_line1, theory);
            TheoryConstituent tc_dummy1 = new TheoryConstituent();
            tc_dummy1 = forced_step1.result_of_step;
            System.out.println("globalAndLocal l-inc: tc_dummy1 is " + tc_dummy1.writeTheoryConstituent());

            if(tc_dummy1 instanceof Concept)
            {
                System.out.println("globalAndLocal l-inc: tc_dummy1 is a concept");
                Concept tc_dummy1_concept = (Concept)tc_dummy1;
                tc_dummy1_concept.lakatos_method = "lemma-incorporation";
                Implication imp = new Implication();
                imp.lh_concept = (Concept)tc_dummy1_concept;
                imp.rh_concept = global_conj_nimp.rh_concept;
                System.out.println("globalAndLocal l-inc: just made a new conjecture - imp=" + imp.writeConjecture());
                improved_proofscheme.conj = imp;
                System.out.println("globalAndLocal l-inc: setting the new proofscheme to have our new imp ");

                //change the faulty lemma as well.

            }
        }

        System.out.println("finishing globalAndLocalLemmaIncorporation - returning " + improved_proofscheme.conj.writeConjecture());

        return improved_proofscheme;
    }


    //eg cylinder - a global counterexample but not to any of the lemmas
    public ProofScheme hiddenLemmaIncorporation_new(Theory theory, ProofScheme proofscheme, AgentWindow window)
    {
        ProofScheme improved_proofscheme = new ProofScheme();

        System.out.println("into hiddenLemmaIncorporation");



        //stage 1 - find out if there are any hidden lemmas in the
        //proofscheme and if so, identify it and generate an explicit
        //lemma which the global counter does break. We do this by testing
        //to see whether the global counterexample is surprising with
        //respect to any lemma in the proof, where surprising is defined
        //first as surprisingness 1, and then if unsuccessful, as
        //surprisingness 2


        Entity global_counter = new Entity();
        Conjecture hidden_faulty_lemma = new Conjecture();
        Conjecture explicit_lemma = new Conjecture();
        ProofScheme intermediate_proofscheme = new ProofScheme();
        boolean got_hidden_and_explicit = false;


        if((proofscheme.conj.counterexamples).size() == 1)
            global_counter = (Entity)(proofscheme.conj.counterexamples).elementAt(0);

        System.out.println("Tuesday morning -- got global_counter = " + global_counter.name);

        //test for surprisingness 1
        for(int i=0;i<proofscheme.proof_vector.size();i++)
        {
            Conjecture current_conj = (Conjecture)proofscheme.proof_vector.elementAt(i);
            if(current_conj instanceof Implication)
            {
                System.out.println("Tues morning.............");
                Implication imp = (Implication)current_conj;
                explicit_lemma = surprisingEntity(global_counter, imp, theory, window);


                System.out.println("Given global_counterexample = " + global_counter.name
                        + ", and imp =" + imp.writeConjecture() + ":  ");
                System.out.println("surprisingness 1 test returns " + explicit_lemma.writeConjecture());
                if(explicit_lemma!=null)
                {
                    hidden_faulty_lemma = imp;
                    got_hidden_and_explicit = true;
                    //intermediate_proofscheme = createNewProofScheme(hidden_faulty_lemma, explicit_lemma,proofscheme);
                    break;
                }
            }
        }


        //test for surprisingness 2 -- only do if the test for surprisingness 1 was unsuccessful
        if(!got_hidden_and_explicit)
        {
            for(int i=0;i<proofscheme.proof_vector.size();i++)
            {
                Conjecture current_conj = (Conjecture)proofscheme.proof_vector.elementAt(i);
                boolean b = antecedentFailure(global_counter, current_conj);
                if(b)
                {
                    hidden_faulty_lemma = current_conj;
                    explicit_lemma = fixHiddenFaultyLemma(hidden_faulty_lemma, proofscheme, theory);
                    got_hidden_and_explicit = true;
                    break;
                }

            }
        }


        //stage 2 - if the search for hidden and explicit lemmas was
        //successful, then remove the hidden lemma from the proof scheme
        //and insert the explicit lemma into it. The resulting proofscheme
        //is called an intermediate proofscheme.

        if(got_hidden_and_explicit)
        {
            intermediate_proofscheme = createNewProofScheme(hidden_faulty_lemma, explicit_lemma,proofscheme);
        }


        //stage 3 - perform explicit lemma-incoroporation, ie global and
        //local lemma incorporation on the intermediate_proofscheme

        System.out.println("look - i've got to here now. i'm gonna perform globalAndLocal on " + explicit_lemma.writeConjecture());
        improved_proofscheme = globalAndLocalLemmaIncorporation(theory, intermediate_proofscheme, explicit_lemma, window);

        System.out.println("\n\nimproved_proofscheme is: " + improved_proofscheme.writeProofScheme());
        return improved_proofscheme;
    }



    //eg cylinder - a global counterexample but not to any of the lemmas
    public ProofScheme hiddenLemmaIncorporation(Theory theory, ProofScheme proofscheme, AgentWindow window)
    {
        ProofScheme improved_proofscheme = new ProofScheme();

        System.out.println("into hiddenLemmaIncorporation");



        //stage 1 - identify hidden faulty lemma
        Conjecture hidden_faulty_lemma = identifyHiddenFaultyLemma(proofscheme);
        System.out.println("The first lemma in which the problem entities occur, is: " + hidden_faulty_lemma.writeConjecture());

        System.out.println("**************************** found the hidden fucking lemma");

        //stage 2 - fix faulty lemma by adding an explicit condition which
        //the counter does break (or adding a lemma)
        Conjecture explicit_lemma = fixHiddenFaultyLemma(hidden_faulty_lemma, proofscheme, theory);
        System.out.println("explicit_lemma is " + explicit_lemma.writeConjecture());
        ProofScheme intermediate_proofscheme = createNewProofScheme(hidden_faulty_lemma,explicit_lemma,proofscheme);
        System.out.println("\n\nintermediate_proofscheme is: " + intermediate_proofscheme.writeProofScheme());

        //stage 3 - perform explicit lemma-incoroporation on it

        System.out.println("look - i've got to here now. i'm gonna perform globalAndLocal on " + explicit_lemma.writeConjecture());
        improved_proofscheme = globalAndLocalLemmaIncorporation(theory, intermediate_proofscheme, explicit_lemma, window);

        System.out.println("\n\nimproved_proofscheme is: " + improved_proofscheme.writeProofScheme());
        return improved_proofscheme;
    }

    /** Takes in a proofscheme, which contains the hidden faulty lemma,
     * and replaces the hidden faulty lemma with the explicit lemma, and
     * returns the result.
     */
    public ProofScheme createNewProofScheme(Conjecture hidden_faulty_lemma,Conjecture explicit_lemma,ProofScheme proofscheme)
    {
        //look for the hidden_faulty_lemma in the proof_vector. When you
        //find it, take it out, and replace it with the explicit_lemma
        for(int i=0;i<proofscheme.proof_vector.size();i++)
        {
            Conjecture conj = (Conjecture)proofscheme.proof_vector.elementAt(i);
            if(conj.equals(hidden_faulty_lemma))//check this works
            {
                proofscheme.proof_vector.removeElementAt(i);
                proofscheme.proof_vector.insertElementAt(explicit_lemma,i);
            }
        }
        return proofscheme;
    }

    /** This method may need work - see all the instances of Conjecture
     * at the end. Works if given an implication and returns a
     * nearImplication. Haven't tested on other cases though */
    public Conjecture fixHiddenFaultyLemma(Conjecture hidden_faulty_lemma, ProofScheme proofscheme, Theory theory)
    {
        Conjecture explicit_faulty_lemma = new Conjecture();
        Conjecture first_lemma_with_counters = getFirstConjInProofSchemeWithCounters(proofscheme);
        Vector problem_entities = first_lemma_with_counters.counterexamples;
        Concept concept_to_modify = new Concept();
        Concept concept_to_leave = new Concept();
        Vector concepts_in_lemma = hidden_faulty_lemma.conceptsInConjecture();
        boolean leave_first_concept = false;
        boolean leave_second_concept = false;
        Vector counters_to_global = proofscheme.conj.counterexamples;

        System.out.println("starting fixHiddenFaultyLemma. at the moment we have hidden_faulty_lemma is "
                + hidden_faulty_lemma.writeConjecture());
        System.out.println("is this by chance the same as the thing you just tried??? ");

        //find which concept to modify and which one to leave

        concept_to_modify = getConceptWhoseAncestorsHaveEntities(hidden_faulty_lemma,problem_entities);

        //System.out.println("concept_to_modify is " + concept_to_modify.writeDefinition());


        for(int i=0;i<concepts_in_lemma.size();i++)
        {
            Concept concept = (Concept)concepts_in_lemma.elementAt(i);
            if(concept.writeDefinition().equals(concept_to_modify.writeDefinition()))
            {
                if(i==0 && concepts_in_lemma.size()==2)
                {
                    leave_second_concept = true;
                    concept_to_leave = (Concept)concepts_in_lemma.elementAt(1);
                    break;
                }

                if(i==1)
                {
                    leave_first_concept = true;
                    concept_to_leave = (Concept)concepts_in_lemma.elementAt(0);
                    break;
                }

                //the NonExists case
                //if(i==0 && concepts_in_lemma.size()==1)
                // {

                // }
            }
        }

        //go through the concepts in the theory, and see whether any of
        //them have exactly the problem entities as negatives

        for(int i=0;i<theory.concepts.size();i++)
        {
            Concept concept = (Concept)theory.concepts.elementAt(i);
            Vector negative_entities = fromStringsToEntities(theory, concept.negatives());
            boolean concept_has_same_negatives = vectorsAreEqual(negative_entities, problem_entities);

            if(concept_has_same_negatives)
            {
                //TheoryConstituent tc = composeConceptWithAnotherInTheory(concept, theory);
                for(int j=0;j<theory.concepts.size();j++)
                {
                    Concept concept_to_compose_with = (Concept)theory.concepts.elementAt(j);
                    String agenda_line1 = "id_of_dummy1 = " + concept_to_compose_with.id + " " + concept.id + " compose [0,1] ";
                    //should use getParameters here
                    //System.out.println("agenda_line1 is " + agenda_line1);
                    Step forced_step1 = guider.forceStep(agenda_line1, theory);
                    TheoryConstituent tc_dummy1 = new TheoryConstituent();
                    tc_dummy1 = forced_step1.result_of_step;
                    if(tc_dummy1 instanceof Concept)
                    {
                        Concept concept_to_do_exists_on = (Concept)tc_dummy1;
                        concept_to_do_exists_on.lakatos_method = "lemma_incorporation";
                        String agenda_line2 = "id_of_dummy2 = " + concept_to_do_exists_on.id + " exists [1] ";
                        //should use getParameters here
                        //System.out.println("agenda_line2 is " + agenda_line2);
                        Step forced_step2 = guider.forceStep(agenda_line2, theory);
                        TheoryConstituent tc_dummy2 = new TheoryConstituent();
                        tc_dummy2 = forced_step2.result_of_step;
                        //System.out.println("tc_dummy2 is " + tc_dummy2.writeTheoryConstituent());
                        if(tc_dummy2 instanceof Concept)
                        {
                            //System.out.println("fixing 1 -- in here");
                            Concept candidate_concept = (Concept)tc_dummy2;
                            candidate_concept.lakatos_method = "lemma-incorporation";
                            //System.out.println("candidate_concept is " + candidate_concept.writeDefinition());
                            Vector negatives_to_candidate_concept = fromStringsToEntities(theory,candidate_concept.negatives());
                            boolean candidate_concept_has_same_negatives = vectorsAreEqual(negatives_to_candidate_concept, counters_to_global);
                            //System.out.println("candidate_concept_has_same_negatives is " + candidate_concept_has_same_negatives);
                            if(candidate_concept_has_same_negatives)
                            {
                                //System.out.println("fixing 2 -- in here");
                                double dummy_double = 0;//work out properly

                                //System.out.println("leave_first_concept is " +leave_first_concept);
                                //System.out.println("leave_second_concept is " +leave_second_concept);

                                if(hidden_faulty_lemma instanceof Implication)
                                {
                                    //System.out.println("fixing 3 -- in here");
                                    if(leave_first_concept)
                                    {
                                        //System.out.println("fixing 4-- in here");
                                        // explicit_faulty_lemma = new NearImplication(concept_to_leave, candidate_concept,counters_to_global,dummy_double);

                                        return (new NearImplication(concept_to_leave, candidate_concept,counters_to_global,dummy_double));
                                    }
                                    if(leave_second_concept)
                                    {
                                        //System.out.println("fixing 5 -- in here");
                                        explicit_faulty_lemma = new NearImplication(candidate_concept,concept_to_leave,counters_to_global,dummy_double);
                                        return explicit_faulty_lemma;
                                    }
                                }



                                if(hidden_faulty_lemma instanceof NearImplication)
                                {
                                    //System.out.println("fixing 6 -- in here");
                                    if(leave_first_concept)
                                    {
                                        //System.out.println("fixing 7-- in here");
                                        // explicit_faulty_lemma = new NearImplication(concept_to_leave, candidate_concept,counters_to_global,dummy_double);

                                        return (new NearImplication(concept_to_leave, candidate_concept,counters_to_global,dummy_double));
                                    }
                                    if(leave_second_concept)
                                    {
                                        //System.out.println("fixing 8 -- in here");
                                        explicit_faulty_lemma = new NearImplication(candidate_concept,concept_to_leave,counters_to_global,dummy_double);
                                        return explicit_faulty_lemma;
                                    }
                                }

                                if(hidden_faulty_lemma instanceof NearEquivalence)
                                {
                                    //System.out.println("fixing 9 -- in here");
                                    if(leave_first_concept)
                                    {
                                        explicit_faulty_lemma = new NearEquivalence(concept_to_leave, candidate_concept,counters_to_global,dummy_double);
                                        return explicit_faulty_lemma;
                                    }
                                    if(leave_second_concept)
                                    {
                                        explicit_faulty_lemma = new NearEquivalence(candidate_concept, concept_to_leave,counters_to_global,dummy_double);
                                        return explicit_faulty_lemma;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return explicit_faulty_lemma;
    }


    //can we just use this?
    public Conjecture identifyHiddenFaultyLemma(ProofScheme proofscheme)
    {
        Conjecture faulty_lemma = new Conjecture();
        //find the first conj in the proofscheme with counters

        Conjecture first_lemma_with_counters = getFirstConjInProofSchemeWithCounters(proofscheme);
        boolean there_is_lemma_with_counters = !((first_lemma_with_counters.writeConjecture()).equals(""));

        //if there is a lemma with counters, then get the counters, and
        //then (i) either find which lemma they first appear in, or (ii)
        //find lemma which links concepts whose ancestors contain the
        //counters
        if(there_is_lemma_with_counters)
        {
            Vector problem_entities = first_lemma_with_counters.counterexamples;

            //return the first lemma in which the problem entities occur
            boolean method1 = false;
            if(method1)
            {
                Conjecture poss_lemma_in_which_problem_entities_first_occur = getConjWithEntities(proofscheme.proof_vector,problem_entities);
                boolean there_is_a_lemma = !((poss_lemma_in_which_problem_entities_first_occur.writeConjecture()).equals(""));
                if(there_is_a_lemma)
                    faulty_lemma = poss_lemma_in_which_problem_entities_first_occur;
            }
            //return the first lemma which links concepts whose ancestors
            //contain the counters
            boolean method2 = true;
            if(method2)
            {
                //stem.out.println("looking into the ancestors thing");
                Conjecture poss_lemma_in_which_problem_entities_first_occur = getConjWhoseAncestorsHaveEntities(proofscheme.proof_vector,problem_entities);
                boolean there_is_a_lemma = !((poss_lemma_in_which_problem_entities_first_occur.writeConjecture()).equals(""));
                if(there_is_a_lemma)
                    faulty_lemma = poss_lemma_in_which_problem_entities_first_occur;

            }
        }

        //here -- need to identify the first lemma with a non lem concept
        if(!there_is_lemma_with_counters)
        {
            System.out.println("none of the lemmas have counterexamples");

            Vector counters_to_global = proofscheme.conj.counterexamples;

            //start test




            //look at the history of the concepts

            System.out.println("starting lem test here");

            boolean got_problem_concept = false;
            boolean got_faulty_lemma = false;

            //have a look to see whether any of the concepts in the lemmas
            //in the proof are non_lem_concepts with resepct to the global
            //counters. If so, return the relevant lemma.
            for(int i=0;i<proofscheme.proof_vector.size();i++)
            {
                Conjecture current_conj = (Conjecture)proofscheme.proof_vector.elementAt(i);

                //look at the concepts
                Vector concepts_to_check = current_conj.conceptsInConjecture();
                for(int j=0;j<concepts_to_check.size();j++)
                {
                    Concept concept = (Concept)concepts_to_check.elementAt(j);
                    System.out.println(" current_concept is " + concept.writeDefinition());
                    System.out.println(concept.datatable.toTable());

                    got_problem_concept = got_non_lem_concept(concept, counters_to_global);

                    if(got_problem_concept)
                    {
                        faulty_lemma = current_conj;
                        System.out.println("1 -- returning " + faulty_lemma.writeConjecture() + " as the faulty lemma");
                        return faulty_lemma;
                    }
                }
            }

            //have a look to see whether any of the ancestors of the
            //concepts in the lemmas in the proof are non_lem_concepts
            //with resepct to the global counters. If so, return the
            //relevant lemma.

            for(int i=0;i<proofscheme.proof_vector.size();i++)
            {
                Conjecture current_conj = (Conjecture)proofscheme.proof_vector.elementAt(i);
                Vector concepts_to_check = current_conj.conceptsInConjecture();

                for(int j=0;j<concepts_to_check.size();j++)
                {
                    Concept concept = (Concept)concepts_to_check.elementAt(j);

                    for(int k=0;k<concept.ancestors.size();k++)
                    {
                        Concept ancestor_concept = (Concept)concept.ancestors.elementAt(k);
                        got_problem_concept = got_non_lem_concept(ancestor_concept, counters_to_global);

                        if(got_problem_concept)
                        {
                            faulty_lemma = current_conj;
                            System.out.println("2 -- returning " + faulty_lemma.writeConjecture() + " as the faulty lemma");
                            System.out.println("found this one through the ancestor thing");
                            return faulty_lemma;
                        }

                    }


                }
            }

        }
        return faulty_lemma;
    }


    /** returns true if the concept is a non_lem_concept with respect
     * to all the entities in the vector counters_to_global. eg if you
     * have the counters [apple] and the concept of having 2 divisors,
     * it should get the ancestors of the concept, the concept of
     * divisors, and should notice that div(apple) = [], as opposed to
     * div(1) = [[1,1]]; div(2) = [[1,2]]; div(3) = [[1,3]]; div(4) =
     * [[1,4], [2,2]]; etc. then it should return true.  */

    public boolean got_non_lem_concept(Concept concept, Vector counters_to_global)
    {
        Vector non_lem_concept_vector = concept.nonLemConcept();
        System.out.println(" starting got_non_lem_concept");
        System.out.println("got concept: " + concept.writeDefinition());
        System.out.println("and counters_to_global: " + counters_to_global);


        System.out.println("non_lem_concept vector is " +  non_lem_concept_vector + "\n\n\n");

        if(((Vector)non_lem_concept_vector.elementAt(1)).isEmpty() &&
                !(((Vector)non_lem_concept_vector.elementAt(2)).isEmpty()))
        {
            System.out.println(" 1 got_non_lem_concept");

            for(int k=0;k<counters_to_global.size();k++)
            {
                Entity counter = (Entity)counters_to_global.elementAt(k);
                System.out.println(" 2 got_non_lem_concept");

                System.out.println("counter is " + counter.name);
                System.out.println("non_lem_concept_vector.elementAt(0) is "
                        + non_lem_concept_vector.elementAt(0));


                boolean contains_counter = ((Vector)non_lem_concept_vector.elementAt(0)).contains(counter);
                System.out.println("contains_counter is " + contains_counter);

                Vector vector_to_check = (Vector)non_lem_concept_vector.elementAt(0);

                if(!contains(vector_to_check,counter)) //this works -- need to check on all the negatives though
                {
                    System.out.println(" 3 got_non_lem_concept");
                    return false;

                }
            }
            System.out.println(" 4 we wanna be here --- returning true for concept: " + concept.writeDefinition());
            System.out.println("and counters_to_global: " + counters_to_global);
            return true;
        }
        return false;
    }

    public boolean contains(Vector vector, Entity counter)
    {
        System.out.println("testing to see whether " + vector + " contains " + counter.name);

        for(int i=0;i<vector.size();i++)
        {
            String entity = (String)vector.elementAt(i);
            //Entity entity = (Entity)vector.elementAt(i);

            boolean b = (counter.name).equals(entity);
            System.out.println("counter is " + counter.name);
            System.out.println("entity is " + entity);
            System.out.println("(counter.name).equals(entity) is " + b);

            if((counter.name).equals(entity))
            {
                System.out.println("returning true");
                return true;
            }
        }
        System.out.println("returning false");
        return false;
    }



    public Conjecture getFirstConjInProofSchemeWithCounters(ProofScheme proofscheme)
    {
        Conjecture first_lemma_with_counters = new Conjecture();
        for(int i=0;i<proofscheme.proof_vector.size();i++)
        {
            Conjecture current_conj = (Conjecture)proofscheme.proof_vector.elementAt(i);
            if (!current_conj.counterexamples.isEmpty())
                return current_conj;
        }
        return first_lemma_with_counters;
    }

    //returns the first conj in proof_vector which contains one of the
    //problem entities in its datatable (ie the datatables of the
    //concept(s) in the conjecture) - needs testing
    public Conjecture getConjWithEntities(Vector proof_vector, Vector problem_entities)
    {
        Conjecture output = new Conjecture();
        for(int i=0;i<proof_vector.size();i++)
        {
            Conjecture current_conj = (Conjecture)proof_vector.elementAt(i);
            Vector concepts_in_conjecture = current_conj.conceptsInConjecture();

            boolean current_conj_got_problem_entities = currentConjGotProblemEntities(concepts_in_conjecture,problem_entities);
            if(current_conj_got_problem_entities)
                return current_conj;
        }
        return output;
    }


    //returns the first concept in the conjecture whose ancestors
    //contain one of the problem entities in its datatable - needs
    //testing
    public Concept getConceptWhoseAncestorsHaveEntities(Conjecture conjecture,Vector problem_entities)
    {
        Concept output_concept = new Concept();
        Vector concepts_in_conjecture = conjecture.conceptsInConjecture();

        for(int i=0;i<concepts_in_conjecture.size();i++)
        {
            Concept concept = (Concept)concepts_in_conjecture.elementAt(i);

            for(int j=0;j<concept.ancestors.size();j++)
            {
                Concept ancestor_concept = (Concept)concept.ancestors.elementAt(j);

                for(int k=0;k<ancestor_concept.datatable.size();k++)
                {
                    Row row = (Row)ancestor_concept.datatable.elementAt(k);

                    //if the row contains any of the problem entities
                    //then return the conjecture which it appears in

                    for(int l=0;l<problem_entities.size();l++)
                    {
                        Entity problem_entity = (Entity)problem_entities.elementAt(l);
                        boolean entity_is_one_of_problem_entities = (row.entity.toString()).equals(problem_entity.toString());

                        if(entity_is_one_of_problem_entities)
                            return concept;
                    }
                }
            }

        }
        System.out.println();
        return output_concept;
    }



    //returns the first conj in proof_vector in which the ancestors contain which one of the
    //problem entities in its datatable - needs testing
    public Conjecture getConjWhoseAncestorsHaveEntities(Vector proof_vector, Vector problem_entities)
    {
        Conjecture output = new Conjecture();
        for(int i=0;i<proof_vector.size();i++)
        {
            Conjecture current_conj = (Conjecture)proof_vector.elementAt(i);
            Vector concepts_in_conjecture = current_conj.conceptsInConjecture();
            Vector concepts_and_ancestors_in_conjecture = new Vector();
            for(int j=0;j<concepts_in_conjecture.size();j++)
            {
                Concept concept = (Concept)concepts_in_conjecture.elementAt(j);
                for(int k=0;k<concept.ancestors.size();k++)
                {
                    Concept ancestor_concept = (Concept)concept.ancestors.elementAt(k);
                    concepts_and_ancestors_in_conjecture.addElement(ancestor_concept);
                }
            }
            boolean current_conj_got_problem_entities = currentConjGotProblemEntities(concepts_and_ancestors_in_conjecture,problem_entities);
            if(current_conj_got_problem_entities)
                return current_conj;
        }
        return output;
    }


    public boolean currentConjGotProblemEntities(Vector concepts, Vector problem_entities)
    {
        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);

            for(int j=0;j<concept.datatable.size();j++)
            {
                Row row = (Row)concept.datatable.elementAt(j);

                //if the row contains any of the problem entities
                //then return the conjecture which it appears in


                for(int k=0;k<problem_entities.size();k++)
                {
                    Entity problem_entity = (Entity)problem_entities.elementAt(k);
                    boolean entity_is_one_of_problem_entities = (row.entity.toString()).equals(problem_entity.toString());

                    if(entity_is_one_of_problem_entities)
                        return true;
                }
            }
        }
        return false;
    }


    //eg cube - not a global counter, but can be interpreted as a
    //counter to one of the lemmas
    public ProofScheme onlyLocalLemmaIncorporation(Theory theory, ProofScheme proofscheme, Conjecture faulty_lemma, AgentWindow window)
    {
        System.out.println("starting onlyLocalLemmaIncorporation");
        ProofScheme output = new ProofScheme();
        Vector problem_entities = faulty_lemma.counterexamples;
        Vector problem_entities_as_strings = fromEntitiesToStrings(problem_entities);
        System.out.println("faulty_lemma is " + faulty_lemma.writeConjecture());
        System.out.println("faulty_lemma.counterexamples are " + faulty_lemma.counterexamples);

        Vector poss_pm_conjectures = piecemealExclusion(faulty_lemma, theory, window);
        System.out.println("poss_pm_conjectures.size() is " + poss_pm_conjectures.size());

        for(int i=0;i<poss_pm_conjectures.size();i++)
        {
            TheoryConstituent tc = (TheoryConstituent)poss_pm_conjectures.elementAt(i);
            System.out.println("the " + i + " th the theory constituent is " + tc.writeTheoryConstituent());
            if(tc instanceof Conjecture)
            {
                Conjecture conj = (Conjecture)tc;
                return createNewProofScheme(faulty_lemma,conj,proofscheme);
            }
            if(tc instanceof Concept)
            {
                Concept new_concept = (Concept)tc;
                new_concept.lakatos_method = "lemma-incorporation";
                if(faulty_lemma instanceof NearImplication)
                {
                    NearImplication nimp = (NearImplication)faulty_lemma;
                    Implication new_imp = new Implication(new_concept, nimp.lh_concept, "lemma_inc_conj");
                    Vector poss_counters = new_imp.counterexamples;
                    if(poss_counters.isEmpty())
                        return createNewProofScheme(faulty_lemma,new_imp,proofscheme);
                }
                if(faulty_lemma instanceof NearEquivalence)
                {
                    NearEquivalence nequiv = (NearEquivalence)faulty_lemma;
                    Equivalence new_equiv = new Equivalence(new_concept, nequiv.lh_concept, "lemma_inc_conj");
                    Vector poss_counters = nequiv.counterexamples;
                    if(poss_counters.isEmpty())
                        return createNewProofScheme(faulty_lemma,nequiv,proofscheme);
                }

                //return createNewProofScheme(faulty_lemma,conj,proofscheme);
            }
        }
        Vector poss_sw_conjectures = strategicWithdrawal(faulty_lemma, theory, window);
        for(int i=0;i<poss_sw_conjectures.size();i++)
        {
            TheoryConstituent tc = (TheoryConstituent)poss_sw_conjectures.elementAt(i);
            if(tc instanceof Conjecture)
            {
                Conjecture conj = (Conjecture)tc;
                return createNewProofScheme(faulty_lemma,conj,proofscheme);
            }
        }

        return output;
    }

    /**

     public ProofScheme onlyLocalLemmaIncorporation1(Theory theory, ProofScheme proofscheme, Conjecture faulty_lemma, AgentWindow window)
     {
     //go through all the concepts in the theory, and look to see
     //whether you have any (i) whose negatives exactly cover the
     //counterexample, or (ii) whose positives exactly cover the
     //counterexamples. In case (i), ...


     //go through the concepts in the theory, and see whether any of
     //them have exactly the problem entities as negatives

     Concept concept_to_incorporate = getConceptWhichExactlyCoversGivenEntities(problem_entities_as_strings);


     Concept concept_to_exclude = getConceptWhichExactlyCoversGivenEntities(faulty_lemma.getPor);
     if(concept_to_exclude.writeDefinition("ascii").equals(""))
     return output;
     else
     {
     window.writeToFrontEnd("looking for a concept to cover the counters - got " + concept_to_exclude.writeDefinition("ascii"));
     String agenda_line = "id_of_dummy = " + concept_to_exclude.id + " " + concept.id + " negate [] ";
     System.out.println("agenda_line is " + agenda_line);
     agenda.addForcedStep(agenda_line, false);
     Step forcedstep = guider.forceStep(agenda_line, theory);
     output = forcedstep.result_of_step;
     return output;
     }



     for(int i=0;i<theory.concepts.size();i++)
     {
     Concept concept = (Concept)theory.concepts.elementAt(i);
     System.out.println("concept.id is : " + concept.id);
     Vector negative_entities = fromStringsToEntities(theory, concept.negatives());
     boolean concept_has_same_negatives = vectorsAreEqual(negative_entities, problem_entities);

     //case (i)
     if(concept_has_same_negatives)
     {
     //TheoryConstituent tc = composeConceptWithAnotherInTheory(concept, theory);
     for(int j=0;j<theory.concepts.size();j++)
     {
     Concept concept_to_compose_with = (Concept)theory.concepts.elementAt(j);
     String agenda_line1 = "id_of_dummy1 = " + concept_to_compose_with.id + " " + concept.id + " compose [0,1] ";
     //should use getParameters here
     //System.out.println("agenda_line1 is " + agenda_line1);
     Step forced_step1 = guider.forceStep(agenda_line1, theory);
     TheoryConstituent tc_dummy1 = new TheoryConstituent();
     tc_dummy1 = forced_step1.result_of_step;
     if(tc_dummy1 instanceof Concept)
     {
     Concept concept_to_do_exists_on = (Concept)tc_dummy1;
     String agenda_line2 = "id_of_dummy2 = " + concept_to_do_exists_on.id + " exists [1] ";
     //should use getParameters here
     //System.out.println("agenda_line2 is " + agenda_line2);
     Step forced_step2 = guider.forceStep(agenda_line2, theory);
     TheoryConstituent tc_dummy2 = new TheoryConstituent();
     tc_dummy2 = forced_step2.result_of_step;
     //System.out.println("tc_dummy2 is " + tc_dummy2.writeTheoryConstituent());
     if(tc_dummy2 instanceof Concept)
     {
     //System.out.println("fixing 1 -- in here");
     Concept candidate_concept = (Concept)tc_dummy2;
     //System.out.println("candidate_concept is " + candidate_concept.writeDefinition());
     Vector negatives_to_candidate_concept = fromStringsToEntities(theory,candidate_concept.negatives());
     boolean candidate_concept_has_same_negatives = vectorsAreEqual(negatives_to_candidate_concept, counters_to_global);
     //System.out.println("candidate_concept_has_same_negatives is " + candidate_concept_has_same_negatives);
     if(candidate_concept_has_same_negatives)
     {
     //System.out.println("fixing 2 -- in here");
     double dummy_double = 0;//work out properly

     }
     }
     }
     }
     }
     //case (ii) -
     Vector positive_strings = concept.getPositives();
     Vector positive_entities = fromStringsToEntities(theory, positive_strings);

     boolean positives_in_concept_match_problem_entities = vectorsAreEqual(positive_entities, problem_entities);
     if(positives_in_concept_match_problem_entities)
     {
     System.out.println("we were expecting this");
     Concept object_of_interest_concept = findObjectOfInterestConcept(theory.concepts, positive_strings)

     String agenda_line1 = "id_of_dummy1 = " + concept.id + " " + object_of_interest_concept.id + " negate [] ";
     System.out.println("agenda_line1 is " + agenda_line1);
     Step forced_step1 = guider.forceStep(agenda_line1, theory);
     TheoryConstituent tc_dummy1 = new TheoryConstituent();
     tc_dummy1 = forced_step1.result_of_step;

     System.out.println("tc_dummy1 is  " + tc_dummy1.writeTheoryConstituent());
     if(tc_dummy1 instanceof Concept)
     {

     }
     }


     if(faulty_lemma instanceof NearImplication)
     {
     //System.out.println("l-inc: got 2 nimps");


     NearImplication faulty_lemma_nimp = (NearImplication)faulty_lemma;

     //need to test it on concepts of a different arity. the
     //arguments to compose will change (maybe use the
     //getParameters method)

     String agenda_line1 = "id_of_dummy1 = " + faulty_lemma_nimp.rh_concept.id + " " + core_concept.id + " compose [1] ";
     System.out.println("agenda_line1 is " + agenda_line1);
     Step forced_step1 = guider.forceStep(agenda_line1, theory);
     TheoryConstituent tc_dummy1 = new TheoryConstituent();
     tc_dummy1 = forced_step1.result_of_step;

     if(tc_dummy1 instanceof Concept)
     {
     //System.out.println("globalAndLocal l-inc: tc_dummy1 is a concept");
     Concept tc_dummy1_concept = (Concept)tc_dummy1;
     Implication imp = new Implication();
     imp.lh_concept = (Concept)tc_dummy1_concept;
     imp.rh_concept = global_conj_nimp.rh_concept;
     //System.out.println("globalAndLocal l-inc: just made a new conjecture - imp=" + imp.writeConjecture());
     improved_proofscheme.conj = imp;
     //System.out.println("globalAndLocal l-inc: setting the new proofscheme to have our new imp ");

     //change the faulty lemma as well.

     }
     }





     return improved_proofscheme;
     }

     */

    /** Takes in a proofscheme and returns the first conjecture in it,
     * to which there are counters.
     */
    public Conjecture countersInProofScheme(ProofScheme proofscheme)
    {
        Conjecture conjecture = new Conjecture();
        for(int i=0; i<proofscheme.proof_vector.size();i++)
        {
            Conjecture conj = (Conjecture)proofscheme.proof_vector.elementAt(i);
            if(!conj.counterexamples.isEmpty())
                return conj;
        }

        return conjecture;
    }

    /** Takes in a proofscheme and a vector of counterexamples and
     * returns the first conjecture in it, to which the same objects are
     * counters.
     */
    public Conjecture getFaultyLemma(ProofScheme proofscheme, Vector counters, Theory theory)
    {
        System.out.println("in getFaultyLemma");
        System.out.println("The counters to the global conj are: " + counters);

        Conjecture conjecture = new Conjecture();
        for(int i=0; i<proofscheme.proof_vector.size();i++)
        {
            Conjecture conj = (Conjecture)proofscheme.proof_vector.elementAt(i);
            System.out.println("got " + conj.writeConjecture());
            System.out.println("conj.counterexamples are " + conj.counterexamples);

            if(!conj.counterexamples.isEmpty())
            {
                boolean b = vectorsAreEqual(counters, conj.counterexamples);
                if(b)
                {
                    System.out.println("getFaultyLemma .... in here");
                    return conj;
                }
            }
        }
        return conjecture;
    }



    /**************************************************************************************/



    /** Given a vector of entities, returns a concept which exactly covers
     * them - used for both strategic withdrawal (entities are positives
     * which we want to limit to) and piecemeal exclusion (entities are
     * negatives which we want to exclude). For each concept in theory, if
     * it has arity 1 and contains the positives, add to vector
     * app_con_pair */

    public Concept getConceptWhichExactlyCoversGivenEntities(Vector entities_to_cover)
    {

        Concept output = new Concept();

        Tuples app_con_pairs = new Tuples();//applicability_conjecture_pairs
        for(int i=0; i < concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if (concept.arity==1 &&
                    concept.positivesContains(entities_to_cover))
            {

                Vector app_con_pair = new Vector();
                app_con_pair.addElement(Double.toString(concept.applicability));
                app_con_pair.addElement(concept);
                app_con_pairs.addElement(app_con_pair);

            }
        }


        app_con_pairs.sort();

        if(app_con_pairs.isEmpty())
            return output;
        else
        {

            Vector app_con_pair_to_limit = (Vector)app_con_pairs.elementAt(0);//take first [should it be last?]


            double given_entities_size = (new Double(entities_to_cover.size())).doubleValue();

            boolean entities_of_certain_type_only = true;
            //(works for counter-barring) test - may not make any diff - normally set this false

            int size_of_entities = 0;

            //we work out the size of the set of entities which are of the
            //same type as those in the vector of entities_to_cover
            if(entities_of_certain_type_only)
            {
                Concept core_concept = findObjectOfInterestConcept(concepts, entities_to_cover);
                size_of_entities = core_concept.getPositives().size();
            }
            if(!entities_of_certain_type_only)
                size_of_entities = entities.size();


            //entities_size = (new Double(entities.size())).doubleValue();
            double entities_size = (new Double(size_of_entities)).doubleValue();

            //System.out.println("we'd expect this to be 1.... given_entities_size is " + given_entities_size);

            //System.out.println("entities_size is " + entities_size);
            double p_over_e = given_entities_size/entities_size;
            Concept concept = (Concept)app_con_pair_to_limit.elementAt(1);
            //System.out.println("concept.id is " + concept.id);
            //System.out.println("concept is " + concept.writeDefinition());
            String app_string = (String)app_con_pair_to_limit.elementAt(0);

            //System.out.println("p_over_e is " + p_over_e);
            //System.out.println("app_string is " + app_string);
            //System.out.println("app_string.equals(Double.toString(p_over_e)) is " + app_string.equals(Double.toString(p_over_e)));

            if (app_string.equals(Double.toString(p_over_e))) //we only want concepts which exactly cover positives
            {
                //System.out.println("in here .... Finished getConceptWhichExactlyCoversGivenEntities (got one which covers exactly))");
                //System.out.println("....concept is " + concept.writeDefinition("ascii"));
                return concept;
            }
            else
            {
                //System.out.println("Finished getConceptWhichExactlyCoversGivenEntities");
                //System.out.println("output is " + output.writeDefinition("ascii"));
                return output;
            }
        }
    }





    /** Given a vector of entities, returns a concept which exactly
     * covers them - used for both strategic withdrawal (entities are
     * positives which we want to limit to) and piecemeal exclusion
     * (entities are negatives which we want to exclude). For each concept
     * in theory, if it has arity 1 and contains the positives, add to
     * vector app_con_pair */

    public Concept getNewConceptWhichExactlyCoversGivenEntities(Vector entities_to_cover, Concept concept_to_avoid)
    {
        //System.out.println("Started getNewConceptWhichExactlyCoversGivenEntities(" + entities_to_cover + ")");
        //System.out.println(" and avoids this concept: " + concept_to_avoid.writeDefinition("ascii"));
        Concept concept_of_interest = new Concept();

        Tuples app_con_pairs = new Tuples();//applicability_conjecture_pairs
        for(int i=0; i < concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if (concept.arity==1 &&
                    concept.positivesContains(entities_to_cover)) //&& !(concept.equals(concept_to_avoid)) here
            {
                Vector app_con_pair = new Vector();
                app_con_pair.addElement(Double.toString(concept.applicability));
                app_con_pair.addElement(concept);
                app_con_pairs.addElement(app_con_pair);
            }
        }

        app_con_pairs.sort();
        for(int i=0; i<app_con_pairs.size(); i++)
        {
            Vector app_con_pair_to_limit = (Vector)app_con_pairs.elementAt(i);//take first [should it be last?]

            double given_entities_size = (new Double(entities_to_cover.size())).doubleValue();
            double entities_size = (new Double(entities.size())).doubleValue();
            double p_over_e = given_entities_size/entities_size;
            Concept concept = (Concept)app_con_pair_to_limit.elementAt(1);

            //System.out.println("concept is: " + concept.writeDefinition("ascii"));
            //System.out.println("concept_to_avoid is: " + concept_to_avoid.writeDefinition("ascii"));
            //System.out.println("concept.equals(concept_to_avoid) is: " + (concept.writeDefinition("ascii")).equals(concept_to_avoid.writeDefinition("ascii")));

            //check to see if the concept definition is the same as that to avoid
            if(!((concept.writeDefinition("ascii")).equals(concept_to_avoid.writeDefinition("ascii"))))
            {
                //System.out.println("got a different one!");
                //System.out.println("To confirm, concept_to_avoid is: " + concept_to_avoid.writeDefinition("ascii"));
                //System.out.println("The new concept is " + concept.writeDefinition("ascii"));
                //System.out.println("Positives in new concept are: " + concept.positives());

                String app_string = (String)app_con_pair_to_limit.elementAt(0);
                if (app_string.equals(Double.toString(p_over_e))) //we only want concepts which exactly cover positives
                {
                    //System.out.println("Finished getNewConceptWhichExactlyCoversGivenEntities (got one which covers exactly))");
                    //System.out.println("concept is " + concept.writeDefinition("ascii"));
                    return concept;
                }
            }

        }
        //System.out.println("Finished getNewConceptWhichExactlyCoversGivenEntities but didn't get a new one");
        //System.out.println("concept_of_interest is " + concept_of_interest.writeDefinition("ascii"));
        return concept_of_interest;

    }








    /** Given a faulty conjecture P <~> Q and two vectors of counters
     * (for each side), returns a Vector positives of all the objects
     * which are in the intersection P /\ Q
     */


    /** Given a Vector of entities to exclude, and a concept, returns
     the positive entities of the concept - i.e. all the entities
     covered by the concept except those in entities to exclude */

    /** for each concept in theory, if it has arity 1 and contains the positives, add to vector  app_con_pair
     */
    public Concept makeConceptForST(Concept gen_concept, Concept subset_concept, Vector positives)
    {
        Concept concept_to_limit = new Concept();

        Tuples app_con_pairs = new Tuples();//applicability_conjecture_pairs
        for(int i=0; i < concepts.size(); i++)
        {
            concept_to_limit = (Concept)concepts.elementAt(i);
            if (concept_to_limit.arity==1 &&
                    concept_to_limit.positivesContains(positives))
            {
                Vector app_con_pair = new Vector();
                app_con_pair.addElement(Double.toString(concept_to_limit.applicability));
                app_con_pair.addElement(concept_to_limit);
                app_con_pairs.addElement(app_con_pair);
            }
        }

        app_con_pairs.sort();
        Vector app_con_pair_to_limit = (Vector)app_con_pairs.elementAt(0);//take first [should it be last?]

        double positives_size = (new Double(positives.size())).doubleValue();
        double entities_size = (new Double(entities.size())).doubleValue();
        double p_over_e = positives_size/entities_size;
        concept_to_limit = (Concept)app_con_pair_to_limit.elementAt(1);
        String app_string = (String)app_con_pair_to_limit.elementAt(0);
        if (app_string.equals(Double.toString(p_over_e))) //we only want concepts which exactly cover positives
            return concept_to_limit;

        return concept_to_limit;
        // {
        //	Concept object_of_interest_concept = new Concept();
        //	for(int i=0; i< concept_to_limit.generalisations.size();i++)
        //	  {
        //	    object_of_interest_concept = (Concept)concept_to_limit.generalisations.elementAt(i);
        //	    if (object_of_interest_concept.is_object_of_interest_concept) break;
        //	  }
        //	String agenda_line1 = "id_of_dummy1 = " + concept_to_limit.id + " " +
        //	  object_of_interest_concept.id
        //	  + " negate [] ";
    }

    /** Returns a vector of the entities which have the same datatable
     entry in both concepts, i.e. the union of the two concepts*/

    //doesn't work
    public Vector getUnionOfConcepts(Concept concept1, Concept concept2)
    {
        //System.out.println("getting union of " + concept1.writeDefinition("ascii") + " and " + concept2.writeDefinition("ascii"));
        Vector output = new Vector();
        for (int i=0; i<concept1.datatable.size(); i++)
        {
            Row row = (Row)concept1.datatable.elementAt(i);

            if (concept2.sameValueOfEntity(row))
            {
                //System.out.println("got " + row.entity + " from concept1, and it's also a positive in concept2");
                output.addElement(row.entity);
            }
        }

        return output;

    }
    /** Given a Vector of entities to exclude, and a concept, returns
     the positive entities of the concept - i.e. all the entities
     covered by the concept except those in entities to exclude */


    public Vector getPositives(Vector entities_to_exclude, Concept concept)
    {
        //System.out.println("started getPositives");
        //System.out.println("entities_to_exclude is: " + entities_to_exclude);
        Vector positives = new Vector();

        //Concept left_concept = (Concept)faulty_conj.lh_concept;
        //Concept right_concept = (Concept)faulty_conj.rh_concept;


        //needs work - all of the entities in the left hand concept (given a concept return a vector of all the entities which satisfy it
        for (int i=0; i<concept.datatable.size(); i++)
        {
            Row row = (Row)concept.datatable.elementAt(i);
            if(!entities_to_exclude.contains(row.entity))
            {
                positives.addElement(row.entity);
            }
        }
        //get Vector of objects in P, and for each object, if it is in counters_to_lhs, do not add it to positives, otherwise add it

        //System.out.println("finished getPositives");
        return positives;
    }




    /** Given concepts P and Q (left and right respectively),
     * makes and returns the conjecture P -> Q
     */

    public Conjecture makeImpConj(Concept left_concept, Concept right_concept)
    {
        String id = "dummy_id";
        Implication imp_conj = new Implication(left_concept, right_concept, id);

        return imp_conj;
    }


    /** Given concepts P and Q (left and right respectively),
     * makes and returns the conjecture P <-> Q
     */

    public Conjecture makeEquivConj(Concept left_concept, Concept right_concept)
    {
        String id = "dummy_id";
        Equivalence equiv_conj = new Equivalence(left_concept, right_concept, id);
        return equiv_conj;
    }


    /** Takes in a Vector of entities and outputs a vector of the entity strings */

    public Vector fromEntitiesToStrings(Vector vector)
    {
        Vector entity_strings = new Vector();
        for(int i=0;i< vector.size(); i++)
        {
            Entity entity = (Entity)vector.elementAt(i);
            String entity_string = entity.toString();
            entity_strings.addElement(entity_string);
        }
        return entity_strings;
    }

    /** Takes in a Vector of string_entities and outputs a vector of the entities */

    public Vector fromStringsToEntities(Theory theory, Vector vector)
    {
        Vector entities = new Vector();
        for(int i=0;i<vector.size(); i++)
        {
            String entity_string = (String)vector.elementAt(i);
            for(int j=0;j<theory.entities.size();j++)
            {
                Entity entity = (Entity)theory.entities.elementAt(j);
                if(entity.name.equals(entity_string))
                {
                    entities.addElement(entity);
                    break;
                }
            }
        }
        return entities;
    }

    /**************     added by simon (in 19th nov version)   ***********************/


    public Vector getBreakers(Concept concept, Vector positives, Vector negatives)
    {
        Vector output = new Vector();
        Vector concept_positives = (Vector)concept.categorisation.elementAt(0);
        Vector concept_negatives = (Vector)concept.categorisation.elementAt(1);

        for (int i=0; i<positives.size(); i++)
        {
            String positive = (String)positives.elementAt(i);
            if (!concept.positivesContains(positive))
                output.addElement(positive);
        }
        for (int i=0; i<negatives.size(); i++)
        {
            String negative = (String)negatives.elementAt(i);
            if (concept.positivesContains(negative))
                output.addElement(negative);
        }
        return output;
    }


    /** Given two concepts, calculates the counters and the proportion
     of entities that have in common, and constructs a near
     equivalence conjecture.*/

    public Conjecture constructNearEquivalence(Concept lh_concept, Concept rh_concept)
    {
        Conjecture output = new Conjecture();

        Vector positives_in_lh = lh_concept.positives();
        Vector negatives_in_lh = lh_concept.negatives();

        Vector positives_in_rh = rh_concept.positives();
        Vector negatives_in_rh = rh_concept.negatives();

        Vector counterexamples = new Vector();

        double score;

        for(int i=0; i<positives_in_lh.size(); i++)
        {
            String positive = (String)positives_in_lh.elementAt(i);

            if (!positives_in_rh.contains(positive))
                counterexamples.addElement(positive);
        }

        for(int i=0; i<negatives_in_lh.size(); i++)
        {
            String negative = (String)negatives_in_lh.elementAt(i);
            if (positives_in_rh.contains(negative))
                counterexamples.addElement(negative);
        }

        int num_shared_entities = lh_concept.sharedEntities(rh_concept).size();

        score = num_shared_entities/(num_shared_entities + counterexamples.size());

        if(score==1.00)
            output = new Equivalence(lh_concept, rh_concept, "dummy_id");

        else
            output = new NearEquivalence(lh_concept, rh_concept, counterexamples, score);

        //Conjecture output = (Conjecture)output_conj;
        return output;
    }

    public SortableVector getNearConceptsForEntities(Theory theory, Vector positives,
                                                     String domain, double percent_match_min)
    {
        SortableVector output = new SortableVector();
        Concept entity_concept = theory.conceptFromName(domain);
        Vector negatives = new Vector();
        double wsize = 0;
        for (int i=0; i<entity_concept.datatable.size(); i++)
        {
            Row row = (Row)entity_concept.datatable.elementAt(i);
            if (!positives.contains(row.entity))
                negatives.addElement(row.entity);
            wsize++;
        }
        for (int i=0; i<theory.concepts.size(); i++)
        {
            Concept concept = (Concept)theory.concepts.elementAt(i);
            if (concept.domain.equals(domain))
            {
                Vector concept_positives = concept.getPositives();
                Vector counterexamples = new Vector();
                for (int j=0; j<positives.size(); j++)
                {
                    String positive = (String)positives.elementAt(j);
                    if (!concept_positives.contains(positive))
                        counterexamples.addElement(positive);
                }
                for (int j=0; j<negatives.size(); j++)
                {
                    String negative = (String)negatives.elementAt(j);
                    if (concept_positives.contains(negative))
                        counterexamples.addElement(negative);
                }
                double csize = (new Double(counterexamples.size())).doubleValue();
                double score = 1 - (csize/wsize);
                if (score >= percent_match_min/100)
                {
                    NearEquivalence nequiv = new NearEquivalence(concept, null, counterexamples, score);
                    output.addElement(nequiv, "score");
                }
            }
        }
        return output;
    }




    /** Given a vector of entities, returns a concept for which the
     entities in the vector are positives and all other entities in
     the theory are negatives. Works by taking the
     object_of_interest_concept, (and if there is more than one, by
     taking the one which includes the entities in the given Vector -
     i.e. is same type), and applying the production rule
     EntityDisjunct. Returns the concept which follows from the theory
     formation step. */

    public Concept makeConceptFromVector1(Vector entities, Theory theory)
    {
        //System.out.println("1 started makeConceptFromVector1");
        Vector entity_strings = fromEntitiesToStrings(entities);
        //Concept core_concept = new Concept();
        Concept output = new Concept();
        //System.out.println("2 started makeConceptFromVector1");


        Concept core_concept = findObjectOfInterestConcept(theory.concepts,entity_strings);

        //System.out.println("core_concept is " + core_concept.writeDefinition("ascii"));
        //System.out.println("core_concept.datatable.toTable() is " + core_concept.datatable.toTable());
        String agenda_line = "forcedstep = "+core_concept.id+" entity_disjunct "+entity_strings;
        //System.out.println("agenda_line is " + agenda_line);
        agenda.addForcedStep(agenda_line, false);
        Step forcedstep = guider.forceStep(agenda_line, theory);
        //System.out.println("forcedstep.result_of_step from entity_disjunct is " + forcedstep.result_of_step);
        if(forcedstep.result_of_step instanceof Concept)
        {
            output = (Concept)forcedstep.result_of_step;
            //System.out.println("1) it is a concept: " + output.writeDefinition("ascii"));
            //System.out.println("output.datatable.toTable() is " + output.datatable.toTable());
        }
        if(forcedstep.result_of_step instanceof Equivalence)
        {
            Equivalence equiv = (Equivalence)forcedstep.result_of_step;
            //System.out.println("it is an equiv" +  equiv.writeConjecture());
            output = ((Equivalence)forcedstep.result_of_step).rh_concept;
        }




        //sunday test
        output.arity = core_concept.arity;
        output.types = core_concept.types;
        // System.out.println("2a) it is a concept: " + output.writeDefinition("ascii"));



        for(int i=0; i<output.specifications.size(); i++)
        {
            Specification spec = (Specification)output.specifications.elementAt(i);
            //spec.writeDefinition("ascii");
            String spec_string = spec.writeSpec();
            //System.out.println("output.specifications.elementAt("+i+") is: " + spec_string);
        }

        // System.out.println("2b) it is a concept: " + output.writeDefinition("ascii"));

        //output.specifications = (Vector)(core_concept.specifications).clone(); //not sure about this...
        //output.specifications = (Vector)(core_concept.specifications.copy());

        //  for(int i=0;i<output.specifications.size();i++)
//       {
// 	output.specifications.removeElementAt(i);

//       }
//     System.out.println("3) it is a concept: " + output.writeDefinition("ascii"));
//     for(int i=0;i<core_concept.specifications.size();i++)
//       {

// 	Specification spec = (Specification)core_concept.specifications.elementAt(i);
// 	Specification new_spec = new Specification();
// 	new_spec = spec.copy();
// 	output.specifications.addElement(new_spec);
//       }


        //System.out.println("4) it is a concept: " + output.writeDefinition("ascii"));

        Datatable output_datatable = core_concept.datatable.copy();
        //System.out.println("5) it is a concept: " + output.writeDefinition("ascii"));
        for (int i = 0; i< output_datatable.size(); i++)
        {
            Row row = (Row)output_datatable.elementAt(i);
            if (!(entity_strings.contains(row.entity)))
            {

                output_datatable.removeElementAt(i);
                Tuples empty_tuples = new Tuples();
                Row row_to_add = new Row(row.entity, empty_tuples);
                output_datatable.insertElementAt(row_to_add, i);
                //tuples.isEmpty()
            }

        }
        output.datatable = output_datatable;
        //System.out.println("6) it is a concept: " + output.writeDefinition("ascii"));

        Categorisation output_catn = (Categorisation)core_concept.categorisation.clone();
        //System.out.println("7) it is a concept: " + output.writeDefinition("ascii"));

        for(int i = 0; i< output_catn.size(); i++)
        {
            Vector core_category = (Vector)output_catn.elementAt(i);

            for (int j=0; j<core_category.size(); j++)
            {
                if(entity_strings.contains((String)core_category.elementAt(j)))
                {

                    core_category.removeElementAt(j);
                }
            }
        }
        //System.out.println("8) it is a concept: " + output.writeDefinition("ascii"));

        output_catn.addElement(entity_strings);
        //System.out.println("9) it is a concept: " + output.writeDefinition("ascii"));
        output.categorisation = output_catn;
        //  System.out.println("10) last one okay --- it is a concept: " + output.writeDefinition("ascii"));

        output.concept_to_cover_entities = false;//change to true
        output.entity_strings = entity_strings;
        // System.out.println("11) it is a concept: " + output.writeDefinition("ascii"));
        //Get definition for new concept
        //output.definition_writer = (DefinitionWriter)(core_concept.definition_writer).clone();

        //System.out.println("12) it is a concept: " + output.writeDefinition("ascii"));

        //System.out.println("1) core_concept.specifications is " + core_concept.specifications);
        for(int i=0; i<core_concept.specifications.size(); i++)
        {
            Specification spec = (Specification)core_concept.specifications.elementAt(i);
            //spec.writeDefinition("ascii");
            String spec_string = spec.writeSpec();
            //System.out.println("core_concept.specifications.elementAt("+i+") is: " + spec_string);
        }


        //end sunday test





        //add the output concept into the list of concepts in the theory.
        theory.concepts.addElement(output);

        return output;
    }

    /**finds object_of_interest_concept with the same type as those entities in the Vector **/
    public Concept findObjectOfInterestConcept(Vector concepts, Vector entity_strings)
    {
        Concept core_concept = new Concept();
        for (int i = 0; i<concepts.size(); i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            if(concept.is_object_of_interest_concept)
            {

                boolean concept_ok_so_far = true;

                for (int j = 0; j< entity_strings.size(); j++)
                {

                    String entity_string = (String)entity_strings.elementAt(j);

                    if(!concept.datatable.hasEntity(entity_string))
                    {
                        concept_ok_so_far = false;
                        break;
                    }
                }
                if(concept_ok_so_far)
                {
                    core_concept = concept;
                    break;
                }
            }
        }
        return core_concept;
    }


    public void test_equals(Theory theory, AgentWindow window)
    {
        Tuples poly_row_tuples = new Tuples();
        Tuples graph_row_tuples = new Tuples();

        for (int i = 0; i<theory.concepts.size(); i++)
        {
            Concept poly_concept = (Concept)theory.concepts.elementAt(i);
            if(poly_concept.is_user_given && poly_concept.id.equals("poly006"))
            {
                for (int k=0;k<poly_concept.datatable.size();k++)
                {
                    Row row = (Row)poly_concept.datatable.elementAt(k);
                    if(row.entity.equals("tetrahedron"))
                    {
                        poly_row_tuples = row.tuples;
                        break;

                    }
                }

                for (int j = 0; j<theory.concepts.size(); j++)
                {
                    Concept graph_concept = (Concept)theory.concepts.elementAt(j);
                    if(graph_concept.is_user_given && graph_concept.id.equals("graph004"))
                        graph_row_tuples = (Tuples)((Row)graph_concept.datatable.elementAt(0)).tuples;

                    for(int l=0;l<poly_row_tuples.size();l++)
                    {
                        //if(((Vector)elementAt(l)).toString().equals(((Vector)elementAt(i+1)).toString()))
                        // {
                        boolean b = poly_row_tuples.toString().equals(graph_row_tuples.toString());
                        window.writeToFrontEnd("poly_row_tuples.toString() is " + poly_row_tuples.toString());
                        window.writeToFrontEnd("graph_row_tuples.toString() is " + graph_row_tuples.toString());
                        window.writeToFrontEnd("poly_row_tuples.equals(graph_row_tuples) is " + b);
                        // }
                    }
                }

            }

        }
    }


    //here
    public Concept test(Vector entities, Theory theory)
    {
        System.out.println("1 started test");
        /**
         Vector entity_strings = fromEntitiesToStrings(entities);
         Concept core_concept = new Concept();
         Concept output = new Concept();
         System.out.println("2 started makeConceptFromVector1");
         System.out.println("theory.concepts.size() is " + theory.concepts.size());
         //finds object_of_interest_concept with the same type as those entities in the Vector
         for (int i = 0; i<theory.concepts.size(); i++)
         {
         Concept concept = (Concept)theory.concepts.elementAt(i);
         System.out.println("current concept is " + concept.writeDefinition("ascii"));
         System.out.println("concept.is_object_of_interest_concept is " + concept.is_object_of_interest_concept);
         if(concept.is_object_of_interest_concept)
         {
         boolean concept_ok_so_far = true;
         for (int j = 0; j< entity_strings.size(); j++)
         {
         String entity_string = (String)entity_strings.elementAt(j);
         if(!concept.datatable.hasEntity(entity_string))
         {
         concept_ok_so_far = false;
         break;
         }
         }
         if(concept_ok_so_far)
         {
         core_concept = concept;
         break;
         }
         }
         }
         */
        Concept output = new Concept();
        for (int i = 0; i<theory.concepts.size(); i++)
        {
            Concept concept = (Concept)theory.concepts.elementAt(i);
            if(concept.is_user_given && concept.id.equals("poly004"))
            {
                //System.out.println("2 - here");
                //System.out.println("concept is " + concept.writeDefinition("ascii"));
                //Datatable faces_datatable = concept.datatable.copy();

                //System.out.println("concept.datatable is " + (concept.datatable).toTable());

                for (int j=0;j<concept.datatable.size();j++)
                {
                    Row entity_row = (Row)concept.datatable.elementAt(j);
                    //System.out.println("entity_row is " + entity_row.toString());
                    //find face_to_make_concept
                    if(!(entity_row.tuples.isEmpty()))
                    {
                        Vector faces = (Vector)entity_row.tuples.elementAt(0);
                        String face_to_make_concept = (String)faces.elementAt(0);
                        Vector face_string_vector = new Vector();
                        face_string_vector.addElement(face_to_make_concept);
                        String agenda_line = "forcedstep = poly009 entity_disjunct "+face_string_vector;
                        //System.out.println("agenda_line is " + agenda_line);
                        agenda.addForcedStep(agenda_line, false);
                        Step forcedstep = guider.forceStep(agenda_line, theory);
                        //System.out.println("forcedstep.result_of_step from entity_disjunct is " + forcedstep.result_of_step);
                        if(forcedstep.result_of_step instanceof Concept)
                        {
                            output = (Concept)forcedstep.result_of_step;
                            //System.out.println("it is a concept: " + output.writeDefinition("ascii"));
                        }
                        if(forcedstep.result_of_step instanceof Equivalence)
                        {
                            output = ((Equivalence)forcedstep.result_of_step).rh_concept;
                        }
                        if(forcedstep.result_of_step instanceof NonExists)
                        {
                            output = ((NonExists)forcedstep.result_of_step).concept;

                        }
                        //System.out.println("1: output is: " + output.writeDefinition("ascii"));


                        return output;
                    }
                }

            }
        }
        //System.out.println("2: returning output = " + output.writeDefinition("ascii"));
        return output;


    }


    public void test1(Vector entities, Theory theory)
    {
        //System.out.println("1 started test1");
        Vector entity_strings = new Vector();
        Concept output  = new Concept();

        for (int i = 0; i<theory.concepts.size(); i++)
        {
            Concept concept = (Concept)theory.concepts.elementAt(i);
            if(concept.is_user_given && concept.id.equals("poly009"))
            {
                //System.out.println("2 - here");
                //System.out.println("concept is " + concept.writeDefinition("ascii"));
                //Datatable faces_datatable = concept.datatable.copy();

                //System.out.println("concept.datatable is " + (concept.datatable).toTable());

                if(!(concept.datatable.isEmpty()))
                {
                    Row entity_row = (Row)concept.datatable.elementAt(0);
                    //System.out.println("entity_row is " + entity_row.toString());
                    //System.out.println("entity_row.entity is " + entity_row.entity);
                    entity_strings.addElement((entity_row.entity).toString());
                }
            }
        }


        String agenda_line = "forcedstep = poly009 entity_disjunct "+entity_strings;
        //System.out.println("agenda_line is " + agenda_line);
        agenda.addForcedStep(agenda_line, false);
        Step forcedstep = guider.forceStep(agenda_line, theory);
        //System.out.println("forcedstep.result_of_step from entity_disjunct is " + forcedstep.result_of_step);



        if(forcedstep.result_of_step instanceof Concept)
        {
            output = (Concept)forcedstep.result_of_step;
            //System.out.println("it is a concept: " + output.writeDefinition("ascii"));
        }
        if(forcedstep.result_of_step instanceof Equivalence)
        {
            output = ((Equivalence)forcedstep.result_of_step).rh_concept;
        }
        if(forcedstep.result_of_step instanceof NonExists)
        {
            output = ((NonExists)forcedstep.result_of_step).concept;

        }

    }












    /**
     //Entity face_entity = ((Row)faces_datatable.elementAt(j)).entity;
     String face_string = ((Row)concept.datatable.elementAt(j)).entity;//=cube
     Tuples face_tuples = ((Row)concept.datatable.elementAt(j)).tuples;
     Vector face_string_vector = new Vector();
     face_string_vector.addElement(face_tuples.elementAt(0));
     //face_string_vector.addElement(face_string);
     //String agenda_line = "forcedstep = poly004 entity_disjunct "+face_string_vector;
     String agenda_line = "forcedstep = poly004 entity_disjunct "+face_string_vector;
     System.out.println("agenda_line is " + agenda_line);
     agenda.addForcedStep(agenda_line, false);
     Step forcedstep = guider.forceStep(agenda_line, theory);
     System.out.println("forcedstep.result_of_step from entity_disjunct is " + forcedstep.result_of_step);
     if(forcedstep.result_of_step instanceof Concept)
     {
     output = (Concept)forcedstep.result_of_step;
     System.out.println("it is a concept: " + output.writeDefinition("ascii"));
     }
     if(forcedstep.result_of_step instanceof Equivalence)
     {
     output = ((Equivalence)forcedstep.result_of_step).rh_concept;

     }
     System.out.println("1: returning output = " + output.writeDefinition("ascii"));
     return output;
     }
     }

     }
     System.out.println("2: returning output = " + output.writeDefinition("ascii"));
     return output;
     }
     */

    /**

     System.out.println("core_concept is " + core_concept.writeDefinition("ascii"));
     String agenda_line = "forcedstep = "+core_concept.id+" entity_disjunct "+entity_strings;
     System.out.println("agenda_line is " + agenda_line);
     agenda.addForcedStep(agenda_line, false);
     Step forcedstep = guider.forceStep(agenda_line, theory);
     System.out.println("forcedstep.result_of_step from entity_disjunct is " + forcedstep.result_of_step);
     if(forcedstep.result_of_step instanceof Concept)
     {
     output = (Concept)forcedstep.result_of_step;
     System.out.println("it is a concept: " + output.writeDefinition("ascii"));
     }
     if(forcedstep.result_of_step instanceof Equivalence)
     {
     output = ((Equivalence)forcedstep.result_of_step).rh_concept;

     }

     return output;
     }

     */


    //test to see whether two vectors of Entities are equal - tested and working
    public boolean vectorsAreEqual(Vector vector1, Vector vector2)
    {


        for(int i=0;i<vector1.size();i++)
        {
            Entity entity1 = (Entity)vector1.elementAt(i);
            boolean so_far = false;
            for(int j=0;j<vector2.size();j++)
            {
                Entity entity2 = (Entity)vector2.elementAt(j);
                if(entity1.equals(entity2))
                {
                    so_far = true;
                    break;
                }
            }
            if(!so_far)
                return false;
            //if(!vector2.contains(entity))
            //return false;
        }

        for(int i=0;i<vector2.size();i++)
        {
            Entity entity1 = (Entity)vector2.elementAt(i);
            boolean so_far = false;

            for(int j=0;j<vector1.size();j++)
            {
                Entity entity2 = (Entity)vector1.elementAt(j);
                if(entity1.equals(entity2))
                {
                    so_far = true;
                    break;
                }
            }
            if(!so_far)
                return false;

            //if(!vector1.contains(entity))
            // return false;
        }

        return true;
    }

    /** Given a vector of entities, returns a concept for which the
     entities in the vector are positives and all other entities in
     the theory are negatives. Works by copying the datatable and
     categorisation for the core concept (the first concept in the
     theory) and just changing the entities which are in the given
     vector. Then WriteDefinitition is changed to [] All other fields
     contain the default values for concept. */
    public Concept makeConceptFromVector(Vector entity_strings, Theory theory)
    {
        //System.out.println("starting makeConceptFromVector()");
        Concept core_concept = new Concept();
        Concept output = new Concept();
        output.id = "dummy_id";

        //is_object_of_interest_concept
        for (int i = 0; i< theory.concepts.size(); i++)
        {
            Concept concept = (Concept)theory.concepts.elementAt(i);
            if(concept.position_in_theory==0)
            {
                System.out.println("got first concept");
                core_concept = concept;
                break;
            }
        }

        //System.out.println("heyhey: 1) output.arity is " + output.arity);
        //Get arity for new concept
        output.arity = core_concept.arity;
        //Get types for new concept
        output.types = core_concept.types;

        output.specifications = (Vector)(core_concept.specifications).clone(); //not sure about this...

        //Get datatable for new concept - go through each row in
        //core_concept datatable. If the entity is not in entity_strings
        //then change that row to empty
        //System.out.println("heyhey: 1) output.arity is " + output.arity);

        Datatable output_datatable = core_concept.datatable.copy();

        //System.out.println(" core_concept.datatable " + core_concept.prettyPrintExamples(core_concept.datatable));
        //System.out.println(" output.datatable " + output.prettyPrintExamples(output_datatable));




        for (int i = 0; i< output_datatable.size(); i++)
        {
            Row row = (Row)output_datatable.elementAt(i);
            if (!(entity_strings.contains(row.entity)))
            {

                output_datatable.removeElementAt(i);
                Tuples empty_tuples = new Tuples();
                Row row_to_add = new Row(row.entity, empty_tuples);
                output_datatable.insertElementAt(row_to_add, i);
                //tuples.isEmpty()
            }

        }


        output.datatable = output_datatable;

        //Get catagorisation for new concept
        Categorisation output_catn = (Categorisation)core_concept.categorisation.clone();


        for(int i = 0; i< output_catn.size(); i++)
        {
            Vector core_category = (Vector)output_catn.elementAt(i);

            for (int j=0; j<core_category.size(); j++)
            {
                if(entity_strings.contains((String)core_category.elementAt(j)))
                {

                    core_category.removeElementAt(j);
                }
            }
        }

        output_catn.addElement(entity_strings);

        output.categorisation = output_catn;

        output.concept_to_cover_entities = true;
        output.entity_strings = entity_strings;
        //Get definition for new concept
        output.definition_writer = (DefinitionWriter)(core_concept.definition_writer).clone();
        //System.out.println("core_concept.writeDefinition(\"ascii\") is " + core_concept.writeDefinition("ascii"));
        //System.out.println("!core_concept.concept_to_cover_entities (should be true) is: " + !core_concept.concept_to_cover_entities);
        //System.out.println("!output.concept_to_cover_entities (should be false) is: " + !output.concept_to_cover_entities);


        //System.out.println("output.writeDefinition(\"ascii\") is " + output.writeDefinition("ascii"));

        // String output_def = (String)(output.definition_writer).writeDefinitionForGivenEntities(output,"ascii",entity_strings);
        //System.out.println("output_def is: " + output_def);
        // System.out.println("output.writeDefinition(\"ascii\") is: " + output.writeDefinition("ascii"));


        System.out.println("core_concept.specifications is " + core_concept.specifications);
        for(int i=0; i<core_concept.specifications.size(); i++)
        {
            Specification spec = (Specification)core_concept.specifications.elementAt(i);
            //spec.writeDefinition("ascii");
            String spec_string = spec.writeSpec();
            System.out.println("core_concept.specifications.elementAt("+i+") is: " + spec_string);
        }

        //System.out.println("output.specifications is " + output.specifications);

        //System.out.println("A: core_concept.datatable " + core_concept.prettyPrintExamplesHTML(core_concept.datatable));
        //System.out.println("A: output.datatable " + output.prettyPrintExamplesHTML(output.datatable));
        //System.out.println("A: core_concept.datatable " + core_concept.prettyPrintExamples(core_concept.datatable));
        //System.out.println("A: output.datatable " + output.prettyPrintExamples(output.datatable));
        //System.out.println("entity_strings are " + entity_strings);
        // System.out.println("B - here -" + (core_concept.definition_writer).writeDefinition(core_concept, "ascii", entity_strings));
        //System.out.println("C - here -" + (core_concept.definition_writer).writeDefinition(core_concept, "ascii"));
        //System.out.println("D - here - got" + (output.definition_writer).writeDefinition(output, "ascii"));//, entity_strings));



        //System.out.println("finished makeConceptFromVector()");
        theory.concepts.addElement(output);

        //here
        // if (!step.concept_arising_name.equals(""))
        //	      concept_forced_names.put(step.concept_arising_name, concept_produced);
        return output;
    }

    // public void lemmaIncorporation(Conjecture conj_to_modify, Theory theory, AgentWindow window)
    public void lemmaIncorporation_old(Theory theory, AgentWindow window)
    {
        window.writeToFrontEnd("starting lemmaIncorporation");
        //get the data tables for vertex, edge and face, etc and clone them
        Datatable integers_datatable = new Datatable();
        Datatable vertices_datatable = new Datatable();
        Datatable edges_datatable = new Datatable();
        Datatable faces_datatable = new Datatable();
        Datatable vertices_on_edges_datatable = new Datatable();
        Datatable faces_on_edges_datatable = new Datatable();

        Datatable polys_datatable = new Datatable();


        for(int i=0;i<theory.concepts.size();i++)
        {
            Concept concept = (Concept)theory.concepts.elementAt(i);

            if(concept.is_user_given && concept.id.equals("int001"))
                integers_datatable = concept.datatable;


            if(concept.is_user_given && concept.id.equals("poly002"))
            {
                Datatable cloned_datatable = concept.datatable.copy();
                vertices_datatable = cloned_datatable;
            }

            if(concept.is_user_given && concept.id.equals("poly003"))
            {
                Datatable cloned_datatable = concept.datatable.copy();
                edges_datatable = cloned_datatable;
            }

            if(concept.is_user_given && concept.id.equals("poly004"))
            {
                Datatable cloned_datatable = concept.datatable.copy();
                faces_datatable = cloned_datatable;
            }

            if(concept.is_user_given && concept.id.equals("poly005"))
            {
                Datatable cloned_datatable = concept.datatable.copy();
                vertices_on_edges_datatable = cloned_datatable;
            }

            if(concept.is_user_given && concept.id.equals("poly006"))
            {
                Datatable cloned_datatable = concept.datatable.copy();
                faces_on_edges_datatable = cloned_datatable;
            }
            //here - thurs test
            if(concept.is_user_given && concept.id.equals("poly001"))
            {
                Datatable cloned_datatable = concept.datatable.copy();
                polys_datatable = cloned_datatable;
            }


        }
        window.writeToFrontEnd("polys_datatable is " + polys_datatable.toTable());
        window.writeToFrontEnd("faces_datatable is " + faces_datatable.toTable());
        window.writeToFrontEnd("integers_datatable is " + integers_datatable.toTable());


        //EulerProofScheme euler_proof_scheme = new EulerProofScheme(integers_datatable, vertices_datatable, edges_datatable,faces_datatable, vertices_on_edges_datatable, faces_on_edges_datatable);
        //Exception e = euler_proof_scheme.performProof(window);

        window.writeToFrontEnd("finishing lemmaIncorporation");
        // if e is null then no lemma-incorp to perform.
        // otherwise need to determine the type of error
    }



    //returns the first conj in proof_vector which contains one of the
    //problem entities in its datatable (ie the datatables of the
    //concept(s) in the conjecture) - needs testing
    public Conjecture getConjWithEntitiesBackUp(Vector proof_vector, Vector problem_entities)
    {
        Conjecture output = new Conjecture();
        for(int i=0;i<proof_vector.size();i++)
        {
            Conjecture current_conj = (Conjecture)proof_vector.elementAt(i);
            Vector concepts_in_conjecture = current_conj.conceptsInConjecture();

            for(int j=0;j<concepts_in_conjecture.size();j++)
            {
                Concept concept = (Concept)concepts_in_conjecture.elementAt(j);

                for(int k=0;k<concept.datatable.size();k++)
                {
                    Row row = (Row)concept.datatable.elementAt(k);

                    //if the row contains any of the problem entities
                    //then return the conjecture which it appears in

                    boolean so_far = false;
                    for(int l=0;l<problem_entities.size();l++)
                    {
                        Entity problem_entity = (Entity)problem_entities.elementAt(l);
                        boolean entity_is_one_of_problem_entities = (row.entity.toString()).equals(problem_entity.toString());

                        if(entity_is_one_of_problem_entities)
                        {
                            so_far = true;
                            break;
                        }
                    }
                    if(so_far)
                        return current_conj;
                }
            }
        }
        return output;
    }


    public void writeProofSchemeWithDatatables(ProofScheme proofscheme)
    {
        System.out.println("proofscheme.proof_vector, complete with datatables,  is:");
        for(int i=0;i<proofscheme.proof_vector.size();i++)
        {
            Conjecture current_conj = (Conjecture)proofscheme.proof_vector.elementAt(i);
            System.out.println("\n\n\n\n("+i+") " +current_conj.writeConjecture());

            Concept lhconcept = new Concept();
            Concept rhconcept = new Concept();

            if(current_conj instanceof Implication)
            {
                Implication conj = (Implication)current_conj;
                lhconcept = conj.lh_concept;
                rhconcept = conj.rh_concept;
            }
            if(current_conj instanceof NearImplication)
            {
                NearImplication conj = (NearImplication)current_conj;
                lhconcept = conj.lh_concept;
                rhconcept = conj.rh_concept;
            }
            if(current_conj instanceof Equivalence)
            {
                Equivalence conj = (Equivalence)current_conj;
                lhconcept = conj.lh_concept;
                rhconcept = conj.rh_concept;
            }

            if(current_conj instanceof NearEquivalence)
            {
                NearEquivalence conj = (NearEquivalence)current_conj;
                lhconcept = conj.lh_concept;
                rhconcept = conj.rh_concept;
            }

            System.out.println("lhconcept is " + lhconcept.writeDefinition());
            System.out.println("Its datatable is: ");
            System.out.println(lhconcept.datatable.toTable());

            System.out.println("rhconcept is " + rhconcept.writeDefinition());
            System.out.println("Its datatable is: ");
            System.out.println(rhconcept.datatable.toTable());


        }

        System.out.println("   ***************         ");

    }


    /** This takes in a proofscheme and global counterexample and tests
     * to see whether any of the lemmas in the proofscheme have
     * counterexamples which can be traced back up to the global
     * counterexample. If so, it returns the problem conjecture in which
     * they occur.
     */
    public Conjecture testForRelatedCounter(ProofScheme proofscheme, Vector global_counters)
    {
        //System.out.println("Starting testForRelatedCounter");
        //System.out.println("got global_counters " + global_counters);

        //assumes that there's only one counter:
        for(int i=0;i<global_counters.size();i++)
        {
            Entity entity = (Entity)global_counters.elementAt(i);
            String global_counter = entity.name;


            //find the first conj in the proofscheme with counters

            Conjecture first_lemma_with_counters = getFirstConjInProofSchemeWithCounters(proofscheme);
            System.out.println("first_lemma_with_counters is " + first_lemma_with_counters.writeConjecture());

            boolean there_is_lemma_with_counters = !((first_lemma_with_counters.writeConjecture()).equals(""));

            //if there is a lemma with counters, then get the counters, and
            //then (i) either find which lemma they first appear in, or (ii)
            //find lemma which links concepts whose ancestors contain the
            //counters
            if(there_is_lemma_with_counters)
            {
                Vector local_counters = first_lemma_with_counters.counterexamples;
                for(int j=0;j<concepts.size();j++)
                {
                    Concept concept = (Concept)concepts.elementAt(j);
                    // if(concept.datatable.hasEntity(global_counter))
                    //System.out.println("got concept " + concept.writeDefinition());

                    Row row = concept.datatable.rowWithEntity(global_counter);
                    //System.out.println("row.entity is " + row.entity);
                    //System.out.println("row.tuples.toString() is " + row.tuples.toString());
                    if(!(row==null)) //check
                    {
                        //System.out.println("Tues -- 1");
                        Vector v = new Vector();
                        v.addElement(local_counters);

                        //System.out.println("we're testing to see whether row.tuples.toString() " + row.tuples.toString());
                        //System.out.println("contains  " + v);


                        //boolean b = row.tuples.contains(v);//here
                        //System.out.println("the answer is " + b);
                        for(int h=0;h<local_counters.size();h++)
                        {
                            Entity first_local_counter = (Entity)local_counters.elementAt(h);
                            for(int n=0;n<row.tuples.size();n++)
                            {
                                Vector tuple = (Vector)row.tuples.elementAt(n);
                                for(int m=0;m<tuple.size();m++)
                                {
                                    //this is a vector here
                                    String current_entity = (String)tuple.elementAt(m);
                                    boolean b = (current_entity).equals(first_local_counter.name);
                                    System.out.println("the answer is " + b);
                                    if(b)
                                    {
                                        //System.out.println("Tues -- 2");
                                        Conjecture conj = firstConjectureInvolvingConcept(proofscheme, concept);
                                        return conj;
                                        //Conjecture conj = getConjWhoseAncestorsHaveEntities(proofscheme.proof_vector,local_counters);


                                        //return first_lemma_with_counters;
                                        //System.out.println("we know that current_entity, ie " + current_entity
                                        //		       + " is equal to first_local_counter, ie " +  first_local_counter);
                                    }
                                }

                            }
                        }



                    }
                }
            }
        }
        return new Conjecture();
    }



    /** Returns the first conjecture which either involves the given
     * concept itself or whose ancestors involve the given concept. This
     * assumes that the vector ancestors does *not* contain the concept
     * itself (check this)
     */
    public Conjecture firstConjectureInvolvingConcept(ProofScheme proofscheme, Concept concept)
    {
        //testing to see if any of the lemmas contain the given
        //concept. If so, return the first lemma.
        for(int i=0;i<proofscheme.proof_vector.size();i++)
        {
            Conjecture current_conj = (Conjecture)proofscheme.proof_vector.elementAt(i);
            Vector concepts_in_conjecture = current_conj.conceptsInConjecture();

            for(int j=0;j<concepts_in_conjecture.size();j++)
            {
                Concept current_concept = (Concept)concepts_in_conjecture.elementAt(j);
                if((current_concept.writeDefinition()).equals(concept.writeDefinition()))
                    return current_conj;
            }
        }

        //testing to see if any of the lemmas contain a concept whose
        //ancestors include the given concept. If so, return the first
        //lemma.
        for(int i=0;i<proofscheme.proof_vector.size();i++)
        {
            Conjecture current_conj = (Conjecture)proofscheme.proof_vector.elementAt(i);
            Vector concepts_in_conjecture = current_conj.conceptsInConjecture();

            for(int j=0;j<concepts_in_conjecture.size();j++)
            {
                Concept current_concept = (Concept)concepts_in_conjecture.elementAt(j);
                for(int k=0;k<current_concept.ancestors.size();k++)
                {
                    Concept ancestor_concept = (Concept)current_concept.ancestors.elementAt(k);
                    if((ancestor_concept.writeDefinition()).equals(concept.writeDefinition()))
                        return current_conj;
                }
            }
        }
        return new Conjecture();
    }


    //when calling this method, loop through the conjectures on the
    //proof scheme.

    /** An entity x is considered surprising with respect to a conjecture
     P -> Q if there exists another conjecture C` such that x is the
     only counterexample to C` and C` has the form P -> Q /\ R, for some
     concept R. (In the thesis it is referred to as surprising type 1.)
     If this is the case, then this method returns C`; otherwise it
     returns a null conjecture.  Note - that this method currently only
     works on implication conjectures. needs testing
     */

    public Conjecture surprisingEntity(Entity entity, Implication imp, Theory theory, AgentWindow window)
    {
        //perform for each concept in the theory. Exit when we find
        //success.
        System.out.println("testing surprisingEntity -- 1");
        System.out.println("we wanna know whether the " + entity.name + " is surprising wrt the conjecture "
                + imp.writeConjecture());

        Conjecture reconstructed_conj = new Conjecture();
        Vector ancestors = imp.rh_concept.ancestors;
        ancestors.addElement(imp.rh_concept);
        int n = ancestors.size()-1;



        System.out.println("imp.rh_concept.arity is " + imp.rh_concept.arity);


        for(;n>=0;n--)
        {

            Concept imp_concept = (Concept)ancestors.elementAt(n);
            System.out.println("imp_concept is " + imp_concept.id + ": " + imp_concept.writeDefinition());
            for(int i=0;i<concepts.size();i++)
            {
                //check we got the right arity. If so, make the concept Q(x) and R(x)
                Concept new_concept = (Concept)concepts.elementAt(i);
                System.out.println("new_concept.arity is " + new_concept.arity);






                //	if(new_concept.arity == imp_concept.arity)
                //{

                String production_rule = " compose [0" ;

                for(int j=1;j<imp_concept.arity;j++)
                {
                    production_rule = production_rule + ",1";
                }
                production_rule = production_rule + "] ";

                String agenda_line = "id_of_dummy1 = " + imp_concept.id + " " + new_concept.id + production_rule;
                System.out.println("agenda_line is " + agenda_line);
                Step forced_step1 = guider.forceStep(agenda_line, theory);
                TheoryConstituent tc_dummy = new TheoryConstituent();
                tc_dummy = forced_step1.result_of_step;
                //System.out.println("tc_dummy is " + tc_dummy.writeTheoryConstituent());
                if(tc_dummy instanceof Concept)
                {

                    Concept composed_concept = (Concept)tc_dummy;
                    System.out.println("composed_concept is " + composed_concept.writeDefinition());
                    Implication new_imp = new Implication();


                    if(composed_concept.arity == imp.lh_concept.arity)
                    {
                        Conjecture conj =  makeImpConj(imp.lh_concept, (Concept)tc_dummy);
                        new_imp = (Implication)conj;
                        reconstructed_conj = new_imp.reconstructConjecture(theory, window);
                    }

                    else
                    {
                        int m = composed_concept.arity - imp.lh_concept.arity;
                        for(;m>0;m--)
                        {
                            String agenda_line1 = "id_of_dummy1 = " + composed_concept.id + " exists [1] ";
                            System.out.println("agenda_line1 is " + agenda_line1);
                            Step forced_step = guider.forceStep(agenda_line1, theory);
                            TheoryConstituent tc_dummy1 = new TheoryConstituent();
                            tc_dummy1 = forced_step.result_of_step;
                            System.out.println("tc_dummy1 is " + tc_dummy1);

                            if(tc_dummy1 instanceof Concept)
                            {
                                System.out.println("~~~~~~~~~~~~~~~~~~~~ in here ~~~~~~~~~~~~~~~~~~~~~");
                                if(((Concept)tc_dummy1).arity == imp.lh_concept.arity)
                                {
                                    System.out.println("~~~~~~~ got what we wanted!");
                                    System.out.println("ie, " + ((Concept)tc_dummy1).writeDefinition());

                                    Conjecture conj =  makeImpConj(imp.lh_concept, (Concept)tc_dummy1);
                                    new_imp = (Implication)conj;
                                    reconstructed_conj = new_imp.reconstructConjecture_old(theory, window);//sat - fuck
                                    System.out.println("just made reconstructed_conj = " + reconstructed_conj.writeConjecture());
                                    System.out.println("reconstructed_conj.counterexamples.size() is " + reconstructed_conj.counterexamples.size());
                                    System.out.println("reconstructed_conj.counterexamples are " + reconstructed_conj.counterexamples);

                                    boolean b = entity.entitiesContains(reconstructed_conj.counterexamples);
                                    System.out.println("entity.entitiesContains(new_imp.counterexamples) is " + b);


                                }
                            }
                            else
                                break;
                        }
                    }


                    //test to see whether the entity is the only counterexample
                    if(reconstructed_conj.counterexamples.size()==1 && entity.entitiesContains(reconstructed_conj.counterexamples))
                    //if(new_imp.counterexamples.size()==1 && entity.entitiesContains(new_imp.counterexamples))
                    {
                        System.out.println("~~~~~~~~~~~~~~~~~~~~ now we really got what we wanted...  ~~~~~~~~~~~~~~~~~~~~~");
                        System.out.println("done surprisingEntity on entity = " + entity.name + ", and imp = " + imp.writeConjecture());
                        System.out.println("new_concept is " + new_concept.writeDefinition()
                                + ", and reconstructed_conj is " + reconstructed_conj.writeConjecture());

                        return reconstructed_conj;
                    }

                }

            }
        }



        System.out.println("testing surprisingEntity -- 7 -- returning null conj");
        return reconstructed_conj;
    }


    /** This returns true if the entity is surprising with respect to
     * the given conjecture, and false otherwise. It is considered
     * surprising if the conjecture is an implication/equilavent and the
     * antecedent fails. In the thesis it is referred to as surprising
     * type 2. needs testing */

    public boolean antecedentFailure(Entity entity, Conjecture conjecture)
    {
        Concept lh_concept = new Concept();
        Concept rh_concept = new Concept();
        Vector positives = new Vector();
        positives.addElement(entity.name);

        if(conjecture instanceof Implication)
        {
            Implication imp = (Implication)conjecture;
            lh_concept = imp.lh_concept;
            return !(lh_concept.positivesContains(positives));
        }

        if(conjecture instanceof NearImplication)
        {
            NearImplication nimp = (NearImplication)conjecture;
            lh_concept = nimp.lh_concept;
            return !(lh_concept.positivesContains(positives));
        }

        if(conjecture instanceof Equivalence)
        {
            Equivalence equiv = (Equivalence)conjecture;
            lh_concept = equiv.lh_concept;
            if(!(lh_concept.positivesContains(positives)))
                return !(rh_concept.positivesContains(positives));
        }

        if(conjecture instanceof NearEquivalence)
        {
            NearEquivalence nequiv = (NearEquivalence)conjecture;
            lh_concept = nequiv.lh_concept;
            if(!(lh_concept.positivesContains(positives)))
                return !(rh_concept.positivesContains(positives));
        }

        return false;
    }



    /* This method takes in an implication conjecture and returns a
     * concept of entities for which the conjecture holds -- extend to
     * hold for all types of conjecture
     */

    // public Concept conceptfromConjecture(Implication implication, Theory theory)
//   {
//     //need to test it on concepts of a different arity. the
//     //arguments to compose will change (maybe use the
//     //getParameters method)

//     Concept core_concept = findObjectOfInterestConcept(theory.concepts, entity_strings);

//     Concept lh_concept = implication.lh_concept;
//     Concept rh_concept = implication.rh_concept;

//     String production_rule = " compose [1" ;

//     for(int i=1;i<rh_concept.arity;i++)
//       {
// 	production_rule = production_rule + ",0";
//       }
//     production_rule = production_rule + "] ";


//     String agenda_line1 = "id_of_dummy1 = " + rh_concept.id + " " + core_concept.id + production_rule;

//     //String agenda_line1 = "id_of_dummy1 = " + faulty_lemma_nimp.rh_concept.id + " " + core_concept.id + " compose [1] ";
//     System.out.println("agenda_line1 is " + agenda_line1);
//     Step forced_step1 = guider.forceStep(agenda_line1, theory);
//     TheoryConstituent tc_dummy1 = new TheoryConstituent();
//     tc_dummy1 = forced_step1.result_of_step;
//     System.out.println("globalAndLocal l-inc: tc_dummy1 is " + tc_dummy1.writeTheoryConstituent());



//   }

    //  /** Given */
//   public Vector nonLemConcept()
//   {


//   }


}



/** PROBABLY OMIT
 //note - can only take equiv or impln conjectures at present

 public Vector piecemealExclusion1(Conjecture faulty_conj, Theory theory)
 {
 System.out.println("started piecemealExclusion on " + faulty_conj.toString());
 System.out.println("started piecemealExclusion on " + faulty_conj.writeConjecture("ascii"));

 Concept left_concept = null;
 Concept right_concept = null;

 Vector possible_modifications = new Vector();

 //get counters
 Vector counterexamples = faulty_conj.getCountersToConjecture();

 //work out if in lhs or rhs
 Vector counters_to_lhs = new Vector();
 Vector counters_to_rhs = new Vector();

 if (faulty_conj instanceof Equivalence)
 {
 left_concept = ((Equivalence)faulty_conj).lh_concept;
 right_concept = ((Equivalence)faulty_conj).rh_concept;
 }

 if (faulty_conj instanceof Implication)
 {
 left_concept = ((Implication)faulty_conj).lh_concept;
 right_concept = ((Implication)faulty_conj).rh_concept;
 }

 for (int i=0;i<counterexamples.size();i++)
 {
 String countereg = (String)counterexamples.elementAt(i);
 Vector counterexample_vector = new Vector();
 counterexample_vector.addElement(countereg);
 if (left_concept.positivesContains(counterexample_vector))
 {
 counters_to_lhs.addElement(countereg);
 }
 else counters_to_rhs.addElement(countereg);
 }


 boolean counters_on_left = !(counters_to_lhs.isEmpty());
 boolean counters_on_right = !(counters_to_rhs.isEmpty());

 return possible_modifications; //take out bracket here
 }





 Vector concepts_for_disjunct = new Vector();

 int i = 0;
 //split
 for(i=0; i<entity_strings.size();i++)
 {
 String entity_string = (String)entity_strings.elementAt(i);
 Vector parameters = new Vector();
 parameters.addElement(entity_string);
 System.out.println("\n*****           starting            *********\n");

 String agenda_line = "step_"+i+" = int001 split [[0], "+parameters+"]";
 System.out.println("agenda_line is " + agenda_line);
 System.out.println("**************\n");

 agenda.addForcedStep(agenda_line, false);
 Step forcedstep = guider.forceStep(agenda_line, theory);

 System.out.println("*****               finishing           *********\n");
 if (forcedstep.result_of_step instanceof Concept)
 concepts_for_disjunct.addElement((Concept)forcedstep.result_of_step);

 System.out.println(i + ": forcedstep.result_of_step is " + forcedstep.result_of_step);
 System.out.println("**************\n");
 }

 //disjunct
 if(concepts_for_disjunct.isEmpty())
 return output;
 //omit?
 if(concepts_for_disjunct.size()==1)
 return (Concept)concepts_for_disjunct.elementAt(0);

 else
 {
 System.out.println("into disjunct");
 Concept first_concept = (Concept)concepts_for_disjunct.elementAt(0);
 for(i=1; i<concepts_for_disjunct.size();i++)
 {
 Concept second_concept = (Concept)concepts_for_disjunct.elementAt(i);
 System.out.println("first_concept is " + first_concept.writeDefinition("ascii"));
 System.out.println("second_concept is " + second_concept.writeDefinition("ascii"));
 String agenda_line = "forced_step = "+first_concept.id+" "+second_concept.id+" disjunct []";
 System.out.println("agenda_line is " + agenda_line);
 agenda.addForcedStep(agenda_line, false);
 Step forcedstep = guider.forceStep(agenda_line, theory);
 System.out.println("forcedstep.result_of_step is " + forcedstep.result_of_step);
 if(forcedstep.result_of_step instanceof Concept)
 first_concept = (Concept)forcedstep.result_of_step;
 if(forcedstep.result_of_step instanceof Equivalence)
 first_concept = ((Equivalence)forcedstep.result_of_step).rh_concept;//breaking here
 }
 System.out.println("returning first_concept now: " + first_concept.writeDefinition("ascii"));
 return first_concept;

 }

 */
