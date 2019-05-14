/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.droidmonk.appinfo.apps

import android.content.pm.PackageInfo
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.widget.ImageView

/**
 * Contains [BindingAdapter]s for the [Task] list.
 */
object AppListBindings {

    @BindingAdapter("app:items")
    @JvmStatic fun setItems(list: RecyclerView, items: List<PackageInfo>) {
        with(list.adapter as AppListAdapter) {
            replaceData(items)
        }
    }

    @BindingAdapter("app:img_drawable")
    @JvmStatic fun setImgDrawable(imageView: ImageView, image:Drawable) {
        imageView.setImageDrawable(image)
    }
}
