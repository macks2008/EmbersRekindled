package teamroots.embers.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import teamroots.embers.entity.EntityEmberLight;

public class ItemGlimmerLamp extends ItemBase {
	public ItemGlimmerLamp() {
		super("glimmer_lamp", true);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected){
		if (!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("light", 1200);
		}
		else {
			if (!world.isRemote){
				if (world.getLightBrightness(entity.getPosition()) > 0.625f && entity.posY > world.getTopSolidOrLiquidBlock(entity.getPosition()).getY()-2){
					stack.getTagCompound().setInteger("light", Math.min(1200, stack.getTagCompound().getInteger("light")+1));
				}
			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
		ItemStack stack = player.getHeldItem(hand);
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().getInteger("light") >= 10){
				stack.getTagCompound().setInteger("light",stack.getTagCompound().getInteger("light")-10);
				if (!world.isRemote){
					EntityEmberLight light = (new EntityEmberLight(world));
					light.initCustom(player.posX,player.posY+player.getEyeHeight(),player.posZ,player.getLookVec().x,player.getLookVec().y,player.getLookVec().z);
					world.spawnEntity(light);
				}
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS,stack);
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL,stack);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack){
		if (stack.hasTagCompound()){
			if (stack.getTagCompound().getInteger("light") < 1200){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged){
		return slotChanged || oldStack.getItem() != newStack.getItem();
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack){
		if (stack.hasTagCompound()){
			return (1200.0-(double)stack.getTagCompound().getInteger("light"))/1200.0;
		}
		return 0.0;
	}
}
