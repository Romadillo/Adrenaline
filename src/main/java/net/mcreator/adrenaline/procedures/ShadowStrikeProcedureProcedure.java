package net.mcreator.adrenaline.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.EnchantmentHelper;

import net.mcreator.adrenaline.enchantment.ShadowStrikeEnchantment;
import net.mcreator.adrenaline.AdrenalineMod;

import java.util.Map;
import java.util.HashMap;

public class ShadowStrikeProcedureProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onEntityAttacked(LivingAttackEvent event) {
			if (event != null && event.getEntity() != null) {
				Entity entity = event.getEntity();
				Entity sourceentity = event.getSource().getTrueSource();
				Entity imediatesourceentity = event.getSource().getImmediateSource();
				double i = entity.getPosX();
				double j = entity.getPosY();
				double k = entity.getPosZ();
				double amount = event.getAmount();
				World world = entity.world;
				Map<String, Object> dependencies = new HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
				dependencies.put("amount", amount);
				dependencies.put("world", world);
				dependencies.put("entity", entity);
				dependencies.put("sourceentity", sourceentity);
				dependencies.put("imediatesourceentity", imediatesourceentity);
				dependencies.put("event", event);
				executeProcedure(dependencies);
			}
		}
	}
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency entity for procedure ShadowStrikeProcedure!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency sourceentity for procedure ShadowStrikeProcedure!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency world for procedure ShadowStrikeProcedure!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		IWorld world = (IWorld) dependencies.get("world");
		double enchantmentLevel = 0;
		enchantmentLevel = (double) (EnchantmentHelper.getEnchantmentLevel(ShadowStrikeEnchantment.enchantment,
				((sourceentity instanceof LivingEntity) ? ((LivingEntity) sourceentity).getHeldItemMainhand() : ItemStack.EMPTY)));
		if ((7 > (world.getLight(new BlockPos((int) Math.round((sourceentity.getPosX())), (int) Math.round((sourceentity.getPosY())),
				(int) Math.round((sourceentity.getPosZ()))))))) {
			if ((enchantmentLevel == 1)) {
				entity.attackEntityFrom(DamageSource.GENERIC, (float) 1);
			} else if ((enchantmentLevel == 2)) {
				entity.attackEntityFrom(DamageSource.GENERIC, (float) 2);
			}
		}
	}
}
