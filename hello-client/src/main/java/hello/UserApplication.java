package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@RestController
@RibbonClient(name = "hello-service", configuration = SayHelloConfiguration.class)
public class UserApplication {

  @LoadBalanced
  @Bean
  RestTemplate restTemplate(){
    return new RestTemplate();
  }

  @Autowired
  RestTemplate restTemplate;

  @RequestMapping("/hello")
  public String hello(@RequestParam(value="name", defaultValue="Thiru") String name) {
    String greeting = this.restTemplate.getForObject("http://hello-service/greeting", String.class);
    return String.format("%s, %s!", greeting, name);
  }

  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }
}

