package com.android.komiteebicicycle.presentation.home

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

@Composable
fun HomeScreen(
    members: List<Member>,
    onAddMember: (String) -> Unit,
    onNavigateToContributions: () -> Unit,
    onNavigateToDraw: () -> Unit,
    onNavigateToHistory: () -> Unit
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
                    onAddMember(newMemberName.text)
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
            members.forEach { member ->
                Text(
                    text = "\u2022 ${member.name}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Navigation Buttons
        Button(
            onClick = onNavigateToContributions,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Contributions")
        }

        Button(
            onClick = onNavigateToDraw,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Perform Draw")
        }

        Button(
            onClick = onNavigateToHistory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View History")
        }
    }
}

// Dummy Member Class for Preview
data class Member(val id: Int, val name: String)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        members = listOf(
            Member(1, "John Doe"),
            Member(2, "Jane Smith"),
            Member(3, "Alex Johnson")
        ),
        onAddMember = {},
        onNavigateToContributions = {},
        onNavigateToDraw = {},
        onNavigateToHistory = {}
    )
}
