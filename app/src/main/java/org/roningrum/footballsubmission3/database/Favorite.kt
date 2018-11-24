package org.roningrum.footballsubmission3.database

data class Favorite(val id: Long?,
                    val eventId: String?,
                    val teamHomeName: String?,
                    val teamAwayname: String?,
                    val teamHomeScore: String?,
                    val teamAwayScore: String?,
                    val dateEvent: String?,
                    val timeEvent: String?,
                    val idHomeTeam: String?,
                    val idAwayTeam: String?,
                    val homeGoalDetails: String?,
                    val awayGoalDetails: String?,
                    val homeShots: String?,
                    val awayShots: String?,
                    val homeLineUpDefense: String?,
                    val awayLineUpDefense: String?,
                    val homeLineUpMidfield: String?,
                    val awayLineUpMidfield: String?,
                    val homeLineUpForward: String?,
                    val awayLineUpForward: String?,
                    val homeLineUpSubstitutes: String?,
                    val awayLineUpSubstitutes: String?,
                    val homeLineUpFormation: String?,
                    val awayLineUpFormation: String?,
                    val homeGoalKeeper: String?,
                    val awayGoalKeeper: String?){
companion object {
    const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
    const val ID: String ="ID_"
    const val EVENT_ID: String ="EVENT_ID"
    const val TEAM_HOME_NAME : String= "TEAM_HOME_NAME"
    const val TEAM_AWAY_NAME : String= "TEAM_AWAY_NAME"
    const val TEAM_HOME_SCORE : String= "TEAM_HOME_SCORE"
    const val TEAM_AWAY_SCORE : String= "TEAM_AWAY_SCORE"
    const val DATE_EVENT : String  ="DATE_EVENT"
    const val TIME_EVENT : String ="TIME_EVENT"
    const val ID_HOME_TEAM: String  ="ID_HOME_TEAM"
    const val ID_AWAY_TEAM : String = "ID_AWAY_TEAM"
    const val HOME_GOAL_DETAILS : String  ="HOME_GOAL_DETAIL"
    const val AWAY_GOAL_DETAILS : String ="AWAY_GOAL_DETAIL"
    const val HOME_SHOTS: String  ="HOME_SHOTS"
    const val AWAY_SHOTS : String ="AWAY_SHOTS"
    const val HOME_LINEUP_DEFENSE : String ="HOME_LINEUP_DEFENSE"
    const val AWAY_LINEUP_DEFENSE : String ="AWAY_LINEUP_DEFENSE"
    const val HOME_LINEUP_MIDFIELD : String ="HOME_LINEUP_MIDFIELD"
    const val AWAY_LINEUP_MIDFIELD: String ="AWAY_LINEUP_MIDFIELD"
    const val HOME_LINEUP_FORWARD : String ="HOME_LINEUP_FORWARD"
    const val AWAY_LINEUP_FORWARD : String ="AWAY_LINEUP_FORWARD"
    const val HOME_LINEUP_SUBSTITUTES : String ="HOME_LINEUP_SUBSTITUTES"
    const val AWAY_LINEUP_SUBSTITUTES: String  ="AWAY_LINEUP_SUBSTITUTES"
    const val HOME_LINEUP_FORMATION : String ="HOME_LINEUP_FORMATION"
    const val AWAY_LINEUP_FORMATION : String ="AWAY_LINEUP_FORMATION"
    const val HOME_LINEUP_GOALKEEPER : String ="HOME_LINEUP_GOALKEEPER"
    const val AWAY_LINEUP_GOALKEEPER : String ="AWAY_LINEUP_GOALKEEPER"
}
}