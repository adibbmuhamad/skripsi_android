package com.example.projectskripsi.ui.component

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Locale

class CustomTimeTypeAdapter : TypeAdapter<Time?>() {
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    override fun write(out: JsonWriter, value: Time?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.value(timeFormat.format(value))
        }
    }

    override fun read(reader: JsonReader): Time? {
        return try {
            if (reader.peek() == com.google.gson.stream.JsonToken.NULL) {
                reader.nextNull()
                null
            } else {
                val timeString = reader.nextString()
                Time(timeFormat.parse(timeString).time)
            }
        } catch (e: Exception) {
            null
        }
    }
}