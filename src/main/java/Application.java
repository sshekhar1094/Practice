

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.cacheExample.own.cacheExampleMain;
import com.guava.example.GuavaMain;
import com.observable.example.ObservableMain;
import com.pluralsight.service.CustomerService;

public class Application {

	public static void main(String[] args) {
		
		int x=40, b=3;
		int c = (int) Math.ceil(x/b);
		System.out.println(c);
		
		String s = "oos,running low,p13n_cart, recipes,nutrition , blah";
		String[] a = s.split(",");
		for (String string : a) {
			System.out.println("****" + string + "***");
		}
		
//		Logger logger = LoggerFactory.getLogger(Application.class);
//		logger.info("*******info log");
		
		
		//Future example
		//new FutureExample().example();
		
		//Completable Future example
		//new CompletableFutureExample().example();
		
		//Own cache over a hashmap example
		//new cacheExampleMain().cacheExampleMainFunc();
		
		//Guava cache
		//new GuavaMain().guavaMainFunc();
		
		//Observable example
		new ObservableMain().observableMain();


		// CustomerService service = new CustomerServiceImpl();
		// This was before, now we replace it with appConfig

		ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
		CustomerService service = appContext.getBean("customerService", CustomerService.class);

		System.out.println(service.findAll().get(0).getFirstname());

	}
	
	
	

}
