package com.android.komiteebicicycle.member.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun MemberScreenRoot(
    viewModel: MemberViewModel = hiltViewModel(),
) {

    val state by viewModel.uiState.collectAsState()

    MemberScreen(
        state = state,
        onAction = viewModel::setEvent
    )
}

@Composable
private fun MemberScreen(
    state: MemberContract.State,
    onAction: (MemberContract.Event) -> Unit
) {
    var newMemberName by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Bici System",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Add New Member Section
        Row(verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = newMemberName,
                onValueChange = { newMemberName = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (newMemberName.text.isEmpty()) {
                            Text("Enter member name", style = MaterialTheme.typography.bodyMedium)
                        }
                        innerTextField()
                    }
                }
            )

            Button(onClick = {
                if (newMemberName.text.isNotEmpty()) {
                    onAction.invoke(MemberContract.Event.AddMember(newMemberName.text))
                    newMemberName = TextFieldValue()
                }
            }) {
                Text("Add")
            }
        }

        // Members List
        Text(
            text = "Members:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            state.members.forEach { member ->
                Text(
                    text = "\u2022 ${member.name}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Navigation Buttons
        Button(
            onClick = {
                onAction.invoke(MemberContract.Event.onNavigateToContributions)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Contributions")
        }

        Button(
            onClick = {
                onAction.invoke(MemberContract.Event.onNavigateToDraw)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Perform Draw")
        }

        Button(
            onClick = {
                onAction.invoke(MemberContract.Event.onNavigateToHistory)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View History")
        }
    }
}

@Preview
@Composable
private fun MemberScreenPreview() {
    MemberScreen(
        state = MemberContract.State(),
        onAction = {}
    )
}

