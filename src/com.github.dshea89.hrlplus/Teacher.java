package com.github.dshea89.hrlplus;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Vector;

public class Teacher extends Agent {
    AgentWindow window = new AgentWindow();
    public String name_teacher = null;
    public Vector student_names = new Vector();
    public int num_students;
    Vector student_inetaddresses = new Vector();
    public Request current_request = new Request("");
    public Vector received_objects = new Vector();
    public int num_requests_to_work = 0;
    public String type = "";
    public int num = 1;
    public String condition = "null";
    public Vector theory_constituent_vector = new Vector();
    public Conjecture conj = new Conjecture();
    public String attempted_method = "to get discussion started";
    public Motivation motivation;
    public Request default_request;
    int number_requests_to_work;
    GroupAgendaElement current_group_agenda_element;
    public Vector current_responses;
    public GroupAgenda group_agenda;
    public int port;

    public Teacher() {
        this.motivation = new Motivation(this.conj, this.attempted_method);
        this.default_request = new Request(this.type, this.num, this.condition, this.theory_constituent_vector, this.motivation);
        this.number_requests_to_work = 0;
        this.current_group_agenda_element = new GroupAgendaElement();
        this.current_responses = new Vector();
        this.group_agenda = new GroupAgenda();
        this.port = 8500;
    }

    public Teacher(Vector var1, String var2, String var3) {
        this.motivation = new Motivation(this.conj, this.attempted_method);
        this.default_request = new Request(this.type, this.num, this.condition, this.theory_constituent_vector, this.motivation);
        this.number_requests_to_work = 0;
        this.current_group_agenda_element = new GroupAgendaElement();
        this.current_responses = new Vector();
        this.group_agenda = new GroupAgenda();
        this.port = 8500;
        this.name = var3;
        this.name_teacher = "teacher: " + this.name;
        this.initialiseHR(var2, this.name_teacher);
        this.initialiseTeacher(var1, var2, var3);
    }

    public Teacher(Vector var1, String var2, String var3, String var4) {
        this.motivation = new Motivation(this.conj, this.attempted_method);
        this.default_request = new Request(this.type, this.num, this.condition, this.theory_constituent_vector, this.motivation);
        this.number_requests_to_work = 0;
        this.current_group_agenda_element = new GroupAgendaElement();
        this.current_responses = new Vector();
        this.group_agenda = new GroupAgenda();
        this.port = 8500;
        this.name = var3;
        this.name_teacher = "teacher: " + this.name;
        this.initialiseHR(var2, this.name_teacher, var4);
        this.initialiseTeacher(var1, var2, var3);
    }

    public void initialiseTeacher(Vector var1, String var2, String var3) {
        this.window.setTitle(this.name_teacher);
        this.window.port = this.port;
        this.port = this.port;
        this.student_names = var1;
        this.determineInitialRequest();
        this.student_names.removeElementAt(this.student_names.size() - 1);
        this.num_students = this.student_names.size();
        this.port = this.port;

        while(this.server_socket == null) {
            try {
                this.server_socket = new ServerSocket(this.port);
            } catch (BindException var7) {
                this.port += 20;
            } catch (IOException var8) {
                this.window.writeToFrontEnd("IOException: can't make ServerSocker" + var8);
            }
        }

        InetAddress var4 = null;

        try {
            var4 = InetAddress.getByName(this.name);
        } catch (Exception var6) {
            System.out.println("Problem getting my_hostname: " + var6);
        }

        this.window.writeToFrontEnd("my name is :" + this.name);
        this.window.writeToFrontEnd("my hostname is :" + var4);
        this.window.writeToFrontEnd("my port is " + this.port);
        this.window.writeToFrontEnd("student_names are " + this.student_names);
        this.window.drawLine();
    }

    public static void main(String[] var0) {
        String var1 = var0[0];
        String var2 = var0[1];
        Vector var3 = new Vector();
        int var4 = 2;
        String var5 = new String();
        if (var0.length >= 2) {
            String var6 = var0[2];
            if (var6.endsWith(".hrm")) {
                var5 = var6;
                ++var4;
            }
        }

        for(int var7 = var4; var7 < var0.length; ++var7) {
            var3.addElement(var0[var7]);
        }

        new Teacher();
        Teacher var8;
        if (var5 == null) {
            var8 = new Teacher(var3, var1, var2);
        } else {
            var8 = new Teacher(var3, var1, var2, var5);
        }

        var8.start();
    }

