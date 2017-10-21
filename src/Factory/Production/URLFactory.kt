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
            var line = convert(inputLine)
            if (line.contains("&amp;")) {
                line = cutAndRepairString(line, "&amp;", "&")
            }
            array.add(line)
        }
        this.resource = array

    }

    private fun cutAndRepairString(source: String, cutString: String, repairString: String): String {
        var pass = false
        var line: String = source
        while (!pass) {
            if (line.contains(cutString)){
                val x = source.indexOf(cutString)
                val lineX = source.substring(0, x)
                val lineY = source.substring(x + cutString.length)

                line = lineX + repairString + lineY
            }
            else {
                pass = true
            }
        }
        return line
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