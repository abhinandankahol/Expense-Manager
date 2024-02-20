package com.abhinandankahol.expensemanager

import android.icu.util.Calendar
import com.abhinandankahol.expensemanager.models.CategoryModel
import java.text.SimpleDateFormat


object Constants {

    val categories: ArrayList<CategoryModel> by lazy {
        ArrayList<CategoryModel>().apply {
            add(CategoryModel("Salary", R.drawable.ic_salary, R.color.category1))
            add(CategoryModel("Business", R.drawable.ic_business, R.color.category2))
            add(CategoryModel("Investment", R.drawable.ic_investment, R.color.category3))
            add(CategoryModel("Loan", R.drawable.ic_loan, R.color.category4))
            add(CategoryModel("Rent", R.drawable.ic_rent, R.color.category5))
            add(CategoryModel("Other", R.drawable.ic_other, R.color.category6))
        }
    }
    val calendar = Calendar.getInstance()
    val format = SimpleDateFormat("dd MMMM, yyyy")
    val format2 = SimpleDateFormat("MMMM, yyyy")
    val format3 = SimpleDateFormat("yyyy")



    fun getCategoryDetails(categoryName: String): CategoryModel? {
        for (cat in categories) {
            if (cat.catName == categoryName) {
                return cat
            }
        }
        return null
    }
}

