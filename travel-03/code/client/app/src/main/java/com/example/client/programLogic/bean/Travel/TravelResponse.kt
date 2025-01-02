package com.example.client.programLogic.bean.Travel

data class TravelResponse (
    val success: Boolean ,
    val travelPlanDetails: TravelDetail? = null
)