package vn.edu.iuh.fit.backend.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.backend.models.Customer;
import vn.edu.iuh.fit.backend.services.CustomerService;

import java.util.List;
import java.util.Optional;

@Path("/customer")
public class CustomerResource {
    private CustomerService customerService;

    public CustomerResource(){
        customerService = new CustomerService();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAll(){
        return  customerService.findAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getOne(@PathParam("id") long id){
        Optional<Customer> op = customerService.findOne(id);
        if (op.isPresent()){
            Customer customer = op.get();
            return Response.ok(customer).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertOne(Customer customer){
        boolean result = customerService.add(customer);
        if (result){
            return Response.ok(customer).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOne(Customer customer){
        boolean result = customerService.update(customer);
        if (result){
            return Response.ok(customer).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") long id){
        boolean result = customerService.delete(id);

        if (result){
            return Response.ok(id).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
