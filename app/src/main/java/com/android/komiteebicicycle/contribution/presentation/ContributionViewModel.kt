package com.android.komiteebicicycle.contribution.presentation

import androidx.lifecycle.viewModelScope
import com.android.komiteebicicycle.contribution.data.model.Contribution
import com.android.komiteebicicycle.contribution.domain.repository.ContributionRepository
import com.android.komiteebicicycle.core.naviagtion.Navigator
import com.android.komiteebicicycle.home.data.model.Member
import com.android.komiteebicicycle.home.domain.repository.HomeRepository
import com.si.f1.f1predictor.core.common.BaseViewModel
import com.si.f1.f1predictor.core.common.UiEffect
import com.si.f1.f1predictor.core.common.UiEvent
import com.si.f1.f1predictor.core.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ContributionViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val contributionRepository: ContributionRepository,
    private val navigator: Navigator
) : BaseViewModel<ContributionContract.Event, ContributionContract.State, ContributionContract.Effect>() {

    override fun loadInitialValue() {
        viewModelScope.launch {
            contributionRepository.getContributions()
                .onSuccess {
                    setState {
                        copy(
                            contributions = it
                        )
                    }
                }

            homeRepository.getMembers()
                .onSuccess {
                    setState {
                        copy(
                            members = it
                        )
                    }
                }
        }
    }

    override fun createInitialState(): ContributionContract.State {
        return ContributionContract.State()
    }

    override fun handleEvent(event: ContributionContract.Event) {
        when (event) {

            is ContributionContract.Event.AddContribution ->{
                viewModelScope.launch {
                    val newContribution = Contribution(memberId = event.memberId, amount = event.amount, date = event.date)
                    contributionRepository.addContribution(newContribution)
                    setState {
                        copy(
                            contributions = contributions + listOf(newContribution)
                        )
                    }
                }

            }
            ContributionContract.Event.onBack -> {
                viewModelScope.launch {
                    navigator.navigateUp()
                }

            }
            else -> {}
        }
    }

}

internal class ContributionContract {

    sealed class Event() : UiEvent {
        data class AddContribution(val memberId: Int,val  amount: Double,val  date: String): Event()
        object onBack : Event()
    }

    data class State(
        val contributions: List<Contribution> = listOf(),
        val members: List<Member> = listOf()
    ) : UiState {

        fun getMemberName(id:Int?) = members.find { it.id == id }?.name


    }

    sealed class Effect() : UiEffect {}

}