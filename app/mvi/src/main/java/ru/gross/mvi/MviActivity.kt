package ru.gross.mvi

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

abstract class MviActivity<TState : ViewState, TEffect : ViewEffect> :
    AppCompatActivity(), MviContract<TState, TEffect> {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch { collectState() }
        lifecycleScope.launch { collectEffect() }
    }
}