package com.github.dshea89.hrlplus;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Hashtable;
import java.util.Vector;

public class Student extends Agent implements Serializable {
    AgentWindow window = new AgentWindow();
    String student_name = null;
    public int port;
    InetAddress teacher_hostname;
    int teacher_port = 8500;
    int num_independent_work_stages_so_far = 1;
    int num_loops_so_far = 1;
    boolean test_react_to = false;
    boolean hyp_mode = false;

    public Student() {
    }

    public Student(String var1, String var2, InetAddress var3, int var4) {
        this.name = var2;
        this.student_name = "student: " + this.name;
        this.initialiseHR(var1, this.student_name);
        this.initialiseStudent(var1, var2, var3, var4);
    }

    public Student(String var1, String var2, String var3, InetAddress var4, int var5) {
        this.name = var2;
        this.student_name = "student: " + this.name;
        this.initialiseHR(var1, this.student_name, var3);
        this.initialiseStudent(var1, this.name, var4, var5);
    }

    public void initialiseStudent(String var1, String var2, InetAddress var3, int var4) {
        this.window.setTitle(this.student_name);
        this.window.port = var4;
        this.teacher_hostname = var3;
        this.discussion_vector = this.discussion_vector;
        this.port = var4;
        this.server_socket = this.server_socket;

        while(this.server_socket == null) {
            try {
                this.server_socket = new ServerSocket(var4);
            } catch (BindException var6) {
                var4 += 20;
            } catch (IOException var7) {
                this.window.writeToFrontEnd("IOException: can't make ServerSocker" + var7);
            }
        }

        this.window.writeToFrontEnd("my name is: " + this.name);
        this.window.writeToFrontEnd("my port is " + var4);
        this.window.writeToFrontEnd("teacher_hostname is: " + var3);
        this.window.writeToFrontEnd("teacher port is: " + this.teacher_port);
        this.window.drawLine();
    }

