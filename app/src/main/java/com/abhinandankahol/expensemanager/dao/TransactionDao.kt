package com.abhinandankahol.expensemanager.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.abhinandankahol.expensemanager.models.TransactionModel

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createTransactions(transactions: TransactionModel)

    @Query("SELECT * FROM Transactions")
    fun readTransactions(): LiveData<List<TransactionModel>>

    @Update
    fun updateTransactions(transactions: TransactionModel)

    @Delete
    fun deleteTrsactions(transactions: TransactionModel)

    @Query("SELECT * FROM Transactions WHERE date=:selectedDate")
    fun getTransactionsForDate(selectedDate: String): LiveData<List<TransactionModel>>

    @Query("SELECT * FROM Transactions WHERE type = 'Income'")
    fun getIncomeTransactions(): LiveData<List<TransactionModel>>

    @Query("SELECT * FROM Transactions WHERE type = 'Expense'")
    fun getExpenseTransactions(): LiveData<List<TransactionModel>>

    @Query("SELECT * FROM Transactions WHERE date LIKE '%' || :monthlyDate || '%'")
    fun getTransactionsForMonth(monthlyDate: String): LiveData<List<TransactionModel>>

    @Query("SELECT * FROM Transactions WHERE substr(date, -4) = :selectedYear")
    fun getTransactionsForYear(selectedYear: String): LiveData<List<TransactionModel>>

    @Query("SELECT * FROM Transactions WHERE category LIKE :searchQuery")
    fun searchNotes(searchQuery: String): LiveData<List<TransactionModel>>



}


