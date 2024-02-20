package com.abhinandankahol.expensemanager.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhinandankahol.expensemanager.Constants
import com.abhinandankahol.expensemanager.R
import com.abhinandankahol.expensemanager.adapters.AccountAdapter
import com.abhinandankahol.expensemanager.adapters.CategoryAdapter
import com.abhinandankahol.expensemanager.database.TransactionsDatabase
import com.abhinandankahol.expensemanager.databinding.FragmentAddTransactionBinding
import com.abhinandankahol.expensemanager.databinding.ListDialogBinding
import com.abhinandankahol.expensemanager.models.AccountModel
import com.abhinandankahol.expensemanager.models.CategoryModel
import com.abhinandankahol.expensemanager.models.TransactionModel
import com.abhinandankahol.expensemanager.repository.TransactionsRepo
import com.abhinandankahol.expensemanager.viewmodelfactory.TransasctionsViewModelFactory
import com.abhinandankahol.expensemanager.viewmodels.TransactionsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar

class AddTransactionFragment : BottomSheetDialogFragment() {
    private lateinit var adapter: CategoryAdapter
    private lateinit var accountAdapter: AccountAdapter
    private lateinit var accountList: ArrayList<AccountModel>
    private lateinit var viewmodel: TransactionsViewModel
    private lateinit var transactions: TransactionModel

    private var selectedRadioButtonId: Int = -1


    private val binding by lazy { FragmentAddTransactionBinding.inflate(layoutInflater) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.incomeBtn.setOnClickListener { onRadioButtonClicked(binding.incomeBtn.id) }
        binding.expenseBtn.setOnClickListener { onRadioButtonClicked(binding.expenseBtn.id) }

        Constants.categories

        val dao = TransactionsDatabase.tran_db(requireContext().applicationContext).dao()
        val repo = TransactionsRepo(dao)
        accountList = ArrayList()
        viewmodel = ViewModelProvider(
            this,
            TransasctionsViewModelFactory(repo)
        )[TransactionsViewModel::class.java]
        binding.category.setOnClickListener {


            val dialogBinding = ListDialogBinding.inflate(layoutInflater)
            val catDialog = AlertDialog.Builder(requireContext()).create()
            catDialog.setView(dialogBinding.root)


            Constants.categories
            adapter = CategoryAdapter(

                Constants.categories, requireContext(),
                object : CategoryAdapter.CategoryClickListner {
                    override fun onCategoryClicked(category: CategoryModel) {

                        binding.category.setText(category.catName)
                        catDialog.dismiss()


                    }

                })
            dialogBinding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            dialogBinding.recyclerView.adapter = adapter

            catDialog.show()


        }

        binding.account.setOnClickListener {
            val accountBinding = ListDialogBinding.inflate(layoutInflater)
            val accountsDialog = AlertDialog.Builder(requireContext()).create()
            accountsDialog.setView(accountBinding.root)

            accountList.clear()
            accountList.add(AccountModel(500.0, "Cash"))
            accountList.add(AccountModel(500.0, "Card"))
            accountList.add(AccountModel(500.0, "Bank"))
            accountList.add(AccountModel(500.0, "Paytm"))
            accountList.add(AccountModel(500.0, "GooglePay"))

            accountAdapter = AccountAdapter(
                accountList,
                requireContext(),
                object : AccountAdapter.accountClicked {
                    override fun onItemClick(accountModel: AccountModel) {
                        binding.account.setText(accountModel.accountName)
                        accountsDialog.dismiss()
                    }

                })
            accountBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            accountBinding.recyclerView.addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
            accountBinding.recyclerView.adapter = accountAdapter

            accountsDialog.show()

        }

        binding.incomeBtn.setOnClickListener {
            binding.incomeBtn.setBackgroundResource(R.drawable.income_selector)
            binding.expenseBtn.setBackgroundResource(R.drawable.default_selector)
            binding.expenseBtn.setTextColor(requireContext().getColor(R.color.textColor))
            binding.incomeBtn.setTextColor(requireContext().getColor(R.color.greenColor))

        }
        binding.expenseBtn.setOnClickListener {
            binding.expenseBtn.setBackgroundResource(R.drawable.expense_selector)
            binding.incomeBtn.setBackgroundResource(R.drawable.default_selector)
            binding.incomeBtn.setTextColor(requireContext().getColor(R.color.textColor))
            binding.expenseBtn.setTextColor(requireContext().getColor(R.color.redColor))

        }

        binding.date.setOnClickListener { showDate() }
        binding.saveTransactionBtn.setOnClickListener { addTransaction() }


    }

    fun addTransaction() {
        val date = binding.date.text.toString()
        val amount = binding.amount.text.toString()
        val category = binding.category.text.toString()
        val account = binding.account.text.toString()
        val note = binding.note.text.toString()
        if (date.isEmpty() || amount.isEmpty() || category.isEmpty() || account.isEmpty() || note.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "All the fields are mandatory to fill",
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            val selectedText = getSelectedRadioButtonText()
            if (selectedText != null) {
                transactions = TransactionModel(
                    null,
                    selectedText,
                    category, account, note, date, amount
                )
            }

            viewmodel.createTransactions(transactions)

            Toast.makeText(requireContext(), "Transaction Added Successfully", Toast.LENGTH_SHORT)
                .show()
            dismiss()
        }
    }

    private fun showDate() {
        val datePicker = DatePickerDialog(requireContext())
        datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_MONTH, view.dayOfMonth)
            calendar.set(Calendar.MONTH, view.month)
            calendar.set(Calendar.YEAR, view.year)

            val format = SimpleDateFormat("dd MMMM, yyyy")
            val dateToShow = format.format(calendar.time)

            binding.date.setText(dateToShow)
        }
        datePicker.show()
    }

    private fun getSelectedRadioButtonText(): String? {
        return when {
            binding.incomeBtn.isChecked -> binding.incomeBtn.text.toString()
            binding.expenseBtn.isChecked -> binding.expenseBtn.text.toString()
            else -> null
        }

    }

    private fun onRadioButtonClicked(clickedId: Int) {
        selectedRadioButtonId = clickedId
    }


}