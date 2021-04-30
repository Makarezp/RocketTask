package com.task.spacex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.task.spacex.ui.main.RocketListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, RocketListFragment.newInstance())
                    .commitNow()
        }
    }
}
