package vn.edu.iuh.fit.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.models.ProductPrice;
import vn.edu.iuh.fit.services.ProductPriceService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Path("product-price")
public class ProductPriceResource {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private ProductPriceService productPriceService;

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
    public Response getOne(@QueryParam("product_id") long product_id, @QueryParam("price_date_time") String price_date_time){
        LocalDateTime localDateTime = LocalDateTime.parse(price_date_time, formatter);
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
    public Response deleteOne(@QueryParam("product_id") long product_id, @QueryParam("price_date_time") String price_date_time){
        LocalDateTime localDateTime = LocalDateTime.parse(price_date_time, formatter);
        boolean result = productPriceService.delete(product_id, localDateTime);

        if (result){
            return Response.ok(product_id + " " + price_date_time).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
