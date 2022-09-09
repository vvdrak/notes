package androidx.fragment.app

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

@MainThread
internal fun <VB : ViewDataBinding> Fragment.dataBindings(
    bindingProducer: (View) -> VB
): Lazy<VB> = createDataBindingLazy {
    bindingProducer(view ?: throw IllegalStateException("Root view can not be null"))
}

@MainThread
internal fun <VB : ViewDataBinding> Fragment.dataBindings(
    rootProducer: () -> View? = { view }
): Lazy<VB> = createDataBindingLazy {
    val root = rootProducer() ?: throw IllegalStateException("Root view can not be null")
    DataBindingUtil.bind(root)!!
}

@MainThread
internal fun <VB : ViewDataBinding> Fragment.dataBindings(
    @LayoutRes layoutId: Int,
    parentProducer: () -> ViewGroup? = { mContainer }
): Lazy<VB> = createDataBindingLazy {
    val parent = parentProducer()
    DataBindingUtil.inflate(layoutInflater, layoutId, parent, false)
}

@MainThread
private fun <VB : ViewDataBinding> Fragment.createDataBindingLazy(
    dataBindingProducer: () -> VB
): Lazy<VB> {
    val lifecycleProducer = { viewLifecycleOwner }
    return DataBindingLazy(lifecycleProducer, dataBindingProducer)
}