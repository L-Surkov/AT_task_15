package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import models.Support;
import models.Meta;

@Data
public class UserSingleResponse {
    private User data;
    private Support support;
    @JsonProperty("_meta")
    private Meta meta;
}