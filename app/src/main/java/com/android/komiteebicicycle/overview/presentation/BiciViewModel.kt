package com.android.komiteebicicycle.overview.presentation

import androidx.lifecycle.viewModelScope
import com.android.komiteebicicycle.contribution.data.model.Contribution
import com.android.komiteebicicycle.core.room.dao.BiciDao
import com.android.komiteebicicycle.core.room.dao.MemberDao
import com.android.komiteebicicycle.core.naviagtion.Destination
import com.android.komiteebicicycle.core.naviagtion.Navigator
import com.android.komiteebicicycle.core.room.dao.BiciMemberCrossRef
import com.android.komiteebicicycle.core.room.dao.ContributionDao
import com.android.komiteebicicycle.core.room.model.BiciWithMembers
import com.android.komiteebicicycle.core.utils.getMonthsForBici
import com.android.komiteebicicycle.member.data.model.Member
import com.android.komiteebicicycle.overview.data.model.Bici
import com.si.f1.f1predictor.core.common.BaseViewModel
import com.si.f1.f1predictor.core.common.UiEffect
import com.si.f1.f1predictor.core.common.UiEvent
import com.si.f1.f1predictor.core.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BiciViewModel @Inject constructor(
    private val biciDao: BiciDao,
    private val memberDao: MemberDao,
    private val contributionDao: ContributionDao,
    private val navigator: Navigator
) : BaseViewModel<BiciContract.Event, BiciContract.State, BiciContract.Effect>() {
    init {
        getBiciWithMembers()
    }

    private fun getBiciWithMembers() {
        viewModelScope.launch {
            val biciList = biciDao.getAllBiciWithMembers()
            setState {
                copy(
                    biciList = biciList,
                )
            }

        }
    }

    fun loadMembers() {
        viewModelScope.launch {
            val memberList = memberDao.getAllMembers()

            setState {
                copy(
                    members = memberList
                )
            }
        }
    }

    fun addBici(
        title: String,
        totalAmount: Double,
        startDate: String,
        endDate: String,
        members: List<Member>
    ) {
        viewModelScope.launch {
            val newBici = Bici(
                title = title,
                totalAmount = totalAmount,
                startDate = startDate,
                endDate = endDate,
            )
            val biciId = biciDao.insertBici(newBici)

            members.forEach { member ->
                biciDao.insertBiciMemberCrossRef(
                    BiciMemberCrossRef(biciId = biciId.toInt(), memberId = member.memberId)
                )
            }

            addContribution(
                biciId = biciId.toInt(),
                startDate = startDate,
                endDate = endDate,
                members = members,
                totalAmount = totalAmount
            )

            getBiciWithMembers()
        }
    }

    private fun addContribution(
        biciId: Int,
        startDate: String,
        endDate: String,
        members: List<Member>,
        totalAmount: Double
    ) {
        viewModelScope.launch {
            val months = getMonthsForBici(startDate, endDate)
            val memberCount = members.size
            val amountPerMember = totalAmount / memberCount

            months.forEach { month ->
                // Create contributions for all members if not present
                val newContributions = members.map { member ->
                    Contribution(
                        biciId = biciId,
                        memberId = member.memberId,
                        month = month,
                        amount = amountPerMember
                    )
                }
                newContributions.forEach { contributionDao.insertContribution(it) }
            }
        }
    }

    override fun createInitialState(): BiciContract.State {
        return BiciContract.State()
    }

    override fun handleEvent(event: BiciContract.Event) {
        when (event) {
            is BiciContract.Event.addBici -> {
                addBici(
                    event.title,
                    event.totalAmount,
                    event.startDate,
                    event.endDate,
                    event.members
                )
            }

            BiciContract.Event.OnCreateBici -> {
                viewModelScope.launch {
                    navigator.navigate(Destination.CreateBiciScreen)
                }
            }

            BiciContract.Event.OnBack -> {
                viewModelScope.launch {
                    navigator.navigateUp()
                }
            }

            BiciContract.Event.NavigateToAddMember -> {
                viewModelScope.launch {
                    navigator.navigate(Destination.AddMemberScreen)
                }
            }

            is BiciContract.Event.NavigateToDetails -> {
                viewModelScope.launch {
                    navigator.navigate(Destination.BiciDetailsScreen(event.biciId))
                }
            }

            is BiciContract.Event.OnMemberSelected -> {
                setState {
                    copy(
                        selectedMembers = currentState.selectedMembers.toMutableList().apply {
                            if (contains(event.member)) remove(event.member) else add(event.member)
                        }
                    )
                }
            }
        }
    }
}

class BiciContract {

    sealed class Event : UiEvent {

        object OnCreateBici : Event()
        object OnBack : Event()
        object NavigateToAddMember : Event()
        data class NavigateToDetails(val biciId: Int) : Event()

        data class OnMemberSelected(val member: Member) : Event()

        data class addBici(
            val title: String,
            val totalAmount: Double,
            val startDate: String,
            val endDate: String,
            val members: List<Member>
        ) : Event()
    }

    data class State(
        val biciList: List<BiciWithMembers> = listOf(),
        val members: List<Member> = listOf(),
        val selectedMembers: List<Member> = listOf()
    ) : UiState

    sealed class Effect : UiEffect

}