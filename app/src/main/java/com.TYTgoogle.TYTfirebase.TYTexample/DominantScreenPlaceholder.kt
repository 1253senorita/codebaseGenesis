package com.TYTgoogle.TYTfirebase.TYTexample

// In your MainActivity.kt or where DominantScreenPlaceholder is defined

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.TYTgoogle.TYTfirebase.TYTexample.data.ActionType
import kotlinx.coroutines.launch

import com.TYTgoogle.TYTfirebase.TYTexample.data.SeriesInfo


@Composable
fun DominantScreenPlaceholder(
    seriesInfo: SeriesInfo, // Pass the whole SeriesInfo object
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the screen's icon (your "ic object" idea for the screen itself)
        seriesInfo.iconResId?.let { iconRes ->
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "${seriesInfo.displayName} icon",
                modifier = Modifier.height(64.dp) // Adjust size as needed
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(
            text = "Dominant Screen for ${seriesInfo.displayName}",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "(ID: ${seriesInfo.id})",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Create a button for each ActionItem
        // Your AllSeriesDataInfoID.kt seems to have 8 actions for each series.
        // This will create a button for each of them.
        seriesInfo.initialActions.forEach { action ->
            Button(
                onClick = {
                    when (action.actionType) {
                        ActionType.NAVIGATE_SUB_DOMINANT -> {
                            action.targetRoute?.let { route ->
                                navController.navigate(route)
                            } ?: coroutineScope.launch {
                                snackbarHostState.showSnackbar("Error: Target route is missing for '${action.displayText}'")
                            }
                        }
                        ActionType.SHOW_INFO_DIALOG -> {
                            coroutineScope.launch {
                                // In a real app, you'd show a Dialog Composable here
                                snackbarHostState.showSnackbar("Info Dialog: ${action.displayText}")
                            }
                        }
                        ActionType.UPLOAD_DATA -> {
                            coroutineScope.launch {
                                // Handle data upload logic
                                snackbarHostState.showSnackbar("Upload Data: ${action.displayText} (Type: ${action.uploadDataType})")
                                // Example: navController.navigate("upload_screen/${action.uploadDataType}")
                            }
                        }
                        // Add cases for any other ActionType values you have
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                // Here, you use the properties of the 'action' (ActionItem object)
                // 'displayText' is one such property (value) associated with the 'action' (key-like object)
                Text(action.displayText)
            }
        }
    }
}