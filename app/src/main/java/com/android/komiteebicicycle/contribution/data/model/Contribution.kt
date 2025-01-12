package com.android.komiteebicicycle.contribution.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contribution(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val biciId: Int,
    val memberId: Int,
    val month: String, // Format: YYYY-MM
    val paymentMethod: String? = null, // "Cash" or "Online"
    val isPaid: Boolean = false,
    val amount: Double // Amount to be paid by the member
)