package com.goopey.spiderdens.entity.render;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.core.ModelLayers;
import com.goopey.spiderdens.entity.RedSpider;
import com.goopey.spiderdens.entity.model.RedSpiderModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RedSpiderRender<T extends RedSpider> extends MobRenderer<T, RedSpiderModel<T>> {
  private static final ResourceLocation RED_SPIDER_TEXTURE = new ResourceLocation(SpiderDens.MOD_ID, "textures/entity/red_spider.png");

  public RedSpiderRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new RedSpiderModel<>(renderManagerIn.bakeLayer(ModelLayers.RED_SPIDER)), 0.5F);
	}

  @Override
  public ResourceLocation getTextureLocation(T pEntity) {
    return RED_SPIDER_TEXTURE;
  }
}
