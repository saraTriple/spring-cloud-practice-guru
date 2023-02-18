package practice.sara.msscbrew.web.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import practice.sara.msscbrew.services.BeerService;
import practice.sara.msscbrew.web.model.BeerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@Deprecated
@Validated
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/api/v1/beer/")
@RestController
public class BeerController {
    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeer(@NotNull @PathVariable("beerId") UUID beerId) {
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDto> createBeer(@Valid @NotNull @RequestBody BeerDto beerDto) {
        BeerDto savedDto = beerService.saveNewBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();
        // TODO: add hostname to url
        headers.add("Location", "/api/v1/beer/" + savedDto.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDto> updateCustomer(@PathVariable("beerId") UUID uuid,
                                                  @Valid @NotNull @RequestBody BeerDto beerDto) {
        beerService.update(uuid, beerDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@NotNull @PathVariable("beerId") UUID beerId) {
        beerService.deleteById(beerId);
    }

}
