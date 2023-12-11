package com.dicoding.mymakanannusantara

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mymakanannusantara.databinding.ItemRowFoodBinding

class ListFoodAdapter (private val listFood: ArrayList<Food>) : RecyclerView.Adapter<ListFoodAdapter.ListViewHolder>(){
    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: Food, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): ListViewHolder {
        val binding = ItemRowFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding) // return class ListViewHolder dengan parameter binding
    }

    class ListViewHolder (var binding : ItemRowFoodBinding) : RecyclerView.ViewHolder(binding.root)

    // masukin data ke tiap View ItemRowFood
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        // ambil data yang dikirim dari MainActivity menggunakan parameter class (listFood)
        val (foodName, foodDesc, foodPhoto, foodPrice) = listFood[position]

        // masukin foto yang diambil dari internet pake Glide
        Glide.with(holder.itemView.context)
            .load(foodPhoto) // load URL gambar, butuh akses internet
            .into(holder.binding.imageItemFood) // masukin data ke View imageItemFood

        holder.binding.tvItemName.text = foodName // binding data nama makanan
        holder.binding.tvItemDesc.text = foodDesc // binding data deskripsi makanan

        // format rupiah
        val finalFoodPrice = idrFormat(foodPrice)

        holder.binding.tvItemPrice.text = finalFoodPrice // binding data harga makanan

        // listener item pada MainActivity
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listFood[holder.adapterPosition], position) }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

//    private fun idrFormat(price : Int) : String {
//        // buat format negara Indonesia
//        val formatIDR = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
//        return formatIDR.format(price) // return dalam bentuk format tipe data long
//    }

    override fun getItemCount(): Int = listFood.size

}
