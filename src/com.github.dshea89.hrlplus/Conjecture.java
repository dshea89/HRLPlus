package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.lang.String;
import java.awt.TextField;
import java.io.Serializable;
import java.util.Hashtable;

/** A class representing a conjecture
 * @author Simon Colton, started 1st December 2000
 * @version 1.0
 * @see Equivalence
 * @see NonExists
 */

public class Conjecture extends TheoryConstituent implements Serializable
{
    /** The name of the explainer which explained this conjecture.
     */

    public String explained_by = "";

    /** Whether or not the conjecture involves segregated concepts.
     */

    public boolean involves_segregated_concepts = false;

    /** The parent conjectures of this conjecture (e.g., implication extracted
     * from equivalence, prime implicate extract from implicate, etc.)
     */

    public Vector parent_conjectures = new Vector();

    /** The children conjectures of this conjecture (e.g., implication extracted
     * from equivalence, prime implicate extract from implicate, etc.)
     */

    public Vector child_conjectures = new Vector();

    /** Whether or not to write this conjecture with simplified definitions.
     */

    public boolean simplify_definitions = false;

    /** Whether or not this conjecture involves an instantiation of the entity,
     * e.g., a is prime and even <-> a=2.
     */

    public boolean involves_instantiation = false;

    /** Whether or not to include the entity letter in the universal quantification
     * when writing this conjecture.
     */

    public boolean use_entity_letter = false;

    /** The axiom names used for proving this conjecture.
     */

    public Vector axiom_names = new Vector();

    /** The maximum proof time of all the proof attempts in MathWeb.
     */

    public double mw_max_proof_time = 0;

    /** The average proof time of all the proof attempts in MathWeb.
     */

    public double mw_av_proof_time = 0;

    /** The minimum proof time of all the proof attempts in MathWeb.
     */

    public double mw_min_proof_time = -1;

    /** The difference in the max and min times in all the proof attempts in MathWeb.
     */

    public double mw_diff_proof_time = 0;

    /** A vector of information about proof attempts on this conjecture by various
     * provers (in MathWeb). Each entry is of the form prover:status:time
     */

    public Vector proof_attempts_information = new Vector();

    /** Whether of not this conjecture is trivially true.
     */

    public boolean is_trivially_true = false;

    /** Whether or not this conjecture has been subsumed by a stronger conjecture.
     */

    public boolean is_removed = false;

    /** Whether or not this conjecture is a prime implicate (i.e. an implicate conjecture
     * where no subset of the premises implies the goal).
     */

    public boolean is_prime_implicate = false;

    /** The counterexamples to this conjecture (if any).
     */

    public Vector counterexamples = new Vector();

    /** The proof status of this conjecture. This can either be "proved", "disproved",
     * "sos" or "time", the latter two meaning no proof or disproof has been
     * found, but either the theorem prover has run out of set of support, or it has run
     * of time.
     */

    public String proof_status = "open";

    /** The length of the proof of this conjecture (if any).
     */

    public double proof_length = 0;

    /** The time taken to find the proof
     */

    public double proof_time = 0;

    /** The wall clock time taken to find the proof
     */

    public double wc_proof_time = 0;

    /** The level of the proof of this conjecture (if any).
     */

    public double proof_level = 0;

    /** The arity of the conjecture.
     */

    public double arity = 0;

    /** The applicability of the conjecture.
     */

    public double applicability = 0;

    /** The normalised applicability of the conjecture.
     */

    public double normalised_applicability = 0;

    /** The type of the conjecture. This is either equivalence, non-exists, implication
     * or implicate.
     */

    public String type = "";

    /* The overall interestingness value of this conjecture.
     */

    public double interestingness = 0;

    /** The number of lakatos modifications which have been performed on
     * this method.
     */

    public int num_modifications = 0;

    /* The complexity (measured as the sum of the complexity of both concepts in
     * equivalences and implications, and the complexity of the non-existent concept
     * in ne-conjectures) of this conjecture.
     */

    public double complexity = 0;

    /** The comprehensibility of this conjecture.
     */

    public double comprehensibility = 0;

    /** The normalised comprehensibility of this conjecture.
     */

    public double normalised_comprehensibility = 0;

    /* The surprisingness of this conjecture.
     */

    public double surprisingness = 0;

    /* The normalised surprisingness of this conjecture.
     */

    public double normalised_surprisingness = 0;

    /* The plausibility of this conjecture.
     */

    public double plausibility = 0;

    /* The construction step which led to this conjecture.
     */

    public Step step = new Step();

    /** The object type of the conjecture (the object type of the entities
     * described by the concepts in the conjecture).
     */

    public String object_type = "";


    /** Whether the conjecture has been produced using Lakatos methods, and
     * if so which one.
     */

    public String lakatos_method = "no";


    /** This writes the conjecture, then changes occurences of "<" as &lt;, so
     * that the HTML looks good.
     */

    public String writeConjectureHTML(String language)
    {
        return replaceLTForHTML(writeConjecture(language));
    }

    /** The method for writing the conjecture in the given language.
     */

    public String writeConjecture(String language)
    {
        return "";
    }


    /** The method for writing the conjecture in ascii
     */

    public String writeConjecture()
    {
        return this.writeConjecture("ascii");
    }

    /** Another method for writing the conjecture in ascii.
     */

    public String toString()
    {
        return writeConjecture();
    }


    /** The method for writing the related datatable
     */

    public String writeConcept()
    {
        return "";
    }

    /** This returns the domain in which the conjecture has been made.
     */

    public String getDomain()
    {
        return "";
    }

    /** The proof object for this conjecture (if it has been proved).
     */

    public String proof = "";

    public String fullDetails(String[] attributes_in, int dp, String[] concept_attributes)
    {
        return fullASCIIDetails(attributes_in, dp, concept_attributes);
    }

    /** This writes all the details of the conjecture to a string.
     */

    public String fullASCIIDetails(String[] attributes_in, int dp, String[] concept_attributes)
    {
        Vector attributes = new Vector();
        for (int i=0; i<attributes_in.length; i++)
            attributes.addElement(attributes_in[i]);

        String output = "";

        // Identifier and definitions //

        if (attributes.contains("id"))
            output = output + "(" + id + ")";

        if (attributes.contains("simplified format"))
        {
            boolean simplify_definitions_old = simplify_definitions;
            simplify_definitions = true;
            output = output + writeConjecture("otter") + "\n";
            simplify_definitions = simplify_definitions_old;
        }
        if (attributes.contains("ascii format"))
            output = output + writeConjecture("ascii") + "\n";
        if (attributes.contains("otter format"))
            output = output + writeConjecture("otter") + "\n";
        if (attributes.contains("prolog format"))
            output = output + writeConjecture("prolog") + "\n";
        if (attributes.contains("tptp format"))
            output = output + writeConjecture("tptp") + "\n";

        if (!(output.equals("")))
            output = output + "-----------------\n";
        String snapshot = output;

        // Proof attempts //

        if (attributes.contains("proof_status"))
            output = output + "The proof status of this theorem is: "+proof_status+"\n";
        if (proof_attempts_information.isEmpty())
        {
            if (attributes.contains("prover_details"))
                output = output + "There have been no proof attempts on this conjecture.\n";
        }
        else
        {
            output = output + "Prover details:\n";
            for (int i=0; i<proof_attempts_information.size(); i++)
            {
                String pai = (String)proof_attempts_information.elementAt(i);
                int ind = pai.indexOf(":");
                output = output + pai.substring(0,ind) + " took " +
                        pai.substring(ind+1, pai.lastIndexOf(":")) + " miliseconds, and the proof status was: " +
                        pai.substring(pai.lastIndexOf(":")+1,pai.length())+"\n";
            }
            if (attributes.contains("mw_max_ptime"))
                output = output + "Max proof time in MathWeb = " + decimalPlaces(mw_max_proof_time, dp) + "\n";
            if (attributes.contains("mw_min_ptime"))
                output = output + "Min proof time in MathWeb = " + decimalPlaces(mw_min_proof_time, dp) + "\n";
            if (attributes.contains("mw_av_ptime"))
                output = output + "Average proof time in MathWeb = " + decimalPlaces(mw_av_proof_time, dp) + "\n";
            if (attributes.contains("mw_diff_ptime"))
            {
                output = output + "Largest - smallest proof time in MathWeb = "
                        + decimalPlaces(mw_diff_proof_time, dp) + "\n";
            }
        }

        if (attributes.contains("explained_by"))
            output = output + "Explained by: " + explained_by + "\n";

        if (attributes.contains("proof"))
        {
            if (proof.equals(""))
                output = output + "The proof was not recorded.\n";
            else
            {
                output = output + "proof length = " + proof_length;
                output = output + ", proof level = " + proof_level;
                output = output + "\nProof:\n" + proof + "\n";
            }
        }
        if (attributes.contains("counter"))
        {
            if (counterexamples.isEmpty())
                output = output + "No counterexample to this conjecture has been found.\n";
            else
            {
                output = output  + "The counterexamples to this conjecture were: ";
                for (int i=0; i<counterexamples.size(); i++)
                {
                    Entity entity = (Entity)counterexamples.elementAt(i);
                    output = output + entity.name + " ";
                }
                output = output + "\n";
            }
        }

        // Measures of Interestingness //

        if (!(snapshot.equals(output)))
            output=output+"-----------------\n";
        snapshot = output;

        if (attributes.contains("total score"))
            output = output + "Total score = " + decimalPlaces(interestingness, dp) + "\n";

        if (attributes.contains("arity"))
            output = output + "Arity = " + decimalPlaces(arity, dp) + "\n";

        if (attributes.contains("applicability"))
            output = output + "Applicability = " + decimalPlaces(applicability, dp)
                    + " (" + decimalPlaces(normalised_applicability, dp) + ")\n";

        if (attributes.contains("complexity"))
            output = output + "Complexity = " + decimalPlaces(complexity, dp) + "\n";

        if (attributes.contains("comprehensibility"))
            output = output + "Comprehensibility = " + decimalPlaces(comprehensibility, dp)
                    + " (" + decimalPlaces(normalised_comprehensibility, dp) + ")\n";

        if (attributes.contains("surprisingness"))
        {
            output = output + "Surprisingness = " + decimalPlaces(surprisingness, dp) +
                    " (" + decimalPlaces(normalised_surprisingness, dp) + ")\n";
        }

        if (attributes.contains("plausibility"))
        {
            output = output + "Plausibility = " + decimalPlaces(plausibility, dp)+ "\n";
            //+" (" + decimalPlaces(normalised_plausibility, dp) + ")\n";
        }


        // The construction of this conjecture //

        if (!(snapshot.equals(output)))
            output=output+"-----------------\n";
        snapshot = output;

        if (attributes.contains("const time"))
        {
            String s_or_not = "s";
            String seconds = Long.toString(when_constructed).trim();
            if (seconds.equals("1"))
                s_or_not = "";
            output=output+"Constructed after "+Long.toString(when_constructed) +
                    " second"+s_or_not;
        }

        if (attributes.contains("step"))
            output = output + "Construction step leading to this conjecture: " + step.asString() + "\n";

        if (attributes.contains("lh_concept"))
        {
            if (!(snapshot.equals(output)))
                output=output+"-----------------\n";
            snapshot = output;
            if (this instanceof Equivalence)
            {
                output = output + "The left hand concept of this equivalence has these details:\n";
                output = output + ((Equivalence)this).lh_concept.fullDetails("ascii", concept_attributes, dp);
            }
            if (this instanceof NearEquivalence)
            {
                output = output + "The left hand concept of this near_equivalence has these details:\n";
                output = output + ((NearEquivalence)this).lh_concept.fullDetails("ascii", concept_attributes, dp);
            }
            if (this instanceof NearImplication) //alisonp
            {
                output = output + "The left hand concept of this near_implication has these details:\n";
                output = output + ((NearImplication)this).lh_concept.fullDetails("ascii", concept_attributes, dp);
            }
            if (this instanceof Implication)
            {
                output = output + "The left hand concept of this implication has these details:\n";
                output = output + ((Implication)this).lh_concept.fullDetails("ascii", concept_attributes, dp);
            }
            if (this instanceof Implicate)
            {
                output = output + "The left hand concept of this implicate has these details:\n";
                output = output + ((Implicate)this).premise_concept.fullDetails("ascii", concept_attributes, dp);
            }
            if (this instanceof NonExists)
            {
                output = output + "The non-existent concept has these details:\n";
                output = output + ((NonExists)this).concept.fullDetails("ascii", concept_attributes, dp);
            }
        }

        if (attributes.contains("rh_concept"))
        {
            if (!(snapshot.equals(output)))
                output=output+"-----------------\n";
            snapshot = output;
            if (this instanceof Equivalence)
            {
                output = output + "The right hand concept of this equivalence has these details:\n";
                output = output + ((Equivalence)this).rh_concept.fullDetails(concept_attributes, dp);
            }
            if (this instanceof NearEquivalence)
            {
                output = output + "The right hand concept of this equivalence has these details:\n";
                output = output + ((NearEquivalence)this).rh_concept.fullDetails(concept_attributes, dp);
            }
            if (this instanceof NearImplication) //alisonp
            {
                output = output + "The right hand concept of this near_implication has these details:\n";
                output = output + ((NearImplication)this).rh_concept.fullDetails("ascii", concept_attributes, dp);
            }
            if (this instanceof Implication)
            {
                output = output + "The right hand concept of this equivalence has these details:\n";
                output = output + ((Implication)this).rh_concept.fullDetails(concept_attributes, dp);
            }
        }
        return output;
    }

