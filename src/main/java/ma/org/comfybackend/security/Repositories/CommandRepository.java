package ma.org.comfybackend.security.Repositories;

import ma.org.comfybackend.security.Entities.Command;
import ma.org.comfybackend.security.Entities.Customer;
import ma.org.comfybackend.security.Enumerations.CommandState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommandRepository extends JpaRepository<Command, Integer> {
    List<Command> findByCustomer(Customer c);

    @Query("SELECT COUNT(c) FROM Command c WHERE MONTH(c.date) = MONTH(CURRENT_DATE()) AND YEAR(c.date) = YEAR(CURRENT_DATE())")
    Long countCommandsInCurrentMonth();

    @Query("SELECT SUM(c.totalPrice) FROM Command c WHERE c.commandState <> :exclude")
    Long countProfits(@Param("exclude") CommandState nameToExclude);

    @Query("SELECT SUM(c.totalPrice) FROM Command c WHERE c.commandState <> :exclude AND MONTH(c.date) = MONTH(CURRENT_DATE()) AND YEAR(c.date) = YEAR(CURRENT_DATE())")
    Long currentMounthProfits(@Param("exclude") CommandState nameToExclude);

    @Query("SELECT SUM(c.totalPrice) FROM Command c WHERE c.commandState <> :exclude AND MONTH(c.date) = :month AND YEAR(c.date) = :year")
    Long previousMounthProfits(@Param("exclude") CommandState nameToExclude,@Param("month") int month, @Param("year") int year);

}