package io.soma.cryptobook.settings.domain.usecase

import io.soma.cryptobook.settings.domain.model.UserData
import io.soma.cryptobook.settings.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
) {
    operator fun invoke(): Flow<UserData> = userDataRepository.userData
}
