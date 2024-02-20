package com.abhinandankahol.expensemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abhinandankahol.expensemanager.dao.TransactionDao
import com.abhinandankahol.expensemanager.models.TransactionModel

@Database(entities = [TransactionModel::class], version = 1, exportSchema = false)
abstract class TransactionsDatabase : RoomDatabase() {
    abstract fun dao(): TransactionDao

    companion object {
        var transactionsDB: TransactionsDatabase? = null
        fun tran_db(context: Context):TransactionsDatabase {
            if (transactionsDB == null) {
                transactionsDB = Room.databaseBuilder(
                    context,
                    TransactionsDatabase::class.java,
                    "transactions_DB"
                ).allowMainThreadQueries().build()
            }

            return transactionsDB!!


        }
    }
}