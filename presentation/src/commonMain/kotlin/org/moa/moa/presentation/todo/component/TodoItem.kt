package org.moa.moa.presentation.todo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moa.domain.model.response.TodoItemResponse
import kotlinx.coroutines.MainScope
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.cancel
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.todo.component.TodoItemDimens.endPadding
import org.moa.moa.presentation.todo.component.TodoItemDimens.roundedCornerShape
import org.moa.moa.presentation.todo.component.TodoItemDimens.startPadding
import org.moa.moa.presentation.todo.component.TodoItemDimens.verticalPadding
import org.moa.moa.presentation.ui.theme.WHITE

private object TodoItemDimens {
    val roundedCornerShape = RoundedCornerShape(15.dp)
    val verticalPadding = 12.dp
    val startPadding = 16.dp
    val endPadding = 14.dp
}

@Composable
fun TodoItem(
    modifier: Modifier,
    todo: TodoItemResponse,
    onTodoDelete: () -> Unit,
) {
    Row(
        modifier = modifier
            .clip(roundedCornerShape)
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = verticalPadding)
            .padding(start = startPadding, end = endPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(33.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(WHITE)
        )

        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = todo.content,
            fontSize = 15.sp,
            textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(Res.drawable.cancel),
            contentDescription = "TodoDelete",
            modifier = Modifier.clickable(
                interactionSource = null,
                indication = null,
                onClick = { onTodoDelete() }
            )
        )
    }
}