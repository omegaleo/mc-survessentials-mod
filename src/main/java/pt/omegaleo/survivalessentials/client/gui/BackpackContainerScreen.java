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

    //Normally defined as render method
    @Override
    public void func_230430_a_(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) 
    {
        this.func_231160_c_(); // Render Background?
        super.func_230430_a_(matrix, mouseX, mouseY, partialTicks);
    }


    //drawGuiContainerBackgroundLayer?
    @Override
    protected void func_230450_a_(MatrixStack stack, float partialTicks, int mouseX, int mouseY) 
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.field_230706_i_.getTextureManager().bindTexture(TEXTURE);
                    //this.width
        int posX = (this.field_230708_k_ - this.xSize) / 2;
                    //this.height
        int posY = (this.field_230709_l_ - this.ySize) / 2;

        //blit function
        this.func_238474_b_(stack, posX, posY, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.func_238474_b_(stack, posX, posY + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
        
    }


    //drawGuiContainerForegroundLayer
    @Override
    protected void func_230451_b_(MatrixStack stack, int mouseX, int mouseY) 
    {
        //this.font
        this.field_230712_o_.func_243248_b(stack,this.field_230704_d_, 8, 6, 4210752);
        this.field_230712_o_.func_243248_b(stack, this.playerInventory.getDisplayName(), 8, this.ySize - 96 + 2, 4210752);
    }
}