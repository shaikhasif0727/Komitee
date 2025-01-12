@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.android.komiteebicicycle.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BiciDetailsScreen(
    biciId: Int,
    viewModel: BiciDetailsViewModel = hiltViewModel(),
) {

    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(biciId) {
        viewModel.loadBiciDetails(biciId)
    }

    val currentMonthIndex = remember { mutableStateOf(0) }
    val contributions = viewModel.contributionsByMonth

    state.biciWithMembers?.let { biciWithMembers ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Bici Details") },
                    navigationIcon = {
                        IconButton(onClick = {

                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Bici Information
                Text(
                    text = "Title: ${biciWithMembers.bici.title}",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Total Amount: \$${biciWithMembers.bici.totalAmount}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Text(
                    text = "Duration: ${biciWithMembers.bici.startDate} - ${biciWithMembers.bici.endDate}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Tabs for Months
                val months = viewModel.getMonthsForBici(biciWithMembers.bici.startDate, biciWithMembers.bici.endDate)
                ScrollableTabRow(selectedTabIndex = currentMonthIndex.value) {
                    months.forEachIndexed { index, month ->
                        Tab(
                            selected = currentMonthIndex.value == index,
                            onClick = { currentMonthIndex.value = index }
                        ) {
                            Text(text = month, modifier = Modifier.padding(16.dp))
                        }
                    }
                }

                // Member List for Selected Month
                val selectedMonth = months[currentMonthIndex.value]
                val selectedMonthContributions = contributions[selectedMonth] ?: emptyList()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedMonthContributions.forEach { contribution ->
                        MemberPaymentRow(
                            member = state.getMemberById(contribution.memberId),
                            contribution = contribution,
                            onUpdate = { method, isPaid ->
                                viewModel.updateContribution(contribution, method, isPaid)
                            }
                        )
                    }
                }
            }
        }
    }

}
