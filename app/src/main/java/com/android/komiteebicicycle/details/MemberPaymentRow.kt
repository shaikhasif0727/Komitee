package com.android.komiteebicicycle.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.komiteebicicycle.contribution.data.model.Contribution
import com.android.komiteebicicycle.member.data.model.Member

@Composable
fun MemberPaymentRow(
    member: Member?,
    contribution: Contribution,
    onUpdate: (paymentMethod: String, isPaid: Boolean) -> Unit
) {
    var paymentMethod by remember(member,contribution) { mutableStateOf(contribution.paymentMethod ?: "") }
    var isPaid by remember(member,contribution) { mutableStateOf(contribution.isPaid) }
    var showDoneButton by remember(member,contribution) { mutableStateOf(false) }
    var showPaymentTypeMenu by remember(member,contribution) {
        mutableStateOf(false)
    }

    member?.let {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = member.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            // Payment Method Dropdown
            Box(modifier = Modifier.wrapContentSize()) {

                Text(
                    modifier = Modifier.clickable {
                        showPaymentTypeMenu = true
                    },
                    text = paymentMethod.ifEmpty { "Payment Type" }
                )

                DropdownMenu(
                    expanded = showPaymentTypeMenu,
                    onDismissRequest = { showPaymentTypeMenu = false }
                ) {

                    DropdownMenuItem(text = {
                        Text("Cash")
                    }, onClick = {
                        paymentMethod = "Cash"
                        showPaymentTypeMenu = false
                        showDoneButton = true
                    })
                    DropdownMenuItem(text = {
                        Text("Online")
                    }, onClick = {
                        paymentMethod = "Online"
                        showPaymentTypeMenu = false
                        showDoneButton = true
                    })

                }
            }

            // Paid Checkbox
            Checkbox(
                checked = isPaid,
                onCheckedChange = {
                    isPaid = it
                    showDoneButton = true
                }
            )

            // Done Button
            if (showDoneButton) {
                Button(onClick = {
                    if (isPaid && paymentMethod.isNotEmpty()){
                        onUpdate(paymentMethod, isPaid)
                    } else if (!isPaid){
                        onUpdate("", isPaid)
                    }

                    showDoneButton = false
                }) {
                    Text("Done")
                }
            }
        }
    }
}
