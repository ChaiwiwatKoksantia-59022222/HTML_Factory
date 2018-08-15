import Factory.Cookbook.Tweet
import Factory.Cookbook.TwitterProfile
import Factory.ManagementCenter
import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.close
import java.io.ByteArrayOutputStream
import java.io.BufferedInputStream
import java.net.URL
import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.close
import java.io.FileOutputStream



var arrs = HashSet<String>()

fun main(args: Array<String>) {
    //print("URL : ")
    //val url_from_input: String = readLine()!!
        //val url_from_input = "https://twitter.com/uloruba72"

    //val url_from_input = "https://twitter.com/RisnorWolf/status/953149858730266624"
    //val url_from_input = "https://twitter.com/PokemonCompany/status/1017003294911152128"
    val url_from_input = ""

    var arr = ConvertArr().load()

    var i = 1
    arr.forEach {
        tweet("TEST",i.toString(),"Image"+i.toString(),it)
        i+=1
    }




    //tweet(url_from_input)
        //twitter_profile(url_from_input)



}

fun tw(url :String){
    val tweet = ManagementCenter().requestTweetFactory(url)
    println("PASS")
}

fun tweet(folder: String,count :String,name: String,url_from_input: String){
    try {
        val tweet :Tweet = ManagementCenter().requestTweetFactory(url_from_input)


        /*
        println("Username : " + tweet.username)
        println("Description : " + tweet.desciption)
        println("Type : " + tweet.type)
        println("URL : " + tweet.url)
        println("Protect : " + tweet.isProtect)

        println("Hashtag : ")
        for (i in tweet.hashtagSlot){
            println(i)
        }

        println("Other Name : ")
        for (i in tweet.nameSlot){
            println(i)
        }

        println("Image : ")
        for (i in tweet.imageSlot){
            println(i)
        }

        println("Video : ")
        for (i in tweet.videoSlot){
            println(i)
        }
        println("Video Height : " + tweet.videoHeight)
        println("Video Weight : " + tweet.videoWeight)
        println("Video Aspect : " + tweet.videoAspect)

        println("Date And Time : "+ tweet.dateTime)
        println("Comments : " + tweet.commentsCounter)
        println("Likes : " + tweet.likesCounter)
        println("Retweets : " + tweet.retweetsCounter)

        println()

*/



        var i = 1
        tweet.imageSlot.forEach {
            loadImage(folder,name+"_"+i.toString(),it)
            //println(it)
            //arrs.add(it)
            i+=1
        }


        //println(arrs)




        println("PASS $count")
    } catch (e :Exception){
        println("Error_LOAD")
    }

}

fun filter(){


}

fun loadImage(folder :String,name :String,u: String){
    try {
        val url = URL(u)
        val sin = BufferedInputStream(url.openStream())
        val out = ByteArrayOutputStream()
        val buf = ByteArray(1024)
        var n = sin.read(buf)
        while (-1 != n) {

            out.write(buf, 0, n)
            n = sin.read(buf)
        }
        out.close()
        sin.close()
        val response = out.toByteArray()
        val fos = FileOutputStream("/Users/melondev/Desktop/$folder/$name.png")
        fos.write(response)
        fos.close()
    } catch (e :Exception){
        println("Error_WRITE")
    }
}

fun twitter_profile(url_from_input :String){

    val twitterProfile: TwitterProfile = ManagementCenter().requestTwitterProfileFactory(url_from_input)

    println("Username : " + twitterProfile.userName)
    println("Protect : "+twitterProfile.isProtect)
    println("Location : "+twitterProfile.location)
    println("Join Date : "+twitterProfile.joinDate)
    println("Join Date Detail : "+twitterProfile.joinDateDetail)
    println("Born Date : "+twitterProfile.bornData)
    println("Color : " + twitterProfile.themeColor)
    println("Cover Image : " + twitterProfile.coverImage)
    println("Profile Image : " + twitterProfile.profileImage)
    println("Name Status : " + twitterProfile.statusName)
    println("Tweets : " + twitterProfile.tweetsCounter)
    println("Following : " + twitterProfile.followingCounter)
    println("Followers : " + twitterProfile.followersCounter)
    println("Likes : " + twitterProfile.likesCounter)
    println("External Link : " + twitterProfile.externalLink)
    println("Bio : " + twitterProfile.bio)

}

