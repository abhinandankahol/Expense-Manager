package com.abhinandankahol.expensemanager.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.abhinandankahol.expensemanager.R
import com.abhinandankahol.expensemanager.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = "Transactions"


        navController = findNavController(R.id.fragmentContainerView)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Connect BottomNavigationView with NavController
        bottomNavigationView.setupWithNavController(navController)




    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))

                true
            }

            else -> {
                false
            }

        }

    }


}