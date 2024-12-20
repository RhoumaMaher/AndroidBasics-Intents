package com.rhouma.androidbasics.intents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.rhouma.androidbasics.ui.theme.AndroidBasicsTheme

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidBasicsTheme {
                Text(text = "Second Screen!")
            }
        }
    }
}