package dev.mememarket.persistence;

import dev.mememarket.model.Company;

import java.util.Collection;
import java.util.List;

public interface MarketRepository {
    List<Company> loadCompanies();
    void saveCompanies(Collection<Company> companies);
}
