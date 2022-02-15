package pt.omegaleo.survivalessentials.client.gui;

import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.containers.BookContainer;
import pt.omegaleo.survivalessentials.inventory.BackpackContainer;

public class BookContainerScreen extends AbstractContainerScreen<BookContainer> 
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

    private final Inventory playerInventory;
    private final int inventoryRows;

    public BookContainerScreen(BookContainer screenContainer, Inventory inv, Component titleIn) 
    {
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
    protected void renderBg(PoseStack matrixStack, float p_97788_, int p_97789_, int p_97790_) {
        if (minecraft == null) return;

        // Render the GUI texture
        RenderSystem.enableBlend();
        GlStateManager._clearColor(1, 1, 1, 1);
        minecraft.getTextureManager().getTexture(TEXTURE);
        int posX = (this.width - 256) / 2;
        int posY = (this.height - 256) / 2;
        // blit(posX, posY, minU, minV, maxU, maxV)
        blit(matrixStack, posX, posY, 0, 0, 256, 256);
        minecraft.getTextureManager().getTexture(PAGES[currentPage]);
        blit(matrixStack, posX, posY, 0, 0, 256, 256);

        Button prevPage = new Button(posX - 64, posY + 64, 16, 16, new TranslatableComponent("gui.book.prev"), (button) -> {
            if(currentPage > 0)
            {
                currentPage--;
            }
        });
        addRenderableWidget(prevPage);
        Button nextPage = new Button(posX + 256 + 64, posY + 64, 16, 16, new TranslatableComponent("gui.book.next"), (button) -> {
            if(currentPage < PAGES.length-1)
            {
                currentPage++;
            }
        });
        addRenderableWidget(nextPage);
    }
}