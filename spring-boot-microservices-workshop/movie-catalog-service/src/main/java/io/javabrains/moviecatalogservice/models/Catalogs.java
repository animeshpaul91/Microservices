package io.javabrains.moviecatalogservice.models;

import java.util.Collections;
import java.util.List;

public class Catalogs {

    private String catalogOwner;
    private List<CatalogItem> catalogs;

    public Catalogs() {
        // for jackson deserialization
    }

    public Catalogs(final String catalogOwner, final List<CatalogItem> catalogs) {
        this.catalogOwner = catalogOwner;
        this.catalogs = catalogs;
    }

    public String getCatalogOwner() {
        return catalogOwner;
    }

    public void setCatalogOwner(final String catalogOwner) {
        this.catalogOwner = catalogOwner;
    }

    public List<CatalogItem> getCatalogs() {
        return Collections.unmodifiableList(catalogs);
    }

    public void setCatalogs(final List<CatalogItem> catalogs) {
        this.catalogs = catalogs;
    }
}
