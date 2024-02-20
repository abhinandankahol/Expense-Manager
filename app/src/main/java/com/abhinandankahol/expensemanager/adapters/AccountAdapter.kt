package com.abhinandankahol.expensemanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinandankahol.expensemanager.databinding.RowAccountBinding
import com.abhinandankahol.expensemanager.models.AccountModel

class AccountAdapter(
    private val list: ArrayList<AccountModel>,
    private val context: Context,
    val onClicked: accountClicked
) :

    RecyclerView.Adapter<AccountAdapter.accountViewHolder>() {


    inner class accountViewHolder(val binding: RowAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): accountViewHolder {
        return accountViewHolder(
            binding = RowAccountBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: accountViewHolder, position: Int) {
        val model = list[position]
        holder.apply {
            binding.apply {
                accountName.text = model.accountName
                itemView.setOnClickListener {
                    onClicked.onItemClick(model)

                }


            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface accountClicked {
        fun onItemClick(accountModel: AccountModel)
    }

}


