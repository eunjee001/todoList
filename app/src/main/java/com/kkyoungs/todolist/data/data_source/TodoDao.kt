package com.kkyoungs.todolist.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kkyoungs.todolist.domain.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
// 룸에서 다오 어노테이션을 붙이게 되면 자동으로 활용할 수 있는 형태로 만들어줌

interface TodoDao {

    //Flow는 비동기적인 데이터의 흐름을 처리 하기 적합하고, 코루틴을 같이 사용해서 사용하기 편리한 기능
    @Query("SELECT * FROM todo ORDER BY date DESC")
    fun todos():Flow<List<Todo>>

    // 비동기로 오래걸리기 때문에 suspend 함수로 표현
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo:Todo)

    @Update
    suspend fun update(todo:Todo)

    @Delete
    suspend fun delete(todo: Todo)
}