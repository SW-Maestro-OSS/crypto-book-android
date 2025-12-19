package io.soma.cryptobook.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

/**
 * SideEffect의 일회성 이벤트 처리를 위한 확장 함수
 *
 * @param key LaunchedEffect를 재시작하기 위한 key
 * @param lifecycleOwner 현재 Composable의 생명주기
 * @param state Flow를 collect할 Lifecycle 상태
 * @param block 이벤트 처리 로직
 */

@Composable
fun <T> Flow<T>.collectWithLifecycle(
    key: Any? = true,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    block: (T) -> Unit,
) = LaunchedEffect(key) {
    lifecycleOwner.repeatOnLifecycle(state) {
        collect { block(it) }
    }
}
