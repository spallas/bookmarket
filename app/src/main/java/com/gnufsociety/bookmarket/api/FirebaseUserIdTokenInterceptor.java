package com.gnufsociety.bookmarket.api;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


//TODO DA RIVEDERE BENE!!!
public class FirebaseUserIdTokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null)
                throw new Exception("User not authenticated");
            Task<GetTokenResult> task = user.getIdToken(true);

            String token = Tasks.await(task).getToken();

            if (token == null)
                throw new Exception("Token null!");
            Request modified = request.newBuilder()
                    .addHeader("Authorization", "Bearer "+token)
                    .build();
            return chain.proceed(modified);

        } catch(Exception e){
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }
}
