import java.util.Date;

public class Category {
    private String categoryName;
    private Date createAt;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    private String getCategoryName() {
        return categoryName;
    }

    private void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
