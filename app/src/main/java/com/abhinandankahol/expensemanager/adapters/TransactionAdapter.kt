package com.abhinandankahol.expensemanager.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.abhinandankahol.expensemanager.Constants
import com.abhinandankahol.expensemanager.R
import com.abhinandankahol.expensemanager.databinding.RowTransactionBinding
import com.abhinandankahol.expensemanager.fragments.TransactionsFragment.Companion.calendar
import com.abhinandankahol.expensemanager.fragments.TransactionsFragment.Companion.selectedTab
import com.abhinandankahol.expensemanager.models.TransactionModel
import com.abhinandankahol.expensemanager.viewmodels.TransactionsViewModel
import java.text.SimpleDateFormat

class TransactionAdapter(
    private var transactionsList: ArrayList<TransactionModel>,
    private val context: Context,
    private val viewModel: TransactionsViewModel
) : RecyclerView.Adapter<TransactionAdapter.transViewHolder>() {

    inner class transViewHolder(val binding: RowTransactionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): transViewHolder {
        return transViewHolder(
            binding = RowTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun newlist(filteredList: ArrayList<TransactionModel>) {
        transactionsList = filteredList
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: transViewHolder, position: Int) {
        val model = transactionsList[position]

        holder.binding.apply {
            transactionAmount.text = model.amount
            transactionCategory.text = model.category
            accountLbl.text = model.account

            if (model.type.equals("Income", ignoreCase = true)) {
                transactionAmount.setTextColor(context.getColor(R.color.greenColor))
            } else {
                transactionAmount.setTextColor(context.getColor(R.color.redColor))
            }

            val format = SimpleDateFormat("dd MMMM, yyyy")
            val format2 = SimpleDateFormat("MMMM, yyyy")
            val format3 = SimpleDateFormat("yyyy")

            val dateToShow = format.format(calendar.time)
            val monthToShow = format2.format(calendar.time)
            val yearToShow = format3.format(calendar.time)

            when (selectedTab) {
                0 -> {
                    transactionDate.text = dateToShow.format(model.date)
                }

                1 -> {
                    transactionDate.text = monthToShow.format(model.date)
                }

                2 -> {
                    transactionDate.text = yearToShow.format(model.date)
                }

                3 -> {
                    transactionDate.text = model.note
                }
            }


            val transactionCategory = Constants.getCategoryDetails(model.category)
            if (transactionCategory != null) {
                categoryIcon.setImageResource(transactionCategory.catImage)
                categoryIcon.backgroundTintList =
                    context.getColorStateList(transactionCategory.catColor)
            }



            holder.itemView.setOnLongClickListener {
                if (selectedTab == 0) {
                    val deleteDialog = AlertDialog.Builder(context).create()
                    deleteDialog.setTitle("Delete Transaction")
                    deleteDialog.setMessage("Are you sure you want to delete this transaction")
                    deleteDialog.setCancelable(false)
                    deleteDialog.setButton(
                        DialogInterface.BUTTON_POSITIVE,
                        "Yes",
                        DialogInterface.OnClickListener { dialog, which ->
                            viewModel.deleteTransactions(model)
                            notifyItemRemoved(position)
                            dialog.dismiss()
                            viewModel.updateTransactions(model)
                        })
                    deleteDialog.setButton(
                        DialogInterface.BUTTON_NEGATIVE,
                        "No",
                        DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                    deleteDialog.show()


                } else {
                    Toast.makeText(context, "Feature coming soon", Toast.LENGTH_SHORT).show()
                }
                true
            }

        }
    }

    override fun getItemCount(): Int {
        return transactionsList.size
    }
}