package io.soma.cryptobook.settings.domain.usecase

import io.soma.cryptobook.core.domain.message.MessageHelper
import kotlinx.coroutines.delay
import javax.inject.Inject

class TempLoadingMessageUseCase @Inject constructor(
    private val messageHelper: MessageHelper,
) {
    suspend operator fun invoke() {
        messageHelper.showLoading()
        delay(3000L)
        messageHelper.hideLoading()
    }
}
