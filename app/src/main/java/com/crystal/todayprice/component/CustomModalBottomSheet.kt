package com.crystal.todayprice.component

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.crystal.todayprice.R
import com.crystal.todayprice.databinding.CustomModalBottomSheetBinding
import com.crystal.todayprice.util.OnBottomSheetListener

enum class ModalType {
    BLOCK, DELETE,UNBLOCK, MENU_ANOTHER_BLOCK, MENU_ANOTHER_UNBLOCK
}

enum class ButtonType {
    REPORT, BLOCK , DELETE, OPEN_BLOCK, OPEN_UNBLOCK
}

class CustomModalBottomSheet(
    private val context: Context,
    private val onDialogListener: OnBottomSheetListener?
) {
    private val dialog = Dialog(context)
    private lateinit var binding: CustomModalBottomSheetBinding

    fun show(userName: String, modalType: ModalType) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = CustomModalBottomSheetBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(binding.root)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.window?.attributes?.windowAnimations = R.style.CustomDialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)

        when (modalType) {
            ModalType.BLOCK -> {
                binding.reportTextView.visibility = View.GONE
                binding.blockTextView.visibility = View.GONE
                binding.infoTextView.visibility = View.VISIBLE
                binding.leftButton.visibility = View.VISIBLE
                binding.rightButton.visibility = View.VISIBLE
                binding.infoTextView.text = context.getString(R.string.block_text, userName)
                binding.rightButton.text = context.getString(R.string.button_unblock)
            }

            ModalType.DELETE -> {
                binding.reportTextView.visibility = View.GONE
                binding.blockTextView.visibility = View.GONE
                binding.infoTextView.visibility = View.VISIBLE
                binding.leftButton.visibility = View.VISIBLE
                binding.rightButton.visibility = View.VISIBLE
                binding.infoTextView.text = context.getString(R.string.delete_text)
                binding.rightButton.text = context.getString(R.string.button_delete)
            }

            ModalType.MENU_ANOTHER_BLOCK -> {
                binding.reportTextView.visibility = View.VISIBLE
                binding.blockTextView.visibility = View.VISIBLE
                binding.infoTextView.visibility = View.GONE
                binding.leftButton.visibility = View.VISIBLE
                binding.rightButton.visibility = View.GONE
            }
            ModalType.MENU_ANOTHER_UNBLOCK -> {
                binding.reportTextView.visibility = View.VISIBLE
                binding.blockTextView.visibility = View.VISIBLE
                binding.infoTextView.visibility = View.GONE
                binding.leftButton.visibility = View.VISIBLE
                binding.rightButton.visibility = View.GONE
                binding.blockTextView.text = context.getString(R.string.unblock)
            }
            ModalType.UNBLOCK -> {
                binding.reportTextView.visibility = View.GONE
                binding.blockTextView.visibility = View.GONE
                binding.infoTextView.visibility = View.VISIBLE
                binding.leftButton.visibility = View.VISIBLE
                binding.rightButton.visibility = View.VISIBLE
                binding.infoTextView.text = context.getString(R.string.unblock_text, userName)
                binding.rightButton.text = context.getString(R.string.button_unblock)
            }
        }

        binding.reportTextView.setOnClickListener {
            onDialogListener?.onClick(ButtonType.REPORT)
            dialog.dismiss()
        }

        binding.blockTextView.setOnClickListener {
            if (modalType == ModalType.MENU_ANOTHER_UNBLOCK) {
                onDialogListener?.onClick(ButtonType.OPEN_UNBLOCK)
                dialog.dismiss()
            }
            if (modalType == ModalType.MENU_ANOTHER_BLOCK) {
                onDialogListener?.onClick(ButtonType.OPEN_BLOCK)
                dialog.dismiss()
            }

        }

        binding.leftButton.setOnClickListener {
            dialog.dismiss()
        }

        binding.rightButton.setOnClickListener {
            when (modalType) {
                ModalType.BLOCK -> {onDialogListener?.onClick(ButtonType.BLOCK)}
                ModalType.DELETE -> {onDialogListener?.onClick(ButtonType.DELETE)}
                ModalType.UNBLOCK -> {onDialogListener?.onClick(ButtonType.BLOCK)}
                else -> {}
            }
            dialog.dismiss()
        }

        dialog.show()
    }

}