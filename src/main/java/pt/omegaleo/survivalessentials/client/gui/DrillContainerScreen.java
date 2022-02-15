package pt.omegaleo.survivalessentials.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import pt.omegaleo.survivalessentials.ModItems;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.containers.DrillContainer;
import pt.omegaleo.survivalessentials.util.enums.DrillUpgrade;
import pt.omegaleo.survivalessentials.util.tools.DrillTool;

public class DrillContainerScreen extends AbstractContainerScreen<DrillContainer> 
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(SurvivalEssentialsMod.MOD_ID,"textures/gui/drill.png");
    private final Inventory playerInventory;
    private final int inventoryRows;
    private final DrillContainer container;

    public DrillContainerScreen(DrillContainer screenContainer, Inventory inv, Component titleIn) 
    {
        super(screenContainer, inv, titleIn);
        this.playerInventory = inv;
        this.inventoryRows = screenContainer.getInventoryRows();
        this.container = screenContainer;
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
        int posX = (this.width - this.getXSize()) / 2;
        int posY = (this.height - this.getYSize()) / 2;
        // blit(posX, posY, minU, minV, maxU, maxV)
        blit(matrixStack, posX, posY, 0, 0, this.getXSize(), this.inventoryRows * 18 + 17);
        blit(matrixStack, posX, posY + this.inventoryRows * 18 + 17, 0, 126, this.getXSize(), 96);

        Button button1 = new Button(posX + 20, posY +20, 24, 18, new TranslatableComponent("gui.drill.1"), (button) ->
        {
            Player player = minecraft.player;
            DrillTool tool = null;
            ItemStack stack = player.getMainHandItem();

            if(player != null) //should never be null but just in case
            {
                ItemStack mainHandItem = player.getMainHandItem();
                ItemStack offHandItem = player.getOffhandItem();
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
        addRenderableWidget(button1);

        Button button3 = new Button(posX + 20, posY + 40, 24, 18, new TranslatableComponent("gui.drill.3"), (button) ->
        {
            Player player = minecraft.player;
            DrillTool tool = null;
            ItemStack stack = null;

            if(player != null) //should never be null but just in case
            {
                ItemStack mainHandItem = player.getMainHandItem();
                ItemStack offHandItem = player.getOffhandItem();
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
        addRenderableWidget(button3);

        if(container.hasUpgrade((DrillUpgrade)ModItems.FIVE_BLOCKS.get()))
        {
            Button button5 = new Button(posX + 20, posY + 60, 24, 18, new TranslatableComponent("gui.drill.5"), (button) ->
            {
                Player player = minecraft.player;
                DrillTool tool = null;
                ItemStack stack = null;

                if(player != null) //should never be null but just in case
                {
                    ItemStack mainHandItem = player.getMainHandItem();
                    ItemStack offHandItem = player.getOffhandItem();
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
            addRenderableWidget(button5);
        }

        if(container.hasUpgrade((DrillUpgrade)ModItems.SEVEN_BLOCKS.get()))
        {
            Button button7 = new Button(posX + 20, posY + 80, 24, 18, new TranslatableComponent("gui.drill.7"), (button) ->
            {
                Player player = minecraft.player;
                DrillTool tool = null;
                ItemStack stack = null;

                if(player != null) //should never be null but just in case
                {
                    ItemStack mainHandItem = player.getMainHandItem();
                    ItemStack offHandItem = player.getOffhandItem();
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
            addRenderableWidget(button7);
        }
    }


    private ItemStack getDrillTool()
    {
        Player player = minecraft.player;

        if(player != null) //should never be null but just in case
        {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();
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