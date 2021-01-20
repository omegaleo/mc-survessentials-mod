package pt.omegaleo.survivalessentials.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.MaterialLibrary;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import pt.omegaleo.survivalessentials.SurvivalEssentialsMod;
import pt.omegaleo.survivalessentials.containers.EnchantmentExtractorContainer;
import pt.omegaleo.survivalessentials.tileentity.TileEntityEnchantmentExtractor;

public class BlockEnchantmentExtractor extends ContainerBlock 
{

    public BlockEnchantmentExtractor() 
    {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0f)
                .sound(SoundType.METAL)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(2));
    }

        
          // ---------------------
        
          /**
           * Create the Tile Entity for this block.
           * Forge has a default but I've included it anyway for clarity
           * @return
           */
          @Override
          public TileEntity createTileEntity(BlockState state, IBlockReader world) {
            return createNewTileEntity(world);
          }
        
          @Nullable
          @Override
          public TileEntity createNewTileEntity(IBlockReader worldIn) {
            return new TileEntityEnchantmentExtractor();
          }
        
          // not needed if your block implements ITileEntityProvider (in this case implemented by BlockContainer), but it
          //  doesn't hurt to include it anyway...
          @Override
          public boolean hasTileEntity(BlockState state)
          {
            return true;
          }
        
        
          // Called when the block is right clicked
            // In this block it is used to open the block gui when right clicked by a player
            @Override
          public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
            if (worldIn.isRemote) return ActionResultType.SUCCESS; // on client side, don't do anything
        
            INamedContainerProvider namedContainerProvider = this.getContainer(state, worldIn, pos);
            if (namedContainerProvider != null) {
              if (!(player instanceof ServerPlayerEntity)) return ActionResultType.FAIL;  // should always be true, but just in case...
              ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
              NetworkHooks.openGui(serverPlayerEntity, namedContainerProvider, (packetBuffer)->{});
              // (packetBuffer)->{} is just a do-nothing because we have no extra data to send
            }
            return ActionResultType.SUCCESS;
            }
        
            // This is where you can do something when the block is broken. In this case drop the inventory's contents
          // Code is copied directly from vanilla eg ChestBlock, CampfireBlock
            @Override
          public void onReplaced(BlockState state, World world, BlockPos blockPos, BlockState newState, boolean isMoving) {
            if (state.getBlock() != newState.getBlock()) {
              TileEntity tileentity = world.getTileEntity(blockPos);
              if (tileentity instanceof TileEntityEnchantmentExtractor) {
                TileEntityEnchantmentExtractor tileEntityFurnace = (TileEntityEnchantmentExtractor)tileentity;
                tileEntityFurnace.dropAllContents(world, blockPos);
              }
        //      worldIn.updateComparatorOutputLevel(pos, this);  if the inventory is used to set redstone power for comparators
              super.onReplaced(state, world, blockPos, newState, isMoving);  // call it last, because it removes the TileEntity
            }
          }
        
          // ---------------------------
          // If you want your container to provide redstone power to a comparator based on its contents, implement these methods
          //  see vanilla for examples
        
          @Override
          public boolean hasComparatorInputOverride(BlockState state) {
            return false;
          }
        
          @Override
          public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
            return 0;
          }
        
          //------------------------------------------------------------
            //  The code below isn't necessary for illustrating the Inventory Furnace concepts, it's just used for rendering.
            //  For more background information see MBE03
        
            // render using a BakedModel
          // required because the default (super method) is INVISIBLE for BlockContainers.
            @Override
            public BlockRenderType getRenderType(BlockState iBlockState) {
                return BlockRenderType.MODEL;
            }
}
