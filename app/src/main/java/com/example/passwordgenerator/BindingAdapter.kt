package com.example.passwordgenerator

import android.graphics.Color
import android.graphics.Color.rgb
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.passwordgenerator.database.PasswordDataClass

@BindingAdapter("websiteInput")
fun TextView.websiteInput(item: PasswordDataClass){
    text = item.website
}

@BindingAdapter("passwordInput")
fun TextView.passwordInput(item: PasswordDataClass){
    text = item.password
}

@BindingAdapter("strengthInput")
fun TextView.strengthInput(item: PasswordDataClass){
    var letterCount = 0
    var numberCount = 0
    var specialCount = 0

    val specialLetters = listOf("@", "$", "&", "#", "_", "-")

    for (char in item.password){
        when {
            char.isLetter() -> {
                letterCount++
            }
            char.isDigit() -> {
                numberCount++
            }
            else -> {
                for (eachSign in specialLetters){
                    if (char.toString() == eachSign){
                        specialCount++
                    }
                }
            }
        }
    }

    text = if (letterCount != 0 && numberCount != 0 && specialCount != 0){
        "Strong"
    }
    else if(letterCount != 0 && numberCount != 0){
        "Normal"
    }
    else{
        "Weak"
    }

    setTextColor(
        when(text){
            "Strong" -> Color.GREEN
            "Normal" -> rgb(243, 121, 83)
            else -> rgb(205, 92, 92)
        }
    )
}


//        orange -->> 243, 121, 83
