package com.pawan.netmedstask.repository

import com.pawan.netmedstask.api.RetrofitInstance
import com.pawan.netmedstask.db.MedicineDatabase
import com.pawan.netmedstask.models.Medicine

class MedicineRepository(private val db: MedicineDatabase) {

    suspend fun getMedicineList() = RetrofitInstance.create().getMedicines()

//    suspend fun searchForMedicine(medicineName: String) = db.medicineDao()

    suspend fun addMedicineToPrescriptionPad(medicine: Medicine) =
        db.medicineDao().addToPrescriptionPad(medicine)

    fun getPrescriptionPadMedicines() = db.medicineDao().getMedicineList()

    suspend fun deleteMedicineFromPrescriptionPad(medicine: Medicine) =
        db.medicineDao().deleteMedicine(medicine)

}