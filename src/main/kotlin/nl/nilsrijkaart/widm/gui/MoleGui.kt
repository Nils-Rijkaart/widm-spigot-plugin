package nl.nilsrijkaart.widm.gui

import nl.nilsrijkaart.widm.Main
import nl.nilsrijkaart.widm.util.formattedMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MoleGui(name: String, size: Int) {

    private val inventory = Bukkit.createInventory(null, size, formattedMessage(name))

    fun open(player: Player) {
        player.openInventory(inventory)
    }

    private fun createItemStack(material: Material, name: String, lore: List<String>): ItemStack {
        val itemStack = ItemStack(material)
        val itemMeta = itemStack.itemMeta
        itemMeta?.setDisplayName(formattedMessage(name))
        itemMeta?.lore = lore.map { formattedMessage(it) }
        itemStack.itemMeta = itemMeta
        return itemStack
    }


    // deduplicate this method
    fun addSkull(
        idx: Int = -1,
        userName: String,
        displayName: String,
        lore: List<String>,
        enchanted : Boolean,
        click: (event: InventoryClickEvent) -> Unit
    ) {
        val itemStack = createItemStack(Material.PLAYER_HEAD, displayName, lore)
        val skullMeta = itemStack.itemMeta as org.bukkit.inventory.meta.SkullMeta
        skullMeta.owningPlayer = Bukkit.getPlayer(userName)
        if (enchanted) {
            skullMeta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 1, true)
        }
        itemStack.itemMeta = skullMeta
        if (idx == -1) {
            inventory.addItem(itemStack)
        } else {
            inventory.setItem(idx, itemStack)
        }

        Bukkit.getPluginManager().registerEvents(object : org.bukkit.event.Listener {
            // check performance
            @EventHandler
            fun onInventoryClick(event: InventoryClickEvent) {
                if (event.inventory == inventory) {
                    if (event.currentItem == itemStack) {
                        click(event)
                    }
                }
            }
        }, Main.plugin)

    }

    fun addItem(
        idx: Int = -1,
        material: Material,
        name: String,
        lore: List<String>,
        click: (event: InventoryClickEvent) -> Unit
    ) {
        val itemStack = createItemStack(material, name, lore)
        if (idx == -1) {
            inventory.addItem(itemStack)
        } else {
            inventory.setItem(idx, itemStack)
        }
        Bukkit.getPluginManager().registerEvents(object : org.bukkit.event.Listener {
            // check performance
            @EventHandler
            fun onInventoryClick(event: InventoryClickEvent) {
                if (event.inventory == inventory) {
                    if (event.currentItem == itemStack) {
                        click(event)
                    }
                }
            }
        }, Main.plugin)
    }
}