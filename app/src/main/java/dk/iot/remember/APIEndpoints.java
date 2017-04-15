package dk.iot.remember;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by Jesper on 14/04/2017.
 */

interface APIEndpoints {

    @POST("/api/v1/boards")
    Call<Board> createBoard(@HeaderMap Map<String, String> headers, @Body HashMap<String, Board> board);

    @POST("/api/sign_in")
    Call<User> signIn(@Body HashMap<String, User> user);


}
