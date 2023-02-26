package com.example.currencycalculatorapp.data.source.local.prefs

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.currencycalculatorapp.data.models.dto.ExchangeHistoryDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefsHelper private constructor(context: Context) {

    private var prefs: SharedPreferences

    companion object {
        private const val KEY_SIZE = 256
        private lateinit var instance: PrefsHelper
        private const val MAX_HISTORY_SIZE = 10
        private const val FIRST_POSITION = 0
        private const val PREFS_NAME = "CURRENCY_PREFS"
        private const val EXCHANGE_HISTORY_KEY = "key1"

        fun getInstance(): PrefsHelper {
            if (Companion::instance.isInitialized) {
                return instance
            }

            throw UninitializedPropertyAccessException(
                "Call init(Context) before using this method."
            )
        }

        fun init(context: Context) {
            instance = PrefsHelper(context)
        }

    }

    init {
        val builder = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(KEY_SIZE)
            .build()
        val masterKey = MasterKey.Builder(context)
            .setKeyGenParameterSpec(builder)
            .build()
        prefs = EncryptedSharedPreferences.create(
            context, PREFS_NAME, masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveHistoryItem(model: ExchangeHistoryDto) {
        val gson = Gson()
        val currentHistory = getExchangeHistory()
        if (currentHistory.isEmpty() || currentHistory.size <= MAX_HISTORY_SIZE) {
            currentHistory.add(FIRST_POSITION, model)
        }
        val json = gson.toJson(currentHistory)
        prefs.edit { putString(EXCHANGE_HISTORY_KEY, json) }
    }

    fun getExchangeHistory(): ArrayList<ExchangeHistoryDto> {
        val gson = Gson()
        val json = prefs.getString(EXCHANGE_HISTORY_KEY, null)
        val type = object : TypeToken<ArrayList<ExchangeHistoryDto>>() {}.type
        return if (json == null) ArrayList() else gson.fromJson(json, type)
    }

}