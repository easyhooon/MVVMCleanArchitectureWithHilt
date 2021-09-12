package com.kenshi.newsapiclient.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//this class does not need a class body
@HiltAndroidApp
class NewsApp: Application() {
}

//When we were using dagger we had a lot of other codes
//in this application class.

//But this is all we need for dagger hilt

//We also don't need to create component interfaces.
//(modules are needed)
