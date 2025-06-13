package cn.com.edtechhub.workgeneralmodule.exception;

import cn.com.edtechhub.workgeneralmodule.response.BaseResponse;
import cn.com.edtechhub.workgeneralmodule.response.TheResult;
import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.filter.ExceptionFilter;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.Arrays;

/**
 * 全局异常 RPC 文件过滤器
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@Activate(group = "provider")
@Slf4j
public class GlobalFilterManage extends ExceptionFilter implements Filter {

    @Override
    public Result invoke(
            Invoker<?> invoker, // 执行器
            Invocation invocation // 上下文
    ) throws RpcException {
        // 调试信息
        log.debug("[GlobalFilterManage] >>> 触发 RPC 全局异常管理器 GlobalFilterManage >>>");
        log.debug(
                "[GlobalFilterManage] 执行器信息: \n接口: {}\n路径: {}"
                , invoker.getInterface().getName()
                , invoker.getUrl()
        );
        log.debug(
                "[GlobalFilterManage] 上下文信息 \n方法: {}\n参数: {}\n 参值: {}\n附件: {}\n目标: {}"
                , invocation.getMethodName()
                , Arrays.toString(invocation.getParameterTypes())
                , Arrays.toString(invocation.getArguments())
                , invocation.getAttachments()
                , invocation.getTargetServiceUniqueName()
        );

        // 获取结果
        Result result = invoker.invoke(invocation); // 获取 rpcResult
        log.debug("[GlobalFilterManage] 本次调用获取到的 PRC 结果为 {}", result);

        // 检测异常
        if (!result.hasException() || GenericService.class == invoker.getInterface()) {
            log.debug("[GlobalFilterManage] 没有出现异常");
            return result;
        }
        Throwable exception = result.getException(); // 获取抛出的异常
        log.warn("[GlobalFilterManage] 检测出现异常 {}", exception.getClass().getName());

        // 处理异常
        RpcInvocation rpcInvocation = (RpcInvocation) invocation; // 确保类型正确且非空
        Result newResult = AsyncRpcResult.newDefaultAsyncResult(this.handleException(exception), rpcInvocation);
        log.debug("[GlobalFilterManage] <<< 触发 RPC 全局异常管理器 GlobalFilterManage <<<");
        return newResult;
    }

    /**
     * 分类处理异常
     */
    private BaseResponse<?> handleException(Throwable exception) {
        BaseResponse<Object> response;
        if (exception instanceof BusinessException be) {
            log.warn("[GlobalFilterManage] 触发业务内部异常处理方法, {}", be.getMessage());
            ThrowUtils.printStackTraceStatus(be, 1);
            response = TheResult.error(be.getCodeBindMessageEnums(), be.getMessage());
        } else if (exception instanceof NotLoginException) {
            log.warn("[GlobalFilterManage] 触发登录认证异常处理方法, {}", exception.getMessage());
            response = TheResult.error(CodeBindMessageEnums.NO_LOGIN_ERROR, "登录请先进行登录");
        } else if (exception instanceof NotPermissionException) {
            log.warn("[GlobalFilterManage] 触发权限认证异常处理方法(权限码认证), {}", exception.getMessage());
            response = TheResult.error(CodeBindMessageEnums.NO_AUTH_ERROR, "用户当前权限不允许使用该功能");
        } else if (exception instanceof NotRoleException) {
            log.warn("[GlobalFilterManage] 触发角色认证异常处理方法, {}", exception.getMessage());
            response = TheResult.error(CodeBindMessageEnums.NO_ROLE_ERROR, "用户当前角色不允许使用该功能");
        } else if (exception instanceof DisableServiceException) {
            log.warn("[GlobalFilterManage] 触发封禁异常处理方法, {}", exception.getMessage());
            response = TheResult.error(CodeBindMessageEnums.USER_DISABLE_ERROR, "当前用户因为违规被封禁");
        } else {
            log.error("[GlobalFilterManage] 触发全局异常处理方法, {}", exception.getMessage());
            ThrowUtils.printStackTraceStatus(exception, 0);
            response = TheResult.error(CodeBindMessageEnums.SYSTEM_ERROR, "请联系管理员 89838804@qq.com");
        }
        return response;
    }


}
