package pt.omegaleo.survivalessentials.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import pt.omegaleo.survivalessentials.containers.PortableChargerContainer;
import pt.omegaleo.survivalessentials.util.enums.EnergyStorageItem;

public class PortableChargerItem extends EnergyStorageItem
{

    public PortableChargerItem(int maxEnergy, boolean isFull) 
    {
        super(maxEnergy);
        if(isFull)
        {
            this.setCurrentEnergy(maxEnergy);
        }
    }

    public PortableChargerItem(int maxEnergy) 
    {
        super(maxEnergy);
    }

    

    public int getInventorySize(ItemStack stack) {
        return 2;
    }

    public IItemHandler getInventory(ItemStack stack) {
        ItemStackHandler stackHandler = new ItemStackHandler(getInventorySize(stack));
        stackHandler.deserializeNBT(stack.getOrCreateTag().getCompound("Inventory"));
        return stackHandler;
    }

    public void saveInventory(ItemStack stack, IItemHandler itemHandler) {
        if (itemHandler instanceof ItemStackHandler) {
            stack.getOrCreateTag().put("Inventory", ((ItemStackHandler) itemHandler).serializeNBT());
        }
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
    {
        if (!worldIn.isRemote) 
        {
            playerIn.openContainer(new SimpleNamedContainerProvider(
                    (id, playerInventory, player) -> new PortableChargerContainer(id, playerInventory),
                    new TranslationTextComponent("container.survivalessentials.pcharger")
            ));
        }
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
