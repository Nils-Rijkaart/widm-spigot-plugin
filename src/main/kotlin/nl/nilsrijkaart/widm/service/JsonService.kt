package nl.nilsrijkaart.widm.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.plugin.Plugin
import java.io.BufferedReader
import java.io.File

class JsonService<T : Any>(private val plugin: Plugin, val subPath: String, private val cls: Class<T>) {

    init {
        // Create folders if needed
        directory().mkdirs()
    }

    private fun directory(): File = File("${plugin.dataFolder}/$subPath")

    fun loadAll(): List<T> = directory().listFiles().mapNotNull {
        load(it.name.substringBefore(".json"))
    }

    private fun getFile(name: String): File =
        File("${plugin.dataFolder}/$subPath/$name.json")


    fun delete(name: String): Boolean {
        val file = getFile(name)
        return file.delete()
    }

    fun save(t: T, name: String): T {
        println("Called save for $name")
        val file = getFile(name)
        file.createNewFile()

        val jsonString = gson.toJson(t)

        file.writeText(jsonString)
        return t
    }


    fun load(name: String): T? {

        val file = getFile(name)

        return if (file.exists()) {
            val bufferedReader: BufferedReader = file.bufferedReader()
            val inputString = bufferedReader.use { it.readText() }
            gson.fromJson(inputString, cls)
        } else {
            null
        }
    }

    companion object {
        val gson: Gson = GsonBuilder().create()

    }
}

fun main() {
}