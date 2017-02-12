package cn.sh.lmg.hystrix;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by liaomengge on 17/1/3.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HystrixRetryHandler implements HttpRequestRetryHandler {

    private static Logger logger = LoggerFactory.getLogger(HystrixRetryHandler.class);

    private int reTryTimes = 3;//默认重试3次

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext httpContext) {
        logger.info("retry times: " + executionCount);

        if (executionCount > reTryTimes - 1) {
            // Do not retry if over max retry count
            return false;
        }
        if (exception instanceof SSLException) {
            // SSL handshake exception
            return false;
        }
        if (exception instanceof UnknownHostException) {
            return false;
        }
        return true;
    }
}
