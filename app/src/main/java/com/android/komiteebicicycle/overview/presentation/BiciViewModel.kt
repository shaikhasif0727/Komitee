package com.android.komiteebicicycle.overview.presentation

import androidx.lifecycle.viewModelScope
import com.android.komiteebicicycle.core.room.dao.BiciDao
import com.android.komiteebicicycle.core.room.dao.MemberDao
import com.android.komiteebicicycle.core.naviagtion.Destination
import com.android.komiteebicicycle.core.naviagtion.Navigator
import com.android.komiteebicicycle.core.room.dao.BiciMemberCrossRef
import com.android.komiteebicicycle.core.room.model.BiciWithMembers
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
    private val navigator: Navigator
) : BaseViewModel<BiciContract.Event, BiciContract.State, BiciContract.Effect>() {
    init {
        getBiciWithMembers()
    }

    private fun getBiciWithMembers() {
        viewModelScope.launch {
            val biciList = biciDao.getAllBiciWithMembers()
            val memberList = memberDao.getAllMembers()

            setState {
                copy(
                    biciList = biciList,
                    members = memberList
                )
            }

        }
    }

    fun addBici(title: String, totalAmount: Double, startDate: String, endDate: String,members: List<Member>) {
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

            getBiciWithMembers()
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

            BiciContract.Event.NavigateToAddMember ->{
                viewModelScope.launch {
                    navigator.navigate(Destination.AddMemberScreen)
                }
            }

            is BiciContract.Event.NavigateToDetails -> {
                viewModelScope.launch {
                    navigator.navigate(Destination.BiciDetailsScreen(event.biciId))
                }
            }
        }
    }
}

class BiciContract {

    sealed class Event : UiEvent {

        object OnCreateBici: Event()
        object OnBack : Event()
        object NavigateToAddMember : Event()
        data class NavigateToDetails(val biciId: Int) : Event()

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
        val members: List<Member> = listOf()
    ) : UiState

    sealed class Effect : UiEffect

}