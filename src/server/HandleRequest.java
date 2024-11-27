package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hit.dm.Job;
import controller.JobController;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HandleRequest {
    private final JobController jobController;
    private final Socket someClient;
    Gson g = new GsonBuilder().create();

    public HandleRequest(Socket someClient, JobController jobController){
        this.someClient = someClient;
        this.jobController = jobController;
    }

    public void process() throws IOException {
        Scanner reader = new Scanner(new InputStreamReader(this.someClient.getInputStream()));
        String line = reader.nextLine();
        Request request = g.fromJson(line, Request.class);
        String action = request.getAction();
        Map<String, Object> jobBody;
        String jobBodyStr;
        Job job;
        List<Job> jobs;
        Map<String, Object> jobData;
        Long jobId;
        switch (action){
            case "saveJob":
                jobBody = request.getBody();
                jobData = (Map<String, Object>) jobBody.get("job");
                jobId = ((Double) jobData.get("jobId")).longValue();
                job = new Job(jobId, (String) jobData.get("title"), (String) jobData.get("description"), (String) jobData.get("city"));
                if (jobController.saveJob(job)) {
                    System.out.println("Job save: " + request.getBody());
                } else{
                    System.out.println("Job not save, it already in the database: " + request.getBody());
                }
                break;
            case "deleteJob":
                jobBody = request.getBody();
                jobData = (Map<String, Object>) jobBody.get("job");
                jobId = ((Double) jobData.get("jobId")).longValue();
                job = new Job(jobId, (String) jobData.get("title"), (String) jobData.get("description"), (String) jobData.get("city"));
                if (jobController.deleteJob(job)) {
                    System.out.println("Job save: " + request.getBody());
                }
                break;
            case "findJobById":
                jobBody = request.getBody();
                jobBodyStr = jobBody.get("jobId").toString();
                jobs = jobController.getJobById(Long.parseLong(jobBodyStr));
                if(jobs != null) {
                    transToClient(jobs);
                    System.out.println("The job with this id: " + jobs);
                }
                else
                    System.out.println("Not was found a job with this id: " + jobBodyStr);
                break;
            case "findJobByDesc":
                jobBody = request.getBody();
                jobBodyStr = jobBody.get("jobDesc").toString();
                jobs = jobController.getJobByDesc(jobBodyStr);
                if(jobs != null) {
                    transToClient(jobs);
                    System.out.println("The job with this description: " + jobs);
                }
                else
                    System.out.println("Not was found a job with this description: " + jobBodyStr);
                break;
            case "findJobByTitle":
                jobBody = request.getBody();
                jobBodyStr = jobBody.get("jobTitle").toString();
                jobs = jobController.getJobByTitle(jobBodyStr);
                if(jobs != null){
                    transToClient(jobs);
                    System.out.println("The job with this title: " + jobs);
                }
                else
                    System.out.println("Not was found a job with this title: " + jobBodyStr);
                break;
            case "findJobByCity":
                jobBody = request.getBody();
                jobBodyStr = jobBody.get("jobCity").toString();
                jobs = jobController.getJobByCity(jobBodyStr);
                if(jobs != null){
                    transToClient(jobs);
                    System.out.println("The job with this city: " + jobs);
                }
                else
                    System.out.println("Not was found a job with this city: " + jobBodyStr);
                break;
            case "getAllJobs":
                jobs = jobController.getAllJobs();
                if(jobs != null) {
                    transToClient(jobs);
                    System.out.println("all jobs are:" + jobs);
                } else {
                    System.out.println("Not was found any job");
                }
                break;
            default:
                break;
        }
        reader.close();
    }

    private void transToClient(List<Job> jobs) throws IOException {
        Response response = new Response(jobs);
        String jsonResponse = g.toJson(response);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(someClient.getOutputStream()));
        writer.println(jsonResponse);
        writer.flush();
        writer.close();
    }

    private void close(ObjectOutputStream output, ObjectInputStream input) throws IOException {
        output.close();
        input.close();
        someClient.close();
    }

}