package net.jmb19905.niftycarts.client.screen;

import net.jmb19905.niftycarts.NiftyCarts;
import net.jmb19905.niftycarts.container.SeedDrillMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class SeedDrillScreen extends AbstractContainerScreen<@NotNull SeedDrillMenu> {
    private static final Identifier SEED_DRILL_GUI_TEXTURES = NiftyCarts.resLoc("textures/gui/container/seed_drill.png");

    public SeedDrillScreen(SeedDrillMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    public void extractBackground(@NotNull final GuiGraphicsExtractor guiGraphics, final int mouseX, final int mouseY, final float partialTicks) {
        final int i = (this.width - this.imageWidth) / 2;
        final int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, SEED_DRILL_GUI_TEXTURES, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
    }
}