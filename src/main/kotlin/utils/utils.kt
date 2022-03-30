package utils

object utils {

    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    @JvmStatic
    fun validpriority(priority: Int) : Boolean{
        return priority in 1..5
    }

    @JvmStatic
    fun isValidCategory(categoryin: String): Boolean {
        var category = categoryin.lowercase()
        return !(category != "home" && category != "reminder" && category != "behaviour" && category != "music" && category != "art")
    }

    @JvmStatic
    fun isValidStatus(status: String): Boolean{
        return !(status != "Done" && status!="Todo")
    }


}