package pt.omegaleo.survivalessentials.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.containers.PortableChargerContainer;
import pt.omegaleo.survivalessentials.items.PortableChargerItem;

public class PortableChargerContainerScreen extends ContainerScreen<PortableChargerContainer> 
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/drill.png");
    private final PlayerInventory playerInventory;
    private final int inventoryRows;
    private final PortableChargerContainer container;

    public PortableChargerContainerScreen(PortableChargerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) 
    {
        super(screenContainer, inv, titleIn);
        this.playerInventory = inv;
        this.inventoryRows = screenContainer.getInventoryRows();
        this.container = screenContainer;
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
        this.font.drawString(matrixStack, title.getString(), 8, 6, 4210752);
        ItemStack stack = getEnergyStorage();
        if(stack != null)
        {
            PortableChargerItem energy = (PortableChargerItem)stack.getItem();
            if(energy != null)
            {
                this.font.drawString(matrixStack, "Energy: " + energy.getEnergy(stack) + "/" + energy.MaxEnergy(), 8, this.ySize - 50, 4210752);
            }
        }

        this.font.drawString(matrixStack, playerInventory.getDisplayName().getString(), 8, this.ySize - 40, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) 
    {
        if (minecraft == null) return;

        // Render the GUI texture
        RenderSystem.enableBlend();
        GlStateManager.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bindTexture(TEXTURE);
        int posX = (this.width - this.xSize) / 2;
        int posY = (this.height - this.ySize) / 2;
        // blit(posX, posY, minU, minV, maxU, maxV)
        blit(matrixStack, posX, posY, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        blit(matrixStack, posX, posY + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }


    private ItemStack getEnergyStorage()
    {
        PlayerEntity player = minecraft.player;

        if(player != null) //should never be null but just in case
        {
            ItemStack mainHandItem = player.getHeldItemMainhand();
            ItemStack offHandItem = player.getHeldItemOffhand();
            if(mainHandItem.getItem() instanceof PortableChargerItem)
            {
                return mainHandItem;
            }
            else if(offHandItem.getItem() instanceof PortableChargerItem)
            {
                return offHandItem;
            }
        }

        return null;
    }
}