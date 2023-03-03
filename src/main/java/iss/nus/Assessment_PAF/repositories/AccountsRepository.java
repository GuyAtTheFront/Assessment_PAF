package iss.nus.Assessment_PAF.repositories;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class AccountsRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SQL_SELECT_NAME_CONCAT_ID = 
    """
        SELECT CONCAT(name, " (", account_id, ")") AS name FROM accounts;         
    """;

    private final String SQL_SELECT_BALANCE_BY_ID = 
    """
        SELECT balance FROM accounts where account_id = ?;
    """;

    private final String SQL_UPDATE_BALANCE_BY_ID = 
    """
        update accounts SET balance = balance + ? where account_id = ?;
    """;

    // Task 4
    public List<String> getNameConcatID() {

        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_NAME_CONCAT_ID);

        List<String> names = new LinkedList<>();

        while (rs.next()){
            names.add(rs.getString("name"));
        }

        return names;
    }

    // Task 5(C5)
    public Optional<BigDecimal> getBalanceById(String id) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_BALANCE_BY_ID, id);

        return (rs.next()) ? Optional.of(rs.getBigDecimal("balance")) : Optional.empty();
    }

    // Task 6
    public Integer updateBalanceById(String id, BigDecimal delta) {

        Integer updated  = jdbcTemplate.update(SQL_UPDATE_BALANCE_BY_ID, delta, id);

        return updated;
    }

}
