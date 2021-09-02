package com.henryfabio.minecraft.inventoryapi.tests.inventory;

import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.slot.SlotInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.ViewerConfiguration;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.slot.SlotViewer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ars3ne
 * Github: https://github.com/ars3ne
 */
public final class TestSlotInventory extends SlotInventory {

    Map<String, Map<ItemStack, Integer>> items = new HashMap<>();

    public TestSlotInventory() {
        super(
                "test.inventory.slot", // Identificador do inventário (deve ser único)
                "&8SlotInventory", // Título padrão do inventário
                9 * 3 // Tamanho do inventário
        );

    }

    /**
     * Método utilizado para configurar o visualizador, podendo definir
     * unicamente o título do inventário e tamanho.
     * <p>
     * Não é obrigatória a implementação desde método, apenas caso queira uma configuração
     * exclusiva para cada visualizador.
     *
     * @param viewer visualizador do inventário
     */
    @Override
    protected void configureViewer(@NotNull SlotViewer viewer) {
        ViewerConfiguration configuration = viewer.getConfiguration();
        configuration.titleInventory("&8Seu nome: " + viewer.getName());
    }

    /**
     * Método utilizado para configurar os itens do inventário.
     *
     * @param viewer visualizador do inventário
     * @param editor editor do inventário
     */
    @Override
    protected void configureInventory(@NotNull Viewer viewer, @NotNull InventoryEditor editor) {

        editor.setItem(13, InventoryItem.of(
                new ItemStack(Material.STONE)
        ).callback(ClickType.RIGHT, event -> {
            Player player = event.getPlayer();
            player.sendMessage("§eVocê clicou com o botão direito!");
        }).defaultCallback(event -> {
            Player player = event.getPlayer();
            player.sendMessage("§eVocê interagiu com o inventário!");
        }));

        if(items.containsKey(viewer.getName())) {

            Map<ItemStack, Integer> item_map = items.get(viewer.getName());

            for (Map.Entry<ItemStack, Integer> entry : item_map.entrySet()) {
                ItemStack item = entry.getKey();
                if(item == null || item.getType() == Material.AIR) continue;
                item.setAmount(1);
                editor.setItem(entry.getValue(), InventoryItem.of(item));
            }

        }

    }

    @Override
    public void onItemClick(@NotNull SlotViewer viewer, @NotNull InventoryClickEvent event) {

        if(event.isCancelled()) return;

        // Verifique se o jogador pode remover o item do slot atual.
        if(viewer.getEditor().getItemStack(event.getSlot()) != null && viewer.getEditor().getItemStack(event.getSlot()).getType() != Material.AIR) {

            if(items.containsKey(viewer.getName())) {

                boolean has_item = false;

                // Caso o jogador possua um item no inventário, verifique se o que ele quer remover é um deles.
                // Se for, então remova o item da lista de itens. Se não, cancele o evento e não permita que o jogador remova o item.

                for(ItemStack i: items.get(viewer.getName()).keySet()) {
                    if(i.isSimilar(event.getCurrentItem())) {
                        has_item = true;
                        break;
                    }
                }

                if(!has_item) {
                    event.setCancelled(true);
                }else {
                    items.get(viewer.getName()).remove(event.getCurrentItem());
                }

            }else {
                event.setCancelled(true);
            }
            return;

        }

        else if(event.getCursor() == null || event.getCursor().getType() == Material.AIR) {
            event.setCancelled(true);
            return;
        }

        // Adicione o item na lista do usuário, para permitir que o mesmo possa remover-lo depois.
        Map<ItemStack, Integer> item_map;
        if(items.containsKey(viewer.getName())) {
            item_map = items.get(viewer.getName());
        }else {
            item_map = new HashMap<>();
        }

        item_map.put(event.getCursor(), event.getSlot());
        items.put(viewer.getName(), item_map);

    }

}
