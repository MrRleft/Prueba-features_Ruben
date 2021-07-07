package es.sdos.android.project.data.remote.games

import es.sdos.android.project.data.model.game.*
import es.sdos.android.project.data.remote.games.dto.GameDto
import java.text.SimpleDateFormat
import java.util.Locale

fun GameDto.toBo() : GameBo{

    val rounds = calculateRounds(this.id, convertStringToArrayOfShots(this.shoots))

    return GameBo(
        this.id,
        convertStringDateToDate(this.date),
        rounds,
        rounds.getLastScoreRegistered(),
        getIsFinished(rounds)
        )
}

private fun getIsFinished(rounds: List<RoundBo>) = rounds.find { it.roundNum == 10 }?.isComplete() ?: false

private fun calculateRounds(gameId: Long, shots: List<Int>): List<RoundBo> {
    var result = listOf<RoundBo>()
    for (shot in shots) {
        result = result.addShot(gameId, shot)
    }
    return result
}

private fun convertStringDateToDate(date: String) = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).parse(date)

private fun convertStringToArrayOfShots(rawData: String): List<Int> {
    val stringArray = rawData.split(",")
    val resultList = mutableListOf<Int>()
    stringArray.forEach { resultList.add(it.toInt()) }
    return resultList
}

