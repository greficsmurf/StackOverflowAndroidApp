package com.example.stackexchange.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.stackexchange.R
import com.example.stackexchange.base.BaseFragment
import com.example.stackexchange.databinding.FragmentSettingsBinding

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)
        val themeListPreference = ListPreference(context).apply {
            key = getString(R.string.theme_preference_key)
            title = getString(R.string.theme_preference_title)
            entries = context.resources.getStringArray(R.array.theme_list)
            entryValues = context.resources.getStringArray(R.array.theme_list_entry_values)
            summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()

            setDefaultValue(getString(R.string.system_default_theme_entry_value))

            setOnPreferenceChangeListener { _, newValue ->
                when(newValue as String){
                    getString(R.string.light_theme_entry_value) -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    getString(R.string.dark_theme_entry_value) -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    getString(R.string.system_default_theme_entry_value) -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }

                true
            }
        }

        screen.addPreference(themeListPreference)
        preferenceScreen = screen
    }


}