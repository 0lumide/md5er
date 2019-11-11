package co.mide.md5er

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import android.content.*


class MD5Hash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Use toString() to get String in case of Spannable
        val input = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()

        val hash = input.md5()

        val sharedPreference : SharedPreferences
                = getSharedPreferences(SharedPrefConstants.SHARED_PREF_STRING, Context.MODE_PRIVATE)

        val shouldCopy = sharedPreference.getBoolean(SharedPrefConstants.SHOULD_COPY_PREF, false)

        if(shouldCopy) {
            //copy hash to clipboard
            copyToClipboard(hash)
            Toast.makeText(applicationContext, R.string.hash_copied, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, hash, Toast.LENGTH_LONG).show()
        }
        finish()
    }


    private fun copyToClipboard(text : String?) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("md5 hash", text)
        clipboard.setPrimaryClip(clip)
    }
}


/*
 * author: Phan Van Linh
 * https://stackoverflow.com/a/56423552/2057884
 */
fun String.md5(): String? {
    try {
        val md = MessageDigest.getInstance("MD5")
        val array = md.digest(this.toByteArray())
        val sb = StringBuffer()
        for (i in array.indices) {
            sb.append(Integer.toHexString(array[i].toInt() and 0xFF or 0x100).substring(1, 3))
        }
        return sb.toString()
    } catch (e: java.security.NoSuchAlgorithmException) {
    } catch (ex: UnsupportedEncodingException) {
    }
    return null
}