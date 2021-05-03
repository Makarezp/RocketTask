package com.task.spacex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.task.spacex.ui.launch_list.RocketListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RocketListFragment())
                .commitNow()
        }
    }
}
