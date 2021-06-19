package com.pawan.netmedstask.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "medicine")
data class Medicine(
    val company: String,
    @PrimaryKey()
    val id: Int,
    val name: String,
    @SerializedName("packform")
    val packForm: String,
    val strength: String,
    @SerializedName("strengthtype")
    val strengthType: String,
    val type: String,
    var dose : Int
)