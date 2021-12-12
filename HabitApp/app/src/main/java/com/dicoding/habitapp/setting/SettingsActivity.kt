package com.dicoding.habitapp.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.dicoding.habitapp.R
import com.dicoding.habitapp.utils.DarkMode
import java.util.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            //TODO 11 : Update theme based on value in ListPreference
            findPreference<ListPreference>(getString(R.string.pref_key_dark))?.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, newValue ->
                    newValue?.let {
                        val modeSelected =
                            when((it as String).toUpperCase(Locale.ROOT)) {
                                DarkMode.ON.name -> DarkMode.ON
                                DarkMode.OFF.name -> DarkMode.OFF
                                else -> DarkMode.FOLLOW_SYSTEM
                            }
                        updateTheme(modeSelected.value)
                    }
                    true
                }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}