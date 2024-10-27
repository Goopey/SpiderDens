package com.goopey.spiderdens.entity.model;

import com.goopey.spiderdens.entity.HellSpider;

import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelPart;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HellSpiderModel<T extends HellSpider> extends SpiderModel<T> {
  public HellSpiderModel(ModelPart root) {
    super(root);
  }
}