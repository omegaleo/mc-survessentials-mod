package pt.omegaleo.survivalessentials.containers;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import pt.omegaleo.survivalessentials.ModContainerTypes;

public class BookContainer extends AbstractContainerMenu {

    public BookContainer(int id, Inventory playerInventory) 
    {
        super(ModContainerTypes.book, id);
    }

    public int getInventoryRows() {
        return this.slots.size() / 9;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }
}
