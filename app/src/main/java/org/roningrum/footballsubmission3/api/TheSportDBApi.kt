package org.roningrum.footballsubmission3.api

import android.net.Uri
import org.roningrum.footballsubmission3.BuildConfig

object TheSportDBApi{
    fun getPrevMatch(league:String?): String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("eventspastleague.php")
            .appendQueryParameter("id",league)
            .build()
            .toString()
    }
    fun getNextMatch(league:String?):String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("eventsnextleague.php")
            .appendQueryParameter("id",league)
            .build()
            .toString()
    }
    fun getDetailMatch(eventId:String?):String{
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("lookupevent.php")
            .appendQueryParameter("id",eventId)
            .build()
            .toString()
    }
}