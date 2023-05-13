package com.example.loanshark.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.loanshark.database.enums.TypeEnum
import java.util.Date

@Entity("card_table")
data class Card(
    val name: String,
    val value: Double,
    val date: Date,
    val description: String,
    val type : TypeEnum
){
    @PrimaryKey(autoGenerate = true)
    var uid : Int = 0
}
