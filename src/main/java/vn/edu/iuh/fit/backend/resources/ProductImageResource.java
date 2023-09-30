package vn.edu.iuh.fit.backend.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.backend.models.ProductImage;
import vn.edu.iuh.fit.backend.services.ProductImageService;

import java.util.List;
import java.util.Optional;

@Path("/product-image")
public class ProductImageResource {
    private ProductImageService productImageService;

    public ProductImageResource(){
        this.productImageService = new ProductImageService();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductImage> getAll(){
        return productImageService.findAll();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getOne(@PathParam("id") long id){
        Optional<ProductImage> op = productImageService.findOne(id);
        if (op.isPresent()){
            return Response.ok(op.get()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertOne(ProductImage productImage){
        boolean result = productImageService.add(productImage);
        if (result){
            return Response.ok(productImage).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOne(ProductImage productImage){
        boolean result = productImageService.update(productImage);
        if (result){
            return Response.ok(productImage).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") long id){
        boolean result = productImageService.delete(id);
        if (result){
            return Response.ok(id).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}

