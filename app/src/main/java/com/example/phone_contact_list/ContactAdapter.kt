package com.example.phone_contact_list

class ContactAdapter {

    // Initialize variables

    lateinit var name: String
    lateinit var number: String

    fun getName(): String{
        return name
    }

    fun getNumber(): String{
        return name
    }

    fun setName(name: String){
        this.name = name
    }

    fun setNumber(number:String){
        this.number = number
    }
}