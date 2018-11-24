package org.roningrum.footballsubmission3.favorite

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.roningrum.footballsubmission3.database.Favorite
import org.roningrum.footballsubmission3.R
import java.text.SimpleDateFormat

class FavoriteMatchAdapter(private val favorites: List<Favorite>, private val listener: (Favorite) -> Unit): RecyclerView.Adapter<FavoriteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            FavUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }

    override fun getItemCount(): Int = favorites.size
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorites[position],listener)
    }

}
class FavUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            cardView {
                lparams(width = matchParent, height = wrapContent) {
                    gravity = Gravity.CENTER
                    margin = dip(4)
                    radius = 4f
                }
                verticalLayout {
                    textView("Sabtu, 14 Oktober 2018") {
                        id = R.id.match_date
                    }.lparams(width = wrapContent, height = wrapContent) {
                        margin = dip(10)
                        gravity = Gravity.CENTER
                    }
                    relativeLayout {
                        textView("Manchester United"){
                            id = R.id.match_home_team
                            textSize = 14f
                            textColor = Color.BLACK
                            gravity = Gravity.END
                        }.lparams(width = matchParent, height = wrapContent) {
                            leftOf(R.id.match_score_home_team)
                            margin = dip(10)
                        }
                        textView("2") {
                            id = R.id.match_score_home_team
                            textSize = 16f
                            gravity = Gravity.CENTER
                        }.lparams(width = wrapContent, height = wrapContent) {
                            leftOf(R.id.match_vs)
                            margin = dip(8)
                        }
                        textView("VS") {
                            id = R.id.match_vs
                            textSize = 16f
                            textColor = Color.BLACK
                        }.lparams(width = wrapContent, height = wrapContent) {
                            centerInParent()
                            margin = dip(8)
                        }
                        textView("0") {
                            id = R.id.match_score_away_team
                            textSize = 16f
                            gravity = Gravity.CENTER
                        }.lparams(width = wrapContent, height = wrapContent) {
                            rightOf(R.id.match_vs)
                            margin = dip(8)

                        }
                        textView("LiverPool") {
                            id = R.id.match_away_team
                            textSize = 14f
                            textColor = Color.BLACK
                            gravity = Gravity.START
                        }.lparams(width = matchParent, height = wrapContent){
                            margin = dip(10)
                            rightOf(R.id.match_score_away_team)
                        }
                    }.lparams(width = matchParent, height = wrapContent)
                }.lparams(width= matchParent, height = wrapContent){
                    topMargin = dip(8)
                    bottomMargin = dip(8)
                    gravity = Gravity.CENTER
                }
            }
        }
    }
}
class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val eventDate: TextView = view.find(R.id.match_date)
    private val eventHomeTeam: TextView = view.find(R.id.match_home_team)
    private val eventAwayTeam: TextView = view.find(R.id.match_away_team)
    private val eventHomeScoreTeam: TextView = view.find(R.id.match_score_home_team)
    private val eventAwayScoreTeam: TextView = view.find(R.id.match_score_away_team)

    fun bindItem(events: Favorite, listener: (Favorite) -> Unit) {
        var dateMatch = SimpleDateFormat("E, d MMM yyyy").format(SimpleDateFormat("yyyy-MM-dd").parse(events.dateEvent))
        eventDate.text = dateMatch
        eventHomeTeam.text = events.teamHomeName
        eventAwayTeam.text = events.teamAwayname
        eventAwayScoreTeam.text = events.teamAwayScore
        eventHomeScoreTeam.text = events.teamHomeScore
        itemView.onClick { listener(events)}
    }
}