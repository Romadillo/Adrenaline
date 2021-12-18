package net.mcreator.adrenaline.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.EnchantmentHelper;

import net.mcreator.adrenaline.particle.TraceParticleParticle;
import net.mcreator.adrenaline.enchantment.ShadowStrikeEnchantment;
import net.mcreator.adrenaline.AdrenalineModVariables;
import net.mcreator.adrenaline.AdrenalineMod;

import java.util.Map;
import java.util.HashMap;

public class TraceProcedureProcedure {
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
				AdrenalineMod.LOGGER.warn("Failed to load dependency entity for procedure TraceProcedure!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency x for procedure TraceProcedure!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency y for procedure TraceProcedure!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency z for procedure TraceProcedure!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency world for procedure TraceProcedure!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		IWorld world = (IWorld) dependencies.get("world");
		double traceLevel = 0;
		double velX = 0;
		double velY = 0;
		double velZ = 0;
		double offsetX = 0;
		double offsetY = 0;
		double offsetZ = 0;
		double vel = 0;
		traceLevel = (double) (EnchantmentHelper.getEnchantmentLevel(ShadowStrikeEnchantment.enchantment,
				((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)));
		if (((((entity.getCapability(AdrenalineModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new AdrenalineModVariables.PlayerVariables())).BowDrawTIme) > 0) && (traceLevel > 0))) {
			vel = (double) (3 * (((entity.getCapability(AdrenalineModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new AdrenalineModVariables.PlayerVariables())).BowDrawTIme) / 22));
			offsetX = (double) x;
			offsetY = (double) ((y + 1.62) - (0.5 * Math.sin(Math.toRadians((entity.rotationPitch)))));
			offsetZ = (double) z;
			velX = (double) Math.cos(Math.toRadians(((entity.rotationYaw) + 90)));
			velY = (double) (Math.sin(Math.toRadians(((entity.rotationPitch) * (-1)))) - (0.5 * Math.sin(Math.toRadians((entity.rotationPitch)))));
			velZ = (double) Math.sin(Math.toRadians(((entity.rotationYaw) + 90)));
			for (int index0 = 0; index0 < (int) (20); index0++) {
				offsetX = (double) (offsetX + (vel * velX));
				offsetY = (double) (offsetY + (vel * velY));
				offsetZ = (double) (offsetZ + (vel * velZ));
				vel = (double) (vel * 0.99);
				world.addParticle(TraceParticleParticle.particle, offsetX, offsetY, offsetZ, 0, 0, 0);
			}
		}
	}
}
