package com.abhinandankahol.expensemanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abhinandankahol.expensemanager.database.TransactionsDatabase
import com.abhinandankahol.expensemanager.databinding.FragmentExpenseBinding
import com.abhinandankahol.expensemanager.models.TransactionModel
import com.abhinandankahol.expensemanager.repository.TransactionsRepo
import com.abhinandankahol.expensemanager.viewmodelfactory.TransasctionsViewModelFactory
import com.abhinandankahol.expensemanager.viewmodels.TransactionsViewModel
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry

class ExpenseFragment : Fragment() {
    private val binding by lazy { FragmentExpenseBinding.inflate(layoutInflater) }
    private lateinit var viewmodel: TransactionsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewmodel = ViewModelProvider(
            requireActivity(),
            TransasctionsViewModelFactory(
                repo = TransactionsRepo(
                    dao = TransactionsDatabase.tran_db(
                        requireContext()
                    ).dao()
                )
            )
        )[TransactionsViewModel::class.java]

        viewmodel.readTransactionWithExpense().observeForever { transactions ->
            val list = ArrayList<DataEntry>()
            transactions?.forEach { transaction ->
                val entry = ValueDataEntry(
                    transaction.category,
                    transaction.amount.toDouble()
                )
                list.add(entry)
            }

            val pie = AnyChart.pie()
            pie.data(list)
            binding.anyChart.setChart(pie)
        }
    }
}