package com.task.spacex.di

import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {

    @Provides
    fun provideGlide(fragment: Fragment): RequestManager =
        Glide.with(fragment)
}
