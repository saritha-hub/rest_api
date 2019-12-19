import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
//import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicTest {
    public String authtoken = null;

    @Test
    public void testStatusCode() {
        given()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products")
                .then()
                .statusCode(200);
    }

    @Test
    public void testLogging() {
        given()
                .log().all()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
    }

    @Test
    public void printResponse() {
        Response res = given().when()
                .log().all()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(res.prettyPrint());
        System.out.println("********************************");
    }

    @Test
    public void testCurrency() {
        given()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products")
                .then()
                .body("data.attributes[0].currency", equalTo("USD"));

    }

    @Test
    public void testCurrencyDetails() {
        Response res = given()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        JsonPath jsonpathvalue = res.jsonPath();
        List<Map> products = jsonpathvalue.getList("data");

        for (Map productvalues : products) {
            //   Map valuelist  = productvalues.get("attributes");
            Map attributes = (Map) productvalues.get("attributes");
            System.out.println(attributes.get("currency"));
            // System.out.println("productvalues" +productvalues);
            //Assert.assertEquals(attributes.get("currency")).toString(),"USD");
            Assert.assertEquals(attributes.get("currency").toString(), "USD");

        }

    }

    @Test
    public void testFilter() {
        Response res = given()
                .log().all()
                .queryParam("filter[name]", "bag")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(res.prettyPrint());

    }

    @Test
    public void testId() {
        Response res = given()
                .log().all()
                .queryParam("filter[ids]", "2")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(res.prettyPeek());

    }

    @Test
    public void testPrice() {
        Response res = given()
                .log().all()
                .queryParam("filter[price]", "22.99")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(res.prettyPeek());

    }

    @Test
    public void testTaxons() {
        Response res = given()
                .log().all()
                .queryParam("filter[taxons]", "6")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(res.prettyPeek());

    }

    @Test
    public void testSizeColors() {
        Response res = given()
                .log().all()
                .queryParam("filter[options][tshirt-color]", "S", "Blue")
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(res.prettyPeek());

    }

    @BeforeClass
    public void authToken() {
        Response res = given()
                .formParam("grant_type", "password")
                .formParam("username", "pitla.sarita@gmail.com")
                .formParam("password", "123456")
                .post("https://spree-vapasi-prod.herokuapp.com/spree_oauth/token");
        System.out.println(res.prettyPrint());
        authtoken = "Bearer " + res.path("access_token");
        System.out.println(authtoken);
    }

    @Test
    public void testPostCall() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);
        String createBody = "{\n" +
                "  \"variant_id\": \"17\",\n" +
                "  \"quantity\": 5\n" +
                "}";
        Response res = given()
                .headers(headers)
                .body(createBody)
                .when()
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item");
        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test
    public void testPostCall2() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);
        String createBody = "{\n" +
                "  \"variant_id\": \"6\",\n" +
                "  \"quantity\": 2\n" +
                "}";
        Response res = given()
                .headers(headers)
                .body(createBody)
                .when()
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item");
        Assert.assertEquals(res.statusCode(), 200);
    }


    @Test
    public void testViewCart() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);

        Response res = given()
                .headers(headers)
                .when()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart");
        Assert.assertEquals(res.statusCode(), 200);
        System.out.println(res.prettyPrint());

    }

    @Test
    public void testDeleteCart() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);

        Response res = given()
                .headers(headers)
                .when()
                .delete("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/remove_line_item/404");
        Assert.assertEquals(res.statusCode(), 200);
        System.out.println(res.prettyPrint());

    }



}




