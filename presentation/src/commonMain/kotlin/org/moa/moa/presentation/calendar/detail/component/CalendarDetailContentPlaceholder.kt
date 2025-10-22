package org.moa.moa.presentation.calendar.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.record_background_bottom
import moa.presentation.generated.resources.record_background_left
import moa.presentation.generated.resources.record_background_right
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.calendar.detail.component.CalendarDetailContentPlaceholderDimes.CONTENT_PLACEHOLDER_HEIGHT_FRACTION
import org.moa.moa.presentation.calendar.detail.component.CalendarDetailContentPlaceholderDimes.calendarDetailRoundedCorner
import org.moa.moa.presentation.ui.theme.APP_HORIZONTAL_PADDING1
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.WHITE

private object CalendarDetailContentPlaceholderDimes {
    const val CONTENT_PLACEHOLDER_HEIGHT_FRACTION = 0.5f
    val calendarDetailRoundedCorner = RoundedCornerShape(25.dp)
}

@Composable
fun CalendarDetailContentPlaceholder(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight(CONTENT_PLACEHOLDER_HEIGHT_FRACTION)
            .fillMaxWidth()
            .padding(horizontal = APP_HORIZONTAL_PADDING1)
            .clip(calendarDetailRoundedCorner)
            .background(WHITE)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = Strings.empty_record_placeholder,
            fontSize = 17.sp,
            color = GRAY1
        )

        Spacer(modifier = Modifier.height(15.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(GRAY1)
                    )
                }
            }

            Image(
                painter = painterResource(Res.drawable.record_background_left),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Image(
                painter = painterResource(Res.drawable.record_background_right),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopEnd)
            )
            Image(
                painter = painterResource(Res.drawable.record_background_bottom),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 50.dp)
            )
        }
    }
}