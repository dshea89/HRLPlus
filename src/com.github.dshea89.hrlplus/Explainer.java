package com.github.dshea89.hrlplus;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.String;
import java.awt.Label;
import java.io.*;

/** A class for the explainers (which aim to prove or disprove a conjecture given
 * to them).
 *
 * @author Simon Colton, started 19th December 2000
 * @version 1.0 */

public class Explainer implements Serializable
{
    /** The setup name for this explainer.
     */

    public String setup_name = "";

    /** The condition of a conjecture (as a string) which must be passed if this
     * explainer is going to be used to try to explain the conjecture.
     */

    public String condition_string = "";

    /** The axioms strings for this explainer.
     */

    public Vector axiom_strings = new Vector();

    /** The operating system being used.
     */

    public String operating_system = "";

    /** The name of this explainer.
     */

    public String name = "";

    /** The hashtable of execution parameters (time limits, models required, etc.)
     * for this explainer.
     */

    public Hashtable execution_parameters = new Hashtable();

    /** When this explainer will be tried (in order) to explain a conjecture.
     */

    public int try_pos = 0;

    /** The directory where the batch files to call the external process are kept.
     */

    String input_files_directory = "";

    /** The set of conjectures which are to be taken as axioms.
     */

    public Vector axiom_conjectures = new Vector();

    /** Whether or not to use ground instances (e.g. isprime(2)) as axioms.
     * (can be useful in number theory, for example).
     */

    public boolean use_ground_instances = false;

    /** The ground instances as a string (for adding in as axioms if required).
     */

    public String ground_instances_as_axioms = "";

    /** Whether or not to include the entity letter in the conjecture statement
     * (needed for number theory, but not for group theory).
     */

    public boolean use_entity_letter = false;

    /** The time limit in seconds for this process.
     */

    public int time_limit = 10;

    /** The MathWeb object for this external process.
     * @see MathWebHandler
     */

    public MathWebHandler mathweb_handler = null;

    /** Whether or not to use the mathweb (if one is available).
     */

    public boolean use_mathweb = false;

    /** The name of the external service which the socket will use.
     * (in mathweb).
     */

    public String mathweb_service_name = "";

    /** This writes out the axioms.
     */

    public String writeAxioms()
    {
        String output = "";
        for (int i=0; i<axiom_strings.size(); i++)
        {
            String ax_string = (String)axiom_strings.elementAt(i);
            if (ax_string.indexOf("op")!=0)
                output = output + ax_string + "\n";
        }
        return output;
    }

    /** This writes out the operators.
     */

    public String writeOperators()
    {
        String output = "";
        for (int i=0; i<axiom_strings.size(); i++)
        {
            String ax_string = (String)axiom_strings.elementAt(i);
            if (ax_string.indexOf("op")==0)
                output = output + ax_string + "\n";
        }
        return output;
    }

    /** This returns the execution parameter required as a string.
     */

    public String executionParameter(String key)
    {
        return (String)execution_parameters.get(key);
    }
}
