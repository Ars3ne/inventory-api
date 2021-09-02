package com.henryfabio.minecraft.inventoryapi.viewer.impl.slot;

import com.henryfabio.minecraft.inventoryapi.inventory.CustomInventory;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.impl.ViewerConfigurationImpl;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.ViewerImpl;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
public final class SlotViewer extends ViewerImpl {

    public SlotViewer(String name, CustomInventory customInventory) {
        super(name, customInventory, new ViewerConfigurationImpl.Slot());
    }

}
