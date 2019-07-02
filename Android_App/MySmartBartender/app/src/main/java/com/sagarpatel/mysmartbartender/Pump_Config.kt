package com.sagarpatel.mysmartbartender

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.view.FrameMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_add__drink.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Pump_Config : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Pump Buttons
        var view: View = inflater.inflate(R.layout.fragment_pump__config, container, false)
        var pump1_FAB: View = view.findViewById(R.id.pump1_Button)
        var pump2_FAB: View = view.findViewById(R.id.pump2_Button)
        var pump3_FAB: View = view.findViewById(R.id.pump3_Button)
        var pump4_FAB: View = view.findViewById(R.id.pump4_Button)
        var pump5_FAB: View = view.findViewById(R.id.pump5_Button)
        var pump6_FAB: View = view.findViewById(R.id.pump6_Button)


        pump1_FAB.setOnClickListener {
            val pump1_dialog = Pump_Ingredient()
            pump1_dialog.setPump("1")
            pump1_dialog.show(getChildFragmentManager(), "Pump 1 Dialog")
        }

        pump2_FAB.setOnClickListener {
            val pump2_dialog = Pump_Ingredient()
            pump2_dialog.setPump("2")
            pump2_dialog.show(getChildFragmentManager(), "Pump 2 Dialog")
        }

        pump3_FAB.setOnClickListener {
            val pump3_dialog = Pump_Ingredient()
            pump3_dialog.setPump("3")
            pump3_dialog.show(getChildFragmentManager(), "Pump 3 Dialog")
        }

        pump4_FAB.setOnClickListener {
            val pump4_dialog = Pump_Ingredient()
            pump4_dialog.setPump("4")
            pump4_dialog.show(getChildFragmentManager(), "Pump 4 Dialog")
        }

        pump5_FAB.setOnClickListener {
            val pump5_dialog = Pump_Ingredient()
            pump5_dialog.setPump("5")
            pump5_dialog.show(getChildFragmentManager(), "Pump 5 Dialog")
        }

        pump6_FAB.setOnClickListener {
            val pump6_dialog = Pump_Ingredient()
            pump6_dialog.setPump("6")
            pump6_dialog.show(getChildFragmentManager(), "Pump 6 Dialog")
        }
        return view
    }

    fun update_pump_config(ingredient: String, pump: Int) {
        System.out.println("\n" + ingredient + "\n")
    }




}