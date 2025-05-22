package cn.com.edtechhub.workgeneralmodule.utils;

import cn.com.edtechhub.workgeneralmodule.exception.BusinessException;
import cn.com.edtechhub.workgeneralmodule.exception.CodeBindMessageEnums;

/**
 * 异常处理工具类
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     */
    public static void throwIf(boolean condition, CodeBindMessageEnums codeBindMessageEnums, String message) {
        if (condition) {
            throw new BusinessException(codeBindMessageEnums, message);
        }
    }

}
