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

    fun addItem(material: Material, name: String, lore: List<String>, click: (event: InventoryClickEvent) -> Unit) {
        val itemStack = createItemStack(material, name, lore)
        inventory.addItem(itemStack)
        Bukkit.getPluginManager().registerEvents(object : org.bukkit.event.Listener {
            @EventHandler
            fun onInventoryClick(event: InventoryClickEvent) {
                if (event.inventory == inventory) {
                    click(event)
                }
            }
        }, Main.plugin)
    }
}