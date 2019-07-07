package com.sagarpatel.mysmartbartender

class Drink(name:String,recipe:ArrayList<String>){
    var drink_name = name
    var drink_recipe = recipe
    var pump_1:Int = 0
    var pump_2:Int = 0
    var pump_3:Int = 0
    var pump_4:Int = 0
    var pump_5:Int = 0
    var pump_6:Int = 0

    fun getName(): String {
        return drink_name
    }
    fun setName(newName:String){
        drink_name = newName
    }

    fun setRecipe(newRecipe:ArrayList<String>){
        drink_recipe.clear()
        drink_recipe = newRecipe
    }

    fun getRecipe():ArrayList<String>{
        return drink_recipe
    }



}