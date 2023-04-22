package com.pidan.model.enums;

/**错误码
 * @author 黄大头
 * @date 2023年04月22日 19:31
 */

public enum ErrorCode {
    SUCCESS(200, "ok", ""),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    PASSWORD_ERROR(40003, "密码不正确", ""),
    STATUS_ERROR(40009, "状态异常", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    INSERT_ERROR(40032, "添加失败", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NOT_DATA(40020,"暂无数据",""),
    NO_AUTH(40101, "无权限", ""),
    SYSTEM_ERROR(50000, "系统内部异常", "");
    private final int code;
    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}