package server;

import com.hit.dm.Job;
import java.util.List;

public class Response implements java.io.Serializable {

    private List<Job> jobs;

    public Response(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return "Response{" +
                "jobs=" + jobs +
                '}';
    }
}
