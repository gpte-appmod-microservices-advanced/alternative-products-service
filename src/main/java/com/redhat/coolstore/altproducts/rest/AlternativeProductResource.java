package com.redhat.coolstore.altproducts.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.coolstore.altproducts.model.AlternativeProduct;

@ApplicationScoped
@Path("/altproduct")
public class AlternativeProductResource {

    private Map<String, String[]> alternativeProducts;

    @PostConstruct
    public void init() {
        alternativeProducts = new HashMap<>();
        alternativeProducts.put("329199", new String[] {"629199"});
        alternativeProducts.put("329299", new String[] {"629299","729999"});
    }

    @GET
    @Path("/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public AlternativeProduct getAlternativeProducts(@PathParam("itemId") String itemId) {
        AlternativeProduct alternative = new AlternativeProduct();
        alternative.setItemId(itemId);
        Optional<String[]> alternatives = Optional.ofNullable(alternativeProducts.get(itemId));
        alternative.setAltItemIds(alternatives.orElse(new String[] {}));
        return alternative;
    }

}
