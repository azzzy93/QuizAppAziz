package kg.geektech.quizappaziz.di.game

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.geektech.quizappaziz.data.game.GameRepositoryImpl
import kg.geektech.quizappaziz.data.game.remote.GameApi
import kg.geektech.quizappaziz.di.NetworkModule
import kg.geektech.quizappaziz.domain.game.repository.GameRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class GameModule {

    @Singleton
    @Provides
    fun provideGameApi(retrofit: Retrofit): GameApi {
        return retrofit.create(GameApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGameRepository(gameApi: GameApi): GameRepository {
        return GameRepositoryImpl(gameApi)
    }

}