    /** This writes all the details of the conjecture to a string.
     */

    public String fullHTMLDetails(String[] attributes_in, int dp, String[] concept_attributes)
    {
        Vector attributes = new Vector();
        for (int i=0; i<attributes_in.length; i++)
            attributes.addElement(attributes_in[i]);

        String output = "";

        // Identifier and definitions //

        if (attributes.contains("id"))
            output = output + "(" + id + ")";

        String main_language = "";
        int num_defs = 0;

        if (attributes.contains("simplified format"))
        {
            num_defs++;
            main_language = "otter";
        }
        if (attributes.contains("ascii format"))
        {
            num_defs++;
            main_language = "ascii";
        }
        if (attributes.contains("otter format"))
        {
            num_defs++;
            main_language = "otter";
        }
        if (attributes.contains("prolog format"))
        {
            num_defs++;
            main_language = "prolog";
        }
        if (attributes.contains("tptp format"))
        {
            num_defs++;
            main_language = "tptp";
        }

        String definition_string = "";
        if (num_defs==1)

        {
            boolean simplify_definitions_old = simplify_definitions;
            if (attributes.contains("simplified format"))
                simplify_definitions = true;
            output = output +
                    "<table border=1 cellpadding=3 fgcolor=white bgcolor=\"#ffff99\"><tr><td><font color=blue><b>" +
                    replaceLTForHTML(writeConjecture(main_language))+"</b></font></td></tr></table><br>\n";
            if (attributes.contains("simplified format"))
                simplify_definitions = simplify_definitions_old;
        }

        if (num_defs>1)
        {
            main_language = "ascii";
            output = output + "<ul>\n";

            if (attributes.contains("simplified format"))
            {
                boolean simplify_definitions_old = simplify_definitions;
                simplify_definitions = true;
                output = output + "<li>" + replaceLTForHTML(writeConjecture("otter")) + "</li>\n";
                simplify_definitions = simplify_definitions_old;
            }
            if (attributes.contains("ascii format"))
                output = output + "<li>"+replaceLTForHTML(writeConjecture("ascii"))+"</li>\n";
            if (attributes.contains("otter format"))
                output = output + "<li>"+replaceLTForHTML(writeConjecture("otter"))+"</li>\n";
            if (attributes.contains("prolog format"))
                output = output + "<li>"+replaceLTForHTML(writeConjecture("prolog"))+"</li>\n";
            if (attributes.contains("tptp format"))
                output = output + "<li>"+replaceLTForHTML(writeConjecture("tptp"))+"</li>\n";
        }

        if (!(output.equals("")))
            output = output + "<hr>\n";
        String snapshot = output;

        // Proof attempts //
        output = output + "\n<table border=0><tr><td align=center><font size=4 color=red>Details</font></td>";
        output = output + "<td align=center><font size=4 color=green>Solution</font></td></tr>\n";
        output = output + "<tr valign=top><td><table border=1><td>Measure</td><td>Value</td><td>Normalised</td></tr>\n";

        if (attributes.contains("proof_status"))
            output = output + "<tr><td>Proof status</td><td>" + proof_status+"</td><td></td></tr>\n";

        if (attributes.contains("explained_by"))
            output = output + "<tr><td>Explained by</td><td>" + explained_by + "</td><td></td></tr>\n";

        if (attributes.contains("proof"))
        {
            output = output + "<tr><td>Proof length</td><td>" + proof_length + "</td><td></td></tr>\n";
            output = output + "<tr><td>Proof level</td><td>" + proof_level + "</td><td></td></tr>\n";
        }
        if (!proof_attempts_information.isEmpty())
        {
            for (int i=0; i<proof_attempts_information.size(); i++)
            {
                String pai = (String)proof_attempts_information.elementAt(i);
                int ind = pai.indexOf(":");
                output = output + "<tr><td>" + pai.substring(0,ind) + " time</td><td>" +
                        pai.substring(ind+1, pai.lastIndexOf(":")) + "</td><td>" +
                        pai.substring(pai.lastIndexOf(":")+1,pai.length())+"</td></tr>\n";
            }
            if (attributes.contains("mw_max_ptime"))
                output = output + "<tr><td>Max MathWeb proof time</td><td>" + decimalPlaces(mw_max_proof_time, dp) + "</td><td></td></tr>\n";
            if (attributes.contains("mw_min_ptime"))
                output = output + "<tr><td>Min MathWeb proof time</td><td>" + decimalPlaces(mw_min_proof_time, dp) + "</td><td></td></tr>\n";
            if (attributes.contains("mw_av_ptime"))
                output = output + "<tr><td>Average MathWeb proof time</td><td>" + decimalPlaces(mw_av_proof_time, dp) + "</td><td></td></tr>\n";
            if (attributes.contains("mw_diff_ptime"))
            {
                output = output + "<tr><td>Largest - smallest MathWeb proof time</td><td></td><td>"
                        + decimalPlaces(mw_diff_proof_time, dp) + "</td></tr>\n";
            }
        }

        if (attributes.contains("total score"))
            output = output + "<tr><td>Total score</td><td>" + decimalPlaces(interestingness, dp) + "</td><td></td></tr>\n";

        if (attributes.contains("arity"))
            output = output + "<tr><td>Arity</td><td>" + decimalPlaces(arity, dp) + "</td><td></td></tr>\n";

        if (attributes.contains("applicability"))
            output = output + "<tr><td>Applicability</td><td>" + decimalPlaces(applicability, dp)
                    + "</td><td>" + decimalPlaces(normalised_applicability, dp) + "</td></tr>\n";

        if (attributes.contains("complexity"))
            output = output + "<tr><td>Complexity</td><td>" + decimalPlaces(complexity, dp) + "</td><td></td></tr>\n";

        if (attributes.contains("comprehensibility"))
            output = output + "<tr><td>Comprehensibility</td><td>" + decimalPlaces(comprehensibility, dp)
                    + "</td><td>" + decimalPlaces(normalised_comprehensibility, dp) + "</td></tr>\n";

        if (attributes.contains("surprisingness"))
        {
            output = output + "<tr><td>Surprisingness</td><td>" + decimalPlaces(surprisingness, dp) +
                    "</td><td>" + decimalPlaces(normalised_surprisingness, dp) + "</td></tr>\n";
        }
        if (attributes.contains("plausibility"))
        {
            output = output + "<tr><td>Plausibility</td><td>" + decimalPlaces(plausibility, dp) +
                    "</td><td>" + "N/A" + "</td></tr>\n";
        }


        if (attributes.contains("const time"))
            output = output + "<tr><td>Constructed after</td><td>"+Long.toString(when_constructed)+" seconds</td><td></tr></tr>\n";

        if (attributes.contains("step"))
            output = output + "<tr><td>Construction step</td><td>" + step.asString() + "</td><td></td></tr>\n";

        output = output + "</table>\n";
        output = output + "</td><td>";

        if (proof_attempts_information.isEmpty())
        {
            if (attributes.contains("prover_details"))
                output = output + "There have been no proof attempts on this conjecture.\n";
        }

        if (attributes.contains("counter"))
        {
            if (!counterexamples.isEmpty())
            {
                if (counterexamples.size()==1)
                    output = output + "The counterexample to this conjecture is:<p> ";
                else
                    output = output  + "The counterexamples to this conjecture are:<p> ";
                for (int i=0; i<counterexamples.size(); i++)
                {
                    Entity entity = (Entity)counterexamples.elementAt(i);
                    output = output + entity.name + "<br>";
                }
                output = output + "<p>";
            }
        }
        if (attributes.contains("proof"))
        {
            if (proof.equals(""))
            {
                if (proof_status.equals("proved") && !is_trivially_true)
                    output = output + "The proof was not recorded.\n";
            }
            else
            {
                if (!is_trivially_true)
                {
                    output = output + "Proof:\n";
                    output = output + "<pre>\n" + proof + "</pre>";
                }
            }
        }
        output = output + "</td></tr></table>\n";

        if (attributes.contains("lh_concept"))
        {
            if (!(snapshot.equals(output)))
                output=output+"<hr>\n";
            snapshot = output;
            output = output + "<font size=5 color=red>\n";
            if (this instanceof Equivalence)
            {
                output = output + "Left hand concept of this equivalence:</font><p>\n";
                output = output + ((Equivalence)this).lh_concept.fullDetails("html", concept_attributes, dp);
            }
            if (this instanceof NearEquivalence)
            {
                output = output + "Left hand concept of this near equivalence:</font><p>\n";
                output = output + ((NearEquivalence)this).lh_concept.fullDetails("html", concept_attributes, dp);
            }
            if (this instanceof NearImplication)//alisonp
            {
                output = output + "Left hand concept of this near implication:</font><p>\n";
                output = output + ((NearImplication)this).lh_concept.fullDetails("html", concept_attributes, dp);
            }
            if (this instanceof Implication)
            {
                output = output + "Left hand concept of this implication:</font><p>\n";
                output = output + ((Implication)this).lh_concept.fullDetails("html", concept_attributes, dp);
            }
            if (this instanceof Implicate)
            {
                output = output + "Left hand concept of this implicate:</font><p>\n";
                output = output + ((Implicate)this).premise_concept.fullDetails("html", concept_attributes, dp);
            }
            if (this instanceof NonExists)
            {
                output = output + "Non-existent concept:</font><p>:\n";
                output = output + ((NonExists)this).concept.fullDetails("html", concept_attributes, dp);
            }
        }

        if (attributes.contains("rh_concept"))
        {
            if (!(snapshot.equals(output)))
                output=output+"<hr>\n";
            snapshot = output;
            output = output + "<font size=5 color=red>\n";

            if (this instanceof Equivalence)
            {
                output = output + "Right hand concept of this equivalence:</font><p>\n";
                output = output + ((Equivalence)this).rh_concept.fullDetails("html",concept_attributes, dp);
            }
            if (this instanceof NearEquivalence)
            {
                output = output + "Right hand concept of this near equivalence:</font><p>\n";
                output = output + ((NearEquivalence)this).rh_concept.fullDetails("html",concept_attributes, dp);
            }
            if (this instanceof NearImplication)//alisonp
            {
                output = output + "Right hand concept of this near implication:</font><p>\n";
                output = output + ((NearImplication)this).rh_concept.fullDetails("html",concept_attributes, dp);
            }
            if (this instanceof Implication)
            {
                output = output + "Right hand concept of this implication:</font><p>\n";
                output = output + ((Implication)this).rh_concept.fullDetails("html",concept_attributes, dp);
            }
            if (!(this instanceof Equivalence) && !(this instanceof NearEquivalence) && !(this instanceof NearImplication) && !(this instanceof Implication))//alisonp
                output = output + "</font>\n";
        }
        return output;
    }

