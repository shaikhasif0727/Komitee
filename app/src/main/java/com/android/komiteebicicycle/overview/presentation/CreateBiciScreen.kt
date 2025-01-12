package com.android.komiteebicicycle.overview.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.komiteebicicycle.core.utils.parseDate
import com.android.komiteebicicycle.core.utils.showDatePickerDialog
import com.android.komiteebicicycle.member.data.model.Member

@Composable
internal fun CreateBiciScreenRoot(
    viewModel: BiciViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadMembers()
    }

    CreateBiciScreen(
        state = state,
        onAction = viewModel::setEvent
    )

}

@Composable
private fun CreateBiciScreen(
    state: BiciContract.State,
    onAction: (BiciContract.Event) -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var totalAmount by rememberSaveable { mutableStateOf("") }
    var startDate by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableStateOf("") }
    val selectedMembers = remember { mutableStateListOf<Member>() }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Create Bici",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BasicTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (title.isEmpty()) {
                        Text("Enter Bici Title", style = MaterialTheme.typography.bodyMedium)
                    }
                    innerTextField()
                }
            }
        )

        BasicTextField(
            value = totalAmount,
            onValueChange = { totalAmount = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (totalAmount.isEmpty()) {
                        Text("Enter Total Amount", style = MaterialTheme.typography.bodyMedium)
                    }
                    innerTextField()
                }
            }
        )

        // Start Date Picker
        Button(
            onClick = {
                showDatePickerDialog(context) { selectedDate ->
                    startDate = selectedDate
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (startDate.isEmpty()) "Select Start Date" else "Start Date: $startDate")
        }

        // End Date Picker
        Button(
            onClick = {
                showDatePickerDialog(context) { selectedDate ->
                    endDate = selectedDate
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (endDate.isEmpty()) "Select End Date" else "End Date: $endDate")
        }

        // Member Selection Section
        Text(
            text = "Select Members",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 16.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            state.members.forEach { member ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = state.selectedMembers.contains(member),
                        onCheckedChange = { isChecked ->
                            onAction.invoke(BiciContract.Event.OnMemberSelected(member))
                        }
                    )
                    Text(text = member.name, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        // Add New Member Button
        Button(
            onClick = {
                onAction.invoke(BiciContract.Event.NavigateToAddMember)
                // Navigate to Add Member screen or show dialog to add new member
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add New Member")
        }

        Spacer(modifier = Modifier.weight(1f))

        // Create Bici Button
        Button(onClick = {
            val total = totalAmount.toDoubleOrNull()
            val start = parseDate(startDate)
            val end = parseDate(endDate)

            if (!title.isEmpty() && total != null && state.selectedMembers.isNotEmpty() && start != null && end != null && end.after(
                    start
                )
            ) {
                onAction.invoke(
                    BiciContract.Event.addBici(
                        title,
                        total,
                        startDate,
                        endDate,
                        state.selectedMembers
                    )
                )
                onAction.invoke(BiciContract.Event.OnBack)
            } else {
                // Validation feedback can be added here
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Create Bici")
        }

        Button(onClick = {
            onAction.invoke(BiciContract.Event.OnBack)

        }, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}

@Preview
@Composable
private fun CreateBiciScreenPreview() {
    CreateBiciScreen(
        state = BiciContract.State(),
        onAction = {}
    )
}