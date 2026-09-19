package homeoffice.model;

import java.util.List;
import java.util.Objects;

/**
 * Product model class representing a product from the product catalog.
 * All product data is externalized to eliminate hardcoding.
 */
public class Product {
    private int id;
    private String name;
    private String price;
    private String description;
    private String sku;
    private List<String> aliases;

    // Constructors
    public Product() {
    }

    public Product(int id, String name, String price, String description, String sku) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.sku = sku;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", sku='" + sku + '\'' +
                '}';
    }
}

