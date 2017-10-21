package Factory

import Factory.Cookbook.Tweet
import Factory.Cookbook.TwitterProfile
import Factory.Production.TweetFactory
import Factory.Production.TwitterProfileFactory
import Factory.Production.URLFactory

class ManagementCenter {

    fun requestTweetFactory(URL :String) :Tweet{
        val urlProduct :ArrayList<String> = URLFactory(URL).getResource()
        val tweetProduct = TweetFactory(urlProduct)
        tweetProduct.startTweetFactory()
        return tweetProduct.cookbook
    }

    fun requestTwitterProfileFactory(URL: String) :TwitterProfile{
        val urlProduct :ArrayList<String> = URLFactory(URL).getResource()
        val twitterProfileProduct = TwitterProfileFactory(urlProduct)
        twitterProfileProduct.startTwitterProfileFactory()
        return twitterProfileProduct.cookbook
    }

}