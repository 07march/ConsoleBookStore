package by.repository;


import by.entity.City;
import by.repository.filedb.DB;
import by.repository.filedb.FileDB;

import java.util.List;

public class FileCityRepository implements CityRepository {
    private DB file = new FileDB();
    private List<City> cities;

//    public static void main(String[] args) {
//        CityRepository cityRepository = new FileCityRepository();
//        cityRepository.add(new City("Minsk"));
//        cityRepository.add(new City("Brest"));
//        System.out.println(Arrays.toString(cityRepository.findAll()));
//        cityRepository.update("Kiev", 1);
//        System.out.println(Arrays.toString(cityRepository.findAll()));
//    }

    public FileCityRepository() {
        this.cities = file.getList(City.class);
    }

    @Override
    public void add(City city) {
        int lastCityId = file.getId(City.class);
        city.setId(++lastCityId);
        cities.add(city);
        System.out.println("Город " + city.getName() + " добавлен");
        file.change(cities, lastCityId, City.class);
    }

    @Override
    public void delete(int id) {
        for (City city : cities) {
            if (city == null) break;
            if (city.getId() == id) {
                cities.remove(city);
                System.out.println("Город " + city.getId() + " удален");
                break;
            }
        }
        file.change(cities, id, City.class);
    }

    @Override
    public void delete(String name) {
        int lastCityId = file.getId(City.class);
        for (City city : cities) {
            if (city == null) break;
            if (city.getName().equals(name)) {
                cities.remove(city);
                System.out.println("Город " + city.getName() + " удален");
                break;
            }
        }
        file.change(cities, lastCityId, City.class);
    }

    @Override
    public boolean update(String name, int id) {
        for (City city : cities) {
            if (city == null) break;
            if (city.getId() == id) {
                city.setName(name);
                System.out.println("Город " + city.getId() + " изменен на: " + name);
                return true;
            }
            break;
        }
        file.change(cities, id, City.class);
        return false;
    }

    @Override
    public City[] findAll() {
        return cities.toArray(new City[0]);
    }

    @Override
    public City findByName(String name) {
        for (City city : cities) {
            if (city.getName() == null) continue;
            if (city.getName().equals(name)) {
                System.out.println("Город найден: " + city.getName());
                return city;
            }
            break;
        }
        System.out.println("Город не найден");
        return null;
    }

    @Override
    public City findById(int id) {
        for (City city : cities) {
            if (city.getId() == 0) continue;
            if (city.getId() == id) {
                System.out.println("Город найден: " + city.getName());
                return city;
            }
            break;
        }
        System.out.println("Город не найден");
        return null;
    }
}
