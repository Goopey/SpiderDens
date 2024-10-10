package com.goopey.spiderdens.entity.render;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.core.ModelLayers;
import com.goopey.spiderdens.entity.RedSpider;
import com.goopey.spiderdens.entity.model.RedSpiderModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RedSpiderRender<T extends RedSpider> extends MobRenderer<T, RedSpiderModel<T>> {
  private static final ResourceLocation RED_SPIDER_TEXTURE = new ResourceLocation(SpiderDens.MOD_ID, "textures/entity/red_spider.png");
  private static final ResourceLocation RED_SPIDER_EYE_TEXTURE = new ResourceLocation(SpiderDens.MOD_ID, "textures/entity/red_spider_eyes.png");

  public RedSpiderRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new RedSpiderModel<>(renderManagerIn.bakeLayer(ModelLayers.RED_SPIDER)), 0.5F);
    
    //#####################################
    //            GLOWING EYES
    //#####################################
    this.addLayer(new RenderLayer<T,RedSpiderModel<T>>(this) {
      @SuppressWarnings({ "unchecked", "rawtypes", "null" })
      @Override
      public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(RED_SPIDER_EYE_TEXTURE));
				EntityModel model = new RedSpiderModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.RED_SPIDER));
				this.getParentModel().copyPropertiesTo(model);
				model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
				model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				model.renderToBuffer(poseStack, vertexConsumer, 15728640, LivingEntityRenderer.getOverlayCoords(entity, 0), 1, 1, 1, 1);
			}
    });
  }

  @SuppressWarnings("null")
  @Override
  public ResourceLocation getTextureLocation(T pEntity) {
    return RED_SPIDER_TEXTURE;
  }
}
