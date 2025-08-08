package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//Room Dao

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)     //本app只在此处能插入实体，因此忽略冲突
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

//    根据给定 id 从 item 表中检索特定商品。
    @Query("SELECT * FROM items WHERE id = :id")    //:id 在查询中使用英文冒号来引用函数中的参数。
    fun getItem(id: Int): Flow<Item>
//    建议在持久性层中使用 Flow。将返回值类型设为 Flow 后，只要数据库中的数据发生更改，您就会收到通知。Room 会为您保持更新此 Flow
    @Query("SELECT * FROM items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>

}