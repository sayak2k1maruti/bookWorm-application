package com.internshala.bookworm.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class],version = 1)
abstract class BookDatabase:RoomDatabase(){
    abstract fun bookdao():BookDao    /*to use all  the funtionalities of DAO class*/
}