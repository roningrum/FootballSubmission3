package org.roningrum.footballsubmission3.nextmatch

import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.roningrum.footballsubmission3.api.ApiRepository
import org.roningrum.footballsubmission3.api.TheSportDBApi
import org.roningrum.footballsubmission3.model.EventsResponse

class NextMatchPresenter(private val view: NextMatchView,
                         private val apiRepository: ApiRepository,
                         private val gson: Gson){
    fun getNextMatchList(league: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextMatch(league)), EventsResponse::class.java)
            uiThread {
                view.hideLoading()
                view.showNextMatchList(data.events)
            }
        }
    }
}