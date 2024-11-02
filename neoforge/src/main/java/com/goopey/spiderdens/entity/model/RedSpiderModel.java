package com.goopey.spiderdens.entity.model;

import com.goopey.spiderdens.entity.RedSpider;

import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelPart;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RedSpiderModel<T extends RedSpider> extends SpiderModel<T> {
  public RedSpiderModel(ModelPart root) {
    super(root);
  }
}