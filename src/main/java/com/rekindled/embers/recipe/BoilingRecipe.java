package com.rekindled.embers.recipe;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.rekindled.embers.RegistryManager;
import com.rekindled.embers.util.Misc;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class BoilingRecipe implements Recipe<BoilingContext> {

	public static final Serializer SERIALIZER = new Serializer(); 

	public final ResourceLocation id;

	public final FluidIngredient input;
	public final FluidStack output;

	public BoilingRecipe(ResourceLocation id, FluidIngredient input, FluidStack output) {
		this.id = id;
		this.input = input;
		this.output = output;
	}

	@Override
	public boolean matches(BoilingContext context, Level pLevel) {
		for (FluidStack stack : input.getAllFluids()) {
			if (input.test(context.fluid.drain(stack, FluidAction.SIMULATE))) {
				return true;
			}
		}
		return false;
	}

	public FluidStack getOutput(BoilingContext context) {
		return output;
	}

	public FluidStack process(BoilingContext context, int amount) {
		for (FluidStack stack : input.getAllFluids()) {
			FluidStack drainStack = new FluidStack(stack, stack.getAmount() * amount);
			if (input.test(context.fluid.drain(drainStack, FluidAction.SIMULATE))) {
				context.fluid.drain(drainStack, FluidAction.EXECUTE);
				break;
			}
		}
		return new FluidStack(output, output.getAmount() * amount);
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(RegistryManager.MINI_BOILER_ITEM.get());
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return RegistryManager.BOILING.get();
	}

	public FluidIngredient getDisplayInput() {
		return input;
	}

	public FluidStack getDisplayOutput() {
		return output;
	}

	@Override
	@Deprecated
	public ItemStack assemble(BoilingContext context, RegistryAccess registry) {
		return ItemStack.EMPTY;
	}

	@Override
	@Deprecated
	public ItemStack getResultItem(RegistryAccess registry) {
		return ItemStack.EMPTY;
	}

	@Override
	@Deprecated
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	public static class Serializer implements RecipeSerializer<BoilingRecipe> {

		@Override
		public BoilingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			FluidStack output = Misc.deserializeFluidStack(GsonHelper.getAsJsonObject(json, "output"));
			FluidIngredient input = FluidIngredient.deserialize(json, "input");

			return new BoilingRecipe(recipeId, input, output);
		}

		@Override
		public @Nullable BoilingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			FluidIngredient input = FluidIngredient.read(buffer);
			FluidStack output = FluidStack.readFromPacket(buffer);

			return new BoilingRecipe(recipeId, input, output);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, BoilingRecipe recipe) {
			recipe.input.write(buffer);
			recipe.output.writeToPacket(buffer);
		}
	}
}
