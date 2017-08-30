package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zpf.animmenu.R;

import java.util.ArrayList;

import model.ChatModel;

/**
 * Created by Administrator on 2017/8/30.
 * 聊天页面的Adapter
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Context mContext;
    private ArrayList<ChatModel> models = new ArrayList<>();

    public ChatAdapter(Context context) {

        this.mContext = context;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_mine, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

        ChatModel model = models.get(position);
        holder.tvContent.setText(model.getContent());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAvatar;
        private TextView tvContent;

        ChatViewHolder(View itemView) {
            super(itemView);

            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_chat_item);
            tvContent = (TextView) itemView.findViewById(R.id.tv_chat_item);
        }
    }

    public ArrayList<ChatModel> getModels() {
        return models;
    }

    public void setModels(ArrayList<ChatModel> models) {

        if (models != null && !models.isEmpty()) {

            this.models = models;
            notifyDataSetChanged();
        }
    }
}
