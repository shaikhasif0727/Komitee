package com.android.komiteebicicycle.member.presentation

import androidx.lifecycle.viewModelScope
import com.android.komiteebicicycle.core.naviagtion.Destination
import com.android.komiteebicicycle.member.data.model.Member
import com.android.komiteebicicycle.member.domain.repository.HomeRepository
import com.android.komiteebicicycle.core.naviagtion.Navigator
import com.si.f1.f1predictor.core.common.BaseViewModel
import com.si.f1.f1predictor.core.common.UiEffect
import com.si.f1.f1predictor.core.common.UiEvent
import com.si.f1.f1predictor.core.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val navigator: Navigator
) : BaseViewModel<MemberContract.Event, MemberContract.State, MemberContract.Effect>() {

    override fun loadInitialValue() {
        viewModelScope.launch {
            homeRepository.getMembers()
                .onSuccess { members ->
                    setState {
                        copy(
                            members = members
                        )
                    }
                }
        }
    }

    override fun createInitialState(): MemberContract.State {
        return MemberContract.State()
    }

    override fun handleEvent(event: MemberContract.Event) {
        when (event) {
            is MemberContract.Event.AddMember -> {
                viewModelScope.launch {
                    val newMember = Member(name = event.name)
                    homeRepository.addMember(newMember)
                    setState {
                        copy(
                            members = currentState.members + listOf(newMember)
                        )
                    }
                }
            }

            MemberContract.Event.onNavigateToContributions -> {
                viewModelScope.launch {
                    navigator.navigate(Destination.ContributionScreen)
                }
            }

            MemberContract.Event.onNavigateToDraw -> {

            }

            MemberContract.Event.onNavigateToHistory -> {

            }

            else -> {}
        }
    }
}

class MemberContract {

    sealed class Event : UiEvent {
        data class AddMember(val name: String) : Event()

        object onNavigateToContributions : Event()

        object onNavigateToDraw : Event()

        object onNavigateToHistory : Event()

    }

    data class State(
        val members: List<Member> = listOf()
    ) : UiState

    sealed class Effect : UiEffect

}