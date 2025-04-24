package com.jd.platform.hotkey.dashboard;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableAsync
@EnableScheduling
@SpringBootApplication
public class DashboardApplication{

    /*
    运行控制台之前需要先导入资源目录下的 MySQL 数据, 默认使用 root 账户来导入数据
    若重新建表无法获取到用户列表, 包括登陆页面无法访问, 部分页面报异常, 而异常报错好像是用户认证做的有些粗糙
    我猜测是浏览器缓存的问题, 如果你用的 Chorm 浏览器, 那么可以尝试在 [F12] 后的 "应用" 标签中点击 "存储" 后 "清楚网站数据" 就可以了
    */

    public static void main(String[] args) {
        try {
            SpringApplication.run(DashboardApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
