package es.sdos.android.project.data.model.game

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RoundBoExtensionTest {

    private val gameId = 2L

    private val completedRound = RoundBo(
        1L,
        gameId,
        1,
        0,
        2,
        null,
        2
    )

    private val completedRoundSpare = RoundBo(
        1L,
        gameId,
        1,
        0,
        10,
        null,
        10
    )

    private val completedRoundStrike = RoundBo(
        1L,
        gameId,
        1,
        10,
        null,
        null,
        null
    )

    private val incompletedRound = RoundBo(
        1L,
        gameId, 1,
        2,
        null,
        null,
        2
    )

    private val incompletedRoundFinal = RoundBo(
        1L,
        gameId,
        10,
        0,
        10,
        null,
        2
    )

    private val completedRoundFinal = RoundBo(
        1L,
        gameId,
        10,
        0,
        2,
        null,
        2
    )

    private val prevRoundListSpare = listOf(completedRoundSpare)

    private val prevRoundListStrike = listOf(completedRoundStrike)

    private val prevRoundListNormal = listOf(completedRound)

    private val prevRoundListIncomplete = listOf(incompletedRound)

    @Before
    fun setUp() {
    }

    @Test
    fun addShot_prevRoundListSpare_listOf() {
        val res = prevRoundListSpare.addShot(gameId, 5)

        assert(res.last().firstShot == 5)
        assert(res.getLastScoreRegistered() == 20)

    }

    @Test
    fun addShot_prevRoundListStrike_listOf() {
        val prev = prevRoundListStrike.addShot(gameId, 5)
        val res = prev.addShot(gameId, 4)

        assert(res.last().firstShot == 5)
        assert(res.last().secondShot == 4)
        assert(res.getLastScoreRegistered() == 29)
    }

    @Test
    fun addShot_prevRoundListNormal_listOf() {
        val prev = prevRoundListNormal.addShot(gameId, 2)
        val res = prev.addShot(gameId, 2)

        assert(res.last().firstShot == 2)
        assert(res.getLastScoreRegistered() == 6)
    }

    @Test
    fun addShot_prevRoundListIncomplete_listOfIncomplete() {
        val res = prevRoundListIncomplete.addShot(gameId, 5)

        assert(res.size == 1)
        assert(res.last().score == 7)
    }

    @Test
    fun isComplete_completedRound_true() {
        assertTrue(completedRound.isComplete())
    }

    @Test
    fun isComplete_incompletedRoundFinal_false() {
        assertFalse(incompletedRoundFinal.isComplete())
    }

    @Test
    fun isComplete_completedRoundFinal_() {
        assertTrue(completedRoundFinal.isComplete())

    }
}