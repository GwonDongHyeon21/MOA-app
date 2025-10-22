package com.moa.domain.usecase.todo

class TodoUseCase(
    val getTodos: GetTodos,
    val addTodo: AddTodo,
    val updateTodo: UpdateTodo,
    val deleteTodo: DeleteTodo,
)