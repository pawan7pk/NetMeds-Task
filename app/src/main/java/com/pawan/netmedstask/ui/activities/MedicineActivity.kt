package com.pawan.netmedstask.ui.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.pawan.netmedstask.R
import com.pawan.netmedstask.databinding.ActivityMedicineBinding
import com.pawan.netmedstask.db.MedicineDatabase
import com.pawan.netmedstask.repository.MedicineRepository
import com.pawan.netmedstask.viewmodels.MedicineViewModel
import com.pawan.netmedstask.viewmodels.MedicineViewModelProviderFactory

class MedicineActivity : AppCompatActivity() {

    lateinit var viewModel: MedicineViewModel
    private lateinit var binding: ActivityMedicineBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewBindingAndViewModel()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        binding.bottomNavigationView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)

    }

    private fun setViewBindingAndViewModel() {
        binding = ActivityMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val medicineRepository = MedicineRepository(MedicineDatabase.invoke(this))
        viewModel = ViewModelProvider(
            this,
            MedicineViewModelProviderFactory(application, medicineRepository)
        ).get(MedicineViewModel::class.java)

        binding.bottomNavigationView
    }
}