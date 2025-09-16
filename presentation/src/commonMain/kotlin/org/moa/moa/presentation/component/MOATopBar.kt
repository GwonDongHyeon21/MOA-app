package org.moa.moa.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.left_arrow_icon
import moa.presentation.generated.resources.top_logo
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.navigation.sign.SignNavigationItem
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.TOP_BAR_HEIGHT

@Composable
fun MOATopBar(
    currentScreen: String?,
    onClickBack: () -> Unit,
) {
    when (currentScreen) {
        SignNavigationItem.OnBoarding.route -> {}
        SignNavigationItem.SignUp.route -> {}
        else -> MOADefaultTopBar(Modifier)
    }
}

@Composable
fun MOADefaultTopBar(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(TOP_BAR_HEIGHT)
            .padding(horizontal = APP_HORIZONTAL_PADDING1)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Image(
            painter = painterResource(Res.drawable.top_logo),
            contentDescription = "top_logo",
            contentScale = ContentScale.Fit,
            modifier = modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun MOABackTopBar(
    modifier: Modifier,
    onClickBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp)
    ) {
        IconButton(
            onClick = { onClickBack() },
            modifier = modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(Res.drawable.left_arrow_icon),
                contentDescription = "GoBackIcon"
            )
        }
        Image(
            painter = painterResource(Res.drawable.top_logo),
            contentDescription = "top_logo",
            modifier = modifier
                .align(Alignment.Center)
                .size(50.dp),
            contentScale = ContentScale.Fit
        )
    }
}