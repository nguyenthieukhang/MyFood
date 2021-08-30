package minhoa.test.food1

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ScrollView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.io.InputStream
import java.net.URL


class FoodInfo : AppCompatActivity() {

    private val client = OkHttpClient()
    private lateinit var ivFoodImage : ImageView
    private lateinit var tvFoodName : TextView
    private lateinit var foodName : String
    private lateinit var jsonObject: JSONObject
    private lateinit var imageUrl: String
    private lateinit var recipe: String
    private lateinit var nutrition: String
    private lateinit var tvInfo : TextView
    private var nutrientMap = mapOf(
        "CA" to Pair("Calcium", "mg"),
        "ENERC_KCAL" to Pair("Energy", "kcal"),
        "CHOCDF" to Pair("Carbs", "g"),
        "NIA" to Pair("Niacin (B3)", "mg"),
        "CHOLE" to Pair("Cholesterol", "mg"),
        "P" to Pair("Phosphorus", "mg"),
        "FAMS" to Pair("Monounsaturated", "g"),
        "PROCNT" to Pair("Protein", "g"),
        "FAPU" to Pair("Polyunsaturated", "g"),
        "RIBF" to Pair("Riboflavin (B2)", "mg"),
        "FASAT" to Pair("Saturated", "g"),
        "SUGAR" to Pair("Sugars", "g"),
        "FAT" to Pair("Fat", "g"),
        "THIA" to Pair("Thiamin (B1)", "mg"),
        "FATRN" to Pair("Trans", "g"),
        "TOCPHA" to Pair("Vitamin E", "mg"),
        "FE" to Pair("Iron", "mg"),
        "VITA_RAE" to Pair("Vitamin A", "æg"),
        "FIBTG" to Pair("Fiber", "g"),
        "VITB12" to Pair("Vitamin B12", "æg"),
        "FOLDFE" to Pair("Folate (Equivalent)", "æg"),
        "VITB6A" to Pair("Vitamin B6", "mg"),
        "K" to Pair("Potassium", "mg"),
        "VITC" to Pair("Vitamin C", "mg"),
        "MG" to Pair("Magnesium", "mg"),
        "VITD" to Pair("Vitamin D", "æg"),
        "NA" to Pair("Sodium", "mg"),
        "VITK1" to Pair("Vitamin K", "æg")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.food_info)
        ivFoodImage = findViewById(R.id.food_image)
        tvFoodName = findViewById(R.id.food_name)
        foodName = intent.getStringExtra("food").toString()
        tvInfo = findViewById(R.id.text_info)
        recipe = ""
        nutrition = ""
        getFoodInfo()
        getFoodNutrient()
    }

    private fun getFoodInfo(){
        var appID = "caa2bec3"
        var appKey= "19b0cbc40bae62b7908d1ac2c2054b1f"
        val url = "https://api.edamam.com/api/recipes/v2?type=public&q=" + foodName +
                "&app_id=" + appID + "&app_key=" + appKey
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = (parseRecipe(response))
        })
    }

    private fun getFoodNutrient(){
        var appID = "291a0edd"
        var appKey = "1ffc44a9800d300cab3eba0c461a633b"
        var url = "https://api.edamam.com/api/food-database/v2/parser?app_id=" + appID +
                "&app_key=" + appKey + "&ingr=" + foodName + "&nutrition-type=cooking"
        var request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) = (parseNutrient(response))
        })
    }

    private fun parseNutrient(response : Response){
        jsonObject = JSONTokener(response.body()?.string()).nextValue() as JSONObject
        var nutrient : JSONObject = (jsonObject.getJSONArray("hints")[0] as JSONObject)
            .getJSONObject("food").getJSONObject("nutrients")
        var iter : Iterator<String> = nutrient.keys()
        while (iter.hasNext()){
            var key : String = iter.next()
            nutrition += nutrientMap.getValue(key).first + ": " + nutrient.getString(key) + " " +
                    nutrientMap.getValue(key).second + "\n"
        }
    }

    private fun parseRecipe(response: Response){

        jsonObject = JSONTokener(response.body()?.string()).nextValue() as JSONObject
        val hits : JSONArray = jsonObject.getJSONArray("hits")
        val ingr = (hits[0] as JSONObject).getJSONObject("recipe").getJSONArray("ingredientLines")
//        print(jsonObject.toString() + "Hello")

        for (i in 0 until ingr.length()){
            recipe = recipe  + ingr[i] + "\n"
        }

        imageUrl = (hits[0] as JSONObject).getJSONObject("recipe").getString("image")
        Thread(Runnable {
            this@FoodInfo.runOnUiThread(java.lang.Runnable {
                this.tvFoodName.setText(foodName)
            })
        }).start()

        LoadImageFromWebOperations(imageUrl)
    }

    fun LoadImageFromWebOperations(url: String) {
        try {
            var image : InputStream = (URL(url).getContent()) as InputStream
            var d : Drawable = Drawable.createFromStream(image, "src name")
            Thread(Runnable {
                this@FoodInfo.runOnUiThread(java.lang.Runnable {
                    this.ivFoodImage.setImageDrawable(d)
                })
            }).start()
        }catch (e : Exception){
            return
        }
    }

    fun showRecipe(view: View) {
        Thread(Runnable {
            this@FoodInfo.runOnUiThread(java.lang.Runnable {
                this.tvInfo.setText(recipe)
            })
        }).start()
    }
    fun showNutrition(view: View) {
        Thread(Runnable {
            this@FoodInfo.runOnUiThread(java.lang.Runnable {
                this.tvInfo.setText(nutrition)
            })
        }).start()
    }

}