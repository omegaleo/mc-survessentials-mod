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
import net.minecraft.util.text.TranslationTextComponent;
import pt.omegaleo.survivalessentials.ModItems;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.containers.DrillContainer;
import pt.omegaleo.survivalessentials.util.enums.DrillUpgrade;
import pt.omegaleo.survivalessentials.util.tools.DrillTool;

public class DrillContainerScreen extends ContainerScreen<DrillContainer> 
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/drill.png");
    private final PlayerInventory playerInventory;
    private final int inventoryRows;
    private final DrillContainer container;

    public DrillContainerScreen(DrillContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) 
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
        ItemStack tool = getDrillTool();
        if(tool != null)
        {
            this.font.drawString(matrixStack, "Durability: " + (tool.getMaxDamage() - tool.getDamage()) + "/" + tool.getMaxDamage(), 8, this.ySize - 50, 4210752);
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

            Button button1 = new Button(posX + 20, posY +20, 24, 18, new TranslationTextComponent("gui.drill.1"), (button) -> 
            {
                PlayerEntity player = minecraft.player;
                DrillTool tool = null;
                ItemStack stack = null;

                if(player != null) //should never be null but just in case
                {
                    ItemStack mainHandItem = player.getHeldItemMainhand();
                    ItemStack offHandItem = player.getHeldItemOffhand();
                    if(mainHandItem.getItem() instanceof DrillTool)
                    {
                        tool = (DrillTool)mainHandItem.getItem();
                        stack = mainHandItem;
                        tool.SetCurrentRadius(1, stack);
                    }
                    else if(offHandItem.getItem() instanceof DrillTool)
                    {
                        tool = (DrillTool)offHandItem.getItem();
                        stack = offHandItem;
                        tool.SetCurrentRadius(1, stack);
                    }
                }
            });
            addButton(button1);
    
            Button button3 = new Button(posX + 20, posY + 40, 24, 18, new TranslationTextComponent("gui.drill.3"), (button) -> 
            {
                PlayerEntity player = minecraft.player;
                DrillTool tool = null;
                ItemStack stack = null;

                if(player != null) //should never be null but just in case
                {
                    ItemStack mainHandItem = player.getHeldItemMainhand();
                    ItemStack offHandItem = player.getHeldItemOffhand();
                    if(mainHandItem.getItem() instanceof DrillTool)
                    {
                        tool = (DrillTool)mainHandItem.getItem();
                        stack = mainHandItem;
                        tool.SetCurrentRadius(3, stack);
                    }
                    else if(offHandItem.getItem() instanceof DrillTool)
                    {
                        tool = (DrillTool)offHandItem.getItem();
                        stack = offHandItem;
                        tool.SetCurrentRadius(3, stack);
                    }
                }
            });
            addButton(button3);
            
            if(container.hasUpgrade((DrillUpgrade)ModItems.FIVE_BLOCKS.get()))
            {
                Button button5 = new Button(posX + 20, posY + 60, 24, 18, new TranslationTextComponent("gui.drill.5"), (button) -> 
                {
                    PlayerEntity player = minecraft.player;
                    DrillTool tool = null;
                    ItemStack stack = null;

                    if(player != null) //should never be null but just in case
                    {
                        ItemStack mainHandItem = player.getHeldItemMainhand();
                        ItemStack offHandItem = player.getHeldItemOffhand();
                        if(mainHandItem.getItem() instanceof DrillTool)
                        {
                            tool = (DrillTool)mainHandItem.getItem();
                            stack = mainHandItem;
                            tool.SetCurrentRadius(5, stack);
                        }
                        else if(offHandItem.getItem() instanceof DrillTool)
                        {
                            tool = (DrillTool)offHandItem.getItem();
                            stack = offHandItem;
                            tool.SetCurrentRadius(5, stack);
                        }
                    }
                });
                addButton(button5);
            }

            if(container.hasUpgrade((DrillUpgrade)ModItems.SEVEN_BLOCKS.get()))
            {
                Button button7 = new Button(posX + 20, posY + 80, 24, 18, new TranslationTextComponent("gui.drill.7"), (button) -> 
                {
                    PlayerEntity player = minecraft.player;
                    DrillTool tool = null;
                    ItemStack stack = null;
    
                    if(player != null) //should never be null but just in case
                    {
                        ItemStack mainHandItem = player.getHeldItemMainhand();
                        ItemStack offHandItem = player.getHeldItemOffhand();
                        if(mainHandItem.getItem() instanceof DrillTool)
                        {
                            tool = (DrillTool)mainHandItem.getItem();
                            stack = mainHandItem;
                            tool.SetCurrentRadius(7, stack);
                        }
                        else if(offHandItem.getItem() instanceof DrillTool)
                        {
                            tool = (DrillTool)offHandItem.getItem();
                            stack = offHandItem;
                            tool.SetCurrentRadius(7, stack);
                        }
                    }
                });
                addButton(button7);
            }
    }


    private ItemStack getDrillTool()
    {
        PlayerEntity player = minecraft.player;

        if(player != null) //should never be null but just in case
        {
            ItemStack mainHandItem = player.getHeldItemMainhand();
            ItemStack offHandItem = player.getHeldItemOffhand();
            if(mainHandItem.getItem() instanceof DrillTool)
            {
                return mainHandItem;
            }
            else if(offHandItem.getItem() instanceof DrillTool)
            {
                return offHandItem;
            }
        }

        return null;
    }
}