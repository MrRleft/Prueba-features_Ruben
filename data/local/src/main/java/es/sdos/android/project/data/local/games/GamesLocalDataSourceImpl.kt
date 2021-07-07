package es.sdos.android.project.data.local.games

import es.sdos.android.project.data.datasource.games.GamesLocalDataSource
import es.sdos.android.project.data.local.games.dbo.GameDbo
import es.sdos.android.project.data.model.game.*
import java.util.Date

class GamesLocalDataSourceImpl(
    private val gamesDao: GamesDao
) : GamesLocalDataSource {

    override suspend fun getGame(gameId: Long): GameBo? {
        return gamesDao.getGame(gameId)?.toBo()?.copy(rounds = gamesDao.getRounds(gameId).map { it.toBo() })
    }

    override suspend fun getGames(filter: GameFilter?): List<GameBo> {
        val games = mutableListOf<GameBo>()
        gamesDao.getGames().forEach { game ->
            games.add(game.toBo().copy(rounds = gamesDao.getRounds(game.id).map { it.toBo() }))
        }
        return if (filter != null) {
            games.filter {
                it.finished == (filter == GameFilter.FINISHED)
            }
        } else games
    }

    override suspend fun saveGames(games: List<GameBo>) {
        games.forEach { game ->
            if (gamesDao.saveGame(game.toDbo()) != -1L)
                game.rounds.forEach {
                    gamesDao.saveRound(it.toDbo())
                }
        }
    }

    override suspend fun createGame(): GameBo {
        val id = gamesDao.saveGame(GameDbo(0, Date(System.currentTimeMillis()), 0, false))
        return getGame(id) ?: TODO()
    }

    override suspend fun deleteGame(gameId: Long) {
        gamesDao.deleteGame(gameId)
        gamesDao.deleteRounds(gameId)
    }

    override suspend fun addShot(gameId: Long, shotScore: Int): GameBo? {
        val newRounds = gamesDao.getRounds(gameId).map { it.toBo() }.addShot(gameId, shotScore)
        val score = newRounds.getLastScoreRegistered()
        newRounds.forEach {
            gamesDao.saveRound(it.toDbo())
        }

        val game = gamesDao.getGame(gameId)
        if (game != null) {
            game.finished = newRounds.size >= 10 && newRounds.find{it.roundNum == 10}?.isComplete() == true
            game.score = score
            gamesDao.updateGame(game)
        }

        return getGame(gameId)
    }
}

