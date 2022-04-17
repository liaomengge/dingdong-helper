package util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Data;
import util.ConfigUtil.NoticePush;

import java.util.Map;
import java.util.Objects;

/**
 * Created by liaomengge on 2022/04/14.
 */
public class NoticeUtil {

    private static final String WX_URL = String.format("http://dd.100vs.com/api/%s/send", NoticePush.WX_PUSH_TOKEN);

    private static final String IOS_URL = String.format("https://api.day.app/%s", NoticePush.IOS_PUSH_TOKEN);

    private static final Integer TIMEOUT = 5000;

    public static boolean send(NoticeInfo noticeInfo) {
        boolean wxFlag = wxSend(noticeInfo);
        boolean iosFlag = iosSend(noticeInfo);
        return wxFlag || iosFlag;
    }

    private static boolean wxSend(NoticeInfo noticeInfo) {
        try {
            String result = HttpUtil.get(WX_URL, noticeInfo.toMap(), TIMEOUT);
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

    private static boolean iosSend(NoticeInfo noticeInfo) {
        try {
            String url = IOS_URL + '/' + noticeInfo.getTitle() + '/' + noticeInfo.getContent();
            String result = HttpUtil.get(url, TIMEOUT);
            if (StrUtil.isNotBlank(result)) {
                JSONObject jsonObject = JSONUtil.parseObj(result);
                if (Objects.nonNull(jsonObject)) {
                    return StrUtil.equals("200", jsonObject.getStr("code"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Data
    @Builder
    public static class NoticeInfo {
        private String title;
        private String content;

        public Map<String, Object> toMap() {
            return ImmutableMap.of("title", this.getTitle(), "content", this.getContent());
        }
    }
}
