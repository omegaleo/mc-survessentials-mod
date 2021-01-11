package pt.omegaleo.survivalessentials.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import pt.omegaleo.survivalessentials.ModContainerTypes;
import pt.omegaleo.survivalessentials.tileentity.TileEntityRedstoneGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerRedstoneGenerator extends Container
{
	public TileEntityRedstoneGenerator tileentity;
	private int energy, cookTime;
	
	public ContainerRedstoneGenerator(int id, PlayerInventory player) 
	{
        super(ModContainerTypes.redstone_generator, id);
        
        if(tileentity != null)
        {
            IItemHandler handler = tileentity.handler;

            if(handler != null)
            {
                this.addSlot(new SlotItemHandler(handler, 0, 80, 33));
            }        
        }
        else
        {
            tileentity = new TileEntityRedstoneGenerator();
            IItemHandler handler = tileentity.handler;

            if(handler != null)
            {
                this.addSlot(new SlotItemHandler(handler, 0, 80, 33));
            } 
        }
		
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlot(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
			}
		}
		
		for(int x = 0; x < 9; x++)
		{
			this.addSlot(new Slot(player, x, 8 + x * 18, 142));
		}
    }

    public void setTileentity(TileEntity tileentity) 
    {
        if(tileentity instanceof TileEntityRedstoneGenerator)
            this.tileentity = (TileEntityRedstoneGenerator) tileentity;
    }

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) 
	{
		return true;
	}
	
	@Override
	public void updateProgressBar(int id, int data) 
	{
		this.tileentity.setField(id, data);
	}
	
	@Override
	public void detectAndSendChanges() 
	{
		super.detectAndSendChanges();
		
		this.energy = this.tileentity.getField(0);
		this.cookTime = this.tileentity.getField(1);
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack())
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if(index >= 0 && index < 27)
			{
				if(!this.mergeItemStack(stack1, 27, 36, false)) return ItemStack.EMPTY;
			}
			else if(index >= 27 && index < 36)
			{
				if(!this.mergeItemStack(stack1, 0, 27, false)) return ItemStack.EMPTY;
			}
			else if(!this.mergeItemStack(stack1, 0, 36, false))
			{
				return ItemStack.EMPTY;
			}
			
			if(stack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
			else slot.onSlotChanged();
			
			if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		
		return stack;
    }
    
    
}