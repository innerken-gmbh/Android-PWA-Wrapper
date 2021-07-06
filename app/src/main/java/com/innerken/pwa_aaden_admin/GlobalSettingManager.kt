package com.innerken.pwa_aaden_admin

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GlobalSettingManager @Inject constructor(@ApplicationContext context: Context) {

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        private const val IP = "IP"
    }

    private fun getConfig(key: String): String? {
        return prefs.getString(key, null)
    }

    fun getIP(): String {
        return prefs.getString(IP, null) ?: "192.168.168.1"
    }

    fun getEndPoint(): String {
        return prefs.getString("endPoint", null) ?: "Admin"
    }

    fun getBaseUrl(): String {
        return "http://" + getIP() + "/" + getEndPoint() + "?Base=" + getIP()
    }

    fun getPassword(): String {
        return prefs.getString("password", "") ?: ""
    }


}