package net.nanai10a.twomeat.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.slf4j.LoggerFactory
import java.io.File
import java.io.PrintStream
import java.time.Instant
import java.time.temporal.ChronoUnit


fun main(args: Array<String>) {
    runBlocking {
        val bootedTime = Instant.now()

    var isShutdown = false
    val logFile = File("2Meat-log-${Instant.now().toString().replace(":", "-")}.log")
    logFile.createNewFile()
    val stream = PrintStream(logFile)
    System.setErr(stream)

        launch(Dispatchers.IO) {
            while (!isShutdown)
                stream.flush()
        }

        val logger = LoggerFactory.getLogger("main()")
        logger.info("booted 2Meat ver. 0.1.0-alpha")

        logger.info("initialing...")

        logger.debug("JDA instance building...")
        val jda = JDABuilder
            .createDefault(args[0])
            .build()
        runBlocking {
            var status: JDA.Status = jda.status
            launch {
                while (jda.status == JDA.Status.SHUTDOWN) {
                    if (status == jda.status)
                        continue
                    status = jda.status
                    logger.debug("JDA-instance's status had changed to ${status.name}")
                }
                logger.info("JDA had finished to shutdown")
            }

            jda.awaitReady()

            logger.debug("JDA instance had built.")
            logger.info("JDA (Java Discord API) ver. 4.2.0_207")

            logger.debug("listener-loader init")
            val loader = ListenerLoader()
            logger.debug("listener-loader initialized")

            logger.debug("listener-loader load")
            loader.load()
            logger.debug("listener-loader loaded")

            loader.loadedListeners.let {
                val listeners: List<Listener> = it ?: let {
                    logger.warn("listeners-list is null!")
                    ArrayList()
                }

                var listenersNum = 0
                logger.debug("listeners initialing...")
                listeners.forEach { listener ->
                    listenersNum++
                    logger.debug("listener #$listenersNum [${listener.name}] is initialing...")
                    listener.init()
                    logger.debug("listener ${listener.name} ver. ${listener.version} initialized")
                }
                if (listenersNum == 0) logger.warn("listener was not initialized once!")
                @Suppress("LocalVariableName")
                val listener_ = if (listenersNum <= 1) "listeners" else "listener"
                logger.debug("$listenersNum $listener_ initialized")

                logger.debug("adding $listener_...")
                jda.addEventListener(*listeners.toTypedArray())
                logger.debug("added $listener_")
            }


            logger.info("initialized at ${Instant.now().until(bootedTime, ChronoUnit.SECONDS)}sec")

            logger.info("system started")
        }
        logger.info("started shutdown")
        jda.shutdown()
        logger.info("finished shutdown")

        logger.info("see you next time...")
        isShutdown = true
    }
}
