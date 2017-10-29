package Factory.Production

import Factory.Cookbook.TwitterProfile

class TwitterProfileFactory {

    var cookbook = TwitterProfile()
    var stringLine: String = String()
    var resource: ArrayList<String>
    var currentLine: Int = 0

    var urls: String = String()
    var userCheck = false

    var counts: Int = 0
    var value_array: ArrayList<String> = ArrayList()

    constructor(resource: ArrayList<String>) {
        this.resource = resource
    }

    fun startTwitterProfileFactory() {
        for (i in resource) {
            factoryEngine(i)
            currentLine++
            //println(i)
        }
    }

    private fun factoryEngine(string: String) {
        this.stringLine = string
        protectCheck()
        productProcessing()
    }

    private fun productProcessing() {


        if (stringLine.contains("canonical")) {
            findURL()
            usernameProductLine() //PASS
        }
        if (stringLine.contains("ProfileAvatar-image")) {
            profileImageAndTitleProductLine()
        }
        if (stringLine.contains("<style id=")) {
            themeProductLine() //PASS
        }
        if (stringLine.contains("ProfileCanopy-headerBg")) {
            coverImageProductLine()
        }
        if (stringLine.contains("ProfileHeaderCard-bio u-dir")) {
            //println("BIO")
            //bioProductLine()

            bioProcess()
        }
        if (stringLine.contains("ProfileHeaderCard-locationText u-dir")) {
            locationProductLine() //PASS
        }
        if (stringLine.contains("ProfileHeaderCard-joinDateText js-tooltip u-dir")) {
            joinDateProductLine() //PASS
        }
        if (stringLine.contains("ProfileHeaderCard-birthdateText u-dir") && stringLine.contains("js-tooltip")) {
            bornDateProductLine() //PASS
        }
        if (stringLine.contains("ProfileNav-value")) {
            //println("ER" + countCrop())
            value_array.add(countCrop())
        }
        if (stringLine.contains("ProfileHeaderCard-urlText u-dir")) {
            externalLinkProductLine()
        }
        if (stringLine.contains("AppContent-main content-main u-cf")) {
            for (i in value_array) {
                if (i.contentEquals(value_array.get(0))) {
                    cookbook.tweetsCounter = value_array.get(0)
                } else if (i.contentEquals(value_array.get(1))) {
                    cookbook.followingCounter = value_array.get(1)
                } else if (i.contentEquals(value_array.get(2))) {
                    cookbook.followersCounter = value_array.get(2)
                } else if (i.contentEquals(value_array.get(3))) {
                    cookbook.likesCounter = value_array.get(3)
                }
            }
        }

    }

    //EXTERNAL LINK
    private fun externalLinkProductLine() {
        if (stringLine.contains("title=")) {
            val x = stringLine.indexOf("title=") + 7
            val line = stringLine.substring(x)
            val y = line.indexOf(">") - 1
            cookbook.externalLink = line.substring(0, y)
        }
    }

    //Profile Value
    private fun countCrop(): String {
        checkStringReadyForUse("</span>")
        val initial = stringLine.indexOf(">") + 1
        val line = stringLine.substring(initial)
        val end = line.indexOf("<")
        counts + 1
        return line.substring(0, end)
    }

    // private fun valusCut() :String{

    //}

    //PROFILE IMAGE AND TITLE
    private fun profileImageAndTitleProductLine() {
        val initial = stringLine.indexOf("src=") + 5
        val line = stringLine.substring(initial)
        val end = line.indexOf(" ") - 1
        cookbook.profileImage = line.substring(0, end)

        val x = stringLine.indexOf("alt=") + 5
        val lines = stringLine.substring(x)
        val y = lines.indexOf(">") - 1
        cookbook.statusName = lines.substring(0, y)
    }

    //COVER IMAGE
    private fun coverImageProductLine() {
        checkStringReadyForUse("</div>")
        if (stringLine.contains("src=")) {
            val initial = stringLine.indexOf("src=") + 5
            val line = stringLine.substring(initial)
            val end = line.indexOf(" ") - 1
            cookbook.coverImage = line.substring(0, end)
        }
    }

    //USERNAME
    private fun usernameProductLine() {
        if (!userCheck) {
            val u = "https://twitter.com/"
            val c = u.length + 1
            cookbook.userName = urls.substring(u.length)
            userCheck = true
        }
    }

    private fun findURL() {
        val x = stringLine.indexOf("href=") + 6
        val y = stringLine.indexOf(">") - 1
        urls = stringLine.substring(x, y)

    }

    //THEME
    private fun themeProductLine() {
        checkStringReadyForUse("}")
        val x = stringLine.indexOf("color: ") + 7
        val line = stringLine.substring(x)
        cookbook.themeColor = line.substring(0, 7)

    }

    //JOIN DATE
    private fun joinDateProductLine() {
        checkStringReadyForUse("</span>")
        val x = stringLine.indexOf("title=") + 7
        val y = stringLine.indexOf(">") - 1
        cookbook.joinDateDetail = stringLine.substring(x, y)
        var line = removeSpare(cropStringWithFunc(stringLine))
        cookbook.joinDate = line.substring(7)
    }

