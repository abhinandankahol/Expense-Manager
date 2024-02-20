package com.abhinandankahol.expensemanager.fragments

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinandankahol.expensemanager.Constants
import com.abhinandankahol.expensemanager.R
import com.abhinandankahol.expensemanager.adapters.TransactionAdapter
import com.abhinandankahol.expensemanager.database.TransactionsDatabase
import com.abhinandankahol.expensemanager.databinding.FragmentTransactionsBinding
import com.abhinandankahol.expensemanager.models.TransactionModel
import com.abhinandankahol.expensemanager.repository.TransactionsRepo
import com.abhinandankahol.expensemanager.viewmodelfactory.TransasctionsViewModelFactory
import com.abhinandankahol.expensemanager.viewmodels.TransactionsViewModel
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionsFragment : Fragment() {
    private val binding by lazy {
        FragmentTransactionsBinding.inflate(layoutInflater)
    }

    companion object {
        val calendar: Calendar = Calendar.getInstance()
         var selectedTab = 0
    }

    private lateinit var list: ArrayList<TransactionModel>
    private lateinit var adapter: TransactionAdapter
    private lateinit var viewModel: TransactionsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_transactionsFragment_to_addTransactionFragment)
        }

        viewModel = ViewModelProvider(
            requireActivity(),
            TransasctionsViewModelFactory(
                repo = TransactionsRepo(
                    dao = TransactionsDatabase.tran_db(
                        requireContext(),
                    ).dao()
                )
            )
        )[TransactionsViewModel::class.java]

        list = ArrayList()
        updateDate()
        updateData()



        adapter = TransactionAdapter(list, requireContext(), viewModel)
        binding.mainRec.layoutManager = LinearLayoutManager(requireContext())
        binding.mainRec.adapter = adapter
        adapter.notifyDataSetChanged()



        binding.nextDateBtn.setOnClickListener {
            val currentDate = Calendar.getInstance()
            val nextDate = Calendar.getInstance()
            nextDate.time = calendar.time
            nextDate.add(Calendar.DATE, 1)

            if (selectedTab == 0) {
                if (!nextDate.after(currentDate)) {
                    calendar.add(Calendar.DATE, 1)
                    updateDate()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Cannot go to future dates",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (selectedTab == 1) {
                calendar.add(Calendar.MONTH, 1)
                updateDate()
            } else if (selectedTab == 2) {
                calendar.add(Calendar.YEAR, 1)
                updateDate()
            }


        }
        binding.previousDateBtn.setOnClickListener {
            when (selectedTab) {
                0 -> {
                    calendar.add(Calendar.DATE, -1)
                    updateDate()

                }

                1 -> {
                    calendar.add(Calendar.MONTH, -1)
                    updateDate()
                }

                2 -> {
                    calendar.add(Calendar.YEAR, -1)
                    updateDate()
                }
            }


        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.text!!.equals("Monthly")) {
                    selectedTab = 1
                    updateDate()
                } else if (tab.text == "Daily") {
                    selectedTab = 0
                    updateDate()
                } else if (tab.text == "Yearly") {
                    selectedTab = 2
                    updateDate()
                } else if (tab.text == "Notes") {
                    selectedTab = 3
                    updateDate()

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateData() {
        when (selectedTab) {
            0 -> {
                viewModel.readTransactionsWithDate(Constants.format.format(calendar.time))
                    .observe(viewLifecycleOwner) { transactions ->
                        list.clear()
                        list.addAll(transactions)
                        adapter.notifyDataSetChanged()
                        viewModel.updateTotals(transactions)

                        uiHiding()
                    }


                updateTotal()


            }

            1 -> {
                val monthlyDate =
                    SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).format(calendar.time)
                viewModel.readTransactionsForMonth(monthlyDate)
                    .observe(requireActivity()) { transactions ->
                        list.clear()
                        list.addAll(transactions)
                        adapter.notifyDataSetChanged()
                        viewModel.updateTotals(transactions)

                        uiHiding()
                    }

                updateTotal()
            }

            2 -> {

                viewModel.readTransactionsWithYear(Constants.format3.format(calendar.time))
                    .observe(requireActivity()) {
                        list.clear()
                        list.addAll(it)
                        adapter.notifyDataSetChanged()
                        viewModel.updateTotals(it)

                        uiHiding()
                    }

                updateTotal()


            }

            3 -> {
                viewModel.readTransactions().observe(requireActivity()) {
                    list.clear()
                    list.addAll(it)
                    adapter.notifyDataSetChanged()
                    viewModel.updateTotals(it)

                    uiHiding()
                }
                updateTotal()
            }
        }
    }


    @SuppressLint("SimpleDateFormat", "NotifyDataSetChanged")
    private fun updateDate() {
        when (selectedTab) {
            0 -> {
                binding.currentDate.text = Constants.format.format(calendar.time)
                binding.previousDateBtn.visibility = View.VISIBLE
                binding.nextDateBtn.visibility = View.VISIBLE
            }

            1 -> {
                binding.currentDate.text = Constants.format2.format(calendar.time)
                binding.previousDateBtn.visibility = View.VISIBLE
                binding.nextDateBtn.visibility = View.VISIBLE

            }

            2 -> {
                binding.currentDate.text = Constants.format3.format(calendar.time)
                binding.previousDateBtn.visibility = View.VISIBLE
                binding.nextDateBtn.visibility = View.VISIBLE

            }

            3 -> {
                binding.currentDate.text =
                    "Your Notes"
                binding.previousDateBtn.visibility = View.GONE
                binding.nextDateBtn.visibility = View.GONE
            }
        }

        updateData()


    }

    private fun updateTotal() {
        viewModel.incomeTotal.observe(requireActivity()) { incomeTotal ->
            binding.incomeLbl.text = incomeTotal.toString()
        }

        viewModel.expenseTotal.observe(requireActivity()) { expenseTotal ->
            binding.expenseLbl.text = expenseTotal.toString()
        }

        viewModel.overallTotal.observe(requireActivity()) { overallTotal ->
            binding.totalLbl.text = overallTotal.toString()
        }
    }

    private fun uiHiding() {

        if (list.isEmpty()) {
            binding.imageView.visibility = View.VISIBLE
            binding.mainRec.visibility = View.INVISIBLE
        } else {
            binding.imageView.visibility = View.GONE
            binding.mainRec.visibility = View.VISIBLE
        }
    }

}