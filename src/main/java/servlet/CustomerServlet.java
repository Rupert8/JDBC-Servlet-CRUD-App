package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CustomersService;

import java.io.IOException;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private final CustomersService service = CustomersService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        try (var writer = resp.getWriter()) {
            writer.write("<h1> Список Клієнтів </h1>");
            writer.write("<ul>");
            service.findAll().stream().forEach(customersDto ->
                    writer.write("""
                            <li>
                            <a href='/customer?customerId=%s'>%s</a>
                            </li>
                            """.formatted(customersDto.getId(), customersDto.getFirstName() + " " +customersDto.getLastName())));
            writer.write("</ul>");
        }


    }
}