    /** Whether this conjecture has been forced or not
     */

    public boolean decideForced()
    {
        if (this instanceof Equivalence)
            return (((Equivalence)this).rh_concept.construction).forced;
        if (this instanceof NearEquivalence)
            return (((NearEquivalence)this).rh_concept.construction).forced;
        if (this instanceof NearImplication)//alisonp
            return (((NearImplication)this).rh_concept.construction).forced;
        if (this instanceof Implication)
            return (((Implication)this).rh_concept.construction).forced;
        if (this instanceof NonExists)
            return(((NonExists)this).concept.construction).forced;
        return false;
    }

    /** Whether this conjecture subsumes the given conjecture.
     */

    public boolean subsumes(Conjecture other_conjecture)
    {
        return false;
    }

    /** This sets the involves_segregated_concepts flag if the agenda says
     * that the two concepts (in the implication/equivalence/near_equiv) are segregated.
     */

    public void checkSegregation(Agenda agenda)
    {
        if (this instanceof NearEquivalence)
        {
            NearEquivalence near_equiv = (NearEquivalence)this;
            involves_segregated_concepts =
                    agenda.conceptsAreSegregated(near_equiv.lh_concept, near_equiv.rh_concept);
        }
        if (this instanceof NearImplication)//alisonp
        {
            NearImplication near_imp = (NearImplication)this;
            involves_segregated_concepts =
                    agenda.conceptsAreSegregated(near_imp.lh_concept, near_imp.rh_concept);
        }
        if (this instanceof Equivalence)
        {
            Equivalence equiv = (Equivalence)this;
            involves_segregated_concepts =
                    agenda.conceptsAreSegregated(equiv.lh_concept, equiv.rh_concept);
        }
        if (this instanceof Implication)
        {
            Implication implication = (Implication)this;
            involves_segregated_concepts =
                    agenda.conceptsAreSegregated(implication.lh_concept, implication.rh_concept);
        }
    }

    /** Given a string representation of an entity, this checks whether
     * the conjecture is broken by that entity. NearEquivalence and
     * NearImplication need testing - alisonp
     */

    public boolean conjectureBrokenByEntity(String entity, Vector all_concepts)
    {
        System.out.println("starting conjectureBrokenByEntity");
        System.out.println("conj is:" + this.writeConjecture());
        System.out.println("entity is:" + entity);
        System.out.println("I'm returning false...");

        if (this instanceof Equivalence)
        {
            Equivalence conj = (Equivalence)this;
            Row lh_row = conj.lh_concept.calculateRow(all_concepts, entity);
            Row rh_row = conj.rh_concept.calculateRow(all_concepts, entity);
            if (lh_row.tuples.toString().equals(rh_row.tuples.toString()))
                return false;
        }
        if (this instanceof NearEquivalence) //alisonp
        {
            NearEquivalence nequiv = (NearEquivalence)this;
            Row lh_row = nequiv.lh_concept.calculateRow(all_concepts, entity);
            Row rh_row = nequiv.rh_concept.calculateRow(all_concepts, entity);
            if (lh_row.tuples.toString().equals(rh_row.tuples.toString()))
                return false;
        }

        if (this instanceof Implication)
        {
            Implication imp = (Implication)this;
            Row lh_row = imp.lh_concept.calculateRow(all_concepts, entity);
            Row rh_row = imp.rh_concept.calculateRow(all_concepts, entity);
            if (lh_row.tuples.isEmpty())
                return false;
            if (lh_row.tuples.toString().equals(rh_row.tuples.toString()))
                return false;
        }

        if (this instanceof NearImplication)//alisonp
        {
            NearImplication nimp = (NearImplication)this;
            Row lh_row = nimp.lh_concept.calculateRow(all_concepts, entity);
            Row rh_row = nimp.rh_concept.calculateRow(all_concepts, entity);
            if (lh_row.tuples.isEmpty())
                return false;
            if (lh_row.tuples.toString().equals(rh_row.tuples.toString()))
                return false;
        }

        if (this instanceof NonExists)
        {
            NonExists ne = (NonExists)this;
            Row row = ne.concept.calculateRow(all_concepts, entity);
            if (row.tuples.isEmpty())
                return false;
        }
        if (this instanceof Implicate)
        {
            if (parent_conjectures.isEmpty())
                return false;
            Conjecture parent = (Conjecture)parent_conjectures.elementAt(0);
            return parent.conjectureBrokenByEntity(entity, all_concepts);
        }
        System.out.println("...except here -- i'm now returning true");
        return true;
    }

    /** Reconstructs a conjecture by:
     - reconstructing each concept
     - when done 2nd, getting all the conjectures associated with it
     - looking through each to see whether it has the first concept or not (returning if so)
     alisonp
     */

    // need to add to so can deal with Implicate and NonExists

