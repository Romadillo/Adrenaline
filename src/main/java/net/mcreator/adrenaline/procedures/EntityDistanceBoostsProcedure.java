package net.mcreator.adrenaline.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.adrenaline.AdrenalineModElements;
import net.mcreator.adrenaline.AdrenalineMod;

import java.util.Map;
import java.util.HashMap;

@AdrenalineModElements.ModElement.Tag
public class EntityDistanceBoostsProcedure extends AdrenalineModElements.ModElement {
	public EntityDistanceBoostsProcedure(AdrenalineModElements instance) {
		super(instance, 1);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency entity for procedure EntityDistanceBoosts!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency x for procedure EntityDistanceBoosts!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency y for procedure EntityDistanceBoosts!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency z for procedure EntityDistanceBoosts!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		double Health = 0;
		if ((EntityTypeTags.getCollection().getTagByID(new ResourceLocation(("forge:hostile_mobs").toLowerCase(java.util.Locale.ENGLISH)))
				.contains(entity.getType()))) {
			Health = (double) Math.round((((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHealth() : -1)
					* (1 + (Math.sqrt(((x * x) + ((y * y) + (z * z)))) / 100))));
			((LivingEntity) entity).getAttribute(Attributes.MAX_HEALTH).setBaseValue((Health));
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).setHealth((float) (Health));
		}
	}

	@SubscribeEvent
	public void onEntitySpawned(EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();
		double i = entity.getPosX();
		double j = entity.getPosY();
		double k = entity.getPosZ();
		World world = event.getWorld();
		Map<String, Object> dependencies = new HashMap<>();
		dependencies.put("x", i);
		dependencies.put("y", j);
		dependencies.put("z", k);
		dependencies.put("world", world);
		dependencies.put("entity", entity);
		dependencies.put("event", event);
		this.executeProcedure(dependencies);
	}
}
