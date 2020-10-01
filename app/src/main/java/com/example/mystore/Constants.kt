package com.example.mystore

class Constants {

    interface Default {
        companion object {
            const val API_URL = "http://188.225.11.47/"
            const val DEBUG = true
        }
    }

    interface User {
        companion object {
            const val FREE = "free"
            const val FULL = "full"
        }
    }

    interface Settings {
        companion object {
            const val QUIZ_TIME = "quiz_time"
            const val SENT_TIME = "sent_time"
        }
    }
}