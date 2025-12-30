package io.soma.cryptobook.settings.domain.usecase

import io.soma.cryptobook.core.domain.message.MessageHelper
import javax.inject.Inject

class TempSnackbarMessageUseCase @Inject constructor(
    private val messageHelper: MessageHelper,
) {
    operator fun invoke() {
        messageHelper.showSnackbar(
            message = "스낵바 테스트 메시지입니다",
            actionLabel = "확인",
            onAction = { },
        )
    }
}
