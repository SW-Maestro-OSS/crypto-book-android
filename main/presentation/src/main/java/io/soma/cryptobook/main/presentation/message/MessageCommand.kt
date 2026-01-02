package io.soma.cryptobook.main.presentation.message

import kotlinx.coroutines.flow.SharedFlow

sealed interface MessageCommand {
    data object ShowLoading : MessageCommand
    data object HideLoading : MessageCommand
    data class ShowToast(val message: String) : MessageCommand
    data class ShowSnackbar(
        val message: String,
        val actionLabel: String? = null,
        val onAction: (() -> Unit)? = null,
    ) : MessageCommand

    data object DismissSnackbar : MessageCommand
}

interface MessageCommandSource {
    val commands: SharedFlow<MessageCommand>
}
