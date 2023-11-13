package com.kkyoungs.todolist.ui.main

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kkyoungs.todolist.domain.model.Todo
import com.kkyoungs.todolist.domain.repository.TodoRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application, private val todoRepository: TodoRepository) : AndroidViewModel(application) {

    // 처음에는 비어이는 리스트를 세팅
    private val _items = mutableStateOf(emptyList<Todo>())
    val items:State<List<Todo>> = _items

    private var recentlyDelteTodo:Todo?=null


    fun addTodo(text:String){
        //코루틴 ㅅ ㅡ코프 사용
        viewModelScope.launch {
            todoRepository.addTodo(Todo(title = text))
        }
    }
    fun toggle(uid: Int){
        val todo = _items.value.find { todo -> todo.uid == uid }
        todo?.let {
            viewModelScope.launch {
                todoRepository.updateTodo(it.copy(isDone = it.isDone).apply {
                   this.uid = it.uid
                })
            }
        }
    }
    fun delete(uid: Int){
        val todo = _items.value.find{
            todo-> todo.uid == uid
        }
        todo?.let {
            viewModelScope.launch {

                todoRepository.deleteTodo(it)
                recentlyDelteTodo = it
            }
        }
    }
    fun restoreTodo(){
        viewModelScope.launch {
            // 없을때 종료 널이라면 취소
            todoRepository.addTodo(recentlyDelteTodo ?: return@launch)
            recentlyDelteTodo = null
        }

    }
}