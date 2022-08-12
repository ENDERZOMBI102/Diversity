package com.enderzombi102.diversity.flora.block;

import com.enderzombi102.diversity.flora.config.ConfigManager;
import com.enderzombi102.diversity.flora.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Cataplant extends PlantBlock {
	private static final VoxelShape SHAPE = createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public Cataplant() {
		super( settings -> settings.offsetType( OffsetType.XZ ) );
		setDefaultState( stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH) );
	}

	@Override
	protected void addDrops(List<ItemStack> drops, BlockState state) {
		drops.add( new ItemStack( ItemRegistry.get("cataplant") ) );
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.HORIZONTAL_FACING);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with( Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite() );
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		world.playSound(
			pos.getX(),
			pos.getY(),
			pos.getZ(),
			SoundEvents.BLOCK_LILY_PAD_PLACE,
			SoundCategory.BLOCKS,
			1,
			1,
			false
		);
		Direction face = state.get(FACING);
		int x = face.getVector().getX();
		int z = face.getVector().getZ();
		entity.addVelocity(
			x * ConfigManager.getActiveConfig().plants.cataplant.power,
			0.08 * ConfigManager.getActiveConfig().plants.cataplant.power,
			z * ConfigManager.getActiveConfig().plants.cataplant.power
		);
		entity.velocityDirty = true;
	}
}
