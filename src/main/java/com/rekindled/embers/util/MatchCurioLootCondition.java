package com.rekindled.embers.util;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.rekindled.embers.compat.curios.CuriosCompat;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.fml.ModList;

/**
 * A LootItemCondition that checks worn curios against an {@link ItemPredicate}.
 */
public class MatchCurioLootCondition implements LootItemCondition {

	public static final LootItemConditionType LOOT_CONDITION_TYPE = new LootItemConditionType(new MatchCurioLootCondition.Serializer());

	final ItemPredicate predicate;

	public MatchCurioLootCondition(ItemPredicate pToolPredicate) {
		this.predicate = pToolPredicate;
	}

	public LootItemConditionType getType() {
		return LOOT_CONDITION_TYPE;
	}

	public Set<LootContextParam<?>> getReferencedContextParams() {
		return ImmutableSet.of(LootContextParams.KILLER_ENTITY, LootContextParams.THIS_ENTITY);
	}

	public boolean test(LootContext context) {
		if (!ModList.get().isLoaded("curios"))
			return false;

		Entity user;
		if (context.hasParam(LootContextParams.KILLER_ENTITY))
			user = context.getParam(LootContextParams.KILLER_ENTITY);
		else
			user = context.getParam(LootContextParams.THIS_ENTITY);

		if (user instanceof LivingEntity)
			return CuriosCompat.checkForCurios((LivingEntity) user, stack -> this.predicate.matches(stack));
		return false;
	}

	public static LootItemCondition.Builder curioMatches(ItemPredicate.Builder pToolPredicateBuilder) {
		return () -> {
			return new MatchCurioLootCondition(pToolPredicateBuilder.build());
		};
	}

	public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<MatchCurioLootCondition> {
		public void serialize(JsonObject p_82013_, MatchCurioLootCondition p_82014_, JsonSerializationContext p_82015_) {
			p_82013_.add("predicate", p_82014_.predicate.serializeToJson());
		}

		public MatchCurioLootCondition deserialize(JsonObject p_82021_, JsonDeserializationContext p_82022_) {
			ItemPredicate itempredicate = ItemPredicate.fromJson(p_82021_.get("predicate"));
			return new MatchCurioLootCondition(itempredicate);
		}
	}
}