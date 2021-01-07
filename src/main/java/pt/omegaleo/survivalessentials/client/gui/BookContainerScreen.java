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
import pt.omegaleo.survivalessentials.inventory.BackpackContainer;

public class BookContainerScreen extends ContainerScreen<BookContainer> 
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/book.png");
    private int currentPage = 0;
    private static final ResourceLocation[] PAGES = new ResourceLocation[]
    {
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page1.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page2.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page3.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page4.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page5.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page6.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page7.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page8.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page9.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page10.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page11.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page12.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page13.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page14.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page15.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page16.png"),
        new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/pages/page17.png")
    };

    public BookContainerScreen(BookContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) 
    {
        super(screenContainer, inv, titleIn);
        this.xSize = 256;
        this.ySize = 256;
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
        /*if (minecraft == null) return;

        // Render the GUI texture
        RenderSystem.enableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bindTexture(PAGES[0]);
        int posX = (this.width - 256) / 2;
        int posY = (this.height - 256) / 2;
        blit(matrixStack, posX, posY, 0, 0, 256, 256);*/
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) 
    {
        if (minecraft == null) return;

        // Render the GUI texture
        RenderSystem.enableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bindTexture(TEXTURE);
        int posX = (this.width - 256) / 2;
        int posY = (this.height - 256) / 2;
        // blit(posX, posY, minU, minV, maxU, maxV)
        blit(matrixStack, posX, posY, 0, 0, 256, 256);
        minecraft.getTextureManager().bindTexture(PAGES[currentPage]);
        blit(matrixStack, posX, posY, 0, 0, 256, 256);

        Button prevPage = new Button(posX - 64, posY + 64, 16, 16, new TranslationTextComponent("gui.book.prev"), (button) -> {
            if(currentPage > 0)
            {
                currentPage--;
            }
        });
        addButton(prevPage);

        Button nextPage = new Button(posX + 256 + 64, posY + 64, 16, 16, new TranslationTextComponent("gui.book.next"), (button) -> {
            if(currentPage < PAGES.length-1)
            {
                currentPage++;
            }
        });
        addButton(nextPage);
        
    }
}