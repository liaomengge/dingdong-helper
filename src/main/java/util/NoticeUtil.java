package util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.ImmutableMap;

import java.util.Date;
import java.util.Objects;

/**
 * Created by liaomengge on 2022/04/14.
 */
public class NoticeUtil {

    private static final String token = "3615214566174d95b1265bab76061fa9";

    private static final String url = String.format("http://dd.100vs.com/api/%s/send", token);

    public static boolean send() {
        try {
            String result = HttpUtil.get(url, ImmutableMap.of("title", "下单成功", "content", "下单成功，快去支付吧。。。"), 3000);
            if (StrUtil.isNotBlank(result)) {
                JSONObject jsonObject = JSONUtil.parseObj(result);
                if (Objects.nonNull(jsonObject)) {
                    return StrUtil.equals("200", jsonObject.getStr("status"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
