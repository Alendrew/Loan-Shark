package com.example.loanshark.database.daos

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.loanshark.database.models.Card
import kotlinx.coroutines.flow.Flow

interface CardDao {

    @Insert
    suspend fun insert(card: Card)

    @Query("DELETE FROM card_table WHERE uid = uid")
    suspend fun delete(id:Int) : Void

    @Update
    suspend fun update(card: Card)

    @Query("SELECT * FROM card_table")
    suspend fun getAllCards() : Flow<List<Card>>

}