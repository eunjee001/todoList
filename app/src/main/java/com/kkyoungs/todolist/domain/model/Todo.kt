package com.kkyoungs.todolist.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

// room DB 에서는 이런 Entity 들을 entity 어노텐션을 붙이면 자동으로 룸 디비에서 사용할 수 있게 됨
/** @Entity 란?
 * 데이터베이스의 테이블과 일대일로 매칭되는 객체 단위이며 entity 객체의 인스턴스 하나가 테이블에서 하나의 레코드 값을 의미.
 */
@Entity
data class Todo(
    val title:String,
    val date: Long = Calendar.getInstance().timeInMillis,
    val isDone : Boolean = false,

) {
    @PrimaryKey(autoGenerate = true)
    // 엔티티에는 id가 필요한데 위에다 만들지 않고 따로 밑에다 만들것 !
    // 왜냐면 id 는 동적으로 값이 정해져야 되는 데, 안에다 만들면 동적으로 아이디를 정하는게 어렵기 때문
    // autoGenerate True 옵션을 주면 이 값은 자동으로 다시 마지막 제일 가장 큰 아이디를 가지는 항목을 찾아서 그거보다 하나 증가되는 아이디를 자동으로 부여
    var uid : Int = 0

}