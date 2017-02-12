package cn.sh.lmg.hystrix.data;

/**
 * Created by liaomengge on 16/12/13.
 */
public enum HttpMethod {

    GET((byte) 0, "GET"), POST((byte) 1, "POST");

    private byte code;
    private String description;

    HttpMethod(byte code, String description) {
        this.code = code;
        this.description = description;
    }

    public byte getCode() {
        return code;
    }


    public String getDescription() {
        return description;
    }
}
