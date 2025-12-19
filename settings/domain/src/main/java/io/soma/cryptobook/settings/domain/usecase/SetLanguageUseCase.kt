package io.soma.cryptobook.settings.domain.usecase

import io.soma.cryptobook.settings.domain.model.Language
import io.soma.cryptobook.settings.domain.repository.UserDataRepository
import javax.inject.Inject

class SetLanguageUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
) {
    suspend operator fun invoke(language: Language) {
        userDataRepository.setLanguage(language)
    }
}
