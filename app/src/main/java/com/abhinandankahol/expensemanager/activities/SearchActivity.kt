package com.abhinandankahol.expensemanager.activities

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinandankahol.expensemanager.R
import com.abhinandankahol.expensemanager.adapters.TransactionAdapter
import com.abhinandankahol.expensemanager.database.TransactionsDatabase
import com.abhinandankahol.expensemanager.databinding.ActivitySearchBinding
import com.abhinandankahol.expensemanager.models.TransactionModel
import com.abhinandankahol.expensemanager.repository.TransactionsRepo
import com.abhinandankahol.expensemanager.viewmodelfactory.TransasctionsViewModelFactory
import com.abhinandankahol.expensemanager.viewmodels.TransactionsViewModel

class SearchActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: TransactionAdapter
    private lateinit var list: ArrayList<TransactionModel>
    private lateinit var searchView: androidx.appcompat.widget.SearchView

    private lateinit var viewModel: TransactionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        list = ArrayList()
        viewModel = ViewModelProvider(
            this,
            TransasctionsViewModelFactory(
                repo = TransactionsRepo(
                    dao = TransactionsDatabase.tran_db(
                        this
                    ).dao()
                )
            )
        )[TransactionsViewModel::class.java]
        adapter = TransactionAdapter(list, this, viewModel)


        viewModel.readTransactions().observeForever {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        }

        binding.searchRecView.layoutManager = LinearLayoutManager(this)
        binding.searchRecView.adapter = adapter


        searchView = findViewById(R.id.searchView)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }


        })

    }

    private fun filterList(newText: String?) {
        val filteredList = ArrayList<TransactionModel>()
        for (i in list) {
            if (i.category.contains(newText.orEmpty(), ignoreCase = true) || i.amount.contains(
                    newText.orEmpty(),
                    ignoreCase = true
                ) || i.account.contains(newText.orEmpty(), ignoreCase = true)
            ) {
                filteredList.add(i)
            }
        }
        adapter.newlist(filteredList)

    }


}