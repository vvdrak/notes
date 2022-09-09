package androidx.fragment.app

import androidx.activity.ComponentActivity
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

internal fun <VB : ViewDataBinding> ComponentActivity.dataBindings(
    @LayoutRes layoutId: Int
): Lazy<VB> = createDataBindingLazy(layoutId)

@MainThread
private fun <VB : ViewDataBinding> ComponentActivity.createDataBindingLazy(
    @LayoutRes layoutId: Int
): Lazy<VB> {
    val lifecycleProducer = { this }
    return DataBindingLazy(lifecycleProducer) {
        DataBindingUtil.setContentView(this, layoutId)
    }
}