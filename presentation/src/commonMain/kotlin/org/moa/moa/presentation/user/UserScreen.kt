package org.moa.moa.presentation.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.pencil
import moa.presentation.generated.resources.right_arrow_icon
import moa.presentation.generated.resources.user_background
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.moa.moa.presentation.UiState
import org.moa.moa.presentation.component.MOAErrorScreen
import org.moa.moa.presentation.component.MOALoadingScreen
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING2
import org.moa.moa.presentation.ui.theme.BOTTOM_PADDING_CENTER
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.IVORY
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.textStyle1
import org.moa.moa.presentation.ui.theme.textStyle2
import org.moa.moa.presentation.user.UserScreenDimens.buttonPadding
import org.moa.moa.presentation.user.UserScreenDimens.buttonRoundedCornerShape
import org.moa.moa.presentation.user.UserScreenDimens.columnSpace
import org.moa.moa.presentation.user.UserScreenDimens.verticalPadding
import org.moa.moa.presentation.user.component.TemplateSettingDialog

private object UserScreenDimens {
    val verticalPadding = 20.dp
    val buttonPadding = 15.dp

    val columnSpace = 12.dp

    val buttonRoundedCornerShape = RoundedCornerShape(15.dp)
}

@Composable
fun UserScreen(viewModel: UserViewModel = koinInject()) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState.screenState) {
        UiState.DEFAULT -> Unit
        UiState.SUCCESS -> UserScreen(uiState = uiState)
        UiState.LOADING -> MOALoadingScreen(modifier = Modifier)
        UiState.ERROR -> MOAErrorScreen(modifier = Modifier)
    }
}

@Composable
private fun UserScreen(uiState: UserUiState) {
    var isTemplateExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = verticalPadding)
            .padding(bottom = BOTTOM_PADDING_CENTER + verticalPadding),
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            UserContentSection(modifier = Modifier.align(Alignment.TopStart))

            Image(
                painter = painterResource(Res.drawable.user_background),
                contentDescription = null,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = APP_HORIZONTAL_PADDING1),
            verticalArrangement = Arrangement.spacedBy(columnSpace)
        ) {
            uiState.settings.forEach { setting ->
                SettingButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = setting,
                    onSettingClicked = {
                        when (it) {
                            uiState.settings[0] -> {}
                            uiState.settings[1] -> isTemplateExpanded = !isTemplateExpanded
                            uiState.settings[2] -> {}
                        }
                    }
                )
            }
        }
    }

    if (isTemplateExpanded) {
        TemplateSettingDialog(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth(0.8f),
            personas = uiState.personas,
            onDismissRequest = { isTemplateExpanded = false }
        )
    }
}

@Composable
fun UserContentSection(modifier: Modifier) {
    Column(modifier = modifier.padding(start = APP_HORIZONTAL_PADDING2)) {
        Text(
            text = Strings.MY_PAGE,
            modifier = Modifier.padding(start = APP_HORIZONTAL_PADDING1),
            fontSize = 27.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = Strings.nickname,
            color = GRAY1,
            style = textStyle2
        )

        Spacer(modifier = Modifier.height(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "화이팅", // TODO: User api 구현 후에 수정
                fontSize = 25.sp,
                textDecoration = TextDecoration.Underline,
            )
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(Res.drawable.pencil),
                    contentDescription = "EditNickname"
                )
            }
        }
    }
}

@Composable
fun SettingButton(
    modifier: Modifier,
    text: String,
    onSettingClicked: (String) -> Unit,
) {
    Button(
        onClick = { onSettingClicked(text) },
        modifier = modifier,
        shape = buttonRoundedCornerShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = IVORY
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(buttonPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = textStyle1
            )
            Icon(
                painter = painterResource(Res.drawable.right_arrow_icon),
                contentDescription = null
            )
        }
    }
}