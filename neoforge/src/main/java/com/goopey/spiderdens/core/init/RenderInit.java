package com.goopey.spiderdens.core.init;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.core.ModelLayers;
import com.goopey.spiderdens.entity.model.RedSpiderModel;
import com.goopey.spiderdens.entity.render.RedSpiderRender;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = SpiderDens.MOD_ID, value=Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class RenderInit {
  @SubscribeEvent 
  public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
    event.registerEntityRenderer(EntityInit.RED_SPIDER.get(), RedSpiderRender::new);
  }

  @SubscribeEvent
  public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
    event.registerLayerDefinition(ModelLayers.RED_SPIDER, RedSpiderModel::createSpiderBodyLayer);
  }
}
