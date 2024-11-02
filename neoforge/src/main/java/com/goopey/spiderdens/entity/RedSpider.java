package com.goopey.spiderdens.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class RedSpider extends Spider {
  public RedSpider(EntityType<? extends RedSpider> entityType, Level level) {
    super(entityType, level);
  }

  public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
      .add(Attributes.MOVEMENT_SPEED, (double) 0.3F)
      .add(Attributes.FOLLOW_RANGE, 20.0D)
      .add(Attributes.MAX_HEALTH, 16.0D)
      .add(Attributes.ARMOR, 0.0D)
      .add(Attributes.ATTACK_DAMAGE, 3.0D);
	}
}
