package com.salhack.summit.module.combat;

import com.salhack.summit.events.MinecraftEvent;
import com.salhack.summit.events.bus.EventHandler;
import com.salhack.summit.events.bus.Listener;
import com.salhack.summit.events.player.EventPlayerMotionUpdate;
import com.salhack.summit.managers.ModuleManager;
import com.salhack.summit.module.Module;
import com.salhack.summit.module.Value;
import com.salhack.summit.module.misc.HotbarCache;
import com.salhack.summit.util.BlockInteractionHelper;
import com.salhack.summit.util.entity.EntityUtil;
import com.salhack.summit.util.entity.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.util.EnumHand;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import scala.Int;

import java.util.ArrayList;
import java.util.Comparator;


// Setting up class and objects
public class BedAura extends Module {

    public final Value<Integer> Radius = new Value<Integer>("Radius", new String[]
            {"R"}, "Radius for right clicking beds", 4, 0, 5, 0);
    public final Value<Float> RequiredHealth = new Value<Float>("RequiredHealth", new String[]
            {"RH"}, "RequiredHealth for BedAura to function, must be above or equal to this amount.", 11.0f, 0.0f, 20.0f, 1.0f);
    public final Value<Boolean> EnableHotbarCache = new Value<Boolean>("EnableHotbarCache", new String[]
            { "EHC" }, "Enables hotbar cache if not already enabled. ", true);

    public BedAura() {
        super("BedAura", new String[]{"BedAura"}, "Automatically right clicks beds.", "NONE", 0xFFFB11, Module.ModuleType.COMBAT);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        if (EnableHotbarCache.getValue())
        {
            final Module mod = ModuleManager.Get().GetMod(HotbarCache.class);

            if (!mod.isEnabled())
                mod.toggle();
        }
    }

    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate = new Listener<>(p_Event ->
    {
        if (p_Event.getEra() != MinecraftEvent.Era.PRE)
            return;

        BlockPos l_ClosestPos = BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), Radius.getValue(), Radius.getValue(), false, true, 0).stream()
                .filter(p_Pos -> IsValidBlockPos(p_Pos))
                .min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock(mc.player, p_Pos)))
                .orElse(null);

        if (l_ClosestPos != null)
        {
            boolean hasBed = mc.player.getHeldItemMainhand().getItem() == Items.BED;

            if (!hasBed)
            {
                for (int i = 0; i < 9; ++i)
                {
                    ItemStack stack = mc.player.inventory.getStackInSlot(i);

                    if (stack.isEmpty())
                        continue;

                    if (stack.getItem() == Items.BED)
                    {
                        hasBed = true;
                        mc.player.inventory.currentItem = i;
                        mc.playerController.updateController();
                        break;
                    }
                }
            }

            if (!hasBed)
                return;

            p_Event.cancel();

            final double l_Pos[] = EntityUtil.calculateLookAt(
                    l_ClosestPos.getX(),
                    l_ClosestPos.getY(),
                    l_ClosestPos.getZ(),
                    mc.player);

            PlayerUtil.PacketFacePitchAndYaw((float)l_Pos[1], (float)l_Pos[0]);

            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(
                    l_ClosestPos, EnumFacing.UP, EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F)
            );
        }
    });

    private boolean IsValidBlockPos(final BlockPos p_Pos)
    {
        IBlockState l_State = mc.world.getBlockState(p_Pos);

        if (l_State.getBlock() instanceof BlockBed)
            return true;

        return false;
    }
}