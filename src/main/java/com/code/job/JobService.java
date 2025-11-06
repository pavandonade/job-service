package com.code.job;

import com.code.job.dto.JobDTO;

import java.util.List;

public interface JobService{

    List<JobDTO> findAll();
    Job createJob(Job job);
    JobDTO getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean updateJob(Long id, Job updatedJob);
}
