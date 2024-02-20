package com.abhinandankahol.expensemanager.repository

import androidx.lifecycle.LiveData
import com.abhinandankahol.expensemanager.dao.TransactionDao
import com.abhinandankahol.expensemanager.models.TransactionModel

class TransactionsRepo(private val dao: TransactionDao) {

    fun createTransaction(transaction: TransactionModel) = dao.createTransactions(transaction)
    fun readTransaction(): LiveData<List<TransactionModel>> = dao.readTransactions()
    suspend fun updateTransaction(transaction: TransactionModel) =
        dao.updateTransactions(transaction)

    suspend fun deleteTransaction(transaction: TransactionModel) = dao.deleteTrsactions(transaction)
    fun readTransactionWithDate(selectedDate: String): LiveData<List<TransactionModel>> =
        dao.getTransactionsForDate(selectedDate)

    fun readTransactionWithMonth(monthlyDate: String): LiveData<List<TransactionModel>> =
        dao.getTransactionsForMonth(monthlyDate)

    fun readTransactionWithIncome(): LiveData<List<TransactionModel>> =
        dao.getIncomeTransactions()

    fun readTransactionWithExpense(): LiveData<List<TransactionModel>> =
        dao.getExpenseTransactions()

    fun readTransactionsFromYear(selectedYear: String): LiveData<List<TransactionModel>> =
        dao.getTransactionsForYear(selectedYear)

    fun searchTransactions(search: String): LiveData<List<TransactionModel>> =
        dao.searchNotes(search)

}