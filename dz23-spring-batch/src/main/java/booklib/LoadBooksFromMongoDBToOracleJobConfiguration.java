package booklib;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;

/////////////////////   MongoDB -> Oracle   /////////////////////
@Configuration
public class LoadBooksFromMongoDBToOracleJobConfiguration {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public JdbcTemplate jdbc;

	@Autowired
	MongoTemplate mongoTemplate;

	@Bean
	MongoItemReader<Book> mongoBookReader() {
		HashMap<String, Sort.Direction> sortMap= new HashMap<>();
		sortMap.put("_id", Sort.Direction.ASC);
		return new MongoItemReaderBuilder<Book>()
						.name("mongoBookReader")
						.targetType(Book.class)
						.template(mongoTemplate)
						.collection("books")
						.jsonQuery("{}") // all books
						.sorts(sortMap)
						.build();
	}

	@Bean
	public JdbcBatchItemWriter<Book> oracleBookWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Book>()
						.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
						.sql("INSERT INTO BOOK (ID, ISBN, TITLE, AUTHOR, DESCRIPTION, PUBLISHER, PUBLISHED_YEAR) VALUES (:id, :isbn, :title, :author, :description, :publisher, :published_year)")
						.dataSource(dataSource)
						.build();
	}

	@Bean
	public Step deleteFromBookStep() {
		return stepBuilderFactory.get("deleteFromBookStep")
						.tasklet(new Tasklet() {
							@Override
							public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
								System.out.println("about to delete from BOOK");
								jdbc.execute("delete from BOOK");
								return RepeatStatus.FINISHED;
							}
						})
						.build();
	}


	@Bean
	public Step loadBooksFromMongoDBToOracleStep(DataSource dataSource){
		return stepBuilderFactory.get("loadBooksFromMongoDBToOracleStep")
						.<Book, Book> chunk(10)
						.reader(mongoBookReader())
						.writer(oracleBookWriter(dataSource))
						.build();

	}

    @Bean
    public Job loadBooksFromMongoDBToOracleJob(JobCompletionNotificationListener listener, Step deleteFromBookStep, Step loadBooksFromMongoDBToOracleStep) {
        return jobBuilderFactory.get("loadBooksFromMongoDBToOracleJob")
                .incrementer(new RunIdIncrementer())
				        .listener(listener)
                .flow(deleteFromBookStep).next(loadBooksFromMongoDBToOracleStep)
                .end()
                .build();
    }

}
