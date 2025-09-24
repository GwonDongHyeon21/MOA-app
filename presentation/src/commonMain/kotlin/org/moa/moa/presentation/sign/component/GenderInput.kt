package org.moa.moa.presentation.sign.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.moa.moa.presentation.sign.Gender
import org.moa.moa.presentation.ui.theme.BLACK
import org.moa.moa.presentation.ui.theme.GRAY2
import org.moa.moa.presentation.ui.theme.GRAY7
import org.moa.moa.presentation.ui.theme.MAIN
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.transparent

@Composable
fun GenderInput(
    gender: Gender?,
    onValueChange: (Gender) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = Strings.pleaseEnterGender,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = Strings.pleaseEnterGender2,
            fontSize = 15.sp,
            color = GRAY7
        )

        Spacer(Modifier.height(40.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { onValueChange(Gender.MEN) },
                content = {
                    Text(
                        text = Strings.man,
                        fontSize = 15.sp,
                        color = BLACK
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(15.dp))
                    .background(if (gender == Gender.MEN) MAIN else transparent)
                    .border(
                        width = 1.dp,
                        color = if (gender == Gender.MEN) MAIN else GRAY2,
                        shape = RoundedCornerShape(15.dp)
                    )
            )
            TextButton(
                onClick = { onValueChange(Gender.WOMEN) },
                content = {
                    Text(
                        text = Strings.woman,
                        fontSize = 15.sp,
                        color = BLACK
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(15.dp))
                    .background(if (gender == Gender.WOMEN) MAIN else transparent)
                    .border(
                        width = 1.dp,
                        color = if (gender == Gender.WOMEN) MAIN else GRAY2,
                        shape = RoundedCornerShape(15.dp)
                    )
            )
        }
    }
}