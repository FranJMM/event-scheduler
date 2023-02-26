package com.martinezmencias.eventscheduler.ui.favorites

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.martinezmencias.eventscheduler.R
import com.martinezmencias.eventscheduler.domain.Error
import com.martinezmencias.eventscheduler.domain.Event
import com.martinezmencias.eventscheduler.ui.list.ListFragmentDirections

fun Fragment.createFavoritestate(
    context: Context = requireContext(),
    navController: NavController = findNavController()
) = FavoritesState(context, navController)

class FavoritesState(private val context: Context, private val navController: NavController) {

    fun onFavoriteEventClicked(event: Event) {
        val action = FavoritesFragmentDirections.actionFavoritesToDetail(event.id)
        navController.navigate(action)
    }

    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> String.format(context.getString(R.string.server_error), error.code)
        is Error.Unknown -> String.format(context.getString(R.string.server_error), error.message)
    }
}