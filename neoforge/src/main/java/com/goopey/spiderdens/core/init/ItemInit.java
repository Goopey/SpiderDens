package com.goopey.spiderdens.core.init;

import com.goopey.spiderdens.SpiderDens;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemInit {
  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SpiderDens.MODID);

  public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties()));
}