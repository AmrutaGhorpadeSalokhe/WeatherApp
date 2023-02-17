package com.bersyte.weatherapp.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bersyte.weatherapp.R

class CloseDialog : DialogFragment() {

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      /*  dialog!!.window?.setBackgroundDrawableResource(R.drawable.close_dialog_bg)

        val view = inflater.inflate(R.layout.close_diag, container, false)

        val closeBtn = view.findViewById<View>(R.id.yes_exit)
        closeBtn.setOnClickListener {
            activity?.finish()
        }

        val undoBtn = view.findViewById<View>(R.id.undo)
        undoBtn.setOnClickListener {
            dismiss()
        }*/

        return view;
    }

}