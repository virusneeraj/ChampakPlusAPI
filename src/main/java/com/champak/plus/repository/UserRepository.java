package com.champak.plus.repository;

import com.champak.plus.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, String> {
    public User findUserByEmail(String email);
    /*public User findUserByIdentifier(String identifier);
    public User findUserByEmailAndPassword(String email, String password);*/
}
