package emotion.util;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.example.zpf.animmenu.R;

/**
 * @author : zejian
 * @time : 2016年1月5日 上午11:32:33
 * @email : shinezejian@163.com
 * @description :表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 */
public class EmotionUtils {
    /**
     * 表情类型标志符
     */
    public static final int EMOTION_CLASSIC_TYPE = 0x0001;//经典表情
    /**
     * key-表情文字;
     * value-表情图片资源
     */
    private static ArrayMap<String, Integer> EMPTY_MAP;
    private static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP;

    static {
        EMPTY_MAP = new ArrayMap<>();
        EMOTION_CLASSIC_MAP = new ArrayMap<>();
        EMOTION_CLASSIC_MAP.put("[呵呵]", R.mipmap.face1);
        EMOTION_CLASSIC_MAP.put("[嘻嘻]", R.mipmap.face2);
        EMOTION_CLASSIC_MAP.put("[哈哈]", R.mipmap.face3);
        EMOTION_CLASSIC_MAP.put("[爱你]", R.mipmap.face4);
        EMOTION_CLASSIC_MAP.put("[挖鼻屎]", R.mipmap.face5);
        EMOTION_CLASSIC_MAP.put("[吃惊]", R.mipmap.face6);
        EMOTION_CLASSIC_MAP.put("[晕]", R.mipmap.face7);
        EMOTION_CLASSIC_MAP.put("[泪]", R.mipmap.face8);
        EMOTION_CLASSIC_MAP.put("[馋嘴]", R.mipmap.face9);
        EMOTION_CLASSIC_MAP.put("[抓狂]", R.mipmap.face10);
        EMOTION_CLASSIC_MAP.put("[哼]", R.mipmap.face11);
        EMOTION_CLASSIC_MAP.put("[可爱]", R.mipmap.face12);
        EMOTION_CLASSIC_MAP.put("[怒]", R.mipmap.face13);
        EMOTION_CLASSIC_MAP.put("[汗]", R.mipmap.face14);
        EMOTION_CLASSIC_MAP.put("[害羞]", R.mipmap.face15);
        EMOTION_CLASSIC_MAP.put("[睡觉]", R.mipmap.face16);
        EMOTION_CLASSIC_MAP.put("[钱]", R.mipmap.face17);
        EMOTION_CLASSIC_MAP.put("[偷笑]", R.mipmap.face18);
        EMOTION_CLASSIC_MAP.put("[笑cry]", R.mipmap.face19);
        EMOTION_CLASSIC_MAP.put("[doge]", R.mipmap.face20);
        EMOTION_CLASSIC_MAP.put("[喵喵]", R.mipmap.face21);
        EMOTION_CLASSIC_MAP.put("[酷]", R.mipmap.face22);
        EMOTION_CLASSIC_MAP.put("[衰]", R.mipmap.face23);
        EMOTION_CLASSIC_MAP.put("[闭嘴]", R.mipmap.face24);
        EMOTION_CLASSIC_MAP.put("[鄙视]", R.mipmap.face25);
        EMOTION_CLASSIC_MAP.put("[花心]", R.mipmap.face26);
        EMOTION_CLASSIC_MAP.put("[鼓掌]", R.mipmap.face27);

        EMOTION_CLASSIC_MAP.put("[11]", R.mipmap.face1);
        EMOTION_CLASSIC_MAP.put("[12]", R.mipmap.face2);
        EMOTION_CLASSIC_MAP.put("[13]", R.mipmap.face3);
        EMOTION_CLASSIC_MAP.put("[14]", R.mipmap.face4);
        EMOTION_CLASSIC_MAP.put("[15]", R.mipmap.face5);
        EMOTION_CLASSIC_MAP.put("[16]", R.mipmap.face6);
        EMOTION_CLASSIC_MAP.put("[17]", R.mipmap.face7);
        EMOTION_CLASSIC_MAP.put("[18]", R.mipmap.face8);
        EMOTION_CLASSIC_MAP.put("[19]", R.mipmap.face9);
        EMOTION_CLASSIC_MAP.put("[20]", R.mipmap.face10);
        EMOTION_CLASSIC_MAP.put("[21]", R.mipmap.face11);
        EMOTION_CLASSIC_MAP.put("[22]", R.mipmap.face12);
        EMOTION_CLASSIC_MAP.put("[23]", R.mipmap.face13);
        EMOTION_CLASSIC_MAP.put("[24]", R.mipmap.face14);
        EMOTION_CLASSIC_MAP.put("[25]", R.mipmap.face15);
        EMOTION_CLASSIC_MAP.put("[26]", R.mipmap.face16);
        EMOTION_CLASSIC_MAP.put("[27]", R.mipmap.face17);
        EMOTION_CLASSIC_MAP.put("[28]", R.mipmap.face18);
        EMOTION_CLASSIC_MAP.put("[29]", R.mipmap.face19);
        EMOTION_CLASSIC_MAP.put("[30]", R.mipmap.face20);
        EMOTION_CLASSIC_MAP.put("[31]", R.mipmap.face21);
        EMOTION_CLASSIC_MAP.put("[32]", R.mipmap.face22);
        EMOTION_CLASSIC_MAP.put("[33]", R.mipmap.face23);
        EMOTION_CLASSIC_MAP.put("[34]", R.mipmap.face24);
        EMOTION_CLASSIC_MAP.put("[35]", R.mipmap.face25);
        EMOTION_CLASSIC_MAP.put("[36]", R.mipmap.face26);
        EMOTION_CLASSIC_MAP.put("[37]", R.mipmap.face27);
        EMOTION_CLASSIC_MAP.put("[38]", R.mipmap.face1);
        EMOTION_CLASSIC_MAP.put("[39]", R.mipmap.face2);
        EMOTION_CLASSIC_MAP.put("[40]", R.mipmap.face3);
        EMOTION_CLASSIC_MAP.put("[41]", R.mipmap.face4);
        EMOTION_CLASSIC_MAP.put("[42]", R.mipmap.face5);
        EMOTION_CLASSIC_MAP.put("[43]", R.mipmap.face6);
        EMOTION_CLASSIC_MAP.put("[44]", R.mipmap.face7);
        EMOTION_CLASSIC_MAP.put("[45]", R.mipmap.face8);
        EMOTION_CLASSIC_MAP.put("[46]", R.mipmap.face9);
        EMOTION_CLASSIC_MAP.put("[47]", R.mipmap.face10);
        EMOTION_CLASSIC_MAP.put("[48]", R.mipmap.face11);
        EMOTION_CLASSIC_MAP.put("[49]", R.mipmap.face12);
        EMOTION_CLASSIC_MAP.put("[50]", R.mipmap.face13);
        EMOTION_CLASSIC_MAP.put("[51]", R.mipmap.face14);
        EMOTION_CLASSIC_MAP.put("[52]", R.mipmap.face15);
        EMOTION_CLASSIC_MAP.put("[53]", R.mipmap.face16);
        EMOTION_CLASSIC_MAP.put("[54]", R.mipmap.face17);
        EMOTION_CLASSIC_MAP.put("[55]", R.mipmap.face18);
        EMOTION_CLASSIC_MAP.put("[56]", R.mipmap.face19);
        EMOTION_CLASSIC_MAP.put("[57]", R.mipmap.face20);
        EMOTION_CLASSIC_MAP.put("[58]", R.mipmap.face21);
        EMOTION_CLASSIC_MAP.put("[59]", R.mipmap.face22);
        EMOTION_CLASSIC_MAP.put("[60]", R.mipmap.face23);
        EMOTION_CLASSIC_MAP.put("[61]", R.mipmap.face24);
        EMOTION_CLASSIC_MAP.put("[62]", R.mipmap.face25);
        EMOTION_CLASSIC_MAP.put("[63]", R.mipmap.face26);
        EMOTION_CLASSIC_MAP.put("[64]", R.mipmap.face27);
        EMOTION_CLASSIC_MAP.put("[65]", R.mipmap.face1);
        EMOTION_CLASSIC_MAP.put("[66]", R.mipmap.face2);
        EMOTION_CLASSIC_MAP.put("[67]", R.mipmap.face3);
        EMOTION_CLASSIC_MAP.put("[68]", R.mipmap.face4);
        EMOTION_CLASSIC_MAP.put("[69]", R.mipmap.face5);
        EMOTION_CLASSIC_MAP.put("[70]", R.mipmap.face6);
        EMOTION_CLASSIC_MAP.put("[71]", R.mipmap.face7);
        EMOTION_CLASSIC_MAP.put("[72]", R.mipmap.face8);
        EMOTION_CLASSIC_MAP.put("[73]", R.mipmap.face9);
        EMOTION_CLASSIC_MAP.put("[74]", R.mipmap.face10);
        EMOTION_CLASSIC_MAP.put("[75]", R.mipmap.face11);
        EMOTION_CLASSIC_MAP.put("[76]", R.mipmap.face12);
        EMOTION_CLASSIC_MAP.put("[77]", R.mipmap.face13);
        EMOTION_CLASSIC_MAP.put("[78]", R.mipmap.face14);
        EMOTION_CLASSIC_MAP.put("[79]", R.mipmap.face15);
        EMOTION_CLASSIC_MAP.put("[80]", R.mipmap.face16);
        EMOTION_CLASSIC_MAP.put("[81]", R.mipmap.face17);
        EMOTION_CLASSIC_MAP.put("[82]", R.mipmap.face18);
        EMOTION_CLASSIC_MAP.put("[83]", R.mipmap.face19);
        EMOTION_CLASSIC_MAP.put("[84]", R.mipmap.face20);
        EMOTION_CLASSIC_MAP.put("[85]", R.mipmap.face21);
        EMOTION_CLASSIC_MAP.put("[86]", R.mipmap.face22);
        EMOTION_CLASSIC_MAP.put("[87]", R.mipmap.face23);
        EMOTION_CLASSIC_MAP.put("[88]", R.mipmap.face24);
        EMOTION_CLASSIC_MAP.put("[89]", R.mipmap.face25);
        EMOTION_CLASSIC_MAP.put("[90]", R.mipmap.face26);
        EMOTION_CLASSIC_MAP.put("[91]", R.mipmap.face27);
        EMOTION_CLASSIC_MAP.put("[92]", R.mipmap.face1);
        EMOTION_CLASSIC_MAP.put("[93]", R.mipmap.face2);
    }

    /**
     * 根据名称获取当前表情图标R值
     *
     * @param EmotionType 表情类型标志符
     * @param imgName     名称
     * @return 返回对应表情资源id
     */
    public static int getImgByName(int EmotionType, String imgName) {
        Integer integer = null;
        switch (EmotionType) {
            case EMOTION_CLASSIC_TYPE:
                integer = EMOTION_CLASSIC_MAP.get(imgName);
                break;
            default:
                Log.e("====", "the emojiMap is null!!");
                break;
        }
        return integer == null ? -1 : integer;
    }

    /**
     * 根据类型获取表情数据
     *
     * @param EmotionType 表情类型
     * @return 表情map
     */
    public static ArrayMap<String, Integer> getEmojiMap(int EmotionType) {

        return EmotionType == EMOTION_CLASSIC_TYPE ? EMOTION_CLASSIC_MAP : EMPTY_MAP;
    }
}
