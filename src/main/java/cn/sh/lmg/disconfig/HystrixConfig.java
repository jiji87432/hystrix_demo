package cn.sh.lmg.disconfig;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by liaomengge on 16/12/13.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HystrixConfig extends AbstractDisconfConfig {

    private int circuitBreakerRequestVolumeThreshold = 20;//10秒钟内至少19此请求失败，熔断器才发挥起作用
    private int circuitBreakerSleepWindowInMilliseconds = 5;//熔断器中断请求5秒后会进入半打开状态,放部分流量过去重试
    private int circuitBreakerErrorThresholdPercentage = 50;//错误率达到50开启熔断保护
}
