package cn.sh.lmg.hystrix;

import cn.sh.lmg.hystrix.data.HttpRequestData;
import cn.sh.lmg.util.ThrowableUtil;
import com.netflix.hystrix.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by liaomengge on 16/12/13.
 */
public class HttpHystrixCommand extends HystrixCommand<String> {

    private static Logger logger = LoggerFactory.getLogger(HttpHystrixCommand.class);

    private static final int RETRY_TIMES = 3;

    private HttpRequestData httpRequestData;

    public HttpHystrixCommand(Setter setter, HttpRequestData httpRequestData) {
        super(setter);
        this.httpRequestData = httpRequestData;
    }

    @Override
    protected String run() throws Exception {
        switch (httpRequestData.getHttpMethod()) {
            case GET:
                return HystrixHttpClient.get(httpRequestData.getUrl(), httpRequestData.getTimeoutMilliSeconds(), RETRY_TIMES);
            case POST:
                return HystrixHttpClient.post(httpRequestData.getUrl(), httpRequestData.getPostData(), httpRequestData.getTimeoutMilliSeconds(), RETRY_TIMES);
            default:
                throw new UnsupportedOperationException("不支持该协议[" + httpRequestData.getHttpMethod().getDescription() + "]的请求");
        }
    }

    @Override
    protected String getFallback() {
        String commandKey = super.getLogMessagePrefix();
        if (isFailedExecution()) {
            logger.error("执行方法[{}]失败,异常[{}]...", commandKey, ThrowableUtil.getStackTrace(super.getFailedExecutionException()));
        } else if (isResponseTimedOut()) {
            logger.error("执行方法[{}]响应超时...", commandKey);
        } else if (isResponseRejected()) {
            logger.error("执行方法[{}]拒绝...", commandKey);
        } else {
            logger.error("降级处理异常", super.getExecutionException());
        }

        return "MWEE_ROUTE_FAIL";
    }

}
