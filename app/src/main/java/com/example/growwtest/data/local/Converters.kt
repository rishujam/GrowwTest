package com.example.growwtest.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.growwtest.data.model.Person
import com.example.growwtest.data.parser.JsonParser
import com.google.gson.reflect.TypeToken

/*
 * Created by Sudhanshu Kumar on 11/01/24.
 */

@ProvidedTypeConverter
class Converters(
    private val jsonParson: JsonParser
) {

    @TypeConverter
    fun fromPersonJson(json:String): List<Person> {
        return jsonParson.fromJson<ArrayList<Person>>(
            json,
            object: TypeToken<ArrayList<Person>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toPersonJson(meanings:List<Person>) : String {
        return jsonParson.toJson(
            meanings,
            object: TypeToken<ArrayList<Person>>(){}.type
        ) ?:"[]"
    }

}