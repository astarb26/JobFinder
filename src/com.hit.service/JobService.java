package com.hit.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hit.dao.JobDaoImpl;
import com.hit.dm.Job;
import main.KMPSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobService {
    JobDaoImpl jobDao;
    Gson g = new GsonBuilder().create();

    public JobService(JobDaoImpl jobDao) {
        this.jobDao = jobDao;
    }


    public List<Job> findJobByDesc(String jobDesc) {
        List<Job> allJobs = jobDao.getAllJobs();
        List<Job> jobsWithDesc = new ArrayList<>();
        KMPSearch kmpSearch = new KMPSearch();
        for (Job j : allJobs) {
            if (kmpSearch.containsPattern(j.getDescription(), jobDesc))
                jobsWithDesc.add(j);
        }
        return jobsWithDesc;
    }

    public List<Job> findJobByTitle(String jobTitle) {
        List<Job> allJobs = jobDao.getAllJobs();
        List<Job> jobsWithTitle = new ArrayList<>();
        KMPSearch kmpSearch = new KMPSearch();
        for (Job j : allJobs) {
            if (kmpSearch.containsPattern(j.getTitle(), jobTitle))
                jobsWithTitle.add(j);
        }
        return jobsWithTitle;
    }

    public List<Job> findJobById(Long jobId) {
        List<Job> allJobs = jobDao.getAllJobs();
        List<Job> jobsWithId = new ArrayList<>();
        for (Job j : allJobs) {
            if (Objects.equals(j.getJobId(), jobId)) {
                jobsWithId.add(j);
            }
        }
        return jobsWithId;
    }

    public List<Job> findJobByCity(String jobCity) {
        List<Job> allJobs = jobDao.getAllJobs();
        List<Job> jobsWithCity = new ArrayList<>();
        KMPSearch kmpSearch = new KMPSearch();
        for (Job j : allJobs) {
            if (kmpSearch.containsPattern(j.getCity(), jobCity))
                jobsWithCity.add(j);
        }
        return jobsWithCity;
    }

    public List<Job> getAllJobs() {
        return jobDao.getAllJobs();
    }

    public boolean deleteJob(Job job) {
        return jobDao.delete(job);
    }

    public boolean saveJob(Job job) {
        List<Job> allJobs = jobDao.getAllJobs();
        KMPSearch kmpSearch = new KMPSearch();
        for (Job j : allJobs) {
            if (!kmpSearch.containsPattern(j.getCity(), job.getCity()) || !kmpSearch.containsPattern(j.getDescription(), job.getDescription())
                    || !kmpSearch.containsPattern(j.getTitle(), job.getTitle()) || !job.getJobId().equals(j.getJobId())) {
                jobDao.save(job);
                return true;
            }
        }
        return false;
    }
}