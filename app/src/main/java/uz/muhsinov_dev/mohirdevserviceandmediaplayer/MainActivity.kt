package uz.muhsinov_dev.mohirdevserviceandmediaplayer

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.muhsinov_dev.mohirdevserviceandmediaplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener {
            startService(START)
        }
        binding.pauze.setOnClickListener {
            startService(PAUSE)
        }
        binding.stop.setOnClickListener {
            startService(STOP)
        }

    }


    fun startService(code: Int) {
        Intent(this, ForegroundService::class.java).apply {

            putExtra("code",code)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                startForegroundService(this)
            } else {
                Intent(this@MainActivity, BackgroundService::class.java).apply {
                    startService(this)
                }
            }

        }

    }
    companion object {
        val START = 0
        val PAUSE = 1
        val STOP = 2
    }

}