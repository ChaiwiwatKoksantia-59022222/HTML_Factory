package Factory.Production

import Factory.Cookbook.Tweet
import java.util.regex.Matcher
import java.util.regex.Pattern

class TweetFactory {

    var cookbook = Tweet()
        get() = field

    var stringLine: String = String()

    fun startTweetFactory(string: String) {
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

    private fun videoInformationProcessing(){
        val height :Int = Integer.parseInt(cookbook.videoHeight)
        val weight :Int = Integer.parseInt(cookbook.videoWeight)

        if (height > weight){
            cookbook.videoAspect = "portrait"
        } else if (weight > height){
            cookbook.videoAspect = "Landscape"
        } else{
            cookbook.videoAspect = "square"
        }
    }

    private fun productionProcessing() {
        if (stringLine.contains("og:type") && !stringLine.contains("og:video:type")) {
            cookbook.type = stringCrop()
        } else if (stringLine.contains("og:url") && !stringLine.contains("og:video:url") && !stringLine.contains("og:video:secure_url")) {
            cookbook.username = "@" + usernameCrop()
            cookbook.url = stringCrop()
        } else if (stringLine.contains("og:description")) {
           descriptionProcessing()
        }

        if (stringLine.contains("og:image") && !stringLine.contains("og:image:user_generated") && !stringLine.contains("/profile_images/")) {
            cookbook.addImageSlot(convertToOriginalImage(stringCrop()))
        }
        if (cookbook.type.contentEquals("video")){
            if (stringLine.contains("og:video:url")) {
                //cookbook.setVideo_list(contentCutFunc())
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
        val index = stringLine.indexOf("content=") + 10
        val end = stringLine.length - 3

        return stringLine.substring(index, end)
    }

    private fun usernameCrop(): String {
        val x = "https://twitter.com/"
        val index = stringLine.indexOf(x) + x.length
        val y = "/status/"
        val end = stringLine.indexOf(y)

        return stringLine.substring(index, end)
    }

    private fun stringCrop(): String {

        val index = stringLine.indexOf("content=") + 9
        val end = stringLine.length - 2
        return stringLine.substring(index, end)

    }

    private fun emptyCheck(string: String): Boolean {
        return string.length == 0
    }

    private fun protectCheck(): Boolean {
        cookbook.protect = stringLine.contains("ProtectedTimeline-heading")
        return cookbook.protect
    }

}