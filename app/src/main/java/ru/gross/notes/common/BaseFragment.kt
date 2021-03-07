package ru.gross.notes.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.dataBindings

abstract class BaseFragment<VB : ViewDataBinding>(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {
    protected val binding: VB by dataBindings(contentLayoutId)

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding.root
}

