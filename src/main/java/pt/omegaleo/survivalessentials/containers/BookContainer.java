package pt.omegaleo.survivalessentials.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import pt.omegaleo.survivalessentials.ModContainerTypes;

public class BookContainer extends Container {

    public BookContainer(int id, PlayerInventory playerInventory) 
    {
        super(ModContainerTypes.book, id);
    }

	@Override
    public boolean canInteractWith(PlayerEntity playerIn) 
    {
        return true;
    }
    
}
