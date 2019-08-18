package booklib;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/////////////////////   Oracle -> MongoDB   /////////////////////
@Configuration
public class LoadBooksFromOracleToMongoDBJobConfiguration {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public JdbcTemplate jdbc;

	@Autowired
	MongoTemplate mongoTemplate;


    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book b = new Book();
            b.setId(rs.getString("ID"));
            b.setIsbn(rs.getString("ISBN"));
            b.setTitle(rs.getString("TITLE"));
            b.setAuthor(rs.getString("AUTHOR"));
            b.setDescription(rs.getString("DESCRIPTION"));
            b.setPublisher(rs.getString("PUBLISHER"));
            b.setpublished_year(String.valueOf(rs.getLong("PUBLISHED_YEAR")));
            return b;
        }
    }

    @Bean
    JdbcCursorItemReader<Book> oracleBookReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Book>()
                .name("oracleBookReader")
                .dataSource(dataSource)
                .sql("select * from BOOK")
                .rowMapper(new BookMapper())
                .build()
                ;
    }

    @Bean
    public MongoItemWriter<Book> mongoBookWriter()    {
        return new MongoItemWriterBuilder<Book>()
                .template(mongoTemplate)
                .collection("books2")
                .build();
    }

    @Bean
    public Step dropMongoCollectionStep() {
	    return stepBuilderFactory.get("dropMongoCollectionStep")
					    .tasklet(new Tasklet() {
						    @Override
						    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
							    System.out.println("about to drop collection books2");
							    mongoTemplate.getCollection("books2").drop();
							    return RepeatStatus.FINISHED;
						    }
					    })
					    .build();
    }

    @Bean
    public Step loadBooksFromOracleToMongoDBStep(DataSource dataSource){
        return stepBuilderFactory.get("loadBooksFromOracleToMongoDBStep")
                .<Book, Book> chunk(10)
                .reader(oracleBookReader(dataSource))
                .writer(mongoBookWriter())
                .build();
    }

    @Bean
    public Job loadBooksFromOracleToMongoDBJob(JobCompletionNotificationListener listener, Step dropMongoCollectionStep, Step loadBooksFromOracleToMongoDBStep) {
        return jobBuilderFactory.get("loadBooksFromOracleToMongoDBJob")
            .incrementer(new RunIdIncrementer())
				    .listener(listener)
            .flow(dropMongoCollectionStep)
				    .next(loadBooksFromOracleToMongoDBStep)
            .end()
            .build();
    }

}
