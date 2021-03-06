package Factory.Cookbook

class Tweet {

    var imageSlot: ArrayList<String> = ArrayList()
        get() = field
    var nameSlot: ArrayList<String> = ArrayList()
        get() = field
    var hashtagSlot: ArrayList<String> = ArrayList()
        get() = field
    var videoSlot: ArrayList<String> = ArrayList()
        get() = field

    var username: String = ""
        get() = field
        set(value) {
            field = value
        }
    var desciption: String = ""
        get() = field
        set(value) {
            field = value
        }
    var url: String = ""
        get() = field
        set(value) {
            field = value
        }
    var videoHeight: String = String()
        get() = field
        set(value) {
            field = value
        }
    var videoWeight: String = String()
        get() = field
        set(value) {
            field = value
        }
    var type: String = ""
        get() = field
        set(value) {
            field = value
        }
    var videoAspect: String = ""
        get() = field
        set(value) {
            field = value
        }

    var isProtect: Boolean = false
        get() = field
        set(value) {
            field = value
        }

    var dateTime: String = ""
        get() = field
        set(value) {
            field = value
        }

    var likesCounter: String = ""
        get() = field
        set(value) {
            field = value
        }

    var retweetsCounter: String = ""
        get() = field
        set(value) {
            field = value
        }

    var commentsCounter: String = ""
        get() = field
        set(value) {
            field = value
        }

    fun addImageSlot(word: String) {
        this.imageSlot.add(word)
    }

    fun addNameSlot(word: String) {
        this.nameSlot.add(word)
    }

    fun addHashtagSlot(word: String) {
        this.hashtagSlot.add(word)
    }

    fun addVideoSlot(word: String) {
        this.videoSlot.add(word)
    }

}