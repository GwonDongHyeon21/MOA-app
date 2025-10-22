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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moa.domain.model.response.TodoItemResponse
import moa.presentation.generated.resources.Res
import moa.presentation.generated.resources.cancel
import moa.presentation.generated.resources.check
import org.jetbrains.compose.resources.painterResource
import org.moa.moa.presentation.component.MOADialog
import org.moa.moa.presentation.todo.component.TodoItemDimens.endPadding
import org.moa.moa.presentation.todo.component.TodoItemDimens.roundedCornerShape
import org.moa.moa.presentation.todo.component.TodoItemDimens.startPadding
import org.moa.moa.presentation.todo.component.TodoItemDimens.verticalPadding
import org.moa.moa.presentation.ui.theme.GRAY1
import org.moa.moa.presentation.ui.theme.IVORY
import org.moa.moa.presentation.ui.theme.Strings
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
    onDoneChanged: () -> Unit,
    onTodoDelete: () -> Unit,
) {
    var isDialogExpended by remember { mutableStateOf(false) }

    val backgroundColor = if (todo.done) IVORY else MaterialTheme.colorScheme.primary

    Row(
        modifier = modifier
            .clip(roundedCornerShape)
            .background(backgroundColor)
            .padding(vertical = verticalPadding)
            .padding(start = startPadding, end = endPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(33.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(WHITE)
                .clickable { onDoneChanged() },
            contentAlignment = Alignment.Center
        ) {
            if (todo.done) {
                Icon(
                    painter = painterResource(Res.drawable.check),
                    contentDescription = "TodoCheck",
                    tint = GRAY1
                )
            }
        }

        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = todo.content,
            modifier = Modifier.weight(1f),
            fontSize = 15.sp,
            textDecoration = TextDecoration.Underline,
        )

        Icon(
            painter = painterResource(Res.drawable.cancel),
            contentDescription = "TodoDelete",
            modifier = Modifier
                .padding(start = 15.dp)
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = { isDialogExpended = true }
                )
        )
    }

    if (isDialogExpended) {
        MOADialog(
            title = Strings.todo_delete,
            text = Strings.todo_delete_guideline,
            confirmText = Strings.delete,
            dismissText = Strings.cancel,
            onClickConfirm = { onTodoDelete() },
            onClickDismiss = { isDialogExpended = false }
        )
    }
}