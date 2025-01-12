package com.android.komiteebicicycle.home.presentation

import androidx.lifecycle.viewModelScope
import com.android.komiteebicicycle.core.naviagtion.Destination
import com.android.komiteebicicycle.home.data.model.Member
import com.android.komiteebicicycle.home.domain.repository.HomeRepository
import com.android.komiteebicicycle.core.naviagtion.Navigator
import com.si.f1.f1predictor.core.common.BaseViewModel
import com.si.f1.f1predictor.core.common.UiEffect
import com.si.f1.f1predictor.core.common.UiEvent
import com.si.f1.f1predictor.core.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val navigator: Navigator
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

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

    override fun createInitialState(): HomeContract.State {
        return HomeContract.State()
    }

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.AddMember -> {
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

            HomeContract.Event.onNavigateToContributions -> {
                viewModelScope.launch {
                    navigator.navigate(Destination.ContributionScreen)
                }
            }

            HomeContract.Event.onNavigateToDraw -> {

            }

            HomeContract.Event.onNavigateToHistory -> {

            }

            else -> {}
        }
    }
}

class HomeContract {

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