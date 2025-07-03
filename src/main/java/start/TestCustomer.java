package start;

import dao.CustomerDao;
import entity.Customers;

public class TestCustomer {
    public static void main(String[] args) {
        CustomerDao customerDao = CustomerDao.getInstance();
        Customers customers = new Customers();
        customers.setFirstName("Anton");
        customers.setLastName("Dan");
        customers.setEmail("anton.dan@gmail.com");

        Customers customersUpdate = new Customers();
        customersUpdate.setFirstName("Viktor");
        customersUpdate.setLastName("Predko");
        customersUpdate.setEmail("anton.predko@gmail.com");


        var customersSaveResult = customerDao.save(customers);
        /* insert customer*/System.out.println(customersSaveResult);

        /* update customer*/System.out.println(customerDao.update(11,customersUpdate));

        /* findAll customers*/System.out.println(customerDao.findAll().toString());

        /* Delete customer by id*/System.out.println(customerDao.delete(customersSaveResult.getId()));

        /* update customer*/System.out.println(customerDao.findById(11).toString());
    }
}
