package com.android.komiteebicicycle.details

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import com.android.komiteebicicycle.contribution.data.model.Contribution
import com.android.komiteebicicycle.core.room.dao.BiciDao
import com.android.komiteebicicycle.core.room.dao.ContributionDao
import com.android.komiteebicicycle.core.room.dao.MemberDao
import com.android.komiteebicicycle.core.room.model.BiciWithMembers
import com.si.f1.f1predictor.core.common.BaseViewModel
import com.si.f1.f1predictor.core.common.UiEffect
import com.si.f1.f1predictor.core.common.UiEvent
import com.si.f1.f1predictor.core.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BiciDetailsViewModel @Inject constructor(
    private val biciDao: BiciDao,
    private val contributionDao: ContributionDao,
    private val memberDao: MemberDao
) : BaseViewModel<BiciDetailsContract.Event,BiciDetailsContract.State,BiciDetailsContract.Effect>() {

    private val _contributionsByMonth = mutableStateMapOf<String, List<Contribution>>()
    val contributionsByMonth: Map<String, List<Contribution>> get() = _contributionsByMonth

    // Fetch Bici and Members
    fun loadBiciDetails(biciId: Int) {
        viewModelScope.launch {
            val details = biciDao.getBiciWithMembers(biciId)
            setState {
                copy(
                    biciWithMembers = details
                )
            }

            if (details != null) {
                val months = getMonthsForBici(details.bici.startDate, details.bici.endDate)
                val memberCount = details.members.size
                val amountPerMember = details.bici.totalAmount / memberCount

                months.forEach { month ->
                    val contributions = contributionDao.getContributionsByBiciAndMonth(biciId, month)
                    _contributionsByMonth[month] = contributions.ifEmpty {
                        // Create contributions for all members if not present
                        val newContributions = details.members.map { member ->
                            Contribution(
                                biciId = biciId,
                                memberId = member.memberId,
                                month = month,
                                amount = amountPerMember
                            )
                        }
                        newContributions.forEach { contributionDao.insertContribution(it) }
                        newContributions
                    }
                }
            }
        }
    }

    fun updateContribution(contribution: Contribution, paymentMethod: String, isPaid: Boolean) {
        viewModelScope.launch {
            val updatedContribution = contribution.copy(paymentMethod = paymentMethod, isPaid = isPaid)
            contributionDao.updateContribution(updatedContribution)

            // Update in memory
            _contributionsByMonth[contribution.month] = _contributionsByMonth[contribution.month]
                ?.map { if (it.id == contribution.id) updatedContribution else it }
                ?: listOf(updatedContribution)
        }
    }

    fun getMonthsForBici(startDate: String, endDate: String): List<String> {
        val dateFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val start = dateFormat.parse(startDate)
        val end = dateFormat.parse(endDate)

        val calendar = Calendar.getInstance()
        calendar.time = start

        val months = mutableListOf<String>()
        while (calendar.time.before(end) || calendar.time == end) {
            months.add(dateFormat.format(calendar.time))
            calendar.add(Calendar.MONTH, 1)
        }
        return months
    }

    override fun createInitialState(): BiciDetailsContract.State {
        return BiciDetailsContract.State()
    }

    override fun handleEvent(event: BiciDetailsContract.Event) {
        when(event){

            else -> {}
        }
    }
}

class BiciDetailsContract{

    sealed class Event:UiEvent

    data class State(
        val biciWithMembers:BiciWithMembers?= null,
    ):UiState {

        fun getMemberById(id:Int) = biciWithMembers?.members?.find { it.memberId == id }

    }

    sealed class Effect:UiEffect

}
