package es.sdos.android.project.data.local.games.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class GameDbo(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var date: Date,
    var score: Int,
    var finished: Boolean
)