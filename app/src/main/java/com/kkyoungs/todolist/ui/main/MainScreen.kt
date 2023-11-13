package com.kkyoungs.todolist.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.kkyoungs.todolist.R
import com.kkyoungs.todolist.ui.main.components.TodoItem
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel) {
    // meterial 3 는 remeberScaffoldState 사용 안함
    val scaffoldState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("오늘 할 일") })
        },
        snackbarHost = { SnackbarHost(scaffoldState) },
    ){
        Column(modifier =  Modifier.fillMaxSize()) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),

                value = text, onValueChange = {
                    text = it
                    // placeHolder -- > hint
                }, placeholder = { Text(text = "할일")},
                trailingIcon = {
                    Icon(painter = painterResource(id = R.drawable.baseline_add_24), contentDescription = null)
                },
                maxLines = 1,
                // 하단 오른쪽 키보드 버튼을 체크로 바꾸기
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    // 추가되게하는거 // 추가되고 공백처리 하기
                    viewModel.addTodo(text)
                    text = ""
                    keyboardController?.hide()
                })
                )
            // 줄 나누기
            Divider()
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp),){
                items(viewModel.items.value){ todoItem ->
                    Column {
                        TodoItem(todo = todoItem, onClick = {
                            todo -> viewModel.toggle(todo.uid)
                        },
                            onDeleteClick = {
                                todo-> viewModel.delete(todo.uid)

                                scope.launch {
                                   val result = scaffoldState.showSnackbar(
                                        message = "할일 삭제됨",
                                        actionLabel = "취소",
                                        )
                                if (result == SnackbarResult.ActionPerformed){
                                    viewModel.restoreTodo()
                                }}
                            })
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = Color.Black, thickness = 1.dp)

                    }
                }
            }
        }
    }
}