    public void run() {
        this.getStudentInetAddresses(this.student_names);
        boolean var1 = false;

        while(!var1) {
            if (this.hr.theory.step_counter >= 20) {
                this.hr.theory.step_counter = 0;
                var1 = true;
            }
        }

        this.window.writeToFrontEnd("done initial loop");
        this.window.writeToFrontEnd("hr.theory.use_monster_barring_check is " + this.hr.theory.use_monster_barring);
        this.window.writeToFrontEnd("hr.theory.make_near_equivalences is " + this.hr.theory.make_near_equivalences);
        this.window.writeToFrontEnd("hr.theory.use_communal_piecemeal_exclusion is " + this.hr.theory.use_communal_piecemeal_exclusion);
        this.window.writeToFrontEnd("hr.theory.teacher_requests_nonexists is " + this.hr.theory.teacher_requests_nonexists);
        this.window.writeToFrontEnd("hr.theory.use_lemma_incorporation_check is " + this.hr.theory.use_lemma_incorporation);
        this.window.writeToFrontEnd("hr.theory.lakatos.num_independent_steps is " + this.hr.theory.lakatos.num_independent_steps);
        this.window.drawLine();
        if (this.hr.theory.use_lemma_incorporation) {
            String var2 = this.hr.theory.front_end.force_conjecture_text.getText();
            ProofScheme var3 = new ProofScheme(this.hr.theory, var2);
            String var4 = this.hr.theory.front_end.macro_list.getSelectedItem();
            System.out.println("Teacher: macro_chosen is " + var4);
            System.out.println("proofscheme.conj is " + var3.conj.writeConjecture());
            System.out.println("proofscheme.proof_vector is: ");

            for(int var5 = 0; var5 < var3.proof_vector.size(); ++var5) {
                System.out.println(((Conjecture)var3.proof_vector.elementAt(var5)).writeConjecture());
            }

            System.out.println("I'm the teacher, and I'm starting reconstruct proofscheme here");
            Conjecture var21 = var3.conj.reconstructConjecture(this.hr.theory, this.window);
            System.out.println("Teacher: global conj is " + var21.writeConjecture());
            System.out.println("Teacher: proofscheme.proof_vector.size() is " + var3.proof_vector.size());

            for(int var6 = 0; var6 < var3.proof_vector.size(); ++var6) {
                Conjecture var7 = (Conjecture)var3.proof_vector.elementAt(var6);
                System.out.println("\n\n\nTeacher: ******at the moment we've got conjecture " + var6 + " is: " + var7.writeConjecture());
                Conjecture var8 = var7.reconstructConjecture(this.hr.theory, this.window);
                System.out.println("Teacher: ******local_conj " + var6 + " is: " + var8.writeConjecture());
                System.out.println("\nTeacher: _____________________________________________\n");
            }

            System.out.println("Teacher: - DONE reconstructConjecture");
            this.window.writeToFrontEnd(var3);
            this.default_request.type = "proof scheme";
            this.default_request.condition = "modifies";
            Vector var23 = new Vector();
            var23.addElement(var3);
            this.default_request.theory_constituent_vector = var23;
            this.default_request.motivation.attempted_method = "lemma-incorporation";
        }

        while(true) {
            this.window.writeToFrontEnd("At the beginning of this loop, we have group agenda:");
            this.window.writeToFrontEnd(this.group_agenda.agenda);
            this.window.writeToFrontEnd("The discussion vector is:");
            this.window.writeToFrontEnd(this.discussion_vector);
            if (this.hr.theory.teacher_requests_nonexists) {
                this.default_request.type = "NonExists";
            }

            if (this.hr.theory.teacher_requests_implication) {
                this.default_request.type = "Implication";
            }

            if (this.hr.theory.teacher_requests_nearimplication) {
                this.default_request.type = "NearImplication";
            }

            if (this.hr.theory.teacher_requests_equivalence) {
                this.default_request.type = "Equivalence";
            }

            if (this.hr.theory.teacher_requests_nearequivalence) {
                this.default_request.type = "NearEquivalence";
            }

            if (!this.group_agenda.agenda.isEmpty()) {
                Object var10 = this.group_agenda.agenda.elementAt(0);
                this.window.writeQuietlyToFrontEnd("first element in agenda is " + this.group_agenda.agenda.elementAt(0) + " .... ");
                if (var10 instanceof GroupAgendaStringElement) {
                    this.window.writeQuietlyToFrontEnd("... this is a GroupAgendaStringElement");
                }

                if (var10 instanceof GroupAgendaTheoryConstituentElement) {
                    this.window.writeQuietlyToFrontEnd("... this is a GroupAgendaTheoryConstituentElement");
                }

                if (var10 instanceof GroupAgendaVectorElement) {
                    this.window.writeToFrontEnd("... this is a GroupAgendaVectorElement");
                }

                if (var10 instanceof Request) {
                    this.window.writeQuietlyToFrontEnd("... this is a Request");
                }
            }

            this.window.writeQuietlyToFrontEnd("The current request is " + this.current_request);
            this.window.writeQuietlyToFrontEnd("current_request.isEmpty() is: " + this.current_request.isEmpty());
            this.window.writeQuietlyToFrontEnd("discussion vector: " + this.discussion_vector);
            this.window.writeToFrontEnd("current_responses: " + this.current_responses);
            Request var15;
            if (this.group_agenda.agenda.isEmpty()) {
                this.window.writeQuietlyToFrontEnd("nothing in group agenda at the moment: " + this.group_agenda.agenda);
                var15 = new Request("work independently", this.hr.theory.lakatos.num_independent_steps);
                this.makeCallToAllStudents(this.port, this.student_inetaddresses, var15);
                ++this.num_requests_to_work;
                this.current_request = (Request)this.default_request.clone();
                this.group_file.writeToGroupFile("Teacher: " + this.current_request.toStringForFile());
                this.group_agenda.agenda.addElement(this.current_request);
            } else if (this.group_agenda.agenda.elementAt(0) instanceof Request) {
                this.current_request = (Request)this.group_agenda.agenda.elementAt(0);
                this.group_agenda.agenda.removeElementAt(0);
                var15 = (Request)this.current_request.clone();
                this.discussion_vector.addElement(var15);
                this.makeCallToAllStudents(this.port, this.student_inetaddresses, this.current_request);
                this.listenForResponsesToCurrentRequest(this.server_socket, this.current_request, this.num_students, this.current_responses);
                this.makeCallToAllStudents(this.port, this.student_inetaddresses, this.current_responses);
                this.group_agenda.addResponsesToGroupAgenda(this.current_responses, this.window);
                Vector var18 = this.listenForRequestsToResponses1(this.server_socket, this.current_request, this.num_students);
                this.window.writeQuietlyToFrontEnd("req_to_resp is " + var18);

                for(int var20 = 0; var20 < var18.size(); ++var20) {
                    if (var18.elementAt(var20) instanceof Request) {
                        Request var22 = (Request)var18.elementAt(var20);
                        this.window.writeQuietlyToFrontEnd(var20 + "th item is a request");
                        this.window.writeQuietlyToFrontEnd("group_agenda.agenda just now is " + this.group_agenda.agenda);
                        if (!this.current_request.isEmpty()) {
                            if (this.group_agenda.agenda.elementAt(0) instanceof Request) {
                                Request var24 = (Request)this.group_agenda.agenda.elementAt(0);
                                if (!var22.equals(var24)) {
                                    this.group_agenda.agenda.insertElementAt(var22, 0);
                                }
                            } else {
                                this.group_agenda.agenda.insertElementAt(var22, 0);
                            }
                        }

                        this.window.writeQuietlyToFrontEnd("group_agenda.agenda now is ");
                        this.window.writeQuietlyToFrontEnd(this.group_agenda.agenda);
                    }
                }
            } else if (this.group_agenda.agenda.elementAt(0) instanceof GroupAgendaStringElement) {
                this.window.writeQuietlyToFrontEnd("first element is a GroupAgendaStringElement - taking it out of the agenda and putting it into the dv");
                GroupAgendaStringElement var11 = (GroupAgendaStringElement)this.group_agenda.agenda.elementAt(0);
                this.window.writeQuietlyToFrontEnd("first element is " + var11);
                GroupAgendaStringElement var13 = new GroupAgendaStringElement(var11);
                this.discussion_vector.addElement(var13);
                if (var11.motivation.attempted_method.equals("monster-barring")) {
                    this.window.writeQuietlyToFrontEnd("gase.motivation.attempted_method is m-b");
                    this.current_group_agenda_element = (GroupAgendaElement)this.group_agenda.agenda.elementAt(0);
                    this.group_agenda.agenda.removeElementAt(0);
                    this.window.writeQuietlyToFrontEnd("going to determineCurrentRequest on " + this.current_group_agenda_element);
                    this.determineCurrentRequest();
                    this.window.writeQuietlyToFrontEnd("current_request is NOW " + this.current_request);
                    if (!this.current_request.isEmpty()) {
                        this.window.writeQuietlyToFrontEnd("!current_request.isEmpty() so just inserting into agenda");
                        this.group_agenda.agenda.insertElementAt(this.current_request, 0);
                    } else {
                        boolean var19 = this.current_request.isEmpty();
                        this.window.writeQuietlyToFrontEnd("current_request.isEmpty() is " + var19 + ", so not going to insert anything into agenda");
                    }
                } else {
                    this.group_agenda.agenda.removeElementAt(0);
                }
            } else if (this.group_agenda.agenda.elementAt(0) instanceof GroupAgendaElement) {
                this.current_group_agenda_element = (GroupAgendaElement)this.group_agenda.agenda.elementAt(0);
                if (this.current_group_agenda_element instanceof GroupAgendaTheoryConstituentElement) {
                    GroupAgendaTheoryConstituentElement var12 = (GroupAgendaTheoryConstituentElement)this.current_group_agenda_element;
                    GroupAgendaTheoryConstituentElement var16 = new GroupAgendaTheoryConstituentElement(var12);
                    this.discussion_vector.addElement(var16);
                }

                if (this.current_group_agenda_element instanceof GroupAgendaVectorElement) {
                    GroupAgendaVectorElement var14 = (GroupAgendaVectorElement)this.current_group_agenda_element;
                    GroupAgendaVectorElement var17 = new GroupAgendaVectorElement(var14);
                    this.discussion_vector.addElement(var17);
                }

                this.group_agenda.agenda.removeElementAt(0);
                this.determineCurrentRequest();
                if (!this.current_request.isEmpty()) {
                    this.group_agenda.agenda.insertElementAt(this.current_request, 0);
                }
            }

            try {
                sleep(2000L);
            } catch (Exception var9) {
                ;
            }

            this.window.drawLine();
        }
    }

