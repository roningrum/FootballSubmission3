package org.roningrum.footballsubmission3.nextmatch

import org.roningrum.footballsubmission3.model.Events

interface NextMatchView{
    fun showLoading()
    fun hideLoading()
    fun showNextMatchList(data: List<Events>)
}