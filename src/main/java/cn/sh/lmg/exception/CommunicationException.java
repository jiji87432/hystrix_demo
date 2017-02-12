package cn.sh.lmg.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by liaomengge on 16/8/2.
 */
@Setter
@Getter
public class CommunicationException extends RuntimeException {

    private String errCode;

    private String errMsg;

    public CommunicationException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public CommunicationException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
    }

}
