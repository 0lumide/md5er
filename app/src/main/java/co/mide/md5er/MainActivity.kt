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

        val sharedPreference : SharedPreferences
                = getSharedPreferences(SharedPrefConstants.SHARED_PREF_STRING, Context.MODE_PRIVATE)

        enable_switch.setOnCheckedChangeListener { _, isChecked ->
            test_edit_test.isEnabled = isChecked
            sharedPreference.edit().putBoolean(SharedPrefConstants.ENABLED_PREF, isChecked).apply()
            toggleProcessText(isChecked)
            copy_switch.isEnabled = isChecked
        }

        copy_switch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreference.edit().putBoolean(SharedPrefConstants.SHOULD_COPY_PREF, isChecked).apply()
        }

        enable_switch.isChecked = sharedPreference.getBoolean(SharedPrefConstants.ENABLED_PREF, true)
        copy_switch.isChecked = sharedPreference.getBoolean(SharedPrefConstants.SHOULD_COPY_PREF, false)
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
