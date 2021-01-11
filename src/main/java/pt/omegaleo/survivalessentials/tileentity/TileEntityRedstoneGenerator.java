package pt.omegaleo.survivalessentials.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import pt.omegaleo.survivalessentials.ModItems;
import pt.omegaleo.survivalessentials.energy.CustomEnergyStorage;

public class TileEntityRedstoneGenerator extends TileEntity implements ITickable
{
    public ItemStackHandler handler = new ItemStackHandler(1);

    private CustomEnergyStorage storage = new CustomEnergyStorage(10000);
    public int energy = storage.getEnergyStored();

    public int cookTime;

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) 
    {
        if(cap == CapabilityEnergy.ENERGY) return LazyOptional.of(() -> this.storage).cast();
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return LazyOptional.of(() -> this.handler).cast();
        return super.getCapability(cap, side);
    }

    public TileEntityRedstoneGenerator() 
    {
        super(TileEntityType.FURNACE);
    }

    @Override
    public void tick()
    {
        if(!handler.getStackInSlot(0).isEmpty() && isItemFuel(handler.getStackInSlot(0)))
        {
            cookTime++;
            if(cookTime == 25)
            {
                energy += getFuelValue(handler.getStackInSlot(0));
                handler.getStackInSlot(0).shrink(1);
                cookTime = 0;
            }
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) 
    {
        super.write(compound);
        compound.put("Inventory", this.handler.serializeNBT());
        compound.putInt("CookTime", cookTime);
        compound.putInt("GuiEnergy", this.energy);

        this.storage.writeToNBT(compound);

        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) 
    {
        super.read(state, nbt);
        this.handler.deserializeNBT(nbt.getCompound("Inventory"));
        this.cookTime = nbt.getInt("CookTime");
        this.energy = nbt.getInt("GuiEnergy");
        this.storage.readFromNBT(nbt);
    }

    public int getEnergyStored()
    {
        return this.energy;
    }

    public int getMaxEnergyStored()
    {
        return this.storage.getMaxEnergyStored();
    }

    public int getField(int id)
    {
        switch(id)
        {
            case 0:
                return this.energy;
            case 1:
                return this.cookTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch(id)
        {
            case 0:
                this.energy = value;
            case 1:
                this.cookTime = value;
        }
    }
    
    private boolean isItemFuel(ItemStack stack) 
	{
		return getFuelValue(stack) > 0;
	}
	
	private int getFuelValue(ItemStack stack) 
	{
        if(stack.getItem() == Items.REDSTONE) return 1000;
        if(stack.getItem() == Items.REDSTONE_BLOCK) return 9000;
        if(stack.getItem() == ModItems.REDSTONE_COMPOUND.get()) return 5000;
        if(stack.getItem() == ModItems.REDSTONE_INGOT.get()) return 5500;
		else return 0;
	}
}
