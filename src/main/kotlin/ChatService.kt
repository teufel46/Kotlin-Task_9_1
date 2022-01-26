class ChatService {
    val chats = hashMapOf<List<Int>, Chat>()

    override fun toString(): String {
        var result : String = "Состояние чатов:\n"
        chats.forEach { (key, value) ->
            result += "$key $value \n"
        }
        return result
    }

    fun addMessage(userIds : List<Int>, message : Message) : Chat {
        return chats.getOrPut(userIds) {
            Chat()
        }.apply {
            messages.add(message)
        }
    }

    fun getMessagesFromChat(userIds : List<Int>) : List<Message> {
        val chat = chats.filter { entry ->  entry.key.containsAll(userIds) }.values.first().messages
        chat.forEach { message -> message.isRead = true }
        return chat
    }

    fun readChat(userIds : List<Int>) : Boolean{
        chats.filter { entry ->  entry.key.containsAll(userIds) }.values.first().readMessages()
        return true
    }

    fun deleteChat(userIds : List<Int>) : Boolean{
        return chats.remove(userIds) != null
    }

    fun getChats(userId : Int) : List<Chat>{
        return chats.filter { entry ->  entry.key.contains(userId)}.values.toList()
    }

    fun getChat(userIds: List<Int>): List<Chat> {
        return chats.filter { entry -> entry.key.containsAll(userIds) }.values.toList()
    }

    fun getUnreadChatsCount(userId : Int) : Int {
        return chats.filter { entry ->  entry.key.contains(userId)}.values.filter { !it.unreadMessagesB() }.count()
    }
}