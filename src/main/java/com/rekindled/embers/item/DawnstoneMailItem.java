package com.rekindled.embers.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class DawnstoneMailItem extends Item implements IEmbersCurioItem {

	public DawnstoneMailItem(Properties properties) {
		super(properties);
	}

	public SoundEvent equipSound() {
		return SoundEvents.ARMOR_EQUIP_CHAIN;
	}

	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Curio knockback resistance", 1, AttributeModifier.Operation.ADDITION));
		return builder.build();
	}
}
