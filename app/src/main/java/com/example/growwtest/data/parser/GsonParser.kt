package com.example.growwtest.data.parser

import com.google.gson.Gson
import java.lang.reflect.Type

/*
 * Created by Sudhanshu Kumar on 11/01/24.
 */

class GsonParser(
    private val gson: Gson
) : JsonParser {

    override fun <T> fromJson(json: String, type: Type): T? {
        return gson.fromJson(json,type)
    }

    override fun <T> toJson(obj: T, type: Type): String? {
        return gson.toJson(obj, type)
    }
}