package com.hit.dao;

import com.hit.dm.Job;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobDaoImpl implements IDao<Job> {
    public final String file_path;

    public JobDaoImpl(String filePath) {
        file_path = filePath;
        try {
            if (emptyData()) {
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file_path))) {
                    objectOutputStream.writeObject(new HashMap<Long, Job>());
                    objectOutputStream.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Job entity) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file_path));
            HashMap<Long, Job> jobs = (HashMap<Long, Job>) objectInputStream.readObject();
            objectInputStream.close();

            jobs.remove(entity.getJobId());

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file_path));
            objectOutputStream.writeObject(jobs);
            objectOutputStream.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Job> getAllJobs(){
        HashMap<Long, Job> jobs = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file_path))) {
            jobs = (HashMap<Long, Job>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(jobs != null)
            return new ArrayList<>(jobs.values());
        return null;
    }

    @Override
    public boolean save(Job job) throws IllegalArgumentException {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file_path));
                HashMap<Long, Job> jobs = (HashMap<Long, Job>) objectInputStream.readObject();
                jobs.put(job.getJobId(), job);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file_path));
                objectOutputStream.writeObject(jobs);
                objectOutputStream.flush();
                objectInputStream.close();
            } catch (ClassNotFoundException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        return true;
    }

    private boolean emptyData() throws IOException, ClassNotFoundException {
        File file = new File(file_path);
        return !file.exists() || file.length() == 0;
    }

}