    public void determineCurrentRequest() {
        this.window.writeQuietlyToFrontEnd("\n\n into determineCurrentRequest()");
        this.window.writeQuietlyToFrontEnd("current_request is " + this.current_request);
        this.window.writeQuietlyToFrontEnd("current_group_agenda_element is " + this.current_group_agenda_element + "\n\n");
        new Request();
        boolean var2 = false;
        GroupAgendaTheoryConstituentElement var3;
        Conjecture var4;
        Vector var5;
        if (this.hr.theory.use_communal_piecemeal_exclusion) {
            Vector var18;
            if (this.current_group_agenda_element instanceof GroupAgendaTheoryConstituentElement) {
                this.window.writeToFrontEnd("got a GroupAgendaTheoryConstituentElement");
                var3 = (GroupAgendaTheoryConstituentElement)this.current_group_agenda_element;
                if (var3.theory_constituent instanceof Conjecture) {
                    var4 = (Conjecture)var3.theory_constituent;
                    if (var3.motivation.attempted_method.equals("to get discussion started")) {
                        this.current_request.type = "Entity";
                        this.current_request.num = 100;
                        this.current_request.condition = "breaks";
                        var5 = new Vector();
                        var5.addElement(var4);
                        this.current_request.theory_constituent_vector = var5;
                        this.current_request.motivation.conjecture_under_discussion = var4;
                        this.current_request.motivation.attempted_method = "communal piecemeal exclusion";
                    }

                    boolean var19 = this.motivation.conjecture_under_discussion.equals(var4);
                    if (!var19) {
                        this.current_request.type = "Entity";
                        this.current_request.num = 100;
                        this.current_request.condition = "breaks";
                        Vector var6 = new Vector();
                        var6.addElement(var4);
                        this.current_request.theory_constituent_vector = var6;
                        this.current_request.motivation.conjecture_under_discussion = var4;
                        this.current_request.motivation.attempted_method = "communal piecemeal exclusion";
                    }
                }

                if (var3.theory_constituent instanceof Concept) {
                    Concept var17 = (Concept)var3.theory_constituent;
                    Conjecture var20 = this.current_request.motivation.conjecture_under_discussion;
                    new Concept();
                    new Concept();
                    Concept var7;
                    Concept var23;
                    if (var20 instanceof Implication) {
                        Implication var8 = (Implication)var20;
                        var23 = var8.lh_concept;
                        var7 = var8.rh_concept;
                    }

                    if (var20 instanceof NearImplication) {
                        NearImplication var25 = (NearImplication)var20;
                        var23 = var25.lh_concept;
                        var7 = var25.rh_concept;
                    }

                    if (var20 instanceof Equivalence) {
                        Equivalence var27 = (Equivalence)var20;
                        var23 = var27.lh_concept;
                        var7 = var27.rh_concept;
                    }

                    if (var20 instanceof NearEquivalence) {
                        NearEquivalence var29 = (NearEquivalence)var20;
                        var23 = var29.lh_concept;
                        var7 = var29.rh_concept;
                    }

                    this.current_request.type = "Conjecture";
                    this.current_request.condition = "modifies";
                    Vector var31 = new Vector();
                    var31.addElement(var20);
                    this.current_request.theory_constituent_vector = var31;
                    this.current_request.motivation.conjecture_under_discussion = var20;
                    this.current_request.motivation.attempted_method = "individual methods";
                    this.group_agenda.removeElementsWithGivenMotivation(var20);
                }

                if (var3.theory_constituent instanceof Entity) {
                    var18 = new Vector();
                    var18.addElement((Entity)var3.theory_constituent);
                    this.current_request.type = "Concept";
                    this.current_request.condition = "covers";
                    this.current_request.theory_constituent_vector = var18;
                }
            }

            if (this.current_group_agenda_element instanceof GroupAgendaVectorElement) {
                this.window.writeQuietlyToFrontEnd("****************************** got a GroupAgendaVectorElement");
                GroupAgendaVectorElement var15 = (GroupAgendaVectorElement)this.current_group_agenda_element;
                var18 = var15.vector;
                this.window.writeQuietlyToFrontEnd("vector_of_entities is " + var18);
                if (var15.vector.isEmpty()) {
                    this.window.writeQuietlyToFrontEnd("the vector_of_entities is empty");
                    this.current_request = new Request();
                } else {
                    this.current_request.type = "Concept";
                    this.current_request.condition = "covers";
                    this.current_request.theory_constituent_vector = var18;
                }
            }
        } else if (this.current_group_agenda_element instanceof GroupAgendaTheoryConstituentElement) {
            this.window.writeQuietlyToFrontEnd("2) got a GroupAgendaTheoryConstituentElement");
            var3 = (GroupAgendaTheoryConstituentElement)this.current_group_agenda_element;
            if (var3.theory_constituent instanceof Conjecture) {
                var4 = (Conjecture)var3.theory_constituent;
                this.current_request.type = "Conjecture";
                this.current_request.condition = "modifies";
                var5 = new Vector();
                var5.addElement(var4);
                this.current_request.theory_constituent_vector = var5;
                this.current_request.motivation.conjecture_under_discussion = var4;
                this.current_request.motivation.attempted_method = "individual methods";
            }
        }

        if (this.current_group_agenda_element instanceof Request) {
            this.window.writeQuietlyToFrontEnd("got a Request");
        }

        if (this.current_group_agenda_element instanceof GroupAgendaStringElement) {
            this.window.writeToFrontEnd("got a GroupAgendaStringElement");
            GroupAgendaStringElement var16 = (GroupAgendaStringElement)this.current_group_agenda_element;
            if (var16.motivation.attempted_method.equals("monster-barring")) {
                int var21 = 0;
                int var22 = 0;
                if (var16.string.equals("reject proposal to bar entity")) {
                    ++var21;
                }

                if (var16.string.equals("accept proposal to bar entity")) {
                    ++var22;
                }

                this.window.writeToFrontEnd("going through agenda to look for other votes");
                this.window.writeToFrontEnd("before looking, got votes_to_accept_entity is " + var21 + ", and votes_to_bar_entity is " + var22);
                this.window.writeToFrontEnd("before looking, agenda is " + this.agenda);

                for(int var24 = 0; var24 < this.group_agenda.agenda.size(); ++var24) {
                    GroupAgendaElement var26 = (GroupAgendaElement)this.group_agenda.agenda.elementAt(var24);
                    if (var26 instanceof GroupAgendaStringElement) {
                        GroupAgendaStringElement var33 = (GroupAgendaStringElement)this.group_agenda.agenda.elementAt(0);
                        if (var33.motivation.attempted_method.equals("monster-barring") && var33.motivation.entity_under_discussion.name.equals(var16.motivation.entity_under_discussion.name)) {
                            this.window.writeToFrontEnd("gase is " + var33);
                            if (var33.string.equals("reject proposal to bar entity")) {
                                ++var21;
                            }

                            if (var33.string.equals("accept proposal to bar entity")) {
                                ++var22;
                            }

                            GroupAgendaStringElement var9 = new GroupAgendaStringElement(var33);
                            this.discussion_vector.addElement(var9);
                            this.group_agenda.agenda.removeElementAt(var24);
                            --var24;
                        }
                    }
                }

                this.window.writeToFrontEnd("been through agenda to look for other votes");
                this.window.writeToFrontEnd("votes_to_accept_entity is " + var21 + ", and  votes_to_bar_entity is " + var22);
                this.window.writeQuietlyToFrontEnd("before looking, agenda is " + this.agenda);
                boolean var30 = var21 >= var22;
                this.window.writeToFrontEnd("votes_to_accept_entity >= votes_to_bar_entity is " + var30);
                Vector var28 = new Vector();
                var28.addElement(var16.motivation.entity_under_discussion);
                if (var21 > var22) {
                    this.window.writeToFrontEnd("votes_to_accept_entity >= votes_to_bar_entity");
                    this.current_request.type = "";
                    this.current_request.num = 0;
                    this.current_request.condition = "";
                    this.current_request.theory_constituent_vector = var28;
                    this.current_request.motivation = var16.motivation;
                    this.current_request.string_object = "perform addEntityToTheory";
                } else {
                    this.current_request.type = "";
                    this.current_request.num = 0;
                    this.current_request.condition = "";
                    this.current_request.theory_constituent_vector = var28;
                    this.current_request.motivation = var16.motivation;
                    this.current_request.string_object = "perform downgradeEntityToPseudoEntity";

                    for(int var34 = 0; var34 < this.current_request.theory_constituent_vector.size(); ++var34) {
                        this.current_request.theory_constituent_vector.elementAt(var34);
                    }

                    Request var35 = new Request();
                    var35.type = "Entity";
                    var35.num = 100;
                    var35.condition = "breaks";
                    Vector var32 = new Vector();
                    Conjecture var10 = new Conjecture();
                    this.window.writeToFrontEnd("the last element of the dv is " + this.discussion_vector.elementAt(this.discussion_vector.size() - 1));
                    boolean var11 = this.discussion_vector.elementAt(this.discussion_vector.size() - 1) instanceof Request;
                    boolean var12 = this.discussion_vector.elementAt(this.discussion_vector.size() - 1) instanceof GroupAgendaElement;
                    this.window.writeToFrontEnd("got a Request is " + var11);
                    this.window.writeToFrontEnd("got a GroupAgendaElement is " + var12);
                    if (this.discussion_vector.elementAt(this.discussion_vector.size() - 1) instanceof Request) {
                        Request var13 = (Request)this.discussion_vector.elementAt(this.discussion_vector.size() - 1);
                        var10 = var13.motivation.conjecture_under_discussion;
                    }

                    if (this.discussion_vector.elementAt(this.discussion_vector.size() - 1) instanceof GroupAgendaElement) {
                        GroupAgendaElement var36 = (GroupAgendaElement)this.discussion_vector.elementAt(this.discussion_vector.size() - 1);
                        var10 = var36.motivation.conjecture_under_discussion;
                    }

                    this.window.writeToFrontEnd("conj_to_add is " + var10.writeConjecture());
                    var32.addElement(var10);
                    var35.theory_constituent_vector = var32;
                    var35.motivation.attempted_method = "communal piecemeal exclusion";

                    for(int var37 = 0; var37 < var28.size(); ++var37) {
                        Entity var14 = (Entity)var28.elementAt(var37);
                        this.group_agenda.removeElementsWithConjecture(var10, this.window);
                    }

                    this.group_agenda.agenda.insertElementAt(var35, 0);
                    this.window.writeToFrontEnd("FRIDAY -- just inserted " + var35 + "into the agenda");
                }

                this.window.writeQuietlyToFrontEnd("2) current_request after collecting votes is " + this.current_request.toString());
                this.group_file.writeToGroupFile("Teacher: We'll decide democratically with the default being that " + var16.motivation.entity_under_discussion.name + " is a monster. Can you all " + this.current_request.toStringForFile());
                var2 = true;
            }
        }

        if (!var2 && !this.current_request.type.equals("")) {
            this.group_file.writeToGroupFile("Teacher: " + this.current_request.toStringForFile());
        }

    }

