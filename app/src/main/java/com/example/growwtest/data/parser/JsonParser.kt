package com.example.growwtest.data.parser

import java.lang.reflect.Type

/*
 * Created by Sudhanshu Kumar on 11/01/24.
 */

interface JsonParser {

    fun <T> fromJson(json:String, type:Type) : T?

    fun <T> toJson(obj:T, type:Type):String?

}