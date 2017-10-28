package com.costrella.sp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.costrella.sp.domain.Noname;

import com.costrella.sp.repository.NonameRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Noname.
 */
@RestController
@RequestMapping("/api")
public class NonameResource {

    private final Logger log = LoggerFactory.getLogger(NonameResource.class);
        
    @Inject
    private NonameRepository nonameRepository;

    /**
     * POST  /nonames : Create a new noname.
     *
     * @param noname the noname to create
     * @return the ResponseEntity with status 201 (Created) and with body the new noname, or with status 400 (Bad Request) if the noname has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/nonames",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Noname> createNoname(@RequestBody Noname noname) throws URISyntaxException {
        log.debug("REST request to save Noname : {}", noname);
        if (noname.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("noname", "idexists", "A new noname cannot already have an ID")).body(null);
        }
        Noname result = nonameRepository.save(noname);
        return ResponseEntity.created(new URI("/api/nonames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("noname", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nonames : Updates an existing noname.
     *
     * @param noname the noname to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated noname,
     * or with status 400 (Bad Request) if the noname is not valid,
     * or with status 500 (Internal Server Error) if the noname couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/nonames",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Noname> updateNoname(@RequestBody Noname noname) throws URISyntaxException {
        log.debug("REST request to update Noname : {}", noname);
        if (noname.getId() == null) {
            return createNoname(noname);
        }
        Noname result = nonameRepository.save(noname);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("noname", noname.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nonames : get all the nonames.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nonames in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/nonames",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Noname>> getAllNonames(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Nonames");
        Page<Noname> page = nonameRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nonames");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /nonames/:id : get the "id" noname.
     *
     * @param id the id of the noname to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the noname, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/nonames/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Noname> getNoname(@PathVariable Long id) {
        log.debug("REST request to get Noname : {}", id);
        Noname noname = nonameRepository.findOne(id);
        return Optional.ofNullable(noname)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /nonames/:id : delete the "id" noname.
     *
     * @param id the id of the noname to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/nonames/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNoname(@PathVariable Long id) {
        log.debug("REST request to delete Noname : {}", id);
        nonameRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("noname", id.toString())).build();
    }

}
