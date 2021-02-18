package com.bytcore.cmndroid.ui

enum class MessageType {
    SNACK_BAR,
    TOAST,
    INFO_DIALOG,
    CONFIRMATION_DIALOG,
    ERROR_DIALOG,
    NOTIFICATION
}
class Message(val type: MessageType, val text: String) {
    companion object {
        fun asSnackBar(text: String): Message {
            return Message(MessageType.SNACK_BAR, text)
        }
    }
}
