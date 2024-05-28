package com.example.testlogin.project_java2.service.worker;
import com.example.testlogin.project_java2.constant.EnumConstant;
import com.example.testlogin.project_java2.dto.UserDto;
import com.example.testlogin.project_java2.mapper.UserMapper;
import com.example.testlogin.project_java2.model.Role;
import com.example.testlogin.project_java2.model.UserAccount;
import com.example.testlogin.project_java2.repo.RoleRepo;
import com.example.testlogin.project_java2.repo.UserRepo;
import com.example.testlogin.project_java2.security.Security;
import com.example.testlogin.project_java2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserWorker implements UserService{

    private final UserRepo userRepository;
    private final RoleRepo roleRepo;
    PasswordEncoder passwordEncoder;


    @Override
    public UserDto createUser(UserDto userDto) {

        UserAccount user = new UserAccount();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setType(EnumConstant.NORMAL);
        user.setActive(EnumConstant.isACTIVE);
        Role role = roleRepo.findByName("USER");
        user.setRoles(Collections.singletonList(role));

        UserAccount newUser = userRepository.save(user);
        UserDto userResponse = new UserDto();
        userResponse.setId(newUser.getId());
        userResponse.setEmail(newUser.getEmail());
        userResponse.setPassword(newUser.getPassword());
        userResponse.setType(newUser.getType());
        userResponse.setActive(newUser.getActive());

        return userResponse;
    }

    @Override
    public UserDto update_name_user(String name) {

        UserAccount user = userRepository.findByEmail(Security.getSessionUser());
        user.setName(name);
        UserAccount userUpdated = userRepository.save(user);

        UserDto userResponse = new UserDto();
        userResponse.setId(userUpdated.getId());
        userResponse.setEmail(userUpdated.getEmail());
        userResponse.setName(userUpdated.getName());
        userResponse.setType(userUpdated.getType());
        userResponse.setActive(userUpdated.getActive());

        System.out.println("User updated: " + userResponse);

        return userResponse;
    }


    @Override
    public List<UserDto> listUsersApi() {

        List<UserAccount> users = userRepository.findAll();

        return users.stream()
                .map(UserMapper::mapToUserApiDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserAccount findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean checkActiveUser() {
        UserAccount account = userRepository.findByEmail(Security.getSessionUser());

        if(account != null){
            System.out.println(account.getActive());
            return account.getActive() == EnumConstant.LOCKED;
        }
        return false;
    }


}
