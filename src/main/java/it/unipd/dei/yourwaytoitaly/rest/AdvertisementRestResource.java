package it.unipd.dei.yourwaytoitaly.rest;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Manages the REST API for the {@link Advertisement} resource.
 *
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public class AdvertisementRestResource extends RestResource {

    /**
     * Creates a new REST resource for managing {@code Employee} resources.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public AdvertisementRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(req, res, con);
    }

    public void createAdvertisement() throws IOException {
        Advertisement e  = null;
        Message m = null;

        try{

            final Employee employee =  Employee.fromJSON(req.getInputStream());

            // creates a new object for accessing the database and stores the employee
            e = new CreateEmployeeDatabase(con, employee).createEmployee();

            if(e != null) {
                res.setStatus(HttpServletResponse.SC_CREATED);
                e.toJSON(res.getOutputStream());
            } else {
                // it should not happen
                m = new Message("Cannot create the employee: unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        } catch (Throwable t) {
            if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
                m = new Message("Cannot create the employee: it already exists.", "E5A2", t.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            } else {
                m = new Message("Cannot create the employee: unexpected error.", "E5A1", t.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        }
    }

    /**
     * Reads an employee from the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void readEmployee() throws IOException {

        Employee e  = null;
        Message m = null;

        try{
            // parse the URI path to extract the badge
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("employee") + 8);

            final int badge = Integer.parseInt(path.substring(1));


            // creates a new object for accessing the database and reads the employee
            e = new ReadEmployeeDatabase(con, badge).readEmployee();

            if(e != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                e.toJSON(res.getOutputStream());
            } else {
                m = new Message(String.format("Employee %d not found.", badge), "E5A3", null);
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                m.toJSON(res.getOutputStream());
            }
        } catch (Throwable t) {
            m = new Message("Cannot read employee: unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }

    /**
     * Updates an employee in the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void updateEmployee() throws IOException {

        Employee e  = null;
        Message m = null;

        try{
            // parse the URI path to extract the badge
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("employee") + 8);

            final int badge = Integer.parseInt(path.substring(1));
            final Employee employee =  Employee.fromJSON(req.getInputStream());

            if (badge != employee.getBadge()) {
                m = new Message(
                        "Wrong request for URI /employee/{badge}: URI request and employee resource badges differ.",
                        "E4A7", String.format("Request URI badge %d; employee resource badge %d.",
                        badge, employee.getBadge()));
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                m.toJSON(res.getOutputStream());
                return;
            }

            // creates a new object for accessing the database and updates the employee
            e = new UpdateEmployeeDatabase(con, employee).updateEmployee();

            if(e != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                e.toJSON(res.getOutputStream());
            } else {
                m = new Message(String.format("Employee %d not found.", employee.getBadge()), "E5A3", null);
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                m.toJSON(res.getOutputStream());
            }
        } catch (Throwable t) {
            if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23503")) {
                m = new Message("Cannot update the employee: other resources depend on it.", "E5A4", t.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            } else {
                m = new Message("Cannot update the employee: unexpected error.", "E5A1", t.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        }
    }


    /**
     * Deletes an employee from the database.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void deleteEmployee() throws IOException {

        Employee e  = null;
        Message m = null;

        try{
            // parse the URI path to extract the badge
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("employee") + 8);

            final int badge = Integer.parseInt(path.substring(1));


            // creates a new object for accessing the database and deletes the employee
            e = new DeleteEmployeeDatabase(con, badge).deleteEmployee();

            if(e != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                e.toJSON(res.getOutputStream());
            } else {
                m = new Message(String.format("Employee %d not found.", badge), "E5A3", null);
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                m.toJSON(res.getOutputStream());
            }
        } catch (Throwable t) {
            if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23503")) {
                m = new Message("Cannot delete the employee: other resources depend on it.", "E5A4", t.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            } else {
                m = new Message("Cannot delete the employee: unexpected error.", "E5A1", t.getMessage());
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        }
    }

    /**
     * Searches employees by their salary.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void searchEmployeeBySalary()  throws IOException {

        List<Employee> el  = null;
        Message m = null;

        try{
            // parse the URI path to extract the salary
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("salary") + 6);

            final int salary = Integer.parseInt(path.substring(1));


            // creates a new object for accessing the database and search the employees
            el = new SearchEmployeeBySalaryDatabase(con, salary).searchEmployeeBySalary();

            if(el != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                new ResourceList(el).toJSON(res.getOutputStream());
            } else {
                // it should not happen
                m = new Message("Cannot search employee: unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        } catch (Throwable t) {
            m = new Message("Cannot search employee: unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }

    /**
     * Lists all the employees.
     *
     * @throws IOException
     *             if any error occurs in the client/server communication.
     */
    public void listEmployee() throws IOException {

        List<Employee> el  = null;
        Message m = null;

        try{
            // creates a new object for accessing the database and lists all the employees
            el = new ListEmployeeDatabase(con).listEmployee();

            if(el != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                new ResourceList(el).toJSON(res.getOutputStream());
            } else {
                // it should not happen
                m = new Message("Cannot list employees: unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                m.toJSON(res.getOutputStream());
            }
        } catch (Throwable t) {
            m = new Message("Cannot search employee: unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }


}
