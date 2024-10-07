package com.goopey.spiderdens.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;

public class RedSpider extends Spider {
  public RedSpider(EntityType<? extends RedSpider> entityType, Level level) {
    super(entityType, level);
  }
}
