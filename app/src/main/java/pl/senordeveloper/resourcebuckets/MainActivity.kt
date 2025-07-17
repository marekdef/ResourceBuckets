package pl.senordeveloper.resourcebuckets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.senordeveloper.resourcebuckets.ui.theme.ResourceBucketsTheme

class MainActivity : ComponentActivity() {
    private val mainActivityViewModel by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ResourceBucketsTheme {
                // A surface container using the 'background' color from the theme
                val collectAsState = mainActivityViewModel.uiState.collectAsState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    BucketScreen(
                        modifier = Modifier.padding(innerPadding),
                        uiState = collectAsState.value,
                        onAction = mainActivityViewModel::displayDialog,
                        onGoBack = mainActivityViewModel::goBack
                    )
                }
            }
        }
    }
}

@Composable
fun BucketScreen(
    modifier: Modifier = Modifier,
    uiState: MainActivityViewModel.MainUiState,
    onAction: () -> Unit = {},
    onGoBack: () -> Unit = {}
) {
    when (uiState) {
        is MainActivityViewModel.MainUiState.MainScreen -> BucketContent(
            modifier,
            uiState,
            onAction = onAction
        )

        MainActivityViewModel.MainUiState.ShowDialog -> BucketInfo(modifier, onGoBack)
    }
}

@Composable
fun BucketInfo(modifier: Modifier = Modifier,
    onClose: () -> Unit = {}) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(id = R.string.explanation)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = onClose
        ) {
            Text(stringResource(id = R.string.go_back))
        }
    }
}

@Composable
fun BucketContent(
    modifier: Modifier = Modifier,
    mainScreen: MainActivityViewModel.MainUiState.MainScreen,
    onAction: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                stringResource(id = R.string.bucket_info),
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(stringResource(id = R.string.your_android))
            Text(stringResource(id = R.string.bucket))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                stringResource(
                    id = R.string.your_dpi,
                    mainScreen.dpi
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                stringResource(
                    id = R.string.your_resolution_in_px,
                    mainScreen.widthPixels,
                    mainScreen.heightPixels
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                stringResource(
                    id = R.string.your_resolution_in_dp,
                    mainScreen.screenWidthDp,
                    mainScreen.screenHeightDp
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                stringResource(
                    id = R.string.screen_ratio,
                    mainScreen.screenRatioDp,
                    mainScreen.screenRatioPx
                )
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = onAction
        ) {
            Text(stringResource(id = R.string.report_bucket))
            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(16.dp),
                painter = painterResource(id = R.drawable.android_in_bucket),
                contentDescription = ""
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BucketScreenPreview() {
    ResourceBucketsTheme {
        BucketScreen(
            uiState = MainActivityViewModel.MainUiState.MainScreen(
                dpi = 140,
                heightPixels = 640,
                widthPixels = 480,
                screenHeightDp = 640,
                screenWidthDp = 480
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BucketDialogPreview() {
    ResourceBucketsTheme {
        BucketScreen(
            uiState = MainActivityViewModel.MainUiState.ShowDialog
        )
    }
}