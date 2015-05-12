package org.saurabh.springboot.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    @Id
    private String id;

    @JsonCreator
    public Item(@JsonProperty("checked") boolean checked, @JsonProperty("description") String description) {
        this.checked = checked;
        this.description = description;
    }

    private boolean checked;
    private String description;

    public String getId() {
        return id;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }
}