package com.erdin.connectin

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class FormatDate {

    companion object {
        fun dateFormat(dateJson: String?): String? {
            var formattedDate : String?

            val sourceFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            val destFormat =
                SimpleDateFormat("MMM d, yyyy hh:mm:ss a") //here 'a' for AM/PM

            var date: Date? = null
            try {
                date = sourceFormat.parse(dateJson)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            formattedDate = destFormat.format(date)

            return formattedDate
        }


    }
}