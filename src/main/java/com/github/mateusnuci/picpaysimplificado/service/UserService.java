package com.github.mateusnuci.picpaysimplificado.service;

import com.github.mateusnuci.picpaysimplificado.domain.user.User;
import com.github.mateusnuci.picpaysimplificado.domain.user.UserType;
import com.github.mateusnuci.picpaysimplificado.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT)
            throw new Exception("Usuário não tem autorização para realizar transações");
        if(sender.getBalance().compareTo(amount) < 0)
            throw new Exception("Saldo insuficiente");
    }

    public User findUserById(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        throw new Exception("Usuário não encontrado");
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


}
