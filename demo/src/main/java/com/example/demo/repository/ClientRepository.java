package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.entities.Client;
import com.example.demo.model.projections.ClientLoginProjection;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ClientRepository extends PagingAndSortingRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    @Query(nativeQuery = true, value= "select c.id, c.name, u.username, c.phone from client c inner join user u on c.name = u.username")
    List<ClientLoginProjection> ListClientLogin();
}
