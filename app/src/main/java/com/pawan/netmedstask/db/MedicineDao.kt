package com.pawan.netmedstask.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pawan.netmedstask.models.Medicine

@Dao
interface MedicineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToPrescriptionPad(medicine: Medicine): Long

    @Query("SELECT * FROM medicine")
    fun getMedicineList(): LiveData<List<Medicine>>

    @Delete
    suspend fun deleteMedicine(medicine: Medicine)
}