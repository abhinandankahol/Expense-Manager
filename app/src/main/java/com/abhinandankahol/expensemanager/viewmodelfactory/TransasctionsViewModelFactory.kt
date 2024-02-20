package com.abhinandankahol.expensemanager.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhinandankahol.expensemanager.repository.TransactionsRepo
import com.abhinandankahol.expensemanager.viewmodels.TransactionsViewModel

class TransasctionsViewModelFactory(private val repo: TransactionsRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TransactionsViewModel(repo) as T
    }
}