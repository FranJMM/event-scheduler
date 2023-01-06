package com.martinezmencias.eventscheduler.ui.list

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.martinezmencias.eventscheduler.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.createListState(
    context: Context = requireContext(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester = PermissionRequester(this)
) = ListState(context, scope, navController, locationPermissionRequester)

class ListState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val navController: NavController,
    private val permissionRequester: PermissionRequester
) {
    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = permissionRequester.request(Manifest.permission.ACCESS_COARSE_LOCATION)
            afterRequest(result)
        }
    }
}