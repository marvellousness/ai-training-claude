package nova.publish.bazarbooks.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import nova.publish.bazarbooks.core.data.repository.FakeAuthRepository
import nova.publish.bazarbooks.core.data.repository.FakeBookRepository
import nova.publish.bazarbooks.core.data.repository.FakeCartRepository
import nova.publish.bazarbooks.core.data.repository.FakeNotificationRepository
import nova.publish.bazarbooks.core.data.repository.FakeOrderRepository
import nova.publish.bazarbooks.core.data.repository.FakeUserRepository
import nova.publish.bazarbooks.core.domain.repository.AuthRepository
import nova.publish.bazarbooks.core.domain.repository.BookRepository
import nova.publish.bazarbooks.core.domain.repository.CartRepository
import nova.publish.bazarbooks.core.domain.repository.NotificationRepository
import nova.publish.bazarbooks.core.domain.repository.OrderRepository
import nova.publish.bazarbooks.core.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = FakeAuthRepository()

    @Provides
    @Singleton
    fun provideBookRepository(): BookRepository = FakeBookRepository()

    @Provides
    @Singleton
    fun provideCartRepository(): CartRepository = FakeCartRepository()

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
