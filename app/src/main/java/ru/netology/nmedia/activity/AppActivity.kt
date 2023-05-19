package ru.netology.nmedia.activity

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.os.Build
import android.os.Bundle


import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.findNavController

import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.stringArg
import ru.netology.nmedia.databinding.ActivityAppBinding


class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(POST_NOTIFICATIONS), 100500)
        }
        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }
            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                Snackbar.make(binding.root, R.string.cont_error, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok){
                        finish()
                    }
                    .show()
            } else {
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply {
                        stringArg = text
                    })

//                Toast.makeText(this, text, Toast.LENGTH_LONG).show()
            }

        }
    }
}