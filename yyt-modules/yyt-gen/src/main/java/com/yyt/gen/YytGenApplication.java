package com.yyt.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.yyt.common.security.annotation.EnableCustomConfig;
import com.yyt.common.security.annotation.EnableRyFeignClients;
import com.yyt.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 代码生成
 * 
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class YytGenApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(YytGenApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  代码生成模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
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
