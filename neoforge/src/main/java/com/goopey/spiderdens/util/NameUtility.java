package com.goopey.spiderdens.util;

import com.goopey.spiderdens.SpiderDens;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class NameUtility {
  /**
   * This function takes in an Item and extracts its name to then format
   * it appropriately for registry stuff.
   * 
   * @param item the item whose name we will format
   * @return the item name as a string
   */
  public static String getItemName(Item item) {
    return BuiltInRegistries.ITEM.getKey(item)
      .toString().replace(SpiderDens.MODID + ":", "");
  }
}
