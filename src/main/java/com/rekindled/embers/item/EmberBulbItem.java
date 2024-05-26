package com.rekindled.embers.item;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class EmberBulbItem extends EmberStorageItem implements ICurioItem {

	public static final double CAPACITY = 1000.0;

	public EmberBulbItem(Properties properties) {
		super(properties);
	}

	@Override
	public double getCapacity() {
		return CAPACITY;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		return new EmberJarItem.EmberJarCapability(stack, getCapacity());
	}
}
