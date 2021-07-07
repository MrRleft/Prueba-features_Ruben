package es.sdos.android.project.data.model.game

fun List<RoundBo>.addShot(gameId: Long, shotScore: Int): List<RoundBo> {
    val result = this.toMutableList()
    if (result.isEmpty()) {
        result.add(RoundBo(null, gameId, 1, shotScore, null, null, null))
    } else {
        val lastRound = result.last()
        if (lastRound.isComplete()) {
            result.add(RoundBo(null, gameId, lastRound.roundNum + 1, shotScore, null, null, null))
        } else {
            result[result.size - 1] = if (lastRound.secondShot == null) {
                lastRound.copy(secondShot = shotScore)
            } else {
                lastRound.copy(thirdShot = shotScore)
            }
        }

        updateScores(result)
    }

    return result
}

fun List<RoundBo>.getLastScoreRegistered(): Int {
    var score = 0
    this.forEach { it.score?.let { roundScore -> if (roundScore > score) score = roundScore } }
    return score
}

private fun RoundBo.isStrike(): Boolean = when {
    this.firstShot == 10 -> true
    this.roundNum == 10 && this.thirdShot != null && this.thirdShot == 10 -> true
    else -> false
}

private fun RoundBo.isSpare(): Boolean = when (this.secondShot) {
    null -> false
    else -> this.firstShot + this.secondShot == 10
}

/**
 * Indica que la ronda esta finalizada, no quedan lanzamientos pendientes
 */
fun RoundBo.isComplete() = when {
    this.secondShot == null && !this.isStrike() -> false
    this.roundNum == 10 -> if (this.isSpare() || this.isStrike()) this.thirdShot != null else true
    else -> true
}

private fun updateScores(result: MutableList<RoundBo>) {
    for (i in 0 until result.size) {
        val roundBo = result[i]
        if (!roundBo.isComplete()) {
            break
        }
        val previousScore = result.getOrNull(i - 1)?.score ?: 0

        val roundScore = roundBo.firstShot + (roundBo.secondShot ?: 0) + (roundBo.thirdShot ?: 0)
        val extraScore = if (roundBo.roundNum != 10 && roundBo.isStrike()) {
            getNextShotsScore(result, i + 1, 2)
        } else if (roundBo.roundNum != 10 && roundBo.isSpare()) {
            getNextShotsScore(result, i + 1, 1)
        } else {
            0
        }

        result[i] = roundBo.copy(score = previousScore + roundScore + extraScore)
    }
}

/**
 * Obtiene la puntuación acumulada de los 'x' siguientes lanzamientos a partir de 'startIndex'
 */
private fun getNextShotsScore(roundList: List<RoundBo>, startIndex: Int, numberOfShots: Int): Int {
    return if (roundList.size < startIndex && numberOfShots <= 0)
        0
    else {
        val prevRound = if (startIndex == 1 || startIndex == 0) null else roundList[startIndex - 2]
        val startRound = roundList[startIndex - 1]

        var prevCounter = when {
            prevRound?.isStrike() == true || prevRound?.isSpare() == true -> 10
            else -> 0
        }

        var confirmedAmount = startRound.firstShot
        var advancedShots = 1
        val secondShot = startRound.secondShot ?: 0
        val thirdShot = startRound.thirdShot ?: 0

        if (numberOfShots % 2 == 0 && !startRound.isStrike()) {
            confirmedAmount += secondShot
            if (prevRound?.isStrike() == true) prevCounter += 10
            advancedShots = 2
        }
        if (startRound.roundNum == 10 && numberOfShots >= 3) {
            confirmedAmount += thirdShot
            confirmedAmount += if (startRound.isSpare() || startRound.isStrike()) 10 else 0
            advancedShots = 3
        }
        confirmedAmount += prevCounter
        confirmedAmount + getNextShotsScore(roundList, startIndex + 1, numberOfShots - advancedShots)
    }

}