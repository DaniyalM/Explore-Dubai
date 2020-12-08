package com.app.dubalculture.cronjobs

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

abstract class BaseWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
}