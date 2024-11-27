package server;

import com.hit.dao.JobDaoImpl;
import com.hit.service.JobService;
import controller.JobController;

import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private final int port;

    public Server(int i) {
        port = i;
    }

    public void run(){
        try {
            ServerSocket server = new ServerSocket(port);
            JobController jobController = new JobController(new JobService(new JobDaoImpl("src/resources/jobs.txt")));
            while(true) {
                Socket someClient = server.accept();
                HandleRequest request = new HandleRequest(someClient, jobController);
                request.process();
            }
        } catch (Exception e) {
            System.out.println("tiered of waiting for connection");
        }
    }
}