package io.soma.cryptobook.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.soma.cryptobook.core.data.UserPreferences
import io.soma.cryptobook.core.data.datastore.UserPreferencesSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsDataStoreModule {
    @Provides
    @Singleton
    internal fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        userPreferenceSerializer: UserPreferencesSerializer,
    ): DataStore<UserPreferences> = DataStoreFactory.create(
        serializer = userPreferenceSerializer,
    ) {
        context.dataStoreFile("user_preferences.pb")
    }
}
