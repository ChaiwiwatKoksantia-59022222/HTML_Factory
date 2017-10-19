package Factory

import Factory.Cookbook.Tweet
import Factory.Production.TweetFactory
import Factory.Production.URLFactory

class ManagementCenter {

    fun requestTweetFactory(URL :String) :Tweet{
        val urlProduct = URLFactory(URL).getResource()
        val tweetProduct = TweetFactory()
        for (line in urlProduct){
            tweetProduct.startTweetFactory(line)
        }
        return tweetProduct.cookbook
    }

}