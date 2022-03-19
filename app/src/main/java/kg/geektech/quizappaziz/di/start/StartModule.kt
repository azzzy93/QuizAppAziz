package kg.geektech.quizappaziz.di.start

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.geektech.quizappaziz.data.start.StartRepositoryImpl
import kg.geektech.quizappaziz.data.start.remote.StartApi
import kg.geektech.quizappaziz.di.NetworkModule
import kg.geektech.quizappaziz.domain.start.repository.StartRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class StartModule {

    @Singleton
    @Provides
    fun provideStartApi(retrofit: Retrofit): StartApi {
        return retrofit.create(StartApi::class.java)
    }

    @Singleton
    @Provides
    fun provideStartRepository(mainApi: StartApi): StartRepository {
        return StartRepositoryImpl(mainApi)
    }

}