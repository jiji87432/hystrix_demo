package cn.sh.lmg.hystrix;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by liaomengge on 16/12/13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HystrixKey {

    private String commandGroupKey = "";
    private String commandKey = "";
    private int coreSize = 20;//核心线程数,与对应的key绑定
}
