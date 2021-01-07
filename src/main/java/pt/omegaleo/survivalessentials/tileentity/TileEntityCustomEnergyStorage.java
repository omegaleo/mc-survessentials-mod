package pt.omegaleo.survivalessentials.tileentity;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import pt.omegaleo.survivalessentials.energy.CustomEnergyStorage;

public class TileEntityCustomEnergyStorage extends TileEntity implements ITickable 
{
    private CustomEnergyStorage storage = new CustomEnergyStorage(10000);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) 
    {
        if(cap == CapabilityEnergy.ENERGY) return LazyOptional.of(() -> this.storage).cast();
        return super.getCapability(cap, side);
    }

    public TileEntityCustomEnergyStorage(TileEntityType<?> tileEntityTypeIn) 
    {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick()
    {
        this.storage.receiveEnergy(100, false);
        this.storage.extractEnergy(200, false);
    }
    
}
