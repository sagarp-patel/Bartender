package com.sagarpatel.mysmartbartender

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.res.TypedArrayUtils
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_add__drink.*

//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class Add_Drink : Fragment() {

    lateinit var itemList:ArrayList<CharSequence>
    lateinit var ingredient_spinner_adapter:ArrayAdapter<CharSequence>
    lateinit var itemSpinner: Spinner
    lateinit var recipelist_Adapter:ArrayAdapter<String>
    internal lateinit var callback:onPumpConfigChangedListener
    lateinit var ingredientAmountList:ArrayList<Int>
    lateinit var ingredientAmount:EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Variables
        var view:View = inflater.inflate(R.layout.fragment_add__drink, container, false)
        var createButton: Button = view.findViewById(R.id.create_drink_button)
        itemList = ArrayList<CharSequence>()
        itemSpinner = view.findViewById(R.id.ingredient_pumps_spinner)
        var recipeListView:ListView = view.findViewById(R.id.Recipe_ListView)
        var recipeItems:ArrayList<String> = ArrayList<String>()
        var addItemButton:Button = view.findViewById(R.id.add_ingredient_Button)
        var drinkNameEditText:EditText = view.findViewById(R.id.drink_name_EditText)
        ingredientAmount = view.findViewById(R.id.ingredientAmount_EditText)
        ingredientAmountList = ArrayList<Int>()

        //Ingredient Items
        ingredientAmountList.add(0)
        ingredientAmountList.add(0)
        ingredientAmountList.add(0)
        ingredientAmountList.add(0)
        ingredientAmountList.add(0)
        ingredientAmountList.add(0)

        //Ingredient Spinner Code Here
        itemList.add("Not Set")
        itemList.add("Not Set")
        itemList.add("Not Set")
        itemList.add("Not Set")
        itemList.add("Not Set")
        itemList.add("Not Set")

        ingredient_spinner_adapter = ArrayAdapter<CharSequence>(activity!!.applicationContext,android.R.layout.simple_spinner_dropdown_item,itemList)
        itemSpinner.adapter = ingredient_spinner_adapter
        callback.updatePumpConfig()
        //Recipe ListView Code Here
        recipeItems.add("No Recipe Yet")
        recipelist_Adapter = ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,recipeItems)
        recipeListView.adapter = recipelist_Adapter
        // Create Drink Button for when user creates a drink
        //Code for said Button goes here
        createButton.setOnClickListener{
            val drinkName = drinkNameEditText.text.toString()
            val drink = Drink(drinkName,recipeItems)
            drink.pump_1 = ingredientAmountList[0]
            drink.pump_2 = ingredientAmountList[1]
            drink.pump_3 = ingredientAmountList[2]
            drink.pump_4 = ingredientAmountList[3]
            drink.pump_5 = ingredientAmountList[4]
            drink.pump_6 = ingredientAmountList[5]
            callback.sendDrinktoMain(drink)
            recipeItems.clear()
            recipeItems.add("No Recipe Yet")
            recipelist_Adapter = ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,recipeItems)
            recipeListView.adapter = recipelist_Adapter
            drinkNameEditText.text.clear()
        }
        // Add Ingredient to Recipe Button
        addItemButton.setOnClickListener{
            if(!TextUtils.isEmpty(ingredientAmount.text.toString())) {
                if (recipeItems[0] == "No Recipe Yet") {
                    recipeItems[0] = itemSpinner.selectedItem.toString()
                    recipelist_Adapter =
                        ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, recipeItems)
                } else {
                    recipeItems.add(itemSpinner.selectedItem.toString())
                    recipelist_Adapter =
                        ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, recipeItems)
                    //recipeListView.adapter = recipelist_Adapter
                }
                var itemAmount: Int = ingredientAmount.text.toString().toInt()
                var selectedItem: Int = itemSpinner.selectedItemPosition
                when (selectedItem) {
                    0 -> ingredientAmountList[0] = itemAmount
                    1 -> ingredientAmountList[1] = itemAmount
                    2 -> ingredientAmountList[2] = itemAmount
                    3 -> ingredientAmountList[3] = itemAmount
                    4 -> ingredientAmountList[4] = itemAmount
                    5 -> ingredientAmountList[5] = itemAmount
                }
                recipeListView.adapter = recipelist_Adapter
                ingredientAmount.text.clear()
            }else{
                Snackbar.make(view, "Amount cannot be empty", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
            }
        }
        // Inflate the layout for this fragment
        return view
    }


    interface onPumpConfigChangedListener{
        fun updatePumpConfig()// This Function will be called from Main Activity to update the pump config
        fun sendDrinktoMain(drink:Drink) // This Function will send the drink and its recipe to the main activity
    }


    fun setPumpConfigChangedListener(callback:onPumpConfigChangedListener){
        this.callback = callback
    }

    fun updateItemList(pumps:ArrayList<CharSequence>){// This Function updates the ItemList to most current Pump Configurations
        if(itemList != null) {
            itemList.clear()
            itemList = pumps
            ingredient_spinner_adapter = ArrayAdapter<CharSequence>(
                activity!!.applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                itemList
            )
            itemSpinner.adapter = ingredient_spinner_adapter
        }
    }
}