package vn.edu.iuh.fit.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.models.OrderDetail;
import vn.edu.iuh.fit.services.OrderDetailService;

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
    public Response getOne(@QueryParam("product_id") long product_id, @QueryParam("order_id") long order_id){
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
    public Response deleteOne(@QueryParam("order_id") long order_id, @QueryParam("product_id") long product_id){
        boolean result = orderDetailService.delete(order_id, product_id);
        if (result){
            return Response.ok(order_id + " "  + product_id).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
