package com.pawan.netmedstask.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pawan.netmedstask.repository.MedicineRepository

class MedicineViewModelProviderFactory(private val app: Application, private val medicineRepo: MedicineRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MedicineViewModel(app, medicineRepo) as T
    }
}