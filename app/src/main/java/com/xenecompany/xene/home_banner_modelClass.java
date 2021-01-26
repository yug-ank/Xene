package com.xenecompany.xene;

public class home_banner_modelClass {
    ////todo
    /**
     * change int to string  for firebae contectivity
     */
    private String banner;
    private String id;

    public home_banner_modelClass() {
    }

    public home_banner_modelClass(String banner, String id) {
        this.banner = banner;
        this.id = id;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public String getId() {
        return id;
    }
}
