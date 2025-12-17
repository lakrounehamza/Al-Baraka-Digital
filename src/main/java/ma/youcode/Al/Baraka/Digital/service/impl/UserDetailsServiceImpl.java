package ma.youcode.Al.Baraka.Digital.service.impl;

import lombok.AllArgsConstructor;
import ma.youcode.Al.Baraka.Digital.entity.User;
import ma.youcode.Al.Baraka.Digital.exception.NotFoundException;
import ma.youcode.Al.Baraka.Digital.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOp = userRepository.findByUsername(username);
        if(!userOp.isPresent())
            throw  new UsernameNotFoundException("not found user  by  name "+ username);
        User  user  = userOp.get();
        List<GrantedAuthority>   authorities  = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return   new org.springframework.security.core.userdetails.User(username,user.getPassword(),authorities);
    }
}
