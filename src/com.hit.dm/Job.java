package com.hit.dm;

import java.util.Objects;

public class Job implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String description;
    private Long jobId;
    private String city;
    private boolean filled; // Indicates whether the job is already filled

    public Job() {}

    public Job(Long jobId, String title, String description, String city) {
        this.jobId = jobId;
        this.title = title;
        this.description = description;
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, title, description, city);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(title, job.title) && jobId == job.jobId && Objects.equals(description, job.description)
                && Objects.equals(city, job.city);
    }

    @Override
    public String toString() {
        return "Job{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", jobId=" + jobId +
                ", city='" + city + '\'' +
                ", filled=" + filled +
                '}';
    }

    public void setApplied(boolean b) {
    }
}