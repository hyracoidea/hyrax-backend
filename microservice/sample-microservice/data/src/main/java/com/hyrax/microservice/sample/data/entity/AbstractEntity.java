package com.hyrax.microservice.sample.data.entity;

import lombok.Data;

import java.io.Serializable;

@Data
abstract class AbstractEntity<ID extends Serializable> {

    protected ID id;
}
