package be.cleardigital.demo.tech;

import com.google.gson.annotations.SerializedName;

public class Enterprise {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
