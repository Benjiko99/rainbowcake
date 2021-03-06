package co.zsmb.rainbowcake.internal.logging

import co.zsmb.rainbowcake.config.isProd
import co.zsmb.rainbowcake.internal.config.RainbowCakeConfiguration
import java.io.PrintWriter
import java.io.StringWriter

@Suppress("unused")
internal fun log(tag: String, message: String) {
    if (RainbowCakeConfiguration.isProd) {
        return
    }
    RainbowCakeConfiguration.logger.log(tag, message)
}

internal inline fun <reified T> T.log(tag: String, e: Throwable) {
    if (RainbowCakeConfiguration.isProd) {
        return
    }
    log(tag, getStacktraceString(e))
}

private fun getStacktraceString(e: Throwable): String {
    // Implementation from Timber
    val sw = StringWriter(256)
    val pw = PrintWriter(sw, false)
    e.printStackTrace(pw)
    pw.flush()
    return sw.toString()
}
