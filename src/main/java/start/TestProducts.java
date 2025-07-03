package start;

import dao.ProductsDao;
import entity.Products;

public class TestProducts {
    public static void main(String[] args) {
        ProductsDao dao = ProductsDao.getInstance();
        Products p = new Products();
        p.setProductName("test");
        p.setDescription("test");
        p.setPrice(100);

        System.out.println(dao.findAll());
    }
}
