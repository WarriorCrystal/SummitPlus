package com.salhack.summit.events.render;

import com.salhack.summit.events.MinecraftEvent;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;

public class EventRenderEntity extends MinecraftEvent
{
    private Entity entity;

    public EventRenderEntity(Entity entityIn, ICamera camera, double camX, double camY, double camZ)
    {
        entity = entityIn;
    }

    public Entity GetEntity()
    {
        return entity;
    }

}