    //BORN DATE
    private fun bornDateProductLine() {
        checkStringReadyForUse("</span>")
        val n = stringLine.indexOf("title=")
        val s = stringLine.substring(n)

        cookbook.bornData = removeSpare(cropStringWithSimple(s))
    }

    //LOCATION
    private fun locationProductLine() {
        checkStringReadyForUse("</span>")
        if (stringLine.contains("<a href=")) {
            val x: Int = stringLine.indexOf("<a href=")
            val line: String = stringLine.substring(x)
            cookbook.location = removeSpare(cropStringWithFunc(line))
        } else {
            cookbook.location = removeSpare(cropStringWithFunc(stringLine))
        }
    }

    private fun cropStringWithFunc(string: String): String {
        //println("String : $string")
        val near: Int = string.indexOf("<") + 1
        val s: String = string.substring(near)
        val init: Int = s.indexOf(">") + 1
        val end: Int = s.indexOf("<")
        return s.substring(init, end)
    }

    private fun cropStringWithSimple(string: String): String {
        val init: Int = string.indexOf(">") + 1
        val end: Int = string.indexOf("<")
        return string.substring(init, end)
    }

    private fun removeSpare(string: String): String {
        var line: String = ""
        for (i in string.indices) {
            val char: Char = string.get(i)
            if (!char.equals(' ')) {
                line = string.substring(i)
                if (line.contains("  ")) {
                    val x: Int = line.indexOf("  ")
                    line.substring(0, x)
                    break
                } else {
                    break
                }
            }
        }
        return line
    }


    private fun checkStringReadyForUse(end: String) {
        var pass = false

        var i = 0
        while (!pass) {
            i++
            if (!stringLine.contains(end)) {
                stringLine = stringLine + resource.get(currentLine + i)
            } else {
                pass = true
            }
        }
        //println(stringLine)
    }

    private fun checkStringReadyForUseWithReturn(s: String, end: String): String {
        var pass = false
        var string: String = s

        var i = 0
        while (!pass) {
            i++
            if (!string.contains(end)) {
                string = string + resource.get(currentLine + i)
            } else {
                pass = true
            }
        }
        return s
        //println(stringLine)
    }


    //BIO
    private fun bioProcess() {
        checkStringReadyForUse("</p>")
        //println(stringLine)
        checkS()
    }

    private fun checkS() {
        var pass = false
        var count = 1
        var line = stringLine
        var initial = -1
        var u = false
        var array: ArrayList<String> = ArrayList()
        while (!pass) {
            var a = line[count]
            //println(a+" : "+count)
            if (a.equals('>')) {
                //println("Point : " + line.get(count))
                initial = count + 1
                u = true
            }
            if (a.equals('<') && u == true){
                //println("END")
                var li = line.substring(initial,count)
                //println(line.substring(initial))
                array.add(li)
                u = false
            }
            if (count == line.length - 1){
                pass = true
            }
            count++
        }
        var str = ""
        for (s in array){
            if (s.length > 0){
                str = str + s
            }
        }
        //println(str)
        cookbook.bio = str
    }


/*
    private fun bioCheckSpareAndCrop() {
        var array: ArrayList<String> = ArrayList()

        var pass = false

        var line: String = stringLine

        while (!pass) {
            if (line.contains("<s>@</s><b>")) {
                val initial: Int = line.indexOf("<s>@</s><b>")

                val part: String = line.substring(0, initial)
                //val e_part :Int = part.indexOf("<a href=")
                if (part.contains("<p class=")) {
                    val x = part.indexOf(">") + 1
                    val y = part.indexOf("<a href=")
                    array.add(part.substring(x, y))
                } else if (part.contains("<a href=")) {
                    val y = part.indexOf("<a href=")
                    array.add(part.substring(0, y))
                } else if (part.contains("</p>")) {
                    val y = part.indexOf("</p>")
                    array.add(part.substring(0, y))
                }
                //array.add(part)

                line = line.substring(initial + 11)
                val end: Int = line.indexOf('<')
                var name: String = line.substring(0, end);
                var cut = name.length + 8

                array.add("@" + name)
                cookbook.addbioNameSlot(name)
                line = line.substring(cut)
            }
            else if (line.contains("twitter-timeline-link") && line.contains("ProfileHeaderCard-bio u-dir")){

            }

            else {
                if (line.contains("<p class=")) {
                    val x = line.indexOf(">") + 1
                    val y = line.indexOf("</p>")
                    array.add(line.substring(x, y))
                } else if (line.contains("</p>")) {
                    val y = line.indexOf("</p>")
                    array.add(line.substring(0, y))
                }
                pass = true
            }
        }
        for (i in array) {
            println(i)
        }


    }
*/

    /*
        private fun checkAccountFromBio() {
            if (stringLine.contains("tweet-url twitter-atreply pretty-link")) {
                var pass = false
                var line = stringLine
                while (pass == false) {
                    if (line.contains("<s>@</s><b>")) {
                        val initial: Int = line.indexOf("<s>@</s><b>") + 11
                        line = line.substring(initial)
                        val end: Int = line.indexOf('<')
                        cookbook.addbioNameSlot(line.substring(0, end))
                    } else {
                        pass = true
                    }
                }
            }
        }
    */
    private fun protectCheck() {
        cookbook.isProtect = stringLine.contains("ProtectedTimeline-heading")
    }


}