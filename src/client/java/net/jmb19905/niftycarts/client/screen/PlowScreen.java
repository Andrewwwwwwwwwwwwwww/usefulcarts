package net.jmb19905.niftycarts.client.screen;

import net.jmb19905.niftycarts.NiftyCarts;
import net.jmb19905.niftycarts.container.PlowMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public final class PlowScreen extends AbstractContainerScreen<@NotNull PlowMenu> {
    private static final Identifier PLOW_GUI_TEXTURES = NiftyCarts.resLoc("textures/gui/container/plow.png");

    public PlowScreen(final PlowMenu screenContainer, final Inventory inv, final Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void extractBackground(@NotNull final GuiGraphicsExtractor guiGraphics, final int mouseX, final int mouseY, final float partialTicks) {
        final int i = (this.width - this.imageWidth) / 2;
        final int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, PLOW_GUI_TEXTURES, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
    }
}