package com.github.dshea89.hrlplus;

import java.util.Vector;

public class GroupAgenda extends Vector {
    public Vector agenda = new Vector();
    public boolean best_first_responses = false;
    public boolean best_first_agenda = false;
    public boolean combine_responses = false;
    public boolean depth_first = true;
    public boolean breadth_first = false;

    public GroupAgenda() {
    }

    public GroupAgenda(Vector var1) {
        this.agenda = var1;
    }

    public String getType(Response var1) {
        String var2 = var1.request.type;
        return var2;
    }

    public String getType(TheoryConstituent var1) {
        String var2 = "";
        if (var1 instanceof Concept) {
            var2 = "Concept";
        }

        if (var1 instanceof Conjecture) {
            var2 = "Conjecture";
        }

        if (var1 instanceof Entity) {
            var2 = "Entity";
        }

        return var2;
    }

    public boolean responsesContainsTheoryConstituents(Vector var1) {
        boolean var2 = false;
        if (var1.isEmpty()) {
            return var2;
        } else {
            for(int var3 = 0; var3 < var1.size(); ++var3) {
                Response var4 = (Response)var1.elementAt(var3);
                Vector var5 = var4.response_vector;
                if (!var5.isEmpty()) {
                    var2 = true;
                    return true;
                }
            }

            return var2;
        }
    }

    public Vector orderResponses(Vector var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            GroupAgendaElement var4 = (GroupAgendaElement)var1.elementAt(var3);
            this.insertElementIntoGroupAgenda(var4, var2);
        }

