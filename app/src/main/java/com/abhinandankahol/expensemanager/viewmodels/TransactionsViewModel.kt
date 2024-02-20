package com.abhinandankahol.expensemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhinandankahol.expensemanager.models.TransactionModel
import com.abhinandankahol.expensemanager.repository.TransactionsRepo
import kotlinx.coroutines.launch

class TransactionsViewModel(private val repo: TransactionsRepo) : ViewModel() {

    fun createTransactions(transactions: TransactionModel) =
        viewModelScope.launch { repo.createTransaction(transactions) }

    fun readTransactions(): LiveData<List<TransactionModel>> = repo.readTransaction()
    fun readTransactionsWithDate(selectedDate: String): LiveData<List<TransactionModel>> =
        repo.readTransactionWithDate(selectedDate)

    fun readTransactionsForMonth(monthlyDate: String): LiveData<List<TransactionModel>> {
        return repo.readTransactionWithMonth(monthlyDate)
    }

    fun readTransactionWithIncome(): LiveData<List<TransactionModel>> =
        repo.readTransactionWithIncome()

    fun readTransactionWithExpense(): LiveData<List<TransactionModel>> =
        repo.readTransactionWithExpense()

    fun readTransactionsWithYear(selectedYear: String): LiveData<List<TransactionModel>> =
        repo.readTransactionsFromYear(selectedYear)


    fun updateTransactions(transactions: TransactionModel) =
        viewModelScope.launch { repo.updateTransaction(transactions) }

    fun deleteTransactions(transactions: TransactionModel) =
        viewModelScope.launch { repo.deleteTransaction(transactions) }

    fun search(search: String): LiveData<List<TransactionModel>> = repo.searchTransactions(search)


    private val _incomeTotal = MutableLiveData<Double>()
    val incomeTotal: LiveData<Double> = _incomeTotal

    private val _expenseTotal = MutableLiveData<Double>()
    val expenseTotal: LiveData<Double> = _expenseTotal

    private val _overallTotal = MutableLiveData<Double>()
    val overallTotal: LiveData<Double> = _overallTotal

    fun updateTotals(transactions: List<TransactionModel>) {

        val incomeTotal = transactions
            .filter { it.type == "Income" }
            .sumOf { it.amount.toDouble() }

        val expenseTotal = transactions
            .filter { it.type == "Expense" }
            .sumOf { it.amount.toDouble() }


        val overallTotal = incomeTotal - expenseTotal

        _incomeTotal.value = incomeTotal
        _expenseTotal.value = expenseTotal
        _overallTotal.value = overallTotal
    }


}

