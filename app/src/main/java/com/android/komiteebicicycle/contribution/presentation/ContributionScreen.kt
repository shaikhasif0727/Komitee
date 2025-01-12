package com.android.komiteebicicycle.contribution.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun ContributionScreenRoot(
    viewModel: ContributionViewModel = hiltViewModel(),
) {

    val state by viewModel.uiState.collectAsState()

    ContributionScreen(
        state = state,
        onAction = viewModel::setEvent
    )
}

@Composable
private fun ContributionScreen(
    state: ContributionContract.State,
    onAction: (ContributionContract.Event) -> Unit
) {
    var selectedMemberId by remember { mutableStateOf<Int?>(null) }
    var contributionAmount by remember { mutableStateOf(TextFieldValue()) }
    var contributionDate by remember { mutableStateOf(TextFieldValue()) }
    var openSelectMember by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add Contribution",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )


        BasicTextField(
            value = contributionAmount,
            onValueChange = { contributionAmount = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (contributionAmount.text.isEmpty()) {
                        Text("Enter amount", style = MaterialTheme.typography.bodyMedium)
                    }
                    innerTextField()
                }
            }
        )

        BasicTextField(
            value = contributionDate,
            onValueChange = { contributionDate = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (contributionDate.text.isEmpty()) {
                        Text("Enter date (YYYY-MM-DD)", style = MaterialTheme.typography.bodyMedium)
                    }
                    innerTextField()
                }
            }
        )

        TextButton(onClick = {
            openSelectMember = true
        }) {
            Text(text = state.getMemberName(selectedMemberId) ?: "Select Member", style = MaterialTheme.typography.bodyMedium)
        }

        Button(onClick = {
            val amount = contributionAmount.text.toDoubleOrNull()
            if (selectedMemberId != null && amount != null) {
                onAction.invoke(
                    ContributionContract.Event.AddContribution(
                        selectedMemberId!!,
                        amount,
                        contributionDate.text
                    )
                )
                contributionAmount = TextFieldValue()
                contributionDate = TextFieldValue()
            }
        }) {
            Text("Add Contribution")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Contributions:",
            style = MaterialTheme.typography.titleMedium
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            state.contributions.forEach { contribution ->
                val memberName =
                    state.members.firstOrNull { it.id == contribution.memberId }?.name ?: "Unknown"
                Text(
                    text = "\u2022 $memberName: \$${contribution.amount} on ${contribution.date}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {
            onAction.invoke(ContributionContract.Event.onBack)
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }

    // Member Selection Dropdown
    DropdownMenu(
        expanded = openSelectMember,
        onDismissRequest = { openSelectMember = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        state.members.forEach { member ->
            DropdownMenuItem(text = {
                Text(text = member.name)
            }, onClick = {
                selectedMemberId = member.id
                openSelectMember = false
            })
        }
    }
}

@Preview
@Composable
private fun ContributionScreenPreview() {
    ContributionScreen(
        state = ContributionContract.State(),
        onAction = {}
    )
}