    /**
     public Conjecture reconstructConjecture(Theory theory)
     {
     //System.out.println("started reconstructConjecture(theory)");

     TheoryConstituent tc1 = new TheoryConstituent();
     TheoryConstituent tc2 = new TheoryConstituent();

     Concept concept1_to_build = new Concept();
     Concept concept2_to_build = new Concept();
     Conjecture reconstructed_conjecture = new Conjecture();

     if (this instanceof Implicate)
     concept1_to_build = ((Implicate)this).premise_concept.reconstructConcept(theory);//might need casting
     if (this instanceof NonExists)
     concept1_to_build = ((NonExists)this).concept.reconstructConcept(theory);
     if (this instanceof Implication)
     {
     concept1_to_build = ((Implication)this).lh_concept.reconstructConcept(theory);
     concept2_to_build = ((Implication)this).rh_concept.reconstructConcept(theory);
     }
     if (this instanceof Equivalence)
     {
     concept1_to_build = ((Equivalence)this).lh_concept.reconstructConcept(theory);
     concept2_to_build = ((Equivalence)this).rh_concept.reconstructConcept(theory);
     }
     if (this instanceof NearEquivalence)
     {
     //System.out.println("got a n_equiv - reconstructing the lh_concept: " + ((NearEquivalence)this).lh_concept.writeDefinition("ascii"));
     concept1_to_build = ((NearEquivalence)this).lh_concept.reconstructConcept(theory);
     //System.out.println("finished reconstructing the lh_concept");
     //System.out.println("reconstructing the rh_concept: " + ((NearEquivalence)this).rh_concept.writeDefinition("ascii"));
     concept2_to_build = ((NearEquivalence)this).rh_concept.reconstructConcept(theory);
     //System.out.println("finished reconstructing the rh_concept");
     }

     //now goes through all the conjectures in the theory to see
     //whether the construction of the new concepts has resulted in new
     //conjectures being formed

     //messy - need to change by not casting output of forceConcept to
     //a concept - start here on friday
     //System.out.println("looking for a conj");
     for(int i=0;i<theory.conjectures.size(); i++)
     {
     Concept current_lh_concept = new Concept();
     Concept current_rh_concept = new Concept();
     Conjecture conj = (Conjecture)theory.conjectures.elementAt(i);
     if (conj instanceof Equivalence)
     {
     current_lh_concept = ((Equivalence)conj).lh_concept;
     current_rh_concept = ((Equivalence)conj).rh_concept;
     }
     if (conj instanceof NearEquivalence)
     {
     current_lh_concept = ((NearEquivalence)conj).lh_concept;
     current_rh_concept = ((NearEquivalence)conj).rh_concept;
     }
     if (conj instanceof Implication)
     {
     current_lh_concept = ((Implication)conj).lh_concept;
     current_rh_concept = ((Implication)conj).rh_concept;
     }

     if(current_lh_concept.equals(concept1_to_build) || current_rh_concept.equals(concept1_to_build))
     {
     if(current_rh_concept.equals(concept2_to_build) || current_lh_concept.equals(concept2_to_build))
     {
     reconstructed_conjecture = conj;
     break;
     }
     }
     }
     //System.out.println("finished reconstructConjecture(theory)");
     return reconstructed_conjecture;
     }
     */



    //may need testing
    public Conjecture reconstructConjecture(Theory theory, AgentWindow window)
    {
        String dummy_conj = (new Integer(theory.conjectures.size())).toString();

        //first of all go through the conjectures in the theory to see
        //whether already have the conjecture - if so, return it
        for(int i=0; i<theory.conjectures.size();i++)
        {
            Conjecture conj = (Conjecture)theory.conjectures.elementAt(i);
            if(this.writeConjecture("ascii").equals(conj.writeConjecture("ascii")))
                return conj;
        }

        //if not, then see whether already got a near implication as an
        //implication, or a near equivalence as an equivalence. If so,
        //return it -- dec 2006
        if(this instanceof NearImplication || this instanceof NearEquivalence)
        {
            Concept lh_concept = new Concept();
            Concept rh_concept = new Concept();

            if(this instanceof NearImplication)
            {
                lh_concept = ((NearImplication)this).lh_concept;
                rh_concept = ((NearImplication)this).rh_concept;
            }
            if(this instanceof NearEquivalence)
            {
                lh_concept = ((NearEquivalence)this).lh_concept;
                rh_concept = ((NearEquivalence)this).rh_concept;
            }


            for(int i=0; i<theory.conjectures.size();i++)
            {
                Conjecture conj = (Conjecture)theory.conjectures.elementAt(i);
                Concept lh_concept1 = new Concept();
                Concept rh_concept1 = new Concept();

                if(conj instanceof Implication)
                {
                    lh_concept1 = ((Implication)conj).lh_concept;
                    rh_concept1 = ((Implication)conj).rh_concept;
                    if(lh_concept1.equals(lh_concept) && rh_concept1.equals(rh_concept))
                        return conj;
                    if(lh_concept1.equals(rh_concept) && rh_concept1.equals(lh_concept))
                        return conj;
                }

                if(conj instanceof Equivalence)
                {
                    lh_concept1 = ((Equivalence)conj).lh_concept;
                    rh_concept1 = ((Equivalence)conj).rh_concept;
                    if(lh_concept1.equals(lh_concept) && rh_concept1.equals(rh_concept))
                        return conj;
                    if(lh_concept1.equals(rh_concept) && rh_concept1.equals(lh_concept))
                        return conj;
                }

            }
        }
        //*************************************************

        TheoryConstituent tc1 = null;
        TheoryConstituent tc2 = null;
        Conjecture reconstructed_conjecture = new Conjecture();

        if (this instanceof Implicate)
        {
            for (int i=0; i<theory.concepts.size(); i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);
                if(concept.writeDefinition("ascii").equals((((Implicate)this).premise_concept).writeDefinition("ascii")))
                    tc1 = concept;
            }
            if(tc1 == null)//[check]
                tc1 = ((Implicate)this).premise_concept.reconstructConcept(theory, window);//might need casting
        }


        if (this instanceof NonExists)
        {
            for (int i=0; i<theory.concepts.size(); i++)
            {
                Concept current_concept = (Concept)theory.concepts.elementAt(i);
                if(current_concept.writeDefinition("ascii").equals((((NonExists)this).concept).writeDefinition("ascii")))
                {
                    NonExists reconstructed_conj = new NonExists(current_concept, dummy_conj);
                    reconstructed_conj.counterexamples = reconstructed_conj.getCountersToConjecture();
                    return reconstructed_conj;
                }
            }

            tc1 = ((NonExists)this).concept.reconstructConcept(theory, window);

            if(tc1 instanceof Concept)
            {
                Concept tc1_concept = (Concept)tc1;
                NonExists reconstructed_conj = new NonExists(tc1_concept, dummy_conj);
                reconstructed_conj.counterexamples = reconstructed_conj.getCountersToConjecture();
                return reconstructed_conj;
            }

            if(tc1 instanceof Equivalence)
            {
                Equivalence equiv = (Equivalence)tc1;
                Concept reconstructed_concept = (Concept)equiv.rh_concept;
                NonExists reconstructed_conj = new NonExists(reconstructed_concept, dummy_conj);
                reconstructed_conj.counterexamples = reconstructed_conj.getCountersToConjecture();
                return reconstructed_conj;
            }

            if(tc1 instanceof Implication)//check if works for implication
            {
                Implication imp = (Implication)tc1;
                Concept reconstructed_concept = (Concept)imp.rh_concept;
                NonExists reconstructed_conj = new NonExists(reconstructed_concept, dummy_conj);
                reconstructed_conj.counterexamples = reconstructed_conj.getCountersToConjecture();
                return reconstructed_conj;
            }

            if(tc1 instanceof NonExists)
                return (NonExists)tc1;
        }

        if (this instanceof Implication)
        {

            Concept left_concept = ((Implication)this).lh_concept;
            Concept right_concept = ((Implication)this).rh_concept;
            for (int i=0; i<theory.concepts.size(); i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);
                if(concept.writeDefinition("ascii").equals(left_concept.writeDefinition("ascii")))
                    tc1 = concept;
                if(concept.writeDefinition("ascii").equals(right_concept.writeDefinition("ascii")))
                    tc2 = concept;
            }

            if(tc1 == null)
                tc1 = left_concept.reconstructConcept(theory, window);
            if(tc2 == null)
                tc2 = right_concept.reconstructConcept(theory, window);

