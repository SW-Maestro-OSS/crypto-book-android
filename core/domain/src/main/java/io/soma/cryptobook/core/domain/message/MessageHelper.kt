package io.soma.cryptobook.core.domain.message

interface MessageHelper {
    fun showLoading()
    fun hideLoading()
    fun showToast(message: String)
    fun showSnackbar(message: String, actionLabel: String? = null, onAction: (() -> Unit)? = null)
    fun dismissSnackbar()
}
