package cn.yufenghui.lession.user.common;

/**
 * @author Yu Fenghui
 * @date 2021/4/21 11:19
 * @since
 */
public class Constants {

    public static final String REDIRECT_URI = "http://localhost:8080/user/login";

    public static final String GITEE_CLIENT_ID = "4bf2633e55072cc95325e767ec4087d920780ac76a6d7a45a88469f67767a597";

    public static final String GITEE_CLIENT_SECRET = "604d9dee7eb686643d3fd27596a6b65812b71940483f370f941871313eca5410";

    public static final String GITEE_AUTHORIZATION_URL =
            "https://gitee.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code";

    public static final String GITEE_ACCESS_TOKEN_URL =
            "https://gitee.com/oauth/token?grant_type=authorization_code&code=%s&client_id=%s&redirect_uri=%s&client_secret=%s";

    public static final String GITEE_USER_INFO_URL = "https://gitee.com/api/v5/user";


}
