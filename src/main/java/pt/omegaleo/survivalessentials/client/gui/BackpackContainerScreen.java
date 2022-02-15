package pt.omegaleo.survivalessentials.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import pt.omegaleo.survivalessentials.inventory.BackpackContainer;

public class BackpackContainerScreen extends AbstractContainerScreen<BackpackContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");

    private final Inventory playerInventory;
    private final int inventoryRows;

    public BackpackContainerScreen(BackpackContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.playerInventory = inv;
        this.inventoryRows = screenContainer.getInventoryRows();
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBg(matrixStack, mouseX, mouseY, (int)partialTicks);
        this.font.draw(matrixStack, title.getString(), 8, 6, 4210752);
        this.font.draw(matrixStack, playerInventory.getDisplayName().getString(), 8, this.getYSize() - 96 + 2, 4210752);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {
        if (minecraft == null) return;

        // Render the GUI texture
        GlStateManager._clearColor(1, 1, 1, 1);
        minecraft.getTextureManager().getTexture(TEXTURE);
        int posX = (this.width - this.getXSize()) / 2;
        int posY = (this.height - this.getYSize()) / 2;
        // blit(posX, posY, minU, minV, maxU, maxV)
        blit(p_97787_, posX, posY, 0, 0, this.getXSize(), this.inventoryRows * 18 + 17);
        blit(p_97787_, posX, posY + this.inventoryRows * 18 + 17, 0, 126, this.getXSize(), 96);
    }
}