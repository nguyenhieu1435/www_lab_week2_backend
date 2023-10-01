package vn.edu.iuh.fit.backend.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.backend.models.OrderDetail;
import vn.edu.iuh.fit.backend.services.OrderDetailService;

import java.util.List;
import java.util.Optional;

@Path("order-detail")
public class OrderDetailResource {
    private OrderDetailService orderDetailService;

    public OrderDetailResource(){
        this.orderDetailService = new OrderDetailService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderDetail> getAll(){
        return orderDetailService.findAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{product_id}/{order_id}")
    public Response getOne(@PathParam("product_id") long product_id, @PathParam("order_id") long order_id){
        Optional<OrderDetail> op = orderDetailService.findOne(order_id, product_id);
        if (op.isPresent()){
            return Response.ok(op.get()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertOne(OrderDetail orderDetail){
        boolean result =  orderDetailService.add(orderDetail);
        if (result){
            return Response.ok(orderDetail).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOne(OrderDetail orderDetail){
        boolean result = orderDetailService.update(orderDetail);
        if (result){
            return Response.ok(orderDetail).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{product_id}/{order_id}")
    public Response deleteOne(@PathParam("product_id") long product_id, @PathParam("order_id") long order_id){
        boolean result = orderDetailService.delete(order_id, product_id);
        if (result){
            return Response.ok(product_id + " " + order_id).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
