package de.timmi6790.mpstats.api.versions.v1.java.base.repository.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class GameCategory {
    @JsonIgnore
    private final int repositoryId;

    private final String repositoryName;
}
