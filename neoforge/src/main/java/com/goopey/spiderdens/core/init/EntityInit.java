package com.goopey.spiderdens.core.init;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.entity.RedSpider;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@EventBusSubscriber(modid = SpiderDens.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EntityInit {
  public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, SpiderDens.MOD_ID);

  public static final Supplier<EntityType<RedSpider>> RED_SPIDER = ENTITIES.register("red_spider", () -> 
    EntityType.Builder.of(RedSpider::new, MobCategory.MONSTER).fireImmune().sized(1.5F, 0.9F).clientTrackingRange(8).build(prefix("red_spider"))
  );

  private static String prefix(String path) {
		return SpiderDens.MOD_ID + path;
	}

  @SubscribeEvent
  public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
    event.put(EntityInit.RED_SPIDER.get(), RedSpider.createAttributes().build());
  }

  @SubscribeEvent
  public static void registerEntitySpawn(SpawnPlacementRegisterEvent event) {
    event.register(EntityInit.RED_SPIDER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
  }
}