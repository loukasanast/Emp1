package io.anastasiou;

import io.anastasiou.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO extends DataAccessObject<Employee> {
    private static final String INSERT = "INSERT INTO tblEmployees " +
            "(firstName, lastName, position, salary) VALUES (?, ?, ?, ?)";
    private static final String GET = "SELECT id, firstNAme, lastName, position, salary " +
            "FROM tblEmployees WHERE id=?";
    private static final String GET_ALL = "SELECT id, firstName, lastName, position, salary " +
            "FROM tblEmployees";
    private static final String UPDATE = "UPDATE tblEmployees SET firstName=?, lastName=?, " +
            "position=?, salary=? WHERE id=?";
    private static final String DELETE = "DELETE FROM tblEmployees WHERE id=?";

    private final Connection connection;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Employee getById(int id) {
        Employee employee = null;

        try(PreparedStatement statement = this.connection.prepareStatement(GET)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setPosition(rs.getString("position"));
                employee.setSalary(rs.getDouble("salary"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();

        try(PreparedStatement statement = this.connection.prepareStatement(GET_ALL)) {
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setPosition(rs.getString("position"));
                employee.setSalary(rs.getDouble("salary"));
                employees.add(employee);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    @Override
    public Employee update(Employee dto) {
        Employee employee = null;

        try(PreparedStatement statement = this.connection.prepareStatement(UPDATE)) {
            statement.setString(1, dto.getFirstName());
            statement.setString(2, dto.getLastName());
            statement.setString(3, dto.getPosition());
            statement.setDouble(4, dto.getSalary());
            statement.setInt(5, dto.getId());
            statement.execute();
            employee = this.getById(dto.getId());
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    @Override
    public void create(Employee dto) {
        try(PreparedStatement statement = this.connection.prepareStatement(INSERT)) {
            statement.setString(1, dto.getFirstName());
            statement.setString(2, dto.getLastName());
            statement.setString(3, dto.getPosition());
            statement.setDouble(4, dto.getSalary());
            statement.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try(PreparedStatement statement = this.connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
