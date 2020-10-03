package net.nanai10a.twomeat.core

import java.util.concurrent.CopyOnWriteArrayList

class ListenerLoader {
    companion object {
        val listeners = CopyOnWriteArrayList<Listener>()
        fun register(listener: Listener) {}

        fun load() {}
    }
}
