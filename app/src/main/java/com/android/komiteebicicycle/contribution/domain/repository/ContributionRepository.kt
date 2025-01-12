package com.android.komiteebicicycle.contribution.domain.repository

import com.android.komiteebicicycle.contribution.data.model.Contribution

interface ContributionRepository {

    suspend fun addContribution(contribution: Contribution)

    suspend fun getContributions() : Result<List<Contribution>>

}