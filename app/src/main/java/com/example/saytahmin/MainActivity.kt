package com.example.saytahmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.saytahmin.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Describing ViewBinding
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        val view = bindingMain.root
        setContentView(view)

        var minRange = bindingMain.numberMin.text
        var maxRange= bindingMain.numberMax.text

        bindingMain.startBtn.setOnClickListener {
            if(minRange.toString() == "" || maxRange.toString() == "") {
                Toast.makeText(this,"Minimum and Maximum Ranges cannot be empty!",Toast.LENGTH_SHORT).show()
            }else{
                //If ranges are not empty, we send these data to the second activity with Intent
                val intent : Intent = Intent(this,GameActivity::class.java)
                intent.putExtra("minimumRange",minRange.toString())
                intent.putExtra("maximumRange",maxRange.toString())
                startActivity(intent)
            }
            //After the current game finishes, we clear old data.
            minRange.clear()
            maxRange.clear()
        }
    }
}









