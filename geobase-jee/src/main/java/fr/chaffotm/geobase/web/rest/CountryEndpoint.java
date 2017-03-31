package fr.chaffotm.geobase.web.rest;

import fr.chaffotm.geobase.domain.Country;
import fr.chaffotm.geobase.service.CountryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("countries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CountryEndpoint {

    @Inject
    private CountryService countryService;

    @GET
    public Response findAll() {
        final List<Country> countries = countryService.findAll();
        if (countries.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(countries).build();
    }

}
