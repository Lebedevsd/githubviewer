package com.lebedevsd.githubviewer.di.scheduler

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Qualifier

/**
 * Scheduler that will be used for background work
 */
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
annotation class BackgroundTaskScheduler
