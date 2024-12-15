package com.train.application_crud.service;


import com.train.application_crud.entity.Stock;
import com.train.application_crud.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {


    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public Stock findById(Long id) {
        return stockRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Stock not found"));
    }

    public Stock create(Stock stock) {
        validateStock(stock);
        return stockRepository.save(stock);
    }

    public void delete(Long id) {
        if (stockRepository.existsById(id)){
            stockRepository.deleteById(id);
        }
        else {
            throw new IllegalArgumentException("Stock not found");
        }
    }

    public Stock update(Long id, Stock updatedStock) {

        validateStock(updatedStock);
        Stock existingStock = stockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found"));



        existingStock.setDescription(updatedStock.getDescription());
        existingStock.setNomProduit(updatedStock.getNomProduit());
        existingStock.setPrix(updatedStock.getPrix());
        existingStock.setQuantite(updatedStock.getQuantite());

        return stockRepository.save(existingStock);
    }

    private void validateStock(Stock stock) {
        if (stock == null) {
            throw new IllegalArgumentException("Le stock ne peut pas être null");
        }
        if (stock.getDescription() == null || stock.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("La description du produit est requis");
        }
        if (stock.getNomProduit() == null || stock.getNomProduit().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du produit est requis");
        }
        if (stock.getQuantite() == null) {
            throw new IllegalArgumentException("La quantité est requis");
        }
    }
}
