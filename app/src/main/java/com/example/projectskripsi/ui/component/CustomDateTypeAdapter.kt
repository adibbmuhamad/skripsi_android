package com.example.projectskripsi.ui.component

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.text.SimpleDateFormat
import java.util.*

class CustomDateTypeAdapter : TypeAdapter<Date?>() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun write(out: JsonWriter, value: Date?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(dateFormat.format(value))
        }
    }

    override fun read(reader: JsonReader): Date? {
        return try {
            if (reader.peek() == com.google.gson.stream.JsonToken.NULL) {
                reader.nextNull()
                null
            } else {
                val dateString = reader.nextString()
                dateFormat.parse(dateString)
            }
        } catch (e: Exception) {
            null
        }
    }
}