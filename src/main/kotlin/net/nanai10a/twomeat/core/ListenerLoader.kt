package net.nanai10a.twomeat.core

class ListenerLoader {
    private var _loadedListeners: List<Listener>? = null
    val loadedListeners
        get() = _loadedListeners

    fun load() {}
}
