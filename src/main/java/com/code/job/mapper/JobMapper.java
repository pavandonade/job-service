package com.code.job.mapper;

import com.code.job.Job;
import com.code.job.dto.JobDTO;
import com.code.job.external.Company;
import com.code.job.external.Review;

import java.util.List;

public class JobMapper {

    public static JobDTO mapJobToJobWithCompanyDTO(Job job, Company company, List<Review> reviews) {

        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);
        return jobDTO;
    }
}
