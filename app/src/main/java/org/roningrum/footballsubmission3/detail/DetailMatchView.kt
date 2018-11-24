package org.roningrum.footballsubmission3.detail

import org.roningrum.footballsubmission3.model.Events

interface DetailMatchView{
    fun showLoading()
    fun hideLoading()
    fun showDetailMatch(data: List<Events>)
}