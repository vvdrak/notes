package androidx.fragment.app

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class DataBindingLazy<VB : ViewDataBinding>(
    private val lifecycleProducer: () -> LifecycleOwner,
    private val dataBindingProducer: () -> VB
) : Lazy<VB> {
    private var cached: VB? = null
    private val lifecycleObserver = ClearOnDestroyLifecycleObserver()

    override val value: VB
        get() {
            checkIsMainThread()

            val binding = cached
            return if (binding == null) {
                lifecycleProducer().lifecycle.addObserver(lifecycleObserver)
                dataBindingProducer().also { cached = it }
            } else {
                binding
            }
        }

    override fun isInitialized(): Boolean = cached != null

    @MainThread
    fun clear() {
        checkIsMainThread()
        lifecycleProducer().lifecycle.removeObserver(lifecycleObserver)
        mainHandler.post { cached = null }
    }

    private fun checkIsMainThread() = check(Looper.myLooper() == Looper.getMainLooper())

    private inner class ClearOnDestroyLifecycleObserver : DefaultLifecycleObserver {
        @MainThread
        override fun onDestroy(owner: LifecycleOwner): Unit = clear()
    }

    private companion object {
        private val mainHandler = Handler(Looper.getMainLooper())
    }
}