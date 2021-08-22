package minhoa.test.food1

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity

class About : AppCompatActivity() {

    private lateinit var dm : DisplayMetrics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)

        dm = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(dm)
        var width : Int
        width = dm.widthPixels
        var height : Int
        height = dm.heightPixels

        getWindow().setLayout((width*0.8).toInt(), (height*0.6).toInt())

    }
}