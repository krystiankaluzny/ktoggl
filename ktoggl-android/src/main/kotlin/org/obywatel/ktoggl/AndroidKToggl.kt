package org.obywatel.ktoggl

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen

class AndroidKToggl {
    companion object {
        fun init(application: Application) {
            AndroidThreeTen.init(application)
        }

        fun init(context: Context) {
            AndroidThreeTen.init(context)
        }
    }
}