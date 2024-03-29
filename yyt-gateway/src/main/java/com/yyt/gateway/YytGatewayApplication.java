package com.yyt.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 网关启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class YytGatewayApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(YytGatewayApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  网关启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
            " .----------------.  .----------------.  .----------------. \n" +
            "| .--------------. || .--------------. || .--------------. |\n" +
            "| |  ____  ____  | || |  ____  ____  | || |  _________   | |\n" +
            "| | |_  _||_  _| | || | |_  _||_  _| | || | |  _   _  |  | |\n" +
            "| |   \\ \\  / /   | || |   \\ \\  / /   | || | |_/ | | \\_|  | |\n" +
            "| |    \\ \\/ /    | || |    \\ \\/ /    | || |     | |      | |\n" +
            "| |    _|  |_    | || |    _|  |_    | || |    _| |_     | |\n" +
            "| |   |______|   | || |   |______|   | || |   |_____|    | |\n" +
            "| |              | || |              | || |              | |\n" +
            "| '--------------' || '--------------' || '--------------' |\n" +
            " '----------------'  '----------------'  '----------------' \n");
    }
}
