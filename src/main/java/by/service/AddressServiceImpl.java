package by.service;

import by.entity.Address;
import by.repository.AddressRepository;

public class AddressServiceImpl implements AddressService {
  private AddressRepository addressRepository;

  public AddressServiceImpl(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @Override
  public void add(Address address) {
    addressRepository.add(address);
  }

  @Override
  public void delete(String address) {
    addressRepository.delete(address);
  }

  @Override
  public boolean updateAddressById(String address, int id) {
    return addressRepository.updateAddressById(address, id);
  }

  @Override
  public Address[] findAll() {
    return addressRepository.findAll();
  }

  @Override
  public Address findById(int id) {
    return addressRepository.findById(id);
  }

  @Override
  public Address findByName(String name) {
    return addressRepository.findByName(name);
  }
}
