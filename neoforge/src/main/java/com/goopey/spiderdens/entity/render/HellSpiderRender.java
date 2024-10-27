package com.goopey.spiderdens.entity.render;

import org.joml.Vector3f;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.core.ModelLayers;
import com.goopey.spiderdens.entity.HellSpider;
import com.goopey.spiderdens.entity.model.HellSpiderModel;
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
public class HellSpiderRender<T extends HellSpider> extends MobRenderer<T, HellSpiderModel<T>> {
  private static final ResourceLocation HELL_SPIDER_TEXTURE = ResourceLocation.fromNamespaceAndPath(SpiderDens.MOD_ID, "textures/entity/hell_spider.png");
  private static final ResourceLocation HELL_SPIDER_EYE_TEXTURE = ResourceLocation.fromNamespaceAndPath(SpiderDens.MOD_ID, "textures/entity/hell_spider_eyes.png");
  private static final ResourceLocation HELL_SPIDER_HAIR_TEXTURE = ResourceLocation.fromNamespaceAndPath(SpiderDens.MOD_ID, "textures/entity/hell_spider_hair.png");

  public HellSpiderRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new HellSpiderModel<>(renderManagerIn.bakeLayer(ModelLayers.HELL_SPIDER)), 0.5F);
    
    //#####################################
    //            GLOWING EYES
    //#####################################
    this.addLayer(new RenderLayer<T,HellSpiderModel<T>>(this) {
      @SuppressWarnings({ "unchecked", "rawtypes", "null" })
      @Override
      public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.eyes(HELL_SPIDER_EYE_TEXTURE));
				EntityModel model = new HellSpiderModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.HELL_SPIDER));
				this.getParentModel().copyPropertiesTo(model);
				model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
				model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				model.renderToBuffer(poseStack, vertexConsumer, 15728640, LivingEntityRenderer.getOverlayCoords(entity, 0));
			}
    });
    this.addLayer(new RenderLayer<T,HellSpiderModel<T>>(this) {
      // FIXME : make layer not glow in the dark.
      @SuppressWarnings({ "unchecked", "rawtypes", "null" })
      @Override
      public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(HELL_SPIDER_HAIR_TEXTURE));
        
        var model = Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.HELL_SPIDER);
        model.offsetScale(new Vector3f(.05f, .05f, .05f));
        model.y -= 0.5f;

				EntityModel entityModel = new HellSpiderModel(model);
        this.getParentModel().copyPropertiesTo(entityModel);
				entityModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
				entityModel.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				entityModel.renderToBuffer(poseStack, vertexConsumer, 15728640, LivingEntityRenderer.getOverlayCoords(entity, 0));
			}
    });
  }

  @SuppressWarnings("null")
  @Override
  public ResourceLocation getTextureLocation(T pEntity) {
    return HELL_SPIDER_TEXTURE;
  }
}
