package com.train.application_crud.controller;

import com.train.application_crud.entity.Employe;
import com.train.application_crud.service.EmployeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeController {

    private final EmployeService employeService;

    // Constructor injection au lieu de @Autowired
    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employe>> getAllEmployes() {
        List<Employe> employes = employeService.findAll();
        return ResponseEntity.ok(employes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable Long id) {
        Employe employe = employeService.findById(id);
        return ResponseEntity.ok(employe);
    }

    @PostMapping("/create")
    public ResponseEntity<Employe> createEmploye(@RequestBody Employe employe) {
        Employe createdEmploye = employeService.create(employe);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmploye);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employe> updateEmploye(@PathVariable Long id, @RequestBody Employe employe) {
        Employe updatedEmploye = employeService.update(id, employe);
        return ResponseEntity.ok(updatedEmploye);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        employeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}