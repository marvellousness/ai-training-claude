package nova.publish.bazarbooks.core.data.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import nova.publish.bazarbooks.core.data.local.BazarBooksDatabase
import nova.publish.bazarbooks.core.data.local.source.BookLocalDataSource
import nova.publish.bazarbooks.core.data.local.source.CartLocalDataSource
import nova.publish.bazarbooks.core.data.preferences.UserPreferencesDataSource
import nova.publish.bazarbooks.core.data.remote.SimulatedBazarRemoteDataSource
import nova.publish.bazarbooks.core.data.repository.DataStoreAuthRepository
import nova.publish.bazarbooks.core.data.repository.FakeNotificationRepository
import nova.publish.bazarbooks.core.data.repository.FakeOrderRepository
import nova.publish.bazarbooks.core.data.repository.FakeUserRepository
import nova.publish.bazarbooks.core.data.repository.OfflineFirstAddressRepository
import nova.publish.bazarbooks.core.data.repository.OfflineFirstBookRepository
import nova.publish.bazarbooks.core.data.repository.OfflineFirstCartRepository
import nova.publish.bazarbooks.core.domain.repository.AddressRepository
import nova.publish.bazarbooks.core.domain.repository.AuthRepository
import nova.publish.bazarbooks.core.domain.repository.BookRepository
import nova.publish.bazarbooks.core.domain.repository.CartRepository
import nova.publish.bazarbooks.core.domain.repository.NotificationRepository
import nova.publish.bazarbooks.core.domain.repository.OrderRepository
import nova.publish.bazarbooks.core.domain.repository.UserRepository
import nova.publish.bazarbooks.core.domain.usecase.auth.LoginUseCase
import nova.publish.bazarbooks.core.domain.usecase.auth.CompleteOnboardingUseCase
import nova.publish.bazarbooks.core.domain.usecase.auth.ContinueAsGuestUseCase
import nova.publish.bazarbooks.core.domain.usecase.auth.ObserveOnboardingCompletedUseCase
import nova.publish.bazarbooks.core.domain.usecase.auth.ObserveSessionUseCase
import nova.publish.bazarbooks.core.domain.usecase.auth.RequestPasswordResetUseCase
import nova.publish.bazarbooks.core.domain.usecase.auth.SignUpUseCase

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BazarBooksDatabase = Room.databaseBuilder(
        context,
        BazarBooksDatabase::class.java,
        "bazar-books.db",
    ).fallbackToDestructiveMigration(dropAllTables = true).build()

    @Provides
    @Singleton
    fun provideBookLocalDataSource(database: BazarBooksDatabase) = BookLocalDataSource(database.bookDao(), database.figmaMetadataDao())

    @Provides
    @Singleton
    fun provideCartLocalDataSource(database: BazarBooksDatabase) = CartLocalDataSource(database.cartDao())

    @Provides
    @Singleton
    fun provideSimulatedRemoteDataSource() = SimulatedBazarRemoteDataSource()

    @Provides
    @Singleton
    fun provideUserPreferencesDataSource(@ApplicationContext context: Context): UserPreferencesDataSource {
        val dataStore = PreferenceDataStoreFactory.create {
            context.filesDir.resolve("datastore").also { it.mkdirs() }.resolve("user.preferences_pb")
        }
        return UserPreferencesDataSource(dataStore)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(preferences: UserPreferencesDataSource): AuthRepository = DataStoreAuthRepository(preferences)

    @Provides
    fun provideLoginUseCase(repository: AuthRepository) = LoginUseCase(repository)

    @Provides
    fun provideContinueAsGuestUseCase(repository: AuthRepository) = ContinueAsGuestUseCase(repository)

    @Provides
    fun provideCompleteOnboardingUseCase(repository: AuthRepository) = CompleteOnboardingUseCase(repository)

    @Provides
    fun provideObserveOnboardingCompletedUseCase(repository: AuthRepository) = ObserveOnboardingCompletedUseCase(repository)

    @Provides
    fun provideObserveSessionUseCase(repository: AuthRepository) = ObserveSessionUseCase(repository)

    @Provides
    fun provideSignUpUseCase(repository: AuthRepository) = SignUpUseCase(repository)

    @Provides
    fun provideRequestPasswordResetUseCase(repository: AuthRepository) = RequestPasswordResetUseCase(repository)

    @Provides
    @Singleton
    fun provideBookRepository(
        localDataSource: BookLocalDataSource,
        remoteDataSource: SimulatedBazarRemoteDataSource,
    ): BookRepository = OfflineFirstBookRepository(localDataSource, remoteDataSource)

    @Provides
    @Singleton
    fun provideCartRepository(localDataSource: CartLocalDataSource): CartRepository = OfflineFirstCartRepository(localDataSource)

    @Provides
    @Singleton
    fun provideAddressRepository(database: BazarBooksDatabase): AddressRepository = OfflineFirstAddressRepository(database.figmaMetadataDao())

    @Provides
    @Singleton
    fun provideOrderRepository(cartRepository: CartRepository): OrderRepository = FakeOrderRepository(cartRepository)

    @Provides
    @Singleton
    fun provideNotificationRepository(): NotificationRepository = FakeNotificationRepository()

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository = FakeUserRepository()
}
