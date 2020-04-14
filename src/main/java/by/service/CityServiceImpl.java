package by.service;

import by.entity.City;
import by.repository.CityRepository;

public class CityServiceImpl implements CityService {
    private CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public void add(City city) {
        cityRepository.add(city);
    }

    @Override
    public void delete(int id) {
        cityRepository.delete(id);
    }

    @Override
    public void delete(String name) {
        cityRepository.delete(name);
    }

    @Override
    public boolean update(String name, int id) {
        return cityRepository.update(name, id);
    }

    @Override
    public City[] findAll() {
        return cityRepository.findAll();
    }

    @Override
    public City findByName(String name) {
        return cityRepository.findByName(name);
    }

    @Override
    public City findById(int id) { return cityRepository.findById(id); }
}
