package com.luv2code.springboot.cruddemo.dao;
import com.luv2code.springboot.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


//spring data jpa can be used to get rid of dao and impl
@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

    //define field for entitymanager
    private EntityManager entityManager;

    //set up constructor injection
    @Autowired
    public EmployeeDAOHibernateImpl (EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public List<Employee> findAll() {
        //get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        //create a query
        Query<Employee> theQuery = currentSession.createQuery("from Employee", Employee.class);

        //execute query and get result
        List<Employee> employees = theQuery.getResultList();

        //return result
        return employees;
    }

    @Override
    public Employee findById(int theId) {
        //get current session
        Session currentSession = entityManager.unwrap(Session.class);

        //get the employee
        Employee theEmployee = currentSession.get(Employee.class, theId);

        //return the employee
        return theEmployee;
    }

    @Override
    public void save(Employee theEmployee) {
        //get current session
        Session currentSession = entityManager.unwrap(Session.class);

        //save employee
        currentSession.save(theEmployee);

    }

    @Override
    public void deleteById(int theId) {
        //get current session
        Session currentSession = entityManager.unwrap(Session.class);

        //delete employee
        Query theQuery = currentSession.createQuery(
                "delete from Employee where id=:employeeId");
        theQuery.setParameter("employeeId", theId);

        theQuery.executeUpdate();
    }
}