            if(tc1 instanceof Concept && tc2 instanceof Concept)
            {
                Implication reconstructed_conj = new Implication((Concept)tc1,(Concept)tc2,dummy_conj);
                reconstructed_conj.counterexamples = reconstructed_conj.getCountersToConjecture();
                if(reconstructed_conj.counterexamples.isEmpty())
                    return reconstructed_conj;
                else
                    return (new NearImplication((Concept)tc1,(Concept)tc2,reconstructed_conj.counterexamples,0.0));
            }
        }

        if (this instanceof Equivalence)
        {
            Concept left_concept = ((Equivalence)this).lh_concept;
            Concept right_concept = ((Equivalence)this).rh_concept;
            for (int i=0; i<theory.concepts.size(); i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);
                if(concept.writeDefinition("ascii").equals(left_concept.writeDefinition("ascii")))
                    tc1 = concept;
                if(concept.writeDefinition("ascii").equals(right_concept.writeDefinition("ascii")))
                    tc2 = concept;
            }

            if(tc1 == null)
                tc1 = left_concept.reconstructConcept(theory, window);
            if(tc2 == null)
                tc2 = right_concept.reconstructConcept(theory, window);

            if(tc1 instanceof Concept && tc2 instanceof Concept)
            {
                Equivalence reconstructed_conj = new Equivalence((Concept)tc1,(Concept)tc2,dummy_conj);
                Vector counters = reconstructed_conj.getCountersToConjecture();
                if(counters.isEmpty())
                    return reconstructed_conj;
                else
                    return (new NearEquivalence((Concept)tc1,(Concept)tc2,counters,0.0));
            }

            //new
            if(tc1 instanceof NonExists && tc2 instanceof Concept)
            {
                NonExists nexists = (NonExists)tc1;
                Equivalence reconstructed_conj = new Equivalence(nexists.concept,(Concept)tc2,dummy_conj);
                reconstructed_conj.counterexamples = reconstructed_conj.getCountersToConjecture();
                if(reconstructed_conj.counterexamples.isEmpty())
                    return reconstructed_conj;
                else
                    return (new NearEquivalence(nexists.concept,(Concept)tc2,reconstructed_conj.counterexamples,0.0));

            }
            if(tc1 instanceof Concept && tc2 instanceof NonExists)
            {
                NonExists nexists = (NonExists)tc2;
                Equivalence reconstructed_conj = new Equivalence((Concept)tc1,nexists.concept,dummy_conj);
                reconstructed_conj.counterexamples = reconstructed_conj.getCountersToConjecture();
                if(reconstructed_conj.counterexamples.isEmpty())
                    return reconstructed_conj;
                else
                    return (new NearEquivalence((Concept)tc1,nexists.concept,reconstructed_conj.counterexamples,0.0));

            }


        }


        if (this instanceof NearEquivalence)
        {
            Concept left_concept = ((NearEquivalence)this).lh_concept;
            Concept right_concept = ((NearEquivalence)this).rh_concept;

            for (int i=0; i<theory.concepts.size(); i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);
                if(concept.writeDefinition("ascii").equals(left_concept.writeDefinition("ascii")))
                    tc1 = concept;
                if(concept.writeDefinition("ascii").equals(right_concept.writeDefinition("ascii")))
                    tc2 = concept;
            }
            if(tc1 == null)
                tc1 = left_concept.reconstructConcept(theory, window);
            if(tc2 == null)
                tc2 = right_concept.reconstructConcept(theory, window);

            if(tc1 instanceof Concept && tc2 instanceof Concept)
            {
                Equivalence reconstructed_conj = new Equivalence((Concept)tc1,(Concept)tc2,dummy_conj);
                reconstructed_conj.counterexamples = reconstructed_conj.getCountersToConjecture();
                if(reconstructed_conj.counterexamples.isEmpty())
                    return reconstructed_conj;
                else
                    return (new NearEquivalence((Concept)tc1,(Concept)tc2,reconstructed_conj.counterexamples,0.0));
            }
        }

        if (this instanceof NearImplication)
        {
            Concept left_concept = ((NearImplication)this).lh_concept;
            Concept right_concept = ((NearImplication)this).rh_concept;

            for (int i=0; i<theory.concepts.size(); i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);
                if(concept.writeDefinition("ascii").equals(left_concept.writeDefinition("ascii")))
                    tc1 = concept;
                if(concept.writeDefinition("ascii").equals(right_concept.writeDefinition("ascii")))
                    tc2 = concept;
            }
            if(tc1 == null)
                tc1 = left_concept.reconstructConcept(theory, window);

            if(tc2 == null)
                tc2 = right_concept.reconstructConcept(theory, window);

            if(tc1 instanceof Concept && tc2 instanceof Concept)
            {
                Implication reconstructed_conj = new Implication((Concept)tc1,(Concept)tc2,dummy_conj);
                reconstructed_conj.counterexamples = reconstructed_conj.getCountersToConjecture();
                if(reconstructed_conj.counterexamples.isEmpty())
                    return reconstructed_conj;
                else
                    return (new NearImplication((Concept)tc1,(Concept)tc2,reconstructed_conj.counterexamples,0.0));
            }
        }


        //now goes through all the conjectures in the theory to see
        //whether the construction of the new concepts has resulted in new
        //conjectures being formed

        if (tc1 instanceof Conjecture)
            return (Conjecture)tc1;

        if (tc2 instanceof Conjecture)
            return (Conjecture)tc2;

        return reconstructed_conjecture;
    }

    /** Reconstructs a given conjecture with a new entity added to the
     theory. */

    public Conjecture reconstructConjectureWithNewEntity(Theory theory, AgentWindow window, Entity entity)
    {
        window.writeToFrontEnd("started reconstructConjectureWithNewEntity");
        window.writeToFrontEnd("initially the conj is " + this.writeConjecture("ascii"));
        Conjecture reconstructed_conjecture = new Conjecture();
        Vector conj_concepts = this.conceptsInConjecture();

        //concept1.datatable
        //   concept1.categorisation

        Concept domain_concept = new Concept();
        for(int i=0; i<theory.concepts.size();i++)
        {
            Concept current_concept = (Concept)theory.concepts.elementAt(i);
            if (current_concept.is_object_of_interest_concept)
            {
                domain_concept = current_concept;
                break;
            }
        }

        // X.2 Update the datatables, categorisations and same datatable
        // hashtable entries of the concepts in the theory

        Vector old_categorisations = theory.categorisations;

        theory.categorisations.removeAllElements();
        Vector changed_first_tuples = new Vector();
        Hashtable datatable_hashtable = new Hashtable();
        for (int conc_num=0;conc_num<conj_concepts.size();conc_num++)
        {
            Concept c = (Concept)conj_concepts.elementAt(conc_num);
            if (c.domain.equals(domain_concept.domain))
            {
                c.updateDatatable(conj_concepts, entity);
                String first_tuples = c.datatable.firstTuples();
                theory.addCategorisation(c);
                Vector same_first_tuple_concepts =
                        (Vector)datatable_hashtable.get(first_tuples);
                if (same_first_tuple_concepts!=null)
                    same_first_tuple_concepts.addElement(c);
                else
                {
                    same_first_tuple_concepts = new Vector();
                    same_first_tuple_concepts.addElement(c);
                    datatable_hashtable.put(first_tuples, same_first_tuple_concepts);
                }
            }
        }

        theory.categorisations = old_categorisations; //may be prob with cloning - check

        window.writeToFrontEnd("now however the conj is " + (this.reconstructConjecture(theory, window)).writeConjecture("ascii"));
        window.writeToFrontEnd("finished reconstructConjectureWithNewEntity");
        return this.reconstructConjecture(theory, window);

        //  Equivalence(Concept lh, Concept rh, String id_number)
        //Implication(Concept lh, Concept rh, String id_number)

        //NearEquivalence(Concept given_lh_concept, Concept given_rh_concept,
        //	 Vector given_counterexamples, double given_score)
        //NearImplication(Concept given_lh_concept, Concept given_rh_concept,
        //	 Vector given_counterexamples, double given_score)

        // NonExists(Concept c, String id_number)
        //Implicate(Concept concept, Specification rhs, Step construction_step)

        //return reconstructed_conjecture;
    }




    public boolean equals(Conjecture other_conj)
    {
        //if(this.equals(null) && other_conj.equals(null))
        // return true;

        //String conj_string = this.writeConjecture("ascii");
        //if(string.equals("null")&& conj_string.equals(""))
        //  {
        //	//System.out.println("returning true");
        //	return true;
        // }

        if((this.writeConjecture("ascii")).equals("") && (other_conj.writeConjecture("ascii").equals("")))
            return true;

        if (this instanceof Equivalence && other_conj instanceof Equivalence)
        {
            Equivalence this_equiv = (Equivalence)this;
            Equivalence other_equiv = (Equivalence)other_conj;

            if(!(this_equiv.lh_concept.writeDefinition("ascii").equals(other_equiv.lh_concept.writeDefinition("ascii"))))
                return false;
            if(!(this_equiv.rh_concept.writeDefinition("ascii").equals(other_equiv.rh_concept.writeDefinition("ascii"))))
                return false;

            return true;
        }

        if(this instanceof NearEquivalence && other_conj instanceof NearEquivalence)
        {
            NearEquivalence this_nequiv = (NearEquivalence)this;
            NearEquivalence other_nequiv = (NearEquivalence)other_conj;

            if(!(this_nequiv.lh_concept.writeDefinition("ascii").equals(other_nequiv.lh_concept.writeDefinition("ascii"))))
                return false;
            if(!(this_nequiv.rh_concept.writeDefinition("ascii").equals(other_nequiv.rh_concept.writeDefinition("ascii"))))
                return false;

            return true;
        }

        if(this instanceof NearImplication && other_conj instanceof NearImplication)
        {
            NearImplication this_nimp = (NearImplication)this;
            NearImplication other_nimp = (NearImplication)other_conj;

            if(!(this_nimp.lh_concept.writeDefinition("ascii").equals(other_nimp.lh_concept.writeDefinition("ascii"))))
                return false;
            if(!(this_nimp.rh_concept.writeDefinition("ascii").equals(other_nimp.rh_concept.writeDefinition("ascii"))))
                return false;

            return true;
        }

        if(this instanceof Implication && other_conj instanceof Implication)
        {
            Implication this_imp = (Implication)this;
            Implication other_imp = (Implication)other_conj;

            if(!(this_imp.lh_concept.writeDefinition("ascii").equals(other_imp.lh_concept.writeDefinition("ascii"))))
                return false;
            if(!(this_imp.rh_concept.writeDefinition("ascii").equals(other_imp.rh_concept.writeDefinition("ascii"))))
                return false;

            return true;
        }

        if (this instanceof Implicate && other_conj instanceof Implicate)
        {
            Implicate this_imp = (Implicate)this;
            Implicate other_imp = (Implicate)other_conj;

            if(!(this_imp.premise_concept.writeDefinition("ascii").equals(other_imp.premise_concept.writeDefinition("ascii"))))
                return false;
            if (!(this_imp.goal==other_imp.goal))
                return false;
            return true;
        }

        if (this instanceof NonExists && other_conj instanceof NonExists)
        {
            NonExists this_nexists = (NonExists)this;
            NonExists other_nexists = (NonExists)other_conj;

            if(!(this_nexists.concept.writeDefinition("ascii").equals(other_nexists.concept.writeDefinition("ascii"))))
                return false;
            return true;
        }
        return false;
    }



    /** return true if the string expression of the conjecture
     is blank, false otherwise [doesn't work] - alisonp*/
    public boolean equals(String string)
    {
        String conjecture_string = this.writeConjecture("ascii");
        if(string.equals("")&& conjecture_string.equals(""))
            return true;
        if(string.equals(conjecture_string)) //added dec 2006
            return true;
        else
            return false;
    }


    /** return true if the string expression of the conjecture
     is blank, false otherwise [doesn't work] - alisonp*/
    public boolean equals(Object object)
    {
        if(object instanceof String)
            return equals((String)object);
        if(object instanceof Conjecture)
            return equals((Conjecture)object);
        else
        {
            System.out.println("Warning: Conjecture equals method - not a conjecture or string");
            return false;
        }
    }


    /** Returns a vector of the one or two concepts in the conjecture */
    //not fully tested - works for equivs
    public Vector conceptsInConjecture()
    {
        Vector output = new Vector();
        Concept concept1 = new Concept();
        Concept concept2 = new Concept();

        if(this instanceof Equivalence)
        {
            Equivalence equiv = (Equivalence)this;
            concept1 = equiv.lh_concept;
            concept2 = equiv.rh_concept;
        }
        if(this instanceof NearEquivalence)
        {
            NearEquivalence nequiv = (NearEquivalence)this;
            concept1 = nequiv.lh_concept;
            concept2 = nequiv.rh_concept;
        }
        if(this instanceof Implication)
        {
            Implication imp = (Implication)this;
            concept1 = imp.lh_concept;
            concept2 = imp.rh_concept;
        }
        if(this instanceof NearImplication)
        {
            NearImplication nimp = (NearImplication)this;
            concept1 = nimp.lh_concept;
            concept2 = nimp.rh_concept;
        }
        if(this instanceof NonExists)
        {
            NonExists non_exists = (NonExists)this;
            concept1 = non_exists.concept;
        }
        if(this instanceof Implicate)
        {
            Implicate implicate = (Implicate)this;
            concept1 = implicate.premise_concept;
        }
        output.addElement(concept1);
        if(!((concept2.writeDefinition("ascii")).equals("")))//here
            output.addElement(concept2);

        return output;
    }

    /** Returns a vector of all the counterexamples (make sure they
     are entities not strings) */
    //needs work - only works on equiv conjs
    public Vector getCountersToConjecture()
    {
        Vector counterexamples = new Vector();
        // if equiv/near_equiv conj then we need to return *two* vectors of counters to lhs and to rhs

        if (this instanceof Equivalence)
            return ((Equivalence)this).knownCounterexamples();

        if (this instanceof Implication)//just added dec/06 -- needs testing
            return ((Implication)this).knownCounterexamples();//just added dec/06

        if (this instanceof NearEquivalence || this instanceof NearImplication)
            return this.counterexamples;
        if (this instanceof NonExists)//needs testing
        {
            NonExists ne_conj = (NonExists)this;
            counterexamples = ne_conj.concept.positiveEntities();
        }
        return counterexamples;
    }


    /** Returns true if the conjecture contains the concepts in the
     * vector, or if it contains descendents of the concepts in the
     * vector, and false otherwise. needs testing
     */

    public boolean relatesConcepts(Vector concepts, Theory theory)
    {

        //System.out.println("starting relatesConcepts -- got concepts: ");
        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            //System.out.println(concept.writeDefinition());
        }

        //System.out.println("and conjecture: " + this.writeConjecture());

        boolean so_far = true;
        Vector concepts_in_conj = this.conceptsInConjecture();


        for(int i=0;i<concepts_in_conj.size();i++)
        {
            Concept concept = (Concept)concepts_in_conj.elementAt(i);
            if(!concepts.contains(concept))
            {
                Vector ancestors_vector = concept.getAncestors(theory);
                boolean ancestor_in_concepts = false;
                for(int j=0;j<ancestors_vector.size();j++)
                {
                    Concept ancestor_concept = (Concept)ancestors_vector.elementAt(j);
                    if(concepts.contains(ancestor_concept))
                    {
                        ancestor_in_concepts = true;
                        break;
                    }
                }
                if(!ancestor_in_concepts)
                    return false;
            }

        }
        return so_far;
    }


    /** Returns true if the conjecture contains all of the concepts in
     * the vector, false otherwise. needs testing
     */

    public boolean relatesAllConcepts(Vector concepts, Theory theory)
    {

        //System.out.println("MONDAY - starting relatesAllConcepts -- got concepts: ");
        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            //System.out.println(concept.writeDefinition());
        }

        //System.out.println("and conjecture: " + this.writeConjecture());

        Vector concepts_in_conj = this.conceptsInConjecture();

        //write out
        //System.out.println("concepts_in_conj are: ");
        for(int i=0;i<concepts_in_conj.size();i++)
        {
            Concept concept = (Concept)concepts_in_conj.elementAt(i);
            //System.out.println(concept.writeDefinition());
        }

        //start here

        boolean ok_so_far = true;
        for(int i=0;i<concepts.size();i++)
        {
            Concept concept = (Concept)concepts.elementAt(i);
            //System.out.println("\n\ngot concept " + concept.writeDefinition());
            Vector letters = concept.definition_writer.lettersForTypes(concept.types,"ascii",new Vector());

            //System.out.println("letters for " + concept.writeDefinition() + " is " + letters);

            for(int j=0;j<concepts_in_conj.size();j++)
            {
                Concept conj_concept = (Concept)concepts_in_conj.elementAt(j);
                //System.out.println("conj_concept is " + conj_concept.writeDefinition());

                Vector letters_conj_concept = conj_concept.definition_writer.lettersForTypes(conj_concept.types,"ascii",new Vector());
                //System.out.println("letters_conj_concept is " + concept.writeDefinition() + " is " + letters);


                if(letters_conj_concept.size()==letters.size())
                {//System.out.println("1a -- ");

                    if(letters_conj_concept.equals(letters))
                    {
                        //System.out.println("1b -- ");
                        if(conj_concept.writeDefinition().equals(concept.writeDefinition()))
                        {
                            //System.out.println("1 -- " + conj_concept.writeDefinition() + " is the same as "
                            //	   + concept.writeDefinition());
                            break;
                        }

                        else
                        {
                            String concept_string = concept.writeDefinition();
                            String conjecture_string = this.writeConjecture();
                            boolean string_match = false;

                            //"hamburger".substring(4, 8) returns "urge"
                            if(conjecture_string.length() > concept_string.length())
                            {
                                ////System.out.println("MONDAY - concept_string is " + concept_string);
                                int cs = concept_string.length();
                                int n = conjecture_string.length()-concept_string.length();
                                for(int k=0;k<n;k++)
                                {
                                    String poss_string = conjecture_string.substring(k, k+cs);
                                    ////System.out.println("MONDAY - poss_string is \"" + poss_string + "\"");

                                    boolean b = poss_string.equals(concept_string);
                                    ////System.out.println("MONDAY - bool is " + b);

                                    if(poss_string.equals(concept_string))
                                    {
                                        string_match = true;
                                        break;
                                    }
                                }
                                if(!string_match)
                                    ok_so_far = false;



                                //System.out.println("1c -- ");

                            }
                        }
                    }
                    else
                    {
                        //Vector cloned_letters_conj_concept = letters_conj_concept.clone();
                        //System.out.println("1d -- ");
                        for(int k=0;k<letters.size();k++)
                        {
                            String s = (String)letters.elementAt(k);
                            letters_conj_concept.removeElementAt(k);
                            letters_conj_concept.insertElementAt(s, k);
                        }

                        //System.out.println("conj_concept is now " + conj_concept.writeDefinition());
                        if(conj_concept.writeDefinition().equals(concept.writeDefinition()))
                        {
                            //System.out.println("2 -- "+ conj_concept.writeDefinition() + " is the same as "
                            //	   + concept.writeDefinition());
                            break;
                        }
                        else
                            ok_so_far = false;

                    }
                }
                else
                    ok_so_far = false;



            }


            //System.out.println("ok_so_far is " + ok_so_far);
            if(!ok_so_far)
                break;
        }













