package com.backend.collab_backend.administrator;

import com.backend.collab_backend.student.Student;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdministratorServiceImpl implements AdministratorService{
  private static final Logger logger = LoggerFactory.getLogger(AdministratorServiceImpl.class);
  private final AdministratorRepository administratorRepository;

  @Override
  public List<AdministratorDTO> getAllAdministrators() {
    List<Administrator> administrators = administratorRepository.findAll();
    List<AdministratorDTO> administratorDTOs = new ArrayList<>();
    for (Administrator administrator : administrators) {
      AdministratorDTO administratorDTO = convertRealToDTOAdmin(administrator);
      administratorDTOs.add(administratorDTO);
    }
    return administratorDTOs;
  }

  public AdministratorDTO convertRealToDTOAdmin(Administrator administrator) {
    AdministratorDTO adminDTO = new AdministratorDTO();
    adminDTO.firstName = administrator.getFirstName();
    adminDTO.lastName = administrator.getLastName();
    adminDTO.email = administrator.getEmail();
    adminDTO.specialty = administrator.getSpecialty();
    return adminDTO;
  }


  @Override
  public Administrator getAdministratorByAdministratorEmail(String email) {
    Optional<Administrator> admin = administratorRepository.findAdministratorByEmail(email);
    return admin.orElseGet(Administrator::new);
  }

  public Long signinAdmin(String login, String password) {
    if(login.contains("@")) {
      Optional<Administrator> admin = administratorRepository.findAdministratorByEmailAndPassword(login, password);
      if (admin.isPresent()) {
        return admin.get().getId();
      } else {return 0L;}
    } else {
      Optional<Administrator> admin = administratorRepository.findAdministratorByUsernameAndPassword(login, password);
      if (admin.isPresent()) {
        return admin.get().getId();
      } else {return 0L;}
    }
  }

  @Override
  public AdministratorDTO createAdministrator(AdministratorDTO administrator) {
    Administrator administrator1 = convertDTOtoRealAdmin(administrator);
    administratorRepository.save(administrator1);
    return administrator;
  }
  Administrator convertDTOtoRealAdmin(AdministratorDTO administratorDTO) {
    Administrator administrator = new Administrator();
    System.out.println(administratorDTO.firstName);
    administrator.setFirstName(administratorDTO.firstName);
    administrator.setLastName(administratorDTO.lastName);
    administrator.setEmail(administratorDTO.email);
    System.out.println(administratorDTO.email);
    administrator.setSpecialty(administratorDTO.specialty);
    administrator.setUsername(administratorDTO.firstName.charAt(0)+administratorDTO.lastName);
    return administrator;
  }

  public AdministratorDTO editAdmin(String email, AdministratorDTO administratorDTO) {
    Optional<Administrator> administratorOptional = administratorRepository.findAdministratorByEmail(email);
    if (administratorOptional.isPresent()) {
      Administrator administrator = administratorOptional.get();
      administrator.setEmail(administratorDTO.email);
      administrator.setSpecialty(administratorDTO.specialty);
      administrator.setUsername(administratorDTO.firstName+administratorDTO.lastName);
      administrator.setFirstName(administratorDTO.firstName);
      administrator.setLastName(administratorDTO.lastName);
      System.out.println("Saving edited admin");
      administratorRepository.save(administrator);
    }
    return administratorDTO;
  }
  @Override
  public ResponseEntity.BodyBuilder deleteAdministrator(String email) {
    Optional<Administrator> administratorOptional = administratorRepository.findAdministratorByEmail(email);
    if (administratorOptional.isPresent()) {
      Administrator administrator = administratorOptional.get();
      administratorRepository.deleteById(administrator.getId());
    }
    return ResponseEntity.ok();
  }
}
