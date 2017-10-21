package Factory.Production

import Factory.Cookbook.Tweet
import java.util.regex.Matcher
import java.util.regex.Pattern

class TweetFactory {

    var cookbook = Tweet()
        get() = field

    var stringLine: String = String()

    var count: Int = 0
    var datePass :Boolean = false
    var CounterPass :Int = 0
    val resource: ArrayList<String>

    constructor(resource: ArrayList<String>) {
        this.resource = resource
    }

    fun startTweetFactory() {
        for (i in resource) {
            factoryEngine(i)
            count++
        }
    }

    private fun factoryEngine(string: String) {
        this.stringLine = string
        protectCheck()
        lookAtLine()
    }

    private fun lookAtLine() {
        if (stringLine.contains("og:")) {
            productionProcessing()
        }
        if (emptyCheck(cookbook.videoAspect) && !emptyCheck(cookbook.videoWeight) && !emptyCheck(cookbook.videoHeight)) {
            videoInformationProcessing()
        }
        if (stringLine.contains("tweet-timestamp js-permalink js-nav js-tooltip") && stringLine.contains(cookbook.username) && !datePass) {
            dateTimeProductLine()
        }
        if (stringLine.contains("ProfileTweet-action--reply u-hiddenVisually") && CounterPass < 3){
            cropCounterProductLine(1)
        }
        if (stringLine.contains("ProfileTweet-action--favorite u-hiddenVisually") && CounterPass < 3){
            cropCounterProductLine(2)
        }
        if (stringLine.contains("ProfileTweet-action--retweet u-hiddenVisually") && CounterPass < 3){
            cropCounterProductLine(3)
        }
    }

    private fun cropCounterProductLine(id: Int){
        checkStringReadyForUse("</span>")
        val s = "data-tweet-stat-count="
        if (stringLine.contains("data-aria-label-part")){
            val x = stringLine.indexOf(s) + s.length + 1
            val line = stringLine.substring(x)
            val y = line.indexOf(">") - 1
            val result = line.substring(0,y)
            when (id){
                1 -> cookbook.commentsCounter = result
                2 -> cookbook.likesCounter = result
                3 -> cookbook.retweetsCounter = result
            }
            CounterPass++
        }
    }

    private fun checkStringReadyForUse(end: String) {
        var pass = false

        var i = 0
        while (!pass) {
            i++
            if (!stringLine.contains(end)) {
                stringLine = stringLine + resource.get(count + i)
            } else {
                pass = true
            }
        }
        //println(stringLine)
    }

    private fun dateTimeProductLine(){
        val x = stringLine.indexOf("title=")+7
        val line = stringLine.substring(x)
        val y = line.indexOf("  data-conversation-id=") - 1
        cookbook.dateTime = line.substring(0,y)
        datePass = true
    }

    private fun convertToOriginalImage(url: String): String {
        val initial = "https://"
        var end = url.substring(initial.length)
        if (end.contains(":")) {
            val stop = end.indexOf(":")
            end = end.substring(0, stop)
        }
        return initial + end + ":orig"
    }

    private fun videoInformationProcessing() {
        val height: Int = Integer.parseInt(cookbook.videoHeight)
        val weight: Int = Integer.parseInt(cookbook.videoWeight)

        if (height > weight) {
            cookbook.videoAspect = "portrait"
        } else if (weight > height) {
            cookbook.videoAspect = "Landscape"
        } else {
            cookbook.videoAspect = "square"
        }
    }

    private fun productionProcessing() {
        if (stringLine.contains("og:type") && !stringLine.contains("og:video:type")) {
            cookbook.type = stringCrop()
        } else if (stringLine.contains("og:url") && !stringLine.contains("og:video:url") && !stringLine.contains("og:video:secure_url")) {
            cookbook.username = usernameCrop()
            cookbook.url = stringCrop()
        } else if (stringLine.contains("og:description")) {
            descriptionProcessing()
        }

        if (stringLine.contains("og:image") && !stringLine.contains("og:image:user_generated") && !stringLine.contains("/profile_images/")) {
            cookbook.addImageSlot(convertToOriginalImage(stringCrop()))
        }
        if (cookbook.type.contentEquals("video")) {
            if (stringLine.contains("og:video:url")) {
                cookbook.addVideoSlot(stringCrop())
            } else if (stringLine.contains("og:video:width")) {
                cookbook.videoWeight = stringCrop()
            } else if (stringLine.contains("og:video:height")) {
                cookbook.videoHeight = stringCrop()
            }
        }

    }

    private fun descriptionProcessing() {
        var initial: Int = -1
        var mantle: Boolean = false

        val description: String = descriptionCrop()

        for (i in description.indices) {
            val character: Char = description.get(i)
            if (character == '@' && !mantle) {
                mantle = true
                initial = i
            } else if (mantle == true && !(character >= 'a' && character <= 'z'
                    || character >= 'A' && character <= 'Z' || character == '_'
                    || character >= '0' && character <= '9')) {
                mantle = false
                cookbook.addNameSlot(description.substring(initial, i))
            }
            if (i == description.length - 1 && mantle) {
                cookbook.addNameSlot(description.substring(initial, i + 1))
                mantle = false
            }
        }

        hashtagProcessing(description)
        cookbook.desciption = description
    }

    private fun hashtagProcessing(description: String) {
        val pattern: Pattern = Pattern.compile("#(\\S+)")
        val mat: Matcher = pattern.matcher(description)

        while (mat.find()) {
            val group: String = mat.group(1)
            if (group.get(group.length - 1) == ')') {
                cookbook.addHashtagSlot("#" + group.substring(0, group.length - 1))
            } else {
                cookbook.addHashtagSlot("#" + group)
            }
        }
    }

    private fun descriptionCrop(): String {
        val index: Int = stringLine.indexOf("content=") + 10
        val end: Int = stringLine.length - 3

        return stringLine.substring(index, end)
    }

    private fun usernameCrop(): String {
        val x = "https://twitter.com/"
        val index: Int = stringLine.indexOf(x) + x.length
        val y = "/status/"
        val end: Int = stringLine.indexOf(y)

        return stringLine.substring(index, end)
    }

    private fun stringCrop(): String {

        val index: Int = stringLine.indexOf("content=") + 9
        val end: Int = stringLine.length - 2
        return stringLine.substring(index, end)

    }

    private fun findCurrentLine(): Int {
        return count
    }

    private fun emptyCheck(string: String): Boolean {
        return string.length == 0
    }

    private fun protectCheck(): Boolean {
        cookbook.isProtect = stringLine.contains("ProtectedTimeline-heading")
        return cookbook.isProtect
    }

}