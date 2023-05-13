package com.example.loanshark.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("card_table")
data class Card(
    val name: String,
    val value: Double,
    val date: Date,
    val description: String
){
    @PrimaryKey(autoGenerate = true)
    var uid : Int = 0
}
