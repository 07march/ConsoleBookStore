package by.repository;

import by.entity.Store;
import by.repository.filedb.DB;
import by.repository.filedb.FileDB;

import java.util.List;

public class FileStoreRepository implements StoreRepository {
    private DB file = new FileDB();
    private List<Store> stores;

    public FileStoreRepository() {
        this.stores = file.getList(Store.class);
    }

//    public static void main(String[] args) {
//        StoreRepository storeRepository = new FileStoreRepository();
//        storeRepository.add(new Store("Mila", new Address("Mayak"), new City("Minsk")));
////    storeRepository.add(new Store("Ma23", new Address("Mayak"), new City("Minsk")));
////    storeRepository.add(new Store("34", new Address("Mayak"), new City("Minsk")));
//        System.out.println(Arrays.toString(storeRepository.findAll()));
//    }

    @Override
    public void add(Store store) {
        int lastStoreId = file.getId(Store.class);
        store.setId(++lastStoreId);
        stores.add(store);
        System.out.println("Магазин " + store.getName() + " добавлен");
        file.change(stores, lastStoreId, Store.class);
    }

    @Override
    public void delete(int id) {
        for (Store store : stores) {
            if (store == null) break;
            if (store.getId() == id) {
                stores.remove(store);
                System.out.println("Магазин " + store.getId() + " удален");
            }
            break;
        }
        file.change(stores, id, Store.class);
    }

    @Override
    public boolean update(String name, int id) {
        for (Store store : stores) {
            if (store == null) break;
            if (store.getId() == id) {
                store.setName(name);
                System.out.println("Название магазина изменено на: " + name);
                return true;
            }
            break;
        }
        file.change(stores, id, Store.class);
        return false;
    }

    @Override
    public Store[] findAll() {
        return stores.toArray(new Store[0]);
    }

    @Override
    public Store findByName(String name) {
        for (Store store : stores) {
          if (store == null) continue;
            if (store.getName().equals(name)) {
                System.out.println("Магазин найден: " + store.getName());
                return store;
            }
            break;
        }
        System.out.println("Магазин не найден");
        return null;
    }
}
