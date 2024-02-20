package com.abhinandankahol.expensemanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Transactions")
class TransactionModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var type: String = "",
    var category: String = "",
    var account: String = "",
    var note: String = "",
    var date: String = "",
    var amount: String = ""
)





