package com.sell.service;

import com.sell.model.Company;
import com.sell.model.Item;
import com.sell.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepo;

    public void saveCompany(Company com){
        companyRepo.save(com);}

    public void updateCompany(long id,Company com){
        com.setCompanyId(id); companyRepo.save(com);}

    public void deleteCompany(long id){
        companyRepo.deleteById(id);}

    public List<Company> showAll(){
        return companyRepo.findAll();}

    public Optional<Company> showCompanyById(long id){
        return companyRepo.findById(id);
    }

    public Company getCompany(long id){
        return companyRepo.findById(id).get();
    }

    public List<Company> getAllCategory(){
        return companyRepo.findAll();
    }
}
