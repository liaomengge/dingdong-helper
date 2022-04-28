import util.ConfigUtil;
import util.ConfigUtil.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息
 * 通过小程序抓包购物车接口获取headers和body中的数据填入
 */
public class UserConfig {

    //城市
    public static final String cityId = "0101";//默认上海

    //站点id
    public static final String stationId = UserInfo.STATION_ID;

    //收货地址id
    public static final String addressId = UserInfo.ADDRESS_ID;

    /**
     * 确认收货地址id和站点id
     * 每天抢之前先允许一下此接口 确认登录信息是否有效 如果失效了重新抓一次包
     */
    public static void main(String[] args) {
        Api.checkUserConfig();
    }

    /**
     * 抓包后参考项目中的image/headers.jpeg 把信息一行一行copy到下面 没有的key不需要复制
     */
    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("ddmc-city-number", cityId);
        headers.put("ddmc-time", String.valueOf(System.currentTimeMillis() / 1000));
        headers.put("ddmc-build-version", "2.74.2");
        headers.put("ddmc-station-id", stationId);
        headers.put("ddmc-channel", "applet");
        headers.put("ddmc-os-version", "[object Undefined]");
        headers.put("ddmc-app-client-id", "4");
        headers.put("ddmc-ip", "");
        headers.put("ddmc-api-version", "9.44.0");
        headers.put("ddmc-sdkversion", "2.13.2");
        headers.put("accept-encoding", "gzip, deflate, br");
        headers.put("referer", "https://servicewechat.com/wx1e113254eda17715/437/page-frame.html");

        // ------------  填入以下6项 上面不要动 ------------
        headers.put("ddmc-device-id", UserInfo.DDMC_DEVICE_ID + Api.random);
        headers.put("cookie", UserInfo.COOKIE);
        headers.put("ddmc-longitude", UserInfo.DDMC_LONGITUDE);
        headers.put("ddmc-latitude", UserInfo.DDMC_LATITUDE);
        headers.put("ddmc-uid", UserInfo.DDMC_UID);
        headers.put("user-agent", UserInfo.USER_AGENT);
        return headers;
    }

    /**
     * 抓包后参考项目中的image/body.jpeg 把信息一行一行copy到下面 没有的key不需要复制
     * <p>
     * 这里不能加泛型 有些接口是params  泛型必须要求<String,String> 有些是form表单 泛型要求<String,Object> 无法统一
     */
    public static Map getBody(Map<String, String> headers) {
        Map body = new HashMap<>();
        body.put("uid", headers.get("ddmc-uid"));
        body.put("longitude", headers.get("ddmc-longitude"));
        body.put("latitude ", headers.get("ddmc-latitude"));
        body.put("station_id", headers.get("ddmc-station-id"));
        body.put("city_number", headers.get("ddmc-city-number"));
        body.put("api_version", "9.50.2");
        body.put("applet_source", "");
        body.put("channel", "applet");
        body.put("app_client_id", "4");
        body.put("app_version ", "2.85.4");
        body.put("sharer_uid", "");
        body.put("h5_source", "");
        body.put("source_type", "5");
        body.put("time", headers.get("ddmc-time"));
        body.put("openid", headers.get("ddmc-device-id"));

        // ------------  填入这2项上面不要动 ------------
        body.put("s_id", UserInfo.S_ID);
        body.put("device_token", UserInfo.DEVICE_TOKEN);
        return body;
    }
}
