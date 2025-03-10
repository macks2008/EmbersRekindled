package com.rekindled.embers.research;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rekindled.embers.Embers;
import com.rekindled.embers.RegistryManager;
import com.rekindled.embers.compat.curios.CuriosCompat;
import com.rekindled.embers.item.EmberStorageItem;
import com.rekindled.embers.network.PacketHandler;
import com.rekindled.embers.network.message.MessageResearchData;
import com.rekindled.embers.network.message.MessageResearchTick;
import com.rekindled.embers.research.capability.DefaultResearchCapability;
import com.rekindled.embers.research.capability.IResearchCapability;
import com.rekindled.embers.research.capability.ResearchCapabilityProvider;
import com.rekindled.embers.research.subtypes.ResearchFakePage;
import com.rekindled.embers.research.subtypes.ResearchShowItem;
import com.rekindled.embers.research.subtypes.ResearchShowItem.DisplayItem;
import com.rekindled.embers.research.subtypes.ResearchSwitchCategory;
import com.rekindled.embers.util.Vec2i;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.PacketDistributor;

public class ResearchManager {
	public static final ResourceLocation PLAYER_RESEARCH = new ResourceLocation(Embers.MODID, "research");
	public static final ResourceLocation PAGE_ICONS = new ResourceLocation(Embers.MODID, "textures/gui/codex_pageicons.png");
	public static final double PAGE_ICON_SIZE = 48;
	public static List<ResearchCategory> researches = new ArrayList<ResearchCategory>();

	public static ResearchBase dials, ores, hammer, ancient_golem, gauge, caminite, access, bore, excavation_buckets, crystals, activator, tinker_lens, reaction_chamber, heat_exchanger, //WORLD
	copper_cell, emitters, relays, dawnstone, melter, stamper, mixer, breaker, hearth_coil, char_instiller, atmospheric_bellows, heat_insulation, pressureRefinery, mini_boiler, pump, clockwork_attenuator, geo_separator, //MECHANISMS
	beam_cannon, pulser, splitter, crystal_cell, cinder_staff, clockwork_tools, blazing_ray, charger, jars, alchemy, cinder_plinth, aspecti, ember_siphon, //METALLURGY
	tyrfing, waste, slate, mnemonic_inscriber, entropic_enumerator, catalytic_plug, cluster, ashen_cloak, inflictor, materia, field_chart, glimmer, metallurgic_dust, //ALCHEMY
	augments, inferno_forge, heat, dawnstone_anvil, autohammer, dismantling //SMITHING
	;
	public static ResearchBase pipes, tank, bin, dropper, reservoir, vacuum, transfer, golem_eye, requisition; //PIPES
	public static ResearchBase adhesive, hellish_synthesis, archaic_brick, motive_core, dwarven_oil; //SIMPLE ALCHEMY
	public static ResearchBase wildfire, combustor, catalyzer, reactor, injector, stirling, ember_pipe; //WILDFIRE
	public static ResearchBase superheater, caster_orb, resonating_bell, blasting_core, /*core_stone,*/ winding_gears; //WEAPON AUGMENTS
	public static ResearchBase cinder_jet, eldritch_insignia, intelligent_apparatus, flame_barrier, tinker_lens_augment, anti_tinker_lens, shifting_scales; //ARMOR_AUGMENTS
	public static ResearchBase diffraction_barrel, focal_lens; //PROJECTILE_AUGMENTS
	public static ResearchBase cost_reduction, mantle_bulb, explosion_charm, nonbeliever_amulet, ashen_amulet, dawnstone_mail, explosion_pedestal; //BAUBLE
	public static ResearchBase gearbox, mergebox, axle_iron, gear_iron, actuator, steam_engine; //MECHANICAL POWER

	public static ResearchCategory categoryWorld;
	public static ResearchCategory categoryMechanisms;
	public static ResearchCategory categoryMetallurgy;
	public static ResearchCategory categoryAlchemy;
	public static ResearchCategory categorySmithing;
	public static ResearchCategory categoryMateria;
	public static ResearchCategory categoryCore;

	public static ResearchCategory subCategoryPipes;
	public static ResearchCategory subCategoryWeaponAugments;
	public static ResearchCategory subCategoryArmorAugments;
	public static ResearchCategory subCategoryProjectileAugments;
	public static ResearchCategory subCategoryMiscAugments;
	public static ResearchCategory subCategoryMechanicalPower;
	public static ResearchCategory subCategoryBaubles;
	public static ResearchCategory subCategorySimpleAlchemy;
	public static ResearchCategory subCategoryWildfire;

	public static boolean isPathToLock(ResearchBase entry) {
		for (ResearchCategory category : researches) {
			for (ResearchBase target : category.prerequisites) {
				if (isPathTowards(entry, target))
					return true;
			}
		}
		return false;
	}

	public static boolean isPathTowards(ResearchBase entry, ResearchBase target) {
		if (entry.isPathTowards(target))
			return true;
		for (ResearchBase ancestor : target.getAllRequirements()) {
			if (isPathTowards(entry, ancestor))
				return true;
		}
		return false;
	}

