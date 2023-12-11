package com.dicoding.mymakanannusantara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mymakanannusantara.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var rvFood : RecyclerView
    private lateinit var binding : ActivityMainBinding
    private var list = ArrayList<Food>()

    override fun onCreate(savedInstanceState: Bundle?) {
//        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // binding view elemen activity_main
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setLogo(R.drawable.food_bank_fill0_wght400_grad0_opsz24)
        supportActionBar?.elevation = 0F

        // binding view rvFood
        rvFood = binding.rvFood
        rvFood.setHasFixedSize(true) // optimasi ukuran lebar dan tinggi

        // buat fungsi masukin food data ke list
        list.addAll(getListFood())

        // tampilin RecylerView pake fungsi
        showRecylerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_page -> {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListFood() : ArrayList<Food>{
        val foodName = resources.getStringArray(R.array.food_name)
        val foodDesc = resources.getStringArray(R.array.food_desc)
        val foodPhoto = resources.getStringArray(R.array.food_photo)
        val foodPrice = resources.getIntArray(R.array.food_price)

        val listFood = ArrayList<Food>()

        // looping masukin data ke ArrayList<Food> untuk listFood
        // ".indices" untuk jumlah range data
        for (i in foodName.indices){
            val food = Food(foodName[i], foodDesc[i], foodPhoto[i], foodPrice[i])
            listFood.add(food)
        }

        return listFood
    }

    // function tampilin data RecylerView
    private fun showRecylerList() {
        rvFood.layoutManager = LinearLayoutManager(this) // model Layout RecylerView Linear
        val listFoodAdapter = ListFoodAdapter(list) // panggil ListFoodAdapter untuk kirim data list food
        rvFood.adapter = listFoodAdapter // pasang adapter rvFood dengan listFoodAdapter

        listFoodAdapter.setOnItemClickCallback(object : ListFoodAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Food, position: Int) {
                val intent = Intent(this@MainActivity, FoodDetailActivity::class.java)
                intent.putExtra(FoodDetailActivity.EXTRA_DATA, data)
                intent.putExtra(FoodDetailActivity.EXTRA_POSITION, position)
                startActivity(intent)
            }
        })
    }
}
