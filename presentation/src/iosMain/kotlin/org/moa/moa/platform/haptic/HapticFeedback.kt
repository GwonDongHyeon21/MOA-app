package org.moa.moa.platform.haptic

import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle

actual object Haptic {
    private val generator = UIImpactFeedbackGenerator(style = UIImpactFeedbackStyle.UIImpactFeedbackStyleMedium)

    actual fun vibrate() {
        generator.prepare()
        generator.impactOccurred()
    }
}
