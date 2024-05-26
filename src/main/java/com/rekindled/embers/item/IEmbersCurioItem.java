package com.rekindled.embers.item;

import com.rekindled.embers.datagen.EmbersSounds;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public interface IEmbersCurioItem extends ICurioItem {

	default boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
		return true;
	}

	//disable the default sound because it plays on the player blockpos instead of the exact player position which is really distracting
	default ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
		return new ICurio.SoundInfo(SoundEvents.EMPTY, 1.0f, 1.0f);
	}

	default void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
		Vec3 pos = slotContext.entity().position();
		slotContext.entity().level().playSound(null, pos.x, pos.y, pos.z, EmbersSounds.BAUBLE_EQUIP.get(), slotContext.entity().getSoundSource(), 1.0f, 1.0f);
	}

	default void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
		Vec3 pos = slotContext.entity().position();
		slotContext.entity().level().playSound(null, pos.x, pos.y, pos.z, EmbersSounds.BAUBLE_UNEQUIP.get(), slotContext.entity().getSoundSource(), 1.0f, 1.0f);
	}
}
