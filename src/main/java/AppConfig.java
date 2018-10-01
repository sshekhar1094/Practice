import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.pluralsight.repository.CustomerRepository;
import com.pluralsight.repository.HibernateCustomerRepositoryImpl;
import com.pluralsight.service.CustomerService;
import com.pluralsight.service.CustomerServiceImpl;

@Configuration
@ComponentScan({"com.pluralsight"})
@PropertySource("app.properties")
public class AppConfig {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer getPropertySourcesPlaceholderConfigurer() {
		// this loads the values from app.properties
		return new PropertySourcesPlaceholderConfigurer();
	}

//	@Bean(name="customerRepository")
//	public CustomerRepository getCustomerRepository() {
//		return new HibernateCustomerRepositoryImpl();
//	}
	//Now we can decalre HibernateCustomerRepositoryImpl under @Repository, now spring will autowire to this
	
	
//	@Bean(name="customerService")
//	public CustomerService getCustomerService() {
//		CustomerServiceImpl customerService = new CustomerServiceImpl();
//		
//		//customerService.setCustomerRepository(getCustomerRepository());
//		// Since we are using autowiring we don need to explicitly call teh constructor
//		
//		return customerService;
//	}
	// Declare CustomerServiceImpl under @Service and we can comment this as well
	
}
