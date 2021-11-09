package com.example.saytahmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.schedule

class AnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        Timer().schedule(4500) {
            val intent : Intent = Intent(this@AnimationActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}