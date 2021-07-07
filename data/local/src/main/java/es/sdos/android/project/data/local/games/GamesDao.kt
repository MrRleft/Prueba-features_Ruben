package es.sdos.android.project.data.local.games

import androidx.room.*
import es.sdos.android.project.data.local.games.dbo.GameDbo
import es.sdos.android.project.data.local.games.dbo.RoundDbo

@Dao
abstract class GamesDao {

    @Transaction
    @Query("SELECT * from gamedbo where gamedbo.id like :gameId")
    abstract suspend fun getGame(gameId: Long): GameDbo?

    @Transaction
    @Query("SELECT * from gamedbo")
    abstract suspend fun getGames(): List<GameDbo>

    @Transaction
    @Query("SELECT * from rounddbo where rounddbo.gameId like :gameId")
    abstract suspend fun getRounds(gameId: Long): List<RoundDbo>

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun saveGame(gameDbo: GameDbo) : Long

    @Insert
    abstract suspend fun saveRound(roundDbo: RoundDbo) : Long

    @Query("DELETE FROM gamedbo WHERE gamedbo.id like :gameId")
    abstract suspend fun deleteGame(gameId: Long)

    @Query("DELETE FROM rounddbo WHERE rounddbo.gameId like :gameId")
    abstract suspend fun deleteRounds(gameId: Long)

}