//     //mon night - for each concept in concepts, see whether the
//     //conjecture statement contains that substring

//     for(int i=0;i<concepts.size();i++)
//       {
// 	Concept concept = (Concept)concepts.elementAt(i);

// 	Vector letters = concept.definition_writer.lettersForTypes(concept.types,"ascii",new Vector());
// 	//System.out.println("letters for " + concept.writeDefinition() + " is " + letters);

// 	String concept_string = concept.writeDefinition();
// 	String conjecture_string = this.writeConjecture();


// 	//"hamburger".substring(4, 8) returns "urge"
// 	if(conjecture_string.length() > concept_string.length())
// 	  {
// 	    //System.out.println("MONDAY - concept_string is " + concept_string);
// 	    int cs = concept_string.length();
// 	    int n = conjecture_string.length()-concept_string.length();
// 	    for(int j=0;j<n;j++)
// 	      {
// 		String poss_string = conjecture_string.substring(j, j+cs);
// 		//System.out.println("MONDAY - poss_string is \"" + poss_string + "\"");

// 		boolean b = poss_string.equals(concept_string);
// 		//System.out.println("MONDAY - bool is " + b);

// 		if(poss_string.equals(concept_string))
// 		  break;

// 	      }
// 	  }


//       }




//     Vector ancestors_of_concepts_in_conjecture = new Vector();
//     for(int i=0;i<concepts_in_conj.size();i++)
//       {
// 	Concept concept = (Concept)concepts_in_conj.elementAt(i);
// 	Vector ancestors_vector = concept.getAncestors(theory);
// 	//System.out.println("The ancestors_vector contains: ");
// 	for(int j=0;j<ancestors_vector.size();j++)
// 	  {
// 	    Concept anc_concept = (Concept)ancestors_vector.elementAt(j);
// 	    ancestors_of_concepts_in_conjecture.addElement(anc_concept);

// 	    Vector anc_letters = anc_concept.definition_writer.lettersForTypes(anc_concept.types,"ascii",new Vector());
// 	    //System.out.println("letters for `" + anc_concept.writeDefinition() + "' is " + anc_letters);

