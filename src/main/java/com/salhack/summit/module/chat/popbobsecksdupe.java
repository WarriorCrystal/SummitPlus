package com.salhack.summit.module.chat;

import com.salhack.summit.module.Module;

import java.util.Random;

public class popbobsecksdupe extends Module {

    public popbobsecksdupe()
    {
        super("PopBobSexDupe", new String[]
                        { "PopBobSexDupe" }, "Have sexual intercorse with PopBob for items to dupe.", "NONE",
                0xDB2485, Module.ModuleType.CHAT);
    }

    @Override
    public void onEnable() {
        if(mc.player != null)
            mc.player.sendChatMessage("I just performed the PopBob sex dupe and got " +  (new Random().nextInt(30) + 1) + " shulkers!");
        toggle();
    }
}