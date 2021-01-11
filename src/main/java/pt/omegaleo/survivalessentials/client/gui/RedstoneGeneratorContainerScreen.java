package pt.omegaleo.survivalessentials.client.gui;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.containers.BookContainer;
import pt.omegaleo.survivalessentials.containers.ContainerRedstoneGenerator;
import pt.omegaleo.survivalessentials.inventory.BackpackContainer;
import pt.omegaleo.survivalessentials.tileentity.TileEntityRedstoneGenerator;

public class RedstoneGeneratorContainerScreen extends ContainerScreen<ContainerRedstoneGenerator> 
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/redstone_generator.png");
    private static String title = "";
    private static TileEntityRedstoneGenerator tileEntity;

    public RedstoneGeneratorContainerScreen(ContainerRedstoneGenerator screenContainer, PlayerInventory inv,
            ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        title = titleIn.getString();
        this.tileEntity = screenContainer.tileentity;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) 
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) 
    {
        this.font.drawString(matrixStack, title, (this.xSize / 2 - this.font.getStringWidth(title) / 2) - 5, 6, 4210752);
        this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 7, this.ySize - 96 + 2, 4210752);

        if(this.tileEntity != null)
        {
            this.font.drawString(matrixStack, Integer.toString(this.tileEntity.getEnergyStored()), 115, 72, 4210752);
        }
        else
        {
            this.font.drawString(matrixStack, "TileEntity null", 115, 72, 4210752);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) 
    {
        if (minecraft == null) return;

        // Render the GUI texture
        RenderSystem.enableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bindTexture(TEXTURE);
        int posX = this.width / 2;
        int posY = this.height / 2;
        // blit(posX, posY, minU, minV, maxU, maxV)
        blit(matrixStack, posX, posY, 0, 0, this.width, this.height);        
    }
}