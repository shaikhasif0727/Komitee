package com.android.komiteebicicycle.contribution.data.repository

import com.android.komiteebicicycle.contribution.data.model.Contribution
import com.android.komiteebicicycle.contribution.domain.repository.ContributionRepository
import com.android.komiteebicicycle.core.data.room.dao.ContributionDao
import javax.inject.Inject

class ContributionRepositoryImpl @Inject constructor(
    private val contributionDao: ContributionDao,
) : ContributionRepository {
    override suspend fun addContribution(contribution: Contribution) {
        contributionDao.insertContribution(contribution)
    }

    override suspend fun getContributions(): Result<List<Contribution>> {
       return Result.success(contributionDao.getAllContributions())
    }

}