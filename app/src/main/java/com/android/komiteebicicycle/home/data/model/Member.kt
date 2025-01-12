package com.android.komiteebicicycle.home.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Member(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)