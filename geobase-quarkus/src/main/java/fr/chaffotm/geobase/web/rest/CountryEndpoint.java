package fr.chaffotm.geobase.web.rest;

import fr.chaffotm.geobase.service.CountryService;
import fr.chaffotm.geobase.web.resource.Frame;
import fr.chaffotm.geodata.resource.Country;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("countries")
public class CountryEndpoint implements ResourceEndpoint {

    private final CountryService countryService;

    public CountryEndpoint(final CountryService countryService) {
        this.countryService = countryService;
    }

    @GET
    public Frame<Country> findAll(@QueryParam("offset") @DefaultValue("1") @Min(value = 1, message = "offset cannot be less than 1") final int offset,
                                  @QueryParam("limit") final Integer limit,
                                  @QueryParam("sort") @DefaultValue("id") final String sort) {
        return countryService.findAll(offset, limit, sort);
    }

    @POST
    public Response create(@Valid @NotNull final Country country) {
        final long countryId = countryService.create(country);
        return Response.created(UriBuilder.fromResource(CountryEndpoint.class)
                .path(String.valueOf(countryId)).build())
                .build();
    }

    @GET
    @Path("{id}")
    public Country get(@PathParam("id") final long id) {
        return countryService.get(id);
    }

    @PUT
    @Path("{id}")
    public Country update(@PathParam("id") long id, @Valid final Country country) {
        return countryService.update(id, country);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") final long id) {
        countryService.delete(id);
    }

}
