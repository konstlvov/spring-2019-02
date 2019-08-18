package booklib;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing(modular = true)
public class BatchConfiguration {

	@Bean
	public ApplicationContextFactory addNewLoadBooksFromMongoDBToOracleJob(){
	    return new GenericApplicationContextFactory(LoadBooksFromMongoDBToOracleJobConfiguration.class);
	}

	@Bean
	public ApplicationContextFactory addNewLoadBooksFromOracleToMongoDBJob(){
		return new GenericApplicationContextFactory(LoadBooksFromOracleToMongoDBJobConfiguration.class);
	}
}
