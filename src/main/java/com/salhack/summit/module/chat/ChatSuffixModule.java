package com.salhack.summit.module.chat;

import com.salhack.summit.events.player.EventPlayerSendChatMessage;
import com.salhack.summit.module.Module;
import com.salhack.summit.module.Value;
import com.salhack.summit.events.bus.EventHandler;
import com.salhack.summit.events.bus.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.client.model.ICustomModelLoader;

public final class ChatSuffixModule extends Module {
    public final Value<Modes> mode = new Value<Modes>("Mode", new String[]{"M"}, "pro chat append", Modes.standard);


    // I added nhack and impact+ cuz y not lol
    public enum Modes {
        standard,
        nhack,
        Impactplus,
    }

    public ChatSuffixModule() {
        super("ChatSuffix", new String[]
                        {""}, "Makes your chat have a string appended on the end", "NONE",
                0xDB2485, ModuleType.CHAT);
    }

    @Override
    public String getMetaData() {
        return mode.getValue().toString();
    }

    @EventHandler
    private Listener<EventPlayerSendChatMessage> OnSendChatMsg = new Listener<>(p_Event ->
    {
        if (p_Event.Message.startsWith("/"))
            return;

        String l_Message = "";

        switch (mode.getValue()) {
            case standard: {
                l_Message = "  | \ua731\u1d1c\u1d0d\u1d0d\u026a\u1d1b\u002b";

                break;
            }
            case nhack: {
                l_Message = "  | \u0274\u029c\u1d00\u1d04\u1d0b";
                break;
            }
            case Impactplus: {
                l_Message = "  | \u026a\u1d0d\u1d18\u1d00\u1d04\u1d1b\u002b";
                break;
            }
        }

        p_Event.cancel();
        mc.getConnection().sendPacket(new CPacketChatMessage(p_Event.Message + l_Message));
    });
}


