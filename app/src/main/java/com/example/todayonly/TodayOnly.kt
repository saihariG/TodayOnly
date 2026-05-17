package com.example.todayonly

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// App entry point
@HiltAndroidApp // creates application level dependency container
class TodayOnly: Application()