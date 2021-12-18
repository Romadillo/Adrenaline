package net.mcreator.adrenaline.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.World;
import net.minecraft.entity.Entity;

import net.mcreator.adrenaline.AdrenalineModVariables;
import net.mcreator.adrenaline.AdrenalineMod;

import java.util.Map;
import java.util.HashMap;

public class BowDrawTimeProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				Entity entity = event.player;
				World world = entity.world;
				double i = entity.getPosX();
				double j = entity.getPosY();
				double k = entity.getPosZ();
				Map<String, Object> dependencies = new HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
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
				AdrenalineMod.LOGGER.warn("Failed to load dependency entity for procedure BowDrawTime!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if ((((entity.getCapability(AdrenalineModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new AdrenalineModVariables.PlayerVariables())).BowDrawn) == (true))) {
			if ((((entity.getCapability(AdrenalineModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new AdrenalineModVariables.PlayerVariables())).BowDrawTIme) < 22)) {
				{
					double _setval = (double) (((entity.getCapability(AdrenalineModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new AdrenalineModVariables.PlayerVariables())).BowDrawTIme) + 1);
					entity.getCapability(AdrenalineModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.BowDrawTIme = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
		}
	}
}
