package com.salhack.summit.events.render;

import com.salhack.summit.events.MinecraftEvent;
import net.minecraft.item.ItemStack;

public class EventRenderTooltip extends MinecraftEvent
{
    private ItemStack Item;
    private int X;
    private int Y;

    public EventRenderTooltip(ItemStack p_Stack, int p_X, int p_Y)
    {
        Item = p_Stack;
        X = p_X;
        Y = p_Y;
    }

    public ItemStack getItemStack()
    {
        return Item;
    }

    public int getX()
    {
        return X;
    }

    public int getY()
    {
        return Y;
    }

}
