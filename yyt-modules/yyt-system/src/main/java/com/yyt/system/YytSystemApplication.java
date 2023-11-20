package com.yyt.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.yyt.common.security.annotation.EnableCustomConfig;
import com.yyt.common.security.annotation.EnableRyFeignClients;
import com.yyt.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 系统模块
 *
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class YytSystemApplication {
  public static void main(String[] args) {
    SpringApplication.run(YytSystemApplication.class, args);
    System.out.println("(♥◠‿◠)ﾉﾞ  系统模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
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
