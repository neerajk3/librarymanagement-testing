package com.librarymanagement.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Neerajkumar
 *
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Address {

    @ApiModelProperty(name = "addressLine1", dataType = "String", example = "house-01")
    private String addressLine1;
    @ApiModelProperty(name = "addressLine2", dataType = "String", example = "testStreet")
    private String addressLine2;

    @ApiModelProperty(name = "area", dataType = "String", example = "Whitefield")
    private String area;
    @ApiModelProperty(name = "city", dataType = "String", example = "Bangalore")
    private String city;
    @ApiModelProperty(name = "state", dataType = "String", example = "Karnataka")
    private String state;
    @ApiModelProperty(name = "country", dataType = "String", example = "India")
    private String country;
    @ApiModelProperty(name = "zipCode", dataType = "String", example = "560105")
    private String zipCode;

}
