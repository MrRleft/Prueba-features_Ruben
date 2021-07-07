package es.sdos.android.project.home.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import es.sdos.android.project.feature.home.databinding.DialogConfirmNewGameBinding

class ConfirmNewGameDialog(private val confirmButtonAction: () -> Unit) :
    DialogFragment() {

    private lateinit var binding: DialogConfirmNewGameBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogConfirmNewGameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureUI()
    }

    private fun configureUI() {
        binding.dialogAcceptButton.setOnClickListener {
            confirmButtonAction()
            this@ConfirmNewGameDialog.dismiss()
        }
        binding.dialogCancelButton.setOnClickListener { this@ConfirmNewGameDialog.dismiss() }
    }
}