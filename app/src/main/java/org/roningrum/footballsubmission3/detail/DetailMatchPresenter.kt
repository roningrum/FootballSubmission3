package org.roningrum.footballsubmission3.detail

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.roningrum.footballsubmission3.api.ApiRepository
import org.roningrum.footballsubmission3.api.TheSportDBApi
import org.roningrum.footballsubmission3.model.EventsResponse

class DetailMatchPresenter(private val view: DetailMatchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson){
    fun getDetailMatchList(eventId: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getDetailMatch(eventId)), EventsResponse::class.java)
            uiThread {
                view.hideLoading()
                view.showDetailMatch(data.events)
            }
        }
    }
}