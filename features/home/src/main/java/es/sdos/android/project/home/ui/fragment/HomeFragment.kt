package es.sdos.android.project.home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import es.sdos.android.project.common.di.ViewModelFactory
import es.sdos.android.project.common.ui.BaseFragment
import es.sdos.android.project.common.ui.BaseViewModel
import es.sdos.android.project.data.model.game.GameBo
import es.sdos.android.project.data.repository.util.AsyncResult
import es.sdos.android.project.feature.home.databinding.FragmentHomeBinding
import es.sdos.android.project.home.ui.dialog.ConfirmNewGameDialog
import es.sdos.android.project.home.ui.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>
    private val viewModel: HomeViewModel by lazy { viewModelFactory.get() }

    private lateinit var binding: FragmentHomeBinding
    private var pendingGame: GameBo? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        bindClicks()
        bindStates()
        return binding.root
    }

    private fun bindStates() {
        binding.viewmodel?.getPendingGameLiveData()?.observe(::getLifecycle, ::getPendingGame)
    }

    private fun getPendingGame(result: AsyncResult<List<GameBo>>?) {
        when (result?.status) {
            AsyncResult.Status.SUCCESS -> {
                result.data?.let{
                    if(it.isNotEmpty()){
                        pendingGame = result.data?.last()
                        binding.homeContinueGame.visibility = VISIBLE
                    }
                }
            }
            AsyncResult.Status.ERROR -> TODO()
            AsyncResult.Status.LOADING -> {
                //Todo: Implement loader
            }
            null -> TODO()
        }
    }

    private fun bindClicks() {
        binding.homeNewGame.setOnClickListener {
            ConfirmNewGameDialog(::onNewGameClick).show(this.childFragmentManager, "WNGD")
        }
        binding.homeContinueGame.setOnClickListener {
            onContinueGameClick()
        }
    }

    private fun onContinueGameClick() {
        pendingGame?.id?.let { viewModel.goToGame(it) }
    }

    private fun onNewGameClick() {
        viewModel.createGame().observe(viewLifecycleOwner, Observer { result ->
            binding.homeNewGame.isEnabled = result.status != AsyncResult.Status.LOADING
            result.data?.takeIf { result.status == AsyncResult.Status.SUCCESS }?.id?.let { viewModel.goToGame(it) }
        })
    }

    override fun getViewModel() = viewModel as BaseViewModel

}