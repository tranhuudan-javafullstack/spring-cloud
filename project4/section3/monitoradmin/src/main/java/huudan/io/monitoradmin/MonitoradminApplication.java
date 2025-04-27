package huudan.io.monitoradmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
public class MonitoradminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitoradminApplication.class, args);
    }
}
