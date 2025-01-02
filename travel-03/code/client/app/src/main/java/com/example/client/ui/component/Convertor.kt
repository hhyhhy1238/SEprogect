package com.example.client.ui.component

fun ContextToBrief(context: String): String{
    if (context.length>=16){
        return context.substring(0,15)+"..."
    }
    else{
        return context
    }
}