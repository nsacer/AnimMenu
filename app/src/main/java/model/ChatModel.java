package model;

/**
 * Created by Administrator on 2017/8/30.
 * 聊天信息model
 */

public class ChatModel {

    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * 聊天内容
     */
    private String content;
    /**
     * 是否是我的消息，
     * true：显示在页面右边
     * false：显示在左边
     */
    private boolean isMine;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
