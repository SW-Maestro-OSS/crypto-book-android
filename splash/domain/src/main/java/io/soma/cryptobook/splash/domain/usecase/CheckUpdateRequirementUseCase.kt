package io.soma.cryptobook.splash.domain.usecase

import io.soma.cryptobook.splash.domain.repository.AppInitRepository
import javax.inject.Inject

class CheckUpdateRequirementUseCase @Inject constructor(
    private val appInitRepository: AppInitRepository,
) {
    suspend operator fun invoke(currentVersion: String): Boolean {
        val minVersion = appInitRepository.getAppVersionInfo().minVersion
        return isUpdatedRequired(currentVersion, minVersion)
    }

    private fun isUpdatedRequired(currentVersion: String, minVersion: String): Boolean {
        val currentParts = currentVersion.split(".").map { it.toIntOrNull() ?: 0 }
        val minParts = minVersion.split(".").map { it.toIntOrNull() ?: 0 }

        for (i in 0 until maxOf(currentParts.size, minParts.size)) {
            val currentPart = currentParts.getOrElse(i) { 0 }
            val minPart = minParts.getOrElse(i) { 0 }
            if (currentPart < minPart) return true
            if (currentPart > minPart) return false
        }
        return false
    }
}
