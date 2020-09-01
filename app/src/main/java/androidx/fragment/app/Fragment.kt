package androidx.fragment.app

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <VB : ViewDataBinding> Fragment.dataBinding(
    @LayoutRes contentLayoutId: Int,
    container: ViewGroup? = null
): Lazy<VB> {
    val c = container ?: mContainer
    return BindingLazy { DataBindingUtil.inflate(layoutInflater, contentLayoutId, c, false) }
}

class BindingLazy<VB : ViewDataBinding>(
    private val producer: () -> VB
) : Lazy<VB> {
    private var cached: VB? = null

    override val value: VB
        get() = cached ?: producer().also { cached = it }

    override fun isInitialized(): Boolean = cached != null
}