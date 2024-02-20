package com.abhinandankahol.expensemanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abhinandankahol.expensemanager.databinding.SampleCategoryItemBinding
import com.abhinandankahol.expensemanager.models.CategoryModel

class CategoryAdapter(
    private val catList: ArrayList<CategoryModel>,
    private val context: Context,

    val clickListner: CategoryClickListner
) :

    RecyclerView.Adapter<CategoryAdapter.Catvh>() {


    inner class Catvh(val binding: SampleCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Catvh {
        return Catvh(
            binding = SampleCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Catvh, position: Int) {
        val model = catList[position]
        holder.apply {
            binding.apply {
                categoryText.text = model.catName
                categoryIcon.setImageResource(model.catImage)
                categoryIcon.backgroundTintList = context.getColorStateList(model.catColor)
                itemView.setOnClickListener {
                    clickListner.onCategoryClicked(model)

                }

            }
        }
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    interface CategoryClickListner {
        fun onCategoryClicked(category: CategoryModel)

    }


}