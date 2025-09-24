package org.moa.moa.presentation.sign.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.moa.moa.presentation.ui.theme.BLACK
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.GRAY2
import org.moa.moa.presentation.ui.theme.GRAY7
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.transparent

@Composable
fun UserIdInput(
    userId: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = Strings.pleaseEnterId,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = Strings.pleaseEnterId2,
            fontSize = 15.sp,
            color = GRAY7
        )

        Spacer(Modifier.height(40.dp))
        OutlinedTextField(
            value = userId,
            onValueChange = { if (it.length < 20) onValueChange(it) },
            modifier = Modifier.fillMaxWidth().border(1.dp, GRAY2, RoundedCornerShape(15.dp)),
            placeholder = {
                Text(
                    text = Strings.enterPlaceholder,
                    fontSize = 15.sp,
                    color = GRAY1
                )
            },
            textStyle = TextStyle(fontSize = 15.sp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = transparent,
                unfocusedContainerColor = transparent,
                focusedIndicatorColor = transparent,
                unfocusedIndicatorColor = transparent,
                cursorColor = BLACK
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
    }
}