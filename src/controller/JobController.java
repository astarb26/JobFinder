package controller;

import com.hit.dm.Job;
import com.hit.service.JobService;

import java.util.List;

public class JobController {
    private final JobService jobService;
    public JobController(JobService jobService){
        this.jobService = jobService;
    }
    public boolean saveJob(Job job){return jobService.saveJob(job);}
    public boolean deleteJob(Job job){return jobService.deleteJob(job);}
    public List<Job> getJobById(Long id){
        return jobService.findJobById(id);
    }
    public List<Job> getJobByDesc(String description){
        return jobService.findJobByDesc(description);
    }
    public List<Job> getJobByTitle(String title){
        return jobService.findJobByTitle(title);
    }

    public List<Job> getJobByCity(String city){
        return jobService.findJobByCity(city);
    }
    public List<Job> getAllJobs(){
        return jobService.getAllJobs();
    }
}
