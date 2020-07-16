@file:Suppress("UNCHECKED_CAST")

package ru.gross.notes.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * Описывает фабрику [ViewModel] использующую механизм внедрения зависимостей.
 * @author gross_va
 */
class InjectableViewModelFactory @Inject constructor(
    private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(vmClass: Class<T>): T {
        val creator = viewModels[vmClass] ?: viewModels.asIterable()
            .firstOrNull { vmClass.isAssignableFrom(it.key) }?.value
        ?: throw IllegalArgumentException("unknown model class $vmClass")
        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}

/**
 * [ViewModelProvider.Factory] объекты для внедрения
 * @author gross_va
 */
@Module
interface ViewModelFactoryModule {
    @Binds
    fun bindViewModelFactory(factory: InjectableViewModelFactory): ViewModelProvider.Factory
}

/**
 * Аннотация, помогающая Dagger различать реализации одного суперкласса [ViewModel]
 * @author gross_va
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)