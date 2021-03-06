package com.costrella.sp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costrella.sp.domain.Family;

import com.costrella.sp.repository.FamilyRepository;
import com.costrella.sp.web.rest.util.HeaderUtil;
import com.costrella.sp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Family.
 */
@RestController
@RequestMapping("/api")
public class FamilyResource {

    private final Logger log = LoggerFactory.getLogger(FamilyResource.class);
        
    @Inject
    private FamilyRepository familyRepository;

    /**
     * POST  /families : Create a new family.
     *
     * @param family the family to create
     * @return the ResponseEntity with status 201 (Created) and with body the new family, or with status 400 (Bad Request) if the family has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/families",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Family> createFamily(@Valid @RequestBody Family family) throws URISyntaxException {
        log.debug("REST request to save Family : {}", family);
        if (family.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("family", "idexists", "A new family cannot already have an ID")).body(null);
        }
        Family result = familyRepository.save(family);
        return ResponseEntity.created(new URI("/api/families/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("family", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /families : Updates an existing family.
     *
     * @param family the family to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated family,
     * or with status 400 (Bad Request) if the family is not valid,
     * or with status 500 (Internal Server Error) if the family couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/families",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Family> updateFamily(@Valid @RequestBody Family family) throws URISyntaxException {
        log.debug("REST request to update Family : {}", family);
        if (family.getId() == null) {
            return createFamily(family);
        }
        Family result = familyRepository.save(family);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("family", family.getId().toString()))
            .body(result);
    }

    /**
     * GET  /families : get all the families.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of families in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/families",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Family>> getAllFamilies(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Families");
        Page<Family> page = familyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/families");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /families/:id : get the "id" family.
     *
     * @param id the id of the family to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the family, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/families/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Family> getFamily(@PathVariable Long id) {
        log.debug("REST request to get Family : {}", id);
        Family family = familyRepository.findOne(id);
        return Optional.ofNullable(family)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /families/:id : delete the "id" family.
     *
     * @param id the id of the family to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/families/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFamily(@PathVariable Long id) {
        log.debug("REST request to delete Family : {}", id);
        familyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("family", id.toString())).build();
    }

}
