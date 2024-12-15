package com.train.application_crud.service;

import com.train.application_crud.entity.Employe;
import com.train.application_crud.repository.EmployeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j  // Pour les logs
public class EmployeService {

    private final EmployeRepository employeRepository;

    // Constructor injection
    public EmployeService(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    public Employe create(Employe employe){
        log.info("Création d'un nouvel employé");
        validateEmploye(employe);
        return employeRepository.save(employe);
    }

    public List<Employe> findAll(){
        log.info("Récupération de tous les employés");
        return employeRepository.findAll();
    }

    public Employe findById(long id){
        log.info("Recherche de l'employé avec l'id : {}", id);
        return employeRepository.findById(id)
                .orElseThrow(() -> new EmployeNotFoundException(id));
    }

    public void delete(long id){
        log.info("Suppression de l'employé avec l'id : {}", id);
        if (employeRepository.existsById(id)) {
            employeRepository.deleteById(id);
        } else {
            throw new EmployeNotFoundException(id);
        }
    }

    public Employe update(Long id, Employe updatedEmploye){
        log.info("Mise à jour de l'employé avec l'id : {}", id);
        validateEmploye(updatedEmploye);

        Employe existingEmploye = employeRepository.findById(id)
                .orElseThrow(() -> new EmployeNotFoundException(id));

        existingEmploye.setEmployeName(updatedEmploye.getEmployeName());
        existingEmploye.setEmployeEmail(updatedEmploye.getEmployeEmail());
        existingEmploye.setEmployeSurname(updatedEmploye.getEmployeSurname());
        existingEmploye.setEmployePhone(updatedEmploye.getEmployePhone());
        existingEmploye.setEmployePassword(updatedEmploye.getEmployePassword());
        existingEmploye.setEmployeDepartment(updatedEmploye.getEmployeDepartment());

        return employeRepository.save(existingEmploye);
    }

    private void validateEmploye(Employe employe) {
        if (employe == null) {
            throw new IllegalArgumentException("L'employé ne peut pas être null");
        }
        if (employe.getEmployeName() == null || employe.getEmployeName().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'employé est requis");
        }
        if (employe.getEmployeEmail() == null || !employe.getEmployeEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email invalide");
        }
    }
}