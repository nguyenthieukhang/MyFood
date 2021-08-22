package minhoa.test.food1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException


class FoodInfo : AppCompatActivity() {

    private val client = OkHttpClient()
    private lateinit var tvInfo : TextView
    private lateinit var tvFoodName : TextView
    private lateinit var foodName : String
    private lateinit var jsonObject: JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_info)
        tvInfo = findViewById(R.id.info)
        tvFoodName = findViewById(R.id.food_name)
        foodName = intent.getStringExtra("food").toString()
        getFoodInfo()
    }

    private fun getFoodInfo(){
        var appID = "caa2bec3"
        var appKey= "19b0cbc40bae62b7908d1ac2c2054b1f"
        val url = "https://api.edamam.com/api/recipes/v2?type=public&q=" + foodName +
                "&app_id=" + appID + "&app_key=" + appKey
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = (parse(response))
        })
    }

    private fun parse(response: Response){

        jsonObject = JSONTokener(response.body()?.string()).nextValue() as JSONObject
        val hits : JSONArray = jsonObject.getJSONArray("hits")
        val ingr = (hits[0] as JSONObject).getJSONObject("recipe").getJSONArray("ingredientLines")
//        print(jsonObject.toString() + "Hello")
        var info = ""

        for (i in 0 until ingr.length()){
            info = info  + ingr[i] + "\n"
        }
        Thread(Runnable {
            this@FoodInfo.runOnUiThread(java.lang.Runnable {
                this.tvFoodName.setText(foodName)
                this.tvInfo.setText(info)
            })
        }).start()
    }

}