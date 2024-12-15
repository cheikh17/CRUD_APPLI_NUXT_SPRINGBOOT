package com.train.application_crud.controller;

import com.train.application_crud.entity.Stock;
import com.train.application_crud.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "http://localhost:3000")
public class StockController {


    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Stock>> getAllStock() {
        List<Stock> stocks = stockService.findAll();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.findById(id);
        return ResponseEntity.ok(stock);
    }

    @PostMapping("/create")
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock createdStock = stockService.create(stock);
        return ResponseEntity.ok(createdStock);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        Stock updatedStock = stockService.update(id, stock);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
