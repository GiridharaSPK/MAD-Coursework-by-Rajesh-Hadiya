package dev.giridharaspk.retrofitandpaging.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeFormatter {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun convertTimeStampToReadableFormat(inputDateString: String): String {
            val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
            val outputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
            val dateTime = LocalDateTime.parse(inputDateString, inputFormatter)
            return dateTime.format(outputFormatter)
        }
    }

}