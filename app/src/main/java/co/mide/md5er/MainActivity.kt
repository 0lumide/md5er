package co.mide.md5er

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.content.ComponentName



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefString = "37F9CA"
        val sharedPreference : SharedPreferences = getSharedPreferences(sharedPrefString, Context.MODE_PRIVATE)
        val enabledPref = "IS_ENABLED"

        enable_switch.setOnCheckedChangeListener { _, isChecked ->
            test_edit_test.isEnabled = isChecked
            sharedPreference.edit().putBoolean(enabledPref, isChecked).apply()
            toggleProcessText(isChecked)
        }
        enable_switch.isChecked = sharedPreference.getBoolean(enabledPref, true)
    }

    private fun toggleProcessText(shouldEnable: Boolean) {
        val pm = applicationContext.packageManager
        val componentName = ComponentName(
            packageName, "$packageName.MD5Hash"
        )
        val state = when (shouldEnable) {
            true -> PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            false-> PackageManager.COMPONENT_ENABLED_STATE_DISABLED
        }
        pm.setComponentEnabledSetting(
            componentName,
            state,
            PackageManager.DONT_KILL_APP
        )
    }
}
