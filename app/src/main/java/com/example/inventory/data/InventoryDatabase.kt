package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//告诉 Room：数据库包含哪些表（entities）、版本号是多少、是否导出架构
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase(){
//    声明一个返回 ItemDao 的抽象函数
    abstract fun itemDao(): ItemDao
    //所有定义在 companion object 中的函数/变量都可以用类名直接访问
    companion object {
        @Volatile      //这是一个线程安全相关的注解,确保多线程访问这个变量时能看到最新值，避免创建多个实例
        private var Instance: InventoryDatabase ?= null
//        提供一个公开的函数来返回数据库实例,若已存在直接返回；否则创建一个新的
        fun getDatabase(context: Context): InventoryDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }

}