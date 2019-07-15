package ru.morou.korekor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.morou.korekor.persist.model.Role;
import ru.morou.korekor.persist.model.User;
import ru.morou.korekor.persist.repo.RoleRepository;
import ru.morou.korekor.persist.repo.UserRepository;
import ru.morou.korekor.service.repr.SystemUser;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Autowired - Аннотация позволяет автоматически установить значение поля.
 * @Transactional - Перед исполнением метода помеченного данной аннотацией начинается транзакция, после выполнения
 * метода транзакция коммитится, при выбрасывании RuntimeException откатывается.
 */


public class UserServiceJpaImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceJpaImpl(UserRepository userRepository, RoleRepository roleRepository,
                              BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public SystemUser findById(Long id) {
        return new SystemUser(userRepository.findById(id).get());
    }

    @Override
    @Transactional
    public SystemUser findByUserName(String username) {
        User user = userRepository.findOneByUserName(username);
        return new SystemUser(user.getUserName(), user.getPassword(),
                user.getFirstName(), user.getLastName(), user.getEmail(), user.getRoles(), user.getPhoto ());
    }

    @Override
    @Transactional
    public boolean save(SystemUser systemUser) {
        User user = systemUser.getId() != null ? userRepository
                .findById(systemUser.getId())
                .orElse(new User()) : new User();
        user.setUserName(systemUser.getUserName());
        if (systemUser.getId() == null || (systemUser.getPassword() != null && !systemUser.getPassword().trim().isEmpty())) {
            user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        }
        user.setFirstName(systemUser.getFirstName());
        user.setLastName(systemUser.getLastName());
        user.setEmail(systemUser.getEmail());
        user.setPhoto(systemUser.getPhoto());
        user.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findOneByName("ROLE_CLIENT"))));
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<SystemUser> findAll() {
        return userRepository.findAll().stream()
                .map(SystemUser::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SystemUser user = findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
