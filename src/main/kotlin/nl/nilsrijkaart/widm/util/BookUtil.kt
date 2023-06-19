package nl.nilsrijkaart.widm.util

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta

class BookUtil {
    companion object {
        fun createBook(title: String, author: String, pages: List<String>): ItemStack {
            val book = ItemStack(Material.WRITTEN_BOOK)
            val meta = book.itemMeta as BookMeta
            meta.title = title
            meta.author = author
            meta.pages = pages
            book.itemMeta = meta
            return book
        }
    }
}