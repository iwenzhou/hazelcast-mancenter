package com.wen.hazelcast.mancenter;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @author: wen <br>
 * @date: 2019/09/07 02:41 <br>
 */
@Configuration
public class TomcatConfigurer {

    @Bean
    public ServletWebServerFactory servletContainerFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                // 目标文件名
                String targetName = "hazelcast-mancenter-3.12.4.war";
                targetName = "hello.war";
                // 取目标文件绝对地址
                String targetFile = System.getProperty("user.dir") + File.separator + "vendor" + File.separator + targetName;

                System.out.println("目标文件地址：" + targetFile);

                // 创建SpringBoot内置Tomcat文件夹
                File catalinaBase = new File(tomcat.getServer().getCatalinaBase(), "webapps");
                catalinaBase.mkdirs();
                catalinaBase.toPath().resolve("vendor").toFile().mkdirs();

                System.out.println("内置Tomcat运行地址：" + tomcat.getServer().getCatalinaBase());

                try {
                    Files.copy(new File(targetFile).toPath(), catalinaBase.toPath().resolve("vendor").resolve(targetName), StandardCopyOption.REPLACE_EXISTING);
                    Context context = tomcat.addWebapp("/hazelcast-mancenter", "vendor" + File.separator + targetName);
                    context.setParentClassLoader(getClass().getClassLoader());

                    System.out.println("已将目标文件拷贝至SpringBoot内置Tomcat运行目录.");
                } catch (Exception ex) {
                    System.out.println("拷贝目标文件异常：" + ex.getMessage());
                    throw new IllegalStateException("Failed to add webapp", ex);
                }

                return super.getTomcatWebServer(tomcat);
            }
        };
    }

}
