package org.roningrum.footballsubmission3.prevmatch

import org.roningrum.footballsubmission3.model.Events

interface PrevMatchView{
    fun showLoading()
    fun hideLoading()
    fun showPrevMatchList(data: List<Events>)
}