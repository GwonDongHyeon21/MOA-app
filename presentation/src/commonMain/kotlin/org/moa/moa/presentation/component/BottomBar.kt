package org.moa.moa.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.main_button_icon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.navigation.Navigator
import org.moa.moa.navigation.screen.Home
import org.moa.moa.navigation.screen.Screen
import org.moa.moa.presentation.ui.theme.IVORY
import org.moa.moa.presentation.ui.theme.MAIN
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.transparent
import org.moa.moa.presentation.ui.theme.unspecified

@Composable
fun BottomBar(
    bottomItems: List<Triple<String, DrawableResource, Screen>>,
    navigator: Navigator,
    tabScreen: String,
) {
    Box(contentAlignment = Alignment.BottomCenter) {
        CustomBottomBar()
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            bottomItems.forEachIndexed { index, item ->
                val isSelected = tabScreen == item.third.route
                if (index == bottomItems.size / 2)
                    Box(
                        modifier = Modifier.weight(1f).offset(y = (-35).dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(MAIN)
                                .clickable(onClick = {

                                })
                        )
                        Image(
                            painter = painterResource(Res.drawable.main_button_icon),
                            contentDescription = Strings.moa,
                        )
                    }
                else
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    navigator.resetAndNavigateTo(
                                        bottomItems.first().third,
                                        item.third
                                    )
                                }
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(item.second),
                            contentDescription = item.first,
                            tint = if (isSelected) MAIN else unspecified
                        )
                        Text(
                            text = item.first,
                            fontSize = 12.sp,
                            color = if (isSelected) MAIN else unspecified,
                        )
                    }
            }
        }
    }
}

@Composable
fun CustomBottomBar() {
    val firstDpToPx = dpToPx(55.dp)
    val secondDpToPx = dpToPx(45.dp)

    Canvas(modifier = Modifier.fillMaxWidth().height(90.dp)) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(width * 0.5f - firstDpToPx, 0f)
            lineTo(0f, 0f)
            lineTo(0f, height)
            lineTo(width, height)
            lineTo(width, 0f)
            lineTo(width * 0.5f + firstDpToPx, 0f)

            cubicTo(
                width * 0.5f + secondDpToPx, height * 0f,
                width * 0.5f + secondDpToPx, height * 0.5f,
                width * 0.5f, height * 0.5f
            )
            cubicTo(
                width * 0.5f - secondDpToPx, height * 0.5f,
                width * 0.5f - secondDpToPx, height * 0f,
                width * 0.5f - firstDpToPx, 0f
            )
            close()
        }

        drawPath(
            path = path,
            color = IVORY
        )
    }
}

@Composable
fun dpToPx(value: Dp): Float {
    val density = LocalDensity.current
    return with(density) { value.toPx() }
}