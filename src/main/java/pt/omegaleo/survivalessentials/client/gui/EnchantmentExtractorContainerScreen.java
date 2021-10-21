package pt.omegaleo.survivalessentials.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.containers.EnchantmentExtractorContainer;

public class EnchantmentExtractorContainerScreen extends ContainerScreen<EnchantmentExtractorContainer> {

    private EnchantmentExtractorContainer containerFurnace;

    public EnchantmentExtractorContainerScreen(EnchantmentExtractorContainer containerFurnace,
            PlayerInventory playerInventory, ITextComponent title) {
        super(containerFurnace, playerInventory, title);
        this.containerFurnace = containerFurnace;

        // Set the width and height of the gui. Should match the size of the texture!
        xSize = 176;
        ySize = 207;
    }

    // some [x,y] coordinates of graphical elements
    final int COOK_BAR_XPOS = 49;
    final int COOK_BAR_YPOS = 60;
    final int COOK_BAR_ICON_U = 0; // texture position of white arrow icon [u,v]
    final int COOK_BAR_ICON_V = 207;
    final int COOK_BAR_WIDTH = 80;
    final int COOK_BAR_HEIGHT = 17;

    final int FLAME_XPOS = 54;
    final int FLAME_YPOS = 80;
    final int FLAME_ICON_U = 176; // texture position of flame icon [u,v]
    final int FLAME_ICON_V = 0;
    final int FLAME_WIDTH = 14;
    final int FLAME_HEIGHT = 14;
    final int FLAME_X_SPACING = 18;


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        //super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
        final float LABEL_XPOS = 8;
        final float LABEL_YPOS = 5;
        font.drawString(matrixStack, title.getString(), LABEL_XPOS, LABEL_YPOS, Color.fromHex("#03f4fc").getColor());
        this.font.drawString(matrixStack, playerInventory.getDisplayName().getString(), 8, 115, 4210752);
    }

    // Returns true if the given x,y coordinates are within the given rectangle
    public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
        return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
    }

    // This is the resource location for the background image
    private static final ResourceLocation TEXTURE = new ResourceLocation(SurvivalEssentialsMod.MOD_ID,
            "textures/gui/enchantment_extractor.png");

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);

        // width and height are the size provided to the window when initialised after
        // creation.
        // xSize, ySize are the expected size of the texture-? usually seems to be left
        // as a default.
        // The code below is typical for vanilla containers, so I've just copied that-
        // it appears to centre the texture within
        // the available window
        int edgeSpacingX = (this.width - this.xSize) / 2;
        int edgeSpacingY = (this.height - this.ySize) / 2;
        this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.xSize, this.ySize);
    }
}