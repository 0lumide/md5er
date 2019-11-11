package co.mide.md5er

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import java.io.UnsupportedEncodingException
import java.security.MessageDigest


class MD5Hash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val input = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()

        Toast.makeText(applicationContext, input.md5(), Toast.LENGTH_LONG).show()

        finish()
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