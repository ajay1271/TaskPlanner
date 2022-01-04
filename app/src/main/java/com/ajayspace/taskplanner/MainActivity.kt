package com.ajayspace.taskplanner

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import android.graphics.drawable.ColorDrawable


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment
        // val navController = navHostFragment.navController

        val colorDrawable = ColorDrawable(Color.parseColor("#1F1B24"))

        actionBar?.setBackgroundDrawable(colorDrawable)


        val navController = this.findNavController(R.id.nav_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_fragment)
        return navController.navigateUp()
    }

}