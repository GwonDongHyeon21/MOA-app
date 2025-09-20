package org.moa.moa.presentation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.calendar_button_icon
import moa.presentation.generated.resources.home_button_icon
import moa.presentation.generated.resources.main_button_icon
import moa.presentation.generated.resources.my_button_icon
import moa.presentation.generated.resources.record_camera
import moa.presentation.generated.resources.record_record
import moa.presentation.generated.resources.record_text
import moa.presentation.generated.resources.todo_button_icon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.navigation.calendar.CalendarNavigationItem
import org.moa.moa.navigation.home.HomeNavigationItem
import org.moa.moa.navigation.record.RecordNavigationItem
import org.moa.moa.navigation.sign.SignNavigationItem
import org.moa.moa.navigation.todo.TodoNavigationItem
import org.moa.moa.navigation.user.UserNavigationItem
import org.moa.moa.presentation.ui.theme.IVORY
import org.moa.moa.presentation.ui.theme.IVORY4
import org.moa.moa.presentation.ui.theme.MAIN
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.unspecified
import org.moa.moa.util.dpToPx

object BottomCenter {
    val bottomPadding = 20.dp
    val height = 35.dp
    val size = 80.dp
    val centerHeight = height + size / 2
    const val PROGRESS_DURATION_MILLS = 500
}

@Composable
fun MOABottomBar(
    navController: NavController,
    currentScreen: String?,
) {
    val isBottomBar = currentScreen !in listOf(
        SignNavigationItem.OnBoarding.route,
        SignNavigationItem.SignUp.route,
        RecordNavigationItem.RecordText.route,
    )
    val bottomItems = listOf(
        Triple(Strings.home, Res.drawable.home_button_icon, HomeNavigationItem.Home.route),
        Triple(
            Strings.calendar,
            Res.drawable.calendar_button_icon,
            CalendarNavigationItem.Calendar.route
        ),
        Triple(Strings.moa, Res.drawable.main_button_icon, RecordNavigationItem.MainButton.route),
        Triple(Strings.todo, Res.drawable.todo_button_icon, TodoNavigationItem.Todo.route),
        Triple(Strings.user, Res.drawable.my_button_icon, UserNavigationItem.User.route),
    )
    var tabScreen by remember { mutableStateOf(bottomItems.first().third) }

    LaunchedEffect(currentScreen) {
        if (bottomItems.any { it.third == currentScreen }) tabScreen = currentScreen!!
    }

    if (isBottomBar) BottomBar(navController, bottomItems, tabScreen)
}

@Composable
fun BottomBar(
    navController: NavController,
    bottomItems: List<Triple<String, DrawableResource, String>>,
    tabScreen: String,
) {
    var centerExpanded by remember { mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (centerExpanded) 1f else 0f,
        animationSpec = tween(
            durationMillis = BottomCenter.PROGRESS_DURATION_MILLS,
            easing = FastOutSlowInEasing
        ),
        label = "bottom_center_button"
    )

    Box(contentAlignment = Alignment.BottomCenter) {
        CenterBackdrop(progress, dpToPx(BottomCenter.centerHeight))
        RecordOptions(centerExpanded) {
            when (it) {
                0 -> navController.navigate(RecordNavigationItem.RecordText.route)
                1 -> navController.navigate(RecordNavigationItem.RecordCamera.route)
                2 -> navController.navigate(RecordNavigationItem.RecordRecord.route)
            }
        }
        CustomBottomBar()

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = BottomCenter.bottomPadding),
            verticalAlignment = Alignment.Bottom
        ) {
            bottomItems.forEachIndexed { index, item ->
                val isSelected = tabScreen == item.third
                if (index == bottomItems.size / 2) {
                    Box(
                        modifier = Modifier.weight(1f).offset(y = -BottomCenter.height),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(BottomCenter.size)
                                .clip(CircleShape)
                                .background(MAIN)
                                .clickable(onClick = {
                                    centerExpanded = !centerExpanded
                                })
                        )
                        Image(
                            painter = painterResource(Res.drawable.main_button_icon),
                            contentDescription = Strings.moa,
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    navController.navigate(item.third) {
                                        popUpTo(bottomItems.first().third) { inclusive = false }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
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
}

@Composable
fun CustomBottomBar() {
    val firstDpToPx = dpToPx(BottomCenter.size / 2 + 15.dp)
    val secondDpToPx = dpToPx(BottomCenter.size / 2 + 5.dp)

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
private fun CenterBackdrop(progress: Float, bottomCenterHeight: Float) {
    val density = LocalDensity.current
    val maxRadius = with(density) { (BottomCenter.size * 3 / 2).toPx() }
    val minRadius = with(density) { (BottomCenter.size / 2).toPx() }
    val radius = lerp(minRadius, maxRadius, progress)

    Canvas(modifier = Modifier.padding(bottom = BottomCenter.bottomPadding).zIndex(0f)) {
        val x = size.width / 2f
        val y = size.height - bottomCenterHeight
        drawCircle(
            color = IVORY4,
            radius = radius,
            center = Offset(x, y),
        )
    }
}

@Composable
fun RecordOptions(
    expanded: Boolean,
    onClick: (Int) -> Unit,
) {
    val height = BottomCenter.size * 3 / 2 + BottomCenter.centerHeight + BottomCenter.bottomPadding
    val size by animateDpAsState(
        targetValue = if (expanded) BottomCenter.size * 3 / 4 else 0.dp,
        animationSpec = tween(durationMillis = BottomCenter.PROGRESS_DURATION_MILLS),
        label = "icon_size"
    )

    Row(
        modifier = Modifier
            .height(height)
            .padding(top = BottomCenter.bottomPadding / 2),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column {
            IconButton(
                onClick = { onClick(0) },
                modifier = Modifier.size(size)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.record_text),
                    contentDescription = "record_text",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(height - BottomCenter.size - BottomCenter.bottomPadding))
        }
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            IconButton(
                onClick = { onClick(1) },
                modifier = Modifier.size(size)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.record_camera),
                    contentDescription = "record_camera",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(height - BottomCenter.size * 3 / 4))
        }
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            IconButton(
                onClick = { onClick(2) },
                modifier = Modifier.size(size)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.record_record),
                    contentDescription = "record_record",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(height - BottomCenter.size - BottomCenter.bottomPadding))
        }
    }
}