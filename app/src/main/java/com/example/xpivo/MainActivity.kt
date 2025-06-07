package com.example.xpivo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import com.example.xpivo.data.cache.DataStoreCache
import com.example.xpivo.navigation.PrimaryNavHost
import com.example.xpivo.ui.theme.PrimaryWhite
import com.example.xpivo.ui.theme.XPivoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var dataStoreCache: DataStoreCache
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XPivoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = PrimaryWhite
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding(),
                            start = 16.dp,
                            end = 16.dp
                        )
                    ) {
                        PrimaryNavHost()
                    }
                }
            }
        }
    }
}