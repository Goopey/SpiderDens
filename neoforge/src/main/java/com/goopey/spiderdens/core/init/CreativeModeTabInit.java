package com.goopey.spiderdens.core.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.goopey.spiderdens.SpiderDens;

public class CreativeModeTabInit {
  public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SpiderDens.MOD_ID);

  // tab title
  public static String SPIDER_DENS_TAB_TITLE = "creativetab.spiderdens";

  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TUTORIAL_MOD_TAB = CREATIVE_MODE_TABS.register("spiderdens_mod_tab", () -> {
      CreativeModeTab.Builder builder = CreativeModeTab.builder();

      builder.displayItems((itemDisplayParameters, output) -> {
          ItemInit.ITEMS.getEntries()
                  .stream()
                  .map(DeferredHolder::get)
                  .forEach(output::accept);

          // BlockInit.BLOCKS.getEntries()
          //         .stream()
          //         .map(DeferredHolder::get)
          //         .forEach(output::accept);
      });

      builder.icon(() -> new ItemStack(ItemInit.EXAMPLE_ITEM.get()));
      builder.title(Component.translatable(SPIDER_DENS_TAB_TITLE));

      return builder.build();
  });
}