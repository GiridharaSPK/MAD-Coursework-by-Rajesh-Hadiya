package dev.giridharaspk.clickcounterxml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.giridharaspk.clickcounterxml.databinding.ActivityMainBinding
import dev.giridharaspk.clickcounterxml.R

class MainActivity : AppCompatActivity() {
    private var clickCount = 0
    private val activityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        setClickListeners()
    }

    private fun setClickListeners() {
        activityMainBinding.button.setOnClickListener {
            clickCount++
            updateCount()
        }
    }

    private fun updateCount() {
        activityMainBinding.tvClickCounter.text = getString(R.string.click_count) + " $clickCount"
    }

    override fun onStop() {
        super.onStop()
        clickCount = 0
        updateCount()
    }
}