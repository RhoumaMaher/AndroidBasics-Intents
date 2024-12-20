package com.rhouma.androidbasics.intents

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.rhouma.androidbasics.ui.theme.AndroidBasicsTheme

class FirstActivity : ComponentActivity() {

    private val viewModel: ImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidBasicsTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    viewModel.uri?.let {
                        AsyncImage(model = viewModel.uri, contentDescription = null)
                    }

                    Button(onClick = {
                        navigateToSecondActivity(this@FirstActivity)
                    }) {
                        Text(text = "Navigate to Second Activity!")
                    }
                    Button(onClick = {
                        navigateToYoutube(this@FirstActivity)
                    }) {
                        Text(text = "Navigate to Youtube!")
                    }
                    Button(onClick = {
                        sendEmail(this@FirstActivity)
                    }) {
                        Text(text = "Send Email!")
                    }
                }

            }
        }
    }


    // This would respond to implicit intents from other apps which we have defined in the manifest
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent.getParcelableExtra(Intent.EXTRA_STREAM)
        }
        if (uri != null) {
            viewModel.updateUri(uri)
        }
    }

}

// This is an explicit intent within the app to navigate from one activity to another
fun navigateToSecondActivity(context: Context) {
    Intent(context, SecondActivity::class.java).also {
        context.startActivity(it)
    }
}

// This is an explicit intent to another app (Youtube in this case) using it's package name
fun navigateToYoutube(context: Context) {
    Intent(Intent.ACTION_MAIN).also {
        it.`package` = "com.google.android.youtube"
        try {
            context.startActivity(it)
        } catch (e: Exception) {
            println(e.message)
        }

    }
}

// This is an implicit intent to send an email
fun sendEmail(context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_EMAIL, arrayOf("test@test.com"))
        putExtra(Intent.EXTRA_SUBJECT, "Email subject")
        putExtra(Intent.EXTRA_TEXT, "Email body text")
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}

