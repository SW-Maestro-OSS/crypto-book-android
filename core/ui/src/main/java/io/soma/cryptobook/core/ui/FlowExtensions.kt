package io.soma.cryptobook.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

// SideEffect의 일회성 이벤트 처리를 위한 확장함수
@Composable
fun <T> Flow<T>.collectWithLifecycle(
    key: Any? = true,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,   // 현재 Composable의 생명주기
    state: Lifecycle.State = Lifecycle.State.STARTED,
    block: (T) -> Unit                                              // 이벤트 처리 로직
) = LaunchedEffect(key) {
    lifecycleOwner.repeatOnLifecycle(state) {
        collect { block(it) }
    }
}