package com.code.job;

import com.code.job.dto.JobWithCompanyDTO;

import java.util.List;

public interface JobService{

    List<JobWithCompanyDTO> findAll();
    Job createJob(Job job);
    Job getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean updateJob(Long id, Job updatedJob);
}
