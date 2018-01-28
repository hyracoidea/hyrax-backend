package com.hyrax.microservice.account.data.entity;

import lombok.Data;

import java.io.Serializable;

@Data
abstract class AbstractEntity<ID extends Serializable> {

    protected ID id;
}