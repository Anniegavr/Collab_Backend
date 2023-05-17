package com.backend.collab_backend.administrator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdministratorServiceImpl implements AdministratorService{
  private final AdministratorRepository administratorRepository;

  @Override
  public List<Administrator> getAllAdministrators() {
    return administratorRepository.findAll();
  }

  @Override
  public Administrator getAdministratorByAdministratorId(Long id) {
    return administratorRepository.findById(id).get();
  }

  @Override
  public Administrator createAdministrator(AdministratorDTO administrator) {
    Administrator administrator1 = convertDTOtoRealAdmin(administrator);
    return administratorRepository.save(administrator1);
  }
  Administrator convertDTOtoRealAdmin(AdministratorDTO administratorDTO) {
    Administrator administrator = new Administrator();
    administrator.setFirstName(administratorDTO.firstName);
    administrator.setLastName(administratorDTO.lastName);
    administrator.setEmail(administratorDTO.email);
    administrator.setSpecialty(administratorDTO.specialty);
    administrator.setAge(administratorDTO.age);
    administrator.setRole(administratorDTO.role);
    administrator.setSecondRole(administratorDTO.secondRole);
    administrator.setThirdRole(administratorDTO.thirdRole);
    administrator.setUsername(administrator.getFirstName().toLowerCase().charAt(0)+administrator.getLastName().toLowerCase());
    return administrator;
  }

  public Administrator updateAdministrator(Long id, int age) {
    return administratorRepository.updateAgeBy(age);
  }

  @Override
  public ResponseEntity.BodyBuilder deleteAdministrator(Long id) {
    administratorRepository.deleteById(id);
    return ResponseEntity.ok();
  }
}
