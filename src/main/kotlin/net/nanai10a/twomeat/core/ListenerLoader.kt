package net.nanai10a.twomeat.core

import org.slf4j.LoggerFactory
import java.util.concurrent.CopyOnWriteArrayList

class ListenerLoader {
    companion object {
        private val logger = LoggerFactory.getLogger(ListenerLoader::class.java)
        val listeners = CopyOnWriteArrayList<Listener>()
        private fun register(listener: Listener) {
            listeners.add(listener)
            logger.debug("listener ${listener.name} was registered")
        }

        fun load() {}
    }
}
