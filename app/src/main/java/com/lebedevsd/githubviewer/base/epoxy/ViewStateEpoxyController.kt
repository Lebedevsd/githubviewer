package com.lebedevsd.githubviewer.base.epoxy

import android.os.Handler
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.TypedEpoxyController
import com.lebedevsd.githubviewer.base.ui.LCE

abstract class ViewStateEpoxyController<T>(
    modelBuildingHandler: Handler = EpoxyAsyncUtil.getAsyncBackgroundHandler(),
    diffingHandler: Handler = EpoxyAsyncUtil.getAsyncBackgroundHandler()
) : TypedEpoxyController<LCE<T>>(modelBuildingHandler, diffingHandler)
