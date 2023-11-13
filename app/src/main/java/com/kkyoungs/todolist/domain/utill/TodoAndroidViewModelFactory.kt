package com.kkyoungs.todolist.domain.utill

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kkyoungs.todolist.data.repository.TodoRepositoryImpl
import com.kkyoungs.todolist.domain.repository.TodoRepository
import com.kkyoungs.todolist.ui.main.MainViewModel

class TodoAndroidViewModelFactory(private val application: Application,
    private val repository: TodoRepository = TodoRepositoryImpl(application)) : ViewModelProvider.AndroidViewModelFactory(application){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(application = application, repository) as T
        }
        return super.create(modelClass)
    }
}