package vn.edu.iuh.fit.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iuh.fit.models.Product;
import vn.edu.iuh.fit.services.ProductService;

import java.util.List;
import java.util.Optional;

@Path("product")
public class ProductResource {
    private ProductService productService;

    public ProductResource(){
        this.productService = new ProductService();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAll(){
        return productService.findAll();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getOne(@PathParam("id") long id){
        Optional<Product> op = productService.findOne(id);
        if (op.isPresent()){
            return Response.ok(op.get()).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertOne(Product product){
        boolean result = productService.add(product);
        if (result){
            return Response.ok(product).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOne(Product product){
        boolean result = productService.update(product);
        if (result){
            return Response.ok(product).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteOne(@PathParam("id") long id){
        boolean result = productService.delete(id);
        if (result){
            return Response.ok(id).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
