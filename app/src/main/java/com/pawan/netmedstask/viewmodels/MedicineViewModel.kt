package com.pawan.netmedstask.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.pawan.netmedstask.models.Medicine
import com.pawan.netmedstask.models.MedicineResponse
import com.pawan.netmedstask.repository.MedicineRepository
import com.pawan.netmedstask.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MedicineViewModel(app: Application, private val medicineRepo: MedicineRepository) :
    AndroidViewModel(app) {

    val medicineListLiveData = MutableLiveData<Resource<MedicineResponse>>()
    var medicineResponse: MedicineResponse? = null


    init {
        getMedicineList()
    }

    private fun getMedicineList() = viewModelScope.launch { safeMedicineListCall() }

    fun getPrescriptionPadList()  = medicineRepo.getPrescriptionPadMedicines()

    private suspend fun safeMedicineListCall() {
        medicineListLiveData.postValue(Resource.Loading())
        try {
            val response = medicineRepo.getMedicineList()
            Log.i("log", "Response: ${Gson().toJson(response.body())}")
            medicineListLiveData.postValue(handleResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> medicineListLiveData.postValue(Resource.Error("Network Failure"))
                else -> medicineListLiveData.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleResponse(response: Response<MedicineResponse>): Resource<MedicineResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (medicineResponse == null) {
                    medicineResponse = resultResponse
                } else {
                    val oldMedicines = medicineResponse
                    oldMedicines?.addAll(resultResponse)
                }
                return Resource.Success(medicineResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun addMedicine(medicine: Medicine) = viewModelScope.launch {
        medicineRepo.addMedicineToPrescriptionPad(medicine)
    }

    fun deleteMedicine(medicine: Medicine) = viewModelScope.launch {
        medicineRepo.deleteMedicineFromPrescriptionPad(medicine)
    }

}