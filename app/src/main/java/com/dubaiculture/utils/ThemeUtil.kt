package com.dubaiculture.utils

import androidx.appcompat.app.AppCompatDelegate


object ThemeUtil {
    const val THEME_LIGHT = "light"
    const val THEME_DARK = "dark"
    const val THEME_BATTERY = "battery"
    const val THEME_SYSTEM = "system"
    fun applyTheme(theme: String) {
        when (theme) {
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            THEME_BATTERY -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            THEME_SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}