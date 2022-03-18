package com.example.myapplication


data class Card(var czyWidoczna: Boolean, var czyOdgadnieta: Boolean, var id: Int)
{
    fun czyTakieSame(karta2: Card): Boolean {
        return (this.id == karta2.id);

    }


}