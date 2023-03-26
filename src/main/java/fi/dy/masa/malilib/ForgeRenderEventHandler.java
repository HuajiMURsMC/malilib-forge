package fi.dy.masa.malilib;

import net.minecraft.client.MinecraftClient;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import fi.dy.masa.malilib.event.RenderEventHandler;

class ForgeRenderEventHandler
{
    @SubscribeEvent
    public void onRenderTooltipPost(RenderTooltipEvent.Pre event)
    {
        ((RenderEventHandler) RenderEventHandler.getInstance()).onRenderTooltipLast(event.getPoseStack(), event.getItemStack(), event.getX(), event.getY());
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderLevelStageEvent event)
    {
        net.minecraft.client.render.Camera cam = MinecraftClient.getInstance().gameRenderer.getCamera();
        float yaw = cam.getYaw();
        float pitch = cam.getPitch();
        org.lwjgl.opengl.GL11.glPushMatrix();
        org.lwjgl.opengl.GL11.glRotatef(pitch, 1, 0, 0);
        org.lwjgl.opengl.GL11.glRotatef(yaw+180, 0, 1, 0);
        ((RenderEventHandler) RenderEventHandler.getInstance()).onRenderWorldLast(event.getPoseStack(), event.getProjectionMatrix(), MinecraftClient.getInstance());
        org.lwjgl.opengl.GL11.glPopMatrix();
    }
}
