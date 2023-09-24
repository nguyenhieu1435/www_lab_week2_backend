package vn.edu.iuh.fit.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.models.Employee;
import vn.edu.iuh.fit.services.EmployeeService;

import java.util.List;
import java.util.Optional;

@Path("/employee")
public class EmployeeResource {
    private EmployeeService employeeService;
    public EmployeeResource(){
        this.employeeService = new EmployeeService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAll(){
        return employeeService.findAll();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getOne(@PathParam("id") long id){
        Optional<Employee> op = employeeService.findOne(id);
        if (op.isPresent()){
            Employee employee = op.get();
            return Response.ok(employee).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertOne(Employee employee){
        boolean result = employeeService.add(employee);
        if (result){
            return Response.ok(employee).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOne(Employee employee){
        boolean result = employeeService.update(employee);
        if (result){
            return Response.ok(employee).build();
        }
        return  Response.status(Response.Status.BAD_REQUEST).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") long id){
        boolean result = employeeService.delete(id);
        if (result){
            return Response.ok(id).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
