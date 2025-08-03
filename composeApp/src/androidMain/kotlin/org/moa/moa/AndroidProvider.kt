package org.moa.moa

import android.os.Build
import org.moa.moa.platform.PlatformProvider

class AndroidProvider : PlatformProvider {
    override fun getGreeting(): String {
        return "Android ${Build.VERSION.SDK_INT}"
    }
}