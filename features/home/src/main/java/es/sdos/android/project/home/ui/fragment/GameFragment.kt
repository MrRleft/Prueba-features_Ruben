package es.sdos.android.project.home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import es.sdos.android.project.common.di.ViewModelFactory
import es.sdos.android.project.common.ui.BaseFragment
import es.sdos.android.project.common.ui.BaseViewModel
import es.sdos.android.project.data.repository.util.AsyncResult
import es.sdos.android.project.feature.home.databinding.FragmentGameBinding
import es.sdos.android.project.home.ui.viewmodel.GameViewModel
import javax.inject.Inject

class GameFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<GameViewModel>
    private val viewModel: GameViewModel by lazy { viewModelFactory.get() }

    private val safeArgs: GameFragmentArgs by navArgs()
    private lateinit var binding: FragmentGameBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestGame(safeArgs.gameId)
        viewModel.getGameLiveData().observe(viewLifecycleOwner, Observer { result ->
            binding.game = result.data?.takeIf { result.status == AsyncResult.Status.SUCCESS }
            binding.game?.let {if (it.finished) binding.gameBtnShotSubmit.visibility = View.GONE}
        })
        binding.gameBtnShotSubmit.setOnClickListener {
            viewModel.addShot(safeArgs.gameId, Integer.parseInt(binding.gameInputShotScore.text.toString()))
        }
    }

    override fun getViewModel() = viewModel as BaseViewModel

}