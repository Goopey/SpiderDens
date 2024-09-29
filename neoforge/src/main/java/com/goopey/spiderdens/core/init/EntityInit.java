package com.goopey.spiderdens.core.init;

import com.goopey.spiderdens.SpiderDens;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Spider;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EntityInit {
  public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, SpiderDens.MOD_ID);

  public static final Supplier<EntityType<Spider>> RED_SPIDER = ENTITIES.register("hunter", () -> 
    EntityType.Builder.of(Spider::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8).build(prefix("hunter"))
  );

  private static String prefix(String path) {
		return SpiderDens.MOD_ID + path;
	}
}