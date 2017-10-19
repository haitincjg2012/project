/*package com.alqsoft.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class BatchTest {
		@Autowired
	private JobOperator jobOperator;

	@Resource
	private Job testjob;

	@Autowired
	private TestReader testReader;

	@Test
	public void testll() throws Exception {
		while (true) {
		if (testReader.getUserLogs().isEmpty()) {
			testReader.init();
		}
		Long id = jobOperator.startNextInstance(testjob.getName());
		System.out.println("我的运行id=" + id);
		while (!testReader.getUserLogs().isEmpty()) {
			Thread.sleep(1000);
		}
		Thread.sleep(1000);
		}
	}
}
*/