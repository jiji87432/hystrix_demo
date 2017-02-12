package cn.sh.lmg.hystrix;

import cn.sh.lmg.disconfig.HystrixConfig;
import cn.sh.lmg.hystrix.data.HttpRequestData;
import com.netflix.hystrix.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by liaomengge on 16/12/14.
 */
@Component("hystrixUtil")
public class HystrixUtil {

    @Autowired
    private HystrixConfig hystrixConfig;

    public String execute(HystrixKey hystrixKey, HttpRequestData httpRequestData) {
        HystrixCommand.Setter setter = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(hystrixKey.getCommandGroupKey()))
                .andCommandKey(HystrixCommandKey.Factory.asKey(hystrixKey.getCommandKey()))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withCircuitBreakerRequestVolumeThreshold(hystrixConfig.getCircuitBreakerRequestVolumeThreshold())
                        .withCircuitBreakerSleepWindowInMilliseconds(hystrixConfig.getCircuitBreakerSleepWindowInMilliseconds())
                        .withCircuitBreakerErrorThresholdPercentage(hystrixConfig.getCircuitBreakerErrorThresholdPercentage())
                        .withExecutionTimeoutEnabled(false))//使用HttpClient 超时时间
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(hystrixKey.getCoreSize()));
        return new HttpHystrixCommand(setter, httpRequestData).execute();
    }
}
