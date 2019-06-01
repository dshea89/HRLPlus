package com.github.dshea89.hrlplus;

public abstract class AgentProcess extends Process {
    static int count = 1;
    String id;

    public AgentProcess() {
        this.id = "AgentProcess" + count++;
    }
}
