package cn.sh.lmg.hystrix.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by liaomengge on 16/12/13.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class HttpRequestData {

    protected HttpMethod httpMethod;
    protected String url;
    protected String postData;
    protected int timeoutMilliSeconds = 2_000;

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static final class Get extends HttpRequestData {

        public Get(String url, int timeoutMilliSeconds) {
            super(HttpMethod.GET, url, null, timeoutMilliSeconds);
        }
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static final class POST extends HttpRequestData {

        public POST(String url, String postData, int timeoutMilliSeconds) {
            super(HttpMethod.POST, url, postData, timeoutMilliSeconds);
        }
    }
}