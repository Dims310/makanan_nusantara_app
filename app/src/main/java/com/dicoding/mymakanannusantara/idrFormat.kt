package com.dicoding.mymakanannusantara

import java.text.NumberFormat
import java.util.Locale

fun idrFormat(price : Int) : String {
    // buat format negara Indonesia
    val formatIDR = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return formatIDR.format(price) // return dalam bentuk format tipe data long
}