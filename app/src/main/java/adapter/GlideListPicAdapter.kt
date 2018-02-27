package adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.zpf.animmenu.R

/**
 * Created by Administrator on 2018/2/5.
 * Glide加载图片列表
 */
class GlideListPicAdapter constructor(private val requestManager: RequestManager, urls: ArrayList<String>, private val dpHeight: Int) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_glide_list_pic, urls) {

    private val requestOption = RequestOptions()
            .placeholder(R.mipmap.bg_loading)
            .error(R.mipmap.bg_loading)
            .fallback(R.mipmap.ask_help)

    override fun convert(helper: BaseViewHolder?, item: String?) {

        helper!!.setText(R.id.tvGlideListPicItem, item)

        val iv = helper.getView<ImageView>(R.id.ivGlideListPicItem)
        val llF = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dpHeight)
        iv.layoutParams = llF

        if (!item.isNullOrBlank() && item != iv.getTag(R.id.ivGlideListPicItem)) {

            iv.setTag(R.id.ivGlideListPicItem, item)
            requestManager.load(item)
                    .apply(requestOption)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                            if (resource != null) {

                                Log.i("====", "w: " + resource.intrinsicWidth + "\nh: " + resource.intrinsicHeight)
                                llF.width = dpHeight * (resource.intrinsicWidth / resource.intrinsicHeight)
                                iv.layoutParams = llF
                            }
                            return false
                        }
                    })
                    .thumbnail(0.4f)
                    .into(helper.getView(R.id.ivGlideListPicItem))
        }
    }
}