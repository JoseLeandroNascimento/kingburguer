package com.example.kingburguer.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val USER_CREDENTIALS_NAME = "user_credentials"
private val Context.dataStore by preferencesDataStore(name = USER_CREDENTIALS_NAME)

class KingBurguerLocalStorage @Inject constructor( @ApplicationContext context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    val userCredentialsFlow = dataStore.data.map { preferences ->
        mapUserCredential(preferences)
    }
    
    suspend fun fetchInitialUserCredential(): UserCredentials {
        return mapUserCredential(dataStore.data.first().toPreferences())
    }

    suspend fun updateUserCredential(userCredentials: UserCredentials) {

        dataStore.edit { preferences ->
            preferences[EXPIRE_TIMESTAMP] =
                System.currentTimeMillis() + (userCredentials.expiresTimestamp * 1000L)

            preferences[ACCESS_TOKEN] = userCredentials.accessToken
            preferences[REFRESH_TOKEN] = userCredentials.refreshToken
            preferences[TOKEN_TYPE] = userCredentials.tokenType
        }

    }

    private fun mapUserCredential(preferences: Preferences): UserCredentials {
        val expires = preferences[EXPIRE_TIMESTAMP] ?: 0
        val accessToken = preferences[ACCESS_TOKEN] ?: ""
        val refreshToken = preferences[REFRESH_TOKEN] ?: ""
        val tokenType = preferences[TOKEN_TYPE] ?: ""

        return UserCredentials(
            accessToken = accessToken,
            expiresTimestamp = expires,
            tokenType = tokenType,
            refreshToken = refreshToken
        )
    }

    companion object {

        val EXPIRE_TIMESTAMP = longPreferencesKey("expires_timestamp")
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val TOKEN_TYPE = stringPreferencesKey("token_type")

    }
}