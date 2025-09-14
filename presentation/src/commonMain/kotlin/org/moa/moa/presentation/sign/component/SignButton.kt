package org.moa.moa.presentation.sign.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.right_arrow_icon
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.ui.theme.IVORY
import org.moa.moa.presentation.ui.theme.Strings
import org.moa.moa.presentation.ui.theme.WHITE

@Composable
fun SignButton(
    loginOptionText: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(47.dp))
            .clickable { onClick() }
            .background(IVORY)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(WHITE)
        )

        Spacer(Modifier.width(20.dp))
        Text(text = loginOptionText)

        Spacer(Modifier.weight(1f))
        Icon(
            painter = painterResource(Res.drawable.right_arrow_icon),
            contentDescription = "right_arrow_icon",
            modifier = Modifier.padding(end = 10.dp)
        )
    }
}