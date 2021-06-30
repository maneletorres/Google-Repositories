package com.example.googlerepositories.util.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.googlerepositories.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (T) -> Unit) {
    liveData.observe(this, Observer(observer))
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(R.drawable.ic_placeholder_image).into(this)
}

fun View.getString(resourceId: Int) = resources.getString(resourceId)

fun CardView.validate(itemView: View) =
    setCardBackgroundColor(itemView.resources.getColor(R.color.green_fork, null))

fun Context.toast(message: String, context: Context = this, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(context, message, length).show()

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) =
    requireActivity().toast(message, requireContext(), length)