    public void run() {
        while(this.num_independent_work_stages_so_far < this.hr.theory.lakatos.max_num_independent_work_stages) {
            this.window.writeQuietlyToFrontEnd("just started loop: " + this.num_loops_so_far);
            this.listenForCall(this.server_socket);
            if (this.current_request.string_object.equals("work independently")) {
                int var1 = this.current_request.num_steps;
                this.window.writeToFrontEnd("IWP " + this.num_independent_work_stages_so_far + " (" + var1 + " steps)");
                this.hr.theory.front_end.start_button.setEnabled(false);
                this.hr.theory.front_end.stop_button.setEnabled(true);
                this.hr.theory.front_end.step_button.setEnabled(false);
                this.hr.theory.stop_now = false;
                this.hr.theory.use_front_end = true;
                this.hr.theory.getFrontEndSettings();
                int var2 = new Integer(this.hr.theory.front_end.required_text.getText());
                this.hr.theory.formTheoryUntil(var2, this.hr.theory.front_end.required_choice.getSelectedItem());
                boolean var3 = false;

                while(!var3) {
                    if (this.hr.theory.step_counter > var1) {
                        var3 = true;
                        this.hr.theory.stop_now = true;
                    }
                }

                this.hr.theory.macro_to_complete = "";
                this.hr.theory.front_end.macro_text.select(0, 0);
                this.hr.theory.front_end.start_button.setLabel("Continue");
                this.hr.theory.front_end.stop_button.setLabel("Kill");
                this.hr.theory.step_counter = 0;
                ++this.num_independent_work_stages_so_far;
                if (this.hr.theory.teacher_requests_nonexists) {
                    this.current_request.type = "NonExists";
                }

                if (this.hr.theory.teacher_requests_implication) {
                    this.current_request.type = "Implication";
                }

                if (this.hr.theory.teacher_requests_nearimplication) {
                    this.current_request.type = "NearImplication";
                }

                if (this.hr.theory.teacher_requests_equivalence) {
                    this.current_request.type = "Equivalence";
                }

                if (this.hr.theory.teacher_requests_nearequivalence) {
                    this.current_request.type = "NearEquivalence";
                }

                this.window.writeQuietlyToFrontEnd("current_request.type is " + this.current_request.type);
                this.window.writeToFrontEnd("I have entities " + this.hr.theory.entities);
                this.listenForCall(this.server_socket);
            }

            if (this.personal_discussion_vector.isEmpty()) {
                this.window.writeQuietlyToFrontEnd("    **    personal_discussion_vector.isEmpty()    **");
                this.listenForCall(this.server_socket);
            } else if (this.personal_discussion_vector.lastElement() instanceof Request && this.current_request != null) {
                this.current_request = (Request)this.personal_discussion_vector.lastElement();
                this.window.writeToFrontEnd("The current request is " + this.current_request);
                Response var4 = this.respondToRequest(this.current_request);
                this.makeCall(this.port, this.teacher_hostname, this.teacher_port, var4);
                this.listenForCall(this.server_socket);
                this.respondToResponses(this.current_responses);
                this.current_responses.clear();
            } else if (this.personal_discussion_vector.lastElement() instanceof Vector) {
                this.window.writeQuietlyToFrontEnd("    **    LAST ELEMENT OF PDV IS Vector    **");
                this.listenForCall(this.server_socket);
            }

            ++this.num_loops_so_far;
            this.window.drawLine();
        }

        this.window.writeToFrontEnd("I've done " + this.num_independent_work_stages_so_far + " independent work stages now, so I'm stopping.");
        this.writeVariableInformation();
        String var5 = this.hr.theory.theoryReport();
        this.window.writeToFrontEnd("My theory report follows. \n\n" + var5);
        System.out.println("Hi, I'm " + this.port + ", and  my theoryReport is: \n" + var5);

        // Mission complete, so reset the student
        HR var7 = new HR();
        var7.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent var1) {
                System.exit(0);
            }
        });
        for(int var9 = 0; var9 < var7.theory.front_end.predict_names_buttons_vector.size(); ++var9) {
            ((Button)var7.theory.front_end.predict_names_buttons_vector.elementAt(var9)).addActionListener(var7.theory);
        }
        var7.config_handler = this.hr.config_handler;
        var7.config_handler.initialiseHR(var7);
        var7.theory.passOnSettings();
        var7.theory.front_end.init();
        var7.setVisible(true);
        this.hr.dispose();
    }

    public Vector getConjecturesFromDiscussionVector() {
        Vector var1 = new Vector();

        for(int var2 = 0; var2 < this.personal_discussion_vector.size(); ++var2) {
            if (!this.personal_discussion_vector.isEmpty() && this.personal_discussion_vector.elementAt(var2) instanceof Response) {
                Response var3 = (Response)this.personal_discussion_vector.elementAt(var2);

                for(int var4 = 0; var4 < var3.response_vector.size(); ++var4) {
                    if (var3.response_vector.elementAt(var4) instanceof Conjecture) {
                        var1.addElement((Conjecture)var3.response_vector.elementAt(var4));
                    }
                }
            }
        }

        return var1;
    }

    public Request getCurrentRequestfromDiscussionVector() {
        for(int var1 = this.discussion_vector.size() - 1; var1 >= 0; --var1) {
            if (this.discussion_vector.elementAt(var1) instanceof Request) {
                this.current_request = (Request)this.discussion_vector.elementAt(var1);
                break;
            }
        }

        return this.current_request;
    }

    public Response respondToRequest(Request var1) {
        boolean var2 = var1.theory_constituent_vector.isEmpty();
        this.window.writeQuietlyToFrontEnd("current_request.theory_constituent_vector.isEmpty() is " + var2);
        this.window.writeQuietlyToFrontEnd("current_request.type is " + var1.type);
        if (!var2) {
            boolean var3 = var1.theory_constituent_vector.elementAt(0) instanceof Entity;
            this.window.writeQuietlyToFrontEnd("current_request.theory_constituent_vector.elementAt(0) instanceof Entity is " + var3);
        }

        Vector var31 = new Vector();
        Response var4 = new Response(var31, var1);
        boolean var5 = var1.condition.equals("null");
        boolean var6 = var1.theory_constituent_vector.isEmpty();
        this.window.writeQuietlyToFrontEnd("crc_null is " + var5);
        this.window.writeQuietlyToFrontEnd("crtc_empty is " + var6);
        Vector var7;
        if (var1.condition.equals("null") && var1.theory_constituent_vector.isEmpty()) {
            if (var1.type.equals("Concept")) {
                var4.response_vector = this.getNBestConcepts(var1.num);
            } else {
                var7 = this.getConjecturesFromDiscussionVector();
                if (var1.type.equals("Conjecture")) {
                    var4.response_vector = this.getNBestConjectures("Conjecture", var1.num, var7);
                }

                if (var1.type.equals("NearEquivalence")) {
                    var4.response_vector = this.getNBestConjectures("NearEquivalence", var1.num, var7);
                }

                if (var1.type.equals("NearImplication")) {
                    var4.response_vector = this.getNBestConjectures("NearImplication", var1.num, var7);
                }

                if (var1.type.equals("Equivalence")) {
                    var4.response_vector = this.getNBestConjectures("Equivalence", var1.num, var7);
                }

                if (var1.type.equals("Implication")) {
                    var4.response_vector = this.getNBestConjectures("Implication", var1.num, var7);
                }

                if (var1.type.equals("NonExists")) {
                    var4.response_vector = this.getNBestConjectures("NonExists", var1.num, var7);
                }

                if (var1.type.equals("Implicate")) {
                    var4.response_vector = this.getNBestConjectures("Implicate", var1.num, var7);
                }

                this.window.writeToFrontEnd("my response vector is ");
                this.window.writeToFrontEnd(var4.response_vector);
            }
        }

        if (var1.string_object.equals("perform addEntityToTheory")) {
            this.window.writeQuietlyToFrontEnd("***************************************************");
            this.window.writeQuietlyToFrontEnd("HEY HEY HEY");
            this.window.writeToFrontEnd("told to perform addEntityToTheory");
            this.hr.theory.addEntityToTheory(var1.theory_constituent_vector, this.hr.theory, this.window);

            for(int var38 = 0; var38 < var1.theory_constituent_vector.size(); ++var38) {
                Entity var41 = (Entity)var1.theory_constituent_vector.elementAt(var38);
                var41.lakatos_method = "highlighted by dialogue";
            }

            var4.response_vector = new Vector();
            var4.request = new Request();
            this.group_file.writeToGroupFile("Student " + this.port + ": OK - my entities are now " + this.hr.theory.entities);
            return var4;
        } else if (var1.string_object.equals("perform downgradeEntityToPseudoEntity")) {
            this.window.writeToFrontEnd("told to perform downgradeEntityToPseudoEntity");
            boolean var37 = var1.motivation.entity_under_discussion.entitiesContains(this.hr.theory.entities);
            this.window.writeToFrontEnd("current_request.motivation.entity_under_discussion.entitiesContains(hr.theory.entities) is " + var37);
            if (var37) {
                this.window.writeToFrontEnd("in herererer ");
                this.window.writeToFrontEnd("right now, the entities in my theory are " + this.hr.theory.entities);
                this.hr.theory.downgradeEntityToPseudoEntity(var1.motivation.entity_under_discussion, this.window);
                var1.motivation.entity_under_discussion.lakatos_method = "monster-barring";
                this.window.writeToFrontEnd("NOW however, the entities in my theory are " + this.hr.theory.entities);
            } else {
                this.hr.theory.upgradeEntityToPseudoEntity(var1.motivation.entity_under_discussion);
            }

            var4.response_vector = new Vector();
            var4.request = new Request();
            this.group_file.writeToGroupFile("Student " + this.port + ": OK - my pseudo entities are now " + this.hr.theory.pseudo_entities);
            return var4;
        } else {
            Conjecture var8;
            double var10;
            Conjecture var32;
            if (var1.type.equals("Entity") && var1.condition.equals("breaks") && var1.theory_constituent_vector.elementAt(0) instanceof Conjecture) {
                var32 = (Conjecture)var1.theory_constituent_vector.elementAt(0);
                this.window.writeQuietlyToFrontEnd("looking for entities to break " + var32.writeConjecture("ascii"));
                var8 = var32.reconstructConjecture(this.hr.theory, this.window);
                this.window.writeQuietlyToFrontEnd("reconstructed_conj is " + var8.writeConjecture());
                if (var8 instanceof Implication) {
                    Implication var9 = (Implication)var8;
                    var10 = var9.lh_concept.datatable.percentageMatchWith(var9.rh_concept.datatable);
                    this.window.writeQuietlyToFrontEnd("percentageMatchWith returns " + var10);
                }

                this.hr.theory.addConjectureToTheory(var8);
                this.window.writeQuietlyToFrontEnd("looking for counters");
                var4.response_vector = var8.getCountersToConjecture();
                this.window.writeToFrontEnd("counters are " + var4.response_vector);
            }

            Vector var39;
            int var44;
            if (var1.type.equals("Concept") && var1.condition.equals("covers") && var1.theory_constituent_vector.elementAt(0) instanceof Entity) {
                var7 = var1.theory_constituent_vector;
                Vector var34 = this.hr.theory.lakatos.fromEntitiesToStrings(var7);
                var39 = new Vector();
                var39.addElement(this.hr.theory.lakatos.getConceptWhichExactlyCoversGivenEntities(var34));

                for(var44 = 0; var44 < var39.size(); ++var44) {
                    Concept var11 = (Concept)var39.elementAt(var44);
                    var11.lakatos_method = "communal_piecemeal_exclusion";
                }

                var4.response_vector = var39;
            }

            String var12;
            boolean var43;
            Conjecture var50;
            if (var1.motivation.attempted_method.equals("monster-barring") && var1.motivation.entity_under_discussion != null) {
                Entity var33 = var1.motivation.entity_under_discussion;
                boolean var35 = this.hr.theory.lakatos.use_breaks_conj_under_discussion;
                String var45;
                if (var35) {
                    Conjecture var42 = var1.motivation.conjecture_under_discussion;
                    if (var42.counterexamples.isEmpty()) {
                        var45 = "accept proposal to bar entity";
                        var4.response_vector.addElement(var45);
                    }

                    if (!var42.counterexamples.isEmpty()) {
                        var45 = "reject proposal to bar entity";
                        var4.response_vector.addElement(var45);
                    }
                }

                var43 = this.hr.theory.lakatos.accept_strictest;
                if (var43) {
                    var45 = "accept proposal to bar entity";
                    var4.response_vector.addElement(var45);
                } else {
                    var10 = this.hr.theory.lakatos.percentageConjecturesBrokenByEntity(this.hr.theory, var33, this.window);
                    this.window.writeToFrontEnd(var33.name + " is breaking " + var10 + " % of my conjs");
                    if (var10 > this.hr.theory.lakatos.monster_barring_min) {
                        this.window.writeToFrontEnd("going to accept proposal to bar entity");
                        var12 = "accept proposal to bar entity";
                        var4.response_vector.addElement(var12);
                    } else {
                        this.window.writeToFrontEnd("going to reject proposal to bar entity");
                        if (var1.theory_constituent_vector.isEmpty()) {
                            var12 = "reject proposal to bar entity";
                            var4.response_vector.addElement(var12);
                        } else {
                            var50 = var1.motivation.conjecture_under_discussion;
                            Concept var13 = new Concept();

                            for(int var14 = 0; var14 < this.hr.theory.concepts.size(); ++var14) {
                                var13 = (Concept)this.hr.theory.concepts.elementAt(var14);
                                if (var13.is_object_of_interest_concept) {
                                    break;
                                }
                            }

                            Concept var60 = this.hr.theory.lakatos.getSpecificFromVagueDefinition(var33, true, var13.domain, this.hr.theory, var50, this.window);
                            var60.lakatos_method = "monster-barring";
                            ++var50.num_modifications;
                            var50.lakatos_method = "monster-barring";
                            this.window.writeToFrontEnd("-- because I think that a " + var13.domain + " is something which satisfies " + var60.writeDefinition());
                            String var15 = "reject proposal to bar entity";
                            var4.response_vector.addElement(var15);
                            var4.response_vector.addElement(var60);
                        }
                    }
                }
            }

            String var46;
            if (var1.type.equals("Conjecture") && var1.condition.equals("modifies") && var1.theory_constituent_vector.elementAt(0) instanceof Conjecture) {
                this.window.writeQuietlyToFrontEnd("hr.theory.use_surrender is " + this.hr.theory.use_surrender + ", hr.theory.use_strategic_withdrawal is " + this.hr.theory.use_strategic_withdrawal + ", hr.theory.use_piecemeal_exclusion is " + this.hr.theory.use_piecemeal_exclusion);
                var32 = (Conjecture)var1.theory_constituent_vector.elementAt(0);
                var8 = var32.reconstructConjecture(this.hr.theory, this.window);
                if (!(var8 instanceof NonExists)) {
                    System.out.println("adding to theory");
                    this.hr.theory.addConjectureToTheory(var8);
                }

                if (!var8.writeConjecture().equals(var32.writeConjecture())) {
                    this.window.writeToFrontEnd("I've reconstructed the conjecture as " + var8.writeConjecture() + ", which is slightly different. Now I'm going to try to modify this.");
                }

                if (var8.getCountersToConjecture().isEmpty()) {
                    new String();
                    var46 = var8.writeConjecture() + " holds for all my examples";
                    var4.response_vector.addElement(var46);
                    this.group_file.writeToGroupFile("Student " + this.port + ": " + var4.toStringForFile());
                    return var4;
                }

                if (this.hr.theory.use_surrender) {
                    this.window.writeToFrontEnd(" ** might surrender on " + var8.writeConjecture());
                    var43 = this.hr.theory.lakatos.test_number_modifications_surrender;
                    var44 = this.hr.theory.lakatos.number_modifications_surrender;
                    boolean var47 = this.hr.theory.lakatos.test_interestingness_surrender;
                    double var53 = this.hr.theory.lakatos.interestingness_th_surrender;
                    boolean var61 = this.hr.theory.lakatos.compare_average_interestingness_surrender;
                    boolean var63 = this.hr.theory.lakatos.test_plausibility_surrender;
                    double var16 = this.hr.theory.lakatos.plausibility_th_surrender;
                    boolean var18 = this.hr.theory.lakatos.test_domain_application_surrender;
                    double var19 = this.hr.theory.lakatos.domain_application_th_surrender;

                    for(int var21 = 0; var21 < this.hr.theory.conjectures.size(); ++var21) {
                        Conjecture var22 = (Conjecture)this.hr.theory.conjectures.elementAt(var21);
                        this.hr.theory.measure_conjecture.measureConjecture(var22, this.hr.theory);
                    }

                    Vector var64 = var8.getCountersToConjecture();
                    double var65 = var8.applicability / (double)this.hr.theory.entities.size();
                    double var24 = this.hr.theory.averageInterestingnessOfConjectures();
                    if (!var64.isEmpty()) {
                        this.window.writeToFrontEnd("got counters, so starting to calculate whether to perform surrender");
                        this.window.writeToFrontEnd("counters are " + var64);
                        this.window.writeToFrontEnd("dom_of_app is " + var65);
                        this.window.writeToFrontEnd("av_interestingness_conjs is " + var24);
                        this.window.writeToFrontEnd("test_interestingness: " + var47);
                        this.window.writeToFrontEnd("reconstructed_conj.interestingness: " + var8.interestingness);
                        this.window.writeToFrontEnd("reconstructed_conj.plausibility: " + var8.plausibility);
                        boolean var26 = var8.num_modifications > this.hr.theory.lakatos.number_modifications_surrender;
                        boolean var27 = var8.interestingness < this.hr.theory.lakatos.interestingness_th_surrender;
                        boolean var28 = var8.interestingness < var24;
                        boolean var29 = var8.plausibility < this.hr.theory.lakatos.plausibility_th_surrender;
                        boolean var30 = var65 < this.hr.theory.lakatos.domain_application_th_surrender;
                        this.window.writeToFrontEnd("test1a is " + var26);
                        this.window.writeToFrontEnd("test1 is " + var27);
                        this.window.writeToFrontEnd("test2 is " + var28);
                        this.window.writeToFrontEnd("test3 is " + var29);
                        this.window.writeToFrontEnd("test4 is " + var30);
                        if (var43 && var8.num_modifications >= var44 || var47 && var8.interestingness < var53 || var61 && var8.interestingness < var24 || var63 && var8.plausibility < var16 || var18 && var65 < var19) {
                            this.window.writeToFrontEnd("gonna perform surrender");
                            var4.response_vector = this.hr.theory.lakatos.surrender(var8);
                            var8.lakatos_method = "surrender";
                            this.hr.theory.downgradeConjectureToPseudoConjecture(var8);
                            this.window.writeToFrontEnd("response.toString() is now " + var4.toString());
                            this.window.writeToFrontEnd(" ** finished surrender");
                            this.group_file.writeToGroupFile("Student " + this.port + ": " + var4.toStringForFile());
                            return var4;
                        }
                    }
                }

                if (this.hr.theory.use_piecemeal_exclusion) {
                    this.window.writeToFrontEnd(" ** starting piecemeal_exclusion on " + var8.writeConjecture("ascii") + "      ** ");
                    var43 = false;
                    if (var43) {
                        this.hr.theory.measure_conjecture.measureConjecture(var8, this.hr.theory);
                        var10 = this.hr.theory.averageInterestingnessOfConjectures();
                        this.window.writeToFrontEnd("Before modification, the interestingness of the conjecture is " + var8.interestingness);
                        this.window.writeToFrontEnd("reconstructed_conj.applicability is " + var8.applicability);
                        this.window.writeToFrontEnd("reconstructed_conj.normalised_applicability is " + var8.normalised_applicability);
                        this.window.writeToFrontEnd("reconstructed_conj.complexity is " + var8.complexity);
                        this.window.writeToFrontEnd("reconstructed_conj.comprehensibility is " + var8.comprehensibility);
                        this.window.writeToFrontEnd("reconstructed_conj.normalised_comprehensibility is " + var8.normalised_comprehensibility);
                        this.window.writeToFrontEnd("reconstructed_conj.surprisingness is " + var8.surprisingness);
                        this.window.writeToFrontEnd("reconstructed_conj.normalised_surprisingness is " + var8.normalised_surprisingness);
                        this.window.writeToFrontEnd("reconstructed_conj.plausibility is " + var8.plausibility);
                        this.window.writeToFrontEnd("The averageinterestingness of all of my conjectures is " + var10);
                    }

                    Vector var54 = this.hr.theory.lakatos.piecemealExclusion(var8, this.hr.theory, this.window);

                    for(int var48 = 0; var48 < var54.size(); ++var48) {
                        if (var54.elementAt(var48) instanceof Conjecture) {
                            var50 = (Conjecture)var54.elementAt(var48);
                            if (var43) {
                                this.hr.theory.measure_conjecture.measureConjecture(var8, this.hr.theory);
                                double var57 = this.hr.theory.averageInterestingnessOfConjectures();
                                this.window.writeToFrontEnd("After modification, the interestingness of the conjecture is " + var8.interestingness);
                                this.window.writeToFrontEnd("reconstructed_conj.applicability is " + var8.applicability);
                                this.window.writeToFrontEnd("reconstructed_conj.normalised_applicability is " + var8.normalised_applicability);
                                this.window.writeToFrontEnd("reconstructed_conj.complexity is " + var8.complexity);
                                this.window.writeToFrontEnd("reconstructed_conj.comprehensibility is " + var8.comprehensibility);
                                this.window.writeToFrontEnd("reconstructed_conj.normalised_comprehensibility is " + var8.normalised_comprehensibility);
                                this.window.writeToFrontEnd("reconstructed_conj.surprisingness is " + var8.surprisingness);
                                this.window.writeToFrontEnd("reconstructed_conj.normalised_surprisingness is " + var8.normalised_surprisingness);
                                this.window.writeToFrontEnd("reconstructed_conj.plausibility is " + var8.plausibility);
                                this.window.writeToFrontEnd("The averageinterestingness of all of my conjectures is " + var57);
                            }

                            if (!var50.lakatos_method.equals("counterexample_barring")) {
                                var50.lakatos_method = "piecemeal_exclusion";
                            }

                            ++var50.num_modifications;
                            var4.response_vector.addElement(var50);
                        }

                        if (var54.elementAt(var48) instanceof String) {
                            var12 = (String)var54.elementAt(var48);
                            var4.response_vector.addElement(var12);
                        }
                    }

                    this.window.writeToFrontEnd("response is " + var4.toString());
                    this.window.writeToFrontEnd(" ** finished piecemeal_exclusion **");
                }

                if (this.hr.theory.use_strategic_withdrawal) {
                    this.window.writeToFrontEnd(" ** starting use_strategic_withdrawal on " + var8 + "        **");
                    var39 = this.hr.theory.lakatos.strategicWithdrawal(var8, this.hr.theory, this.window);

                    for(var44 = 0; var44 < var39.size(); ++var44) {
                        if (var39.elementAt(var44) instanceof Conjecture) {
                            Conjecture var49 = (Conjecture)var39.elementAt(var44);
                            var49.lakatos_method = "strategic_withdrawal";
                            ++var49.num_modifications;
                            var4.response_vector.addElement(var49);
                        }

                        if (var39.elementAt(var44) instanceof String) {
                            String var51 = (String)var39.elementAt(var44);
                            var4.response_vector.addElement(var51);
                        }
                    }

                    this.window.writeToFrontEnd(" ** finished use_strategic_withdrawal  **");
                }
            }

            if (var1.motivation.attempted_method.equals("lemma-incorporation") && !var1.theory_constituent_vector.isEmpty()) {
                ProofScheme var36 = (ProofScheme)var1.theory_constituent_vector.elementAt(0);
                ProofScheme var40 = new ProofScheme();
                var46 = this.hr.theory.front_end.macro_list.getSelectedItem();
                System.out.println("Student: macro_chosen is " + var46);
                System.out.println("I'm a student, and I'm going to reconstruct the proofscheme now");
                Conjecture var55 = var36.conj.reconstructConjecture_old(this.hr.theory, this.window);
                var40.conj = var55;
                Vector var52 = new Vector();
                System.out.println("Student: global conj is " + var55.writeConjecture());
                System.out.println("Student: proofscheme.proof_vector.size() is " + var36.proof_vector.size());

                for(int var56 = 0; var56 < var36.proof_vector.size(); ++var56) {
                    Conjecture var58 = (Conjecture)var36.proof_vector.elementAt(var56);
                    System.out.println("\n\n\nStudent: ******at the moment we've got conjecture " + var56 + " is: " + var58.writeConjecture());
                    Conjecture var62 = var58.reconstructConjecture(this.hr.theory, this.window);
                    var52.addElement(var62);
                    System.out.println("Student: ******local_conj " + var56 + " is: " + var62.writeConjecture());
                    System.out.println("\nStudent: _____________________________________________\n");
                }

                System.out.println("Student: done reconstructConjecture");
                System.out.println("Student: macro_chosen is " + var46);
                var40.proof_vector = var52;
                ProofScheme var59 = this.hr.theory.lakatos.lemmaIncorporation(this.hr.theory, var40, this.window);
                var59.lakatos_method = "lemma_incorporation";
                System.out.println("old proofscheme was " + var36);
                System.out.println("improved_proofscheme is " + var59);
            }

            if (!var4.toStringForFile().equals("")) {
                this.group_file.writeToGroupFile("Student " + this.port + ": " + var4.toStringForFile());
            }

            return var4;
        }
    }

    public Vector getNBestConjectures(String var1, int var2, Vector var3) {
        Vector var4 = new Vector();
        double var5 = 1.0D;
        boolean var7 = var3.isEmpty();

        int var8;
        Conjecture var9;
        for(var8 = 0; var8 < this.hr.theory.conjectures.size(); ++var8) {
            var9 = (Conjecture)this.hr.theory.conjectures.elementAt(var8);
        }

        if (var2 >= this.hr.theory.conjectures.size()) {
            for(var8 = 0; var8 < this.hr.theory.conjectures.size(); ++var8) {
                var9 = (Conjecture)this.hr.theory.conjectures.elementAt(var8);
                this.contains(var3, var9);
                if ((var9.arity == 1.0D || var9.arity == 0.0D) && !this.contains(var3, var9)) {
                    if (var1.equals("Conjecture")) {
                        var4.addElement(var9);
                    }

                    if (var1.equals("NearEquivalence") && var9 instanceof NearEquivalence) {
                        NearEquivalence var11 = (NearEquivalence)var9;
                        if (var11.lh_concept.arity == 1 && var11.rh_concept.arity == 1) {
                            var4.addElement(var11);
                        }
                    }

                    if (var1.equals("NearImplication") && var9 instanceof NearImplication) {
                        NearImplication var14 = (NearImplication)var9;
                        if (var14.lh_concept.arity == 1 && var14.rh_concept.arity == 1) {
                            var4.addElement(var14);
                        }
                    }

                    if (var1.equals("Equivalence") && var9 instanceof Equivalence) {
                        Equivalence var15 = (Equivalence)var9;
                        if (var15.lh_concept.arity == 1 && var15.rh_concept.arity == 1) {
                            var4.addElement(var15);
                        }
                    }

                    if (var1.equals("Implication") && var9 instanceof Implication) {
                        Implication var16 = (Implication)var9;
                        if (var16.lh_concept.arity == 1 && var16.rh_concept.arity == 1) {
                            var4.addElement(var16);
                        }

                        if ((double)var16.lh_concept.arity == 0.0D && var16.rh_concept.arity == 1) {
                            var4.addElement(var16);
                        }

                        if ((double)var16.lh_concept.arity == 1.0D && (double)var16.rh_concept.arity == 0.0D) {
                            var4.addElement(var16);
                        }
                    }

                    if (var1.equals("NonExists") && var9 instanceof NonExists) {
                        NonExists var18 = (NonExists)var9;
                        if (var18.concept.arity == 1) {
                            var4.addElement(var18);
                        }
                    }

                    if (var1.equals("Implicate") && var9 instanceof Implicate) {
                        Implicate var20 = (Implicate)var9;
                        if (var20.premise_concept.arity == 1) {
                            var4.addElement(var20);
                        }
                    }
                }
            }
        } else {
            var8 = 0;

            for(int var13 = 0; var13 < this.hr.theory.conjectures.size() && var8 < var2; ++var13) {
                Conjecture var10 = (Conjecture)this.hr.theory.conjectures.elementAt(var13);
                boolean var21 = true;
                if (this.hyp_mode) {
                    var21 = var10.interestingness >= var5;
                }

                if ((var10.arity == 1.0D || var10.arity == 0.0D) && !this.contains(var3, var10) && var21) {
                    if (var1.equals("Conjecture")) {
                        var4.addElement(var10);
                        ++var8;
                    }

                    if (var1.equals("NearEquivalence") && var10 instanceof NearEquivalence) {
                        NearEquivalence var12 = (NearEquivalence)var10;
                        if (var12.lh_concept.arity == 1 && var12.rh_concept.arity == 1) {
                            var4.addElement(var12);
                            ++var8;
                        }
                    }

                    if (var1.equals("NearImplication") && var10 instanceof NearImplication) {
                        NearImplication var17 = (NearImplication)var10;
                        if (var17.lh_concept.arity == 1 && var17.rh_concept.arity == 1) {
                            var4.addElement(var17);
                            ++var8;
                        }
                    }

                    if (var1.equals("Equivalence") && var10 instanceof Equivalence) {
                        Equivalence var19 = (Equivalence)var10;
                        if (var19.lh_concept.arity == 1 && var19.rh_concept.arity == 1) {
                            var4.addElement(var19);
                            ++var8;
                        }

                        if (var19.lh_concept.arity == 1 && var19.rh_concept.arity == 0 || var19.lh_concept.arity == 0 && var19.rh_concept.arity == 1) {
                            var4.addElement(var19);
                            ++var8;
                        }
                    }

                    if (var1.equals("Implication") && var10 instanceof Implication) {
                        Implication var22 = (Implication)var10;
                        if (var22.lh_concept.arity == 1 && var22.rh_concept.arity == 1) {
                            var4.addElement(var22);
                            ++var8;
                        }

                        if (var22.lh_concept.arity == 0 && var22.rh_concept.arity == 1) {
                            var4.addElement(var22);
                            ++var8;
                        }

                        if (var22.lh_concept.arity == 1 && var22.rh_concept.arity == 0) {
                            var4.addElement(var22);
                            ++var8;
                        }
                    }

                    if (var1.equals("NonExists") && var10 instanceof NonExists) {
                        NonExists var23 = (NonExists)var10;
                        if (var23.concept.arity == 0 || var23.concept.arity == 1) {
                            var4.addElement(var23);
                            ++var8;
                        }
                    }

                    if (var1.equals("Implicate") && var10 instanceof Implicate) {
                        Implicate var24 = (Implicate)var10;
                        if (var24.premise_concept.arity == 1) {
                            var4.addElement(var24);
                            ++var8;
                        }
                    }
                }
            }
        }

        return var4;
    }

    public Vector getNBestConjectures1(String var1, int var2, Vector var3) {
        Vector var4 = new Vector();
        Vector var5 = new Vector();
        if (var1.equals("NearEquivalence")) {
            var5 = this.hr.theory.near_equivalences;
        }

        if (var1.equals("NearImplication")) {
            var5 = this.hr.theory.near_implications;
        }

        if (var1.equals("Conjecture")) {
            var5 = this.hr.theory.conjectures;
        }

        if (var1.equals("Equivalence")) {
            var5 = this.hr.theory.equivalences;
        }

        if (var1.equals("NonExistence")) {
            var5 = this.hr.theory.non_existences;
        }

        if (var1.equals("Implication")) {
            var5 = this.hr.theory.implications;
        }

        if (var1.equals("Implicate")) {
            var5 = this.hr.theory.implicates;
        }

        int var6;
        if (var2 >= var5.size()) {
            for(var6 = 0; var6 < var5.size(); ++var6) {
                Conjecture var7 = (Conjecture)var5.elementAt(var6);
                if ((var7.arity == 1.0D || var7.arity == 0.0D) && !this.contains(var3, var7)) {
                    var4.addElement(var7);
                }
            }
        } else {
            var6 = 0;

            for(int var9 = 0; var9 < var5.size() && var6 < var2; ++var9) {
                Conjecture var8 = (Conjecture)var5.elementAt(var9);
                if (var8.arity == 1.0D || var8.arity == 0.0D && !this.contains(var3, var8)) {
                    var4.addElement(var8);
                    ++var6;
                }
            }
        }

        return var4;
    }

    public Vector getNBestConcepts(int var1) {
        Vector var2 = new Vector();
        if (var1 >= this.hr.theory.concepts.size()) {
            var2 = this.hr.theory.concepts;
        } else {
            for(int var3 = 0; var3 < var1; ++var3) {
                Concept var4 = (Concept)this.hr.theory.concepts.elementAt(var3);
                var2.addElement(var4);
            }
        }

        this.window.writeQuietlyToFrontEnd("getNBestConcepts():");
        this.window.writeQuietlyToFrontEnd("my " + var1 + " best concepts are " + var2);
        return var2;
    }

    public static void main(String[] var0) {
        String var1 = var0[0];
        String var2 = var0[1];
        String var3 = var0[2];
        String var4 = var0[3];
        int var5 = Integer.parseInt(var4);
        InetAddress var6 = null;

        try {
            var6 = InetAddress.getByName(var3);
        } catch (Exception var9) {
            System.out.println("Problem getting teacher_hostname: " + var9);
        }

        Student var7 = new Student();
        if (var0.length == 4) {
            var7 = new Student(var1, var2, var6, var5);
        }

        if (var0.length == 5) {
            String var8 = var0[4];
            var7 = new Student(var1, var2, var8, var6, var5);
        }

        var7.start();
    }

    public void sleeping() {
        System.out.println("Sleeping");
    }

    public void receiveTeachersRequest() {
        boolean var1 = true;

        while(var1) {
            try {
                this.window.writeQuietlyToFrontEnd("Just read this request in the teacher's file:");
                this.window.writeQuietlyToFrontEnd(this.current_request);
                this.window.drawQuietLine();
                var1 = false;
            } catch (Exception var3) {
                System.out.println(this.name);
                System.out.println(" just looked in teacher's file but couldn't get object");
            }
        }

    }

    public boolean contains(Vector var1, Conjecture var2) {
        for(int var3 = 0; var3 < var1.size(); ++var3) {
            int var5;
            if (var1.elementAt(var3) instanceof Vector) {
                Vector var4 = (Vector)var1.elementAt(var3);

                for(var5 = 0; var5 < var4.size(); ++var5) {
                    Response var6 = (Response)var1.elementAt(var5);
                    if (!var6.response_vector.isEmpty() && var6.response_vector.elementAt(0) instanceof Conjecture) {
                        for(int var7 = 0; var5 < var6.response_vector.size(); ++var7) {
                            Conjecture var8 = (Conjecture)var6.response_vector.elementAt(var7);
                            if (var8.writeConjecture("ascii").equals(var2.writeConjecture("ascii"))) {
                                return true;
                            }
                        }
                    }
                }
            }

            if (var1.elementAt(var3) instanceof Response) {
                Response var9 = (Response)var1.elementAt(var3);
                if (!var9.response_vector.isEmpty() && var9.response_vector.elementAt(0) instanceof Conjecture) {
                    for(var5 = 0; var5 < var9.response_vector.size(); ++var5) {
                        Conjecture var11 = (Conjecture)var9.response_vector.elementAt(var5);
                        if (var11.writeConjecture("ascii").equals(var2.writeConjecture("ascii"))) {
                            return true;
                        }
                    }
                }
            }

            if (var1.elementAt(var3) instanceof Conjecture) {
                Conjecture var10 = (Conjecture)var1.elementAt(var3);
                if (var10.equals(var2)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void respondToResponses(Vector var1) {
        this.window.writeQuietlyToFrontEnd("started respondToResponses(" + var1);
        Request var2 = new Request();
        this.window.writeQuietlyToFrontEnd("received_vector.size() is " + var1.size());
        Conjecture var3 = this.current_request.motivation.conjecture_under_discussion;

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            Response var5 = (Response)var1.elementAt(var4);
            Vector var6 = var5.response_vector;
            if (!var6.isEmpty() && var6.elementAt(0) instanceof Entity) {
                for(int var7 = 0; var7 < var6.size(); ++var7) {
                    Entity var8 = (Entity)var6.elementAt(var7);
                    if (!var8.entitiesContains(this.hr.theory.entities)) {
                        if (!this.hr.theory.use_monster_barring) {
                            this.window.writeToFrontEnd("use_monster_barring is false");

                            for(int var9 = 0; var7 < this.hr.theory.concepts.size(); ++var9) {
                                Concept var10 = (Concept)this.hr.theory.concepts.elementAt(var9);
                                if (var10.is_object_of_interest_concept) {
                                    System.out.println("\n\n\n 1) got a new entity and going to add it to theory");
                                    this.hr.theory.addNewEntityToTheory(var8, var10.domain, "adding new entity to theory");
                                    System.out.println("\n\n\n 2) just performed addNewEntityToTheory() for entity=" + var8 + ", concept.domain = " + var10.domain);
                                    this.group_file.writeToGroupFile("Student " + this.port + ": OK - I hadn't seen the " + var10.domain + " " + var8.toString() + " before. I've just added it to my theory.");
                                    break;
                                }
                            }
                        }

                        if (this.hr.theory.use_monster_barring) {
                            this.window.writeToFrontEnd("use_monster_barring is true");
                            boolean var19 = this.hr.theory.lakatos.use_breaks_conj_under_discussion;
                            boolean var20 = false;
                            if (var19 && var3.counterexamples.isEmpty()) {
                                ++var3.num_modifications;

                                for(int var11 = 0; var11 < this.hr.theory.concepts.size(); ++var11) {
                                    Concept var12 = (Concept)this.hr.theory.concepts.elementAt(var11);
                                    if (var12.is_object_of_interest_concept) {
                                        var20 = true;
                                        var2 = this.hr.theory.lakatos.monsterBarring(var8, var12.domain, this.hr.theory, var3, this.hr.theory.lakatos.monster_barring_type, this.window);
                                        this.group_file.writeToGroupFile("Student " + this.port + ": " + var2);
                                        break;
                                    }
                                }
                            }

                            boolean var21 = this.hr.theory.lakatos.use_percentage_conj_broken;
                            if (var21) {
                                double var22 = this.hr.theory.lakatos.percentageConjecturesBrokenByEntity(this.hr.theory, var8, this.window);
                                this.window.writeToFrontEnd(var22 + "% of my conjectures are broken by " + var8.name);
                                if (var22 > this.hr.theory.lakatos.monster_barring_min) {
                                    this.window.writeToFrontEnd(var22 + " > " + this.hr.theory.lakatos.monster_barring_min + " so am going to perform m-b");
                                    var8.lakatos_method = "monster_barring";
                                    ++var3.num_modifications;

                                    for(int var14 = 0; var14 < this.hr.theory.concepts.size(); ++var14) {
                                        Concept var15 = (Concept)this.hr.theory.concepts.elementAt(var14);
                                        if (var15.is_object_of_interest_concept) {
                                            var20 = true;
                                            var2 = this.hr.theory.lakatos.monsterBarring(var8, var15.domain, this.hr.theory, var3, this.hr.theory.lakatos.monster_barring_type, this.window);
                                            this.group_file.writeToGroupFile("Student " + this.port + ": " + var2);
                                            break;
                                        }
                                    }
                                }
                            }

                            boolean var23 = this.hr.theory.lakatos.use_culprit_breaker;
                            boolean var13 = this.hr.theory.lakatos.use_culprit_breaker_on_conj;
                            boolean var24 = this.hr.theory.lakatos.use_culprit_breaker_on_all;
                            this.window.writeToFrontEnd("use_culprit_breaker is " + var23);
                            Concept var30;
                            if (var23) {
                                this.window.drawQuietStars();
                                this.window.writeToFrontEnd("into use_culprit_breaker");
                                this.window.writeToFrontEnd("rv is " + var6);
                                this.window.writeToFrontEnd("rv.size() is " + var6.size());
                                this.window.writeToFrontEnd(" hr.theory.entities.size()/2 is " + this.hr.theory.entities.size() / 2);
                                if (var6.size() > this.hr.theory.entities.size() / 2) {
                                    this.window.writeQuietlyToFrontEnd("rv.size() is " + var6.size());
                                    this.window.writeQuietlyToFrontEnd("hr.theory.entities.size() is " + this.hr.theory.entities.size());
                                    this.window.writeToFrontEnd("It seems that rv.size()>hr.theory.entities.size()/2");
                                    String var25;
                                    if (var13) {
                                        this.window.writeQuietlyToFrontEnd("conj is " + var3.writeConjecture("ascii"));
                                        var25 = this.hr.theory.lakatos.conjectureBrokenByCulpritEntity(this.hr.theory, var3, this.window);
                                        this.window.writeQuietlyToFrontEnd("just had a look to see whether we have a culprit breaker to " + var3.writeConjecture("ascii"));
                                        this.window.writeQuietlyToFrontEnd("... found culprit_entity_string = " + var25);
                                        Entity var16 = new Entity(var25);
                                        var16.lakatos_method = "monster_barring";
                                        ++var3.num_modifications;
                                        this.window.writeQuietlyToFrontEnd("just converted this culprit_entity_string from String to entity - now got " + var16);
                                        if (!var25.equals("no")) {
                                            this.window.writeQuietlyToFrontEnd("it isn't no");

                                            for(int var17 = 0; var7 < this.hr.theory.concepts.size(); ++var17) {
                                                Concept var18 = (Concept)this.hr.theory.concepts.elementAt(var17);
                                                if (var18.is_object_of_interest_concept) {
                                                    var20 = true;
                                                    var2 = this.hr.theory.lakatos.monsterBarring(var16, var18.domain, this.hr.theory, var3, this.hr.theory.lakatos.monster_barring_type, this.window);
                                                    this.group_file.writeToGroupFile("Student " + this.port + ": " + var2);
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    if (var24) {
                                        var25 = this.hr.theory.lakatos.conjectureBrokenByCulpritEntity(this.hr.theory, this.window, this.hr.theory.lakatos.monster_barring_culprit_min);
                                        if (!var25.equals("no")) {
                                            this.window.writeQuietlyToFrontEnd("it isn't no");

                                            for(int var27 = 0; var7 < this.hr.theory.concepts.size(); ++var27) {
                                                var30 = (Concept)this.hr.theory.concepts.elementAt(var27);
                                                if (var30.is_object_of_interest_concept) {
                                                    var20 = true;
                                                    var2 = this.hr.theory.lakatos.monsterBarring(var8, var30.domain, this.hr.theory, var3, this.hr.theory.lakatos.monster_barring_type, this.window);
                                                    this.group_file.writeToGroupFile("Student " + this.port + ": " + var2);
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    this.window.writeQuietlyToFrontEnd("finished use_culprit_breaker");
                                    this.window.drawStars();
                                }
                            }

                            int var26;
                            for(var26 = 0; var26 < var2.theory_constituent_vector.size(); ++var26) {
                                TheoryConstituent var28 = (TheoryConstituent)var2.theory_constituent_vector.elementAt(var26);
                                if (var28 instanceof Concept) {
                                    var30 = (Concept)var28;
                                    var30.lakatos_method = "monster_barring";
                                    ++var3.num_modifications;
                                }
                            }

                            if (!var20) {
                                for(var26 = 0; var7 < this.hr.theory.concepts.size(); ++var26) {
                                    Concept var29 = (Concept)this.hr.theory.concepts.elementAt(var26);
                                    if (var29.is_object_of_interest_concept) {
                                        this.window.writeQuietlyToFrontEnd("6");
                                        this.hr.theory.addNewEntityToTheory(var8, var29.domain, "adding new entity to theory");
                                        var8.lakatos_method = "highlighted by dialogue";
                                        this.group_file.writeToGroupFile("Student " + this.port + ": OK - I hadn't seen the " + var29.domain + " " + var8.toString() + " before. I've decided to add it to my theory.");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        this.makeCall(this.port, this.teacher_hostname, this.teacher_port, var2);
    }

    public void testReact(HR var1) {
        Concept var2 = new Concept();

        for(int var3 = 0; var3 < var1.theory.concepts.size(); ++var3) {
            Concept var4 = (Concept)var1.theory.concepts.elementAt(var3);
            if (var4.id.equals("int001")) {
                var2 = var4;
            }
        }

        Hashtable var7 = new Hashtable();
        Vector var8 = new Vector();
        String var5 = var2.id;
        Reaction var6 = new Reaction("dummy_id", "System.out.println(this is a reaction)", var7);
        var8.addElement(var6);
        var1.theory.react.reaction_vector_hashtable.put(var5, var8);
    }

    public void addToTheoryFromDiscussionVector() {
        System.out.println("started addToTheoryFromDiscussionVector");
        System.out.println("personal_discussion_vector is " + this.personal_discussion_vector);
        System.out.println("my conjectures so far are:");
        this.window.writeToFrontEnd("my conjectures so far are:");

        int var1;
        Conjecture var2;
        for(var1 = 0; var1 < this.hr.theory.conjectures.size(); ++var1) {
            var2 = (Conjecture)this.hr.theory.conjectures.elementAt(var1);
            System.out.println(var2.id + ": " + var2.writeConjecture());
            this.window.writeToFrontEnd(var2.id + ": " + var2.writeConjecture());
        }

        System.out.println("end of my conjectures");
        System.out.println("\n               ---------------");
        this.window.writeToFrontEnd("end of my conjectures");
        this.window.writeToFrontEnd("\n               ---------------");
        this.window.writeToFrontEnd("my concepts are:");

        Concept var8;
        for(var1 = 0; var1 < this.hr.theory.concepts.size(); ++var1) {
            var8 = (Concept)this.hr.theory.concepts.elementAt(var1);
            this.window.writeToFrontEnd(var8.id + ": " + var8.writeDefinition());
        }

        this.window.writeToFrontEnd("end of my new concepts");
        this.window.writeToFrontEnd("\n              --------------------");

        for(var1 = 0; var1 < this.personal_discussion_vector.size(); ++var1) {
            if (this.personal_discussion_vector.elementAt(var1) instanceof Response) {
                Response var9 = (Response)this.personal_discussion_vector.elementAt(var1);

                for(int var3 = 0; var3 < var9.response_vector.size(); ++var3) {
                    boolean var5;
                    if (var9.response_vector.elementAt(var3) instanceof Conjecture) {
                        Conjecture var10 = (Conjecture)var9.response_vector.elementAt(var3);
                        System.out.println(var10.id + ": " + var10.writeConjecture() + " is a conj");
                        var5 = var10.theoryContainsConjecture(this.hr.theory.conjectures);
                        System.out.println("theory_contains_conj is " + var5);
                        if (!var5) {
                            var10.id = (new Integer(this.hr.theory.conjectures.size())).toString();
                            this.hr.theory.measure_conjecture.measureConjecture(var10, this.hr.theory);
                            if (var10.interestingness >= this.hr.theory.lakatos.threshold_to_add_conj_to_theory) {
                                Conjecture var6 = var10.reconstructConjecture(this.hr.theory, this.window);
                                this.hr.theory.addConjectureToTheory(var6);
                            }
                        }

                        var9.response_vector.removeElementAt(var3);
                        --var3;
                        break;
                    }

                    if (var9.response_vector.elementAt(var3) instanceof Concept) {
                        Concept var4 = (Concept)var9.response_vector.elementAt(var3);
                        System.out.println(var4.writeDefinition() + " is a concept");
                        var5 = var4.theoryContainsConcept(this.hr.theory.concepts);
                        System.out.println("theory_contains_concept is " + var5);
                        if (!var5) {
                            var4.id = (new Integer(this.hr.theory.concepts.size())).toString();
                            System.out.println("(concept.interestingness is " + var4.interestingness);
                            this.hr.theory.measure_concept.measureConcept(var4, this.hr.theory.concepts, true);
                            System.out.println("(concept.interestingness is NOW " + var4.interestingness);
                            if (var4.interestingness >= this.hr.theory.lakatos.threshold_to_add_concept_to_theory) {
                                var4.dont_develop = false;
                                this.hr.theory.addConceptToTheory(var4);
                                System.out.println("concept over threshold");
                            }
                        }

                        var9.response_vector.removeElementAt(var3);
                        --var3;
                        break;
                    }

                    this.personal_discussion_vector.removeElementAt(var1);
                    --var1;
                }
            }
        }

        System.out.println("my conjectures NOW are:");
        this.window.writeToFrontEnd("my conjectures NOW are:");

        for(var1 = 0; var1 < this.hr.theory.conjectures.size(); ++var1) {
            var2 = (Conjecture)this.hr.theory.conjectures.elementAt(var1);
            System.out.println(var2.id + ": " + var2.writeConjecture());
            this.window.writeToFrontEnd(var2.id + ": " + var2.writeConjecture());
        }

        System.out.println("end of my new conjectures");
        System.out.println("\n              ---------------");
        this.window.writeToFrontEnd("end of my new conjectures");
        this.window.writeToFrontEnd("\n              ---------------");
        this.window.writeToFrontEnd("my concepts NOW are:");

        for(var1 = 0; var1 < this.hr.theory.concepts.size(); ++var1) {
            var8 = (Concept)this.hr.theory.concepts.elementAt(var1);
            this.window.writeToFrontEnd(var8.id + ": " + var8.writeDefinition());
        }

        this.window.writeToFrontEnd("end of my new concepts");
        this.window.writeToFrontEnd("\n              --------------------");
        System.out.println("personal_discussion_vector is now " + this.personal_discussion_vector);
        System.out.println("finished addToTheoryFromDiscussionVector");
        System.out.println("\n\n\n");
    }

    public void writeVariableInformation() {
        this.window.writeQuietlyToFrontEnd("By the way,");
        if (this.hr.theory.use_surrender) {
            this.window.writeQuietlyToFrontEnd("hr.theory.front_end.use_surrender.getState() is " + this.hr.theory.front_end.use_surrender_check.getState());
        }

        if (this.hr.theory.use_strategic_withdrawal) {
            this.window.writeQuietlyToFrontEnd("hr.theory.use_strategic_withdrawal_check is " + this.hr.theory.use_strategic_withdrawal);
        }

        if (this.hr.theory.use_piecemeal_exclusion) {
            this.window.writeQuietlyToFrontEnd("hr.theory.use_piecemeal_exclusion_check is " + this.hr.theory.use_piecemeal_exclusion);
        }

        if (this.hr.theory.use_monster_barring) {
            this.window.writeQuietlyToFrontEnd("hr.theory.use_monster_barring_check is " + this.hr.theory.use_monster_barring);
        }

        if (this.hr.theory.use_monster_adjusting) {
            this.window.writeQuietlyToFrontEnd("hr.theory.use_monster_adjusting_check is " + this.hr.theory.use_monster_adjusting);
        }

        if (this.hr.theory.use_lemma_incorporation) {
            this.window.writeQuietlyToFrontEnd("hr.theory.use_lemma_incorporation_check is " + this.hr.theory.use_lemma_incorporation);
        }

        if (this.hr.theory.use_proofs_and_refutations) {
            this.window.writeQuietlyToFrontEnd("hr.theory.use_proofs_and_refutations_check is " + this.hr.theory.use_proofs_and_refutations);
        }

        if (this.hr.theory.use_communal_piecemeal_exclusion) {
            this.window.writeQuietlyToFrontEnd("hr.theory.use_communal_piecemeal_exclusion_check is " + this.hr.theory.use_communal_piecemeal_exclusion);
        }

        this.window.writeQuietlyToFrontEnd("the entities in my theory are " + this.hr.theory.entities);
        this.window.writeQuietlyToFrontEnd("the pseudo_entities in my theory are " + this.hr.theory.pseudo_entities);
        this.window.writeQuietlyToFrontEnd("my personal discussion_vector is " + this.personal_discussion_vector);
        this.window.writeQuietlyToFrontEnd("the discussion vector is " + this.discussion_vector);
        String var1 = this.hr.theory.front_end.macro_list.getSelectedItem();
        this.window.writeToFrontEnd("The macro chosen is " + var1);
        boolean var2 = true;
        boolean var3 = true;
        boolean var4 = true;
        boolean var5 = true;
        if (var2) {
            this.window.writeToFrontEnd("max_num_independent_work_stages is " + this.hr.theory.lakatos.max_num_independent_work_stages);
            this.window.writeToFrontEnd("test_num_independent_steps is " + this.hr.theory.lakatos.test_num_independent_steps);
            this.window.writeToFrontEnd("num_independent_steps is " + this.hr.theory.lakatos.num_independent_steps);
            this.window.writeToFrontEnd("hr.theory.lakatos.threshold_to_add_conj_to_theory is " + this.hr.theory.lakatos.threshold_to_add_conj_to_theory);
            this.window.writeToFrontEnd("hr.theory.lakatos.threshold_to_add_concept_to_theory is " + this.hr.theory.lakatos.threshold_to_add_concept_to_theory);
        }

        if (var3) {
            this.window.writeToFrontEnd("hr.theory.lakatos.test_number_modifications_surrender is " + this.hr.theory.lakatos.test_number_modifications_surrender);
            this.window.writeToFrontEnd("hr.theory.lakatos.test_interestingness_surrender is " + this.hr.theory.lakatos.test_interestingness_surrender);
            this.window.writeToFrontEnd("hr.theory.lakatos.compare_average_interestingness_surrender is " + this.hr.theory.lakatos.compare_average_interestingness_surrender);
            this.window.writeToFrontEnd("hr.theory.lakatos.test_plausibility_surrender is  " + this.hr.theory.lakatos.test_plausibility_surrender);
            this.window.writeToFrontEnd("hr.theory.lakatos.test_domain_application_surrender is " + this.hr.theory.lakatos.test_domain_application_surrender);
            this.window.writeToFrontEnd("hr.theory.lakatos.number_modifications_surrender is " + this.hr.theory.lakatos.number_modifications_surrender);
            this.window.writeToFrontEnd("hr.theory.lakatos.interestingness_th_surrender is " + this.hr.theory.lakatos.interestingness_th_surrender);
            this.window.writeToFrontEnd("hr.theory.lakatos.plausibility_th_surrender is " + this.hr.theory.lakatos.plausibility_th_surrender);
            this.window.writeToFrontEnd("hr.theory.lakatos.domain_application_th_surrender is " + this.hr.theory.lakatos.domain_application_th_surrender);
        }

        if (var4) {
            this.window.writeToFrontEnd("hr.theory.lakatos.monster_barring_min is " + this.hr.theory.lakatos.monster_barring_min);
            this.window.writeToFrontEnd("hr.theory.lakatos.monster_barring_type is " + this.hr.theory.lakatos.monster_barring_type);
            this.window.writeToFrontEnd("hr.theory.lakatos.monster_barring_culprit_min is " + this.hr.theory.lakatos.monster_barring_culprit_min);
            this.window.writeToFrontEnd("hr.theory.lakatos.use_breaks_conj_under_discussion is " + this.hr.theory.lakatos.use_breaks_conj_under_discussion);
            this.window.writeToFrontEnd("hr.theory.lakatos.accept_strictest is " + this.hr.theory.lakatos.accept_strictest);
            this.window.writeToFrontEnd("hr.theory.lakatos.use_percentage_conj_broken is " + this.hr.theory.lakatos.use_percentage_conj_broken);
            this.window.writeToFrontEnd("hr.theory.lakatos.use_culprit_breaker is " + this.hr.theory.lakatos.use_culprit_breaker);
            this.window.writeToFrontEnd("hr.theory.lakatos.use_culprit_breaker_on_conj is " + this.hr.theory.lakatos.use_culprit_breaker_on_conj);
            this.window.writeToFrontEnd("hr.theory.lakatos.use_culprit_breaker_on_all is " + this.hr.theory.lakatos.use_culprit_breaker_on_all);
        }

    }
}
