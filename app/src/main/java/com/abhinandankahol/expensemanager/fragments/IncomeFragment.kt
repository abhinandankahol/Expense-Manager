package com.abhinandankahol.expensemanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abhinandankahol.expensemanager.database.TransactionsDatabase
import com.abhinandankahol.expensemanager.databinding.FragmentIncomeBinding
import com.abhinandankahol.expensemanager.repository.TransactionsRepo
import com.abhinandankahol.expensemanager.viewmodelfactory.TransasctionsViewModelFactory
import com.abhinandankahol.expensemanager.viewmodels.TransactionsViewModel
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry

class IncomeFragment : Fragment() {
    private val binding by lazy { FragmentIncomeBinding.inflate(layoutInflater) }
    private lateinit var viewmodel: TransactionsViewModel

    private lateinit var list: ArrayList<DataEntry>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        list = ArrayList()


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


        viewmodel.readTransactionWithIncome().observeForever {
            list.clear()
            it.forEach { transaction ->
                val entry = ValueDataEntry(transaction.category, transaction.amount.toDouble())
                list.add(entry)
            }
            binding.anyChart.let { chart ->

                val pie = AnyChart.pie()

                pie.data(list)

                chart.setChart(pie)

                if (list.isEmpty()) {
                    binding.imageView.visibility = View.VISIBLE
                    binding.anyChart.visibility = View.INVISIBLE
                } else {
                    binding.imageView.visibility = View.GONE
                    binding.anyChart.visibility = View.VISIBLE
                }

            }
        }
    }

}