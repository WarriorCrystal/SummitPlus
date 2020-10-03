package com.salhack.summit.module.combat;

import com.salhack.summit.events.bus.EventHandler;
import com.salhack.summit.events.bus.Listener;
import com.salhack.summit.events.network.EventNetworkPacketEvent;
import com.salhack.summit.module.Module;
import com.salhack.summit.module.Value;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public class CriticalsModule extends Module
{
    public final Value<Modes> Mode = new Value<Modes>("Mode", new String[] {"M"}, "Mode to change to for criticals", Modes.Packet);

    public enum Modes
    {
        Packet,
        //Jump,
    }


    public CriticalsModule()
    {
        super("Criticals", new String[]
                { "BS" }, "Tries to crit your oponent on attack by spoofing positions", "NONE", 0xF2190E, ModuleType.COMBAT);
    }

    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent = new Listener<>(p_Event ->
    {
        if (p_Event.getPacket() instanceof CPacketUseEntity)
        {
            CPacketUseEntity l_Packet = (CPacketUseEntity)p_Event.getPacket();

            if (l_Packet.getAction() == CPacketUseEntity.Action.ATTACK)
            {
                //case Jump:
                    mc.player.jump();
                    //break;

                if (l_Packet.getEntityFromWorld(mc.world) instanceof EntityLivingBase && mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown())
                {
                    switch (Mode.getValue())
                    {

                        case Packet:
                            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                            break;
                        default:
                            break;
                        //jump before the hit so you will get the crit cus now the second mod is usles.
                    }
                }
            }
        }
    });
}