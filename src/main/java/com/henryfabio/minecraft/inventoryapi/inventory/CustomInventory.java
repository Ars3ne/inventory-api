package com.henryfabio.minecraft.inventoryapi.inventory;

import com.henryfabio.minecraft.inventoryapi.inventory.configuration.InventoryConfiguration;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.ViewerImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.slot.SlotViewer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public interface CustomInventory {

    @NotNull
    String getId();

    @NotNull
    String getTitle();

    int getSize();

    @NotNull <T extends InventoryConfiguration> T getConfiguration();

    <T extends InventoryConfiguration> void configuration(@NotNull Consumer<T> consumer);

    <T extends CustomInventory> T init();

    <T extends Viewer> void openInventory(@NotNull Player player, @Nullable Consumer<T> viewerConsumer);

    void openInventory(@NotNull Player player);

    void updateInventory(@NotNull Player player);

    String getType();

    void onItemClick(@NotNull SlotViewer viewer, @NotNull InventoryClickEvent event);

}