// 	    ////System.out.println(anc_concept.writeDefinition());
// 	  }

//       }

//     //here
//     for(int i=0;i<concepts.size();i++)
//       {
// 	Concept concept = (Concept)concepts.elementAt(i);
// 	//System.out.println("currently got the concept " + concept.writeDefinition());
// 	boolean b = concepts_in_conj.contains(concept);

// 	//System.out.println("concepts_in_conj.contains(concept) is " + b);
// 	if(!concepts_in_conj.contains(concept))
// 	  //return false;
// 	 {



// 	   // Vector ancestors_vector = concept.getAncestors(theory);
// 	   ////System.out.println("the ancestors_vector contains the concepts: ");
// 	   //for(int j=0;j<ancestors_vector.size();j++)
// 	   // {
// 	   //   Concept anc_concept = (Concept)ancestors_vector.elementAt(j);
// 	   //    //System.out.println(anc_concept.writeDefinition());
// 	   //  }



// 	   boolean b1 = ancestors_of_concepts_in_conjecture.contains(concept);
// 	   //System.out.println("ancestors_of_concepts_in_conjecture is " + b1);
// 	   if(!ancestors_of_concepts_in_conjecture.contains(concept))
// 	    return false;
// 	 }



//       }

        return ok_so_far;
    }


    /** returns true if the conj is already in the theory, false
     * otherwise */

    public boolean theoryContainsConjecture(Vector conjectures)
    {
        for(int i=0;i<conjectures.size();i++)
        {
            Conjecture conjecture = (Conjecture)conjectures.elementAt(i);
            if(this.writeConjecture().equals(conjecture.writeConjecture()))
                return true;
        }
        return false;
    }



    //may need testing
    public Conjecture reconstructConjecture_old(Theory theory, AgentWindow window)
    {
        String dummy_conj = (new Integer(theory.conjectures.size())).toString();

        //first of all go through the conjectures in the theory to see
        //whether already have the conjecture - if so, return it
        for(int i=0; i<theory.conjectures.size();i++)
        {
            Conjecture conj = (Conjecture)theory.conjectures.elementAt(i);
            if(this.writeConjecture("ascii").equals(conj.writeConjecture("ascii")))
                return conj;
        }

        TheoryConstituent tc1 = new TheoryConstituent();
        TheoryConstituent tc2 = new TheoryConstituent();

        Conjecture reconstructed_conjecture = new Conjecture();
        Vector poss_conjs = new Vector();
        if (this instanceof Implicate)
        {
            for (int i=0; i<theory.concepts.size(); i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);
                if(concept.writeDefinition("ascii").equals((((Implicate)this).premise_concept).writeDefinition("ascii")))
                    tc1 = concept;
            }
            if(tc1.equals("null"))//[check]
                tc1 = ((Implicate)this).premise_concept.reconstructConcept(theory, window);//might need casting
        }
        if (this instanceof NonExists)
        {
            for (int i=0; i<theory.concepts.size(); i++)
            {
                Concept current_concept = (Concept)theory.concepts.elementAt(i);
                if(current_concept.writeDefinition("ascii").equals((((NonExists)this).concept).writeDefinition("ascii")))
                    tc1 = current_concept; //can break after this
            }
            if(tc1.equals("null"))//[check]
                tc1 = ((NonExists)this).concept.reconstructConcept(theory, window);

            if(tc1 instanceof Concept)
            {
                Concept tc1_concept = (Concept)tc1;
                NonExists reconstructed_conj = new NonExists(tc1_concept, dummy_conj);
                reconstructed_conj.counterexamples = reconstructed_conj.getCountersToConjecture();
                return reconstructed_conj;
            }

            //here -- wed night (7th sept)
            if(tc1 instanceof Conjecture)
            {
                Conjecture tc1_conj = (Conjecture)tc1;
                if(tc1_conj instanceof Equivalence)
                {
                    Equivalence equiv = (Equivalence)tc1_conj;
                    Concept reconstructed_concept = (Concept)equiv.rh_concept;
                    NonExists reconstructed_conj = new NonExists(reconstructed_concept, dummy_conj);
                    reconstructed_conj.counterexamples = reconstructed_conj.getCountersToConjecture();
                    return reconstructed_conj;
                }

                if(tc1_conj instanceof Implication)//check if works for implication
                {
                    Implication imp = (Implication)tc1_conj;
                    Concept reconstructed_concept = (Concept)imp.rh_concept;
                    NonExists reconstructed_conj = new NonExists(reconstructed_concept, dummy_conj);
                    reconstructed_conj.counterexamples = reconstructed_conj.getCountersToConjecture();
                    return reconstructed_conj;
                }

            }

        }
        if (this instanceof Implication)
        {
            Concept left_concept = ((Implication)this).lh_concept;
            Concept right_concept = ((Implication)this).rh_concept;
            for (int i=0; i<theory.concepts.size(); i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);
                if(concept.writeDefinition("ascii").equals(left_concept.writeDefinition("ascii")))
                    tc1 = concept;
                if(concept.writeDefinition("ascii").equals(right_concept.writeDefinition("ascii")))
                    tc2 = concept;
            }

            if(tc1.equals("null"))//[check]
                tc1 = left_concept.reconstructConcept(theory, window);
            if(tc2.equals("null"))
                tc2 = right_concept.reconstructConcept(theory, window);

        }
        if (this instanceof Equivalence)
        {
            Concept left_concept = ((Equivalence)this).lh_concept;
            System.out.println(" left_concept ancestors are: " + left_concept.construction_history);
            Concept right_concept = ((Equivalence)this).rh_concept;
            System.out.println(" right_concept ancestors are: " + right_concept.construction_history);
            System.out.println("trying to reconstruct an equiv");
            System.out.println("left_concept is " + left_concept.writeDefinition("ascii"));
            System.out.println("ESP INTERESTED IN THIS ONE: right_concept is " + right_concept.writeDefinition("ascii"));
            System.out.println("looking to see whether got them already...");


            //window.writeToFrontEnd("trying to reconstruct an equiv");
            //window.writeToFrontEnd("left_concept is " + left_concept.writeDefinition("ascii"));
            //window.writeToFrontEnd("ESP INTERESTED IN THIS ONE: right_concept is " + right_concept.writeDefinition("ascii"));
            //window.writeToFrontEnd("looking to see whether got them already...");

            for (int i=0; i<theory.concepts.size(); i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);
                System.out.println("current concept is " + concept.writeDefinition("ascii"));
                //window.writeToFrontEnd("current concept is " + concept.writeDefinition("ascii"));
                if(concept.writeDefinition("ascii").equals(left_concept.writeDefinition("ascii")))
                {
                    System.out.println("this is same as the left concept, so setting tc1 to it");
                    //window.writeToFrontEnd("this is same as the left concept, so setting tc1 to it");
                    tc1 = concept;
                    if(tc1.equals("null"))
                        System.out.println("tc1 is still null (odd...)");
                    else
                        System.out.println("tc1 isn't null now");
                }
                if(concept.writeDefinition("ascii").equals(right_concept.writeDefinition("ascii")))
                {
                    //window.writeToFrontEnd("this is same as the right concept, so setting tc2 to it");
                    System.out.println("we'd expect this to be true at some point...");
                    System.out.println("this is same as the right concept, so setting tc2 to it");
                    tc2 = concept;
                    if(tc2.equals("null"))
                        System.out.println("tc2 is still null (odd...)");
                    else
                        System.out.println("tc2 isn't null now");
                }
            }
            boolean b1 = tc1.equals("null");
            boolean b2 = tc2.equals("null");
            if(b1)//[check]
            {
                //window.writeToFrontEnd("tc1 is null - going to reconstruct this concept: " + left_concept.writeDefinition());
                tc1 = left_concept.reconstructConcept(theory, window);

                //window.writeToFrontEnd("have reconstructed it as: " + tc1.writeTheoryConstituent());
            }
            if(b2)
            {
                //window.writeToFrontEnd("tc2 is null - going to reconstruct this concept: " + right_concept.writeDefinition());
                tc2 = right_concept.reconstructConcept(theory, window);

                //window.writeToFrontEnd("have reconstructed it as: " + tc2.writeTheoryConstituent());
            }
            else
            {
                //window.writeToFrontEnd("got tc1: " + tc1.writeTheoryConstituent() + " and tc2: " + tc2.writeTheoryConstituent());
            }

            //test
            if(!b1 && !b2)
            {
                if(tc1 instanceof Concept && tc2 instanceof Concept)
                {
                    System.out.println(" left_concept tc1 ancestors are: " + ((Concept)tc1).construction_history);
                    System.out.println(" right_concept tc1 ancestors are: " + ((Concept)tc2).construction_history);
                }
                Equivalence new_equiv = new Equivalence((Concept)tc1,(Concept)tc2,"dummy");
                System.out.println("new_equiv is " + new_equiv.writeConjecture());
                //window.writeToFrontEnd("new_equiv is " + new_equiv.writeConjecture());
            }
        }
        if (this instanceof NearEquivalence)
        {
            Concept left_concept = ((NearEquivalence)this).lh_concept;
            Concept right_concept = ((NearEquivalence)this).rh_concept;

            for (int i=0; i<theory.concepts.size(); i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);
                if(concept.writeDefinition("ascii").equals(left_concept.writeDefinition("ascii")))
                    tc1 = concept;
                if(concept.writeDefinition("ascii").equals(right_concept.writeDefinition("ascii")))
                    tc2 = concept;
            }
            if(tc1.equals("null"))//[check]
            {
                ////System.out.println("got a n_equiv - reconstructing the lh_concept: " + ((NearEquivalence)this).lh_concept.writeDefinition("ascii"));
                System.out.println(" left_concept tc1 ancestors are: " + ((Concept)tc1).construction_history);
                tc1 = left_concept.reconstructConcept(theory, window);
                System.out.println(" left_concept tc1 ancestors are: " + ((Concept)tc1).construction_history);
                ////System.out.println("finished reconstructing the lh_concept");

            }
            if(tc2.equals("null"))
            {
                ////System.out.println("reconstructing the rh_concept: " + ((NearEquivalence)this).rh_concept.writeDefinition("ascii"));
                System.out.println(" right_concept tc1 ancestors are: " + ((Concept)tc2).construction_history);
                tc2 = right_concept.reconstructConcept(theory, window);
                System.out.println(" right_concept tc1 ancestors are: " + ((Concept)tc2).construction_history);
                // //System.out.println("finished reconstructing the rh_concept");

            }
        }
        if (this instanceof NearImplication)
        {
            Concept left_concept = ((NearImplication)this).lh_concept;
            Concept right_concept = ((NearImplication)this).rh_concept;

            for (int i=0; i<theory.concepts.size(); i++)
            {
                Concept concept = (Concept)theory.concepts.elementAt(i);
                if(concept.writeDefinition("ascii").equals(left_concept.writeDefinition("ascii")))
                    tc1 = concept;
                if(concept.writeDefinition("ascii").equals(right_concept.writeDefinition("ascii")))
                    tc2 = concept;
            }
            if(tc1.equals("null"))//[check]
            {
                ////System.out.println("got a n_imp - reconstructing the lh_concept: " + left_concept.writeDefinition("ascii"));
                tc1 = left_concept.reconstructConcept(theory, window);
                ////System.out.println("finished reconstructing the lh_concept");
            }
            if(tc2.equals("null"))
            {
                ////System.out.println("reconstructing the rh_concept: " + right_concept.writeDefinition("ascii"));
                tc2 = right_concept.reconstructConcept(theory, window);
                ////System.out.println("finished reconstructing the rh_concept");
            }
        }


        //now goes through all the conjectures in the theory to see
        //whether the construction of the new concepts has resulted in new
        //conjectures being formed

        if (tc1 instanceof Conjecture)
        {
            ////System.out.println("tc2 is a conj - it's " + ((Conjecture)tc2).writeConjecture("ascii"));
            return reconstructed_conjecture = (Conjecture)tc1;
        }


        //messy - need to change by not casting output of forceConcept to
        //a concept - start here on friday

        if (tc2 instanceof Conjecture)
        {
            ////System.out.println("tc2 is a conj - it's " + ((Conjecture)tc2).writeConjecture("ascii"));
            return reconstructed_conjecture = (Conjecture)tc2;
        }

        if(tc1 instanceof Concept && tc2 instanceof Concept)
        {
            Concept tc1_concept = (Concept)tc1;
            Concept tc2_concept = (Concept)tc2;

            System.out.println(" tc1_concept ancestors are: " + tc1_concept.construction_history);
            System.out.println(" tc2_concept ancestors are: " + tc2_concept.construction_history);


            //System.out.println("tc1_concept is " + tc1_concept.writeDefinition());
            //System.out.println("tc2_concept is " + tc2_concept.writeDefinition());

            ////System.out.println("tc1_concept is " + tc1_concept.writeDefinition("ascii"));
            ////System.out.println("lh egs are: " + tc1_concept.datatable.toTable());

            ////System.out.println("tc2_concept is " + tc2_concept.writeDefinition("ascii"));
            ////System.out.println("lh egs are: " + tc2_concept.datatable.toTable());
            ////System.out.println("looking for a conj");


            poss_conjs = tc1_concept.getConjectures(theory, tc2_concept); //is the method symmetrical?
            //System.out.println("poss_conjs is ");
            for(int i=0;i<poss_conjs.size();i++)
            {
                //System.out.println(((Conjecture)poss_conjs.elementAt(i)).writeConjecture());
            }
            //find the right reconstructed conj (i.e. that as close to
            //conj_type as possible and with the lh/rh_concepts the same way
            //around). Any conjecture on poss_conjs has the same concepts as
            //those in the conjecture we want to reconstruct, so no check
            //needed. [needs thinking about] -- esp in marked lines

            for(int i=0; i<poss_conjs.size();i++)
            {
                //  //System.out.println("looking in poss_conjs");
                Conjecture poss_conj = (Conjecture)poss_conjs.elementAt(i);
                if(this instanceof Implicate && poss_conj instanceof Implicate)
                    return (Implicate)poss_conj;//or break

                if(this instanceof NonExists && poss_conj instanceof NonExists)
                    return (NonExists)poss_conj;

                if(this instanceof Equivalence && poss_conj instanceof Equivalence)
                    return (Equivalence)poss_conj;

                if(this instanceof Equivalence && poss_conj instanceof NearEquivalence) //here - extend to other cases??
                {
                    NearEquivalence neq = (NearEquivalence)poss_conj;
                    if(((Equivalence)this).lh_concept.writeDefinition("ascii").equals(neq.lh_concept.writeDefinition("ascii")))
                    {
                        if(neq.counterexamples.isEmpty())
                            reconstructed_conjecture = new Equivalence(neq.lh_concept, neq.rh_concept,dummy_conj);
                        else
                            return (NearEquivalence)poss_conj;
                    }
                }

                if(this instanceof NearEquivalence && poss_conj instanceof Equivalence)
                    return (Equivalence)poss_conj;

                if(this instanceof NearEquivalence && poss_conj instanceof NearEquivalence)
                    return (NearEquivalence)poss_conj;


                if(this instanceof Implication && poss_conj instanceof Implication)
                {
                    Implication imp = (Implication)poss_conj;
                    if(((Implication)this).lh_concept.writeDefinition("ascii").equals(imp.lh_concept.writeDefinition("ascii")))
                        return imp;
                }

                if(this instanceof NearImplication && poss_conj instanceof Implication)
                {
                    Implication imp = (Implication)poss_conj;
                    if(((NearImplication)this).lh_concept.writeDefinition("ascii").equals(imp.lh_concept.writeDefinition("ascii")))
                        return imp;
                }

                if(this instanceof Implication && poss_conj instanceof NearImplication)
                {
                    ////System.out.println("was passed an implication - but have reconconstructed it an a nimp");
                    NearImplication nimp = (NearImplication)poss_conj;
                    if(((Implication)this).lh_concept.writeDefinition("ascii").equals(nimp.lh_concept.writeDefinition("ascii")))
                        reconstructed_conjecture = nimp;
                }

                if(this instanceof Implication && poss_conj instanceof NearEquivalence)//just added (oct04)- needs testign
                {
                    ////System.out.println("was passed an implication - but have reconconstructed it an a neq");
                    NearEquivalence neq = (NearEquivalence)poss_conj;
                    if(((Implication)this).lh_concept.writeDefinition("ascii").equals(neq.lh_concept.writeDefinition("ascii")))
                    {//testing here
                        if(neq.counterexamples.isEmpty())
                            reconstructed_conjecture = new Implication(neq.lh_concept, neq.rh_concept,dummy_conj);
                        else
                            reconstructed_conjecture = neq;
                    }
                }


                if(this instanceof NearImplication && poss_conj instanceof NearImplication)
                {
                    NearImplication nimp = (NearImplication)poss_conj;
                    if(((NearImplication)this).lh_concept.writeDefinition("ascii").equals(nimp.lh_concept.writeDefinition("ascii")))
                        reconstructed_conjecture = nimp;
                }
            }

            //[need to pur back in ]
            //   if(reconstructed_conjecture.equals(null) && !(poss_conjs.isEmpty()))
            // reconstructed_conjecture = (Conjecture)poss_conjs.elementAt(0);

            ////System.out.println("returning reconstructed_conjecture is " + reconstructed_conjecture.writeConjecture("ascii"));
            return reconstructed_conjecture;
        }
        return reconstructed_conjecture;
    }

    /** Returns a string with relevant information about the
     * conjecture. The interestingness measures are printed to 2 decimal
     * places.
     */

    public String printConjectureInformation(MeasureConjecture measure_conjecture)
    {
        int dp = 2;
        String output = "\n\n" + this.id + ": " + this.writeConjecture();
        output = output +  "\nThis conjecture discusses these entities: ";
        if(this instanceof Equivalence)
        {
            Equivalence equivalence  = (Equivalence)this;
            output = output
                    +  measure_conjecture.removeDuplicates(equivalence.lh_concept.getPositives(),equivalence.rh_concept.getPositives());
        }

        if(this instanceof NearEquivalence)
        {
            NearEquivalence near_equivalence  = (NearEquivalence)this;
            output = output
                    +  measure_conjecture.removeDuplicates(near_equivalence.lh_concept.getPositives(),near_equivalence.rh_concept.getPositives());
        }
        if(this instanceof Implication)
        {
            Implication implication  = (Implication)this;
            output = output + implication.lh_concept.getPositives();
        }


        if(this instanceof NearImplication)
        {
            NearImplication near_implication  = (NearImplication)this;
            output = output + near_implication.lh_concept.getPositives();
        }

        if(this instanceof NonExists)
        {
            NonExists non_exists  = (NonExists)this;
            output = output + non_exists.concept.getPositives();
        }

        output = output +  "\nThis has negatives " + this.counterexamples;
        double plaus_times_app = this.plausibility*this.applicability;

        output = output
                + "\n It has arity " + this.arity
                + "\n It has applicability " + this.decimalPlaces(this.applicability, dp)
                + "\n It has comprehensibility " + this.decimalPlaces(this.comprehensibility, dp)
                + "\n It has surprisingness " + this.decimalPlaces(this.surprisingness, dp)
                + "\n It has plausibility " + this.decimalPlaces(this.plausibility, dp)
                + "\n It has plausibility*applicability " + this.decimalPlaces(plaus_times_app, dp)
                + "\n It has interestingness " + this.decimalPlaces(this.interestingness, dp)
                + "\n It was found by the lakatos method: " + this.lakatos_method;

        return output;
    }
}
