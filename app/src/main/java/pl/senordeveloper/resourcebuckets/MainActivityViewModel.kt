package pl.senordeveloper.resourcebuckets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {


    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(
        mainScreenState(application)
    )

    private fun mainScreenState(application: Application) = MainUiState.MainScreen(
        dpi = application.resources.configuration.densityDpi,
        widthPixels = application.resources.displayMetrics.widthPixels,
        heightPixels = application.resources.displayMetrics.heightPixels,
        screenWidthDp = application.resources.configuration.screenWidthDp,
        screenHeightDp = application.resources.configuration.screenHeightDp,
    )

    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun displayDialog() {
        _uiState.value = MainUiState.ShowDialog
    }

    fun goBack() {
        _uiState.value = mainScreenState(getApplication())
    }

    sealed class MainUiState() {
        data object ShowDialog : MainUiState()

        data class MainScreen(
            val dpi: Int,
            val widthPixels: Int,
            val heightPixels: Int,
            val screenWidthDp: Int,
            val screenHeightDp: Int
        ) : MainUiState() {
            val screenRatioDp = screenHeightDp.toFloat().div(screenWidthDp)
            val screenRatioPx = heightPixels.toFloat().div(widthPixels)
        }
    }
}
