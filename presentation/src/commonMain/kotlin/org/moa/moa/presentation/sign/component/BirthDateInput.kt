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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.moa.moa.presentation.ui.theme.BLACK
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.GRAY2
import org.moa.moa.presentation.ui.theme.GRAY7
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.transparent

@Composable
fun BirthDateInput(
    birthDate: String,
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
            text = Strings.pleaseEnterBirthDate,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = Strings.pleaseEnterBirthDate2,
            fontSize = 15.sp,
            color = GRAY7
        )

        Spacer(Modifier.height(40.dp))
        OutlinedTextField(
            value = birthDate,
            onValueChange = { if (it.length <= 8) onValueChange(it) },
            modifier = Modifier.fillMaxWidth().border(1.dp, GRAY2, RoundedCornerShape(15.dp)),
            placeholder = {
                Text(
                    text = Strings.birthDatePlaceholder,
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
            visualTransformation = BirthdateVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )
    }
}

class BirthdateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text.take(8) // 안전장치
        val out = buildString {
            for (i in raw.indices) {
                append(raw[i])
                if (i == 3 && raw.length > 4) append('.')   // YYYY.
                if (i == 5 && raw.length > 6) append('.')   // YYYY.MM.
            }
        }

        // 커서 위치 보정을 위한 매핑
        val offsetMapping = object : OffsetMapping {
            // 원본 → 표시문자열
            override fun originalToTransformed(offset: Int): Int {
                var extra = 0
                if (offset > 4) extra += 1     // dot after 4
                if (offset > 6) extra += 1     // dot after 6
                return offset + extra
            }

            // 표시문자열 → 원본
            override fun transformedToOriginal(offset: Int): Int {
                var extra = 0
                if (offset > 4) extra += 1     // dot index 4
                if (offset > 7) extra += 1     // dot index 7 (4 + 1 + 2)
                return (offset - extra).coerceAtLeast(0)
            }
        }

        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}