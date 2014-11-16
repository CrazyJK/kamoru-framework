package kamoruStorageTester;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
//        SpringApplication.run(ServiceTester.class, args);
    	   ApplicationContext context = new ClassPathXmlApplicationContext("tester-conf.xml");
    	   
           ServiceTester tester = context.getBean(ServiceTester.class);        
           tester.getVideo();
    }
    
//    public static StorageService storageService() {
//    	RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
//    	rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1588/StorageService");
//    	rmiProxyFactoryBean.setServiceInterface(StorageService.class);
//    	
//    	return (StorageService)rmiProxyFactoryBean.getObject();
//    }
}
