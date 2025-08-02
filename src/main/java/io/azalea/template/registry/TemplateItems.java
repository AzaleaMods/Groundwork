package io.azalea.template.registry;

import io.azalea.template.TemplateMod;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class TemplateItems {

    public static final Item TEST_ITEM = new Item(new Item.Settings());

    public static void register() {
        registerItem("test_item", TEST_ITEM);
    }

    public static void registerItem(String id, Item item) {
        Registry.register(Registries.ITEM, TemplateMod.id(id), item);
    }

}
