package com.rekindled.embers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;
import com.rekindled.embers.api.EmbersAPI;
import com.rekindled.embers.api.augment.AugmentUtil;
import com.rekindled.embers.api.augment.IAugment;
import com.rekindled.embers.augment.BlastingCoreAugment;
import com.rekindled.embers.augment.CasterOrbAugment;
import com.rekindled.embers.augment.CinderJetAugment;
import com.rekindled.embers.augment.CoreAugment;
import com.rekindled.embers.augment.DiffractionBarrelAugment;
import com.rekindled.embers.augment.EldritchInsigniaAugment;
import com.rekindled.embers.augment.FlameBarrierAugment;
import com.rekindled.embers.augment.FocalLensAugment;
import com.rekindled.embers.augment.IntelligentApparatusAugment;
import com.rekindled.embers.augment.ResonatingBellAugment;
import com.rekindled.embers.augment.ShiftingScalesAugment;
import com.rekindled.embers.augment.SuperheaterAugment;
import com.rekindled.embers.augment.TinkerLensAugment;
import com.rekindled.embers.augment.WindingGearsAugment;
import com.rekindled.embers.block.AlchemyPedestalBlock;
import com.rekindled.embers.block.AlchemyTabletBlock;
import com.rekindled.embers.block.ArchaicLightBlock;
import com.rekindled.embers.block.AtmosphericBellowsBlock;
import com.rekindled.embers.block.AtmosphericGaugeBlock;
import com.rekindled.embers.block.AutomaticHammerBlock;
import com.rekindled.embers.block.BeamCannonBlock;
import com.rekindled.embers.block.BeamSplitterBlock;
import com.rekindled.embers.block.BinBlock;
import com.rekindled.embers.block.CaminiteGaugeBlock;
import com.rekindled.embers.block.CaminiteGaugeEdgeBlock;
import com.rekindled.embers.block.CaminiteRingBlock;
import com.rekindled.embers.block.CaminiteRingEdgeBlock;
import com.rekindled.embers.block.CaminiteValveBlock;
import com.rekindled.embers.block.CaminiteValveEdgeBlock;
import com.rekindled.embers.block.CatalysisChamberBlock;
import com.rekindled.embers.block.CatalyticPlugBlock;
import com.rekindled.embers.block.CharInstillerBlock;
import com.rekindled.embers.block.CinderPlinthBlock;
import com.rekindled.embers.block.ClockworkAttenuatorBlock;
import com.rekindled.embers.block.CombustionChamberBlock;
import com.rekindled.embers.block.CopperCellBlock;
import com.rekindled.embers.block.CopperChargerBlock;
import com.rekindled.embers.block.CreativeEmberBlock;
import com.rekindled.embers.block.CrystalCellBlock;
import com.rekindled.embers.block.CrystalCellEdgeBlock;
import com.rekindled.embers.block.CrystalSeedBlock;
import com.rekindled.embers.block.DawnstoneAnvilBlock;
import com.rekindled.embers.block.EmberActivatorBlock;
import com.rekindled.embers.block.EmberBoreBlock;
import com.rekindled.embers.block.EmberBoreEdgeBlock;
import com.rekindled.embers.block.EmberDialBlock;
import com.rekindled.embers.block.EmberEjectorBlock;
import com.rekindled.embers.block.EmberEmitterBlock;
import com.rekindled.embers.block.EmberFunnelBlock;
import com.rekindled.embers.block.EmberInjectorBlock;
import com.rekindled.embers.block.EmberLanternBlock;
import com.rekindled.embers.block.EmberReceiverBlock;
import com.rekindled.embers.block.EmberRelayBlock;
import com.rekindled.embers.block.EmberSiphonBlock;
import com.rekindled.embers.block.EntropicEnumeratorBlock;
import com.rekindled.embers.block.ExcavationBucketsBlock;
import com.rekindled.embers.block.FieldChartBlock;
import com.rekindled.embers.block.FieldChartEdgeBlock;
import com.rekindled.embers.block.FluidDialBlock;
import com.rekindled.embers.block.FluidExtractorBlock;
import com.rekindled.embers.block.FluidPipeBlock;
import com.rekindled.embers.block.FluidTransferBlock;
import com.rekindled.embers.block.FluidVesselBlock;
import com.rekindled.embers.block.GeologicSeparatorBlock;
import com.rekindled.embers.block.GlimmerBlock;
import com.rekindled.embers.block.HearthCoilBlock;
import com.rekindled.embers.block.HearthCoilEdgeBlock;
import com.rekindled.embers.block.HeatExchangerBlock;
import com.rekindled.embers.block.HeatInsulationBlock;
import com.rekindled.embers.block.IgnemReactorBlock;
import com.rekindled.embers.block.InfernoForgeBlock;
import com.rekindled.embers.block.InfernoForgeEdgeBlock;
import com.rekindled.embers.block.ItemDialBlock;
import com.rekindled.embers.block.ItemDropperBlock;
import com.rekindled.embers.block.ItemExtractorBlock;
import com.rekindled.embers.block.ItemPipeBlock;
import com.rekindled.embers.block.ItemTransferBlock;
import com.rekindled.embers.block.ItemVacuumBlock;
import com.rekindled.embers.block.MechanicalCoreBlock;
import com.rekindled.embers.block.MechanicalPumpBlock;
import com.rekindled.embers.block.MelterBlock;
import com.rekindled.embers.block.MiniBoilerBlock;
import com.rekindled.embers.block.MirrorRelayBlock;
import com.rekindled.embers.block.MithrilBlock;
import com.rekindled.embers.block.MixerCentrifugeBlock;
import com.rekindled.embers.block.MnemonicInscriberBlock;
import com.rekindled.embers.block.PressureRefineryBlock;
import com.rekindled.embers.block.ReservoirBlock;
import com.rekindled.embers.block.ReservoirEdgeBlock;
import com.rekindled.embers.block.StampBaseBlock;
import com.rekindled.embers.block.StamperBlock;
import com.rekindled.embers.block.WaterloggableButtonBlock;
import com.rekindled.embers.block.WaterloggableLeverBlock;
import com.rekindled.embers.block.WildfireStirlingBlock;
import com.rekindled.embers.blockentity.AlchemyPedestalBlockEntity;
import com.rekindled.embers.blockentity.AlchemyPedestalTopBlockEntity;
import com.rekindled.embers.blockentity.AlchemyTabletBlockEntity;
import com.rekindled.embers.blockentity.AtmosphericBellowsBlockEntity;
import com.rekindled.embers.blockentity.AtmosphericGaugeBlockEntity;
import com.rekindled.embers.blockentity.AutomaticHammerBlockEntity;
import com.rekindled.embers.blockentity.BeamCannonBlockEntity;
import com.rekindled.embers.blockentity.BeamSplitterBlockEntity;
import com.rekindled.embers.blockentity.BinBlockEntity;
import com.rekindled.embers.blockentity.CaminiteValveBlockEntity;
import com.rekindled.embers.blockentity.CatalysisChamberBlockEntity;
import com.rekindled.embers.blockentity.CatalyticPlugBlockEntity;
import com.rekindled.embers.blockentity.CharInstillerBlockEntity;
import com.rekindled.embers.blockentity.CinderPlinthBlockEntity;
import com.rekindled.embers.blockentity.ClockworkAttenuatorBlockEntity;
import com.rekindled.embers.blockentity.CombustionChamberBlockEntity;
import com.rekindled.embers.blockentity.CopperCellBlockEntity;
import com.rekindled.embers.blockentity.CopperChargerBlockEntity;
import com.rekindled.embers.blockentity.CreativeEmberBlockEntity;
import com.rekindled.embers.blockentity.CrystalCellBlockEntity;
import com.rekindled.embers.blockentity.CrystalSeedBlockEntity;
import com.rekindled.embers.blockentity.DawnstoneAnvilBlockEntity;
import com.rekindled.embers.blockentity.EmberActivatorBottomBlockEntity;
import com.rekindled.embers.blockentity.EmberActivatorTopBlockEntity;
import com.rekindled.embers.blockentity.EmberBoreBlockEntity;
import com.rekindled.embers.blockentity.EmberEjectorBlockEntity;
import com.rekindled.embers.blockentity.EmberEmitterBlockEntity;
import com.rekindled.embers.blockentity.EmberFunnelBlockEntity;
import com.rekindled.embers.blockentity.EmberInjectorBlockEntity;
import com.rekindled.embers.blockentity.EmberReceiverBlockEntity;
import com.rekindled.embers.blockentity.EmberRelayBlockEntity;
import com.rekindled.embers.blockentity.EmberSiphonBlockEntity;
import com.rekindled.embers.blockentity.EntropicEnumeratorBlockEntity;
import com.rekindled.embers.blockentity.ExcavationBucketsBlockEntity;
import com.rekindled.embers.blockentity.FieldChartBlockEntity;
import com.rekindled.embers.blockentity.FluidExtractorBlockEntity;
import com.rekindled.embers.blockentity.FluidPipeBlockEntity;
import com.rekindled.embers.blockentity.FluidTransferBlockEntity;
import com.rekindled.embers.blockentity.FluidVesselBlockEntity;
import com.rekindled.embers.blockentity.GeologicSeparatorBlockEntity;
import com.rekindled.embers.blockentity.HearthCoilBlockEntity;
import com.rekindled.embers.blockentity.HeatExchangerBlockEntity;
import com.rekindled.embers.blockentity.HeatInsulationBlockEntity;
import com.rekindled.embers.blockentity.IgnemReactorBlockEntity;
import com.rekindled.embers.blockentity.InfernoForgeBottomBlockEntity;
import com.rekindled.embers.blockentity.InfernoForgeTopBlockEntity;
import com.rekindled.embers.blockentity.ItemDropperBlockEntity;
import com.rekindled.embers.blockentity.ItemExtractorBlockEntity;
import com.rekindled.embers.blockentity.ItemPipeBlockEntity;
import com.rekindled.embers.blockentity.ItemTransferBlockEntity;
import com.rekindled.embers.blockentity.ItemVacuumBlockEntity;
import com.rekindled.embers.blockentity.MechanicalCoreBlockEntity;
import com.rekindled.embers.blockentity.MechanicalPumpBottomBlockEntity;
import com.rekindled.embers.blockentity.MechanicalPumpTopBlockEntity;
import com.rekindled.embers.blockentity.MelterBottomBlockEntity;
import com.rekindled.embers.blockentity.MelterTopBlockEntity;
import com.rekindled.embers.blockentity.MiniBoilerBlockEntity;
import com.rekindled.embers.blockentity.MirrorRelayBlockEntity;
import com.rekindled.embers.blockentity.MithrilBlockEntity;
import com.rekindled.embers.blockentity.MixerCentrifugeBottomBlockEntity;
import com.rekindled.embers.blockentity.MixerCentrifugeTopBlockEntity;
import com.rekindled.embers.blockentity.MnemonicInscriberBlockEntity;
import com.rekindled.embers.blockentity.PressureRefineryBottomBlockEntity;
import com.rekindled.embers.blockentity.PressureRefineryTopBlockEntity;
import com.rekindled.embers.blockentity.ReservoirBlockEntity;
import com.rekindled.embers.blockentity.StampBaseBlockEntity;
import com.rekindled.embers.blockentity.StamperBlockEntity;
import com.rekindled.embers.blockentity.WildfireStirlingBlockEntity;
import com.rekindled.embers.datagen.EmbersFluidTags;
import com.rekindled.embers.datagen.EmbersSounds;
import com.rekindled.embers.entity.AncientGolemEntity;
import com.rekindled.embers.entity.EmberPacketEntity;
import com.rekindled.embers.entity.EmberProjectileEntity;
import com.rekindled.embers.entity.GlimmerProjectileEntity;
import com.rekindled.embers.fluidtypes.EmbersFluidType.FluidInfo;
import com.rekindled.embers.fluidtypes.MoltenMetalFluidType;
import com.rekindled.embers.fluidtypes.SteamFluidType;
import com.rekindled.embers.fluidtypes.ViscousFluidType;
import com.rekindled.embers.gui.SlateMenu;
import com.rekindled.embers.item.AlchemicalNoteItem;
import com.rekindled.embers.item.AlchemyHintItem;
import com.rekindled.embers.item.AncientCodexItem;
import com.rekindled.embers.item.AshenArmorGemItem;
import com.rekindled.embers.item.BlazingRayItem;
import com.rekindled.embers.item.CaminiteRingBlockItem;
import com.rekindled.embers.item.CinderStaffItem;
import com.rekindled.embers.item.ClockworkAxeItem;
import com.rekindled.embers.item.ClockworkHammerItem;
import com.rekindled.embers.item.ClockworkPickaxeItem;
import com.rekindled.embers.item.CodebreakingSlateItem;
import com.rekindled.embers.item.CopperCellBlockItem;
import com.rekindled.embers.item.EmberCartridgeItem;
import com.rekindled.embers.item.EmberJarItem;
import com.rekindled.embers.item.EmberRecordItem;
import com.rekindled.embers.item.EmberStorageItem;
import com.rekindled.embers.item.FluidVesselBlockItem;
import com.rekindled.embers.item.FuelItem;
import com.rekindled.embers.item.GlimmerCrystalItem;
import com.rekindled.embers.item.GlimmerLampItem;
import com.rekindled.embers.item.InflictorGemItem;
import com.rekindled.embers.item.TinkerHammerItem;
import com.rekindled.embers.item.TyrfingItem;
import com.rekindled.embers.particle.AlchemyCircleParticleOptions;
import com.rekindled.embers.particle.GlowParticleOptions;
import com.rekindled.embers.particle.SmokeParticleOptions;
import com.rekindled.embers.particle.SparkParticleOptions;
import com.rekindled.embers.particle.StarParticleOptions;
import com.rekindled.embers.particle.TyrfingParticleOptions;
import com.rekindled.embers.particle.VaporParticleOptions;
import com.rekindled.embers.particle.XRayGlowParticleOptions;
import com.rekindled.embers.recipe.AlchemyRecipe;
import com.rekindled.embers.recipe.AnvilAugmentRecipe;
import com.rekindled.embers.recipe.AnvilAugmentRemoveRecipe;
import com.rekindled.embers.recipe.AnvilBreakdownRecipe;
import com.rekindled.embers.recipe.AnvilRepairMateriaRecipe;
import com.rekindled.embers.recipe.AnvilRepairRecipe;
import com.rekindled.embers.recipe.BoilingRecipe;
import com.rekindled.embers.recipe.BoringRecipe;
import com.rekindled.embers.recipe.CatalysisCombustionRecipe;
import com.rekindled.embers.recipe.EmberActivationRecipe;
import com.rekindled.embers.recipe.ExcavationRecipe;
import com.rekindled.embers.recipe.GaseousFuelRecipe;
import com.rekindled.embers.recipe.GemSocketRecipe;
import com.rekindled.embers.recipe.GemUnsocketRecipe;
import com.rekindled.embers.recipe.IAlchemyRecipe;
import com.rekindled.embers.recipe.IBoilingRecipe;
import com.rekindled.embers.recipe.IBoringRecipe;
import com.rekindled.embers.recipe.ICatalysisCombustionRecipe;
import com.rekindled.embers.recipe.IDawnstoneAnvilRecipe;
import com.rekindled.embers.recipe.IEmberActivationRecipe;
import com.rekindled.embers.recipe.IGaseousFuelRecipe;
import com.rekindled.embers.recipe.IMeltingRecipe;
import com.rekindled.embers.recipe.IMetalCoefficientRecipe;
import com.rekindled.embers.recipe.IMixingRecipe;
import com.rekindled.embers.recipe.IStampingRecipe;
import com.rekindled.embers.recipe.MeltingRecipe;
import com.rekindled.embers.recipe.MetalCoefficientRecipe;
import com.rekindled.embers.recipe.MixingRecipe;
import com.rekindled.embers.recipe.StampingRecipe;
import com.rekindled.embers.util.AshenAmuletLootModifier;
import com.rekindled.embers.util.AshenArmorMaterial;
import com.rekindled.embers.util.EmbersTiers;
import com.rekindled.embers.util.GrandhammerLootModifier;
import com.rekindled.embers.util.LegacyDeferredRegister;
import com.rekindled.embers.util.Misc;
import com.rekindled.embers.util.SuperHeaterLootModifier;
import com.rekindled.embers.worldgen.CaveStructure;
import com.rekindled.embers.worldgen.CrystalSeedStructureProcessor;
import com.rekindled.embers.worldgen.EntityMobilizerStructureProcessor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidInteractionRegistry.InteractionInformation;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryManager {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Embers.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Embers.MODID);
	public static final DeferredRegister<FluidType> FLUIDTYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Embers.MODID);
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Embers.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Embers.MODID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES_NEW = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Embers.MODID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES_OLD = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Embers.MODID_OLD);
	public static final LegacyDeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = new LegacyDeferredRegister<BlockEntityType<?>>(BLOCK_ENTITY_TYPES_NEW, BLOCK_ENTITY_TYPES_OLD);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Embers.MODID);
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Embers.MODID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Embers.MODID);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Embers.MODID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Embers.MODID);
	public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Embers.MODID);
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Embers.MODID);
	public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, Embers.MODID);
	public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, Embers.MODID);

	public static Map<ResourceLocation, IAugment> augmentRegistry = new HashMap<ResourceLocation, IAugment>();

	public static List<FluidStuff> fluidList = new ArrayList<FluidStuff>();

	public static FluidStuff addFluid(String localizedName, FluidInfo info, BiFunction<FluidType.Properties, FluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Source> source, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Flowing> flowing, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
		FluidStuff fluid = new FluidStuff(info.name, localizedName, info.color, type.apply(prop, info), block, fluidProperties, source, flowing);
		fluidList.add(fluid);
		return fluid;
	}

	public static FluidStuff addFluid(String localizedName, FluidInfo info, BiFunction<FluidType.Properties, FluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
		return addFluid(localizedName, info, type, block, ForgeFlowingFluid.Source::new, ForgeFlowingFluid.Flowing::new, fluidProperties, prop);
	}

	public static FluidType.Properties moltenMetalProps() {
		return FluidType.Properties.create()
				.canSwim(false)
				.canDrown(false)
				.pathType(BlockPathTypes.LAVA)
				.adjacentPathType(null)
				.motionScale(0.0023333333333333335D)
				.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
				.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
				.lightLevel(12)
				.density(3000)
				.viscosity(6000)
				.temperature(1100);
	}

	public static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) {
		return ENTITY_TYPES.register(name, () -> builder.build(Embers.MODID + ":" + name));
	}

	public static <T extends ParticleOptions> RegistryObject<ParticleType<T>> registerParticle(String name, boolean overrideLimiter, ParticleOptions.Deserializer<T> deserializer, Codec<T> codec) {
		return PARTICLE_TYPES.register(name, () -> new ParticleType<T>(overrideLimiter, deserializer) {
			public Codec<T> codec() {
				return codec;
			}
		});
	}

	public static <T extends Recipe<?>> RegistryObject<RecipeType<T>> registerRecipeType(final String identifier) {
		return RECIPE_TYPES.register(identifier, () -> new RecipeType<T>() {
			public String toString() {
				return Embers.MODID + ":" + identifier;
			}
		});
	}

	//blocks
	public static final RegistryObject<Block> LEAD_ORE = BLOCKS.register("lead_ore", () -> new DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(3.0f)));
	public static final RegistryObject<Block> DEEPSLATE_LEAD_ORE = BLOCKS.register("deepslate_lead_ore", () -> new DropExperienceBlock(Properties.copy(LEAD_ORE.get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
	public static final RegistryObject<Block> RAW_LEAD_BLOCK = BLOCKS.register("raw_lead_block", () -> new Block(Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(3.0F, 6.0F)));
	public static final RegistryObject<Block> LEAD_BLOCK = BLOCKS.register("lead_block", () -> new Block(Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

	public static final RegistryObject<Block> SILVER_ORE = BLOCKS.register("silver_ore", () -> new DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).requiresCorrectToolForDrops().strength(3.0f)));
	public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE = BLOCKS.register("deepslate_silver_ore", () -> new DropExperienceBlock(Properties.copy(SILVER_ORE.get()).mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
	public static final RegistryObject<Block> RAW_SILVER_BLOCK = BLOCKS.register("raw_silver_block", () -> new Block(Properties.of().mapColor(MapColor.ICE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(3.0F, 6.0F)));
	public static final RegistryObject<Block> SILVER_BLOCK = BLOCKS.register("silver_block", () -> new Block(Properties.of().mapColor(MapColor.ICE).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));

	public static final RegistryObject<Block> DAWNSTONE_BLOCK = BLOCKS.register("dawnstone_block", () -> new Block(Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
	public static final RegistryObject<Block> MITHRIL_BLOCK = BLOCKS.register("mithril_block", () -> new MithrilBlock(Properties.of().mapColor(MapColor.GLOW_LICHEN).sound(SoundType.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).noOcclusion()));

	public static final RegistryObject<Block> CAMINITE_BRICKS = BLOCKS.register("caminite_bricks", () -> new Block(Properties.of().mapColor(MapColor.WOOD).sound(EmbersSounds.CAMINITE).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks CAMINITE_BRICKS_DECO = new StoneDecoBlocks("caminite_bricks", CAMINITE_BRICKS, Properties.of().mapColor(MapColor.WOOD).sound(EmbersSounds.CAMINITE).requiresCorrectToolForDrops().strength(1.6f));
	public static final RegistryObject<Block> CAMINITE_LARGE_BRICKS = BLOCKS.register("caminite_large_bricks", () -> new Block(Properties.of().mapColor(MapColor.WOOD).sound(EmbersSounds.CAMINITE).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks CAMINITE_LARGE_BRICKS_DECO = new StoneDecoBlocks("caminite_large_bricks", CAMINITE_LARGE_BRICKS, Properties.of().mapColor(MapColor.WOOD).sound(EmbersSounds.CAMINITE).requiresCorrectToolForDrops().strength(1.6f), true, true, false);
	public static final RegistryObject<Block> RAW_CAMINITE_BLOCK = BLOCKS.register("raw_caminite_block", () -> new Block(Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).sound(SoundType.GRAVEL).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> CAMINITE_LARGE_TILE = BLOCKS.register("caminite_large_tile", () -> new Block(Properties.of().mapColor(MapColor.WOOD).sound(EmbersSounds.CAMINITE).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks CAMINITE_LARGE_TILE_DECO = new StoneDecoBlocks("caminite_large_tile", CAMINITE_LARGE_TILE, Properties.of().mapColor(MapColor.WOOD).sound(EmbersSounds.CAMINITE).requiresCorrectToolForDrops().strength(1.6f), true, true, false);
	public static final RegistryObject<Block> CAMINITE_TILES = BLOCKS.register("caminite_tiles", () -> new Block(Properties.of().mapColor(MapColor.WOOD).sound(EmbersSounds.CAMINITE).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks CAMINITE_TILES_DECO = new StoneDecoBlocks("caminite_tiles", CAMINITE_TILES, Properties.of().mapColor(MapColor.WOOD).sound(EmbersSounds.CAMINITE).requiresCorrectToolForDrops().strength(1.6f), true, true, false);
	public static final RegistryObject<Block> ARCHAIC_BRICKS = BLOCKS.register("archaic_bricks", () -> new Block(Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks ARCHAIC_BRICKS_DECO = new StoneDecoBlocks("archaic_bricks", ARCHAIC_BRICKS, Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().strength(1.6f));
	public static final RegistryObject<Block> ARCHAIC_EDGE = BLOCKS.register("archaic_edge", () -> new Block(Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> ARCHAIC_TILE = BLOCKS.register("archaic_tile", () -> new Block(Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks ARCHAIC_TILE_DECO = new StoneDecoBlocks("archaic_tile", ARCHAIC_TILE, Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().strength(1.6f), true, true, false);
	public static final RegistryObject<Block> ARCHAIC_LARGE_BRICKS = BLOCKS.register("archaic_large_bricks", () -> new Block(Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks ARCHAIC_LARGE_BRICKS_DECO = new StoneDecoBlocks("archaic_large_bricks", ARCHAIC_LARGE_BRICKS, Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().strength(1.6f), true, true, false);
	public static final RegistryObject<Block> ARCHAIC_LIGHT = BLOCKS.register("archaic_light", () -> new ArchaicLightBlock(Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().strength(1.6f).lightLevel(state -> 15)));
	public static final RegistryObject<Block> ASHEN_STONE = BLOCKS.register("ashen_stone", () -> new Block(Properties.of().mapColor(MapColor.COLOR_BLACK).sound(EmbersSounds.ASHEN_STONE).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks ASHEN_STONE_DECO = new StoneDecoBlocks("ashen_stone", ASHEN_STONE, Properties.of().mapColor(MapColor.COLOR_BLACK).sound(EmbersSounds.ASHEN_STONE).requiresCorrectToolForDrops().strength(1.6f));
	public static final RegistryObject<Block> ASHEN_BRICK = BLOCKS.register("ashen_brick", () -> new Block(Properties.of().mapColor(MapColor.COLOR_BLACK).sound(EmbersSounds.ASHEN_STONE).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks ASHEN_BRICK_DECO = new StoneDecoBlocks("ashen_brick", ASHEN_BRICK, Properties.of().mapColor(MapColor.COLOR_BLACK).sound(EmbersSounds.ASHEN_STONE).requiresCorrectToolForDrops().strength(1.6f));
	public static final RegistryObject<Block> ASHEN_TILE = BLOCKS.register("ashen_tile", () -> new Block(Properties.of().mapColor(MapColor.COLOR_BLACK).sound(EmbersSounds.ASHEN_STONE).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks ASHEN_TILE_DECO = new StoneDecoBlocks("ashen_tile", ASHEN_TILE, Properties.of().mapColor(MapColor.COLOR_BLACK).sound(EmbersSounds.ASHEN_STONE).requiresCorrectToolForDrops().strength(1.6f));
	public static final RegistryObject<Block> SEALED_PLANKS = BLOCKS.register("sealed_planks", () -> new Block(Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.WOOD).strength(1.6f)));
	public static final StoneDecoBlocks SEALED_PLANKS_DECO = new StoneDecoBlocks("sealed_planks", SEALED_PLANKS, Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.WOOD).strength(1.6f), true, true, false);
	public static final RegistryObject<Block> REINFORCED_SEALED_PLANKS = BLOCKS.register("reinforced_sealed_planks", () -> new Block(Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.WOOD).strength(2.6f)));
	public static final RegistryObject<Block> SEALED_WOOD_TILE = BLOCKS.register("sealed_wood_tile", () -> new Block(Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.WOOD).strength(1.6f)));
	public static final StoneDecoBlocks SEALED_WOOD_TILE_DECO = new StoneDecoBlocks("sealed_wood_tile", SEALED_WOOD_TILE, Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.WOOD).strength(1.6f), true, true, false);
	public static final RegistryObject<RotatedPillarBlock> SEALED_WOOD_PILLAR = BLOCKS.register("sealed_wood_pillar", () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.WOOD).strength(1.6f)));
	public static final RegistryObject<RotatedPillarBlock> SEALED_WOOD_KEG = BLOCKS.register("sealed_wood_keg", () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.WOOD).strength(1.6f)));
	public static final RegistryObject<Block> SOLIDIFIED_METAL = BLOCKS.register("solidified_metal", () -> new Block(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.SOLID_METAL).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> METAL_PLATFORM = BLOCKS.register("metal_platform", () -> new Block(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.SOLID_METAL).requiresCorrectToolForDrops().strength(1.6f)));
	public static final StoneDecoBlocks METAL_PLATFORM_DECO = new StoneDecoBlocks("metal_platform", METAL_PLATFORM, Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.SOLID_METAL).requiresCorrectToolForDrops().strength(1.6f), false, true, false);
	public static final RegistryObject<Block> EMBER_LANTERN = BLOCKS.register("ember_lantern", () -> new EmberLanternBlock(Properties.of().mapColor(MapColor.NONE).sound(SoundType.LANTERN).requiresCorrectToolForDrops().strength(1.6f).lightLevel(state -> 15)));

	public static final RegistryObject<Block> COPPER_CELL = BLOCKS.register("copper_cell", () -> new CopperCellBlock(Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.4f).noOcclusion()));
	public static final RegistryObject<Block> CREATIVE_EMBER = BLOCKS.register("creative_ember_source", () -> new CreativeEmberBlock(Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).sound(EmbersSounds.SOLID_METAL).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> EMBER_DIAL = BLOCKS.register("ember_dial", () -> new EmberDialBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> ITEM_DIAL = BLOCKS.register("item_dial", () -> new ItemDialBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> FLUID_DIAL = BLOCKS.register("fluid_dial", () -> new FluidDialBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> ATMOSPHERIC_GAUGE = BLOCKS.register("atmospheric_gauge", () -> new AtmosphericGaugeBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_EMITTER = BLOCKS.register("ember_emitter", () -> new EmberEmitterBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_MACHINE).requiresCorrectToolForDrops().strength(0.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_RECEIVER = BLOCKS.register("ember_receiver", () -> new EmberReceiverBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_MACHINE).requiresCorrectToolForDrops().strength(0.6f).noOcclusion()));
	public static final RegistryObject<Block> CAMINITE_LEVER = BLOCKS.register("caminite_lever", () -> new WaterloggableLeverBlock(Properties.of().mapColor(MapColor.NONE).noCollission().sound(EmbersSounds.CAMINITE).strength(0.75f)));
	public static final RegistryObject<ButtonBlock> CAMINITE_BUTTON = BLOCKS.register("caminite_button", () -> new WaterloggableButtonBlock(Properties.of().mapColor(MapColor.NONE).noCollission().strength(0.5f), new BlockSetType("caminite", true, EmbersSounds.CAMINITE, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON), 20, false));
	public static final RegistryObject<Block> ITEM_PIPE = BLOCKS.register("item_pipe", () -> new ItemPipeBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_METAL).requiresCorrectToolForDrops().strength(1.6f).dynamicShape().noOcclusion()));
	public static final RegistryObject<Block> ITEM_EXTRACTOR = BLOCKS.register("item_extractor", () -> new ItemExtractorBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_METAL).requiresCorrectToolForDrops().strength(1.6f).dynamicShape().noOcclusion()));
	public static final RegistryObject<Block> EMBER_BORE = BLOCKS.register("ember_bore", () -> new EmberBoreBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.MULTIBLOCK_CENTER).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_BORE_EDGE = BLOCKS.register("ember_bore_edge", () -> new EmberBoreEdgeBlock(Properties.of().mapColor(MapColor.WOOD).sound(EmbersSounds.MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> MECHANICAL_CORE = BLOCKS.register("mechanical_core", () -> new MechanicalCoreBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_ACTIVATOR = BLOCKS.register("ember_activator", () -> new EmberActivatorBlock(Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion(), EmbersSounds.MULTIBLOCK_EXTRA));
	public static final RegistryObject<Block> MELTER = BLOCKS.register("melter", () -> new MelterBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion(), EmbersSounds.MULTIBLOCK_EXTRA));
	public static final RegistryObject<Block> FLUID_PIPE = BLOCKS.register("fluid_pipe", () -> new FluidPipeBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_METAL).requiresCorrectToolForDrops().strength(1.6f).dynamicShape().noOcclusion()));
	public static final RegistryObject<Block> FLUID_EXTRACTOR = BLOCKS.register("fluid_extractor", () -> new FluidExtractorBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_METAL).requiresCorrectToolForDrops().strength(1.6f).dynamicShape().noOcclusion()));
	public static final RegistryObject<Block> FLUID_VESSEL = BLOCKS.register("fluid_vessel", () -> new FluidVesselBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> STAMPER = BLOCKS.register("stamper", () -> new StamperBlock(Properties.of().mapColor(MapColor.WOOD).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> STAMP_BASE = BLOCKS.register("stamp_base", () -> new StampBaseBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> BIN = BLOCKS.register("bin", () -> new BinBlock(Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> MIXER_CENTRIFUGE = BLOCKS.register("mixer_centrifuge", () -> new MixerCentrifugeBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion(), EmbersSounds.MULTIBLOCK_EXTRA));
	public static final RegistryObject<Block> ITEM_DROPPER = BLOCKS.register("item_dropper", () -> new ItemDropperBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_METAL).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> PRESSURE_REFINERY = BLOCKS.register("pressure_refinery", () -> new PressureRefineryBlock(Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion(), EmbersSounds.MULTIBLOCK_EXTRA));
	public static final RegistryObject<Block> EMBER_EJECTOR = BLOCKS.register("ember_ejector", () -> new EmberEjectorBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_MACHINE).requiresCorrectToolForDrops().strength(0.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_FUNNEL = BLOCKS.register("ember_funnel", () -> new EmberFunnelBlock(Properties.of().mapColor(MapColor.NONE).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_RELAY = BLOCKS.register("ember_relay", () -> new EmberRelayBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_METAL).requiresCorrectToolForDrops().strength(0.6f).noOcclusion()));
	public static final RegistryObject<Block> MIRROR_RELAY = BLOCKS.register("mirror_relay", () -> new MirrorRelayBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_METAL).requiresCorrectToolForDrops().strength(0.6f).noOcclusion()));
	public static final RegistryObject<Block> BEAM_SPLITTER = BLOCKS.register("beam_splitter", () -> new BeamSplitterBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.SOLID_METAL).requiresCorrectToolForDrops().strength(0.6f).noOcclusion()));
	public static final RegistryObject<Block> ITEM_VACUUM = BLOCKS.register("item_vacuum", () -> new ItemVacuumBlock(Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> HEARTH_COIL = BLOCKS.register("hearth_coil", () -> new HearthCoilBlock(Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(EmbersSounds.MULTIBLOCK_CENTER).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> HEARTH_COIL_EDGE = BLOCKS.register("hearth_coil_edge", () -> new HearthCoilEdgeBlock(Properties.of().mapColor(MapColor.WOOD).pushReaction(PushReaction.BLOCK).sound(EmbersSounds.MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> RESERVOIR = BLOCKS.register("reservoir", () -> new ReservoirBlock(Properties.of().mapColor(MapColor.WOOD).sound(EmbersSounds.CAMINITE_MULTIBLOCK_CENTER).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> RESERVOIR_EDGE = BLOCKS.register("reservoir_edge", () -> new ReservoirEdgeBlock(Properties.of().mapColor(MapColor.WOOD).pushReaction(PushReaction.BLOCK).sound(EmbersSounds.MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> CAMINITE_RING = BLOCKS.register("caminite_ring", () -> new CaminiteRingBlock(Properties.of().mapColor(MapColor.NONE).pushReaction(PushReaction.BLOCK).noCollission().sound(EmbersSounds.CAMINITE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> CAMINITE_RING_EDGE = BLOCKS.register("caminite_ring_edge", () -> new CaminiteRingEdgeBlock(Properties.of().mapColor(MapColor.WOOD).pushReaction(PushReaction.BLOCK).sound(EmbersSounds.CAMINITE_MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> CAMINITE_GAUGE = BLOCKS.register("caminite_gauge", () -> new CaminiteGaugeBlock(Properties.of().mapColor(MapColor.NONE).pushReaction(PushReaction.BLOCK).noCollission().sound(EmbersSounds.CAMINITE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> CAMINITE_GAUGE_EDGE = BLOCKS.register("caminite_gauge_edge", () -> new CaminiteGaugeEdgeBlock(Properties.of().mapColor(MapColor.WOOD).pushReaction(PushReaction.BLOCK).sound(EmbersSounds.CAMINITE_MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> CAMINITE_VALVE = BLOCKS.register("caminite_valve", () -> new CaminiteValveBlock(Properties.of().mapColor(MapColor.NONE).pushReaction(PushReaction.BLOCK).noCollission().sound(EmbersSounds.CAMINITE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> CAMINITE_VALVE_EDGE = BLOCKS.register("caminite_valve_edge", () -> new CaminiteValveEdgeBlock(Properties.of().mapColor(MapColor.WOOD).pushReaction(PushReaction.BLOCK).sound(EmbersSounds.CAMINITE_MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> CRYSTAL_CELL = BLOCKS.register("crystal_cell", () -> new CrystalCellBlock(Properties.of().mapColor(MapColor.WOOD).pushReaction(PushReaction.BLOCK).sound(EmbersSounds.MULTIBLOCK_CENTER).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> CRYSTAL_CELL_EDGE = BLOCKS.register("crystal_cell_edge", () -> new CrystalCellEdgeBlock(Properties.of().mapColor(MapColor.WOOD).sound(EmbersSounds.MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> CLOCKWORK_ATTENUATOR = BLOCKS.register("clockwork_attenuator", () -> new ClockworkAttenuatorBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.LIGHT_METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> GEOLOGIC_SEPARATOR = BLOCKS.register("geologic_separator", () -> new GeologicSeparatorBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> COPPER_CHARGER = BLOCKS.register("copper_charger", () -> new CopperChargerBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.SOLID_METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_SIPHON = BLOCKS.register("ember_siphon", () -> new EmberSiphonBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> ITEM_TRANSFER = BLOCKS.register("item_transfer", () -> new ItemTransferBlock(Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> FLUID_TRANSFER = BLOCKS.register("fluid_transfer", () -> new FluidTransferBlock(Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> ALCHEMY_PEDESTAL = BLOCKS.register("alchemy_pedestal", () -> new AlchemyPedestalBlock(Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).sound(EmbersSounds.CAMINITE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion(), EmbersSounds.MULTIBLOCK_EXTRA));
	public static final RegistryObject<Block> ALCHEMY_TABLET = BLOCKS.register("alchemy_tablet", () -> new AlchemyTabletBlock(Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> BEAM_CANNON = BLOCKS.register("beam_cannon", () -> new BeamCannonBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.SOLID_METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> MECHANICAL_PUMP = BLOCKS.register("mechanical_pump", () -> new MechanicalPumpBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion(), EmbersSounds.MULTIBLOCK_EXTRA));
	public static final RegistryObject<Block> MINI_BOILER = BLOCKS.register("mini_boiler", () -> new MiniBoilerBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> CATALYTIC_PLUG = BLOCKS.register("catalytic_plug", () -> new CatalyticPlugBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.SOLID_METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> WILDFIRE_STIRLING = BLOCKS.register("wildfire_stirling", () -> new WildfireStirlingBlock(Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).sound(EmbersSounds.SOLID_METAL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> EMBER_INJECTOR = BLOCKS.register("ember_injector", () -> new EmberInjectorBlock(Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final MetalCrystalSeed COPPER_CRYSTAL_SEED = new MetalCrystalSeed("copper");
	public static final MetalCrystalSeed IRON_CRYSTAL_SEED = new MetalCrystalSeed("iron");
	public static final MetalCrystalSeed GOLD_CRYSTAL_SEED = new MetalCrystalSeed("gold");
	public static final MetalCrystalSeed LEAD_CRYSTAL_SEED = new MetalCrystalSeed("lead");
	public static final MetalCrystalSeed SILVER_CRYSTAL_SEED = new MetalCrystalSeed("silver");
	public static final MetalCrystalSeed NICKEL_CRYSTAL_SEED = new MetalCrystalSeed("nickel");
	public static final MetalCrystalSeed TIN_CRYSTAL_SEED = new MetalCrystalSeed("tin");
	public static final MetalCrystalSeed ALUMINUM_CRYSTAL_SEED = new MetalCrystalSeed("aluminum");
	public static final MetalCrystalSeed ZINC_CRYSTAL_SEED = new MetalCrystalSeed("zinc");
	public static final MetalCrystalSeed PLATINUM_CRYSTAL_SEED = new MetalCrystalSeed("platinum");
	public static final MetalCrystalSeed URANIUM_CRYSTAL_SEED = new MetalCrystalSeed("uranium");
	public static final MetalCrystalSeed DAWNSTONE_CRYSTAL_SEED = new MetalCrystalSeed("dawnstone");
	public static final RegistryObject<Block> FIELD_CHART = BLOCKS.register("field_chart", () -> new FieldChartBlock(Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> FIELD_CHART_EDGE = BLOCKS.register("field_chart_edge", () -> new FieldChartEdgeBlock(Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).pushReaction(PushReaction.BLOCK).sound(EmbersSounds.ARCHAIC_MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> IGNEM_REACTOR = BLOCKS.register("ignem_reactor", () -> new IgnemReactorBlock(Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> CATALYSIS_CHAMBER = BLOCKS.register("catalysis_chamber", () -> new CatalysisChamberBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion(), EmbersSounds.MULTIBLOCK_EXTRA));
	public static final RegistryObject<Block> COMBUSTION_CHAMBER = BLOCKS.register("combustion_chamber", () -> new CombustionChamberBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion(), EmbersSounds.MULTIBLOCK_EXTRA));
	public static final RegistryObject<Block> GLIMMER = BLOCKS.register("glimmer", () -> new GlimmerBlock(Properties.of().mapColor(MapColor.COLOR_YELLOW).sound(SoundType.STONE).strength(0f).lightLevel(state -> 15).noParticlesOnBreak().noCollission().noOcclusion()));
	public static final RegistryObject<Block> CINDER_PLINTH = BLOCKS.register("cinder_plinth", () -> new CinderPlinthBlock(Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> DAWNSTONE_ANVIL = BLOCKS.register("dawnstone_anvil", () -> new DawnstoneAnvilBlock(Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).sound(SoundType.ANVIL).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> AUTOMATIC_HAMMER = BLOCKS.register("automatic_hammer", () -> new AutomaticHammerBlock(Properties.of().mapColor(MapColor.WOOD).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> INFERNO_FORGE = BLOCKS.register("inferno_forge", () -> new InfernoForgeBlock(Properties.of().mapColor(MapColor.COLOR_GRAY).sound(EmbersSounds.MULTIBLOCK_CENTER).requiresCorrectToolForDrops().strength(1.6f).noOcclusion(), EmbersSounds.MULTIBLOCK_EXTRA));
	public static final RegistryObject<Block> INFERNO_FORGE_EDGE = BLOCKS.register("inferno_forge_edge", () -> new InfernoForgeEdgeBlock(Properties.of().mapColor(MapColor.TERRACOTTA_YELLOW).sound(EmbersSounds.MULTIBLOCK_EXTRA).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> MNEMONIC_INSCRIBER = BLOCKS.register("mnemonic_inscriber", () -> new MnemonicInscriberBlock(Properties.of().mapColor(MapColor.NONE).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f).noOcclusion()));
	public static final RegistryObject<Block> CHAR_INSTILLER = BLOCKS.register("char_instiller", () -> new CharInstillerBlock(Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.WOOD).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> ATMOSPHERIC_BELLOWS = BLOCKS.register("atmospheric_bellows", () -> new AtmosphericBellowsBlock(Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).sound(SoundType.WOOD).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> ENTROPIC_ENUMERATOR = BLOCKS.register("entropic_enumerator", () -> new EntropicEnumeratorBlock(Properties.of().mapColor(MapColor.COLOR_BLACK).sound(EmbersSounds.ASHEN_STONE).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> HEAT_EXCHANGER = BLOCKS.register("heat_exchanger", () -> new HeatExchangerBlock(Properties.of().mapColor(MapColor.COLOR_ORANGE).sound(EmbersSounds.SOLID_METAL).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> HEAT_INSULATION = BLOCKS.register("heat_insulation", () -> new HeatInsulationBlock(Properties.of().mapColor(MapColor.COLOR_BLACK).sound(EmbersSounds.MACHINE).requiresCorrectToolForDrops().strength(1.6f)));
	public static final RegistryObject<Block> EXCAVATION_BUCKETS = BLOCKS.register("excavation_buckets", () -> new ExcavationBucketsBlock(Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).sound(EmbersSounds.SOLID_METAL).requiresCorrectToolForDrops().strength(1.6f)));

	//items
	public static final RegistryObject<Item> TINKER_HAMMER = ITEMS.register("tinker_hammer", () -> new TinkerHammerItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> TINKER_LENS = ITEMS.register("tinker_lens", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> SMOKY_TINKER_LENS = ITEMS.register("smoky_tinker_lens", () -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> ANCIENT_CODEX = ITEMS.register("ancient_codex", () -> new AncientCodexItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> ATMOSPHERIC_GAUGE_ITEM = ITEMS.register("atmospheric_gauge", () -> new BlockItem(ATMOSPHERIC_GAUGE.get(), new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> EMBER_JAR = ITEMS.register("ember_jar", () -> new EmberJarItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> EMBER_CARTRIDGE = ITEMS.register("ember_cartridge", () -> new EmberCartridgeItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> CLOCKWORK_PICKAXE = ITEMS.register("clockwork_pickaxe", () -> new ClockworkPickaxeItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> CLOCKWORK_AXE = ITEMS.register("clockwork_axe", () -> new ClockworkAxeItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> GRANDHAMMER = ITEMS.register("grandhammer", () -> new ClockworkHammerItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> BLAZING_RAY = ITEMS.register("blazing_ray", () -> new BlazingRayItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> CINDER_STAFF = ITEMS.register("cinder_staff", () -> new CinderStaffItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> ALCHEMICAL_WASTE = ITEMS.register("alchemical_waste", () -> new AlchemyHintItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> ALCHEMICAL_NOTE = ITEMS.register("alchemical_note", () -> new AlchemicalNoteItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> CODEBREAKING_SLATE = ITEMS.register("codebreaking_slate", () -> new CodebreakingSlateItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> TYRFING = ITEMS.register("tyrfing", () -> new TyrfingItem(EmbersTiers.TYRFING, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> INFLICTOR_GEM = ITEMS.register("inflictor_gem", () -> new InflictorGemItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> ASHEN_GOGGLES = ITEMS.register("ashen_goggles", () -> new AshenArmorGemItem(AshenArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new Item.Properties(), ConfigManager.ASHEN_GOGGLES_SLOTS));
	public static final RegistryObject<Item> ASHEN_CLOAK = ITEMS.register("ashen_cloak", () -> new AshenArmorGemItem(AshenArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new Item.Properties(), ConfigManager.ASHEN_CLOAK_SLOTS));
	public static final RegistryObject<Item> ASHEN_LEGGINGS = ITEMS.register("ashen_leggings", () -> new AshenArmorGemItem(AshenArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new Item.Properties(), ConfigManager.ASHEN_LEGGINGS_SLOTS));
	public static final RegistryObject<Item> ASHEN_BOOTS = ITEMS.register("ashen_boots", () -> new AshenArmorGemItem(AshenArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new Item.Properties(), ConfigManager.ASHEN_BOOTS_SLOTS));
	public static final RegistryObject<Item> GLIMMER_CRYSTAL = ITEMS.register("glimmer_crystal", () -> new GlimmerCrystalItem(new Item.Properties().durability(800)));
	public static final RegistryObject<Item> GLIMMER_LAMP = ITEMS.register("glimmer_lamp", () -> new GlimmerLampItem(new Item.Properties().durability(1200)));

	public static final RegistryObject<Item> COPPER_CELL_ITEM = ITEMS.register("copper_cell", () -> new CopperCellBlockItem(COPPER_CELL.get(), new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> CREATIVE_EMBER_ITEM = ITEMS.register("creative_ember_source", () -> new BlockItem(CREATIVE_EMBER.get(), new Item.Properties()));
	public static final RegistryObject<Item> EMBER_DIAL_ITEM = ITEMS.register("ember_dial", () -> new BlockItem(EMBER_DIAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> ITEM_DIAL_ITEM = ITEMS.register("item_dial", () -> new BlockItem(ITEM_DIAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> FLUID_DIAL_ITEM = ITEMS.register("fluid_dial", () -> new BlockItem(FLUID_DIAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> EMBER_EMITTER_ITEM = ITEMS.register("ember_emitter", () -> new BlockItem(EMBER_EMITTER.get(), new Item.Properties()));
	public static final RegistryObject<Item> EMBER_RECEIVER_ITEM = ITEMS.register("ember_receiver", () -> new BlockItem(EMBER_RECEIVER.get(), new Item.Properties()));
	public static final RegistryObject<Item> CAMINITE_LEVER_ITEM = ITEMS.register("caminite_lever", () -> new BlockItem(CAMINITE_LEVER.get(), new Item.Properties()));
	public static final RegistryObject<Item> CAMINITE_BUTTON_ITEM = ITEMS.register("caminite_button", () -> new BlockItem(CAMINITE_BUTTON.get(), new Item.Properties()));
	public static final RegistryObject<Item> ITEM_PIPE_ITEM = ITEMS.register("item_pipe", () -> new BlockItem(ITEM_PIPE.get(), new Item.Properties()));
	public static final RegistryObject<Item> ITEM_EXTRACTOR_ITEM = ITEMS.register("item_extractor", () -> new BlockItem(ITEM_EXTRACTOR.get(), new Item.Properties()));
	public static final RegistryObject<Item> EMBER_BORE_ITEM = ITEMS.register("ember_bore", () -> new BlockItem(EMBER_BORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> MECHANICAL_CORE_ITEM = ITEMS.register("mechanical_core", () -> new BlockItem(MECHANICAL_CORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> EMBER_ACTIVATOR_ITEM = ITEMS.register("ember_activator", () -> new BlockItem(EMBER_ACTIVATOR.get(), new Item.Properties()));
	public static final RegistryObject<Item> MELTER_ITEM = ITEMS.register("melter", () -> new BlockItem(MELTER.get(), new Item.Properties()));
	public static final RegistryObject<Item> FLUID_PIPE_ITEM = ITEMS.register("fluid_pipe", () -> new BlockItem(FLUID_PIPE.get(), new Item.Properties()));
	public static final RegistryObject<Item> FLUID_EXTRACTOR_ITEM = ITEMS.register("fluid_extractor", () -> new BlockItem(FLUID_EXTRACTOR.get(), new Item.Properties()));
	public static final RegistryObject<Item> FLUID_VESSEL_ITEM = ITEMS.register("fluid_vessel", () -> new FluidVesselBlockItem(FLUID_VESSEL.get(), new Item.Properties()));
	public static final RegistryObject<Item> STAMPER_ITEM = ITEMS.register("stamper", () -> new BlockItem(STAMPER.get(), new Item.Properties()));
	public static final RegistryObject<Item> STAMP_BASE_ITEM = ITEMS.register("stamp_base", () -> new BlockItem(STAMP_BASE.get(), new Item.Properties()));
	public static final RegistryObject<Item> BIN_ITEM = ITEMS.register("bin", () -> new BlockItem(BIN.get(), new Item.Properties()));
	public static final RegistryObject<Item> MIXER_CENTRIFUGE_ITEM = ITEMS.register("mixer_centrifuge", () -> new BlockItem(MIXER_CENTRIFUGE.get(), new Item.Properties()));
	public static final RegistryObject<Item> ITEM_DROPPER_ITEM = ITEMS.register("item_dropper", () -> new BlockItem(ITEM_DROPPER.get(), new Item.Properties()));
	public static final RegistryObject<Item> PRESSURE_REFINERY_ITEM = ITEMS.register("pressure_refinery", () -> new BlockItem(PRESSURE_REFINERY.get(), new Item.Properties()));
	public static final RegistryObject<Item> EMBER_EJECTOR_ITEM = ITEMS.register("ember_ejector", () -> new BlockItem(EMBER_EJECTOR.get(), new Item.Properties()));
	public static final RegistryObject<Item> EMBER_FUNNEL_ITEM = ITEMS.register("ember_funnel", () -> new BlockItem(EMBER_FUNNEL.get(), new Item.Properties()));
	public static final RegistryObject<Item> EMBER_RELAY_ITEM = ITEMS.register("ember_relay", () -> new BlockItem(EMBER_RELAY.get(), new Item.Properties()));
	public static final RegistryObject<Item> MIRROR_RELAY_ITEM = ITEMS.register("mirror_relay", () -> new BlockItem(MIRROR_RELAY.get(), new Item.Properties()));
	public static final RegistryObject<Item> BEAM_SPLITTER_ITEM = ITEMS.register("beam_splitter", () -> new BlockItem(BEAM_SPLITTER.get(), new Item.Properties()));
	public static final RegistryObject<Item> ITEM_VACUUM_ITEM = ITEMS.register("item_vacuum", () -> new BlockItem(ITEM_VACUUM.get(), new Item.Properties()));
	public static final RegistryObject<Item> HEARTH_COIL_ITEM = ITEMS.register("hearth_coil", () -> new BlockItem(HEARTH_COIL.get(), new Item.Properties()));
	public static final RegistryObject<Item> RESERVOIR_ITEM = ITEMS.register("reservoir", () -> new BlockItem(RESERVOIR.get(), new Item.Properties()));
	public static final RegistryObject<Item> CAMINITE_RING_ITEM = ITEMS.register("caminite_ring", () -> new CaminiteRingBlockItem(CAMINITE_RING.get(), new Item.Properties()));
	public static final RegistryObject<Item> CAMINITE_GAUGE_ITEM = ITEMS.register("caminite_gauge", () -> new CaminiteRingBlockItem(CAMINITE_GAUGE.get(), new Item.Properties()));
	public static final RegistryObject<Item> CAMINITE_VALVE_ITEM = ITEMS.register("caminite_valve", () -> new CaminiteRingBlockItem(CAMINITE_VALVE.get(), new Item.Properties()));
	public static final RegistryObject<Item> CRYSTAL_CELL_ITEM = ITEMS.register("crystal_cell", () -> new BlockItem(CRYSTAL_CELL.get(), new Item.Properties()));
	public static final RegistryObject<Item> CLOCKWORK_ATTENUATOR_ITEM = ITEMS.register("clockwork_attenuator", () -> new BlockItem(CLOCKWORK_ATTENUATOR.get(), new Item.Properties()));
	public static final RegistryObject<Item> GEOLOGIC_SEPARATOR_ITEM = ITEMS.register("geologic_separator", () -> new BlockItem(GEOLOGIC_SEPARATOR.get(), new Item.Properties()));
	public static final RegistryObject<Item> COPPER_CHARGER_ITEM = ITEMS.register("copper_charger", () -> new BlockItem(COPPER_CHARGER.get(), new Item.Properties()));
	public static final RegistryObject<Item> EMBER_SIPHON_ITEM = ITEMS.register("ember_siphon", () -> new BlockItem(EMBER_SIPHON.get(), new Item.Properties()));
	public static final RegistryObject<Item> ITEM_TRANSFER_ITEM = ITEMS.register("item_transfer", () -> new BlockItem(ITEM_TRANSFER.get(), new Item.Properties()));
	public static final RegistryObject<Item> FLUID_TRANSFER_ITEM = ITEMS.register("fluid_transfer", () -> new BlockItem(FLUID_TRANSFER.get(), new Item.Properties()));
	public static final RegistryObject<Item> ALCHEMY_PEDESTAL_ITEM = ITEMS.register("alchemy_pedestal", () -> new BlockItem(ALCHEMY_PEDESTAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> ALCHEMY_TABLET_ITEM = ITEMS.register("alchemy_tablet", () -> new BlockItem(ALCHEMY_TABLET.get(), new Item.Properties()));
	public static final RegistryObject<Item> BEAM_CANNON_ITEM = ITEMS.register("beam_cannon", () -> new BlockItem(BEAM_CANNON.get(), new Item.Properties()));
	public static final RegistryObject<Item> MECHANICAL_PUMP_ITEM = ITEMS.register("mechanical_pump", () -> new BlockItem(MECHANICAL_PUMP.get(), new Item.Properties()));
	public static final RegistryObject<Item> MINI_BOILER_ITEM = ITEMS.register("mini_boiler", () -> new BlockItem(MINI_BOILER.get(), new Item.Properties()));
	public static final RegistryObject<Item> CATALYTIC_PLUG_ITEM = ITEMS.register("catalytic_plug", () -> new BlockItem(CATALYTIC_PLUG.get(), new Item.Properties()));
	public static final RegistryObject<Item> WILDFIRE_STIRLING_ITEM = ITEMS.register("wildfire_stirling", () -> new BlockItem(WILDFIRE_STIRLING.get(), new Item.Properties()));
	public static final RegistryObject<Item> EMBER_INJECTOR_ITEM = ITEMS.register("ember_injector", () -> new BlockItem(EMBER_INJECTOR.get(), new Item.Properties()));
	public static final RegistryObject<Item> FIELD_CHART_ITEM = ITEMS.register("field_chart", () -> new BlockItem(FIELD_CHART.get(), new Item.Properties()));
	public static final RegistryObject<Item> IGNEM_REACTOR_ITEM = ITEMS.register("ignem_reactor", () -> new BlockItem(IGNEM_REACTOR.get(), new Item.Properties()));
	public static final RegistryObject<Item> CATALYSIS_CHAMBER_ITEM = ITEMS.register("catalysis_chamber", () -> new BlockItem(CATALYSIS_CHAMBER.get(), new Item.Properties()));
	public static final RegistryObject<Item> COMBUSTION_CHAMBER_ITEM = ITEMS.register("combustion_chamber", () -> new BlockItem(COMBUSTION_CHAMBER.get(), new Item.Properties()));
	public static final RegistryObject<Item> CINDER_PLINTH_ITEM = ITEMS.register("cinder_plinth", () -> new BlockItem(CINDER_PLINTH.get(), new Item.Properties()));
	public static final RegistryObject<Item> DAWNSTONE_ANVIL_ITEM = ITEMS.register("dawnstone_anvil", () -> new BlockItem(DAWNSTONE_ANVIL.get(), new Item.Properties()));
	public static final RegistryObject<Item> AUTOMATIC_HAMMER_ITEM = ITEMS.register("automatic_hammer", () -> new BlockItem(AUTOMATIC_HAMMER.get(), new Item.Properties()));
	public static final RegistryObject<Item> INFERNO_FORGE_ITEM = ITEMS.register("inferno_forge", () -> new BlockItem(INFERNO_FORGE.get(), new Item.Properties()));
	public static final RegistryObject<Item> MNEMONIC_INSCRIBER_ITEM = ITEMS.register("mnemonic_inscriber", () -> new BlockItem(MNEMONIC_INSCRIBER.get(), new Item.Properties()));
	public static final RegistryObject<Item> CHAR_INSTILLER_ITEM = ITEMS.register("char_instiller", () -> new BlockItem(CHAR_INSTILLER.get(), new Item.Properties()));
	public static final RegistryObject<Item> ATMOSPHERIC_BELLOWS_ITEM = ITEMS.register("atmospheric_bellows", () -> new BlockItem(ATMOSPHERIC_BELLOWS.get(), new Item.Properties()));
	public static final RegistryObject<Item> ENTROPIC_ENUMERATOR_ITEM = ITEMS.register("entropic_enumerator", () -> new BlockItem(ENTROPIC_ENUMERATOR.get(), new Item.Properties()));
	public static final RegistryObject<Item> HEAT_EXCHANGER_ITEM = ITEMS.register("heat_exchanger", () -> new BlockItem(HEAT_EXCHANGER.get(), new Item.Properties()));
	public static final RegistryObject<Item> HEAT_INSULATION_ITEM = ITEMS.register("heat_insulation", () -> new BlockItem(HEAT_INSULATION.get(), new Item.Properties()));
	public static final RegistryObject<Item> EXCAVATION_BUCKETS_ITEM = ITEMS.register("excavation_buckets", () -> new BlockItem(EXCAVATION_BUCKETS.get(), new Item.Properties()));

	public static final RegistryObject<Item> EMBER_CRYSTAL = ITEMS.register("ember_crystal", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> EMBER_SHARD = ITEMS.register("ember_shard", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> EMBER_GRIT = ITEMS.register("ember_grit", () -> new FuelItem(new Item.Properties(), 1600));
	public static final RegistryObject<Item> CAMINITE_BLEND = ITEMS.register("caminite_blend", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CAMINITE_BRICK = ITEMS.register("caminite_brick", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ARCHAIC_BRICK = ITEMS.register("archaic_brick", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ANCIENT_MOTIVE_CORE = ITEMS.register("ancient_motive_core", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ASH = ITEMS.register("ash", () -> new FuelItem(new Item.Properties(), 200));
	public static final RegistryObject<Item> ASHEN_FABRIC = ITEMS.register("ashen_fabric", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> EMBER_CRYSTAL_CLUSTER = ITEMS.register("ember_crystal_cluster", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> WILDFIRE_CORE = ITEMS.register("wildfire_core", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ISOLATED_MATERIA = ITEMS.register("isolated_materia", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ADHESIVE = ITEMS.register("adhesive", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ARCHAIC_CIRCUIT = ITEMS.register("archaic_circuit", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> SUPERHEATER = ITEMS.register("superheater", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CINDER_JET = ITEMS.register("cinder_jet", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> MUSIC_DISC_7F_PATTERNS = ITEMS.register("music_disc_7f_patterns", () -> new EmberRecordItem(7, EmbersSounds.ULTRASYD_7F_PATTERNS, new Item.Properties().rarity(Rarity.RARE).stacksTo(1), 4444));
	public static final RegistryObject<Item> BLASTING_CORE = ITEMS.register("blasting_core", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CASTER_ORB = ITEMS.register("caster_orb", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RESONATING_BELL = ITEMS.register("resonating_bell", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FLAME_BARRIER = ITEMS.register("flame_barrier", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ELDRITCH_INSIGNIA = ITEMS.register("eldritch_insignia", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INTELLIGENT_APPARATUS = ITEMS.register("intelligent_apparatus", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DIFFRACTION_BARREL = ITEMS.register("diffraction_barrel", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FOCAL_LENS = ITEMS.register("focal_lens", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SHIFTING_SCALES = ITEMS.register("shifting_scales", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> WINDING_GEARS = ITEMS.register("winding_gears", () -> new Item(new Item.Properties()));

	static { COPPER_CRYSTAL_SEED.makeItem(); }
	static { IRON_CRYSTAL_SEED.makeItem(); }
	static { GOLD_CRYSTAL_SEED.makeItem(); }
	static { LEAD_CRYSTAL_SEED.makeItem(); }
	static { SILVER_CRYSTAL_SEED.makeItem(); }
	static { NICKEL_CRYSTAL_SEED.makeItem(); }
	static { TIN_CRYSTAL_SEED.makeItem(); }
	static { ALUMINUM_CRYSTAL_SEED.makeItem(); }
	static { ZINC_CRYSTAL_SEED.makeItem(); }
	static { PLATINUM_CRYSTAL_SEED.makeItem(); }
	static { URANIUM_CRYSTAL_SEED.makeItem(); }
	static { DAWNSTONE_CRYSTAL_SEED.makeItem(); }

	public static final RegistryObject<Item> RAW_CAMINITE_PLATE = ITEMS.register("raw_caminite_plate", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RAW_FLAT_STAMP = ITEMS.register("raw_flat_stamp", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RAW_INGOT_STAMP = ITEMS.register("raw_ingot_stamp", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RAW_NUGGET_STAMP = ITEMS.register("raw_nugget_stamp", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RAW_PLATE_STAMP = ITEMS.register("raw_plate_stamp", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RAW_GEAR_STAMP = ITEMS.register("raw_gear_stamp", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> CAMINITE_PLATE = ITEMS.register("caminite_plate", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FLAT_STAMP = ITEMS.register("flat_stamp", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INGOT_STAMP = ITEMS.register("ingot_stamp", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> NUGGET_STAMP = ITEMS.register("nugget_stamp", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PLATE_STAMP = ITEMS.register("plate_stamp", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> GEAR_STAMP = ITEMS.register("gear_stamp", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> IRON_ASPECTUS = ITEMS.register("iron_aspectus", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> COPPER_ASPECTUS = ITEMS.register("copper_aspectus", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> LEAD_ASPECTUS = ITEMS.register("lead_aspectus", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SILVER_ASPECTUS = ITEMS.register("silver_aspectus", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DAWNSTONE_ASPECTUS = ITEMS.register("dawnstone_aspectus", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate", () -> new Item(new Item.Properties()));
	//public static final RegistryObject<Item> GOLD_PLATE = ITEMS.register("gold_plate", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("copper_plate", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> RAW_LEAD = ITEMS.register("raw_lead", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> LEAD_PLATE = ITEMS.register("lead_plate", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> LEAD_ORE_ITEM = ITEMS.register("lead_ore", () -> new BlockItem(LEAD_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DEEPSLATE_LEAD_ORE_ITEM = ITEMS.register("deepslate_lead_ore", () -> new BlockItem(DEEPSLATE_LEAD_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> RAW_LEAD_BLOCK_ITEM = ITEMS.register("raw_lead_block", () -> new BlockItem(RAW_LEAD_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> LEAD_BLOCK_ITEM = ITEMS.register("lead_block", () -> new BlockItem(LEAD_BLOCK.get(), new Item.Properties()));

	public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SILVER_PLATE = ITEMS.register("silver_plate", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> SILVER_ORE_ITEM = ITEMS.register("silver_ore", () -> new BlockItem(SILVER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> DEEPSLATE_SILVER_ORE_ITEM = ITEMS.register("deepslate_silver_ore", () -> new BlockItem(DEEPSLATE_SILVER_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> RAW_SILVER_BLOCK_ITEM = ITEMS.register("raw_silver_block", () -> new BlockItem(RAW_SILVER_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> SILVER_BLOCK_ITEM = ITEMS.register("silver_block", () -> new BlockItem(SILVER_BLOCK.get(), new Item.Properties()));

	public static final RegistryObject<Item> DAWNSTONE_INGOT = ITEMS.register("dawnstone_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DAWNSTONE_NUGGET = ITEMS.register("dawnstone_nugget", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DAWNSTONE_PLATE = ITEMS.register("dawnstone_plate", () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> DAWNSTONE_BLOCK_ITEM = ITEMS.register("dawnstone_block", () -> new BlockItem(DAWNSTONE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> MITHRIL_BLOCK_ITEM = ITEMS.register("mithril_block", () -> new BlockItem(MITHRIL_BLOCK.get(), new Item.Properties()));

	public static final ToolSet LEAD_TOOLS = new ToolSet("lead", EmbersTiers.LEAD);
	public static final ToolSet SILVER_TOOLS = new ToolSet("silver", EmbersTiers.SILVER);
	public static final ToolSet DAWNSTONE_TOOLS = new ToolSet("dawnstone", EmbersTiers.DAWNSTONE);

	public static final RegistryObject<Item> CAMINITE_BRICKS_ITEM = ITEMS.register("caminite_bricks", () -> new BlockItem(CAMINITE_BRICKS.get(), new Item.Properties()));
	static { CAMINITE_BRICKS_DECO.makeItems(); }
	public static final RegistryObject<Item> CAMINITE_LARGE_BRICKS_ITEM = ITEMS.register("caminite_large_bricks", () -> new BlockItem(CAMINITE_LARGE_BRICKS.get(), new Item.Properties()));
	static { CAMINITE_LARGE_BRICKS_DECO.makeItems(); }
	public static final RegistryObject<Item> RAW_CAMINITE_BLOCK_ITEM = ITEMS.register("raw_caminite_block", () -> new BlockItem(RAW_CAMINITE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> CAMINITE_LARGE_TILE_ITEM = ITEMS.register("caminite_large_tile", () -> new BlockItem(CAMINITE_LARGE_TILE.get(), new Item.Properties()));
	static { CAMINITE_LARGE_TILE_DECO.makeItems(); }
	public static final RegistryObject<Item> CAMINITE_TILES_ITEM = ITEMS.register("caminite_tiles", () -> new BlockItem(CAMINITE_TILES.get(), new Item.Properties()));
	static { CAMINITE_TILES_DECO.makeItems(); }
	public static final RegistryObject<Item> ARCHAIC_BRICKS_ITEM = ITEMS.register("archaic_bricks", () -> new BlockItem(ARCHAIC_BRICKS.get(), new Item.Properties()));
	static { ARCHAIC_BRICKS_DECO.makeItems(); }
	public static final RegistryObject<Item> ARCHAIC_EDGE_ITEM = ITEMS.register("archaic_edge", () -> new BlockItem(ARCHAIC_EDGE.get(), new Item.Properties()));
	public static final RegistryObject<Item> ARCHAIC_TILE_ITEM = ITEMS.register("archaic_tile", () -> new BlockItem(ARCHAIC_TILE.get(), new Item.Properties()));
	static { ARCHAIC_TILE_DECO.makeItems(); }
	public static final RegistryObject<Item> ARCHAIC_LARGE_BRICKS_ITEM = ITEMS.register("archaic_large_bricks", () -> new BlockItem(ARCHAIC_LARGE_BRICKS.get(), new Item.Properties()));
	static { ARCHAIC_LARGE_BRICKS_DECO.makeItems(); }
	public static final RegistryObject<Item> ARCHAIC_LIGHT_ITEM = ITEMS.register("archaic_light", () -> new BlockItem(ARCHAIC_LIGHT.get(), new Item.Properties()));
	public static final RegistryObject<Item> ASHEN_STONE_ITEM = ITEMS.register("ashen_stone", () -> new BlockItem(ASHEN_STONE.get(), new Item.Properties()));
	static { ASHEN_STONE_DECO.makeItems(); }
	public static final RegistryObject<Item> ASHEN_BRICK_ITEM = ITEMS.register("ashen_brick", () -> new BlockItem(ASHEN_BRICK.get(), new Item.Properties()));
	static { ASHEN_BRICK_DECO.makeItems(); }
	public static final RegistryObject<Item> ASHEN_TILE_ITEM = ITEMS.register("ashen_tile", () -> new BlockItem(ASHEN_TILE.get(), new Item.Properties()));
	static { ASHEN_TILE_DECO.makeItems(); }
	public static final RegistryObject<Item> SEALED_PLANKS_ITEM = ITEMS.register("sealed_planks", () -> new BlockItem(SEALED_PLANKS.get(), new Item.Properties()));
	static { SEALED_PLANKS_DECO.makeItems(); }
	public static final RegistryObject<Item> REINFORCED_SEALED_PLANKS_ITEM = ITEMS.register("reinforced_sealed_planks", () -> new BlockItem(REINFORCED_SEALED_PLANKS.get(), new Item.Properties()));
	public static final RegistryObject<Item> SEALED_WOOD_TILE_ITEM = ITEMS.register("sealed_wood_tile", () -> new BlockItem(SEALED_WOOD_TILE.get(), new Item.Properties()));
	static { SEALED_WOOD_TILE_DECO.makeItems(); }
	public static final RegistryObject<Item> SEALED_WOOD_PILLAR_ITEM = ITEMS.register("sealed_wood_pillar", () -> new BlockItem(SEALED_WOOD_PILLAR.get(), new Item.Properties()));
	public static final RegistryObject<Item> SEALED_WOOD_KEG_ITEM = ITEMS.register("sealed_wood_keg", () -> new BlockItem(SEALED_WOOD_KEG.get(), new Item.Properties()));
	public static final RegistryObject<Item> SOLIDIFIED_METAL_ITEM = ITEMS.register("solidified_metal", () -> new BlockItem(SOLIDIFIED_METAL.get(), new Item.Properties()));
	public static final RegistryObject<Item> METAL_PLATFORM_ITEM = ITEMS.register("metal_platform", () -> new BlockItem(METAL_PLATFORM.get(), new Item.Properties()));
	static { METAL_PLATFORM_DECO.makeItems(); }
	public static final RegistryObject<Item> EMBER_LANTERN_ITEM = ITEMS.register("ember_lantern", () -> new BlockItem(EMBER_LANTERN.get(), new Item.Properties()));

	//fluids
	public static final FluidStuff MOLTEN_IRON = addFluid("Molten Iron", new FluidInfo("molten_iron", 0xC72913, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_GOLD = addFluid("Molten Gold", new FluidInfo("molten_gold", 0xF9C026, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_COPPER = addFluid("Molten Copper", new FluidInfo("molten_copper", 0xEA7E38, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_LEAD = addFluid("Molten Lead", new FluidInfo("molten_lead", 0x665975, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_SILVER = addFluid("Molten Silver", new FluidInfo("molten_silver", 0xBCEAF7, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_DAWNSTONE = addFluid("Molten Dawnstone", new FluidInfo("molten_dawnstone", 0xFF9C36, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_NICKEL = addFluid("Molten Nickel", new FluidInfo("molten_nickel", 0xDDEBC0, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_TIN = addFluid("Molten Tin", new FluidInfo("molten_tin", 0xDCEDE5, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_ALUMINUM = addFluid("Molten Aluminum", new FluidInfo("molten_aluminum", 0xFFAE9C, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_ZINC = addFluid("Molten Zinc", new FluidInfo("molten_zinc", 0x8C8D7B, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_PLATINUM = addFluid("Molten Platinum", new FluidInfo("molten_platinum", 0xA6DEE8, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_URANIUM = addFluid("Molten Uranium", new FluidInfo("molten_uranium", 0x314630, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_BRONZE = addFluid("Molten Bronze", new FluidInfo("molten_bronze", 0xEDAE66, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_ELECTRUM = addFluid("Molten Electrum", new FluidInfo("molten_electrum", 0xFAE176, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_BRASS = addFluid("Molten Brass", new FluidInfo("molten_brass", 0xD3A756, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_CONSTANTAN = addFluid("Molten Constantan", new FluidInfo("molten_constantan", 0xB55C46, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff MOLTEN_INVAR = addFluid("Molten Invar", new FluidInfo("molten_invar", 0xB1D0D1, 0.1F, 1.5F), MoltenMetalFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2), moltenMetalProps());

	public static final FluidStuff STEAM = addFluid("Steam", new FluidInfo("steam", 0xFFFCFC, 0.1F, 1.5F), SteamFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(3),
			FluidType.Properties.create()
			.canSwim(false)
			.canDrown(false)
			.pathType(BlockPathTypes.LAVA)
			.adjacentPathType(null)
			.motionScale(0.0005D)
			.canPushEntity(false)
			.canHydrate(true)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
			.density(-1000)
			.viscosity(100)
			.temperature(400));

	public static final FluidStuff SOUL_CRUDE = addFluid("Soul Crude", new FluidInfo("soul_crude", 0x35261D, 0.1F, 1.5F), ViscousFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).slopeFindDistance(2).levelDecreasePerBlock(2),
			FluidType.Properties.create()
			.canSwim(false)
			.canDrown(true)
			.motionScale(0.0023333333333333335D)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
			.density(3000)
			.viscosity(4000)
			.temperature(330));

	public static final FluidStuff DWARVEN_OIL = addFluid("Dwarven Oil", new FluidInfo("dwarven_oil", 0xffDB18, 0.1F, 1.5F), ViscousFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(8).slopeFindDistance(2).levelDecreasePerBlock(2),
			FluidType.Properties.create()
			.canSwim(false)
			.canDrown(true)
			.motionScale(0.0023333333333333335D)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
			.density(2000)
			.viscosity(2000)
			.temperature(330));

	public static final FluidStuff DWARVEN_GAS = addFluid("Dwarven Gas", new FluidInfo("dwarven_gas", 0x99DC4D, 0.1F, 1.5F), SteamFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(3).slopeFindDistance(2).levelDecreasePerBlock(2),
			FluidType.Properties.create()
			.canSwim(false)
			.canDrown(false)
			.pathType(BlockPathTypes.LAVA)
			.adjacentPathType(null)
			.motionScale(0.0005D)
			.canPushEntity(false)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
			.density(-100)
			.viscosity(100)
			.temperature(400));

	//block entities
	public static final RegistryObject<BlockEntityType<CopperCellBlockEntity>> COPPER_CELL_ENTITY = BLOCK_ENTITY_TYPES.register("copper_cell", () -> BlockEntityType.Builder.of(CopperCellBlockEntity::new, COPPER_CELL.get()).build(null));
	public static final RegistryObject<BlockEntityType<CreativeEmberBlockEntity>> CREATIVE_EMBER_ENTITY = BLOCK_ENTITY_TYPES.register("creative_ember_source", () -> BlockEntityType.Builder.of(CreativeEmberBlockEntity::new, CREATIVE_EMBER.get()).build(null));
	public static final RegistryObject<BlockEntityType<AtmosphericGaugeBlockEntity>> ATMOSPHERIC_GAUGE_ENTITY = BLOCK_ENTITY_TYPES.register("atmospheric_gauge", () -> BlockEntityType.Builder.of(AtmosphericGaugeBlockEntity::new, ATMOSPHERIC_GAUGE.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberEmitterBlockEntity>> EMBER_EMITTER_ENTITY = BLOCK_ENTITY_TYPES.register("ember_emitter", () -> BlockEntityType.Builder.of(EmberEmitterBlockEntity::new, EMBER_EMITTER.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberReceiverBlockEntity>> EMBER_RECEIVER_ENTITY = BLOCK_ENTITY_TYPES.register("ember_receiver", () -> BlockEntityType.Builder.of(EmberReceiverBlockEntity::new, EMBER_RECEIVER.get()).build(null));
	public static final RegistryObject<BlockEntityType<ItemPipeBlockEntity>> ITEM_PIPE_ENTITY = BLOCK_ENTITY_TYPES.register("item_pipe", () -> BlockEntityType.Builder.of(ItemPipeBlockEntity::new, ITEM_PIPE.get()).build(null));
	public static final RegistryObject<BlockEntityType<ItemExtractorBlockEntity>> ITEM_EXTRACTOR_ENTITY = BLOCK_ENTITY_TYPES.register("item_extractor", () -> BlockEntityType.Builder.of(ItemExtractorBlockEntity::new, ITEM_EXTRACTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberBoreBlockEntity>> EMBER_BORE_ENTITY = BLOCK_ENTITY_TYPES.register("ember_bore", () -> BlockEntityType.Builder.of(EmberBoreBlockEntity::new, EMBER_BORE.get()).build(null));
	public static final RegistryObject<BlockEntityType<MechanicalCoreBlockEntity>> MECHANICAL_CORE_ENTITY = BLOCK_ENTITY_TYPES.register("mechanical_core", () -> BlockEntityType.Builder.of(MechanicalCoreBlockEntity::new, MECHANICAL_CORE.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberActivatorBottomBlockEntity>> EMBER_ACTIVATOR_BOTTOM_ENTITY = BLOCK_ENTITY_TYPES.register("ember_activator_bottom", () -> BlockEntityType.Builder.of(EmberActivatorBottomBlockEntity::new, EMBER_ACTIVATOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberActivatorTopBlockEntity>> EMBER_ACTIVATOR_TOP_ENTITY = BLOCK_ENTITY_TYPES.register("ember_activator_top", () -> BlockEntityType.Builder.of(EmberActivatorTopBlockEntity::new, EMBER_ACTIVATOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<MelterBottomBlockEntity>> MELTER_BOTTOM_ENTITY = BLOCK_ENTITY_TYPES.register("melter_bottom", () -> BlockEntityType.Builder.of(MelterBottomBlockEntity::new, MELTER.get()).build(null));
	public static final RegistryObject<BlockEntityType<MelterTopBlockEntity>> MELTER_TOP_ENTITY = BLOCK_ENTITY_TYPES.register("melter_top", () -> BlockEntityType.Builder.of(MelterTopBlockEntity::new, MELTER.get()).build(null));
	public static final RegistryObject<BlockEntityType<FluidPipeBlockEntity>> FLUID_PIPE_ENTITY = BLOCK_ENTITY_TYPES.register("fluid_pipe", () -> BlockEntityType.Builder.of(FluidPipeBlockEntity::new, FLUID_PIPE.get()).build(null));
	public static final RegistryObject<BlockEntityType<FluidExtractorBlockEntity>> FLUID_EXTRACTOR_ENTITY = BLOCK_ENTITY_TYPES.register("fluid_extractor", () -> BlockEntityType.Builder.of(FluidExtractorBlockEntity::new, FLUID_EXTRACTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<FluidVesselBlockEntity>> FLUID_VESSEL_ENTITY = BLOCK_ENTITY_TYPES.register("fluid_vesel", () -> BlockEntityType.Builder.of(FluidVesselBlockEntity::new, FLUID_VESSEL.get()).build(null));
	public static final RegistryObject<BlockEntityType<StamperBlockEntity>> STAMPER_ENTITY = BLOCK_ENTITY_TYPES.register("stamper", () -> BlockEntityType.Builder.of(StamperBlockEntity::new, STAMPER.get()).build(null));
	public static final RegistryObject<BlockEntityType<StampBaseBlockEntity>> STAMP_BASE_ENTITY = BLOCK_ENTITY_TYPES.register("stamp_base", () -> BlockEntityType.Builder.of(StampBaseBlockEntity::new, STAMP_BASE.get()).build(null));
	public static final RegistryObject<BlockEntityType<BinBlockEntity>> BIN_ENTITY = BLOCK_ENTITY_TYPES.register("bin", () -> BlockEntityType.Builder.of(BinBlockEntity::new, BIN.get()).build(null));
	public static final RegistryObject<BlockEntityType<MixerCentrifugeBottomBlockEntity>> MIXER_CENTRIFUGE_BOTTOM_ENTITY = BLOCK_ENTITY_TYPES.register("mixer_centrifuge_bottom", () -> BlockEntityType.Builder.of(MixerCentrifugeBottomBlockEntity::new, MIXER_CENTRIFUGE.get()).build(null));
	public static final RegistryObject<BlockEntityType<MixerCentrifugeTopBlockEntity>> MIXER_CENTRIFUGE_TOP_ENTITY = BLOCK_ENTITY_TYPES.register("mixer_centrifuge_top", () -> BlockEntityType.Builder.of(MixerCentrifugeTopBlockEntity::new, MIXER_CENTRIFUGE.get()).build(null));
	public static final RegistryObject<BlockEntityType<ItemDropperBlockEntity>> ITEM_DROPPER_ENTITY = BLOCK_ENTITY_TYPES.register("item_dropper", () -> BlockEntityType.Builder.of(ItemDropperBlockEntity::new, ITEM_DROPPER.get()).build(null));
	public static final RegistryObject<BlockEntityType<PressureRefineryBottomBlockEntity>> PRESSURE_REFINERY_BOTTOM_ENTITY = BLOCK_ENTITY_TYPES.register("pressure_refinery_bottom", () -> BlockEntityType.Builder.of(PressureRefineryBottomBlockEntity::new, PRESSURE_REFINERY.get()).build(null));
	public static final RegistryObject<BlockEntityType<PressureRefineryTopBlockEntity>> PRESSURE_REFINERY_TOP_ENTITY = BLOCK_ENTITY_TYPES.register("pressure_refinery_top", () -> BlockEntityType.Builder.of(PressureRefineryTopBlockEntity::new, PRESSURE_REFINERY.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberEjectorBlockEntity>> EMBER_EJECTOR_ENTITY = BLOCK_ENTITY_TYPES.register("ember_ejector", () -> BlockEntityType.Builder.of(EmberEjectorBlockEntity::new, EMBER_EJECTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberFunnelBlockEntity>> EMBER_FUNNEL_ENTITY = BLOCK_ENTITY_TYPES.register("ember_funnel", () -> BlockEntityType.Builder.of(EmberFunnelBlockEntity::new, EMBER_FUNNEL.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberRelayBlockEntity>> EMBER_RELAY_ENTITY = BLOCK_ENTITY_TYPES.register("ember_relay", () -> BlockEntityType.Builder.of(EmberRelayBlockEntity::new, EMBER_RELAY.get()).build(null));
	public static final RegistryObject<BlockEntityType<MirrorRelayBlockEntity>> MIRROR_RELAY_ENTITY = BLOCK_ENTITY_TYPES.register("mirror_relay", () -> BlockEntityType.Builder.of(MirrorRelayBlockEntity::new, MIRROR_RELAY.get()).build(null));
	public static final RegistryObject<BlockEntityType<BeamSplitterBlockEntity>> BEAM_SPLITTER_ENTITY = BLOCK_ENTITY_TYPES.register("beam_splitter", () -> BlockEntityType.Builder.of(BeamSplitterBlockEntity::new, BEAM_SPLITTER.get()).build(null));
	public static final RegistryObject<BlockEntityType<ItemVacuumBlockEntity>> ITEM_VACUUM_ENTITY = BLOCK_ENTITY_TYPES.register("item_vacuum", () -> BlockEntityType.Builder.of(ItemVacuumBlockEntity::new, ITEM_VACUUM.get()).build(null));
	public static final RegistryObject<BlockEntityType<HearthCoilBlockEntity>> HEARTH_COIL_ENTITY = BLOCK_ENTITY_TYPES.register("hearth_coil", () -> BlockEntityType.Builder.of(HearthCoilBlockEntity::new, HEARTH_COIL.get()).build(null));
	public static final RegistryObject<BlockEntityType<ReservoirBlockEntity>> RESERVOIR_ENTITY = BLOCK_ENTITY_TYPES.register("reservoir", () -> BlockEntityType.Builder.of(ReservoirBlockEntity::new, RESERVOIR.get()).build(null));
	public static final RegistryObject<BlockEntityType<CaminiteValveBlockEntity>> CAMINITE_VALVE_ENTITY = BLOCK_ENTITY_TYPES.register("caminite_valve", () -> BlockEntityType.Builder.of(CaminiteValveBlockEntity::new, CAMINITE_VALVE_EDGE.get()).build(null));
	public static final RegistryObject<BlockEntityType<CrystalCellBlockEntity>> CRYSTAL_CELL_ENTITY = BLOCK_ENTITY_TYPES.register("crystal_cell", () -> BlockEntityType.Builder.of(CrystalCellBlockEntity::new, CRYSTAL_CELL.get()).build(null));
	public static final RegistryObject<BlockEntityType<ClockworkAttenuatorBlockEntity>> CLOCKWORK_ATTENUATOR_ENTITY = BLOCK_ENTITY_TYPES.register("clockwork_attenuator", () -> BlockEntityType.Builder.of(ClockworkAttenuatorBlockEntity::new, CLOCKWORK_ATTENUATOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<GeologicSeparatorBlockEntity>> GEOLOGIC_SEPARATOR_ENTITY = BLOCK_ENTITY_TYPES.register("geologic_separator", () -> BlockEntityType.Builder.of(GeologicSeparatorBlockEntity::new, GEOLOGIC_SEPARATOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<CopperChargerBlockEntity>> COPPER_CHARGER_ENTITY = BLOCK_ENTITY_TYPES.register("copper_charger", () -> BlockEntityType.Builder.of(CopperChargerBlockEntity::new, COPPER_CHARGER.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberSiphonBlockEntity>> EMBER_SIPHON_ENTITY = BLOCK_ENTITY_TYPES.register("ember_siphon", () -> BlockEntityType.Builder.of(EmberSiphonBlockEntity::new, EMBER_SIPHON.get()).build(null));
	public static final RegistryObject<BlockEntityType<ItemTransferBlockEntity>> ITEM_TRANSFER_ENTITY = BLOCK_ENTITY_TYPES.register("item_transfer", () -> BlockEntityType.Builder.of(ItemTransferBlockEntity::new, ITEM_TRANSFER.get()).build(null));
	public static final RegistryObject<BlockEntityType<FluidTransferBlockEntity>> FLUID_TRANSFER_ENTITY = BLOCK_ENTITY_TYPES.register("fluid_transfer", () -> BlockEntityType.Builder.of(FluidTransferBlockEntity::new, FLUID_TRANSFER.get()).build(null));
	public static final RegistryObject<BlockEntityType<AlchemyPedestalBlockEntity>> ALCHEMY_PEDESTAL_ENTITY = BLOCK_ENTITY_TYPES.register("alchemy_pedestal", () -> BlockEntityType.Builder.of(AlchemyPedestalBlockEntity::new, ALCHEMY_PEDESTAL.get()).build(null));
	public static final RegistryObject<BlockEntityType<AlchemyPedestalTopBlockEntity>> ALCHEMY_PEDESTAL_TOP_ENTITY = BLOCK_ENTITY_TYPES.register("alchemy_pedestal_top", () -> BlockEntityType.Builder.of(AlchemyPedestalTopBlockEntity::new, ALCHEMY_PEDESTAL.get()).build(null));
	public static final RegistryObject<BlockEntityType<AlchemyTabletBlockEntity>> ALCHEMY_TABLET_ENTITY = BLOCK_ENTITY_TYPES.register("alchemy_tablet", () -> BlockEntityType.Builder.of(AlchemyTabletBlockEntity::new, ALCHEMY_TABLET.get()).build(null));
	public static final RegistryObject<BlockEntityType<BeamCannonBlockEntity>> BEAM_CANNON_ENTITY = BLOCK_ENTITY_TYPES.register("beam_cannon", () -> BlockEntityType.Builder.of(BeamCannonBlockEntity::new, BEAM_CANNON.get()).build(null));
	public static final RegistryObject<BlockEntityType<MechanicalPumpBottomBlockEntity>> MECHANICAL_PUMP_BOTTOM_ENTITY = BLOCK_ENTITY_TYPES.register("mechanical_pump_bottom", () -> BlockEntityType.Builder.of(MechanicalPumpBottomBlockEntity::new, MECHANICAL_PUMP.get()).build(null));
	public static final RegistryObject<BlockEntityType<MechanicalPumpTopBlockEntity>> MECHANICAL_PUMP_TOP_ENTITY = BLOCK_ENTITY_TYPES.register("mechanical_pump_top", () -> BlockEntityType.Builder.of(MechanicalPumpTopBlockEntity::new, MECHANICAL_PUMP.get()).build(null));
	public static final RegistryObject<BlockEntityType<MiniBoilerBlockEntity>> MINI_BOILER_ENTITY = BLOCK_ENTITY_TYPES.register("mini_boiler", () -> BlockEntityType.Builder.of(MiniBoilerBlockEntity::new, MINI_BOILER.get()).build(null));
	public static final RegistryObject<BlockEntityType<CatalyticPlugBlockEntity>> CATALYTIC_PLUG_ENTITY = BLOCK_ENTITY_TYPES.register("catalytic_plug", () -> BlockEntityType.Builder.of(CatalyticPlugBlockEntity::new, CATALYTIC_PLUG.get()).build(null));
	public static final RegistryObject<BlockEntityType<WildfireStirlingBlockEntity>> WILDFIRE_STIRLING_ENTITY = BLOCK_ENTITY_TYPES.register("wildfire_stirling", () -> BlockEntityType.Builder.of(WildfireStirlingBlockEntity::new, WILDFIRE_STIRLING.get()).build(null));
	public static final RegistryObject<BlockEntityType<EmberInjectorBlockEntity>> EMBER_INJECTOR_ENTITY = BLOCK_ENTITY_TYPES.register("ember_injector", () -> BlockEntityType.Builder.of(EmberInjectorBlockEntity::new, EMBER_INJECTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<FieldChartBlockEntity>> FIELD_CHART_ENTITY = BLOCK_ENTITY_TYPES.register("field_chart", () -> BlockEntityType.Builder.of(FieldChartBlockEntity::new, FIELD_CHART.get()).build(null));
	public static final RegistryObject<BlockEntityType<IgnemReactorBlockEntity>> IGNEM_REACTOR_ENTITY = BLOCK_ENTITY_TYPES.register("ignem_reactor", () -> BlockEntityType.Builder.of(IgnemReactorBlockEntity::new, IGNEM_REACTOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<CatalysisChamberBlockEntity>> CATALYSIS_CHAMBER_ENTITY = BLOCK_ENTITY_TYPES.register("catalysis_chamber", () -> BlockEntityType.Builder.of(CatalysisChamberBlockEntity::new, CATALYSIS_CHAMBER.get()).build(null));
	public static final RegistryObject<BlockEntityType<CombustionChamberBlockEntity>> COMBUSTION_CHAMBER_ENTITY = BLOCK_ENTITY_TYPES.register("combustion_chamber", () -> BlockEntityType.Builder.of(CombustionChamberBlockEntity::new, COMBUSTION_CHAMBER.get()).build(null));
	public static final RegistryObject<BlockEntityType<CinderPlinthBlockEntity>> CINDER_PLINTH_ENTITY = BLOCK_ENTITY_TYPES.register("cinder_plinth", () -> BlockEntityType.Builder.of(CinderPlinthBlockEntity::new, CINDER_PLINTH.get()).build(null));
	public static final RegistryObject<BlockEntityType<DawnstoneAnvilBlockEntity>> DAWNSTONE_ANVIL_ENTITY = BLOCK_ENTITY_TYPES.register("dawnstone_anvil", () -> BlockEntityType.Builder.of(DawnstoneAnvilBlockEntity::new, DAWNSTONE_ANVIL.get()).build(null));
	public static final RegistryObject<BlockEntityType<AutomaticHammerBlockEntity>> AUTOMATIC_HAMMER_ENTITY = BLOCK_ENTITY_TYPES.register("automatic_hammer", () -> BlockEntityType.Builder.of(AutomaticHammerBlockEntity::new, AUTOMATIC_HAMMER.get()).build(null));
	public static final RegistryObject<BlockEntityType<InfernoForgeBottomBlockEntity>> INFERNO_FORGE_BOTTOM_ENTITY = BLOCK_ENTITY_TYPES.register("inferno_forge_bottom", () -> BlockEntityType.Builder.of(InfernoForgeBottomBlockEntity::new, INFERNO_FORGE.get()).build(null));
	public static final RegistryObject<BlockEntityType<InfernoForgeTopBlockEntity>> INFERNO_FORGE_TOP_ENTITY = BLOCK_ENTITY_TYPES.register("inferno_forge_top", () -> BlockEntityType.Builder.of(InfernoForgeTopBlockEntity::new, INFERNO_FORGE.get()).build(null));
	public static final RegistryObject<BlockEntityType<MnemonicInscriberBlockEntity>> MNEMONIC_INSCRIBER_ENTITY = BLOCK_ENTITY_TYPES.register("mnemonic_inscriber", () -> BlockEntityType.Builder.of(MnemonicInscriberBlockEntity::new, MNEMONIC_INSCRIBER.get()).build(null));
	public static final RegistryObject<BlockEntityType<CharInstillerBlockEntity>> CHAR_INSTILLER_ENTITY = BLOCK_ENTITY_TYPES.register("char_instiller", () -> BlockEntityType.Builder.of(CharInstillerBlockEntity::new, CHAR_INSTILLER.get()).build(null));
	public static final RegistryObject<BlockEntityType<AtmosphericBellowsBlockEntity>> ATMOSPHERIC_BELLOWS_ENTITY = BLOCK_ENTITY_TYPES.register("atmospheric_bellows", () -> BlockEntityType.Builder.of(AtmosphericBellowsBlockEntity::new, ATMOSPHERIC_BELLOWS.get()).build(null));
	public static final RegistryObject<BlockEntityType<EntropicEnumeratorBlockEntity>> ENTROPIC_ENUMERATOR_ENTITY = BLOCK_ENTITY_TYPES.register("entropic_enumerator", () -> BlockEntityType.Builder.of(EntropicEnumeratorBlockEntity::new, ENTROPIC_ENUMERATOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<MithrilBlockEntity>> MITHRIL_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("mithril_block", () -> BlockEntityType.Builder.of(MithrilBlockEntity::new, MITHRIL_BLOCK.get()).build(null));
	public static final RegistryObject<BlockEntityType<HeatExchangerBlockEntity>> HEAT_EXCHANGER_ENTITY = BLOCK_ENTITY_TYPES.register("heat_exchanger", () -> BlockEntityType.Builder.of(HeatExchangerBlockEntity::new, HEAT_EXCHANGER.get()).build(null));
	public static final RegistryObject<BlockEntityType<HeatInsulationBlockEntity>> HEAT_INSULATION_ENTITY = BLOCK_ENTITY_TYPES.register("heat_insulation", () -> BlockEntityType.Builder.of(HeatInsulationBlockEntity::new, HEAT_INSULATION.get()).build(null));
	public static final RegistryObject<BlockEntityType<ExcavationBucketsBlockEntity>> EXCAVATION_BUCKETS_ENTITY = BLOCK_ENTITY_TYPES.register("excavation_buckets", () -> BlockEntityType.Builder.of(ExcavationBucketsBlockEntity::new, EXCAVATION_BUCKETS.get()).build(null));

	//creative tabs
	public static final RegistryObject<CreativeModeTab> EMBERS_TAB = CREATIVE_TABS.register("main_tab", () -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup." + Embers.MODID))
			.icon(() -> new ItemStack(EMBER_CRYSTAL.get()))
			.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
			.displayItems((params, output) -> {
				for (RegistryObject<Item> item : ITEMS.getEntries()) {
					if (item == MITHRIL_BLOCK_ITEM)
						continue;
					output.accept(item.get());
					if (item == COPPER_CELL_ITEM)
						output.accept(CopperCellBlockItem.getCharged());
					if (item.get() instanceof EmberStorageItem)
						output.accept(EmberStorageItem.withFill(item.get(), ((EmberStorageItem) item.get()).getCapacity()));
				}
			})
			.build());

	//entities
	public static final RegistryObject<EntityType<EmberPacketEntity>> EMBER_PACKET = registerEntity("ember_packet", EntityType.Builder.<EmberPacketEntity>of(EmberPacketEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune().clientTrackingRange(3).updateInterval(1));
	public static final RegistryObject<EntityType<EmberProjectileEntity>> EMBER_PROJECTILE = registerEntity("ember_projectile", EntityType.Builder.<EmberProjectileEntity>of(EmberProjectileEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune().clientTrackingRange(3).updateInterval(1));
	public static final RegistryObject<EntityType<GlimmerProjectileEntity>> GLIMMER_PROJECTILE = registerEntity("glimmer_projectile", EntityType.Builder.<GlimmerProjectileEntity>of(GlimmerProjectileEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune().clientTrackingRange(3).updateInterval(1));
	public static final RegistryObject<EntityType<AncientGolemEntity>> ANCIENT_GOLEM = registerEntity("ancient_golem", EntityType.Builder.<AncientGolemEntity>of(AncientGolemEntity::new, MobCategory.MONSTER).sized(0.6F, 1.8F).fireImmune().clientTrackingRange(8));

	//spawn eggs
	public static final RegistryObject<Item> ANCIENT_GOLEM_SPAWN_EGG = ITEMS.register("ancient_golem_spawn_egg", () -> new ForgeSpawnEggItem(ANCIENT_GOLEM, Misc.intColor(48, 38, 35), Misc.intColor(79, 66, 61), new Item.Properties()));

	//augments
	public static final IAugment CORE_AUGMENT = AugmentUtil.registerAugment(new CoreAugment());
	public static final IAugment TINKER_LENS_AUGMENT = AugmentUtil.registerAugment(new TinkerLensAugment(new ResourceLocation(Embers.MODID, "tinker_lens"), false));
	public static final IAugment SMOKY_LENS_AUGMENT = AugmentUtil.registerAugment(new TinkerLensAugment(new ResourceLocation(Embers.MODID, "smoky_tinker_lens"), true));
	public static final IAugment SUPERHEATER_AUGMENT = AugmentUtil.registerAugment(new SuperheaterAugment(new ResourceLocation(Embers.MODID, "superheater")));
	public static final IAugment CINDER_JET_AUGMENT = AugmentUtil.registerAugment(new CinderJetAugment(new ResourceLocation(Embers.MODID, "cinder_jet")));
	public static final IAugment BLASTING_CORE_AUGMENT = AugmentUtil.registerAugment(new BlastingCoreAugment(new ResourceLocation(Embers.MODID, "blasting_core")));
	public static final IAugment CASTER_ORB_AUGMENT = AugmentUtil.registerAugment(new CasterOrbAugment(new ResourceLocation(Embers.MODID, "caster_orb")));
	public static final IAugment RESONATING_BELL_AUGMENT = AugmentUtil.registerAugment(new ResonatingBellAugment(new ResourceLocation(Embers.MODID, "resonating_bell")));
	public static final IAugment FLAME_BARRIER_AUGMENT = AugmentUtil.registerAugment(new FlameBarrierAugment(new ResourceLocation(Embers.MODID, "flame_barrier")));
	public static final IAugment ELDRITCH_INSIGNIA_AUGMENT = AugmentUtil.registerAugment(new EldritchInsigniaAugment(new ResourceLocation(Embers.MODID, "eldritch_insignia")));
	public static final IAugment INTELLIGENT_APPARATUS_AUGMENT = AugmentUtil.registerAugment(new IntelligentApparatusAugment(new ResourceLocation(Embers.MODID, "intelligent_apparatus")));
	public static final IAugment DIFFRACTION_BARREL_AUGMENT = AugmentUtil.registerAugment(new DiffractionBarrelAugment(new ResourceLocation(Embers.MODID, "diffraction_barrel")));
	public static final IAugment FOCAL_LENS_AUGMENT = AugmentUtil.registerAugment(new FocalLensAugment(new ResourceLocation(Embers.MODID, "focal_lens")));
	public static final IAugment SHIFTING_SCALES_AUGMENT = AugmentUtil.registerAugment(new ShiftingScalesAugment(new ResourceLocation(Embers.MODID, "shifting_scales")));
	public static final IAugment WINDING_GEARS_AUGMENT = AugmentUtil.registerAugment(new WindingGearsAugment(new ResourceLocation(Embers.MODID, "winding_gears")));

	//particle types
	public static final RegistryObject<ParticleType<GlowParticleOptions>> GLOW_PARTICLE = registerParticle("glow", false, GlowParticleOptions.DESERIALIZER, GlowParticleOptions.CODEC);
	public static final RegistryObject<ParticleType<StarParticleOptions>> STAR_PARTICLE = registerParticle("star", false, StarParticleOptions.DESERIALIZER, StarParticleOptions.CODEC);
	public static final RegistryObject<ParticleType<SparkParticleOptions>> SPARK_PARTICLE = registerParticle("spark", false, SparkParticleOptions.DESERIALIZER, SparkParticleOptions.CODEC);
	public static final RegistryObject<ParticleType<SmokeParticleOptions>> SMOKE_PARTICLE = registerParticle("smoke", false, SmokeParticleOptions.DESERIALIZER, SmokeParticleOptions.CODEC);
	public static final RegistryObject<ParticleType<VaporParticleOptions>> VAPOR_PARTICLE = registerParticle("vapor", false, VaporParticleOptions.DESERIALIZER, VaporParticleOptions.CODEC);
	public static final RegistryObject<ParticleType<AlchemyCircleParticleOptions>> ALCHEMY_CIRCLE_PARTICLE = registerParticle("alchemy_circle", false, AlchemyCircleParticleOptions.DESERIALIZER, AlchemyCircleParticleOptions.CODEC);
	public static final RegistryObject<ParticleType<TyrfingParticleOptions>> TYRFING_PARTICLE = registerParticle("tyrfing", false, TyrfingParticleOptions.DESERIALIZER, TyrfingParticleOptions.CODEC);
	public static final RegistryObject<ParticleType<XRayGlowParticleOptions>> XRAY_GLOW_PARTICLE = registerParticle("xray_glow", false, XRayGlowParticleOptions.DESERIALIZER, XRayGlowParticleOptions.CODEC);

	//recipe types
	public static final RegistryObject<RecipeType<IBoringRecipe>> BORING = registerRecipeType("boring");
	public static final RegistryObject<RecipeType<IBoringRecipe>> EXCAVATION = registerRecipeType("excavation");
	public static final RegistryObject<RecipeType<IEmberActivationRecipe>> EMBER_ACTIVATION = registerRecipeType("ember_activation");
	public static final RegistryObject<RecipeType<IMeltingRecipe>> MELTING = registerRecipeType("melting");
	public static final RegistryObject<RecipeType<IStampingRecipe>> STAMPING = registerRecipeType("stamping");
	public static final RegistryObject<RecipeType<IMixingRecipe>> MIXING = registerRecipeType("mixing");
	public static final RegistryObject<RecipeType<IMetalCoefficientRecipe>> METAL_COEFFICIENT = registerRecipeType("metal_coefficient");
	public static final RegistryObject<RecipeType<IAlchemyRecipe>> ALCHEMY = registerRecipeType("alchemy");
	public static final RegistryObject<RecipeType<IBoilingRecipe>> BOILING = registerRecipeType("boiling");
	public static final RegistryObject<RecipeType<IGaseousFuelRecipe>> GASEOUS_FUEL = registerRecipeType("gaseous_fuel");
	public static final RegistryObject<RecipeType<ICatalysisCombustionRecipe>> CATALYSIS_COMBUSTION = registerRecipeType("catalysis_combustion");
	public static final RegistryObject<RecipeType<IDawnstoneAnvilRecipe>> DAWNSTONE_ANVIL_RECIPE = registerRecipeType("dawnstone_anvil");

	//recipe serializers
	public static final RegistryObject<RecipeSerializer<BoringRecipe>> BORING_SERIALIZER = RECIPE_SERIALIZERS.register("boring", () -> BoringRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<ExcavationRecipe>> EXCAVATION_SERIALIZER = RECIPE_SERIALIZERS.register("excavation", () -> ExcavationRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<EmberActivationRecipe>> EMBER_ACTIVATION_SERIALIZER = RECIPE_SERIALIZERS.register("ember_activation", () -> EmberActivationRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<MeltingRecipe>> MELTING_SERIALIZER = RECIPE_SERIALIZERS.register("melting", () -> MeltingRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<StampingRecipe>> STAMPING_SERIALIZER = RECIPE_SERIALIZERS.register("stamping", () -> StampingRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<MixingRecipe>> MIXING_SERIALIZER = RECIPE_SERIALIZERS.register("mixing", () -> MixingRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<MetalCoefficientRecipe>> METAL_COEFFICIENT_SERIALIZER = RECIPE_SERIALIZERS.register("metal_coefficient", () -> MetalCoefficientRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<AlchemyRecipe>> ALCHEMY_SERIALIZER = RECIPE_SERIALIZERS.register("alchemy", () -> AlchemyRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<BoilingRecipe>> BOILING_SERIALIZER = RECIPE_SERIALIZERS.register("boiling", () -> BoilingRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<GaseousFuelRecipe>> GASEOUS_FUEL_SERIALIZER = RECIPE_SERIALIZERS.register("gaseous_fuel", () -> GaseousFuelRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<CatalysisCombustionRecipe>> CATALYSIS_COMBUSTION_SERIALIZER = RECIPE_SERIALIZERS.register("catalysis_combustion", () -> CatalysisCombustionRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<GemSocketRecipe>> GEM_SOCKET_SERIALIZER = RECIPE_SERIALIZERS.register("gem_socket", () -> GemSocketRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<GemUnsocketRecipe>> GEM_UNSOCKET_SERIALIZER = RECIPE_SERIALIZERS.register("gem_unsocket", () -> GemUnsocketRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<AnvilRepairRecipe>> TOOL_REPAIR = RECIPE_SERIALIZERS.register("tool_repair", () -> AnvilRepairRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<AnvilRepairMateriaRecipe>> MATERIA_REPAIR = RECIPE_SERIALIZERS.register("tool_materia_repair", () -> AnvilRepairMateriaRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<AnvilBreakdownRecipe>> TOOL_BREAKDOWN = RECIPE_SERIALIZERS.register("tool_breakdown", () -> AnvilBreakdownRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<AnvilAugmentRecipe>> TOOL_AUGMENT = RECIPE_SERIALIZERS.register("tool_augment", () -> AnvilAugmentRecipe.SERIALIZER);
	public static final RegistryObject<RecipeSerializer<AnvilAugmentRemoveRecipe>> TOOL_AUGMENT_REMOVE = RECIPE_SERIALIZERS.register("tool_augment_remove", () -> AnvilAugmentRemoveRecipe.SERIALIZER);

	//loot modifiers
	public static final RegistryObject<Codec<GrandhammerLootModifier>> GRANDHAMMER_MODIFIER = LOOT_MODIFIERS.register("grandhammer", () -> GrandhammerLootModifier.CODEC);
	public static final RegistryObject<Codec<SuperHeaterLootModifier>> SUPERHEATER_MODIFIER = LOOT_MODIFIERS.register("superheater", () -> SuperHeaterLootModifier.CODEC);
	public static final RegistryObject<Codec<AshenAmuletLootModifier>> ASHENAMULET_MODIFIER = LOOT_MODIFIERS.register("ashenamulet", () -> AshenAmuletLootModifier.CODEC);

	//menu types
	public static final RegistryObject<MenuType<SlateMenu>> SLATE_MENU = MENU_TYPES.register("codebreaking_slate", () -> IForgeMenuType.create(SlateMenu::fromBuffer));

	//structure types
	public static final RegistryObject<StructureType<CaveStructure>> CAVE_STRUCTURE = STRUCTURE_TYPES.register("cave_structure", () -> () -> CaveStructure.CODEC);

	//structure processor types
	public static final RegistryObject<StructureProcessorType<CrystalSeedStructureProcessor>> CRYSTAL_SEED_PROCESSOR = STRUCTURE_PROCESSOR_TYPES.register("crystal_seed_processor", () -> () -> CrystalSeedStructureProcessor.CODEC);
	public static final RegistryObject<StructureProcessorType<EntityMobilizerStructureProcessor>> ENTITY_MOBILIZER_PROCESSOR = STRUCTURE_PROCESSOR_TYPES.register("entity_mobilizer", () -> () -> EntityMobilizerStructureProcessor.CODEC);

	public static void init(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			DispenseItemBehavior dispenseBucket = new DefaultDispenseItemBehavior() {
				private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

				@Override
				public ItemStack execute(BlockSource source, ItemStack stack) {
					DispensibleContainerItem container = (DispensibleContainerItem)stack.getItem();
					BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
					Level level = source.getLevel();
					if (container.emptyContents(null, level, blockpos, null)) {
						container.checkExtraContent(null, level, stack, blockpos);
						return new ItemStack(Items.BUCKET);
					} else {
						return this.defaultDispenseItemBehavior.dispense(source, stack);
					}
				}
			};
			for (FluidStuff fluid : fluidList) {
				DispenserBlock.registerBehavior(fluid.FLUID_BUCKET.get(), dispenseBucket);
			}

			EmbersAPI.registerEmberResonance(Ingredient.of(DAWNSTONE_TOOLS.SWORD.get(), DAWNSTONE_TOOLS.SHOVEL.get(), DAWNSTONE_TOOLS.PICKAXE.get(), DAWNSTONE_TOOLS.AXE.get(), DAWNSTONE_TOOLS.HOE.get()), 2.0);
			EmbersAPI.registerEmberResonance(Ingredient.of(CLOCKWORK_PICKAXE.get(), CLOCKWORK_AXE.get(), GRANDHAMMER.get(), BLAZING_RAY.get(), CINDER_STAFF.get()), 2.0);
			EmbersAPI.registerEmberResonance(Ingredient.of(ASHEN_GOGGLES.get(), ASHEN_CLOAK.get(), ASHEN_LEGGINGS.get(), ASHEN_BOOTS.get()), 2.0);

			moltenMetalFluidInteractions(MOLTEN_IRON.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_GOLD.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_COPPER.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_LEAD.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_SILVER.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_DAWNSTONE.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_TIN.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_ALUMINUM.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_ZINC.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_PLATINUM.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_URANIUM.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_BRONZE.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_ELECTRUM.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_BRASS.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_CONSTANTAN.TYPE.get());
			moltenMetalFluidInteractions(MOLTEN_INVAR.TYPE.get());

			EmbersAPI.registerLens(Ingredient.of(TINKER_LENS.get()));
			EmbersAPI.registerWearableLens(Ingredient.of(ASHEN_GOGGLES.get()));
		});
	}

	public static void moltenMetalFluidInteractions(FluidType source) {
		FluidInteractionRegistry.addInteraction(source, new InteractionInformation(
				(level, currentPos, relativePos, currentState) -> level.getFluidState(relativePos).is(EmbersFluidTags.WATERY),
				SOLIDIFIED_METAL.get().defaultBlockState()));
	}

	public static class FluidStuff {

		public final ForgeFlowingFluid.Properties PROPERTIES;

		public final RegistryObject<ForgeFlowingFluid.Source> FLUID;
		public final RegistryObject<ForgeFlowingFluid.Flowing> FLUID_FLOW;
		public final RegistryObject<FluidType> TYPE;

		public final RegistryObject<LiquidBlock> FLUID_BLOCK;

		public final RegistryObject<BucketItem> FLUID_BUCKET;

		public final String name;
		public final String localizedName;
		public final int color;

		public FluidStuff(String name, String localizedName, int color, FluidType type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Source> source, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Flowing> flowing) {
			this.name = name;
			this.localizedName = localizedName;
			this.color = color;

			FLUID = FLUIDS.register(name, () -> source.apply(getFluidProperties()));
			FLUID_FLOW = FLUIDS.register("flowing_" + name, () -> flowing.apply(getFluidProperties()));
			TYPE = FLUIDTYPES.register(name, () -> type);

			PROPERTIES = new ForgeFlowingFluid.Properties(TYPE, FLUID, FLUID_FLOW);
			if (fluidProperties != null)
				fluidProperties.accept(PROPERTIES);

			FLUID_BLOCK = BLOCKS.register(name + "_block", () -> block.apply(FLUID, Block.Properties.of().liquid().pushReaction(PushReaction.DESTROY).lightLevel((state) -> { return type.getLightLevel(); }).randomTicks().replaceable().strength(100.0F).noLootTable()));
			FLUID_BUCKET = ITEMS.register(name + "_bucket", () -> new BucketItem(FLUID, new BucketItem.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

			PROPERTIES.bucket(FLUID_BUCKET).block(FLUID_BLOCK);
		}

		public ForgeFlowingFluid.Properties getFluidProperties() {
			return PROPERTIES;
		}
	}

	public static class StoneDecoBlocks {

		public String name;

		public RegistryObject<Block> block;
		public RegistryObject<StairBlock> stairs = null;
		public RegistryObject<Item> stairsItem = null;
		public RegistryObject<SlabBlock> slab = null;
		public RegistryObject<Item> slabItem = null;
		public RegistryObject<WallBlock> wall = null;
		public RegistryObject<Item> wallItem = null;

		public StoneDecoBlocks(String name, RegistryObject<Block> block, Properties properties, boolean stairs, boolean slab, boolean wall) {
			this.name = name;
			this.block = block;
			if (stairs) {
				this.stairs = BLOCKS.register(name + "_stairs", () -> new StairBlock(() -> block.get().defaultBlockState(), properties));
			}
			if (slab) {
				this.slab = BLOCKS.register(name + "_slab", () -> new SlabBlock(properties));
			}
			if (wall) {
				this.wall = BLOCKS.register(name + "_wall", () -> new WallBlock(properties));
			}
		}

		public void makeItems() {
			if (stairs != null) {
				this.stairsItem = ITEMS.register(name + "_stairs", () -> new BlockItem(this.stairs.get(), new Item.Properties()));
			}
			if (slab != null) {
				this.slabItem = ITEMS.register(name + "_slab", () -> new BlockItem(this.slab.get(), new Item.Properties()));
			}
			if (wall != null) {
				this.wallItem = ITEMS.register(name + "_wall", () -> new BlockItem(this.wall.get(), new Item.Properties()));
			}
		}

		public StoneDecoBlocks(String name, RegistryObject<Block> block, Properties properties) {
			this(name, block, properties, true, true, true);
		}
	}

	public static class ToolSet {

		public String name;

		public final RegistryObject<Item> SWORD;
		public final RegistryObject<Item> SHOVEL;
		public final RegistryObject<Item> PICKAXE;
		public final RegistryObject<Item> AXE;
		public final RegistryObject<Item> HOE;

		public ToolSet(String name, Tier tier) {
			this.name = name;

			SWORD = ITEMS.register(name + "_sword", () -> new SwordItem(tier, 3, -2.4F, new Item.Properties()));
			SHOVEL = ITEMS.register(name + "_shovel", () -> new ShovelItem(tier, 1.5F, -3.0F, new Item.Properties()));
			PICKAXE = ITEMS.register(name + "_pickaxe", () -> new PickaxeItem(tier, 1, -2.8F, new Item.Properties()));
			AXE = ITEMS.register(name + "_axe", () -> new AxeItem(tier, 6.0F, -3.0F, new Item.Properties()));
			HOE = ITEMS.register(name + "_hoe", () -> new HoeItem(tier, (int) -tier.getAttackDamageBonus(), Math.min(0.0F, tier.getAttackDamageBonus() - 3.0F), new Item.Properties()));
		}
	}

	public static class MetalCrystalSeed {

		public static HashMap<String, MetalCrystalSeed> seeds = new HashMap<String, MetalCrystalSeed>();

		public String name;

		public final RegistryObject<Block> BLOCK;
		public RegistryObject<Item> ITEM;
		public final RegistryObject<BlockEntityType<CrystalSeedBlockEntity>> BLOCKENTITY;

		public MetalCrystalSeed(String type) {
			this.name = type;

			BLOCK = BLOCKS.register(type + "_crystal_seed", () -> new CrystalSeedBlock(Properties.of().mapColor(MapColor.NONE).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().strength(1.6f).noOcclusion(), type));
			BLOCKENTITY = BLOCK_ENTITY_TYPES.register(type + "_crystal_seed", () -> BlockEntityType.Builder.of((pos, state) -> new CrystalSeedBlockEntity(pos, state, type), BLOCK.get()).build(null));
			seeds.put(type, this);
		}

		public void makeItem() {
			ITEM = ITEMS.register(name + "_crystal_seed", () -> new BlockItem(BLOCK.get(), new Item.Properties()));
		}
	}
}
