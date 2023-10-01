package vn.edu.iuh.fit.backend.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.services.OrderService;

import java.util.List;
import java.util.Optional;

@Path("/order")
public class OrderResource {
    private OrderService orderService;

    public OrderResource(){
        this.orderService = new OrderService();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getAll(){
        return orderService.findAll();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getOne(@PathParam("id") long id){
        Optional<Order> op = orderService.findOne(id);
        if (op.isPresent()){
            return Response.ok(op.get()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertOne(Order order){
        boolean result = orderService.add(order);
        if (result){
            return Response.ok(order).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOne(Order order){
        boolean result = orderService.update(order);
        if (result){
            return Response.ok(order).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") long id){
        boolean result = orderService.delete(id);
        if (result){
            return Response.ok(id).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
