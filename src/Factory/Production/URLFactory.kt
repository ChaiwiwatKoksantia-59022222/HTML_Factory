package Factory.Production

import org.apache.commons.lang3.StringEscapeUtils
import java.net.*
import java.io.*
import kotlin.collections.ArrayList

class URLFactory {

    private val URL: String
    private var resource: ArrayList<String> = ArrayList()

    constructor(URL: String) {
        this.URL = URL

        genaratorURLFunction()
    }

    private fun genaratorURLFunction() {
        usingBuffer(URL(URL))
    }

    private fun usingBuffer(url: URL) {
        generator(BufferedReader(InputStreamReader(url.openStream())))
    }

    private fun generator(buffer: BufferedReader) {
        var array: ArrayList<String> = ArrayList()
        for (inputLine in buffer.lines()) {
            array.add(convert(inputLine))
        }
        this.resource = array

    }

    fun getResource(): ArrayList<String> {
        return this.resource
    }

    private fun convert(word: String): String {
        return StringEscapeUtils.unescapeHtml4(word)
    }

    fun printResource() {
        for (i in resource) {
            println(i)
        }
    }



}