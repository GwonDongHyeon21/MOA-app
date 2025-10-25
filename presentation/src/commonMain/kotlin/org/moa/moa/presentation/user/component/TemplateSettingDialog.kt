package org.moa.moa.presentation.user.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.IVORY
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.textStyle2
import org.moa.moa.presentation.user.component.TemplateSettingDialogDimens.columnSpace
import org.moa.moa.presentation.util.TokenUtils

private object TemplateSettingDialogDimens {
    val columnSpace = 12.dp
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateSettingDialog(
    modifier: Modifier,
    personas: List<Pair<String, Int>>,
    onDismissRequest: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    BasicAlertDialog(
        onDismissRequest = { onDismissRequest() },
        content = {
            Card(modifier = modifier) {
                Column(
                    modifier = Modifier.padding(APP_HORIZONTAL_PADDING1),
                    verticalArrangement = Arrangement.spacedBy(columnSpace),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = Strings.set_template,
                        modifier = Modifier.padding(columnSpace),
                        style = textStyle2
                    )
                    personas.forEach { persona ->
                        val (personaText, personaInt) = persona
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(IVORY.copy(0.4f))
                                .clickable {
                                    coroutineScope.launch {
                                        TokenUtils.savePersona(personaInt)
                                        onDismissRequest()
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = personaText,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    )
}