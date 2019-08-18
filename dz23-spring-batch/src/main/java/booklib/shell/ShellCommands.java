package booklib.shell;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Date;

@ShellComponent
public class ShellCommands {

	private final ConfigurableApplicationContext ctx;

	public ShellCommands(ConfigurableApplicationContext ctx) {
		this.ctx = ctx;
	}

	@ShellMethod("Load data from MongoDB to Oracle")
	public void m2o() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		System.out.println("about to load data from MongoDB to Oracle " + ctx.toString());
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		JobParameters jobParameters = new JobParametersBuilder()
						.addString("date", (new Date()).toString()) // if we don't pass date as parameter, Spring will consider this job as already finished and refuses to start it next time
						.toJobParameters();
		// use name of bean, not the argument to jobBuilderFactory.get
		Job loadBooksFromMongoDBToOracleJob = ctx.getBean("loadBooksFromMongoDBToOracleJob", Job.class);
		JobExecution jobExecution = jobLauncher.run(loadBooksFromMongoDBToOracleJob, jobParameters);

	}

	@ShellMethod("Load data from Oracle to MongoDB")
	public void o2m()  throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		System.out.println("about to load data from Oracle to MongoDB " + ctx.toString());
		JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		JobParameters jobParameters = new JobParametersBuilder()
						.addString("date", (new Date()).toString()) // if we don't pass date as parameter, Spring will consider this job as already finished and refuses to start it next time
						.toJobParameters();
		// use name of bean, not the argument to jobBuilderFactory.get
		Job loadBooksFromMongoDBToOracleJob = ctx.getBean("loadBooksFromOracleToMongoDBJob", Job.class);
		JobExecution jobExecution = jobLauncher.run(loadBooksFromMongoDBToOracleJob, jobParameters);
	}

}
