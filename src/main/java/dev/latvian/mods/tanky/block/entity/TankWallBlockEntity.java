package dev.latvian.mods.tanky.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TankWallBlockEntity extends BlockEntity implements TankEntityLookup {
	public BlockPos controllerPos = null;
	public TankControllerBlockEntity cachedEntity = null;

	public TankWallBlockEntity() {
		super(TankyBlockEntities.TANK_WALL.get());
	}

	@Override
	public void load(BlockState state, CompoundTag tag) {
		super.load(state, tag);

		if (controllerPos != null) {
			tag.putInt("ControllerX", controllerPos.getX());
			tag.putInt("ControllerY", controllerPos.getY());
			tag.putInt("ControllerZ", controllerPos.getZ());
		}

		cachedEntity = null;
	}

	@Override
	public CompoundTag save(CompoundTag tag) {
		if (tag.contains("ControllerX")) {
			controllerPos = new BlockPos(tag.getInt("ControllerX"), tag.getInt("ControllerY"), tag.getInt("ControllerZ"));
		} else {
			controllerPos = null;
		}

		return super.save(tag);
	}

	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (controllerPos != null && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			TankControllerBlockEntity entity = getController();

			if (entity != null) {
				return entity.getCapability(cap, side);
			}
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void clearCache() {
		super.clearCache();
		cachedEntity = null;
	}

	@Override
	public TankControllerBlockEntity getController() {
		if (level != null && (cachedEntity == null || cachedEntity.isRemoved())) {
			cachedEntity = null;

			if (controllerPos != null) {
				if (!level.isClientSide()) {
					level.getBlockEntity(controllerPos);
				}

				BlockEntity entity = level.getBlockEntity(controllerPos);

				if (entity instanceof TankControllerBlockEntity) {
					cachedEntity = (TankControllerBlockEntity) entity;
				}
			}
		}

		return cachedEntity;
	}
}
