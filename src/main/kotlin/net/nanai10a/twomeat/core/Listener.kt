package net.nanai10a.twomeat.core

import net.dv8tion.jda.api.hooks.ListenerAdapter

abstract class Listener : ListenerAdapter() {
    abstract val version: String
    abstract val name: String

    abstract fun init()
}
