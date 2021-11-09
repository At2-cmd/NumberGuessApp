package com.example.saytahmin

import android.app.Activity
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
    var remainder : Int = 10

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
        binding.resultText.text = "Haydi Tahmin Etmeye Başlayalım!"
        var entry = binding.inputGuess.text


        binding.btnGuessApply.setOnClickListener {
                if (entry.toString().toInt() < randomlyChoosed ) {
                    binding.resultText.text = "Arttır"
                    binding.inputGuess.text.clear()
                    remainder-=1
                    Toast.makeText(this," $remainder Guesses left!",Toast.LENGTH_SHORT).show()
                    if (remainder==0) {
                        hideKeyboard(view)
                        binding.resultText.text = "You Lost :( Answer was $randomlyChoosed"
                        Toast.makeText(this,"You will be navigated to main screen in 5 seconds.",Toast.LENGTH_SHORT).show()
                        Timer().schedule(5000) {
                            finish()
                        }
                    }
                } else if (entry.toString().toInt() > randomlyChoosed){
                    binding.resultText.text = "Azalt"
                    binding.inputGuess.text.clear()
                    remainder-=1
                    Toast.makeText(this," $remainder Guesses left!",Toast.LENGTH_SHORT).show()
                    if (remainder==0) {
                        hideKeyboard(view)
                        binding.resultText.text = "You Lost :( Answer was $randomlyChoosed"
                        Toast.makeText(this,"You will be navigated to main screen in 5 seconds.",Toast.LENGTH_SHORT).show()
                        Timer().schedule(5000) {
                            finish()
                        }
                    }
                } else {
                    binding.resultText.text ="Kazandın!"
                    hideKeyboard(view)
                    //I used Konfetti View Library to celebrate the user.
                    konfettiView.build()
                        .addColors(Color.YELLOW, Color.GREEN)
                        .setDirection(0.0, 359.0)
                        .setSpeed(5f, 15f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(1000L)
                        .addShapes(Shape.Square, Shape.Circle)
                        .addSizes(Size(12))
                        .setPosition(-50f, konfettiView.width + 50f, -50f, -50f)
                        .streamFor(300, 5000L)
                }
        }
    }
    // Function to close keyboard when the remainder finishes
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}