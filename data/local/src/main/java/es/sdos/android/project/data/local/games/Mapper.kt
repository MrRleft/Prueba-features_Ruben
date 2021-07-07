package es.sdos.android.project.data.local.games

import es.sdos.android.project.data.local.games.dbo.GameDbo
import es.sdos.android.project.data.local.games.dbo.RoundDbo
import es.sdos.android.project.data.model.game.GameBo
import es.sdos.android.project.data.model.game.RoundBo
import java.util.Date

fun GameDbo.toBo() = GameBo(
    this.id,
    this.date,
    listOf(),
    this.score,
    this.finished
)

fun GameBo.toDbo() = GameDbo(
    id,
    this.date,
    this.totalScore,
    this.finished
)

fun RoundDbo.toBo() = RoundBo(
    id,
    this.gameId,
    this.roundNum,
    this.firstShot,
    this.secondShot,
    this.thirdShot,
    this.score
)

fun RoundBo.toDbo() = RoundDbo(
    id,
    this.gameId,
    this.roundNum,
    this.firstShot,
    this.secondShot,
    this.thirdShot,
    this.score
)
