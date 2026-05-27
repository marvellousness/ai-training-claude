package nova.publish.bazarbooks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import nova.publish.bazarbooks.core.designsystem.theme.BazarBooksTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BazarBooksTheme {
                BazarBooksApp()
            }
        }
    }
}
