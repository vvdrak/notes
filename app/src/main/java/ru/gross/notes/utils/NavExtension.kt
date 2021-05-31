package ru.gross.notes.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController


/**
 * Выполняет переход вверх по графику навигации.
 *
 * @author gross_va
 */
@MainThread
fun Fragment.navigateUp() = navigateUp(findNavController())

/**
 * Выполняет переход вверх по графику навигации.
 *
 * @author gross_va
 */
@MainThread
fun navigateUp(navController: NavController) =
    navController.navigateUp()

/**
 * Выполняет безопасный переход к пункту назначения из текущего графика навигации
 *
 * @param navController Контроллер навигации [NavController]
 * @param direction Описание навигации [NavDirections]
 * @author gross_va
 */
@MainThread
fun navigate(
    navController: NavController,
    direction: NavDirections,
    navOptions: NavOptions? = null,
    navExtras: Navigator.Extras? = null
) = navigate(navController, direction.actionId, direction.arguments, navOptions, navExtras)

/**
 * Выполняет безопасный переход к пункту назначения из текущего графика навигации.
 * @param navController Контроллер навигации [NavController]
 * @param resId Идентификатор места назначения
 * @param args Аргументы для передачи к месту назначения
 * @param navOptions Специальные параметры для навигации
 * @param navExtras Индикатор специфического поведения [Navigator]
 * @author gross_va
 */
private fun navigate(
    navController: NavController,
    resId: Int,
    args: Bundle?,
    navOptions: NavOptions?,
    navExtras: Navigator.Extras?
) {
    val action =
        navController.currentDestination?.getAction(resId) ?: navController.graph.getAction(resId)
    if (navController.currentDestination?.id != action?.destinationId) {
        navController.navigate(resId, args, navOptions, navExtras)
    }
}

/**
 * Вовзращает текущий [фрагмент][Fragment] в рамках Jetpack Navigation.
 * @param navHostId Идентификатор хоста.
 * @param fragmentManagerProducer Функция извлечения [FragmentManager]
 */
private fun currentFragment(
    @IdRes navHostId: Int,
    fragmentManagerProducer: () -> FragmentManager
): Fragment? {
    return fragmentManagerProducer().findFragmentById(navHostId)
        ?.childFragmentManager
        ?.fragments
        ?.get(0)
}

/**
 * Возвращает текущий [фрагмент][Fragment], отображаемый на этой [активити][FragmentActivity]
 */
fun FragmentActivity.currentFragment(@IdRes navHostId: Int): Fragment? {
    return currentFragment(navHostId) { supportFragmentManager }
}