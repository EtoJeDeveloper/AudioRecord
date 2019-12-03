package android.bignerdranch.audiorecord.fileSystems

import android.bignerdranch.audiorecord.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_file_option.*
import kotlinx.android.synthetic.main.dialog_file_option.*

class FileOptionsDialog : BottomSheetDialogFragment() {

    var onDeleteClickListener: (() -> Unit)? = null
    var onCopyClickListener: (() -> Unit)? = null

    companion object {
        fun build(block: Builder.() -> Unit): FileOptionsDialog = Builder().apply(block).build()
    }

    class Builder {
        var path: String? = null

        fun build(): FileOptionsDialog {
            val fragment = FileOptionsDialog()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.dialog_file_option,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        deleteTextView.setOnClickListener {
            onDeleteClickListener?.invoke()
            dismiss()
        }
    }
}