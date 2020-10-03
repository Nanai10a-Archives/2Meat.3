package net.nanai10a.twomeat.core

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.CopyOnWriteArrayList
import java.util.zip.ZipInputStream

class ListenerLoader {
    companion object {
        private val logger = LoggerFactory.getLogger(ListenerLoader::class.java)
        val listeners = CopyOnWriteArrayList<Listener>()
        private fun register(listener: Listener) {
            listeners.add(listener)
            logger.debug("listener ${listener.name} was registered")
        }

        fun load() {
            logger.debug("getting listeners directory...")
            val directory = File("./listeners")
            if (!directory.exists()) directory.mkdir()
            if (!directory.isDirectory) throw Exception("can't get ./listeners/* paths")
            logger.debug("gotten listeners directory")

            logger.debug("getting listeners property...")
            directory.listFiles()?.forEach {
                if (!it.isFile) return@forEach

                val input = ZipInputStream(BufferedInputStream(FileInputStream(it)))

                while (true) {
                    val entry = input.nextEntry ?: break
                    if (entry.name != "listener.2tjson") continue
                    String(input.readAllBytes()).let {
                        ObjectMapper().readValue(it, TwoTJson::class.java)
                    }
                }
            } ?: throw Exception("can't get listeners file")
            logger.debug("gotten listeners property")
        }
    }
}
