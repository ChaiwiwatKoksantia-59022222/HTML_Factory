import Factory.ManagementCenter

fun main(args: Array<String>) {

    print("URL : ")
    val url_from_input: String = readLine()!!

    val tweet = ManagementCenter().requestTweetFactory(url_from_input)
    print(tweet.username)


}
