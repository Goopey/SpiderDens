package com.goopey.spiderdens.core.init;

import com.goopey.spiderdens.SpiderDens;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ItemInit {
  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SpiderDens.MOD_ID);

  public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties()));

  //#########################################
  //              SPAWN EGGS
  //#########################################
  public static final Supplier<DeferredSpawnEggItem> RED_SPIDER_EGG = ITEMS.register("spawnegg_red_spider", 
    () -> new DeferredSpawnEggItem(
      EntityInit.RED_SPIDER,
      -10081750, 
      -2943723, 
      new Item.Properties())
  );
}