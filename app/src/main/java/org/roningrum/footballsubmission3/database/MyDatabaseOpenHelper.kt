package org.roningrum.footballsubmission3.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favoriteteam.db", null, 1){
    companion object {
        private var instance : MyDatabaseOpenHelper?=null
        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if(instance == null){
                instance =
                        MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            Favorite.TABLE_FAVORITE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.EVENT_ID to TEXT + UNIQUE,
            Favorite.TEAM_HOME_NAME to TEXT,
            Favorite.TEAM_AWAY_NAME to TEXT,
            Favorite.TEAM_HOME_SCORE to TEXT,
            Favorite.TEAM_AWAY_SCORE to TEXT,
            Favorite.DATE_EVENT to TEXT,
            Favorite.TIME_EVENT to TEXT,
            Favorite.ID_HOME_TEAM to TEXT,
            Favorite.ID_AWAY_TEAM to TEXT,
            Favorite.HOME_GOAL_DETAILS to TEXT,
            Favorite.AWAY_GOAL_DETAILS to TEXT,
            Favorite.HOME_SHOTS to TEXT,
            Favorite.AWAY_SHOTS to TEXT,
            Favorite.HOME_LINEUP_DEFENSE to TEXT,
            Favorite.AWAY_LINEUP_DEFENSE to TEXT,
            Favorite.HOME_LINEUP_MIDFIELD to TEXT,
            Favorite.AWAY_LINEUP_MIDFIELD to TEXT,
            Favorite.HOME_LINEUP_FORWARD to TEXT,
            Favorite.AWAY_LINEUP_FORWARD to TEXT,
            Favorite.HOME_LINEUP_SUBSTITUTES to TEXT,
            Favorite.AWAY_LINEUP_SUBSTITUTES to TEXT,
            Favorite.HOME_LINEUP_FORMATION to TEXT,
            Favorite.AWAY_LINEUP_FORMATION to TEXT,
            Favorite.HOME_LINEUP_GOALKEEPER to TEXT,
            Favorite.AWAY_LINEUP_GOALKEEPER to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorite.TABLE_FAVORITE,true)
    }
}
val Context.database: MyDatabaseOpenHelper
get() = MyDatabaseOpenHelper.getInstance(applicationContext)