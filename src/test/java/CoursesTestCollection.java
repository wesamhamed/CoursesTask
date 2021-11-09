import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class CoursesTestCollection {
    String token;

    @BeforeTest
    public void login(){
        JSONObject reqParams = new JSONObject();
        reqParams.put("email","olivier@mail.com");
        reqParams.put("password","bestPassw0rd");
        baseURI="http://localhost:3000";
        RequestSpecification request = given();
        request.header("content-type","application/json");
        request.body(reqParams.toString());
        Response  response= request.post("/login");
        token=response.jsonPath().get("accessToken");
//        System.out.println("Token "+response.jsonPath().get("accessToken"));
    }
    @Test(priority = 1)
    public void createCourse(){
        JSONObject reqParams = new JSONObject();
        reqParams.put("id",1);
        reqParams.put("title","QA");
        reqParams.put("description","QA description");
        baseURI="http://localhost:3000/660";
        RequestSpecification request = given();
        request.header("content-type","application/json");
        request.header("Authorization","Bearer "+token);
        request.body(reqParams.toString());
        Response  response= request.post("/courses");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,201);
        String title = response.jsonPath().get("title");
        Assert.assertEquals(title,"QA");
    }
    @Test(priority = 2)
    public void FetchAllCourses(){
        JSONObject reqParams = new JSONObject();
        baseURI="http://localhost:3000/660";
        RequestSpecification request = given();
        request.header("content-type","application/json");
        request.header("Authorization","Bearer "+token);
        Response  response= request.get("/courses");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
//        JSONObject bodyResponse =new JSONObject(response.body().asPrettyString());
//        JSONArray firstObject =bodyResponse.getJSONArray("0");
//        firstObject.
//        System.out.println("Body "+response.body().asPrettyString());
//        String title = response.jsonPath().get("title");
//        Assert.assertEquals(title,"QA");
    }
    @Test(priority = 3)
    public void updateCourse(){
        JSONObject reqParams = new JSONObject();
        reqParams.put("title","QA");
        reqParams.put("description","QA description 111");
        baseURI="http://localhost:3000/660";
        RequestSpecification request = given();
        request.header("content-type","application/json");
        request.header("Authorization","Bearer "+token);
        request.body(reqParams.toString());
        Response  response= request.put("/courses/1");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
        String title = response.jsonPath().get("title");
        Assert.assertEquals(title,"QA");
    }
    @Test(priority = 4)
    public void getCourse(){
        baseURI="http://localhost:3000/660";
        RequestSpecification request = given();
        request.header("content-type","application/json");
        request.header("Authorization","Bearer "+token);
        Response  response= request.get("/courses/1");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
        String title = response.jsonPath().get("title");
        Assert.assertEquals(title,"QA");
    }
    @Test(priority = 5)
    public void deleteCourse(){
        baseURI="http://localhost:3000/660";
        RequestSpecification request = given();
        request.header("content-type","application/json");
        request.header("Authorization","Bearer "+token);
        Response  response= request.delete("/courses/1");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,200);
    }
}
