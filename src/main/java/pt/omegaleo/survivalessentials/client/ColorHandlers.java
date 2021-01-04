package pt.omegaleo.survivalessentials.client;

import net.minecraftforge.client.event.ColorHandlerEvent;
import pt.omegaleo.survivalessentials.ModItems;
import pt.omegaleo.survivalessentials.items.BackpackItem;

public class ColorHandlers 
{
    public static void registerItemColor(ColorHandlerEvent.Item event)
    {
        event.getItemColors().register(BackpackItem::getItemColor, ModItems.BACKPACK.get());
    }
}
