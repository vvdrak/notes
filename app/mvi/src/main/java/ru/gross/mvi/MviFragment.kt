package ru.gross.mvi

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

abstract class MviFragment<TState : ViewState, TEffect : ViewEffect>(
    @LayoutRes layoutId: Int
) : Fragment(layoutId), MviContract<TState, TEffect> {

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch { collectState() }
        viewLifecycleOwner.lifecycleScope.launch { collectEffect() }
    }
}