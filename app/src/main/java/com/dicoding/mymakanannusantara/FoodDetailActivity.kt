package com.dicoding.mymakanannusantara

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.mymakanannusantara.databinding.ActivityFoodDetailBinding


class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailBinding

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_POSITION = "extra_position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getExtraData = if (Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra(EXTRA_DATA, Food::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }

        val getExtraPos = intent.getIntExtra(EXTRA_POSITION, 0)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.title = getExtraData?.name
        supportActionBar?.elevation = 0F
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tvDetailImg = binding.tvDetailImg
        val tvDetailPrice = binding.tvDetailPrice
        val tvDetailDesc = binding.tvDetailDesc
        val tvCopywriter = binding.tvDetailCopywr
        val tvDetailBahan = binding.tvDetailBahan
        val tvDetailLoc = binding.tvDetailLoc

        // masukin foto yang diambil dari internet pake Glide
        Glide.with(this)
            .load(getExtraData?.photo) // load URL gambar, butuh akses internet
            .into(tvDetailImg) // masukin data ke View imageItemFood

        tvCopywriter.text = getExtraData?.desc
        tvDetailPrice.text = getExtraData?.price?.let { idrFormat(it) }
        tvDetailDesc.text = resources.getStringArray(R.array.food_detail_desc)[getExtraPos]
        tvDetailLoc.text = resources.getStringArray(R.array.food_loc)[getExtraPos]
        tvDetailBahan.text = resources.getStringArray(R.array.food_bahan)[getExtraPos]

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                finish()
            }

            R.id.action_share -> {
                shareContent()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareContent() {
        val getExtraData = if (Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra(EXTRA_DATA, Food::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }

        val foodLoc = resources.getStringArray(R.array.food_loc)[intent.getIntExtra(
            EXTRA_POSITION, 0)]

        val textToSend = "Rasakan Masakan ${getExtraData?.name} Nusantara khas $foodLoc - ${getExtraData?.price?.let { idrFormat(it) }}, nikmati sekarang juga!"

        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend)
        sendIntent.type = "text/plain"

        val shareIntent = Intent.createChooser(sendIntent, getExtraData?.name)
        startActivity(shareIntent)
    }

}