	public static void sendResearchData(ServerPlayer player) {
		IResearchCapability research = getPlayerResearch(player);
		if (research != null) {
			PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new MessageResearchData(research.getCheckmarks()));
		}
	}

	public static void receiveResearchData(Map<String, Boolean> checkmarks) {
		for (ResearchBase research : getAllResearch()) {
			Boolean checked = checkmarks.get(research.name);
			if (checked != null)
				research.check(checked);
		}
	}

	public static void sendCheckmark(ResearchBase research, boolean checked) {
		PacketHandler.INSTANCE.sendToServer(new MessageResearchTick(research.name, checked));
	}

	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof Player && !event.getCapabilities().containsKey(PLAYER_RESEARCH)) {
			event.addCapability(PLAYER_RESEARCH, new ResearchCapabilityProvider(new DefaultResearchCapability()));
		}
	}

	public static void onClone(PlayerEvent.Clone event) {
		IResearchCapability oldCap = getPlayerResearch(event.getOriginal());
		IResearchCapability newCap = getPlayerResearch(event.getEntity());
		if (oldCap != null && newCap != null) {
			CompoundTag compound = new CompoundTag();
			oldCap.writeToNBT(compound);
			newCap.readFromNBT(compound);
		}
	}

	public static IResearchCapability getPlayerResearch(Player player) {
		return player.getCapability(ResearchCapabilityProvider.researchCapability).orElse(null);
	}

	public static List<ResearchBase> getAllResearch() {
		Set<ResearchBase> result = new HashSet<>();
		for (ResearchCategory category : researches) {
			category.getAllResearch(result);
		}
		return new ArrayList<>(result);
	}

	public static Map<ResearchBase,Integer> findByTag(String match) {
		HashMap<ResearchBase,Integer> result = new HashMap<>();
		HashSet<ResearchCategory> categories = new HashSet<>();
		if (!match.isEmpty())
			for (ResearchCategory category : researches) {
				category.findByTag(match,result,categories);
			}
		return result;
	}

	public static void initResearches() {
		categoryWorld = new ResearchCategory("world", 16);
		categoryMechanisms = new ResearchCategory("mechanisms", 32);
		categoryMetallurgy = new ResearchCategory("metallurgy", 48);
		categoryAlchemy = new ResearchCategory("alchemy", 64);
		categorySmithing = new ResearchCategory("smithing", 80);
		categoryMateria = new ResearchCategoryComingSoon("materia", 224, 0);
		categoryCore = new ResearchCategoryComingSoon("core", 224, 16);
		Vec2i[] ringPositions = {new Vec2i(1, 1), new Vec2i(0, 3), new Vec2i(0, 5), new Vec2i(1, 7), new Vec2i(11, 7), new Vec2i(12, 5), new Vec2i(12, 3), new Vec2i(11, 1), new Vec2i(4, 1), new Vec2i(2, 4), new Vec2i(4, 7), new Vec2i(8, 7), new Vec2i(10, 4),new Vec2i(8, 1)};
		subCategoryWeaponAugments = new ResearchCategory("weapon_augments", 0).pushGoodLocations(ringPositions);
		subCategoryArmorAugments = new ResearchCategory("armor_augments", 0).pushGoodLocations(ringPositions);
		subCategoryProjectileAugments = new ResearchCategory("projectile_augments", 0).pushGoodLocations(ringPositions);
		subCategoryMiscAugments = new ResearchCategory("misc_augments", 0).pushGoodLocations(ringPositions);
		subCategoryPipes = new ResearchCategory("pipes", 0);
		subCategoryMechanicalPower = new ResearchCategory("mystical_mechanics", 0);
		subCategoryBaubles = new ResearchCategory("baubles", 0);
		subCategorySimpleAlchemy = new ResearchCategory("simple_alchemy", 0);
		subCategoryWildfire = new ResearchCategory("wildfire", 0);

		//WORLD
		ores = new ResearchBase("ores", new ItemStack(RegistryManager.RAW_LEAD.get()), 0, 7);
		hammer = new ResearchBase("hammer", new ItemStack(RegistryManager.TINKER_HAMMER.get()), 0, 3).addAncestor(ores);
		ancient_golem = new ResearchBase("ancient_golem", ItemStack.EMPTY, 0, 0).setIconBackground(PAGE_ICONS, PAGE_ICON_SIZE *1, PAGE_ICON_SIZE *0);
		gauge = new ResearchBase("gauge", new ItemStack(RegistryManager.ATMOSPHERIC_GAUGE_ITEM.get()), 4, 3).addAncestor(ores);
		caminite = new ResearchBase("caminite", new ItemStack(RegistryManager.CAMINITE_BRICK.get()), 6, 7);
		access = new ResearchBase("access", new ItemStack(RegistryManager.MECHANICAL_CORE_ITEM.get()), 7, 2).addAncestor(caminite);
		bore = new ResearchBase("bore", new ItemStack(RegistryManager.EMBER_BORE_ITEM.get()), 9, 0).addAncestor(hammer).addAncestor(access);
		excavation_buckets = new ResearchBase("excavation_buckets", new ItemStack(RegistryManager.EXCAVATION_BUCKETS_ITEM.get()), 6, 0).addAncestor(bore);
		crystals = new ResearchBase("crystals", new ItemStack(RegistryManager.EMBER_CRYSTAL.get()), 12, 3).addAncestor(bore);
		activator = new ResearchBase("activator", new ItemStack(RegistryManager.EMBER_ACTIVATOR_ITEM.get()), 10, 6).addAncestor(crystals);
		dials = new ResearchBase("dials", new ItemStack(RegistryManager.EMBER_DIAL_ITEM.get()), 5, 5).addAncestor(hammer);
		tinker_lens = new ResearchBase("tinker_lens", new ItemStack(RegistryManager.TINKER_LENS.get()),4,7).addAncestor(hammer);
		//reaction_chamber = new ResearchBase("reaction_chamber", new ItemStack(RegistryManager.reaction_chamber), 12, 5).addAncestor(mini_boiler);
		heat_exchanger = new ResearchBase("heat_exchanger", new ItemStack(RegistryManager.HEAT_EXCHANGER_ITEM.get()), 12, 7).addAncestor(activator);

		pipes = new ResearchBase("pipes", new ItemStack(RegistryManager.FLUID_EXTRACTOR_ITEM.get()), 2, 4);
		pipes.addPage(new ResearchShowItem("routing",ItemStack.EMPTY,0,0).addItem(new DisplayItem(new ItemStack(RegistryManager.ITEM_PIPE_ITEM.get()),new ItemStack(RegistryManager.FLUID_PIPE_ITEM.get()))));
		pipes.addPage(new ResearchShowItem("valves",ItemStack.EMPTY,0,0).addItem(new DisplayItem(new ItemStack(RegistryManager.ITEM_EXTRACTOR_ITEM.get()),new ItemStack(RegistryManager.FLUID_EXTRACTOR_ITEM.get()))));
		pipes.addPage(new ResearchShowItem("pipe_tools",ItemStack.EMPTY,0,0).addItem(new DisplayItem(new ItemStack(RegistryManager.TINKER_HAMMER.get()),new ItemStack(Items.STICK))));
		//golem_eye = new ResearchBase("golem_eye", new ItemStack(RegistryManager.golems_eye), 5, 7)
		//		.addPage(new ResearchShowItem("filter_existing", new ItemStack(RegistryManager.item_request), 0, 0).addItem(new DisplayItem(new ItemStack(RegistryManager.item_request))))
		//		.addPage(new ResearchShowItem("filter_not_existing", new ItemStack(RegistryManager.dawnstone_anvil), 0, 0).addItem(new DisplayItem(new ItemStack(RegistryManager.dawnstone_anvil))));
		transfer = new ResearchBase("transfer", new ItemStack(RegistryManager.ITEM_TRANSFER_ITEM.get()), 5, 5).addAncestor(pipes);//.addAncestor(golem_eye);
		transfer.addPage(new ResearchShowItem("fluid_transfer",ItemStack.EMPTY,0,0).addItem(new DisplayItem(new ItemStack(RegistryManager.FLUID_TRANSFER_ITEM.get()))));
		vacuum = new ResearchBase("vacuum", new ItemStack(RegistryManager.ITEM_VACUUM_ITEM.get()), 8, 4).addPage(new ResearchBase("vacuum_transfer",ItemStack.EMPTY,0,0)).addAncestor(pipes);
		dropper = new ResearchBase("dropper", new ItemStack(RegistryManager.ITEM_DROPPER_ITEM.get()), 8, 6).addAncestor(pipes);
		bin = new ResearchBase("bin", new ItemStack(RegistryManager.BIN_ITEM.get()), 4, 3).addAncestor(pipes);
		tank = new ResearchBase("tank", new ItemStack(RegistryManager.FLUID_VESSEL_ITEM.get()), 3, 1).addAncestor(pipes);
		reservoir = new ResearchBase("reservoir", new ItemStack(RegistryManager.RESERVOIR_ITEM.get()), 6, 0).addAncestor(tank)
				.addPage(new ResearchShowItem("reservoir_valve", new ItemStack(RegistryManager.CAMINITE_VALVE_ITEM.get()), 0, 0).addItem(new DisplayItem(new ItemStack(RegistryManager.CAMINITE_VALVE_ITEM.get()))));
		//requisition = new ResearchBase("requisition", new ItemStack(RegistryManager.item_request), 3, 6).addAncestor(pipes).addAncestor(golem_eye);

		//MECHANISMS
		emitters = new ResearchShowItem("emitters", new ItemStack(RegistryManager.EMBER_EMITTER_ITEM.get()), 0, 2).addItem(new DisplayItem(new ItemStack(RegistryManager.EMBER_EMITTER_ITEM.get())))
				.addPage(new ResearchShowItem("receivers", new ItemStack(RegistryManager.EMBER_RECEIVER_ITEM.get()), 0, 0).addItem(new DisplayItem(new ItemStack(RegistryManager.EMBER_RECEIVER_ITEM.get()))))
				.addPage(new ResearchShowItem("linking", ItemStack.EMPTY, 0, 0).addItem(new DisplayItem(new ItemStack(RegistryManager.EMBER_EMITTER_ITEM.get()),new ItemStack(RegistryManager.TINKER_HAMMER.get()),new ItemStack(RegistryManager.EMBER_RECEIVER_ITEM.get()))));
		relays = new ResearchShowItem("relays", new ItemStack(RegistryManager.EMBER_RELAY_ITEM.get()), 2, 7).addItem(new DisplayItem(new ItemStack(RegistryManager.EMBER_RELAY_ITEM.get()))).addAncestor(emitters)
				.addPage(new ResearchShowItem("mirror_relay", new ItemStack(RegistryManager.MIRROR_RELAY_ITEM.get()), 0, 0).addItem(new DisplayItem(new ItemStack(RegistryManager.MIRROR_RELAY_ITEM.get()))));
		melter = new ResearchBase("melter", new ItemStack(RegistryManager.MELTER_ITEM.get()), 2, 0).addAncestor(emitters);
		geo_separator = new ResearchBase("geo_separator", new ItemStack(RegistryManager.GEOLOGIC_SEPARATOR_ITEM.get()), 0, 0).addAncestor(melter);
		stamper = new ResearchBase("stamper", new ItemStack(RegistryManager.STAMPER_ITEM.get()), 3, 4).addAncestor(melter).addAncestor(emitters);
		mixer = new ResearchBase("mixer", new ItemStack(RegistryManager.MIXER_CENTRIFUGE_ITEM.get()), 5, 2).addAncestor(stamper).addAncestor(melter);
		//breaker = new ResearchBase("breaker", new ItemStack(RegistryManager.breaker), 4, 7).addAncestor(stamper);
		dawnstone = new ResearchBase("dawnstone", new ItemStack(RegistryManager.DAWNSTONE_INGOT.get()), 11, 4).addAncestor(mixer);
		pressureRefinery = new ResearchBase("pressure_refinery", new ItemStack(RegistryManager.PRESSURE_REFINERY_ITEM.get()), 10, 0).addAncestor(dawnstone);
		pump = new ResearchBase("pump", new ItemStack(RegistryManager.MECHANICAL_PUMP_ITEM.get()), 7, 0).addAncestor(pressureRefinery);
		mini_boiler = new ResearchBase("mini_boiler", new ItemStack(RegistryManager.MINI_BOILER_ITEM.get()), 8, 5).addAncestor(pump);
		copper_cell = new ResearchBase("copper_cell", new ItemStack(RegistryManager.COPPER_CELL_ITEM.get()), 0, 5).addAncestor(emitters);
		hearth_coil = new ResearchBase("hearth_coil", new ItemStack(RegistryManager.HEARTH_COIL_ITEM.get()), 6, 6).addAncestor(copper_cell);
		char_instiller = new ResearchBase("char_instiller", new ItemStack(RegistryManager.CHAR_INSTILLER_ITEM.get()), 8, 7).addAncestor(hearth_coil);
		atmospheric_bellows = new ResearchBase("atmospheric_bellows", new ItemStack(RegistryManager.ATMOSPHERIC_BELLOWS_ITEM.get()), 10, 7).addAncestor(hearth_coil);
		heat_insulation = new ResearchBase("heat_insulation", new ItemStack(RegistryManager.HEAT_INSULATION_ITEM.get()), 4, 7).addAncestor(hearth_coil);
		clockwork_attenuator = new ResearchBase("clockwork_attenuator", new ItemStack(RegistryManager.CLOCKWORK_ATTENUATOR.get()), 12, 7);

		//METALLURGY
		crystal_cell = new ResearchBase("crystal_cell", new ItemStack(RegistryManager.CRYSTAL_CELL_ITEM.get()), 0, 1);
		pulser = new ResearchShowItem("pulser", new ItemStack(RegistryManager.EMBER_EJECTOR_ITEM.get()), 0, 3.5).addItem(new DisplayItem(new ItemStack(RegistryManager.EMBER_EJECTOR_ITEM.get()))).addAncestor(crystal_cell)
				.addPage(new ResearchShowItem("ember_funnel",new ItemStack(RegistryManager.EMBER_FUNNEL_ITEM.get()),0,0).addItem(new DisplayItem(new ItemStack(RegistryManager.EMBER_FUNNEL_ITEM.get()))));
		charger = new ResearchBase("charger", new ItemStack(RegistryManager.COPPER_CHARGER_ITEM.get()), 4, 0);
		ember_siphon = new ResearchBase("ember_siphon", new ItemStack(RegistryManager.EMBER_SIPHON_ITEM.get()), 2, 0).addAncestor(ResearchManager.charger);
		ItemStack fullJar = EmberStorageItem.withFill(RegistryManager.EMBER_JAR.get(), ((EmberStorageItem)RegistryManager.EMBER_JAR.get()).getCapacity());
		jars = new ResearchBase("jars", fullJar, 7, 1).addAncestor(charger);
		clockwork_tools = new ResearchBase("clockwork_tools", new ItemStack(RegistryManager.CLOCKWORK_AXE.get()), 2, 2).addAncestor(jars)
				.addPage(new ResearchShowItem("clockwork_pickaxe",ItemStack.EMPTY,0,0).addItem(new DisplayItem(new ItemStack(RegistryManager.CLOCKWORK_PICKAXE.get()))))
				.addPage(new ResearchShowItem("clockwork_hammer",ItemStack.EMPTY,0,0).addItem(new DisplayItem(new ItemStack(RegistryManager.GRANDHAMMER.get()))))
				.addPage(new ResearchShowItem("clockwork_axe",ItemStack.EMPTY,0,0).addItem(new DisplayItem(new ItemStack(RegistryManager.CLOCKWORK_AXE.get()))));
		splitter = new ResearchBase("splitter", new ItemStack(RegistryManager.BEAM_SPLITTER_ITEM.get()), 0, 6).addAncestor(pulser);
		cinder_staff = new ResearchBase("cinder_staff", new ItemStack(RegistryManager.CINDER_STAFF.get()), 4, 4).addAncestor(jars);
		blazing_ray = new ResearchBase("blazing_ray", new ItemStack(RegistryManager.BLAZING_RAY.get()), 6, 5).addAncestor(jars);
		aspecti = new ResearchBase("aspecti", new ItemStack(RegistryManager.DAWNSTONE_ASPECTUS.get()), 12, 1);
		cinder_plinth = new ResearchBase("cinder_plinth", new ItemStack(RegistryManager.CINDER_PLINTH_ITEM.get()), 9, 0);
		beam_cannon = new ResearchBase("beam_cannon", new ItemStack(RegistryManager.BEAM_CANNON_ITEM.get()), 9, 7);
		alchemy = new ResearchBase("alchemy", new ItemStack(RegistryManager.ALCHEMY_TABLET_ITEM.get()), 9, 4).addAncestor(aspecti).addAncestor(beam_cannon);

		//TRANSMUTATION
		waste = new ResearchBase("waste", new ItemStack(RegistryManager.ALCHEMICAL_WASTE.get()), 6, 0)
				.addPage(new ResearchBase("waste_page_2", new ItemStack(RegistryManager.ALCHEMICAL_WASTE.get()), 0, 0));
		slate = new ResearchBase("slate", new ItemStack(RegistryManager.CODEBREAKING_SLATE.get()), 6, 2).addAncestor(waste)
				.addPage(new ResearchBase("slate_alchemy_recap",ItemStack.EMPTY,0,0));
		mnemonic_inscriber = new ResearchBase("mnemonic_inscriber", new ItemStack(RegistryManager.MNEMONIC_INSCRIBER_ITEM.get()), 4, 1).addAncestor(slate);
		entropic_enumerator = new ResearchBase("entropic_enumerator", new ItemStack(RegistryManager.ENTROPIC_ENUMERATOR_ITEM.get()), 1, 0).addAncestor(slate);
		catalytic_plug = new ResearchBase("catalytic_plug", new ItemStack(RegistryManager.CATALYTIC_PLUG_ITEM.get()), 12, 5).addAncestor(slate);
		materia = new ResearchBase("materia", new ItemStack(RegistryManager.ISOLATED_MATERIA.get()), 6, 5).addAncestor(slate);
		cluster = new ResearchBase("cluster", new ItemStack(RegistryManager.EMBER_CRYSTAL_CLUSTER.get()), 3, 4).addAncestor(slate);
		ashen_cloak = new ResearchShowItem("ashen_cloak", new ItemStack(RegistryManager.ASHEN_CLOAK.get()), 9, 4).addItem(new DisplayItem(new ItemStack(RegistryManager.ASHEN_GOGGLES.get()),new ItemStack(RegistryManager.ASHEN_CLOAK.get()),new ItemStack(RegistryManager.ASHEN_LEGGINGS.get()),new ItemStack(RegistryManager.ASHEN_BOOTS.get()))).addAncestor(slate);
		field_chart = new ResearchBase("field_chart", new ItemStack(RegistryManager.FIELD_CHART_ITEM.get()), 0, 5).addAncestor(cluster);
		inflictor = new ResearchBase("inflictor", new ItemStack(RegistryManager.INFLICTOR_GEM.get()), 11, 7).addAncestor(ashen_cloak);
		tyrfing = new ResearchBase("tyrfing", new ItemStack(RegistryManager.TYRFING.get()), 8, 6).addAncestor(slate);
		glimmer = new ResearchBase("glimmer", new ItemStack(RegistryManager.GLIMMER_CRYSTAL.get()), 9, 0).addAncestor(slate);
		//metallurgic_dust = new ResearchBase("metallurgic_dust", new ItemStack(RegistryManager.dust_metallurgic), 0, 2).addAncestor(slate);

		adhesive = new ResearchBase("adhesive", new ItemStack(RegistryManager.ADHESIVE.get()), 10, 1);
		hellish_synthesis = new ResearchBase("hellish_synthesis", new ItemStack(Items.NETHERRACK), 2, 1);
		archaic_brick = new ResearchBase("archaic_brick", new ItemStack(RegistryManager.ARCHAIC_BRICK.get()), 5, 2).addAncestor(hellish_synthesis);
		motive_core = new ResearchBase("motive_core", new ItemStack(RegistryManager.ANCIENT_MOTIVE_CORE.get()), 4, 4).addAncestor(archaic_brick);
		dwarven_oil = new ResearchBase("dwarven_oil", new ItemStack(RegistryManager.DWARVEN_OIL.FLUID_BUCKET.get()), 1, 4).addAncestor(hellish_synthesis);

		wildfire = new ResearchBase("wildfire", new ItemStack(RegistryManager.WILDFIRE_CORE.get()), 1, 5);
		injector = new ResearchBase("injector", new ItemStack(RegistryManager.EMBER_INJECTOR_ITEM.get()), 0, 7).addAncestor(wildfire)
				.addPage(new ResearchShowItem("crystal_level",ItemStack.EMPTY,0,0)
						.addItem(new DisplayItem(new ItemStack(RegistryManager.IRON_CRYSTAL_SEED.ITEM.get()), new ItemStack(RegistryManager.GOLD_CRYSTAL_SEED.ITEM.get()), new ItemStack(RegistryManager.COPPER_CRYSTAL_SEED.ITEM.get()), new ItemStack(RegistryManager.TIN_CRYSTAL_SEED.ITEM.get())))
						.addItem(new DisplayItem(new ItemStack(RegistryManager.SILVER_CRYSTAL_SEED.ITEM.get()), new ItemStack(RegistryManager.LEAD_CRYSTAL_SEED.ITEM.get()), new ItemStack(RegistryManager.NICKEL_CRYSTAL_SEED.ITEM.get()), new ItemStack(RegistryManager.ALUMINUM_CRYSTAL_SEED.ITEM.get()))));
		combustor = new ResearchBase("combustor", new ItemStack(RegistryManager.COMBUSTION_CHAMBER_ITEM.get()), 6, 5).addAncestor(wildfire);
		combustor.addPage(new ResearchShowItem("empty", ItemStack.EMPTY, 0, 0)
				.addItem(new DisplayItem("combustor_coal",new ItemStack(Items.COAL)))
				.addItem(new DisplayItem("combustor_nether_brick",new ItemStack(Items.NETHER_BRICK)))
				.addItem(new DisplayItem("combustor_blaze_powder",new ItemStack(Items.BLAZE_POWDER)))
				);
		catalyzer = new ResearchBase("catalyzer", new ItemStack(RegistryManager.CATALYSIS_CHAMBER_ITEM.get()), 5, 7).addAncestor(wildfire);
		catalyzer.addPage(new ResearchShowItem("empty", ItemStack.EMPTY, 0, 0)
				.addItem(new DisplayItem("catalyzer_grit",new ItemStack(RegistryManager.EMBER_GRIT.get())))
				.addItem(new DisplayItem("catalyzer_gunpowder",new ItemStack(Items.GUNPOWDER)))
				.addItem(new DisplayItem("catalyzer_glowstone",new ItemStack(Items.GLOWSTONE_DUST)))
				);
		reactor = new ResearchBase("reactor", new ItemStack(RegistryManager.IGNEM_REACTOR_ITEM.get()), 9, 7).addAncestor(combustor).addAncestor(catalyzer);
		stirling = new ResearchBase("stirling", new ItemStack(RegistryManager.WILDFIRE_STIRLING_ITEM.get()), 0, 2).addAncestor(ResearchManager.wildfire);
		//ember_pipe = new ResearchBase("ember_pipe", new ItemStack(RegistryManager.ember_pipe), 12, 6).addAncestor(ResearchManager.reactor);

		//SMITHING
		dawnstone_anvil = new ResearchBase("dawnstone_anvil", new ItemStack(RegistryManager.DAWNSTONE_ANVIL_ITEM.get()), 12, 7);
		autohammer = new ResearchBase("autohammer", new ItemStack(RegistryManager.AUTOMATIC_HAMMER_ITEM.get()), 9, 5).addAncestor(dawnstone_anvil);
		heat = new ResearchBase("heat", new ItemStack(RegistryManager.EMBER_CRYSTAL.get()), 7, 7).addAncestor(dawnstone_anvil);
		augments = new ResearchBase("augments", new ItemStack(RegistryManager.ANCIENT_MOTIVE_CORE.get()), 5, 7).addAncestor(heat);
		dismantling = new ResearchBase("dismantling", ItemStack.EMPTY, 3, 5).setIconBackground(PAGE_ICONS, PAGE_ICON_SIZE * 2, PAGE_ICON_SIZE * 0).addAncestor(augments);
		inferno_forge = new ResearchBase("inferno_forge", new ItemStack(RegistryManager.INFERNO_FORGE_ITEM.get()), 6, 4).addAncestor(heat);

		superheater = new ResearchBase("superheater", new ItemStack(RegistryManager.SUPERHEATER.get()), subCategoryWeaponAugments.popGoodLocation());
		blasting_core = new ResearchBase("blasting_core", new ItemStack(RegistryManager.BLASTING_CORE.get()), subCategoryWeaponAugments.popGoodLocation());
		caster_orb = new ResearchBase("caster_orb", new ItemStack(RegistryManager.CASTER_ORB.get()), subCategoryWeaponAugments.popGoodLocation()).addPage(new ResearchBase("caster_orb_addendum",ItemStack.EMPTY,0,0));
		resonating_bell = new ResearchBase("resonating_bell", new ItemStack(RegistryManager.RESONATING_BELL.get()), subCategoryWeaponAugments.popGoodLocation());
		//core_stone = new ResearchBase("core_stone", new ItemStack(RegistryManager.core_stone), subCategoryWeaponAugments.popGoodLocation());
		winding_gears = new ResearchBase("winding_gears", new ItemStack(RegistryManager.WINDING_GEARS.get()), subCategoryWeaponAugments.popGoodLocation()).addPage(new ResearchShowItem("winding_gears_boots",ItemStack.EMPTY,0,0).addItem(new DisplayItem(new ItemStack(Items.IRON_BOOTS))));

		eldritch_insignia = new ResearchBase("eldritch_insignia", new ItemStack(RegistryManager.ELDRITCH_INSIGNIA.get()), subCategoryArmorAugments.popGoodLocation());
		intelligent_apparatus = new ResearchBase("intelligent_apparatus", new ItemStack(RegistryManager.INTELLIGENT_APPARATUS.get()), subCategoryArmorAugments.popGoodLocation());
		flame_barrier = new ResearchBase("flame_barrier", new ItemStack(RegistryManager.FLAME_BARRIER.get()), subCategoryArmorAugments.popGoodLocation());
		cinder_jet = new ResearchBase("cinder_jet", new ItemStack(RegistryManager.CINDER_JET.get()), subCategoryArmorAugments.popGoodLocation());
		tinker_lens_augment = new ResearchBase("tinker_lens_augment", new ItemStack(RegistryManager.TINKER_LENS.get()), subCategoryArmorAugments.popGoodLocation());
		anti_tinker_lens = new ResearchBase("anti_tinker_lens", new ItemStack(RegistryManager.SMOKY_TINKER_LENS.get()), subCategoryArmorAugments.popGoodLocation()).addAncestor(tinker_lens_augment);
		shifting_scales = new ResearchBase("shifting_scales", new ItemStack(RegistryManager.SHIFTING_SCALES.get()), subCategoryArmorAugments.popGoodLocation());

		diffraction_barrel = new ResearchBase("diffraction_barrel", new ItemStack(RegistryManager.DIFFRACTION_BARREL.get()), subCategoryProjectileAugments.popGoodLocation());
		focal_lens = new ResearchBase("focal_lens", new ItemStack(RegistryManager.FOCAL_LENS.get()), subCategoryProjectileAugments.popGoodLocation());

		//tinker_lens.addPage(tinker_lens_augment);

		ResearchBase infernoForgeWeapon = new ResearchFakePage(inferno_forge, 6, 4);
		subCategoryWeaponAugments.addResearch(infernoForgeWeapon);
		subCategoryWeaponAugments.addResearch(superheater.addAncestor(infernoForgeWeapon));
		subCategoryWeaponAugments.addResearch(blasting_core.addAncestor(infernoForgeWeapon));
		subCategoryWeaponAugments.addResearch(caster_orb.addAncestor(infernoForgeWeapon));
		subCategoryWeaponAugments.addResearch(resonating_bell.addAncestor(infernoForgeWeapon));
		//subCategoryWeaponAugments.addResearch(core_stone.addAncestor(infernoForgeWeapon));
		subCategoryWeaponAugments.addResearch(winding_gears.addAncestor(infernoForgeWeapon));

		ResearchBase infernoForgeArmor = new ResearchFakePage(inferno_forge, 6, 4);
		subCategoryArmorAugments.addResearch(infernoForgeArmor);
		subCategoryArmorAugments.addResearch(cinder_jet.addAncestor(infernoForgeArmor));
		subCategoryArmorAugments.addResearch(eldritch_insignia.addAncestor(infernoForgeArmor));
		subCategoryArmorAugments.addResearch(intelligent_apparatus.addAncestor(infernoForgeArmor));
		subCategoryArmorAugments.addResearch(flame_barrier.addAncestor(infernoForgeArmor));
		subCategoryArmorAugments.addResearch(new ResearchFakePage(blasting_core,subCategoryArmorAugments.popGoodLocation()).addAncestor(infernoForgeArmor));
		subCategoryArmorAugments.addResearch(tinker_lens_augment.addAncestor(infernoForgeArmor));
		subCategoryArmorAugments.addResearch(anti_tinker_lens.addAncestor(infernoForgeArmor));
		subCategoryArmorAugments.addResearch(shifting_scales.addAncestor(infernoForgeArmor));
		subCategoryArmorAugments.addResearch(new ResearchFakePage(winding_gears,subCategoryArmorAugments.popGoodLocation()).addAncestor(infernoForgeArmor));
		//subCategoryArmorAugments.addResearch(new ResearchFakePage(core_stone,subCategoryArmorAugments.popGoodLocation()).addAncestor(infernoForgeArmor));

		ResearchBase infernoForgeProjectile = new ResearchFakePage(inferno_forge, 6, 4);
		subCategoryProjectileAugments.addResearch(infernoForgeProjectile);
		subCategoryProjectileAugments.addResearch(diffraction_barrel.addAncestor(infernoForgeProjectile));
		subCategoryProjectileAugments.addResearch(focal_lens.addAncestor(infernoForgeProjectile));

		ResearchBase infernoForgeMisc = new ResearchFakePage(inferno_forge, 6, 4);
		subCategoryMiscAugments.addResearch(infernoForgeMisc);

		subCategoryPipes.addResearch(pipes);
		subCategoryPipes.addResearch(bin);
		subCategoryPipes.addResearch(tank);
		subCategoryPipes.addResearch(reservoir);
		subCategoryPipes.addResearch(transfer);
		subCategoryPipes.addResearch(vacuum);
		subCategoryPipes.addResearch(dropper);
		//subCategoryPipes.addResearch(requisition);
		//subCategoryPipes.addResearch(golem_eye);

		subCategorySimpleAlchemy.addResearch(hellish_synthesis);
		subCategorySimpleAlchemy.addResearch(archaic_brick);
		subCategorySimpleAlchemy.addResearch(motive_core);
		subCategorySimpleAlchemy.addResearch(adhesive);
		subCategorySimpleAlchemy.addResearch(dwarven_oil);

		subCategoryWildfire.addResearch(wildfire);
		subCategoryWildfire.addResearch(injector);
		subCategoryWildfire.addResearch(combustor);
		subCategoryWildfire.addResearch(catalyzer);
		subCategoryWildfire.addResearch(reactor);
		subCategoryWildfire.addResearch(stirling);
		//subCategoryWildfire.addResearch(ember_pipe);

		/*
		ResearchBase mechanicalPowerSwitch;
		if (ConfigManager.isMysticalMechanicsIntegrationEnabled()) {
			mechanicalPowerSwitch = makeCategorySwitch(subCategoryMechanicalPower, 8, 7, ItemStack.EMPTY, 4, 1);

			MysticalMechanicsIntegration.initMysticalMechanicsCategory();
		}
		else
			mechanicalPowerSwitch = new ResearchBase("mystical_mechanics", ItemStack.EMPTY, 8, 7).setIconBackground(PAGE_ICONS, PAGE_ICON_SIZE * 0, PAGE_ICON_SIZE * 2);
		mechanicalPowerSwitch.addAncestor(access);
		 */
		if (ModList.get().isLoaded("curios")) {
			ResearchBase baublesSwitch = makeCategorySwitch(subCategoryBaubles, 5, 7, ItemStack.EMPTY, 5, 1);

			CuriosCompat.initCuriosCategory();
			baublesSwitch.addAncestor(cluster);
			categoryAlchemy.addResearch(baublesSwitch);
		}

		ResearchBase pipeSwitch = makeCategorySwitch(subCategoryPipes, 3, 0, new ItemStack(RegistryManager.FLUID_PIPE_ITEM.get()), 0, 1).addAncestor(hammer);
		ResearchBase weaponAugmentSwitch = makeCategorySwitch(subCategoryWeaponAugments, 2, 1, ItemStack.EMPTY, 1, 1).setMinEntries(2).addAncestor(inferno_forge);
		ResearchBase armorAugmentSwitch = makeCategorySwitch(subCategoryArmorAugments, 1, 3, ItemStack.EMPTY, 2, 1).setMinEntries(2).addAncestor(inferno_forge);
		ResearchBase projectileAugmentSwitch = makeCategorySwitch(subCategoryProjectileAugments, 11, 3, ItemStack.EMPTY, 3, 1).setMinEntries(2).addAncestor(inferno_forge);
		ResearchBase miscAugmentSwitch = makeCategorySwitch(subCategoryMiscAugments, 10, 1, ItemStack.EMPTY, 0, 1).setMinEntries(2).addAncestor(inferno_forge);
		ResearchBase wildfireSwitch = makeCategorySwitch(subCategoryWildfire, 1, 7, new ItemStack(RegistryManager.WILDFIRE_CORE.get()), 0, 1).addAncestor(cluster);
		ResearchBase simpleAlchemySwitch = makeCategorySwitch(subCategorySimpleAlchemy, 12, 1, new ItemStack(Items.SOUL_SAND), 0, 1).addAncestor(slate);

		pipes.subCategory = pipeSwitch;
		infernoForgeWeapon.subCategory = weaponAugmentSwitch;
		infernoForgeArmor.subCategory = armorAugmentSwitch;
		infernoForgeProjectile.subCategory = projectileAugmentSwitch;
		infernoForgeMisc.subCategory = miscAugmentSwitch;
		//miscAugmentSwitch;
		wildfire.subCategory = wildfireSwitch;
		adhesive.subCategory = simpleAlchemySwitch;
		hellish_synthesis.subCategory = simpleAlchemySwitch;

		categoryWorld
		.addResearch(ores)
		.addResearch(hammer)
		.addResearch(ancient_golem)
		.addResearch(gauge)
		.addResearch(tinker_lens)
		.addResearch(caminite)
		.addResearch(access)
		.addResearch(bore)
		.addResearch(excavation_buckets)
		.addResearch(pipeSwitch)
		.addResearch(crystals)
		.addResearch(activator)
		//.addResearch(reaction_chamber)
		.addResearch(dials)
		.addResearch(heat_exchanger);
		categoryMechanisms
		.addResearch(melter)
		.addResearch(stamper)
		.addResearch(hearth_coil)
		.addResearch(char_instiller)
		.addResearch(atmospheric_bellows)
		.addResearch(heat_insulation)
		.addResearch(mixer)
		.addResearch(pump)
		.addResearch(pressureRefinery)
		.addResearch(mini_boiler)
		//.addResearch(mechanicalPowerSwitch)
		//.addResearch(breaker)
		.addResearch(dawnstone)
		.addResearch(emitters)
		.addResearch(relays)
		.addResearch(copper_cell)
		.addResearch(clockwork_attenuator)
		.addResearch(geo_separator);
		categoryMetallurgy
		.addResearch(splitter)
		.addResearch(pulser)
		.addResearch(crystal_cell)
		.addResearch(charger)
		.addResearch(ember_siphon)
		.addResearch(jars)
		.addResearch(clockwork_tools)
		.addResearch(cinder_staff)
		.addResearch(blazing_ray)
		.addResearch(cinder_plinth)
		.addResearch(aspecti)
		.addResearch(alchemy)
		.addResearch(beam_cannon);
		categoryAlchemy
		.addResearch(waste)
		.addResearch(slate)
		.addResearch(mnemonic_inscriber)
		.addResearch(entropic_enumerator)
		.addResearch(simpleAlchemySwitch)
		.addResearch(catalytic_plug)
		.addResearch(cluster)
		.addResearch(ashen_cloak)
		.addResearch(inflictor)
		.addResearch(field_chart)
		.addResearch(materia)
		.addResearch(tyrfing)
		.addResearch(glimmer)
		//.addResearch(metallurgic_dust)
		.addResearch(wildfireSwitch);
		categorySmithing
		.addResearch(dawnstone_anvil)
		.addResearch(autohammer)
		.addResearch(heat)
		.addResearch(augments)
		.addResearch(dismantling)
		.addResearch(inferno_forge)
		.addResearch(weaponAugmentSwitch)
		.addResearch(armorAugmentSwitch)
		.addResearch(projectileAugmentSwitch)
		.addResearch(miscAugmentSwitch);

		categoryMechanisms.addPrerequisite(activator);
		categoryMetallurgy.addPrerequisite(dawnstone);
		categoryAlchemy.addPrerequisite(alchemy);
		categorySmithing.addPrerequisite(wildfire);

		researches.add(categoryWorld);
		researches.add(categoryMechanisms);
		researches.add(categoryMetallurgy);
		researches.add(categoryAlchemy);
		researches.add(categorySmithing);
		//researches.add(categoryMateria);
		//researches.add(categoryCore);
	}

	private static ResearchSwitchCategory makeCategorySwitch(ResearchCategory targetCategory, int x, int y, ItemStack icon, int u, int v) {
		return (ResearchSwitchCategory) new ResearchSwitchCategory(targetCategory.name+"_category", icon, x, y).setTargetCategory(targetCategory).setIconBackground(PAGE_ICONS, PAGE_ICON_SIZE * u, PAGE_ICON_SIZE * v);
	}
}
