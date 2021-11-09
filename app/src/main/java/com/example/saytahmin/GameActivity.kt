package com.example.saytahmin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.saytahmin.databinding.ActivityGameBinding
import com.example.saytahmin.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_game.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import java.util.*
import kotlin.concurrent.schedule
import kotlin.random.Random
import kotlin.reflect.KParameter

class GameActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGameBinding
    var remainder : Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        //Describing ViewBinding
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = intent
        var min = intent.getStringExtra("minimumRange")
        var max = intent.getStringExtra("maximumRange")

        val randomlyChoosed = (min!!.toInt()..max!!.toInt()).random()
        binding.resultText.text = "Ready? Let's give it a shot!"
        var entry = binding.inputGuess.text

        binding.btnGuessApply.setOnClickListener {
            if (entry.toString() == "") {
                Toast.makeText(this,"You have to input an entry to guess pal!.",Toast.LENGTH_SHORT).show()
            } else {
                if (entry.toString().toInt() < randomlyChoosed) {
                    binding.resultText.text = "Increase it!"
                    binding.inputGuess.text.clear()
                    remainder -= 1
                    Toast.makeText(this, " $remainder Guesses left!", Toast.LENGTH_SHORT).show()
                    if (remainder == 0) {
                        markButtonDisable(binding.btnGuessApply)
                        hideKeyboard(view)
                        binding.resultText.text = "You Lost :( Answer was $randomlyChoosed"
                        Toast.makeText(
                            this,
                            "You will be navigated to main screen in 5 seconds.",
                            Toast.LENGTH_SHORT
                        ).show()
                        Timer().schedule(5000) {
                            val intent: Intent = Intent(this@GameActivity, AnimationActivity::class.java)
                            startActivity(intent)
                        }
                    }
                } else if (entry.toString().toInt() > randomlyChoosed) {
                    binding.resultText.text = "Decrease it!"
                    binding.inputGuess.text.clear()
                    remainder -= 1
                    Toast.makeText(this, " $remainder Guesses left!", Toast.LENGTH_SHORT).show()
                    if (remainder == 0) {
                        markButtonDisable(binding.btnGuessApply)
                        Toast.makeText(this, "You will be navigated to main screen in 5 seconds.", Toast.LENGTH_SHORT).show()
                        binding.resultText.text = "You lost :( Answer was: $randomlyChoosed"
                        hideKeyboard(view)
                        Timer().schedule(5000) {
                            val intent: Intent = Intent(this@GameActivity, AnimationActivity::class.java)
                            startActivity(intent)
                        }
                    }
                } else {
                    binding.resultText.text = "YOU WON!"
                    Toast.makeText(this, "You will be navigated to main screen in 5 seconds.", Toast.LENGTH_SHORT).show()
                    hideKeyboard(view)
                    //I used Konfetti View Library to celebrate the user.
                    konfettiView.build()
                        .addColors(Color.YELLOW, Color.GREEN)
                        .setDirection(0.0, 359.0)
                        .setSpeed(5f, 15f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(400L)
                        .addShapes(Shape.Square, Shape.Circle)
                        .addSizes(Size(12))
                        .setPosition(-50f, konfettiView.width + 50f, -50f, -50f)
                        .streamFor(300, 3000L)
                    Timer().schedule(5000) {
                        val intent2: Intent = Intent(this@GameActivity, AnimationActivity::class.java)
                        startActivity(intent2)
                    }
                }
            }
        }


    }
    // Function to close keyboard when the remainder finishes
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun markButtonDisable(button: Button) {
        button?.isEnabled = false
        button?.setBackgroundColor(ContextCompat.getColor( button.context ,R.color.red))
    }

}