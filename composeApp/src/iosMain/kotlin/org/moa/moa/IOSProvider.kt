package org.moa.moa

import org.moa.moa.platform.PlatformProvider
import platform.UIKit.UIDevice

class IOSProvider : PlatformProvider {
    override fun getGreeting(): String {
        return UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    }
}