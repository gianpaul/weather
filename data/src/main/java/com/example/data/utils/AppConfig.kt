package com.example.data.utils

import java.lang.reflect.Field

object AppConfig {

    lateinit var packageName: String

    val API_URL by lazy { getBuildConfigValue<String>("API_URL") }
    val API_KEY by lazy { getBuildConfigValue<String>("API_KEY") }
    val MAPS_API_KEY by lazy { getBuildConfigValue<String>("MAPS_API_KEY") }
    @Suppress("UNCHECKED_CAST", "UseCheckOrError")
    private fun <T> getBuildConfigValue(fieldName: String): T {
        try {
            val clazz = Class.forName("$packageName.BuildConfig")
            val field: Field = clazz.getField(fieldName)
            return field.get(null) as T
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        throw IllegalStateException("Shouldn't happen")
    }
}