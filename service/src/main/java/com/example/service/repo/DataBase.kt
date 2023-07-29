package com.example.service.repo

import android.app.Application
import android.content.Context
import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.common.BaseApplication

//1、entity的定义
@Keep
@Entity(tableName = "tb_user")
data class UserInfo(
    @PrimaryKey
    val id: Int,//主键
    val course_permission: Boolean,
    val token: String?,
    @Embedded//内嵌的数据表,User的字段将会被添加到表tb_user中
    val user: User?
) {
    @Keep
    data class User(
        @ColumnInfo(name = "user_id")
        val id: Int,//用户id
        val logo_url: String?,//用户头像
        val reg_time: String?,//用户注册时间
        val username: String?//用户名
    )
}

//2、定义dao
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: UserInfo)

    @Delete
    fun deleteUser(user: UserInfo)

    //= in like
    @Query("select * from tb_user where id =:id")
    fun queryUser(id: Int = 0): UserInfo?

    @Query("select * from tb_user where id =:id")
    fun queryLiveData(id: Int = 0): LiveData<UserInfo>

}

//3、定义数据库
@Database(entities = [UserInfo::class], version = 1, exportSchema = true)
abstract class DataBase : RoomDatabase() {
    abstract fun userDao():UserDao
    //单例
    companion object {
        private const val DB_NAME = "rookie_edu_database"

        @Volatile
        private var instance: DataBase? = null

        @Synchronized
        fun getInstance(context: Context): DataBase {
            return instance ?: Room.databaseBuilder(
                context,
                DataBase::class.java,
                DB_NAME
            ).build().also { instance = it }
        }
    }
}