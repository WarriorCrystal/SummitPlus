package com.salhack.summit.module.chat;

import com.salhack.summit.events.player.EventPlayerSendChatMessage;
import com.salhack.summit.module.Module;
import com.salhack.summit.module.Value;
import com.salhack.summit.events.bus.EventHandler;
import com.salhack.summit.events.bus.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.client.model.ICustomModelLoader;

public final class ChatSuffixModule extends Module {
    public final Value<Modes> Mode = new Value<Modes>("Mode", new String[]{"M"}, "suffix", Modes.SummitPlus);


    public enum Modes {
        SummitPlus,
    }

    public ChatSuffixModule()
    {
        super("ChatSuffix", new String[]
                { "ChatSuffix" }, "ChatSuffix", "NONE", 0xB49FAD, ModuleType.CHAT);
        setMetaData(getMetaData());

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
            case SummitPlus: {
                l_Message = " - \ua731\u1d1c\u1d0d\u1d0d\u026a\u1d1b\u002b";

                break;
            }

        }

        p_Event.cancel();
        mc.getConnection().sendPacket(new CPacketChatMessage(p_Event.Message + l_Message));
    });
}


