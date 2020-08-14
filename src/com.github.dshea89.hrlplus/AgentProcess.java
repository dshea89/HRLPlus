package com.github.dshea89.hrlplus;

import java.lang.Process;

public abstract class AgentProcess extends Process
{
    static int count = 1;
    String id;

    public AgentProcess()
    {
        id = "AgentProcess" + count++;
    }
}
