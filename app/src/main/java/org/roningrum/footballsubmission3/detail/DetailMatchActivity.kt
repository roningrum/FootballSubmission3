package org.roningrum.footballsubmission3.detail

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.json.JSONObject
import org.roningrum.footballsubmission3.*
import org.roningrum.footballsubmission3.R.color.colorAccent
import org.roningrum.footballsubmission3.R.color.colorSecondaryText
import org.roningrum.footballsubmission3.api.ApiRepository
import org.roningrum.footballsubmission3.database.Favorite
import org.roningrum.footballsubmission3.database.database
import org.roningrum.footballsubmission3.model.Events
import org.roningrum.footballsubmission3.util.invisible
import org.roningrum.footballsubmission3.util.visible
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {
    private lateinit var presenter: DetailMatchPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var events: Events
    private lateinit var idEvent: String
    private var idHome: String = ""
    private var idAway: String = ""

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var eventHometeamBadge: ImageView
    private lateinit var eventAwayteamBadge: ImageView
    private lateinit var eventMatchDate: TextView
    private lateinit var eventMatchTime: TextView
    private lateinit var eventHomeTeam: TextView
    private lateinit var eventAwayTeam: TextView
    private lateinit var eventMatchScore: TextView
    private lateinit var eventHomeFormation: TextView
    private lateinit var eventAwayFormation: TextView
    private lateinit var eventHomeGoals: TextView
    private lateinit var eventAwayGoals: TextView
    private lateinit var eventHomeShots: TextView
    private lateinit var eventAwayShots: TextView
    private lateinit var eventHomeGoalKeeper: TextView
    private lateinit var eventAwayGoalKeeper: TextView
    private lateinit var eventHomeDefense: TextView
    private lateinit var eventAwayDefense: TextView
    private lateinit var eventHomeMidField: TextView
    private lateinit var eventAwayMidField: TextView
    private lateinit var eventHomeForward: TextView
    private lateinit var eventAwayForward: TextView
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        idEvent = intent.getStringExtra("idEvent")
        idHome = intent.getStringExtra("idHome")
        idAway = intent.getStringExtra("idAway")

        supportActionBar?.title = "Event Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )
                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(16)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL
                            eventMatchDate = textView {
                                this.gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, colorSecondaryText)
                            }.lparams(width = matchParent, height = wrapContent) {
                                topMargin = dip(5)
                            }
                            eventMatchTime = textView {
                                this.gravity = Gravity.CENTER
                            }.lparams(width = matchParent, height = wrapContent) {
                                topMargin = dip(5)
                            }
                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                orientation = LinearLayout.HORIZONTAL

                                eventHometeamBadge = imageView {
                                }.lparams(width = dip(70), height = dip(70)) {
                                }
                                eventMatchScore = textView("VS") {
                                    textSize = 36f
                                    this.gravity = Gravity.CENTER
                                }.lparams(width = dip(0), height = wrapContent, weight = 1f) {
                                }
                                eventAwayteamBadge = imageView {
                                }.lparams(width = dip(70), height = dip(70)) {
                                    gravity = Gravity.END
                                }
                            }
                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                orientation = LinearLayout.HORIZONTAL
                                eventHomeTeam = textView("Man United") {
                                    typeface = Typeface.DEFAULT_BOLD
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                }
                                eventAwayTeam = textView("Liverpool") {
                                    this.gravity = Gravity.END
                                    typeface = Typeface.DEFAULT_BOLD
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {

                                }

                            }
                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                bottomPadding = dip(16)
                                orientation = LinearLayout.HORIZONTAL

                                eventHomeFormation = textView {
                                    this.gravity = Gravity.CENTER
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                }
                                eventAwayFormation = textView {
                                    this.gravity = Gravity.END
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {

                                }
                            }
                            view {
                                setBackgroundResource(R.drawable.shadow)
                            }.lparams(width = matchParent, height = dip(2)) {
                            }
                            textView("Goals") {
                                this.gravity = Gravity.CENTER
                                typeface = Typeface.DEFAULT_BOLD
                            }.lparams(width = matchParent, height = wrapContent, weight = 1f) {

                                topPadding = dip(16)
                            }
                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                bottomPadding = dip(16)
                                orientation = LinearLayout.HORIZONTAL

                                eventHomeGoals = textView {
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                }
                                eventAwayGoals = textView {
                                    this.gravity = Gravity.END
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {

                                }
                            }
                            view {
                                setBackgroundResource(R.drawable.shadow)
                            }.lparams(width = matchParent, height = dip(2)) {
                            }
                            textView("Shots") {
                                this.gravity = Gravity.CENTER
                                typeface = Typeface.DEFAULT_BOLD
                            }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                topPadding = dip(16)
                            }
                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                bottomPadding = dip(16)
                                orientation = LinearLayout.HORIZONTAL

                                eventHomeShots = textView {
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                }
                                eventAwayShots = textView {
                                    this.gravity = Gravity.END
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                }
                            }
                            view {
                                setBackgroundResource(R.drawable.shadow)
                            }.lparams(width = matchParent, height = dip(2)) {
                            }
                            textView("Line Ups") {
                                this.gravity = Gravity.CENTER
                                typeface = Typeface.DEFAULT_BOLD
                            }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                topPadding = dip(16)
                            }
                            textView("Goal Keeper") {
                                this.gravity = Gravity.CENTER
                                typeface = Typeface.DEFAULT_BOLD
                            }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                topPadding = dip(16)
                            }
                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                bottomPadding = dip(16)
                                orientation = LinearLayout.HORIZONTAL

                                eventHomeGoalKeeper = textView {
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                }
                                eventAwayGoalKeeper = textView {
                                    this.gravity = Gravity.END
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                }
                            }
                            textView("Defender") {
                                this.gravity = Gravity.CENTER
                                typeface = Typeface.DEFAULT_BOLD
                            }.lparams(width = matchParent, height = wrapContent, weight = 1f) {

                            }
                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                bottomPadding = dip(16)
                                orientation = LinearLayout.HORIZONTAL

                                eventHomeDefense = textView {
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                }
                                eventAwayDefense = textView {
                                    this.gravity = Gravity.END
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {

                                }
                            }
                            textView("MidFieder") {
                                this.gravity = Gravity.CENTER
                                typeface = Typeface.DEFAULT_BOLD
                            }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                            }
                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                bottomPadding = dip(16)
                                orientation = LinearLayout.HORIZONTAL

                                eventHomeMidField = textView {
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                }
                                eventAwayMidField = textView {
                                    this.gravity = Gravity.END
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {

                                }
                            }
                            textView("Forward") {
                                this.gravity = Gravity.CENTER
                                typeface = Typeface.DEFAULT_BOLD
                            }.lparams(width = matchParent, height = wrapContent, weight = 1f) {

                            }
                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                bottomPadding = dip(16)
                                orientation = LinearLayout.HORIZONTAL

                                eventHomeForward = textView {
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                }
                                eventAwayForward = textView {
                                    this.gravity = Gravity.END
                                }.lparams(width = matchParent, height = wrapContent, weight = 1f) {
                                }
                            }
                        }
                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }

        }
        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailMatchPresenter(this, request, gson)
        presenter.getDetailMatchList(idEvent)
        swipeRefresh.onRefresh {
            presenter.getDetailMatchList(idEvent)
        }
        val logo = arrayOf(idHome, idAway)
        getBadge(logo)
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE).whereArgs("(EVENT_ID={id})",
                "id" to idEvent)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showDetailMatch(data: List<Events>) {
        events = Events(
            data[0].idEvent,
            data[0].teamHomeName,
            data[0].teamAwayName,
            data[0].teamHomeScore,
            data[0].teamAwayScore,
            data[0].dateEvent,
            data[0].timeEvent,
            data[0].idHomeTeam,
            data[0].idAwayTeam,
            data[0].strHomeGoalDetails,
            data[0].strAwayGoalDetails,
            data[0].intHomeShots,
            data[0].intAwayShots,
            data[0].strHomeLineupDefense,
            data[0].strAwayLineupDefense,
            data[0].strHomeLineupMidfield,
            data[0].strAwayLineupMidfield,
            data[0].strHomeLineupForward,
            data[0].strAwayLineupForward,
            data[0].strHomeLineupSubstitutes,
            data[0].strAwayLineupSubstitutes,
            data[0].strHomeFormation,
            data[0].strAwayFormation,
            data[0].strHomeLineupGoalkeeper,
            data[0].strAwayLineupGoalkeeper
        )
        swipeRefresh.isRefreshing = false
        val tanggalMatch = SimpleDateFormat("EEE, d MMM yyyy")
            .format(toGMTFormat(data[0].dateEvent, data[0].timeEvent))
        val waktuMatch = SimpleDateFormat("HH:mm")
            .format(toGMTFormat(data[0].dateEvent, data[0].timeEvent))
        eventMatchDate.text = tanggalMatch
        eventMatchTime.text = waktuMatch
        eventHomeTeam.text = data[0].teamHomeName
        eventAwayTeam.text = data[0].teamAwayName
        if (data[0].teamHomeScore.isNullOrEmpty() && data[0].teamAwayScore.isNullOrEmpty()) {
            eventMatchScore.text = " VS "
        } else {
            eventMatchScore.text = data[0].teamHomeScore + "  VS  " + data[0].teamAwayScore
        }
        eventHomeFormation.text = data[0].strHomeFormation
        eventAwayFormation.text = data[0].strAwayFormation
        eventHomeGoals.text = data[0].strHomeGoalDetails?.replace(";", "\n")
        eventAwayGoals.text = data[0].strAwayGoalDetails?.replace(";", "\n")
        eventHomeShots.text = data[0].intHomeShots
        eventAwayShots.text = data[0].intAwayShots
        eventHomeGoalKeeper.text = cleanRapi(data[0].strHomeLineupGoalkeeper)
        eventAwayGoalKeeper.text = cleanRapi(data[0].strAwayLineupGoalkeeper)
        eventHomeDefense.text = cleanRapi(data[0].strHomeLineupDefense)
        eventAwayDefense.text = cleanRapi(data[0].strAwayLineupDefense)
        eventHomeMidField.text = cleanRapi(data[0].strHomeLineupMidfield)
        eventAwayMidField.text = cleanRapi(data[0].strAwayLineupMidfield)
        eventHomeForward.text = cleanRapi(data[0].strHomeLineupForward)
        eventAwayForward.text = cleanRapi(data[0].strAwayLineupForward)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.EVENT_ID to events.idEvent,
                    Favorite.TEAM_HOME_NAME to events.teamHomeName,
                    Favorite.TEAM_AWAY_NAME to events.teamAwayName,
                    Favorite.TEAM_HOME_SCORE to events.teamHomeScore,
                    Favorite.TEAM_AWAY_SCORE to events.teamAwayScore,
                    Favorite.DATE_EVENT to events.dateEvent,
                    Favorite.TIME_EVENT to events.timeEvent,
                    Favorite.ID_HOME_TEAM to events.idHomeTeam,
                    Favorite.ID_AWAY_TEAM to events.idAwayTeam,
                    Favorite.HOME_GOAL_DETAILS to events.strHomeGoalDetails,
                    Favorite.AWAY_GOAL_DETAILS to events.strAwayGoalDetails,
                    Favorite.HOME_SHOTS to events.intHomeShots,
                    Favorite.AWAY_SHOTS to events.intAwayShots,
                    Favorite.HOME_LINEUP_DEFENSE to events.strHomeLineupDefense,
                    Favorite.AWAY_LINEUP_DEFENSE to events.strAwayLineupDefense,
                    Favorite.HOME_LINEUP_MIDFIELD to events.strHomeLineupMidfield,
                    Favorite.AWAY_LINEUP_MIDFIELD to events.strAwayLineupMidfield,
                    Favorite.HOME_LINEUP_FORWARD to events.strHomeLineupForward,
                    Favorite.AWAY_LINEUP_FORWARD to events.strAwayLineupForward,
                    Favorite.HOME_LINEUP_SUBSTITUTES to events.strHomeLineupSubstitutes,
                    Favorite.AWAY_LINEUP_SUBSTITUTES to events.strAwayLineupSubstitutes,
                    Favorite.HOME_LINEUP_FORMATION to events.strHomeFormation,
                    Favorite.AWAY_LINEUP_FORMATION to events.strAwayFormation,
                    Favorite.HOME_LINEUP_GOALKEEPER to events.strHomeLineupGoalkeeper,
                    Favorite.AWAY_LINEUP_GOALKEEPER to events.strAwayLineupGoalkeeper
                )
            }
            swipeRefresh.snackbar("Added to favorite").show()

        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    Favorite.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to idEvent)
            }
            swipeRefresh.snackbar( "Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun toGMTFormat(dateEvent: String?, timeEvent: String?): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = "$dateEvent $timeEvent"
        return formatter.parse(dateTime)
    }

    private fun getBadge(logo: Array<String>) {
        for(i in 0..1){
            val request = Request.Builder()
                .url("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id="+logo[i])
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    val a = response.body()?.string()
                    runOnUiThread {
                        run(){
                            val json = JSONObject(a)
                            val badge = json.getJSONArray("teams").getJSONObject(0).getString("strTeamBadge")
                            if(i == 0) {
                                Picasso.get().load(badge).into(eventHometeamBadge)
                            }else{
                                Picasso.get().load(badge).into(eventAwayteamBadge)
                            }
                        }
                    }
                }
            })
        }

    }

    private fun cleanRapi(player: String?):String?{
        return  player?.replace(";","\n")
    }
    }

