package com.yyt.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.yyt.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 文件服务
 *
 * @author ruoyi
 */
@EnableCustomSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class YytFileApplication {
  public static void main(String[] args) {
    SpringApplication.run(YytFileApplication.class, args);
    System.out.println("(♥◠‿◠)ﾉﾞ  文件服务模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
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