        return var2;
    }

    public Vector unpackResponses(Vector var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Response var4 = (Response)var1.elementAt(var3);
            Vector var5 = var4.response_vector;
            Motivation var6 = var4.request.motivation;
            if (!var5.isEmpty() && var5.elementAt(0) instanceof String) {
                String var7 = (String)var5.elementAt(0);
                GroupAgendaStringElement var8 = new GroupAgendaStringElement(var7, var6);
                var2.addElement(var8);
            }

            if (!var5.isEmpty() && var5.elementAt(0) instanceof Entity) {
                GroupAgendaVectorElement var10 = new GroupAgendaVectorElement(var5, var6);
                var2.addElement(var10);
            }

            if (!var5.isEmpty() && !(var5.elementAt(0) instanceof String)) {
                for(int var11 = 0; var11 < var5.size(); ++var11) {
                    if (var5.elementAt(var11) instanceof TheoryConstituent) {
                        TheoryConstituent var12 = (TheoryConstituent)var5.elementAt(var11);
                        GroupAgendaTheoryConstituentElement var9 = new GroupAgendaTheoryConstituentElement(var12, var6);
                        var2.addElement(var9);
                    }
                }
            }
        }

        return var2;
    }

    public void addResponsesToGroupAgenda(Vector var1, AgentWindow var2) {
        for(int var3 = 0; var3 < var1.size(); ++var3) {
            Response var4 = (Response)var1.elementAt(var3);
        }

        Vector var15 = this.unpackResponses(var1);

        GroupAgendaElement var5;
        int var16;
        for(var16 = 0; var16 < var15.size(); ++var16) {
            var5 = (GroupAgendaElement)var15.elementAt(var16);
        }

        Vector var17;
        if (this.combine_responses) {
            var17 = new Vector();
            byte var18 = 0;
            GroupAgendaVectorElement var6 = (GroupAgendaVectorElement)var15.elementAt(var18);
            Motivation var7 = var6.motivation;

            for(int var8 = 0; var8 < var15.size(); ++var8) {
                GroupAgendaElement var9 = (GroupAgendaElement)var15.elementAt(var8);
                Motivation var10 = var9.motivation;
                if (var9 instanceof GroupAgendaVectorElement && var10.equals(var7)) {
                    GroupAgendaVectorElement var11 = (GroupAgendaVectorElement)var9;
                    Vector var12 = var11.vector;

                    for(int var13 = 0; var13 < var12.size(); ++var13) {
                        Entity var14 = (Entity)var12.elementAt(var13);
                        var17.addElement(var14);
                    }
                }
            }

            var15 = var17;
        }

        if (this.best_first_responses) {
            var17 = this.orderResponses(var15);

            for(int var19 = var17.size() - 1; var19 >= 0; --var19) {
                this.agenda.insertElementAt(var17.elementAt(var19), 0);
            }
        }

        if (this.best_first_agenda) {
            for(var16 = 0; var16 < var15.size(); ++var16) {
                var5 = (GroupAgendaElement)var15.elementAt(var16);
                this.insertElementIntoGroupAgenda(var5, this.agenda);
            }
        }

        if (this.depth_first) {
            for(var16 = var15.size() - 1; var16 >= 0; --var16) {
                this.agenda.insertElementAt(var15.elementAt(var16), 0);
            }
        }

        if (this.breadth_first) {
            for(var16 = 0; var16 < var15.size(); ++var16) {
                this.agenda.addElement(var15.elementAt(var16));
            }
        }

        var1.removeAllElements();
        this.agenda = this.removeDuplicatesFromVector(this.agenda, var2);
    }

    public void removeElementsWithGivenMotivation(Conjecture var1) {
        for(int var2 = 0; var2 < this.agenda.size(); ++var2) {
            GroupAgendaElement var3 = (GroupAgendaElement)this.agenda.elementAt(var2);
            if (var3.motivation.conjecture_under_discussion.equals(var1)) {
                this.agenda.removeElementAt(var2);
                --var2;
            }
        }

    }

    public void insertElementIntoGroupAgenda(Conjecture var1, GroupAgendaElement var2, Vector var3) {
        boolean var4 = false;

        for(int var5 = 0; var5 < var3.size() && !var4; ++var5) {
            Object var6 = var3.elementAt(var5);
            if (var6 instanceof GroupAgendaTheoryConstituentElement) {
                GroupAgendaTheoryConstituentElement var7 = (GroupAgendaTheoryConstituentElement)var6;
                if (var7.theory_constituent instanceof Conjecture) {
                    Conjecture var8 = (Conjecture)var7.theory_constituent;
                    if (var1.interestingness >= var8.interestingness) {
                        var3.insertElementAt(var2, var5);
                        var4 = true;
                    }
                }
            }
        }

        if (!var4) {
            var3.addElement(var2);
        }

    }

    public void insertElementIntoGroupAgenda(Concept var1, GroupAgendaElement var2, Vector var3) {
        boolean var4 = false;

        for(int var5 = 0; var5 < var3.size() && !var4; ++var5) {
            GroupAgendaElement var6 = (GroupAgendaElement)var3.elementAt(var5);
            if (var6 instanceof GroupAgendaTheoryConstituentElement) {
                GroupAgendaTheoryConstituentElement var7 = (GroupAgendaTheoryConstituentElement)var6;
                if (var7.theory_constituent instanceof Concept) {
                    Concept var8 = (Concept)var7.theory_constituent;
                    if (var1.interestingness >= var8.interestingness) {
                        var3.insertElementAt(var2, var5);
                        var4 = true;
                    }
                }
            }
        }

        if (!var4) {
            var3.addElement(var2);
        }

    }

    public void insertElementIntoGroupAgenda(Vector var1, GroupAgendaElement var2, Vector var3) {
        var3.addElement(var2);
    }

    public void insertElementIntoGroupAgenda(GroupAgendaElement var1, Vector var2) {
        if (var1 instanceof GroupAgendaTheoryConstituentElement) {
            GroupAgendaTheoryConstituentElement var3 = (GroupAgendaTheoryConstituentElement)var1;
            this.insertElementIntoGroupAgenda(var3, var2);
        }

        if (var1 instanceof GroupAgendaVectorElement) {
            GroupAgendaVectorElement var4 = (GroupAgendaVectorElement)var1;
            this.insertElementIntoGroupAgenda(var4, var2);
        }

    }

    public void insertElementIntoGroupAgenda(GroupAgendaTheoryConstituentElement var1, Vector var2) {
        if (var1.theory_constituent instanceof Concept) {
            Concept var3 = (Concept)var1.theory_constituent;
            this.insertElementIntoGroupAgenda((Concept)var3, var1, var2);
        }

        if (var1.theory_constituent instanceof Conjecture) {
            Conjecture var4 = (Conjecture)var1.theory_constituent;
            this.insertElementIntoGroupAgenda((Conjecture)var4, var1, var2);
        }

    }

    public void insertElementIntoGroupAgenda(GroupAgendaVectorElement var1, Vector var2) {
        Vector var3 = var1.vector;
        this.insertElementIntoGroupAgenda((Vector)var3, var1, var2);
    }

    public boolean checkResponsesToSameRequest(Vector var1) {
        boolean var2 = true;
        if (var1.isEmpty()) {
            return var2;
        } else {
            Response var3 = (Response)var1.elementAt(0);
            Request var4 = var3.request;

            for(int var5 = 1; var5 < var1.size(); ++var5) {
                Response var6 = (Response)var1.elementAt(var5);
                if (!var6.request.equals(var4)) {
                    var2 = false;
                    break;
                }
            }

            return var2;
        }
    }

    public Vector removeDuplicatesFromVector(Vector var1, AgentWindow var2) {
        Vector var3 = new Vector();
        byte var4 = 0;

        while(var4 < var1.size()) {
            boolean var5 = true;
            Object var6 = var1.elementAt(var4);
            var2.writeToFrontEnd("got object " + var6);
            if (var6 instanceof GroupAgendaElement) {
                GroupAgendaElement var7 = (GroupAgendaElement)var1.elementAt(var4);
                boolean var8 = var1.isEmpty();
                var1.removeElementAt(var4);

                for(int var9 = 0; var9 < var1.size(); ++var9) {
                    Object var10 = var1.elementAt(var9);
                    if (var10 instanceof GroupAgendaElement) {
                        GroupAgendaElement var11 = (GroupAgendaElement)var1.elementAt(var9);
                        if (var11.equals(var7)) {
                            var5 = false;
                        }
                    }
                }
            }

            int var15;
            Object var18;
            if (var6 instanceof Concept) {
                Concept var12 = (Concept)var1.elementAt(var4);
                var1.removeElementAt(var4);

                for(var15 = 0; var15 < var1.size(); ++var15) {
                    var18 = var1.elementAt(var15);
                    if (var18 instanceof Concept) {
                        Concept var19 = (Concept)var1.elementAt(var15);
                        if (var19.equals(var12)) {
                            var5 = false;
                        }
                    }
                }
            }

            if (var6 instanceof Conjecture) {
                Conjecture var13 = (Conjecture)var1.elementAt(var4);
                var1.removeElementAt(var4);

                for(var15 = 0; var15 < var1.size(); ++var15) {
                    var18 = var1.elementAt(var15);
                    if (var18 instanceof Conjecture) {
                        Conjecture var20 = (Conjecture)var1.elementAt(var15);
                        if (var20.equals(var13)) {
                            var5 = false;
                        }
                    }
                }
            }

            if (var6 instanceof Entity) {
                Entity var14 = (Entity)var1.elementAt(var4);
                var1.removeElementAt(var4);

                for(var15 = 0; var15 < var1.size(); ++var15) {
                    var18 = var1.elementAt(var15);
                    if (var18 instanceof Entity) {
                        Entity var21 = (Entity)var1.elementAt(var15);
                        if (var21.equals(var14)) {
                            var5 = false;
                        }
                    }
                }
            }

            if (var6 instanceof Response) {
                Response var16 = (Response)var1.elementAt(var4);
                var1.removeElementAt(var4);

                for(var15 = 0; var15 < var1.size(); ++var15) {
                    var18 = var1.elementAt(var15);
                    if (var18 instanceof Response) {
                        Response var22 = (Response)var1.elementAt(var15);
                        if (var22.equals(var16)) {
                            var5 = false;
                        }
                    }
                }
            }

            if (var6 instanceof String) {
                String var17 = (String)var1.elementAt(var4);
                var1.removeElementAt(var4);

                for(var15 = 0; var15 < var1.size(); ++var15) {
                    var18 = var1.elementAt(var15);
                    if (var18 instanceof String) {
                        String var23 = (String)var1.elementAt(var15);
                        if (var23.equals(var17)) {
                            var5 = false;
                        }
                    }
                }
            }

            if (var5) {
                var3.addElement(var6);
            }
        }

        return var3;
    }

    public void removeElementsWithConjecture(Conjecture var1, AgentWindow var2) {
        var2.writeToFrontEnd("we want to eliminate all elements in the agenda with this conj:" + var1.writeConjecture());

        for(int var3 = 0; var3 < this.agenda.size(); ++var3) {
            GroupAgendaElement var4 = (GroupAgendaElement)this.agenda.elementAt(var3);
            var2.writeToFrontEnd("the conj of the current element is: " + var4.motivation.conjecture_under_discussion.writeConjecture());
            boolean var5 = var4.motivation.conjecture_under_discussion.writeConjecture().equals(var1.writeConjecture());
            var2.writeToFrontEnd("gae.motivation.conjecture_under_discussion.writeConjecture().equals(conjecture.writeConjecture()) is " + var5);
            if (var4.motivation.conjecture_under_discussion.writeConjecture().equals(var1.writeConjecture())) {
                this.agenda.removeElementAt(var3);
                --var3;
            }
        }

    }

    public void removeEntity(Entity var1, AgentWindow var2) {
        var2.writeQuietlyToFrontEnd("started removeEntity on " + var1);
        var2.writeQuietlyToFrontEnd("at the moment, agenda is " + this.agenda);

        for(int var3 = 0; var3 < this.agenda.size(); ++var3) {
            GroupAgendaElement var4 = (GroupAgendaElement)this.agenda.elementAt(var3);
            if (var4 instanceof GroupAgendaStringElement) {
                GroupAgendaStringElement var5 = (GroupAgendaStringElement)var4;
                if (var5.motivation.entity_under_discussion.name.equals(var1.name)) {
                    this.agenda.removeElementAt(var3);
                }
            }

            if (var4 instanceof GroupAgendaTheoryConstituentElement) {
                GroupAgendaTheoryConstituentElement var8 = (GroupAgendaTheoryConstituentElement)var4;
                if (var8.theory_constituent instanceof Entity) {
                    Entity var6 = (Entity)var8.theory_constituent;
                    if (var6.name.equals(var1.name)) {
                        this.agenda.removeElementAt(var3);
                    }
                }
            }

            Entity var7;
            int var11;
            if (var4 instanceof GroupAgendaVectorElement) {
                GroupAgendaVectorElement var9 = (GroupAgendaVectorElement)var4;

                for(var11 = 0; var11 < var9.vector.size(); ++var11) {
                    if (var9.vector.elementAt(var11) instanceof Entity) {
                        var7 = (Entity)var9.vector.elementAt(var11);
                        if (var7.name.equals(var1.name)) {
                            var9.vector.removeElementAt(var11);
                        }
                    }
                }
            }

            if (var4 instanceof Request) {
                Request var10 = (Request)var4;
                if (var10.motivation.entity_under_discussion.name.equals(var1.name)) {
                    this.agenda.removeElementAt(var3);
                }

                for(var11 = 0; var11 < var10.theory_constituent_vector.size(); ++var11) {
                    if (var10.theory_constituent_vector.elementAt(var11) instanceof Entity) {
                        var7 = (Entity)var10.theory_constituent_vector.elementAt(var11);
                        if (var7.name.equals(var1.name)) {
                            var10.theory_constituent_vector.removeElementAt(var11);
                        }
                    }
                }
            }
        }

        var2.writeQuietlyToFrontEnd("finished removeEntity on " + var1);
        var2.writeQuietlyToFrontEnd("agenda is now " + this.agenda);
    }
}
