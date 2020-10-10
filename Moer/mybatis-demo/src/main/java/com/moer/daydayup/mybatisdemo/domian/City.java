package com.moer.daydayup.mybatisdemo.domian;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * city
 * @author 
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class City implements Serializable {
    private Integer id;

    private String name;

    private String state;

    private String country;

    private static final long serialVersionUID = 1L;
}