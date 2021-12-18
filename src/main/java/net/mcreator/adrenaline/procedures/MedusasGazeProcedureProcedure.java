package net.mcreator.adrenaline.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.EnchantmentHelper;

import net.mcreator.adrenaline.enchantment.MedusasGazeEnchantment;
import net.mcreator.adrenaline.AdrenalineModVariables;
import net.mcreator.adrenaline.AdrenalineMod;

import java.util.function.Function;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

public class MedusasGazeProcedureProcedure {
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
				AdrenalineMod.LOGGER.warn("Failed to load dependency entity for procedure MedusasGazeProcedure!");
			return;
		}
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				AdrenalineMod.LOGGER.warn("Failed to load dependency world for procedure MedusasGazeProcedure!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		IWorld world = (IWorld) dependencies.get("world");
		double i = 0;
		double scanRange = 0;
		double scanPrecision = 0;
		double medusaLevel = 0;
		if (((((entity.getCapability(AdrenalineModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new AdrenalineModVariables.PlayerVariables())).BowDrawTIme) > 0)
				&& ((EnchantmentHelper.getEnchantmentLevel(MedusasGazeEnchantment.enchantment,
						((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY))) > 0))) {
			medusaLevel = (double) (EnchantmentHelper.getEnchantmentLevel(MedusasGazeEnchantment.enchantment,
					((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY)));
			if ((medusaLevel == 1)) {
				scanRange = (double) 20;
				scanPrecision = (double) 1;
			} else if ((medusaLevel == 2)) {
				scanRange = (double) 30;
				scanPrecision = (double) 1.5;
			} else {
				scanRange = (double) 40;
				scanPrecision = (double) 2;
			}
			i = (double) 0;
			for (int index0 = 0; index0 < (int) (scanRange); index0++) {
				if ((((Entity) world
						.getEntitiesWithinAABB(MobEntity.class,
								new AxisAlignedBB(
										(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
												entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
														entity.getLook(1f).z * i),
												RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getX())
												- (scanPrecision / 2d),
										(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
												entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
														entity.getLook(1f).z * i),
												RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getY())
												- (scanPrecision / 2d),
										(entity.world
												.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
														entity.getEyePosition(1f)
																.add(entity.getLook(1f).x * i, entity.getLook(1f).y * i, entity.getLook(1f).z * i),
														RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity))
												.getPos().getZ()) - (scanPrecision / 2d),
										(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
												entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
														entity.getLook(1f).z * i),
												RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getX())
												+ (scanPrecision / 2d),
										(entity.world
												.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
														entity.getEyePosition(1f)
																.add(entity.getLook(1f).x * i, entity.getLook(1f).y * i, entity.getLook(1f).z * i),
														RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity))
												.getPos().getY()) + (scanPrecision / 2d),
										(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
												entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
														entity.getLook(1f).z * i),
												RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ())
												+ (scanPrecision / 2d)),
								null)
						.stream().sorted(new Object() {
							Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
								return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
							}
						}.compareDistOf(
								(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
										entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i, entity.getLook(1f).z * i),
										RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getX()),
								(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
										entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i, entity.getLook(1f).z * i),
										RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getY()),
								(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
										entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i, entity.getLook(1f).z * i),
										RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ())))
						.findFirst().orElse(null)) != null)) {
					if ((!(((Entity) world.getEntitiesWithinAABB(MobEntity.class,
							new AxisAlignedBB(
									(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
											entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
													entity.getLook(1f).z * i),
											RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getX())
											- (scanPrecision / 2d),
									(entity.world
											.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
													entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
															entity.getLook(1f).z * i),
													RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity))
											.getPos().getY()) - (scanPrecision / 2d),
									(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
											entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
													entity.getLook(1f).z * i),
											RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ())
											- (scanPrecision / 2d),
									(entity.world
											.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
													entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
															entity.getLook(1f).z * i),
													RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity))
											.getPos().getX()) + (scanPrecision / 2d),
									(entity.world
											.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
													entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
															entity.getLook(1f).z * i),
													RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity))
											.getPos().getY()) + (scanPrecision / 2d),
									(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
											entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
													entity.getLook(1f).z * i),
											RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ())
											+ (scanPrecision / 2d)),
							null).stream().sorted(new Object() {
								Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
									return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
								}
							}.compareDistOf(
									(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
											entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
													entity.getLook(1f).z * i),
											RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getX()),
									(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
											entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
													entity.getLook(1f).z * i),
											RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getY()),
									(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
											entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
													entity.getLook(1f).z * i),
											RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ())))
							.findFirst().orElse(null)) == (null)))) {
						if (((Entity) world.getEntitiesWithinAABB(MobEntity.class,
								new AxisAlignedBB(
										(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
												entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
														entity.getLook(1f).z * i),
												RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getX())
												- (scanPrecision / 2d),
										(entity.world
												.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
														entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
																entity.getLook(1f).z * i),
														RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity))
												.getPos().getY()) - (scanPrecision / 2d),
										(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
												entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
														entity.getLook(1f).z * i),
												RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ())
												- (scanPrecision / 2d),
										(entity.world
												.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
														entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
																entity.getLook(1f).z * i),
														RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity))
												.getPos().getX()) + (scanPrecision / 2d),
										(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
												entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
														entity.getLook(1f).z * i),
												RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getY())
												+ (scanPrecision / 2d),
										(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
												entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
														entity.getLook(1f).z * i),
												RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ())
												+ (scanPrecision / 2d)),
								null).stream().sorted(new Object() {
									Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
										return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
									}
								}.compareDistOf(
										(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
												entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
														entity.getLook(1f).z * i),
												RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getX()),
										(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
												entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
														entity.getLook(1f).z * i),
												RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getY()),
										(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
												entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
														entity.getLook(1f).z * i),
												RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ())))
								.findFirst().orElse(null)) instanceof LivingEntity)
							((LivingEntity) ((Entity) world.getEntitiesWithinAABB(MobEntity.class,
									new AxisAlignedBB(
											(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
													entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
															entity.getLook(1f).z * i),
													RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getX())
													- (scanPrecision / 2d),
											(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
													entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
															entity.getLook(1f).z * i),
													RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getY())
													- (scanPrecision / 2d),
											(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
													entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
															entity.getLook(1f).z * i),
													RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ())
													- (scanPrecision / 2d),
											(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
													entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
															entity.getLook(1f).z * i),
													RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getX())
													+ (scanPrecision / 2d),
											(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
													entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
															entity.getLook(1f).z * i),
													RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getY())
													+ (scanPrecision / 2d),
											(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
													entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
															entity.getLook(1f).z * i),
													RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ())
													+ (scanPrecision / 2d)),
									null).stream().sorted(new Object() {
										Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
											return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
										}
									}.compareDistOf(
											(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
													entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
															entity.getLook(1f).z * i),
													RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getX()),
											(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
													entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
															entity.getLook(1f).z * i),
													RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getY()),
											(entity.world.rayTraceBlocks(new RayTraceContext(entity.getEyePosition(1f),
													entity.getEyePosition(1f).add(entity.getLook(1f).x * i, entity.getLook(1f).y * i,
															entity.getLook(1f).z * i),
													RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity)).getPos().getZ())))
									.findFirst().orElse(null)))
											.addPotionEffect(new EffectInstance(Effects.SLOWNESS, (int) 2, (int) medusaLevel, (false), (true)));
						break;
					}
				} else {
					i = (double) (i + 1);
				}
			}
		}
	}
}
