package com.code.job.impl;

import com.code.job.Job;
import com.code.job.JobRepository;
import com.code.job.JobService;
import com.code.job.clients.CompanyClient;
import com.code.job.clients.ReviewClient;
import com.code.job.dto.JobDTO;
import com.code.job.external.Company;
import com.code.job.external.Review;
import com.code.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final CompanyClient companyClient;

    private final ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    @CircuitBreaker(name = "companyBreaker")
    public List<JobDTO> findAll() {

        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(this::convertToDto).toList();
    }

    private JobDTO convertToDto(Job job) {

        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        return JobMapper.mapJobToJobWithCompanyDTO(job, company, reviews);
    }

    @Override
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        return jobRepository.findById(id)
                .map(job -> {
                    job.setTitle(updatedJob.getTitle());
                    job.setDescription(updatedJob.getDescription());
                    job.setMinSalary(updatedJob.getMinSalary());
                    job.setMaxSalary(updatedJob.getMaxSalary());
                    job.setLocation(updatedJob.getLocation());
                    jobRepository.save(job); // persist changes
                    return true;
                })
                .orElse(false);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job != null)
            return convertToDto(job);
        else
            return null;
    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