    public void orderResponses(Vector var1) {
        new Vector();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            var1.elementAt(var3);
        }

    }

    public Vector combineResponses(Vector var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Response var4 = (Response)var1.elementAt(var3);
            Vector var5 = var4.response_vector;
            if (!var5.isEmpty() && var5.elementAt(0) instanceof Entity) {
                for(int var6 = 0; var6 < var5.size(); ++var6) {
                    Entity var7 = (Entity)var5.elementAt(var6);
                    var2.addElement(var7);
                }
            }
        }

        return var2;
    }

    public Conjecture sortReceivedConjectures(Vector var1) {
        SortableVector var2 = new SortableVector();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Conjecture var4 = (Conjecture)var1.elementAt(var3);
            var2.addElement(var4, "interestingness");
        }

        this.received_objects.removeElementAt(var2.size() - 1);
        return (Conjecture)var2.lastElement();
    }

    public void getStudentInetAddresses(Vector var1) {
        for(int var2 = 0; var2 < var1.size(); ++var2) {
            try {
                String var3 = (String) var1.elementAt(var2);
                int var4 = var3.indexOf(58);
                String var5 = var3.substring(0, var4);
                String var6 = var3.substring(var4 + 1);
                InetAddress var7 = null;

                try {
                    var7 = InetAddress.getByName(var5);
                    this.student_inetaddresses.addElement(var7);
                    this.student_inetaddresses.addElement(var6);
                } catch (Exception var9) {
                    System.out.println("Problem getting LocalHost: " + var9);
                }
            } catch (Exception e) {
                System.out.println("Problem getting student: " + e.getMessage());
            }
        }
    }

    public Vector determineFocus(Vector var1) {
        Vector var2 = new Vector();
        Object var3 = "";
        String var4 = "";
        String var5 = "";
        if (var1.isEmpty()) {
            return new Vector();
        } else {
            Object var6 = var1.elementAt(0);
            if (var6 instanceof Conjecture) {
                Conjecture var7 = this.sortReceivedConjectures(var1);
                var3 = var7;
                var4 = "Entity";
                var5 = "breaks conjecture";
            }

            if (var6 instanceof Entity) {
                var3 = "counters";
                var4 = "Concept";
                var5 = "covers counters";
            }

            if (var6 instanceof Concept) {
                ;
            }

            var2.addElement(var3);
            var2.addElement(var4);
            var2.addElement(var5);
            return var2;
        }
    }

    public ProofScheme constructProofScheme() {
        Conjecture var1 = new Conjecture();
        Conjecture var2 = new Conjecture();
        Vector var3 = new Vector();

        for(int var4 = 0; var4 < this.hr.theory.conjectures.size(); ++var4) {
            Conjecture var5 = (Conjecture)this.hr.theory.conjectures.elementAt(var4);
            if (var5.id.equals("2")) {
                var1 = var5;
            }

            if (var5.id.equals("6")) {
                var2 = var5;
            }
        }

        String var7 = new String();
        ProofSchemeElement var8 = new ProofSchemeElement(var7, var2);
        var3.addElement(var8);
        ProofScheme var6 = new ProofScheme(var1, var3);
        return var6;
    }

    public void determineInitialRequest() {
        if (this.hr.theory.teacher_requests_conjectures) {
            this.default_request.type = "Conjecture";
        }

        if (this.hr.theory.teacher_requests_nonexists) {
            this.default_request.type = "NonExists";
        }

        if (this.hr.theory.teacher_requests_implication) {
            this.default_request.type = "Implication";
        }

        if (this.hr.theory.teacher_requests_nearimplication) {
            this.default_request.type = "NearImplication";
        }

        if (this.hr.theory.teacher_requests_equivalence) {
            this.default_request.type = "Equivalence";
        }

        if (this.hr.theory.teacher_requests_nearequivalence) {
            this.default_request.type = "NearEquivalence";
        }

    }
}
