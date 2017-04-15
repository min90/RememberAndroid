package dk.iot.remember;

import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jesper on 14/04/2017.
 */

class NetworkManager {

    private static final String SHARED = "MY_SHARED";
    private static final String DEBUG_TAG = NetworkManager.class.getSimpleName();
    private static final String BASE_URL = "https://rhubarb-surprise-64515.herokuapp.com/";
    private SharedPreferences sharedprefs;
    private SharedPreferences.Editor editor;

    private Context context;

    NetworkManager(Context context) {
        this.context = context;
        this.sharedprefs = this.context.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        this.editor = this.sharedprefs.edit();
    }

    private Retrofit getRetrofitInstance(OkHttpClient builder) {
        if (builder == null) {
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(builder)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    private OkHttpClient builder() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    void addBoard(Board board) {
        Log.d(DEBUG_TAG, "Token in board: " + sharedprefs.getString("token", null));
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Token token=" + sharedprefs.getString("token", null));

        HashMap<String, Board> boards = new HashMap<>();
        boards.put("board", board);

        APIEndpoints apiEndpoints = getRetrofitInstance(builder()).create(APIEndpoints.class);
        Call call1 = apiEndpoints.createBoard(headers, boards);


        call1.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(DEBUG_TAG, "response: " + response.body());
                Log.d(DEBUG_TAG, "Error: " + response.errorBody());
                Toast.makeText(context, "Board created", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(DEBUG_TAG, "Error. " + t.toString());
                Toast.makeText(context, "Board creation failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void signIn(User user) {

        APIEndpoints apiEndpoints = getRetrofitInstance(builder()).create(APIEndpoints.class);
        HashMap<String, User> m = new HashMap<>();
        m.put("user_login", user);

        Call call2 = apiEndpoints.signIn(m);
        call2.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.body() != null) {
                    User user = (User) response.body();
                    String authToken = user.getAuthToken();
                    editor.putString("token", authToken).commit();
                    Log.d(DEBUG_TAG, "Token : " + authToken);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(DEBUG_TAG, "Error: " + t.toString());
            }
        });
    }
}
