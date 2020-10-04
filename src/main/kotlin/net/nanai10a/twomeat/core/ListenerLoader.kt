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
        private val logger = LoggerFactory.getLogger(this::class.java)
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
            directory.listFiles()?.forEach { file ->
                if (!file.isFile) {
                    logger.debug("${file.name} (${file.absolutePath}) is not file")
                    return@forEach
                }

                logger.debug("open InputStream! (${file.absolutePath})")
                val input = ZipInputStream(BufferedInputStream(FileInputStream(file)))

                while (true) {
                    val entry = input.nextEntry ?: break
                    if (entry.name != "listener.2tjson") continue

                    logger.debug("found 2tjson! (${entry.name}) ")

                    logger.debug("read all bytes of 2tjson!")
                    val string = String(input.readAllBytes())

                    input.close()
                    logger.debug("closed InputStream...")

                    logger.debug("read bytes Analyzing...")
                    val coreClassString = ObjectMapper()
                        .readValue(string, TwoTJson::class.java)
                        .listenerProperty
                        .listenerCoreClass

                    logger.debug("loading coreClass...")
                    val coreClass = ClassLoader
                        .getSystemClassLoader()
                        .loadClass(coreClassString)

                    if (coreClass.superclass != Listener::class.java)
                        throw Exception("${coreClass.simpleName} don't have ${Listener::class.java.simpleName} on superclass")

                    var instance: Any? = null

                    logger.debug("calling constructor...")
                    coreClass
                        .constructors
                        .iterator()
                        .forEachRemaining {
                            try {
                                instance = it.newInstance()
                            } catch (e: IllegalAccessException) {
                                return@forEachRemaining
                            } catch (e: IllegalArgumentException) {
                                return@forEachRemaining
                            }
                        }
                    if (instance == null) throw Exception("can't create ${coreClass.simpleName} instance")
                    logger.debug("created listener instance! (${{instance as Listener}.invoke().name} ver. ${{instance as Listener}.invoke().version} )")
                    listeners.add(instance as Listener)
                }
            } ?: throw Exception("can't get listeners file")
            logger.debug("gotten listeners property!")
        }
    }
}
