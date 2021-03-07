package androidx.fragment.app

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

@MainThread
fun <VB : ViewDataBinding> Fragment.dataBindings(
    rootProducer: () -> View? = { view }
): Lazy<VB> = createDataBindingLazy {
    val root = rootProducer() ?: throw IllegalStateException("Root view can not be null")
    DataBindingUtil.bind(root)!!
}

@MainThread
fun <VB : ViewDataBinding> Fragment.dataBindings(
    @LayoutRes layoutId: Int,
    parentProducer: () -> ViewGroup? = { mContainer }
): Lazy<VB> = createDataBindingLazy {
    val parent = parentProducer()
    DataBindingUtil.inflate(layoutInflater, layoutId, parent, false)
}

@MainThread
fun <VB : ViewDataBinding> Fragment.createDataBindingLazy(
    dataBindingProducer: () -> VB
): Lazy<VB> {
    val lifecycleProducer = { viewLifecycleOwner }
    return DataBindingLazy(lifecycleProducer, dataBindingProducer)
}