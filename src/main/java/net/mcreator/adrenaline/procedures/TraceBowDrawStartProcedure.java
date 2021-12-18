package net.mcreator.adrenaline.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import net.minecraft.world.World;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;

import net.mcreator.adrenaline.AdrenalineModVariables;
import net.mcreator.adrenaline.AdrenalineMod;

import java.util.Map;
import java.util.HashMap;

public class TraceBowDrawStartProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onUseItemStart(LivingEntityUseItemEvent.Start event) {
			if (event != null && event.getEntity() != null) {
				Entity entity = event.getEntity();
				double i = entity.getPosX();
				double j = entity.getPosY();
				double k = entity.getPosZ();
				double duration = event.getDuration();
				ItemStack itemstack = event.getItem();
				World world = entity.world;
				Map<String, Object> dependencies = new HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
				dependencies.put("itemstack", itemstack);
				dependencies.put("duration", duration);
				dependencies.put("world", world);
				dependencies.put("entity", entity);
				dependencies.put("event", event);
				executeProcedure(dependencies);
			}
		}
	}
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency entity for procedure TraceBowDrawStart!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency itemstack for procedure TraceBowDrawStart!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		if ((((itemstack).getItem() == Items.BOW) || ((itemstack).getItem() == Items.CROSSBOW))) {
			{
				boolean _setval = (boolean) (true);
				entity.getCapability(AdrenalineModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.BowDrawn = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
	}
}
