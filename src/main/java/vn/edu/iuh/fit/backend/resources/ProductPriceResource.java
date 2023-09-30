package vn.edu.iuh.fit.backend.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.services.ProductPriceService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Path("/product-price")
public class ProductPriceResource {
    private ProductPriceService productPriceService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public ProductPriceResource(){
        this.productPriceService = new ProductPriceService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductPrice> getAll(){
        return productPriceService.findAll();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{product_id}/{price_date_time}")
    public Response getOne(@PathParam("product_id") long product_id, @PathParam("price_date_time") String priceDateTime){
        LocalDateTime localDateTime = LocalDateTime.parse(priceDateTime, formatter);
        Optional<ProductPrice> op = productPriceService.findOne(product_id, localDateTime);
        if (op.isPresent()){
            return Response.ok(op.get()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertOne(ProductPrice productPrice){
        boolean result = productPriceService.add(productPrice);
        if (result){
            return Response.ok(productPrice).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOne(ProductPrice productPrice){
        boolean result = productPriceService.update(productPrice);
        if (result){
            return Response.ok(productPrice).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{product_id}/{price_date_time}")
    public Response deleteOne(@PathParam("product_id") long product_id, @PathParam("price_date_time") String priceDateTime){
        LocalDateTime localDateTime = LocalDateTime.parse(priceDateTime, formatter);
        boolean result = productPriceService.delete(product_id, localDateTime);
        if (result){
            return Response.ok(product_id + " " + priceDateTime).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
