package com.pawan.netmedstask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pawan.netmedstask.models.Medicine

@Database(
    entities = [Medicine::class],
    version = 1
)
abstract class MedicineDatabase : RoomDatabase() {

    abstract fun medicineDao(): MedicineDao

    companion object {

        @Volatile
        private var instance: MedicineDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MedicineDatabase::class.java,
            "medicine_db.db"
        ).build()
    }
}