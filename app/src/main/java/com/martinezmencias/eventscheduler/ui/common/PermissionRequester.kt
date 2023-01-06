package com.martinezmencias.eventscheduler.ui.common

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PermissionRequester(fragment: Fragment) {

    private var onResult: (Boolean) -> Unit = {}
    private val launcher =
        fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            onResult(isGranted)
        }

    suspend fun request(permission: String): Boolean =
        suspendCancellableCoroutine { continuation ->
            onResult = {
                continuation.resume(it)
            }
            launcher.launch(permission)
        }
}