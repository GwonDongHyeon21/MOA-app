package org.moa.moa.presentation.home.record.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.app_logo
import moa.presentation.generated.resources.combine_record_guide
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.component.BounceImage

@Composable
fun HomeRecordLoading(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(2f))
        Image(
            painter = painterResource(Res.drawable.combine_record_guide),
            contentDescription = null
        )
        Spacer(modifier = Modifier.weight(1f))
        BounceImage(
            painter = painterResource(Res.drawable.app_logo),
            amplitude = 40.dp,
            periodMs = 1_600
        )
        Spacer(modifier = Modifier.weight(2f))
    }
}
