package cn.com.edtechhub.workgeneralmodule.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理工具类
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@Slf4j
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     */
    public static void throwIf(boolean condition, CodeBindMessageEnums codeBindMessageEnums, String message) {
        if (condition) {
            throw new BusinessException(codeBindMessageEnums, message);
        }
    }

    /**
     * 打印异常堆栈定位信息
     */
    public static void printStackTraceStatus(Throwable e, int tier) {
        StackTraceElement[] stack = e.getStackTrace();
        if (stack.length > tier) {
            StackTraceElement element = stack[tier];
            log.warn("异常位置: {} -> 文件: {}, 方法: {}, 码行: {}",
                    element.getFileName(),
                    element.getClassName(),
                    element.getMethodName(),
                    element.getLineNumber()
            );
        }
    }

}
