package ebills.models;

/**
 * Created by pranavsharma on 09/05/15.
 */
public class Bill_Pic {
    
    public  String Column__id = "_id";
    public  String Column_image_path = "image_path";
    public  String Column_image_name = "image_name";
    public  String Column_image_date = "image_date";
    public  String Column_image_seller_id = "image_seller_id";

    public String getColumn__id() {
        return Column__id;
    }

    public void setColumn__id(String column__id) {
        Column__id = column__id;
    }

    public String getColumn_image_path() {
        return Column_image_path;
    }

    public void setColumn_image_path(String column_image_path) {
        Column_image_path = column_image_path;
    }

    public String getColumn_image_name() {
        return Column_image_name;
    }

    public void setColumn_image_name(String column_image_name) {
        Column_image_name = column_image_name;
    }

    public String getColumn_image_date() {
        return Column_image_date;
    }

    public void setColumn_image_date(String column_image_date) {
        Column_image_date = column_image_date;
    }

    public String getColumn_image_seller_id() {
        return Column_image_seller_id;
    }

    public void setColumn_image_seller_id(String column_image_seller_id) {
        Column_image_seller_id = column_image_seller_id;
    }
}
