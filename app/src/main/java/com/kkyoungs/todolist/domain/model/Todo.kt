package com.kkyoungs.todolist.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity
data class Todo(
    val title:String,
    val date: Long = Calendar.getInstance().timeInMillis,
    val isDone : Boolean = false,

) {
    @PrimaryKey(autoGenerate = true)
    // autoGenerate True 옵션을 주면 이 값은 자동으로 다시 마지막 제일 가장 큰 아이디를 가지는 항목을 찾아서 그거보다 하나 증가되는 아이디를 자동으로 부여
    var uid : Int = -1

}