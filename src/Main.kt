import Factory.Cookbook.TwitterProfile
import Factory.ManagementCenter

fun main(args: Array<String>) {

    print("URL : ")
    val url_from_input: String = readLine()!!
    //val url_from_input = "https://twitter.com/uloruba72"


    //tweet(url_from_input)
    twitter_profile(url_from_input)



}

fun tweet(url_from_input: String){
    val tweet = ManagementCenter().requestTweetFactory(url_from_input)
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

