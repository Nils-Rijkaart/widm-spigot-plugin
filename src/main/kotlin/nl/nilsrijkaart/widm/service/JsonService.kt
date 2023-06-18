package nl.nilsrijkaart.widm.service

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File

class JsonService<T : Any>(val subPath: String, private val cls: Class<T>) {

    init {
        directory().mkdirs()
    }

    private fun directory(): File = File("data/$subPath")

    fun loadAll(): List<T> = directory().listFiles().mapNotNull {
        load(it.name.substringBefore(".json"))
    }

    private fun getFile(name: String): File =
        File("data/$subPath/$name.json")


    fun delete(name: String): Boolean {
        val file = getFile(name)
        return file.delete()
    }

    fun save(t: T, name: String): T {
        val directory = name.replace(name.split("/").last(), "")
        File("data/$subPath/$directory").mkdirs()


        val file = getFile(name)
        file.createNewFile()

        val jsonString = gson.toJson(t)


        file.writeText(jsonString)
        return t
    }


    fun loadIndex(index: Int): List<T>? {
        if (directory().listFiles() == null) {
            return null
        }

        val fileList = directory().listFiles().sortedByDescending {
            it.name
        }.filter {
            !it.isDirectory
        }
        if (fileList.size <= index) {
            return null
        }
        val file = fileList.reversed()[index]
        return if (file.exists()) {
            val bufferedReader = file.bufferedReader()
            val inputString = bufferedReader.use { it.readText() }
            val itemType = object : TypeToken<List<T?>>() {}.type

            gson.fromJson<List<T>>(inputString, itemType)
        } else {
            null
        }
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

    fun getIndex(directory: String, name: String): Int =
        File("data/$subPath/$directory").listFiles().sortedBy {
            it.name
        }.indexOfFirst {
            it.name == "$name.json"
        }

    companion object {
        val gson: Gson = GsonBuilder().create()
    }
}
