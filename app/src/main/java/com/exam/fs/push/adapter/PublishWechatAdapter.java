package com.exam.fs.push.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.exam.fs.push.R;
import com.exam.fs.push.databinding.AdapterPublishWechatBinding;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;

import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;
import cn.droidlover.xdroidbase.kit.AppUtils;
import cn.droidlover.xdroidbase.kit.Kits;

public class PublishWechatAdapter extends SimpleRecAdapter<LocalMedia, SimpleRecBindingViewHolder<AdapterPublishWechatBinding>> {
    private int selectMax = 9;
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    /**
     * 点击添加图片跳转
     */
    private OnAddPicClickListener mOnAddPicClickListener;

    public interface OnAddPicClickListener {
        void onAddPicClick();
    }

    public void setOnAddPicClickListener(OnAddPicClickListener onAddPicClickListener){
        this.mOnAddPicClickListener = onAddPicClickListener;
    }

    public PublishWechatAdapter(Context context) {
        super(context);
    }

    @Override
    public SimpleRecBindingViewHolder<AdapterPublishWechatBinding> newViewHolder(View view) {
        return new SimpleRecBindingViewHolder(view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_publish_wechat;
    }

    @Override
    public int getItemCount() {
        if (data.size() < selectMax) {
            return data.size() + 1;
        } else {
            return data.size();
        }
    }

    private boolean isShowAddItem(int position) {
        int size = data.size() == 0 ? 0 : data.size();
        return position == size;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }


    @Override
    public void onBindViewHolder(SimpleRecBindingViewHolder<AdapterPublishWechatBinding> holder, int position) {
        //少于8张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            holder.getBinding().imgPic.setImageResource(R.drawable.icon_add_pic);
            holder.getBinding().imgPic.setOnClickListener(v -> mOnAddPicClickListener.onAddPicClick());
            holder.getBinding().ivDel.setVisibility(View.INVISIBLE);
        } else {
            holder.getBinding().ivDel.setVisibility(View.VISIBLE);
            holder.getBinding().ivDel.setOnClickListener(view -> {
                int index = holder.getAdapterPosition();
                // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                if (index != RecyclerView.NO_POSITION && data.size() > index) {
                    data.remove(index);
                    notifyItemRemoved(index);
                    notifyItemRangeChanged(index, data.size());
                }
            });
            LocalMedia media = data.get(position);
            if (media == null
                    || TextUtils.isEmpty(media.getPath())) {
                return;
            }
            int chooseModel = media.getChooseModel();
            String path;
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }

            long duration = media.getDuration();
            holder.getBinding().tvDuration.setVisibility(PictureMimeType.eqVideo(media.getMimeType())
                    ? View.VISIBLE : View.GONE);
            if (chooseModel == PictureMimeType.ofAudio()) {
                holder.getBinding().tvDuration.setVisibility(View.VISIBLE);
                holder.getBinding().tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds
                        (R.drawable.picture_icon_audio, 0, 0, 0);

            } else {
                holder.getBinding().tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds
                        (R.drawable.picture_icon_video, 0, 0, 0);
            }
            holder.getBinding().tvDuration.setText(DateUtils.formatDurationTime(duration));
            if (chooseModel != PictureMimeType.ofAudio()) {
                Glide.with(context)
                        .load(path.startsWith("content://") && !media.isCut() && !media.isCompressed() ? Uri.parse(path)
                                : path)
                        .centerCrop()
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(Kits.Dimens.dpToPxInt(AppUtils.getAppContext(), 4))))
                        .placeholder(R.color.color_f6f6f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.getBinding().imgPic);
            }
            //itemView 的点击事件
            if (mItemClickListener != null) {
                holder.itemView.setOnClickListener(v -> {
                    int adapterPosition = holder.getAdapterPosition();
                    mItemClickListener.onItemClick(adapterPosition, v);
                });
            }
        }
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
