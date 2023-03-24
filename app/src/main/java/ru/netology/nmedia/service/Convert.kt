package ru.netology.nmedia.service

import java.math.RoundingMode
import java.text.DecimalFormat

object Convert {
    fun toConvert(number: Int): String{
        when (number) {
            in 0..999 -> return number.toString()
            in 1000..1099 -> return "1K"
            in 1100..9999 -> return calcNumber(number, 1) + "K"
            in 10000..999999 -> return calcNumber(number, 0) + "K"
            in 1000000..999999999 -> return calcNumber(number, 1) + "M"
            else -> return "Billion+"
        }
    }
    fun calcNumber(number: Int, places: Int): String {
        val df: DecimalFormat

        if (places==1){
            df = DecimalFormat("###.#")
            df.roundingMode = RoundingMode.DOWN
        }else{
            df = DecimalFormat("###")
            df.roundingMode = RoundingMode.DOWN
        }

        if(number < 1000000) {
            val calced: Double
            calced = number.toDouble() / 1000
            return df.format(calced)
        } else {
            val calced: Double
            calced = number.toDouble() / 1000000
            return df.format(calced)
        }
    }

}