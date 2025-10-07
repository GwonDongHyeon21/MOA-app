package org.moa.moa.navigation.record

sealed class RecordNavigationItem(val route: String) {
    data object MainButton : RecordNavigationItem("main_button")
    data object RecordText : RecordNavigationItem("record_text")
    data object RecordCamera : RecordNavigationItem("record_camera")
    data object Recorder : RecordNavigationItem("recorder")
}