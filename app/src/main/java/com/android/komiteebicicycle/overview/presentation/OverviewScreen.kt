@file:OptIn(ExperimentalMaterial3Api::class)

package com.android.komiteebicicycle.overview.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun OverviewScreenRoot(
    viewModel: BiciViewModel = hiltViewModel(),
) {

    val state by viewModel.uiState.collectAsState()

    OverviewScreen(
        state = state,
        onAction = viewModel::setEvent
    )
}

@Composable
private fun OverviewScreen(
    state: BiciContract.State,
    onAction: (BiciContract.Event) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bici Overview") },
                actions = {
                    IconButton(onClick = {
                        onAction.invoke(BiciContract.Event.OnCreateBici)
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Bici")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Active Bici:",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )

            if (state.biciList.isEmpty()) {
                Text(
                    text = "No active Bici found.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    state.biciList.forEach { biciWithMembers ->
                        val bici = biciWithMembers.bici
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Title: ${bici.title}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Total Amount: \$${bici.totalAmount}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Duration: ${bici.startDate} - ${bici.endDate}", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun OverviewScreenPreview() {
    OverviewScreen(
        state = BiciContract.State(),
        onAction = {}
    )
}