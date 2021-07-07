package es.sdos.android.project.data.local.games.dbo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class GameWithRoundsDbo(
    @Embedded val games: GameDbo,
    @Relation(
        parentColumn = "id",
        entityColumn = "gameId"
    )
    val rounds: List<RoundDbo>
)