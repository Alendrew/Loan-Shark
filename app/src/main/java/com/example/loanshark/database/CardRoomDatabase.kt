package com.example.loanshark.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.loanshark.database.daos.CardDao
import com.example.loanshark.database.enums.TypeEnum
import com.example.loanshark.database.models.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Database(entities = [Card::class], version = 1, exportSchema = false)
abstract class CardRoomDatabase : RoomDatabase() {

    abstract fun cardDao() : CardDao
    
    companion object {

        @Volatile
        private var INSTANCE: CardRoomDatabase? = null

        fun getDatabase(scope: CoroutineScope, context: Context): CardRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardRoomDatabase::class.java,
                    "Card_database"
                ).addCallback(CardDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }

        private class CardDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.cardDao())
                    }
                }
            }

            suspend fun populateDatabase(cardDao: CardDao) {
                val myFormat = "dd-MM-yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                cardDao.deleteAll()
                cardDao.insert(
                    Card(
                        "Matheus Alencar",
                        90.00,
                        sdf.parse("13-05-2023"),
                        "Lanche e McDonalds",
                        TypeEnum.PAY
                    )
                )
                cardDao.insert(
                    Card(
                        "André Alencar",
                        100.00,
                        sdf.parse("13-05-2023"),
                        "Pagar chuteira",
                        TypeEnum.CHARGE
                    )
                )
            }
        }
    }
}