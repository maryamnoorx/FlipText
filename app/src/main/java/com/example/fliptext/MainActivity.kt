package com.example.fliptext
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fliptext.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var clipboardManager: ClipboardManager? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding!!.getRoot()
        setContentView(view)
        clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        binding!!.buttonFlip.setOnClickListener(View.OnClickListener {
            val originalText: String = binding!!.editText.getText().toString()
            val flippedText = flipText(originalText)
            binding!!.textViewFlipped.setText(
                """${getString(R.string.flipped_text)}
 
$flippedText"""
            )
            Toast.makeText(this@MainActivity, "Text copied", Toast.LENGTH_SHORT).show()
        })
        binding!!.buttonCopy.setOnClickListener(View.OnClickListener {
            val textToCopy: String = binding!!.textViewFlipped.getText().toString()
                .replace(getString(R.string.flipped_text) + "", "")
            val clip = ClipData.newPlainText("Copied Text", textToCopy)
            clipboardManager!!.setPrimaryClip(clip)
            Toast.makeText(this@MainActivity, "Text copied", Toast.LENGTH_SHORT).show()
        })
        binding!!.buttonCut.setOnClickListener(View.OnClickListener {
            val textToCut: String = binding!!.editText.getText().toString()
            val clip = ClipData.newPlainText("Cut Text", textToCut)
            clipboardManager!!.setPrimaryClip(clip)
            binding!!.editText.setText("")
        })
    }

    private fun flipText(originalText: String): String {
        val flipped = StringBuilder(originalText)
        return flipped.reverse().toString()
    }
}