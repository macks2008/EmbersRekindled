package com.rekindled.embers.item;

import java.util.Map;

import com.rekindled.embers.api.event.EmberRemoveEvent;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class EmberDiscountBaubleItem extends Item implements IEmbersCurioItem {

	public double reduction;

	public EmberDiscountBaubleItem(Properties properties, double reduction) {
		super(properties);
		this.reduction = reduction;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onTake(EmberRemoveEvent event) {
		LazyOptional<ICuriosItemHandler> inv = CuriosApi.getCuriosInventory(event.getPlayer());
		if (inv.isPresent()) {
			Map<String, ICurioStacksHandler> curios = inv.resolve().get().getCurios();
			for (ICurioStacksHandler curio : curios.values()) {
				for (int i = 0; i < curio.getStacks().getSlots(); i++) {
					if (curio.getStacks().getStackInSlot(i).getItem() == this) {
						event.addReduction(reduction);
					}
				}
			}
		}
	}
}
