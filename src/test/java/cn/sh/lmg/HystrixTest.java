package cn.sh.lmg;

import cn.sh.lmg.hystrix.HystrixKey;
import cn.sh.lmg.hystrix.HystrixUtil;
import cn.sh.lmg.hystrix.data.HttpRequestData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by liaomengge on 16/12/28.
 */
public class HystrixTest {

    private static final String URL = "https://www.baidu.com/";
    private static final int TIME_OUT = 2000;

    private ClassPathXmlApplicationContext context = null;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("spring-bean.xml");
    }

    @Test
    public void test() {
        HystrixUtil hystrixUtil = context.getBean("hystrixUtil", HystrixUtil.class);

        HystrixKey hystrixKey = new HystrixKey();
        hystrixKey.setCommandGroupKey("ping");
        hystrixKey.setCommandKey("ping");
        hystrixKey.setCoreSize(20);

        long startTime = System.currentTimeMillis();

        String result = hystrixUtil.execute(hystrixKey, this.buildGet());

        long endTime = System.currentTimeMillis();

        System.out.println("result: " + result);
        System.out.println("spend time: " + (endTime - startTime));
    }

    private HttpRequestData buildGet() {
        String url = URL;
        return new HttpRequestData.Get(url, TIME_OUT);
    }
}
