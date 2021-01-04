package pt.omegaleo.survivalessentials.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import pt.omegaleo.survivalessentials.inventory.BackpackContainer;

public class BackpackContainerScreen extends ContainerScreen<BackpackContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");

    private final PlayerInventory playerInventory;
    private final int inventoryRows;

    public BackpackContainerScreen(BackpackContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.playerInventory = inv;
        this.inventoryRows = screenContainer.getInventoryRows();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.font.drawString(matrixStack, title.getString(), 8, 6, 4210752);
        this.font.drawString(matrixStack, playerInventory.getDisplayName().getString(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        if (minecraft == null) return;

        // Render the GUI texture
        GlStateManager.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bindTexture(TEXTURE);
        int posX = (this.width - this.xSize) / 2;
        int posY = (this.height - this.ySize) / 2;
        // blit(posX, posY, minU, minV, maxU, maxV)
        blit(matrixStack, posX, posY, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        blit(matrixStack, posX, posY + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}