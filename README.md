# crypto-book-android

- Language: Kotlin
- Architecture: MVI
- UI: Jetpack Compose
- DI: Hilt
- Networking: Retrofit, OkHttp
- Data: Room, DataStore
- Concurrency: Kotlin Coroutines, Flow

## Development

### Code Style

This project uses Spotless for code formatting.

- Check formatting: `./gradlew spotlessCheck`
- Auto-fix formatting: `./gradlew spotlessApply`
- **Before committing, always run `./gradlew spotlessApply`**