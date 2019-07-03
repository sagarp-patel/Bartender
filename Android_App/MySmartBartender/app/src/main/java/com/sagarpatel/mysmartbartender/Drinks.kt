package com.sagarpatel.mysmartbartender

class Drink(name:String,recipe:ArrayList<String>){
    var drink_name = name
    var drink_recipe = recipe
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