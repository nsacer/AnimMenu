package utils;

/**
 * created by zpf on 2018/11/27
 */
public class SPKey {

    private static final SPKey ourInstance = new SPKey();

    public static SPKey getInstance() {
        return ourInstance;
    }

    private SPKey() {
    }

    public final String MAIN_BG_URL = "mainBgUrl";
}
