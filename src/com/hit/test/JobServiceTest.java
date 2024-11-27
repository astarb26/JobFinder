package com.hit.test;

import com.hit.dao.JobDaoImpl;
import com.hit.dm.Job;
import com.hit.service.JobService;
import main.KMPSearch;
import org.junit.Test;

public class JobServiceTest {

    @Test
    public void testSearch() {
        JobService jobService = new JobService(new JobDaoImpl("job.txt"));
        KMPSearch kmpSearch = new KMPSearch();
        Job job1 = new Job(1L, "Software Engineer", "Exciting opportunity", "Ramla");
        Job job2 = new Job(2L, "Data Scientist", "Data analysis role", "Shoam");
        System.out.println(kmpSearch.containsPattern(job1.getTitle(), "Engineer"));
        jobService.saveJob(job1);
        jobService.saveJob(job2);
    }
}