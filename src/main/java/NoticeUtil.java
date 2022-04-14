import cn.hutool.http.HttpUtil;
import com.google.common.collect.ImmutableMap;

/**
 * Created by liaomengge on 2022/04/14.
 */
public class NoticeUtil {

    private static final String token = "3615214566174d95b1265bab76061fa9";

    private static final String url = String.format("http://dd.100vs.com/api/%s/send", token);

    public static void send() {
        HttpUtil.get(url, ImmutableMap.of("title", "下单成功", "content", "下单成功，快去支付吧。。。"), 3000);
    }
}
