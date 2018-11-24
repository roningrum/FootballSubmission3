package org.roningrum.footballsubmission3.prevmatch

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.roningrum.footballsubmission3.api.ApiRepository
import org.roningrum.footballsubmission3.api.TheSportDBApi
import org.roningrum.footballsubmission3.model.EventsResponse

class PrevMatchPresenter(private val view: PrevMatchView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson){
    fun getPrevMatchList(league: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPrevMatch(league)),EventsResponse::class.java)
            uiThread {
                view.hideLoading()
                view.showPrevMatchList(data.events)
            }
        }
    }
}