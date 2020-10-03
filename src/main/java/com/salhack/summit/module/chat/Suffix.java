package com.salhack.summit.module.chat;

import com.salhack.summit.events.bus.EventHandler;
import com.salhack.summit.events.bus.Listener;
import com.salhack.summit.events.player.EventPlayerSendChatMessage;
import com.salhack.summit.module.Module;
import com.salhack.summit.module.Value;
import net.minecraft.network.play.client.CPacketChatMessage;

public class Suffix extends Module {
    public final Value<String> Mode = new Value<>("Mode", new String[] { "M" }, "Suffix Mode", "SummitPlus");

    public Suffix()
    {
        super("ChatSuffix", new String[] { "Suffix" }, "Prevents fall damage", "NONE", 0x4BCA5C, ModuleType.CHAT);

        Mode.addString("SummitPlus");
        Mode.addString("nhack");
        Mode.addString("ImpactPlus");
        Mode.addString("Salhack");
        Mode.addString("Future");
    }
    @Override
    public String getMetaData() {
        return Mode.getValue().toString();
    }

    @EventHandler
    private Listener<EventPlayerSendChatMessage> OnSendChatMsg = new Listener<>(p_Event ->
    {
        if (p_Event.Message.startsWith("/"))
            return;

        String l_Message = "";

        switch (Mode.getValue()) {
            case "SummitPlus": {
                l_Message = " - \ua731\u1d1c\u1d0d\u1d0d\u026a\u1d1b\u002b";
                break;
            }
            case "nhack": {
                l_Message = " - \u0274\u029c\u1d00\u1d04\u1d0b";
                break;
            }
            case "ImpactPlus": {
                l_Message = " - \u026a\u1d0d\u1d18\u1d00\u1d04\u1d1b\u002b";
                break;
            }
            case "Salhack": {
                l_Message = " - \ua731\u1d00\u029f\u029c\u1d00\u1d04\u1d0b";
                break;
            }
            case "Future": {
                l_Message = " - \ua730\u1d1c\u1d1b\u1d1c\u0280\u1d07";
                break;
            }
        }

        p_Event.cancel();
        mc.getConnection().sendPacket(new CPacketChatMessage(p_Event.Message + l_Message));

    });

}








