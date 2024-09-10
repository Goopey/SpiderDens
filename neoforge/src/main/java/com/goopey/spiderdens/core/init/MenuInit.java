package com.goopey.spiderdens.core.init;

import java.util.function.Supplier;

import com.goopey.spiderdens.SpiderDens;
import com.goopey.spiderdens.world.screens.FilterHopperMenu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

public class MenuInit {
    public static final DeferredRegister<MenuType<?>> MENU_REGISTER = DeferredRegister.create(BuiltInRegistries.MENU, SpiderDens.MOD_ID);

    public static final Supplier<MenuType<FilterHopperMenu>> FILTER_HOPPER_MENU = MENU_REGISTER.register("filter_hopper_menu", () ->
        IMenuTypeExtension.create(FilterHopperMenu::new));
}
