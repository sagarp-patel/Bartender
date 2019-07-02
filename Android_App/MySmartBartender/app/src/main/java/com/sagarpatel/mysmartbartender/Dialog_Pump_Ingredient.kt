package com.sagarpatel.mysmartbartender

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.EditText

class Pump_Ingredient : DialogFragment() {

    lateinit var dialog_view:View
    public lateinit var editText: EditText
    lateinit var pumpNum:String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            dialog_view = inflater.inflate(R.layout.dialog_pump_ingredient, null)
            editText = dialog_view.findViewById(R.id.pump_edittext)
            builder.setMessage("Pump Configuration")
                .setPositiveButton("Set",
                    DialogInterface.OnClickListener { dialog, id ->
                        // FIRE ZE MISSILES!
                        if(pumpNum == null)
                            pumpNum = "0"
                        listener.getIngredientName(editText.getText().toString(),pumpNum)
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog

                    })
            // Create the AlertDialog object and return it
            builder.setView(dialog_view)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")


    }

    internal lateinit var listener: Pump_Ingredient_Listener

    interface Pump_Ingredient_Listener {
        fun getIngredientName(name:String,num:String)
    }

    fun setPump(name:String){
        pumpNum = name
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as Pump_Ingredient_Listener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )
        }

    }

}